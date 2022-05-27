package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.TagDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TagEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());

        tagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<TagEntity> list() {
        return tagRepository.findAll();
    }

    public TagDTO getById(Integer id) {
        TagEntity entity = tagRepository.getById(id);
        TagDTO dto = new TagDTO();

        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public TagDTO update(Integer id, TagDTO dto) {
        TagEntity entity = tagRepository.getById(id);
        entity.setName(dto.getName());

        tagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public boolean delete(Integer id) {
        TagEntity entity = tagRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (entity == null) {
            return false;
        }

        tagRepository.delete(entity);
        return true;
    }

    public TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    public TagEntity get(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public List<TagDTO> getTagList(List<Integer> tagList) {
        List<TagEntity> entityList = tagRepository.findAllById(tagList);
        List<TagDTO> dtoList = new LinkedList<>();
        for (TagEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
}
