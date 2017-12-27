package com.lotto.sampling.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotto.sampling.dao.SamplingDao;
import com.lotto.sampling.util.EtcUtil;
import com.lotto.sampling.vo.LottoNumberBaseVo;
import com.lotto.sampling.vo.StatisticsVo;

@Service
public class SamplingService {

	@Autowired
	private SamplingDao dao;
	
	public List<LottoNumberBaseVo> getWinningNumberLteDrwNo(int cnt) {
		
		List<LottoNumberBaseVo> result = dao.getWinningNumberLteDrwNo(cnt);
		return result;
	}

	public List<LottoNumberBaseVo> getAllWinningNumber() {
		List<LottoNumberBaseVo> result = dao.getAllWinningNumber();
		return result;
	}
	
	public void insertWinningNumber(LottoNumberBaseVo param) {
		dao.insertWinningNumber(param);
	}

	public List<LottoNumberBaseVo> expectationNumberSampling(List<LottoNumberBaseVo> winningNumberList) {
//		List<LottoNumberBaseVo> winningNumberList = dao.getAllWinningNumber();
		
		StatisticsVo statisticsResult = statisticsNumber(winningNumberList);
		
		// Filtering
//		exportTargetNumber(statisticsResult);
		
		// 가능성이 있는 번호를 추출
		List<Integer> expectResult = expectNumber(statisticsResult);
		
		// 정렬
		List<Integer> sortResult = sortNumber(expectResult);
		
		// 경우의 수
		samplingNumber(sortResult);
		
		return winningNumberList;
	}
	
	/**
	 * 당첨된 숫자의 횟수를 기본으로 표준편차를 계산
	 * @param numberList
	 * @return
	 */
	private StatisticsVo statisticsNumber(List<LottoNumberBaseVo> numberList) {
		
		int allCount = 0;
		StatisticsVo statistic = new StatisticsVo();
		Map<String, Integer> numberWinningCountMap = new HashMap<String, Integer>();
		
		for (LottoNumberBaseVo item : numberList) {
			int number1 = item.getNumber1();
			int number2 = item.getNumber2();
			int number3 = item.getNumber3();
			int number4 = item.getNumber4();
			int number5 = item.getNumber5();
			int number6 = item.getNumber6();
			int bnusNo = item.getBnusNo();
			
			String key = String.valueOf(number1);
			int count1 = 0;
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);				
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number2);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number3);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number4);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number5);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number6);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
//			key = String.valueOf(bnusNo);
//			if(numberWinningCountMap.containsKey(key)) {
//				count1 = numberWinningCountMap.get(key);
//			}
//			else {
//				count1 = 0;
//			}
//			numberWinningCountMap.put(key, ++count1);
			
			allCount++;
		}
		
		statistic.setAllCount(allCount);
		statistic.setNumberWinningCount(numberWinningCountMap);
		
		Set<String> keySet = numberWinningCountMap.keySet();
		int keySize = keySet.size();
		Iterator<String> iter = keySet.iterator();
		
		HashMap<String, Double> setNumberWinningProbability = new HashMap<String, Double>();
		double probabilitySum = 0;
		while(iter.hasNext()) {
			String key = iter.next();
			int value = numberWinningCountMap.get(key);
			double  probability = (double)value / (double)allCount;
			probabilitySum = Double.sum(probabilitySum, probability);
			setNumberWinningProbability.put(key, probability);
		}
		
		double standardProbability = probabilitySum / keySize;
		statistic.setNumberWinningProbability(setNumberWinningProbability);
		statistic.setStandardProbability(standardProbability);
		statistic.setStatisticDate(EtcUtil.nowDate());
		
		System.out.println("statistic = " + standardProbability);
		return statistic;
	}
	
	/**
	 * 제외할 숫자들을 구함
	 * @param statisticsResult
	 */
	private StatisticsVo exportTargetNumber(StatisticsVo statisticsResult) {
		
//		double standardProbability = statisticsResult.getStandardProbability();
		Map<String, Double> proMap = statisticsResult.getNumberWinningProbability();
		Iterator<String> iter = proMap.keySet().iterator();
		
		/* 편차가 제일 큰 값을 제외하기 위해 구함 */
		String removeKey = iter.next();
		double compareValue = proMap.get(removeKey);
		
		while(iter.hasNext()) {
			String key = iter.next();
			double value = proMap.get(key);
			
			if(value < compareValue) {
				compareValue = value;
				removeKey = key;
			}
		}
		
		proMap.remove(removeKey, compareValue);
		return statisticsResult;
	}

	/**
	 * 표준 편차를 기준으로 숫자 추출
	 * @param param
	 * @return
	 */
	private List<Integer> expectNumber(StatisticsVo param) {
		List<Integer> expectNumber = new ArrayList<>();
		
		double standardProbability = param.getStandardProbability();
		Map<String, Double> proMap = param.getNumberWinningProbability();
		Iterator<String> iter = proMap.keySet().iterator();
		
		while(iter.hasNext()) {
			String key = iter.next();
			double value = proMap.get(key);
			
			if(value > (standardProbability-0.01d) && value < (standardProbability+0.01d)) {
				expectNumber.add(Integer.parseInt(key));
			}
		}
		
		return expectNumber;
	}
	
	/**
	 * 오름차순으로 정렬
	 * @param expectResult
	 * @return
	 */
	private List<Integer> sortNumber(List<Integer> expectResult) {
		
		for(int i=0; i<expectResult.size()-1; i++) {
			int swapIndex = i;
			int target = expectResult.get(i);
			
			for(int j=i+1; j<expectResult.size(); j++) {
				if(target > expectResult.get(j)) {
					target = expectResult.get(j);
					swapIndex = j;
				}
			}
			
			if(swapIndex != i) {
				int temp = expectResult.get(i);
				expectResult.set(i, expectResult.get(swapIndex));
				expectResult.set(swapIndex, temp);				
			}
		}
		
		return expectResult;
	}
	
	private void samplingNumber(List<Integer> sortResult) {
		
		
	}
}
