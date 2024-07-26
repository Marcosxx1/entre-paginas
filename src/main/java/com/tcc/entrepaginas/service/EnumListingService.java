package com.tcc.entrepaginas.service;

import java.util.List;

public interface EnumListingService {
    List<String> listarTodasCategorias();

    List<String> listarTodosEstados();

    List<String> listarTodosTipos();

    List<String> listarTodosEstadosBrasil();
}
