package com.example.mjpg;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.io.DataOutputStream;

import java.util.List;

public class protocol {
	DataOutputStream dataOutputStream = null;
    public Socket socket;
    String IPaddress;
    int server_port;
    InetAddress serverAddr;
	
    public void start()
    {
		try  {
				serverAddr = InetAddress.getByName(IPaddress);
				Log.d("Client", IPaddress);
				socket = new Socket(serverAddr, server_port);
			 } catch (UnknownHostException e1) {
				Log.d("Client", "stack trace"); 
				e1.printStackTrace();
			 } catch (IOException e1) {
				Log.d("Client", "io eception"); 
				e1.printStackTrace();
			 }		
    }

    public void stop()
    {
		try  {
				socket.close();			 
			 } catch (UnknownHostException e1) {
				Log.d("Client", "stack trace"); 
				e1.printStackTrace();
			 } catch (IOException e1) {
				Log.d("Client", "io eception"); 
				e1.printStackTrace();
			 }		
    }
    
    
	public void test1()
	{
		byte[] buffer2;  
		buffer2 = new byte[10];		
		
		buffer2[0] = 0x88 - 256;
		buffer2[1] = (byte)(20);
		buffer2[2] = 0x8D - 256;
		buffer2[3] = (byte)(20);		
		
		try {
	         //Log.d("Client", "data stream"); 
	         dataOutputStream = new DataOutputStream(socket.getOutputStream());
           
	         //Log.d("Client", "send"); 
	         dataOutputStream.write(buffer2, 0, 4);
	         
	      } catch (UnknownHostException e1) {
	         Log.d("Client", "stack trace"); 
	         e1.printStackTrace();
	      } catch (IOException e1) {
		     Log.d("Client", "io eception"); 
	         e1.printStackTrace();
	    }		
	}

	public void drive(int left, int right)
	{
		byte[] buffer2;  
		buffer2 = new byte[10];		
		
		if(left>0)
			buffer2[0] = 0x88 - 256;
		else
		{
			buffer2[0] = 0x8A - 256;
			left = 0 - left;
		}

		if(left>127) left = 127;
		if(left<-127) left = -127;
		
		// ------------
		
		buffer2[1] = (byte)(left);
		
		if(right>0)
			buffer2[2] = 0x8D - 256;
		else
		{
			buffer2[2] = 0x8E - 256;
			right = 0 - right;

		}

		if(right>127) right = 127;
		if(right<-127) right = -127;
		
		buffer2[3] = (byte)(right);		
		
		try {
	         //Log.d("Client", "data stream"); 
	         dataOutputStream = new DataOutputStream(socket.getOutputStream());
           
	         //Log.d("Client", "send"); 
	         dataOutputStream.write(buffer2, 0, 4);
	         
	      } catch (UnknownHostException e1) {
	         Log.d("Client", "stack trace"); 
	         e1.printStackTrace();
	      } catch (IOException e1) {
		     Log.d("Client", "io eception"); 
	         e1.printStackTrace();
	    }		
	}	
}
