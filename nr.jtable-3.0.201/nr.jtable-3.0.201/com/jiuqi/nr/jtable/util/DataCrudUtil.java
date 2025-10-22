/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.GradeLink
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.TypeFormatStrategy
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datacrud.util.TypeStrategyUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.GradeLink;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.MeasureViewData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FilterMethod;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DataCrudUtil {
    public static String buildFloatRowID(IRowData rowData, LinkedHashMap<String, FieldData> dimNameField, List<MetaData> fillLinks) {
        if (rowData.getGroupTreeDeep() >= 0) {
            return DataCrudUtil.buildTotalRowId(rowData);
        }
        if (rowData.isFilledRow() && !CollectionUtils.isEmpty(fillLinks)) {
            return DataCrudUtil.buildFilledRowId(rowData, fillLinks);
        }
        if (dimNameField == null || dimNameField.size() == 0) {
            return UUID.randomUUID().toString();
        }
        return DataCrudUtil.buildDimensionRowId(rowData, dimNameField);
    }

    private static String buildFilledRowId(IRowData rowData, List<MetaData> fillLinks) {
        StringBuilder floatRowID = new StringBuilder();
        floatRowID.append("FILL_ENTITY_EMPTY");
        for (MetaData filledEnumLink : fillLinks) {
            floatRowID.append("#^$");
            String value = "";
            IDataValue valueByLink = rowData.getDataValueByLink(filledEnumLink.getLinkKey());
            if (valueByLink != null) {
                if (valueByLink.getAsNull() || ObjectUtils.isEmpty(valueByLink.getAsObject())) {
                    int dataType = filledEnumLink.getDataType();
                    if (5 != dataType) {
                        value = "-";
                    } else {
                        Date date = DataTypesConvert.periodToDate((PeriodWrapper)new PeriodWrapper("9999R0001"));
                        value = AbstractData.valueOf((Object)date, (int)5).getAsString();
                    }
                } else {
                    value = valueByLink.getAsString();
                }
            }
            floatRowID.append((Object)value);
        }
        return floatRowID.toString();
    }

    public static String buildTotalRowId(IRowData rowData) {
        DimensionCombination dimension = rowData.getDimension();
        Object value = dimension.getValue("GROUP_KEY");
        if (value == null) {
            value = "";
        }
        return value + "#^$" + rowData.getGroupTreeDeep();
    }

    public static String buildDimensionRowId(IRowData rowData, LinkedHashMap<String, FieldData> dimNameField) {
        StringBuilder floatRowID = new StringBuilder();
        for (Map.Entry<String, FieldData> nameEntry : dimNameField.entrySet()) {
            DimensionCombination rowDimension = rowData.getDimension();
            Object dimensionValue = rowDimension.getValue(nameEntry.getKey());
            if (dimensionValue == null || StringUtils.isEmpty((String)dimensionValue.toString())) {
                dimensionValue = "-";
            }
            if (floatRowID.length() > 0) {
                floatRowID.append("#^$");
            }
            floatRowID.append(dimensionValue);
        }
        return floatRowID.toString();
    }

    public static List<String> getGroupingLinks(RegionRelation regionRelation, RegionQueryInfo regionQueryInfo) {
        ArrayList<String> groupingLinks = new ArrayList<String>();
        List metaDatas = regionRelation.getMetaData(null);
        for (MetaData metaData : metaDatas) {
            if (metaData.isFormulaLink()) continue;
            DataField dataField = metaData.getDataField();
            if (regionQueryInfo.getRestructureInfo().getGrade() == null || regionQueryInfo.getRestructureInfo().getGrade().getGradeCells() == null || regionQueryInfo.getRestructureInfo().getGrade().getGradeCells().isEmpty()) continue;
            for (GradeCellInfo gradeCellInfo : regionQueryInfo.getRestructureInfo().getGrade().getGradeCells()) {
                if (!gradeCellInfo.getZbid().contains(dataField.getKey())) continue;
                groupingLinks.add(metaData.getLinkKey());
            }
        }
        return groupingLinks;
    }

    public static Object buildSumTitle(GroupingRelationEvn groupingRelationEvn, JtableContext jtableContext, List<String> groupingLinks, IRowData rowData) {
        if (rowData.getGroupTreeDeep() < 0) {
            return "";
        }
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        String unitDimension = groupingRelationEvn.getUnitDimension();
        String periodDimension = groupingRelationEvn.getPeriodDimension();
        if (groupingLinks.size() > 0) {
            for (int i = groupingLinks.size() - 1; i >= 0; --i) {
                String groupLink = groupingLinks.get(i);
                IDataValue dataValue = rowData.getDataValueByLink(groupLink);
                String cellValue = dataValue.getAsString();
                if (StringUtils.isEmpty((String)cellValue)) continue;
                if (groupLink.equals(unitDimension)) {
                    EntityData entity = groupingRelationEvn.getUnitInfo(cellValue);
                    if (entity == null) continue;
                    return entity.getRowCaption();
                }
                if (groupLink.equals(periodDimension)) {
                    return groupingRelationEvn.getPeriodTitle(cellValue);
                }
                LinkData dataLink = jtableParamService.getLink(groupLink);
                if (dataLink instanceof EnumLinkData) {
                    EnumLinkData enumLink = (EnumLinkData)dataLink;
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setEntityViewKey(enumLink.getEntityKey());
                    entityQueryByKeyInfo.setEntityKey(cellValue);
                    entityQueryByKeyInfo.setContext(jtableContext);
                    EntityByKeyReturnInfo entityDataByKey = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    if (entityDataByKey.getEntity() == null) {
                        return cellValue;
                    }
                    return entityDataByKey.getEntity().getRowCaption();
                }
                return cellValue;
            }
        }
        return "\u5408\u8ba1";
    }

    public static void setDataAndFormatData(IRowData rowData, List<Object> dataList, List<Object> dataFormatList, DataFormaterCache dataFormaterCache, DataValueFormatter formatter, Map<String, LinkData> regionAllLinkMap) {
        List rowDataValues = rowData.getLinkDataValues();
        for (IDataValue dataValue : rowDataValues) {
            if (dataValue.getAsNull()) {
                dataList.add("");
                dataFormatList.add("");
                continue;
            }
            Object data = DataCrudUtil.getData(dataValue);
            dataList.add(data);
            Object formatData = DataCrudUtil.getFormatData(dataValue, formatter, dataFormaterCache, regionAllLinkMap);
            dataFormatList.add(formatData);
        }
    }

    public static void dataValueBalanceFromat(IRowData rowData, JtableContext jtableContext, FormData formData) {
        DataValueBalanceActuatorFactory dataValueBalanceActuatorFactory = (DataValueBalanceActuatorFactory)BeanUtil.getBean(DataValueBalanceActuatorFactory.class);
        IDataValueBalanceActuator dataValueBalanceActuator = dataValueBalanceActuatorFactory.getDataValueBalanceActuator();
        Map<String, String> measureMap = jtableContext.getMeasureMap();
        if (formData != null) {
            List<MeasureViewData> measures = formData.getMeasures();
            if (measureMap != null && measureMap.size() > 0 && measures != null && measures.size() > 0) {
                MeasureViewData measureViewData = measures.get(0);
                Measure measure = new Measure();
                measure.setKey(measureViewData.getKey());
                measure.setCode(measureMap.get(measureViewData.getKey()));
                dataValueBalanceActuator.setMeasure(measure);
                String decimal = jtableContext.getDecimal();
                if (StringUtils.isNotEmpty((String)decimal)) {
                    Integer numDecimalPlaces = Integer.valueOf(decimal);
                    dataValueBalanceActuator.setNumDecimalPlaces(numDecimalPlaces);
                }
                dataValueBalanceActuator.balanceValue(rowData);
            }
        }
    }

    public static void setDataAndFormatData(IRowData rowData, List<Object> dataList, List<Object> dataFormatList, DataValueFormatter formatter) {
        List rowDataValues = rowData.getLinkDataValues();
        for (IDataValue dataValue : rowDataValues) {
            if (dataValue.getAsNull()) {
                dataList.add("");
                dataFormatList.add("");
                continue;
            }
            Object data = DataCrudUtil.getData(dataValue);
            dataList.add(data);
            Object formatData = DataCrudUtil.getFormatData(dataValue, formatter);
            dataFormatList.add(formatData);
        }
    }

    public static Object getData(IDataValue dataValue) {
        if (dataValue.getAsNull()) {
            return "";
        }
        IMetaData metaData = dataValue.getMetaData();
        if (metaData.isFormulaLink()) {
            return dataValue.getAsString();
        }
        DataFieldType dataFieldType = metaData.getDataFieldType();
        switch (dataFieldType) {
            case BOOLEAN: {
                return dataValue.getAsBool();
            }
        }
        return dataValue.getAsString();
    }

    public static Object getFormatData(IDataValue dataValue, DataValueFormatter formatter, DataFormaterCache dataFormaterCache, Map<String, LinkData> regionAllLinkMap) {
        if (dataValue.getAsNull()) {
            return "";
        }
        IMetaData metaData = dataValue.getMetaData();
        if (metaData.isFormulaLink()) {
            return formatter.format(dataValue);
        }
        DataFieldType dataFieldType = metaData.getDataFieldType();
        switch (dataFieldType) {
            case BOOLEAN: {
                return dataValue.getAsBool();
            }
            case STRING: {
                if (metaData.isEnumType()) {
                    LinkData linkData = regionAllLinkMap.get(metaData.getLinkKey());
                    if (linkData != null) {
                        linkData.getFormatData(dataValue.getAbstractData(), dataFormaterCache);
                    }
                    return dataValue.getAsString();
                }
                return formatter.format(dataValue);
            }
            case FILE: {
                return dataValue.getAsString();
            }
            case PICTURE: {
                LinkData linkData = regionAllLinkMap.get(metaData.getLinkKey());
                if (linkData != null) {
                    linkData.getFormatData(dataValue.getAbstractData(), dataFormaterCache);
                }
                return dataValue.getAsString();
            }
        }
        return formatter.format(dataValue);
    }

    public static Object getFormatData(IDataValue dataValue, DataValueFormatter formatter) {
        if (dataValue.getAsNull()) {
            return "";
        }
        IMetaData metaData = dataValue.getMetaData();
        if (metaData.isFormulaLink()) {
            return formatter.format(dataValue);
        }
        DataFieldType dataFieldType = metaData.getDataFieldType();
        switch (dataFieldType) {
            case BOOLEAN: {
                return dataValue.getAsBool();
            }
            case FILE: 
            case PICTURE: {
                return dataValue.getAsString();
            }
        }
        return formatter.format(dataValue);
    }

    public static void setNonDataFieldPos(int floatOrderIndex, String rowID, List<Object> dataList, List<Object> dataFormatList) {
        Object floatOrder = dataList.get(floatOrderIndex);
        dataList.remove(floatOrderIndex);
        dataList.add(0, rowID);
        dataList.add(1, floatOrder);
        dataFormatList.remove(floatOrderIndex);
        dataFormatList.add(0, rowID);
        dataFormatList.add(1, floatOrder);
    }

    public static void setNonDataFieldPos(int floatOrderIndex, String rowID, Object sumTitle, List<Object> dataList, List<Object> dataFormatList) {
        Object floatOrder = dataList.get(floatOrderIndex);
        dataList.remove(floatOrderIndex);
        dataList.add(0, rowID);
        dataList.add(1, floatOrder);
        dataList.add(2, sumTitle);
        dataFormatList.remove(floatOrderIndex);
        dataFormatList.add(0, rowID);
        dataFormatList.add(1, floatOrder);
        dataFormatList.add(2, sumTitle);
    }

    public static DataValueFormatter getDataValueFormatter(JtableContext jtableContext) {
        DataValueFormatterBuilderFactory formatFactory = (DataValueFormatterBuilderFactory)BeanUtil.getBean(DataValueFormatterBuilderFactory.class);
        DataValueFormatterBuilder formatterBuilder = formatFactory.createFormatterBuilder();
        String decimal = jtableContext.getDecimal();
        String millennialDecimal = jtableContext.getMillennialDecimal();
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        FormData formData = jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        List<MeasureViewData> measures = formData.getMeasures();
        boolean haveMesure = measures != null && !measures.isEmpty();
        String millennial = jtableContext.getMillennial();
        if (haveMesure || StringUtils.isNotEmpty((String)millennial)) {
            boolean useSysNumber = false;
            TypeStrategyUtil strategyUtil = (TypeStrategyUtil)BeanUtil.getBean(TypeStrategyUtil.class);
            SysNumberTypeStrategy sysNumberTypeStrategy = strategyUtil.initSysNumberTypeStrategy();
            if (haveMesure && StringUtils.isNotEmpty((String)decimal)) {
                sysNumberTypeStrategy.setNumDecimalPlaces(Integer.valueOf(Integer.parseInt(decimal)));
                useSysNumber = true;
            }
            if (StringUtils.isNotEmpty((String)millennialDecimal)) {
                sysNumberTypeStrategy.setGlobalNumDecimalPlaces(Integer.valueOf(Integer.parseInt(millennialDecimal)));
                useSysNumber = true;
            }
            if (StringUtils.isNotEmpty((String)millennial) && !millennial.equals("0")) {
                sysNumberTypeStrategy.setThousands(Boolean.valueOf(millennial.equals("1")));
                useSysNumber = true;
            }
            if (useSysNumber) {
                formatterBuilder.registerFormatStrategy(DataFieldType.INTEGER.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
                formatterBuilder.registerFormatStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeFormatStrategy)sysNumberTypeStrategy);
            }
        }
        DataValueFormatter formatter = formatterBuilder.build();
        return formatter;
    }

    public static QueryInfoBuilder getBaseQueryInfoBuilder(JtableContext jtableContext, String regionKey, RegionRelation regionRelation) {
        String formulaSchemeKey;
        Map<String, Object> variableMap;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        RegionData regionData = jtableParamService.getRegion(regionKey);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        RegionSettingUtil.rebuildMasterKeyByRegion(regionData, jtableContext, dimensionValueSet, regionRelation);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionKey, (DimensionCombination)dimensionCombinationBuilder.getCombination());
        FormData formData = jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        Map<String, String> measureMap = jtableContext.getMeasureMap();
        List<MeasureViewData> measures = formData.getMeasures();
        if (measureMap != null && measureMap.size() > 0 && measures != null && measures.size() > 0) {
            MeasureViewData measureViewData = measures.get(0);
            Measure measure = new Measure();
            measure.setKey(measureViewData.getKey());
            measure.setCode(measureMap.get(measureViewData.getKey()));
            queryInfoBuilder.setMeasure(measure);
        }
        if ((variableMap = jtableContext.getVariableMap()) != null && variableMap.size() > 0) {
            queryInfoBuilder.setVariable(variableMap);
        }
        if (StringUtils.isNotEmpty((String)(formulaSchemeKey = jtableContext.getFormulaSchemeKey()))) {
            queryInfoBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        if (StringUtils.isNotEmpty((String)regionData.getFilterCondition())) {
            queryInfoBuilder.whereRegionFilter();
        }
        return queryInfoBuilder;
    }

    public static RegionQueryInfo initRegionQueryInfo(RegionRelation regionRelation, RegionQueryInfo regionQueryInfo) {
        int limit;
        PagerInfo pagerInfo;
        IJtableParamService jtableParamService;
        RegionData region;
        if (regionQueryInfo == null) {
            regionQueryInfo = new RegionQueryInfo();
        }
        if (!(region = (jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class)).getRegion(regionQueryInfo.getRegionKey())).getCells().isEmpty() && regionQueryInfo.getFilterInfo().getCellQuerys().isEmpty()) {
            List<CellQueryInfo> cellQueryInfos = region.getCells();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                String fieldKey = cellQueryInfo.getCellKey();
                MetaData metaData = regionRelation.getMetaDataByFieldKey(fieldKey);
                if (metaData == null) continue;
                cellQueryInfo.setCellKey(metaData.getLinkKey());
            }
            regionQueryInfo.getFilterInfo().setCellQuerys(region.getCells());
        }
        JtableContext jtableContext = new JtableContext(regionQueryInfo.getContext());
        regionQueryInfo.setContext(jtableContext);
        List<String> filterFormulaList = regionQueryInfo.getFilterInfo().getFilterFormula();
        if ((filterFormulaList == null || filterFormulaList.size() == 0) && region.getTabs().size() > 0) {
            ArrayList<String> filterFormulas = new ArrayList<String>();
            filterFormulas.add(region.getTabs().get(0).getFilter());
            regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulas);
        }
        if (regionQueryInfo.getRestructureInfo().getGrade() == null) {
            regionQueryInfo.getRestructureInfo().setGrade(region.getGrade());
        }
        if (regionQueryInfo.getPagerInfo() == null && region.getPageSize() > 0) {
            pagerInfo = new PagerInfo();
            pagerInfo.setOffset(0);
            pagerInfo.setLimit(region.getPageSize());
            regionQueryInfo.setPagerInfo(pagerInfo);
        } else if (regionQueryInfo.getPagerInfo() != null && region.getPageSize() > 0 && (limit = (pagerInfo = regionQueryInfo.getPagerInfo()).getLimit()) == 0) {
            pagerInfo.setLimit(region.getPageSize());
        }
        return regionQueryInfo;
    }

    public static void buildRegionFilter(QueryInfoBuilder queryInfoBuilder, RegionFilterInfo filterInfo) {
        List<String> regionFilterFormulas = filterInfo.getFilterFormula();
        if (regionFilterFormulas != null && regionFilterFormulas.size() > 0) {
            for (String regionFilterFormula : regionFilterFormulas) {
                if (!StringUtils.isNotEmpty((String)regionFilterFormula)) continue;
                FormulaFilter formulaFilter = new FormulaFilter(regionFilterFormula);
                queryInfoBuilder.where((RowFilter)formulaFilter);
            }
        }
    }

    public static void buildPaging(QueryInfoBuilder queryInfoBuilder, PagerInfo pagerInfo) {
        if (pagerInfo != null) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageIndex(pagerInfo.getOffset());
            pageInfo.setRowsPerPage(pagerInfo.getLimit());
            queryInfoBuilder.setPage(pageInfo);
        }
    }

    public static void buildOrderColumns(RegionRelation regionRelation, QueryInfoBuilder queryInfoBuilder, RegionFilterInfo filterInfo) {
        List<CellQueryInfo> cellQuerys = filterInfo.getCellQuerys();
        for (CellQueryInfo cellQuery : cellQuerys) {
            String sortMethod;
            String linkKey = cellQuery.getCellKey();
            MetaData metaData = regionRelation.getMetaDataByLink(linkKey);
            if (metaData == null || !StringUtils.isNotEmpty((String)(sortMethod = cellQuery.getSort()))) continue;
            LinkSort linkSort = new LinkSort();
            linkSort.setLinkKey(linkKey);
            if ("desc".equals(sortMethod)) {
                linkSort.setMode(SortMode.DESC);
            } else if ("asc".equals(sortMethod)) {
                linkSort.setMode(SortMode.ASC);
            }
            queryInfoBuilder.orderBy(linkSort);
        }
    }

    public static void buildFilterColumns(RegionRelation regionRelation, QueryInfoBuilder queryInfoBuilder, JtableContext context, RegionFilterInfo filterInfo) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        List<CellQueryInfo> cellQuerys = filterInfo.getCellQuerys();
        for (CellQueryInfo cellQuery : cellQuerys) {
            String linkKey = cellQuery.getCellKey();
            if (!StringUtils.isNotEmpty((String)linkKey)) continue;
            FilterMethod filterMethod = new FilterMethod();
            MetaData metaData = regionRelation.getMetaDataByLink(linkKey);
            if (metaData == null) continue;
            DataField dataField = metaData.getDataField();
            FieldData fieldData = jtableParamService.getField(dataField.getKey());
            fieldData.setDataLinkKey(linkKey);
            filterMethod.filterMethod(cellQuery, queryInfoBuilder, context, fieldData);
        }
    }

    public static boolean existTopTenFilter(RegionFilterInfo filterInfo) {
        boolean existTopTen = false;
        if (filterInfo != null && filterInfo.getCellQuerys() != null) {
            List<CellQueryInfo> cellQuerys = filterInfo.getCellQuerys();
            for (CellQueryInfo cellQueryInfo : cellQuerys) {
                if (!StringUtils.isNotEmpty((String)cellQueryInfo.getShortcuts()) || !cellQueryInfo.getShortcuts().contains(FilterMethod.topTen)) continue;
                existTopTen = true;
                break;
            }
        }
        return existTopTen;
    }

    public static List<String> getSelectLinks(RegionRelation regionRelation, RegionFilterInfo filterInfo) {
        List<String> fieldKeys;
        ArrayList<String> linkKeys = new ArrayList<String>();
        if (filterInfo != null && (fieldKeys = filterInfo.getFieldKeys()) != null && fieldKeys.size() > 0) {
            for (String fieldKey : fieldKeys) {
                MetaData metaData = regionRelation.getMetaDataByFieldKey(fieldKey);
                if (metaData == null) continue;
                linkKeys.add(metaData.getLinkKey());
            }
        }
        return linkKeys;
    }

    public static com.jiuqi.nr.jtable.params.input.RegionGradeInfo getRegionGradeInfo(RegionGradeInfo grade) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        com.jiuqi.nr.jtable.params.input.RegionGradeInfo regionGradeInfo = new com.jiuqi.nr.jtable.params.input.RegionGradeInfo();
        RegionGradeInfo crudRegionGradeInfo = new RegionGradeInfo();
        regionGradeInfo.setDetail(grade.isQueryDetails());
        regionGradeInfo.setFold(grade.isCollapseTotal());
        regionGradeInfo.setLevels((ArrayList)grade.getGradeLevels());
        regionGradeInfo.setHidenRow(grade.isHideSingleDetail());
        regionGradeInfo.setSum(crudRegionGradeInfo.isQuerySummary());
        regionGradeInfo.setLocate(1);
        List gradeLinks = grade.getGradeLinks();
        ArrayList<GradeCellInfo> gradeCells = new ArrayList<GradeCellInfo>();
        for (GradeLink gradeLink : gradeLinks) {
            GradeCellInfo gradeCell = new GradeCellInfo();
            String fieldKey = gradeLink.getFieldKey();
            if (StringUtils.isEmpty((String)fieldKey) && StringUtils.isNotEmpty((String)gradeLink.getLinkKey())) {
                LinkData link = jtableParamService.getLink(gradeLink.getLinkKey());
                if (link != null) {
                    String zbid = link.getZbid();
                    gradeCell.setZbid(zbid);
                }
            } else {
                gradeCell.setZbid(gradeLink.getFieldKey());
            }
            gradeCell.setTrim(gradeLink.isHideEnd0());
            gradeCell.setLevels((ArrayList)gradeLink.getGradeSetting());
            gradeCells.add(gradeCell);
        }
        regionGradeInfo.setGradeCells(gradeCells);
        return regionGradeInfo;
    }

    public static RegionGradeInfo getCrudGradeInfo(com.jiuqi.nr.jtable.params.input.RegionGradeInfo grade) {
        RegionGradeInfo crudRegionGradeInfo = new RegionGradeInfo();
        crudRegionGradeInfo.setCollapseTotal(grade.isFold());
        crudRegionGradeInfo.setGradeLevels(grade.getLevels());
        crudRegionGradeInfo.setHideSingleDetail(grade.isHidenRow());
        crudRegionGradeInfo.setQueryDetails(grade.isDetail());
        crudRegionGradeInfo.setQuerySummary(grade.isSum());
        List<GradeCellInfo> gradeCells = grade.getGradeCells();
        if (gradeCells != null && gradeCells.size() > 0) {
            ArrayList<GradeLink> gradeLinks = new ArrayList<GradeLink>();
            for (GradeCellInfo gradeCell : gradeCells) {
                GradeLink gradeLink = new GradeLink();
                gradeLink.setFieldKey(gradeCell.getZbid());
                gradeLink.setGradeSetting(gradeCell.getLevels());
                gradeLink.setHideEnd0(gradeCell.isTrim());
                gradeLinks.add(gradeLink);
            }
            crudRegionGradeInfo.setGradeLinks(gradeLinks);
        }
        return crudRegionGradeInfo;
    }

    public static DataFieldGatherType getDataFieldGatherType(int gatherType) {
        switch (gatherType) {
            case 0: {
                return DataFieldGatherType.NONE;
            }
            case 1: {
                return DataFieldGatherType.SUM;
            }
            case 2: {
                return DataFieldGatherType.COUNT;
            }
            case 3: {
                return DataFieldGatherType.AVERAGE;
            }
            case 4: {
                return DataFieldGatherType.MIN;
            }
            case 5: {
                return DataFieldGatherType.MAX;
            }
            case 7: {
                return DataFieldGatherType.DISTINCT_COUNT;
            }
        }
        return null;
    }

    public static Map<String, LinkData> getRegionAllLinkMap(List<MetaData> metaDatas) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        LinkedHashMap<String, LinkData> linkDataMap = new LinkedHashMap<String, LinkData>();
        for (IMetaData iMetaData : metaDatas) {
            LinkData link;
            String linkKey = iMetaData.getLinkKey();
            if (!StringUtils.isNotEmpty((String)linkKey) || (link = jtableParamService.getLink(linkKey)) == null) continue;
            linkDataMap.put(linkKey, link);
        }
        return linkDataMap;
    }

    public static void setBizKeyValueForDimension(DimensionCombinationBuilder dimensionCombinationBuilder, String rowID, List<FieldData> bizKeyOrderFields) {
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        String[] bizKeyValues = rowID.split("\\#\\^\\$");
        int i = 0;
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = jtableDataEngineService.getDimensionName(bizKeyOrderField);
            if ("RECORDKEY".equals(dimensionName)) {
                dimensionCombinationBuilder.setValue("RECORDKEY", (Object)bizKeyValues[i]);
            } else if (bizKeyOrderField.getFieldType() == FieldType.FIELD_TYPE_DATE.getValue()) {
                dimensionCombinationBuilder.setValue(dimensionName, (Object)bizKeyValues[i]);
            } else {
                dimensionCombinationBuilder.setValue(dimensionName, (Object)bizKeyValues[i]);
            }
            ++i;
        }
    }

    public static void setBizKeyValueForDimension(DimensionCombinationBuilder dimensionCombinationBuilder, DimensionValueSet dimensionValueSet, String rowID, List<FieldData> bizKeyOrderFields) {
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        String[] bizKeyValues = rowID.split("\\#\\^\\$");
        int i = 0;
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = jtableDataEngineService.getDimensionName(bizKeyOrderField);
            if (dimensionValueSet.hasValue(dimensionName)) {
                ++i;
                continue;
            }
            if ("RECORDKEY".equals(dimensionName)) {
                dimensionCombinationBuilder.setValue("RECORDKEY", (Object)bizKeyValues[i]);
            } else if (bizKeyOrderField.getFieldType() == FieldType.FIELD_TYPE_DATE.getValue()) {
                dimensionCombinationBuilder.setValue(dimensionName, (Object)bizKeyValues[i]);
            } else {
                dimensionCombinationBuilder.setValue(dimensionName, (Object)bizKeyValues[i]);
            }
            ++i;
        }
    }

    public static boolean cellDataChange(List<Object> datas) {
        Object oldValue = datas.get(0);
        Object newValue = datas.get(1);
        if (oldValue == null) {
            oldValue = "";
        }
        if (newValue == null) {
            newValue = "";
        }
        return !oldValue.toString().equals(newValue.toString());
    }

    public static List<Integer> getValidDataRowIndexs(List<MetaData> regionMetaDatas, List<String> linkKeys, RegionData regionData, JtableContext jtableContext, List<List<List<Object>>> newdatas) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        LinkedHashMap<String, LinkData> linkDataMap = new LinkedHashMap<String, LinkData>();
        LinkedHashMap<String, LinkData> linkPosMap = new LinkedHashMap<String, LinkData>();
        ArrayList<Integer> validDataRowIndexs = new ArrayList<Integer>();
        List<String> calcDataLinks = jtableParamService.getCalcDataLinks(jtableContext);
        ArrayList<String> regionCalcDataLinks = new ArrayList<String>();
        ArrayList<String> enumRelationLinks = new ArrayList<String>();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
        Map<String, Integer> linkLevelMap = regionData.getLinkLevelMap();
        List<String> fillLinks = regionData.getFillLinks();
        for (MetaData metaData : regionMetaDatas) {
            Object object;
            AbstractData expressionEvaluat;
            String linkKey = metaData.getLinkKey();
            LinkData link = jtableParamService.getLink(linkKey);
            if (link == null) continue;
            if (StringUtils.isNotEmpty((String)link.getDefaultValue()) && (expressionEvaluat = jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), jtableContext, DimensionValueSetUtil.getDimensionValueSet(jtableContext))) != null && (object = link.getFormatData(expressionEvaluat, dataFormaterCache, jtableContext)) != null) {
                link.setDefaultValue(object.toString());
            }
            linkDataMap.put(linkKey, link);
            Position position = new Position(link.getCol(), link.getRow());
            linkPosMap.put(position.toString(), link);
            if (!calcDataLinks.contains(linkKey)) continue;
            regionCalcDataLinks.add(linkKey);
        }
        for (String key : linkDataMap.keySet()) {
            EnumLinkData enumLink;
            LinkData linkData = (LinkData)linkDataMap.get(key);
            if (!(linkData instanceof EnumLinkData) || (enumLink = (EnumLinkData)linkData).getEnumFieldPosMap() == null || enumLink.getEnumFieldPosMap().isEmpty()) continue;
            for (Map.Entry entry : enumLink.getEnumFieldPosMap().entrySet()) {
                String fieldPos = (String)entry.getValue();
                LinkData enumFieldPosLink = (LinkData)linkPosMap.get(fieldPos);
                if (enumFieldPosLink == null) continue;
                enumRelationLinks.add(enumFieldPosLink.getKey());
            }
        }
        List<EntityDefaultValue> regionEntityDefaultValue = regionData.getRegionEntityDefaultValue();
        for (int i = 0; i < newdatas.size(); ++i) {
            boolean isEmpty = true;
            List<List<Object>> dataRow = newdatas.get(i);
            for (int j = 0; j < dataRow.size(); ++j) {
                LinkData dataLink;
                String string = linkKeys.get(j);
                if (string.equals("ID") || string.equals("FLOATORDER") || string.equals("SUM") || (dataLink = (LinkData)linkDataMap.get(string)) == null) continue;
                Object value = dataRow.get(j).get(1);
                if (StringUtils.isNotEmpty((String)dataLink.getDefaultValue()) && dataLink.getDefaultValue().equals(value.toString()) || !CollectionUtils.isEmpty(regionEntityDefaultValue) && RegionSettingUtil.checkRegionSettingContainDefaultVal(regionData, dataLink) || regionCalcDataLinks.contains(dataLink.getKey()) || enumRelationLinks.contains(dataLink.getKey()) || fillLinks != null && fillLinks.contains(dataLink.getKey())) continue;
                if (dataLink instanceof EnumLinkData) {
                    EnumLinkData enumLinkData;
                    EnumLink enumLink;
                    boolean isLevelLink;
                    boolean bl = isLevelLink = linkLevelMap != null && linkLevelMap.containsKey(dataLink.getKey());
                    if (isLevelLink && (enumLink = (enumLinkData = (EnumLinkData)dataLink).getEnumLink()) != null && enumLink.getNextLinks().size() > 0) {
                        String nextLink = enumLink.getNextLinks().get(0);
                        if (fillLinks != null && fillLinks.contains(nextLink)) continue;
                    }
                }
                if (value == null || !StringUtils.isNotEmpty((String)value.toString())) continue;
                isEmpty = false;
            }
            if (!isEmpty) continue;
            validDataRowIndexs.add(i);
        }
        return validDataRowIndexs;
    }
}

