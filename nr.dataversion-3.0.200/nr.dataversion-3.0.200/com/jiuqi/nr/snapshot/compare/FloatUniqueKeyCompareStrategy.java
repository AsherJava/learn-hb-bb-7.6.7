/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.service.IoEntityService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.snapshot.bean.CompareDifferenceItem;
import com.jiuqi.nr.snapshot.bean.FloatUniqueKeyRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.ICompareDifferenceItem;
import com.jiuqi.nr.snapshot.bean.NaturalKeyCompareDifference;
import com.jiuqi.nr.snapshot.compare.AbstractCompareStrategy;
import com.jiuqi.nr.snapshot.compare.CompareDiffenceUtil;
import com.jiuqi.nr.snapshot.conts.Consts;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatUniqueKeyCompareStrategy
extends AbstractCompareStrategy {
    private static String FRACTION_ZERO = ".0000000000000000000000000000000";
    private IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
    private IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private IoEntityService ioEntityService = (IoEntityService)BeanUtil.getBean(IoEntityService.class);
    private static final Logger logger = LoggerFactory.getLogger(FloatUniqueKeyCompareStrategy.class);

    public FloatUniqueKeyRegionCompareDifference compareRegionVersionDataFile(RegionData region, TableContext tableContext, String initialDataVersionId, String compareDataVersionId, List<Map<String, Object>> initalRows, List<Map<String, Object>> compareRows) {
        NaturalKeyCompareDifference naturalKeyCompareDifference;
        Object df;
        FieldDefine fieldDefine;
        String bizKeyValue;
        ArrayList<String> bizKeyNames;
        List<String> bizKeyValues;
        FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = new FloatUniqueKeyRegionCompareDifference();
        floatUniqueKeyRegionCompareDifference.setRegionKey(region.getKey());
        floatUniqueKeyRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<NaturalKeyCompareDifference> naturalKeyCompareDifferences = new ArrayList<NaturalKeyCompareDifference>();
        floatUniqueKeyRegionCompareDifference.setNatures(naturalKeyCompareDifferences);
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, tableContext, initialDataVersionId.toString(), "VERSIONID");
        ArrayList<Map<String, String>> naturalFields = new ArrayList<Map<String, String>>();
        List bizFieldDefList = initialRegionDataSet.getBizFieldDefList();
        for (FieldDefine fieldDefine2 : bizFieldDefList) {
            HashMap<String, String> oneBizField = new HashMap<String, String>();
            oneBizField.put("fieldCode", fieldDefine2.getCode());
            oneBizField.put("fieldTitle", fieldDefine2.getTitle());
            naturalFields.add(oneBizField);
        }
        Map<String, Map<String, Object>> initialBizKeyDataMap = this.getBizKeyDataMapFile(region, initalRows, naturalFields);
        Map<String, Map<String, Object>> compareBizKeyDataMap = this.getBizKeyDataMapFile(region, compareRows, naturalFields);
        for (String bizKeyStr : initialBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList<String>();
            for (int i = 0; i < bizFieldDefList.size(); ++i) {
                bizKeyValue = bizKeyValues.get(i);
                fieldDefine = (FieldDefine)bizFieldDefList.get(i);
                df = (DataField)fieldDefine;
                if (null == df.getRefDataEntityKey()) continue;
                this.queryEnumTitle(tableContext, bizKeyNames, bizKeyValue, fieldDefine, (DataField)df);
            }
            Map<String, Object> initialRowData = initialBizKeyDataMap.get(bizKeyStr);
            naturalKeyCompareDifference = new NaturalKeyCompareDifference();
            naturalKeyCompareDifference.setNaturalNames(bizKeyNames);
            naturalKeyCompareDifference.setNaturalKeys(bizKeyValues);
            String initialFloatId = "";
            if (initialRowData.containsKey("BIZKEYORDER")) {
                initialFloatId = initialRowData.get("BIZKEYORDER").toString();
            }
            naturalKeyCompareDifference.setInitialFloatId(initialFloatId);
            naturalKeyCompareDifference.setNaturalKey(UUID.randomUUID().toString());
            if (compareBizKeyDataMap.containsKey(bizKeyStr)) {
                Map<String, Object> compareRowData = compareBizKeyDataMap.get(bizKeyStr);
                naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.UPDATE);
                String compareFloatId = "";
                if (compareRowData.containsKey("BIZKEYORDER")) {
                    compareFloatId = compareRowData.get("BIZKEYORDER").toString();
                }
                naturalKeyCompareDifference.setCompareFloatId(compareFloatId);
                ArrayList<ICompareDifferenceItem> differenceItems = new ArrayList<ICompareDifferenceItem>();
                naturalKeyCompareDifference.setUpdateItems(differenceItems);
                boolean different = false;
                String ownerTableKey = ((FieldDefine)initialRegionDataSet.getBizFieldDefList().get(0)).getOwnerTableKey();
                List allFields = null;
                try {
                    allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(ownerTableKey);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u6307\u5b9a\u5b58\u50a8\u8868\u7684\u6307\u6807\u5217\u8868\u5931\u8d25", e);
                }
                List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(region.getKey()));
                listFieldDefine.addAll(allFields);
                HashMap<String, FieldDefine> fields = new HashMap<String, FieldDefine>();
                for (FieldDefine fieldDefine3 : listFieldDefine) {
                    fields.put(fieldDefine3.getCode(), fieldDefine3);
                }
                List fieldDataList = initialRegionDataSet.getFieldDataList();
                for (int cellIndex = 0; cellIndex < fieldDataList.size(); ++cellIndex) {
                    String format1;
                    String format;
                    SimpleDateFormat sdf;
                    String cell = ((ExportFieldDefine)fieldDataList.get(cellIndex)).getCode();
                    FieldDefine fieldDefine4 = null;
                    if (cell.contains(".")) {
                        fieldDefine4 = (FieldDefine)fields.get(cell.split("\\.")[1]);
                    }
                    String initialValue = "";
                    String compareValue = "";
                    String zbcode = fieldDefine4.getCode();
                    Object initialValueObject = initialRowData.get(zbcode);
                    if (initialValueObject instanceof BigDecimal) {
                        BigDecimal bigDecimal = (BigDecimal)initialValueObject;
                        initialValue = bigDecimal.toPlainString();
                    } else {
                        initialValue = initialValueObject == null ? "" : initialValueObject.toString();
                    }
                    Object compareValueObject = compareRowData.get(zbcode);
                    if (compareValueObject instanceof BigDecimal) {
                        BigDecimal bigDecimal = (BigDecimal)compareValueObject;
                        compareValue = bigDecimal.toPlainString();
                    } else {
                        String string = compareValue = null != compareValueObject ? compareValueObject.toString() : "";
                    }
                    if (initialValue.equals(compareValue)) continue;
                    try {
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
                        nf.setMinimumFractionDigits(30);
                        Number number = nf.parse(initialValue);
                        Number number1 = nf.parse(compareValue);
                        BigDecimal bigDecimal = BigDecimal.valueOf(number.doubleValue());
                        BigDecimal bigDecimal1 = BigDecimal.valueOf(number1.doubleValue());
                        if (bigDecimal.compareTo(bigDecimal1) == 0) {
                            continue;
                        }
                    }
                    catch (ParseException nf) {
                        // empty catch block
                    }
                    if (fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_DATE)) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                        }
                        catch (Exception e) {
                            format = initialValue;
                        }
                        try {
                            format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                        }
                        catch (Exception e) {
                            format1 = compareValue;
                        }
                        if (format.equals(format1)) continue;
                    }
                    if (fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            format = sdf.format(new Date(Long.valueOf(String.valueOf(initialValue))));
                        }
                        catch (Exception e) {
                            format = initialValue;
                        }
                        try {
                            format1 = sdf.format(new Date(Long.valueOf(String.valueOf(compareValue))));
                        }
                        catch (Exception e) {
                            format1 = compareValue;
                        }
                        if (format.equals(format1)) continue;
                    }
                    different = true;
                    if (!fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) && !fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) {
                        CompareDifferenceItem differenceItem = new CompareDifferenceItem();
                        initialValue = CompareDiffenceUtil.translateString(fieldDefine4, initialValue);
                        compareValue = CompareDiffenceUtil.translateString(fieldDefine4, compareValue);
                        if (fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL)) {
                            int fraction = fieldDefine4.getFractionDigits() + 1;
                            if (null != initialValue && initialValue.length() > 0 && !initialValue.contains(".") && fraction > 1) {
                                initialValue = initialValue + FRACTION_ZERO.substring(0, fraction);
                            }
                            if (null != compareValue && compareValue.length() > 0 && !compareValue.contains(".") && fraction > 1) {
                                compareValue = compareValue + FRACTION_ZERO.substring(0, fraction);
                            }
                        }
                        differenceItem.setInitialValue(initialValue);
                        differenceItem.setCompareValue(compareValue);
                        String difference = CompareDiffenceUtil.compareDifference(fieldDefine4.getType().getValue(), initialValue, compareValue);
                        differenceItem.setDifference(difference);
                        List linksInRegionByField = this.runTimeViewController.getLinksInRegionByField(region.getKey(), fieldDefine4.getKey());
                        DataLinkDefine dataLinkDefine = null;
                        if (linksInRegionByField != null && !linksInRegionByField.isEmpty()) {
                            dataLinkDefine = (DataLinkDefine)linksInRegionByField.get(0);
                        }
                        if (dataLinkDefine != null) {
                            differenceItem.setDataLinkKey(dataLinkDefine.getKey());
                        }
                        differenceItem.setFieldKey(fieldDefine4.getKey());
                        differenceItem.setFieldCode(fieldDefine4.getCode());
                        differenceItem.setFieldTitle(fieldDefine4.getTitle());
                        differenceItems.add(differenceItem);
                        continue;
                    }
                    different = false;
                }
                if (different) {
                    naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
                }
                compareBizKeyDataMap.remove(bizKeyStr);
                continue;
            }
            naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.REMOVE);
            ArrayList<ICompareDifferenceItem> updateItems = new ArrayList<ICompareDifferenceItem>();
            CompareDifferenceItem differenceItem = new CompareDifferenceItem();
            differenceItem.setCompareValue("");
            differenceItem.setInitialValue(StringUtils.join(naturalKeyCompareDifference.getNaturalNames().iterator(), (String)";"));
            updateItems.add(differenceItem);
            naturalKeyCompareDifference.setUpdateItems(updateItems);
            naturalKeyCompareDifference.setNaturalFields(naturalFields);
            naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
        }
        for (String bizKeyStr : compareBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList();
            for (int i = 0; i < bizFieldDefList.size(); ++i) {
                bizKeyValue = bizKeyValues.get(i);
                fieldDefine = (FieldDefine)bizFieldDefList.get(i);
                df = (DataField)fieldDefine;
                if (null == df.getRefDataEntityKey()) continue;
                this.queryEnumTitle(tableContext, bizKeyNames, bizKeyValue, fieldDefine, (DataField)df);
            }
            Map<String, Object> compareRowData = compareBizKeyDataMap.get(bizKeyStr);
            naturalKeyCompareDifference = new NaturalKeyCompareDifference();
            naturalKeyCompareDifference.setNaturalNames(bizKeyNames);
            ArrayList<String> bizKeyLinks = new ArrayList<String>();
            for (FieldDefine bizField : bizFieldDefList) {
                bizKeyLinks.add(bizField.getKey());
            }
            naturalKeyCompareDifference.setNaturalKeys(bizKeyLinks);
            naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.ADD);
            String compareFloatId = "";
            if (compareRowData.containsKey("BIZKEYORDER")) {
                compareFloatId = compareRowData.get("BIZKEYORDER").toString();
            }
            naturalKeyCompareDifference.setCompareFloatId(compareFloatId);
            naturalKeyCompareDifference.setNaturalKey(UUID.randomUUID().toString());
            ArrayList<ICompareDifferenceItem> updateItems = new ArrayList<ICompareDifferenceItem>();
            CompareDifferenceItem differenceItem = new CompareDifferenceItem();
            differenceItem.setCompareValue(StringUtils.join(naturalKeyCompareDifference.getNaturalNames().iterator(), (String)";"));
            differenceItem.setInitialValue("");
            updateItems.add(differenceItem);
            naturalKeyCompareDifference.setNaturalFields(naturalFields);
            naturalKeyCompareDifference.setUpdateItems(updateItems);
            naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
        }
        return floatUniqueKeyRegionCompareDifference;
    }

    private void queryEnumTitle(TableContext tableContext, List<String> bizKeyNames, String bizKeyValue, FieldDefine fieldDefine, DataField df) {
        try {
            FieldDefine refer = this.dataDefinitionRuntimeController.queryFieldDefine(df.getRefDataEntityKey());
            if (refer == null) {
                ColumnModelDefine colum = this.dataModelService.getColumnModelDefineByID(df.getRefDataFieldKey());
                String referCode = colum.getCode();
            } else {
                String referCode = refer.getCode();
            }
            String selectViewKey = fieldDefine.getEntityKey();
            EntityViewDefine queryEntityView = this.entityViewRunTimeController.buildEntityView(selectViewKey);
            if (null != queryEntityView && queryEntityView.getEntityId() != null) {
                ExecutorContext executorContext = this.getExecutorContext(tableContext);
                IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, tableContext, executorContext);
                if (entityTables != null) {
                    IEntityRow findByCode = entityTables.findByCode(bizKeyValue);
                    if (findByCode != null) {
                        bizKeyNames.add(findByCode.getTitle());
                    } else {
                        bizKeyNames.add(bizKeyValue);
                    }
                } else {
                    bizKeyNames.add(bizKeyValue);
                }
            } else {
                bizKeyNames.add(bizKeyValue);
            }
        }
        catch (Exception e) {
            logger.debug("\u6ca1\u6709\u627e\u5173\u8054\u7684\u6307\u6807{}", (Object)e.getMessage());
        }
    }

    @Override
    public FloatUniqueKeyRegionCompareDifference compareRegionVersionData(RegionData region, TableContext tableContext, String initialDimension, String compareDimension, String dimensionName) {
        CompareDifferenceItem differenceItem;
        ArrayList<ICompareDifferenceItem> updateItems;
        NaturalKeyCompareDifference naturalKeyCompareDifference;
        DataField df;
        FieldDefine fieldDefine;
        String bizKeyValue;
        ArrayList<String> bizKeyNames;
        List<String> bizKeyValues;
        FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = new FloatUniqueKeyRegionCompareDifference();
        floatUniqueKeyRegionCompareDifference.setRegionKey(region.getKey());
        floatUniqueKeyRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<NaturalKeyCompareDifference> naturalKeyCompareDifferences = new ArrayList<NaturalKeyCompareDifference>();
        floatUniqueKeyRegionCompareDifference.setNatures(naturalKeyCompareDifferences);
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, tableContext, initialDimension, dimensionName);
        Map<String, List<Object>> initialBizKeyDataMap = this.getBizKeyDataMap(region, null, (IRegionDataSet)initialRegionDataSet);
        List bizFieldDefList = initialRegionDataSet.getBizFieldDefList();
        ArrayList<Map<String, String>> naturalFields = new ArrayList<Map<String, String>>();
        for (FieldDefine bizField : bizFieldDefList) {
            HashMap<String, String> oneBizField = new HashMap<String, String>();
            oneBizField.put("fieldCode", bizField.getCode());
            oneBizField.put("fieldTitle", bizField.getTitle());
            naturalFields.add(oneBizField);
        }
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, tableContext, compareDimension, dimensionName);
        Map<String, List<Object>> compareBizKeyDataMap = this.getBizKeyDataMap(region, null, (IRegionDataSet)compareRegionDataSet);
        if (this.runTimeViewController == null) {
            this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        }
        String ownerTableKey = ((FieldDefine)initialRegionDataSet.getBizFieldDefList().get(0)).getOwnerTableKey();
        List allFields = null;
        try {
            allFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(ownerTableKey);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6307\u5b9a\u5b58\u50a8\u8868\u7684\u6307\u6807\u5217\u8868\u5931\u8d25", e);
        }
        List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(region.getKey()));
        listFieldDefine.addAll(allFields);
        HashMap<String, FieldDefine> fields = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine2 : listFieldDefine) {
            fields.put(fieldDefine2.getCode(), fieldDefine2);
        }
        List fieldDataList = initialRegionDataSet.getFieldDataList();
        ArrayList<String> bizKeyLinks = new ArrayList<String>();
        for (FieldDefine fieldDefine3 : bizFieldDefList) {
            bizKeyLinks.add(fieldDefine3.getKey());
        }
        for (String bizKeyStr : initialBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList<String>();
            for (int i = 0; i < bizFieldDefList.size(); ++i) {
                bizKeyValue = bizKeyValues.get(i);
                fieldDefine = (FieldDefine)bizFieldDefList.get(i);
                df = (DataField)fieldDefine;
                if (null == df.getRefDataEntityKey()) continue;
                this.queryEnumTitle(tableContext, bizKeyNames, bizKeyValue, fieldDefine, df);
            }
            List<Object> initialRowData = initialBizKeyDataMap.get(bizKeyStr);
            naturalKeyCompareDifference = new NaturalKeyCompareDifference();
            naturalKeyCompareDifference.setNaturalNames(bizKeyNames);
            naturalKeyCompareDifference.setNaturalKeys(bizKeyValues);
            naturalKeyCompareDifference.setInitialFloatId(initialRowData.get(0).toString());
            naturalKeyCompareDifference.setNaturalKey(UUID.randomUUID().toString());
            if (compareBizKeyDataMap.containsKey(bizKeyStr)) {
                List<Object> compareRowData = compareBizKeyDataMap.get(bizKeyStr);
                naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.UPDATE);
                naturalKeyCompareDifference.setCompareFloatId(compareRowData.get(0).toString());
                ArrayList<ICompareDifferenceItem> differenceItems = new ArrayList<ICompareDifferenceItem>();
                naturalKeyCompareDifference.setUpdateItems(differenceItems);
                boolean different = false;
                for (int cellIndex = 0; cellIndex < fieldDataList.size(); ++cellIndex) {
                    String cell = ((ExportFieldDefine)fieldDataList.get(cellIndex)).getCode();
                    FieldDefine fieldDefine4 = null;
                    if (cell.contains(".")) {
                        fieldDefine4 = (FieldDefine)fields.get(cell.split("\\.")[1]);
                    }
                    if (!UUIDUtils.isUUID((String)cell)) continue;
                    String initialValue = "";
                    String compareValue = "";
                    Object initialValueObject = initialRowData.get(cellIndex);
                    if (initialValueObject instanceof BigDecimal) {
                        BigDecimal bigDecimal = (BigDecimal)initialValueObject;
                        initialValue = bigDecimal.toPlainString();
                    } else {
                        initialValue = initialValueObject.toString();
                    }
                    Object compareValueObject = compareRowData.get(cellIndex);
                    if (compareValueObject instanceof BigDecimal) {
                        BigDecimal bigDecimal = (BigDecimal)compareValueObject;
                        compareValue = bigDecimal.toPlainString();
                    } else {
                        compareValue = compareValueObject.toString();
                    }
                    if (initialValue.equals(compareValue)) continue;
                    different = true;
                    if (fieldDefine4 == null) continue;
                    if (!fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_FILE) || !fieldDefine4.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE)) {
                        CompareDifferenceItem differenceItem2 = new CompareDifferenceItem();
                        initialValue = CompareDiffenceUtil.translateString(fieldDefine4, initialValue);
                        compareValue = CompareDiffenceUtil.translateString(fieldDefine4, compareValue);
                        differenceItem2.setInitialValue(initialValue);
                        differenceItem2.setCompareValue(compareValue);
                        String difference = CompareDiffenceUtil.compareDifference(fieldDefine4.getType().getValue(), initialValue, compareValue);
                        differenceItem2.setDifference(difference);
                        List linksInRegionByField = this.runTimeViewController.getLinksInRegionByField(region.getKey(), fieldDefine4.getKey());
                        DataLinkDefine dataLinkDefine = null;
                        if (linksInRegionByField != null && !linksInRegionByField.isEmpty()) {
                            dataLinkDefine = (DataLinkDefine)linksInRegionByField.get(0);
                        }
                        if (dataLinkDefine != null) {
                            differenceItem2.setDataLinkKey(dataLinkDefine.getKey());
                        }
                        differenceItem2.setFieldKey(fieldDefine4.getKey());
                        differenceItem2.setFieldCode(fieldDefine4.getCode());
                        differenceItem2.setFieldTitle(fieldDefine4.getTitle());
                        differenceItems.add(differenceItem2);
                        continue;
                    }
                    different = false;
                }
                if (different) {
                    naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
                }
                compareBizKeyDataMap.remove(bizKeyStr);
                continue;
            }
            naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.REMOVE);
            updateItems = new ArrayList<ICompareDifferenceItem>();
            differenceItem = new CompareDifferenceItem();
            differenceItem.setCompareValue("");
            differenceItem.setInitialValue(StringUtils.join(naturalKeyCompareDifference.getNaturalNames().iterator(), (String)";"));
            updateItems.add(differenceItem);
            naturalKeyCompareDifference.setUpdateItems(updateItems);
            naturalKeyCompareDifference.setNaturalFields(naturalFields);
            naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
        }
        for (String bizKeyStr : compareBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList();
            for (int i = 0; i < bizFieldDefList.size(); ++i) {
                bizKeyValue = bizKeyValues.get(i);
                fieldDefine = (FieldDefine)bizFieldDefList.get(i);
                df = (DataField)fieldDefine;
                if (null == df.getRefDataEntityKey()) continue;
                this.queryEnumTitle(tableContext, bizKeyNames, bizKeyValue, fieldDefine, df);
            }
            List<Object> compareRowData = compareBizKeyDataMap.get(bizKeyStr);
            naturalKeyCompareDifference = new NaturalKeyCompareDifference();
            naturalKeyCompareDifference.setNaturalNames(bizKeyNames);
            naturalKeyCompareDifference.setNaturalKeys(bizKeyLinks);
            naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.ADD);
            naturalKeyCompareDifference.setCompareFloatId(compareRowData.get(0).toString());
            naturalKeyCompareDifference.setNaturalKey(UUID.randomUUID().toString());
            updateItems = new ArrayList();
            differenceItem = new CompareDifferenceItem();
            differenceItem.setCompareValue(StringUtils.join(naturalKeyCompareDifference.getNaturalNames().iterator(), (String)";"));
            differenceItem.setInitialValue("");
            updateItems.add(differenceItem);
            naturalKeyCompareDifference.setNaturalFields(naturalFields);
            naturalKeyCompareDifference.setUpdateItems(updateItems);
            naturalKeyCompareDifferences.add(naturalKeyCompareDifference);
        }
        return floatUniqueKeyRegionCompareDifference;
    }

    public Map<String, List<Object>> getBizKeyDataMap(RegionData region, List<String> bizKeyLinks, IRegionDataSet regionDataSet) {
        LinkedHashMap<String, List<Object>> bizKeyDataMap = new LinkedHashMap<String, List<Object>>();
        List bizFieldDefList = regionDataSet.getBizFieldDefList();
        List fieldDataList = regionDataSet.getFieldDataList();
        HashMap<String, Integer> cells = new HashMap<String, Integer>();
        for (int i = 0; i < fieldDataList.size(); ++i) {
            for (int j = 0; j < bizFieldDefList.size(); ++j) {
                if (!((ExportFieldDefine)fieldDataList.get(i)).getCode().split("\\.")[0].equals(bizFieldDefList.get(j))) continue;
                cells.put(((FieldDefine)bizFieldDefList.get(j)).getCode(), i);
            }
        }
        while (regionDataSet.hasNext()) {
            List row = (List)regionDataSet.next();
            StringBuffer bizKeyStrBuf = new StringBuffer();
            for (FieldDefine bizKey : bizFieldDefList) {
                int bizKeyIndex = (Integer)cells.get(bizKey.getCode());
                String bizKeyValue = row.get(bizKeyIndex).toString();
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(bizKeyValue);
            }
            bizKeyDataMap.put(bizKeyStrBuf.toString(), row);
        }
        return bizKeyDataMap;
    }

    private Map<String, Map<String, Object>> getBizKeyDataMapFile(RegionData region, List<Map<String, Object>> rows, List<Map<String, String>> naturalFields) {
        LinkedHashMap<String, Map<String, Object>> bizKeyDataMap = new LinkedHashMap<String, Map<String, Object>>();
        if (null != rows) {
            for (Map<String, Object> rowData : rows) {
                StringBuffer bizKeyStrBuf = new StringBuffer();
                for (Map<String, String> bizKey : naturalFields) {
                    String bizKeyValue = rowData.get(bizKey.get("fieldCode")).toString();
                    if (bizKeyStrBuf.length() > 0) {
                        bizKeyStrBuf.append("#^$");
                    }
                    bizKeyStrBuf.append(bizKeyValue);
                }
                bizKeyDataMap.put(bizKeyStrBuf.toString(), rowData);
            }
        }
        return bizKeyDataMap;
    }

    private ExecutorContext getExecutorContext(TableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }
}

