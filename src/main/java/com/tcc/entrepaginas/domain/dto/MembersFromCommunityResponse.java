package com.tcc.entrepaginas.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class MembersFromCommunityResponse {

    /*Tive que fazer esse response pois estava sendo retornada uma recursão danada se fossemos
     * retornar isso do controller:  public List<Membros> listAllCommunityMembers*/

    private String memberId;
    private String userId;
    private String communityRole;
    private String communityId;
}
