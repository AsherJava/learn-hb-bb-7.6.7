/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.syntax.cell.Position;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import org.json.JSONArray;
import org.json.JSONObject;

public class CellFilterInfo
implements Serializable,
Cloneable,
Comparable<CellFilterInfo> {
    private static final long serialVersionUID = 1L;
    private String dataSetName;
    private boolean measure;
    private Position position;
    private List<CellRestrictionInfo> restrictions;
    private static final String FILTER_DSNAME = "dsName";
    private static final String FILTER_MEASURE = "measure";
    private static final String FILTER_POSITION = "pos";
    private static final String FILTER_RESTRICTIONS = "restrictions";

    public CellFilterInfo() {
        this(null);
    }

    public CellFilterInfo(Position position) {
        this.position = position;
        this.restrictions = new ArrayList<CellRestrictionInfo>();
    }

    public String getDataSetName() {
        return this.dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public boolean isMeasure() {
        return this.measure;
    }

    public void setMeasure(boolean measure) {
        this.measure = measure;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<CellRestrictionInfo> getRestrictions() {
        return this.restrictions;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(FILTER_DSNAME, (Object)this.dataSetName);
        json.put(FILTER_MEASURE, this.measure);
        json.put(FILTER_POSITION, (Object)this.position.toString());
        if (!this.restrictions.isEmpty()) {
            JSONArray arr = new JSONArray();
            for (CellRestrictionInfo restriction : this.restrictions) {
                arr.put((Object)restriction.toJSON());
            }
        }
        return json;
    }

    public void fromJSON(JSONObject json) {
        this.dataSetName = json.getString(FILTER_DSNAME);
        this.measure = json.getBoolean(FILTER_MEASURE);
        this.position = Position.valueOf((String)json.getString(FILTER_POSITION));
        this.restrictions.clear();
        JSONArray arr = json.optJSONArray(FILTER_RESTRICTIONS);
        if (arr != null) {
            for (int i = 0; i < arr.length(); ++i) {
                CellRestrictionInfo restriction = new CellRestrictionInfo();
                restriction.fromJSON(arr.getJSONObject(i));
                this.restrictions.add(restriction);
            }
        }
    }

    public int hashCode() {
        int hash = Boolean.hashCode(this.measure);
        hash = hash * 31 + (this.dataSetName == null ? 0 : this.dataSetName.hashCode());
        hash = hash * 31 + (this.position == null ? 0 : this.position.hashCode());
        hash = hash * 31 + this.restrictions.hashCode();
        return hash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellFilterInfo)) {
            return false;
        }
        return this.compareTo((CellFilterInfo)obj) == 0;
    }

    @Override
    public int compareTo(CellFilterInfo o) {
        int cmp;
        if (this.dataSetName != o.dataSetName) {
            if (this.dataSetName == null) {
                return -1;
            }
            if (o.dataSetName == null) {
                return 1;
            }
            cmp = this.dataSetName.compareTo(o.dataSetName);
            if (cmp != 0) {
                return cmp;
            }
        }
        if (this.measure != o.measure) {
            return Boolean.compare(this.measure, o.measure);
        }
        if (this.position != o.position) {
            if (this.position == null) {
                return -1;
            }
            if (o.position == null) {
                return 1;
            }
            cmp = this.position.compareTo(o.position);
            if (cmp != 0) {
                return cmp;
            }
        }
        Iterator<CellRestrictionInfo> thisItr = this.restrictions.iterator();
        Iterator<CellRestrictionInfo> thatItr = o.restrictions.iterator();
        while (thisItr.hasNext() && thatItr.hasNext()) {
            CellRestrictionInfo r2;
            CellRestrictionInfo r1 = thisItr.next();
            int cmp2 = r1.compareTo(r2 = thatItr.next());
            if (cmp2 == 0) continue;
            return cmp2;
        }
        if (thisItr.hasNext()) {
            return 1;
        }
        if (thatItr.hasNext()) {
            return -1;
        }
        return 0;
    }

    public Object clone() {
        CellFilterInfo info;
        try {
            info = (CellFilterInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        info.restrictions = new ArrayList<CellRestrictionInfo>(this.restrictions.size());
        for (CellRestrictionInfo restriction : this.restrictions) {
            info.restrictions.add(restriction.clone());
        }
        return info;
    }

    public String toString() {
        StringJoiner builder = new StringJoiner(", ", "[", "]");
        if (this.position != null) {
            builder.add(this.position.toString());
        }
        if (this.measure) {
            builder.add(FILTER_MEASURE);
        }
        this.restrictions.stream().map(CellRestrictionInfo::toString).forEach(builder::add);
        return builder.toString();
    }
}

