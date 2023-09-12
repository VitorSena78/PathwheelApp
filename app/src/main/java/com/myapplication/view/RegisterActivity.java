package com.myapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.myapplication.R;
import com.myapplication.api.crypto.MD5;
import com.myapplication.api.endpoint.RegisterUserListener;
import com.myapplication.api.endpoint.UserRegisterEndpoint;
import com.myapplication.api.io.Logger;
import com.myapplication.api.model.User;
import com.myapplication.api.request.RegisterUserRequest;
import com.myapplication.api.response.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextUserName;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private Button buttonRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_pathwheel_ac);
            getSupportActionBar().setTitle("  Pathwheel");

        } catch (Exception e) {
            Log.d("showHomeEnabled", e.getMessage());
        }


        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserName =findViewById(R.id.editTextUserName);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Encerra a atividade atual (registro) e volta para a atividade anterior (login)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void registrar() {

        String fullNome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();
        String userName = editTextUserName.getText().toString();
        String password = editTextSenha.getText().toString();
        String passwordConfirm = editTextConfirmarSenha.getText().toString();

        if (TextUtils.isEmpty(fullNome)) {
            editTextNome.setError("Por favor, insira um fullNome");
            editTextNome.requestFocus();
            return;
        }else if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Por favor, insira um email");
            editTextEmail.requestFocus();
            return;
        }else if (!isValidEmail(email)) {
            editTextEmail.setError("Por favor, insira um email válido");
            editTextEmail.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)) {
            editTextSenha.setError("Por favor, insira uma senha");
            editTextSenha.requestFocus();
            return;
        }else if (password.length() < 6) {
            editTextSenha.setError("A senha deve ter pelo menos 6 caracteres");
            editTextSenha.requestFocus();
            return;
        }else if (!password.equals(passwordConfirm)) {
            editTextConfirmarSenha.setError("As senhas não coincidem");
            editTextConfirmarSenha.requestFocus();
            return;
        }

        // Processo de registro
        final RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        final User user = new User();
        user.setFullName(fullNome);
        user.setEmail(email.trim());
        user.setUserName(userName.trim());
        registerUserRequest.setUser(user);
        registerUserRequest.setSecret(MD5.encode(password.trim()));

        /*
        Log.i("loginActivit: ",fullNome);
        Log.i("loginActivit: ",email.trim());
        Log.i("loginActivit: ",userName.trim());
        Log.i("loginActivit: ",MD5.encode(password.trim()));
        */

        final UserRegisterEndpoint userRegisterEndpoint = new UserRegisterEndpoint();
        userRegisterEndpoint.register(registerUserRequest, new RegisterUserListener() {
            @Override
            public void onRegisterUserListener(Response response) {
                Logger.debug(getApplicationContext(), "response register User: " + response.getDescription());
                Log.i("registrar usuario", "response: "+ response);
                if (response.getCode() == 200) {
                    Toast.makeText(RegisterActivity.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
                } else {
                    Logger.debug(getApplicationContext(), "retrying in 30s...");
                    // Exibir janela de alerta com o conteúdo da resposta
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setTitle("Erro");
                    if (response.getDescription().contains("violates unique constraint")){
                        //alertDialog.setMessage(response.getDescription());
                        alertDialog.setMessage("nome de usuário ou Email já existe");
                    } else {
                        //alertDialog.setMessage(response.getDescription());
                        alertDialog.setMessage("não foi possível realizar o cadastro");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });


    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



}
