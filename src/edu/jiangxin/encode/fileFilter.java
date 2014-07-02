/**
 * �ļ�������
 * @author jiangxin
 */
package edu.jiangxin.encode;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class fileFilter {
	static ArrayList<File> arrayList = new ArrayList<>();

	public static void main(String[] args) {
		ArrayList<File> mylist = list("E:/temp/java/Test", ".java");
		System.out.println(mylist);
	}

	public static ArrayList<File> list(String name, String suffix) {

		try {
			File file = new File(name);
			if (!file.exists()) {
				System.out.println("Can't find the file!");
				return null;
			}
			System.out.println("here1");

			if (file.isDirectory()) { //�����Ŀ¼�Ļ�������Ŀ¼�·����������ļ�����ArrayList
				System.out.println("here2");
				File[] list = file.listFiles(getFileExtensionFilter(suffix));
				for (int i = 0; i < list.length; i++) {
					System.out.println(list[i].toString());
					arrayList.add(list[i]);
				}
				System.out.println("here2");
				System.out.println(list);

				list = file.listFiles(getDirectoryFilter()); // ���˳����е�Ŀ¼
				for (int i = 0; i < list.length; i++) {
					list(list[i].toString(), suffix);
				}

			} else if (file.isFile()) { //������ļ��Ļ���ֱ�ӽ����ļ�����ArrayList
				arrayList.add(file);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return arrayList;

	}

	public static FilenameFilter getFileExtensionFilter(final String extension) {// ָ����չ������
		if (extension == null) { //û��ָ����׺���򷵻ظ�Ŀ¼�����е��ļ�
			return new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return true;
				}
			};
		} else { //ָ����׺���򷵻ظ�Ŀ¼��ӵ����Щ��׺���ļ�
			return new FilenameFilter() {
				public boolean accept(File file, String name) {
					boolean ret = name.endsWith(extension);
					return ret;
				}
			};
		}

	}

	public static FileFilter getDirectoryFilter() { // �õ����е�Ŀ¼
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();// �ؼ��жϵ�
			}
		};
	}
}
