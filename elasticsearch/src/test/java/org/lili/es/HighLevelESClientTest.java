package org.lili.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HighLevelESClientTest {

    @Test
    @SneakyThrows
    void connect() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        hc.close();
    }

    @Test
    @SneakyThrows
    void createIndex() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        CreateIndexRequest indexRequest = new CreateIndexRequest("user");
        CreateIndexResponse response = hc.indices().create(indexRequest, RequestOptions.DEFAULT);
        //响应状态
        boolean acknowledged = response.isAcknowledged();
        assertTrue(acknowledged);
        hc.close();
    }


    @Test
    @SneakyThrows
    void searchIndex() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = hc.indices().get(request, RequestOptions.DEFAULT);
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());
        System.out.println(response.getAliases());
    }


    @Test
    @SneakyThrows
    void delIndex() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = hc.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    @SneakyThrows
    void insertDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");
        User user = new User();
        user.setAge(1);
        user.setName("lili");
        user.setSex("male");
        request.source(user.toString(), XContentType.JSON);
        IndexResponse response = hc.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
        System.out.println(response.getShardInfo());
        System.out.println(response.getShardId());
    }


    @Test
    @SneakyThrows
    void updateDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "sex", "女");
        UpdateResponse response = hc.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getGetResult());
    }


    @Test
    @SneakyThrows
    void getDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse response = hc.get(request, RequestOptions.DEFAULT);
        System.out.println(response.isExists());
        System.out.println(response.getSourceAsString());
        System.out.println(response.getSource());
    }


    @Test
    @SneakyThrows
    void delDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1001");
        DeleteResponse response = hc.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }


    @Test
    @SneakyThrows
    void bulkInsertDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        BulkRequest request = new BulkRequest();

        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "lili", "age", 30));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", ""));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", ""));

        BulkResponse responses = hc.bulk(request, RequestOptions.DEFAULT);
        System.out.println(responses.getTook());
        for (BulkItemResponse item : responses.getItems()) {
            System.out.println(item.getIndex());
        }
    }


    @Test
    @SneakyThrows
    void bulkDelDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        BulkRequest request = new BulkRequest();

        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));

        BulkResponse responses = hc.bulk(request, RequestOptions.DEFAULT);
        System.out.println(responses.getTook());
        for (BulkItemResponse item : responses.getItems()) {
            System.out.println(item.getIndex());
        }
    }

    @Test
    @SneakyThrows
    void queryDocument() {
        RestHighLevelClient hc = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //全量查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        SearchResponse response = hc.search(request, RequestOptions.DEFAULT);
        System.out.println(response.getHits().getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
        hc.close();
    }


}