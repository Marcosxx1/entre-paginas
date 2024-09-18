package com.tcc.entrepaginas.service.user;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.dto.UserListResponse;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {

        String registerAndRedirect(Authentication authentication, Model model);

        String saveUserFromForm(
                        NovoUsuarioRequest novoUsuarioRequest,
                        BindingResult result,
                        RedirectAttributes attributes,
                        Model model,
                        HttpServletRequest request);

        String saveUserImage(Authentication authentication, MultipartFile image, HttpServletRequest request);

        String updateUserNameLoginAndEmail(
                        Usuario user,
                        String id,
                        UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
                        BindingResult result,
                        RedirectAttributes attributes,
                        Model model);

        String deleteUser(String id);

        List<Community> getUserCommunities(String username);

        List<UserListResponse> listAllUserBasedOnQuery(String query);

        List<Livro> getUserLivros(String username);

        Usuario getUserByEmail(String email);

        void saveUser(Usuario user);

        String verifyEmail(String token);

        void updateUserPassword(String id, String senha);

        List<Usuario> listUser(Sort sort);

}
