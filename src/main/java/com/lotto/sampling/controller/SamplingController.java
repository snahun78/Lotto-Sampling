package com.lotto.sampling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lotto.sampling.service.SamplingService;
import com.lotto.sampling.vo.LottoNumberBaseVo;

@Controller
public class SamplingController {

	@Autowired
	private SamplingService service;
	
	@RequestMapping(value="/getWinningNumberLteDrwNo.ax", method=RequestMethod.GET)
	public void getWinningNumberOneByCnt(@RequestParam int param) {
		List<LottoNumberBaseVo> result = service.getWinningNumberLteDrwNo(param);
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
	
	@RequestMapping(value="/samplingNumber.ax")
	public ModelAndView expectationNumberSampling(){
		ModelAndView mv = new ModelAndView("samplingPage");
		
		List<LottoNumberBaseVo> result = service.getAllWinningNumber();
		List<LottoNumberBaseVo> expectationNumber = service.expectationNumberSampling(result);
		
		mv.addObject("result", result);
		mv.addObject("samplingNumber", expectationNumber);
		return mv;
	}
	
	@RequestMapping(value="/samplingNumberByDrwNo.ax")
	public ModelAndView expectationNumberSamplingByDrwNo(@RequestParam int param){
		
		ModelAndView mv = new ModelAndView("samplingPage");
		
		List<LottoNumberBaseVo> result = null;
		
		if(param > 0) {
			result = service.getWinningNumberLteDrwNo(param);
		}
		else {
			result = service.getAllWinningNumber();
		}
		
		List<LottoNumberBaseVo> allWinningData = service.expectationNumberSampling(result);
		
		mv.addObject("result", result);
		mv.addObject("samplingNumber", allWinningData);
		return mv;
	}
	
}
