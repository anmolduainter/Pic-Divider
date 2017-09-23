package com.example.anmol.pic_divider;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<File> imageFiles;

        ArrayList<String> images=new ArrayList<>();

        //getting all images path
        imageFiles=getFilePaths(Environment.getExternalStorageDirectory());

        for (int i=0;i<imageFiles.size();i++){
            images.add(imageFiles.get(i).getName().toString());
        }

        //printing images
        System.out.println(images);

        //size of images
        System.out.println(images.size());



    }

    public ArrayList<File> getFilePaths(File root){

        ArrayList<File> arrayfiles=new ArrayList<>();
        File[] files=root.listFiles();

        for (File f:files){

            if (f.isDirectory() && !f.isHidden()){
                arrayfiles.addAll(getFilePaths(f));
            }

            else{

                if ( f.getName().contains(".jpg")|| f.getName().contains(".JPG")
                        || f.getName().contains(".jpeg")|| f.getName().contains(".JPEG")
                        || f.getName().contains(".png") || f.getName().contains(".PNG")
                        || f.getName().contains(".gif") || f.getName().contains(".GIF")
                        || f.getName().contains(".bmp") || f.getName().contains(".BMP")){


                    arrayfiles.add(f);

                }
            }

        }

        return arrayfiles;

    }


}
