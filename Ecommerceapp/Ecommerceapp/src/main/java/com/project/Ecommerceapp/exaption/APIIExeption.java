package com.project.Ecommerceapp.exaption;

public class APIIExeption extends RuntimeException{
    private static final Long serialVersionUID=1l;
    public APIIExeption(){}

    public APIIExeption(String messege){
        super(messege);
    }
}
