/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.StringUtils;

public class GcOffsetExecutorVO {
    private String actionCode;
    private String pageCode;
    private String dataSourceCode;
    private String filterMethod;
    private Map<String, Object> extendMap;
    private Object paramObject;

    public GcOffsetExecutorVO() {
    }

    public GcOffsetExecutorVO(String actionCode, String pageCode, String dataSourceCode, String filterMethod) {
        this(actionCode, pageCode, dataSourceCode, filterMethod, null);
    }

    public GcOffsetExecutorVO(String actionCode, String pageCode, String dataSourceCode, String filterMethod, Object paramObject) {
        this.actionCode = actionCode;
        this.pageCode = pageCode;
        this.dataSourceCode = dataSourceCode;
        this.filterMethod = filterMethod;
        this.paramObject = paramObject;
        this.extendMap = new HashMap<String, Object>();
    }

    public Object getParamObject() {
        return this.paramObject;
    }

    public void setParamObject(Object paramObject) {
        this.paramObject = paramObject;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getPageCode() {
        return this.pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getFilterMethod() {
        return this.filterMethod;
    }

    public void setFilterMethod(String filterMethod) {
        this.filterMethod = filterMethod;
    }

    public Map<String, Object> getExtendMap() {
        return this.extendMap;
    }

    public void setExtendMap(Map<String, Object> extendMap) {
        this.extendMap = extendMap;
    }

    public void addExtend(String key, Object value) {
        this.extendMap.put(key, value);
    }

    public Object getExtend(String key) {
        return this.extendMap.get(key);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GcOffsetExecutorVO that = (GcOffsetExecutorVO)o;
        return Objects.equals(this.actionCode, that.actionCode) && Objects.equals(this.pageCode, that.pageCode) && (!StringUtils.hasLength(this.dataSourceCode) && !StringUtils.hasLength(that.dataSourceCode) || StringUtils.hasLength(this.dataSourceCode) && StringUtils.hasLength(that.dataSourceCode) && Objects.equals(this.dataSourceCode, that.dataSourceCode)) && Objects.equals(this.filterMethod, that.filterMethod);
    }

    public String toString() {
        return "GcOffsetExecutorVO{actionCode='" + this.actionCode + '\'' + ", pageCode='" + this.pageCode + '\'' + ", dataSourceCode='" + this.dataSourceCode + '\'' + ", filterMethod='" + this.filterMethod + '\'' + ", extendMap=" + this.extendMap + ", paramObject=" + this.paramObject + '}';
    }
}

