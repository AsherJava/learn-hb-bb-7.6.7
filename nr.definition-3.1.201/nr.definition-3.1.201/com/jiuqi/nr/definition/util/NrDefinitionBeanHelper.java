/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NrDefinitionBeanHelper {
    private static IRunTimeViewController runTimeViewController;
    private static IRuntimeFormService runtimeFormService;

    @Autowired
    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        NrDefinitionBeanHelper.runTimeViewController = runTimeViewController;
    }

    public static IRunTimeViewController getRunTimeViewController() {
        return runTimeViewController;
    }

    @Autowired
    public void setRuntimeFormService(IRuntimeFormService runtimeFormService) {
        NrDefinitionBeanHelper.runtimeFormService = runtimeFormService;
    }

    public static IRuntimeFormService getRuntimeFormService() {
        return runtimeFormService;
    }
}

