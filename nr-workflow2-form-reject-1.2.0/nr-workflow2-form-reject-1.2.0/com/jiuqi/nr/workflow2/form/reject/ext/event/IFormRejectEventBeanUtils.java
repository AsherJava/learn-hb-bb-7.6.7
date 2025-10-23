/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 */
package com.jiuqi.nr.workflow2.form.reject.ext.event;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class IFormRejectEventBeanUtils
implements InitializingBean {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IProcessRuntimeParamHelper processRuntimeParamHelper;
    @Autowired
    private IProcessEntityQueryHelper processEntityQueryHelper;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IFormRejectQueryService formRejectQueryService;
    private static IFormRejectEventBeanUtils INSTANCE;

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static IRunTimeViewController getIRunTimeViewController() {
        return IFormRejectEventBeanUtils.INSTANCE.runTimeViewController;
    }

    public static IProcessRuntimeParamHelper getIProcessRuntimeParamHelper() {
        return IFormRejectEventBeanUtils.INSTANCE.processRuntimeParamHelper;
    }

    public static IProcessEntityQueryHelper getIProcessEntityQueryHelper() {
        return IFormRejectEventBeanUtils.INSTANCE.processEntityQueryHelper;
    }

    public static IProcessDimensionsBuilder getIProcessDimensionsBuilder() {
        return IFormRejectEventBeanUtils.INSTANCE.processDimensionsBuilder;
    }

    public static IProcessQueryService getIProcessQueryService() {
        return IFormRejectEventBeanUtils.INSTANCE.processQueryService;
    }

    public static IFormRejectQueryService getIFormRejectQueryService() {
        return IFormRejectEventBeanUtils.INSTANCE.formRejectQueryService;
    }
}

