package com.githubtimemachine.repository;

import com.githubtimemachine.entity.RepositorySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorySnapshotRepository extends JpaRepository<RepositorySnapshot, UUID> {

    List<RepositorySnapshot> findByAnalyzedUserUsername(String username);

    List<RepositorySnapshot> findByAnalyzedUserId(UUID analyzedUserId);
}
