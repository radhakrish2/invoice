package com.app.dto;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

public record InvoiceRequestDTO(
    Long clientId,
    LocalDate issueDate,
    LocalDate dueDate,
    List<InvoiceItemDTO> items
) {}
