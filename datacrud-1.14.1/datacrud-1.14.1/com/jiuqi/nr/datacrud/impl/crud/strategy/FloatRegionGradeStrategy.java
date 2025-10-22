/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.datacrud.GradeLink;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.calc.GroupRowCalcExecutor;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.spi.FillDataProvider;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.entity.EntityQueryMode;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.entity.QueryModeImpl;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FloatRegionGradeStrategy
extends BaseRegionDataStrategy<IGroupingQuery>
implements IRegionDataSetStrategy {
    protected int bizKeyOrderIndex = -1;
    protected final FillDataProvider fillDataProvider;
    protected final IRunTimeViewController runTimeViewController;
    protected final DataModelService dataModelService;
    protected final IEntityTableFactory entityTableFactory;
    private Set<String> links = null;
    private Set<String> fields = null;
    private GroupRowCalcExecutor calcExecutor;
    private IEntityTableWrapper entityTableWrapper;

    public FloatRegionGradeStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
        this.fillDataProvider = factory.getFillDataProvider();
        this.runTimeViewController = factory.getRunTimeViewController();
        this.dataModelService = factory.getDataModelService();
        this.entityTableFactory = factory.getEntityTableFactory();
    }

    @Override
    protected IGroupingQuery getDataQuery(IQueryInfo queryInfo, RegionRelation relation) {
        return this.dataEngineService.getGroupingQuery(relation);
    }

    @Override
    public IRegionDataSet queryData(IQueryInfo queryInfo, RegionRelation relation) {
        IGroupingTable table;
        List<MetaData> metaData = relation.getMetaData(null);
        RegionDataSet set = new RegionDataSet(metaData, Collections.emptyList());
        set.setRegionKey(queryInfo.getRegionKey());
        DimensionCombination dimensionCombination = queryInfo.getDimensionCombination();
        set.setMasterDimension(dimensionCombination);
        if (metaData.isEmpty()) {
            return set;
        }
        super.initDataQuery(queryInfo, relation);
        this.addQueryCol(metaData);
        RegionGradeInfo gradeInfo = relation.getGradeInfo();
        this.addFilter();
        this.bizKeyOrderIndex = this.addBizKeyOrderField();
        this.addFloatOrderField(metaData);
        this.setGroupingSetting(gradeInfo);
        this.setGroupColumn(gradeInfo);
        this.addOrder();
        this.fillEnum();
        PageInfo pageInfo = queryInfo.getPageInfo();
        try {
            table = ((IGroupingQuery)this.dataQuery).executeReader(this.context);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", e);
        }
        List<IRowData> rowData = pageInfo != null ? this.buildData((IReadonlyTable)table, metaData, pageInfo.getPageIndex() * pageInfo.getRowsPerPage(), (pageInfo.getPageIndex() + 1) * pageInfo.getRowsPerPage()) : this.buildData((IReadonlyTable)table, metaData);
        set.setRows(rowData);
        set.setSupportTreeGroup(table.supportTreeGroup());
        set.setTotalCount(table.getTotalCount());
        return set;
    }

    @Override
    protected void addOrder() {
        Iterator<LinkSort> sortItr = this.queryInfo.linkSortItr();
        if (sortItr == null) {
            List<LinkSort> regionDefaultOrder = this.relation.getRegionDefaultOrder();
            sortItr = regionDefaultOrder.iterator();
        }
        while (sortItr.hasNext()) {
            LinkSort next = sortItr.next();
            String linkKey = next.getLinkKey();
            SortMode mode = next.getMode();
            if ("FLOATORDER".equals(linkKey)) {
                DataField floatOrderField = this.relation.getFloatOrderField();
                if (floatOrderField == null) continue;
                ((IGroupingQuery)this.dataQuery).addOrderByItem((FieldDefine)((DataFieldDTO)floatOrderField), mode == SortMode.DESC);
                continue;
            }
            MetaData byLink = this.relation.getMetaDataByLink(linkKey);
            if (byLink == null || byLink.getDataField() == null) continue;
            ((IGroupingQuery)this.dataQuery).addOrderByItem((FieldDefine)((DataFieldDTO)byLink.getDataField()), mode == SortMode.DESC);
        }
        ((IGroupingQuery)this.dataQuery).setIgnoreDefaultOrderBy(true);
    }

    private void fillEnum() {
        if (!this.queryInfo.isEnableEnumFill()) {
            return;
        }
        List<MetaData> enumLinks = this.relation.getFilledEnumLinks();
        if (CollectionUtils.isEmpty(enumLinks)) {
            return;
        }
        ArrayList<DataFieldDTO> enumFields = new ArrayList<DataFieldDTO>();
        for (MetaData fillLink : enumLinks) {
            enumFields.add((DataFieldDTO)fillLink.getDataField());
        }
        List<List<String>> tableData = this.fillDataProvider.fillData(this.queryInfo, this.relation);
        if (!CollectionUtils.isEmpty(tableData)) {
            ((IGroupingQuery)this.dataQuery).setFilledEnumLinks(enumFields, tableData);
        }
    }

    private void addExpression() {
        List<IParsedExpression> expressions = this.relation.getParsedExpression(this.relation.getMetaData(null));
        if (!CollectionUtils.isEmpty(expressions)) {
            ArrayList<IExpression> list = new ArrayList<IExpression>();
            for (IParsedExpression expression : expressions) {
                list.add(expression.getRealExpression());
            }
            ((IGroupingQuery)this.dataQuery).setCalcExpressions(list);
        }
    }

    protected void setGroupingSetting(RegionGradeInfo gradeInfo) {
        if (gradeInfo == null) {
            return;
        }
        List<GradeLink> gradeLinks = gradeInfo.getGradeLinks();
        if (CollectionUtils.isEmpty(gradeLinks)) {
            ((IGroupingQuery)this.dataQuery).setSummarizingMethod(SummarizingMethod.None);
        } else {
            ((IGroupingQuery)this.dataQuery).setSummarizingMethod(SummarizingMethod.RollUp);
        }
        ((IGroupingQuery)this.dataQuery).setHasRootGatherRow(gradeInfo.isQuerySummary());
        ((IGroupingQuery)this.dataQuery).setWantDetail(gradeInfo.isQueryDetails());
        ((IGroupingQuery)this.dataQuery).setHidenOneDetailRow(gradeInfo.isHideSingleDetail());
        ((IGroupingQuery)this.dataQuery).setSortGroupingAndDetailRows(true);
    }

    private int addBizKeyOrderField() {
        return ((IGroupingQuery)this.dataQuery).addColumn((FieldDefine)((DataFieldDTO)this.relation.getBizKeyOrderField()));
    }

    private void addFloatOrderField(List<MetaData> metaData) {
        MetaData floatOrder = new MetaData("FLOATORDER", this.relation.getFloatOrderField());
        metaData.add(floatOrder);
        int floatIndex = ((IGroupingQuery)this.dataQuery).addColumn((FieldDefine)((DataFieldDTO)this.relation.getFloatOrderField()));
        floatOrder.setIndex(floatIndex);
    }

    private void setGroupColumn(RegionGradeInfo gradeInfo) {
        if (CollectionUtils.isEmpty(gradeInfo.getGradeLinks())) {
            DataField periodField = this.relation.getPeriodField();
            int column = ((IGroupingQuery)this.dataQuery).addColumn((FieldDefine)((DataFieldDTO)periodField));
            ((IGroupingQuery)this.dataQuery).addGroupColumn(column);
            return;
        }
        LinkedHashMap<MetaData, GradeLink> gradeEntityCellMap = new LinkedHashMap<MetaData, GradeLink>();
        List<GradeLink> gradeLinks = gradeInfo.getGradeLinks();
        for (GradeLink gradeLink : gradeLinks) {
            String fieldKey = gradeLink.getFieldKey();
            String linkKey = gradeLink.getLinkKey();
            MetaData metaData = null;
            if (fieldKey != null) {
                metaData = this.relation.getMetaDataByFieldKey(fieldKey);
            } else if (linkKey != null) {
                metaData = this.relation.getMetaDataByLink(linkKey);
            }
            if (metaData == null) continue;
            gradeEntityCellMap.put(metaData, gradeLink);
            ((IGroupingQuery)this.dataQuery).addGroupColumn(metaData.getIndex());
            DataField dataField = metaData.getDataField();
            DataFieldGatherType dataFieldGatherType = gradeLink.getDataFieldGatherType();
            if (dataFieldGatherType == null || dataField.getDataFieldGatherType() == dataFieldGatherType) continue;
            ((IGroupingQuery)this.dataQuery).setGatherType(metaData.getIndex(), FieldGatherType.forValue((int)dataFieldGatherType.getValue()));
        }
        if (!CollectionUtils.isEmpty(gradeEntityCellMap) || !CollectionUtils.isEmpty(gradeInfo.getGradeLevels())) {
            this.setDataRegTotalInfo(gradeEntityCellMap, gradeInfo.getGradeLevels(), gradeInfo.getPrecisions());
        }
    }

    public void setDataRegTotalInfo(Map<MetaData, GradeLink> gradeEntityCellMap, List<Integer> levels, List<Integer> precisions) {
        ArrayList<GradeTotalItem> gradeTotalItems = new ArrayList<GradeTotalItem>();
        for (Map.Entry<MetaData, GradeLink> entry : gradeEntityCellMap.entrySet()) {
            MetaData linkKey = entry.getKey();
            GradeLink value = entry.getValue();
            GradeLinkItem linkItem = new GradeLinkItem();
            linkItem.setKey(linkKey.getLinkKey());
            DataLinkDefine dataLinkDefine = linkKey.getDataLinkDefine();
            linkItem.setLinkExpression(dataLinkDefine.getLinkExpression());
            DataField dataField = linkKey.getDataField();
            if (dataField != null && StringUtils.hasLength(dataField.getRefDataEntityKey())) {
                EntityViewDefine entityView = this.runTimeViewController.getViewByLinkDefineKey(dataLinkDefine.getKey());
                linkItem.setEntityView(entityView);
            }
            GradeTotalItem item = new GradeTotalItem(linkItem, linkKey.getIndex(), new ArrayList<Integer>(value.getGradeSetting()));
            item.setNeedEnd0(value.isNeedEnd0());
            gradeTotalItems.add(item);
        }
        DataRegTotalInfo dataRegTotalInfo = new DataRegTotalInfo();
        if (!gradeTotalItems.isEmpty()) {
            dataRegTotalInfo.setGradeTotalItems(gradeTotalItems);
        }
        if (!CollectionUtils.isEmpty(levels)) {
            dataRegTotalInfo.setGradeLevels(levels);
        }
        if (!CollectionUtils.isEmpty(precisions)) {
            dataRegTotalInfo.setPrecisions(precisions);
        }
        ((IGroupingQuery)this.dataQuery).setDataRegTotalInfo(dataRegTotalInfo);
    }

    @Override
    public int queryDataCount(IQueryInfo queryInfo, RegionRelation relation) {
        IRegionDataSet iRegionDataSet = this.queryData(queryInfo, relation);
        return iRegionDataSet.getRowCount();
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        return 0;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        return null;
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo queryInfo, RegionRelation relation) {
        return null;
    }

    @Override
    protected void metaRowTransfer(IDataRow row, RowData rowData) {
        if (this.bizKeyOrderIndex >= 0) {
            AbstractData value = row.getValue(this.bizKeyOrderIndex);
            rowData.setRecKey(value.getAsString());
        }
        try {
            if (row.getGroupTreeDeep() >= 0) {
                if (this.calcExecutor == null) {
                    RegionGradeInfo gradeInfo = this.relation.getGradeInfo();
                    String formulaSchemeKey = gradeInfo.getFormulaSchemeKey();
                    List<IParsedExpression> expressions = this.relation.getParsedExpression(this.relation.getMetaData(null), formulaSchemeKey);
                    if (!CollectionUtils.isEmpty(expressions)) {
                        this.calcExecutor = new GroupRowCalcExecutor();
                        this.calcExecutor.setDataModelService(this.dataModelService);
                        this.calcExecutor.setExpressions(expressions);
                        this.calcExecutor.setContext(this.context);
                        this.calcExecutor.setQueryParam(this.dataEngineService.getQueryParam());
                        this.calcExecutor.initialize();
                    }
                }
                if (this.calcExecutor != null) {
                    this.calcExecutor.calcRow(rowData);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5206\u7ea7\u5408\u8ba1\u884c\u8ba1\u7b97\u884c\u5185\u516c\u5f0f\u9519\u8bef", e);
            throw new CrudException(4102, "\u5206\u7ea7\u5408\u8ba1\u884c\u8ba1\u7b97\u884c\u5185\u516c\u5f0f\u9519\u8bef");
        }
        if (rowData.getGroupTreeDeep() >= 0) {
            List<IDataValue> dataValues = rowData.getLinkDataValues();
            for (IDataValue dataValue : dataValues) {
                Optional first;
                Map enumPosMap;
                DataLinkDefine srcLinkDef;
                String enumKey;
                IDataValue enumKeyData;
                MetaData enumShowMeta;
                String srcLink;
                IMetaData metaData = dataValue.getMetaData();
                if (!metaData.isEnumShow() || !StringUtils.hasLength(srcLink = metaData.getEnumShowLink()) || (enumShowMeta = this.relation.getMetaDataByLink(srcLink)) == null || (enumKeyData = rowData.getDataValueByLink(srcLink)) == null || enumKeyData.getAsNull() || !StringUtils.hasLength(enumKey = enumKeyData.getAsString()) || (srcLinkDef = enumShowMeta.getDataLinkDefine()) == null || (enumPosMap = srcLinkDef.getEnumPosMap()) == null || !(first = enumPosMap.keySet().stream().findFirst()).isPresent()) continue;
                String attr = (String)first.get();
                if (this.entityTableWrapper == null) {
                    this.entityTableWrapper = this.entityTableFactory.getEntityTable((ParamRelation)this.relation, rowData.getMasterDimension(), (IMetaData)enumShowMeta, QueryModeImpl.create(EntityQueryMode.IGNORE_ISOLATE_CONDITION.getValue(), true));
                } else {
                    this.entityTableFactory.reBuildEntityTable((ParamRelation)this.relation, rowData.getMasterDimension(), (IMetaData)enumShowMeta, this.entityTableWrapper, QueryModeImpl.create(EntityQueryMode.IGNORE_ISOLATE_CONDITION.getValue(), true));
                }
                IEntityRow entityRow = this.entityTableWrapper.findByEntityKey(enumKey);
                if (entityRow == null) continue;
                String value = entityRow.getAsString(attr);
                dataValue.setAbstractData(AbstractData.valueOf((String)value));
            }
        }
        super.metaRowTransfer(row, rowData);
    }

    @Override
    protected AbstractData metaDataTransfer(MetaData meta, IDataRow row, RowData rowData) {
        if (this.links == null || this.fields == null) {
            List<GradeLink> gradeLinks;
            this.links = new HashSet<String>();
            this.fields = new HashSet<String>();
            RegionGradeInfo gradeInfo = this.relation.getGradeInfo();
            if (gradeInfo != null && (gradeLinks = gradeInfo.getGradeLinks()) != null) {
                for (GradeLink gradeLink : gradeLinks) {
                    this.links.add(gradeLink.getLinkKey());
                    this.fields.add(gradeLink.getFieldKey());
                }
            }
        }
        if (!(row.getGroupTreeDeep() < 0 || DataFieldGatherType.NONE != meta.getGatherType() || this.links.contains(meta.getLinkKey()) || this.fields.contains(meta.getFieldKey()) || meta.isEnumShow() || meta.getLinkKey().equals("FLOATORDER"))) {
            return AbstractData.valueOf((String)"\u2014\u2014");
        }
        return super.metaDataTransfer(meta, row, rowData);
    }
}

