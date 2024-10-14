package com.project.Ecommerceapp.exaption;

public class DataNotPresentExeption extends RuntimeException {
    public DataNotPresentExeption(String Message){
        super(Message);
    }
}
