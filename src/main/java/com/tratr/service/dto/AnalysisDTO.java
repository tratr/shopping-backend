package com.tratr.service.dto;

import static java.sql.Types.DOUBLE;

import com.tratr.domain.Category;
import com.tratr.domain.Item;
import java.io.Serializable;
import org.aspectj.weaver.tools.ISupportsMessageContext;

public class AnalysisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double sumPrice;
    private Double maxPrice;
    private Double minPrice;

    public AnalysisDTO() {
        // Empty constructor needed for Jackson.
    }

    public AnalysisDTO(Double sumPrice, Double maxPrice, Double minPrice) {
        this.sumPrice = sumPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public Double getSumPrice() {
        return this.sumPrice;
    }

    public void setSumPrice(Category category) {
        Double total = 0.0;
        for (Item item : category.getItems()) {
            total += item.getPrice();
        }
        this.sumPrice = total;
    }

    public Double getMaxPrice() {
        return this.maxPrice;
    }

    public void setMinPrice(Category category) {
        Double min = Double.MAX_VALUE;
        for (Item item : category.getItems()) {
            if (item.getPrice() < min) {
                min = item.getPrice();
            }
        }
        this.minPrice = min;
    }

    public Double getMinPrice() {
        return this.minPrice;
    }

    public void setMaxPrice(Category category) {
        Double max = 0.0;
        for (Item item : category.getItems()) {
            if (item.getPrice() > max) {
                max = item.getPrice();
            }
        }
        this.minPrice = max;
    }

    @Override
    public String toString() {
        return "AnalysisDTO{" + "sumPrice=" + sumPrice + "maxPrice" + maxPrice + "minPrice" + minPrice + "}";
    }
}
