package com.lotto.sampling.vo;

import java.util.ArrayList;
import java.util.List;

public class RequirementVo {

	private int totSum;
	private double totEvg;
	private double deviation;
	
	private List<ReqDetailVo> detailList;

	public int getTotSum() {
		return totSum;
	}

	public void setTotSum(int totSum) {
		this.totSum = totSum;
	}

	public double getTotEvg() {
		return totEvg;
	}

	public void setTotEvg(double totEvg) {
		this.totEvg = totEvg;
	}

	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

	public List<ReqDetailVo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ReqDetailVo> detailList) {
		this.detailList = detailList;
	}
	
	public void addDetailListItem(ReqDetailVo detailItem) {
		
		if(this.detailList == null || this.detailList.isEmpty()) {
			this.detailList = new ArrayList<ReqDetailVo>();
		}
		this.detailList.add(detailItem);
	}
	
}
