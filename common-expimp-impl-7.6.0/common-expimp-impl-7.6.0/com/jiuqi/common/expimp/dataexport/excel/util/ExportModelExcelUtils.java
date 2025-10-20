/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ReflectionUtils
 *  org.apache.poi.ss.usermodel.BuiltinFormats
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.common.expimp.dataexport.excel.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ReflectionUtils;
import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public final class ExportModelExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportModelExcelUtils.class);
    private static final String CACHE_INDEX_FIELDS_MAP_KEY = "index2FeildMap";

    public static <TModel> ExportExcelSheet convertModelsToExportExcelSheet(ExportContext context, Workbook workbook, Class<TModel> modelClass, List<TModel> models) {
        Class<? extends ExpImpConverter> converterClass;
        Field[] fields = ReflectionUtils.getDeclaredFieldsWith(modelClass, ExcelColumn.class);
        if (fields == null || fields.length == 0) {
            throw new BusinessRuntimeException("\u5b9e\u4f53\u65e0Excel\u5217\u4fe1\u606f\u3002");
        }
        Excel excel = modelClass.getAnnotation(Excel.class);
        String excelSheetName = "\u9875\u7b7e1";
        if (excel != null) {
            excelSheetName = excel.title();
        }
        ExpImpConverter converter = null;
        if (excel != null && (converterClass = excel.converter()) != null) {
            try {
                converter = converterClass.newInstance();
            }
            catch (Exception e) {
                LOGGER.error("\u6570\u636e\u5bfc\u51fa\u83b7\u53d6\u683c\u5f0f\u5316\u5de5\u5177\u5f02\u5e38\u3002", e);
            }
        }
        AtomicInteger maxColumnIndex = new AtomicInteger();
        AtomicInteger maxHeadRowIndex = new AtomicInteger();
        LinkedHashMap index2FieldMap = new LinkedHashMap();
        LinkedHashMap<Integer, List> index2HeadsMap = new LinkedHashMap<Integer, List>();
        Arrays.asList(fields).stream().forEach(field -> {
            ExcelColumn excelColumnProperty = field.getAnnotation(ExcelColumn.class);
            if (excelColumnProperty == null) {
                return;
            }
            int columnIndex = excelColumnProperty.index();
            if (columnIndex == -1) {
                return;
            }
            List<String> indexHeadTitles = excelColumnProperty.title() != null ? Arrays.asList(excelColumnProperty.title()) : Arrays.asList(field.getName());
            index2HeadsMap.put(columnIndex, indexHeadTitles);
            maxHeadRowIndex.set(Math.max(maxHeadRowIndex.get(), indexHeadTitles.size() - 1));
            index2FieldMap.put(columnIndex, field);
            maxColumnIndex.set(Math.max(maxColumnIndex.get(), columnIndex));
        });
        context.getVarMap().put(CACHE_INDEX_FIELDS_MAP_KEY, index2FieldMap);
        ArrayList<String[]> headRowDatas = new ArrayList<String[]>();
        if (index2HeadsMap.size() > 0) {
            int i = 0;
            while (i <= maxHeadRowIndex.get()) {
                int headRowIndex = i++;
                String[] headRowData = new String[maxColumnIndex.get() + 1];
                index2HeadsMap.forEach((columnIndex, heads) -> {
                    if (headRowIndex < heads.size()) {
                        headRowData[columnIndex.intValue()] = (String)heads.get(headRowIndex);
                    }
                });
                headRowDatas.add(headRowData);
            }
        }
        ArrayList contentRowDatas = new ArrayList();
        if (!CollectionUtils.isEmpty(models)) {
            models.stream().forEach(model -> {
                Object[] rowData = new Object[maxColumnIndex.get() + 1];
                index2FieldMap.forEach((columnIndex, field) -> {
                    rowData[columnIndex.intValue()] = ReflectionUtils.getFieldValue((Object)model, (String)field.getName());
                });
                contentRowDatas.add(rowData);
            });
        }
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(0, excelSheetName, maxHeadRowIndex.get() + 1, converter);
        exportExcelSheet.getRowDatas().addAll(headRowDatas);
        exportExcelSheet.getRowDatas().addAll(contentRowDatas);
        Map<Integer, CellType> cellTypes = ExportModelExcelUtils.buildContentCellTypes(context);
        exportExcelSheet.getContentCellTypeCache().putAll(cellTypes);
        Map<Integer, CellStyle> contentCellStyles = ExportModelExcelUtils.buildContentCellStyles(context, workbook);
        exportExcelSheet.getContentCellStyleCache().putAll(contentCellStyles);
        return exportExcelSheet;
    }

    private static Map<Integer, CellStyle> buildContentCellStyles(ExportContext context, Workbook workbook) {
        HashMap<Integer, CellStyle> contentCellStyles = new HashMap<Integer, CellStyle>();
        Map index2FieldMap = (Map)context.getVarMap().get(CACHE_INDEX_FIELDS_MAP_KEY);
        index2FieldMap.forEach((columnIndex, field) -> {
            CellStyle cellStyle = ExportModelExcelUtils.createCellStyleByExcelColumn(workbook, field);
            contentCellStyles.put((Integer)columnIndex, cellStyle);
        });
        return contentCellStyles;
    }

    private static Map<Integer, CellType> buildContentCellTypes(ExportContext context) {
        HashMap<Integer, CellType> contentCellTypes = new HashMap<Integer, CellType>();
        Map index2FieldMap = (Map)context.getVarMap().get(CACHE_INDEX_FIELDS_MAP_KEY);
        index2FieldMap.forEach((columnIndex, field) -> {
            CellType cellType = ExportModelExcelUtils.createCellTypeByExcelColumn(field);
            contentCellTypes.put((Integer)columnIndex, cellType);
        });
        return contentCellTypes;
    }

    private static CellType createCellTypeByExcelColumn(Field field) {
        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
        CellType cellType = ExportModelExcelUtils.getDefaultCellTypeByType(field.getType());
        if (excelColumn == null) {
            return cellType;
        }
        CellType annotationCellType = excelColumn.cellType();
        if (annotationCellType == null) {
            return cellType;
        }
        return annotationCellType;
    }

    private static CellStyle createCellStyleByExcelColumn(Workbook workbook, Field field) {
        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
        if (excelColumn == null) {
            return null;
        }
        HorizontalAlignment horizontalAlignment = excelColumn.horizontalAlignment();
        if (HorizontalAlignment.GENERAL.equals((Object)horizontalAlignment)) {
            horizontalAlignment = ExportModelExcelUtils.getHorizontalAlignmentByType(field.getType());
        }
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(excelColumn.fontName());
        font.setFontHeightInPoints(excelColumn.fontHeightInPoints());
        font.setItalic(excelColumn.fontItalic());
        font.setStrikeout(excelColumn.fontStrikeout());
        font.setColor(excelColumn.fontColor());
        font.setTypeOffset(excelColumn.fontTypeOffset());
        font.setUnderline(excelColumn.fontUnderline());
        font.setCharSet(excelColumn.fontCharset());
        font.setBold(excelColumn.fontBold());
        cellStyle.setFont(font);
        String formatStr = excelColumn.dataFormat();
        int builtinFormat = BuiltinFormats.getBuiltinFormat((String)formatStr);
        cellStyle.setDataFormat((short)builtinFormat);
        cellStyle.setHidden(excelColumn.hidden());
        cellStyle.setLocked(excelColumn.locked());
        cellStyle.setQuotePrefixed(excelColumn.quotePrefix());
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setWrapText(excelColumn.wrapped());
        cellStyle.setVerticalAlignment(excelColumn.verticalAlignment());
        cellStyle.setRotation(excelColumn.rotation());
        cellStyle.setIndention(excelColumn.indent());
        cellStyle.setBorderLeft(excelColumn.borderLeft());
        cellStyle.setBorderRight(excelColumn.borderRight());
        cellStyle.setBorderTop(excelColumn.borderTop());
        cellStyle.setBorderBottom(excelColumn.borderBottom());
        cellStyle.setLeftBorderColor(excelColumn.leftBorderColor());
        cellStyle.setRightBorderColor(excelColumn.rightBorderColor());
        cellStyle.setTopBorderColor(excelColumn.topBorderColor());
        cellStyle.setBottomBorderColor(excelColumn.bottomBorderColor());
        cellStyle.setFillPattern(excelColumn.fillPatternType());
        cellStyle.setFillBackgroundColor(excelColumn.fillBackgroundColor());
        cellStyle.setFillForegroundColor(excelColumn.fillForegroundColor());
        cellStyle.setShrinkToFit(excelColumn.shrinkToFit());
        return cellStyle;
    }

    private static CellType getDefaultCellTypeByType(Class<?> typeClass) {
        CellType cellType = Boolean.class.equals(typeClass) || Boolean.TYPE.equals(typeClass) ? CellType.BOOLEAN : (Byte.class.equals(typeClass) || Byte.TYPE.equals(typeClass) ? CellType.STRING : (Short.class.equals(typeClass) || Short.TYPE.equals(typeClass) ? CellType.NUMERIC : (Integer.class.equals(typeClass) || Integer.TYPE.equals(typeClass) ? CellType.NUMERIC : (Long.class.equals(typeClass) || Long.TYPE.equals(typeClass) ? CellType.NUMERIC : (Float.class.equals(typeClass) || Float.TYPE.equals(typeClass) ? CellType.NUMERIC : (Double.class.equals(typeClass) || Double.TYPE.equals(typeClass) ? CellType.NUMERIC : (BigInteger.class.equals(typeClass) ? CellType.NUMERIC : (BigDecimal.class.equals(typeClass) ? CellType.NUMERIC : (String.class.equals(typeClass) ? CellType.STRING : CellType.STRING)))))))));
        return cellType;
    }

    private static HorizontalAlignment getHorizontalAlignmentByType(Class<?> typeClass) {
        HorizontalAlignment horizontalAlignment;
        CellType cellType = ExportModelExcelUtils.getDefaultCellTypeByType(typeClass);
        switch (cellType) {
            case NUMERIC: {
                horizontalAlignment = HorizontalAlignment.RIGHT;
                break;
            }
            case BOOLEAN: {
                horizontalAlignment = HorizontalAlignment.CENTER;
                break;
            }
            default: {
                horizontalAlignment = HorizontalAlignment.LEFT;
            }
        }
        return horizontalAlignment;
    }
}

