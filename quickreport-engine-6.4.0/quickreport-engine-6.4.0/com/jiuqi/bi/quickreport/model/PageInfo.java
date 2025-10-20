/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.DSPageInfo;
import com.jiuqi.bi.quickreport.model.PageMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PageInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private PageMode pageMode = PageMode.NONE;
    private int rowCount;
    private List<DSPageInfo> dsPageInfos = new ArrayList<DSPageInfo>();
    private static final String PI_PAGEMODE = "pageMode";
    private static final String PI_ROWCOUNT = "rowCount";
    private static final String PI_DATASETNAME = "datasetName";
    private static final String PI_GROUPFIELD = "groupField";
    private static final String PI_DSPAGEINFOS = "dsPageInfos";

    public PageMode getPageMode() {
        return this.pageMode;
    }

    public void setPageMode(PageMode pageMode) {
        this.pageMode = pageMode;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<DSPageInfo> getDSPageInfos() {
        return this.dsPageInfos;
    }

    @Deprecated
    public String getDatasetName() {
        return this.dsPageInfos.isEmpty() ? null : this.dsPageInfos.get(0).getDatasetName();
    }

    @Deprecated
    public void setDatasetName(String datasetName) {
        if (this.dsPageInfos.isEmpty()) {
            this.dsPageInfos.add(new DSPageInfo());
        }
        this.dsPageInfos.get(0).setDatasetName(datasetName);
    }

    @Deprecated
    public String getGroupField() {
        return this.dsPageInfos.isEmpty() ? null : this.dsPageInfos.get(0).getGroupField();
    }

    @Deprecated
    public void setGroupField(String groupField) {
        if (this.dsPageInfos.isEmpty()) {
            this.dsPageInfos.add(new DSPageInfo());
        }
        this.dsPageInfos.get(0).setGroupField(groupField);
    }

    public PageInfo clone() {
        PageInfo result;
        try {
            result = (PageInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.dsPageInfos = new ArrayList<DSPageInfo>(this.dsPageInfos.size());
        for (DSPageInfo dsPageInfo : this.dsPageInfos) {
            result.dsPageInfos.add(dsPageInfo.clone());
        }
        return result;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(PI_PAGEMODE, (Object)this.pageMode.toString());
        json.put(PI_ROWCOUNT, this.rowCount);
        JSONArray arr = new JSONArray();
        this.dsPageInfos.stream().filter(pi -> pi.getDatasetName() != null).forEach(pi -> arr.put((Object)pi.toJSON()));
        json.put(PI_DSPAGEINFOS, (Object)arr);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.dsPageInfos.clear();
        this.pageMode = json.isNull(PI_PAGEMODE) ? PageMode.NONE : PageMode.valueOf(json.getString(PI_PAGEMODE));
        this.rowCount = json.optInt(PI_ROWCOUNT);
        if (this.pageMode == PageMode.DATASET) {
            JSONArray arr = json.optJSONArray(PI_DSPAGEINFOS);
            if (arr != null && !arr.isEmpty()) {
                for (int i = 0; i < arr.length(); ++i) {
                    DSPageInfo dsPi = new DSPageInfo();
                    dsPi.fromJSON(arr.getJSONObject(i));
                    this.dsPageInfos.add(dsPi);
                }
            } else if (!json.isNull(PI_DATASETNAME)) {
                String dsName = json.getString(PI_DATASETNAME);
                String groupField = json.optString(PI_GROUPFIELD);
                this.dsPageInfos.add(new DSPageInfo(dsName, groupField));
            }
        }
    }
}

