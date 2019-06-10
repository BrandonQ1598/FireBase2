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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText user,password;
    private Button registrar;
    private TextView iniciarsesion;
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        user=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressDialog= new ProgressDialog(this);
        registrar=findViewById(R.id.registrar);
        registrar.setOnClickListener(this);
        iniciarsesion= findViewById(R.id.textregistrar);
        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistroActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        
    }

    private void registrarusuario(){
        final String email,contraseña;
        email=user.getText().toString().trim();
        contraseña=password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Debe de ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contraseña)){
            Toast.makeText(this,"Debe de ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registrando Usuario");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this,"Usuario "+email+" Registrado",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                            Intent o= new Intent(RegistroActivity.this,MainActivity.class);
                            o.putExtra(MainActivity.usuario,email);
                            o.putExtra(MainActivity.contra,contraseña);
                            startActivity(o);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegistroActivity.this,"El usuario ya existe",Toast.LENGTH_LONG).show();
                                user.setText("");
                                password.setText("");
                                progressDialog.cancel();
                            }else {
                                Toast.makeText(RegistroActivity.this, "Fallo al Registrar Usuario", Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        registrarusuario();
    }
}
