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

    @NotNull(message = "ID cannot be null")
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 5000, message = "Content cannot exceed 5000 characters")
    private String content;

    private String icone; // not required

    private Boolean privado; // not required

    @NotNull(message = "Date cannot be null")
    private Instant date;
}
