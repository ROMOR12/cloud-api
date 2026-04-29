package com.roberto.cloud_api.domain.port;

import com.roberto.cloud_api.domain.model.AuditRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuditRepositoryPort {
    List<AuditRecord> findAll();
    Page<AuditRecord> findAll(Pageable pageable);
    Page<AuditRecord> findAll(Specification<AuditRecord> spec, Pageable pageable);
    Optional<AuditRecord> findById(Long id);
    boolean existsById(Long id);
    boolean existsByEventHash(String eventHash);
    void deleteById(Long id);
    void deleteByEventHash(String eventHash);
    Set<String> findAllHashes();
    boolean existsByHash(String hash);

}
