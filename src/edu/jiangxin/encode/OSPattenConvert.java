/**
 * ��ͬ����ϵͳ�ļ���ʽת��
 * @author jiangxin
 */
package edu.jiangxin.encode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class OSPattenConvert {
	
	public static void main(String[] args) throws IOException {
		//dos2Unix("temp/test.txt", "temp/unix.txt");
		//dos2Mac("temp/test.txt", "temp/mac.txt");
		//unix2Dos("temp/unix.txt", "temp/dos.txt");
		osDirConvert("temp/temp","temp/tempdos","toDoS");
		osDirConvert("temp/temp","temp/tempunix","dos2unix");
		osDirConvert("temp/temp","temp/tempmac","dostomAC");
		osDirConvert("temp/temp","temp/templinux","toLinux");
	}
	
	/**
	 * ת������������ʵ�ֺ���������ת������������ô˺���
	 * @param srcFileString:Դ�ļ����ļ���
	 * @param desFileString:Ŀ���ļ����ļ���
	 * @param options:���з�������:\n,\r,\r\n
	 * @throws IOException
	 */
	private static void convert(String srcFileString,String desFileString,String options) throws IOException {
		
		String special = ".OSPattenConvert.temp"; //��ʱ�ļ��ĺ�׺����������֤���Ậ��ͬ���ļ�
		if(srcFileString.equals(desFileString)) { //���Դ�ļ���Ŀ���ļ���ͬ������·��������ʹ����ʱ�ļ�����ת��
			srcFileString = srcFileString + special;
			FileProcess.copyFile(desFileString, srcFileString);
		}
		File srcFileFile = new File(srcFileString);
		File desFileFile = new File(desFileString);
		
		if(!srcFileFile.exists()) { //�ж�Դ�ļ��Ƿ����
			System.out.println("Դ�ļ�������:" + srcFileFile.getAbsolutePath());
			return ;
		}
		if(!desFileFile.getParentFile().exists()) { //�ж�Ŀ���ļ��Ƿ����
			desFileFile.getParentFile().mkdirs();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(srcFileFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(desFileFile));
		
		String temp = null;
		while((temp=reader.readLine())!=null) {
			writer.write(temp);
			writer.write(options);
		}
		writer.close();
		reader.close();
		
		if(srcFileString.equals(desFileString+special)) { //���������ʱ�ļ�����ɾ��
			srcFileFile.delete();
		}
	}
	
	private static void toUnix(String srcFileString,String desFileString) throws IOException {
		convert(srcFileString, desFileString, "\n");
		System.out.println("Success to convert " + srcFileString + " to unix");
	}
	
	private static void toDos(String srcFileString,String desFileString) throws IOException {
		convert(srcFileString, desFileString, "\r\n");
		System.out.println("Success to convert " + srcFileString + " to dos");
	}
	
	private static void toMac(String srcFileString,String desFileString) throws IOException {
		convert(srcFileString, desFileString, "\r");
		System.out.println("Success to convert " + srcFileString + " to mac");
	}
	
	
	public static void dos2Unix(String srcFileString,String desFileString) throws IOException {
		toUnix(srcFileString, desFileString);
	}
	
	public static void dos2Mac(String srcFileString,String desFileString) throws IOException {
		toMac(srcFileString, desFileString);
	}

	public static void unix2Dos(String srcFileString,String desFileString) throws IOException {
		toDos(srcFileString, desFileString);
	}
	
	public static void unix2Mac(String srcFileString,String desFileString) throws IOException {
		toMac(srcFileString, desFileString);
	}

	public static void mac2Unix(String srcFileString,String desFileString) throws IOException {
		toUnix(srcFileString, desFileString);
	}
	public static void mac2Dos(String srcFileString,String desFileString) throws IOException {
		toDos(srcFileString, desFileString);
	}
	
	/**
	 * �ļ�ת����������һ������ϵͳ�ļ���ʽת����һ��
	 * @param srcFileString
	 * @param desFileString
	 * @param ת��ģʽ
	 * @throws IOException
	 */
	public static void osFileConvert(String srcFileString,String desFileString,String pattern) throws IOException {
		
		pattern = pattern.toLowerCase(); //���������д��ĸ��ʽ��ת��ģʽ
		pattern = pattern.replace("to", "2"); //�滻pattern�е�to����ֹ������
		pattern = pattern.replace("linux", "unix"); //����linux��unix�ļ���ʽ��ͬ������ֱ����unix�滻linux���������unix2unix����
		pattern = pattern.replace("bsd", "unix"); //����bsd��unix�ļ���ʽ��ͬ������ֱ����unix�滻linux���������unix2unix����
		boolean isToUnix = pattern.equals("2unix") || pattern.equals("mac2unix") ||pattern.equals("dos2unix") || pattern.equals("unix2unix");
		boolean isToMac = pattern.equals("2mac") ||pattern.equals("dos2mac") || pattern.equals("unix2mac");
		boolean isToDos = pattern.equals("2dos") ||pattern.equals("mac2dos") || pattern.equals("unix2dos");
		
		if(isToUnix) {
			toUnix(srcFileString, desFileString);
		} else if(isToDos) {
			toDos(srcFileString, desFileString);
		} else if(isToMac) {
			toMac(srcFileString, desFileString);
		} else{
			System.err.println("Error input,can't convert!");
		}
	}
	public static void osFileConvert(String fileString,String pattern) throws IOException {
		osFileConvert(fileString,fileString,pattern);
	}
	
	/**
	 * �ļ�Ŀ¼ת�����������������е��ļ���һ������ϵͳ�ļ���ʽת����һ��
	 * @param srcDirString
	 * @param desDirString
	 * @param pattern
	 * @param suffix �����ض��ļ���׺
	 * @throws IOException
	 */
	public static void osDirConvert(String srcDirString,String desDirString,String pattern,String suffix) throws IOException {
		File srcDirFile = new File(srcDirString);
		File desDirFile = new File(desDirString);
		if(!srcDirFile.exists()) {
			System.out.println("ԴĿ¼������" + srcDirFile.getAbsolutePath());
		}
		ArrayList<File> arrayList = fileFilter.list(srcDirString, suffix);
		Iterator<File> it = arrayList.iterator();
		while(it.hasNext()) {
			File srcFileFile = it.next();
			
			String srcFileString = srcFileFile.getAbsolutePath(); //�õ�Դ�ļ����Ե�ַ
			//System.out.println("srcFileString" + srcFileString);
			
			String temp = srcFileFile.getAbsolutePath().substring(srcDirFile.getAbsolutePath().toString().length());
			//System.out.println("temp" + temp);
			
			String desFileString = desDirFile.getAbsolutePath() + temp; //�õ�Ŀ���ļ����Ե�ַ
			//System.out.println("desFileString" + desFileString);
			
			osFileConvert(srcFileString, desFileString, pattern);
		}
		
	}
	
	/**
	 * osDirConvert(String srcDirString,String desDirString,String pattern,String suffix)�����غ���Ĭ��suffixΪnull
	 * @param srcDirString
	 * @param desDirString
	 * @param pattern
	 * @throws IOException
	 */
	public static void osDirConvert(String srcDirString,String desDirString,String pattern) throws IOException {
		osDirConvert(srcDirString, desDirString, pattern, null);
	}
	public static void osConvertFiles(ArrayList<File> files,String pattern) throws IOException {
		Iterator<File> it = files.iterator();
		while(it.hasNext()) {
			osFileConvert(it.next().getAbsolutePath(), pattern);
		}
	}

}
