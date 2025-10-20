/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.rate.client.dto.RateTypeVO
 *  com.jiuqi.common.rate.client.enums.ApplyRangeEnum
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.gcreport.rate.impl.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.rate.client.dto.RateTypeVO;
import com.jiuqi.common.rate.client.enums.ApplyRangeEnum;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

public class CommonRateUtils {
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

    public static List<RateTypeVO> getShowRateType(ApplyRangeEnum type) {
        List<Object> rateTypesList;
        if (ApplyRangeEnum.REPORT.equals((Object)type)) {
            rateTypesList = CommonRateUtils.getAllRateTypes().stream().filter(v -> {
                String applyRange;
                String string = applyRange = v.get((Object)"applyrange") != null ? v.get((Object)"applyrange").toString() : null;
                return applyRange == null || applyRange.equals(ApplyRangeEnum.REPORT.getCode()) || applyRange.equals(ApplyRangeEnum.ALL.getCode());
            }).collect(Collectors.toList());
        } else if (ApplyRangeEnum.ACCOUNT.equals((Object)type)) {
            rateTypesList = CommonRateUtils.getAllRateTypes().stream().filter(v -> {
                String applyRange;
                String string = applyRange = v.get((Object)"applyrange") != null ? v.get((Object)"applyrange").toString() : null;
                return applyRange == null || applyRange.equals(ApplyRangeEnum.ACCOUNT.getCode()) || applyRange.equals(ApplyRangeEnum.ALL.getCode());
            }).collect(Collectors.toList());
        } else {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u6b63\u786e\u7684\u6298\u7b97\u7c7b\u578b");
        }
        List<RateTypeVO> result = CommonRateUtils.changeRateType(rateTypesList);
        result.addAll(CommonRateUtils.getSegmentType(type));
        return result;
    }

    public static String getRateFormula(BaseDataDO rateTypeBdo) {
        String formula = ConverterUtils.getAsString((Object)rateTypeBdo.get((Object)"FORMULA"));
        if (ObjectUtils.isEmpty(formula)) {
            formula = ConverterUtils.getAsString((Object)rateTypeBdo.get((Object)"formula"));
        }
        return formula;
    }

    public static String getCurrencyTitle(String currencyCode) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_CURRENCY");
        baseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
        baseDataDTO.setCode(currencyCode);
        PageVO list = ((BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class)).list(baseDataDTO);
        if (list.getTotal() != 0) {
            return ((BaseDataDO)list.getRows().get(0)).getLocalizedName();
        }
        return currencyCode;
    }

    public static List<BaseDataDO> getAllNotVirtualRateTypes() {
        List<BaseDataDO> allRateTypes = CommonRateUtils.getAllRateTypes();
        List<BaseDataDO> rateTypes = allRateTypes.stream().filter(rateType -> {
            boolean isTrue;
            String formula = CommonRateUtils.getRateFormula(rateType);
            if (!ObjectUtils.isEmpty(formula)) {
                return false;
            }
            String rateTypeCode = rateType.getCode();
            RateTypeEnum rateTypeEnum = RateTypeEnum.getEnumByCode(rateTypeCode);
            if (rateTypeEnum == null) {
                return true;
            }
            switch (rateTypeEnum) {
                case FORMULA: 
                case NOTCONV: 
                case CALC: 
                case COPY: 
                case HIS: {
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

    public static List<RateTypeVO> getSegmentType(ApplyRangeEnum type) {
        List reportRateTypes = Stream.of(RateTypeEnum.SEGMENT_QC_LJ, RateTypeEnum.SEGMENT_QC_BN, RateTypeEnum.SEGMENT_PJ_LJ, RateTypeEnum.SEGMENT_PJ_BN, RateTypeEnum.SEGMENT_FORMULA_LJ, RateTypeEnum.SEGMENT_FORMULA_BN).collect(Collectors.toList());
        List accountRateTypes = Stream.of(RateTypeEnum.SEGMENT_PJ_BN).collect(Collectors.toList());
        ArrayList selectedRateTypes = new ArrayList();
        if (type.equals((Object)ApplyRangeEnum.REPORT)) {
            selectedRateTypes.addAll(reportRateTypes);
        } else if (type.equals((Object)ApplyRangeEnum.ACCOUNT)) {
            selectedRateTypes.addAll(accountRateTypes);
        } else {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u6b63\u786e\u7684\u6298\u7b97\u7c7b\u578b");
        }
        ArrayList<RateTypeVO> segmentTypeList = new ArrayList<RateTypeVO>();
        for (RateTypeEnum rateType : selectedRateTypes) {
            RateTypeVO rateTypeVO = new RateTypeVO();
            rateTypeVO.setName(rateType.getTitle());
            rateTypeVO.setCode(rateType.getCode());
            segmentTypeList.add(rateTypeVO);
        }
        return segmentTypeList;
    }

    public static BaseDataDO findByTitle(String headTitle, List<BaseDataDO> baseDataDOS) {
        for (BaseDataDO baseDataDO : baseDataDOS) {
            if (!baseDataDO.getName().equals(headTitle)) continue;
            return baseDataDO;
        }
        return null;
    }

    public static String getPeriodTypeTitle(String periodType) {
        String title = null;
        switch (periodType) {
            case "N": {
                title = "\u5e74";
                break;
            }
            case "H": {
                title = "\u534a\u5e74";
                break;
            }
            case "J": {
                title = "\u5b63";
                break;
            }
            case "Y": {
                title = "\u6708";
                break;
            }
            case "X": {
                title = "\u65ec";
                break;
            }
            case "R": {
                title = "\u65e5";
                break;
            }
            case "Z": {
                title = "\u5468";
                break;
            }
            default: {
                title = "\u4e0d\u5b9a\u671f";
            }
        }
        return title;
    }

    public static BaseDataDO findByCode(String rateTypeCode, List<BaseDataDO> rateTypeInfosCache) {
        Optional<BaseDataDO> rateTypeInfo = rateTypeInfosCache.stream().filter(rateType -> rateType.getCode().equals(rateTypeCode)).findFirst();
        if (!rateTypeInfo.isPresent()) {
            return null;
        }
        return rateTypeInfo.get();
    }

    public static int getRateValueFieldFractionDigits() {
        return CommonRateUtils.getRateValueFieldFractionDigits(null);
    }

    public static int getRateValueFieldFractionDigits(String rateTypeCode) {
        int rateValueFieldFractionDigits = 10;
        try {
            if (StringUtils.isEmpty((String)rateTypeCode)) {
                rateTypeCode = RateTypeEnum.QC.getCode();
            }
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine rateTableDefine = dataModelService.getTableModelDefineByName("MD_ENT_RATE");
            ColumnModelDefine rateValueField = dataModelService.getColumnModelDefineByCode(rateTableDefine.getID(), "RATETYPE_" + rateTypeCode);
            rateValueFieldFractionDigits = rateValueField.getDecimal();
            return rateValueFieldFractionDigits;
        }
        catch (Exception e) {
            e.printStackTrace();
            return rateValueFieldFractionDigits;
        }
    }

    public static BigDecimal formateRateValue(String rateValueStr) {
        BigDecimal rateValue;
        if (StringUtils.isEmpty((String)rateValueStr)) {
            return new BigDecimal(0);
        }
        try {
            rateValue = new BigDecimal(rateValueStr.replace(",", ""));
        }
        catch (NumberFormatException e) {
            throw new BusinessRuntimeException("\u6c47\u7387\u503c\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + rateValueStr);
        }
        return rateValue;
    }

    public static List<RateTypeVO> changeRateType(List<BaseDataDO> rateTypeList) {
        ArrayList<RateTypeVO> result = new ArrayList<RateTypeVO>();
        for (BaseDataDO rateType : rateTypeList) {
            if (rateType.getStopflag() == 1) continue;
            RateTypeVO dataDO = new RateTypeVO();
            if (rateType.getCode().equals(RateTypeEnum.COPY.getCode()) || rateType.getCode().equals(RateTypeEnum.NOTCONV.getCode()) || rateType.getCode().equals(RateTypeEnum.CALC.getCode())) {
                BeanUtils.copyProperties(rateType, dataDO);
                result.add(dataDO);
                continue;
            }
            BeanUtils.copyProperties(rateType, dataDO);
            dataDO.setName(rateType.getName() + "|\u76f4\u63a5\u6298\u7b97");
            dataDO.setCode(rateType.getCode() + "_01");
            result.add(dataDO);
        }
        return result;
    }

    public static void updateDefine(String tableName, List<DataModelColumn> addColumns) {
        DataModelClient dataModelClient = (DataModelClient)SpringContextUtils.getBean(DataModelClient.class);
        if (CollectionUtils.isEmpty(addColumns)) {
            return;
        }
        DataModelDTO dataModelFilter = new DataModelDTO();
        dataModelFilter.setName(tableName);
        DataModelDO oldDataModel = dataModelClient.get(dataModelFilter);
        Map oldColumns = oldDataModel.getColumns().stream().collect(Collectors.toMap(DataModelColumn::getColumnName, Function.identity()));
        List needAddColumns = addColumns.stream().filter(column -> !oldColumns.containsKey(column.getColumnName().toUpperCase()) && !oldColumns.containsKey(column.getColumnName().toLowerCase())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needAddColumns)) {
            return;
        }
        oldDataModel.getColumns().addAll(needAddColumns);
        dataModelClient.push(oldDataModel);
    }
}

