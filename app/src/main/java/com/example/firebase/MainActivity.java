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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String usuario="nombre";
    public static final String contra="contra";
    private Button iniciarsesion;
    private TextView contrasenaolvidada,registro;
    private EditText user,password;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarsesion=findViewById(R.id.iniciarsesion);
        contrasenaolvidada=findViewById(R.id.olvidastecontraseña);
        registro=findViewById(R.id.registrar);
        user=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        String nombre2= getIntent().getStringExtra("nombre");
        String contra2= getIntent().getStringExtra("contra");
        user.setText(nombre2);
        password.setText(contra2);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent1);
            }
        });
        contrasenaolvidada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contra= new Intent(MainActivity.this,ContrasenaActivity.class);
                startActivity(contra);
            }
        });
        iniciarsesion.setOnClickListener(this);
    }

    private void loguearusuario(){
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

        progressDialog.setMessage("Iniciando Sesión");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Bienvenido "+email,Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                            Intent inicio= new Intent(MainActivity.this,InicioActivity.class);
                            inicio.putExtra(InicioActivity.usuario,email);
                            startActivity(inicio);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(MainActivity.this,"El usuario no existe",Toast.LENGTH_LONG).show();
                                user.setText("");
                                password.setText("");
                                progressDialog.cancel();
                            }else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(MainActivity.this, "Contraseña Invalida", Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                                password.setText("");
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        loguearusuario();
    }
}
