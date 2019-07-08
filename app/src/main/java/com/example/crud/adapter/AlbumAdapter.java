package com.example.crud.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crud.R;
import com.example.crud.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter {

    private List<Album> albumList;
    private Context context;

    public AlbumAdapter(List<Album> albumList,  Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_album, viewGroup, false);
        AlbumViewHolder holder = new AlbumViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        AlbumViewHolder holder = (AlbumViewHolder) viewHolder;

        Album album  = albumList.get(position) ;

        holder.titulo.setText(album.getTitle());
        Picasso.with(context).load(album.getThumbnailUrl()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.crud).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
