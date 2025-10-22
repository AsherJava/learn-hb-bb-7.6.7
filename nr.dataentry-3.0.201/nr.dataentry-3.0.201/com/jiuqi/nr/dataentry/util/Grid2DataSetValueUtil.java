/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.jtable.params.base.DecimalLinkData
 *  com.jiuqi.nr.jtable.params.base.EnumLinkData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.output.FormulaDataLinkNodeInfo
 *  com.jiuqi.nr.jtable.util.FileUtil
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl
 */
package com.jiuqi.nr.dataentry.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.FormulaDataLinkNodeInfo;
import com.jiuqi.nr.jtable.util.FileUtil;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Grid2DataSetValueUtil {
    private static final Logger logger = LoggerFactory.getLogger(Grid2DataSetValueUtil.class);
    private static Pattern exclamationPattern = Pattern.compile("!");
    private static ThreadLocal<Map<JtableContext, Map<String, String>>> threadLocal = new ThreadLocal();
    private static FileUtil fileDataFormater = new FileUtil();

    public static void converterFieldTypeToGridCellData(int fieldType, String dataReturnFormat, GridCellData cell, LinkData dataLink, Object data, Grid2Data gridData, JtableContext jtableContext, Map<String, String> enumPosMap) {
        switch (fieldType) {
            case 1: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, jtableContext);
                break;
            }
            case 2: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                break;
            }
            case 3: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            case 4: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            case 5: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            case 6: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            case 7: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                break;
            }
            case 8: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, jtableContext);
                break;
            }
            case 17: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            case 19: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                Grid2DataSetValueUtil.setDataFormat(dataReturnFormat, cell, dataLink, fieldType, null);
                break;
            }
            default: {
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
            }
        }
        Grid2DataSetValueUtil.setGridCellDataValue(dataLink, data, cell, jtableContext, gridData, enumPosMap);
    }

    private static void setDataFormat(String dataReturnFormat, GridCellData cell, LinkData dataLink, int fieldType, JtableContext jtableContext) {
        if (fieldType == FieldType.FIELD_TYPE_FLOAT.getValue() || fieldType == FieldType.FIELD_TYPE_INTEGER.getValue() || fieldType == FieldType.FIELD_TYPE_DECIMAL.getValue()) {
            if (StringUtils.isNotEmpty((String)dataReturnFormat)) {
                IDataDefinitionRuntimeController iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
                RuntimeViewController runTimeViewController = (RuntimeViewController)BeanUtil.getBean(RuntimeViewController.class);
                DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
                DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefine(dataLink.getKey());
                boolean haeMeasure = true;
                if (jtableContext != null) {
                    FormDefine formDefine = null;
                    try {
                        formDefine = runTimeViewController.queryFormById(jtableContext.getFormKey());
                        if (dataLink instanceof DecimalLinkData && (((DecimalLinkData)dataLink).getMeasureUnit() != null && ((DecimalLinkData)dataLink).getMeasureUnit().indexOf("NotDimession") >= 0 || ((DecimalLinkData)dataLink).getMeasureUnit() == null && (formDefine.getMeasureUnit() == null || formDefine.getMeasureUnit().indexOf("NotDimession") >= 0))) {
                            haeMeasure = false;
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
                if (dataLinkDefine.getFormatProperties() != null && !StringUtils.isEmpty((String)dataLinkDefine.getFormatProperties().getPattern())) {
                    String pattern = dataLinkDefine.getFormatProperties().getPattern();
                    NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)dataLinkDefine.getFormatProperties());
                    int decimal = -1;
                    if (jtableContext != null && jtableContext.getDecimal() != null && !jtableContext.getDecimal().equals("") && haeMeasure) {
                        decimal = Integer.valueOf(jtableContext.getDecimal());
                    }
                    String patternFromated = Grid2DataSetValueUtil.formatPattern(pattern, numberFormatParser, decimal);
                    cell.setFormatter(patternFromated);
                } else {
                    int thousandDiv = Integer.parseInt(dataReturnFormat.substring(0, 1));
                    int floatCount = Integer.parseInt(dataReturnFormat.substring(8, 10));
                    if (jtableContext != null && jtableContext.getDecimal() != null && !jtableContext.getDecimal().equals("") && haeMeasure) {
                        floatCount = Integer.valueOf(jtableContext.getDecimal());
                    }
                    int baifenbi = Integer.parseInt(dataReturnFormat.substring(5, 6));
                    StringBuffer str = new StringBuffer("");
                    if (thousandDiv > 0) {
                        str.append("#,##0");
                    } else {
                        str = new StringBuffer("##0");
                    }
                    if (floatCount > 0) {
                        if (baifenbi > 0) {
                            floatCount -= 2;
                        }
                        if (floatCount > 0) {
                            str.append('.');
                        }
                        for (int i = 0; i < floatCount; ++i) {
                            str.append('0');
                        }
                    }
                    if (baifenbi > 0) {
                        str.append("%");
                    }
                    cell.setFormatter(str.toString());
                }
            }
        } else if (fieldType == FieldType.FIELD_TYPE_DATE.getValue()) {
            if (dataLink.getStyle() != null && dataLink.getStyle().equals("yyyy-MM")) {
                cell.setFormatter("yyyy-MM");
            } else {
                cell.setFormatter("yyyy-mm-dd");
            }
        } else if (fieldType == FieldType.FIELD_TYPE_DATE_TIME.getValue()) {
            cell.setFormatter("yyyy/m/d h:mm");
        } else if (fieldType == FieldType.FIELD_TYPE_TIME.getValue()) {
            cell.setFormatter("h:mm");
        } else if (fieldType == FieldType.FIELD_TYPE_LOGIC.getValue()) {
            cell.setFormatter("\u221a/\u00d7");
        }
    }

    private static String formatPattern(String pattern, NumberFormatParser numberFormatParser, int decimal) {
        boolean isPercentage;
        String[] patterns = pattern.split("\\.");
        int index = -1;
        if (numberFormatParser.getCurrency() != null) {
            index = pattern.indexOf(numberFormatParser.getCurrency());
        }
        boolean bl = isPercentage = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
        if (patterns.length == 2) {
            String intStr = patterns[0];
            String decimalStr = patterns[1].replace("#", "0");
            String decimalStrAfterReplace = decimalStr.replace("0", "");
            int decimalNum = patterns[1].length() - decimalStrAfterReplace.length();
            if (decimal > -1) {
                decimalNum = decimal;
            }
            decimalStr = decimalStrAfterReplace;
            if (decimalNum > 0) {
                for (int i = 0; i < decimalNum; ++i) {
                    decimalStr = "0" + decimalStr;
                }
            }
            pattern = decimalStr.contains("0") ? intStr + "." + decimalStr : intStr + decimalStr;
        } else if (decimal > 0) {
            String lastChar = pattern.substring(pattern.length() - 1, pattern.length());
            if (lastChar.equals("%")) {
                StringBuilder patternInfo = new StringBuilder(pattern);
                patternInfo.insert(pattern.length() - 1, ".");
                for (int i = 0; i < decimal; ++i) {
                    patternInfo.insert(pattern.length() - 1, "0");
                }
            } else {
                pattern = pattern + ".";
                for (int i = 0; i < decimal; ++i) {
                    pattern = pattern + "0";
                }
            }
        }
        if (numberFormatParser.getNegativeStyle() != null && "1".equals(numberFormatParser.getNegativeStyle().getValue())) {
            pattern = pattern + "_);(" + pattern + ")";
        } else if (numberFormatParser.getFormatType() == 2 && index > -1) {
            StringBuilder patternInfo = new StringBuilder(pattern);
            patternInfo.insert(index + 1, "-");
            pattern = pattern + ";" + patternInfo.toString();
        }
        return pattern;
    }

    /*
     * Unable to fully structure code
     */
    private static void setGridCellDataValue(LinkData dataLink, Object data, GridCellData cell, JtableContext jtableContext, Grid2Data gridData, Map<String, String> enumPosMap) {
        strReturn = "";
        if (dataLink instanceof EnumLinkData) {
            enumLink = (EnumLinkData)dataLink;
            if (data instanceof String && StringUtils.isNotEmpty((String)((String)data))) {
                try {
                    gson = new Gson();
                    json = (JsonArray)gson.fromJson((String)data, JsonArray.class);
                    enumFieldPosMap = enumLink.getEnumFieldPosMap();
                    if (json.isEmpty()) ** GOTO lbl53
                    if (json.size() > 1) {
                        for (E one : json) {
                            oneMap = (JsonObject)one;
                            strReturn = strReturn.toString() + oneMap.get("title").getAsString() + ";";
                        }
                        if (strReturn.toString().endsWith(";")) {
                            strReturn = strReturn.toString().substring(0, strReturn.toString().length() - 1);
                        }
                        if (enumFieldPosMap == null || enumFieldPosMap.isEmpty()) ** GOTO lbl53
                        for (String enumField : enumFieldPosMap.keySet()) {
                            enShowTitle = "";
                            showPos = (String)enumFieldPosMap.get(enumField);
                            for (E one : json) {
                                oneMap = (JsonObject)one;
                                if (!oneMap.has(enumField)) continue;
                                enShowTitle = enShowTitle + oneMap.get(enumField).getAsString() + ";";
                            }
                            relationCellData = Grid2DataSetValueUtil.localRelationCellData(enumLink, showPos, cell, gridData);
                            if (null == relationCellData || !StringUtils.isEmpty((String)relationCellData.getShowText()) && !relationCellData.getShowText().equals("--")) continue;
                            if (enShowTitle.toString().endsWith(";")) {
                                enShowTitle = enShowTitle.toString().substring(0, enShowTitle.toString().length() - 1);
                            }
                            relationCellData.setShowText(enShowTitle);
                            relationCellData.setEditText(enShowTitle);
                        }
                    }
                    for (E one : json) {
                        oneMap = (JsonObject)one;
                        v0 = strReturn = oneMap.get("title").getAsString().equals("-") != false ? "" : oneMap.get("title").getAsString();
                        if (enumFieldPosMap == null || enumFieldPosMap.isEmpty()) continue;
                        for (String enumField : enumFieldPosMap.keySet()) {
                            if (!oneMap.has(enumField)) continue;
                            enShowTitle = oneMap.get(enumField).getAsString();
                            showPos = (String)enumFieldPosMap.get(enumField);
                            relationCellData = Grid2DataSetValueUtil.localRelationCellData(enumLink, showPos, cell, gridData);
                            if (null == relationCellData || !StringUtils.isEmpty((String)relationCellData.getShowText()) && !relationCellData.getShowText().equals("--") || enumPosMap.isEmpty() || !enumPosMap.values().contains(showPos)) continue;
                            relationCellData.setShowText(enShowTitle);
                            relationCellData.setEditText(enShowTitle);
                        }
                    }
                }
                catch (Exception e) {
                    Grid2DataSetValueUtil.logger.error("\u6570\u636edata\u4e0d\u662fjson\u683c\u5f0f", e);
                }
            }
        } else {
            strReturn = data;
        }
lbl53:
        // 6 sources

        if (null != strReturn && StringUtils.isEmpty((String)cell.getEditText()) && StringUtils.isEmpty((String)cell.getShowText())) {
            cell.setEditText(strReturn.toString());
        } else if (null != strReturn && !strReturn.equals("--")) {
            cell.setEditText(strReturn.toString());
        }
        if (GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean) == cell.getDataType()) {
            if ("true".equalsIgnoreCase(cell.getEditText())) {
                cell.setShowText("\u221a");
            } else {
                cell.setShowText("\u00d7");
            }
        } else if (GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) == cell.getDataType()) {
            editText = cell.getEditText();
            if (!StringUtils.isEmpty((String)editText)) {
                fileInfos = FileUtil.getFileInfos((JtableContext)jtableContext, (String)editText);
                if (null != fileInfos && fileInfos.size() > 0) {
                    fileInfo = (FileInfo)fileInfos.get(0);
                    downFile = FileUtil.downFile((String)fileInfo.getKey(), (String)jtableContext.getTaskKey());
                    if (null != downFile) {
                        imageDescriptorImpl = new ImageDescriptorImpl();
                        imageData = new ImageData(downFile);
                        imageDescriptorImpl.setImageData(imageData);
                        imageDescriptorImpl.setImageId(fileInfo.getName());
                        cell.setEditText(null);
                        cell.setCellData("IMG_DATA", (Object)imageDescriptorImpl);
                    }
                }
            }
        } else if (dataLink != null && dataLink.getStyle() != null && dataLink.getStyle().equals("yyyy-MM")) {
            cell.setShowText(cell.getEditText() == null || cell.getEditText().length() < 5 ? cell.getEditText() : cell.getEditText().substring(0, 7));
        } else {
            cell.setShowText(cell.getEditText());
        }
    }

    private static GridCellData localRelationCellData(EnumLinkData dataLink, String position, GridCellData cell, Grid2Data gridData) {
        String[] rows;
        int col = dataLink.getCol();
        int row = dataLink.getRow();
        int nowCol = cell.getColIndex();
        int nowRow = cell.getRowIndex();
        String[] englishs = position.split("\\d");
        String english = "";
        for (String n : englishs) {
            english = english + n;
        }
        String relationRowString = "";
        for (String r : rows = position.split("\\D")) {
            relationRowString = relationRowString + r;
        }
        int relationRow = Integer.valueOf(relationRowString);
        int relationCol = Grid2DataSetValueUtil.excelColStrToNum(english, english.length());
        int addCol = relationCol - col - (relationCol - nowCol);
        int addRow = relationRow - row - (relationRow - nowRow);
        GridCellData relationCellData = gridData.getGridCellData(relationCol += addCol, relationRow += addRow);
        return relationCellData;
    }

    public static void handleformula(GridCellData cell, FormulaData formulaData, Map<String, String> formulaMap, String sheetName, RegionData regionData, JtableContext jtableContext) {
        String[] splits;
        String formCode = regionData.getFormCode();
        String formula = formulaData.getFormula();
        if (!StringUtils.isEmpty((String)formula) && null != formulaMap && (splits = formula.split("=")).length == 2) {
            int sheetNameCount;
            int exclamationCount;
            List dataLinkNodes = formulaData.getDataLinkNodes();
            for (FormulaDataLinkNodeInfo formulaDataLinkNodeInfo : dataLinkNodes) {
                if (formulaDataLinkNodeInfo.getFormKey().equals(formulaData.getFormKey())) continue;
                return;
            }
            String sheetCode = formCode + "!";
            String sheetCell = ExcelErrorUtil.index2ColName(cell.getColIndex()) + cell.getRowIndex();
            String sheetCellFormula = splits[1].trim();
            String replaceSheetName = "'" + sheetName + "'!";
            sheetCellFormula = sheetCellFormula.replaceAll(sheetCode, replaceSheetName);
            if (null != jtableContext && (exclamationCount = Grid2DataSetValueUtil.count(sheetCellFormula, "!", exclamationPattern)) > (sheetNameCount = Grid2DataSetValueUtil.count(sheetCellFormula, replaceSheetName, null))) {
                Map<JtableContext, Map<String, String>> cache = threadLocal.get();
                if (null == cache) {
                    cache = new HashMap<JtableContext, Map<String, String>>();
                }
                JtableContext key = new JtableContext();
                key.setDimensionSet(jtableContext.getDimensionSet());
                key.setFormSchemeKey(jtableContext.getFormSchemeKey());
                key.setTaskKey(jtableContext.getTaskKey());
                key.setVariableMap(null);
                Map<String, String> tempMap = cache.get(key);
                if (null == tempMap) {
                    tempMap = new HashMap<String, String>();
                    IDataEntryParamService dataEntryParamService = (IDataEntryParamService)BeanUtil.getBean(IDataEntryParamService.class);
                    List<FormGroupData> formGroup = dataEntryParamService.getRuntimeFormList(jtableContext);
                    for (FormGroupData formGroupData : formGroup) {
                        List<FormData> tempList = DataEntryUtil.getAllForms(formGroupData);
                        for (FormData tempFormData : tempList) {
                            tempMap.put(tempFormData.getKey(), tempFormData.getCode());
                            tempMap.put(tempFormData.getCode(), DataEntryUtil.getFormTitle(tempFormData));
                        }
                    }
                    cache.put(key, tempMap);
                }
                Iterator iterator = dataLinkNodes.iterator();
                while (iterator.hasNext()) {
                    FormulaDataLinkNodeInfo formulaDataLinkNodeInfo = (FormulaDataLinkNodeInfo)iterator.next();
                    if (!formulaDataLinkNodeInfo.getFormKey().equals(formulaData.getFormKey())) continue;
                    iterator.remove();
                }
                for (FormulaDataLinkNodeInfo formulaDataLinkNodeInfo : dataLinkNodes) {
                    String formDataCode = tempMap.get(formulaDataLinkNodeInfo.getFormKey());
                    String formDataSheetName = tempMap.get(formDataCode);
                    sheetCellFormula = sheetCellFormula.replaceAll(formDataCode + "!", "'" + formDataSheetName + "'!");
                }
            }
            formulaMap.put(sheetCell, sheetCellFormula);
        }
    }

    private static int count(String srcStr, String findStr, Pattern pattern) {
        int count = 0;
        if (null == pattern) {
            pattern = Pattern.compile(findStr);
        }
        Matcher matcher = pattern.matcher(srcStr);
        while (matcher.find()) {
            ++count;
        }
        return count;
    }

    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for (int i = 0; i < length; ++i) {
            char ch = colStr.charAt(length - i - 1);
            num = ch - 65 + 1;
            num = (int)((double)num * Math.pow(26.0, i));
            result += num;
        }
        return result;
    }
}

