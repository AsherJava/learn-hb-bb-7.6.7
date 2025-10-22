/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.dataset.FieldIndexInfo;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSModelBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class QueryDSModel
extends DSModel {
    private String blockId;
    private Map<FieldDefine, FieldIndexInfo> fieldIndexMap;
    private int timeKeyIndex = -1;
    private List<Integer> timeDims = new ArrayList<Integer>();
    private TimeGranularity timeGranularity;
    private String formSchemeKey;
    private Map<String, String> paraNamesMap = new HashMap<String, String>();
    private Map<String, Integer> paraColumnIndexMap = new HashMap<String, Integer>();
    private NrQueryDSModelBuilder nrQueryDSModelBuilder;
    private QueryBlockDefine block;

    public QueryDSModel(NrQueryDSModelBuilder nrQueryDSModelBuilder) {
        this.nrQueryDSModelBuilder = nrQueryDSModelBuilder;
    }

    public Map<FieldDefine, FieldIndexInfo> getFieldIndexMap() {
        if (this.fieldIndexMap == null) {
            this.fieldIndexMap = new HashMap<FieldDefine, FieldIndexInfo>();
        }
        return this.fieldIndexMap;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public int getTimeKeyIndex() {
        return this.timeKeyIndex;
    }

    public void setTimeKeyIndex(int timeKeyIndex) {
        this.timeKeyIndex = timeKeyIndex;
    }

    public TimeGranularity getTimeGranularity() {
        return this.timeGranularity;
    }

    public void setTimeGranularity(TimeGranularity timeGranularity) {
        this.timeGranularity = timeGranularity;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<Integer> getTimeDims() {
        return this.timeDims;
    }

    public Map<String, String> getParaNamesMap() {
        return this.paraNamesMap;
    }

    public String getType() {
        return "QueryDataSet";
    }

    public QueryBlockDefine getBlock() {
        return this.block;
    }

    public void setBlock(QueryBlockDefine block) {
        this.block = block;
    }

    public Map<String, Integer> getParaColumnIndexMap() {
        return this.paraColumnIndexMap;
    }

    protected void loadExtFromJSON(JSONObject json) throws Exception {
        this.blockId = json.getString("blockId");
        this.nrQueryDSModelBuilder.buildModel(this);
    }

    protected void saveExtToJSON(JSONObject json) throws Exception {
        json.put("blockId", (Object)this.blockId);
    }
}

