package com.dds6.sqliteconection;

public class CustomerEntity {

    private int id;
    private String user;
    private String pass;

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }


    public CustomerEntity() {}

    public CustomerEntity(int id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public String setPass(String pass) {
        this.pass = pass;
        return pass;
    }

}
