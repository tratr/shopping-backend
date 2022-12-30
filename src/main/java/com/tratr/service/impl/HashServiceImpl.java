package com.tratr.service.impl;

import com.tratr.domain.Category;
import com.tratr.service.CategoryService;
import com.tratr.service.HashService;
import com.tratr.service.dto.HashDTO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HashServiceImpl implements HashService {

    private final CategoryService categoryService;

    public HashServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public HashDTO hashcode(Long Id) {
        Optional<Category> category = this.categoryService.findOne(Id);
        Category cat = category.get();
        String name = DigestUtils.md5Hex(cat.getName());
        HashDTO hash = new HashDTO(name);
        return hash;
    }
}
