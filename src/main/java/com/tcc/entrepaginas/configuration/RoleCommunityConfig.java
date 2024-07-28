package com.tcc.entrepaginas.configuration;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.repository.RoleCommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleCommunityConfig implements CommandLineRunner {

    @Autowired
    private RoleCommunityRepository roleCommunityRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = {"ADMIN", "MODERATOR", "USER"};

        for (String papelString : papeis) {
            RoleCommunity papel = roleCommunityRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new RoleCommunity(papelString);
                roleCommunityRepository.save(papel);
            }
        }
    }
}
