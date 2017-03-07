package com.abdymalikmulky.androidcsvexample;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 3/6/17.
 */

public class User {
    String name;
    String fullName;
    String address;
    int age;


    public User(String name, String fullName, String address, int age) {
        this.name = name;
        this.fullName = fullName;
        this.address = address;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
