/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  org.json.JSONObject
 */
package com.jiuqi.nr.snapshot.dataset.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.snapshot.dataset.builder.SnapshotDSModelBuilder;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDsModelDefine;
import java.util.List;
import org.json.JSONObject;

public class SnapshotDSModel
extends DSModel {
    public static final String TYPE = "SnapshotDataSet";
    private SnapshotDsModelDefine snapshotDsModelDefine;
    private SnapshotDSModelBuilder snapshotDSModelBuilder;
    private String DWDimensionName;
    private String dataTimeDimensionName;
    private PeriodType periodType;
    private String dataRegionKey;
    private List<String> allFieldKeys;

    public String getType() {
        return TYPE;
    }

    public void saveExtToJSON(JSONObject jsonObject) throws Exception {
        if (this.snapshotDsModelDefine == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        String reportDsModelDefineStr = mapper.writeValueAsString((Object)this.snapshotDsModelDefine);
        jsonObject.put("snapshotDsModelDefine", (Object)new JSONObject(reportDsModelDefineStr));
    }

    public void loadExtFromJSON(JSONObject jsonObject) throws Exception {
        String snapshotDsModelDefineStr = jsonObject.optString("snapshotDsModelDefine");
        if (StringUtils.isNotEmpty((String)snapshotDsModelDefineStr) && !"NULL".equalsIgnoreCase(snapshotDsModelDefineStr)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            this.snapshotDsModelDefine = (SnapshotDsModelDefine)mapper.readValue(snapshotDsModelDefineStr, SnapshotDsModelDefine.class);
            this.snapshotDSModelBuilder.buildModel(this);
        }
    }

    public SnapshotDsModelDefine getSnapshotDsModelDefine() {
        return this.snapshotDsModelDefine;
    }

    public void setSnapshotDsModelDefine(SnapshotDsModelDefine snapshotDsModelDefine) {
        this.snapshotDsModelDefine = snapshotDsModelDefine;
    }

    public SnapshotDSModelBuilder getSnapshotDSModelBuilder() {
        return this.snapshotDSModelBuilder;
    }

    public void setSnapshotDSModelBuilder(SnapshotDSModelBuilder snapshotDSModelBuilder) {
        this.snapshotDSModelBuilder = snapshotDSModelBuilder;
    }

    public String getDWDimensionName() {
        return this.DWDimensionName;
    }

    public void setDWDimensionName(String DWDimensionName) {
        this.DWDimensionName = DWDimensionName;
    }

    public String getDataTimeDimensionName() {
        return this.dataTimeDimensionName;
    }

    public void setDataTimeDimensionName(String dataTimeDimensionName) {
        this.dataTimeDimensionName = dataTimeDimensionName;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getDataRegionKey() {
        return this.dataRegionKey;
    }

    public void setDataRegionKey(String dataRegionKey) {
        this.dataRegionKey = dataRegionKey;
    }

    public List<String> getAllFieldKeys() {
        return this.allFieldKeys;
    }

    public void setAllFieldKeys(List<String> allFieldKeys) {
        this.allFieldKeys = allFieldKeys;
    }
}

