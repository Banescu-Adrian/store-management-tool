package com.store.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @NotNull
    @Size(min = 1)
    @Size(max = 255)
    public String name;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("9999.0")
    public Double price;
}
