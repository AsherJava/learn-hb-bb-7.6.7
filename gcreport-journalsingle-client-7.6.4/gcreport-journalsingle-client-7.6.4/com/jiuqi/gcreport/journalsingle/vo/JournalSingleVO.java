/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 */
package com.jiuqi.gcreport.journalsingle.vo;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JournalSingleVO {
    private String id;
    private String mRecid;
    private String taskId;
    private String schemeId;
    private String adjust;
    private String effectType;
    private String adjustTypeCode;
    private int acctYear;
    private int acctPeriod;
    private int enterPeriod;
    private String defaultPeriod;
    private int periodType;
    private String inputUnitId;
    private String unitName;
    private String unitVersion;
    private int sortOrder;
    private int dc;
    private BaseDataVO subjectVo;
    private String subjectCode;
    private String subjectName;
    private Double debit;
    private Double credit;
    private String currencyCode;
    private String memo;
    private String postFlagTitle;
    private String adjType;
    private int srcType;
    private String srcID;
    private String createUser;
    private Date createDate;
    private Date createTime;
    private Map<String, Object> unSysFields = new HashMap<String, Object>();
    private boolean isEdit;
    private int groupIndex;

    public int getGroupIndex() {
        return this.groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(String adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitVersion() {
        return this.unitVersion;
    }

    public void setUnitVersion(String unitVersion) {
        this.unitVersion = unitVersion;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getDc() {
        return this.dc;
    }

    public void setDc(int dc) {
        this.dc = dc;
    }

    public BaseDataVO getSubjectVo() {
        return this.subjectVo;
    }

    public void setSubjectVo(BaseDataVO subjectVo) {
        this.subjectVo = subjectVo;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAdjType() {
        return this.adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public int getSrcType() {
        return this.srcType;
    }

    public void setSrcType(int srcType) {
        this.srcType = srcType;
    }

    public String getSrcID() {
        return this.srcID;
    }

    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public <T> T map2JavaBean(Class<T> clazz, Map<String, Object> oneRowRecordMap) throws Exception {
        Method[] methods;
        T javabean = clazz.newInstance();
        for (Method method : methods = clazz.getMethods()) {
            if (!method.getName().startsWith("set")) continue;
            Class<?> parameterType = method.getParameterTypes()[0];
            String field = method.getName();
            field = field.substring(field.indexOf("set") + 3);
            Object mapValue = oneRowRecordMap.remove(field = field.toLowerCase().charAt(0) + field.substring(1));
            if (null == mapValue) continue;
            Object value = mapValue;
            String mapValueStr = null;
            if (this.isPrimitive(value) || "UUID".equals(parameterType.getSimpleName())) {
                if (!(mapValue instanceof String)) {
                    mapValueStr = String.valueOf(mapValue);
                } else if (null != mapValue) {
                    mapValueStr = mapValue.toString();
                }
                switch (parameterType.getSimpleName()) {
                    case "UUID": {
                        value = mapValueStr;
                        break;
                    }
                    case "Integer": 
                    case "int": {
                        value = Integer.valueOf(mapValueStr);
                        break;
                    }
                    case "boolean": 
                    case "Boolean": {
                        value = Boolean.valueOf(mapValueStr);
                        break;
                    }
                    case "Double": {
                        value = Double.valueOf(mapValueStr);
                        break;
                    }
                    case "Date": {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        try {
                            value = formatter.parse(mapValueStr);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                method.invoke(javabean, value);
                continue;
            }
            try {
                method.invoke(javabean, value);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        HashMap<String, Object> unSysFields = new HashMap<String, Object>();
        for (Map.Entry<String, Object> oneRowRecord : oneRowRecordMap.entrySet()) {
            if (oneRowRecord.getValue() instanceof Map) {
                unSysFields.put(oneRowRecord.getKey(), ((Map)oneRowRecord.getValue()).get("code"));
                continue;
            }
            unSysFields.put(oneRowRecord.getKey(), oneRowRecord.getValue());
        }
        ReflectionUtils.setFieldValue(javabean, (String)"unSysFields", unSysFields);
        return javabean;
    }

    private boolean isPrimitive(Object obj) {
        try {
            return ((Class)obj.getClass().getField("TYPE").get(null)).isPrimitive();
        }
        catch (Exception e) {
            return false;
        }
    }

    public void setUnSysFields(Map<String, Object> unSysFields) {
        this.unSysFields = unSysFields;
    }

    public Map<String, Object> getUnSysFields() {
        return this.unSysFields;
    }

    public void addUnSysFieldValue(String field, Object value) {
        if (this.unSysFields == null) {
            this.unSysFields = new HashMap<String, Object>();
        }
        this.unSysFields.put(field, value);
    }

    public Object getUnSysFieldValue(String field) {
        if (this.unSysFields == null) {
            return null;
        }
        return this.unSysFields.get(field);
    }

    public String getPostFlagTitle() {
        return this.postFlagTitle;
    }

    public void setPostFlagTitle(String postFlagTitle) {
        this.postFlagTitle = postFlagTitle;
    }

    public int getEnterPeriod() {
        return this.enterPeriod;
    }

    public void setEnterPeriod(int enterPeriod) {
        this.enterPeriod = enterPeriod;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}

