package com.tcc.entrepaginas.modules.books.record;

import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import java.util.ArrayList;
import java.util.List;

public record ImageDto(String id, String nome, long tamanhoEmBytes) {

    public static ImageDto fromImage(ImagemLivro imagemLivro) {
        return new ImageDto(imagemLivro.getId(), imagemLivro.getNome(), imagemLivro.getTamanhoEmBytes());
    }

    public static List<ImageDto> transformeImagemEmDto(List<ImagemLivro> imagens) {
        List<ImageDto> imageDtos = new ArrayList<>();

        for (ImagemLivro imagem : imagens) {
            ImageDto imageDto = ImageDto.fromImage(imagem);
            imageDtos.add(imageDto);
        }

        return imageDtos;
    }
}
