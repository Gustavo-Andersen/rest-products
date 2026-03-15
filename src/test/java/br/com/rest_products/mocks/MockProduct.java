package br.com.rest_products.mocks;

import br.com.rest_products.data.ProductEntity;
import br.com.rest_products.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockProduct {

    public ProductEntity mockEntity(){
        return mockEntity(0);
    }

    public ProductDTO mockDTO(){
        return mockDTO(0);
    }

    public List<ProductEntity> mockEntityList(){
        List<ProductEntity> products = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            products.add(mockEntity(i));
        }
        return products;
    }

    public List<ProductDTO> mockDTOList(){
        List<ProductDTO> products = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            products.add(mockDTO(i));
        }
        return products;
    }

    public ProductEntity mockEntity(int number){
        ProductEntity p = new ProductEntity();
        p.setId(UUID.randomUUID());
        p.setName("Nome teste" + number);
        p.setDescription("Descrição teste" + number);
        p.setValue(1.0);
        p.setQuantity(1);
        p.setActive(true);
        return p;
    }
    public ProductDTO mockDTO(int number){
        ProductDTO p = new ProductDTO(
                UUID.randomUUID(),
                "Nome teste" + number,
                "Descrição teste" + number,
                1.0,
               1);
        return p;
    }
}
