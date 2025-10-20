/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.ReflectionUtils
 */
package com.jiuqi.common.expimp.dataimport.excel.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.ReflectionUtils;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class ImportModelExcelUtils {
    private static final String CACHE_INDEX_FIELDS_MAP_KEY = "index2FeildMap";
    private static final String CACHE_MAXINDEX_KEY = "maxIndex";

    public static <TModel> List<TModel> convertImportSheetDatasToModels(ImportContext context, List<ImportExcelSheet.ImportExcelSheetRowData> excelDatas, Class<TModel> modelClass) {
        return ImportModelExcelUtils.convertImportExcelSheetDatasToModels(context, excelDatas.stream().map(ImportExcelSheet.ImportExcelSheetRowData::getRowData).collect(Collectors.toList()), modelClass);
    }

    public static <TModel> List<TModel> convertImportExcelSheetDatasToModels(ImportContext context, List<Object[]> excelDatas, Class<TModel> modelClass) {
        Field[] fields = ReflectionUtils.getDeclaredFieldsWith(modelClass, ExcelColumn.class);
        if (fields == null || fields.length == 0) {
            throw new BusinessRuntimeException("\u5b9e\u4f53\u65e0Excel\u5217\u4fe1\u606f\u3002");
        }
        AtomicInteger maxColumnIndex = new AtomicInteger();
        AtomicInteger maxHeadRowIndex = new AtomicInteger();
        LinkedHashMap index2FieldMap = new LinkedHashMap();
        LinkedHashMap index2HeadsMap = new LinkedHashMap();
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
        context.getVarMap().put(CACHE_MAXINDEX_KEY, maxColumnIndex.get());
        ArrayList<TModel> rowDatas = new ArrayList<TModel>();
        for (int rowIndex = 0; rowIndex < excelDatas.size(); ++rowIndex) {
            Object[] rowData;
            TModel tModel;
            if (rowIndex <= maxHeadRowIndex.get() || (tModel = ImportModelExcelUtils.convertRowDataToModel(context, rowData = excelDatas.get(rowIndex), modelClass)) == null) continue;
            rowDatas.add(tModel);
        }
        return rowDatas;
    }

    private static <TModel> TModel convertRowDataToModel(ImportContext context, Object[] rowData, Class<TModel> modelClass) {
        Object tModel;
        Map index2FieldMap = (Map)context.getVarMap().get(CACHE_INDEX_FIELDS_MAP_KEY);
        try {
            tModel = modelClass.newInstance();
            index2FieldMap.forEach((index, field) -> {
                Object fieldValue = index >= rowData.length ? null : rowData[index];
                String fieldName = field.getName();
                Class<?> typeClass = field.getType();
                fieldValue = ConverterUtils.cast((Object)fieldValue, typeClass);
                ReflectionUtils.setFieldValue((Object)tModel, (String)fieldName, (Object)fieldValue);
            });
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return tModel;
    }
}

