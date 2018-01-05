package com.lotto.sampling.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		
		Query query = new Query(Criteria.where("drwNo").is(cnt));
		
		LottoNumberBaseVo result = mongoTemplate.findOne(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}

	public List<LottoNumberBaseVo> getWinningNumberLteDrwNo(int cnt) {
		Query query = new Query(Criteria.where("drwNo").lte(cnt));
		List<LottoNumberBaseVo> result = mongoTemplate.find(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public List<LottoNumberBaseVo> getWinningNumberByCount(int lmt){
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "drwNo"));
		query.limit(lmt);
		
		List<LottoNumberBaseVo> result = mongoTemplate.find(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public List<LottoNumberBaseVo> getWinningNumberByCount(int lmt, String searchStr, String searchVal, String searchType){
		Query query = new Query();
		
		if(searchStr != null && !"".equals(searchStr) && searchVal != null && !"".equals(searchVal)) {
			if("lt".equals(searchType.toLowerCase()) || "<".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).lt(searchVal));				
			}
			else if("lte".equals(searchType.toLowerCase()) || "<=".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).lte(searchVal));
			}
			else if("gt".equals(searchType.toLowerCase()) || ">".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).gt(searchVal));
			}
			else if("gte".equals(searchType.toLowerCase()) || ">=".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).gte(searchVal));
			}
			else if("eq".equals(searchType.toLowerCase()) || "==".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).is(searchVal));
			}
			else if("neq".equals(searchType.toLowerCase()) || "!=".equals(searchType)) {
				query.addCriteria(Criteria.where(searchStr).ne(searchVal));
			}
		}
		
		query.with(new Sort(Sort.Direction.DESC, "drwNo"));
		query.limit(lmt);
		
		List<LottoNumberBaseVo> result = mongoTemplate.find(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public List<LottoNumberBaseVo> getAllWinningNumber(){
//		List<LottoNumberBaseVo> result = mongoTemplate.findAll(LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "drwNo"));
		List<LottoNumberBaseVo> result = mongoTemplate.find(query, LottoNumberBaseVo.class, DB_COLLECTION_NAME);
		return result;
	}
	
	public void insertWinningNumber(LottoNumberBaseVo param) {
		mongoTemplate.insert(param, DB_COLLECTION_NAME);
	}
}
