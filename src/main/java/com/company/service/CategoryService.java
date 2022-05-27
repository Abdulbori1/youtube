package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.ChanelEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());

        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<CategoryEntity> list() {
        return categoryRepository.findAll();
    }

    public CategoryDTO getById(Integer id) {
        CategoryEntity entity = categoryRepository.getById(id);
        CategoryDTO dto = new CategoryDTO();

        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = categoryRepository.getById(id);
        entity.setName(dto.getName());

        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public boolean delete(Integer id) {
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (entity == null) {
            return false;
        }

        categoryRepository.delete(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Chanel not found");
        });
    }

    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }
}

