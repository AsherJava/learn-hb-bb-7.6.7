/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.db;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import java.lang.annotation.Annotation;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ExtDBField
implements DBAnno.DBField {
    private String dbField;
    private Class<?> appType = Object.class;
    private Class<?> dbType = Object.class;
    private String tranWith;
    private String getMethod;
    private String setMethod;

    public ExtDBField(String dbField) {
        this.dbField = dbField;
    }

    @Override
    public String dbField() {
        return this.dbField;
    }

    @Override
    public String get() {
        return this.getMethod;
    }

    @Override
    public String set() {
        return this.setMethod;
    }

    @Override
    public String tranWith() {
        return this.tranWith;
    }

    @Override
    public Class<?> appType() {
        return this.appType;
    }

    @Override
    public Class<?> dbType() {
        return this.dbType;
    }

    @Override
    public boolean isPk() {
        return false;
    }

    @Override
    public boolean isOrder() {
        return false;
    }

    @Override
    public boolean notUpdate() {
        return false;
    }

    @Override
    public boolean autoDate() {
        return false;
    }

    @Override
    public boolean upper() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    public String getDbField() {
        return this.dbField;
    }

    public void setDbField(String dbField) {
        this.dbField = dbField;
    }

    public Class<?> getAppType() {
        return this.appType;
    }

    public void setAppType(Class<?> appType) {
        this.appType = appType;
    }

    public Class<?> getDbType() {
        return this.dbType;
    }

    public void setDbType(Class<?> dbType) {
        this.dbType = dbType;
    }

    public String getTranWith() {
        return this.tranWith;
    }

    public void setTranWith(String tranWith) {
        this.tranWith = tranWith;
    }

    public String getGetMethod() {
        return this.getMethod;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public String getSetMethod() {
        return this.setMethod;
    }

    public void setSetMethod(String setMethod) {
        this.setMethod = setMethod;
    }

    public static ExtDBField valueOf(PropInfo propInfo) {
        ExtDBField extDBField = new ExtDBField(propInfo.getFieldName());
        switch (propInfo.getDataType()) {
            case INTEGER: {
                extDBField.setDbType(Integer.class);
                extDBField.setAppType(Integer.class);
                break;
            }
            case DOUBLE: 
            case BIG_DECIMAL: {
                extDBField.setDbType(Double.class);
                extDBField.setAppType(Double.class);
                break;
            }
            case BOOLEAN: {
                extDBField.setDbType(Integer.class);
                extDBField.setAppType(Boolean.class);
                extDBField.setTranWith("transBoolean");
                break;
            }
            case DATETIME: {
                extDBField.setDbType(Timestamp.class);
                extDBField.setAppType(Instant.class);
                extDBField.setTranWith("transTimeStamp2");
                break;
            }
            case CLOB: {
                extDBField.setDbType(Clob.class);
                extDBField.setAppType(String.class);
                break;
            }
            case BLOB: {
                extDBField.setDbType(String.class);
                extDBField.setAppType(String.class);
                break;
            }
            case STRING: {
                if (!propInfo.isMultiple()) break;
                extDBField.setAppType(List.class);
                extDBField.setDbType(List.class);
                extDBField.setTranWith("transListJson");
                break;
            }
        }
        return extDBField;
    }
}

