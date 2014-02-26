package com.lib4.picmove.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lib4.picmove.CameraPreview;
import com.lib4.picmove.R;

public class CapturePictureFragment extends BaseFragment{
	

    private Camera mCamera;
    private CameraPreview mPreview;

	
	LinearLayout mLinearLayout;
	ImageView captureImage;
	String TAG	=	CapturePictureFragment.class.getCanonicalName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.capturepicture_fragment, container, false);
		
		
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(getActivity(), mCamera);
        FrameLayout preview = (FrameLayout) mLinearLayout.findViewById(R.id.camera_preview);
        preview.addView(mPreview);
		captureImage	=	(ImageView) mLinearLayout.findViewById(R.id.capture_image);
		captureImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				  mCamera.takePicture(null, null, mPicture);

				
			}
		});
		return mLinearLayout;
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {

	        File pictureFile  = new File(getActivity().getFilesDir(), "test.jpg");
	        if (pictureFile == null){
	            Log.d(TAG, "Error creating media file, check storage permissions: ");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	    }
	};
}
