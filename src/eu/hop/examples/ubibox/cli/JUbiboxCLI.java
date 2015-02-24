package eu.hop.examples.ubibox.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.hop.callbacks.BLECallback;
import eu.hop.callbacks.BLEScanCallback;
import eu.hop.devices.BLEDevice;
import eu.hop.leshan.client.LeshanHttpClient;
import eu.hop.leshan.objects.OMAClient;
import eu.hop.leshan.objects.ObjectLink;
import eu.hop.ubibox.Ubibox;

public class JUbiboxCLI {
	
	private static HashMap<Integer, BLEDevice> myDevices;
	private static HashMap<String, OMAClient> omaClients;
	
	public static void main(String[] args) {
		
		
		
		//Creating Ubibox instances
		//This constructor will load the native library
		final Ubibox ubi = new Ubibox();
		
		//Initializes Ubibox
		ubi.init("/dev/ttyACM0", "/dev/ttyACM1",
				"wlan0", "aaaa::121",
				"/64", "192.168.0.2",true, "coaps://[0000:0000:0000:0000:0000:0000:7f00:0001]:5683",null);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				ubi.clean();
			}
		}));
		
		myDevices = new HashMap<Integer, BLEDevice>();
		omaClients = new HashMap<String, OMAClient>();
		
		System.out.println("===============================================");
		System.out.println("============= Ubibox CLI Example ==============");
		System.out.println("===============================================");
		System.out.print("#> ");
		Scanner scan = new Scanner(System.in);
		String command = "";
		while((command = scan.nextLine()).compareTo("exit") != 0){
			
			if(command.compareTo("scan")==0){
				
				//Each Ubibox function needs a Callback where the dynamic library will call when the main procedure ends
				ubi.scan(new BLEScanCallback() {
					
					@Override
					public void onSuccess(BLEDevice[] devices) {
						for(int i = 0;i<devices.length;i++){
							myDevices.put(devices[i].getId(), devices[i]);
						}
					}
					
					@Override
					public void onError() {
						System.out.println("Error scanning devices.");
					}
					
					@Override
					public void onDrop() {
						System.out.println("Request dropped.");						
					}
				});
			}else if(command.startsWith("connect")){
				
				String params[] = command.split(" ");
				int deviceID = Integer.parseInt(params[1]);
				final BLEDevice dev = myDevices.get(deviceID);
				
				//When a device is connected, all OMA procedure is started, so a Leshan server will be needed.
				ubi.connect(dev, new BLECallback() {
					
					@Override
					public void onSuccess() {
						System.out.println("Connected device "+dev);
					}
					
					@Override
					public void onError(int errno) {
						System.out.println("Error connecting device "+dev);						
					}
					
					@Override
					public void onDrop(int errno) {
						System.out.println("Request dropped.");						
					}
				});
				
			}else if(command.startsWith("disconnect")){
				
				String params[] = command.split(" ");
				int deviceID = Integer.parseInt(params[1]);
				final BLEDevice dev = myDevices.get(deviceID);
				
				//Disconnect cleans all processes for a single device
				ubi.disconnect(dev, new BLECallback() {
					
					@Override
					public void onSuccess() {
						System.out.println("Disconnected device "+dev);
					}
					
					@Override
					public void onError(int errno) {
						System.out.println("Error connecting device "+dev);						
					}
					
					@Override
					public void onDrop(int errno) {
						System.out.println("Request dropped.");						
					}
				});
			}else if(command.compareTo("devlist") ==0){
				
				ubi.printDeviceList();
				
			}else if(command.startsWith("getdev")){
				
				String params[] = command.split(" ");
				int deviceID = Integer.parseInt(params[1]);
				BLEDevice dev = myDevices.get(deviceID);
				
				System.out.println(ubi.getDeviceInfo(dev));
				
			}else if(command.startsWith("leshan")){
				
				String params[] = command.split(" ");
				if(params == null || params.length < 3){
					System.out.println("Leshan command usage:");
					System.out.println("\tleshan aaaa::122 clients");
					System.out.println("\tleshan aaaa::122 search \"Client to search\"");
					System.out.println("\tleshan aaaa::122 getResource \"ClientId\" \"ResourceId\"");
					System.out.println("\tleshan aaaa::122 getResource \"ClientId\" \"ResourceId\" \"new value\"");
					System.out.print("#> ");
					continue;
				}
				
				LeshanHttpClient lhttpc = new LeshanHttpClient(params[1]);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				if(params[2].compareTo("clients") == 0){				
					try {
						LinkedList<OMAClient> clients = lhttpc.getClients();
						
						for(OMAClient oc : clients)
							omaClients.put(oc.getEndpoint(), oc);						
						
						System.out.println(gson.toJson(clients));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(params.length == 4 && params[2].compareTo("search") == 0){
					try {
						System.out.println(gson.toJson(lhttpc.getClientInfo(params[3])));
					} catch (IOException e) {
						System.out.println("Not Found");
					}
				}else if(params.length == 5 && params[2].compareTo("getResource")==0){
					String clientid = params[3];
					String resourceid = params[4];
					
					OMAClient selClient = omaClients.get(clientid);
					if(selClient == null){
						System.out.print("#> ");
						System.out.println("Client not found");
						continue;
					}
					
					for(ObjectLink ol : selClient.getObjectLinks()){
						if(ol.getUrl().compareTo(resourceid)==0){
							try {
								System.out.println(gson.toJson(lhttpc.getResource(selClient, ol)));
							} catch (IOException e) {
								System.out.println("Resource not found.");
							}
						}
					}
					
				}else if(params.length == 6 && params[2].compareTo("setResource")==0){
					String clientid = params[3];
					String resourceid = params[4];
					String value = params[5];
					
					OMAClient selClient = omaClients.get(clientid);
					if(selClient == null){
						System.out.print("#> ");
						System.out.println("Client not found");
						continue;
					}
					
					for(ObjectLink ol : selClient.getObjectLinks()){
						if(ol.getUrl().compareTo(resourceid)==0){
							try {
								if(lhttpc.setResource(selClient, ol, value)){
									System.out.println("Update DONE.");
								}else{
									System.out.println("Update ERROR.");
								}							
							} catch (Exception e) {
								System.out.println("Resource not found.");
							}
						}
					}
					
				}else{
					System.out.println("leshan command usage:");
					System.out.println("\tleshan aaaa::122 clients");
					System.out.println("\tleshan aaaa::122 search \"Client to search\"");
					System.out.println("\tleshan aaaa::122 getResource \"ClientId\" \"ResourceId\"");
					System.out.println("\tleshan aaaa::122 getResource \"ClientId\" \"ResourceId\" \"new value\"");
				}
				
			}
			System.out.print("#> ");
		}
		
		ubi.clean();
		
		
	}
	
	

}
