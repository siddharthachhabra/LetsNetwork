package com.network.response;

import lombok.Data;

@Data
public class IPLocationResponse {
	
	private boolean isValid;
	private String city,country,latitude,longitude;

}
