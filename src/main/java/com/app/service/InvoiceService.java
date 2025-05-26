package com.app.service;

import java.util.List;

import com.app.dto.InvoiceRequestDTO;
import com.app.dto.InvoiceResponseDTO;
import com.app.entity.InvoiceStatus;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(Long userId, InvoiceRequestDTO dto);
    List<InvoiceResponseDTO> getAllInvoices(Long userId);
    InvoiceResponseDTO getInvoiceById(Long invoiceId);
    InvoiceResponseDTO updateInvoice(Long invoiceId, InvoiceRequestDTO dto);
    
    void deleteInvoice(Long invoiceId);
    InvoiceResponseDTO updateInvoiceStatus(Long invoiceId, InvoiceStatus status);

}