package com.lib4.picmove.httphandler;



public interface HTTPResponseListener{

	public void onSuccess(String message);
	public void onFailure(int failureCode,String message);
	
}
