package com.project.Ecommerceapp.controller;

import com.project.Ecommerceapp.categoryDTO.ProductDTO;
import com.project.Ecommerceapp.categoryDTO.ProductResponse;
import com.project.Ecommerceapp.model.Product;
import com.project.Ecommerceapp.service.Productmethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Productcontroller {
    @Autowired
    private Productmethod productservice;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/api/admin/addProduct/{Category_Id}")
    public ResponseEntity<ProductDTO> addproduct(@RequestBody ProductDTO productDTO, @PathVariable Long Category_Id){
        Product product=modelMapper.map(productDTO,Product.class);
        return new ResponseEntity<>(productservice.AddProduct(product,Category_Id), HttpStatus.CREATED);
    }

    @GetMapping("/api/public/getProduct")
    public ResponseEntity<ProductResponse> GetProduct(
            @RequestParam(name = "Pagenumber",defaultValue = "0")Integer pagenumber
            ,@RequestParam(name = "PageSize",defaultValue = "10")Integer Size
            ,@RequestParam(name = "SortBy",defaultValue = "productId")String sortBy
            ,@RequestParam(name = "SortOrder",defaultValue = "asc")String sortOrder){

        return new ResponseEntity<>(productservice.getProduct(pagenumber,Size,sortBy,sortOrder),HttpStatus.OK);
    }

    @GetMapping("/api/public/getbyidProduct/{ID}")
    public ResponseEntity<ProductDTO> GetById(@PathVariable Long ID){
        return new ResponseEntity<>(productservice.FindbyId(ID),HttpStatus.OK);
    }

    @GetMapping("/api/public/getProduct/category/{ID}")
    public ResponseEntity<ProductResponse> getBycategory(@PathVariable Long ID
            ,@RequestParam(name = "Pagenumber",defaultValue = "0")Integer pagenumber
            ,@RequestParam(name = "PageSize",defaultValue = "10")Integer Size
            ,@RequestParam(name = "SortBy",defaultValue = "productId")String sortBy
            ,@RequestParam(name = "SortOrder",defaultValue = "asc")String sortOrder){
        return new ResponseEntity<>(productservice.getByCategory(ID,pagenumber,Size,sortBy,sortOrder),HttpStatus.FOUND);
    }

    @GetMapping("/api/public/getProduct/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getBykeyword(@PathVariable String keyword
            ,@RequestParam(name = "Pagenumber",defaultValue = "0")Integer pagenumber
            ,@RequestParam(name = "PageSize",defaultValue = "10")Integer Size
            ,@RequestParam(name = "SortBy",defaultValue = "productId")String sortBy
            ,@RequestParam(name = "SortOrder",defaultValue = "asc")String sortOrder){
        return new ResponseEntity<>(productservice.getByKeyword(keyword,pagenumber,Size,sortBy,sortOrder),HttpStatus.OK);
    }

    @PutMapping("/api/admin/updateProduct/{ID}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long ID){
        Product product=modelMapper.map(productDTO,Product.class);
        return new ResponseEntity<>(productservice.updateProduct(product,ID),HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/deleteProduct/{ID}")
    public ResponseEntity<ProductDTO> deleteproduct(@PathVariable Long ID){
        return new ResponseEntity<>(productservice.deleteProduct(ID),HttpStatus.OK);
    }

    @PutMapping("/api/admin/{ID}/image")
    public ResponseEntity<ProductDTO> updateimage(@PathVariable Long ID,
                                                  @RequestParam(name = "Image") MultipartFile Image) throws IOException {

        return new ResponseEntity<>(productservice.updateimage(ID,Image),HttpStatus.OK);
    }


}
