package com.example.crud.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crud.R;
import com.example.crud.adapter.AlbumAdapter;
import com.example.crud.model.Album;
import com.example.crud.retrofit.RetrofitConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;


    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        // Monta o progress
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Carregando....");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.recycler);

        //Recupera albuns
        getAlbum();

        return view;
    }

    private void getAlbum() {
        /*
         Recupera lista de albuns
        */
        Call<List<Album>> call = new RetrofitConfig().getAlbumService().listarAlbum();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                progressDialog.dismiss();
                geraDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e("Album   ", "Erro ao buscar o Album:" + t.getMessage());
            }
        });
    }

   public void geraDataList(List<Album> albumList){
       recyclerView.setAdapter(new AlbumAdapter(albumList, getActivity()));
       RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity(),
               LinearLayoutManager.VERTICAL, false);
       recyclerView.setLayoutManager(layout);
   }
}
