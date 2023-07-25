package com.dds6.sqliteconection;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {
    EditText txt_mail, txt_pass;
    TextView lbl_record;
    Switch swt_remember;
    Button btn_next;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_mail = this.findViewById(R.id.txt_mail);
        txt_pass = this.findViewById(R.id.txt_pass);
        swt_remember = this.findViewById(R.id.swt_remember);
        btn_next = this.findViewById(R.id.btn_next);
        lbl_record = this.findViewById(R.id.lbl_record);
        sharedPreferences = getSharedPreferences("customer",Context.MODE_PRIVATE);

        String usuario = sharedPreferences.getString("user","");
        if(usuario.length()>0){
            Toast.makeText(MainActivity.this, "Bienvenidos al himalaya", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

        SqliteConection sqliteConection = new SqliteConection(MainActivity.this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerEntity customerEntity;

                // Hacer el hash de la contraseña
                String user = txt_mail.getText().toString();
                String password = txt_pass.getText().toString();
                //Setteando valores
                customerEntity = new CustomerEntity(-1, user, password);

                //Revisa si está vacio
                if (txt_mail.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Error: Debe anotar su usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    //Verifica las credenciales de acceso
                    if (sqliteConection.verify(customerEntity.getUser(), customerEntity.getPass()) == 1) {
                        if (swt_remember.isChecked()){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user",user);
                            editor.commit();

                        }

                        Toast.makeText(MainActivity.this, "Bienvenidos al himalaya", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), Home.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Usuario o contraseña inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Abrir SingIn
        lbl_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SingIn.class);
                startActivity(intent);
            }
        });
    }
}
