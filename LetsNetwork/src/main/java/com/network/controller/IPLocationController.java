package com.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.network.request.IPLocationRequest;
import com.network.response.IPLocationResponse;
import com.network.response.MyPrivateIPResponse;
import com.network.response.MyPublicIPResponse;
import com.network.service.IPLocationService;

@Controller
public class IPLocationController {

	@Autowired
	private IPLocationService ipInspectorService;

	@RequestMapping("/")
	public String index() {
		return "Welcome to Lets Network!";
	}

	@RequestMapping(value = "/ipLocation", method = RequestMethod.GET)
	@ResponseBody
	public IPLocationResponse evaluate(@RequestParam("ipAddress") String ipAddress) {
		IPLocationRequest request = new IPLocationRequest();
		request.setIpAddress(ipAddress);
		IPLocationResponse response = ipInspectorService.inspectIp(request);
		return response;
	}

	@RequestMapping(value = "/myPublicIP", method = RequestMethod.GET)
	@ResponseBody
	public MyPublicIPResponse myPublicIP() throws Exception {
		MyPublicIPResponse response = ipInspectorService.myPublicIP();
		return response;
	}
	
	@RequestMapping(value = "/myPrivateIP", method = RequestMethod.GET)
	@ResponseBody
	public MyPrivateIPResponse myPrivateIP() throws Exception {
		MyPrivateIPResponse response = ipInspectorService.myPrivateIP();
		return response;
	}

}
