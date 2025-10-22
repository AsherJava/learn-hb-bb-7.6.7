/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.AggregationType
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryParentType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.nr.bql.datasource.reader;

import com.jiuqi.bi.adhoc.model.AggregationType;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.column.DatatimeColumnReader;
import com.jiuqi.nr.bql.datasource.reader.column.EntityKeyColumnReader;
import com.jiuqi.nr.bql.datasource.reader.column.HOrderColumnReader;
import com.jiuqi.nr.bql.datasource.reader.column.MainDimTitleColumnReader;
import com.jiuqi.nr.bql.datasource.reader.column.ParentCodeColumnReader;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryColumnInfo {
    private Column<ColumnInfo> metaColumn;
    private int fieldIndex;
    private boolean isDataTimeField;
    private boolean isHOrderField;
    private Map<String, Integer> orderCache;
    private String refDimensionName;
    private boolean ignoreValue;
    private boolean isParentCode;
    private boolean isObjectCode;
    private boolean isEntityKey;
    private boolean resetMainDimTitle = false;
    private IEntityDefine entityDefine;
    private ColumnModelDefine dataColumnModel;
    private Map<String, String> objectCodeMap;
    private BaseDataDTO baseDataDTO;
    private BaseDataClient baseDataClient;
    private int objectCodeFieldIndex = -1;
    private boolean isMeasure = false;
    private AggregationType aggregationType;
    private QueryColumnReader columnReader;

    public QueryColumnInfo(Column<ColumnInfo> metaColumn, int fieldIndex) {
        this.metaColumn = metaColumn;
        this.fieldIndex = fieldIndex;
    }

    public void initColumnReader(QueryContext qContext, List<QueryColumnInfo> columnInfos) {
        this.columnReader = this.isDataTimeField() ? new DatatimeColumnReader(qContext, this) : (this.isHOrderField() && this.getEntityDefine() != null ? new HOrderColumnReader(qContext, this) : (this.isParentCode() ? new ParentCodeColumnReader(qContext, this, columnInfos) : (this.isResetMainDimTitle() ? new MainDimTitleColumnReader(qContext, this, columnInfos) : (this.isEntityKey() ? new EntityKeyColumnReader(qContext, this) : new QueryColumnReader(qContext, this)))));
    }

    public Object readData(IDataRow dataRow) {
        return this.columnReader.readData(dataRow);
    }

    public boolean isResetParentCode() {
        return this.entityDefine != null && this.entityDefine.getIsolation() != 0;
    }

    public String getParentObjectCode(String objectcode) {
        String parentObjectCode;
        if (this.objectCodeMap == null) {
            this.objectCodeMap = new HashMap<String, String>();
            this.baseDataDTO = new BaseDataDTO();
            this.baseDataDTO.setTableName(this.entityDefine.getDimensionName());
            this.baseDataDTO.setQueryParentType(BaseDataOption.QueryParentType.DIRECT_PARENT);
        }
        if ((parentObjectCode = this.objectCodeMap.get(objectcode)) == null) {
            this.baseDataDTO.setObjectcode(objectcode);
            PageVO pageVO = this.baseDataClient.list(this.baseDataDTO);
            if (pageVO != null && pageVO.getTotal() > 0) {
                List list = pageVO.getRows();
                parentObjectCode = ((BaseDataDO)list.get(0)).getObjectcode();
                this.objectCodeMap.put(objectcode, parentObjectCode);
            }
        }
        return parentObjectCode;
    }

    public Column<ColumnInfo> getMetaColumn() {
        return this.metaColumn;
    }

    public void setMetaColumn(Column<ColumnInfo> metaColumn) {
        this.metaColumn = metaColumn;
    }

    public int getFieldIndex() {
        return this.fieldIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public boolean isDataTimeField() {
        return this.isDataTimeField;
    }

    public void setDataTimeField(boolean isDataTimeField) {
        if (isDataTimeField) {
            this.isMeasure = false;
        }
        this.isDataTimeField = isDataTimeField;
    }

    public boolean isHOrderField() {
        return this.isHOrderField;
    }

    public void setHOrderField(boolean isHOrderField) {
        this.isHOrderField = isHOrderField;
    }

    public Map<String, Integer> getOrderCache() {
        return this.orderCache;
    }

    public void setOrderCache(Map<String, Integer> orderCache) {
        this.orderCache = orderCache;
    }

    public String getRefDimensionName() {
        return this.refDimensionName;
    }

    public void setRefDimensionName(String refDimensionName) {
        this.refDimensionName = refDimensionName;
    }

    public boolean isIgnoreValue() {
        return this.ignoreValue;
    }

    public void setIgnoreValue(boolean ignoreValue) {
        this.ignoreValue = ignoreValue;
    }

    public boolean isParentCode() {
        return this.isParentCode;
    }

    public void setParentCode(boolean isParentCode) {
        this.isParentCode = isParentCode;
    }

    public boolean isObjectCode() {
        return this.isObjectCode;
    }

    public void setObjectCode(boolean isObjectCode) {
        this.isObjectCode = isObjectCode;
    }

    public IEntityDefine getEntityDefine() {
        return this.entityDefine;
    }

    public void setEntityDefine(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
    }

    public void setBaseDataClient(BaseDataClient baseDataClient) {
        this.baseDataClient = baseDataClient;
    }

    public int getObjectCodeFieldIndex() {
        return this.objectCodeFieldIndex;
    }

    public void setObjectCodeFieldIndex(int objectCodeFieldIndex) {
        this.objectCodeFieldIndex = objectCodeFieldIndex;
    }

    public boolean isMeasure() {
        return this.isMeasure;
    }

    public void setMeasure(boolean isMeasure) {
        this.isMeasure = isMeasure;
    }

    public AggregationType getAggregationType() {
        return this.aggregationType;
    }

    public void setAggregationType(AggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }

    public boolean isResetMainDimTitle() {
        return this.resetMainDimTitle;
    }

    public void setResetMainDimTitle(boolean resetMainDimTitle) {
        this.resetMainDimTitle = resetMainDimTitle;
    }

    public boolean isEntityKey() {
        return this.isEntityKey;
    }

    public void setEntityKey(boolean isEntityKey) {
        this.isEntityKey = isEntityKey;
    }

    public ColumnModelDefine getDataColumnModel() {
        return this.dataColumnModel;
    }

    public void setDataColumnModel(ColumnModelDefine dataColumnModel) {
        this.dataColumnModel = dataColumnModel;
    }
}

