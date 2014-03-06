package com.lib4.picmove.network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.lib4.picmove.entity.ItemProperty;
import com.lib4.picmove.utils.Utils;



public class MultipartConnection {

	
	public String responseString;
	public int responseCode=200;
	
	public void connect(String url,String requestBody) throws IOException{
		
		try{
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);

	    MultipartEntity reqEntity = new MultipartEntity(
	        HttpMultipartMode.BROWSER_COMPATIBLE);

	    reqEntity.addPart("move",
	        new StringBody(requestBody));

	    Iterator mIterator = Utils.Items.keySet().iterator();
		while (mIterator.hasNext()) {
			String key = (String) mIterator.next();
			ItemProperty mItemProperty	=	Utils.Items.get(key);
			FileBody bin = new FileBody(
			        new File(mItemProperty.path));
			    reqEntity.addPart(mItemProperty.path, bin );
		}
	    
	    
	    

	    httppost.setEntity(reqEntity);

	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    
	   
	    HttpEntity resEntity = response.getEntity();

	  
	    if (resEntity != null) {
	    	responseString = EntityUtils.toString(resEntity);
	        System.out.println("PAGE :" + responseString);
	    }

		}catch(IOException e){
			e.printStackTrace(); 
			
		}

	}
}
