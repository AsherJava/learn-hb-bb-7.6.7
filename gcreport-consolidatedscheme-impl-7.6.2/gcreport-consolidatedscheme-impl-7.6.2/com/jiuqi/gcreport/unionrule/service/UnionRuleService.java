/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.DragRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ITree
 *  com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.unionrule.service;

import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.gcreport.unionrule.vo.DragRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ITree;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public interface UnionRuleService {
    public UnionRuleVO saveOrUpdateRule(UnionRuleVO var1);

    public UnionRuleVO importRule(AbstractUnionRule var1);

    public void deleteUnionRule(String var1);

    public UnionRuleVO selectUnionRuleAndChildrenById(String var1, boolean var2);

    public List<UnionRuleVO> selectUnionRuleChildrenByGroup(String var1);

    public List<UnionRuleEO> selectAllChildrenRuleEo(String var1);

    public UnionRuleVO selectUnionRuleById(String var1);

    public List<UnionRuleVO> selectUnionRuleByIds(List<String> var1);

    public AbstractUnionRule selectUnionRuleDTOById(String var1);

    public List<AbstractUnionRule> selectRuleListByReportSystemAndRuleTypes(String var1, Collection<String> var2);

    public List<AbstractUnionRule> selectRuleListBySchemeIdAndRuleTypes(String var1, String var2, List<String> var3);

    public void updateNodeName(String var1, String var2);

    public List<UnionRuleVO> selectRuleTreeByReportSystem(String var1, boolean var2);

    public String importRule(String var1, List<Map<String, Object>> var2);

    public List<UnionRuleEO> selectRuleEoListByTaskId(String var1, String var2);

    public List<UnionRuleVO> selectRuleTreeBySystemId(String var1);

    public void stopUnionRule(String var1, Boolean var2);

    public String moveRuleNode(String var1, Integer var2);

    public void dragRuleNode(DragRuleVO var1);

    public UnionRuleVO cutOrPaste(String var1, String var2, String var3);

    public Stack<UnionRuleVO> getParentsByChildId(String var1);

    public List<AbstractUnionRule> selectUnionRuleDTOByIdList(Collection<String> var1);

    public boolean hasRulesByReportSystemId(String var1);

    public List<UnionRuleVO> selectRuleTreeBySystemIdAndRules(String var1, String var2, boolean var3, GcOrgCacheVO var4);

    public List<UnionRuleVO> selectRuleTreeByTaskIdAndRules(String var1, String var2, String var3, boolean var4, String var5, String var6);

    public List<UnionRuleVO> selectInitRuleBySystemIdAndRuleTypes(String var1, String var2);

    public List<SelectOptionVO> getManagementDimensionVOByReportSystem(String var1);

    public Map<String, List<String>> getFilterRepeatFetchSubject(Map<String, Object> var1);

    public List<BaseRuleVO> findAllRuleTitles(String var1);

    public List<String> getDataSourceSubject(String var1);

    public List<SelectFloatLineOptionTreeVO> getFloatLineRuleOption(String var1);

    public List<SelectOptionVO> getDataRegionFieldOption(String var1);

    public Map<String, Object> getChangeRatioOption(String var1);

    public List<ITree<UnionRuleVO>> listRuleTree(Map<String, Object> var1);

    public List<String> findAllRuleIdsBySystemIdAndRuleTypes(String var1, Collection<String> var2);

    public Set<String> findRuleIdsByRuleType(String var1, String var2);

    public String selectUnionRuleTitleById(String var1);

    public List<AbstractUnionRule> selectAllRuleListByReportSystemAndRuleTypes(String var1, Collection<String> var2);
}

