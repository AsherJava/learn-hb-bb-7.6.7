/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 */
package com.jiuqi.gcreport.workingpaper.utils;

import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.ArbitrarilyMergeOffSetVchrItemDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class ArbitrarilyMergeOffsetConvertUtil {
    private static ArbitrarilyMergeOffsetConvertUtil showOneUnitUtil;
    private static ArbitrarilyMergeOffsetConvertUtil showTwoUnitUtil;
    private boolean showOneUnit = true;

    private ArbitrarilyMergeOffsetConvertUtil() {
    }

    public static ArbitrarilyMergeOffsetConvertUtil getInstance() {
        if (null == showOneUnitUtil) {
            showOneUnitUtil = new ArbitrarilyMergeOffsetConvertUtil();
            ArbitrarilyMergeOffsetConvertUtil.showOneUnitUtil.showOneUnit = true;
        }
        return showOneUnitUtil;
    }

    public ArbitrarilyMergeOffSetVchrItemAdjustEO convertRyDTO2EO(ArbitrarilyMergeOffSetVchrItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        ArbitrarilyMergeOffSetVchrItemAdjustEO itemEo = new ArbitrarilyMergeOffSetVchrItemAdjustEO();
        HashMap fields = new HashMap();
        fields.putAll(itemDTO.getFields());
        BeanUtils.copyProperties((Object)itemDTO, (Object)itemEo);
        itemEo.resetFields(fields);
        itemEo.setDisableFlag(Boolean.TRUE.equals(itemDTO.getDisableFlag()) ? 1 : 0);
        if (null == itemEo.getSrcOffsetGroupId()) {
            itemEo.setSrcOffsetGroupId(itemEo.getmRecid());
        }
        Map<String, Object> unSysFields = itemDTO.getUnSysFields();
        for (Map.Entry<String, Object> unSysField : unSysFields.entrySet()) {
            if (unSysField.getValue() instanceof Map) {
                itemEo.addFieldValue(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                continue;
            }
            itemEo.addFieldValue(unSysField.getKey(), unSysField.getValue());
        }
        itemEo.setOffSetSrcType(OffSetSrcTypeEnum.getEnumValue((OffSetSrcTypeEnum)itemDTO.getOffSetSrcType()));
        String offSetCurr = itemDTO.getOffSetCurr().toUpperCase();
        if ("CNY".equals(offSetCurr) || "USD".equals(offSetCurr) || "HKD".equals(offSetCurr)) {
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("debit" + offSetCurr), (Object)itemDTO.getDebit());
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("credit" + offSetCurr), (Object)itemDTO.getCredit());
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("offSetCredit" + offSetCurr), (Object)itemDTO.getOffSetCredit());
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("offSetDebit" + offSetCurr), (Object)itemDTO.getOffSetDebit());
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("diffc" + offSetCurr), (Object)itemDTO.getDiffc());
            ReflectionUtils.setFieldValue((Object)((Object)itemEo), (String)("diffd" + offSetCurr), (Object)itemDTO.getDiffd());
        } else {
            itemEo.addFieldValue("DEBIT_" + offSetCurr, itemDTO.getDebit());
            itemEo.addFieldValue("CREDIT_" + offSetCurr, itemDTO.getCredit());
            itemEo.addFieldValue("OFFSETCREDIT_" + offSetCurr, itemDTO.getOffSetCredit());
            itemEo.addFieldValue("OFFSETDEBIT_" + offSetCurr, itemDTO.getOffSetDebit());
            itemEo.addFieldValue("DIFFC_" + offSetCurr, itemDTO.getDiffc());
            itemEo.addFieldValue("DIFFD_" + offSetCurr, itemDTO.getDiffd());
        }
        this.initNullValue(itemEo);
        if (itemEo.getDisableFlag() == null) {
            itemEo.setDisableFlag(0);
        }
        itemEo.setSubjectOrient(itemDTO.getSubjectOrient().getValue());
        itemEo.setOrient(itemDTO.getOrient().getValue());
        itemEo.setAdjust(itemDTO.getSelectAdjustCode());
        return itemEo;
    }

    private void initNullValue(ArbitrarilyMergeOffSetVchrItemAdjustEO item) {
        if (item.getDebitCNY() == null) {
            item.setDebitCNY(0.0);
        }
        if (item.getDebitHKD() == null) {
            item.setDebitHKD(0.0);
        }
        if (item.getDebitUSD() == null) {
            item.setDebitUSD(0.0);
        }
        if (item.getOffSetDebitCNY() == null) {
            item.setOffSetDebitCNY(0.0);
        }
        if (item.getOffSetDebitHKD() == null) {
            item.setOffSetDebitHKD(0.0);
        }
        if (item.getOffSetDebitUSD() == null) {
            item.setOffSetDebitUSD(0.0);
        }
        if (item.getDiffdCNY() == null) {
            item.setDiffdCNY(0.0);
        }
        if (item.getDiffdHKD() == null) {
            item.setDiffdHKD(0.0);
        }
        if (item.getDiffdUSD() == null) {
            item.setDiffdUSD(0.0);
        }
        if (item.getCreditCNY() == null) {
            item.setCreditCNY(0.0);
        }
        if (item.getCreditHKD() == null) {
            item.setCreditHKD(0.0);
        }
        if (item.getCreditUSD() == null) {
            item.setCreditUSD(0.0);
        }
        if (item.getOffSetCreditCNY() == null) {
            item.setOffSetCreditCNY(0.0);
        }
        if (item.getOffSetCreditHKD() == null) {
            item.setOffSetCreditHKD(0.0);
        }
        if (item.getOffSetCreditUSD() == null) {
            item.setOffSetCreditUSD(0.0);
        }
        if (item.getDiffcCNY() == null) {
            item.setDiffcCNY(0.0);
        }
        if (item.getDiffcHKD() == null) {
            item.setDiffcHKD(0.0);
        }
        if (item.getDiffcUSD() == null) {
            item.setDiffcUSD(0.0);
        }
    }
}

