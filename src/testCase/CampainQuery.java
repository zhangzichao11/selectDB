package testCase;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.bean.Campain;
import sqlJDBC.FileOperation;
import sqlJDBC.DBUtils;

public class CampainQuery {
	
	//private static String filePath="testdata/dianfubao/getOTP.txt";
	static FileOperation fileOperation=new FileOperation();
	static DBUtils jdbc=new DBUtils();
	private static String campaign_name="戦国アスカZERO";
	private static String mm;
	private static int rowCount;
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Campain cp=new Campain();
		String sql="select * from campaign where campaign_name=?";
		String sql1="select * from campaign where id=124";
		Object params[]={campaign_name};
		ResultSet rs = null;
		try {
			
			rs = jdbc.query(sql, params);
			mm=jdbc.getData(DBUtils.getConnection(), sql1,1,7);
			
			
			while (rs.next()) {  
				
				cp.setId(Integer.parseInt(rs.getString("id")));
				cp.setCampaign_name(rs.getString("weget"));
				cp.setAdvertiser_name(rs.getString("package_name"));
				cp.setPackage_name(rs.getString("Campaign_name"));
				cp.setPayout(rs.getString("advertiser_name"));
				cp.setWeget(rs.getString("payout"));
				
				System.out.println("id:" + cp.getId());
				System.out.println("weget:" + cp.getWeget());
				System.out.println("package_name:" + cp.getPackage_name());
				System.out.println("campaignName:" + cp.getCampaign_name());
				System.out.println("advertiser_name:" + cp.getAdvertiser_name());
				System.out.println("payout:" + cp.getPayout());
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			rs.close();
			jdbc.close();
		}
		
		System.out.println("mm的值是: " + mm);
		System.out.println("一共有" + rowCount +"满足条件的记录");
		/*Object params1[]={contentString};
		ResultSet rs1=jdbc.query(sql1, params1);
		while (rs1.next()) {  
			
		    System.out.println("otp");
			System.out.println(rs1.getString("OtpCode"));  
		    contentString= "otp:"+rs1.getString("OtpCode");
			
		}
		
		jdbc.close();//close db connect
		File file=new File(filePath);
		fileOperation.createFile(file);//create file
		fileOperation.writeTxtFile(contentString, file);//Query data written to the file*/
	}
}
