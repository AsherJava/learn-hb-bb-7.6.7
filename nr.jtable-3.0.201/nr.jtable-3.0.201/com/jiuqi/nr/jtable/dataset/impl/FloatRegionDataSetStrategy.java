/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IEntityExtraService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionDataSetStrategy
extends AbstractRegionDataSetStrategy {
    private FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionDataSetStrategy.class);

    public FloatRegionDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        super(regionRelationEvn, regionQueryTableStrategy, dataFormaterCache, regionQueryInfo);
    }

    public RegionDataSet getRegionDataSet() {
        int i;
        IReadonlyTable regionQueryTable;
        RegionDataSet regionDataSet = new RegionDataSet();
        RegionQueryInfo regionQueryInfo = this.regionQueryTableStrategy.getRegionQueryInfo();
        JtableContext jtableContext = regionQueryInfo.getContext();
        RegionAnnotationResult regionAnnotationResult = this.regionRelationEvn.getRegionAnnotationResult(jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.dataFormaterCache.init(regionDataSet);
        List<String> cells = this.regionQueryTableStrategy.getCells();
        regionDataSet.getCells().put(this.regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (cells.isEmpty()) {
            return regionDataSet;
        }
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        List<String> fillLinks = this.regionRelationEvn.getRegionData().getFillLinks();
        ArrayList<Integer> fillDataIndex = new ArrayList<Integer>();
        ArrayList<Integer> fillEntityIndex = new ArrayList<Integer>();
        if (fillLinks != null && !fillLinks.isEmpty()) {
            ArrayList<FieldDefine> enumFields = new ArrayList<FieldDefine>();
            for (String fillLink : fillLinks) {
                fillDataIndex.add(this.regionRelationEvn.getCellIndex(fillLink));
                fillEntityIndex.add(cells.indexOf(fillLink));
                try {
                    FieldDefine fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(this.regionRelationEvn.getFieldByDataLink(fillLink).getFieldKey());
                    enumFields.add(fieldDefine);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            boolean containsBean = true;
            try {
                BeanUtil.getBean(IEntityExtraService.class);
            }
            catch (Exception e) {
                containsBean = false;
            }
            List<List<String>> allEntityData = null;
            if (containsBean) {
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                allEntityData = jtableEntityService.queryRelEntityDatas(fillLinks, regionQueryInfo.getContext());
            } else {
                allEntityData = this.getAllEntityData(fillLinks, new ArrayList<String>(), 0);
            }
            this.regionQueryTableStrategy.setEnumFilledQuery(enumFields, allEntityData);
        }
        if ((regionQueryTable = this.regionQueryTableStrategy.getRegionQueryTable()).getCount() > 0) {
            for (i = 0; i < regionQueryTable.getCount(); ++i) {
                IDataRow iDataRow = regionQueryTable.getItem(i);
                if (iDataRow.isFilledRow()) continue;
                regionDataSet.setRegionOnlyHasExtentGridData(false);
                break;
            }
        }
        if (!this.regionRelationEvn.isPaginate()) {
            regionDataSet.setTotalCount(regionQueryTable.getCount());
        } else {
            regionDataSet.setTotalCount(regionQueryTable.getTotalCount());
        }
        for (i = 0; i < regionQueryTable.getCount(); ++i) {
            IDataRow dataRow = regionQueryTable.getItem(i);
            regionDataSet.getData().add(this.getRowData(dataRow, fillDataIndex));
        }
        this.getCalcDimensionLinks(regionDataSet);
        this.mergeCells(regionDataSet, fillEntityIndex, 0, 0, regionDataSet.getData().size() - 1);
        return regionDataSet;
    }

    public PagerInfo getLocatDataById(String dataId) {
        DimensionValueSet locateDimensionValueSet = this.getRowDimensionValueSet(dataId);
        IndexItem indexItem = this.regionQueryTableStrategy.getRowIndex(locateDimensionValueSet);
        int currentIndex = indexItem.getCurrentIndex(locateDimensionValueSet);
        int totalCount = indexItem.getTotalCount();
        PagerInfo locateInfo = new PagerInfo();
        if (currentIndex > totalCount || currentIndex < 0) {
            locateInfo.setOffset(-1);
        } else {
            locateInfo.setOffset(currentIndex - 1);
        }
        locateInfo.setTotal(totalCount);
        return locateInfo;
    }

    private List<List<String>> getAllEntityData(List<String> fillLinks, List<String> parentEntityValues, int linkIndex) {
        ArrayList<List<String>> fillEntityDataList;
        block6: {
            block7: {
                block5: {
                    fillEntityDataList = new ArrayList<List<String>>();
                    if (linkIndex < fillLinks.size()) break block5;
                    fillEntityDataList.add(parentEntityValues);
                    break block6;
                }
                if (linkIndex != 0) break block7;
                String linkKey = fillLinks.get(linkIndex);
                LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(linkKey);
                if (!(dataLink instanceof EnumLinkData)) break block6;
                EnumLinkData enumLink = (EnumLinkData)dataLink;
                EntityReturnInfo fillEntityData = enumLink.getFillEntityData(null, null, this.dataFormaterCache);
                List<String> entityIds = this.getEntityIds(fillEntityData.getEntitys());
                for (String entityId : entityIds) {
                    ArrayList<String> entityValues = new ArrayList<String>();
                    entityValues.addAll(parentEntityValues);
                    entityValues.add(entityId);
                    fillEntityDataList.addAll(this.getAllEntityData(fillLinks, entityValues, linkIndex + 1));
                }
                break block6;
            }
            String linkKey = fillLinks.get(linkIndex);
            String parentLinkKey = fillLinks.get(linkIndex - 1);
            String parentKey = parentEntityValues.get(parentEntityValues.size() - 1);
            LinkData dataLink = this.regionRelationEvn.getDataLinkByKey(linkKey);
            LinkData parentDataLink = this.regionRelationEvn.getDataLinkByKey(parentLinkKey);
            String parentViewKey = null;
            if (parentDataLink instanceof EnumLinkData) {
                EnumLinkData parentEnumLink = (EnumLinkData)parentDataLink;
                parentViewKey = parentEnumLink.getEntityKey();
            }
            if (dataLink instanceof EnumLinkData) {
                EnumLinkData enumLink = (EnumLinkData)dataLink;
                EnumLink enumLinkPre = enumLink.getEnumLink();
                if (enumLinkPre == null || !enumLinkPre.getPreLinks().contains(parentLinkKey)) {
                    parentKey = null;
                    parentViewKey = null;
                }
                EntityReturnInfo fillEntityData = enumLink.getFillEntityData(parentKey, parentViewKey, this.dataFormaterCache);
                List<String> entityIds = this.getEntityIds(fillEntityData.getEntitys());
                for (String entityId : entityIds) {
                    ArrayList<String> entityValues = new ArrayList<String>();
                    entityValues.addAll(parentEntityValues);
                    entityValues.add(entityId);
                    fillEntityDataList.addAll(this.getAllEntityData(fillLinks, entityValues, linkIndex + 1));
                }
            }
        }
        return fillEntityDataList;
    }

    private List<String> getEntityIds(List<EntityData> entitys) {
        ArrayList<String> entityIds = new ArrayList<String>();
        for (EntityData entityDataInfo : entitys) {
            entityIds.add(entityDataInfo.getId());
            if (entityDataInfo.getChildren().isEmpty()) continue;
            entityIds.addAll(this.getEntityIds(entityDataInfo.getChildren()));
        }
        return entityIds;
    }

    private void mergeCells(RegionDataSet regionDataSet, List<Integer> fillEntityIndex, int mergeLevel, int rowStart, int rowEnd) {
        if (mergeLevel < fillEntityIndex.size() - 1) {
            List<Object> mergeIndexList;
            int entityIndex = fillEntityIndex.get(mergeLevel);
            String entityLink = regionDataSet.getCells().get(this.regionRelationEvn.getRegionData().getKey().toString()).get(entityIndex);
            if (regionDataSet.getMergeCells().containsKey(entityLink)) {
                mergeIndexList = regionDataSet.getMergeCells().get(entityLink);
            } else {
                mergeIndexList = new ArrayList();
                regionDataSet.getMergeCells().put(entityLink, mergeIndexList);
            }
            String mergeValue = "";
            int mergeStart = rowStart;
            int mergeEnd = rowStart;
            int rowIndex = rowStart;
            while (rowIndex <= rowEnd) {
                List<Object> dataRow = regionDataSet.getData().get(rowIndex);
                String entityValue = dataRow.get(entityIndex).toString();
                if (entityValue.equals(mergeValue)) {
                    mergeEnd = rowIndex;
                }
                if (!(entityValue.equals(mergeValue) && rowIndex != rowEnd || mergeEnd <= mergeStart)) {
                    ArrayList<Integer> mergeCell = new ArrayList<Integer>();
                    mergeCell.add(mergeStart);
                    mergeCell.add(mergeEnd);
                    mergeIndexList.add(mergeCell);
                    this.mergeCells(regionDataSet, fillEntityIndex, mergeLevel + 1, mergeStart, mergeEnd);
                }
                if (!entityValue.equals(mergeValue)) {
                    mergeValue = entityValue;
                    mergeStart = rowIndex;
                }
                mergeEnd = rowIndex++;
            }
        }
    }
}

