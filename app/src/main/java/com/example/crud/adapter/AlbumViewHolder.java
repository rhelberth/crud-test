package com.example.crud.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crud.R;

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    final TextView titulo;
    final ImageView thumbnail;


    public AlbumViewHolder(View view) {
        super(view);
        titulo = (TextView) view.findViewById(R.id.txt_titulo_album);
        thumbnail = (ImageView) view.findViewById(R.id.img_thumb_album);
    }

}