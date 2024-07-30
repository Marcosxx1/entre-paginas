package com.tcc.entrepaginas.utils.community;

import com.tcc.entrepaginas.service.CommunityServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Component
@AllArgsConstructor
public class CommunityUtils {

    private final CommunityServiceNew communityService;

    public String createIconUrl(MultipartFile icone, HttpServletRequest request) {

        String fileName = communityService.atualizarIconeComunidade(icone);

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        return baseUrl + "/icone/" + fileName;
    }
}
