/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.single.map.data;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.HashMap;
import java.util.Map;

public class DataEntityInfo {
    private String entityKey;
    private String entityTitle;
    private String entityCode;
    private String entityOrgCode;
    private String entityExpCode;
    private String entityRuleCode;
    private String entityExCode;
    private String expEntityExCode;
    private String entityRowCaption;
    private String entityParentKey;
    private String entityAppendCode;
    private Object entintyRow;
    private String singleZdm;
    private String singleZdmWithOutPeriod;
    private String parentZdm;
    private String singleDwdm;
    private String singleBblx;
    private String expSingleZdm;
    private boolean isUnitMap;
    Map<String, String> fieldValues;
    private Map<String, DimensionValue> singleDimValueSet;

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public String getEntityRowCaption() {
        return this.entityRowCaption;
    }

    public void setEntityRowCaption(String entityRowCaption) {
        this.entityRowCaption = entityRowCaption;
    }

    public String getEntityParentKey() {
        return this.entityParentKey;
    }

    public void setEntityParentKey(String entityParentKey) {
        this.entityParentKey = entityParentKey;
    }

    public Object getEntintyRow() {
        return this.entintyRow;
    }

    public void setEntintyRow(Object entintyRow) {
        this.entintyRow = entintyRow;
    }

    public String getSingleZdm() {
        return this.singleZdm;
    }

    public void setSingleZdm(String singleZdm) {
        this.singleZdm = singleZdm;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityExCode() {
        return this.entityExCode;
    }

    public void setEntityExCode(String entityExCode) {
        this.entityExCode = entityExCode;
    }

    public String getExpEntityExCode() {
        return this.expEntityExCode;
    }

    public void setExpEntityExCode(String expEntityExCode) {
        this.expEntityExCode = expEntityExCode;
    }

    public String getExpSingleZdm() {
        return this.expSingleZdm;
    }

    public void setExpSingleZdm(String expSingleZdm) {
        this.expSingleZdm = expSingleZdm;
    }

    public boolean getIsUnitMap() {
        return this.isUnitMap;
    }

    public void setIsUnitMap(boolean isUnitMap) {
        this.isUnitMap = isUnitMap;
    }

    public String getEntityAppendCode() {
        return this.entityAppendCode;
    }

    public void setEntityAppendCode(String entityAppendCode) {
        this.entityAppendCode = entityAppendCode;
    }

    public String getEntityAutoExCode() {
        return this.entityExCode + this.entityAppendCode;
    }

    public String getParentZdm() {
        return this.parentZdm;
    }

    public void setParentZdm(String parentZdm) {
        this.parentZdm = parentZdm;
    }

    public String getSingleDwdm() {
        return this.singleDwdm;
    }

    public void setSingleDwdm(String singleDwdm) {
        this.singleDwdm = singleDwdm;
    }

    public String getSingleBblx() {
        return this.singleBblx;
    }

    public void setSingleBblx(String singleBblx) {
        this.singleBblx = singleBblx;
    }

    public Map<String, String> getFieldValues() {
        if (this.fieldValues == null) {
            this.fieldValues = new HashMap<String, String>();
        }
        return this.fieldValues;
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getEntityExpCode() {
        return this.entityExpCode;
    }

    public void setEntityExpCode(String entityExpCode) {
        this.entityExpCode = entityExpCode;
    }

    public Map<String, DimensionValue> getSingleDimValueSet() {
        if (this.singleDimValueSet == null) {
            this.singleDimValueSet = new HashMap<String, DimensionValue>();
        }
        return this.singleDimValueSet;
    }

    public void setSingleDimValueSet(Map<String, DimensionValue> singleDimValueSet) {
        this.singleDimValueSet = singleDimValueSet;
    }

    public String getEntityRuleCode() {
        return this.entityRuleCode;
    }

    public void setEntityRuleCode(String entityRuleCode) {
        this.entityRuleCode = entityRuleCode;
    }

    public String getEntityOrgCode() {
        return this.entityOrgCode;
    }

    public void setEntityOrgCode(String entityOrgCode) {
        this.entityOrgCode = entityOrgCode;
    }

    public String getSingleZdmWithOutPeriod() {
        return this.singleZdmWithOutPeriod;
    }

    public void setSingleZdmWithOutPeriod(String singleZdmWithOutPeriod) {
        this.singleZdmWithOutPeriod = singleZdmWithOutPeriod;
    }
}

