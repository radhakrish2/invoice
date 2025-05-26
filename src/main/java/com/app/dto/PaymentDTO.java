package com.app.dto;
import java.util.Date;

public record PaymentDTO(
    Long invoiceId,
    Double amount,
    Date paymentDate,
    String paymentMethod
) {}