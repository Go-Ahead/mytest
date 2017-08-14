package com.cmy.demo;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

public class Demo1 {
	@Test
	public void testSolrj() throws Exception {
		String baseURL = "http://localhost:8082/solr";
		SolrServer server = new HttpSolrServer(baseURL );
		
		SolrQuery params = new SolrQuery();
		//关键词查询
		params.set("q", "product_name:钻石");
		//过滤查询
		params.setFilterQueries("product_price:[1 TO 5]");
		//排序
		params.setSort("product_price", ORDER.desc);
		//分页
		params.setStart(0);
		params.setRows(2);
		//结果域中的列表
		params.setFields("id","product_name","product_price","product_catalog_name","product_picture");
		//设置默认搜索域
		params.set("df", "product_keywords");
		//开启高亮显示
		params.setHighlight(true);
		//添加要高亮显示的字段
		params.addHighlightField("product_name");
		//前缀
		params.setHighlightSimplePre("<span style='color:red'>");
		//后缀
		params.setHighlightSimplePost("</span>");
		QueryResponse response = server.query(params);
		SolrDocumentList results = response.getResults();
		Map<String, Map<String, List<String>>> map = response.getHighlighting();
		long num = results.getNumFound();
		for (SolrDocument d : results) {
			System.out.println("id:"+d.get("id"));
			System.out.println("product_name:"+d.get("product_name"));
			System.out.println("product_price:"+d.get("product_price"));
			System.out.println("product_catalog_name:"+d.get("product_catalog_name"));
			System.out.println("product_picture:"+d.get("product_picture"));
			List<String> list = map.get(d.get("id")).get("product_name");
			if(null != list) {
				System.out.println(list.get(0));
			}			
			System.out.println(".....................");
		}
					
		server.commit();
	}
}
