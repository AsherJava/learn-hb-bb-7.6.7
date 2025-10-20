/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.common.base.util.StringUtils
 *  org.apache.commons.lang3.time.FastDateFormat
 */
package com.jiuqi.dc.base.impl.ncell.org.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.FastDateFormat;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgToJsonVO {
    private String id;
    @Deprecated
    private String orgid;
    private String code;
    private String title;
    private String label;
    private String simpletitle;
    private String parentid;
    private boolean stopFlag;
    private boolean recoveryFlag;
    private String parentcode;
    private boolean stopflag;
    private boolean recoveryflag;
    @Deprecated
    private boolean leaf;
    private List<OrgToJsonVO> children;
    private Map<String, Object> datas;

    @Deprecated
    public boolean isLeaf() {
        return this.leaf;
    }

    @Deprecated
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getId() {
        return this.id == null ? StringUtils.toViewString((Object)this.getFieldValue("CODE")) : this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.setFieldValue("CODE", id);
    }

    @Deprecated
    public String getOrgid() {
        return this.orgid == null ? StringUtils.toViewString((Object)this.getFieldValue("ID")) : this.orgid;
    }

    @Deprecated
    public void setOrgid(String orgid) {
        this.orgid = orgid;
        this.setFieldValue("ID", orgid);
    }

    public String getCode() {
        return this.code == null ? StringUtils.toViewString((Object)this.getFieldValue("CODE")) : this.code;
    }

    public void setCode(String code) {
        this.code = code;
        this.setFieldValue("CODE", code);
    }

    public String getTitle() {
        return this.title == null ? StringUtils.toViewString((Object)this.getFieldValue("NAME")) : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.setFieldValue("NAME", title);
    }

    public String getLabel() {
        return this.label == null ? StringUtils.toViewString((Object)this.getFieldValue("SHORTNAME")) : this.label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.setFieldValue("SHORTNAME", label);
    }

    public String getSimpletitle() {
        return this.simpletitle == null ? StringUtils.toViewString((Object)this.getFieldValue("SHORTNAME")) : this.simpletitle;
    }

    public void setSimpletitle(String simpletitle) {
        this.simpletitle = simpletitle;
        this.setFieldValue("SHORTNAME", simpletitle);
    }

    public String getParentid() {
        return this.parentid == null ? StringUtils.toViewString((Object)this.getFieldValue("PARENTCODE")) : this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
        this.setFieldValue("PARENTCODE", parentid);
    }

    public boolean isStopFlag() {
        Object fieldValue = this.getFieldValue("STOPFLAG");
        if (fieldValue instanceof Boolean) {
            return (Boolean)fieldValue;
        }
        Integer v = (Integer)this.getFieldValue("STOPFLAG");
        return v == null ? this.stopFlag : v != 0;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
        this.setFieldValue("STOPFLAG", stopFlag ? 1 : 0);
    }

    public boolean isRecoveryFlag() {
        Object fieldValue = this.getFieldValue("RECOVERYFLAG");
        if (fieldValue instanceof Boolean) {
            return (Boolean)fieldValue;
        }
        Integer v = (Integer)this.getFieldValue("RECOVERYFLAG");
        return v == null ? this.recoveryFlag : v != 0;
    }

    public void setRecoveryFlag(boolean recoveryFlag) {
        this.recoveryFlag = recoveryFlag;
        this.setFieldValue("RECOVERYFLAG", recoveryFlag ? 1 : 0);
    }

    public Map<String, Object> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public List<OrgToJsonVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<OrgToJsonVO>();
        }
        return this.children;
    }

    public void setFieldValue(String key, Object value) {
        if (this.datas == null) {
            this.datas = new HashMap<String, Object>();
        }
        this.datas.put(key.trim().toUpperCase(), value);
    }

    public void setChildren(List<OrgToJsonVO> children) {
        this.children = children;
    }

    public void setUpdateTime(Date date) {
        String format = FastDateFormat.getInstance((String)"yyyy-MM-dd HH:mm:ss").format(date);
        if (this.datas == null) {
            this.datas = new HashMap<String, Object>();
        }
        this.datas.put("UPDATETIME", format);
    }

    public void setCreateTime(Date date) {
        String format = FastDateFormat.getInstance((String)"yyyy-MM-dd HH:mm:ss").format(date);
        if (this.datas == null) {
            this.datas = new HashMap<String, Object>();
        }
        this.datas.put("CREATETIME", format);
    }

    public Object getFieldValue(String name) {
        if (this.datas == null) {
            return null;
        }
        return this.datas.get(name.trim().toUpperCase());
    }

    public String getParents() {
        if (this.datas == null) {
            return null;
        }
        Object value = this.datas.get("PARENTS");
        return value == null ? null : String.valueOf(value.toString());
    }

    public Double getOrdinal() {
        if (this.datas == null) {
            return 0.0;
        }
        Object value = this.datas.get("ORDINAL");
        return value == null ? 0.0 : Double.valueOf(value.toString());
    }

    public String getBaseUnitId() {
        if (this.datas == null) {
            return null;
        }
        Object baseUnitId = this.datas.get("BASEUNITID");
        return StringUtils.toViewString((Object)baseUnitId);
    }

    public String getDiffUnitId() {
        if (this.datas == null) {
            return null;
        }
        Object baseUnitId = this.datas.get("DIFFUNITID");
        return StringUtils.toViewString((Object)baseUnitId);
    }

    public String getSplitUnitId() {
        if (this.datas == null) {
            return null;
        }
        Object baseUnitId = this.datas.get("SPLITID");
        return StringUtils.toViewString((Object)baseUnitId);
    }

    public boolean isRecoveryflag() {
        Object fieldValue = this.getFieldValue("RECOVERYFLAG");
        if (fieldValue instanceof Boolean) {
            return (Boolean)fieldValue;
        }
        Integer v = (Integer)this.getFieldValue("RECOVERYFLAG");
        return v == null ? this.recoveryFlag : v != 0;
    }

    public boolean isStopflag() {
        Object fieldValue = this.getFieldValue("STOPFLAG");
        if (fieldValue instanceof Boolean) {
            return (Boolean)fieldValue;
        }
        Integer v = (Integer)this.getFieldValue("STOPFLAG");
        return v == null ? this.stopFlag : v != 0;
    }

    public String getParentcode() {
        return this.parentid == null ? StringUtils.toViewString((Object)this.getFieldValue("PARENTCODE")) : this.parentid;
    }
}

