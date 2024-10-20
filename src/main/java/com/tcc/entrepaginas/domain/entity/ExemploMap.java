package com.tcc.entrepaginas.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExemploMap {
    public static void main(String[] args) {
        // Cria um HashMap para armazenar o nome como chave e a idade como valor
        Map<String, Integer> pessoas = new HashMap<>();
        //Map<String, Integer> pessoas = new HashMap<>(); change this to a list
        List<String> pessoas2 = new ArrayList<>();

        // Adiciona alguns pares chave-valor ao mapa
        pessoas.put("Alice", 30);
        pessoas.put("Bob", 25);
        pessoas.put("Carlos", 28);

        // Acessa um valor pelo nome
        Integer idadeBob = pessoas.get("Bob");
        System.out.println("A idade de Bob é: " + idadeBob);

        // Percorre e imprime todos os elementos no mapa
        for (Map.Entry<String, Integer> entrada : pessoas.entrySet()) {
            System.out.println(entrada.getKey() + " tem " + entrada.getValue() + " anos");
        }
    }
}
