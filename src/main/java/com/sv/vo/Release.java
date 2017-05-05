package com.sv.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Release {
	
	private int releaseId;
	private String releaseInfo;
	private String release_desc;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deployment_date;

	public int getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseInfo() {
		return releaseInfo;
	}

	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

	public String getRelease_desc() {
		return release_desc;
	}

	public void setRelease_desc(String release_desc) {
		this.release_desc = release_desc;
	}

	public Date getDeployment_date() {
		return deployment_date;
	}

	public void setDeployment_date(Date deployment_date) {
		this.deployment_date = deployment_date;
	}

}
