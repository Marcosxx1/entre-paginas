package com.tcc.entrepaginas.domain.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class UpdateCommunityRequest {

    private String id;

    private String title;

    private String content;

    private String icone; // not required

    private Boolean privado; // not required

    private Instant date;
}
