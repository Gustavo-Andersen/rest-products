package br.com.rest_products.service;

import br.com.rest_products.data.ProductEntity;
import br.com.rest_products.data.ProductRepository;
import br.com.rest_products.dto.ProductDTO;
import br.com.rest_products.dto.ProductMapper;
import br.com.rest_products.exception.UnsupportedExceptionProducts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    private ProductEntity findActive(UUID id){
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    logger.warn("Produto não encontrado ou desativado com id: {}", id);
                    return new UnsupportedExceptionProducts("Produto não encontrado");
                });
    }


    @Autowired
    ProductRepository repository;

    @Autowired
    ProductMapper mapper;

    public ProductDTO getProduct(UUID id){
        logger.info("Procurando produto");

        ProductEntity product = findActive(id);

        return mapper.toDTO(product);
    }

    public List<ProductDTO> listProduct(){
        logger.info("Procurando produtos");

        List<ProductEntity> productList = repository.findByActiveTrue();

        return productList.stream()
                .map(p -> mapper.toDTO(p))
                .toList();
    }

    public ProductDTO postProduct(ProductDTO dto){
        logger.info("Salvando produto");

        ProductEntity product = mapper.toEntity(dto);

        repository.save(product);

        return mapper.toDTO(product);

    }

    public ProductDTO updateProduct(ProductDTO dto, UUID id){
        logger.info("Atualizando produto");

        ProductEntity product = findActive(id);

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setValue(dto.value());
        product.setQuantity(dto.quantity());

        repository.save(product);

        return mapper.toDTO(product);
    }

    public void deleteProduct(UUID id){
        logger.info("Desativando produto com id: {}", id);

        ProductEntity deletado = findActive(id);

        deletado.setActive(false);
        repository.save(deletado);
    }
}
