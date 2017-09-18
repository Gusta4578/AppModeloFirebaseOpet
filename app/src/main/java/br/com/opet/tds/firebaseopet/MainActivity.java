package br.com.opet.tds.firebaseopet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {

    EditText editPalavra;
    TextView textResultado;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String uid;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("palavra");

        editPalavra = (EditText) findViewById(R.id.editPalavra);
        textResultado = (TextView) findViewById(R.id.textResultado);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }

        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dado = dataSnapshot.getValue(String.class);
                textResultado.setText(dado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ((TextView)findViewById(R.id.textNomeUsuario)).setText("Bem-vindo! " + email);
    }

    public void salvarNoBanco(View v){
        myRef.child(uid).setValue(editPalavra.getText().toString());
    }

    public void logout(View v){
       FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent loginActivity = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }
}
