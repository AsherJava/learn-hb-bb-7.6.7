/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.quickreport.QuickReportError;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TableField
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -9044005996955381695L;
    private String name;
    private String title;
    private DataType dataType;
    private int decimal;
    private int precision;
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DATATYPE = "dataType";
    private static final String TAG_DECIMAL = "decimal";
    private static final String TAG_PRECISION = "precision";

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean equals(Object obj) {
        TableField field;
        if (obj == null) {
            return false;
        }
        return obj instanceof TableField && (field = (TableField)obj).getName().equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public void save(JSONObject fieldObj) throws JSONException {
        fieldObj.put(TAG_NAME, (Object)this.name);
        fieldObj.put(TAG_TITLE, (Object)this.title);
        if (this.dataType != null) {
            fieldObj.put(TAG_DATATYPE, this.dataType.value());
        } else {
            fieldObj.put(TAG_DATATYPE, (Object)DataType.UNKNOWN);
        }
        fieldObj.put(TAG_DECIMAL, this.decimal);
        fieldObj.put(TAG_PRECISION, this.precision);
    }

    public void load(JSONObject fieldObj) throws JSONException {
        this.name = fieldObj.getString(TAG_NAME);
        this.title = fieldObj.getString(TAG_TITLE);
        int dataTypeValue = fieldObj.getInt(TAG_DATATYPE);
        this.dataType = DataType.valueOf((int)dataTypeValue);
        if (!fieldObj.isNull(TAG_DECIMAL)) {
            this.decimal = fieldObj.getInt(TAG_DECIMAL);
        }
        if (!fieldObj.isNull(TAG_PRECISION)) {
            this.precision = fieldObj.getInt(TAG_PRECISION);
        }
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public String toString() {
        return this.name;
    }
}

