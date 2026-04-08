package com.roberto.cloud_api.usecase.billing;

import com.roberto.cloud_api.repository.BillingRepository;
import org.springframework.stereotype.Component;

/**
 * This tool handles the safe removal of a billing record.
 * It checks the database first to make sure the record actually exists before deleting it,
 * which prevents the app from throwing ugly errors.
 */
@Component
public class DeleteBillingUseCase {

    private final BillingRepository billingRepository;

    public DeleteBillingUseCase(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public void execute(Long id) {
        if (!billingRepository.existsById(id)) {
            throw new RuntimeException("Error: Billing record with ID " + id + " does not exist.");
        }
        billingRepository.deleteById(id);
    }

    public void executeByHash(String hash) {
        if (!billingRepository.existsByBillingHash(hash)) {
            throw new RuntimeException("Error: Billing record with hash " + hash + " does not exist.");
        }
        billingRepository.deleteByBillingHash(hash);
    }
}