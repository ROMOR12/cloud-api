package com.roberto.cloud_api.repository;

import com.roberto.cloud_api.model.AuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * This is our direct line to the database.
 * Spring Boot magically writes all the SQL for us behind the scenes, giving us easy ways
 * to save, search, and safely delete records using their unique hash.
 */
public interface AuditRepository extends JpaRepository<AuditRecord, Long>, JpaSpecificationExecutor<AuditRecord> {
    boolean existsByEventHash(String eventHash);
    @Transactional
    void deleteByEventHash(String eventHash);

    @Query("SELECT a.eventHash FROM AuditRecord a")
    Set<String> findAllAuditHashes();
}
