package com.example.crud.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crud.R;
import com.example.crud.database.UsuarioDAO;
import com.example.crud.model.Usuario;
import com.example.crud.utils.PrefsConstants;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button cadastrar;
    private UsuarioDAO dao;
    private Usuario usuario = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.text_nome);
        email = findViewById(R.id.text_email);
        senha = findViewById(R.id.text_senha);
        cadastrar = findViewById(R.id.button_cadastrar);

        dao = new UsuarioDAO(this);

        Intent intent = getIntent();
        if (intent.hasExtra("usuario")) {
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            preencheCampos();
        }

    }

    public void cadastrar(View view) {
        if (validaCampos()) {
            if (usuario == null) {
                Usuario usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setNome(nome.getText().toString());
                usuario.setSenha(senha.getText().toString());
                dao.inserir(usuario);
                Toast.makeText(this, getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
            } else {
                usuario.setEmail(email.getText().toString());
                usuario.setNome(nome.getText().toString());
                usuario.setSenha(senha.getText().toString());
                dao.atualizar(usuario);
                atualizaNomeUsuarioLogado(usuario);
                Toast.makeText(this, getString(R.string.alteracao_sucesso), Toast.LENGTH_LONG).show();
            }


        }
    }

    public void preencheCampos() {
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
        senha.setText(usuario.getSenha());

        email.setEnabled(false);
        cadastrar.setText(getString(R.string.atualizar));
    }

    public boolean validaCampos() {
        if (email.getText().length() == 0) {
            email.setError(getString(R.string.campo_vazio));
            return false;
        } else if (nome.getText().length() == 0) {
            nome.setError(getString(R.string.campo_vazio));
            return false;
        } else if (senha.getText().length() == 0) {
            senha.setError(getString(R.string.campo_vazio));
            return false;
        } else if (!email.getText().toString().contains("@")){
            email.setError(getString(R.string.formato_email_invalido));
            return false;
        } else
            return true;
    }

    private void atualizaNomeUsuarioLogado(Usuario usuario) {
        SharedPreferences settings = getSharedPreferences(PrefsConstants.PREFS_NAME, 0);
        int idUser = 0;
        if (settings.getInt(PrefsConstants.ID_USER, 0) != 0) {
            idUser = settings.getInt(PrefsConstants.ID_USER, 0);
        }
        if (idUser == usuario.getId()) {

            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PrefsConstants.NOME_USER, usuario.getNome());
            editor.commit();
        }
    }
}
