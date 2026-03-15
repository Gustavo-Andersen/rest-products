package br.com.rest_products.service;

import br.com.rest_products.data.ProductEntity;
import br.com.rest_products.data.ProductRepository;
import br.com.rest_products.dto.ProductDTO;
import br.com.rest_products.dto.ProductMapper;
import br.com.rest_products.mocks.MockProduct;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class) //injeta o mockito no service
class ProductServiceTest {

    MockProduct input;

    @Mock //cria objeto falso mock, sem isso, usaria o banco de verdade
    ProductRepository repository;
    @Mock
    ProductMapper mapper;
    @InjectMocks //pega o mock e injeta dentro do service
    private ProductService service;

    @BeforeEach
    void setUp() { // sempre vai ser ativo antes dos testes
        input = new MockProduct();
    }

    @Test
    void getProduct() {
        ProductEntity product = input.mockEntity(1);
        product.setId(UUID.randomUUID());

        //aqui é o repository
        when(repository.findByIdAndActiveTrue(product.getId()))
                .thenReturn(Optional.of(product));

        //sobre a linha de cima: Quando chamarem o repository.findById, retorne o Optional
        //no caso, devolve duas alternativas:
        // Com valor, no caso foi encontrado
        // ou sem valor, não foi encontrado

        //aqui é o mapper

        when(mapper.toDTO(product)).thenReturn(
                new br.com.rest_products.dto.ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getValue(),
                        product.getQuantity()
                )
                //como o mapper é um mock, precisa explicar oque ele vai devolver,
                //por isso a o caminho da classe
        );

        var result = service.getProduct(product.getId());

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getName(), result.name());
        assertEquals(product.getDescription(), result.description());
        assertEquals(product.getValue(), result.value());
        assertEquals(product.getQuantity(), result.quantity());

        //verificações
    }

    @Test
    void listProduct() {
        List<ProductEntity> list = input.mockEntityList();
        when(repository.findByActiveTrue())
                .thenReturn(list);

        List<ProductDTO> dto = service.listProduct();

        assertNotNull(dto);
    }

    @Test
    void postProduct() {
        ProductEntity product = input.mockEntity(1);
        product.setId(UUID.randomUUID());
        ProductDTO dto = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getValue(),
                product.getQuantity()
                );

        //dto que o service recebe e vira entity
        when(mapper.toEntity(dto)).thenReturn(product);
        //repository salva
        when(repository.save(product)).thenReturn(product);
        //entity vira dto que o service devolve
        when(mapper.toDTO(product)).thenReturn(dto);

        var result = service.postProduct(dto);

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getName(), result.name());
        assertEquals(product.getDescription(), result.description());
        assertEquals(product.getValue(), result.value());
        assertEquals(product.getQuantity(), result.quantity());
    }

    @Test
    void updateProduct() {
        ProductEntity product = input.mockEntity(1);
        product.setId(UUID.randomUUID());

        ProductDTO dto = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getValue(),
                product.getQuantity()
        );

        when(repository.findByIdAndActiveTrue(product.getId()))
                .thenReturn(Optional.of(product));

        when(repository.save(product)).thenReturn(product);

        when(mapper.toDTO(product)).thenReturn(dto);

        var result = service.updateProduct(dto, product.getId());

        assertNotNull(result);
        assertEquals(product.getId(), result.id());
        assertEquals(product.getName(), result.name());
        assertEquals(product.getDescription(), result.description());
        assertEquals(product.getValue(), result.value());
        assertEquals(product.getQuantity(), result.quantity());
    }

    @Test
    void deleteProduct() {
        ProductEntity product = input.mockEntity(1);
        product.setId(UUID.randomUUID());
        product.setActive(true);

        when(repository.findByIdAndActiveTrue(product.getId()))
                .thenReturn(Optional.of(product));

        when(repository.save(product))
                .thenReturn(product);

        service.deleteProduct(product.getId());

        assertFalse(product.getActive());
        verify(repository).save(product);
    }
}