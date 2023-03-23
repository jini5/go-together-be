package com.example.gotogether.category.dto;

import com.example.gotogether.category.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class makeCate{
        @ApiModelProperty(value = "생성할 카테고리 이름", required = true)
        private String categoryName;
        @ApiModelProperty(value = "생성할 카테고리 단계 ( 1: 대분류, 2: 중분류, 3: 소분류", required = true)
        private int categoryDepth;
        @ApiModelProperty(value = "생성할 카테고리 부모( 생성할 카테고리가 중분류, 소분류인 경우 필요, 대분류의 경우 아무값 넣어도 상관 없음.", required = true)
        private Long categoryParent;


        public Category toChild(Category categoryParent){
            return new Category(categoryName,categoryParent,categoryDepth);
        }

        public Category toParent(){
            return new Category(categoryName,categoryDepth);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewCate{
        private Long categoryId;
        private String categoryName;
        private int categoryDepth;
        private List<ViewCate> children;

        public static ViewCate of(Category category){
            return new ViewCate(
                    category.getCategoryId(),
                    category.getName(),
                    category.getCategoryDepth(),
                    category.getChildren().stream().map(ViewCate::of).collect(Collectors.toList())
            );
        }
    }
}
