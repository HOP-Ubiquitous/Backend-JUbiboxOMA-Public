package eu.hop.leshan.client;

import hop.http.client.HopHTTPClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import eu.hop.leshan.objects.OMAClient;
import eu.hop.leshan.objects.ObjectLink;
import eu.hop.leshan.objects.Resource;
import eu.hop.leshan.responses.GETResourceResponse;

public class LeshanHttpClient {
	
	private String leshanServerAddr;
	private HopHTTPClient httpClient;
	
	private boolean isIPv4(String ipAddress){
		try {
			Inet4Address addr = (Inet4Address) Inet4Address.getByName(ipAddress);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean isIPv6(String ipAddress){
		try {
			Inet6Address addr = (Inet6Address)Inet6Address.getByName(ipAddress);
			return true;
		} catch (UnknownHostException e) {
			return false;
		}
	}
		
	public LeshanHttpClient(String dstAddr){
		
		if(isIPv4(dstAddr)){
			this.leshanServerAddr = "http://"+dstAddr+":8080";
		}else if(isIPv6(dstAddr)){
			this.leshanServerAddr = "http://["+dstAddr+"]:8080";
		}else{
			throw new IllegalArgumentException("The parameter is not an IP");
		}
		this.httpClient = new HopHTTPClient();
	}
	
	public LinkedList<OMAClient> getClients() throws IOException{
		String result = httpClient.sendGET(leshanServerAddr+"/api/clients", null, HopHTTPClient.CT_APPLICATION_JSON);
//		System.out.println(result);
		
		Type type = new TypeToken<LinkedList<OMAClient>>() {
        }.getType();
		
		return new Gson().fromJson(result, type);
	}
	
	public OMAClient getClientInfo(String omaClientName) throws IOException{
		String result = httpClient.sendGET(leshanServerAddr+"/api/clients/"+omaClientName, null, HopHTTPClient.CT_APPLICATION_JSON);
//		System.out.println(result);
						
		return new Gson().fromJson(result, OMAClient.class);
	}
	
	public List<Resource> getResource(OMAClient omac, ObjectLink link) throws IOException{
		String result = httpClient.sendGET(leshanServerAddr+"/api/clients/"+omac.getEndpoint()+link.getUrl(), null, HopHTTPClient.CT_APPLICATION_JSON);
//		System.out.println(result);						
		GETResourceResponse rr = new Gson().fromJson(result, GETResourceResponse.class);
		
		if(rr.getStatus().compareTo("CONTENT")==0){
			return rr.getContent().getResources();
		}
		return null;	
	}
	
	public boolean setResource(OMAClient omac, ObjectLink link, String newValue) throws Exception{
		Resource rec = new Resource();
		rec.setId(link.getObjectId());
		rec.setValue(newValue);
		String result = httpClient.sendPUT(leshanServerAddr+"/api/clients/"+omac.getEndpoint()+link.getUrl(), new Gson().toJson(rec), HopHTTPClient.CT_APPLICATION_JSON);
//		System.out.println(result);						
		GETResourceResponse rr = new Gson().fromJson(result, GETResourceResponse.class);
		
		if(rr.getStatus().compareTo("CHANGED")==0){
			return true;
		}
		return false;
	}
	
	
	
	
//	public static void main(String[] args) {
//		Gson gson = new Gson();
//		LeshanHttpClient leshanClient = new LeshanHttpClient("aaaa::122");
//		
//		try {
//			for(OMAClient omac : leshanClient.getClients()){
//				System.out.println(omac.getEndpoint());			
//				leshanClient.getResource(omac, omac.getObjectLinks().get(0));			
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

}
