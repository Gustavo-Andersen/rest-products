package br.com.rest_products.dto;

import br.com.rest_products.data.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(ProductEntity entity);
    ProductEntity toEntity(ProductDTO dto);
}
