/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.embedded;

import com.jiuqi.bi.dataset.BIDataSetError;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class EmbeddedDSModel
extends DSModel {
    public static final String EMBEDDED_DS_TYPE = "embedded";
    public static final String HIERARCHY_PROPERTY = "hierSerials";
    private byte[] memoryDataset;

    public EmbeddedDSModel() {
    }

    public EmbeddedDSModel(MemoryDataSet<?> dataset) {
        Metadata metadata = dataset.getMetadata();
        Map properties = metadata.getProperties();
        List hiers = (List)properties.get("HIERARCHY");
        if (hiers != null && !hiers.isEmpty()) {
            ArrayList<String> newHiers = new ArrayList<String>();
            for (DSHierarchy hier : hiers) {
                newHiers.add(hier.toJson().toString());
            }
            properties.put(HIERARCHY_PROPERTY, StringUtils.join(newHiers.iterator(), (String)"||"));
        }
        try {
            this.memoryDataset = dataset.save();
        }
        catch (IOException e) {
            throw new BIDataSetError(e);
        }
    }

    @Override
    public String getType() {
        return EMBEDDED_DS_TYPE;
    }

    public void setData(byte[] memoryDataset) {
        this.memoryDataset = memoryDataset;
    }

    public byte[] getData() {
        return this.memoryDataset;
    }

    @Override
    protected void saveExtToJSON(JSONObject json) throws Exception {
        if (this.memoryDataset != null) {
            String v = Base64.getEncoder().encodeToString(this.memoryDataset);
            json.put("data", (Object)v);
        }
    }

    @Override
    protected void loadExtFromJSON(JSONObject json) throws Exception {
        String v = json.optString("data");
        if (v != null) {
            this.memoryDataset = Base64.getDecoder().decode(v);
        }
    }
}

