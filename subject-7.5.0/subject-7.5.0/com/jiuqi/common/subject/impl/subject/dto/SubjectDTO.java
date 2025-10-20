/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.common.subject.impl.subject.dto;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.cache.intf.CacheEntity;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class SubjectDTO
extends BaseDataDO
implements CacheEntity {
    public static final String TBMD_ACCTSUBJECT = "MD_ACCTSUBJECT";
    public static final String TBMD_ACCTSUBJECTCLASS = "MD_ACCTSUBJECTCLASS";
    public static final String FN_CODERULE = "4222222";
    public static final String FD_GENERALTYPE = "generaltype";
    public static final String FD_GENERALTYPEVO = "generaltypeVo";
    public static final String FD_ORIENT = "orient";
    private Integer orient;
    public static final String FD_ORIENTVO = "orientVo";
    public static final String FD_CURRENCY = "currency";
    public static final String FD_CURRENCYVO = "currencyVo";
    public static final String FD_ASSTYPE = "asstype";
    public static final String FD_ASSTYPEVO = "asstypeVo";
    public static final String FD_ASSTYPELIST = "asstypeList";
    public static final String FD_ASSTYPEMAP = "asstypeMap";
    public static final String FD_REMARK = "remark";
    public static final String FD_STOPCHILDITEM = "stopChildItem";

    public Object put(String key, Object value) {
        if (key.equals(FD_ORIENT) && !ObjectUtils.isEmpty(value)) {
            this.orient = value instanceof Integer ? (Integer)value : Integer.valueOf(Integer.parseInt(value.toString()));
        }
        this.putVal(key, value);
        return value;
    }

    public String getGeneralType() {
        return (String)this.get(FD_GENERALTYPE);
    }

    public void setGeneralType(String generalType) {
        this.put(FD_GENERALTYPE, (Object)generalType);
    }

    public void setGeneralTypeVo(String generalType) {
        this.put(FD_GENERALTYPEVO, (Object)generalType);
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.put(FD_ORIENT, (Object)orient);
    }

    public void setOrientVo(String orient) {
        this.put(FD_ORIENTVO, (Object)orient);
    }

    public String getCurrency() {
        return (String)this.get(FD_CURRENCY);
    }

    public void setCurrency(String currency) {
        this.put(FD_CURRENCY, (Object)currency);
    }

    public void setCurrencyVo(String currency) {
        this.put(FD_CURRENCYVO, (Object)currency);
    }

    public String getAssType() {
        String assType = (String)this.get(FD_ASSTYPE);
        if (StringUtils.isEmpty((String)assType)) {
            this.setAssType("{}");
        }
        return (String)this.get(FD_ASSTYPE);
    }

    public void setAssType(String assType) {
        this.put(FD_ASSTYPE, (Object)(StringUtils.isEmpty((String)assType) ? "{}" : assType));
    }

    public Map<String, Integer> getAssTypeMap() {
        Object object = this.get(FD_ASSTYPEMAP);
        if (object == null) {
            return null;
        }
        if (object instanceof Map) {
            return (Map)object;
        }
        throw new BusinessRuntimeException("\u7ef4\u5ea6\u53c2\u6570\u4e0d\u7b26\u5408\u89c4\u8303");
    }

    public void setAssTypeMap(Map<String, Integer> assTypeMap) {
        this.put(FD_ASSTYPEMAP, assTypeMap);
    }

    public List<String> getAssTypeList() {
        Object object = this.get(FD_ASSTYPELIST);
        if (object == null) {
            return null;
        }
        if (object instanceof List) {
            return (List)object;
        }
        return CollectionUtils.newArrayList();
    }

    public void setAssTypeList(List<String> assTypeList) {
        this.put(FD_ASSTYPELIST, assTypeList);
    }

    public void setAssTypeVo(String assTypeVo) {
        this.put(FD_ASSTYPEVO, (Object)assTypeVo);
    }

    public String getRemark() {
        return (String)this.get(FD_REMARK);
    }

    public void setRemark(String remark) {
        this.put(FD_REMARK, (Object)remark);
    }

    public void setStopChildItem(String stopChildItem) {
        this.put(FD_STOPCHILDITEM, (Object)stopChildItem);
    }

    public Boolean getStopChildItem() {
        return (Boolean)this.get(FD_STOPCHILDITEM);
    }

    @Override
    public String getCacheKey() {
        return this.getCode();
    }

    public Object getValueOf(String fieldName) {
        if (fieldName != null) {
            return this.get(fieldName.toLowerCase());
        }
        return null;
    }
}

