package com.example.anmol.pic_divider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.anmol.pic_divider.adapter.SwipeDeckAdapter;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.anmol.pic_divider.R.id.button;


public class MainActivity extends AppCompatActivity {

    private SwipeDeck cardStack;
    private ArrayList<String> listLike;
    private ArrayList<String> listDislike;


    private static int loop=0;
    //Taking this to have loop
    private HashMap<String,ArrayList<String>> hashMap;
    ArrayList<String> fileNameKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStack= (SwipeDeck) findViewById(R.id.swipe_deck);
        listLike=new ArrayList<>();
        listDislike=new ArrayList<>();
        hashMap=new HashMap<String,ArrayList<String>>();
        fileNameKeys=new ArrayList<>();

        //Getting All File Paths
        getFilePaths(Environment.getExternalStorageDirectory());

        loop();

        System.out.println("Loop Called : "+loop);

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

                SuperActivityToast.create(MainActivity.this,new Style(),Style.TYPE_PROGRESS_BAR).setProgressBarColor(Color.WHITE)
                        .setText("Folder  :  " + fileNameKeys.get(loop)).setDuration(Style.DURATION_SHORT).setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_ORANGE)).setAnimations(Style.ANIMATIONS_FLY).show();

                loop();
            }
            @Override
            public void cardActionDown() {


            }
            @Override
            public void cardActionUp() {


            }
        });


    }


    //loop
    public void loop(){
        System.out.println("Value of loop : " + loop);

        while(loop<fileNameKeys.size()){
            //checking if list corresponding to a key is empty or not
            if (hashMap.get(fileNameKeys.get(loop)).isEmpty()){
                loop+=1;
                continue;
            }
            else{
                System.out.println("Value of  i : "+loop);
                System.out.println(loop+" -> " + fileNameKeys.get(loop));
                SwipeDeckAdapter swipeDeckAdapter=new SwipeDeckAdapter(hashMap.get(fileNameKeys.get(loop)),MainActivity.this);
                cardStack.setAdapter(swipeDeckAdapter);
                loop+=1;
                break;
            }
        }
    }

    //Dislike Photos
    public void dislikePhotos(int pos){
        listDislike.add(hashMap.get(fileNameKeys.get(loop-1)).get(pos));
        System.out.println(listDislike);
    }

    //Like Photos
    public void LikePhotos(int pos){
        System.out.println("Loop Inside Like function : "+loop);
        listLike.add(hashMap.get(fileNameKeys.get(loop-1)).get(pos));
        System.out.println("Size: " + listLike.size());
    }

    // Method to get All Images Path
    public ArrayList<String> getFilePaths(File root){

        ArrayList<String> arrayfiles=new ArrayList<>();
        File[] files=root.listFiles();

        for (File f:files){

            if (f.isDirectory() && !f.isHidden()){
                fileNameKeys.add(f.getName());
                hashMap.put(f.getName(),getFilePaths(f));
            }

            else{

                if ( f.getName().contains(".jpg")|| f.getName().contains(".JPG")
                        || f.getName().contains(".jpeg")|| f.getName().contains(".JPEG")
                        || f.getName().contains(".png") || f.getName().contains(".PNG")
                        || f.getName().contains(".gif") || f.getName().contains(".GIF")
                        || f.getName().contains(".bmp") || f.getName().contains(".BMP")){


                    arrayfiles.add(f.getAbsolutePath());

                }
            }

        }

        return arrayfiles;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Done :

                dialog();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    public void dialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete DisLikeItems Permenantly ?");
        //ans is yes then delete dislikes one till now
        alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                //Deleting all dislike Images

                                for (int i=0;i<listDislike.size();i++){

                                    //getting file
                                    File file=new File(listDislike.get(i));

                                    //deleting file if exists
                                    if (file.exists()){
                                        file.delete();
                                    }

                                }

                                SuperActivityToast.create(MainActivity.this).setText("Successfully Deleted").setColor(Color.BLUE).setDuration(Style.DURATION_LONG).show();

                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dont do anything as the ans. is no
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
