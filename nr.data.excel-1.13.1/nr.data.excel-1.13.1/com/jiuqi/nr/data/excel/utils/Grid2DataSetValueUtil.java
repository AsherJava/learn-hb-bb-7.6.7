/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.FormatPropertiesParser
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator
 *  com.jiuqi.nr.datacrud.common.DataTypeConvert
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.FormatPropertiesParser;
import com.jiuqi.nr.data.excel.extend.ImgDownload;
import com.jiuqi.nr.data.excel.obj.ExcelExportEnv;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.FileData;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.wb.format.CellFormat;
import com.jiuqi.nr.data.excel.wb.format.Currency;
import com.jiuqi.nr.data.excel.wb.format.GeneralFormat;
import com.jiuqi.nr.data.excel.wb.format.NegativeStyle;
import com.jiuqi.nr.data.excel.wb.format.TextFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroAccountingFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroCurrencyFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroNumberFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroPercentFormat;
import com.jiuqi.nr.data.excel.wb.format.zero_show.ZeroPermillageFormat;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class Grid2DataSetValueUtil {
    private static final ImgDownload imgDownload = (ImgDownload)BeanUtil.getBean(ImgDownload.class);
    private static final String FORMAT_STR_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static void converterFieldTypeToGridCellData(GridCellData cell, IDataValue dataValue, Grid2Data grid2Data, ExportCache exportCache, SheetInfo sheetInfo) {
        if (exportCache.isGridDataFormatted()) {
            Grid2DataSetValueUtil.gridDataFormattedSet(grid2Data, cell, dataValue, exportCache, sheetInfo);
        } else {
            DataFieldType dataType = Grid2DataSetValueUtil.getDataType(dataValue);
            IDataValueBalanceActuator balanceActuator = exportCache.getBalanceActuator(sheetInfo.getFormKey());
            if (balanceActuator != null) {
                balanceActuator.balanceValue(dataValue);
            }
            Grid2DataSetValueUtil.setDataTypeAndFormatAndValue(cell, dataValue.getMetaData(), dataType, exportCache, dataValue, sheetInfo);
            Grid2DataSetValueUtil.setEnumRelationValue(dataValue, cell, grid2Data, exportCache);
        }
    }

    private static void gridDataFormattedSet(Grid2Data grid2Data, GridCellData cell, IDataValue dataValue, ExportCache exportCache, SheetInfo sheetInfo) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Auto));
        DataFieldType dataType = Grid2DataSetValueUtil.getDataType(dataValue);
        if (dataType == DataFieldType.BOOLEAN) {
            String value = !dataValue.getAsNull() && dataValue.getAsBool() ? "\u221a" : "\u00d7";
            cell.setEditText(value);
            cell.setShowText(value);
        } else if (!dataValue.getAsNull() && dataType != DataFieldType.FILE && dataType != DataFieldType.PICTURE) {
            DataValueFormatter formatter = exportCache.getDataValueFormatter(sheetInfo.getFormKey());
            String value = formatter.format(dataValue);
            cell.setEditText(value);
            cell.setShowText(value);
            Grid2DataSetValueUtil.setEnumRelationValue(dataValue, cell, grid2Data, exportCache);
        }
    }

    private static DataFieldType getDataType(IDataValue dataValue) {
        DataFieldType dataType = null;
        if (dataValue.getMetaData().isFormulaLink()) {
            if (!dataValue.getAsNull()) {
                dataType = DataTypeConvert.dataType2DataFieldType((int)dataValue.getAbstractData().dataType);
            }
        } else {
            dataType = dataValue.getMetaData().getDataFieldType();
        }
        return dataType;
    }

    private static String getDateFormatterString(IMetaData metaData) {
        String formatPattern = Grid2DataSetValueUtil.getFormatPattern(metaData);
        if (formatPattern != null && formatPattern.equals("yyyy-MM")) {
            return "yyyy-MM";
        }
        return "yyyy-MM-dd";
    }

    private static String getFormatPattern(IMetaData metaData) {
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        DataField dataField = metaData.getDataField();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && dataField != null) {
            formatProperties = dataField.getFormatProperties();
        }
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.getPattern();
        }
        return showFormat;
    }

    private static void setEnumRelationValue(IDataValue dataValue, GridCellData cell, Grid2Data grid2Data, ExportCache exportCache) {
        Map enumPosMap;
        String[] enumCodes;
        DataField dataField = dataValue.getMetaData().getDataField();
        DataLinkDefine dataLinkDefine = dataValue.getMetaData().getDataLinkDefine();
        Object data = dataValue.getAsObject();
        if (dataField != null && StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey()) && data instanceof String && StringUtils.isNotEmpty((String)((String)data)) && (enumCodes = ((String)data).split(";")).length > 0 && !CollectionUtils.isEmpty(enumPosMap = dataLinkDefine.getEnumPosMap())) {
            for (Map.Entry e : enumPosMap.entrySet()) {
                String enumField = (String)e.getKey();
                StringBuilder enShowTitle = new StringBuilder();
                String showPos = enumPosMap.get(enumField).toString();
                for (String enumCode : enumCodes) {
                    IEntityRow entityData = exportCache.getEntityData(dataField.getRefDataEntityKey(), enumCode);
                    if (entityData == null) continue;
                    enShowTitle.append(entityData.getAsString(enumField)).append(";");
                }
                GridCellData relationCellData = Grid2DataSetValueUtil.localRelationCellData(dataLinkDefine, showPos, cell, grid2Data);
                if (null == relationCellData || !StringUtils.isEmpty((String)relationCellData.getShowText()) && !relationCellData.getShowText().equals("--")) continue;
                if (enShowTitle.toString().endsWith(";")) {
                    enShowTitle = new StringBuilder(enShowTitle.substring(0, enShowTitle.toString().length() - 1));
                }
                relationCellData.setShowText(enShowTitle.toString());
                relationCellData.setEditText(enShowTitle.toString());
            }
        }
    }

    private static GridCellData localRelationCellData(DataLinkDefine dataLink, String position, GridCellData cell, Grid2Data gridData) {
        String[] rows;
        int col = dataLink.getPosX();
        int row = dataLink.getPosY();
        int nowCol = cell.getColIndex();
        int nowRow = cell.getRowIndex();
        String[] englishs = position.split("\\d");
        StringBuilder english = new StringBuilder();
        for (String n : englishs) {
            english.append(n);
        }
        StringBuilder relationRowString = new StringBuilder();
        for (String r : rows = position.split("\\D")) {
            relationRowString.append(r);
        }
        int relationRow = Integer.parseInt(relationRowString.toString());
        int relationCol = Grid2DataSetValueUtil.excelColStrToNum(english.toString(), english.length());
        int addCol = relationCol - col - (relationCol - nowCol);
        int addRow = relationRow - row - (relationRow - nowRow);
        return gridData.getGridCellData(relationCol += addCol, relationRow += addRow);
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

    public static void converterFieldTypeToGridCellData(GridCellData gridCellData, String indexNum) {
        gridCellData.setEditText(indexNum);
        gridCellData.setShowText(indexNum);
    }

    public static GridCellData locateGridCell(Grid2Data gridData, String position) {
        String[] rows;
        String[] englishs = position.split("\\d");
        StringBuilder english = new StringBuilder();
        for (String n : englishs) {
            english.append(n);
        }
        StringBuilder relationRowString = new StringBuilder();
        for (String r : rows = position.split("\\D")) {
            relationRowString.append(r);
        }
        int row = Integer.parseInt(relationRowString.toString());
        int col = Grid2DataSetValueUtil.excelColStrToNum(english.toString(), english.length());
        return gridData.getGridCellData(col, row);
    }

    public static void setCellDataType(IMetaData metaData, GridCellData cell, ExportCache exportCache, SheetInfo sheetInfo) {
        if (exportCache.isGridDataFormatted()) {
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Auto));
        } else {
            DataFieldType dataType = metaData.getDataFieldType();
            Grid2DataSetValueUtil.setDataTypeAndFormatAndValue(cell, metaData, dataType, exportCache, null, sheetInfo);
        }
    }

    private static void setDataTypeAndFormatAndValue(GridCellData cell, IMetaData metaData, DataFieldType dataType, ExportCache exportCache, @Nullable IDataValue dataValue, SheetInfo sheetInfo) {
        if (dataType == null) {
            Grid2DataSetValueUtil.general(cell, dataValue, exportCache.getDataValueFormatter(sheetInfo.getFormKey()));
        } else {
            switch (dataType) {
                case CLOB: 
                case STRING: {
                    Grid2DataSetValueUtil.text(cell, dataValue, exportCache.getDataValueFormatter(sheetInfo.getFormKey()));
                    break;
                }
                case INTEGER: 
                case BIGDECIMAL: {
                    Grid2DataSetValueUtil.number(cell, metaData, dataType, exportCache, dataValue, sheetInfo);
                    break;
                }
                case BOOLEAN: {
                    Grid2DataSetValueUtil.bool(cell, dataValue);
                    break;
                }
                case DATE: {
                    Grid2DataSetValueUtil.date(cell, metaData, dataValue);
                    break;
                }
                case DATE_TIME: {
                    Grid2DataSetValueUtil.dateTime(cell, metaData, dataValue);
                    break;
                }
                case PICTURE: {
                    Grid2DataSetValueUtil.picture(cell, dataValue, exportCache);
                    break;
                }
                case FILE: {
                    Grid2DataSetValueUtil.file(cell, dataValue, exportCache);
                    break;
                }
                default: {
                    Grid2DataSetValueUtil.general(cell, dataValue, exportCache.getDataValueFormatter(sheetInfo.getFormKey()));
                }
            }
        }
    }

    private static void number(GridCellData cell, IMetaData metaData, DataFieldType dataType, ExportCache exportCache, IDataValue dataValue, SheetInfo sheetInfo) {
        Integer displayPlacesForce;
        String zeroShow = exportCache.getZeroShow();
        boolean useMeasure = Grid2DataSetValueUtil.useMeasure(metaData);
        Integer measureDecimal = useMeasure ? exportCache.getMeasureDecimal(sheetInfo.getFormKey()) : null;
        Integer n = displayPlacesForce = measureDecimal != null ? measureDecimal : exportCache.getExportOps().getDisplayDecimalPlaces();
        if (dataValue != null && !dataValue.getAsNull()) {
            BigDecimal asCurrency = dataValue.getAsCurrency();
            int precision = asCurrency.precision();
            if (precision > 15) {
                Grid2DataSetValueUtil.text(cell, dataValue, exportCache.getDataValueFormatter(sheetInfo.getFormKey()));
            } else {
                FormatPropertiesParser formatParser = Grid2DataSetValueUtil.getFormatParser(metaData, dataValue);
                CellFormat cellFormat = Grid2DataSetValueUtil.getCellFormat(formatParser, zeroShow, dataType, exportCache.getExportOps().getThousands(), displayPlacesForce);
                cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                cell.setFormatter(cellFormat.getFormatStr());
                if (formatParser.isThousandPer()) {
                    BigDecimal value = new BigDecimal(dataValue.getAsString()).multiply(new BigDecimal(1000));
                    String plainString = value.setScale(dataValue.getAsCurrency().scale(), RoundingMode.HALF_UP).toPlainString();
                    cell.setEditText(plainString);
                    cell.setShowText(plainString);
                } else {
                    cell.setEditText(dataValue.getAsString());
                    cell.setShowText(dataValue.getAsString());
                }
            }
        } else {
            FormatPropertiesParser formatParser = Grid2DataSetValueUtil.getFormatParser(metaData, null);
            CellFormat cellFormat = Grid2DataSetValueUtil.getCellFormat(formatParser, zeroShow, dataType, exportCache.getExportOps().getThousands(), displayPlacesForce);
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
            cell.setFormatter(cellFormat.getFormatStr());
        }
        Grid2DataSetValueUtil.cusFormat(metaData, cell, dataValue, zeroShow);
    }

    private static boolean useMeasure(IMetaData metaData) {
        IFMDMAttribute fmAttribute;
        if (metaData == null) {
            return false;
        }
        if (metaData.isFieldLink()) {
            DataField dataField = metaData.getDataField();
            if (dataField != null) {
                String measureUnit = dataField.getMeasureUnit();
                return StringUtils.isEmpty((String)measureUnit) || !measureUnit.endsWith("NotDimession");
            }
        } else if (metaData.isFMDMLink() && (fmAttribute = metaData.getFmAttribute()) != null) {
            String measureUnit = fmAttribute.getMeasureUnit();
            return StringUtils.isEmpty((String)measureUnit) || !measureUnit.endsWith("NotDimession");
        }
        return true;
    }

    private static void general(GridCellData cell, @Nullable IDataValue dataValue, DataValueFormatter formatter) {
        cell.setDataType(-1);
        cell.setFormatter(GeneralFormat.getInstance().getFormatStr());
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        String value = formatter.format(dataValue);
        cell.setEditText(value);
        cell.setShowText(value);
    }

    private static void picture(GridCellData cell, @Nullable IDataValue dataValue, ExportCache exportCache) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic));
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        String imgFieldData = Grid2DataSetValueUtil.getDataValueStr(dataValue);
        if (imgDownload == null || StringUtils.isEmpty((String)imgFieldData)) {
            return;
        }
        ExcelExportEnv excelExportEnv = exportCache.getExcelExportEnv(dataValue.getRowData().getMasterDimension());
        FileData img = imgDownload.getImg(imgFieldData, excelExportEnv);
        if (img != null) {
            ImageDescriptorImpl imageDescriptorImpl = new ImageDescriptorImpl();
            ImageData imageData = new ImageData(img.getData());
            imageDescriptorImpl.setImageData(imageData);
            imageDescriptorImpl.setImageId(img.getFileInfo().getName());
            cell.setEditText(null);
            cell.setCellData("IMG_DATA", (Object)imageDescriptorImpl);
        }
    }

    private static void file(GridCellData cell, @Nullable IDataValue dataValue, ExportCache exportCache) {
        cell.setDataType(-1);
        cell.setFormatter(GeneralFormat.getInstance().getFormatStr());
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        exportCache.getFileGroupKeys().add(dataValue.getAsString());
        cell.setEditText("");
        cell.setShowText("");
    }

    private static void text(GridCellData cell, @Nullable IDataValue dataValue, DataValueFormatter formatter) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
        cell.setFormatter(TextFormat.getInstance().getFormatStr());
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        String value = formatter.format(dataValue);
        cell.setEditText(value);
        cell.setShowText(value);
    }

    private static void bool(GridCellData cell, @Nullable IDataValue dataValue) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
        cell.setFormatter("\u221a/\u00d7");
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        boolean asBool = dataValue.getAsBool();
        String value = asBool ? "true" : "false";
        cell.setEditText(value);
        cell.setShowText(value);
    }

    private static void date(GridCellData cell, IMetaData metaData, @Nullable IDataValue dataValue) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
        cell.setFormatter(Grid2DataSetValueUtil.getDateFormatterString(metaData));
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        cell.setEditText(Grid2DataSetValueUtil.getDataValueStr(dataValue));
        cell.setShowText(Grid2DataSetValueUtil.getDataValueStr(dataValue));
    }

    private static void dateTime(GridCellData cell, IMetaData metaData, @Nullable IDataValue dataValue) {
        cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
        cell.setFormatter(FORMAT_STR_DATETIME);
        if (dataValue == null || dataValue.getAsNull()) {
            return;
        }
        cell.setEditText(Grid2DataSetValueUtil.getDataValueStr(dataValue));
        cell.setShowText(Grid2DataSetValueUtil.getDataValueStr(dataValue));
    }

    private static String getDataValueStr(@Nullable IDataValue dataValue) {
        if (dataValue == null || dataValue.getAsNull()) {
            return "";
        }
        return dataValue.getAsString();
    }

    @NonNull
    private static FormatPropertiesParser getFormatParser(@NonNull IMetaData metaData, @Nullable IDataValue dataValue) {
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null) {
            IFMDMAttribute fmAttribute;
            if (metaData.isFieldLink()) {
                DataField dataField = metaData.getDataField();
                if (dataField != null) {
                    formatProperties = dataField.getFormatProperties();
                }
            } else if (metaData.isFMDMLink() && (fmAttribute = metaData.getFmAttribute()) != null) {
                formatProperties = new FormatProperties();
                formatProperties.setPattern(fmAttribute.getShowFormat());
            }
        }
        if (formatProperties == null || StringUtils.isEmpty((String)formatProperties.getPattern())) {
            IFMDMAttribute fmAttribute;
            StringBuilder pattern = new StringBuilder("#,##0");
            int decimal = -1;
            boolean useValueDecimal = true;
            if (metaData.isFieldLink()) {
                DataField dataField = metaData.getDataField();
                if (dataField != null) {
                    Integer integer = dataField.getDecimal();
                    decimal = integer == null ? -1 : integer;
                    useValueDecimal = false;
                }
            } else if (metaData.isFMDMLink() && (fmAttribute = metaData.getFmAttribute()) != null) {
                decimal = fmAttribute.getDecimal();
                useValueDecimal = false;
            }
            if (useValueDecimal && dataValue != null && !dataValue.getAsNull()) {
                decimal = dataValue.getAsCurrency().scale();
            }
            if (decimal > 0) {
                pattern.append(".");
                for (int i = 0; i < decimal; ++i) {
                    pattern.append("0");
                }
            }
            formatProperties = new FormatProperties();
            formatProperties.setPattern(pattern.toString());
        }
        return FormatPropertiesParser.parse((FormatProperties)formatProperties);
    }

    private static CellFormat getCellFormat(@NonNull FormatPropertiesParser formatPropertiesParser, String zeroShowSetting, DataFieldType dataType, Boolean thousandsForce, Integer decimalForce) {
        int formatType = formatPropertiesParser.getFormatType();
        boolean thousands = 2 == formatType || 3 == formatType ? true : formatPropertiesParser.isThousands();
        if (thousandsForce != null) {
            thousands = thousandsForce;
        }
        int decimal = -1;
        if (DataFieldType.INTEGER == dataType) {
            decimal = 0;
        } else {
            if (decimalForce != null) {
                decimal = decimalForce;
            }
            if (decimal < 0) {
                Integer displayDigits = formatPropertiesParser.getDisplayDigits();
                decimal = displayDigits != null ? displayDigits : -1;
            }
        }
        NegativeStyle negativeStyle = Grid2DataSetValueUtil.getNegativeStyle(formatPropertiesParser.getNegativeStyle());
        Currency currency = Grid2DataSetValueUtil.getCurrency(formatPropertiesParser.getCurrency());
        if (1 == formatType) {
            return new ZeroNumberFormat(decimal, thousands, negativeStyle, zeroShowSetting, zeroShowSetting != null);
        }
        if (2 == formatType) {
            ZeroCurrencyFormat zeroCurrencyFormat = new ZeroCurrencyFormat(currency, decimal, negativeStyle, zeroShowSetting, zeroShowSetting != null);
            zeroCurrencyFormat.setThousands(thousands);
            return zeroCurrencyFormat;
        }
        if (3 == formatType) {
            ZeroAccountingFormat zeroAccountingFormat = new ZeroAccountingFormat(currency, decimal, zeroShowSetting, zeroShowSetting != null);
            zeroAccountingFormat.setThousands(thousands);
            return zeroAccountingFormat;
        }
        if (4 == formatType) {
            return new ZeroPercentFormat(decimal, zeroShowSetting, zeroShowSetting != null);
        }
        if (5 == formatType) {
            return new ZeroPermillageFormat(decimal, zeroShowSetting, zeroShowSetting != null);
        }
        return new ZeroNumberFormat(decimal, thousands, negativeStyle, zeroShowSetting, zeroShowSetting != null);
    }

    private static NegativeStyle getNegativeStyle(com.jiuqi.np.definition.internal.format.NegativeStyle negativeStyle) {
        if (com.jiuqi.np.definition.internal.format.NegativeStyle.NS_0 == negativeStyle) {
            return NegativeStyle.NS_0;
        }
        if (com.jiuqi.np.definition.internal.format.NegativeStyle.NS_1 == negativeStyle) {
            return NegativeStyle.NS_1;
        }
        return NegativeStyle.NS_0;
    }

    private static Currency getCurrency(String currencySymbol) {
        if (String.valueOf(FormatPropertiesParser.CURRENCY_1).equals(currencySymbol)) {
            return Currency.CNY;
        }
        if (String.valueOf(FormatPropertiesParser.CURRENCY_2).equals(currencySymbol)) {
            return Currency.USD;
        }
        if (String.valueOf(FormatPropertiesParser.CURRENCY_3).equals(currencySymbol)) {
            return Currency.EUR;
        }
        return Currency.NONE;
    }

    private static void cusFormat(IMetaData metaData, GridCellData cell, @Nullable IDataValue dataValue, String zeroShow) {
        FormatProperties formatProperties = Grid2DataSetValueUtil.getFormatProperties(metaData);
        if (formatProperties == null || formatProperties.getFormatType() == null || StringUtils.isEmpty((String)formatProperties.getPattern())) {
            return;
        }
        if (6 == formatProperties.getFormatType()) {
            cell.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
            String pattern = formatProperties.getPattern();
            if (zeroShow != null) {
                pattern = pattern + ";\\-" + pattern + ";" + zeroShow + ";@";
            }
            cell.setFormatter(pattern);
            if (dataValue != null && !dataValue.getAsNull()) {
                cell.setEditText(dataValue.getAsString());
                cell.setShowText(dataValue.getAsString());
            }
        }
    }

    private static FormatProperties getFormatProperties(IMetaData metaData) {
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && metaData.isFieldLink() && metaData.getDataField() != null) {
            formatProperties = metaData.getDataField().getFormatProperties();
        }
        return formatProperties;
    }
}

