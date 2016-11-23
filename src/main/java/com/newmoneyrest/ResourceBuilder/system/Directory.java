package com.newmoneyrest.ResourceBuilder.system;

import java.io.File;

public class Directory {
	  public static boolean create(String folderPath){
		  File path = new File(folderPath);
		  boolean fileCreationStatus = false;
		  if(!path.isDirectory()){
			   fileCreationStatus = path.mkdirs();
		  }
		  return fileCreationStatus;
	  }
	  private static String [] getDirectoryNames( String folderPath){
		  String tempFolderPath = folderPath;	  
		  return tempFolderPath.split("/");
	  }
	  public static boolean deleteDirectory(String DirectoryName){
		  File directory = new File(DirectoryName);
		  boolean directoryDeletionStatus = false;
		  if(directory.exists()){
			  try{
				  directoryDeletionStatus = directory.delete();
			  }catch(SecurityException e){
				  System.out.println("Error deleting folder: " + e.getMessage());
			  }
		  }
		  return directoryDeletionStatus;
	  }
	  public static boolean deleteAllDirectories(String folderPath){
		  boolean directoriesDeletionStatus = false;
		  boolean filesDeletionStatus = false;
		  String [] directories = getDirectoryNames(folderPath);
		  for(int i = (directories.length - 1); i >= 0; --i){
			  directoriesDeletionStatus = deleteAllFiles(directories[i]);
			  filesDeletionStatus = deleteDirectory(directories[i]);
		  }
		  return (filesDeletionStatus == true && directoriesDeletionStatus == true);	  
	  }
	  public static boolean deleteAllFiles(String folderPath){
		  File path = new File(folderPath);
		  boolean fileDeletionStatus = false;
		  if(path.isDirectory()){
			  File [] list = path.listFiles();
			  if(list != null){
				  for(int i = 0; i < list.length; ++i){
					  File tempFile = list[i];
					  if(tempFile.isDirectory()){
						  deleteAllFiles(tempFile.toString());
					  }
					  fileDeletionStatus = tempFile.delete();
				  }
			  }
		  }
		  return fileDeletionStatus;
	  }
}
