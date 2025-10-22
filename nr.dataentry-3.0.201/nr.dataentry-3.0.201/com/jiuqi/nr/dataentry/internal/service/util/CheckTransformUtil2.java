/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckGroupReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckResultGroupInfo;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CheckTransformUtil2 {
    public FormulaCheckGroupReturnInfo transformCheckResultGroup(CheckResultGroup checkResultGroup) {
        FormulaCheckGroupReturnInfo formulaCheckGroupReturnInfo = new FormulaCheckGroupReturnInfo();
        formulaCheckGroupReturnInfo.setMessage(checkResultGroup.getMessage());
        formulaCheckGroupReturnInfo.setShowCount(checkResultGroup.getShowCount());
        formulaCheckGroupReturnInfo.setTotalCount(checkResultGroup.getTotalCount());
        Map checkTypeCountMap = checkResultGroup.getCheckTypeCountMap();
        if (checkTypeCountMap.containsKey("\u9519\u8bef\u578b")) {
            formulaCheckGroupReturnInfo.setErrorCount((Integer)checkTypeCountMap.get("\u9519\u8bef\u578b"));
        }
        if (checkTypeCountMap.containsKey("\u8b66\u544a\u578b")) {
            formulaCheckGroupReturnInfo.setWarnCount((Integer)checkTypeCountMap.get("\u8b66\u544a\u578b"));
        }
        if (checkTypeCountMap.containsKey("\u63d0\u793a\u578b")) {
            formulaCheckGroupReturnInfo.setHintCount((Integer)checkTypeCountMap.get("\u63d0\u793a\u578b"));
        }
        List groupData = checkResultGroup.getGroupData();
        formulaCheckGroupReturnInfo.setResults(this.getFormulaCheckResultGroupInfoList(groupData));
        return formulaCheckGroupReturnInfo;
    }

    public FormulaCheckResultGroupInfo transformCheckResultGroupData(CheckResultGroupData checkResultGroupData) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        FormulaCheckResultGroupInfo formulaCheckResultGroupInfo = new FormulaCheckResultGroupInfo();
        formulaCheckResultGroupInfo.setCount(checkResultGroupData.getCount());
        formulaCheckResultGroupInfo.setKey(checkResultGroupData.getKey());
        formulaCheckResultGroupInfo.setCode(checkResultGroupData.getCode());
        formulaCheckResultGroupInfo.setTitle(checkResultGroupData.getTitle());
        FormulaDefine formulaDefine = formulaRunTimeController.queryFormulaDefine(checkResultGroupData.getKey());
        if (formulaDefine != null) {
            formulaCheckResultGroupInfo.setDescription(formulaDefine.getDescription());
        }
        List children = checkResultGroupData.getChildren();
        formulaCheckResultGroupInfo.setChildrenGroups(this.getFormulaCheckResultGroupInfoList(children));
        return formulaCheckResultGroupInfo;
    }

    public List<FormulaCheckResultGroupInfo> getFormulaCheckResultGroupInfoList(List<CheckResultGroupData> list) {
        ArrayList<FormulaCheckResultGroupInfo> result = new ArrayList<FormulaCheckResultGroupInfo>();
        for (CheckResultGroupData checkResultGroupData : list) {
            result.add(this.transformCheckResultGroupData(checkResultGroupData));
        }
        return result;
    }
}

