package com.example.gotogether.category.service.impl;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.category.entity.Category;
import com.example.gotogether.category.repository.CategoryRepository;
import com.example.gotogether.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> makeCate(CategoryDTO.makeCate dto){
        try {
            if (categoryRepository.existsByName(dto.getCategoryName())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (dto.getCategoryDepth() != 1) {
                Category parent = categoryRepository.findById(dto.getCategoryParent()).orElseThrow(IllegalArgumentException::new);
                if (parent.getCategoryDepth()!=dto.getCategoryDepth()-1){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                categoryRepository.save(dto.toChild(parent));
            } else {
                categoryRepository.save(dto.toParent());
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> selectCate(){
        List<CategoryDTO.ViewCate> list = categoryRepository.findAllByParentIsNull().stream().map(CategoryDTO.ViewCate::of).collect(Collectors.toList());
        if (list.size()<1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
