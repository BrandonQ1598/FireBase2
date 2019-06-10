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
import com.google.firebase.auth.FirebaseUser;

public class CambiarcorreoActivity extends AppCompatActivity {
    private Button cambiarcorreo;
    private EditText emailnuevo;
    private TextView regresar;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarcorreo);
        regresar=findViewById(R.id.regresarini);
        emailnuevo=findViewById(R.id.emailnuevo);
        cambiarcorreo=findViewById(R.id.cambiarcorreo);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallainicio= new Intent(CambiarcorreoActivity.this,InicioActivity.class);
                pantallainicio.putExtra(InicioActivity.usuario,mAuth.getCurrentUser().getEmail().toString());
                startActivity(pantallainicio);
            }
        });
        cambiarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo= emailnuevo.getText().toString().trim();
                if (TextUtils.isEmpty(correo)){
                    Toast.makeText(CambiarcorreoActivity.this,"Debe ingresar un correo",Toast.LENGTH_LONG).show();
                    return;
                }

                FirebaseUser user= mAuth.getCurrentUser();

                user.updateEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.setMessage("Actualizando Correo");
                            progressDialog.show();
                            Toast.makeText(CambiarcorreoActivity.this,"Correo Actualizado",Toast.LENGTH_LONG).show();
                            Intent pantallainicio1= new Intent(CambiarcorreoActivity.this,InicioActivity.class);
                            pantallainicio1.putExtra(InicioActivity.usuario,mAuth.getCurrentUser().getEmail().toString());
                            startActivity(pantallainicio1);
                            progressDialog.cancel();
                        }else{
                            Toast.makeText(CambiarcorreoActivity.this, "Fallo al actualizar correo", Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        });

    }
}
