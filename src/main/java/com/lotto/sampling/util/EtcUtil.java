package com.lotto.sampling.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EtcUtil {

	public static String nowDate() {
		Date today = new Date();
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		return nowDate.format(today);
	}
	
	/**
	 * 오름차순으로 정렬
	 * @param expectResult
	 * @return
	 */
	public static List<Integer> sortNumber(List<Integer> expectResult) {
		
		List<Integer> sortList = expectResult;
		
		for(int i=0; i<sortList.size()-1; i++) {
			int swapIndex = i;
			int target = sortList.get(i);
			
			for(int j=i+1; j<sortList.size(); j++) {
				if(target > sortList.get(j)) {
					target = sortList.get(j);
					swapIndex = j;
				}
				
				if(target == sortList.get(j) && swapIndex != j) {
					sortList.remove(j);
				}
			}
			
			if(swapIndex != i) {
				int temp = sortList.get(i);
				sortList.set(i, sortList.get(swapIndex));
				sortList.set(swapIndex, temp);				
			}
		}
		
		return sortList;
	}
}
