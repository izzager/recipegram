package com.eltavi.recipegrampath.validator;

public interface DtoValidator<T> {
    void validate(T dto);
}
