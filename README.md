JUbiboxOMA
==========

Provides a developer interface for set up a BLE ipv6 network that registers in a Leshan server.

More Info: https://github.com/plopezm/JUbiboxOMA/wiki/1.-Home

Installation
============

This software requires linux OS and root user. Also it is required JRE 1.8 to execute this software.

Go to bin/ folder and executes the .jar for launch the test


Execution
=========

The bin/ directory contains an example java executable. When JUbiboxOMA.jar is executed, a simple command line will appears. By default the JUbiboxOMA.jar it is executed in autoconnect mode. The default OMA server destination will be localhost.

- Usage: sudo java -jar JUbiboxOMA.jar "my ipv4 interface address" ["destination OMA server address"]
- Example: 
	- sudo java -jar JUbiboxOMA.jar 192.168.0.2
	- sudo java -jar JUbiboxOMA.jar 192.168.0.2 188.34.32.1

The example CLI provides the following functionalities:

- scan: Launchs a scan to discover the available BLE devices. 
- connect <dev_id>: Connects with the selected device.
- disconnect <dev_id>: Disconnects selected device
- leshan http client: Type leshan to see the possibilities.
- exit: Close CLI.

NOTE: If autoconnect is enabled, the JUbiboxOMA is set as automatic router, so the commands will be useless except "leshan" commands.

Binary source code
==================

Go to src/eu/hop/examples/ubibox/cli/JUbiboxCLI.java to see an example of use of JUbiboxOMA

Ubibox class explanation
========================

Ubibox is the gateway that unifies a IPv6 network with a Bluetooth network providing a IPv6 interface to each device. This library allows to connect BLE devices with a OMA implementation to a IPv6 network. 

Below functions are displayed with a short description.

Init method
===========

Initializes gateway with the parameters. This method will be called at statup. The meaning of the parameters are as follows:

- mainSerialPort: String with the device port (/dev/ttyACM0)
- auxSerialPort: Reserved.
- networkInterface: The network interface where the gateway will bind.
- networkUbiboxAddressIPv6: The gateway network address (IPv6).
- networkUbiboxAddressMaskIPv6: The gateway network mask.
- networkUbiboxAddressMaskIPv4: Network ipv4 address to bind.
- autoconnect: If the gateway supports autoconnect (true | false).
- omaserv: Set if the gateway must to stablish the connection with a specific OMA server (null for default server)
- macFilter: Mac whitelist. (null for all)

```	
	public native boolean init(String mainSerialPort, String auxSerialPort,
			String networkInterface, String networkUbiboxAddressIPv6,
			String networkUbiboxAddressMaskIPv6, String networkUbiboxAddressIPv4,
			boolean autoconnect, String omaserv, String macFilter);
```	
On example class it is showed how to use this method.


Scan method
===========

Scans to find BLE devices.

- callback: This object contains 3 methods that will be called by the driver depending of operation output.

```
	public native void scan(BLEScanCallback callback);
```

The BLEScanCallback is an abstract class wich is defined as follows.
```
	public abstract class BLEScanCallback {
		public abstract void onSuccess(BLEDevice[] devices);
		public abstract void onError();
		public abstract void onDrop();
	}

```
When scan process is completed, an array of BLE devices identifiers are returned as parameter.


Connect method
==============

When a device is discovered the next step is connect it. When a device is connected, the driver checks if is a OMA GlowbalIPv6 compatible device. If the device is compatible, the gateway connects the device to the network and sets the IPv6 address on device.

```
	public native void connect(BLEDevice ble, BLECallback callback);
```
This method requires two parameters, the BLE device identifier and a BLECallback. BLECallback object definition is as follows:
```
	public abstract class BLECallback {
		public abstract void onSuccess();
		public abstract void onError(int errno);
		public abstract void onDrop(int errno);	
	}
```
When the address is set in the ble device, OMA bootstrap will begin in order to register and be available for future requests.

Accessing Leshan resources using Ubibox
=======================================

Once the device is connected and registered on OMA server, it is possible access to sensor data using the API with OMA server address as parameter. 
```
LeshanHttpClient lhttpc = new LeshanHttpClient(String destAddr);
```
* Get Leshan registered clients.
```
LinkedList<OMAClient> clients = lhttpc.getClients();
```
* Get Leshan client information by name.
```
OMAClient client = lhttpc.getClientInfo(String clientName);
```
* Get Leshan resources with its values.
```
List<Resource> resources = lhttpc.getResource(OMAClient omac, ObjectLink link);
```
* Set resource value
```
lhttpc.setResource(OMAClient omac, ObjectLink link, String newValue);
```

Disconnect Method
=================

Removes a BLE device of the IPv6 network. Again, BLE identifier and a callback will be necessary.

```	
	public native void disconnect(BLEDevice ble, BLECallback callback);
```

PrintDeviceList Method
======================

Prints the available devices and their characteristics. This method is usefull for a Command Line Interface.

```	
	public native void printDeviceList();
```	

GetDeviceInfo Method
====================

This method returns a JSON representation of a BLEDevice with its characteristics.

```
	public native String getDeviceInfo(BLEDevice ble);
```

Clean Method
============

This method must to be called before shutdown in order to clean all memory allocations.

```	
	public native void clean();
```

Version History
===============

v0.2

- Leshan Http client added to library.

v0.1 Initial Realease





