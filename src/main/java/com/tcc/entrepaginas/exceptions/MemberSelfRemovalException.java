package com.tcc.entrepaginas.exceptions;

public class MemberSelfRemovalException extends RuntimeException {
    public MemberSelfRemovalException(String message) {
        super(message);
    }
}