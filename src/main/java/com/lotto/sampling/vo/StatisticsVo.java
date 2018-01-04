package com.lotto.sampling.vo;

import java.util.Map;

public class StatisticsVo {

	private int tx;
	private String statisticDate;
	private int allCount;
	private Map<String, Integer> numberWinningCount;
	private Map<String, Double> numberWinningProbability;
	private double countAvg;
	private double standardProbability;
	
	public int getTx() {
		return tx;
	}
	public void setTx(int tx) {
		this.tx = tx;
	}
	public String getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public Map<String, Integer> getNumberWinningCount() {
		return numberWinningCount;
	}
	public void setNumberWinningCount(Map<String, Integer> numberWinningCount) {
		this.numberWinningCount = numberWinningCount;
	}
	public Map<String, Double> getNumberWinningProbability() {
		return numberWinningProbability;
	}
	public void setNumberWinningProbability(Map<String, Double> numberWinningProbability) {
		this.numberWinningProbability = numberWinningProbability;
	}
	public double getCountAvg() {
		return countAvg;
	}
	public void setCountAvg(double countAvg) {
		this.countAvg = countAvg;
	}
	public double getStandardProbability() {
		return standardProbability;
	}
	public void setStandardProbability(double standardProbability) {
		this.standardProbability = standardProbability;
	}
	
}
