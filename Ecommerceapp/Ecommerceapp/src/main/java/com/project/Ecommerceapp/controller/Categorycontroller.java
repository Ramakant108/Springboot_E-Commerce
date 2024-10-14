package com.project.Ecommerceapp.controller;

import com.project.Ecommerceapp.categoryDTO.CategoryDTO;
import com.project.Ecommerceapp.categoryDTO.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Ecommerceapp.service.categorymethod;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class Categorycontroller {
    @Autowired
    private categorymethod categorymethod;



    @GetMapping("/api/public/getCategory")
    public CategoryResponse getCategories(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return categorymethod.getcategory(pageNumber,pageSize);
    }

    @PostMapping("/api/public/getCategory")
    public String createcategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categorymethod.add(categoryDTO);
       return "added";
    }

    @DeleteMapping("/api/public/getCategory/{Id}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long Id){

            CategoryDTO status=categorymethod.delete(Id);
            return  new ResponseEntity<>(status, HttpStatus.OK);

    }

    @PutMapping("/api/public/getCategory/{Id}")
    public ResponseEntity<String> update(@RequestBody CategoryDTO category,@PathVariable Long Id){
        try{
            String status=categorymethod.update(category,Id);
            return new ResponseEntity<>(status,HttpStatus.OK);
        }
        catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}
