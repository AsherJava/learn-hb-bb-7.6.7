/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.CurrencyData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.dataengine.data.IntData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.common.CalcItem
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.json.JSONArray
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.common.CalcItem;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.CalcDimensionLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRegionDataSetStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRegionDataSetStrategy.class);
    protected DataFormaterCache dataFormaterCache;
    protected AbstractRegionRelationEvn regionRelationEvn;
    protected AbstractRegionQueryTableStrategy regionQueryTableStrategy;
    protected RegionQueryInfo regionQueryInfo;

    public AbstractRegionDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        this.regionRelationEvn = regionRelationEvn;
        this.regionQueryTableStrategy = regionQueryTableStrategy;
        this.dataFormaterCache = dataFormaterCache;
        this.regionQueryInfo = regionQueryInfo;
    }

    protected List<Object> getRowData(IDataRow dataRow, List<Integer> fillDataIndex) {
        ArrayList<Object> rowData = new ArrayList<Object>();
        List<String> cells = this.regionQueryTableStrategy.getCells();
        Map<String, String> fieldBalanceFormulaMap = this.regionRelationEvn.getFieldBalanceFormulaMap();
        for (String cell : cells) {
            Object value = null;
            int cellIndex = this.regionRelationEvn.getCellIndex(cell);
            AbstractData fieldValue = null;
            if (cellIndex >= 0) {
                try {
                    fieldValue = dataRow.getValue(cellIndex);
                }
                catch (RuntimeException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
                }
            }
            LinkData dataLink = null;
            if (this.regionRelationEvn.getDataLinkByKey(cell) != null) {
                dataLink = this.regionRelationEvn.getDataLinkByKey(cell);
                String balanceFieldKey = AbstractRegionRelationEvn.balanceKey + dataLink.getZbid();
                int balanceIndex = this.regionRelationEvn.getCellIndex(balanceFieldKey);
                if (balanceIndex > 0) {
                    AbstractData balanceValue = null;
                    try {
                        balanceValue = dataRow.getValue(balanceIndex);
                    }
                    catch (RuntimeException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
                    }
                    if ((fieldValue instanceof CurrencyData || fieldValue instanceof FloatData || fieldValue instanceof IntData) && fieldValue.getAsCurrency() != null) {
                        if (balanceValue == null || balanceValue.isNull) {
                            logger.error("\u5e73\u8861\u516c\u5f0f:" + fieldBalanceFormulaMap.get(balanceFieldKey) + ";\u8ba1\u7b97\u503c\u4e3a\u7a7a");
                        } else if (balanceValue instanceof CurrencyData || balanceValue instanceof FloatData || balanceValue instanceof IntData) {
                            BigDecimal subtract = fieldValue.getAsCurrency().subtract(balanceValue.getAsCurrency());
                            fieldValue = new CurrencyData(subtract);
                        } else {
                            logger.error("\u5e73\u8861\u516c\u5f0f:" + fieldBalanceFormulaMap.get(balanceFieldKey) + ";\u8ba1\u7b97\u503c\u4e0d\u662f\u6570\u503c:" + balanceValue.getAsString());
                        }
                    }
                }
                value = dataLink.getFormatData(fieldValue, this.dataFormaterCache, this.regionQueryInfo.getContext());
            } else {
                if ("ID".equals(cell)) {
                    value = this.getBizKeyStr(dataRow, fillDataIndex);
                }
                if ("FLOATORDER".equals(cell)) {
                    FieldData fieldDefine = this.regionRelationEvn.getFloatOrderFields().get(0);
                    value = this.dataFormaterCache.getFieldValue(fieldValue, fieldDefine);
                }
            }
            rowData.add(value);
        }
        this.fillLinkPosField(rowData);
        return rowData;
    }

    private void fillLinkPosField(List<Object> rowData) {
        Map<String, LinkData> dataLinkMap = this.regionRelationEvn.getDataLinkMap();
        Map<String, LinkData> linkPosMap = this.regionRelationEvn.getLinkPosMap();
        for (String linkKey : dataLinkMap.keySet()) {
            EntityReturnInfo entityReturnInfo;
            String linkVaule;
            int cellIndex;
            EnumLinkData enumLinkData;
            Map<String, String> enumFieldPosMap;
            LinkData linkData = dataLinkMap.get(linkKey);
            if (!(linkData instanceof EnumLinkData) || (enumFieldPosMap = (enumLinkData = (EnumLinkData)linkData).getEnumFieldPosMap()) == null || enumFieldPosMap.isEmpty() || (cellIndex = this.regionQueryTableStrategy.getCells().indexOf(linkKey)) == -1 || StringUtils.isEmpty((String)(linkVaule = rowData.get(cellIndex).toString()))) continue;
            if (this.dataFormaterCache.isJsonData()) {
                JSONArray jsonArray = new JSONArray(linkVaule);
                linkVaule = jsonArray.getJSONObject(0).getString("code");
            }
            if ((entityReturnInfo = this.dataFormaterCache.getEntityDataMap().get(enumLinkData.getEntityKey())) == null) continue;
            List<String> entityFields = entityReturnInfo.getCells();
            List<EntityData> entitys = entityReturnInfo.getEntitys();
            EntityData entityData = null;
            for (EntityData entity : entitys) {
                if (!entity.getId().equals(linkVaule)) continue;
                entityData = entity;
            }
            if (entityData == null) continue;
            for (String entityfieldKey : enumFieldPosMap.keySet()) {
                int posIndex;
                String posLinkVaule;
                if (!entityFields.contains(entityfieldKey)) continue;
                int fieldIndex = entityFields.indexOf(entityfieldKey);
                String enumPosValue = entityData.getData().get(fieldIndex);
                String enumPos = enumFieldPosMap.get(entityfieldKey);
                LinkData enumFieldPosLink = linkPosMap.get(enumPos);
                if (enumFieldPosLink == null || !StringUtils.isEmpty((String)(posLinkVaule = rowData.get(posIndex = this.regionQueryTableStrategy.getCells().indexOf(enumFieldPosLink.getKey())).toString()))) continue;
                if (enumFieldPosLink instanceof EnumLinkData) {
                    EnumLinkData posEnumLinkData = (EnumLinkData)enumFieldPosLink;
                    Object enumPosValueObj = posEnumLinkData.getFormatData((AbstractData)new StringData(enumPosValue), this.dataFormaterCache, this.regionQueryInfo.getContext());
                    rowData.set(posIndex, enumPosValueObj);
                    continue;
                }
                rowData.set(posIndex, enumPosValue);
            }
        }
    }

    protected void getCalcDimensionLinks(RegionDataSet regionDataSet) {
        JtableContext jtableContext = this.regionQueryInfo.getContext();
        if (jtableContext.getFormulaSchemeKey() != null && null != jtableContext.getFormKey()) {
            IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
            IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
            List dimensionCalcCells = formulaRunTimeController.getDimensionCalcCells(jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey());
            for (CalcItem calcItem : dimensionCalcCells) {
                String linkKey = calcItem.getLinkId();
                DimensionValueSet dimensionValueSet = calcItem.getDimValues();
                List<FieldData> bizKeyOrderFields = this.regionRelationEvn.getBizKeyOrderFields().get(0);
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

    public String getBizKeyStr(IDataRow dataRow, List<Integer> fillDataIndex) {
        StringBuffer bizKeyStrBuf = new StringBuffer();
        if (dataRow.isFilledRow() && fillDataIndex.size() > 0) {
            bizKeyStrBuf.append("FILL_ENTITY_EMPTY");
            for (int cellIndex : fillDataIndex) {
                String bizKeyValue = this.getCellValue(dataRow, cellIndex);
                if (StringUtils.isEmpty((String)bizKeyValue)) {
                    if (FieldType.FIELD_TYPE_DATE == dataRow.getFieldsInfo().getDataType(cellIndex)) {
                        Date date = DataTypesConvert.periodToDate((PeriodWrapper)new PeriodWrapper("9999R0001"));
                        bizKeyValue = AbstractData.valueOf((Object)date, (int)5).getAsString();
                    } else {
                        bizKeyValue = "-";
                    }
                }
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(bizKeyValue);
            }
            return bizKeyStrBuf.toString();
        }
        List<FieldData> bizKeyOrderFields = this.regionRelationEvn.getBizKeyOrderFields().get(0);
        for (FieldData bizKeyField : bizKeyOrderFields) {
            LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(bizKeyField.getFieldKey());
            int cellIndex = -1;
            cellIndex = dataLink == null && bizKeyField.getFieldValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue() ? this.regionRelationEvn.getCellIndex("ID") : this.regionRelationEvn.getCellIndex(dataLink.getKey());
            if (cellIndex < 0) continue;
            String bizKeyValue = this.getCellValue(dataRow, cellIndex);
            if (StringUtils.isEmpty((String)bizKeyValue)) {
                if (FieldType.FIELD_TYPE_DATE.getValue() == bizKeyField.getFieldType()) {
                    Date date = DataTypesConvert.periodToDate((PeriodWrapper)new PeriodWrapper("9999R0001"));
                    bizKeyValue = AbstractData.valueOf((Object)date, (int)5).getAsString();
                } else {
                    bizKeyValue = "-";
                }
            }
            if (bizKeyStrBuf.length() > 0) {
                bizKeyStrBuf.append("#^$");
            }
            bizKeyStrBuf.append(bizKeyValue);
        }
        return bizKeyStrBuf.toString();
    }

    private String getCellValue(IDataRow dataRow, int cellIndex) {
        String cellValue = "";
        if (cellIndex >= 0) {
            try {
                cellValue = dataRow.getAsString(cellIndex);
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u83b7\u53d6\u6307\u6807\u6570\u636e\u51fa\u9519"});
            }
        }
        return cellValue;
    }

    public RegionDataCount getRegionDataCount() {
        RegionDataCount regionDataCout = new RegionDataCount();
        regionDataCout.setDataType(this.regionRelationEvn.getRegionData().getType());
        IReadonlyTable regionQueryTable = this.regionQueryTableStrategy.getRegionQueryTable();
        regionDataCout.setTotalCount(regionQueryTable.getTotalCount());
        return regionDataCout;
    }

    public DimensionValueSet getRowDimensionValueSet(String dataId) {
        IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String[] bizKeys = dataId.split("\\#\\^\\$");
        int i = 0;
        for (FieldData fieldData : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
            String dimensionName = jtableDataEngineService.getDimensionName(fieldData);
            dimensionValueSet.setValue(dimensionName, (Object)bizKeys[i]);
            ++i;
        }
        return dimensionValueSet;
    }
}

