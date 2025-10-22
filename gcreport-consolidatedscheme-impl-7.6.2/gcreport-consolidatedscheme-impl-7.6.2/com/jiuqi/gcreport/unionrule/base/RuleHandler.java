/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.unionrule.base;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public abstract class RuleHandler {
    public abstract String convertJsonStringWhenVO2EO(UnionRuleVO var1);

    public abstract String convertJsonStringWhenEO2VO(UnionRuleEO var1);

    public abstract AbstractUnionRule convertEO2DTO(UnionRuleEO var1);

    public abstract UnionRuleEO convertDTO2EO(AbstractUnionRule var1);

    public String reorganizeJsonStringWhenCopy(String json) {
        return json;
    }

    public Set<ImportMessageVO> checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, String reportSystemId) {
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        if (!CollectionUtils.isEmpty(unionRuleDTO.getSrcDebitSubjectCodeList())) {
            this.checkSubjectCode(unionRuleDTO, unionRuleDTO.getSrcDebitSubjectCodeList(), resultList, reportSystemId);
        }
        if (!CollectionUtils.isEmpty(unionRuleDTO.getSrcCreditSubjectCodeList())) {
            this.checkSubjectCode(unionRuleDTO, unionRuleDTO.getSrcCreditSubjectCodeList(), resultList, reportSystemId);
        }
        return resultList;
    }

    protected void checkSubjectCode(AbstractUnionRule unionRuleDTO, Collection<String> subjectCodes, Set<ImportMessageVO> resultList, String reportSystemId) {
        subjectCodes.forEach(debitConfigSubject -> {
            BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)debitConfigSubject, (String[])new String[]{reportSystemId})));
            if (Objects.isNull(baseDataVO)) {
                resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u79d1\u76ee" + debitConfigSubject + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
            }
        });
    }

    public abstract AbstractUnionRule convertMapToDTO(Map<String, Object> var1);

    public List<String> getFormulaFetchDataSourceTable(String reportSystemId) {
        return new ArrayList<String>();
    }

    public List<UnionRuleVO> getUnionRuleChildNodes(UnionRuleEO rule) {
        return new ArrayList<UnionRuleVO>();
    }

    public boolean filterRuleByParams(UnionRuleEO rule, Map<String, Object> params) {
        return true;
    }
}

