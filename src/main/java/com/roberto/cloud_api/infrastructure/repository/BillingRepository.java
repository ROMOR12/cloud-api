package com.roberto.cloud_api.infrastructure.repository;

import com.roberto.cloud_api.domain.model.BillingRecord;
import com.roberto.cloud_api.domain.port.BillingRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

public interface BillingRepository extends JpaRepository<BillingRecord, Long>,
        JpaSpecificationExecutor<BillingRecord> {

    boolean existsByBillingHash(String billingHash);

    @Transactional
    void deleteByBillingHash(String billingHash);

    @Query("SELECT b.billingHash FROM BillingRecord b")
    Set<String> findAllBillingHashes();

}
