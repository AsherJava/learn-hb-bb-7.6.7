/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.common.CalibreTableColumn;
import com.jiuqi.nr.calibre2.domain.CalibreExpressionDO;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.HashMap;
import java.util.Map;

public class CalibreDataDO {
    private String key;
    private String code;
    private String name;
    private String parent;
    private long order;
    private CalibreExpressionDO value;
    private String calibreCode;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public long getOrder() {
        return this.order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public CalibreExpressionDO getValue() {
        return this.value;
    }

    public void setValue(CalibreExpressionDO value) {
        this.value = value;
    }

    public String getCalibreCode() {
        return this.calibreCode;
    }

    public void setCalibreCode(String calibreCode) {
        this.calibreCode = calibreCode;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>(7);
        map.put(CalibreDataMapper.FIELD_KEY, this.getKey());
        map.put(CalibreDataMapper.FIELD_CODE, this.getCode());
        map.put(CalibreDataMapper.FIELD_NAME, this.getName());
        map.put(CalibreDataMapper.FIELD_PARENT, this.getParent());
        map.put(CalibreDataMapper.FIELD_ORDER, this.getOrder());
        map.put(CalibreDataMapper.FIELD_VALUE, JacksonUtils.objectToJson((Object)this.getValue()));
        map.put(CalibreDataMapper.FIELD_CALIBRE_CODE, this.getCalibreCode());
        return map;
    }

    public String toString() {
        return "CalibreDataDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", name='" + this.name + '\'' + ", parent='" + this.parent + '\'' + ", order='" + this.order + '\'' + ", value=" + this.value + ", calibreCode='" + this.calibreCode + '\'' + '}';
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CalibreDataDO other = (CalibreDataDO)obj;
        return !(this.key == null ? other.key != null : !this.key.equals(other.key));
    }

    public Object getValue(String key) {
        if (CalibreTableColumn.KEY.getCode().equals(key)) {
            return this.key;
        }
        if (CalibreTableColumn.CODE.getCode().equals(key)) {
            return this.code;
        }
        if (CalibreTableColumn.NAME.getCode().equals(key)) {
            return this.name;
        }
        if (CalibreTableColumn.PARENT.getCode().equals(key)) {
            return this.parent;
        }
        if (CalibreTableColumn.ORDER.getCode().equals(key)) {
            return this.order;
        }
        if (CalibreTableColumn.VALUE.getCode().equals(key)) {
            return this.value;
        }
        if (CalibreTableColumn.CALIBRE_CODE.getCode().equals(key)) {
            return this.calibreCode;
        }
        return null;
    }
}

