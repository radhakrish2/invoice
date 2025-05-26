package com.app.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.app.entity.InvoiceStatus;

public record InvoiceResponseDTO(
    Long invoiceId,
    String invoiceNumber,
    LocalDate issueDate,
    LocalDate dueDate,
    InvoiceStatus status,
    Double totalAmount,
    ClientDTO client,
    List<InvoiceItemDTO> items
) {}