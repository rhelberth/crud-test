package com.example.crud.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud.R;
import com.example.crud.activity.CadastroActivity;
import com.example.crud.activity.LoginActivity;
import com.example.crud.database.UsuarioDAO;
import com.example.crud.model.Usuario;
import com.example.crud.utils.PrefsConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltro = new ArrayList<>();
    private TextView nomeUsuario;
    private int idUser;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        listView = view.findViewById(R.id.list_usuarios);
        nomeUsuario = view.findViewById(R.id.text_nome_usuario);
        dao = new UsuarioDAO(view.getContext());
        usuarios = dao.listarTodos();
        usuariosFiltro.addAll(usuarios);
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, usuariosFiltro);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        verificarUsuarioLogado();


        return view;
    }

    public void buscaUsuario(String termo) {
        usuariosFiltro.clear();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().toLowerCase().contains(termo.toLowerCase())) {
                usuariosFiltro.add(usuario);
            }
        }
        listView.invalidateViews();
    }

    public void editar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Usuario usuarioEditar = usuariosFiltro.get(menuInfo.position);

        Intent intent = new Intent(getActivity(), CadastroActivity.class);
        intent.putExtra("usuario", usuarioEditar);
        startActivity(intent);
    }


    public void cadastrar(MenuItem item) {
        Intent intent = new Intent(getActivity(), CadastroActivity.class);
        startActivity(intent);
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Usuario usuarioExcluir = usuariosFiltro.get(menuInfo.position);

        if (usuarioExcluir.getId().equals(idUser)){
            Toast.makeText(getActivity(), "Não é possível excluir o usuario logado", Toast.LENGTH_LONG).show();
        }else {

            AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle("Atenção")
                    .setMessage("Realmente deseja excluir o usuario?")
                    .setNegativeButton("Não", null)
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            usuariosFiltro.remove(usuarioExcluir);
                            usuarios.remove(usuarioExcluir);
                            dao.delete(usuarioExcluir);
                            listView.invalidateViews();
                            Toast.makeText(getActivity(), usuarioExcluir.getNome() + " removido com sucesso!", Toast.LENGTH_LONG).show();
                        }
                    }).create();
            dialog.show();
        }
    }

    public void sair(MenuItem item){
        SharedPreferences settings = Objects.requireNonNull(getActivity()).getSharedPreferences(PrefsConstants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(PrefsConstants.NOME_USER);
        editor.apply();
        abrirLogin();

    }

    private void verificarUsuarioLogado() {

        SharedPreferences settings = Objects.requireNonNull(getActivity()).getSharedPreferences(PrefsConstants.PREFS_NAME, 0);
        if (!settings.getString(PrefsConstants.NOME_USER, "").isEmpty()) {
            nomeUsuario.setText(settings.getString(PrefsConstants.NOME_USER,""));
        }
        if (settings.getInt(PrefsConstants.ID_USER, 0) != 0) {
            idUser = settings.getInt(PrefsConstants.ID_USER,0);
        }

    }

    public void abrirLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_lista, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
               editar(item);
                return true;
            case R.id.menu_excluir:
              excluir(item);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        usuarios = dao.listarTodos();
        usuariosFiltro.clear();
        usuariosFiltro.addAll(usuarios);
        listView.invalidateViews();
        verificarUsuarioLogado();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscaUsuario(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bar_cadastrar:
                cadastrar(item);
                return true;
            case R.id.menu_bar_sair:
                sair(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

