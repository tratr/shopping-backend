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

    public Double getMaxPrice() {
        return this.maxPrice;
    }

    public Double getMinPrice() {
        return this.minPrice;
    }

    @Override
    public String toString() {
        return "AnalysisDTO{" + "sumPrice=" + sumPrice + "maxPrice" + maxPrice + "minPrice" + minPrice + "}";
    }
}
