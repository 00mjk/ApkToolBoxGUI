package edu.jiangxin.encode;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class fileFilter {
	static ArrayList<File> arrayList = new ArrayList<>();

	public static void main(String[] args) {
		ArrayList<File> mylist = list("E:/temp/java/Test",".java");
		System.out.println(mylist);
	}

	public static ArrayList<File> list(String name,String suffix) {

		try {
			File file = new File(name);
			if (!file.exists()) {
				System.out.println("Can't find the file!");
				return null;
			}

			if (file.isDirectory()) {

				File[] list = file.listFiles(getFileExtensionFilter(suffix));
				for (int i = 0; i < list.length; i++) {
					System.out.println(list[i].toString());
					arrayList.add(list[i]);
				}

				list = file.listFiles(getDirectoryFilter()); // ���˳����е�Ŀ¼
				for (int i = 0; i < list.length; i++) {
					list(list[i].toString(),suffix);
				}

			}

		} catch (Exception e) {
			System.out.println("IO error!/r/n" + e.toString());
		}
		return arrayList;

	}

	public static FilenameFilter getFileExtensionFilter(final String extension) {// ָ����չ������
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean ret = name.endsWith(extension);
				return ret;
			}
		};
	}

	public static FileFilter getDirectoryFilter() { // �õ����е�Ŀ¼
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();// �ؼ��жϵ�
			}
		};
	}
}

