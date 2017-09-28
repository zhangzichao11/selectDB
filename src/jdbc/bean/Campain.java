package jdbc.bean;

public class Campain {

	private int id;
	private String campaign_name;
	private String advertiser_name;
	private String package_name;
	private String weget;
	private String payout;
	private String campaign_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCampaign_name() {
		return campaign_name;
	}

	public void setCampaign_name(String campaign_name) {
		this.campaign_name = campaign_name;
	}

	public String getAdvertiser_name() {
		return advertiser_name;
	}

	public void setAdvertiser_name(String advertiser_name) {
		this.advertiser_name = advertiser_name;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getWeget() {
		return weget;
	}

	public void setWeget(String weget) {
		this.weget = weget;
	}

	public String getPayout() {
		return payout;
	}

	public void setPayout(String payout) {
		this.payout = payout;
	}

	public String getCampaign_status() {
		return campaign_status;
	}

	public void setCampaign_status(String campaign_status) {
		this.campaign_status = campaign_status;
	}
}
