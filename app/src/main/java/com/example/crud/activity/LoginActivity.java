package com.example.crud.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crud.R;
import com.example.crud.database.UsuarioDAO;
import com.example.crud.model.Usuario;
import com.example.crud.utils.PrefsConstants;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edit_email_usuario);
        senha = findViewById(R.id.edit_login_senha);

        dao = new UsuarioDAO(this);

        //Verificar se o usuário está logado
        verificarUsuarioLogado();
    }

    private void verificarUsuarioLogado() {

        SharedPreferences settings = getSharedPreferences(PrefsConstants.PREFS_NAME, 0);
        if (!settings.getString(PrefsConstants.NOME_USER, "").isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void validaCampos() {
        if (email.getText().length() == 0)
            email.setError(getString(R.string.campo_vazio));

        if (senha.getText().length() == 0)
            senha.setError(getString(R.string.campo_vazio));
    }

    public void logar(View view){
        validaCampos();
        Usuario usuarioLogin = new Usuario(email.getText().toString(),senha.getText().toString());
        Usuario usuarioLogado = dao.procurar(usuarioLogin);

        if (usuarioLogado != null){
            setaUsuarioLogado(usuarioLogado);
            abrirHome();
        }else{
            Toast.makeText(this, getString(R.string.email_senha_invalido), Toast.LENGTH_LONG).show();
        }

    }

    /*os dados aqui são persistidos pelo sharedPreferences pois acredito que a performance é superior comparado com o sql
     * com isso na tela home para validar o usuario e recuperar o nome não é necessario uma consulta sql*/
    private void setaUsuarioLogado(Usuario usuario) {

        SharedPreferences settings = getSharedPreferences(PrefsConstants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PrefsConstants.NOME_USER, usuario.getNome());
        editor.putInt(PrefsConstants.ID_USER, usuario.getId());
        editor.commit();
    }

    public void abrirHome(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
