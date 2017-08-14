package com.cmy.demo;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class Demo {
	private SolrServer server = null;
	@Before
	public void init(){
		String baseURL = "http://localhost:8080/solr";
		server = new HttpSolrServer(baseURL);
		
	}
	@Test
	public void testAdd() throws Exception{		
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "333");
		doc.setField("name","李白");
		server.commit();
	}

	@Test
	public void testDelete() throws Exception{
		//server.deleteById("12");
		server.deleteByQuery("*:*");
		server.commit();
		
	}
	
	@Test
	public void testQuery() throws Exception{
		SolrQuery params = new SolrQuery();
		params.set("q", "id:12");
		QueryResponse response = server.query(params);
		SolrDocumentList results = response.getResults();
		System.out.println("一共"+results.getNumFound()+"条");
		for (SolrDocument solrDocument : results) {
			System.out.println("id:"+solrDocument.get("id"));
			System.out.println("name:"+solrDocument.get("name"));
			System.out.println("......................");
		}
		server.commit();
	}
}	
