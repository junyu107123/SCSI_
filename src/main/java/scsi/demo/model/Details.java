package scsi.demo.model;


public class Details {
	public String sysid;
	public String links_sysid;
	public String details_id;
	public String details_type;
	public String details_operator;
	public String details_bandwidth;
	public String details_failure;
	public String details_failure_bandwidth;
	
	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Details(String sysid, String links_sysid, String details_id, String details_type, String details_operator,
			String details_bandwidth, String details_failure, String details_failure_bandwidth) {
		super();
		this.sysid = sysid;
		this.links_sysid = links_sysid;
		this.details_id = details_id;
		this.details_type = details_type;
		this.details_operator = details_operator;
		this.details_bandwidth = details_bandwidth;
		this.details_failure = details_failure;
		this.details_failure_bandwidth = details_failure_bandwidth;
	}

	
	
}
