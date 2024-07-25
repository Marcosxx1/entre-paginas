package com.tcc.entrepaginas.modules.users.record;

import java.util.ArrayList;
import java.util.List;

import com.tcc.entrepaginas.domain.Contato;


public record ContactDto(String id, String telefone) {

    public static ContactDto fromContato(Contato contato) {
        return new ContactDto(contato.getId(),
                contato.getTelefone());

    }

    public static List<ContactDto>  transformeContatoEmDto(List<Contato> contatos) {
        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contato contato : contatos) {
            ContactDto contactDto = ContactDto.fromContato(contato);
            contactDtos.add(contactDto);
        }

        return contactDtos;
    }

}