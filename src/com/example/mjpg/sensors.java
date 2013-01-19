package com.example.mjpg;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// for button
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
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
 
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.ImageView;

//for button
import android.widget.*;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


public class sensors extends Activity {
	// timer
	long starttime = 0;
	Handler h2 = new Handler();
	Timer timer = new Timer();
	//public TextView text_ssid;
	//public TextView text_rssi;
	WifiManager wifi;
	
	class secondTask extends TimerTask {
        @Override
        public void run() {
        	sensors.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
            		//text_ssid.setText(SSID(wifi));
            		//text_rssi.setText(String.valueOf(RSSI(wifi)));
            		//text_ssid.invalidate();
            		//text_rssi.invalidate();
                }
            });
        }
    };	   	

   
	protected void start() {
		starttime = System.currentTimeMillis();
		timer = new Timer();
		timer.schedule(new secondTask(), 0, 50);
   	}

	public void stop() {
        timer.cancel();
        timer.purge();		
   	}
	
	
    public String SSID(WifiManager wifi)
    {
		if (wifi != null) {
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null) {
				return info.getSSID();
			}
		}
		
		return "---";
    }

    public int RSSI(WifiManager wifi)
    {
		if (wifi != null) {
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null) {
				return info.getRssi();
			}
		}
		
		return 0;
    }
	
}
