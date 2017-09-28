package testCase;
import sqlJDBC.FileOperation;
import sqlJDBC.DBUtils;

public class CampainDel {
	
	
	static FileOperation fileOperation=new FileOperation();

	static DBUtils jdbc=new DBUtils();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String Delesql="delete from geo1";
	
		int mm=jdbc.delete(Delesql);
		if(mm>0){
			
			System.out.println("删除成功");
		}else{
			
			System.out.println("删除失败");
		}
		
		jdbc.close();//close db connect
		
	}
}
