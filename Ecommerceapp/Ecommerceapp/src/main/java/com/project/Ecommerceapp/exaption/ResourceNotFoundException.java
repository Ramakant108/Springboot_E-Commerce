package com.project.Ecommerceapp.exaption;

public class ResourceNotFoundException extends RuntimeException {
    String categoryName;
    String fildName;
    Long ID;
    String name;
     public  ResourceNotFoundException(String categoryName,String fildName,Long Id){
         super(String.format(categoryName+" not presetn with "+fildName+": "+Id));
         this.categoryName=categoryName;
         this.fildName=fildName;
         this.ID=Id;
     }
//    public  ResourceNotFoundException(String categoryName,String fildName,String name,Long Id){
//        super(String.format(categoryName+"not presetn with "+fildName+": "+Id)+" name:"+name+" alredy exist");
//        this.categoryName=categoryName;
//        this.fildName=fildName;
//        this.name=name;
//        this.ID=Id;
//    }
}
