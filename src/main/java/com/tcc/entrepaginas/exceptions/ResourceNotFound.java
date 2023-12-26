package com.tcc.entrepaginas.exceptions;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(Object id) {
        super("Resource not found. Id " + id);
    }

}
