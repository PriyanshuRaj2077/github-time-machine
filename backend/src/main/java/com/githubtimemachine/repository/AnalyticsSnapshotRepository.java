package com.githubtimemachine.repository;

import com.githubtimemachine.entity.AnalyticsSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot, UUID> {

    Optional<AnalyticsSnapshot> findByAnalyzedUserUsername(String username);

    Optional<AnalyticsSnapshot> findByAnalyzedUserId(UUID analyzedUserId);
}
