/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.condition;

import com.jiuqi.nr.dataentry.gather.ISingletonGather;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DefaultTemplateCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String[] beanNamesForType;
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for (String name : beanNamesForType = beanFactory.getBeanNamesForType(ISingletonGather.class)) {
            ISingletonGather bean = beanFactory.getBean(name, ISingletonGather.class);
            if (!bean.getGatherType().getCode().equals(Consts.GatherType.DEFAULTTEMPLATE.getCode())) continue;
            return false;
        }
        return true;
    }
}

