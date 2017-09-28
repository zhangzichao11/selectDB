package sqlJDBC;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshConnect {
	
	private static String sshHost = TestProperties.getSshHost();//get sshHost
	private static String sshUserName=TestProperties.getSshUserName();//get sshUserName
	private static String sshPwd=TestProperties.getSshPwd();//get sshPwd
	private static String sshPort=TestProperties.getSshPort();//get sshPort
	private static String dbHost=TestProperties.getDbHost();
	public static int localPort=3306;
	static JSch jsch= new JSch();//java jdbc ssh
	static Session session = null;
	public void sshConnect(){
		
		try {
			
			
			session = jsch.getSession(sshUserName,sshHost,Integer.parseInt(sshPort));
			  		session.setPassword(sshPwd);  
			  		session.setConfig("StrictHostKeyChecking", "no");  
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			session.connect();
			session.setPortForwardingL(localPort, dbHost, localPort); 
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			System.out.println("Appear a little problem, do not affect use"+e.toString());
			//e.printStackTrace();
		}
	}
	
	public static void sshDisConnect(){
		
		session.disconnect();
	}
}
