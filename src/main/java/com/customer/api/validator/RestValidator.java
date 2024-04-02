package com.customer.api.validator;

public interface RestValidator<T>
{
    RestEntityResponse<T> validate(
        T t );
}
