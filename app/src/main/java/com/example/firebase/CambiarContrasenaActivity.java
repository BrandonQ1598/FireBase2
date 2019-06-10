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

public class CambiarContrasenaActivity extends AppCompatActivity {
    private EditText passwordn,passwordn2;
    private Button cambiarpassword;
    private TextView atras;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);
        passwordn=findViewById(R.id.passwordnuevo);
        passwordn2=findViewById(R.id.confpasswordnuevo);
        cambiarpassword=findViewById(R.id.cambiarpassword);
        atras=findViewById(R.id.regresarinicon);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaini= new Intent(CambiarContrasenaActivity.this, InicioActivity.class);
                pantallaini.putExtra(InicioActivity.usuario,mAuth.getCurrentUser().getEmail().toString());
                startActivity(pantallaini);
            }
        });
        cambiarpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String contraseña1,contraseña2;
                contraseña1=passwordn.getText().toString().trim();
                contraseña2=passwordn2.getText().toString().trim();
                if(TextUtils.isEmpty(contraseña1)){
                    Toast.makeText(CambiarContrasenaActivity.this,"Debe ingresar una contraseña",Toast.LENGTH_LONG).show();
                    return;
                }else if(TextUtils.isEmpty(contraseña2)){
                    Toast.makeText(CambiarContrasenaActivity.this,"Debe ingresar una contraseña",Toast.LENGTH_LONG).show();
                    return;
                }

                if (contraseña1.equals(contraseña2)==true){
                    FirebaseUser user=mAuth.getCurrentUser();
                    user.updatePassword(contraseña1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.setMessage("Actualizando Contraseña");
                                progressDialog.show();
                                Toast.makeText(CambiarContrasenaActivity.this,"Contraseña Actualizada",Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                                passwordn.setText("");
                                passwordn2.setText("");
                            }else{
                                Toast.makeText(CambiarContrasenaActivity.this,"Error al actualizar Contraseña",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(CambiarContrasenaActivity.this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                    passwordn.setText("");
                    passwordn2.setText("");
                    return;
                }
            }
        });
    }
}
