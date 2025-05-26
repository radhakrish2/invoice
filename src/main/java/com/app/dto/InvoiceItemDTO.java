package com.app.dto;

public record InvoiceItemDTO(
	    String description,
	    Integer quantity,
	    Double unitPrice
	) {}