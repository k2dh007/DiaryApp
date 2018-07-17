package com.example.q.diaryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class BigImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        Intent intent = new Intent(this.getIntent());

        ImageView imageView = findViewById(R.id.imgView);

        Bundle extras = intent.getExtras();

        if(extras.getString("path") != null) {

            String currentPhotoPath = extras.getString("path");

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
            imageView.setImageBitmap(rotate(bitmap, exifDegree));
        }

        else {
            Uri uriValue = extras.getParcelable("uri");
            Glide.with(this).load(uriValue).into(imageView);
        }

        ImageView delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BigImage.this, ContentActivity.class);
                int DELETE_CODE = 1114;
                startActivityForResult(intent, DELETE_CODE);
            }
        });
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
}
