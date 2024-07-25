package com.tcc.entrepaginas.configuration;

import com.tcc.entrepaginas.domain.entity.Papel;
import com.tcc.entrepaginas.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PapelConfig implements CommandLineRunner {

    @Autowired
    private PapelRepository papelRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = {"ADMIN", "USER"};

        for (String papelString : papeis) {
            Papel papel = papelRepository.findByPapelNome(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                papelRepository.save(papel);
            }
        }
    }
}
