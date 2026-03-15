package br.com.rest_products.controller;

import br.com.rest_products.dto.ProductDTO;
import br.com.rest_products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
        value = "/product",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto){
        ProductDTO created = service.postProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> listAllProducts(){
        List<ProductDTO> dtoList = service.listProduct();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> searchProduct(@PathVariable UUID id){
        ProductDTO dto = service.getProduct(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductDTO newProduct){
        ProductDTO dto = service.updateProduct(newProduct, id);
        return ResponseEntity.ok(dto);
    }
}
