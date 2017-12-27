package com.lotto.sampling.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lotto.sampling.service.SamplingService;
import com.lotto.sampling.vo.DrwNoVo;
import com.lotto.sampling.vo.LottoNumberBaseVo;

@Controller
public class MainController {

	@Autowired
	private SamplingService service;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		List<LottoNumberBaseVo> result = service.getAllWinningNumber();
		int count = result.size();
		
		if(count > 0) {
			LottoNumberBaseVo lastItem = result.get(count-1);
			mv.addObject("lastItem", lastItem);
		}
		
		mv.addObject("count", count);
		return mv;
	}
	
	@RequestMapping("/winningList")
	public ModelAndView getWinningList() {
		ModelAndView mv = new ModelAndView("winningListPage");
		List<LottoNumberBaseVo> result = service.getAllWinningNumber();
		int count = result.size();
		
		if(count > 0) {
			LottoNumberBaseVo lastItem = result.get(count-1);
			mv.addObject("lastItem", lastItem);
		}
		
		mv.addObject("count", count);
		mv.addObject("winningList", result);
		return mv;
	}
}
