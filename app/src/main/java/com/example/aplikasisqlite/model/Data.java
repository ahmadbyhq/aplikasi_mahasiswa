package com.example.aplikasisqlite.model;

// Kelas model Data untuk menyimpan informasi id, name, dan address
public class Data {
    // Deklarasi variabel id, name, dan address
    private String id, name, address;

    // Konstruktor tanpa parameter
    public Data() {
    }

    // Konstruktor dengan parameter id, name, dan address
    public Data(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Getter untuk mendapatkan id
    public String getId() {
        return id;
    }

    // Setter untuk mengatur id
    public void setId(String id) {
        this.id = id;
    }

    // Getter untuk mendapatkan name
    public String getName() {
        return name;
    }

    // Setter untuk mengatur name
    public void setName(String name) {
        this.name = name;
    }

    // Getter untuk mendapatkan address
    public String getAddress() {
        return address;
    }

    // Setter untuk mengatur address
    public void setAddress(String address) {
        this.address = address;
    }
}
