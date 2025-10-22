/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.engine.grouping.EnumRowItem
 *  com.jiuqi.nr.data.engine.grouping.GradeEnumTree
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.engine.grouping.EnumRowItem;
import com.jiuqi.nr.data.engine.grouping.GradeEnumTree;
import com.jiuqi.nr.datacrud.GradeLink;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.api.IDataGatherService;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.GroupDataValue;
import com.jiuqi.nr.datacrud.impl.GroupRowData;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.EntityDataService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataGatherServiceImpl
implements IDataGatherService {
    private final Logger logger = LoggerFactory.getLogger(DataGatherServiceImpl.class);
    @Autowired
    private EntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IRunTimeViewController controller;

    @Override
    public IRegionDataSet gradeGather(IRegionDataSet regionDataSet, RegionGradeInfo regionGradeInfo, RegionRelation relation) {
        if (regionDataSet == null || regionGradeInfo == null) {
            return regionDataSet;
        }
        List<IMetaData> metaData = regionDataSet.getMetaData();
        List<IRowData> rowData = regionDataSet.getRowData();
        if (CollectionUtils.isEmpty(rowData) || CollectionUtils.isEmpty(metaData)) {
            return regionDataSet;
        }
        if (regionGradeInfo.isGrade()) {
            List<GradeLink> gradeLinks = regionGradeInfo.getGradeLinks();
            List<Integer> gradeLevels = regionGradeInfo.getGradeLevels();
            if (!CollectionUtils.isEmpty(gradeLevels)) {
                return this.gradeFreeGather(regionDataSet, regionGradeInfo, relation);
            }
            if (!CollectionUtils.isEmpty(gradeLinks)) {
                return this.gradeEnumGather(regionDataSet, regionGradeInfo, relation);
            }
        } else if (regionGradeInfo.isQuerySummary()) {
            return this.gradeAllGather(regionDataSet, regionGradeInfo);
        }
        return regionDataSet;
    }

    private IRegionDataSet gradeEnumGather(IRegionDataSet regionDataSet, RegionGradeInfo regionGradeInfo, RegionRelation relation) {
        List<GradeLink> gradeLinks = regionGradeInfo.getGradeLinks();
        boolean querySummary = regionGradeInfo.isQuerySummary();
        HashMap<String, GradeEnumTree> gradeEnumTrees = new HashMap<String, GradeEnumTree>();
        List<IRowData> rowData = regionDataSet.getRowData();
        HashMap<String, GroupRowData> levelGroupMap = new HashMap<String, GroupRowData>();
        HashSet<String> gradeLinkSet = new HashSet<String>();
        for (GradeLink gradeLink : gradeLinks) {
            MetaData byFieldKey;
            String linkKey = gradeLink.getLinkKey();
            if (linkKey == null && (byFieldKey = relation.getMetaDataByFieldKey(gradeLink.getFieldKey())) != null) {
                linkKey = byFieldKey.getLinkKey();
            }
            if (linkKey == null) {
                this.logger.warn("\u672a\u8bbe\u7f6e\u5206\u7ea7\u94fe\u63a5\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                return regionDataSet;
            }
            gradeLink.setLinkKey(linkKey);
            gradeLinkSet.add(linkKey);
        }
        List<IMetaData> metaDataList = regionDataSet.getMetaData();
        HashMap<String, Integer> metaDataIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < metaDataList.size(); ++i) {
            metaDataIndexMap.put(metaDataList.get(i).getLinkKey(), i);
        }
        int groupDeepMax = 0;
        GroupRowData root = this.initGroupRowData(regionDataSet, gradeLinkSet, "");
        for (GradeLink gradeLink : gradeLinks) {
            root.getGroupDataValues().get((Integer)metaDataIndexMap.get(gradeLink.getLinkKey())).writeValue(AbstractData.valueOf((String)"\u2014\u2014"));
        }
        for (IRowData iRowData : rowData) {
            StringBuilder rowKey = new StringBuilder();
            GroupRowData rowPack = new GroupRowData();
            rowPack.setRow(iRowData);
            int deep = 0;
            for (GradeLink gradeTotalItem : gradeLinks) {
                GradeEnumTree enumTree;
                String linkKey = gradeTotalItem.getLinkKey();
                MetaData metaData = relation.getMetaDataByLink(linkKey);
                String entityId = metaData.getEntityId();
                if (entityId == null) {
                    this.logger.warn("\u5206\u7ea7\u5408\u8ba1\u65f6\u5b9e\u4f53id\u4e3a\u7a7a\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                    return regionDataSet;
                }
                EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
                String entityViewId = entityViewDefine.getEntityId();
                if (!gradeEnumTrees.containsKey(entityViewId)) {
                    try {
                        enumTree = this.getEnumTree(entityViewDefine, relation);
                    }
                    catch (Exception e) {
                        this.logger.warn("\u5206\u7ea7\u5408\u8ba1\u65f6\u672a\u67e5\u5230\u5206\u7ea7\u5408\u8ba1\u7684\u679a\u4e3e\u6570\u636e\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1", e);
                        return regionDataSet;
                    }
                    gradeEnumTrees.put(entityViewId, enumTree);
                } else {
                    enumTree = (GradeEnumTree)gradeEnumTrees.get(entityViewId);
                }
                if (enumTree == null) {
                    this.logger.warn("\u5206\u7ea7\u5408\u8ba1\u65f6\u672a\u67e5\u5230\u5206\u7ea7\u5408\u8ba1\u7684\u679a\u4e3e\u6570\u636e\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                    return regionDataSet;
                }
                LinkedHashSet gradeLevels = new LinkedHashSet(enumTree.getGradeLevels());
                if (gradeTotalItem.getGradeSetting().size() > 0 && !gradeLevels.containsAll(gradeTotalItem.getGradeSetting())) {
                    this.logger.warn("\u5408\u8ba1\u7ea7\u6b21\u8bbe\u7f6e\u9519\u8bef\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                    return regionDataSet;
                }
                if (gradeTotalItem.getGradeSetting().size() > 0) {
                    gradeLevels.retainAll(gradeTotalItem.getGradeSetting());
                }
                IDataValue value = iRowData.getDataValueByLink(linkKey);
                for (Integer gradeLevel : gradeLevels) {
                    String code = value.getAsString();
                    EnumRowItem enumRowItem = (EnumRowItem)enumTree.getEnumRowItems().get(code);
                    if (enumRowItem.getParentPath() != null && gradeLevel <= enumRowItem.getParentPath().size()) {
                        code = (String)enumRowItem.getParentPath().get(gradeLevel - 1);
                    }
                    GroupRowData parentRow = rowKey.length() > 0 ? (GroupRowData)levelGroupMap.get(rowKey.toString()) : root;
                    rowKey.append(code);
                    String rowKeyStr = rowKey.toString();
                    GroupRowData groupRowData = (GroupRowData)levelGroupMap.get(rowKeyStr);
                    if (groupRowData == null) {
                        groupRowData = this.initGroupRowData(regionDataSet, gradeLinkSet, rowKeyStr);
                        groupRowData.setGroupKey(code);
                        levelGroupMap.put(rowKeyStr, groupRowData);
                        if (parentRow != null) {
                            parentRow.getChildren().add(groupRowData);
                        }
                        boolean writeBegin = false;
                        for (GradeLink gradeLink : gradeLinks) {
                            if (writeBegin) {
                                Integer index = (Integer)metaDataIndexMap.get(gradeLink.getLinkKey());
                                groupRowData.getGroupDataValues().get(index).writeValue(AbstractData.valueOf((String)"\u2014\u2014"));
                            }
                            if (gradeTotalItem != gradeLink) continue;
                            writeBegin = true;
                        }
                        Integer index = (Integer)metaDataIndexMap.get(gradeTotalItem.getLinkKey());
                        groupRowData.getGroupDataValues().get(index).writeValue(AbstractData.valueOf((String)code));
                    }
                    groupRowData.setGroupingFlag(deep);
                    groupDeepMax = Math.max(deep, groupDeepMax);
                    ++deep;
                    groupRowData.getChildren().add(rowPack);
                    for (int i = 0; i < metaDataList.size(); ++i) {
                        IDataValue iDataValue = iRowData.getLinkDataValues().get(i);
                        AbstractData abstractData = iDataValue.getAbstractData();
                        groupRowData.gatherValue(i, abstractData);
                    }
                }
            }
            rowPack.setGroupKey(rowKey.toString());
            for (int i = 0; i < metaDataList.size(); ++i) {
                IDataValue iDataValue = iRowData.getLinkDataValues().get(i);
                AbstractData abstractData = iDataValue.getAbstractData();
                root.gatherValue(i, abstractData);
            }
        }
        Collection values = levelGroupMap.values();
        for (GroupRowData groupRowDatum : values) {
            groupRowDatum.setGroupingFlag(groupDeepMax - groupRowDatum.getGroupTreeDeep());
            List<GroupRowData> children = groupRowDatum.getChildren();
            if (groupRowDatum.getGroupTreeDeep() != 0) {
                children.removeIf(child -> child.getGroupTreeDeep() < 0);
            } else if (!regionGradeInfo.isQueryDetails()) {
                children.clear();
            }
            if (regionGradeInfo.isHideSingleDetail() && children.size() == 1) continue;
            groupRowDatum.gatherRow();
        }
        RegionDataSet regionDataSet2 = new RegionDataSet(regionDataSet);
        root.gatherRow();
        root.setGroupingFlag(groupDeepMax + 1);
        List<GroupRowData> rows = root.traversal(querySummary);
        regionDataSet2.setRows(rows.stream().map(GroupRowData::getRow).collect(Collectors.toList()));
        regionDataSet2.setTotalCount(rows.size());
        regionDataSet2.setSupportTreeGroup(true);
        return regionDataSet2;
    }

    private IRegionDataSet gradeFreeGather(IRegionDataSet regionDataSet, RegionGradeInfo regionGradeInfo, RegionRelation relation) {
        boolean querySummary = regionGradeInfo.isQuerySummary();
        List<Integer> gradeLevels = regionGradeInfo.getGradeLevels();
        List<IMetaData> metaData = regionDataSet.getMetaData();
        List<IRowData> rowData = regionDataSet.getRowData();
        LinkedList<GroupRowData> allRows = new LinkedList<GroupRowData>();
        HashMap<String, GroupRowData> groupRowDataMap = new HashMap<String, GroupRowData>();
        LinkedHashSet<String> gradleLinkSet = new LinkedHashSet<String>(3);
        List<GradeLink> gradeLinks = regionGradeInfo.getGradeLinks();
        for (GradeLink gradeLink : gradeLinks) {
            MetaData metaData2;
            Object linkKey = gradeLink.getLinkKey();
            if (linkKey == null && (metaData2 = relation.getMetaDataByFieldKey(gradeLink.getFieldKey())) != null) {
                linkKey = metaData2.getLinkKey();
            }
            if (linkKey == null) {
                this.logger.warn("\u672a\u8bbe\u7f6e\u5206\u7ea7\u94fe\u63a5\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                return regionDataSet;
            }
            gradleLinkSet.add((String)linkKey);
            gradeLink.setLinkKey((String)linkKey);
        }
        GroupRowData root = this.initGroupRowData(regionDataSet, gradleLinkSet, "");
        List<GroupDataValue> rootCols = root.getGroupDataValues();
        for (GroupDataValue groupDataValue : rootCols) {
            if (!gradleLinkSet.contains(groupDataValue.getMetaData().getLinkKey())) continue;
            groupDataValue.writeValue(AbstractData.valueOf((String)"\u2014\u2014"));
        }
        for (IRowData iRowData : rowData) {
            StringBuilder rowGroupKey = new StringBuilder();
            for (String linkKey : gradleLinkSet) {
                IDataValue valueByLink = iRowData.getDataValueByLink(linkKey);
                if (valueByLink == null || valueByLink.getAsNull()) {
                    this.logger.warn("\u8981\u5408\u8ba1\u7684\u6307\u6807\u6570\u636e\u4e3a\u7a7a\uff0c\u4e0d\u8fdb\u884c\u6570\u636e\u5408\u8ba1");
                    return regionDataSet;
                }
                rowGroupKey.append(valueByLink.getAsString());
            }
            GroupRowData rowPack = new GroupRowData();
            rowPack.setRow(iRowData);
            String rowKey = rowGroupKey.toString();
            rowPack.setGroupKey(rowKey);
            int deep = 0;
            for (int levelNum = gradeLevels.size() - 1; levelNum >= 0; --levelNum) {
                Integer gradeLevel = gradeLevels.get(levelNum);
                if (gradeLevel > rowKey.length()) {
                    gradeLevel = rowKey.length();
                }
                String groupKey = gradeLevel < rowKey.length() ? rowGroupKey.substring(0, gradeLevel) : rowGroupKey.toString();
                GroupRowData groupRowData = groupRowDataMap.computeIfAbsent(groupKey, key -> this.initGroupRowData(regionDataSet, (Set<String>)gradleLinkSet, (String)key));
                groupRowData.setGroupingFlag(deep);
                ++deep;
                for (int i = 0; i < metaData.size(); ++i) {
                    IDataValue iDataValue = iRowData.getLinkDataValues().get(i);
                    AbstractData abstractData = iDataValue.getAbstractData();
                    groupRowData.gatherValue(i, abstractData);
                }
                groupRowData.getChildren().add(rowPack);
            }
            if (regionGradeInfo.isQueryDetails()) {
                allRows.add(rowPack);
            }
            for (int i = 0; i < metaData.size(); ++i) {
                IDataValue iDataValue = iRowData.getLinkDataValues().get(i);
                AbstractData abstractData = iDataValue.getAbstractData();
                root.gatherValue(i, abstractData);
            }
        }
        Collection groupRowData = groupRowDataMap.values();
        for (GroupRowData groupRowDatum : groupRowData) {
            if (regionGradeInfo.isHideSingleDetail() && groupRowDatum.getChildren().size() == 1) continue;
            allRows.add(0, groupRowDatum.gatherRow());
            String groupKey = groupRowDatum.getGroupKey();
            int startIndex = 0;
            for (GradeLink gradeLink : gradeLinks) {
                IDataValue dataValue = groupRowDatum.getDataValueByLink(gradeLink.getLinkKey());
                String linkValue = dataValue.getAsString();
                if (linkValue == null) continue;
                int endIndex = startIndex + linkValue.length();
                if (groupKey.length() >= endIndex) {
                    linkValue = groupKey.substring(startIndex, endIndex);
                } else {
                    StringBuilder linkValueBuilder = new StringBuilder(groupKey.substring(startIndex));
                    if (linkValueBuilder.length() < linkValue.length()) {
                        int zeroCount = linkValue.length() - linkValueBuilder.length();
                        for (int i = 0; i < zeroCount; ++i) {
                            linkValueBuilder.append("0");
                        }
                        linkValue = linkValueBuilder.toString();
                    }
                }
                if (dataValue instanceof DataValue) {
                    AbstractData abstractData = AbstractData.valueOf((String)linkValue);
                    ((DataValue)dataValue).setAbstractData(abstractData);
                }
                startIndex = endIndex;
            }
        }
        if (querySummary) {
            root.setGroupingFlag(gradeLevels.size());
            allRows.add(0, root.gatherRow());
        }
        List<IRowData> list = allRows.stream().sorted().map(GroupRowData::getRow).collect(Collectors.toList());
        RegionDataSet gatherSet = new RegionDataSet(regionDataSet);
        gatherSet.setRows(list);
        gatherSet.setTotalCount(list.size());
        gatherSet.setSupportTreeGroup(true);
        return gatherSet;
    }

    public GroupRowData initGroupRowData(IRegionDataSet regionDataSet, Set<String> gradeLinks, String groupDimValue) {
        GroupRowData groupRowData = new GroupRowData();
        groupRowData.setGroupKey(groupDimValue);
        groupRowData.setMasterDimension(regionDataSet.getMasterDimension());
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        for (FixedDimensionValue dimValue : regionDataSet.getMasterDimension()) {
            builder.setValue(dimValue.getName(), dimValue.getEntityID(), dimValue.getValue());
        }
        builder.setValue("GROUP_KEY", "", (Object)groupDimValue);
        DimensionCombination combination = builder.getCombination();
        groupRowData.setDimension(combination);
        for (IMetaData metaDatum : regionDataSet.getMetaData()) {
            List<GroupDataValue> groupDataValues = groupRowData.getGroupDataValues();
            if (gradeLinks != null && gradeLinks.contains(metaDatum.getLinkKey())) {
                groupDataValues.add(new GroupDataValue(metaDatum, 6));
                continue;
            }
            groupDataValues.add(new GroupDataValue(metaDatum));
        }
        return groupRowData;
    }

    private IRegionDataSet gradeAllGather(IRegionDataSet regionDataSet, RegionGradeInfo regionGradeInfo) {
        List<IMetaData> metaData = regionDataSet.getMetaData();
        List<IRowData> rowData = regionDataSet.getRowData();
        GroupRowData root = this.initGroupRowData(regionDataSet, null, "");
        root.setGroupingFlag(0);
        for (IRowData rowDatum : rowData) {
            for (int i = 0; i < metaData.size(); ++i) {
                IDataValue iDataValue = rowDatum.getLinkDataValues().get(i);
                AbstractData abstractData = iDataValue.getAbstractData();
                root.gatherValue(i, abstractData);
            }
        }
        ArrayList<IRowData> gatherRows = new ArrayList<IRowData>();
        gatherRows.add(root.gatherRow());
        if (regionGradeInfo.isQueryDetails()) {
            gatherRows.addAll(rowData);
        }
        RegionDataSet gatherSet = new RegionDataSet(regionDataSet);
        gatherSet.setTotalCount(gatherSet.getTotalCount() + 1);
        gatherSet.setRows(gatherRows);
        gatherSet.setSupportTreeGroup(true);
        return gatherSet;
    }

    @Override
    public IRegionDataSet gradeGather(IRegionDataSet regionDataSet, RegionRelation relation) {
        return this.gradeGather(regionDataSet, relation.getGradeInfo(), relation);
    }

    /*
     * WARNING - void declaration
     */
    private GradeEnumTree getEnumTree(EntityViewDefine entityViewDefine, RegionRelation relation) throws Exception {
        void var12_16;
        IEntityQuery entityQuery = this.entityDataService.getEntityQuery();
        DimensionValueSet masterKeys = new DimensionValueSet();
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setEntityView(entityViewDefine);
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        context.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.controller, this.runtimeController, this.iEntityViewRunTimeController, relation.getFormSchemeDefine().getKey());
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(masterKeys);
        context.setPeriodView(relation.getFormSchemeDefine().getDateTime());
        IEntityTable entityTable = entityQuery.executeReader((IContext)context);
        List entityRows = entityTable.getAllRows();
        int treeDepth = 1;
        HashMap<String, EnumRowItem> enumRowItems = new HashMap<String, EnumRowItem>();
        for (IEntityRow iEntityRow : entityRows) {
            EnumRowItem enumRowItem = new EnumRowItem(iEntityRow.getEntityKeyData(), iEntityRow.getTitle(), iEntityRow.getParentEntityKey());
            enumRowItem.setLeaf(true);
            enumRowItem.setOrder(iEntityRow.getEntityOrder());
            if (enumRowItems.containsKey(iEntityRow.getEntityKeyData())) continue;
            enumRowItems.put(iEntityRow.getEntityKeyData(), enumRowItem);
        }
        for (Map.Entry entry : enumRowItems.entrySet()) {
            String parentCode = ((EnumRowItem)entry.getValue()).getParentCode();
            if (StringUtils.isEmpty((String)parentCode)) continue;
            if (!enumRowItems.containsKey(parentCode)) {
                ((EnumRowItem)entry.getValue()).setParentCode("");
                continue;
            }
            ArrayList<String> parentPath = new ArrayList<String>();
            this.getParentPath(enumRowItems, parentCode, parentPath);
            Collections.reverse(parentPath);
            ((EnumRowItem)entry.getValue()).setParentPath(parentPath);
            int pathDepth = parentPath.size() + 1;
            if (pathDepth <= treeDepth) continue;
            treeDepth = pathDepth;
        }
        ArrayList<Integer> gradeLevels = new ArrayList<Integer>();
        boolean bl = true;
        while (var12_16 <= treeDepth) {
            gradeLevels.add((int)var12_16);
            ++var12_16;
        }
        GradeEnumTree gradeEnumTree = new GradeEnumTree();
        gradeEnumTree.setEntityViewId(entityViewDefine.getEntityId());
        gradeEnumTree.setTreeDepth(treeDepth);
        gradeEnumTree.setEnumRowItems(enumRowItems);
        gradeEnumTree.setGradeLevels(gradeLevels);
        return gradeEnumTree;
    }

    private void getParentPath(HashMap<String, EnumRowItem> enumRowItems, String parentCode, List<String> parentPath) {
        if (parentPath.contains(parentCode)) {
            return;
        }
        parentPath.add(parentCode);
        if (StringUtils.isEmpty((String)parentCode) || !enumRowItems.containsKey(parentCode)) {
            return;
        }
        EnumRowItem enumRowItem = enumRowItems.get(parentCode);
        enumRowItem.setLeaf(false);
        String newParentCode = enumRowItem.getParentCode();
        if (StringUtils.isEmpty((String)newParentCode) || !enumRowItems.containsKey(newParentCode)) {
            return;
        }
        this.getParentPath(enumRowItems, newParentCode, parentPath);
    }
}

