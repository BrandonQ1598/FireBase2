package com.example.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;

public class ContrasenaActivity extends AppCompatActivity {
    private TextView regresar;
    private EditText correo;
    private Button enviar;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena);
        regresar= findViewById(R.id.regresarcon);
        correo= findViewById(R.id.emailpassolv);
        enviar= findViewById(R.id.enviarcorrconolv);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login= new Intent(ContrasenaActivity.this,MainActivity.class);
                startActivity(login);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Enviando Correo");
                progressDialog.show();
                final String email=correo.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ContrasenaActivity.this,"Debe ingresar un correo",Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ContrasenaActivity.this,"Correo Enviado a "+email,Toast.LENGTH_LONG).show();
                            Intent a= new Intent(ContrasenaActivity.this,MainActivity.class);
                            startActivity(a);
                            progressDialog.cancel();
                        }
                    }
                });
            }
        });
    }
}
