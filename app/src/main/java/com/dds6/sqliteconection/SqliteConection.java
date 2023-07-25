package com.dds6.sqliteconection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.ArrayList;

public class SqliteConection extends SQLiteOpenHelper {


    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUM_CUSTOMER_USER = "COLUM_CUSTOMER_USER";
    public static final String COLUM_CUSTOMER_PASSWORD = "COLUM_CUSTOMER_PASSWORD";
    public static final String COLUM_ID = "COLUM_ID";

    public SqliteConection(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "Create table " + CUSTOMER_TABLE + "(" + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUM_CUSTOMER_USER + " TEXT, " + COLUM_CUSTOMER_PASSWORD + " TEXT )";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addOne(CustomerEntity customerEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUM_CUSTOMER_USER, customerEntity.getUser());
        cv.put(COLUM_CUSTOMER_PASSWORD, customerEntity.getPass());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifExist(CustomerEntity customerEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + COLUM_CUSTOMER_USER + " =? ", new String[]{customerEntity.getUser()});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //SELECT * FROM CUSTOMER_TABLE
    public ArrayList<CustomerEntity> selecAlltCostumer() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomerEntity> lista = new ArrayList<>();
        lista.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setId(cursor.getInt(0));
                customerEntity.setUser(cursor.getString(1));
                //customerEntity.setPass(cursor.getString(2));

                lista.add(customerEntity);

            } while (cursor.moveToNext());
        }
        return lista;
    }

    public int verify(String user, String pass) {
        int a = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), cursor.getString(2));
                if (cursor.getString(1).equals(user)&&result.verified) {
                        System.out.println("Usuario verificado");
                        a++;
                }
            } while (cursor.moveToNext());
        }
        System.out.println("El valor de a es "+a);
        return a;
    }

    public CustomerEntity finbOneByUsername(String user){
        ArrayList<CustomerEntity> lista = selecAlltCostumer();
        for (CustomerEntity customerEntity : lista){
            if (customerEntity.getUser().equals(user)){
                return customerEntity;
            }
        }
        return null;
    }


}