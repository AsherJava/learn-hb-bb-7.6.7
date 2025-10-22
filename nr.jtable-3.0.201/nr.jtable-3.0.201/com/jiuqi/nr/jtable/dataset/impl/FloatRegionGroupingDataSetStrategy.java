/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IEntityExtraService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionGroupingDataSetStrategy
extends AbstractRegionGroupingDataSetStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionGroupingDataSetStrategy.class);

    public FloatRegionGroupingDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionGroupingQueryTableStrategy regionGroupingQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        super(regionRelationEvn, regionGroupingQueryTableStrategy, dataFormaterCache, regionQueryInfo);
    }

    public RegionDataSet getRegionDataSet() {
        RegionDataSet regionDataSet = new RegionDataSet();
        RegionQueryInfo regionQueryInfo = this.regionGroupingQueryTableStrategy.getRegionQueryInfo();
        RegionAnnotationResult regionAnnotationResult = this.regionRelationEvn.getRegionAnnotationResult(regionQueryInfo.getContext());
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.dataFormaterCache.init(regionDataSet);
        List<String> cells = this.regionGroupingQueryTableStrategy.getCells();
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
            this.regionGroupingQueryTableStrategy.setEnumFilledQuery(enumFields, allEntityData);
        }
        IReadonlyTable regionQueryTable = this.regionGroupingQueryTableStrategy.getRegionQueryTable();
        if (this.regionRelationEvn.isEntityGrade() || !this.regionRelationEvn.isPaginate()) {
            regionDataSet.setTotalCount(regionQueryTable.getCount());
        } else {
            regionDataSet.setTotalCount(regionQueryTable.getTotalCount());
        }
        RegionGradeDataLoader loader = new RegionGradeDataLoader();
        loader.loadRegionGradeData(regionQueryTable, regionDataSet);
        int startIndex = 0;
        int endIndex = regionQueryTable.getCount();
        PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
        List<List<Object>> relList = regionDataSet.getRel();
        if (pagerInfo != null && pagerInfo.getLimit() > 0 && regionQueryTable.getCount() == regionQueryTable.getTotalCount()) {
            startIndex = pagerInfo.getOffset() * pagerInfo.getLimit();
            endIndex = (pagerInfo.getOffset() + 1) * pagerInfo.getLimit();
            if (startIndex < 0) {
                startIndex = 0;
            } else if (startIndex > regionQueryTable.getCount()) {
                startIndex = regionQueryTable.getCount();
            }
            if (endIndex < 0) {
                endIndex = 0;
            } else if (endIndex > regionQueryTable.getCount()) {
                endIndex = regionQueryTable.getCount();
            }
            if (startIndex > endIndex) {
                startIndex = endIndex;
            }
        }
        ArrayList<List<Object>> pageRelList = new ArrayList<List<Object>>();
        if (this.regionRelationEvn.isPaginate()) {
            for (int i = 0; i < relList.size(); ++i) {
                boolean isDetail;
                List<Object> relRow = relList.get(i);
                boolean bl = isDetail = (Integer)relRow.get(4) == 2;
                if (!isDetail) continue;
                relRow.remove(5);
                int order = i;
                if (pagerInfo != null) {
                    order = pagerInfo.getLimit() * pagerInfo.getOffset() + order;
                }
                relRow.add(order);
            }
        } else {
            int curOrder = 1;
            for (int i = 0; i < relList.size(); ++i) {
                boolean isDetail;
                List<Object> relRow = relList.get(i);
                boolean bl = isDetail = (Integer)relRow.get(4) == 2;
                if (isDetail) {
                    relRow.remove(5);
                    relRow.add(curOrder++);
                    continue;
                }
                curOrder = 1;
            }
        }
        pageRelList.addAll(relList.subList(startIndex, endIndex));
        regionDataSet.setRel(pageRelList);
        for (int rowIndex = startIndex; rowIndex < endIndex; ++rowIndex) {
            IDataRow dataRow = regionQueryTable.getItem(rowIndex);
            regionDataSet.getData().add(this.getRowData(dataRow, fillDataIndex));
        }
        this.getCalcDimensionLinks(regionDataSet);
        return regionDataSet;
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

