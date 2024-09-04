package com.tcc.entrepaginas.utils.member;

import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.repository.MembrosRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembersUtil {

    private final MembrosRepository membrosRepository;

    /*Seria uma boa ideia criarmos todos os UTILS como priavate/protected
     * E outros métodos para acessar eles, assim garantimos integridade na
     * Aplicação*/

    public void validateSelfRemoval(String memberId, String userFromContext) {
        Objects.requireNonNull(memberId, "O ID do membro não pode ser nulo.");
        Objects.requireNonNull(userFromContext, "O ID do usuário não pode ser nulo.");

        if (Objects.equals(memberId, userFromContext)) {
            throw new IllegalArgumentException(
                    "Você não pode remover a si mesmo da comunidade."); // TODO - PADRONIZAR, CRIAR EXCEPTION CORRETA
        }
    }

    public void validateSingleMemberInCommunity(Membros member) {
        if (isOnlyMemberInCommunity(member)) {
            throw new IllegalStateException("Você não pode remover o único membro da comunidade.");
        }
    }

    public boolean isOnlyMemberInCommunity(Membros member) {
        int memberCount =
                membrosRepository.countByCommunityId(member.getCommunity().getId());

        return memberCount == 1;
    }
}
