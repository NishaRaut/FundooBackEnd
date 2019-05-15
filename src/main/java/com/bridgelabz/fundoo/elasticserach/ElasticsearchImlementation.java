package com.bridgelabz.fundoo.elasticserach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilder;

import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticsearchImlementation implements ElasticSearch {
	private final String INDEX = "notedata";
	private final String TYPE = "note";  

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Note create(Note note) throws IOException {
		System.out.println(note);
		@SuppressWarnings("unchecked")
		//convert our Note Object to a Map object
		Map<String, Object> dataMap = mapper.convertValue(note, Map.class);

		System.out.println(dataMap);
		IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(note.getId()))
				.source(dataMap);
		client.index(indexRequest, RequestOptions.DEFAULT);

		return note;
	}

	@Override
	public Note updateNote(Note note) {
		System.out.println("hello");
		UpdateRequest updateRequest = new UpdateRequest(
				INDEX,
				TYPE,
				String.valueOf(note.getId()));



		@SuppressWarnings("unchecked")
		Map<String, Object> documentMapper = mapper.convertValue(note, Map.class);

		updateRequest.doc(documentMapper);

		try {
			@SuppressWarnings("unused")
			UpdateResponse updateResponse = 
			client.update(updateRequest, RequestOptions.DEFAULT);
			System.out.println("Update SuccessFully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return note;	
	}

	@Override
	public void deleteNote(Long NoteId) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(NoteId));
		try {
			DeleteResponse response =
					client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	
	

	@Override
	public List<Note> searchData(String query, long userId) {
		SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*"+query+"*").analyzeWildcard(true).field("title", 2.0f)
				.field("description").field("labels"))
				
				.filter(QueryBuilders.termsQuery("user.id", String.valueOf(userId)));

//	
//		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//		queryBuilder.filter(QueryBuilders.multiMatchQuery(query, "content.query.*"));
//		
		
		
		
		System.out.println();
		searchSourceBuilder.query(queryBuilder);

		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse=null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

			System.out.println(searchResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Note> allnote=getSearchResult(searchResponse);
	

		return allnote;
}


	private List<Note> getSearchResult(SearchResponse response) {

		SearchHit[] searchHits = response.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			notes.add(mapper.convertValue(hit.getSourceAsMap(), Note.class));
		}

		System.out.println(notes);
		return notes;
	}

	



}


