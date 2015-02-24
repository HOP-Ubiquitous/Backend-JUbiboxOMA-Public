package eu.hop.devices;

import java.net.Inet6Address;
import java.net.UnknownHostException;

public class BLEDevice {
	
	private int id;
	
	public BLEDevice(int id) {
		super();
		this.id = id;		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){		
		return "\n==============\n"+
				"\tID: "+id+"\n"+
				"==============\n";
	}

}
