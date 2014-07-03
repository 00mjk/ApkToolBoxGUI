package edu.jiangxin.encode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class RemoveComments {

	/** 
	 * COMMENCODESΪ��ͨ����ģʽ,PRECOMMENTSΪб��ģʽ,MORELINECOMMENTSΪ����ע��ģʽ,
	 * STARMODELΪ����ע�����Ǻ�ģʽ��SINGLELINECOMMENTSΪ����ע��ģʽ��STRINGMODELΪ�ַ���ģʽ��
	 * TRANSFERMODELΪ�ַ���ת��ģʽ
	 */
	private enum model {
		COMMENCODES, PRECOMMENTS, MORELINECOMMENTS, STARMODEL, SINGLELINECOMMENTS, STRINGMODEL, TRANSFERMODEL
	}

	private static model stats = model.COMMENCODES; // stats��¼״̬

	public static String remove(Reader in) throws IOException {
		StringBuilder s = new StringBuilder();
		int n;
		while ((n = in.read()) != -1) {
			switch ((char) n) {
			case '/':
				if (stats == model.COMMENCODES) {// �����ǰλ��ͨ����ģʽ��ת��б��ģʽ
					stats = model.PRECOMMENTS;
				} else if (stats == model.PRECOMMENTS) {// �����ǰΪб��ģʽ��ת������ע��ģʽ
					stats = model.SINGLELINECOMMENTS;
					s.append("  ");
				} else if (stats == model.MORELINECOMMENTS) {//
					s.append(" ");
				} else if (stats == model.STARMODEL) {// �����ǰΪ�Ǻ�ģʽ��ת����ͨ����ģʽ
					stats = model.COMMENCODES;
					s.append(" ");
				} else if (stats == model.SINGLELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STRINGMODEL) {
					s.append("/");
				} else if (stats == model.TRANSFERMODEL) {
					stats = model.STRINGMODEL;
					s.append("/");
				}
				break;
			case '*':
				if (stats == model.COMMENCODES) {
					s.append("*");
				} else if (stats == model.PRECOMMENTS) {// ���Ϊб��ģʽ��ת������ע��ģʽ
					stats = model.MORELINECOMMENTS;
					s.append("  ");
				} else if (stats == model.MORELINECOMMENTS) {// �����ǰΪ����ע��ģʽ��ת���Ǻ�ģʽ
					stats = model.STARMODEL;
					s.append(" ");
				} else if (stats == model.STARMODEL) {
					s.append(" ");
				} else if (stats == model.SINGLELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STRINGMODEL) {
					s.append("*");
				} else if (stats == model.TRANSFERMODEL) {
					s.append("*");
				}
				break;
			case '"':
				if (stats == model.COMMENCODES) {// �����ǰΪ��ͨ����ģʽ��ת���ַ���ģʽ
					stats = model.STRINGMODEL;
					s.append("\"");
				} else if (stats == model.PRECOMMENTS) {// �����ǰΪб��ģʽ��ת����ͨ����ģʽ
					stats = model.COMMENCODES;
					s.append("/\"");
				} else if (stats == model.STARMODEL) {// �����ǰΪ�Ǻ�ģʽ��ת������ע��ģʽ
					stats = model.MORELINECOMMENTS;
					s.append(" ");
				} else if (stats == model.SINGLELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STRINGMODEL) {// �����ǰΪ�ַ���ģʽ��ת����ͨ����ģʽ
					stats = model.COMMENCODES;
					s.append("\"");
				} else if (stats == model.TRANSFERMODEL) {// �����ǰΪת��ģʽ��ת���ַ�����ʽ
					stats = model.STRINGMODEL;
					s.append("\"");
				}
				break;
			case '\\':
				if (stats == model.COMMENCODES) {
					s.append("\\");
				} else if (stats == model.PRECOMMENTS) {// �����ǰΪб��ģʽ��ת����ͨ�����ʽ
					stats = model.COMMENCODES;
					s.append("/\\");
				} else if (stats == model.MORELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STARMODEL) {// �����ǰΪ�Ǻ�ģʽ��ת������ע��ģʽ
					stats = model.MORELINECOMMENTS;
					s.append(" ");
				} else if (stats == model.SINGLELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STRINGMODEL) {// �����ǰΪ�ַ���ģʽ��ת���ַ���ת��ģʽ
					stats = model.TRANSFERMODEL;
					s.append("\\");
				} else if (stats == model.TRANSFERMODEL) {// �����ǰΪ�ַ���ת��ģʽ��ת���ַ���ģʽ
					stats = model.STRINGMODEL;
					s.append("\\");
				}
				break;
			case '\n':
				if (stats == model.COMMENCODES) {
					s.append("\n");
				} else if (stats == model.PRECOMMENTS) {// �����ǰΪб��ģʽ��ת����ͨ�����ʽ
					stats = model.COMMENCODES;
					s.append("/\n");
				} else if (stats == model.MORELINECOMMENTS) {
					s.append("\n");
				} else if (stats == model.STARMODEL) {// �����ǰΪ�Ǻ�ģʽ��ת������ע��ģʽ
					stats = model.MORELINECOMMENTS;
					s.append("\n");
				} else if (stats == model.SINGLELINECOMMENTS) {// �����ǰΪ����ע��ģʽ��ת����ͨ�����ʽ
					stats = model.COMMENCODES;
					s.append("\n");
				} else if (stats == model.STRINGMODEL) {
					s.append("\n");
				} else if (stats == model.TRANSFERMODEL) {
					s.append("\\n");
				}
				break;
			default:
				if (stats == model.COMMENCODES) {
					s.append((char) n);
				} else if (stats == model.PRECOMMENTS) {// �����ǰΪб��ģʽ��ת����ͨ�����ʽ
					stats = model.COMMENCODES;
					s.append("/" + (char) n);
				} else if (stats == model.STARMODEL) {// �����ǰΪ�Ǻ�ģʽ��ת������ע��ģʽ
					stats = model.MORELINECOMMENTS;
					s.append(" ");
				} else if (stats == model.SINGLELINECOMMENTS) {
					s.append(" ");
				} else if (stats == model.STRINGMODEL) {
					s.append((char) n);
				} else if (stats == model.TRANSFERMODEL) {// �����ǰΪ�ַ���ת��ģʽ��ת���ַ���ģʽ
					stats = model.STRINGMODEL;
					s.append((char) n);
				}
				break;
			}
		}
		String result = s.toString();
		//System.out.println(result);
		return result;
	}
	public static void removeJavaFileComment(String srcFileString,String desFileString,String encoding) throws IOException {
		String special = ".removeJavaFileComment.temp"; //��ʱ�ļ��ĺ�׺����������֤���Ậ��ͬ���ļ�
		if(srcFileString.equals(desFileString)) { //���Դ�ļ���Ŀ���ļ���ͬ������·��������ʹ����ʱ�ļ�����ת��
			srcFileString = srcFileString + special;
			FileProcess.copyFile(desFileString, srcFileString);
		}
		
		File srcFileFile = new File(srcFileString);
		File desFileFile = new File(desFileString);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileFile), encoding));
		String tempString = remove(reader);
		FileWriter writer = new FileWriter(desFileFile);
		writer.write(tempString);
		writer.close();
		
		if(srcFileString.equals(desFileString+special)) { //���������ʱ�ļ�����ɾ��
			srcFileFile.delete();
		}
	}
	public static void removeJavaFileComment(String fileString,String encoding) throws IOException {
		removeJavaFileComment(fileString,fileString,encoding);
		
	}
	public static void removeJavaFilesComment(ArrayList<File> files,String encoding) throws IOException {
		Iterator<File> it = files.iterator();
		while(it.hasNext()) {
			removeJavaFileComment(it.next().getAbsolutePath(), encoding);
		}
	}
}
