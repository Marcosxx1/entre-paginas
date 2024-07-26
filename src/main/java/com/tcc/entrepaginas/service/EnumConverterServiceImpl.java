package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import org.springframework.stereotype.Service;

@Service
public class EnumConverterServiceImpl implements EnumConverterService {
    @Override
    public EstadoBrasil convertEstadoBrasil(String nomeEstadoBrasil) {
        try {
            return EstadoBrasil.valueOf(nomeEstadoBrasil);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Categoria convertCategoria(String nomeCategoria) {
        try {
            return Categoria.valueOf(nomeCategoria);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Estado convertEstado(String nomeEstado) {
        try {
            return Estado.valueOf(nomeEstado);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Tipo convertTipo(String nomeTipo) {
        try {
            return Tipo.valueOf(nomeTipo);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
