package com.infinite.bitmapregiondecoder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LargeBitmapView img1,img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img2= (LargeBitmapView) findViewById(R.id.img2);

        try {
            InputStream is=getAssets().open("world.jpg");
            img2.setInputStream(is);
//            BitmapFactory.Options tempOptions=new BitmapFactory.Options();
//            tempOptions.inJustDecodeBounds=true;
//            BitmapFactory.decodeStream(is,null,tempOptions);
//            int width=tempOptions.outWidth;
//            int height=tempOptions.outHeight;
//
//            BitmapRegionDecoder brd=BitmapRegionDecoder.newInstance(is,false);
//            BitmapFactory.Options options=new BitmapFactory.Options();
//            options.inJustDecodeBounds=false;
//            Bitmap bitmap=brd.decodeRegion(new Rect(0,0,2000,2000),options);
//            img2.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
