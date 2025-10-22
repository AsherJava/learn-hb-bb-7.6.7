/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.condition;

import com.jiuqi.nr.dataentry.gather.ISlotListGathers;
import com.jiuqi.nr.dataentry.gather.SlotItem;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Order(value=0x7FFFFFFF)
public class BatchCheckMonitorCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String[] strs;
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for (String beanName : strs = beanFactory.getBeanNamesForType(ISlotListGathers.class)) {
            ISlotListGathers gather = beanFactory.getBean(beanName, ISlotListGathers.class);
            if (!Consts.GatherType.SLOT.equals((Object)gather.getGatherType())) continue;
            ISlotListGathers listGather = gather;
            List slots = listGather.gather();
            for (SlotItem slotItem : slots) {
                if (!Consts.SlotGatherType.BATCH_CHECK_MONITOR.equals((Object)slotItem.getType())) continue;
                return false;
            }
        }
        return true;
    }
}

