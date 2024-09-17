package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Community;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<Community, String> {

    @Query(
            """
            SELECT com FROM Community com \
            JOIN Membros mem ON com.id = mem.community.id \
            JOIN Usuario usu ON mem.usuario.id = usu.id \
            JOIN RoleCommunity ro ON mem.roleCommunity.id = ro.id \
            WHERE ro.papel ILIKE :role \
            AND usu.id = :userId\
            """)
    Optional<List<Community>> getCommunitiesByRole(@Param("role") String role, @Param("userId") String userId);

    Optional<List<Community>> findByTitleContainingIgnoreCase(String query);

    @Query("SELECT com FROM Community com JOIN com.membros mem WHERE mem.usuario.id = :userId")
    Optional<List<Community>> findCommunitiesByUserId(@Param("userId") String userId);
}
