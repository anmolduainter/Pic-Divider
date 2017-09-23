package com.example.anmol.pic_divider.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anmol.pic_divider.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter {

    List<String> li;
    Context ctx;

    public SwipeDeckAdapter(List<String> li, Context ctx){
        this.li=li;
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return li.size();
    }

    @Override
    public Object getItem(int i) {
        return li.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_layout, viewGroup, false);
        }

        // Showing Images Here
        Uri uri = Uri.fromFile(new File(li.get(i)));
        Picasso.with(ctx).load(uri).fit().into((ImageView) v.findViewById(R.id.SdCardImages));

        return v;
    }
}
