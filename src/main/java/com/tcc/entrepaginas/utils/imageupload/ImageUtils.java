package com.tcc.entrepaginas.utils.imageupload;

import com.tcc.entrepaginas.domain.entity.ImagemPost;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ImageUtils {

    public static void init(Path root) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }

    public static String saveImageWithUniqueName(MultipartFile image, Path root) {
        try {
            init(root);

            UUID uuid = UUID.randomUUID();
            String newFileName = uuid.toString();
            String extension = FilenameUtils.getExtension(image.getOriginalFilename());
            String fileName = newFileName + "." + extension;
            Files.copy(image.getInputStream(), root.resolve(fileName));

            return fileName;
        } catch (Exception e) {
            throw new CustomException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public static ImagemPost
            createImagePost( // TODO Ver possibilidade de ser Generic<T> ao invés de padrão apenas para o post
                    MultipartFile image,
                    HttpServletRequest request,
                    Post post,
                    Path root) { // TODO - VER POSSIBILIDADE DE NÃO SER STATICO
        if (image != null && !image.isEmpty()) {
            String fileName = saveImageWithUniqueName(image, root);

            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String url = baseUrl + "/post/" + fileName;

            return new ImagemPost(url, post);
        }
        return null;
    }
}
