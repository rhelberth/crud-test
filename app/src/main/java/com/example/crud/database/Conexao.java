package com.example.crud.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String dbNome = "banco.db";
    private static final int version = 1;


    public Conexao(Context context) {
        super(context, dbNome, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(id integer primary key autoincrement," +
                "nome varchar(50) not null, email varchar(50) not null, senha varchar(50) not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //necessaria implementacao caso o banco troque de versão
    }
}
