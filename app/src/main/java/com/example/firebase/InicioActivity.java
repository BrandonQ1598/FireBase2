package com.example.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioActivity extends AppCompatActivity {
    public static final String usuario="nombre";
    private TextView user;
    private Button cambiarcorreo,cambiarpass,eliminar,enviarcorreo,cerrarsesion;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        user= findViewById(R.id.nombreuser);
        String nombre2= getIntent().getStringExtra("nombre");
        user.setText(nombre2);
        cambiarcorreo= findViewById(R.id.btncambiocorreo);
        cambiarpass= findViewById(R.id.btncambiopass);
        eliminar= findViewById(R.id.btneliminar);
        enviarcorreo= findViewById(R.id.btncorreo);
        cerrarsesion= findViewById(R.id.btncerrar);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Eliminando Usuario");
                progressDialog.show();
                FirebaseUser user= mAuth.getCurrentUser();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(InicioActivity.this,"Usuario Eliminado",Toast.LENGTH_LONG).show();
                            Intent registrar= new Intent(InicioActivity.this,RegistroActivity.class);
                            startActivity(registrar);
                            progressDialog.cancel();
                        }else{
                            Toast.makeText(InicioActivity.this,"Fallo al eliminar Usuario",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        });

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cerrando Sesión");
                progressDialog.show();
                mAuth.signOut();
                Intent i= new Intent(InicioActivity.this,MainActivity.class);
                startActivity(i);
                progressDialog.cancel();
            }
        });

        enviarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Enviando Correo");
                progressDialog.show();
                FirebaseUser user= mAuth.getCurrentUser();
                final String correo= user.getEmail();
                mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(InicioActivity.this,"Correo enviado a "+correo,Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }else {
                            Toast.makeText(InicioActivity.this,"Fallo al enviar correo",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        });

        cambiarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b= new Intent(InicioActivity.this,CambiarcorreoActivity.class);
                startActivity(b);
            }
        });

        cambiarpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c= new Intent(InicioActivity.this,CambiarContrasenaActivity.class);
                startActivity(c);
            }
        });
    }
}
