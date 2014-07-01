/**
 * �ļ����ļ��еļ򵥴���
 * @author jiangxin
 */
package edu.jiangxin.encode;

import java.io.*;

public class FileProcess {
	final static int BUFFERSIZE = 1024*5; //The size of the buffer

	public static void main(String args[]) throws IOException {
		//copyDirectory("test1", "test2");
		//copyFile("test1/test.txt","test2/test.txt");
	}
	
	/**
	 * �����ļ�
	 * @param srcFileString
	 * @param desFileString
	 * @throws IOException
	 */
	public static void copyFile(String srcFileString, String desFileString) throws IOException {
		File srcFileFile = new File(srcFileString);
		File desFileFile = new File(desFileString);
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcFileFile));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desFileFile));

		byte[] buffer = new byte[BUFFERSIZE]; // ��������
		int len;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.flush(); // ˢ�´˻���������
		in.close();
		out.close();
		System.out.println("Success to copy " + srcFileString + " to " + desFileString);
	}


	/**
	 * �����ļ���
	 * @param srcDirString
	 * @param desDirString
	 * @throws IOException
	 */
	public static void copyDirectory(String srcDirString, String desDirString) throws IOException {
		File srcDirFile = new File(srcDirString);
		File desDirFile = new File(desDirString);
		if(!desDirFile.exists()) {
			desDirFile.mkdirs(); // �½�Ŀ��Ŀ¼
		}
		
		File[] files = srcDirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String srcFileTemp = files[i].getAbsolutePath();
				String desFileTemp = desDirFile.getAbsolutePath()+File.separator+files[i].getName();
				copyFile(srcFileTemp, desFileTemp);
			}
			if (files[i].isDirectory()) {
				String srcDirTemp = files[i].getAbsolutePath();
				String desDirTemp = desDirFile.getAbsolutePath() + File.separator + files[i].getName();
				copyDirectory(srcDirTemp, desDirTemp);
			}
		}
		System.out.println("Success to copy " + srcDirString + " to " + desDirString);
	}
	
	/**
	 * �ƶ��ļ�
	 * @param srcFileString
	 * @param desFileString
	 * @param isOverride
	 * @throws IOException
	 */
	public static void moveFile(String srcFileString,String desFileString,boolean isOverride) throws IOException {
		File srcFileFile = new File(srcFileString);
		File desFileFile = new File(desFileString);
		if(isOverride) {
			if(desFileFile.delete()) {
				System.out.println("Success to delete desFile");
			}
		} else {
			return;
		}
		copyFile(srcFileString, desFileString);
		if(srcFileFile.delete()) {
			System.out.println("Success to delete srcFile");
		}
		
	}
}