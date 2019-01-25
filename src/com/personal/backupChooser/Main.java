package com.personal.backupChooser;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ShutdownChannelGroupException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

// aplikasi untuk list path folder yang mw di backup
// add path folder
// auto zip per each folder
// return di folder yg di pilih untuk di upload ke google drive

/* reference
 * https://stackoverflow.com/questions/24361690/the-method-showsavedialogcomponent-in-the-type-jfilechooser-is-not-applicable
 * https://stackoverflow.com/questions/7768071/how-to-delete-directory-content-in-java
 * https://www.javatpoint.com/java-swing
**/

//TODO bikin execute able, add sesuatu ke history, ganti path untuk allpath karena ga bs di laod klo jd jar
//https://stackoverflow.com/questions/394616/running-jar-file-on-windows
//https://www.wikihow.com/Create-an-Executable-File-from-Eclipse

public class Main implements ActionListener {
	
	private Frame mainFrame;
	   private Label headerLabel;
	   private Label statusLabel;
	   private Panel controlPanel;

	   public Main(List<String> allpath){
	      prepareGUI(allpath);
	   }

	   static SettingLoader setLoad;
	   public static void main(String[] args){
		   
		   // TODO
		   // sudah bisa copy file, copy folder
		   // next version history
		   // running di backround/loading bar
		   // jd in app independent
		   
		   setLoad = new SettingLoader();
		   Main test = new Main(setLoad.getMlistAllPath());
		   
//		   setLoad.getMlistFileList().forEach(System.out::println);
//		   setLoad.getMlistBackupDest().forEach(System.out::println);
//		   
		   

	   }
	   
	   final JFileChooser fc1 = new JFileChooser();
	   final JFileChooser fc2 = new JFileChooser();
	   final JFileChooser fc3 = new JFileChooser();
	   final JFileChooser fc4 = new JFileChooser();
	   public void actionPerformed(ActionEvent e) {

		   FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		   fc1.setFileFilter(filter);
		   fc2.setFileFilter(filter);
		   fc3.setFileFilter(filter);
		   fc4.setFileFilter(filter);
		   
	        //Handle open button action.
	        if (e.getSource() == btnFileList) {
	            int returnVal = fc1.showOpenDialog(fc1);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc1.getSelectedFile();
	                String path = file.getPath();
	                tvFilelistPath.setText(path);
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            }
	            //log.setCaretPosition(log.getDocument().getLength());
	        }
	        else if(e.getSource() == btnDestination)
	        {
	            int returnVal = fc2.showOpenDialog(fc2);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc2.getSelectedFile();
	                String path = file.getPath();
	                tvDestinationPath.setText(path);
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            }
	            //log.setCaretPosition(log.getDocument().getLength());
	        }
	        else if(e.getSource() == btnHistory)
	        {
	            int returnVal = fc3.showOpenDialog(fc3);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc3.getSelectedFile();
	                String path = file.getPath();
	                tvHistoryPath.setText(path);
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            }
	            //log.setCaretPosition(log.getDocument().getLength());
	        }
	        else if(e.getSource() == btnVersion)
	        {
	            int returnVal = fc4.showOpenDialog(fc4);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc4.getSelectedFile();
	                String path = file.getPath();
	                tvVersionPath.setText(path);
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            }
	            //log.setCaretPosition(log.getDocument().getLength());
	        }
	        else if(e.getSource() == btnStartProgram)
	        {
	        	List<String> newPath  = new ArrayList<>();
	        	newPath.add(tvFilelistPath.getText());
	        	newPath.add(tvDestinationPath.getText());
	        	newPath.add(tvHistoryPath.getText());
	        	newPath.add(tvVersionPath.getText());
	        	newPath.forEach(System.out::println);
	        	
	        	//rewrite old path
	        	setLoad.rewrite(setLoad.getAllPath(), newPath);
	            
	        	LibBackup lib = new LibBackup();
	        	
	        	
//	            //empty destination folder ## optional          
//				lib.deleteFolder(new File(setLoad.getMlistBackupDest().get(0)));
				
				String versionPath = "v"+setLoad.getMlistVersion().get(0)+"_"+lib.getDateTime();
				for(int i=0;i<setLoad.getMlistFileList().size();i++)
				{
					File source = new File( setLoad.getMlistFileList().get(i));
					lib.generalCopy(source,new File(setLoad.getMlistBackupDest().get(0)+"/"+versionPath));
				}
				
				
				//update versi backup
				List<String> newVersion = new ArrayList<>();
				int version = Integer.parseInt(setLoad.getMlistVersion().get(0))+1;
				newVersion.add(String.valueOf(version));
				setLoad.rewrite(setLoad.getVersionFilePath(), newVersion);
				
				setLoad.reloadAll();
				
				//tvInfo.append(str);
				tvInfo.append("File Name : \n"+versionPath);
	        }
	   }

	   JButton btnFileList,btnDestination, btnHistory,btnVersion,btnStartProgram;
	   JTextField tvFilelistPath,tvDestinationPath, tvHistoryPath,tvVersionPath;
	   JTextArea tvInfo;
	   private void prepareGUI(List<String> allpath){
		   JFrame mainFrame = new JFrame();//creating instance of JFrame 
		   mainFrame.setTitle("Backup file collector");
		   
		   //label app name
		   JLabel lblAppName = new JLabel("## Backup File Chooser ##");  
		   lblAppName.setBounds(50,50, 100,30);
		   
		   //label Path name
		   JLabel lblPath1 = new JLabel("File List");  
		   lblPath1.setBounds(50,50, 150,30);
		   JLabel lblPath2 = new JLabel("Backup Destination");  
		   lblPath2.setBounds(50,50, 150,30);
		   JLabel lblPath3 = new JLabel("History");  
		   lblPath3.setBounds(50,50, 150,30);
		   JLabel lblPath4 = new JLabel("Backup Version");  
		   lblPath4.setBounds(50,50, 150,30);

	          
		   //button
		   btnFileList = new JButton("Browse");//creating instance of JButton  
		   btnDestination = new JButton("Browse");//creating instance of JButton  
		   btnHistory = new JButton("Browse");//creating instance of JButton  
		   btnVersion = new JButton("Browse");//creating instance of JButton  
		   
		   int btnx = 130, btny = 100, btnw= 100, btnh=30;
		   btnFileList.setBounds(btnx,btny,btnw, btnh);//x axis, y axis, width, height  
		   btnDestination.setBounds(btnx,btny,btnw, btnh);//x axis, y axis, width, height
		   btnHistory.setBounds(btnx,btny,btnw, btnh);//x axis, y axis, width, height   
		   btnVersion.setBounds(btnx,btny,btnw, btnh);//x axis, y axis, width, height   
		   
		   
		   //textview
		   tvFilelistPath = new JTextField();  
		   tvDestinationPath = new JTextField();  
		   tvHistoryPath = new JTextField();  
		   tvVersionPath = new JTextField();
		   tvInfo = new JTextArea();
		   tvFilelistPath.setEditable(false);
		   tvDestinationPath.setEditable(false);
		   tvHistoryPath.setEditable(false);
		   tvVersionPath.setEditable(false);
		   tvInfo.setEditable(false);
		   
		   int tvx = 50, tvy=50, tvw=200, tvh=20;
		   tvFilelistPath.setBounds(tvx,tvy,tvw,tvh);  
		   tvDestinationPath.setBounds(tvx,tvy,tvw,tvh);  
		   tvHistoryPath.setBounds(tvx,tvy,tvw,tvh);    
		   tvVersionPath.setBounds(tvx,tvy,tvw,tvh);  
		   tvInfo.setBounds(tvx,tvy,tvw+100,100);  
		   
		   //button start
		   btnStartProgram = new JButton("Run");
		   btnStartProgram.setBounds(btnx,btny,btnw, btnh);
		   
		   //listener
		   btnFileList.addActionListener(this);
		   btnDestination.addActionListener(this);
		   btnHistory.addActionListener(this);
		   btnVersion.addActionListener(this);
		   btnStartProgram.addActionListener(this);
		   
		   
		   //add to frame     
		   mainFrame.add(lblAppName);
		   mainFrame.add(lblPath1);
		   mainFrame.add(lblPath2);
		   mainFrame.add(lblPath3);
		   mainFrame.add(lblPath4);
		   
		   mainFrame.add(btnFileList);//adding button in JFrame  
		   mainFrame.add(btnDestination);//adding button in JFrame  
		   mainFrame.add(btnHistory);//adding button in JFrame  
		   mainFrame.add(btnVersion);//adding button in JFrame  
		   
		   mainFrame.add(tvFilelistPath);
		   mainFrame.add(tvDestinationPath);
		   mainFrame.add(tvHistoryPath);
		   mainFrame.add(tvVersionPath);
		   mainFrame.add(tvInfo);
		   
		   mainFrame.add(btnStartProgram);
		   
		   
		   
		   //set position
		   
		   lblAppName.setLocation(130, 10);
		   int lblgap = 50;
		   int lbl_y = 30;
		   lblPath1.setLocation(30,lbl_y);
		   lblPath2.setLocation(30,lbl_y+lblgap);
		   lblPath3.setLocation(30,lbl_y+lblgap*2);
		   lblPath4.setLocation(30,lbl_y+lblgap*3);
		   
		   int btn_x_position = 250;
		   int btn_y_position = 50;
		   int btn_gap = 50;
		   btnFileList.setLocation(btn_x_position, btn_y_position);
		   btnDestination.setLocation(btn_x_position, btn_y_position+btn_gap);
		   btnHistory.setLocation(btn_x_position, btn_y_position+btn_gap*2);
		   btnVersion.setLocation(btn_x_position, btn_y_position+btn_gap*3);
		   
		   int tv_x_position = 30;
		   int tv_y_position = 55;
		   int tv_gap = 50;
		   tvFilelistPath.setLocation(tv_x_position, tv_y_position);
		   tvDestinationPath.setLocation(tv_x_position, tv_y_position+tv_gap);
		   tvHistoryPath.setLocation(tv_x_position, tv_y_position+tv_gap*2);
		   tvVersionPath.setLocation(tv_x_position, tv_y_position+tv_gap*3);
		   
		   
		   btnStartProgram.setLocation(150,270);
		   
		   tvInfo.setLocation(50,320);
		   tvInfo.setText("== INFO ==\n");
		   
		   //setText
		   lblAppName.setSize(200,30);
		 
		   if(allpath.size() > 0)
		   {
			   tvFilelistPath.setText(allpath.get(0)); 
			   tvDestinationPath.setText(allpath.get(1)); 
			   tvHistoryPath.setText(allpath.get(2));   
			   tvVersionPath.setText(allpath.get(3));  
		   }

		   
		   //set frame
		   mainFrame.setSize(400,500);//400 width and 500 height  
		   mainFrame.setLayout(null);//using no layout managers  
		   mainFrame.setVisible(true);//making the frame visible  
	   }

}
