package com.example.crud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Conexao conexao;
    private SQLiteDatabase database;


    public UsuarioDAO(Context context) {
        conexao = new Conexao(context);
        database = conexao.getWritableDatabase();
    }


    public long inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        return database.insert("usuario", null, values);
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = database.query("usuario", new String[]{"id", "nome", "email", "senha"}, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNome(cursor.getString(1));
                usuario.setEmail(cursor.getString(2));
                usuario.setSenha(cursor.getString(3));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void delete(Usuario usuario) {
        database.delete("usuario", "id = ?", new String[]{usuario.getId().toString()});
    }

    public void atualizar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        database.update("usuario", values, "id = ?", new String[]{usuario.getId().toString()});
    }

    public Usuario procurar(Usuario usuario) {

        Cursor cursor = database.query("usuario", new String[]{"id", "nome", "email", "senha"}, "email = ? AND senha = ?", new String[]{usuario.getEmail(),usuario.getSenha()}, null, null, null);
        Usuario usuarioRetorno = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            usuarioRetorno = new Usuario();
            usuarioRetorno.setId(cursor.getInt(0));
            usuarioRetorno.setNome(cursor.getString(1));
            usuarioRetorno.setEmail(cursor.getString(2));
            usuarioRetorno.setSenha(cursor.getString(3));
        }
            return usuarioRetorno;
    }
}
