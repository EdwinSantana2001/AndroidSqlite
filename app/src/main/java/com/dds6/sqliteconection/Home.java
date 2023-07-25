package com.dds6.sqliteconection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ListView listview;
    EditText txt_findCustomer;
    TextView lbl_user;
    Button btn_logOut, btn_viewAll;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //SharedPreferences
        sharedPreferences = getSharedPreferences("customer", Context.MODE_PRIVATE);
        //Buttons
        btn_logOut = this.findViewById(R.id.btn_logOut);
        btn_viewAll = this.findViewById(R.id.btn_viewAll);
        //EditText
        txt_findCustomer = this.findViewById(R.id.txt_findCustomer);
        //ListView
        listview = this.findViewById(R.id.lv_listview);
        //TextView
        lbl_user = this.findViewById(R.id.lbl_user);
        //Dar SharePreference
        lbl_user.setText(sharedPreferences.getString("user",""));

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqliteConection sqliteConection = new SqliteConection(Home.this);
                List<CustomerEntity> list = sqliteConection.selecAlltCostumer();
                ArrayAdapter adapter = new ArrayAdapter<CustomerEntity>(Home.this, android.R.layout.simple_list_item_1,list);
                listview.setAdapter(adapter);
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Home.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}