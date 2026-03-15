package br.com.rest_products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ProductDTO (
        UUID id,
        @NotBlank(message = "Nome obrigatório")
        String name,
        @NotBlank(message = "Descrição obrigatória")
        String description,
        @NotNull(message = "Preço obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        Double value,
        @NotNull(message = "Quantidade obrigatório")
        @Positive(message = "Quantidade deve ser maior que zero")
        int quantity) {
}
