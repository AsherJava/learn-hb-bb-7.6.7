/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RegionDataSet
implements IRegionDataSet {
    private static final Logger logger = LoggerFactory.getLogger(RegionDataSet.class);
    protected String regionKey;
    protected DimensionCombination masterDimension;
    protected List<IMetaData> metaData;
    protected Set<Integer> rowDimIndex;
    protected Map<IMetaData, Integer> metaIndex;
    protected Map<String, String> dimNameMap;
    protected int page;
    protected List<IRowData> rows;
    protected Integer totalCount;
    protected boolean supportTreeGroup;
    private DataEngineService dataEngineService;
    private RegionRelation regionRelation;

    public RegionRelation getRegionRelation() {
        return this.regionRelation;
    }

    public DataEngineService getDataEngineService() {
        return this.dataEngineService;
    }

    public void setDataEngineService(DataEngineService dataEngineService) {
        this.dataEngineService = dataEngineService;
    }

    public RegionDataSet() {
    }

    public RegionDataSet(IRegionDataSet dataSet) {
        this.metaData = dataSet.getMetaData();
        this.page = dataSet.getPage();
        this.rows = dataSet.getRowData();
        this.totalCount = dataSet.getTotalCount();
        this.supportTreeGroup = true;
        this.masterDimension = dataSet.getMasterDimension();
        this.metaDataIndex();
        this.dimNameBuild();
    }

    public RegionDataSet(List<? extends IMetaData> metaData) {
        if (metaData != null) {
            this.metaData = metaData;
            this.metaDataIndex();
            this.dimNameBuild();
        }
    }

    public RegionDataSet(List<? extends IMetaData> metaData, List<IRowData> rows) {
        if (metaData != null) {
            this.metaData = metaData;
            this.metaDataIndex();
            this.dimNameBuild();
        }
        this.rows = rows;
    }

    private void metaDataIndex() {
        this.metaIndex = new HashMap<IMetaData, Integer>();
        for (int i = 0; i < this.metaData.size(); ++i) {
            DataFieldKind dataFieldKind;
            IMetaData iMetaData = this.metaData.get(i);
            this.metaIndex.put(iMetaData, i);
            DataField dataField = iMetaData.getDataField();
            if (dataField == null || DataFieldKind.TABLE_FIELD_DIM != (dataFieldKind = dataField.getDataFieldKind())) continue;
            if (this.rowDimIndex == null) {
                this.rowDimIndex = new HashSet<Integer>();
            }
            this.rowDimIndex.add(i);
        }
    }

    private void dimNameBuild() {
        if (this.rowDimIndex == null) {
            return;
        }
        if (this.dataEngineService == null) {
            return;
        }
        if (this.regionRelation == null) {
            return;
        }
        try {
            HashMap<String, String> dimNameMap = new HashMap<String, String>();
            ExecutorContext executorContext = this.dataEngineService.getExecutorContext(this.regionRelation, this.masterDimension);
            IDataAssist dataAssist = this.dataEngineService.getDataAssist(executorContext);
            for (Integer dimIndex : this.rowDimIndex) {
                IMetaData iMetaData = this.getMetaData().get(dimIndex);
                DataField dataField = iMetaData.getDataField();
                if (dataField == null) continue;
                String dimensionName = dataAssist.getDimensionName((FieldDefine)((DataFieldDTO)dataField));
                if (dimensionName == null) {
                    dimensionName = dataField.getCode();
                }
                dimNameMap.put(iMetaData.getFieldKey(), dimensionName);
            }
            this.dimNameMap = dimNameMap;
        }
        catch (Exception e) {
            logger.warn("\u6570\u636e\u96c6\u6784\u5efa\u7ef4\u5ea6\u5931\u8d25", e);
        }
    }

    public Set<Integer> getRowDimIndex() {
        return this.rowDimIndex;
    }

    public Map<IMetaData, Integer> getMetaIndex() {
        return this.metaIndex;
    }

    public Map<String, String> getDimNameMap() {
        if (this.dimNameMap == null) {
            this.dimNameBuild();
        }
        return this.dimNameMap;
    }

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    @Override
    public DimensionCombination getMasterDimension() {
        return this.masterDimension;
    }

    public void setMasterDimension(DimensionCombination masterDimension) {
        this.masterDimension = masterDimension;
    }

    @Override
    public List<IMetaData> getMetaData() {
        return this.metaData;
    }

    public void setMetaData(List<? extends IMetaData> metaData) {
        if (metaData == null) {
            return;
        }
        this.metaData = metaData;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getRowCount() {
        if (this.rows == null) {
            return 0;
        }
        return this.rows.size();
    }

    @Override
    public int getTotalCount() {
        int rowCount = this.getRowCount();
        if (this.totalCount == null) {
            return rowCount;
        }
        if (this.totalCount < rowCount) {
            return rowCount;
        }
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public List<IRowData> getRowData() {
        return this.rows;
    }

    public void setRows(List<IRowData> rows) {
        this.rows = rows;
    }

    public RowData initRow() {
        return new RowData(this);
    }

    public RowData appendRow() {
        RowData rowData = this.initRow();
        if (this.rows == null) {
            this.rows = new ArrayList<IRowData>();
        }
        this.rows.add(rowData);
        return rowData;
    }

    @Override
    public List<IDataValue> getDataValuesByLink(String link) {
        if (this.metaData == null || !StringUtils.hasLength(link)) {
            return Collections.emptyList();
        }
        ArrayList<IDataValue> values = new ArrayList<IDataValue>();
        for (int i = 0; i < this.metaData.size(); ++i) {
            if (!this.metaData.get(i).getLinkKey().equals(link)) continue;
            for (IRowData row : this.rows) {
                IDataValue iDataValue = row.getLinkDataValues().get(i);
                values.add(iDataValue);
            }
            return values;
        }
        return Collections.emptyList();
    }

    public IRowData getRowDataByBizKeyOrder(String bizKeyOrder) {
        return null;
    }

    @Override
    public boolean supportTreeGroup() {
        return this.supportTreeGroup;
    }

    public void setSupportTreeGroup(boolean supportTreeGroup) {
        this.supportTreeGroup = supportTreeGroup;
    }

    public void setRegionRelation(RegionRelation regionRelation) {
        this.regionRelation = regionRelation;
    }
}

