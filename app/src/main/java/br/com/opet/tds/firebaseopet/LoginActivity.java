package br.com.opet.tds.firebaseopet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {

    private EditText editMail;
    private EditText editSenha;
    private TextView textStatus;
    //mAuth armazena a instância de autenticação do Firebase.
    private FirebaseAuth mAuth;
    //Listener responsável por verificar operações de autenticação.
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Recuperando a instância de autenticação.
        mAuth = FirebaseAuth.getInstance();

        //Criando o Listener para verificar o usuário atual.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Usuário logou
                    Intent novatela = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(novatela);
                } else {
                    // Usuário saiu.
                }
            }
        };

        editMail = (EditText) findViewById(R.id.editLogin);
        editSenha = (EditText) findViewById(R.id.editSenha);
        textStatus = (TextView) findViewById(R.id.textStatus);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Adiciona o listener quando a activity é iniciada.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Remove o listener quando a activity é parada.
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void novaConta(View v){
        String email = editMail.getText().toString();
        String senha = editSenha.getText().toString();
        textStatus.setText("Criando nova conta. Aguarde.");
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            textStatus.setText("Falha ao criar usuário.");
                        }
                        else{
                            textStatus.setText("Usuário criado com sucesso.");
                        }

                    }
                });
    }

    public void logar(View v){
        String email = editMail.getText().toString();
        String senha = editSenha.getText().toString();
        textStatus.setText("Logando. Aguarde.");
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            textStatus.setText("Falha ao logar no aplicativo.");
                        }
                        else{
                            textStatus.setText("Usuário logado com sucesso!");
                        }

                        // ...
                    }
                });
    }
}
