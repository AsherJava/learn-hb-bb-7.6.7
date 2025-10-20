/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.model.datasource;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.parameter.model.ParameterDimType;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DataSourceModel
implements Cloneable {
    private String type;
    private DataType dataType;
    private ParameterHierarchyType hierarchyType = ParameterHierarchyType.NONE;
    private String refObject;
    private String dataPattern;
    private String showPattern;
    private boolean isTimekey;
    private TimeGranularity timegranularity;
    protected ParameterDimType dimType = ParameterDimType.NONE;
    private boolean orderEnable = false;
    private static final String TAG_TYPE = "type";
    private static final String TAG_DATATYPE = "dataType";
    private static final String TAG_HIERARCHY_TYPE = "hierarchyType";
    private static final String TAG_DATAPATTERN = "dataPattern";
    private static final String TAG_ISTIMEKEY = "isTimekey";
    private static final String TAG_SHOWPATTERN = "showPattern";
    private static final String TAG_TIMEGRANULARITY = "timegranularity";
    private static final String TAG_DIMTYPE = "dimType";
    private static final String TAG_REFOBJECT = "refObject";
    private static final String TAG_ORDER_ENABLE = "orderEnable";

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimekey(boolean isTimekey) {
        this.isTimekey = isTimekey;
    }

    public boolean isTimekey() {
        return this.isTimekey;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ParameterHierarchyType getHireachyType() {
        return this.hierarchyType;
    }

    public void setHierarchyType(ParameterHierarchyType hireachyType) {
        this.hierarchyType = hireachyType;
    }

    public String getDataPattern() {
        return this.dataPattern;
    }

    public void setDataPattern(String dataPattern) {
        this.dataPattern = dataPattern;
    }

    public String getShowPattern() {
        return this.showPattern;
    }

    public void setShowPattern(String showPattern) {
        this.showPattern = showPattern;
    }

    public TimeGranularity getTimegranularity() {
        return this.timegranularity;
    }

    public void setTimegranularity(TimeGranularity timegranularity) {
        this.timegranularity = timegranularity;
    }

    public ParameterDimType getDimType() {
        return this.dimType;
    }

    public void setDimType(ParameterDimType dimType) {
        this.dimType = dimType;
    }

    public String getRefObject() {
        return this.refObject;
    }

    public void setRefObject(String refObject) {
        this.refObject = refObject;
    }

    public boolean isOrderEnable() {
        return this.orderEnable;
    }

    public void setOrderEnable(boolean orderEnable) {
        this.orderEnable = orderEnable;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_TYPE, (Object)this.type);
        if (this.dataType != null) {
            value.put(TAG_DATATYPE, this.dataType.value());
        }
        if (this.hierarchyType != null) {
            value.put(TAG_HIERARCHY_TYPE, this.hierarchyType.value());
        }
        if (this.refObject != null) {
            value.put(TAG_REFOBJECT, (Object)this.refObject);
        }
        if (this.dimType != null) {
            value.put(TAG_DIMTYPE, this.dimType.value());
        }
        if (StringUtils.isNotEmpty((String)this.dataPattern)) {
            value.put(TAG_DATAPATTERN, (Object)this.dataPattern);
        }
        if (StringUtils.isNotEmpty((String)this.showPattern)) {
            value.put(TAG_SHOWPATTERN, (Object)this.showPattern);
        }
        if (this.timegranularity != null) {
            value.put(TAG_TIMEGRANULARITY, this.timegranularity.value());
        }
        value.put(TAG_ISTIMEKEY, this.isTimekey);
        value.put(TAG_ORDER_ENABLE, this.orderEnable);
        this.saveExt(value);
    }

    public void load(JSONObject value) throws JSONException {
        this.type = value.getString(TAG_TYPE);
        if (!value.isNull(TAG_DATATYPE)) {
            this.dataType = DataType.valueOf(value.getInt(TAG_DATATYPE));
        }
        if (!value.isNull(TAG_HIERARCHY_TYPE)) {
            this.hierarchyType = ParameterHierarchyType.valueOf(value.getInt(TAG_HIERARCHY_TYPE));
        }
        if (!value.isNull(TAG_REFOBJECT)) {
            this.refObject = value.getString(TAG_REFOBJECT);
        }
        if (!value.isNull(TAG_DIMTYPE)) {
            int dimTypeValue = value.getInt(TAG_DIMTYPE);
            this.dimType = ParameterDimType.valueOf(dimTypeValue);
        }
        if (!value.isNull(TAG_DATAPATTERN)) {
            this.dataPattern = value.getString(TAG_DATAPATTERN);
        }
        if (!value.isNull(TAG_SHOWPATTERN)) {
            this.showPattern = value.getString(TAG_SHOWPATTERN);
        }
        if (!value.isNull(TAG_TIMEGRANULARITY)) {
            int timeGranularityValue = value.getInt(TAG_TIMEGRANULARITY);
            this.timegranularity = TimeGranularity.valueOf(timeGranularityValue);
        }
        if (!value.isNull(TAG_ISTIMEKEY)) {
            this.isTimekey = value.getBoolean(TAG_ISTIMEKEY);
        }
        if (!value.isNull(TAG_ORDER_ENABLE)) {
            this.orderEnable = value.getBoolean(TAG_ORDER_ENABLE);
        }
        this.loadExt(value);
    }

    public static String loadDataSourceType(JSONObject value) throws JSONException {
        return value.getString(TAG_TYPE);
    }

    public DataSourceModel clone() {
        try {
            return (DataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void saveExt(JSONObject var1) throws JSONException;

    protected abstract void loadExt(JSONObject var1) throws JSONException;

    public Map<String, String> getDefaultValueFilterModeExtend() {
        return new HashMap<String, String>();
    }
}

