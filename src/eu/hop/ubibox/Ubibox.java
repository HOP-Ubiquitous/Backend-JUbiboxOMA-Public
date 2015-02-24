package eu.hop.ubibox;

import java.io.IOException;

import eu.hop.callbacks.BLECallback;
import eu.hop.callbacks.BLEScanCallback;
import eu.hop.devices.BLEDevice;
import eu.hop.utils.NativeUtils;

public class Ubibox {
	
	static {
		try{
	    System.loadLibrary("NativeUbibox64");
		} catch (UnsatisfiedLinkError e) {
	        try {
	            NativeUtils.loadLibraryFromJar("/libNativeUbibox64.so"); // during runtime .so within .JAR
	        } catch (IOException e1) {
	            throw new RuntimeException(e1);
	        }
	    }
	}
	
	
	/**
	 * 
	 * Initializes Ubibox Driver
	 * 
	 * @param mainSerialPort					Serial port where the BLE master is
	 * @param auxSerialPort						Unused for the moment
	 * @param networkInterface					Interface to bind
	 * @param networkUbiboxAddressIPv6			IPv6 network address
	 * @param networkUbiboxAddressMaskIPv6		IPv6 mask
	 * @param networkUbiboxAddressIPv4			IPv4 network address
	 * @param autoconnect						Set if the gateway must to autoconnect BLE
	 * @param omaserv							Set if the gateway must to stablish the connection with a specific OMA server
	 * @param macFilter 						MAC filter -> String with the next format "01:02:03:04:05:05" or for more than one mac "01:02:03:04:05:05|01:02:03:04:05:06"
	 * @return
	 */
	public native boolean init(String mainSerialPort, String auxSerialPort,
			String networkInterface, String networkUbiboxAddressIPv6,
			String networkUbiboxAddressMaskIPv6, String networkUbiboxAddressIPv4,
			boolean autoconnect, String omaserv, String macFilter);
	
	public native void scan(BLEScanCallback callback);
	
	public native void connect(BLEDevice ble, BLECallback callback);
	
	public native void disconnect(BLEDevice ble, BLECallback callback);
	
	public native void printDeviceList();
	
	public native String getDeviceInfo(BLEDevice ble);
	
	public native void clean();

}
