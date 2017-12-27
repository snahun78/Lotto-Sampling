package com.lotto.sampling.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EtcUtil {

	public static String nowDate() {
		Date today = new Date();
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		return nowDate.format(today);
	}
	
}
