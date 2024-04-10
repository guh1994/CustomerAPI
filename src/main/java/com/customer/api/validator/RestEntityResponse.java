package com.customer.api.validator;

import java.util.List;

public record RestEntityResponse<T>(
        boolean success,
        List<String> messages,
        T entity) {

    public static <T> RestEntityResponse<T> createSuccess(
            final T entity) {
        return new RestEntityResponse<T>(true, List.of(), entity);
    }

    public static <T> RestEntityResponse<T> createError(
            final List<String> errorMessages) {
        return new RestEntityResponse<T>(false, errorMessages, null);
    }

    public static <T> RestEntityResponse<T> deleteSuccess() {

        return new RestEntityResponse<T>(true, List.of("Customer Deleted with success"), null);
    }

    public static <T> RestEntityResponse<T> deleteError(final List<String> errorMessages) {

        return new RestEntityResponse<T>(false, errorMessages, null);
    }

}
