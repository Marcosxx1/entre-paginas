package com.tcc.entrepaginas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcc.entrepaginas.domain.Papel;
import com.tcc.entrepaginas.repository.PapelRepository;

@Component
public class PapelConfig implements CommandLineRunner {

    @Autowired
    private PapelRepository papelRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = { "ADMIN", "USER" };

        for (String papelString : papeis) {
            Papel papel = papelRepository.findByPapelNome(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                papelRepository.save(papel);
            }
        }
    }
}
