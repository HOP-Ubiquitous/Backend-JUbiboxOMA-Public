package eu.hop.callbacks;

import eu.hop.devices.BLEDevice;

public abstract class BLEScanCallback {
	public abstract void onSuccess(BLEDevice[] devices);
	public abstract void onError();
	public abstract void onDrop();
}
