/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.annotation.output.FormAnnotationResult
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.CalcItem
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportDataLinkFinder
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.definition.util.TitleAndKey
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.snapshot.service.DataOperationService
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportDataLinkFinder;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.TitleAndKey;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.CalcDimensionLink;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.LinkSimpleData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.input.SingleCellValueQueryInfo;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CardRowData;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.CellValueInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.MultiPeriodRegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;
import com.jiuqi.nr.jtable.service.IAnnotationApplyService;
import com.jiuqi.nr.jtable.service.ICustomRegionDataSetService;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableDataQueryService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FilterMethod;
import com.jiuqi.nr.jtable.util.GradeDataLoader;
import com.jiuqi.nr.jtable.util.JLoggerUtils;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
public class JtableDataQueryServiceImpl
implements IJtableDataQueryService {
    private static final Logger logger = LoggerFactory.getLogger(JtableDataQueryServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IJtableContextService jtableContextService;
    @Autowired
    private IJtableFileService jtableFileService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private DataValueFormatterBuilderFactory formatFactory;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    protected IAnnotationApplyService annotationService;
    @Autowired
    private DataOperationService dataOperationService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ICustomRegionsGradeService iCustomRegionsGradeService;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private JLoggerUtils jLoggerUtils;
    @Autowired(required=false)
    private ICustomRegionDataSetService iCustomRegionDataSetService;

    @Override
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
            RegionRestructureInfo restructureInfo;
            boolean enableLevelFloat;
            String displayLevel = regionDefine.getDisplayLevel();
            boolean bl = enableLevelFloat = StringUtils.isNotEmpty((String)displayLevel) && displayLevel.split(";").length > 1;
            regionDataSet = enableLevelFloat ? this.queryLevelFloatRegionDatas(regionQueryInfo, regionRelation) : ((restructureInfo = regionQueryInfo.getRestructureInfo()).getGrade() != null && (!restructureInfo.getGrade().getGradeCells().isEmpty() || restructureInfo.getGrade().isSum()) ? this.queryGradeRegionDatas(regionQueryInfo, regionRelation) : this.queryFloatRegionDatas(regionQueryInfo, regionRelation));
            if (regionDataSet.getData().size() > 0) {
                if (regionQueryInfo.getPagerInfo() != null) {
                    int currentPage = regionQueryInfo.getPagerInfo().getOffset();
                    regionDataSet.setCurrentPage(currentPage);
                }
            } else if (regionQueryInfo.getPagerInfo() != null && regionQueryInfo.isQueryPreData() && (queryPage = regionQueryInfo.getPagerInfo().getOffset()) != 0) {
                PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
                pagerInfo.setOffset(--queryPage);
                regionDataSet = this.queryRegionDatas(regionQueryInfo);
                regionDataSet.setCurrentPage(queryPage);
            }
        } else {
            regionDataSet = this.queryFixRegionDatas(regionQueryInfo, regionRelation);
        }
        if (this.iCustomRegionDataSetService != null) {
            regionDataSet = this.iCustomRegionDataSetService.getCustomRegionDataSet(regionQueryInfo, regionDataSet);
        }
        return regionDataSet;
    }

    private RegionDataSet queryFixRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        FormType formType;
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        List<String> selectLinks = DataCrudUtil.getSelectLinks(regionRelation, regionQueryInfo.getFilterInfo());
        for (String selectLink : selectLinks) {
            queryInfoBuilder.select(selectLink);
        }
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
        boolean fmdmForm = false;
        FormDefine formDefine = regionRelation.getFormDefine();
        if (formDefine != null && (formType = formDefine.getFormType()) == FormType.FORM_TYPE_NEWFMDM) {
            fmdmForm = true;
        }
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey, fmdmForm);
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            DataCrudUtil.dataValueBalanceFromat(rowData, jtableContext, formData);
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
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        boolean existTopTenFilter = DataCrudUtil.existTopTenFilter(regionQueryInfo.getFilterInfo());
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
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey, false);
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
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
            DataCrudUtil.dataValueBalanceFromat(iRowData, jtableContext, formData);
            String rowID = DataCrudUtil.buildFloatRowID(iRowData, dimName2Field, filledEnumLinks);
            DataCrudUtil.setDataAndFormatData(iRowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(existTopTenFilter ? iRegionDataSet.getRowCount() : iRegionDataSet.getTotalCount());
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

    private void setMergeCellsForFloat(String regionKey, RegionDataSet regionDataSet, List<Integer> mergeLinkIndexs, int mergeLevel, int rowStart, int rowEnd) {
        if (mergeLevel < mergeLinkIndexs.size() - 1) {
            List<Object> mergeIndexList;
            RegionData regionData = this.jtableParamService.getRegion(regionKey);
            int entityIndex = mergeLinkIndexs.get(mergeLevel);
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
                    this.setMergeCellsForFloat(regionKey, regionDataSet, mergeLinkIndexs, mergeLevel + 1, mergeStart, mergeEnd);
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
        String formSchemeKey = jtableContext.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        String dataTimeDimensionName = this.iPeriodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        String dataTime = jtableContext.getDimensionSet().get(dataTimeDimensionName).getValue();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        boolean existTopTenFilter = DataCrudUtil.existTopTenFilter(regionQueryInfo.getFilterInfo());
        if (regionQueryInfo.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
            String id = regionQueryInfo.getContext().getDimensionSet().get("DATASNAPSHOTID").getValue();
            iRegionDataSet = this.dataOperationService.querySanpshotData(queryInfoBuilder.build(), id);
        } else {
            RegionGradeInfo crudGradeInfo = null;
            if (regionQueryInfo.getRestructureInfo().isCustomGrade()) {
                boolean noSumData = regionQueryInfo.getRestructureInfo().isNoSumData();
                if (noSumData) {
                    crudGradeInfo = new RegionGradeInfo();
                    crudGradeInfo.setGrade(false);
                    crudGradeInfo.setQuerySummary(false);
                } else {
                    com.jiuqi.nr.jtable.params.input.RegionGradeInfo grade = regionQueryInfo.getRestructureInfo().getGrade();
                    if (grade != null) {
                        crudGradeInfo = DataCrudUtil.getCrudGradeInfo(grade);
                    }
                }
            } else if (this.iCustomRegionsGradeService != null) {
                ArrayList<String> regionKeys = new ArrayList<String>();
                regionKeys.add(regionKey);
                Map<String, RegionGradeInfo> customRegionsGrade = this.iCustomRegionsGradeService.getCustomRegionsGrade(regionKeys, dataTime);
                if (customRegionsGrade != null && !customRegionsGrade.isEmpty() && customRegionsGrade.containsKey(regionKey)) {
                    crudGradeInfo = customRegionsGrade.get(regionKey);
                }
            }
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build(), crudGradeInfo);
        }
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), formSchemeKey);
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
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey, false);
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        List rowDatas = iRegionDataSet.getRowData();
        int rowNum = rowDatas.size();
        int linkNum = linkKeys.size();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>(rowNum);
        ArrayList<List<Object>> dataFormats = new ArrayList<List<Object>>(rowNum);
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>(linkNum);
            ArrayList<Object> dataFormatList = new ArrayList<Object>(linkNum);
            DataCrudUtil.dataValueBalanceFromat(rowData, jtableContext, formData);
            String rowID = DataCrudUtil.buildFloatRowID(rowData, dimName2Field, null);
            String sumTitle = "";
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, sumTitle, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(existTopTenFilter ? iRegionDataSet.getRowCount() : iRegionDataSet.getTotalCount());
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

    private RegionDataSet queryLevelFloatRegionDatas(RegionQueryInfo regionQueryInfo, RegionRelation regionRelation) {
        IRegionDataSet iRegionDataSet;
        RegionDataSet regionDataSet = new RegionDataSet();
        JtableContext jtableContext = regionQueryInfo.getContext();
        String regionKey = regionQueryInfo.getRegionKey();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(regionQueryInfo.isDesensitized());
        DataCrudUtil.buildRegionFilter(queryInfoBuilder, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildPaging(queryInfoBuilder, regionQueryInfo.getPagerInfo());
        DataCrudUtil.buildFilterColumns(regionRelation, queryInfoBuilder, jtableContext, regionQueryInfo.getFilterInfo());
        DataCrudUtil.buildOrderColumns(regionRelation, queryInfoBuilder, regionQueryInfo.getFilterInfo());
        boolean existTopTenFilter = DataCrudUtil.existTopTenFilter(regionQueryInfo.getFilterInfo());
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
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionKey, false);
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
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
            DataCrudUtil.dataValueBalanceFromat(iRowData, jtableContext, formData);
            String rowID = DataCrudUtil.buildFloatRowID(iRowData, dimName2Field, filledEnumLinks);
            DataCrudUtil.setDataAndFormatData(iRowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionDataSet.setData(datas);
        regionDataSet.setDataFormat(dataFormats);
        regionDataSet.setTotalCount(existTopTenFilter ? iRegionDataSet.getRowCount() : iRegionDataSet.getTotalCount());
        RegionAnnotationResult regionAnnotationResult = this.getRegionAnnotation(regionKey, jtableContext);
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        this.setCalcDimensionLinksForFloat(regionQueryInfo, regionDataSet, bizKeyOrderFields);
        RegionData regionData = this.jtableParamService.getRegion(regionKey);
        Map<String, Integer> linkLevelMap = regionData.getLinkLevelMap();
        ArrayList<Integer> levelLinks = new ArrayList<Integer>();
        for (String linkKey : linkLevelMap.keySet()) {
            levelLinks.add(linkKeys.indexOf(linkKey));
        }
        ArrayList<Integer> fillEntityIndex = new ArrayList<Integer>();
        for (MetaData filledEnumLink : filledEnumLinks) {
            if (linkLevelMap.containsKey(filledEnumLink.getLinkKey())) continue;
            fillEntityIndex.add(linkKeys.indexOf(filledEnumLink.getLinkKey()));
        }
        this.setMergeCellsForFloat(regionKey, regionDataSet, levelLinks, 0, 0, regionDataSet.getData().size() - 1);
        this.setMergeCellsForFloat(regionKey, regionDataSet, fillEntityIndex, 0, 0, regionDataSet.getData().size() - 1);
        return regionDataSet;
    }

    private void buildDataFormtCache(DataFormaterCache dataFormaterCache, Map<String, LinkData> regionAllLinkMap, String regionKey, boolean fmdm) {
        EnumLinkData enumLink;
        LinkData linkData;
        dataFormaterCache.setDesensitized(true);
        RegionData regionData = this.jtableParamService.getRegion(regionKey);
        HashedMap<String, Set<String>> entityCaptionFields = new HashedMap<String, Set<String>>();
        LinkedHashMap<String, LinkData> linkPosMap = new LinkedHashMap<String, LinkData>();
        JtableContext jtableContext = dataFormaterCache.getJtableContext();
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        LinkData orgEnumLink = null;
        dataFormaterCache.setQueryChildrenCount(false);
        for (String key : regionAllLinkMap.keySet()) {
            linkData = regionAllLinkMap.get(key);
            if (linkData == null) continue;
            Position position = new Position(linkData.getCol(), linkData.getRow());
            linkPosMap.put(position.toString(), linkData);
        }
        for (String key : regionAllLinkMap.keySet()) {
            linkData = regionAllLinkMap.get(key);
            if (!(linkData instanceof EnumLinkData)) continue;
            enumLink = (EnumLinkData)linkData;
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
            if (enumLink.getEnumFieldPosMap() == null || enumLink.getEnumFieldPosMap().isEmpty()) continue;
            captionFields.addAll(enumLink.getEnumFieldPosMap().keySet());
        }
        dataFormaterCache.setEntityCaptionFields(entityCaptionFields);
        for (String key : regionAllLinkMap.keySet()) {
            Optional<EntityDefaultValue> optional;
            linkData = regionAllLinkMap.get(key);
            if (!(linkData instanceof EnumLinkData)) continue;
            enumLink = (EnumLinkData)linkData;
            boolean regionDimDefaultValueEmpty = CollectionUtils.isEmpty(regionData.getRegionEntityDefaultValue());
            if (!regionDimDefaultValueEmpty && (optional = regionData.getRegionEntityDefaultValue().stream().filter(Objects::nonNull).filter(e -> e.getEntityId() != null).filter(e -> e.getEntityId().equals(enumLink.getEntityKey()) && linkData.getZbid().equals(e.getFieldKey())).findFirst()).isPresent()) {
                Object val = RegionSettingUtil.checkRegionDefaultValueGetIfAbsent(regionData, linkData);
                linkData.getFormatData((AbstractData)new StringData(val.toString()), dataFormaterCache);
            }
            if (StringUtils.isNotEmpty((String)linkData.getDefaultValue())) {
                if (linkData.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                    linkData.getFormatData((AbstractData)new StringData(linkData.getDefaultValue()), dataFormaterCache);
                } else {
                    AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(linkData.getDefaultValue(), jtableContext, DimensionValueSetUtil.getDimensionValueSet(jtableContext));
                    if (expressionEvaluat != null) {
                        linkData.getFormatData(expressionEvaluat, dataFormaterCache);
                    }
                }
            }
            if (taskDefine == null || !taskDefine.getDw().equals(enumLink.getEntityKey())) continue;
            orgEnumLink = linkData;
        }
        if (fmdm && orgEnumLink != null) {
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            String dimensionName = dwEntity.getDimensionName();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
            Object dimensionValue = dimensionValueSet.getValue(dimensionName);
            StringData stringData = new StringData(dimensionValue.toString());
            orgEnumLink.getFormatData((AbstractData)stringData, dataFormaterCache);
        }
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

    @Override
    public ReportDataSet queryReportFormDatas(ReportDataQueryInfo reportDataQueryInfo) {
        JtableContext jtableContext = reportDataQueryInfo.getContext();
        ReportDataSet reportDataSet = new ReportDataSet();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        Map<String, RegionQueryInfo> regionQueryInfoMap = reportDataQueryInfo.getRegionQueryInfo();
        for (RegionData region : regions) {
            RegionQueryInfo regionDataQueryInfo = new RegionQueryInfo();
            if (regionQueryInfoMap != null && regionQueryInfoMap.containsKey(region.getKey())) {
                regionDataQueryInfo = regionQueryInfoMap.get(region.getKey());
            }
            regionDataQueryInfo.setContext(jtableContext);
            regionDataQueryInfo.setRegionKey(region.getKey());
            RegionDataSet queryRegionDatas = this.queryRegionDatas(regionDataQueryInfo);
            reportDataSet.getQueryData().put(region.getKey(), queryRegionDatas);
        }
        return reportDataSet;
    }

    @Override
    public ReportDataSet queryRegionsDatas(ReportDataQueryInfo reportDataQueryInfo) {
        ReportDataSet reportDataSet = new ReportDataSet();
        JtableContext context = reportDataQueryInfo.getContext();
        Map<String, RegionQueryInfo> regionQueryInfoMap = reportDataQueryInfo.getRegionQueryInfo();
        for (String regionKey : regionQueryInfoMap.keySet()) {
            RegionQueryInfo regionQueryInfo = regionQueryInfoMap.get(regionKey);
            regionQueryInfo.setContext(context);
            RegionDataSet regionDataSet = this.queryRegionDatas(regionQueryInfo);
            reportDataSet.getQueryData().put(regionKey, regionDataSet);
        }
        return reportDataSet;
    }

    @Override
    public RegionDataCount queryRegionDatasCount(RegionQueryInfo regionQueryInfo) {
        RegionDataCount regionDataCount = new RegionDataCount();
        JtableContext jtableContext = regionQueryInfo.getContext();
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionQueryInfo.getRegionKey(), null);
        int totalCount = this.dataQueryService.queryRegionDataCount(queryInfoBuilder.build());
        regionDataCount.setDataType(region.getType());
        regionDataCount.setTotalCount(totalCount);
        return regionDataCount;
    }

    @Override
    public PagerInfo queryFloatRowIndex(RegionQueryInfo regionQueryInfo) {
        PagerInfo pagerInfo = new PagerInfo();
        pagerInfo.setLimit(0);
        pagerInfo.setOffset(-1);
        pagerInfo.setTotal(0);
        String regionKey = regionQueryInfo.getRegionKey();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        int type = region.getType();
        if (type == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return pagerInfo;
        }
        String dataID = regionQueryInfo.getRestructureInfo().getDataID();
        if (regionQueryInfo.getRestructureInfo() == null || StringUtils.isEmpty((String)dataID)) {
            return pagerInfo;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionQueryInfo.getRegionKey(), null);
        List<FieldData> bizKeyOrderFields = new ArrayList<FieldData>();
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionQueryInfo.getRegionKey(), regionQueryInfo.getContext());
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        DataCrudUtil.setBizKeyValueForDimension(builder, dataID, bizKeyOrderFields);
        int rowIndex = this.dataQueryService.queryDataIndex(queryInfoBuilder.build(), builder.getCombination());
        if (rowIndex != -1) {
            pagerInfo.setOffset(rowIndex - 1);
        }
        return pagerInfo;
    }

    @Override
    public RegionSingleDataSet querySingleFloatRowData(RegionQueryInfo regionQueryInfo) {
        RegionData region = this.jtableParamService.getRegion(regionQueryInfo.getRegionKey());
        RegionSingleDataSet regionSingleDataSet = new RegionSingleDataSet();
        ArrayList cells = new ArrayList();
        regionSingleDataSet.getCells().put(regionQueryInfo.getRegionKey(), cells);
        if (region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            logger.info("\u533a\u57df" + region.getTitle() + "\u662f\u56fa\u5b9a\u533a\u57df");
            return regionSingleDataSet;
        }
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionQueryInfo.getRegionKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            logger.info("\u533a\u57df" + regionRelation.getRegionDefine().getTitle() + "\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            return regionSingleDataSet;
        }
        RegionRestructureInfo restructureInfo = regionQueryInfo.getRestructureInfo();
        String dataID = restructureInfo.getDataID();
        if (StringUtils.isEmpty((String)dataID)) {
            logger.info("dataID\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u67e5\u8be2\u5355\u6761\u6570\u636e");
            return regionSingleDataSet;
        }
        JtableContext jtableContext = regionQueryInfo.getContext();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, regionQueryInfo.getRegionKey(), regionRelation);
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionQueryInfo.getRegionKey(), jtableContext);
        ArrayList<FieldData> bizKeyOrderFields = new ArrayList();
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        DataCrudUtil.setBizKeyValueForDimension(builder, regionQueryInfo.getRestructureInfo().getDataID(), bizKeyOrderFields);
        IRegionDataSet iRegionDataSet = this.dataQueryService.dataLocate(queryInfoBuilder.build(), builder.getCombination(), restructureInfo.getOffset());
        if (iRegionDataSet == null || iRegionDataSet.getTotalCount() == 0 || iRegionDataSet.getRowData() == null || iRegionDataSet.getRowData().size() == 0) {
            logger.info("\u672a\u67e5\u5230\u6570\u636e");
            return regionSingleDataSet;
        }
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
        cells.addAll(linkKeys);
        Map<String, LinkData> regionAllLinkMap = DataCrudUtil.getRegionAllLinkMap(regionRelation.getMetaData(null));
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(jtableContext);
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext, new HashedMap<String, List<FileInfo>>(), new HashedMap<String, List<byte[]>>());
        this.buildDataFormtCache(dataFormaterCache, regionAllLinkMap, regionQueryInfo.getRegionKey(), false);
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        List rowDatas = iRegionDataSet.getRowData();
        ArrayList<List<Object>> datas = new ArrayList<List<Object>>();
        LinkedHashMap<String, FieldData> dimName2Field = new LinkedHashMap<String, FieldData>();
        for (FieldData fieldData : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
            dimName2Field.put(dimensionName, fieldData);
        }
        ArrayList<ArrayList<Object>> dataFormats = new ArrayList<ArrayList<Object>>();
        for (IRowData rowData : rowDatas) {
            ArrayList<Object> dataList = new ArrayList<Object>();
            ArrayList<Object> dataFormatList = new ArrayList<Object>();
            DataCrudUtil.dataValueBalanceFromat(rowData, jtableContext, formData);
            String rowID = DataCrudUtil.buildFloatRowID(rowData, dimName2Field, null);
            DataCrudUtil.setDataAndFormatData(rowData, dataList, dataFormatList, dataFormaterCache, formatter, regionAllLinkMap);
            DataCrudUtil.setNonDataFieldPos(floatOrderIndex, rowID, dataList, dataFormatList);
            datas.add(dataList);
            dataFormats.add(dataFormatList);
        }
        regionSingleDataSet.setData(datas);
        int n = this.dataQueryService.queryDataIndex(queryInfoBuilder.build(), builder.getCombination());
        if (n != -1) {
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setLimit(1);
            pagerInfo.setOffset(n);
            pagerInfo.setTotal(1);
            regionSingleDataSet.setPagerInfo(pagerInfo);
        }
        this.getFileDataMap(rowDatas, dataFormaterCache);
        regionSingleDataSet.setFileDataMap(dataFormaterCache.getFileDataMap());
        regionSingleDataSet.setImgDataMap(dataFormaterCache.getImgDataMap());
        regionSingleDataSet.setEntityDataMap(dataFormaterCache.getEntityDataMap());
        return regionSingleDataSet;
    }

    @Override
    public CellDataSet queryCellDataSet(CellValueQueryInfo cellValueQueryInfo) {
        List<CellQueryInfo> cellQueryInfos;
        JtableContext jtableContext = cellValueQueryInfo.getContext();
        CellDataSet cellDataSet = new CellDataSet();
        String linkKey = cellValueQueryInfo.getCellKey();
        LinkData linkData = this.jtableParamService.getLink(linkKey);
        RegionData region = this.jtableParamService.getRegion(linkData.getRegionKey());
        cellDataSet.setCellKey(cellValueQueryInfo.getCellKey());
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(jtableContext, region.getKey(), null);
        queryInfoBuilder.select(linkKey);
        queryInfoBuilder.groupBy(linkKey);
        queryInfoBuilder.setDesensitized(true);
        StringBuffer filterBuf = new StringBuffer();
        if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFilter())) {
            filterBuf.append(" (" + cellValueQueryInfo.getFilter() + ") ");
        }
        if (StringUtils.isNotEmpty((String)region.getFilterCondition())) {
            if (filterBuf.length() != 0) {
                filterBuf.append(" AND ");
            }
            filterBuf.append(" (" + region.getFilterCondition() + ") ");
        }
        if ((cellQueryInfos = cellValueQueryInfo.getCells()) != null && cellQueryInfos.size() > 0) {
            FilterMethod filterMethod = new FilterMethod();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                String otherLinkKey = cellQueryInfo.getCellKey();
                LinkData otherLink = this.jtableParamService.getLink(otherLinkKey);
                queryInfoBuilder.select(otherLinkKey);
                FieldData otherField = this.jtableParamService.getField(otherLink.getZbid());
                otherField.setDataLinkKey(otherLinkKey);
                filterMethod.filterMethod(cellQueryInfo, queryInfoBuilder, jtableContext, otherField);
            }
        }
        if (StringUtils.isNotEmpty((String)filterBuf.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(filterBuf.toString());
            queryInfoBuilder.where((RowFilter)formulaFilter);
        }
        boolean isEnumLink = linkData.getType() == LinkType.LINK_TYPE\uff3fENUM.getValue();
        IRegionDataSet iRegionDataSet = jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID") ? this.dataOperationService.querySanpshotCellData(queryInfoBuilder.build(), jtableContext.getDimensionSet().get("DATASNAPSHOTID").getValue()) : this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        List rowDatas = iRegionDataSet.getRowData();
        int maxNumber = 201;
        ArrayList<Object> values = new ArrayList<Object>();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
        dataFormaterCache.jsonData();
        for (IRowData rowData : rowDatas) {
            if (values.size() >= maxNumber && !isEnumLink) break;
            IDataValue dataValue = rowData.getDataValueByLink(linkKey);
            Object value = linkData.getFormatData(dataValue.getAbstractData(), dataFormaterCache, jtableContext);
            if (StringUtils.isNotEmpty((String)cellValueQueryInfo.getFuzzyValue()) && !linkData.checkFuzzyValue(dataValue.getAbstractData(), value, dataFormaterCache, cellValueQueryInfo.getFuzzyValue())) continue;
            values.add(value);
        }
        cellDataSet.setMessage("success");
        cellDataSet.setCount(values.size());
        if (isEnumLink) {
            List<Object> enumValues = this.setPageEnumValue(values, cellValueQueryInfo.getPagerInfo());
            cellDataSet.setCurrentPage(cellValueQueryInfo.getPagerInfo() == null ? 0 : cellValueQueryInfo.getPagerInfo().getOffset());
            cellDataSet.setData(enumValues);
        } else {
            cellDataSet.setData(values);
        }
        return cellDataSet;
    }

    private List<Object> setPageEnumValue(List<Object> values, PagerInfo pagerInfo) {
        ArrayList<Object> valueList = new ArrayList();
        if (values == null || values.size() == 0 || pagerInfo == null) {
            return values;
        }
        int total = values.size();
        int currentPage = pagerInfo.getOffset();
        int pageSize = pagerInfo.getLimit();
        if (total < pageSize) {
            return values;
        }
        int start = currentPage * pageSize;
        int end = start + pageSize;
        if (start > total) {
            start = total;
            end = total;
        }
        if (end > total) {
            end = total;
        }
        valueList = values.subList(start, end);
        return valueList;
    }

    @Override
    public CardInputInfo cardInputInit(CardInputInit cardInputInit) {
        CardInputInfo cardInputInfo = new CardInputInfo();
        JtableContext context = cardInputInit.getContext();
        String regionKey = cardInputInit.getRegionKey();
        if (!cardInputInit.isOnlyRowData()) {
            RegionData region = this.jtableParamService.getRegion(regionKey);
            cardInputInfo.setCanDeleteRow(region.isCanDeleteRow());
            cardInputInfo.setCanInsertRow(region.isCanInsertRow());
            List<LinkData> links = this.jtableParamService.getLinks(region.getKey());
            RecordCardData recordCardData = region.getCardRecord();
            RecordCard cardRecord = recordCardData.getRecordCard();
            if (null == cardRecord) {
                links.sort((o1, o2) -> o1.getCol() - o2.getCol());
                cardRecord = new RecordCard();
                cardRecord.setColumu("1");
                ArrayList<TitleAndKey> linkTitleKey = new ArrayList<TitleAndKey>();
                for (LinkData linkData : links) {
                    TitleAndKey titleAndKey = new TitleAndKey();
                    titleAndKey.setTitle(linkData.getZbtitle());
                    titleAndKey.setId(linkData.getKey());
                    linkTitleKey.add(titleAndKey);
                }
                cardRecord.setLinkTitleKey(linkTitleKey);
            }
            cardInputInfo.setCardRecord(cardRecord);
            DimensionValueSet dimensionValueSet = this.jtableContextService.getDimensionValueSet(context);
            HashSet<String> entityKeys = new HashSet<String>();
            HashSet notSuppots = new HashSet();
            DataValueFormatterBuilder formatterBuilder = this.formatFactory.createFormatterBuilder();
            DataValueFormatter formatter = formatterBuilder.build();
            for (LinkData link : links) {
                EnumLinkData enumLink;
                if (StringUtils.isNotEmpty((String)link.getZbid()) && StringUtils.isNotEmpty((String)link.getDefaultValue())) {
                    AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), context, dimensionValueSet);
                    if (expressionEvaluat != null) {
                        String fieldValue = formatter.format(link.getKey(), expressionEvaluat.getAsObject());
                        link.setDefaultValue(fieldValue.toString());
                    } else {
                        link.setDefaultValue(link.getDefaultValue());
                    }
                }
                LinkSimpleData linkSimpleData = new LinkSimpleData(link);
                cardInputInfo.getLinks().add(linkSimpleData);
                if (!(link instanceof EnumLinkData) || (enumLink = (EnumLinkData)link).getDisplayMode() != EnumDisplayMode.DISPLAY_MODE_IN_CELL || entityKeys.contains(enumLink.getEntityKey())) continue;
                entityKeys.add(enumLink.getEntityKey());
            }
            List<String> calcDataLinks = this.jtableParamService.getCalcDataLinks(context);
            cardInputInfo.setCalcDataLinks(calcDataLinks);
            if (null != cardRecord) {
                ArrayList linkTitleKey = cardRecord.getLinkTitleKey();
                Iterator iterators = linkTitleKey.iterator();
                while (iterators.hasNext()) {
                    TitleAndKey titleAndKey = (TitleAndKey)iterators.next();
                    if (notSuppots.contains(titleAndKey.getId())) {
                        iterators.remove();
                        continue;
                    }
                    if (null == calcDataLinks || !calcDataLinks.contains(titleAndKey.getId())) continue;
                    iterators.remove();
                }
            }
            for (String entityKey : entityKeys) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(entityKey);
                entityQueryInfo.setAllChildren(false);
                EntityReturnInfo queryEntityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
                Map<String, EntityReturnInfo> entityDataMap = cardInputInfo.getEntityDataMap();
                entityDataMap.put(entityKey, queryEntityData);
            }
        }
        String rowId = cardInputInit.getRowId();
        CardRowData cardRowData = cardInputInfo.getCardRowData();
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(context);
        regionQueryInfo.setRegionKey(regionKey);
        regionQueryInfo.setDesensitized(true);
        PagerInfo pagerInfo = cardInputInit.getPagerInfo();
        if (pagerInfo != null) {
            regionQueryInfo.setPagerInfo(cardInputInit.getPagerInfo());
        }
        RegionDataSet regionDataSet = this.queryRegionDatas(regionQueryInfo);
        cardRowData.setTotalCount(regionDataSet.getData().size());
        cardInputInfo.setFileDataMap(regionDataSet.getFileDataMap());
        cardInputInfo.setImgDataMap(regionDataSet.getImgDataMap());
        List<String> list = regionDataSet.getCells().get(regionKey);
        int idFieldIndex = list.indexOf("ID");
        int floatOrderFieldIndex = list.indexOf("FLOATORDER");
        cardRowData.setCells(list);
        if (StringUtils.isEmpty((String)rowId)) {
            return cardInputInfo;
        }
        if (idFieldIndex >= 0) {
            List<List<Object>> data = regionDataSet.getData();
            int offset = cardInputInit.getOffset();
            block4: for (int i = 0; i < data.size(); ++i) {
                List<Object> rowData = data.get(i);
                if (!rowId.equals(rowData.get(idFieldIndex))) continue;
                int offsetRowIndex = i + offset;
                if (offsetRowIndex >= data.size() || offsetRowIndex < 0) break;
                List<List<Object>> rel = regionDataSet.getRel();
                boolean detailRow = false;
                while (!detailRow) {
                    Object type;
                    List<Object> relType;
                    if (rel.size() > offsetRowIndex && (relType = rel.get(offsetRowIndex)).size() > 0 && null != (type = relType.get(4)) && RegionGradeDataLoader.groupData == (Integer)type) {
                        if (offset == 0) {
                            offset = 1;
                        }
                        if ((offsetRowIndex += offset) < data.size() && offsetRowIndex >= 0) continue;
                        break block4;
                    }
                    detailRow = true;
                }
                cardRowData.setRow(offsetRowIndex + 1);
                cardRowData.setData(data.get(offsetRowIndex));
                cardRowData.setRowId(data.get(offsetRowIndex).get(idFieldIndex));
                if (offsetRowIndex + 1 < data.size()) {
                    List<Object> nextRowData = data.get(offsetRowIndex + 1);
                    if (floatOrderFieldIndex < 0) break;
                    cardRowData.setOrder(nextRowData.get(floatOrderFieldIndex));
                    break;
                }
                if (pagerInfo == null) break;
                PagerInfo nextPageInfo = new PagerInfo();
                nextPageInfo.setLimit(pagerInfo.getLimit());
                nextPageInfo.setOffset(pagerInfo.getOffset() + 1);
                nextPageInfo.setTotal(pagerInfo.getTotal());
                regionQueryInfo.setPagerInfo(nextPageInfo);
                regionQueryInfo.setQueryPreData(false);
                RegionDataSet nextPageRegionDataSet = this.queryRegionDatas(regionQueryInfo);
                if (nextPageRegionDataSet.getData().size() <= 0) break;
                List<List<Object>> nextData = nextPageRegionDataSet.getData();
                List<Object> nextRowData = nextData.get(0);
                if (floatOrderFieldIndex < 0) break;
                cardRowData.setOrder(nextRowData.get(floatOrderFieldIndex));
                break;
            }
        }
        if (!cardInputInit.isOnlyRowData()) {
            Map<String, EntityReturnInfo> entityMap = regionDataSet.getEntityDataMap();
            Map<String, EntityReturnInfo> entityDataMap = cardInputInfo.getEntityDataMap();
            for (Map.Entry<String, EntityReturnInfo> entity : entityMap.entrySet()) {
                if (entityDataMap.containsKey(entity.getKey())) continue;
                entityDataMap.put(entity.getKey(), entity.getValue());
            }
        }
        return cardInputInfo;
    }

    @Override
    public MultiPeriodDataSet queryMultiPeriodData(JtableContext jtableContext) {
        int formPeriodType;
        MultiPeriodDataSet multiPeriodDataSet = new MultiPeriodDataSet();
        Map<String, MultiPeriodRegionDataSet> resultMap = multiPeriodDataSet.getRegionMap();
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        Object periodCode = dimensionValueSet.getValue("DATATIME");
        if (periodCode == null || StringUtils.isEmpty((String)periodCode.toString())) {
            multiPeriodDataSet.setNoPrevPeriod(false);
            multiPeriodDataSet.setNoPrevYear(false);
            return multiPeriodDataSet;
        }
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode.toString());
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
        int periodType = periodWrapper.getType();
        if (periodType != (formPeriodType = formSchemeDefine.getPeriodType().type())) {
            multiPeriodDataSet.setNoPrevPeriod(true);
            multiPeriodDataSet.setNoPrevYear(true);
            return multiPeriodDataSet;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formSchemeDefine.getDateTime());
        String prevPeriod = this.getPrevPeriod(periodCode.toString(), periodProvider);
        String prevYear = this.getPrevYear(periodCode.toString(), periodProvider);
        multiPeriodDataSet.setNoPrevPeriod(StringUtils.isEmpty((String)prevPeriod));
        multiPeriodDataSet.setNoPrevPeriod(StringUtils.isEmpty((String)prevYear));
        if (StringUtils.isEmpty((String)prevPeriod) && StringUtils.isEmpty((String)prevYear)) {
            return multiPeriodDataSet;
        }
        List<RegionData> regionDatas = this.jtableParamService.getRegions(jtableContext.getFormKey());
        for (RegionData regionData : regionDatas) {
            if (regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
            RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionData.getKey());
            MultiPeriodRegionDataSet regionDataSet = this.getMultiPeriodRegionDataSet(jtableContext, regionRelation, prevPeriod, prevYear);
            resultMap.put(regionData.getKey(), regionDataSet);
        }
        return multiPeriodDataSet;
    }

    private String getPrevPeriod(String periodCode, IPeriodProvider periodProvider) {
        String priorPeriod = periodProvider.priorPeriod(periodCode);
        return priorPeriod;
    }

    private String getPrevYear(String periodCode, IPeriodProvider periodProvider) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        boolean prevYearState = periodProvider.priorYear(periodWrapper);
        if (!prevYearState) {
            return null;
        }
        return periodWrapper.toString();
    }

    public MultiPeriodRegionDataSet getMultiPeriodRegionDataSet(JtableContext jtableContext, RegionRelation relation, String prevPeriod, String prevYear) {
        MultiPeriodRegionDataSet multiPeriodRegionDataSet = new MultiPeriodRegionDataSet();
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
        String fromPeriod = formSchemeDefine.getFromPeriod();
        List<MetaData> metaDataList = relation.getMetaData(null).stream().filter(this::filterMeta).collect(Collectors.toList());
        List<String> cellKeys = metaDataList.stream().map(MetaData::getLinkKey).collect(Collectors.toList());
        multiPeriodRegionDataSet.setCells(cellKeys);
        if (CollectionUtils.isEmpty(cellKeys)) {
            multiPeriodRegionDataSet.setPrevPeriodData(Collections.emptyList());
            multiPeriodRegionDataSet.setPrevPeriodData(Collections.emptyList());
            return multiPeriodRegionDataSet;
        }
        if (StringUtils.isEmpty((String)fromPeriod) || StringUtils.isNotEmpty((String)prevYear) && prevYear.compareTo(fromPeriod) >= 0) {
            List<Object> prevData = this.getPreData(jtableContext, relation, prevPeriod, metaDataList);
            List<Object> prevYearData = prevPeriod.equals(prevYear) ? prevData : this.getPreData(jtableContext, relation, prevYear, metaDataList);
            multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            multiPeriodRegionDataSet.setPrevYearData(prevYearData);
        } else {
            List<Object> prevData;
            if (prevPeriod.compareTo(fromPeriod) >= 0) {
                prevData = this.getPreData(jtableContext, relation, prevPeriod, metaDataList);
                multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            } else {
                prevData = this.getPreDataByTask(jtableContext, relation, prevPeriod, metaDataList);
                multiPeriodRegionDataSet.setPrevPeriodData(prevData);
            }
            List<Object> prevYearData = this.getPreDataByTask(jtableContext, relation, prevYear, metaDataList);
            multiPeriodRegionDataSet.setPrevYearData(prevYearData);
        }
        multiPeriodRegionDataSet.setCells(cellKeys);
        return multiPeriodRegionDataSet;
    }

    private List<Object> getPreDataByTask(JtableContext jtableContext, RegionRelation relation, String prevPeriod, List<MetaData> currMetaData) {
        FormDefine relatedForm;
        List links = this.runtimeViewController.queryLinksByCurrentFormScheme(jtableContext.getFormSchemeKey());
        if (CollectionUtils.isEmpty(links)) {
            return new ArrayList<Object>();
        }
        TaskLinkDefine taskLinkDefine = this.getTaskLinkDefine(links, jtableContext, prevPeriod);
        if (taskLinkDefine == null) {
            return new ArrayList<Object>();
        }
        TaskDefine relatedTaskDefine = null;
        String relatedFormSchemeKey = taskLinkDefine.getRelatedFormSchemeKey();
        if (StringUtils.isNotEmpty((String)relatedFormSchemeKey)) {
            FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(relatedFormSchemeKey);
            if (formScheme != null) {
                String taskKey = formScheme.getTaskKey();
                relatedTaskDefine = this.runtimeViewController.queryTaskDefine(taskKey);
            }
        } else {
            String relatedTaskKey = taskLinkDefine.getRelatedTaskKey();
            if (StringUtils.isNotEmpty((String)relatedTaskKey)) {
                relatedTaskDefine = this.runtimeViewController.queryTaskDefine(relatedTaskKey);
            }
        }
        if (relatedTaskDefine == null) {
            return new ArrayList<Object>();
        }
        FormDefine formDefine = this.runtimeViewController.queryFormById(jtableContext.getFormKey());
        try {
            relatedForm = this.runtimeViewController.queryFormByCodeInScheme(taskLinkDefine.getRelatedFormSchemeKey(), formDefine.getFormCode());
        }
        catch (Exception e) {
            return new ArrayList<Object>();
        }
        if (relatedForm == null) {
            return new ArrayList<Object>();
        }
        List<LinkData> nowLinkDataS = this.jtableParamService.getLinks(relation.getRegionDefine().getKey());
        HashSet<String> zbTitle = new HashSet<String>();
        for (LinkData nowLinkData : nowLinkDataS) {
            zbTitle.add(nowLinkData.getZbtitle());
        }
        List<RegionData> relatedRegionDataS = this.jtableParamService.getRegions(relatedForm.getKey());
        RegionData relatedRegion = new RegionData();
        for (RegionData relatedRegionData : relatedRegionDataS) {
            if (relatedRegionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) continue;
            List<LinkData> linkDataS = this.jtableParamService.getLinks(relatedRegionData.getKey());
            for (LinkData linkData : linkDataS) {
                if (!zbTitle.contains(linkData.getZbtitle())) continue;
                relatedRegion = relatedRegionData;
            }
        }
        if (StringUtils.isEmpty((String)relatedRegion.getKey())) {
            return new ArrayList<Object>();
        }
        List<EntityViewData> relateEntityViewList = this.jtableParamService.getEntityList(taskLinkDefine.getRelatedFormSchemeKey());
        List<EntityViewData> entityViewList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        EntityViewData mastEntityViewData = new EntityViewData();
        EntityViewData mastRelateEntityViewData = new EntityViewData();
        for (EntityViewData entityViewData : entityViewList) {
            if (!entityViewData.isMasterEntity()) continue;
            mastEntityViewData = entityViewData;
        }
        for (EntityViewData entityViewData : relateEntityViewList) {
            if (!entityViewData.isMasterEntity()) continue;
            mastRelateEntityViewData = entityViewData;
        }
        Object o = dimensionValueSet.getValue(mastEntityViewData.getDimensionName());
        List<Object> unitKeys = new ArrayList<Object>();
        if (o instanceof List) {
            if (((List)o).size() != 1) {
                return new ArrayList<Object>();
            }
            unitKeys = (List)o;
        } else {
            unitKeys.add(o);
        }
        JtableContext relatedContext = new JtableContext(jtableContext);
        relatedContext.setFormSchemeKey(taskLinkDefine.getRelatedFormSchemeKey());
        relatedContext.setTaskKey(relatedTaskDefine.getKey());
        relatedContext.setFormKey(relatedForm.getKey());
        ReportDataLinkFinder reportDataLinkFinder = new ReportDataLinkFinder(this.runtimeViewController, taskLinkDefine.getCurrentFormSchemeKey());
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jtableContext);
        Map relatedUnitKeyMap = reportDataLinkFinder.findRelatedUnitKeyMap(executorContext, taskLinkDefine.getLinkAlias(), null, unitKeys);
        List relatedUnitValue = (List)relatedUnitKeyMap.get(o);
        Object relatedValue = null;
        if (relatedUnitValue == null || relatedUnitValue.size() != 1) {
            return new ArrayList<Object>();
        }
        relatedValue = relatedUnitValue.get(0);
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        Map<String, DimensionValue> dimensionValueMapJtable = jtableContext.getDimensionSet();
        if (relatedValue != null) {
            Set<String> mapKeyList = dimensionValueMapJtable.keySet();
            for (String mapStr : mapKeyList) {
                DimensionValue dimensionValueNew = new DimensionValue();
                DimensionValue dimensionValue = dimensionValueMapJtable.get(mapStr);
                if (dimensionValue.getName().equals(mastEntityViewData.getDimensionName())) {
                    dimensionValueNew.setValue(relatedValue.toString());
                    dimensionValueNew.setName(mastRelateEntityViewData.getDimensionName());
                    dimensionValueMap.put(mastRelateEntityViewData.getDimensionName(), dimensionValueNew);
                    continue;
                }
                dimensionValueMap.put(mapStr, dimensionValue);
            }
        }
        relatedContext.setDimensionSet(dimensionValueMap);
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(relatedRegion.getKey());
        List<MetaData> reMetaData = regionRelation.getMetaData(null).stream().filter(this::filterMeta).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(reMetaData)) {
            return Collections.emptyList();
        }
        List<Object> preData = this.getPreData(relatedContext, regionRelation, prevPeriod, reMetaData);
        HashMap<String, Integer> metaMap = new HashMap<String, Integer>();
        for (int i = 0; i < reMetaData.size(); ++i) {
            MetaData metaData1 = reMetaData.get(i);
            if (metaData1.getDataField() == null) continue;
            metaMap.put(metaData1.getDataField().getTitle(), i);
        }
        ArrayList<Object> periodRegionDataNew = new ArrayList<Object>();
        for (MetaData currMetaDatum : currMetaData) {
            Integer index;
            DataField dataField = currMetaDatum.getDataField();
            Object data = null;
            if (dataField != null && (index = (Integer)metaMap.get(dataField.getTitle())) != null && preData.size() > index) {
                data = preData.get(index);
            }
            periodRegionDataNew.add(data);
        }
        return periodRegionDataNew;
    }

    private TaskLinkDefine getTaskLinkDefine(List<TaskLinkDefine> links, JtableContext jtableContext, String prevPeriod) {
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
        for (TaskLinkDefine linkDefine : links) {
            FormSchemeDefine linkFormSchemeDefine;
            if (!StringUtils.isNotEmpty((String)linkDefine.getRelatedFormSchemeKey()) || (linkFormSchemeDefine = this.runtimeViewController.getFormScheme(linkDefine.getRelatedFormSchemeKey())) == null || formSchemeDefine.getPeriodType() != linkFormSchemeDefine.getPeriodType()) continue;
            String fromPeriod = linkFormSchemeDefine.getFromPeriod();
            String toPeriod = linkFormSchemeDefine.getToPeriod();
            if (!StringUtils.isEmpty((String)fromPeriod) && prevPeriod.compareTo(fromPeriod) < 0 || !StringUtils.isEmpty((String)toPeriod) && prevPeriod.compareTo(toPeriod) > 0) continue;
            return linkDefine;
        }
        return null;
    }

    private List<Object> getPreData(JtableContext jtableContext, RegionRelation relation, String prevPeriod, List<MetaData> reMetaData) {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        dimensionValueSet.setValue("DATATIME", (Object)prevPeriod);
        JtableContext currentContext = new JtableContext(jtableContext);
        currentContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet(dimensionValueSet));
        RegionQueryInfo queryInfo = new RegionQueryInfo();
        queryInfo.setContext(currentContext);
        queryInfo.setRegionKey(relation.getRegionDefine().getKey());
        RegionDataSet regionDataSet = this.queryFixRegionDatas(queryInfo, relation);
        List<List<Object>> data = regionDataSet.getData();
        if (!CollectionUtils.isEmpty(data)) {
            List<Object> currentDatas = data.get(0);
            List<String> currentCells = regionDataSet.getCells().get(relation.getRegionDefine().getKey());
            ArrayList<Object> resultValue = new ArrayList<Object>(reMetaData.size());
            if (currentCells != null) {
                HashMap<String, Integer> cellMap = this.getCellMap(currentCells);
                for (MetaData cellKey : reMetaData) {
                    Integer cellIndex = cellMap.get(cellKey.getLinkKey());
                    if (cellIndex == null) {
                        resultValue.add("");
                        continue;
                    }
                    resultValue.add(currentDatas.get(cellIndex));
                }
            }
            return resultValue;
        }
        return new ArrayList<Object>();
    }

    public boolean filterMeta(MetaData metaData) {
        DataField dataField = metaData.getDataField();
        if (dataField == null) {
            return false;
        }
        DataFieldType dataFieldType = dataField.getDataFieldType();
        return dataFieldType == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL;
    }

    private HashMap<String, Integer> getCellMap(List<String> cellKeys) {
        HashMap<String, Integer> cellMap = new HashMap<String, Integer>();
        for (int index = 0; index < cellKeys.size(); ++index) {
            cellMap.put(cellKeys.get(index), index);
        }
        return cellMap;
    }

    @Override
    public CellValueInfo querySingleCellValue(SingleCellValueQueryInfo singleCellValueQueryInfo) {
        IRegionDataSet iRegionDataSet;
        CellValueInfo cellValueInfo = new CellValueInfo();
        JtableContext context = singleCellValueQueryInfo.getContext();
        String regionKey = singleCellValueQueryInfo.getRegionKey();
        RegionData region = this.jtableParamService.getRegion(regionKey);
        if (region == null) {
            cellValueInfo.setMessage("\u67e5\u8be2\u5931\u8d25\uff1a\u672a\u67e5\u5230\u533a\u57df\uff01");
            return cellValueInfo;
        }
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        List metaDatas = regionRelation.getMetaData();
        if (metaDatas == null || metaDatas.size() == 0) {
            cellValueInfo.setMessage("\u67e5\u8be2\u5931\u8d25\uff1a\u533a\u57df\u4e0d\u5b58\u5728\u94fe\u63a5\uff01");
            return cellValueInfo;
        }
        String linkKey = singleCellValueQueryInfo.getLinkKey();
        MetaData metaData = regionRelation.getMetaDataByLink(linkKey);
        if (metaData == null) {
            cellValueInfo.setMessage("\u67e5\u8be2\u5931\u8d25\uff1a\u533a\u57df\u4e0d\u5b58\u5728\u6b64\u94fe\u63a5\uff01");
            return cellValueInfo;
        }
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        DataField dataField = metaData.getDataField();
        boolean isMaskLink = StringUtils.isNotEmpty((String)dataField.getDataMaskCode());
        String rowId = singleCellValueQueryInfo.getRowId();
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(context, regionKey, regionRelation);
        queryInfoBuilder.setDesensitized(false);
        queryInfoBuilder.select(linkKey);
        if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            if (StringUtils.isEmpty((String)rowId)) {
                cellValueInfo.setMessage("\u67e5\u8be2\u5931\u8d25\uff1a\u884c\u7ef4\u5ea6\u4e0d\u4e0d\u80fd\u4e3a\u7a7a\uff01");
                return cellValueInfo;
            }
            List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(regionKey, context);
            List<FieldData> bizKeyOrderFields = new ArrayList<FieldData>();
            if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
                bizKeyOrderFields = bizKeyOrderFieldList.get(0);
            }
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
            DataCrudUtil.setBizKeyValueForDimension(builder, rowId, bizKeyOrderFields);
            RegionGradeInfo regionGradeInfo = new RegionGradeInfo();
            regionGradeInfo.setGrade(false);
            iRegionDataSet = this.dataQueryService.dataLocate(queryInfoBuilder.build(), regionGradeInfo, builder.getCombination());
        } else {
            iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        }
        List rowDatas = iRegionDataSet.getRowData();
        if (iRegionDataSet == null || iRegionDataSet.getTotalCount() == 0 || iRegionDataSet.getRowData() == null || iRegionDataSet.getRowData().size() == 0) {
            cellValueInfo.setMessage("\u67e5\u8be2\u5931\u8d25\uff1a\u672a\u67e5\u5230\u6570\u636e\uff01");
            return cellValueInfo;
        }
        IRowData rowData = (IRowData)rowDatas.get(0);
        IDataValue dataValue = rowData.getDataValueByLink(linkKey);
        String data = dataValue.getAsString();
        DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter(context);
        String formatData = formatter.format(dataValue);
        cellValueInfo.setData(data);
        cellValueInfo.setFormatData(formatData);
        cellValueInfo.setMessage("success");
        if (isMaskLink) {
            this.jLoggerUtils.logSensitiveCellLog(context, regionDefine, dataField);
        }
        return cellValueInfo;
    }
}

