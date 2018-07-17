package com.example.q.diaryapp;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContentActivity  extends AppCompatActivity {
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int CROP_CODE = 1113;
    private final int DELETE_CODE = 1114;
    private Uri mImageCaptureUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //DB: 시작할 때 data 조회해서 있으면 보여주고, 없으면 새로 만들기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = new Intent(this.getIntent());

        final EditText editText = findViewById(R.id.edit_text);

        String[] date = getDate();

        TextView dateView = (TextView) findViewById(R.id.dateView);

        dateView.setText(date[3]);
        dateView.append(" / ");
        dateView.append(date[1]);
        dateView.append(" ");
        dateView.append(date[2]);
        dateView.append(" / ");
        dateView.append(date[0]);



        ImageView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime();
                editText.append(time);
            }
        });

        ImageView photo = findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"앨범에서 사진 선택", "카메라로 사진 촬영", "취소"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContentActivity.this);
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which){
                            case 0:
                                //앨범에서 사진 선택
                                selectGallery();
                                dialogInterface.dismiss();
                                break;
                            case 1:
                                //카메라로 사진 촬영
                                selectPhoto();
                                dialogInterface.dismiss();
                                break;
                            case 2:
                                //취소
                                dialogInterface.cancel();
                                break;
                            default:
                                //불가능한 접근
                                dialogInterface.cancel();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        TextView done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB: 여기서 날짜와 사진, text INSERT!!!
            }
        });
    }

    public String[] getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-E", Locale.KOREA);
        String str_date = df.format(new Date());

        String[] arr_date = str_date.split("-");
        switch (arr_date[1]) {
            case "01":
                arr_date[1] = "JANUARY";
                break;
            case "02":
                arr_date[1] = "FABRUARY";
                break;
            case "03":
                arr_date[1] = "MARCH";
                break;
            case "04":
                arr_date[1] = "APRIL";
                break;
            case "05":
                arr_date[1] = "MAY";
                break;
            case "06":
                arr_date[1] = "JUNE";
                break;
            case "07":
                arr_date[1] = "JULY";
                break;
            case "08":
                arr_date[1] = "AUGUST";
                break;
            case "09":
                arr_date[1] = "SEPTEMBER";
                break;
            case "10":
                arr_date[1] = "OCTOBER";
                break;
            case "11":
                arr_date[1] = "NOVEMBER";
                break;
            case "12":
                arr_date[1] = "DECEMBER";
                break;
            default:
                //impossible
                break;
        }

        switch (arr_date[3]){
            case "월":
                arr_date[3]="MONDAY";
                break;
            case "화":
                arr_date[3]="TUESDAY";
                break;
            case "수":
                arr_date[3]="WEDNESDAY";
                break;
            case "목":
                arr_date[3]="THURSDAY";
                break;
            case "금":
                arr_date[3]="FRIDAY";
                break;
            case "토":
                arr_date[3]="SATURDAY";
                break;
            case "일":
                arr_date[3]="SUNDAY";
                break;
            default:
                //impossible
                break;
        }

        return arr_date;
    }

    public String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String str_time = sdfNow.format(date);

        String[] time = new String[3];
        time = str_time.split(":");

        if(Integer.parseInt(time[0])>12){
            int temp = Integer.parseInt(time[0]);
            int temp2 = temp - 12;
            time[0] = String.valueOf(temp2);
            return time[0]+":"+time[1]+"pm ";
        }

        else{
            return time[0]+":"+time[1]+"am ";
        }

    }

    private void selectPhoto(){

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, CAMERA_CODE);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/DCIM/Camera/"

                + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        ImageView iv = findViewById(R.id.imageView);
        iv.setVisibility(View.VISIBLE);
        final Bitmap rbitmap = rotate(bitmap, exifDegree);
        iv.setImageBitmap(rbitmap);
        //ST: 흑백 on이면 아래 4줄 처리!
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);                        //0이면 grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        iv.setColorFilter(cf);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentActivity.this, BigImage.class);
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("return-data", true);
                intent.putExtra("path", currentPhotoPath);
                //DB: 여기에서 쓰던 text INSERT!(text만)
                startActivity(intent);
            }
        });
    }

    private void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GALLERY_CODE:{
                    mImageCaptureUri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(mImageCaptureUri, "image/*");
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CROP_CODE);
                    break;}
                case CAMERA_CODE:{
                    getPictureForPhoto();
                    break;}

                    case CROP_CODE:{
                    final Bundle extras = data.getExtras();


                    if(extras != null)
                    {
                        final Bitmap photo = extras.getParcelable("data");
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageBitmap(photo);
                        imageView.setVisibility(View.VISIBLE);
                        //ST: 흑백 on이면 아래 4줄 처리!
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0);                        //0이면 grayscale
                        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                        imageView.setColorFilter(cf);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ContentActivity.this, BigImage.class);
                                intent.setDataAndType(mImageCaptureUri, "image/*");
                                intent.putExtra("return-data", true);
                                intent.putExtra("uri", mImageCaptureUri);
                                //DB: 여기에서 쓰던 text INSERT!!(text만)
                                startActivity(intent);
                            }
                        });
                    }

                    // 임시 파일 삭제
                    File f = new File(mImageCaptureUri.getPath());
                    if(f.exists())
                    {
                        f.delete();
                    }

                    break;}
                case DELETE_CODE:
                    Intent intent = new Intent(this.getIntent());
                    Bundle extras = intent.getExtras();
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setVisibility(View.GONE);
                    break;
                default:
                    System.out.println("불가능한 접근\n");
                    break;
            }
        }
    }

    private void sendPicture(Uri imgUri) {
        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ImageView iv = findViewById(R.id.imageView);
        iv.setVisibility(View.VISIBLE);
        iv.setImageBitmap(rotate(bitmap, exifDegree));
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

}
