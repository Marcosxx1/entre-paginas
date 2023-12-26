package com.tcc.entrepaginas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcc.entrepaginas.modules.community.entities.RoleCommunity;
import com.tcc.entrepaginas.modules.community.repositories.RoleCommunityRepository;

@Component
public class RoleCommunityConfig implements CommandLineRunner {

    @Autowired
    private RoleCommunityRepository roleCommunityRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = { "ADMIN", "MODERATOR", "USER" };

        for (String papelString : papeis) {
            RoleCommunity papel = roleCommunityRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new RoleCommunity(papelString);
                roleCommunityRepository.save(papel);
            }
        }
    }
}