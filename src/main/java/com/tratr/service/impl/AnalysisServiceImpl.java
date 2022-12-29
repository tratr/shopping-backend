package com.tratr.service.impl;

import com.tratr.domain.Category;
import com.tratr.domain.Item;
import com.tratr.repository.CategoryRepository;
import com.tratr.service.AnalysisService;
import com.tratr.service.CategoryService;
import com.tratr.service.dto.AnalysisDTO;
import java.util.NavigableMap;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    private final CategoryService categoryService;

    public AnalysisServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public AnalysisDTO calculate(Long categoryId) {
        Optional<Category> category = this.categoryService.findOne(categoryId);
        Category cat = category.get();
        Double total = 0.0;
        for (Item item : cat.getItems()) {
            total += item.getPrice();
        }
        AnalysisDTO ana = new AnalysisDTO(total, 0.0, 0.0);

        return ana;
    }
}
