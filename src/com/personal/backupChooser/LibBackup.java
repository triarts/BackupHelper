package com.personal.backupChooser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class LibBackup {
	
	 public LibBackup() {
		// TODO Auto-generated constructor stub
		 
	}
	 
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
		Date date = new Date();
		String val = dateFormat.format(date);
		System.out.println(val);
		return val;
	}
	 
	 public boolean generalCopy(File Source, File Dest)
	 {
		 boolean resultFlag = true;
		 if(Source.exists())
		 {
			 if(Source.isDirectory())
			 {
				 try {
					copyDirectory(Source.getPath(), Dest.getPath()+"/"+Source.getName());
				} catch (IOException e) {
					resultFlag = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else if(Source.isFile())
			 {
				 try {
					copyFile(Source, new File(Dest.getPath()+"/"+Source.getName()) );
				} catch (IOException e) {
					resultFlag = false;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		 else {
			 resultFlag = false;
		 }

		 
		 return resultFlag;
	 }
	 
	 public void copyDirectory(String sourcePath, String destPath) throws IOException {
		 String source = sourcePath;
		 File srcDir = new File(source);

		 String destination = destPath;
		 File destDir = new File(destination);

		 FileUtils.copyDirectory(srcDir, destDir);
	}
	 
	 public void copyFile(File source, File dest) throws IOException {
		    FileUtils.copyFile(source, dest);
		}
	 
	 public static void deleteFolder(File folder) {
		    File[] files = folder.listFiles();
		    if(files!=null) { //some JVMs return null for empty dirs
		        for(File f: files) {
		            if(f.isDirectory()) {
		                deleteFolder(f);
		            } else {
		                f.delete();
		            }
		        }
		    }
		    folder.delete();
		}
}
