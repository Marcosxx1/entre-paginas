package com.tcc.entrepaginas.service.enums;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EnumListingServiceImpl implements EnumListingService {

    @Override
    public List<String> listarTodasCategorias() {
        return Arrays.stream(Categoria.values()).map(Categoria::name).collect(Collectors.toList());
    }

    @Override
    public List<String> listarTodosEstados() {
        return Arrays.stream(Estado.values()).map(Estado::name).collect(Collectors.toList());
    }

    @Override
    public List<String> listarTodosTipos() {
        return Arrays.stream(Tipo.values()).map(Tipo::name).collect(Collectors.toList());
    }

    @Override
    public List<String> listarTodosEstadosBrasil() {
        return Arrays.stream(EstadoBrasil.values()).map(EstadoBrasil::name).collect(Collectors.toList());
    }
}
