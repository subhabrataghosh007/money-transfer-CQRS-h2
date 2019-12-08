package com.payment.dto;

import javax.ws.rs.core.Response.Status;

public class APIResponse {
	private String status;
	private String description;

	public APIResponse(Status status, String description) {
		this.status = status.getReasonPhrase();
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
