package com.tcc.entrepaginas.modules.community.usecases.community.updatecommunity;

import com.tcc.entrepaginas.modules.community.record.CommunityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comunidades")
public class UpdateCController {

    @Autowired
    private UpdateCUseCase updateCommUseCase;

    public UpdateCController(UpdateCUseCase updateCommUseCase) {
        this.updateCommUseCase = updateCommUseCase;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommunityDto> updateCommunity(
            @PathVariable String id, @RequestBody CommunityDto communityDto) {
        CommunityDto updateCommunity = updateCommUseCase.updateCommunity(id, communityDto);
        if (updateCommunity != null) {
            return ResponseEntity.ok(updateCommunity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
