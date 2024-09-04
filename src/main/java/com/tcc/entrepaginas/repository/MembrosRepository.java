package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Membros;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembrosRepository extends JpaRepository<Membros, String> {

    @Query("SELECT m FROM Membros m WHERE m.community.id = :communityId")
    Optional<List<Membros>> findByCommunityId(String communityId);

    int countByCommunityId(String id);
}
