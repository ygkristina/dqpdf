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
		// 是否排序
		boolean sort = false;
		// pdf文件名 @1 “E:\\data\\Inputpdf\\”是pdf文件夹根目录，所有的pdf文件都放在该目录下（自己可以设置）
		String pdfFile = "D:\\pdfNew\\" + file;
		// 输入文本文件名称
		String textFile = null;
		// 编码方式
		String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 文件输入流，生成文本文件
		Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;

		try {
			try {
				// 首先当作一个URL来加载文件，如果得到异常再从本地系统装载文件
				URL url = new URL(pdfFile);
				document = PDDocument.load(url);
				// 获取PDF的文件名
				// String fileName = url.getFile();

				// 以原来pdf名称来命名新产生的txt文件
				if (file.length() > 4) {
					File outputFile = new File(file.substring(0,
							file.length() - 4) + ".txt");
					textFile = outputFile.getName();
				}
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				document = PDDocument.load(pdfFile);
				if (file.length() > 4) {
					textFile = file.substring(0, file.length() - 4) + ".txt";
				}
			}
			// 文件输入流，写入文件到textFile @2 “E:\\data\\Outputtxt\\”是text文档输出目录（自己可以设置）
			output = new OutputStreamWriter(new FileOutputStream(
					"D:\\export.txt"), encoding);
			// PDFTextStripper来提取文本
			PDFTextStripper stripper = null;
			stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(sort);
			// 设置起始页
			stripper.setStartPage(startPage);
			// 设置结束页
			stripper.setEndPage(endPage);
			// 调用PDFTextStripper的writeText提取并输出文本
			stripper.writeText(document, output);
		} finally {
			if (output != null) {
				// 关闭输出流
				output.close();
			}
			if (document != null) {
				// 关闭PDF Document
				document.close();
			}
		}

	}

	public static void main(String[] args) {
		// @3 “E:\\data\\Inputpdf\\”是pdf文件夹根目录，所有的pdf文件都放在该目录下（自己可以设置）
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



