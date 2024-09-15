package com.tcc.entrepaginas.service.member;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.repository.MembrosRepository;
import com.tcc.entrepaginas.utils.community.CommunityUtils;
import com.tcc.entrepaginas.utils.member.MembersUtil;
import com.tcc.entrepaginas.utils.roleCommunity.RoleCommunityUtils;
import com.tcc.entrepaginas.utils.user.UserUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MembrosRepository membrosRepository;
    private final MemberMapper memberMapper;
    private final MembersUtil membersUtil;
    private final UserUtils userUtils;
    private final CommunityUtils communityUtils;
    private final RoleCommunityUtils roleCommunityUtils;

    @Override
    public void saveMember(Membros membros) {
        membrosRepository.save(membros);
    }

    @Override
    public List<MembersFromCommunityResponse> getMembersByCommunityId(String communityId) {
        var members = membrosRepository
                .findByCommunityId(communityId)
                .orElseThrow(() -> new ResourceNotFound("TODO -  SEM USUÁRIOS PARA COMUNIDADE"));

        return memberMapper.toListOfMembersFromCommunityResponse(members);
    }

    @Override
    public void removeMemberFromCommunity(String memberId, Authentication authentication) {

        var userFromContext = userUtils.getIdUserFromUserDetail(authentication);

        membersUtil.validateSelfRemoval(memberId, userFromContext); // Não pode se remover

        var member = getMemberById(memberId);

        membersUtil.validateSingleMemberInCommunity(member); // verifica se é o único na comunidade

        membrosRepository.delete(member);
    }

    @Override
    public void updateMemberAuthorities(String memberId, String newComunnityRole) {

        var member = getMemberById(memberId);

        member.getRoleCommunity().setPapel(newComunnityRole);
        membrosRepository.save(member);
    }

    private Membros getMemberById(String memberId) {
        return membrosRepository
                .findById(memberId)
                .orElseThrow(() -> new ResourceNotFound("TODO -  Membro não encontrado"));
    }

    @Override
    public void addMemberToCommunity(String communityId, String userId) {

        if (!membrosRepository.findByCommunityAndUsuario(userId,
                communityId).isPresent()) {
            Usuario user = userUtils.getUserById(userId);
            Community community = communityUtils.getCommunityById(communityId);

            RoleCommunity roleCommunity = roleCommunityUtils.getRoleCommunity("USER");

            Membros membros = Membros.builder().usuario(user).community(community).roleCommunity(roleCommunity).build();

            membrosRepository.save(membros);
        }
    }
}
