package com.newmoneyrest.ResourceBuilder.NetworkRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.newmoneyrest.ResourceBuilder.error.Logger.ErrorLog;
import com.newmoneyrest.ResourceBuilder.profiler.Timer;

public class Ftp {
	private FTPClient client = new FTPClient();
	private String server;
	private String user;
	private String password;
	private boolean reinitialize = true;
	
	
	public Ftp(String server, String user, String password) {
		this.server = server;
		this.user = user;
		this.password = password;
		Connect();
	}
	
	public void Connect() {
		int port = 21;
		int reply;
		try {
			Close();
			client.connect(server, port);
			client.login(user, password);
			reply = client.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				Close();
				ErrorLog.Write("SEC refused Connection!!!");
			}
		} catch (IOException e) {
			ErrorLog.Write(e.getMessage());
		}
	}
	
	public void Close() {
		try {
			if(client.isConnected()) {
					client.logout();
					client.disconnect();
			}
		} catch (IOException e) {
			ErrorLog.Write(e.getMessage());
		}
	}
	
	public boolean IsConnected() {
		return client.isConnected();
	}

	public StringBuilder Request(String location) {
		// Timer start 
		long start = Timer.start();
		
		StringBuilder value = new StringBuilder("");
		boolean datarecieved = false;
		int runcount = 0;
		
		while(!datarecieved && runcount < 3) {
			try {
				Connect();
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				            
	            InputStream inputStream = client.retrieveFileStream(location);
	            
	            byte[] bytesArray = new byte[4096];
	            int bytesRead = -1;
	            
	            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
	                String text = new String(bytesArray, 0, bytesRead, "ASCII");
	                value.append(text);
	            }
	            
	            if (!client.completePendingCommand()) {
	            	runcount++;
	            	value = new StringBuilder("");
	            	ErrorLog.Write("FTP File Error: Complete Pending Command False");
	            }
	            else {
		            inputStream.close();
					datarecieved = true; 	
	            }
	            Close();
			} catch (Exception e) {
				if(reinitialize) {
					try {
						client.reinitialize();
					} catch(Exception e2) {
						ErrorLog.Write("Server does not support FTP reinitialize function!");
						ErrorLog.Write("Error caught when trying to re-initialize: " + e2.getMessage());
						reinitialize = false;
					}	
				}
				Close();
				runcount++;
				value = new StringBuilder("");
				ErrorLog.Write("FTP File Error: " + e.getMessage());
			}
		}
		
		// End timer
		long stop = Timer.stop();
		Timer.printResults(Timer.getElapsedTime(start, stop));
		
        return value;
	}
	
	public List<String> GetFiles(String location) {
		// Timer start 
		long start = Timer.start();
		
		List<String> value = new ArrayList<String>();
		boolean datarecieved = false;
		int runcount = 0;
		
		while(!datarecieved && runcount < 3) {
			try {
				Connect();
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				            	            
	            FTPFile [] files = client.listFiles(location);
	            
	            for(int i = 0; i < files.length; i++) {
	            	value.add(files[i].getName());
	            }
	            datarecieved = true;
	            Close();
			} catch(Exception e) {
				if(reinitialize) {
					try {
						client.reinitialize();
					} catch(Exception e2) {
						ErrorLog.Write("Server does not support FTP reinitialize function!");
						ErrorLog.Write("Error caught when trying to re-initialize: " + e2.getMessage());
						reinitialize = false;
					}	
				}
				Close();
				runcount++;
				value = new ArrayList<String>();
				ErrorLog.Write("FTP Directory Error: " + e.getMessage());
			}
		}
		
		// End timer
		long stop = Timer.stop();
		Timer.printResults(Timer.getElapsedTime(start, stop));
		
        return value;
	}
}
