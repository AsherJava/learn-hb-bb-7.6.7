/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import java.util.Date;
import java.util.HashMap;

public class DataRefDTO
extends HashMap<String, Object>
implements CacheEntity {
    private static final long serialVersionUID = -7448202809317069813L;

    public String getId() {
        return (String)this.get("ID");
    }

    public void setId(String id) {
        this.put("ID", id);
    }

    public String getDataSchemeCode() {
        return (String)this.get("DATASCHEMECODE");
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.put("DATASCHEMECODE", dataSchemeCode);
    }

    public String getOdsId() {
        return (String)this.get("ODS_ID");
    }

    public void setOdsId(String odsId) {
        this.put("ODS_ID", odsId);
    }

    public String getOdsCode() {
        return (String)this.get("ODS_CODE");
    }

    public void setOdsCode(String odsCode) {
        this.put("ODS_CODE", odsCode);
    }

    public String getOdsName() {
        return (String)this.get("ODS_NAME");
    }

    public void setOdsName(String odsName) {
        this.put("ODS_NAME", odsName);
    }

    public String getCode() {
        return (String)this.get("CODE");
    }

    public void setCode(String code) {
        this.put("CODE", code);
    }

    public String getName() {
        return (String)this.get("NAME");
    }

    public void setName(String name) {
        this.put("NAME", name);
    }

    public Integer getAutoMatchFlag() {
        Object autoMatchFlag = this.get("AUTOMATCHFLAG");
        if (autoMatchFlag != null) {
            if (autoMatchFlag instanceof Integer) {
                return (Integer)autoMatchFlag;
            }
            return Integer.parseInt(autoMatchFlag.toString());
        }
        return 0;
    }

    public void setAutoMatchFlag(Integer autoMatchFlag) {
        this.put("AUTOMATCHFLAG", autoMatchFlag);
    }

    public Integer getIdx() {
        Object idx = this.get("IDX");
        if (idx != null) {
            if (idx instanceof Integer) {
                return (Integer)idx;
            }
            return Integer.parseInt(idx.toString());
        }
        return 0;
    }

    public void setIdx(Integer idx) {
        this.put("IDX", idx);
    }

    public Boolean getIgnored() {
        Object ignored = this.get("IGNORED");
        if (ignored != null) {
            if (ignored instanceof Boolean) {
                return (Boolean)ignored;
            }
            return Boolean.parseBoolean(ignored.toString());
        }
        return null;
    }

    public void setIgnored(boolean ignored) {
        this.put("IGNORED", ignored);
    }

    public Boolean getMatched() {
        Object ignored = this.get("MATCHED");
        if (ignored != null) {
            if (ignored instanceof Boolean) {
                return (Boolean)ignored;
            }
            return Boolean.parseBoolean(ignored.toString());
        }
        return null;
    }

    public void setMatched(boolean ignored) {
        this.put("MATCHED", ignored);
    }

    public String getValueStr(String fieldName) {
        return (String)this.get(fieldName);
    }

    public String getTableName() {
        return (String)this.get("TABLENAME");
    }

    public void setTableName(String tableName) {
        this.put("TABLENAME", tableName);
    }

    public String getHandleStatus() {
        return this.getValueStr("HANDLESTATUS");
    }

    public void setHandleStatus(String handleStatus) {
        this.put("HANDLESTATUS", handleStatus);
    }

    public String getOperator() {
        return this.getValueStr("OPERATOR");
    }

    public void setOperator(String operator) {
        this.put("OPERATOR", operator);
    }

    public Date getOperateTime() {
        return (Date)this.get("OPERATETIME");
    }

    public void setOperateTime(Date operateTime) {
        this.put("OPERATETIME", operateTime);
    }

    public String getCacheKey() {
        return this.getDataSchemeCode() + "|" + this.getTableName() + "|" + this.getOdsId();
    }
}

