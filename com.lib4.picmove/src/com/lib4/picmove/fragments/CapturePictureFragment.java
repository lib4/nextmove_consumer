package com.lib4.picmove.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appbase.androidquery.AQuery;
import com.lib4.picmove.CameraPreview;
import com.lib4.picmove.R;
import com.lib4.picmove.entity.ItemProperty;
import com.lib4.picmove.utils.Utils;

public class CapturePictureFragment extends BaseFragment {

	private Camera mCamera;
	private CameraPreview mPreview;

	LinearLayout mLinearLayout;
	ImageView captureImage;
	String TAG = CapturePictureFragment.class.getCanonicalName();

	LinearLayout mLayout;
	File mFile[];
	LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.capturepicture_fragment, container, false);

		mInflater = getActivity().getLayoutInflater();
		// Create an instance of Camera
		mCamera = getCameraInstance();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(getActivity(), mCamera);
		FrameLayout preview = (FrameLayout) mLinearLayout
				.findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		captureImage = (ImageView) mLinearLayout
				.findViewById(R.id.capture_image);
		captureImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mCamera.takePicture(null, null, mPicture);

			}
		});

		mLayout = (LinearLayout) mLinearLayout
				.findViewById(R.id.imagecontainer);

		File Folder = new File(getActivity().getFilesDir() + "/"
				+ Utils.CURRENT_ACTIVE_FOLDER);
		if (!Folder.exists())
			Folder.mkdir();
		else
			mFile = Folder.listFiles();

		if (mFile != null && mFile.length > 0) {

			for (File file : mFile) {
				addImageView(file);
			}
		}

		return mLinearLayout;
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			String filename	=	UUID.randomUUID().toString()+".jpg";
			String folderPath	=	getActivity().getFilesDir() + "/"
					+ Utils.CURRENT_ACTIVE_FOLDER;
			File pictureFile = new File(folderPath,filename);
			if (pictureFile == null) {
				Log.d(TAG,
						"Error creating media file, check storage permissions: ");
				return;
			}

			Log.e("Picture File ", " " + pictureFile.getAbsolutePath());

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			}
			addImageView(pictureFile);
			addEntryToPropertyMap(filename,folderPath);
			mPreview.resetPreview(null);
			
		}
	};
	
	private void addEntryToPropertyMap(String fileName,String folderPath){
		
		ItemProperty mItemProperty	=	 new ItemProperty();
		mItemProperty.fitInElevator	=	true;
		mItemProperty.requiresDiassembly	=	false;
		mItemProperty.path		=	folderPath+"/"+fileName;
		Utils.Items.put(fileName, mItemProperty);
	}

	private void addImageView(File pictureFile) {

		ImageView mImageView = (ImageView) mInflater.inflate(
				R.layout.horizonalscroll_item, null);
		mImageView.setOnClickListener(TileClickLister);
		mImageView.setTag(pictureFile.getName());
		Log.e("TAG  "," "+pictureFile.getName());
		AQuery aq = new AQuery(mImageView);
		aq.id(mImageView).image(pictureFile, 200);
		mLayout.addView(mImageView);
	}

	public OnClickListener TileClickLister = new OnClickListener() {

		@Override
		public void onClick(final View v) {

			View checkBoxView = View.inflate(getActivity(), R.layout.checkbox,
					null);

			final AlertDialog builder = new AlertDialog.Builder(getActivity())
					.create();

			builder.setTitle(null);
			builder.setMessage(null);
			builder.setView(checkBoxView);
			builder.setCancelable(false);

			builder.setButton(DialogInterface.BUTTON_POSITIVE, "Done",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							builder.cancel();
						}
					});

			CheckBox requireDiassemblyCheckBox = (CheckBox) checkBoxView
					.findViewById(R.id.requiresdiassebly_chkbox);
			requireDiassemblyCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							
							ItemProperty mItemProperty	=	Utils.Items.get((String)v.getTag());
							
							if(isChecked){
								mItemProperty.requiresDiassembly	=	true;
							}else{
								mItemProperty.requiresDiassembly	=	false;
							}
							
							// Save to shared preferences
						}
					});

			CheckBox fitInElevatorCheckBox = (CheckBox) checkBoxView
					.findViewById(R.id.fitinelevator_chkbox);
			fitInElevatorCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							ItemProperty mItemProperty	=	Utils.Items.get((String)v.getTag());
							
							if(isChecked){
								mItemProperty.fitInElevator	=	true;
							}else{
								mItemProperty.fitInElevator	=	false;
							}
						}
					});

			Button removeButton = (Button) checkBoxView
					.findViewById(R.id.delete_btn);
			removeButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					builder.cancel();
					deleteAlert();
				}
			});
			
			
			ItemProperty mItemProperty	=	Utils.Items.get((String)v.getTag());
			if(mItemProperty!=null){
				
				if(mItemProperty.fitInElevator){
					fitInElevatorCheckBox.setChecked(true);
					
				}else{
					fitInElevatorCheckBox.setChecked(false);
				}
				
				if(mItemProperty.requiresDiassembly){
					requireDiassemblyCheckBox.setChecked(true);
					
				}else{
					requireDiassemblyCheckBox.setChecked(false);
				}
			}
			
			
			
			builder.show();

		}
	};

	private void deleteAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(null);
		builder.setMessage("You want to remove the item?")
				.setCancelable(true)
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				})
				.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						}).show();
	}

}
