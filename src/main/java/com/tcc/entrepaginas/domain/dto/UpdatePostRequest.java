package com.tcc.entrepaginas.domain.dto;

import com.tcc.entrepaginas.domain.entity.ImagemPost;
import com.tcc.entrepaginas.modules.community.record.CommentsDto;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class UpdatePostRequest {
    private String id;
    private String title;
    private String content;
    private ImagemPost Image;
    private Instant date;
    private List<CommentsDto> comments;
}
