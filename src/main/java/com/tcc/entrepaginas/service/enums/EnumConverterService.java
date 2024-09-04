package com.tcc.entrepaginas.service.enums;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;

public interface EnumConverterService {
    EstadoBrasil convertEstadoBrasil(String nomeEstadoBrasil);

    Categoria convertCategoria(String nomeCategoria);

    Estado convertEstado(String nomeEstado);

    Tipo convertTipo(String nomeTipo);
}
