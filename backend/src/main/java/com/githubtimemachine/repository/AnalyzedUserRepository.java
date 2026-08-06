package com.githubtimemachine.repository;

import com.githubtimemachine.entity.AnalyzedUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalyzedUserRepository extends JpaRepository<AnalyzedUser, UUID> {

    @EntityGraph(attributePaths = {"repositorySnapshots", "analyticsSnapshot"})
    Optional<AnalyzedUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
