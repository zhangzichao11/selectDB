package testCase;

import java.io.File;
import java.sql.ResultSet;

import sqlJDBC.FileOperation;
import sqlJDBC.DBUtils;

public class UpdateCreditBaseInfo {
	
	private static String filePath="testdata/dianfubao/UpdateCredit.txt";
	static FileOperation fileOperation=new FileOperation();
	private static String contentString;
	static DBUtils jdbc=new DBUtils();
	private static String phone="13600000000";
	private static String BankCardNo="621098569000112";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String Querysql="select * from PlatTransLogs where PhoneOfPayer=? order by CreateTime desc limit 1";
		String UpdateSql="update BankAccounts set BankCardNo=? where UserGuid=?";
		String Querybank="select * from BankAccounts where BankCardNo=?";
		File file=new File(filePath);
		fileOperation.createFile(file);//create file
		
		Object params[]={phone};//根据手机号查询交易出userId
		ResultSet rs=jdbc.query(Querysql, params);

		while (rs.next()) {  
			
		    contentString=rs.getString("PayerGuid");
		   
		}
		
		System.out.println(contentString);//输出查询的userId
		
		Object params1[]={BankCardNo,contentString};//根据userId修改银行卡号
		int mm=jdbc.update(UpdateSql, params1);
		if(mm>0){
			
			System.out.println("修改成功");
		}else{
			
			System.out.println("修改失败");
		}
		
		Object params2[]={BankCardNo};//查询修改后的数据
		ResultSet rs1=jdbc.query(Querybank, params2);
		while (rs1.next()) {  
			
			System.out.print("AccountName:"+rs1.getString("AccountName")+"\t");
			System.out.print("City:"+rs1.getString("City")+"\t");
			System.out.print("IdCard:"+rs1.getString("IdCard")+"\t");
			System.out.print("BankCardNo:"+rs1.getString("BankCardNo")+"\t");
			System.out.println("\n");
			contentString="AccountName:"+rs1.getString("AccountName")+"\t"+"City:"+rs1.getString("City")+"\t"
					+"IdCard:"+rs1.getString("IdCard")+"\t"+"BankCardNo:"+rs1.getString("BankCardNo")+"\t";
			fileOperation.writeTxtFile(contentString, file);//Query data written to the file
		}
		if(rs1!=null){
			
			rs1.close();
		}
		jdbc.close();//close db connect
		
	}
}
