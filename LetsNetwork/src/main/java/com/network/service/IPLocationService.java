package com.network.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.network.request.IPLocationRequest;
import com.network.response.IPLocationResponse;
import com.network.response.MyPrivateIPResponse;
import com.network.response.MyPublicIPResponse;

@Service
public class IPLocationService {

	public IPLocationResponse inspectIp(IPLocationRequest iPInspectorRequest) {
		IPLocationResponse ipInspectorResponse = new IPLocationResponse();
		try {
			ipInspectorResponse.setValid(true);
			String ip[] = iPInspectorRequest.getIpAddress().split("\\.");
			if (ip.length == 4) {
				for (int i = 0; i < 4; i++) {
					int x = Integer.parseInt(ip[i]);
					if (x < 0 || x > 255)
						ipInspectorResponse.setValid(false);
				}
				if (ipInspectorResponse.isValid()) {
					File file = new File("GeoLite2-City.mmdb");
					System.out.println(file.getAbsolutePath());
					DatabaseReader database = new DatabaseReader.Builder(file).build();
					InetAddress inetAddress = InetAddress.getByName(iPInspectorRequest.getIpAddress());
					CityResponse cityResponse = database.city(inetAddress);
					ipInspectorResponse.setCity(cityResponse.getCity().getName());
					ipInspectorResponse.setLatitude(cityResponse.getLocation().getLatitude().toString());
					ipInspectorResponse.setLongitude(cityResponse.getLocation().getLongitude().toString());
					ipInspectorResponse.setCountry(cityResponse.getCountry().getName());
				}
			} else
				ipInspectorResponse.setValid(false);
			return ipInspectorResponse;
		} catch (Exception e) {
			ipInspectorResponse.setValid(false);
			return ipInspectorResponse;
		}
	}

	public MyPublicIPResponse myPublicIP() throws Exception {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		String ip = in.readLine();
		MyPublicIPResponse myPublicIPResponse = new MyPublicIPResponse();
		myPublicIPResponse.setIpAddress(ip);
		return myPublicIPResponse;
	}

	public MyPrivateIPResponse myPrivateIP() throws Exception {
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		HashMap<String,String> map=new HashMap<String, String>();
		while(e.hasMoreElements())
		{
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		        InetAddress i = (InetAddress) ee.nextElement();
		        String name=i.getHostName();
		        if(!(name.contains("localhost")||name.contains("%"))) map.put(name, i.getHostAddress());
		    }
		}
		MyPrivateIPResponse myPrivateIPResponse=new MyPrivateIPResponse();
		myPrivateIPResponse.setMap(map);
		return myPrivateIPResponse;
	}

}
