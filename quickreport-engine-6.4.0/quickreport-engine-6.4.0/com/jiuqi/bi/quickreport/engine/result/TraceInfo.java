/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.syntax.DataType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TraceInfo
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private String dataSetName;
    private String expression;
    private int traceType;
    private List<String> filters = new ArrayList<String>();
    private int dataType;
    private transient Object value;
    private String showPattern;
    private static final String TRACE_DSNAME = "dsName";
    private static final String TRACE_EXPR = "expr";
    private static final String TRACE_TYPE = "traceType";
    private static final String TRACE_DATATYPE = "dataType";
    private static final String TRACE_FILTERS = "filters";
    private static final String TRACE_VALUE = "value";
    private static final String TRACE_PATTERN = "pattern";
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_PREV_PERIOD = 1;
    public static final int TYPE_PREV_YEAR = 2;

    public String getDataSetName() {
        return this.dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getTraceType() {
        return this.traceType;
    }

    public void setTraceType(int traceType) {
        this.traceType = traceType;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getShowPattern() {
        return this.showPattern;
    }

    public void setShowPattern(String showPattern) {
        this.showPattern = showPattern;
    }

    public List<String> getFilters() {
        return this.filters;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(TRACE_DSNAME, (Object)this.dataSetName);
        json.put(TRACE_EXPR, (Object)this.expression);
        json.put(TRACE_TYPE, this.traceType);
        json.put(TRACE_DATATYPE, this.dataType);
        json.put(TRACE_FILTERS, this.filters);
        if (this.value != null) {
            if (this.dataType == 2) {
                json.put(TRACE_VALUE, (Object)DataType.formatValue((int)this.dataType, (Object)this.value));
            } else {
                json.put(TRACE_VALUE, this.value);
            }
        }
        json.put(TRACE_PATTERN, (Object)this.showPattern);
        return json;
    }

    public void fromJSONObject(JSONObject json) {
        this.dataSetName = json.getString(TRACE_DSNAME);
        this.expression = json.getString(TRACE_EXPR);
        this.traceType = json.getInt(TRACE_TYPE);
        this.dataType = json.getInt(TRACE_DATATYPE);
        JSONArray arr = json.getJSONArray(TRACE_FILTERS);
        this.filters.clear();
        for (int i = 0; i < arr.length(); ++i) {
            this.filters.add(arr.getString(i));
        }
        if (json.has(TRACE_VALUE)) {
            switch (this.dataType) {
                case 6: {
                    this.value = json.getString(TRACE_VALUE);
                    break;
                }
                case 3: 
                case 5: 
                case 8: {
                    this.value = json.getNumber(TRACE_VALUE);
                    break;
                }
                case 10: {
                    this.value = json.getBigDecimal(TRACE_VALUE);
                    break;
                }
                case 1: {
                    this.value = json.getBoolean(TRACE_VALUE);
                    break;
                }
                case 2: {
                    String date = json.getString(TRACE_VALUE);
                    this.value = DataType.parseValue((int)this.dataType, (String)date);
                    break;
                }
                default: {
                    this.value = json.get(TRACE_VALUE);
                    break;
                }
            }
        } else {
            this.value = null;
        }
        this.showPattern = json.optString(TRACE_PATTERN);
    }

    public TraceInfo clone() {
        TraceInfo result;
        try {
            result = (TraceInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        result.filters = new ArrayList<String>(this.filters);
        return result;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.expression).append(" = ").append(this.value);
        return buffer.toString();
    }
}

