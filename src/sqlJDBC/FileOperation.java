package sqlJDBC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOperation {
	/**
	 * CreateFile
	 * @param fileName
	 * @return flag
	 * @throws Exception
	 */
	public boolean createFile(File fileName) throws Exception {

		boolean flag = false;
		try {

			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * read text
	 * @param fileName
	 * @return result
	 * @throws IOException
	 */
	public static String readTxtFile(File fileName) throws IOException {

		String result = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {

			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			String read = null;
			try {

				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bufferedReader != null) {

				bufferedReader.close();
			}

			if (fileReader != null) {

				fileReader.close();
			}
		}
		return result;
	}
	
	/**
	 * write text
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream fileOutPutStream = null;
		try {
			fileOutPutStream = new FileOutputStream(fileName);
			fileOutPutStream.write(content.getBytes("UTF-8"));
			fileOutPutStream.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}
	
	/**
	 * add to text
	 * @param filePath
	 * @param content
	 */
	public void contentToTxt(String filePath, String content) {
		String str = new String(); // Source file content
		String s1 = new String();// New file content
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.print("file already exist");
			} else {
				System.out.print("file does not exist");
				f.createNewFile();// create file
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
