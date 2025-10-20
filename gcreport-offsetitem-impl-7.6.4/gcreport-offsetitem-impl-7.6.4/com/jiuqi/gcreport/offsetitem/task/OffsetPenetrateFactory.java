/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  org.activiti.engine.impl.util.CollectionUtil
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.gcreport.offsetitem.task.OffSetPenetrateTask;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OffsetPenetrateFactory
implements InitializingBean {
    @Autowired(required=false)
    private List<OffSetPenetrateTask> offSetPenetrateTaskList;
    private Map<String, OffSetPenetrateTask> taskType2InstanceMap = new HashMap<String, OffSetPenetrateTask>();

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    private void init() {
        if (CollectionUtil.isNotEmpty(this.offSetPenetrateTaskList)) {
            for (OffSetPenetrateTask item : this.offSetPenetrateTaskList) {
                this.taskType2InstanceMap.put(item.getTaskType(), item);
            }
        }
    }

    public OffSetPenetrateTask getOffsetPenetrateType(String offsetRuleType) {
        String offsetPenetrateType = "";
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)offsetRuleType);
        if (ruleTypeEnum == RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT) {
            offsetPenetrateType = "fvchBill";
        } else if (ruleTypeEnum == RuleTypeEnum.DIRECT_INVESTMENT || ruleTypeEnum == RuleTypeEnum.INDIRECT_INVESTMENT || ruleTypeEnum == RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT) {
            offsetPenetrateType = "investBill";
        }
        return this.taskType2InstanceMap.get(offsetPenetrateType);
    }
}

