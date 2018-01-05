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
import com.lotto.sampling.vo.ReqDetailVo;
import com.lotto.sampling.vo.RequirementVo;
import com.lotto.sampling.vo.SamplingNumberVo;
import com.lotto.sampling.vo.StatisticsVo;

@Service
public class SamplingService {
	
	public static Integer[] PRIME_NUMBER = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43};

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
		
		StatisticsVo statisticsResult = statisticsNumber(winningNumberList);
		
		// Filtering
//		exportTargetNumber(statisticsResult);
		
		// 가능성이 있는 번호를 추출
		List<Integer> expectResult = expectNumber(statisticsResult);
		
		// 정렬
		List<Integer> expectList = EtcUtil.sortNumber(expectResult);
		
		// 조건
		RequirementVo reqMent = getRequirement(10);
		
		// 경우의 수
		samplingNumber(expectList, reqMent);
		
		return winningNumberList;
	}
	
	/**
	 * 당첨된 숫자의 횟수를 기본으로 Base Data를 구함.
	 * @param numberList
	 * @return
	 */
	private StatisticsVo statisticsNumber(List<LottoNumberBaseVo> numberList) {
		
		int allCount = 0;
		int totalCountSum = 0;
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
				totalCountSum++;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number2);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number3);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number4);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number5);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(number6);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			key = String.valueOf(bnusNo);
			if(numberWinningCountMap.containsKey(key)) {
				count1 = numberWinningCountMap.get(key);
				totalCountSum++;
			}
			else {
				count1 = 0;
			}
			numberWinningCountMap.put(key, ++count1);
			
			allCount++;
		}
		
		statistic.setAllCount(allCount);
		statistic.setNumberWinningCount(numberWinningCountMap);
		statistic.setCountAvg((double)totalCountSum/45.0);
		
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
	@SuppressWarnings("unused")
	private StatisticsVo exportTargetNumber(StatisticsVo statisticsResult) {
		
//		double standardProbability = statisticsResult.getStandardProbability();
		Map<String, Double> proMap = statisticsResult.getNumberWinningProbability();
		Iterator<String> iter = proMap.keySet().iterator();
		
		// 최근 10개
		List<LottoNumberBaseVo> recentList = dao.getWinningNumberByCount(10);
		
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
			
			if(value < standardProbability) {
				expectNumber.add(Integer.parseInt(key));
			}
		}
		
		double countAvg = param.getCountAvg();
		Map<String, Integer> winningMap = param.getNumberWinningCount();
		Iterator<String> iter2 = winningMap.keySet().iterator();
		while(iter2.hasNext()) {
			String key = iter2.next();
			double value = winningMap.get(key);
			
			if(value <= countAvg){
				expectNumber.add(Integer.parseInt(key));
			}
		}
		
		return expectNumber;
	}
	
	/**
	 * 조건을 생성
	 * @param recentCnt
	 */
	private RequirementVo getRequirement(int recentCnt) {
		// 최근 10개
		List<LottoNumberBaseVo> recentList = dao.getWinningNumberByCount(recentCnt);
		
		RequirementVo requirement = new RequirementVo();
		for(int i=0; i<recentList.size(); i++) {
			
			LottoNumberBaseVo item = recentList.get(i);
			ReqDetailVo detail = new ReqDetailVo();
			
			// 각회차의 합
			int tnSum = 0;
			tnSum = item.getNumber1() + item.getNumber2() + item.getNumber3() + item.getNumber4() + item.getNumber5() + item.getNumber6();
			detail.setSum(tnSum);
			
			// 홀짝의 갯수
			int evenCnt = 0;
			int oddCnt = 0;
			if(item.getNumber1() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			if(item.getNumber2() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			if(item.getNumber3() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			if(item.getNumber4() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			if(item.getNumber5() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			if(item.getNumber6() % 2 == 0) {
				evenCnt++;
			}
			else {
				oddCnt++;
			}
			detail.setEvenCnt(evenCnt);
			detail.setOddCnt(oddCnt);
			
			// 소수의 갯수
			int primeCnt = 0;
			for(int idx=0; idx<PRIME_NUMBER.length-1; idx++) {
				if(item.getNumber1() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
				else if(item.getNumber2() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
				else if(item.getNumber3() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
				else if(item.getNumber4() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
				else if(item.getNumber5() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
				else if(item.getNumber6() == PRIME_NUMBER[idx]) {
					primeCnt++;
				}
			}
			detail.setPrimeNumberCnt(primeCnt);
			
			requirement.addDetailListItem(detail);
//			requirement.setTotSum(requirement.getTotSum() + tnSum);
//			requirement.setTotEvg(requirement.getTotSum()/(i+1));
			
		}
		
		List<ReqDetailVo> detailList = requirement.getDetailList();
		List<Integer> sumList = new ArrayList<>();
		for(int i=0; i<detailList.size(); i++) {
			ReqDetailVo detilItem = detailList.get(i);
			sumList.add(detilItem.getSum());
		}
		
		int totSum = 0;
		double totSumAvg = 0;
		List<Integer> sortList = EtcUtil.sortNumber(sumList);
		for(int i=1; i<sortList.size()-1; i++) {
			totSum += sortList.get(i);
		}
		totSumAvg = (totSum / (sortList.size()-2));
		requirement.setTotSum(totSum);
		requirement.setTotEvg(totSumAvg);
		
		return requirement;
	}
	
	/**
	 * 추출한 번호를 조건을 적용하여 조합한다.
	 * @param expectList
	 * @param reqMent 
	 */
	private List<SamplingNumberVo> samplingNumber(List<Integer> expectList, RequirementVo reqMent) {
		
		List<SamplingNumberVo> numberList = new ArrayList<SamplingNumberVo>();
		
		// 조건
		double gtNumberSum = reqMent.getTotEvg();
		
		for(int a=0; a<expectList.size()-5; a++) {
			int n1 = expectList.get(a);
			
			for(int b=a+1; b<expectList.size()-4; b++) {
				int n2 = expectList.get(b);
				
				for(int c=b+1; c<expectList.size()-3; c++) {
					int n3 = expectList.get(c);
					
					for(int d=c+1; d<expectList.size()-2; d++) {
						int n4 = expectList.get(d);
						
						for(int e=d+1; e<expectList.size()-1; e++) {
							int n5 = expectList.get(e);
							
							for(int f=e+1; f<expectList.size(); f++) {
								SamplingNumberVo expectNumberVo = new SamplingNumberVo();
								
								int n6 = expectList.get(f);
								int numberSum = n1+n2+n3+n4+n5+n6;
								
								boolean isCaseOne = false;
//								boolean isCaseTwo = false;
//								boolean isCaseThree = false;
//								boolean isCaseFore = false;
								
								// 조건1
								if(numberSum > (gtNumberSum-10) && numberSum < (gtNumberSum+10)) {
									isCaseOne = true;
								}
								else {
									continue;
								}
								
								// 조건2
								
								if(isCaseOne) {
									
								}
								expectNumberVo.setNumber1(n1);
								expectNumberVo.setNumber2(n2);
								expectNumberVo.setNumber3(n3);
								expectNumberVo.setNumber4(n4);
								expectNumberVo.setNumber5(n5);
								expectNumberVo.setNumber6(n6);
								
								numberList.add(expectNumberVo);
							}
						}
					}
				}
			}
		}
		
		return numberList;
	}
}
