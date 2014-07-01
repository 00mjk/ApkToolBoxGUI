/**
 * ����̽������
 * @author jiangxin
 * ʹ���˵������İ�:cpdetector
 * http://cpdetector.sourceforge.net/
 * ʹ��˵������������鿴�ٷ�����
 */

package edu.jiangxin.encode;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.File;
import java.nio.charset.Charset;

public class EncoderDetector {
	/**
	 * �ж��ļ���������
	 * @param fileName
	 * @return ���������ַ���
	 */
	public static String judgeFile(String fileName) {

		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("Can't find the file!");
			return null;
		}

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

		// first one returning non-null wins the decision
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());

		Charset charset = null;
		try {
			charset = detector.detectCodepage(file.toURI().toURL()); // f.toURL()�Ѿ�����������ͨ��toURI()���ת��
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return null;
		}
	}

	public static void main(String[] args) {

		String charset = judgeFile("temp/test.txt");
		System.out.println(charset);
	}
}
