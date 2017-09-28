package testCase;

import java.sql.ResultSet;
import java.util.ArrayList;

import sqlJDBC.DBUtils;
import jdbc.bean.Campain;

public class CampainQuery1 {

	static DBUtils jdbc = new DBUtils();
	static Addbatch addBatch = new Addbatch();

	public static ArrayList<Campain> getList() {// 单表查询
		ArrayList<Campain> ar = new ArrayList<Campain>();// 存储从数据库中取出来的数据

		String sql = "select * from campaign limit 1000";
		ResultSet rs = null;
		try {

			rs = jdbc.getResultSet(DBUtils.getConnection(), sql);
			while (rs.next()) {

				Campain cp = new Campain();
				cp.setId(Integer.parseInt(rs.getString("id")));
				cp.setCampaign_name(rs.getString("campaign_name"));
				cp.setCampaign_status(rs.getString("campaign_status"));

				System.out.println(rs.getString("campaign_status"));
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("campaign_name"));
				ar.add(cp);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {

					rs.close();
					jdbc.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ar;
	}

	public static void main(String[] args) {
		ArrayList<Campain> ar = null;
		try {

			new CampainQuery1();
			ar = CampainQuery1.getList();
			addBatch.insertArpStandardList(ar);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Campain ne : ar) {

			System.out.println("id: " + ne.getId() + " " + "campaign_name: "
					+ ne.getCampaign_name() + "Campaign_status: "
					+ ne.getCampaign_status());
		}
	}
}
