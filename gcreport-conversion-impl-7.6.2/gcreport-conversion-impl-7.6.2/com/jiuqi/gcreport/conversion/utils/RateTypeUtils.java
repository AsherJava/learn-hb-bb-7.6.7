/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.conversion.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;

public class RateTypeUtils {
    public static List<BaseDataDO> getAllRateTypes() {
        BaseDataClient baseDataClient = (BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class);
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_RATETYPE");
        PageVO pageVO = baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return Collections.emptyList();
        }
        List rateTypeList = pageVO.getRows();
        if (CollectionUtils.isEmpty((Collection)rateTypeList)) {
            return Collections.emptyList();
        }
        List<BaseDataDO> rateTypes = rateTypeList.stream().filter(rateType -> {
            Object stopflag = rateType.get((Object)"stopflag");
            Boolean stopFlag = ConverterUtils.getAsBoolean((Object)stopflag, (Boolean)false);
            return stopFlag == false;
        }).collect(Collectors.toList());
        return rateTypes;
    }

    public static List<BaseDataVO> getAllRateTypesContainsStopFlag() {
        List rateTypeList = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO("MD_RATETYPE");
        if (CollectionUtils.isEmpty((Collection)rateTypeList)) {
            return Collections.emptyList();
        }
        return rateTypeList;
    }

    public static List<BaseDataDO> getAllNotVirtualRateTypes() {
        List<BaseDataDO> allRateTypes = RateTypeUtils.getAllRateTypes();
        List<BaseDataDO> rateTypes = allRateTypes.stream().filter(rateType -> {
            boolean isTrue;
            String formula = RateTypeUtils.getRateFormula(rateType);
            if (!ObjectUtils.isEmpty(formula)) {
                return false;
            }
            String rateTypeCode = rateType.getCode();
            RateTypeEnum rateTypeEnum = RateTypeEnum.getEnumByCode((String)rateTypeCode);
            if (rateTypeEnum == null) {
                return true;
            }
            switch (rateTypeEnum) {
                case SEGMENT_FORMULA_BN: 
                case SEGMENT_FORMULA_LJ: 
                case SEGMENT_PJ_BN: 
                case SEGMENT_PJ_LJ: 
                case SEGMENT_QC_BN: 
                case SEGMENT_QC_LJ: 
                case FORMULA: 
                case NOTCONV: {
                    isTrue = false;
                    break;
                }
                default: {
                    isTrue = true;
                }
            }
            return isTrue;
        }).collect(Collectors.toList());
        return rateTypes;
    }

    public static boolean isSegmentRateType(String rateTypeCode) {
        boolean isSegmentRateType;
        RateTypeEnum rateTypeEnum = RateTypeEnum.getEnumByCode((String)rateTypeCode);
        if (rateTypeEnum == null) {
            return false;
        }
        switch (rateTypeEnum) {
            case SEGMENT_FORMULA_BN: 
            case SEGMENT_FORMULA_LJ: 
            case SEGMENT_PJ_BN: 
            case SEGMENT_PJ_LJ: 
            case SEGMENT_QC_BN: 
            case SEGMENT_QC_LJ: {
                isSegmentRateType = true;
                break;
            }
            default: {
                isSegmentRateType = false;
            }
        }
        return isSegmentRateType;
    }

    public static GcBaseData findByCode(String rateTypeCode) {
        return GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RATETYPE", rateTypeCode);
    }

    public static BaseDataDO findByCode(String rateTypeCode, List<BaseDataDO> rateTypeInfosCache) {
        Optional<BaseDataDO> rateTypeInfo = rateTypeInfosCache.stream().filter(rateType -> rateType.getCode().equals(rateTypeCode)).findFirst();
        if (!rateTypeInfo.isPresent()) {
            return null;
        }
        return rateTypeInfo.get();
    }

    public static BaseDataDO findByTitle(String rateTypeTitle) {
        List<BaseDataDO> rateTypes = RateTypeUtils.getAllRateTypes();
        Optional<BaseDataDO> rateTypeInfo = rateTypes.stream().filter(rateType -> rateType.getName().equals(rateTypeTitle)).findFirst();
        if (!rateTypeInfo.isPresent()) {
            return null;
        }
        return rateTypeInfo.get();
    }

    public static BaseDataDO findByTitle(String rateTypeTitle, List<BaseDataDO> rateTypeInfosCache) {
        Optional<BaseDataDO> rateTypeInfo = rateTypeInfosCache.stream().filter(rateType -> rateType.getName().equals(rateTypeTitle)).findFirst();
        if (!rateTypeInfo.isPresent()) {
            return null;
        }
        return rateTypeInfo.get();
    }

    public static String getRateFormula(BaseDataDO rateTypeBdo) {
        String formula = ConverterUtils.getAsString((Object)rateTypeBdo.get((Object)"FORMULA"));
        if (ObjectUtils.isEmpty(formula)) {
            formula = ConverterUtils.getAsString((Object)rateTypeBdo.get((Object)"formula"));
        }
        return formula;
    }

    public static String getRateFormula(GcBaseData rateTypeBdo) {
        String formula = ConverterUtils.getAsString((Object)rateTypeBdo.getFieldVal("FORMULA"));
        if (ObjectUtils.isEmpty(formula)) {
            formula = ConverterUtils.getAsString((Object)rateTypeBdo.getFieldVal("formula"));
        }
        return formula;
    }
}

