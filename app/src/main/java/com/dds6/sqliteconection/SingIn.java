package com.dds6.sqliteconection;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class SingIn extends AppCompatActivity {

    EditText txt_mail_record,txt_pass_record;
    Button btn_save;
    TextView lbl_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        txt_mail_record = this.findViewById(R.id.txt_findCustomer);
        txt_pass_record = this.findViewById(R.id.txt_pass_record);
        btn_save = this.findViewById(R.id.btn_save);
        lbl_login = this.findViewById(R.id.lbl_login);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerEntity customerEntity;


                //hashear Contraseña
                String user = txt_mail_record.getText().toString();
                String password = txt_pass_record.getText().toString();
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                try {

                    //boolean search = sqliteConection.searchOne(customerEntity);

                    customerEntity = new CustomerEntity(-1,user,bcryptHashString);

                    SqliteConection sqliteConection = new SqliteConection(SingIn.this);
                    boolean searchOne =sqliteConection.ifExist(customerEntity);

                    if (searchOne == true){
                        Toast.makeText(SingIn.this, "The user already exist", Toast.LENGTH_SHORT).show();
                    }else {
                        boolean  success =  sqliteConection.addOne(customerEntity);
                        System.out.println(customerEntity);
                        Toast.makeText(SingIn.this, "Success Creating the Costumer"+success, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    System.out.println(e);
                    Toast.makeText(SingIn.this, "error Creating the Costumer", Toast.LENGTH_SHORT).show();
                    customerEntity = new CustomerEntity(-1, "Error", "error");

                }


            }
        });

        lbl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}