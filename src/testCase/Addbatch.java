package testCase;
import java.util.List;

import sqlJDBC.DBUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import jdbc.bean.Campain;

public class Addbatch {
	
	static DBUtils dbUtils=new DBUtils();
//  获取要设置的Arp基准的List后,插入Arp基准表中    
	public boolean insertArpStandardList(List<Campain> list) {
        PreparedStatement pst = null;
        
        //MySql的JDBC连接的url中要加rewriteBatchedStatements参数，并保证5.1.13以上版本的驱动，才能实现高性能的批量插入。
        //优化插入性能，用JDBC的addBatch方法，但是注意在连接字符串加上面写的参数。
        //例如： String connectionUrl="jdbc:mysql://192.168.1.100:3306/test?rewriteBatchedStatements=true" ;
         
        String sql ="insert into moveLink(moveName, moveLink, moveScore) values(?,?,?)";
       
        try{
        	
        	
        	Connection  conn = (Connection) DBUtils.getConnection();
            pst = (PreparedStatement) conn.prepareStatement(sql);
             
            //优化插入第一步       设置手动提交  
            conn.setAutoCommit(false); 
             
            int len = list.size();
            for(int i=0; i<len; i++) {
                pst.setInt(1,10000+list.get(i).getId());
                pst.setString(2, list.get(i).getCampaign_name());
                pst.setString(3, list.get(i).getCampaign_status());     
                 System.out.println("批处理的大小"+len);
                 System.out.println("是否有值"+list.get(i).getId());
                 System.out.println("是否有值"+list.get(i).getCampaign_name());
                 System.out.println("是否有值"+list.get(i).getCampaign_status());
                //if(ps.executeUpdate() != 1) r = false;    优化后，不用传统的插入方法了。
                 
                //优化插入第二步       插入代码打包，等一定量后再一起插入。
                pst.addBatch(); 
                //if(ps.executeUpdate() != 1)result = false;
                //每200次提交一次 
                if((i!=0 && i%100==0) || i==len-1){//可以设置不同的大小；如50，100，200，500，1000等等  
                    pst.executeBatch();  
                    //优化插入第三步       提交，批量插入数据库中。
                    conn.commit();  
                    System.out.println("执行成功");
                    pst.clearBatch();        //提交后，Batch清空。
                }
            }
 
        } catch (Exception e) {
            System.out.println("MibTaskPack->getArpInfoList() error:" + e.getMessage());
            return false;   //出错才报false
        }
        return true;
    }

}
