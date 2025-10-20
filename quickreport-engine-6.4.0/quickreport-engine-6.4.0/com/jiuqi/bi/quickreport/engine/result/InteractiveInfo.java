/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.result.CellFilterInfo;
import com.jiuqi.bi.quickreport.engine.result.CellSortInfo;
import com.jiuqi.bi.syntax.cell.Position;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class InteractiveInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String groupName;
    private boolean sortable;
    private List<CellSortInfo> sortInfos = new ArrayList<CellSortInfo>();
    private boolean filterable;
    private CellFilterInfo filterInfo = new CellFilterInfo();
    private static final String INTERACTIVE_ID = "id";
    private static final String INTERACTIVE_GROUPNAME = "group";
    private static final String INTERACTIVE_SORTABLE = "sortable";
    private static final String INTERACTIVE_SORTINFOS = "sortInfos";
    private static final String INTERACTIVE_FILTERABLE = "filterable";
    private static final String INTERACTIVE_FILTERINFO = "filterInfo";
    @Deprecated
    private static final String INTERACTIVE_MASTERCELL = "sortMaster";
    @Deprecated
    private static final String INTERACTIVE_EXPRESSION = "sortExpr";

    public InteractiveInfo() {
    }

    public InteractiveInfo(int id) {
        this();
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isSortable() {
        return this.sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public List<CellSortInfo> getSortInfos() {
        return this.sortInfos;
    }

    public boolean isFilterable() {
        return this.filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public CellFilterInfo getFilterInfo() {
        return this.filterInfo;
    }

    public boolean enabled() {
        return this.sortable || this.filterable;
    }

    @Deprecated
    public Position getSortMasterCell() {
        return this.sortInfos.isEmpty() ? null : this.sortInfos.get(0).getPosition();
    }

    @Deprecated
    public void setSortMasterCell(Position sortMasterCell) {
        if (this.sortInfos.isEmpty()) {
            this.sortInfos.add(new CellSortInfo());
        }
        this.sortInfos.get(0).setPostion(sortMasterCell);
    }

    @Deprecated
    public String getSortExpression() {
        return this.sortInfos.isEmpty() ? null : this.sortInfos.get(0).getExpression();
    }

    @Deprecated
    public void setSortExpression(String sortExpression) {
        if (this.sortInfos.isEmpty()) {
            this.sortInfos.add(new CellSortInfo());
        }
        this.sortInfos.get(0).setExpression(sortExpression);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(INTERACTIVE_ID, this.id);
        json.put(INTERACTIVE_GROUPNAME, (Object)this.groupName);
        json.put(INTERACTIVE_SORTABLE, this.sortable);
        if (this.sortable) {
            JSONArray arr = new JSONArray();
            for (CellSortInfo sortInfo : this.sortInfos) {
                JSONObject infoObj = sortInfo.toJSON();
                arr.put((Object)infoObj);
            }
            json.put(INTERACTIVE_SORTINFOS, (Object)arr);
        }
        json.put(INTERACTIVE_FILTERABLE, this.filterable);
        if (this.filterable) {
            json.put(INTERACTIVE_FILTERINFO, (Object)this.filterInfo.toJSON());
        }
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.id = json.getInt(INTERACTIVE_ID);
        this.groupName = json.optString(INTERACTIVE_GROUPNAME);
        this.sortable = json.optBoolean(INTERACTIVE_SORTABLE);
        this.sortInfos.clear();
        if (this.sortable) {
            JSONArray arr = json.optJSONArray(INTERACTIVE_SORTINFOS);
            if (arr != null) {
                for (int i = 0; i < arr.length(); ++i) {
                    JSONObject infoObj = arr.getJSONObject(i);
                    CellSortInfo sortInfo = new CellSortInfo();
                    sortInfo.fromJSON(infoObj);
                    this.sortInfos.add(sortInfo);
                }
            } else {
                this.setSortMasterCell(Position.valueOf((String)json.optString(INTERACTIVE_MASTERCELL)));
                this.setSortExpression(json.optString(INTERACTIVE_EXPRESSION));
            }
        }
        this.filterable = json.optBoolean(INTERACTIVE_FILTERABLE);
        this.filterInfo.getRestrictions().clear();
        if (this.filterable) {
            JSONObject obj = json.getJSONObject(INTERACTIVE_FILTERINFO);
            this.filterInfo.fromJSON(obj);
        }
    }

    public InteractiveInfo clone() {
        InteractiveInfo result;
        try {
            result = (InteractiveInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        result.sortInfos = this.sortInfos.stream().map(CellSortInfo::clone).collect(Collectors.toList());
        result.filterInfo = (CellFilterInfo)this.filterInfo.clone();
        return result;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.groupName).append('-').append(this.id).append('(');
        if (this.sortable) {
            buffer.append(INTERACTIVE_SORTABLE);
        }
        if (this.filterable) {
            if (this.sortable) {
                buffer.append(", ");
            }
            buffer.append(INTERACTIVE_FILTERABLE);
        }
        buffer.append(')');
        return buffer.toString();
    }
}

