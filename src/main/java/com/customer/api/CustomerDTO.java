package com.customer.api;


import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(@NotBlank String name, @NotBlank String email) {
}
