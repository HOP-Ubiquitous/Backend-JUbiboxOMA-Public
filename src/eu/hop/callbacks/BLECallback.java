package eu.hop.callbacks;

public abstract class BLECallback {

	public abstract void onSuccess();
	public abstract void onError(int errno);
	public abstract void onDrop(int errno);
	
	
}
