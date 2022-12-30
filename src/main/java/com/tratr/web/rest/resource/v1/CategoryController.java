package com.tratr.web.rest.resource.v1;

import com.tratr.domain.Category;
import com.tratr.repository.CategoryRepository;
import com.tratr.service.AnalysisService;
import com.tratr.service.CategoryService;
import com.tratr.service.HashService;
import com.tratr.service.dto.AnalysisDTO;
import com.tratr.service.dto.HashDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String ENTITY_NAME = "category";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;
    private final AnalysisService analysisService;

    private final CategoryRepository categoryRepository;
    private final HashService hashService;

    public CategoryController(
        CategoryService categoryService,
        CategoryRepository categoryRepository,
        AnalysisService analysisService,
        HashService hashService
    ) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.analysisService = analysisService;
        this.hashService = hashService;
    }

    @GetMapping("/categories")
    // Pageable : paging, pagination (SpringBoot takes care)
    public ResponseEntity<List<Category>> getAllCategories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        // get all data from database and convert them into json
        Page<Category> page = categoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<Category> category = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(category);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the category to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/categories/analysis/{id}")
    public ResponseEntity<AnalysisDTO> getAnalysis(@PathVariable Long id) {
        log.debug("REST request to get Analysis Category : {}", id);
        AnalysisDTO anal = analysisService.calculate(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(anal));
    }

    @GetMapping("/categories/hash/{id}")
    public ResponseEntity<HashDTO> getHashName(@PathVariable Long id) {
        log.debug("REST request to get HashName Category : {}", id);
        HashDTO hash = hashService.hashcode(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(hash));
    }
}
