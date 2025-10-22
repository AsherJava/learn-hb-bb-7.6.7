/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.action.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.action.api.GcActionApi;
import com.jiuqi.gcreport.common.action.service.GcActionService;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcActionController
implements GcActionApi {
    private GcActionService getActionService() {
        GcActionService actionService = (GcActionService)SpringContextUtils.getBean(GcActionService.class);
        return actionService;
    }

    @Override
    @NRContextBuild
    public BusinessResponseEntity<Object> executeAction(@PathVariable(value="actionCode") String actionCode, @RequestBody(required=false) String actionParamJson, HttpServletRequest request) {
        Object executeActionResult = this.getActionService().executeAction(actionCode, actionParamJson);
        return BusinessResponseEntity.ok((Object)executeActionResult);
    }

    @Override
    public BusinessResponseEntity<Boolean> isVisibleAction(String actionCode, String visibleContextJson) {
        boolean isVisibleAction = this.getActionService().isVisibleAction(actionCode, visibleContextJson);
        return BusinessResponseEntity.ok((Object)isVisibleAction);
    }

    @Override
    public BusinessResponseEntity<Boolean> isEnableAction(String actionCode, String enableContextJson) {
        boolean isEnableAction = this.getActionService().isEnableAction(actionCode, enableContextJson);
        return BusinessResponseEntity.ok((Object)isEnableAction);
    }
}

