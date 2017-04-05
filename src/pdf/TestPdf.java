package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class TestPdf {


	public void writeText(String file) throws Exception {
		// �Ƿ�����
		boolean sort = false;
		// pdf�ļ��� @1 ��E:\\data\\Inputpdf\\����pdf�ļ��и�Ŀ¼�����е�pdf�ļ������ڸ�Ŀ¼�£��Լ��������ã�
		String pdfFile = "D:\\pdfNew\\" + file;
		// �����ı��ļ�����
		String textFile = null;
		// ���뷽ʽ
		String encoding = "UTF-8";
		// ��ʼ��ȡҳ��
		int startPage = 1;
		// ������ȡҳ��
		int endPage = Integer.MAX_VALUE;
		// �ļ��������������ı��ļ�
		Writer output = null;
		// �ڴ��д洢��PDF Document
		PDDocument document = null;

		try {
			try {
				// ���ȵ���һ��URL�������ļ�������õ��쳣�ٴӱ���ϵͳװ���ļ�
				URL url = new URL(pdfFile);
				document = PDDocument.load(url);
				// ��ȡPDF���ļ���
				// String fileName = url.getFile();

				// ��ԭ��pdf�����������²�����txt�ļ�
				if (file.length() > 4) {
					File outputFile = new File(file.substring(0,
							file.length() - 4) + ".txt");
					textFile = outputFile.getName();
				}
			} catch (MalformedURLException e) {
				// �����ΪURLװ�صõ��쳣����ļ�ϵͳװ��
				document = PDDocument.load(pdfFile);
				if (file.length() > 4) {
					textFile = file.substring(0, file.length() - 4) + ".txt";
				}
			}
			// �ļ���������д���ļ���textFile @2 ��E:\\data\\Outputtxt\\����text�ĵ����Ŀ¼���Լ��������ã�
			output = new OutputStreamWriter(new FileOutputStream(
					"D:\\export.txt"), encoding);
			// PDFTextStripper����ȡ�ı�
			PDFTextStripper stripper = null;
			stripper = new PDFTextStripper();
			// �����Ƿ�����
			stripper.setSortByPosition(sort);
			// ������ʼҳ
			stripper.setStartPage(startPage);
			// ���ý���ҳ
			stripper.setEndPage(endPage);
			// ����PDFTextStripper��writeText��ȡ������ı�
			stripper.writeText(document, output);
		} finally {
			if (output != null) {
				// �ر������
				output.close();
			}
			if (document != null) {
				// �ر�PDF Document
				document.close();
			}
		}

	}

	public static void main(String[] args) {
		// @3 ��E:\\data\\Inputpdf\\����pdf�ļ��и�Ŀ¼�����е�pdf�ļ������ڸ�Ŀ¼�£��Լ��������ã�
		File input = new File("D:\\pdfNew\\");
		if (input.isDirectory()) {
			String[] fileList = input.list();
			TestPdf test = new TestPdf();
			System.out.println(input.toString()+"\n");
			for (String file : fileList) {
				try {
					System.out.println(" "+file
							+ " is prepared converting to text....");
					test.writeText(file);
					System.out.println(" "+file + "is done.\n");
					
					Pattern p=Pattern.compile("^[^_]+$");
					Matcher m=p.matcher(file);
					if(!m.matches())
						test.writeText(file);
					
//					int i=0;
//					if(file.codePointAt(i)>95){
//						test.writeText(file);
//						System.out.println(" "+file + "is done.\n");
//						i++;
//					} 

				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}



