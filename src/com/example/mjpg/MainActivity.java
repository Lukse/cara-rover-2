package com.example.mjpg; 

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import android.widget.Button;
import android.widget.LinearLayout;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;





import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

//for button
import android.widget.*;
import android.util.Log;
import android.view.View;
import android.view.View.*;

//test
import android.widget.*;

//timer
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.EditText;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView; 

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;



public class MainActivity extends Activity implements SensorEventListener{
    private static final String TAG = "MjpegActivity";

    private MjpegView mv;
    ImageView drawingImageView;
    private SensorManager mySensorManager; 
    private Sensor accSensor;
    float x, y, z;
    float touch_x, touch_y, touch_active;

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
        
        //sample public cam
        //String URL = "http://trackfield.webcam.oregonstate.edu/axis-cgi/mjpg/video.cgi?resolution=800x600&amp%3bdummy=1333689998337";
        String URL = "http://192.168.0.110:8080/?action=stream";

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    drawingImageView = (ImageView) this.findViewById(R.layout.activity_main);
        
        mv = new MjpegView(this);
        setContentView(mv);        

    	WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
    	final sensors sensor = new sensors();
    	sensor.wifi = wifi;
    	sensor.start();
        
    	final protocol wheels = new protocol();
        wheels.IPaddress = "192.168.0.110";
        wheels.server_port = 2002;
    	wheels.start();

    	mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accSensor = mySensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        mySensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        

        mv.setOnTouchListener(
	    //drawingImageView.setOnTouchListener(
        		new RelativeLayout.OnTouchListener() {
        			public boolean onTouch(View v, MotionEvent m) {
        				handleTouch(m);
        				int drive = (int)(-1*(touch_y-300)/2);
        				int turn = (int)(x*10);
        				wheels.drive(drive+turn, drive-turn);
        				
        			    return true;
        			}
        		} 
        	);



        new DoRead().execute(URL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("TestingArea","onAccuracyChanged: " + sensor + " has accuracy: " + accuracy);
    }        
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	TextView text_fps;
		//text_fps = (TextView) findViewById(R.id.text_fps);
    	
    	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
    	{
//    		text_fps.setText("x = " + event.values[0] + "\ny = " + event.values[1] + "\nz = " + event.values[2]);
    		
    		x = event.values[0];
    		y = event.values[1];
    		z = event.values[2];
    	}
    }
    
    void handleTouch(MotionEvent m)
    {
//        	TextView textView1 = (TextView)findViewById(R.id.textView4);
//        	TextView textView2 = (TextView)findViewById(R.id.textView5);
        	
        	int pointerCount = m.getPointerCount();
        	
        	int i = 0;
//        	for (int i = 0; i < pointerCount; i++)
        	{
        		int x = (int) m.getX(i);
        		int y = (int) m.getY(i);    		
        		int id = m.getPointerId(i);
//        		int action = m.getActionMasked();
//        		int actionIndex = m.getActionIndex();
//        		String actionString;
  /*      		
        		switch (action)
        		{
        			case MotionEvent.ACTION_DOWN:
        				actionString = "DOWN";
        				break;
        			case MotionEvent.ACTION_UP:
        				actionString = "UP";
        				break;	
        			case MotionEvent.ACTION_POINTER_DOWN:
        				actionString = "PNTR DOWN";
        				break;
        			case MotionEvent.ACTION_POINTER_UP:
            			actionString = "PNTR UP";
            			break;
        			case MotionEvent.ACTION_MOVE:
        				actionString = "MOVE";
        				break;
        			default:
        				actionString = "";
        		}
*/
        		touch_x = x;
        		touch_y = y;
//        		touch_active = action;
        		
        		
//      		String touchStatus = "Action: " + actionString + " Index: " + actionIndex + " ID: " + id + " X: " + x + " Y: " + y;
        		
//        		if (id == 0)
//        			textView1.setText(touchStatus);
//        		else 
//        			textView2.setText(touchStatus);
        }    
    }
    
    
    public void onPause() {
        super.onPause();
        mv.stopPlayback();
    }

    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
        protected MjpegInputStream doInBackground(String... url) {
            //TODO: if camera has authentication deal with it and don't just not work
            HttpResponse res = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();     
            Log.d(TAG, "1. Sending http request");
            try {
                res = httpclient.execute(new HttpGet(URI.create(url[0])));
                Log.d(TAG, "2. Request finished, status = " + res.getStatusLine().getStatusCode());
                if(res.getStatusLine().getStatusCode()==401){
                    //You must turn off camera User Access Control before this will work
                    return null;
                }
                return new MjpegInputStream(res.getEntity().getContent());  
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.d(TAG, "Request failed-ClientProtocolException", e);
                //Error connecting to camera
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Request failed-IOException ", e);
                //Error connecting to camera
            }

            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            mv.setSource(result);
            //mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            mv.setDisplayMode(MjpegView.SIZE_STANDARD);
            mv.showFps(true);
        }
    }
}
