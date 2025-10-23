/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.ExtractDataRow
 *  com.jiuqi.nvwa.dataextract.IDataExtractRequest
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nvwa.dataextract.impl;

import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import com.jiuqi.nvwa.dataextract.IDataExtractRequest;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ZCDataExtractRequestImpl
implements IDataExtractRequest {
    @Autowired
    @Qualifier(value="AssetExtractRestTemplate")
    private RestTemplate restTemplate;
    @Value(value="${gams2.extract.address:GAMS2-BACKEND}")
    private String applicationName = "GAMS2-BACKEND";
    private static String dataExtractPath = "/gams2/nr/dataExtract";
    private String type = "zc";
    private String title = "\u667a\u6167\u8d44\u4ea7";
    private static String REQUEST_KEY_MODEL = "model";
    private static String REQUEST_KEY_PARAMS = "params";
    private static String RESPONSE_KEY_CODE = "code";
    private static String RESPONSE_KEY_ROWS = "rows";
    private static String RESPONSE_KEY_MESSAGE = "message";
    private static String RESPONSE_KEY_COLCOUNT = "colCount";

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public DataExtractResult getResult(QueryContext qContext, String queryName, List<String> argValues, IReportFunction function) throws Exception {
        DataExtractResult result;
        block6: {
            result = null;
            try {
                JSONObject reqJson = new JSONObject();
                JSONObject resJson = new JSONObject();
                reqJson.put(REQUEST_KEY_MODEL, (Object)queryName);
                reqJson.put(REQUEST_KEY_PARAMS, (Object)new JSONArray(argValues));
                ResponseEntity response = this.restTemplate.postForEntity("http://" + this.applicationName + dataExtractPath, (Object)reqJson, JSONObject.class, new Object[0]);
                resJson = (JSONObject)response.getBody();
                if (resJson.getInt(RESPONSE_KEY_CODE) == 0) {
                    JSONArray array = resJson.getJSONArray(RESPONSE_KEY_ROWS);
                    if (result == null) {
                        result = new DataExtractResult(resJson.getInt(RESPONSE_KEY_COLCOUNT));
                    }
                    for (Object json : array) {
                        JSONArray jsonArr = (JSONArray)json;
                        ExtractDataRow extractDataRow = result.addRow();
                        for (int i = 0; i < jsonArr.length(); ++i) {
                            extractDataRow.setFieldValue(i, jsonArr.get(i));
                        }
                    }
                    break block6;
                }
                throw new RuntimeException("\u5904\u7406\u5931\u8d25\uff0c\u72b6\u6001\u7801\uff1a" + resJson.getString(RESPONSE_KEY_CODE) + "\u4fe1\u606f\uff1a" + resJson.getString(RESPONSE_KEY_MESSAGE));
            }
            catch (Exception e) {
                throw new RuntimeException("\u8c03\u7528\u8fdc\u7a0b\u63a5\u53e3\u63d0\u53d6\u6570\u636e\u51fa\u73b0\u9519\u8bef", e);
            }
        }
        return result;
    }
}

