package com.roberto.cloud_api.domain.port;

import com.roberto.cloud_api.domain.model.BillingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BillingRepositoryPort {
    List<BillingRecord> findAll();
    Page<BillingRecord> findAll(Pageable pageable);
    Page<BillingRecord> findAll(Specification<BillingRecord> spec, Pageable pageable);
    Optional<BillingRecord> findById(Long id);
    boolean existsById(Long id);
    boolean existsByBillingHash(String billingHash);
    void deleteById(Long id);
    void deleteByBillingHash(String billingHash);
    Set<String> findAllHashes();
    boolean existsByHash(String hash);

}
