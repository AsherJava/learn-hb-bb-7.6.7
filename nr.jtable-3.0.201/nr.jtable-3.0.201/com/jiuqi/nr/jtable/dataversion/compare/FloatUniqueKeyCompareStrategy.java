/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.data.engine.bean.CompareDifferenceItem
 *  com.jiuqi.nr.data.engine.bean.FloatUniqueKeyRegionCompareDifference
 *  com.jiuqi.nr.data.engine.bean.NaturalKeyCompareDifference
 *  com.jiuqi.nr.data.engine.util.Consts$NaturalKeyCompareType
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataversion.compare;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.data.engine.bean.CompareDifferenceItem;
import com.jiuqi.nr.data.engine.bean.FloatUniqueKeyRegionCompareDifference;
import com.jiuqi.nr.data.engine.bean.NaturalKeyCompareDifference;
import com.jiuqi.nr.data.engine.util.Consts;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataversion.compare.AbstractCompareStrategy;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.CompareDiffenceUtil;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class FloatUniqueKeyCompareStrategy
extends AbstractCompareStrategy {
    private static String FRACTION_ZERO = ".0000000000000000000000000000000";

    public FloatUniqueKeyRegionCompareDifference compareRegionVersionDataFile(RegionData region, JtableContext jtableContext, UUID initialDataVersionId, UUID compareDataVersionId, List<Map<String, Object>> initalRows, List<Map<String, Object>> compareRows) {
        CompareDifferenceItem differenceItem;
        ArrayList<CompareDifferenceItem> updateItems;
        NaturalKeyCompareDifference naturalKeyCompareDifference;
        String entityKey;
        EnumLinkData enumLink;
        LinkData dataLink;
        String bizKeyValue;
        String bizKeyLink;
        ArrayList<String> bizKeyNames;
        List<String> bizKeyValues;
        FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = new FloatUniqueKeyRegionCompareDifference();
        floatUniqueKeyRegionCompareDifference.setRegionKey(region.getKey());
        floatUniqueKeyRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<NaturalKeyCompareDifference> naturalKeyCompareDifferences = new ArrayList<NaturalKeyCompareDifference>();
        floatUniqueKeyRegionCompareDifference.setNatures(naturalKeyCompareDifferences);
        FloatRegionRelationEvn regionRelationEvn = new FloatRegionRelationEvn(region, jtableContext);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        List<String> bizKeyLinks = regionRelationEvn.getBizKeyLinks();
        ArrayList<Map<String, String>> naturalFields = new ArrayList<Map<String, String>>();
        for (String linkDataKey : bizKeyLinks) {
            LinkData link = jtableParamService.getLink(linkDataKey);
            if (!StringUtils.isNotEmpty((String)link.getZbid())) continue;
            HashMap<String, String> oneBizField = new HashMap<String, String>();
            oneBizField.put("fieldCode", link.getZbcode());
            oneBizField.put("fieldTitle", link.getZbtitle());
            naturalFields.add(oneBizField);
        }
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, jtableContext, initialDataVersionId);
        Map<String, Map<String, Object>> initialBizKeyDataMap = this.getBizKeyDataMapFile(region, bizKeyLinks, initalRows, naturalFields);
        Map<String, Map<String, Object>> compareBizKeyDataMap = this.getBizKeyDataMapFile(region, bizKeyLinks, compareRows, naturalFields);
        List<String> cells = initialRegionDataSet.getCells().get(region.getKey().toString());
        for (String bizKeyStr : initialBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList<String>();
            for (int i = 0; i < bizKeyLinks.size(); ++i) {
                bizKeyLink = bizKeyLinks.get(i);
                bizKeyValue = bizKeyValues.get(i);
                dataLink = regionRelationEvn.getDataLinkByKey(bizKeyLink);
                if (dataLink instanceof EnumLinkData) {
                    enumLink = (EnumLinkData)dataLink;
                    entityKey = enumLink.getEntityKey();
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setContext(jtableContext);
                    entityQueryByKeyInfo.setEntityViewKey(entityKey);
                    entityQueryByKeyInfo.setDataLinkKey(bizKeyLink);
                    entityQueryByKeyInfo.setEntityKey(bizKeyValue);
                    EntityByKeyReturnInfo returnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    if (returnInfo.getEntity() != null) {
                        bizKeyNames.add(returnInfo.getEntity().getRowCaption());
                        continue;
                    }
                    bizKeyNames.add(bizKeyValue);
                    continue;
                }
                bizKeyNames.add(bizKeyValue);
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
                ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
                naturalKeyCompareDifference.setUpdateItems(differenceItems);
                boolean different = false;
                for (int cellIndex = 0; cellIndex < cells.size(); ++cellIndex) {
                    String format1;
                    String format;
                    SimpleDateFormat sdf;
                    String cell = cells.get(cellIndex);
                    if (!UUIDUtils.isUUID((String)cell)) continue;
                    String initialValue = "";
                    String compareValue = "";
                    LinkData linkd = jtableParamService.getLink(cell);
                    String zbcode = StringUtils.isNotEmpty((String)linkd.getZbid()) ? linkd.getZbcode() : (cell.equals("ID") ? "BIZKEYORDER" : cell);
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
                    LinkData link = jtableParamService.getLink(cell);
                    try {
                        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
                        nf.setMinimumFractionDigits(30);
                        Number number = nf.parse(initialValue);
                        Number number1 = nf.parse(compareValue);
                        BigDecimal bigDecimal = new BigDecimal(number.doubleValue());
                        BigDecimal bigDecimal1 = new BigDecimal(number1.doubleValue());
                        if (bigDecimal.compareTo(bigDecimal1) == 0) {
                            continue;
                        }
                    }
                    catch (ParseException nf) {
                        // empty catch block
                    }
                    if (link.getType() == LinkType.LINK_TYPE_DATE.getValue()) {
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
                    if (link.getType() == LinkType.LINK_TYPE_DATETIME.getValue()) {
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
                    if (link.getType() != LinkType.LINK_TYPE_PICTURE.getValue() && link.getType() != LinkType.LINK_TYPE_FILE.getValue()) {
                        CompareDifferenceItem differenceItem2 = new CompareDifferenceItem();
                        initialValue = CompareDiffenceUtil.translateString(link, initialValue);
                        compareValue = CompareDiffenceUtil.translateString(link, compareValue);
                        if (link instanceof DecimalLinkData) {
                            DecimalLinkData dl = (DecimalLinkData)link;
                            int fraction = dl.getFraction() + 1;
                            if (null != initialValue && initialValue.length() > 0 && !initialValue.contains(".") && fraction > 1) {
                                initialValue = initialValue + FRACTION_ZERO.substring(0, fraction);
                            }
                            if (null != compareValue && compareValue.length() > 0 && !compareValue.contains(".") && fraction > 1) {
                                compareValue = compareValue + FRACTION_ZERO.substring(0, fraction);
                            }
                        }
                        differenceItem2.setInitialValue(initialValue);
                        differenceItem2.setCompareValue(compareValue);
                        String difference = CompareDiffenceUtil.compareDifference(link.getType(), initialValue, compareValue);
                        differenceItem2.setDifference(difference);
                        differenceItem2.setDataLinkKey(link.getKey());
                        if (StringUtils.isNotEmpty((String)link.getZbid())) {
                            differenceItem2.setFieldKey(link.getZbid());
                            differenceItem2.setFieldCode(link.getZbcode());
                            differenceItem2.setFieldTitle(link.getZbtitle());
                        }
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
            updateItems = new ArrayList<CompareDifferenceItem>();
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
            for (int i = 0; i < bizKeyLinks.size(); ++i) {
                bizKeyLink = bizKeyLinks.get(i);
                bizKeyValue = bizKeyValues.get(i);
                dataLink = regionRelationEvn.getDataLinkByKey(bizKeyLink);
                if (dataLink instanceof EnumLinkData) {
                    enumLink = (EnumLinkData)dataLink;
                    entityKey = enumLink.getEntityKey();
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setContext(jtableContext);
                    entityQueryByKeyInfo.setEntityViewKey(entityKey);
                    entityQueryByKeyInfo.setDataLinkKey(bizKeyLink);
                    entityQueryByKeyInfo.setEntityKey(bizKeyValue);
                    EntityByKeyReturnInfo returnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    if (returnInfo.getEntity() != null) {
                        bizKeyNames.add(returnInfo.getEntity().getRowCaption());
                        continue;
                    }
                    bizKeyNames.add(bizKeyValue);
                    continue;
                }
                bizKeyNames.add(bizKeyValue);
            }
            Map<String, Object> compareRowData = compareBizKeyDataMap.get(bizKeyStr);
            naturalKeyCompareDifference = new NaturalKeyCompareDifference();
            naturalKeyCompareDifference.setNaturalNames(bizKeyNames);
            naturalKeyCompareDifference.setNaturalKeys(bizKeyLinks);
            naturalKeyCompareDifference.setType(Consts.NaturalKeyCompareType.ADD);
            String compareFloatId = "";
            if (compareRowData.containsKey("BIZKEYORDER")) {
                compareFloatId = compareRowData.get("BIZKEYORDER").toString();
            }
            naturalKeyCompareDifference.setCompareFloatId(compareFloatId);
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

    public FloatUniqueKeyRegionCompareDifference compareRegionVersionData(RegionData region, JtableContext jtableContext, UUID initialDataVersionId, UUID compareDataVersionId) {
        CompareDifferenceItem differenceItem;
        ArrayList<CompareDifferenceItem> updateItems;
        NaturalKeyCompareDifference naturalKeyCompareDifference;
        EntityByKeyReturnInfo returnInfo;
        EntityQueryByKeyInfo entityQueryByKeyInfo;
        LinkData dataLink;
        String bizKeyValue;
        String bizKeyLink;
        ArrayList<String> bizKeyNames;
        List<String> bizKeyValues;
        FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = new FloatUniqueKeyRegionCompareDifference();
        floatUniqueKeyRegionCompareDifference.setRegionKey(region.getKey());
        floatUniqueKeyRegionCompareDifference.setRegionName(region.getTitle());
        ArrayList<NaturalKeyCompareDifference> naturalKeyCompareDifferences = new ArrayList<NaturalKeyCompareDifference>();
        floatUniqueKeyRegionCompareDifference.setNatures(naturalKeyCompareDifferences);
        FloatRegionRelationEvn regionRelationEvn = new FloatRegionRelationEvn(region, jtableContext);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        List<String> bizKeyLinks = regionRelationEvn.getBizKeyLinks();
        ArrayList naturalFields = new ArrayList();
        for (String linkDataKey : bizKeyLinks) {
            LinkData link = jtableParamService.getLink(linkDataKey);
            if (!StringUtils.isNotEmpty((String)link.getZbid())) continue;
            HashMap<String, String> oneBizField = new HashMap<String, String>();
            oneBizField.put("fieldCode", link.getZbcode());
            oneBizField.put("fieldTitle", link.getZbtitle());
            naturalFields.add(oneBizField);
        }
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, jtableContext, initialDataVersionId);
        Map<String, List<Object>> initialBizKeyDataMap = this.getBizKeyDataMap(region, bizKeyLinks, initialRegionDataSet);
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, jtableContext, compareDataVersionId);
        Map<String, List<Object>> compareBizKeyDataMap = this.getBizKeyDataMap(region, bizKeyLinks, compareRegionDataSet);
        List<String> cells = initialRegionDataSet.getCells().get(region.getKey().toString());
        for (String bizKeyStr : initialBizKeyDataMap.keySet()) {
            bizKeyValues = Arrays.asList(bizKeyStr.split("\\#\\^\\$"));
            bizKeyNames = new ArrayList<String>();
            for (int i = 0; i < bizKeyLinks.size(); ++i) {
                bizKeyLink = bizKeyLinks.get(i);
                bizKeyValue = bizKeyValues.get(i);
                dataLink = regionRelationEvn.getDataLinkByKey(bizKeyLink);
                if (dataLink instanceof EnumLinkData) {
                    EnumLinkData enumLink = (EnumLinkData)dataLink;
                    String entityKey = enumLink.getEntityKey();
                    entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setContext(jtableContext);
                    entityQueryByKeyInfo.setEntityViewKey(entityKey);
                    entityQueryByKeyInfo.setDataLinkKey(bizKeyLink);
                    entityQueryByKeyInfo.setEntityKey(bizKeyValue);
                    returnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    if (returnInfo.getEntity() != null) {
                        bizKeyNames.add(returnInfo.getEntity().getRowCaption());
                        continue;
                    }
                    bizKeyNames.add(bizKeyValue);
                    continue;
                }
                bizKeyNames.add(bizKeyValue);
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
                ArrayList<CompareDifferenceItem> differenceItems = new ArrayList<CompareDifferenceItem>();
                naturalKeyCompareDifference.setUpdateItems(differenceItems);
                boolean different = false;
                for (int cellIndex = 0; cellIndex < cells.size(); ++cellIndex) {
                    String cell = cells.get(cellIndex);
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
                    LinkData link = jtableParamService.getLink(cell);
                    if (link.getType() != LinkType.LINK_TYPE_PICTURE.getValue() && link.getType() != LinkType.LINK_TYPE_FILE.getValue()) {
                        CompareDifferenceItem differenceItem2 = new CompareDifferenceItem();
                        initialValue = CompareDiffenceUtil.translateString(link, initialValue);
                        compareValue = CompareDiffenceUtil.translateString(link, compareValue);
                        differenceItem2.setInitialValue(initialValue);
                        differenceItem2.setCompareValue(compareValue);
                        String difference = CompareDiffenceUtil.compareDifference(link.getType(), initialValue, compareValue);
                        differenceItem2.setDifference(difference);
                        differenceItem2.setDataLinkKey(link.getKey());
                        if (StringUtils.isNotEmpty((String)link.getZbid())) {
                            differenceItem2.setFieldKey(link.getZbid());
                            differenceItem2.setFieldCode(link.getZbcode());
                            differenceItem2.setFieldTitle(link.getZbtitle());
                        }
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
            updateItems = new ArrayList<CompareDifferenceItem>();
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
            for (int i = 0; i < bizKeyLinks.size(); ++i) {
                bizKeyLink = bizKeyLinks.get(i);
                bizKeyValue = bizKeyValues.get(i);
                dataLink = regionRelationEvn.getDataLinkByKey(bizKeyLink);
                if (dataLink instanceof EnumLinkData) {
                    EnumLinkData enumLink = (EnumLinkData)dataLink;
                    String entityKey = enumLink.getEntityKey();
                    entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    entityQueryByKeyInfo.setContext(jtableContext);
                    entityQueryByKeyInfo.setEntityViewKey(entityKey);
                    entityQueryByKeyInfo.setDataLinkKey(bizKeyLink);
                    entityQueryByKeyInfo.setEntityKey(bizKeyValue);
                    returnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    if (returnInfo.getEntity() != null) {
                        bizKeyNames.add(returnInfo.getEntity().getRowCaption());
                        continue;
                    }
                    bizKeyNames.add(bizKeyValue);
                    continue;
                }
                bizKeyNames.add(bizKeyValue);
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

    public Map<String, List<Object>> getBizKeyDataMap(RegionData region, List<String> bizKeyLinks, RegionDataSet regionDataSet) {
        LinkedHashMap<String, List<Object>> bizKeyDataMap = new LinkedHashMap<String, List<Object>>();
        List<String> cells = regionDataSet.getCells().get(region.getKey().toString());
        List<List<Object>> rowDatas = regionDataSet.getData();
        for (List<Object> rowData : rowDatas) {
            StringBuffer bizKeyStrBuf = new StringBuffer();
            for (String bizKey : bizKeyLinks) {
                int bizKeyIndex = cells.indexOf(bizKey);
                String bizKeyValue = rowData.get(bizKeyIndex).toString();
                if (bizKeyStrBuf.length() > 0) {
                    bizKeyStrBuf.append("#^$");
                }
                bizKeyStrBuf.append(bizKeyValue);
            }
            bizKeyDataMap.put(bizKeyStrBuf.toString(), rowData);
        }
        return bizKeyDataMap;
    }

    private Map<String, Map<String, Object>> getBizKeyDataMapFile(RegionData region, List<String> bizKeyLinks, List<Map<String, Object>> rows, List<Map<String, String>> naturalFields) {
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
}

