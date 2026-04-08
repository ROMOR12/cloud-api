package com.roberto.cloud_api.controller;

import com.roberto.cloud_api.dto.request.AuditFilterRequest;
import com.roberto.cloud_api.dto.response.AuditResponse;
import com.roberto.cloud_api.dto.response.MessageResponse;
import com.roberto.cloud_api.facade.AuditFacade;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This controller handles all the web requests for the audit section.
 * It gives you endpoints to get, search and delete audit records.
 * It does not do the heavy lifting itself, it just passes the work to the AuditFacade.
 */
@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditFacade auditFacade;

    public AuditController(AuditFacade auditFacade) {
        this.auditFacade = auditFacade;
    }

    @GetMapping
    public Page<AuditResponse> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return auditFacade.getPaginatedAudits(page, size);
    }

    @GetMapping("/all")
    public List<AuditResponse> getAll() {
        return auditFacade.getAllAudits();
    }

    @PostMapping("/search")
    public Page<AuditResponse> search(
            @RequestBody AuditFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return auditFacade.searchAudit(filter, page, size);
    }

    @GetMapping("/{id}")
    public AuditResponse getById(@PathVariable Long id) {
        return auditFacade.getAuditById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable Long id) {
        auditFacade.deleteAudit(id);
        return ResponseEntity.ok(new MessageResponse("Audit record with ID " + id + " deleted successfully."));
    }

    @DeleteMapping("/hash/{hash}")
    public ResponseEntity<MessageResponse> deleteByHash(@PathVariable String hash) {
        auditFacade.deleteAuditByHash(hash);
        return ResponseEntity.ok(new MessageResponse("Audit record with hash " + hash + " deleted successfully."));
    }
}