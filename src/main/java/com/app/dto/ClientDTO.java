package com.app.dto;

public record ClientDTO(
	    Long clientId,
	    String name,
	    String email,
	    String companyName,
	    String address
	) {}
