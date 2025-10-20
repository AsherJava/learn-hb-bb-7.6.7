/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import org.json.JSONException;
import org.json.JSONObject;

public class LogicField
implements Cloneable {
    private String fieldName;
    private String fieldTitle;
    private int dataType;
    private String dataTypeName;
    private int rawType;
    private boolean isSupportI18n;
    private int size;
    private int scale;
    private int precision = -1;
    private boolean nullable;
    private String defaultValue;
    private int alterOptions = 0;
    private int displaySize;
    public static final int IGNORE_FIELDTYPE_WHEN_ALTER = 2;
    public static final int IGNORE_NULLABLE_WHEN_ALTER = 4;
    public static final int IGNORE_DEFAULTVALUE_WHEN_ALTER = 8;
    public static final int FIELD_SIZE_MAX = -100;
    private static final String FIELDNAME = "fieldName";
    private static final String FIELDTITLE = "fieldTitle";
    private static final String DATATYPE = "dataType";
    private static final String RAWTYPE = "rawType";
    private static final String DATATYPENAME = "dataTypeName";
    private static final String SUPPORTI18N = "isSupportI18n";
    private static final String SIZE = "size";
    private static final String SCALE = "scale";
    private static final String PRECISION = "precision";
    private static final String NULLABLE = "nullable";
    private static final String DEFAULTVALUE = "defaultValue";
    private static final String DISPLAY_SIZE = "displaySize";

    public String getFieldName() {
        return this.fieldName;
    }

    public String getOriginalFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setRawType(int rawType) {
        this.rawType = rawType;
    }

    public int getRawType() {
        return this.rawType;
    }

    public String getDataTypeName() {
        return this.dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSupportI18n() {
        return this.isSupportI18n;
    }

    public void setSupportI18n(boolean isSupportI18n) {
        this.isSupportI18n = isSupportI18n;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(FIELDNAME, this.fieldName == null ? JSONObject.NULL : this.fieldName);
        json.put(FIELDTITLE, this.fieldTitle == null ? JSONObject.NULL : this.fieldTitle);
        json.put(NULLABLE, this.nullable);
        json.put(DATATYPE, this.dataType);
        json.put(DATATYPENAME, this.dataTypeName == null ? JSONObject.NULL : this.dataTypeName);
        json.put(SUPPORTI18N, this.isSupportI18n);
        json.put(PRECISION, this.precision);
        json.put(SIZE, this.size);
        json.put(SCALE, this.scale);
        json.put(DEFAULTVALUE, this.defaultValue == null ? JSONObject.NULL : this.defaultValue);
        json.put(RAWTYPE, this.rawType);
        json.put(DISPLAY_SIZE, this.displaySize);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.fieldName = json.optString(FIELDNAME);
        this.fieldTitle = json.optString(FIELDTITLE);
        this.dataType = json.getInt(DATATYPE);
        this.dataTypeName = json.optString(DATATYPENAME);
        this.isSupportI18n = json.getBoolean(SUPPORTI18N);
        this.size = json.optInt(SIZE);
        this.scale = json.getInt(SCALE);
        this.precision = json.getInt(PRECISION);
        this.nullable = json.getBoolean(NULLABLE);
        this.defaultValue = json.optString(DEFAULTVALUE);
        this.rawType = json.optInt(RAWTYPE);
    }

    public LogicField clone() {
        try {
            return (LogicField)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public int getAlterOptions() {
        return this.alterOptions;
    }

    public void setAlterOptions(int options) {
        this.alterOptions |= options;
    }

    public boolean isIgnoreType() {
        return (this.alterOptions & 2) == 2;
    }

    public void setIgnoreType(boolean ignoreType) {
        this.alterOptions = ignoreType ? (this.alterOptions |= 2) : (this.alterOptions &= 0xFFFFFFFD);
    }

    public boolean isIgnoreNullable() {
        return (this.alterOptions & 4) == 4;
    }

    public void setIgnoreNullable(boolean ignoreNullable) {
        this.alterOptions = ignoreNullable ? (this.alterOptions |= 4) : (this.alterOptions &= 0xFFFFFFFB);
    }

    public boolean isIgnoreDefaultValue() {
        return (this.alterOptions & 8) == 8;
    }

    public void setIgnoreDefaultValue(boolean ignoreDefaultValue) {
        this.alterOptions = ignoreDefaultValue ? (this.alterOptions |= 8) : (this.alterOptions &= 0xFFFFFFF7);
    }

    public int getDisplaySize() {
        return this.displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }
}

