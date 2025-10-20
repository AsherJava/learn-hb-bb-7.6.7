/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcStructureVchrItemUtil {
    public static List<GcOffSetVchrItemDTO> convert2offsetRecord(QueryParamsVO queryParamsVO, Map<ArrayKey, Double> combinedKey2offsetValueMap, ConsolidatedOptionVO optionVO, OffSetSrcTypeEnum srcTypeEnum) {
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        List manaAccFieldCodes = optionVO.getManagementAccountingFieldCodes();
        String diffUnitId = Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) ? null : GcStructureVchrItemUtil.getDiffUnitId(queryParamsVO);
        Map<String, String> fieldUpper2fieldMap = GcStructureVchrItemUtil.fieldUpper2fieldMap();
        for (Map.Entry<ArrayKey, Double> entry : combinedKey2offsetValueMap.entrySet()) {
            ArrayKey keys = entry.getKey();
            Double offsetValue = ConverterUtils.getAsDouble((Object)entry.getValue(), (Double)0.0);
            if (NumberUtils.isZreo((Double)offsetValue)) continue;
            List<GcOffSetVchrItemDTO> group = GcStructureVchrItemUtil.initOneGroup(queryParamsVO, diffUnitId, offsetValue, optionVO, srcTypeEnum);
            GcOffSetVchrItemDTO debitRecord = group.get(0);
            GcOffSetVchrItemDTO creditRecord = group.get(1);
            records.addAll(group);
            for (int i = 0; i < manaAccFieldCodes.size(); ++i) {
                String fieldName = fieldUpper2fieldMap.get(manaAccFieldCodes.get(i));
                if (null != fieldName) {
                    ReflectionUtils.setFieldValue((Object)debitRecord, (String)fieldName, (Object)keys.get(i));
                    ReflectionUtils.setFieldValue((Object)creditRecord, (String)fieldName, (Object)keys.get(i));
                    continue;
                }
                debitRecord.addFieldValue((String)manaAccFieldCodes.get(i), keys.get(i));
                creditRecord.addFieldValue((String)manaAccFieldCodes.get(i), keys.get(i));
            }
            if (!records.isEmpty()) continue;
            GcStructureVchrItemUtil.appendEmptyOffsetGroup(records, queryParamsVO, optionVO, diffUnitId, srcTypeEnum);
        }
        return records;
    }

    private static List<GcOffSetVchrItemDTO> initOneGroup(QueryParamsVO queryParamsVO, String diffUnitId, double offsetValue, ConsolidatedOptionVO optionVO, OffSetSrcTypeEnum srcTypeEnum) {
        ArrayList<GcOffSetVchrItemDTO> itemGroup = new ArrayList<GcOffSetVchrItemDTO>();
        String mrecid = UUIDOrderSnowUtils.newUUIDStr();
        GcOffSetVchrItemDTO debitRecord = GcStructureVchrItemUtil.initRecord(mrecid, queryParamsVO, diffUnitId, srcTypeEnum);
        debitRecord.setOffSetDebit(Double.valueOf(offsetValue));
        debitRecord.addFieldValue("ORIENT", (Object)OrientEnum.D.getValue());
        debitRecord.setSubjectCode(GcStructureVchrItemUtil.getDebitSubjectCode(offsetValue, srcTypeEnum, optionVO));
        itemGroup.add(debitRecord);
        GcOffSetVchrItemDTO creditRecord = GcStructureVchrItemUtil.initRecord(mrecid, queryParamsVO, diffUnitId, srcTypeEnum);
        creditRecord.setOffSetCredit(Double.valueOf(offsetValue));
        creditRecord.addFieldValue("ORIENT", (Object)OrientEnum.C.getValue());
        creditRecord.setSubjectCode(GcStructureVchrItemUtil.getCreditSubjectCode(offsetValue, srcTypeEnum, optionVO));
        itemGroup.add(creditRecord);
        return itemGroup;
    }

    private static String getDebitSubjectCode(double offsetValue, OffSetSrcTypeEnum srcTypeEnum, ConsolidatedOptionVO optionVO) {
        String debitSubjectCode = "";
        switch (srcTypeEnum) {
            case DEFERRED_INCOME_TAX: {
                debitSubjectCode = offsetValue >= 0.0 ? optionVO.getDiTax().getPositiveDebitSubject() : optionVO.getDiTax().getNegativeDebitSubject();
                break;
            }
            case MINORITY_LOSS_GAIN_RECOVERY: {
                debitSubjectCode = optionVO.getDiTax().getMinorityEquitySubject();
                break;
            }
            case BROUGHT_FORWARD_LOSS_GAIN: {
                debitSubjectCode = optionVO.getUndistributedProfitSubjectCode();
            }
        }
        return debitSubjectCode;
    }

    private static String getCreditSubjectCode(double offsetValue, OffSetSrcTypeEnum srcTypeEnum, ConsolidatedOptionVO optionVO) {
        String cebitSubjectCode = "";
        switch (srcTypeEnum) {
            case DEFERRED_INCOME_TAX: {
                cebitSubjectCode = offsetValue >= 0.0 ? optionVO.getDiTax().getPositiveCreditSubject() : optionVO.getDiTax().getNegativeCreditSubject();
                break;
            }
            case MINORITY_LOSS_GAIN_RECOVERY: {
                cebitSubjectCode = optionVO.getDiTax().getMinorityLossGainSubject();
                break;
            }
            case BROUGHT_FORWARD_LOSS_GAIN: {
                cebitSubjectCode = optionVO.getIntermediateSubjectCode();
            }
        }
        return cebitSubjectCode;
    }

    private static GcOffSetVchrItemDTO initRecord(String mrecid, QueryParamsVO arg, String diffUnitId, OffSetSrcTypeEnum srcTypeEnum) {
        String oppUnitId = arg.getOrgId();
        if (Boolean.TRUE.equals(arg.getArbitrarilyMerge())) {
            oppUnitId = (String)arg.getOrgIds().get(0);
            diffUnitId = (String)arg.getOrgIds().get(1);
        }
        GcOffSetVchrItemDTO record = new GcOffSetVchrItemDTO();
        record.setmRecid(mrecid);
        record.setAcctPeriod(arg.getAcctPeriod());
        record.setAcctYear(arg.getAcctYear());
        record.setDefaultPeriod(arg.getPeriodStr());
        record.setTaskId(arg.getTaskId());
        record.setSchemeId(arg.getSchemeId());
        record.setId(UUIDOrderUtils.newUUIDStr());
        record.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        record.setCreateTime(Calendar.getInstance().getTime());
        record.setOffSetSrcType(srcTypeEnum);
        record.setOffSetCurr(arg.getCurrencyUpperCase());
        record.setOrgType(arg.getOrgType());
        record.setUnitId(diffUnitId);
        record.setOppUnitId(oppUnitId);
        record.setMemo(srcTypeEnum.getSrcTypeName());
        record.setSelectAdjustCode(arg.getSelectAdjustCode());
        return record;
    }

    private static String getDiffUnitId(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = instance.getOrgByCode(queryParamsVO.getOrgId());
        String diffUnitId = hbOrg.getDiffUnitId();
        Assert.isNotNull((Object)diffUnitId, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.noDiffUnit", (Object[])new String[]{hbOrg.getTitle()}), (Object[])new Object[0]);
        return diffUnitId;
    }

    private static Map<String, String> fieldUpper2fieldMap() {
        Field[] declaredFields;
        HashMap<String, String> fieldMap = new HashMap<String, String>(32);
        for (Field field : declaredFields = GcOffSetVchrItemAdjustEO.class.getDeclaredFields()) {
            fieldMap.put(field.getName().toUpperCase(), field.getName());
        }
        return fieldMap;
    }

    private static void appendEmptyOffsetGroup(List<GcOffSetVchrItemDTO> records, QueryParamsVO queryParamsVO, ConsolidatedOptionVO optionVO, String diffUnitId, OffSetSrcTypeEnum srcTypeEnum) {
        String mrecid = UUIDOrderSnowUtils.newUUIDStr();
        GcOffSetVchrItemDTO debitRecord = GcStructureVchrItemUtil.initRecord(mrecid, queryParamsVO, diffUnitId, srcTypeEnum);
        debitRecord.setOffSetDebit(Double.valueOf(0.0));
        debitRecord.addFieldValue("ORIENT", (Object)OrientEnum.D.getValue());
        debitRecord.setSubjectCode(optionVO.getUndistributedProfitSubjectCode());
        records.add(debitRecord);
        GcOffSetVchrItemDTO creditRecord = GcStructureVchrItemUtil.initRecord(mrecid, queryParamsVO, diffUnitId, srcTypeEnum);
        creditRecord.setOffSetCredit(Double.valueOf(0.0));
        creditRecord.addFieldValue("ORIENT", (Object)OrientEnum.C.getValue());
        creditRecord.setSubjectCode(optionVO.getIntermediateSubjectCode());
        records.add(creditRecord);
    }

    public static ArrayKey getFieldValues(List<String> fieldCodes, MinorityRecoveryRowVO rowVO) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (String fieldCode : fieldCodes) {
            result.add(rowVO.getFieldValue(fieldCode));
        }
        return new ArrayKey(result);
    }
}

