/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.annotation.output.FormAnnotationResult
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.CalcItem
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.snapshot.service.DataOperationService
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.CalcDimensionLink;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IAnnotationApplyService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.GradeDataLoader;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotDataQueryServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotDataQueryServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    protected IAnnotationApplyService annotationService;
    @Autowired
    private DataOperationService dataOperationService;
    @Autowired
    private IJtableFileService jtableFileService;

    public RegionDataSet queryRegionDatas(RegionQueryInfo regionQueryInfo) {
        RegionDataSet regionDataSet = new RegionDataSet();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionQueryInfo.getRegionKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            ArrayList cells = new ArrayList();
            regionDataSet.getCells().put(regionQueryInfo.getRegionKey(), cells);
            return regionDataSet;
        }
        DataCrudUtil.initRegionQueryInfo(regionRelation, regionQueryInfo);
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        DataRegionKind regionKind = regionDefine.getRegionKind();
        if (regionKind != DataRegionKind.DATA_REGION_SIMPLE) {
            int queryPage;
            RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
            regionDataSet = restructureInfo.getGrade() != null && (!restructureInfo.getGrade().getGradeCells().isEmpty() || restructureInfo.getGrade().isSum()) ? this.queryGradeRegionDatas(regionQueryInfo, regionRelation) : this.queryFloatRegionDatas(regionQueryInfo, regionRelation);
            if (regionDataSet.getData().size() > 0) {
                if (regionQueryInfo.getPagerInfo() != null) {
                    int currentPage = regionQueryInfo.getPagerInfo().getOffset();
                    regionDataSet.setCurrentPage(currentPage);
                }
            } else if (regionQueryInfo.getPagerInfo() != null && (queryPage = regionQueryInfo.getPagerInfo().getOffset()) != 0) {
                PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
                pagerInfo.setOffset(--queryPage);
                regionDataSet = this.queryRegionDatas(regionQueryInfo);
                regionDataSet.setCurrentPage(queryPage);
            }
        } else {
            regionDataSet = this.queryFixRegionDatas(regionQueryInfo, regionRelation);
        }
        return regionDataSet;
    }

    public RegionDataSet queryRegionDatas(RegionQueryInfo regionQueryInfo, boolean sumData) {
        RegionDataSet regionDataSet = new RegionDataSet();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionQueryInfo.getRegionKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            ArrayList cells = new ArrayList();
            regionDataSet.getCells().put(regionQueryInfo.getRegionKey(), cells);
            return regionDataSet;
        }
        DataCrudUtil.initRegionQueryInfo(regionRelation, regionQueryInfo);
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        DataRegionKind regionKind = regionDefine.getRegionKind();
        if (regionKind != DataRegionKind.DATA_REGION_SIMPLE) {
            int queryPage;
            RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
            regionDataSet = restructureInfo.getGrade() != null && (!restructureInfo.getGrade().getGradeCells().isEmpty() || restructureInfo.getGrade().isSum()) ? this.queryGradeRegionDatas(regionQueryInfo, regionRelation, sumData) : this.queryFloatRegionDatas(regionQueryInfo, regionRelation);
            if (regionDataSet.getData().size() > 0) {
                if (regionQueryInfo.getPagerInfo() != null) {
                    int currentPage = regionQueryInfo.getPagerInfo().getOffset();
                    regionDataSet.setCurrentPage(currentPage);
                }
            } else if (regionQueryInfo.getPagerInfo() != null && (queryPage = regionQueryInfo.getPagerInfo().getOffset()) != 0) {
                PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
                pagerInfo.setOffset(--queryPage);
                regionDataSet = this.queryRegionDatas(regionQueryInfo);
                regionDataSet.setCurrentPage(queryPage);
            }
        } else {
            regionDataSet = this.queryFixRegionDatas(regionQueryInfo, regionRelation);
        }
        return regionDataSet;
    }

    private RegionDataSet queryFixRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        if (regionQueryInfo.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String id = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            iRegionDataSet = this.dataOperationService.querySanpshotData(queryInfoBuilder.build(), id);
        } else {
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        }
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        List<Object> linkKeys = new ArrayList();
        if (metaDatas != null && metaDatas.size() > 0) {
            linkKeys = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
        }
        cells.put(regionKey, linkKeys);
        regionDataSet.setCells(cells);
        Map<String, LinkData> regionAllLinkMap = DataCrudUtil.getRegionAllLinkMap(regionRelation.getMetaData(null));
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey);
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(iRegionDataSet.getTotalCount());
        RegionAnnotationResult regionAnnotationResult = this.getRegionAnnotation(regionKey, jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        return regionDataSet;
    }

    private RegionDataSet queryFloatRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        if (regionQueryInfo.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String id = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            iRegionDataSet = this.dataOperationService.querySanpshotData(queryInfoBuilder.build(), id);
        } else {
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        }
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        ArrayList<String> linkKeys = new ArrayList<String>();
        linkKeys.add(0, "ID");
        linkKeys.add(1, "FLOATORDER");
        int floatOrderIndex = 0;
        if (metaDatas != null && metaDatas.size() > 0) {
            List metaLinks = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
            floatOrderIndex = metaLinks.indexOf("FLOATORDER");
            metaLinks.remove(floatOrderIndex);
            linkKeys.addAll(metaLinks);
        }
        cells.put(regionKey, linkKeys);
        regionDataSet.setCells(cells);
        Map<String, LinkData> regionAllLinkMap = DataCrudUtil.getRegionAllLinkMap(regionRelation.getMetaData(null));
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, jtableContext);
        List<Object> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        List filledEnumLinks = regionRelation.getFilledEnumLinks();
        LinkedHashMap<String, FieldData> dimName2Field = new LinkedHashMap<String, FieldData>();
        for (FieldData fieldData : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
            dimName2Field.put(dimensionName, fieldData);
        }
        for (IRowData iRowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            String rowID = DataCrudUtil.buildFloatRowID(iRowData, dimName2Field, filledEnumLinks);
            DataCrudUtil.setDataAndFormatData(iRowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(iRegionDataSet.getTotalCount());
        RegionAnnotationResult regionAnnotationResult = this.getRegionAnnotation(regionKey, jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        this.setCalcDimensionLinksForFloat(regionQueryInfo, regionDataSet, bizKeyOrderFields);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (MetaData filledEnumLink : filledEnumLinks) {
            arrayList.add(linkKeys.indexOf(filledEnumLink.getLinkKey()));
        }
        this.setMergeCellsForFloat(regionKey, regionDataSet, arrayList, 0, 0, regionDataSet.getData().size() - 1);
        return regionDataSet;
    }

    private void setCalcDimensionLinksForFloat(RegionQueryInfo regionQueryInfo, RegionDataSet regionDataSet, List<FieldData> bizKeyOrderFields) {
        JtableContext jtableContext = regionQueryInfo.getContext();
        if (jtableContext.getFormulaSchemeKey() != null && null != jtableContext.getFormKey()) {
            IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
            IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
            List dimensionCalcCells = formulaRunTimeController.getDimensionCalcCells(jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey());
            for (CalcItem calcItem : dimensionCalcCells) {
                String linkKey = calcItem.getLinkId();
                DimensionValueSet dimensionValueSet = calcItem.getDimValues();
                StringBuffer bizKeyStrBuf = new StringBuffer();
                for (FieldData bizKeyField : bizKeyOrderFields) {
                    String dimensionName = jtableDataEngineService.getDimensionName(bizKeyField);
                    if (!dimensionValueSet.hasValue(dimensionName)) continue;
                    String bizKeyValue = dimensionValueSet.getValue(dimensionName).toString();
                    if (bizKeyStrBuf.length() > 0) {
                        bizKeyStrBuf.append("#^$");
                    }
                    bizKeyStrBuf.append(bizKeyValue);
                }
                regionDataSet.getCalcDimensionLinks().add(new CalcDimensionLink(linkKey, bizKeyStrBuf.toString()));
            }
        }
    }

    private void setMergeCellsForFloat(String regionKey, RegionDataSet regionDataSet, List<Integer> fillEntityIndex, int mergeLevel, int rowStart, int rowEnd) {
        if (mergeLevel < fillEntityIndex.size() - 1) {
            List<Object> mergeIndexList;
            RegionData regionData = this.jtableParamService.getRegion(regionKey);
            int entityIndex = fillEntityIndex.get(mergeLevel);
            String entityLink = regionDataSet.getCells().get(regionData.getKey()).get(entityIndex);
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
                    this.setMergeCellsForFloat(regionKey, regionDataSet, fillEntityIndex, mergeLevel + 1, mergeStart, mergeEnd);
                }
                if (!entityValue.equals(mergeValue)) {
                    mergeValue = entityValue;
                    mergeStart = rowIndex;
                }
                mergeEnd = rowIndex++;
            }
        }
    }

    private RegionDataSet queryGradeRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        GroupingRelationEvn groupingRelationEvn = new GroupingRelationEvn(region, regionQueryInfo);
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        if (regionQueryInfo.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String id = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            iRegionDataSet = this.dataOperationService.querySanpshotData(queryInfoBuilder.build(), id);
        } else {
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        }
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        ArrayList<String> linkKeys = new ArrayList<String>();
        linkKeys.add(0, "ID");
        linkKeys.add(1, "FLOATORDER");
        linkKeys.add(2, "SUM");
        int floatOrderIndex = 0;
        if (metaDatas != null && metaDatas.size() > 0) {
            List metaLinks = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
            floatOrderIndex = metaLinks.indexOf("FLOATORDER");
            metaLinks.remove(floatOrderIndex);
            linkKeys.addAll(metaLinks);
        }
        cells.put(regionKey, linkKeys);
        regionDataSet.setCells(cells);
        Map<String, LinkData> regionAllLinkMap = DataCrudUtil.getRegionAllLinkMap(regionRelation.getMetaData(null));
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, jtableContext);
        ArrayList<FieldData> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        LinkedHashMap<String, FieldData> dimName2Field = new LinkedHashMap<String, FieldData>();
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(bizKeyOrderField);
            dimName2Field.put(dimensionName, bizKeyOrderField);
        }
        List<String> groupingLinks = DataCrudUtil.getGroupingLinks(regionRelation, regionQueryInfo);
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey);
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            String rowID = DataCrudUtil.buildFloatRowID(rowData, dimName2Field, null);
            Object sumTitle = DataCrudUtil.buildSumTitle(groupingRelationEvn, jtableContext, groupingLinks, rowData);
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, sumTitle, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(iRegionDataSet.getTotalCount());
        RegionAnnotationResult regionAnnotationResult = this.getRegionAnnotation(regionKey, jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        this.setRelRootForGrade(iRegionDataSet, regionDataSet);
        this.setCalcDimensionLinksForGrad(regionQueryInfo, regionDataSet, bizKeyOrderFields);
        return regionDataSet;
    }

    private RegionDataSet queryGradeRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation, boolean sumData) {
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        GroupingRelationEvn groupingRelationEvn = new GroupingRelationEvn(region, regionQueryInfo);
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        if (regionQueryInfo.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String id = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            iRegionDataSet = this.dataOperationService.querySanpshotData(queryInfoBuilder.build(), id);
        } else {
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        }
        HashedMap<String, List<String>> cells = new HashedMap<String, List<String>>();
        List metaDatas = iRegionDataSet.getMetaData();
        ArrayList<String> linkKeys = new ArrayList<String>();
        linkKeys.add(0, "ID");
        linkKeys.add(1, "FLOATORDER");
        linkKeys.add(2, "SUM");
        int floatOrderIndex = 0;
        if (metaDatas != null && metaDatas.size() > 0) {
            List metaLinks = metaDatas.stream().map(IMetaData::getLinkKey).collect(Collectors.toList());
            floatOrderIndex = metaLinks.indexOf("FLOATORDER");
            metaLinks.remove(floatOrderIndex);
            linkKeys.addAll(metaLinks);
        }
        cells.put(regionKey, linkKeys);
        regionDataSet.setCells(cells);
        Map<String, LinkData> regionAllLinkMap = DataCrudUtil.getRegionAllLinkMap(regionRelation.getMetaData(null));
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, jtableContext);
        ArrayList<FieldData> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        LinkedHashMap<String, FieldData> dimName2Field = new LinkedHashMap<String, FieldData>();
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(bizKeyOrderField);
            dimName2Field.put(dimensionName, bizKeyOrderField);
        }
        List<String> groupingLinks = DataCrudUtil.getGroupingLinks(regionRelation, regionQueryInfo);
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey);
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            if (!sumData && rowData.getGroupTreeDeep() >= 0) continue;
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            String rowID = DataCrudUtil.buildFloatRowID(rowData, dimName2Field, null);
            Object sumTitle = DataCrudUtil.buildSumTitle(groupingRelationEvn, jtableContext, groupingLinks, rowData);
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, sumTitle, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(iRegionDataSet.getTotalCount());
        RegionAnnotationResult regionAnnotationResult = this.getRegionAnnotation(regionKey, jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        this.setRelRootForGrade(iRegionDataSet, regionDataSet);
        this.setCalcDimensionLinksForGrad(regionQueryInfo, regionDataSet, bizKeyOrderFields);
        return regionDataSet;
    }

    private void setRelRootForGrade(IRegionDataSet iRegionDataSet, RegionDataSet regionDataSet) {
        GradeDataLoader loader = new GradeDataLoader(iRegionDataSet, regionDataSet);
        loader.loadRegionGradeData();
    }

    private void setCalcDimensionLinksForGrad(RegionQueryInfo regionQueryInfo, RegionDataSet regionDataSet, List<FieldData> bizKeyOrderFields) {
        JtableContext jtableContext = regionQueryInfo.getContext();
        EntityViewData entityViewData = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String unitDimension = entityViewData.getDimensionName();
        if (jtableContext.getFormulaSchemeKey() != null && null != jtableContext.getFormKey()) {
            IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
            IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
            List dimensionCalcCells = formulaRunTimeController.getDimensionCalcCells(jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey());
            for (CalcItem calcItem : dimensionCalcCells) {
                String linkKey = calcItem.getLinkId();
                DimensionValueSet dimensionValueSet = calcItem.getDimValues();
                StringBuffer bizKeyStrBuf = new StringBuffer();
                for (FieldData bizKeyField : bizKeyOrderFields) {
                    String dimensionName = jtableDataEngineService.getDimensionName(bizKeyField);
                    if (!dimensionValueSet.hasValue(dimensionName)) continue;
                    String bizKeyValue = dimensionValueSet.getValue(dimensionName).toString();
                    if (bizKeyStrBuf.length() > 0) {
                        bizKeyStrBuf.append("#^$");
                    }
                    bizKeyStrBuf.append(bizKeyValue);
                }
                if (regionQueryInfo.getRestructureInfo().isUnitAutoSum()) {
                    if (bizKeyStrBuf.length() > 0) {
                        bizKeyStrBuf.append("#^$");
                    }
                    bizKeyStrBuf.append(dimensionValueSet.getValue(unitDimension).toString());
                }
                regionDataSet.getCalcDimensionLinks().add(new CalcDimensionLink(linkKey, bizKeyStrBuf.toString()));
            }
        }
    }

    private void buildDataFormtCache(DataFormaterCache dataFormaterCache, Map<String, LinkData> regionAllLinkMap, String regionKey) {
        LinkData linkData;
        RegionData regionData = this.jtableParamService.getRegion(regionKey);
        HashedMap<String, Set<String>> entityCaptionFields = new HashedMap<String, Set<String>>();
        LinkedHashMap<String, LinkData> linkPosMap = new LinkedHashMap<String, LinkData>();
        JtableContext jtableContext = dataFormaterCache.getJtableContext();
        LinkedHashSet<String> enumFieldPosEntity = new LinkedHashSet<String>();
        for (String key : regionAllLinkMap.keySet()) {
            linkData = regionAllLinkMap.get(key);
            if (linkData == null) continue;
            Position position = new Position(linkData.getCol(), linkData.getRow());
            linkPosMap.put(position.toString(), linkData);
        }
        for (String key : regionAllLinkMap.keySet()) {
            AbstractData expressionEvaluat;
            Optional<EntityDefaultValue> optional;
            boolean regionDimDefaultValueEmpty;
            linkData = regionAllLinkMap.get(key);
            if (!(linkData instanceof EnumLinkData)) continue;
            EnumLinkData enumLink = (EnumLinkData)linkData;
            Set<String> captionFields = null;
            if (entityCaptionFields.containsKey(enumLink.getEntityKey())) {
                captionFields = (Set)entityCaptionFields.get(enumLink.getEntityKey());
            } else {
                captionFields = new LinkedHashSet();
                entityCaptionFields.put(enumLink.getEntityKey(), captionFields);
            }
            if (enumLink.getCapnames() != null && !enumLink.getCapnames().isEmpty()) {
                captionFields.addAll(enumLink.getCapnames());
            }
            if (enumLink.getDropnames() != null && !enumLink.getDropnames().isEmpty()) {
                captionFields.addAll(enumLink.getDropnames());
            }
            if (enumLink.getEnumFieldPosMap() != null && !enumLink.getEnumFieldPosMap().isEmpty()) {
                captionFields.addAll(enumLink.getEnumFieldPosMap().keySet());
                for (Map.Entry<String, String> posMap : enumLink.getEnumFieldPosMap().entrySet()) {
                    String fieldPos = posMap.getValue();
                    LinkData enumFieldPosLink = (LinkData)linkPosMap.get(fieldPos);
                    if (enumFieldPosLink == null || StringUtils.isEmpty((String)enumLink.getEntityKey())) continue;
                    enumFieldPosEntity.add(enumLink.getEntityKey());
                }
            }
            if (!(regionDimDefaultValueEmpty = CollectionUtils.isEmpty(regionData.getRegionEntityDefaultValue())) && (optional = regionData.getRegionEntityDefaultValue().stream().filter(Objects::nonNull).filter(e -> e.getEntityId() != null).filter(e -> e.getEntityId().equals(enumLink.getEntityKey())).findFirst()).isPresent()) {
                Object val = RegionSettingUtil.checkRegionDefaultValueGetIfAbsent(regionData, linkData);
                linkData.getFormatData((AbstractData)new StringData(val.toString()), dataFormaterCache);
                continue;
            }
            if (!StringUtils.isNotEmpty((String)linkData.getDefaultValue()) || (expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(linkData.getDefaultValue(), jtableContext, DimensionValueSetUtil.getDimensionValueSet(jtableContext))) == null) continue;
            linkData.getFormatData(expressionEvaluat, dataFormaterCache);
        }
        for (String entityKey : enumFieldPosEntity) {
            EntityQueryByViewInfo entityQueryByViewInfo = new EntityQueryByViewInfo();
            entityQueryByViewInfo.setEntityViewKey(entityKey);
            entityQueryByViewInfo.setContext(dataFormaterCache.getJtableContext());
            entityQueryByViewInfo.setCaptionFields((Set)entityCaptionFields.get(entityKey));
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryByViewInfo);
            dataFormaterCache.addEntityData(entityKey, entityReturnInfo);
        }
        dataFormaterCache.setEntityCaptionFields(entityCaptionFields);
    }

    private void getFileDataMap(List<IRowData> rowDatas, DataFormaterCache dataFormaterCache) {
        ArrayList<String> fileGroupKeys = new ArrayList<String>();
        for (IRowData rowData : rowDatas) {
            List rowDataValues = rowData.getLinkDataValues();
            for (IDataValue dataValue : rowDataValues) {
                IMetaData metaData = dataValue.getMetaData();
                DataFieldType dataFieldType = metaData.getDataFieldType();
                if (dataFieldType != DataFieldType.FILE || dataValue.getAsNull()) continue;
                fileGroupKeys.add(dataValue.getAsString());
            }
        }
        if (fileGroupKeys != null && fileGroupKeys.size() > 0) {
            this.jtableFileService.getFileDataMap(fileGroupKeys, dataFormaterCache);
        }
    }

    private RegionAnnotationResult getRegionAnnotation(String regionKey, JtableContext jtableContext) {
        Map regionAnnotationMap;
        RegionAnnotationResult regionAnnotationResult = new RegionAnnotationResult();
        FormAnnotationResult formAnnotationResult = this.annotationService.queryFormAnnotation(jtableContext, regionKey);
        regionAnnotationResult.setRegionKey(regionKey);
        if (formAnnotationResult != null && (regionAnnotationMap = formAnnotationResult.getRegions()) != null && regionAnnotationMap.containsKey(regionKey)) {
            regionAnnotationResult = (RegionAnnotationResult)regionAnnotationMap.get(regionKey);
        }
        return regionAnnotationResult;
    }
}

