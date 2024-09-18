package com.tcc.entrepaginas.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class SearchBarResponse {
    private String id;
    private String title;
}
