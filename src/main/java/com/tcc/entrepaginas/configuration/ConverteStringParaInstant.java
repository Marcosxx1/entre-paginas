package com.tcc.entrepaginas.configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverteStringParaInstant implements Converter<String, Instant> {

    @Override
    public Instant convert(String source) {
        LocalDate localDate = LocalDate.parse(source);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}