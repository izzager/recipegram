package com.eltavi.recipegram.validator;

public interface DtoValidator<T> {
    void validate(T dto);
}
