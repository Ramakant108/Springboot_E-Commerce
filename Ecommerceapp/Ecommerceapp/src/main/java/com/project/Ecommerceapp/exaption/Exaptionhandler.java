package com.project.Ecommerceapp.exaption;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Exaptionhandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> exaptionhandler(MethodArgumentNotValidException e){
        Map<String,String> response=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fildName=((FieldError)err).getField();
            String messege=err.getDefaultMessage();
            response.put(fildName,messege);
        });
      return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> ResourceNotFoundExeption(ResourceNotFoundException e){
        String message=e.getMessage();
        return  new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(APIIExeption.class)
    public ResponseEntity<String> APIIExeption(APIIExeption e){
        String message=e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataNotPresentExeption.class)
    public String DatanotpresentExeption(DataNotPresentExeption e){
        String Messege=e.getMessage();
        return Messege;
    }

}
