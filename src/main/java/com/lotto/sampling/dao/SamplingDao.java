package com.lotto.sampling.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lotto.sampling.vo.LottoNumberBaseVo;

@Repository
@Transactional
public class SamplingDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	final String DB_COLLECTION_NAME = "winningNumber";
	
	public LottoNumberBaseVo getWinningNumberOneByCnt(int cnt) {
		
		Query query = new Query(Criteria.where("drwNo").lte(cnt));
		LottoNumberBaseVo result = mongoTemplate.findOne(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}

	public List<LottoNumberBaseVo> getWinningNumberLteDrwNo(int cnt) {
		
		Query query = new Query(Criteria.where("drwNo").lte(cnt));
		List<LottoNumberBaseVo> result = mongoTemplate.find(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public List<LottoNumberBaseVo> getAllWinningNumber(){
		List<LottoNumberBaseVo> result = mongoTemplate.findAll(LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public void insertWinningNumber(LottoNumberBaseVo param) {
		mongoTemplate.insert(param, DB_COLLECTION_NAME);
	}
}
