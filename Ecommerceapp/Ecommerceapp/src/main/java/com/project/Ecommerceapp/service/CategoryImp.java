package com.project.Ecommerceapp.service;

import com.project.Ecommerceapp.categoryDTO.CategoryDTO;
import com.project.Ecommerceapp.categoryDTO.CategoryResponse;
import com.project.Ecommerceapp.exaption.APIIExeption;
import com.project.Ecommerceapp.exaption.DataNotPresentExeption;
import com.project.Ecommerceapp.exaption.ResourceNotFoundException;
import com.project.Ecommerceapp.model.Category;
import com.project.Ecommerceapp.repostory.Repostory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.List;
@Service
public class CategoryImp implements categorymethod{

    @Autowired
    private Repostory repostory;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getcategory(Integer PageNumber,Integer PageSize) {
        Pageable pageCategory= PageRequest.of(PageNumber,PageSize);
        Page<Category> categoryPage=repostory.findAll(pageCategory);
        List<Category> data=categoryPage.getContent();
        if(data.isEmpty()){
            throw new DataNotPresentExeption("there is no data");
        }
        List<CategoryDTO> categoryDTOS=data.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse response=new CategoryResponse();
        response.setCategoryDTOList(categoryDTOS);
        response.setPageNumber(categoryPage.getNumber());
        response.setPageSize(categoryPage.getSize());
        response.setTotalpage(categoryPage.getTotalPages());
        response.setTotalElement(categoryPage.getNumberOfElements());
        response.setCurrentpage(categoryPage.isEmpty());
        return response;
    }

    @Override
    public void add(CategoryDTO categoryDTO) {
         Category category=modelMapper.map(categoryDTO,Category.class);
        Category newCategory=repostory.findByCategoryname(category.getCategoryname());
        if(newCategory!=null){
            throw new APIIExeption("the category alredy exist with name "+category.getCategoryname());
        }
        repostory.save(category);
    }

    @Override
    public CategoryDTO delete(Long id) {
        Category category=repostory.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","CategoryID",id));
//        Category category=categores.stream().filter(c->c.getC_Id().equals(id)).findFirst().
//                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        CategoryDTO categoryDTO=modelMapper.map(category,CategoryDTO.class);
        repostory.deleteById(id);
        return categoryDTO;
    }

    @Override
    public String update(CategoryDTO categoryDTO, Long ID) {
        Category savecategory=repostory.findById(ID).orElseThrow(()->new ResourceNotFoundException("Category","CategoryID",ID));
        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setC_Id(ID);
        savecategory=repostory.save(category);
        return "category is updated:";


    }
}
