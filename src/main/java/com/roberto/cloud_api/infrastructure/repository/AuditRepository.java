package com.roberto.cloud_api.infrastructure.repository;

import com.roberto.cloud_api.domain.model.AuditRecord;
import com.roberto.cloud_api.domain.port.AuditRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

public interface AuditRepository extends JpaRepository<AuditRecord, Long>,
        JpaSpecificationExecutor<AuditRecord> {

    boolean existsByEventHash(String eventHash);

    @Transactional
    void deleteByEventHash(String eventHash);

    @Query("SELECT a.eventHash FROM AuditRecord a")
    Set<String> findAllAuditHashes();

}