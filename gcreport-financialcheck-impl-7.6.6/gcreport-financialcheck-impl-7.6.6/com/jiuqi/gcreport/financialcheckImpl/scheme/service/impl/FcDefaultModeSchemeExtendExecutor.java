/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckProjectDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckMappingDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl.FinancialCheckSchemeServiceImpl;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckProjectDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckMappingDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.BusinessRoleEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class FcDefaultModeSchemeExtendExecutor
implements FinancialCheckSchemeExtendExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FcDefaultModeSchemeExtendExecutor.class);
    @Autowired
    private FinancialCheckSchemeDao schemeDao;
    @Autowired
    private FinancialCheckProjectDao financialCheckProjectDao;
    @Autowired
    private FinancialCheckMappingDao financialCheckMappingDao;
    @Autowired
    private BaseDataClient bdClient;
    @Autowired
    FinancialCheckSchemeServiceImpl schemeService;

    @Override
    public void save(FinancialCheckSchemeVO financialCheckSchemeVO, FinancialCheckSchemeEO schemeEO) {
        this.schemeDao.update((BaseEntity)schemeEO);
        List projects = financialCheckSchemeVO.getProjects();
        for (FinancialCheckProjectDTO project : projects) {
            if (!StringUtils.isEmpty((String)project.getCheckProjectTitle())) continue;
            throw new BusinessRuntimeException("\u5b58\u5728\u5bf9\u8d26\u9879\u76ee\u4e3a\u7a7a\u7684\u79d1\u76ee\u914d\u7f6e\u3002");
        }
        Map<String, List<FinancialCheckProjectDTO>> projectGroup = projects.stream().collect(Collectors.groupingBy(FinancialCheckProjectDTO::getCheckProjectTitle));
        projectGroup.forEach((projectTitle, items) -> {
            if (items.size() > 1) {
                throw new BusinessRuntimeException("\u5bf9\u8d26\u9879\u76ee\u4e0d\u80fd\u91cd\u590d\uff1a" + projectTitle);
            }
        });
        List gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_PROJECT");
        Map<String, String> project2CodeMap = gcBaseData.stream().collect(Collectors.toMap(GcBaseData::getTitle, GcBaseData::getCode, (k1, k2) -> k1));
        ArrayList<FinancialCheckProjectEO> addFinancialCheckProjectEOS = new ArrayList<FinancialCheckProjectEO>();
        ArrayList<FinancialCheckProjectEO> updateFinancialCheckProjectEOS = new ArrayList<FinancialCheckProjectEO>();
        ArrayList<FinancialCheckMappingEO> addFinancialCheckMappingEOS = new ArrayList<FinancialCheckMappingEO>();
        for (int i = 0; i < projects.size(); ++i) {
            String projectCode;
            FinancialCheckProjectDTO financialCheckProjectDTO = (FinancialCheckProjectDTO)projects.get(i);
            FinancialCheckProjectEO financialCheckProjectEO = new FinancialCheckProjectEO();
            BeanUtils.copyProperties(financialCheckProjectDTO, financialCheckProjectEO);
            if (CheckModeEnum.UNILATERAL.getCode().equals(schemeEO.getCheckMode())) {
                financialCheckProjectEO.setBusinessRole(BusinessRoleEnum.ASSET.getCode());
            }
            financialCheckProjectEO.setSortOrder(Double.valueOf(i));
            financialCheckProjectEO.setSchemeId(schemeEO.getId());
            if (StringUtils.isEmpty((String)financialCheckProjectDTO.getId())) {
                if (!project2CodeMap.isEmpty() && project2CodeMap.containsKey(financialCheckProjectDTO.getCheckProjectTitle())) {
                    financialCheckProjectEO.setCheckProject(project2CodeMap.get(financialCheckProjectDTO.getCheckProjectTitle()));
                } else {
                    projectCode = this.addCheckProjectBaseData(financialCheckProjectDTO.getCheckProjectTitle());
                    financialCheckProjectEO.setCheckProject(projectCode);
                }
                financialCheckProjectEO.setId(UUIDUtils.newUUIDStr());
                addFinancialCheckProjectEOS.add(financialCheckProjectEO);
            } else {
                if (!project2CodeMap.isEmpty() && project2CodeMap.containsKey(financialCheckProjectDTO.getCheckProjectTitle())) {
                    financialCheckProjectEO.setCheckProject(project2CodeMap.get(financialCheckProjectDTO.getCheckProjectTitle()));
                } else {
                    projectCode = this.addCheckProjectBaseData(financialCheckProjectDTO.getCheckProjectTitle());
                    financialCheckProjectEO.setCheckProject(projectCode);
                }
                financialCheckProjectEO.setId(financialCheckProjectDTO.getId());
                updateFinancialCheckProjectEOS.add(financialCheckProjectEO);
            }
            List subjects = financialCheckProjectDTO.getSubjects();
            if (CollectionUtils.isEmpty(subjects)) continue;
            List baseDataCodeOnlyParent = BaseDataUtils.getBaseDataCodeOnlyParent((List)subjects, (String)"MD_ACCTSUBJECT");
            for (int j = 0; j < baseDataCodeOnlyParent.size(); ++j) {
                FinancialCheckMappingEO financialCheckMappingEO = new FinancialCheckMappingEO();
                financialCheckMappingEO.setSubject((String)baseDataCodeOnlyParent.get(j));
                financialCheckMappingEO.setCheckProject(financialCheckProjectEO.getId());
                financialCheckMappingEO.setSchemeId(schemeEO.getId());
                financialCheckMappingEO.setSortOrder(Double.valueOf(j));
                financialCheckMappingEO.setId(UUIDUtils.newUUIDStr());
                addFinancialCheckMappingEOS.add(financialCheckMappingEO);
            }
        }
        List deleteProjects = financialCheckSchemeVO.getDeleteProjects();
        List deleteProjectIds = deleteProjects.stream().map(FinancialCheckProjectDTO::getId).filter(id -> !StringUtils.isEmpty((String)id)).collect(Collectors.toList());
        this.financialCheckProjectDao.deleteByIds(deleteProjectIds);
        this.financialCheckProjectDao.addBatch(addFinancialCheckProjectEOS);
        this.financialCheckProjectDao.updateBatch(updateFinancialCheckProjectEOS);
        this.financialCheckMappingDao.deleteBySchemeId(financialCheckSchemeVO.getId());
        this.financialCheckMappingDao.addBatch(addFinancialCheckMappingEOS);
    }

    private String addCheckProjectBaseData(String projectName) {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setTableName("MD_CHECK_PROJECT");
        dto.setId(UUIDUtils.fromString36((String)UUIDUtils.newUUIDStr()));
        String projectCode = this.generateProjectCode();
        dto.setCode(projectCode);
        dto.setName(projectName);
        R r1 = this.bdClient.add(dto);
        if (r1.getCode() == 1) {
            logger.error("\u521b\u5efa\u5bf9\u8d26\u9879\u76ee\u57fa\u7840\u6570\u636e\u5f02\u5e38\uff0c\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)dto));
            throw new BusinessRuntimeException(r1.getMsg());
        }
        return projectCode;
    }

    private String generateProjectCode() {
        String dataStr = DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmssSSS");
        String randomSuffix = "0" + ThreadLocalRandom.current().nextInt(100);
        return "DZXM" + dataStr + randomSuffix.substring(randomSuffix.length() - 2);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void afterDelete(String id) {
        this.financialCheckMappingDao.deleteBySchemeId(id);
        this.financialCheckProjectDao.deleteBySchemeId(id);
    }

    @Override
    public FinancialCheckSchemeVO convertEO2VO(FinancialCheckSchemeEO schemeEO) {
        FinancialCheckSchemeVO financialCheckSchemeVO = new FinancialCheckSchemeVO();
        BeanUtils.copyProperties(schemeEO, financialCheckSchemeVO);
        if (Objects.nonNull(schemeEO.getToleranceEnable())) {
            financialCheckSchemeVO.setToleranceEnable(schemeEO.getToleranceEnable().toString());
        }
        if (Objects.nonNull(schemeEO.getSpecialCheck())) {
            financialCheckSchemeVO.setSpecialCheck(schemeEO.getSpecialCheck().toString());
        }
        if (Objects.nonNull(schemeEO.getCheckAttribute())) {
            GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", schemeEO.getCheckAttribute());
            HashMap<String, String> item = new HashMap<String, String>();
            if (Objects.nonNull(gcBaseData)) {
                item.put("code", gcBaseData.getCode());
                item.put("title", gcBaseData.getTitle());
                financialCheckSchemeVO.setCheckAttributeItem(item);
            }
        }
        FinancialCheckProjectEO param = new FinancialCheckProjectEO();
        param.setSchemeId(schemeEO.getId());
        List financialCheckProjectEOS = this.financialCheckProjectDao.selectList((BaseEntity)param);
        financialCheckProjectEOS.sort(Comparator.comparing(FinancialCheckProjectEO::getSortOrder));
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CHECK_PROJECT");
        Map<String, String> projectCode2Title = baseData.stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle, (k1, k2) -> k1));
        FinancialCheckMappingEO financialCheckMappingParam = new FinancialCheckMappingEO();
        financialCheckMappingParam.setSchemeId(schemeEO.getId());
        List financialCheckMappingEOS = this.financialCheckMappingDao.selectList((BaseEntity)financialCheckMappingParam);
        Map<String, List<FinancialCheckMappingEO>> groupSubjects = financialCheckMappingEOS.stream().collect(Collectors.groupingBy(FinancialCheckMappingEO::getCheckProject));
        groupSubjects.forEach((project, subjects) -> subjects.sort(Comparator.comparing(FinancialCheckMappingEO::getSortOrder)));
        if (!CollectionUtils.isEmpty(financialCheckProjectEOS)) {
            List financialCheckProjectDTOS = financialCheckProjectEOS.stream().map(financialCheckProjectEO -> {
                List financialCheckSubjects;
                GcBaseData baseDataSimpleItem;
                FinancialCheckProjectDTO financialCheckProjectDTO = new FinancialCheckProjectDTO();
                BeanUtils.copyProperties(financialCheckProjectEO, financialCheckProjectDTO);
                financialCheckProjectDTO.setCheckProjectTitle((String)projectCode2Title.get(financialCheckProjectDTO.getCheckProject()));
                if (!StringUtils.isEmpty((String)financialCheckProjectDTO.getOppSubject()) && Objects.nonNull(baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", financialCheckProjectDTO.getOppSubject()))) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("code", baseDataSimpleItem.getCode());
                    item.put("title", baseDataSimpleItem.getTitle());
                    financialCheckProjectDTO.setOppSubjectItems(item);
                }
                if (!CollectionUtils.isEmpty(financialCheckSubjects = (List)groupSubjects.get(financialCheckProjectEO.getId()))) {
                    List<String> subjects = financialCheckSubjects.stream().map(FinancialCheckMappingEO::getSubject).collect(Collectors.toList());
                    ArrayList gcBaseData = new ArrayList();
                    subjects.forEach(subject -> {
                        GcBaseData baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", subject);
                        if (Objects.nonNull(baseDataSimpleItem)) {
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("code", baseDataSimpleItem.getCode());
                            item.put("title", baseDataSimpleItem.getTitle());
                            gcBaseData.add(item);
                        }
                    });
                    financialCheckProjectDTO.setSubjectItems(gcBaseData);
                    financialCheckProjectDTO.setSubjects(subjects);
                }
                return financialCheckProjectDTO;
            }).collect(Collectors.toList());
            financialCheckSchemeVO.setProjects(financialCheckProjectDTOS);
        }
        if (!StringUtils.isEmpty((String)schemeEO.getCheckDimensions())) {
            String[] dims = schemeEO.getCheckDimensions().split(",");
            financialCheckSchemeVO.setCheckDimensions(new ArrayList<String>(Arrays.asList(dims)));
        }
        if (!StringUtils.isEmpty((String)schemeEO.getUnitCodes())) {
            String[] units = schemeEO.getUnitCodes().split(",");
            ArrayList unitList = new ArrayList(units.length);
            for (String unitCode : units) {
                GcOrgCacheVO gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType()).getOrgByCode(unitCode);
                if (!Objects.nonNull(gcOrgCacheVOS)) continue;
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("code", gcOrgCacheVOS.getCode());
                item.put("title", gcOrgCacheVOS.getTitle());
                unitList.add(item);
            }
            financialCheckSchemeVO.setUnits(unitList);
            financialCheckSchemeVO.setUnitCodes(new ArrayList<String>(Arrays.asList(units)));
        }
        return financialCheckSchemeVO;
    }
}

