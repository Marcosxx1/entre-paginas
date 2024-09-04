package com.tcc.entrepaginas.service.imagemlivro;

import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.repository.ImagemLivroRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ImagemLivroServiceImpl implements ImagemLivroService {

    private final ImagemLivroRepository imagemLivroRepository;
    private Path root = Paths.get("bookImage");

    @Override
    public void saveImagemLivro(ImagemLivro imagemLivro) {
        imagemLivroRepository.save(imagemLivro);
    }

    @Override
    public List<ImagemLivro> processImagens(List<MultipartFile> imagens, Livro livro, HttpServletRequest request) {
        List<ImagemLivro> imagensLivro = new ArrayList<>();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        for (MultipartFile imagem : imagens) {
            String fileName = atualizarImagemLivro(imagem);
            String url = baseUrl + "/bookImage/" + fileName;
            imagensLivro.add(new ImagemLivro(url, livro));
        }
        return imagensLivro;
    }

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }

    public String atualizarImagemLivro(MultipartFile image) {
        try {
            init();

            UUID uuid = UUID.randomUUID();

            String newFileName = uuid.toString();

            String extension = FilenameUtils.getExtension(image.getOriginalFilename());

            String fileName = newFileName + "." + extension;

            Files.copy(image.getInputStream(), this.root.resolve(fileName));

            return fileName;
        } catch (Exception e) {
            throw new CustomException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
