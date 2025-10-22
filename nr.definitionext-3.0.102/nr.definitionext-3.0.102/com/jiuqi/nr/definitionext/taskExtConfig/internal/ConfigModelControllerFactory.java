/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.internal;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConfigModelControllerFactory {
    @Autowired
    ApplicationContext applicationContext;

    public IConfigModelController getImpl(String type) {
        Map<String, IConfigModelController> map = this.applicationContext.getBeansOfType(IConfigModelController.class);
        for (Map.Entry<String, IConfigModelController> entry : map.entrySet()) {
            IConfigModelController impl = entry.getValue();
            if (!impl.handled(type)) continue;
            return impl;
        }
        return null;
    }
}

