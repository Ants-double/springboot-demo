package com.ants.elasticsearch.samples;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.elasticsearch.samples.pojo.Friend;
import com.ants.elasticsearch.samples.pojo.User;
import com.ants.elasticsearch.samples.utils.EsUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ElasticsearchSamplesApplicationTests {

	@Autowired
	EsUtil esUtil;

	@Test
	void contextLoads() {
	}

	@Test
	public void esTest() throws IOException {
		// 创建索引
		boolean ants_samples = esUtil.createIndex("ants_samples");
		System.out.println(ants_samples);
		// 获取ik 拆分结果
		List<String> result = esUtil.getIkAnalyseResponse("这是一个中国的地方");
		result.forEach(x ->
				System.out.println(x));
		System.out.println("over");
	}

	@Test
	public void esDelete() throws IOException {
		boolean ants_samples = esUtil.deleteIndex("ants_samples");
		System.out.println(ants_samples);
	}

	@Test
	public void esAdd() throws IOException {
		User ants = User.builder()
				.id("1")
				.userName("ants")
				.email("ants@yeah.net").build();
		Friend zs = Friend.builder().friendId("4")
				.friendName("zs")
				.friendNumber("56").build();
		String samples = esUtil.addData((JSONObject) JSON.toJSON(ants), "ants_samples", "1");
		System.out.println(samples);
		String samples1 = esUtil.addData((JSONObject) JSON.toJSON(ants), "ants_samples");
		esUtil.addData((JSONObject) JSON.toJSON(zs), "ants_samples", "4");
	}

	@Test
	public void esUpdate() throws IOException {
		User ants = User.builder()
				.id("2")
				.userName("ants")
				.email("ants@yeah.net").build();

		esUtil.updateDataById((JSONObject) JSON.toJSON(ants), "ants_samples", "1");

		System.out.println("test");
	}

	@Test
	public void esSearch() throws IOException {
		Map<String, Object> ants_samples = esUtil.searchDataById("ants_samples", "1", "");
		System.out.println(ants_samples);
	}

	@Test
	public void esDeleteData() throws IOException {
		esUtil.deleteDataById("ants_samples", "1");
	}

	@Test
	public void esSearchList() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//searchSourceBuilder.query(QueryBuilders.termQuery("fileName","雷达"));
		BoolQueryBuilder must = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("fileName", "雷达"));
		must.must(QueryBuilders.termQuery("addName","郭京伟"));
		searchSourceBuilder.query(must);
		List<Map<String, Object>> maps = esUtil.searchListData("sale_crm_index", searchSourceBuilder, 10, 0, "", "", "fileName");
		System.out.println(maps);
	}
}
