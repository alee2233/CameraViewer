package com.example.cameraviewer;

import java.io.Serializable;

public class RemoteCamera implements Serializable {

    private int id;
    private String name;
    private String address;

    public RemoteCamera(int i, String n, String a) {
        id = i;
        name = n;
        address = a;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
