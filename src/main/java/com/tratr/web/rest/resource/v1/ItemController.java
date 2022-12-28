package com.tratr.web.rest.resource.v1;

import com.tratr.domain.Category;
import com.tratr.domain.Item;
import com.tratr.repository.ItemRepository;
import com.tratr.service.ItemService;
import com.tratr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

@RestController // annotation
@RequestMapping("/api/v1")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);
    private static final String ENTITY_NAME = "item";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    public ItemController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    // Item: Data Transfer Object (DTO)
    /**
     * {@code GET  /item} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of items");
        // get all data from database and convert them into json
        Page<Item> page = itemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        // database automatically generates Id, programmer doesn't need to pass Id
        if (item.getId() != null) {
            throw new BadRequestAlertException("A new item cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Item result = itemService.save(item); // save item that clients passed into database
        // after this step, Item result has an Id.
        return ResponseEntity
            .created(new URI("/api/items/" + result.getId())) // return 201
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing category.
     *
     * @param id the id of the category to save.
     * @param item the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Item item)
        throws URISyntaxException {
        log.debug("REST request to update Item : {}, {}", id, item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, item.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Item result = itemService.update(item);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, item.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item/:id} : Partial updates given fields of an existing category, field will
     * ignore if it is null
     *
     * @param id the id of the category to save.
     * @param item the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 404 (Not Found)} if the category is not found,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item/{id}", consumes = { "application/json", "application/merge-patch" + "+json" })
    public ResponseEntity<Item> partialUpdateItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Item item
    ) throws URISyntaxException {
        log.debug("REST request to partial update Item partially : {}, {}", id, item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, item.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Item> result = itemService.partialUpdate(item);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, item.getId().toString())
        );
    }

    /**
     * {@code GET  /item/:id} : get the "id" item.
     *
     * @param id the id of the item to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        log.debug("REST request to get Item : {}", id);
        Optional<Item> item = itemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(item);
    }

    /**
     * {@code GET  /item/:name} : get the "name" item.
     *
     * @param name the name of the item to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item/name/{name}")
    public ResponseEntity<Item> getItem(@PathVariable String name) {
        log.debug("REST request to get Item : {}", name);
        Optional<Item> item = itemService.findOne(name);
        return ResponseUtil.wrapOrNotFound(item);
    }

    /**
     * {@code DELETE  /items/:id} : delete the "id" item.
     *
     * @param id the id of the category to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
