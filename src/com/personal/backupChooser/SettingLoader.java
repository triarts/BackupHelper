package com.personal.backupChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SettingLoader {
	
	String allPath = "";
	String historyFilePath = "";
	String fileListFilePath = "";
	String backupDestFilePath = "";
	String versionFilePath = "";
	
	List<String> mlistAllPath = new ArrayList<>();
	List<String> mlistHistory = new ArrayList<>();
	List<String> mlistFileList = new ArrayList<>();
	List<String> mlistBackupDest = new ArrayList<>();
	List<String> mlistVersion = new ArrayList<>();

	public SettingLoader(String allpath) {
		this.allPath = allpath;
		// TODO Auto-generated method stub
		
//		String path = "C:\\Documents and Settings\\Manoj\\Desktop";
//		path = path.replace("\\", "/");
//		// or
//		path = path.replaceAll("\\\\", "/");
		
		
		reloadAll();

		//mlistFileList.forEach(System.out::println);
	}
	
	public List<String> readTxtFile (String filepath)
	{
		List<String> returnList = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
	        stream.forEach(returnList::add);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnList;
	}
	
//	public List<String> readTxtFile (File fileSource)
//	{
//		List<String> returnList = new ArrayList<>();
//		
//		try (Stream<String> stream = Files.lines(Paths.get(fileSource.getAbsolutePath()))) {
//	        stream.forEach(returnList::add);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return returnList;
//	}
//	
	public void rewrite(String txt_filepath,List<String> newListString)
	{
        Path file2 = Paths.get(txt_filepath);
        try {
			Files.write(file2, newListString, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void reloadVersion()
	{
		mlistVersion = readTxtFile(versionFilePath);
	}
	
	public void reloadAll()
	{
//		URL urlAllPath = getClass().getResource("allPath.txt");
//		File fileAllPath = new File(urlAllPath.getPath());
		mlistAllPath = readTxtFile(allPath);
		
		fileListFilePath = mlistAllPath.get(0);
		backupDestFilePath = mlistAllPath.get(1);
		historyFilePath = mlistAllPath.get(2);
		versionFilePath = mlistAllPath.get(3);
		
		mlistFileList = readTxtFile(fileListFilePath);
		mlistBackupDest = readTxtFile(backupDestFilePath);
		mlistHistory = readTxtFile(historyFilePath);
		mlistVersion = readTxtFile(versionFilePath);
	}
	
	public void setAllPath(String allPath) {
		this.allPath = allPath;
	}
	
	public List<String> getMlistBackupDest() {
		return mlistBackupDest;
	}
	
	public List<String> getMlistFileList() {
		return mlistFileList;
	}
	
	public List<String> getMlistHistory() {
		return mlistHistory;
	}
	
	public List<String> getMlistVersion() {
		return mlistVersion;
	}
	
	public List<String> getMlistAllPath() {
		return mlistAllPath;
	}
	
	public String getAllPath() {
		return allPath;
	}
	public String getVersionFilePath() {
		return versionFilePath;
	}
	
	public String getHistoryFilePath() {
		return historyFilePath;
	}
	
	public String getFileListFilePath() {
		return fileListFilePath;
	}
	
	public String getBackupDestFilePath() {
		return backupDestFilePath;
	}
	
}
