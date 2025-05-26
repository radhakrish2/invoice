package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.InvoiceRequestDTO;
import com.app.dto.InvoiceResponseDTO;
import com.app.entity.InvoiceStatus;
import com.app.service.InvoiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InvoiceController {

    private final InvoiceService invoiceService;

    // Create a new invoice
    @PostMapping("/{userId}")
    public ResponseEntity<InvoiceResponseDTO> createInvoice(
            @PathVariable Long userId,
            @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.createInvoice(userId, dto));
    }

    // Get all invoices for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(@PathVariable Long userId) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(userId));
    }

    // Get invoice by ID
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(invoiceId));
    }
    
   
    
 // Update an existing invoice
    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @PathVariable Long invoiceId,
            @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoiceId, dto));
    }
    
 // Delete invoice
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.noContent().build();
    }

    // Update invoice status (PAID/UNPAID etc.)
    @PatchMapping("/{invoiceId}/status")
    public ResponseEntity<InvoiceResponseDTO> updateInvoiceStatus(
            @PathVariable Long invoiceId,
            @RequestParam InvoiceStatus status) {
        return ResponseEntity.ok(invoiceService.updateInvoiceStatus(invoiceId, status));
    }
    
    
}