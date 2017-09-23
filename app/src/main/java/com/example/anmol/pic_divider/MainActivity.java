package com.example.anmol.pic_divider;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.anmol.pic_divider.adapter.SwipeDeckAdapter;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwipeDeck cardStack;
    private ArrayList<File> imageFiles;
    private ArrayList<String> images;
    private ArrayList<String> listLike;
    private ArrayList<String> listDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStack= (SwipeDeck) findViewById(R.id.swipe_deck);
        listLike=new ArrayList<>();
        listDislike=new ArrayList<>();
        ArrayList<String> images=new ArrayList<>();

        //getting all images path
        imageFiles=getFilePaths(Environment.getExternalStorageDirectory());

        for (int i=0;i<imageFiles.size();i++){
            images.add(imageFiles.get(i).getAbsolutePath());
        }

        //printing images
        System.out.println(images);

        //size of images
        System.out.println(images.size());


        //Setting Adapter
        SwipeDeckAdapter swipeDeckAdapter=new SwipeDeckAdapter(images,MainActivity.this);

        cardStack.setAdapter(swipeDeckAdapter);

        //setting Events Callback

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

             dislikePhotos(position);

            }

            @Override
            public void cardSwipedRight(int position) {

                LikePhotos(position);

            }

            @Override
            public void cardsDepleted() {

            }
            @Override
            public void cardActionDown() {


            }
            @Override
            public void cardActionUp() {


            }
        });


    }


    //Dislike Photos
    public void dislikePhotos(int pos){
        listDislike.add(images.get(pos));
        System.out.println(listDislike);
    }

    //Like Photos
    public void LikePhotos(int pos){
        listLike.add(images.get(pos));
        System.out.println(listLike);
    }

    // Method to get All Images Path
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
