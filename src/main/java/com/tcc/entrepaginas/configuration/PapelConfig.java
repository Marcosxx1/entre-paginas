package com.tcc.entrepaginas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcc.entrepaginas.modules.users.entities.Papel;
import com.tcc.entrepaginas.modules.users.repositories.PapelRepository;

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
