/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.DummyObjType
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.masterdata.basedata.enums.BaseDataDefineShareType;
import com.jiuqi.budget.masterdata.basedata.enums.BaseDataDefineStructType;
import com.jiuqi.budget.masterdata.basedata.enums.BaseDataQueryType;
import com.jiuqi.budget.masterdata.intf.FBaseDataDefine;
import com.jiuqi.va.basedata.common.DummyObjType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDataDefine
implements FBaseDataDefine {
    private String id;
    private String code;
    private String name;
    private String parentCode;
    private BigDecimal orderNum;
    private String remark;
    private String creator;
    private BaseDataDefineShareType shareType;
    private BaseDataDefineStructType structType;
    private boolean multiVersion;
    private boolean dimensionflag;
    private String shareFieldName;
    private DummyObjType dummyObjType;
    private String dummySource;
    private List<String> extFields;
    private List<Map<String, Object>> defaultShowColumns;
    private String define;
    private BaseDataQueryType baseDataQueryType = BaseDataQueryType.BASEDATA;
    private Map<String, Object> extInfo;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getParentCode() {
        return this.parentCode;
    }

    @Override
    public BigDecimal getOrderNum() {
        return this.orderNum;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public BaseDataDefineShareType getShareType() {
        return this.shareType;
    }

    public void setShareType(BaseDataDefineShareType shareType) {
        this.shareType = shareType;
    }

    @Override
    public BaseDataDefineStructType getStructType() {
        return this.structType;
    }

    public void setStructType(BaseDataDefineStructType structType) {
        this.structType = structType;
    }

    @Override
    public boolean isMultiVersion() {
        return this.multiVersion;
    }

    public void setMultiVersion(boolean multiVersion) {
        this.multiVersion = multiVersion;
    }

    @Override
    public boolean isDimensionflag() {
        return this.dimensionflag;
    }

    public void setDimensionflag(boolean dimensionflag) {
        this.dimensionflag = dimensionflag;
    }

    @Override
    public String getShareFieldName() {
        return this.shareFieldName;
    }

    public void setShareFieldName(String shareFieldName) {
        this.shareFieldName = shareFieldName;
    }

    @Override
    public DummyObjType getDummyObjType() {
        return this.dummyObjType;
    }

    public void setDummyObjType(DummyObjType dummyObjType) {
        this.dummyObjType = dummyObjType;
    }

    @Override
    public String getDummySource() {
        return this.dummySource;
    }

    public void setDummySource(String dummySource) {
        this.dummySource = dummySource;
    }

    public List<String> getExtFields() {
        return this.extFields;
    }

    public void setExtFields(List<String> extFields) {
        this.extFields = extFields;
    }

    @Override
    public List<Map<String, Object>> getDefaultShowColumns() {
        return this.defaultShowColumns;
    }

    public void setDefaultShowColumns(List<Map<String, Object>> defaultShowColumns) {
        this.defaultShowColumns = defaultShowColumns;
    }

    @Override
    public String getDefine() {
        return this.define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void addExtInfo(String key, Object value) {
        if (this.extInfo == null) {
            this.extInfo = new HashMap<String, Object>();
        }
        this.extInfo.put(key, value);
    }

    public BaseDataQueryType getBaseDataQueryType() {
        return this.baseDataQueryType;
    }

    public void setBaseDataQueryType(BaseDataQueryType baseDataQueryType) {
        this.baseDataQueryType = baseDataQueryType;
    }
}

