/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.datacrud.spi.filter.InValuesFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.common.language.LanguageCommon
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.consts.BatchExportConsts;
import com.jiuqi.nr.data.excel.exception.ExcelException;
import com.jiuqi.nr.data.excel.export.PathTreeBuilder;
import com.jiuqi.nr.data.excel.extend.ISheetNameProvider;
import com.jiuqi.nr.data.excel.extend.param.SheetNameParam;
import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.FilterSort;
import com.jiuqi.nr.data.excel.obj.FormulaNode;
import com.jiuqi.nr.data.excel.param.CellQueryInfo;
import com.jiuqi.nr.data.excel.param.Directory;
import com.jiuqi.nr.data.excel.param.FilterCondition;
import com.jiuqi.nr.data.excel.service.impl.EntityDataHelper;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;
import com.jiuqi.nr.data.excel.service.internal.IFormFinder;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datacrud.spi.filter.InValuesFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.common.language.LanguageCommon;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class ExportUtil {
    private static final String CONDITION_AND = " and ";
    private static final String CONDITION_NOT_EQ = "noteq";
    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);
    private static final PeriodEngineService periodEngineService = (PeriodEngineService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(PeriodEngineService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRunTimeViewController.class);
    private static final INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(INvwaSystemOptionService.class);

    private ExportUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getExpTempPath() {
        return System.getProperty("java.io.tmpdir") + File.separator + "JQ" + File.separator + "EXP" + new Date().getTime() + File.separator;
    }

    public static DimensionValueSet mergeDim(List<Map<String, DimensionValue>> dimensionList) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (!CollectionUtils.isEmpty(dimensionList)) {
            HashMap<String, HashSet<String>> set = new HashMap<String, HashSet<String>>();
            for (Map<String, DimensionValue> map : dimensionList) {
                for (Map.Entry<String, DimensionValue> entry : map.entrySet()) {
                    HashSet<String> dimValues;
                    if (set.containsKey(entry.getKey())) {
                        dimValues = (HashSet<String>)set.get(entry.getKey());
                        dimValues.addAll(Arrays.asList(entry.getValue().getValue().split(";")));
                        continue;
                    }
                    dimValues = new HashSet<String>(Arrays.asList(entry.getValue().getValue().split(";")));
                    set.put(entry.getKey(), dimValues);
                }
            }
            for (Map.Entry entry : set.entrySet()) {
                dimensionValueSet.setValue((String)entry.getKey(), new ArrayList((Collection)entry.getValue()));
            }
        }
        return dimensionValueSet;
    }

    public static List<FormDefine> getExpForms(Collection<String> fromKeys, String formSchemeKey) throws Exception {
        List<FormDefine> sortedForms = ExportUtil.getSortedForms(formSchemeKey);
        return sortedForms.stream().filter(o -> FormType.FORM_TYPE_SURVEY != o.getFormType() && fromKeys.contains(o.getKey())).collect(Collectors.toList());
    }

    public static List<FormDefine> getSortedForms(String formSchemeKey) throws Exception {
        ArrayList<FormDefine> result = new ArrayList<FormDefine>();
        List allFormGroupsInFormScheme = runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        HashSet<String> formKeys = new HashSet<String>();
        for (FormGroupDefine formGroupDefine : allFormGroupsInFormScheme) {
            List allFormsInGroup = runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            for (FormDefine formDefine : allFormsInGroup) {
                if (!formKeys.add(formDefine.getKey())) continue;
                result.add(formDefine);
            }
        }
        return result;
    }

    public static Date getEntityQueryVersionDate(String periodEntityId, String period) {
        IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        Date versionDate = null;
        try {
            versionDate = periodProvider.getPeriodDateRegion(period)[1];
        }
        catch (ParseException e) {
            logger.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
        }
        return versionDate;
    }

    public static DimensionCollection toDimensionCollection(DimensionCombination dimensionCombination) {
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        for (FixedDimensionValue fixedDimensionValue : dimensionCombination) {
            dimensionCollectionBuilder.setEntityValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), new Object[]{fixedDimensionValue.getValue()});
        }
        return dimensionCollectionBuilder.getCollection();
    }

    public static String getDw(String dwShow, IDimensionDataAdapter dwDataAdapter, String dwShowPattern, String split) {
        int titleIndex;
        String dwTitle;
        List<DimensionData> byTitle;
        int codeIndex;
        String dwCode;
        DimensionData dimensionData;
        if (StringUtils.isEmpty((String)split)) {
            split = systemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        }
        if (StringUtils.isEmpty((String)dwShowPattern)) {
            dwShowPattern = systemOptionService.get("nr-data-entry-export", "EXCEL_NAME");
        }
        List<String> dwInfos = Arrays.asList(dwShow.split(split));
        List<String> setting = Arrays.asList(dwShowPattern.split("[\\[\\]\",]"));
        if (setting.contains("1") && (dimensionData = dwDataAdapter.getDimensionData(dwCode = dwInfos.get(codeIndex = setting.indexOf("1")))) != null) {
            return dimensionData.getKeyData();
        }
        if (setting.contains("0") && (byTitle = dwDataAdapter.getByTitle(dwTitle = dwInfos.get(titleIndex = setting.indexOf("0")))) != null && byTitle.size() == 1) {
            return byTitle.get(0).getKeyData();
        }
        throw new ExcelException(String.format("failed to parse dwInfo-%s-%s-%s", dwShow, dwShowPattern, split));
    }

    public static String getForm(String formShow, IFormFinder formFinder, String formShowPattern, String split) {
        int serNumIndex;
        String formSerNum;
        List<FormDefine> bySerialNum;
        int titleIndex;
        String formTitle;
        List<FormDefine> byTitle;
        int codeIndex;
        String formCode;
        FormDefine byCode;
        if (StringUtils.isEmpty((String)split)) {
            split = systemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        }
        if (StringUtils.isEmpty((String)formShowPattern)) {
            formShowPattern = systemOptionService.get("nr-data-entry-export", "SHEET_NAME");
        }
        List<String> formInfos = Arrays.asList(formShow.split(split));
        List<String> setting = Arrays.asList(formShowPattern.split("[\\[\\]\",]"));
        if (setting.contains("1") && (byCode = formFinder.findByCode(formCode = formInfos.get(codeIndex = setting.indexOf("1")))) != null) {
            return byCode.getKey();
        }
        if (setting.contains("0") && (byTitle = formFinder.findByTitle(formTitle = formInfos.get(titleIndex = setting.indexOf("0")))) != null && byTitle.size() == 1) {
            return byTitle.get(0).getKey();
        }
        if (setting.contains("2") && (bySerialNum = formFinder.findBySerialNum(formSerNum = formInfos.get(serNumIndex = setting.indexOf("2")))) != null && bySerialNum.size() == 1) {
            return bySerialNum.get(0).getKey();
        }
        throw new ExcelException(String.format("failed to parse dwInfo-%s-%s-%s", formShow, formShowPattern, split));
    }

    public static void createMapper(SXSSFWorkbook workbook, Map<String, String> map) {
        SXSSFSheet mapperSheet = workbook.createSheet("(\u9875\u540d\u6620\u5c04\u8868)");
        SXSSFRow rowTitle = mapperSheet.getRow(0);
        if (rowTitle == null) {
            rowTitle = mapperSheet.createRow(0);
        }
        block10: for (int i = 0; i < 3; ++i) {
            Cell cell = rowTitle.getCell((short)i);
            if (cell == null) {
                cell = rowTitle.createCell(i);
            }
            switch (i) {
                case 0: {
                    cell.setCellValue("\u5e8f\u53f7");
                    continue block10;
                }
                case 1: {
                    cell.setCellValue("sheet\u9875\u5e8f\u5217");
                    continue block10;
                }
                case 2: {
                    cell.setCellValue("\u5bf9\u5e94\u5168\u540d");
                }
            }
        }
        mapperSheet.setColumnWidth(0, 2500);
        mapperSheet.setColumnWidth(1, 15000);
        mapperSheet.setColumnWidth(2, 17000);
        int rowCount = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            SXSSFRow rowMapper = mapperSheet.getRow(rowCount);
            if (rowMapper == null) {
                rowMapper = mapperSheet.createRow(rowCount);
            }
            block12: for (int i = 0; i < 3; ++i) {
                Cell cell = rowMapper.getCell((short)i);
                if (cell == null) {
                    cell = rowMapper.createCell(i);
                }
                switch (i) {
                    case 0: {
                        cell.setCellValue(rowCount);
                        continue block12;
                    }
                    case 1: {
                        cell.setCellValue(entry.getKey());
                        continue block12;
                    }
                    case 2: {
                        cell.setCellValue(entry.getValue());
                    }
                }
            }
            ++rowCount;
        }
    }

    public static SheetNameHandleResult handleSheetName(String sheetName, @Nullable ISheetNameProvider sheetNameProvider, SheetNameParam sheetNameParam) {
        String provideSheetName;
        if (sheetNameProvider != null && (provideSheetName = sheetNameProvider.getSheetName(sheetNameParam)) != null) {
            logger.debug("\u539fsheet\u540d\uff1a{}\uff0c\u4f7f\u7528\u6269\u5c55\u7684sheet\u540d\uff1a{}", (Object)sheetName, (Object)provideSheetName);
            return new SheetNameHandleResult(true, provideSheetName);
        }
        if (sheetName.length() > BatchExportConsts.SHEET_LENGTH - 3) {
            sheetName = sheetName.substring(0, BatchExportConsts.SHEET_LENGTH - 3);
        }
        if (sheetName.startsWith("'")) {
            sheetName = "_" + sheetName.substring(1);
        }
        if (sheetName.endsWith("'")) {
            sheetName = sheetName.substring(0, sheetName.length() - 1) + "_";
        }
        if (sheetName.contains(":")) {
            sheetName = sheetName.replace(":", "_");
        }
        if (sheetName.contains("\\")) {
            sheetName = sheetName.replace("\\", "_");
        }
        if (sheetName.contains("/")) {
            sheetName = sheetName.replace("/", "_");
        }
        if (sheetName.contains("?")) {
            sheetName = sheetName.replace("?", "_");
        }
        if (sheetName.contains("*")) {
            sheetName = sheetName.replace("*", "_");
        }
        if (sheetName.contains("[")) {
            sheetName = sheetName.replace("[", "_");
        }
        if (sheetName.contains("]")) {
            sheetName = sheetName.replace("]", "_");
        }
        return new SheetNameHandleResult(false, sheetName);
    }

    public static String sheetNameValidate(String sheetName, @Nullable ISheetNameProvider sheetNameProvider, SheetNameParam sheetNameParam) {
        SheetNameHandleResult sheetNameHandleResult = ExportUtil.handleSheetName(sheetName, sheetNameProvider, sheetNameParam);
        return sheetNameHandleResult.getSheetName();
    }

    public static Map<String, String> sheetNameToCompanyName(Sheet sheet) {
        HashMap<String, String> resMap = new HashMap<String, String>();
        for (Row row : sheet) {
            int x = row.getRowNum();
            if (x == 0) continue;
            Cell cellCode = row.getCell(1);
            String code = cellCode.getStringCellValue();
            Cell cellName = row.getCell(2);
            String name = cellName.getStringCellValue();
            resMap.put(code, name);
        }
        return resMap;
    }

    public static String getDateValue(String periodTitle, Map<String, List<IPeriodRow>> periodItemGroupByTitle) {
        if (periodItemGroupByTitle.containsKey(periodTitle = LanguageCommon.getPeriodItemTitleRe((String)periodTitle))) {
            List<IPeriodRow> periodRows = periodItemGroupByTitle.get(periodTitle);
            if (periodRows.size() == 1) {
                return periodRows.get(0).getCode();
            }
            if (periodRows.size() > 1) {
                throw new IllegalArgumentException(String.format("\u6839\u636e\u65f6\u671f\u6807\u9898\u627e\u5230\u591a\u4e2a\u5bf9\u5e94\u7684\u65f6\u671f\uff1a%s\uff0c\u8bf7\u4fee\u6539\u65f6\u671f\u914d\u7f6e\uff01", periodTitle));
            }
            throw new IllegalArgumentException(String.format("\u6839\u636e\u65f6\u671f\u6807\u9898\u672a\u627e\u5230\u5bf9\u5e94\u7684\u65f6\u671f\uff1a%s", periodTitle));
        }
        throw new IllegalArgumentException(String.format("\u6839\u636e\u65f6\u671f\u6807\u9898\u672a\u627e\u5230\u5bf9\u5e94\u7684\u65f6\u671f\uff1a%s", periodTitle));
    }

    public static Map<String, List<IPeriodRow>> getPeriodItemGroupByTitle(IPeriodProvider periodProvider) {
        List periodItems = periodProvider.getPeriodItems();
        return periodItems.stream().collect(Collectors.groupingBy(IPeriodRow::getTitle));
    }

    public static void simplifyDirs(List<Directory> directories, String separator) {
        List paths = directories.stream().map(Directory::getDirectory).collect(Collectors.toList());
        PathTreeBuilder pathTreeBuilder = new PathTreeBuilder();
        for (String path : paths) {
            pathTreeBuilder.addPath(path, BatchExportConsts.SEPARATOR);
        }
        Map<String, String> pathMap = pathTreeBuilder.getTree().getSimplePathMap(BatchExportConsts.SEPARATOR, separator);
        for (Directory directory : directories) {
            String simplePath = pathMap.get(directory.getDirectory());
            if (!StringUtils.isNotEmpty((String)simplePath)) continue;
            directory.setDirectory(simplePath);
        }
    }

    public static void createExcelDirSheet(SXSSFWorkbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets <= 0) {
            return;
        }
        SXSSFSheet dirSheet = workbook.createSheet("\u76ee\u5f55");
        SXSSFRow row = dirSheet.createRow(0);
        SXSSFCell headCell = row.createCell(0);
        headCell.setCellValue("\u76ee\u5f55");
        CellStyle headStyle = workbook.createCellStyle();
        Font headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short)24);
        headFont.setFontName("\u5b8b\u4f53");
        headFont.setBold(true);
        headStyle.setFont(headFont);
        headCell.setCellStyle(headStyle);
        int index = 1;
        CellStyle dirDetailStyle = workbook.createCellStyle();
        Font dirDetailFont = workbook.createFont();
        dirDetailFont.setFontHeightInPoints((short)14);
        dirDetailFont.setFontName("\u5b8b\u4f53");
        dirDetailFont.setUnderline((byte)1);
        dirDetailFont.setColor(IndexedColors.BLUE.index);
        dirDetailStyle.setFont(dirDetailFont);
        CreationHelper creationHelper = workbook.getCreationHelper();
        for (int i = 0; i < numberOfSheets; ++i) {
            SXSSFSheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            if ("(\u9875\u540d\u6620\u5c04\u8868)".equals(sheetName) || "HIDDENSHEETNAME".equals(sheetName)) continue;
            SXSSFRow dirDetailRow = dirSheet.createRow(index++);
            SXSSFCell dirDetailCell = dirDetailRow.createCell(0);
            dirDetailCell.setCellValue(sheetName);
            dirDetailCell.setCellStyle(dirDetailStyle);
            Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
            String temp = "#'" + sheetName + "'!A1";
            hyperlink.setAddress(temp);
            dirDetailCell.setHyperlink(hyperlink);
        }
        dirSheet.trackColumnForAutoSizing(0);
        dirSheet.autoSizeColumn(0);
        workbook.setSheetOrder("\u76ee\u5f55", 0);
        workbook.setActiveSheet(0);
    }

    public static FilterSort getFilterSort(Map<String, List<CellQueryInfo>> conditions, DimensionCombination dimensionCombination, ExportCache exportCache) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRunTimeViewController.class);
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRuntimeDataSchemeService.class);
        HashMap<String, List<RowFilter>> rowFilter = null;
        HashMap linkSort = null;
        HashMap<String, PageInfo> topPageInfo = null;
        if (!CollectionUtils.isEmpty(conditions)) {
            rowFilter = new HashMap<String, List<RowFilter>>();
            linkSort = new HashMap();
            topPageInfo = new HashMap<String, PageInfo>();
            for (Map.Entry<String, List<CellQueryInfo>> e : conditions.entrySet()) {
                String regionKey = e.getKey();
                List<CellQueryInfo> cellQueryInfos = e.getValue();
                if (StringUtils.isEmpty((String)regionKey) || CollectionUtils.isEmpty(cellQueryInfos)) continue;
                ArrayList<LinkSort> linkSorts = new ArrayList<LinkSort>();
                ArrayList<RowFilter> rowFilters = new ArrayList<RowFilter>();
                PageInfo pageInfo = null;
                for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                    DataField field;
                    LinkSort sort;
                    DataLinkDefine dataLinkDefine;
                    String linkKey = cellQueryInfo.getCellKey();
                    if (StringUtils.isEmpty((String)linkKey) || (dataLinkDefine = runTimeViewController.queryDataLinkDefine(linkKey)) == null) continue;
                    if ("asc".equals(cellQueryInfo.getSort())) {
                        sort = new LinkSort();
                        sort.setLinkKey(linkKey);
                        sort.setMode(SortMode.ASC);
                        linkSorts.add(sort);
                    } else if ("desc".equals(cellQueryInfo.getSort())) {
                        sort = new LinkSort();
                        sort.setLinkKey(linkKey);
                        sort.setMode(SortMode.DESC);
                        linkSorts.add(sort);
                    }
                    if ((field = dataSchemeService.getDataField(dataLinkDefine.getLinkExpression())) == null) continue;
                    switch (field.getDataFieldType()) {
                        case STRING: {
                            rowFilters.addAll(ExportUtil.getStringRowFilter(cellQueryInfo, field, dataLinkDefine, dimensionCombination, exportCache));
                            break;
                        }
                        case DATE: {
                            rowFilters.addAll(ExportUtil.getDateRowFilter(cellQueryInfo, field, dataLinkDefine));
                            break;
                        }
                        case BIGDECIMAL: 
                        case INTEGER: 
                        case BOOLEAN: 
                        case UUID: {
                            if (cellQueryInfo.getShortcuts() != null) {
                                String[] string = cellQueryInfo.getShortcuts().split(";");
                                if (pageInfo == null && "topTen".equals(string[0])) {
                                    pageInfo = new PageInfo();
                                    pageInfo.setRowsPerPage(Integer.parseInt(string[1]));
                                    pageInfo.setPageIndex(0);
                                }
                            }
                            rowFilters.addAll(ExportUtil.getNumRowFilter(cellQueryInfo, field, dataLinkDefine));
                            break;
                        }
                    }
                }
                if (rowFilters.size() > 0) {
                    rowFilter.put(regionKey, rowFilters);
                }
                if (linkSorts.size() > 0) {
                    linkSort.put(regionKey, linkSorts);
                }
                if (pageInfo == null) continue;
                topPageInfo.put(regionKey, pageInfo);
            }
        }
        return new FilterSort(rowFilter, linkSort, topPageInfo);
    }

    private static List<RowFilter> getNumRowFilter(CellQueryInfo cellQueryInfo, DataField field, DataLinkDefine dataLinkDefine) {
        StringBuilder cellFilterBufSb = new StringBuilder();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(field.getDataTableKey());
        String tableName = dataTable.getCode();
        for (FilterCondition cond : cellQueryInfo.getOpList()) {
            String stringF = ExportUtil.stringFormat(cellQueryInfo.getAttendedMode());
            String stringFC = ExportUtil.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)stringFC)) continue;
            if (cellFilterBufSb.length() != 0 && "and".equals(stringF)) {
                cellFilterBufSb.append(CONDITION_AND);
            } else if (cellFilterBufSb.length() != 0 && "or".equals(stringF)) {
                cellFilterBufSb.append(" or ");
            }
            if ("eq".equals(stringFC)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" = ").append(" ").append(cond.getOpValue()).append(" ");
                continue;
            }
            if (CONDITION_NOT_EQ.equals(stringFC)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" != ").append(" ").append(cond.getOpValue()).append(" ");
                continue;
            }
            if ("more".equals(stringFC)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" > ").append(" ").append(cond.getOpValue()).append(" ");
                continue;
            }
            if ("less".equals(stringFC)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" < ").append(" ").append(cond.getOpValue()).append(" ");
                continue;
            }
            if ("notless".equals(stringFC)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" >= ").append(" ").append(cond.getOpValue()).append(" ");
                continue;
            }
            if (!"notmore".equals(stringFC)) continue;
            cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" <= ").append(" ").append(cond.getOpValue()).append(" ");
        }
        if (cellQueryInfo.getShortcuts() != null) {
            if ("moreThanEverage".equals(cellQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(CONDITION_AND);
                }
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" > ").append(tableName).append("[").append(field.getCode()).append(",avg] ");
            }
            if ("lessThanEverage".equals(cellQueryInfo.getShortcuts())) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(CONDITION_AND);
                }
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" < ").append(tableName).append("[").append(field.getCode()).append(",avg] ");
            }
        }
        List<String> inList = cellQueryInfo.getInList();
        ArrayList<String> filteredValues = new ArrayList<String>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    filteredValues.add("");
                    continue;
                }
                if (inValue.equals("true")) {
                    filteredValues.add(Integer.toString(1));
                    continue;
                }
                if (inValue.equals("false")) {
                    filteredValues.add(Integer.toString(0));
                    continue;
                }
                filteredValues.add(inValue);
            }
        }
        ArrayList<RowFilter> rowFilters = new ArrayList<RowFilter>();
        if (!filteredValues.isEmpty()) {
            InValuesFilter inValuesFilter = new InValuesFilter(dataLinkDefine.getKey(), filteredValues);
            rowFilters.add((RowFilter)inValuesFilter);
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            rowFilters.add((RowFilter)formulaFilter);
        }
        return rowFilters;
    }

    private static List<RowFilter> getDateRowFilter(CellQueryInfo cellQueryInfo, DataField field, DataLinkDefine dataLinkDefine) {
        StringBuilder cellFilterBufSb = new StringBuilder();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(field.getDataTableKey());
        String tableName = dataTable.getCode();
        for (FilterCondition cond : cellQueryInfo.getOpList()) {
            String stringF = ExportUtil.stringFormat(cellQueryInfo.getAttendedMode());
            String stringFC = ExportUtil.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)stringFC)) continue;
            if (cellFilterBufSb.length() != 0 && stringF.equals("and")) {
                cellFilterBufSb.append(CONDITION_AND);
            } else if (cellFilterBufSb.length() != 0 && stringF.equals("or")) {
                cellFilterBufSb.append(" or ");
            }
            if (stringFC.equals("eq")) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" = ").append("'").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (stringFC.equals(CONDITION_NOT_EQ)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" != ").append("'").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (stringFC.equals("more")) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" > ").append("'").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (stringFC.equals("less")) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" < ").append("'").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (stringFC.equals("notless")) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" >= ").append("'").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (!stringFC.equals("notmore")) continue;
            cellFilterBufSb.append(" ").append(tableName).append("[").append(field.getCode()).append("] ").append(" <= ").append("'").append(cond.getOpValue()).append("' ");
        }
        List<String> inList = cellQueryInfo.getInList();
        ArrayList<String> filteredValues = new ArrayList<String>();
        if (inList.size() < 200) {
            for (String inValue : inList) {
                if (StringUtils.isEmpty((String)inValue)) {
                    filteredValues.add(null);
                    filteredValues.add("");
                    continue;
                }
                filteredValues.add(inValue);
            }
        }
        ArrayList<RowFilter> rowFilters = new ArrayList<RowFilter>();
        if (!filteredValues.isEmpty()) {
            InValuesFilter inValuesFilter = new InValuesFilter(dataLinkDefine.getKey(), filteredValues);
            rowFilters.add((RowFilter)inValuesFilter);
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            rowFilters.add((RowFilter)formulaFilter);
        }
        return rowFilters;
    }

    private static List<RowFilter> getStringRowFilter(CellQueryInfo cellQueryInfo, DataField dataField, DataLinkDefine dataLinkDefine, DimensionCombination dimensionCombination, ExportCache exportCache) {
        ArrayList<RowFilter> rowFilters = new ArrayList<RowFilter>();
        StringBuilder cellFilterBufSb = new StringBuilder();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        String tableName = dataTable.getCode();
        boolean existLike = false;
        String searchText = null;
        for (FilterCondition cond : cellQueryInfo.getOpList()) {
            String stringF = ExportUtil.stringFormat(cellQueryInfo.getAttendedMode());
            String string = ExportUtil.stringFormat(cond.getOpCode());
            if (StringUtils.isEmpty((String)string)) continue;
            if (cellFilterBufSb.length() != 0 && "and".equals(stringF)) {
                cellFilterBufSb.append(CONDITION_AND);
            } else if (cellFilterBufSb.length() != 0 && "or".equals(stringF)) {
                cellFilterBufSb.append(" or ");
            }
            if ("eq".equals(string)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(dataField.getCode()).append("]").append(" = ").append(" '").append(cond.getOpValue()).append("' ");
                continue;
            }
            if (CONDITION_NOT_EQ.equals(string)) {
                cellFilterBufSb.append(" ").append(tableName).append("[").append(dataField.getCode()).append("]").append(" != ").append(" '").append(cond.getOpValue()).append("' ");
                continue;
            }
            if ("contain".equals(string)) {
                cellFilterBufSb.append(" Position('").append(cond.getOpValue()).append("', ").append(tableName).append("[").append(dataField.getCode()).append("])  > 0  ");
                continue;
            }
            if ("notcontain".equals(string)) {
                cellFilterBufSb.append(" Position('").append(cond.getOpValue()).append("', ").append(tableName).append("[").append(dataField.getCode()).append("])  < 1  ");
                continue;
            }
            if (!"like".equals(string)) continue;
            existLike = true;
            searchText = cond.getOpValue();
        }
        List<String> outList = cellQueryInfo.getOutList();
        List<String> inList = cellQueryInfo.getInList();
        if (StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) {
            List entityDataKeys;
            if (outList != null && !outList.isEmpty()) {
                for (String string : outList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(CONDITION_AND);
                    }
                    cellFilterBufSb.append(tableName).append("[").append(dataField.getCode()).append("]").append(" != ").append(" '").append(string).append("' ");
                }
            }
            if (existLike && StringUtils.isNotEmpty(searchText) && (entityDataKeys = ExportUtil.convertEntityDataTextToKeys(dataField.getRefDataEntityKey(), searchText, dimensionCombination, exportCache)) != null && !entityDataKeys.isEmpty()) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(CONDITION_AND);
                }
                cellFilterBufSb.append(" InList(");
                cellFilterBufSb.append(tableName).append("[").append(dataField.getCode()).append("],");
                entityDataKeys = entityDataKeys.stream().map(r -> "'" + r + "'").collect(Collectors.toList());
                cellFilterBufSb.append(String.join((CharSequence)",", entityDataKeys));
                cellFilterBufSb.append(")");
            }
            if (inList != null && !inList.isEmpty()) {
                for (String string : inList) {
                    if (cellFilterBufSb.length() != 0) {
                        cellFilterBufSb.append(" or ");
                    }
                    if (StringUtils.isEmpty((String)string)) {
                        cellFilterBufSb.append(tableName).append("[").append(dataField.getCode()).append("]").append(" = '' OR ").append(tableName).append("[").append(dataField.getCode()).append("] IS NULL");
                        continue;
                    }
                    cellFilterBufSb.append(tableName).append("[").append(dataField.getCode()).append("]").append(" = ").append(" '").append(string).append("' ");
                }
            }
        } else if (outList != null && !outList.isEmpty()) {
            for (String string : outList) {
                if (cellFilterBufSb.length() != 0) {
                    cellFilterBufSb.append(CONDITION_AND);
                }
                cellFilterBufSb.append(tableName).append("[").append(dataField.getCode()).append("]").append(" != ").append(" '").append(string).append("' ");
            }
        } else {
            ArrayList<String> filteredValues = new ArrayList<String>();
            if (inList.size() < 200) {
                for (String inValue : inList) {
                    if (StringUtils.isEmpty((String)inValue)) {
                        filteredValues.add(null);
                        filteredValues.add("");
                        continue;
                    }
                    filteredValues.add(inValue);
                }
            }
            if (!filteredValues.isEmpty()) {
                InValuesFilter inValuesFilter = new InValuesFilter(dataLinkDefine.getKey(), filteredValues);
                rowFilters.add((RowFilter)inValuesFilter);
            }
        }
        if (StringUtils.isNotEmpty((String)cellFilterBufSb.toString())) {
            FormulaFilter formulaFilter = new FormulaFilter(cellFilterBufSb.toString());
            rowFilters.add((RowFilter)formulaFilter);
        }
        return rowFilters;
    }

    private static List<String> convertEntityDataTextToKeys(String refDataEntityKey, String searchText, DimensionCombination dimensionCombination, ExportCache exportCache) {
        try {
            EntityDataHelper entityDataHelper = (EntityDataHelper)com.jiuqi.nr.definition.internal.BeanUtil.getBean(EntityDataHelper.class);
            IEntityTable entityTable = entityDataHelper.getIsoReadEntityTable(refDataEntityKey, exportCache.getCurFormScheme(), dimensionCombination);
            ArrayList<String> result = new ArrayList<String>();
            for (IEntityRow row : entityTable.getAllRows()) {
                if (!row.getEntityKeyData().contains(searchText) && !row.getTitle().contains(searchText)) continue;
                result.add(row.getEntityKeyData());
            }
            return result;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u57fa\u7840\u6570\u636e\u5f02\u5e38" + e.getMessage(), e);
            return null;
        }
    }

    private static String stringFormat(String s) {
        String sTwo = "";
        String sOne = s.toLowerCase();
        sTwo = sOne.replaceAll("[^a-zA-Z]", "");
        return sTwo;
    }

    public static List<FormulaNode> getFormulaNodes(IParsedExpression parsedExpression) {
        ArrayList<FormulaNode> result = new ArrayList<FormulaNode>();
        IExpression exp = parsedExpression.getRealExpression();
        for (IASTNode child : exp) {
            DataModelLinkColumn dataLink;
            if (!(child instanceof DynamicDataNode) || null == (dataLink = ((DynamicDataNode)child).getDataModelLink())) continue;
            FormulaNode formulaNode = new FormulaNode();
            try {
                formulaNode.setDataLinkCode(dataLink.getDataLinkCode());
                if (dataLink.getReportInfo() != null) {
                    formulaNode.setDataLinkFormKey(dataLink.getReportInfo().getReportKey());
                }
                formulaNode.setColumnModel(dataLink.getColumModel());
                result.add(formulaNode);
            }
            catch (Exception e) {
                logger.error(parsedExpression.getSource().getId() + e.getMessage(), e);
            }
        }
        return result;
    }

    @Nullable
    public static DimensionValueSet getMergeDimensionValueSet(DimensionCollection dimensionCollection) {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return null;
        }
        if (allDimension.size() == 1) {
            return allDimension.get(0);
        }
        return ExportUtil.merge(allDimension);
    }

    public static DimensionValueSet merge(List<DimensionValueSet> allDimension) {
        LinkedHashMap dimensionMap = new LinkedHashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (dimensionMap.containsKey(name)) {
                    ((Set)dimensionMap.get(name)).add(value);
                    continue;
                }
                HashSet<Object> valueSet = new HashSet<Object>();
                valueSet.add(value);
                dimensionMap.put(name, valueSet);
            }
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            String dimensionName = (String)entry.getKey();
            Set valueSet = (Set)entry.getValue();
            if (valueSet.size() == 1) {
                for (Object o : valueSet) {
                    dimensionValueSet.setValue(dimensionName, o);
                }
                continue;
            }
            dimensionValueSet.setValue(dimensionName, new ArrayList(valueSet));
        }
        return dimensionValueSet;
    }

    public static boolean formOpenMeasure(@NonNull FormDefine formDefine) {
        String[] measureStr;
        String measureUnit = formDefine.getMeasureUnit();
        if (StringUtils.isNotEmpty((String)measureUnit) && (measureStr = measureUnit.split(";")).length == 2) {
            return !measureStr[1].equalsIgnoreCase("NotDimession");
        }
        return false;
    }

    public static class SheetNameHandleResult {
        private final String sheetName;
        private final boolean provided;

        public SheetNameHandleResult(boolean provided, String sheetName) {
            this.provided = provided;
            this.sheetName = sheetName;
        }

        public boolean isProvided() {
            return this.provided;
        }

        public String getSheetName() {
            return this.sheetName;
        }
    }
}

