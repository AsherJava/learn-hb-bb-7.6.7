/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.utils.GcOffSetItemImportTempCache;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommonImportUtil {
    public static GcOffSetItemImportTempCache initGcOffSetItemImportTempCache(QueryParamsVO queryParamsVO) {
        GcOffSetItemImportTempCache tempCache = new GcOffSetItemImportTempCache();
        CommonImportUtil.initRuleTempCache(tempCache, queryParamsVO, queryParamsVO.getPeriodStr());
        CommonImportUtil.initUnitTempCache(tempCache, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr());
        CommonImportUtil.initSubjectTempCache(tempCache, queryParamsVO);
        List dimensionVOS = ((ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class)).getAllDimensionsByTableName("GC_OFFSETVCHRITEM", queryParamsVO.getSystemId());
        Map<String, Object> allDimMap = new HashMap<String, DimensionVO>();
        if (!CollectionUtils.isEmpty((Collection)dimensionVOS)) {
            allDimMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getTitle, Function.identity(), (key1, key2) -> key2));
        }
        tempCache.setAllDimMap(allDimMap);
        CommonImportUtil.initDimensionsTempCache(tempCache, dimensionVOS);
        return tempCache;
    }

    private static void initRuleTempCache(GcOffSetItemImportTempCache tempCache, QueryParamsVO queryParamsVO, String periodStr) {
        HashMap<String, UnionRuleEO> ruleTitle2EOMap = new HashMap<String, UnionRuleEO>();
        HashMap<String, UnionRuleEO> ruleId2EOMap = new HashMap<String, UnionRuleEO>();
        tempCache.setRuleTitle2RuleVoMap(ruleTitle2EOMap);
        tempCache.setRuleCode2RuleVoMap(ruleId2EOMap);
        String systemId = queryParamsVO.getSystemId();
        if (StringUtils.isEmpty((String)systemId)) {
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), periodStr);
        }
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"));
        }
        UnionRuleDao unionRuleDao = (UnionRuleDao)SpringContextUtils.getBean(UnionRuleDao.class);
        List ruleEOList = unionRuleDao.findRuleListByReportSystem(systemId);
        if (ruleEOList == null) {
            return;
        }
        for (UnionRuleEO unionRuleVO : ruleEOList) {
            ruleTitle2EOMap.put(unionRuleVO.getTitle().trim(), unionRuleVO);
            ruleId2EOMap.put(unionRuleVO.getId(), unionRuleVO);
        }
    }

    private static void initUnitTempCache(GcOffSetItemImportTempCache tempCache, String orgType, String periodStr) {
        HashMap<String, String> unitTitle2unitCodeMap = new HashMap<String, String>();
        HashMap<String, String> unitCode2unitTitleMap = new HashMap<String, String>();
        tempCache.setUnitTitle2unitCodeMap(unitTitle2unitCodeMap);
        tempCache.setUnitCode2UnitTitleMap(unitCode2unitTitleMap);
        List orgs = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, periodStr)).listAllOrgByParentIdContainsSelf(null);
        if (orgs == null) {
            return;
        }
        for (GcOrgCacheVO org : orgs) {
            unitTitle2unitCodeMap.put(org.getTitle(), org.getCode());
            unitCode2unitTitleMap.put(org.getCode(), org.getTitle());
        }
    }

    private static void initSubjectTempCache(GcOffSetItemImportTempCache tempCache, QueryParamsVO queryParamsVO) {
        List<ConsolidatedSubjectEO> subjectEOs = CommonImportUtil.getConsolidatedSubjectEOS(queryParamsVO);
        if (subjectEOs == null) {
            tempCache.setSubjectCode2Title(Collections.EMPTY_MAP);
            tempCache.setSubjectTitle2Code(Collections.EMPTY_MAP);
            tempCache.setParentCodeGroupMap(Collections.EMPTY_MAP);
            return;
        }
        Map<String, List<ConsolidatedSubjectEO>> parentCodeGroupMap = subjectEOs.stream().filter(t -> t.getParentCode() != null).collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode));
        tempCache.setParentCodeGroupMap(parentCodeGroupMap);
        HashMap<String, String> subjectTitle2Code = new HashMap<String, String>();
        HashMap<String, String> subjectCode2Title = new HashMap<String, String>();
        for (ConsolidatedSubjectEO subjectEO : subjectEOs) {
            subjectTitle2Code.put(subjectEO.getTitle(), subjectEO.getCode());
            subjectCode2Title.put(subjectEO.getCode(), subjectEO.getTitle());
        }
        tempCache.setSubjectTitle2Code(subjectTitle2Code);
        tempCache.setSubjectCode2Title(subjectCode2Title);
    }

    private static List<ConsolidatedSubjectEO> getConsolidatedSubjectEOS(QueryParamsVO queryParamsVO) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String systemId = queryParamsVO.getSystemId();
        if (StringUtils.isEmpty((String)systemId)) {
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        }
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"));
        }
        return subjectService.listAllSubjectsBySystemId(systemId);
    }

    private static void initDimensionsTempCache(GcOffSetItemImportTempCache tempCache, List<DimensionVO> allDimMap) {
        HashMap<String, Map<String, String>> tableName2MapDictTitle2Code = new HashMap<String, Map<String, String>>();
        HashMap<String, Map<String, String>> tableName2MapDictCode2Title = new HashMap<String, Map<String, String>>();
        tempCache.setTableName2MapDictTitle2Code(tableName2MapDictTitle2Code);
        tempCache.setTableName2MapDictCode2Title(tableName2MapDictCode2Title);
        for (DimensionVO dimensionVO : allDimMap) {
            CommonImportUtil.initDictTitleAndCodeMap(dimensionVO.getDictTableName(), tableName2MapDictTitle2Code, tableName2MapDictCode2Title);
        }
    }

    private static void initDictTitleAndCodeMap(String tableCode, Map<String, Map<String, String>> tableName2MapDictTitle2Code, Map<String, Map<String, String>> tableName2MapDictCode2Title) {
        if (StringUtils.isEmpty((String)tableCode)) {
            tableName2MapDictTitle2Code.put(tableCode, Collections.EMPTY_MAP);
            tableName2MapDictCode2Title.put(tableCode, Collections.EMPTY_MAP);
            return;
        }
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(tableCode);
        if (null == baseData) {
            tableName2MapDictTitle2Code.put(tableCode, Collections.EMPTY_MAP);
            tableName2MapDictCode2Title.put(tableCode, Collections.EMPTY_MAP);
            return;
        }
        HashMap<String, String> dictTitle2Code = new HashMap<String, String>();
        HashMap<String, String> dictCode2Title = new HashMap<String, String>();
        for (GcBaseData iBaseData : baseData) {
            dictTitle2Code.put(iBaseData.getTitle(), iBaseData.getCode());
            dictCode2Title.put(iBaseData.getCode(), iBaseData.getTitle());
        }
        tableName2MapDictTitle2Code.put(tableCode, dictTitle2Code);
        tableName2MapDictCode2Title.put(tableCode, dictCode2Title);
    }
}

