package com.tcc.entrepaginas.service.community;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public interface CommunityServiceNew {
    List<Community> listarRandomCommunities(int totalItems, Principal principal);

    List<Community> listCommunitiesForUser(Usuario user);

    List<Community> listarCommunities(Sort sort);

    List<Community> listarCommunitiesPorUsuario(String idUsuario, String role);

    List<String> listCommunitiesWithOrWithoutSort(String query);

    List<Community> buscarComunidades(String query);

    Community pegarCommunity(String id);

    String salvarComunidade(
            Model model, BindingResult result, NovaComunidadeRequest novaComunidadeRequest, String idUsuario);

    void atualizarComunidade(Community community);

    String deleteCommunity(String idComunidade);

    String beginCommunityCreation(Model model, String idUsuario, Authentication authentication);

    String allMyCommunities(String idUsuario, Model model, Authentication authentication);

    ResponseEntity<String> changeCommunityIcon(String idComunidade, MultipartFile icone, HttpServletRequest request);

    UpdateCommunityRequest updateCommunity(
            String id, UpdateCommunityRequest updateCommunityRequest, BindingResult result);

    String prepareCommunityAndListOfPosts(String id, Model model, Authentication authentication);
}
