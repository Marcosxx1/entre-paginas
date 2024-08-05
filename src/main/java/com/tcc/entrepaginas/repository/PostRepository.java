package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, String> {

    List<Post> findByCommunity(Community community);

    @Query("SELECT p.community FROM Post p WHERE p.community.id = :communityId")
    Optional<Community> findCommunityById(@Param("communityId") String communityId);
}
