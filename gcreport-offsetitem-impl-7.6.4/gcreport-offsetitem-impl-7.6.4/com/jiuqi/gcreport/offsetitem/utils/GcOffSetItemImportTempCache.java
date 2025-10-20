/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import java.util.List;
import java.util.Map;

public class GcOffSetItemImportTempCache {
    private Map<String, UnionRuleEO> ruleTitle2RuleVoMap;
    private Map<String, UnionRuleEO> ruleCode2RuleVoMap;
    private Map<String, String> unitTitle2unitCodeMap;
    private Map<String, String> unitCode2UnitTitleMap;
    private Map<String, String> subjectTitle2Code;
    private Map<String, String> subjectCode2Title;
    private Map<String, Map<String, String>> tableName2MapDictTitle2Code;
    private Map<String, Map<String, String>> tableName2MapDictCode2Title;
    private Map<String, List<ConsolidatedSubjectEO>> parentCodeGroupMap;
    private Map<String, DimensionVO> allDimMap;

    public Map<String, UnionRuleEO> getRuleTitle2RuleVoMap() {
        return this.ruleTitle2RuleVoMap;
    }

    public void setRuleTitle2RuleVoMap(Map<String, UnionRuleEO> ruleTitle2RuleVoMap) {
        this.ruleTitle2RuleVoMap = ruleTitle2RuleVoMap;
    }

    public Map<String, UnionRuleEO> getRuleCode2RuleVoMap() {
        return this.ruleCode2RuleVoMap;
    }

    public void setRuleCode2RuleVoMap(Map<String, UnionRuleEO> ruleCode2RuleVoMap) {
        this.ruleCode2RuleVoMap = ruleCode2RuleVoMap;
    }

    public Map<String, String> getUnitTitle2unitCodeMap() {
        return this.unitTitle2unitCodeMap;
    }

    public void setUnitTitle2unitCodeMap(Map<String, String> unitTitle2unitCodeMap) {
        this.unitTitle2unitCodeMap = unitTitle2unitCodeMap;
    }

    public Map<String, String> getUnitCode2UnitTitleMap() {
        return this.unitCode2UnitTitleMap;
    }

    public void setUnitCode2UnitTitleMap(Map<String, String> unitCode2UnitTitleMap) {
        this.unitCode2UnitTitleMap = unitCode2UnitTitleMap;
    }

    public Map<String, String> getSubjectTitle2Code() {
        return this.subjectTitle2Code;
    }

    public void setSubjectTitle2Code(Map<String, String> subjectTitle2Code) {
        this.subjectTitle2Code = subjectTitle2Code;
    }

    public Map<String, Map<String, String>> getTableName2MapDictTitle2Code() {
        return this.tableName2MapDictTitle2Code;
    }

    public void setTableName2MapDictTitle2Code(Map<String, Map<String, String>> tableName2MapDictTitle2Code) {
        this.tableName2MapDictTitle2Code = tableName2MapDictTitle2Code;
    }

    public Map<String, Map<String, String>> getTableName2MapDictCode2Title() {
        return this.tableName2MapDictCode2Title;
    }

    public void setTableName2MapDictCode2Title(Map<String, Map<String, String>> tableName2MapDictCode2Title) {
        this.tableName2MapDictCode2Title = tableName2MapDictCode2Title;
    }

    public Map<String, List<ConsolidatedSubjectEO>> getParentCodeGroupMap() {
        return this.parentCodeGroupMap;
    }

    public void setParentCodeGroupMap(Map<String, List<ConsolidatedSubjectEO>> parentCodeGroupMap) {
        this.parentCodeGroupMap = parentCodeGroupMap;
    }

    public Map<String, DimensionVO> getAllDimMap() {
        return this.allDimMap;
    }

    public void setAllDimMap(Map<String, DimensionVO> allDimMap) {
        this.allDimMap = allDimMap;
    }

    public Map<String, String> getSubjectCode2Title() {
        return this.subjectCode2Title;
    }

    public void setSubjectCode2Title(Map<String, String> subjectCode2Title) {
        this.subjectCode2Title = subjectCode2Title;
    }

    public void clear() {
        this.clearMap(this.ruleCode2RuleVoMap);
        this.clearMap(this.ruleTitle2RuleVoMap);
        this.clearMap(this.unitTitle2unitCodeMap);
        this.clearMap(this.unitCode2UnitTitleMap);
        this.clearMap(this.subjectTitle2Code);
        this.clearMap(this.subjectCode2Title);
        this.clearMap(this.tableName2MapDictTitle2Code);
        this.clearMap(this.tableName2MapDictCode2Title);
        this.clearMap(this.parentCodeGroupMap);
        this.clearMap(this.allDimMap);
    }

    private void clearMap(Map map) {
        if (map != null) {
            map.clear();
        }
    }
}

