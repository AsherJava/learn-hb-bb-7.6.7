/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  okhttp3.Call
 *  okhttp3.MediaType
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.RequestBody
 *  okhttp3.Response
 *  org.json.JSONObject
 */
package com.jiuqi.budget.ai.dify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value="budDifyService")
public class DifyService {
    private static final Logger logger = LoggerFactory.getLogger(DifyService.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Value(value="${server.port}")
    private String port;
    @Value(value="${spring.cloud.client.ip-address}")
    private String ip;

    public JsonNode exeWorkFlow(String host, String apiKey, Map<String, String> inputParam) {
        String url = host + "/workflows/run";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("inputs", inputParam == null ? "{}" : inputParam);
        jsonObject.put("response_mode", (Object)"blocking");
        UserLoginDTO user = ShiroUtil.getUser();
        String username = user.getUsername();
        String fullUser = username + "@" + this.ip + "_" + this.port;
        jsonObject.put("user", (Object)fullUser);
        return this.doPost(url, apiKey, jsonObject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private JsonNode doPost(String url, String apiKey, JSONObject param) {
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(5L, TimeUnit.SECONDS).readTimeout(100L, TimeUnit.SECONDS).writeTimeout(100L, TimeUnit.SECONDS).callTimeout(250L, TimeUnit.SECONDS).build();
        MediaType mediaType = MediaType.parse((String)"application/json");
        RequestBody body = RequestBody.create((MediaType)mediaType, (String)param.toString());
        Request request = new Request.Builder().url(url).method("POST", body).addHeader("Authorization", "Bearer " + apiKey).addHeader("Content-Type", "application/json").addHeader("Accept", "*/*").build();
        Call call = client.newCall(request);
        try (Response response = call.execute();){
            String resStr = response.body().string();
            JsonNode jsonNode = this.objectMapper.readTree(resStr);
            return jsonNode;
        }
        catch (IOException e) {
            logger.error("{} {} {} {}", url, apiKey, param, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public JsonNode retrieveKb(String host, String apiKey, String difyKbId, String queryText, Double threshold, int topK, boolean scoreThresholdEnabled) {
        String queryUrl = host + "/datasets/" + difyKbId + "/retrieve";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", (Object)queryText);
        JSONObject retrievalModel = new JSONObject();
        jsonObject.put("retrieval_model", (Object)retrievalModel);
        retrievalModel.put("search_method", (Object)"hybrid_search");
        retrievalModel.put("reranking_enable", true);
        retrievalModel.put("reranking_mode", (Object)"reranking_model");
        JSONObject rerankingModel = new JSONObject();
        rerankingModel.put("reranking_provider_name", (Object)"langgenius/siliconflow/siliconflow");
        rerankingModel.put("reranking_model_name", (Object)"BAAI/bge-reranker-v2-m3");
        retrievalModel.put("reranking_model", (Object)rerankingModel);
        JSONObject weights = new JSONObject();
        JSONObject keywordSetting = new JSONObject();
        keywordSetting.put("keyword_weight", 0.3);
        weights.put("keyword_setting", (Object)keywordSetting);
        JSONObject vectorSetting = new JSONObject();
        vectorSetting.put("vector_weight", 0.7);
        vectorSetting.put("embedding_model_name", (Object)"BAAI/bge-m3");
        vectorSetting.put("embedding_provider_name", (Object)"langgenius/siliconflow/siliconflow");
        weights.put("vector_setting", (Object)vectorSetting);
        retrievalModel.put("weights", (Object)weights);
        retrievalModel.put("top_k", topK);
        retrievalModel.put("score_threshold_enabled", scoreThresholdEnabled);
        retrievalModel.put("score_threshold", (Object)threshold);
        return this.doPost(queryUrl, apiKey, jsonObject);
    }
}

