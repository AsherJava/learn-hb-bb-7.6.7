/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.common.rate.client.vo.RateQueryParam
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.rate.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.RateQueryParam;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateDao;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateSchemeDao;
import com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO;
import com.jiuqi.gcreport.rate.impl.dto.ConvertParam;
import com.jiuqi.gcreport.rate.impl.dto.RateBatchData;
import com.jiuqi.gcreport.rate.impl.dto.RateBatchParam;
import com.jiuqi.gcreport.rate.impl.entity.CommonRateInfoEO;
import com.jiuqi.gcreport.rate.impl.enums.AmountTypeEnum;
import com.jiuqi.gcreport.rate.impl.enums.ConversionTypeEnum;
import com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonRateServiceImpl
implements CommonRateService {
    @Autowired
    private CommonRateDao commonRateDao;
    @Autowired
    private CommonRateSchemeDao commonRateSchemeDao;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private RateSchemeMapper mapper;

    @Override
    public PageInfo<CommonRateInfoVO> queryRateList(RateQueryParam rateQueryParam) {
        PageInfo<CommonRateInfoEO> eos = this.commonRateDao.queryRateList(rateQueryParam.getPeriodStrStart(), rateQueryParam.getPeriodStrEnd(), rateQueryParam.getSourceCurrencyCode(), rateQueryParam.getTargetCurrencyCode(), rateQueryParam.getRateSchemeCode(), rateQueryParam.getPageSize(), rateQueryParam.getPageNum());
        return PageInfo.of(eos.getList().stream().map(this::covertEO2VO).collect(Collectors.toList()), (int)eos.getSize());
    }

    @Override
    public CommonRateInfoVO queryRateInfo(String rateSchemeCode, String dataTime, String sourceCurrencyCode, String targetCurrencyCode) {
        if (StringUtils.isEmpty((String)rateSchemeCode)) {
            rateSchemeCode = "DEFAULT";
        }
        CommonRateInfoEO eo = this.commonRateDao.queryRateInfo(rateSchemeCode, dataTime, sourceCurrencyCode, targetCurrencyCode);
        return this.covertEO2VO(eo);
    }

    @Override
    public List<CommonRateInfoVO> queryRateByYear(String rateSchemeCode, int year, String sourceCurrencyCode, String targetCurrencyCode) {
        String yearStr = String.valueOf(year);
        if (yearStr.length() != 4) {
            throw new BusinessRuntimeException("\u5e74\u5ea6\u4e0d\u6b63\u786e");
        }
        String dataTime = yearStr + "Y0012";
        return this.queryRateByDataTime(rateSchemeCode, dataTime, sourceCurrencyCode, targetCurrencyCode);
    }

    @Override
    public Collection<RateBatchData> queryRateBySubject(RateBatchParam param) {
        if (param.getAcctPeriod() == null) {
            param.setAcctPeriod("0");
        }
        List<CommonRateInfoVO> rateInfoVOS = this.queryRateByYear(null, Integer.parseInt(param.getAcctYear()), param.getSourceCurrencyCode(), param.getTargetCurrencyCode());
        Map<String, CommonRateInfoVO> rateInfoVOMap = rateInfoVOS.stream().collect(Collectors.toMap(CommonRateInfoVO::getDataTime, scheme -> scheme, (k1, k2) -> k2));
        ArrayList<RateBatchData> dataList = new ArrayList<RateBatchData>();
        List<String> subjectCodes = param.getSubjectCode();
        List schemes = this.mapper.select((Object)new ConvertRateSchemeDO());
        Map<String, ConvertRateSchemeDO> schemeMap = schemes.stream().collect(Collectors.toMap(ConvertRateSchemeDO::getSubjectCode, scheme -> scheme, (k1, k2) -> k2));
        Map<String, SubjectDTO> subjectMap = this.subjectService.list(new BaseDataDTO()).stream().collect(Collectors.toMap(BaseDataDO::getCode, subject -> subject, (k1, k2) -> k2));
        for (int period = Integer.parseInt(param.getAcctPeriod()); period < 13; ++period) {
            String dataTimeStr;
            RateBatchData data = new RateBatchData(param.getSourceCurrencyCode(), param.getTargetCurrencyCode(), param.getAcctYear(), String.valueOf(period));
            if (period == 0) {
                dataTimeStr = data.getAcctYear() + "Y0001";
            } else {
                String periodValue = data.getAcctPeriod().length() == 2 ? "00" + data.getAcctPeriod() : "000" + data.getAcctPeriod();
                dataTimeStr = data.getAcctYear() + "Y" + periodValue;
            }
            subjectCodes.forEach(subjectCode -> {
                CommonRateInfoVO rateInfoVO = (CommonRateInfoVO)rateInfoVOMap.get(dataTimeStr);
                ConvertRateSchemeDO rateSchemeDO = this.getConverRateSchemeBySubject((String)subjectCode, schemeMap, subjectMap);
                data.addParam((String)subjectCode, this.getSubjectRateValue(data.getAcctPeriod(), rateInfoVO, rateSchemeDO));
            });
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public List<CommonRateInfoVO> queryRateByDataTime(String rateSchemeCode, String dataTime, String sourceCurrencyCode, String targetCurrencyCode) {
        if (StringUtils.isEmpty((String)rateSchemeCode)) {
            rateSchemeCode = "DEFAULT";
        }
        String periodStart = dataTime.substring(0, 5) + "0000";
        List eos = this.commonRateDao.queryRateList(periodStart, dataTime, sourceCurrencyCode, targetCurrencyCode, rateSchemeCode, 100000, 1).getList();
        return eos.stream().map(this::covertEO2VO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean saveRates(List<CommonRateInfoVO> rateitemEOList) {
        HashMap map = new HashMap();
        rateitemEOList.forEach(vo -> {
            String rateSchemeCode = vo.getRateSchemeCode();
            List<CommonRateInfoEO> eos = new ArrayList();
            if (map.containsKey(rateSchemeCode)) {
                eos = (List)map.get(rateSchemeCode);
            } else {
                map.put(rateSchemeCode, eos);
            }
            CommonRateInfoEO eo = new CommonRateInfoEO();
            BeanUtils.copyProperties(vo, eo);
            eos.add(eo);
        });
        for (String key : map.keySet()) {
            List saveEOList = (List)map.get(key);
            String rateSchemeCode = this.commonRateSchemeDao.getRateSchemeByCode(key).getCode();
            HashMap checkCode2EO = new HashMap();
            saveEOList.forEach(item -> this.checkValidity(rateSchemeCode, checkCode2EO, (CommonRateInfoEO)item));
            this.commonRateDao.saveRates(rateSchemeCode, (List)map.get(key));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteRateInfo(List<CommonRateInfoVO> rateitemEOList) {
        List<String> ids = rateitemEOList.stream().filter(v -> !StringUtils.isEmpty((String)v.getId())).map(CommonRateInfoVO::getId).collect(Collectors.toList());
        return this.commonRateDao.deleteRateInfos(ids);
    }

    private CommonRateInfoVO covertEO2VO(CommonRateInfoEO eo) {
        if (eo == null) {
            return null;
        }
        CommonRateInfoVO vo = new CommonRateInfoVO();
        BeanUtils.copyProperties(eo, vo);
        vo.setSourceCurrencyTitle(CommonRateUtils.getCurrencyTitle(eo.getSourceCurrencyCode()));
        vo.setTargetCurrencyTitle(CommonRateUtils.getCurrencyTitle(eo.getTargetCurrencyCode()));
        return vo;
    }

    private void checkValidity(String rateSchemeCode, Map<String, CommonRateInfoEO> checkCode2EO, CommonRateInfoEO itemEO) {
        CommonRateInfoVO rateEoList;
        if (itemEO.getRateSchemeCode() == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.rate.schemeId.notnull.error"));
        }
        if (StringUtils.isEmpty((String)itemEO.getDataTime())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.rate.dataTime.notnull.error"));
        }
        if (StringUtils.isEmpty((String)itemEO.getSourceCurrencyCode())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.rate.srcCurrency.notnull.error"));
        }
        if (StringUtils.isEmpty((String)itemEO.getTargetCurrencyCode())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.rate.targetCurrency.notnull.error"));
        }
        List<BaseDataDO> rateTypeList = CommonRateUtils.getAllRateTypes();
        Map<String, String> codeToTitleMap = rateTypeList.stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getLocalizedName));
        Map<String, BigDecimal> rateItems = itemEO.getRateInfo();
        for (String key : rateItems.keySet()) {
            if (rateItems.get(key) != null) continue;
            throw new BusinessRuntimeException(codeToTitleMap.get(key) + GcI18nUtil.getMessage((String)"gc.common.rate.value.notnull.error"));
        }
        String key = rateSchemeCode + "_" + itemEO.getDataTime() + "-" + itemEO.getSourceCurrencyCode() + "-" + itemEO.getTargetCurrencyCode();
        if (checkCode2EO.containsKey(key)) {
            String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(itemEO.getDataTime());
            StringBuilder errorMsg = new StringBuilder().append(periodTitle).append(CommonRateUtils.getCurrencyTitle(itemEO.getSourceCurrencyCode())).append("-").append(CommonRateUtils.getCurrencyTitle(itemEO.getTargetCurrencyCode())).append("\u7684\u6c47\u7387\u4fe1\u606f\u5df2\u7ecf\u5b58\u5728\uff01");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
        checkCode2EO.put(key, itemEO);
        itemEO.setCode(key);
        itemEO.setName(key);
        if (StringUtils.isEmpty((String)itemEO.getId()) && (rateEoList = this.queryRateInfo(rateSchemeCode, itemEO.getDataTime(), itemEO.getSourceCurrencyCode(), itemEO.getTargetCurrencyCode())) != null) {
            String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(itemEO.getDataTime());
            StringBuilder errorMsg = new StringBuilder().append(periodTitle).append(CommonRateUtils.getCurrencyTitle(itemEO.getSourceCurrencyCode())).append("-").append(CommonRateUtils.getCurrencyTitle(itemEO.getTargetCurrencyCode())).append("\u7684\u6c47\u7387\u4fe1\u606f\u5df2\u7ecf\u5b58\u5728\uff01");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
    }

    @Override
    public List<BaseDataDO> getNeedSetValueRateType() {
        return CommonRateUtils.getAllNotVirtualRateTypes();
    }

    private List<ConvertParam> getSubjectRateValue(String period, CommonRateInfoVO rateInfoVO, ConvertRateSchemeDO rateSchemeDO) {
        if (rateSchemeDO == null) {
            return null;
        }
        ArrayList<ConvertParam> convertParamList = new ArrayList<ConvertParam>();
        if (period.equals("0")) {
            convertParamList.add(this.getConverParam(AmountTypeEnum.TYPE_NC, rateInfoVO, rateSchemeDO.getBfRateType()));
        } else {
            convertParamList.add(this.getConverParam(AmountTypeEnum.TYPE_QC, rateInfoVO, rateSchemeDO.getQcRateType()));
            convertParamList.add(this.getConverParam(AmountTypeEnum.TYPE_BQ, rateInfoVO, rateSchemeDO.getBqRateType()));
            convertParamList.add(this.getConverParam(AmountTypeEnum.TYPE_BN, rateInfoVO, rateSchemeDO.getSumRateType()));
            convertParamList.add(this.getConverParam(AmountTypeEnum.TYPE_QM, rateInfoVO, rateSchemeDO.getCfRateType()));
        }
        return convertParamList;
    }

    private ConvertParam getConverParam(AmountTypeEnum type, CommonRateInfoVO rateInfoVO, String rateType) {
        String rateCode;
        ConvertParam convertParam = new ConvertParam();
        convertParam.setAmountType(type);
        String conversionType = null;
        int lastIndex = rateType.lastIndexOf(95);
        if (lastIndex != -1) {
            rateCode = rateType.substring(0, lastIndex);
            conversionType = rateType.substring(lastIndex + 1);
        } else {
            rateCode = rateType;
        }
        Map value = rateInfoVO == null ? new HashMap() : rateInfoVO.getRateInfo();
        convertParam.setRateCode(rateCode);
        convertParam.setConversionTypeEnum(conversionType == null ? ConversionTypeEnum.DIRECT : ConversionTypeEnum.getByCode(conversionType));
        convertParam.setRateValue(value.get(rateCode) == null ? BigDecimal.ONE : (BigDecimal)value.get(rateCode));
        return convertParam;
    }

    private ConvertRateSchemeDO getConverRateSchemeBySubject(String subjectCode, Map<String, ConvertRateSchemeDO> rateSchemeDOMap, Map<String, SubjectDTO> subjectMap) {
        if (StringUtils.isEmpty((String)subjectCode)) {
            return new ConvertRateSchemeDO();
        }
        if (rateSchemeDOMap.containsKey(subjectCode)) {
            return rateSchemeDOMap.get(subjectCode);
        }
        SubjectDTO subjectDTO = subjectMap.get(subjectCode);
        if (subjectDTO == null) {
            return null;
        }
        return this.getConverRateSchemeBySubject(subjectDTO.getParentcode(), rateSchemeDOMap, subjectMap);
    }
}

