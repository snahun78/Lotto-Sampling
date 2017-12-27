package com.lotto.sampling.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lotto.sampling.service.SamplingService;
import com.lotto.sampling.vo.LottoNumberBaseVo;

@Controller
@RequestMapping("/sampling")
public class SamplingController {

	@Autowired
	private SamplingService service;
	
	@RequestMapping(value="/getWinningNumberOneByCnt.ax", method=RequestMethod.GET)
	public void getWinningNumberOneByCnt(@RequestParam int param) {
		LottoNumberBaseVo result = service.getWinningNumberOneByCnt(param);
		System.out.println("result = " + result.toString());
	}
	
	@RequestMapping(value="/getAllWinningNumber.ax", method=RequestMethod.POST)
	public @ResponseBody List<LottoNumberBaseVo> getAllWinningNumber() {
		List<LottoNumberBaseVo> result = service.getAllWinningNumber();
		return result;
	}
	
	@RequestMapping(value="/insertWinningNumber.ax", method=RequestMethod.POST)
	public @ResponseBody String insertWinningNumber(@RequestBody LottoNumberBaseVo param) {
		try {
			service.insertWinningNumber(param);
			return "success";
		}catch (Exception e) {
			return "fail";
		}
	}
	
	@RequestMapping("/expectationNumberSampling.ax")
	public List<LottoNumberBaseVo> expectationNumberSampling(){
		List<LottoNumberBaseVo> result = new ArrayList<>();
		
		List<LottoNumberBaseVo> allWinningData = service.expectationNumberSampling();
		
		return result;
	}
	
}
