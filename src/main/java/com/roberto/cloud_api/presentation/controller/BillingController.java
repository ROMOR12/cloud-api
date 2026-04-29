package com.roberto.cloud_api.presentation.controller;

import com.roberto.cloud_api.application.dto.request.BillingFilterRequest;
import com.roberto.cloud_api.application.dto.response.BillingResponse;
import com.roberto.cloud_api.application.dto.response.MessageResponse;
import com.roberto.cloud_api.application.facade.BillingFacade;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This controller handles all the web requests for the billing section.
 * It gives you endpoints to get, search and delete billing records.
 * Just like the audit controller, it does not do the heavy lifting itself, it just passes the work to the BillingFacade.
 */
@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingFacade billingFacade;

    public BillingController(BillingFacade billingFacade) {
        this.billingFacade = billingFacade;
    }

    @GetMapping
    public Page<BillingResponse> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return billingFacade.getPaginatedBilling(page, size);
    }

    @GetMapping("/all")
    public List<BillingResponse> getAll() {
        return billingFacade.getAllBilling();
    }

    @PostMapping("/search")
    public Page<BillingResponse> search(
            @RequestBody BillingFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return billingFacade.searchBilling(filter, page, size);
    }

    @GetMapping("/{id}")
    public BillingResponse getById(@PathVariable Long id) {
        return billingFacade.getBillingById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable Long id) {
        billingFacade.deleteBilling(id);
        return ResponseEntity.ok(new MessageResponse("Record with ID " + id + " deleted successfully."));
    }

    @DeleteMapping("/hash/{hash}")
    public ResponseEntity<MessageResponse> deleteByHash(@PathVariable String hash) {
        billingFacade.deleteBillingByHash(hash);
        return ResponseEntity.ok(new MessageResponse("Record with hash " + hash + " deleted successfully."));
    }

}