package com.example.lazismu.ketek.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

public class RealPathUtil {

	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API19(Context context, Uri uri){
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(uri);

	     // Split at colon, use second item in the array
	     String id = wholeID.split(":")[1];

	     String[] column = { MediaStore.Images.Media.DATA };     

	     // where id is equal to             
	     String sel = MediaStore.Images.Media._ID + "=?";

	     Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
	                               column, sel, new String[]{ id }, null);
	     
	     int columnIndex = cursor.getColumnIndex(column[0]);

	     if (cursor.moveToFirst()) {
	         filePath = cursor.getString(columnIndex);
	     }   

	     cursor.close();
	     
	     return filePath;
	}
	
	
	@SuppressLint("NewApi")
	public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
		  String[] proj = { MediaStore.Images.Media.DATA };
		  String result = null;
		   
		  CursorLoader cursorLoader = new CursorLoader(
				  context, 
		    contentUri, proj, null, null, null);        
		  Cursor cursor = cursorLoader.loadInBackground();
		  
		  if(cursor != null){
		   int column_index = 
		     cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		   cursor.moveToFirst();
		   result = cursor.getString(column_index);
		  }
		    
		  return result;  
	}
	
	public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
		       String[] proj = { MediaStore.Images.Media.DATA };
		       Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		       int column_index
		  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		       cursor.moveToFirst();
		       return cursor.getString(column_index);
	}

	public static void setTextViews(Context context, int sdk, String uriPath, String realPath,
									TextView textView, ImageView imageView){

       /* this.txtSDK.setText("Build.VERSION.SDK_INT: "+sdk);
        this.txtUriPath.setText("URI Path: "+uriPath);
        this.txtRealPath.setText("Real Path: "+realPath);*/

		textView.setText(realPath);
		//getPatch = realPath;

		Uri uriFromPath = Uri.fromFile(new File(realPath));

		// you have two ways to display selected image

		// ( 1 ) imageView.setImageURI(uriFromPath);

		// ( 2 ) imageView.setImageBitmap(bitmap);
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uriFromPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		imageView.setImageBitmap(bitmap);

		Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
		Log.d("HMKCODE", "URI Path:"+uriPath);
		Log.d("HMKCODE", "Real Path: "+realPath);
	}
}