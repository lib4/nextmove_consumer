package com.lib4.picmove.httphandler;



public interface HTTPResponseListener{

	public void onSuccess();
	public void onFailure(int failureCode,String message);
	
}
