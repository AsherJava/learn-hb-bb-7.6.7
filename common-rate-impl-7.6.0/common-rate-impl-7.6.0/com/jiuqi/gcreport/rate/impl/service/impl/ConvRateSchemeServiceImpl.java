/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO
 *  com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO
 *  com.jiuqi.common.rate.client.vo.SubjectShowVO
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.rate.impl.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO;
import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import com.jiuqi.common.rate.client.vo.SubjectShowVO;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO;
import com.jiuqi.gcreport.rate.impl.dto.ConvRateSchemeQueryDTO;
import com.jiuqi.gcreport.rate.impl.enums.RateFunctionModuleEnum;
import com.jiuqi.gcreport.rate.impl.exception.RateSchemeSaveException;
import com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper;
import com.jiuqi.gcreport.rate.impl.service.ConvertRateSchemeService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConvRateSchemeServiceImpl
implements ConvertRateSchemeService {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private RateSchemeMapper mapper;

    @Override
    public PageVO<ConvertRateSchemeVO> query(ConvertRateSchemeDTO param) {
        List schemes = this.mapper.select((Object)new ConvertRateSchemeDO());
        Collections.sort(schemes, Comparator.comparing(ConvertRateSchemeDO::getSubjectCode));
        Map<String, SubjectDTO> subjMap = this.subjectService.list(new BaseDataDTO()).stream().collect(Collectors.toMap(BaseDataDO::getCode, subj -> subj, (k1, k2) -> k2));
        LinkedHashMap<String, ConvertRateSchemeVO> schemeRowMap = new LinkedHashMap<String, ConvertRateSchemeVO>(16);
        for (ConvertRateSchemeDO scheme : schemes) {
            if (schemeRowMap.containsKey(scheme.getRowDataId())) {
                ConvertRateSchemeVO schemeVO = (ConvertRateSchemeVO)schemeRowMap.get(scheme.getRowDataId());
                String subjCode = scheme.getSubjectCode();
                if (!subjMap.containsKey(subjCode)) continue;
                schemeVO.setSubjectCode(String.format("%1$s;%2$s", schemeVO.getSubjectCode(), scheme.getSubjectCode()));
                SubjectShowVO subjVO = new SubjectShowVO();
                subjVO.setCode(subjCode);
                subjVO.setLabel(subjMap.get(subjCode).getName());
                subjVO.setTitle(subjMap.get(subjCode).getName());
                subjVO.setCurrency(subjMap.get(subjCode).getCurrency());
                schemeVO.getSubject().add(subjVO);
                continue;
            }
            schemeRowMap.put(scheme.getRowDataId(), this.convert2VO(scheme, subjMap));
        }
        List retrunSchemes = schemeRowMap.values().stream().skip((param.getPage() - 1) * param.getPageSize()).limit(param.getPageSize().intValue()).collect(Collectors.toList());
        PageVO result = new PageVO(retrunSchemes, schemeRowMap.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageVO<ConvertRateSchemeVO> save(ConvertRateSchemeDTO param) {
        this.insertScheme(param);
        this.updateScheme(param);
        this.deleteScheme(param);
        return this.query(param);
    }

    private void insertScheme(ConvertRateSchemeDTO param) {
        ArrayList<ConvertRateSchemeDO> insertSchemes = new ArrayList<ConvertRateSchemeDO>();
        for (ConvertRateSchemeVO scheme : param.getSaveDatas()) {
            if (!StringUtils.isEmpty((String)scheme.getId())) continue;
            String rowId = UUIDUtils.newUUIDStr();
            if (scheme.getSubjectCode().indexOf(";") > -1) {
                for (String subjCode : scheme.getSubjectCode().split(";")) {
                    ConvertRateSchemeDO schemeDO = new ConvertRateSchemeDO(scheme);
                    schemeDO.setId(UUIDUtils.newUUIDStr());
                    schemeDO.setVer(0L);
                    schemeDO.setRowDataId(rowId);
                    schemeDO.setSubjectCode(subjCode);
                    insertSchemes.add(schemeDO);
                }
                continue;
            }
            ConvertRateSchemeDO schemeDO = new ConvertRateSchemeDO(scheme);
            schemeDO.setId(UUIDUtils.newUUIDStr());
            schemeDO.setVer(0L);
            schemeDO.setRowDataId(rowId);
            insertSchemes.add(schemeDO);
        }
        if (insertSchemes.isEmpty()) {
            return;
        }
        for (ConvertRateSchemeDO schemeDO : insertSchemes) {
            ConvertRateSchemeDO sdo = new ConvertRateSchemeDO();
            sdo.setSubjectCode(schemeDO.getSubjectCode());
            List rst = this.mapper.select((Object)sdo);
            if (rst != null && rst.size() > 0) {
                throw new RateSchemeSaveException(String.format("\u65b0\u589e\u65b9\u6848\u5931\u8d25\uff0c\u79d1\u76ee\u4ee3\u7801\uff1a%1$s \u5df2\u5b58\u5728\u65b9\u6848\u3002", schemeDO.getSubjectCode()));
            }
            if (this.mapper.insert((Object)schemeDO) != 0) continue;
            throw new RateSchemeSaveException(String.format("\u65b0\u589e\u65b9\u6848\u5931\u8d25\uff0c\u79d1\u76ee\u4ee3\u7801\uff1a%1$s", schemeDO.getSubjectCode()));
        }
        LogHelper.info((String)RateFunctionModuleEnum.RATESCHEMESETTINGS.getFullModuleName(), (String)"\u65b0\u589e-\u6c47\u7387\u65b9\u6848", (String)insertSchemes.stream().map(Object::toString).collect(Collectors.joining(";")));
    }

    private void updateScheme(ConvertRateSchemeDTO param) {
        ArrayList<ConvertRateSchemeDO> updateSchemes = new ArrayList<ConvertRateSchemeDO>();
        for (ConvertRateSchemeVO scheme : param.getSaveDatas()) {
            if (StringUtils.isEmpty((String)scheme.getId())) continue;
            String rowId = scheme.getRowDataId();
            if (rowId == null) {
                throw new RateSchemeSaveException(String.format("\u66f4\u65b0\u65b9\u6848\u5931\u8d25\uff0c\u65b9\u6848ID\uff1a%1$s \u7248\u672cVER\uff1a%2$s", scheme.getRowDataId(), String.valueOf(scheme.getVer())));
            }
            if (scheme.getSubjectCode().indexOf(";") > -1) {
                for (String subjCode : scheme.getSubjectCode().split(";")) {
                    ConvertRateSchemeDO schemeDO = new ConvertRateSchemeDO(scheme);
                    schemeDO.setId(UUIDUtils.newUUIDStr());
                    schemeDO.setVer(scheme.getVer() + 1L);
                    schemeDO.setRowDataId(rowId);
                    schemeDO.setSubjectCode(subjCode);
                    updateSchemes.add(schemeDO);
                }
                continue;
            }
            ConvertRateSchemeDO schemeDO = new ConvertRateSchemeDO(scheme);
            schemeDO.setId(UUIDUtils.newUUIDStr());
            schemeDO.setVer(scheme.getVer() + 1L);
            schemeDO.setRowDataId(rowId);
            updateSchemes.add(schemeDO);
        }
        if (updateSchemes.isEmpty()) {
            return;
        }
        for (ConvertRateSchemeVO scheme : param.getSaveDatas()) {
            ConvertRateSchemeDO deleteParam = new ConvertRateSchemeDO();
            deleteParam.setRowDataId(scheme.getRowDataId());
            this.mapper.delete((Object)deleteParam);
        }
        for (ConvertRateSchemeDO schemeDO : updateSchemes) {
            if (this.mapper.insert((Object)schemeDO) != 0) continue;
            throw new RateSchemeSaveException(String.format("\u66f4\u65b0\u65b9\u6848\u5931\u8d25\uff0c\u65b9\u6848ID\uff1a%1$s \u7248\u672cVER\uff1a%2$s", schemeDO.getRowDataId(), String.valueOf(schemeDO.getVer())));
        }
        LogHelper.info((String)RateFunctionModuleEnum.RATESCHEMESETTINGS.getFullModuleName(), (String)"\u4fee\u6539-\u6c47\u7387\u65b9\u6848", (String)updateSchemes.stream().map(Object::toString).collect(Collectors.joining(";")));
    }

    private void deleteScheme(ConvertRateSchemeDTO param) {
        if (param.getDeleteDatas() == null || param.getDeleteDatas().isEmpty()) {
            return;
        }
        for (ConvertRateSchemeVO scheme : param.getDeleteDatas()) {
            if (scheme.getRowDataId() == null) {
                throw new RateSchemeSaveException(String.format("\u5220\u9664\u65b9\u6848\u5931\u8d25\uff0c\u65b9\u6848ID\uff1a%1$s \u7248\u672cVER\uff1a%2$s", scheme.getRowDataId(), String.valueOf(scheme.getVer())));
            }
            ConvertRateSchemeDO deleteParam = new ConvertRateSchemeDO();
            deleteParam.setRowDataId(scheme.getRowDataId());
            this.mapper.delete((Object)deleteParam);
        }
        LogHelper.info((String)RateFunctionModuleEnum.RATESCHEMESETTINGS.getFullModuleName(), (String)"\u5220\u9664-\u6c47\u7387\u65b9\u6848", (String)param.getDeleteDatas().stream().map(Object::toString).collect(Collectors.joining(";")));
    }

    @Override
    public Map<String, ConvertRateSchemeVO> getBySubjectCodes(List<String> subjCodeParams) {
        LinkedHashMap<String, ConvertRateSchemeVO> schemeVoSubjMap = new LinkedHashMap<String, ConvertRateSchemeVO>(16);
        if (subjCodeParams == null || subjCodeParams.isEmpty()) {
            return schemeVoSubjMap;
        }
        Map<String, SubjectDTO> subjectMap = this.subjectService.list(new BaseDataDTO()).stream().collect(Collectors.toMap(BaseDataDO::getCode, subject -> subject, (k1, k2) -> k2));
        ArrayList<String> subjectCodes = new ArrayList<String>(subjCodeParams);
        for (String subjectCode : subjCodeParams) {
            this.getParentSubjCode(subjectCodes, subjectMap, subjectCode);
        }
        ConvRateSchemeQueryDTO param = new ConvRateSchemeQueryDTO();
        Collections.sort(subjCodeParams);
        param.setSubjectCodes(subjectCodes);
        List<ConvertRateSchemeDO> schemes = this.mapper.getSchemeBySubjectCode(param);
        Map<String, ConvertRateSchemeDO> schemeDoSubjMap = schemes.stream().collect(Collectors.toMap(ConvertRateSchemeDO::getSubjectCode, scheme -> scheme, (k1, k2) -> k2));
        for (String subjectCode : subjCodeParams) {
            ConvertRateSchemeVO schemeVO = this.getSchemeBySubjCode(schemeDoSubjMap, subjectMap, subjectCode);
            if (schemeVO != null) {
                SubjectShowVO subjVO = new SubjectShowVO();
                subjVO.setCode(subjectCode);
                if (subjectMap.get(subjectCode) != null) {
                    subjVO.setLabel(subjectMap.get(subjectCode).getName());
                    subjVO.setTitle(subjectMap.get(subjectCode).getName());
                    subjVO.setCurrency(subjectMap.get(subjectCode).getCurrency());
                }
                ArrayList<SubjectShowVO> subjList = new ArrayList<SubjectShowVO>();
                subjList.add(subjVO);
                schemeVO.setSubject(subjList);
            }
            schemeVoSubjMap.put(subjectCode, schemeVO);
        }
        return schemeVoSubjMap;
    }

    private ConvertRateSchemeVO getSchemeBySubjCode(Map<String, ConvertRateSchemeDO> schemeDoSubjMap, Map<String, SubjectDTO> subjectMap, String subjCode) {
        if (schemeDoSubjMap.containsKey(subjCode)) {
            ConvertRateSchemeDO data = schemeDoSubjMap.get(subjCode);
            return new ConvertRateSchemeVO(data.getId(), data.getVer(), data.getRowDataId(), data.getSubjectCode(), data.getBfRateType(), data.getQcRateType(), data.getBqRateType(), data.getSumRateType(), data.getCfRateType(), data.getNextYearAdjustRateType());
        }
        SubjectDTO subject = subjectMap.get(subjCode);
        if (subject == null) {
            return null;
        }
        String parentCode = subject.getParentcode();
        if (StringUtils.isEmpty((String)parentCode)) {
            return null;
        }
        return this.getSchemeBySubjCode(schemeDoSubjMap, subjectMap, parentCode);
    }

    private void getParentSubjCode(List<String> subjectCodes, Map<String, SubjectDTO> subjectMap, String subjCode) {
        SubjectDTO subject = subjectMap.get(subjCode);
        if (subject == null) {
            return;
        }
        String parentCode = subject.getParentcode();
        if (StringUtils.isEmpty((String)parentCode) || "-".equals(parentCode)) {
            return;
        }
        subjectCodes.add(parentCode);
        this.getParentSubjCode(subjectCodes, subjectMap, parentCode);
    }

    private ConvertRateSchemeVO convert2VO(ConvertRateSchemeDO data, Map<String, SubjectDTO> subjMap) {
        ConvertRateSchemeVO vo = new ConvertRateSchemeVO(data.getId(), data.getVer(), data.getRowDataId(), data.getSubjectCode(), data.getBfRateType(), data.getQcRateType(), data.getBqRateType(), data.getSumRateType(), data.getCfRateType(), data.getNextYearAdjustRateType());
        ArrayList<SubjectShowVO> subjVOList = new ArrayList<SubjectShowVO>();
        for (String subjCode : data.getSubjectCode().split(";")) {
            if (!subjMap.containsKey(subjCode)) continue;
            SubjectShowVO subjVO = new SubjectShowVO();
            subjVO.setCode(subjCode);
            subjVO.setLabel(subjMap.get(subjCode).getName());
            subjVO.setTitle(subjMap.get(subjCode).getName());
            subjVO.setCurrency(subjMap.get(subjCode).getCurrency());
            subjVOList.add(subjVO);
        }
        vo.setSubject(subjVOList);
        vo.setId(vo.getRowDataId());
        return vo;
    }

    @Override
    public Map<String, ConvertRateSchemeVO> getRateSchemeBySubjectCodes(List<String> subjCodeParams) {
        if (subjCodeParams == null || subjCodeParams.isEmpty()) {
            return null;
        }
        Map<String, SubjectDTO> subjectMap = this.subjectService.list(new BaseDataDTO()).stream().collect(Collectors.toMap(BaseDataDO::getCode, subject -> subject, (k1, k2) -> k2));
        ArrayList<String> subjectCodes = new ArrayList<String>(subjCodeParams);
        for (String subjectCode : subjCodeParams) {
            this.getParentSubjCode(subjectCodes, subjectMap, subjectCode);
        }
        ConvRateSchemeQueryDTO param = new ConvRateSchemeQueryDTO();
        LinkedHashMap<String, ConvertRateSchemeVO> schemeVoSubjMap = new LinkedHashMap<String, ConvertRateSchemeVO>(16);
        Collections.sort(subjCodeParams);
        param.setSubjectCodes(subjectCodes);
        List<ConvertRateSchemeDO> schemes = this.mapper.getSchemeBySubjectCode(param);
        Map<String, ConvertRateSchemeDO> schemeDoSubjMap = schemes.stream().collect(Collectors.toMap(ConvertRateSchemeDO::getSubjectCode, scheme -> scheme, (k1, k2) -> k2));
        for (String subjectCode : subjCodeParams) {
            ConvertRateSchemeVO schemeVO = this.getSchemeBySubjCode(schemeDoSubjMap, subjectMap, subjectCode);
            if (schemeVO == null) continue;
            SubjectShowVO subjVO = new SubjectShowVO();
            subjVO.setCode(subjectCode);
            if (subjectMap.get(subjectCode) != null) {
                subjVO.setLabel(subjectMap.get(subjectCode).getName());
                subjVO.setTitle(subjectMap.get(subjectCode).getName());
                subjVO.setCurrency(subjectMap.get(subjectCode).getCurrency());
            }
            if (schemeVoSubjMap.containsKey(schemeVO.getRowDataId())) {
                ConvertRateSchemeVO rowData = (ConvertRateSchemeVO)schemeVoSubjMap.get(schemeVO.getRowDataId());
                rowData.setSubjectCode(rowData.getSubjectCode() + ";" + schemeVO.getSubjectCode());
                rowData.getSubject().add(subjVO);
                ArrayList<SubjectShowVO> subjList = new ArrayList<SubjectShowVO>();
                subjList.add(subjVO);
                schemeVO.setSubject(subjList);
                continue;
            }
            ArrayList<SubjectShowVO> subjList = new ArrayList<SubjectShowVO>();
            subjList.add(subjVO);
            schemeVO.setSubject(subjList);
            schemeVoSubjMap.put(schemeVO.getRowDataId(), schemeVO);
        }
        return schemeVoSubjMap;
    }
}

