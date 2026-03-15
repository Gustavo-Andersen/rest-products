package br.com.rest_products.controller;

import br.com.rest_products.dto.ProductDTO;
import br.com.rest_products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO dto){
        ProductDTO created = service.postProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<?> listAllProducts(){
        List<ProductDTO> dtoList = service.listProduct();
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchProduct(@PathVariable UUID id){
            ProductDTO dto = service.getProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
            service.deleteProduct(id);
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductDTO newProduct){
            ProductDTO dto = service.updateProduct(newProduct, id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
