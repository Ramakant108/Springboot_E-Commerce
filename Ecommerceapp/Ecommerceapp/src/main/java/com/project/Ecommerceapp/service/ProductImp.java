package com.project.Ecommerceapp.service;

import com.project.Ecommerceapp.categoryDTO.ProductDTO;
import com.project.Ecommerceapp.categoryDTO.ProductResponse;
import com.project.Ecommerceapp.exaption.DataNotPresentExeption;
import com.project.Ecommerceapp.fileservice.Fileserviceimp;
import com.project.Ecommerceapp.model.Category;
import com.project.Ecommerceapp.model.Product;
import com.project.Ecommerceapp.repostory.ProductRepostory;
import com.project.Ecommerceapp.repostory.Repostory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductImp implements Productmethod{
    @Autowired
    private ProductRepostory productRepostory;

    @Autowired
    private Repostory categoryrRepostory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Fileserviceimp fileserviceimp;

    @Value("${project.path}")
    private String path;

    @Override
    public ProductDTO AddProduct(Product product, Long categoryId) {
        Category category=categoryrRepostory.findById(categoryId)
                .orElseThrow(()-> new DataNotPresentExeption("category not found for this product"));
        product.setCategory(category);
        product.setImage("default.jpg");
        product.setSpeciaprice(product.getPrice()-(product.getDiscount()*0.01)* product.getPrice());

        ProductDTO productDTO=modelMapper.map(productRepostory.save(product),ProductDTO.class);
        return productDTO;
    }


    @Override
    public ProductResponse getProduct(Integer pagenumber, Integer size, String Sortby, String sortOrder) {
        Sort productSort=sortOrder.equalsIgnoreCase("asc")?Sort.by(Sortby).ascending():Sort.by(Sortby).descending();
        Pageable productpage= PageRequest.of(pagenumber,size,productSort);
        Page<Product> products=productRepostory.findAll(productpage);
        List<Product> data=products.getContent();
        if(data.isEmpty())
        {
            throw new DataNotPresentExeption("data no present in product");
        }
        List<ProductDTO> productDTOList=data.stream().map(product->modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse response=new ProductResponse();
        response.setProductResponse(productDTOList);
        response.setPagesize(products.getSize());
        response.setPagenumber(products.getNumber());
        response.setTotalpages(products.getTotalPages());
        response.setTotalelments(products.getTotalElements());
        response.setIslastpage(products.isEmpty());


        return response;
    }

    @Override
    public ProductDTO FindbyId(Long productId) {
        Product product=productRepostory.findById(productId).orElseThrow(()->new DataNotPresentExeption("The product not present with product Id"));
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        return productDTO;
    }

    @Override
    public ProductResponse getByCategory(Long categoryId,Integer pagenumber, Integer size, String Sortby, String sortOrder) {
        Category category=categoryrRepostory.findById(categoryId).orElseThrow(()->new DataNotPresentExeption("Product not present with this category"));
        Sort productSort=sortOrder.equalsIgnoreCase("asc")?Sort.by(Sortby).ascending():Sort.by(Sortby).descending();
        Pageable productpage= PageRequest.of(pagenumber,size,productSort);
        Page<Product> products=productRepostory.findByCategory(category,productpage);

        List<Product> product=products.getContent();
        if(product.isEmpty()){
            throw new DataNotPresentExeption("product not present in this category");
        }
        List<ProductDTO>  productDTOList=product.stream().map(p->modelMapper.map(p,ProductDTO.class)).toList();
        ProductResponse response=new ProductResponse();
        response.setProductResponse(productDTOList);
        response.setPagesize(products.getSize());
        response.setPagenumber(products.getNumber());
        response.setTotalpages(products.getTotalPages());
        response.setTotalelments(products.getTotalElements());
        response.setIslastpage(products.isEmpty());
        return response;
    }

    @Override
    public ProductResponse getByKeyword(String keyword, Integer pagenumber, Integer size, String Sortby, String sortOrder) {
        Sort productSort=sortOrder.equalsIgnoreCase("asc")?Sort.by(Sortby).ascending():Sort.by(Sortby).descending();
        Pageable productpage= PageRequest.of(pagenumber,size,productSort);
        Page<Product> products=productRepostory.findByProductNameLikeIgnoreCase("%"+keyword+"%",productpage);
        List<Product> product=products.getContent();
        if(product.isEmpty()){
            throw new DataNotPresentExeption("product not present with this keyword");
        }
        List<ProductDTO>  productDTOList=product.stream().map(p->modelMapper.map(p,ProductDTO.class)).toList();
        ProductResponse response=new ProductResponse();
        response.setProductResponse(productDTOList);
        response.setPagesize(products.getSize());
        response.setPagenumber(products.getNumber());
        response.setTotalpages(products.getTotalPages());
        response.setTotalelments(products.getTotalElements());
        response.setIslastpage(products.isEmpty());
        return response;
    }

    @Override
    public ProductDTO updateProduct(Product product,Long p_id) {
        Product saveproduct=productRepostory.findById(p_id).orElseThrow(()->new DataNotPresentExeption("no Product such present in reposetory"));
        product.setCategory(saveproduct.getCategory());
        product.setProductId(p_id);
        product.setSpeciaprice(product.getPrice()-(product.getDiscount()*0.01)* product.getPrice());

        return modelMapper.map(productRepostory.save(product),ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long Id) {
        Product product=productRepostory.findById(Id).orElseThrow(()->new DataNotPresentExeption("product not present for delete"));
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        productRepostory.delete(product);
        return productDTO;
    }

    @Override
    public ProductDTO updateimage(Long id, MultipartFile image) throws IOException {
        Product product=productRepostory.findById(id).orElseThrow(()->new DataNotPresentExeption("Product not present"));
//        String path="image/";
        String filename=fileserviceimp.uploadfile(path,image);
        product.setImage(filename);
        Product saveproduct=productRepostory.save(product);

        return modelMapper.map(saveproduct,ProductDTO.class);
    }


}
