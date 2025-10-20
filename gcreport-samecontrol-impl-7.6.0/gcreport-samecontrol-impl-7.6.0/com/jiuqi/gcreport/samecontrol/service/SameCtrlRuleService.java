/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface SameCtrlRuleService {
    public List<SameCtrlRuleVO> listRuleTree(String var1, String var2);

    public SameCtrlRuleVO saveSameCtrlRule(SameCtrlRuleVO var1);

    public SameCtrlRuleVO getSameCtrlRuleById(String var1);

    public SameCtrlRuleVO getSameCtrlRuleGroupByGroupId(String var1);

    public void deleteSameCtrlRuleOrGroupById(String var1);

    public void updateSameCtrlRuleOrGroupNameById(String var1, String var2);

    public void setSameCtrlRuleStatus(String var1, Boolean var2);

    public Set<ImportMessageVO> importJson(String var1, String var2, MultipartFile var3, boolean var4);

    public Resource exportJson(String var1, String var2);

    public SameCtrlRuleVO cutOrPaste(String var1, String var2, String var3);

    public List<AbstractCommonRule> findRuleListByIsolatedFiledAndRuleTypes(String var1, String var2, Collection<SameCtrlRuleTypeEnum> var3);

    public void dragRuleNode(SameCtrlRuleDragRuleVO var1);
}

