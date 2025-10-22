/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.model.DSModel
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.calibersum.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.calibersum.CaliberSumDSModelBuilder;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.remote.model.CaliberSumDSDefine;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public class CaliberSumDSModel
extends DSModel {
    private CaliberSumDSDefine caliberSumDSDefine;
    private CaliberSumDSModelBuilder modelBuilder;

    public CaliberSumDSDefine getCaliberSumDSDefine() {
        return this.caliberSumDSDefine;
    }

    public void setCaliberSumDSDefine(CaliberSumDSDefine caliberSumDSDefine) {
        this.caliberSumDSDefine = caliberSumDSDefine;
    }

    public CaliberSumDSModelBuilder getModelBuilder() {
        return this.modelBuilder;
    }

    public void setModelBuilder(CaliberSumDSModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public String getType() {
        return "CaliberSumDataSet";
    }

    protected void saveExtToJSON(JSONObject json) throws Exception {
        if (this.caliberSumDSDefine == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        String caliberSumDSDefineStr = mapper.writeValueAsString((Object)this.caliberSumDSDefine);
        json.put("caliberSumDSDefine", (Object)caliberSumDSDefineStr);
    }

    protected void loadExtFromJSON(JSONObject json) throws Exception {
        String caliberSumDSDefineStr = json.optString("caliberSumDSDefine");
        if (StringUtils.hasLength(caliberSumDSDefineStr) && !"NULL".equalsIgnoreCase(caliberSumDSDefineStr)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            this.caliberSumDSDefine = (CaliberSumDSDefine)mapper.readValue(caliberSumDSDefineStr, CaliberSumDSDefine.class);
            this.modelBuilder.buildModel(this);
        }
    }
}

