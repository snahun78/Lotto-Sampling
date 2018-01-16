package com.lotto.sampling.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lotto.sampling.service.SamplingService;
import com.lotto.sampling.vo.LottoNumberBaseVo;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.ApisApi;
import io.kubernetes.client.auth.ApiKeyAuth;
import io.kubernetes.client.models.V1APIGroupList;

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
		
		log.debug("main : success");
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
		
		try {
			ApiClient defaultClient = Configuration.getDefaultApiClient();
			
			// Configure API key authorization: BearerToken
			ApiKeyAuth BearerToken = (ApiKeyAuth) defaultClient.getAuthentication("BearerToken");
			BearerToken.setApiKey("MyApiKey");
			// API 키에 대한 접두어를 설정하기 위해 다음 줄의 주석 처리를 제거하십시오 (예 : "Token"(기본값은 null) 
			// BearerToken.setApiKeyPrefix("Token");
			
			ApisApi apiInstance =  new  ApisApi();
		    V1APIGroupList apiResult = apiInstance.getAPIVersions();
		    System.out.println(apiResult);
			
//			CoreV1Api api = new CoreV1Api();
//	        V1PodList list = api.listNamespacedPod(null, null, null, null, null, null, null);
//	        for (V1Pod item : list.getItems()) {
//	            System.out.println(item.getMetadata().getName());
//	        }
		} catch (ApiException e) {
			System.err.println("Exception when calling ApisApi#getAPIVersions");
		    e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
}
