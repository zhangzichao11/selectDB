package testCase;

import java.io.File;
import java.sql.ResultSet;

import sqlJDBC.FileOperation;
import sqlJDBC.DBUtils;

public class DeleteInfo {
	
	private static String filePath="testdata/dianfubao/deleOTP.txt";
	static FileOperation fileOperation=new FileOperation();
	private static String contentString;
	static DBUtils jdbc=new DBUtils();
	private static String phone="13600000001";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String Querysql="select * from PlatTransLogs where PhoneOfPayer=?";
		String Delesql="delete from OTP where UserGuid=?";
		Object params[]={phone};
		ResultSet rs=jdbc.query(Querysql, params);

		while (rs.next()) {  
			
		    contentString=rs.getString("PayerGuid");
		    
		}
		System.out.println("UserGuid:"+contentString);
		Object params1[]={contentString};
		int mm=jdbc.delete(Delesql, params1);
		if(mm>0){
			
			System.out.println("删除成功");
		}else{
			
			System.out.println("删除失败");
		}
		
		jdbc.close();//close db connect
		File file=new File(filePath);
		fileOperation.createFile(file);//create file
		fileOperation.writeTxtFile(contentString, file);//Query data written to the file
	}

}
