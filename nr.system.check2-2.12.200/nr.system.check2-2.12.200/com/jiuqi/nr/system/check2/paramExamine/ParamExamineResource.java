/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.system.check2.paramExamine;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.paramExamine.ParamExamineAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ParamExamineResource
implements ICheckResource {
    public static final String PARAM_EXAMINE_RESOURCE_KEY = "resource-0000-0000-param-examine";
    public static final String PARAM_EXAMINE_RESOURCE_TITLE = "\u53c2\u6570\u68c0\u67e5";
    public static final String PARAM_EXAMINE_RESOURCE_MESSAGE = "\u8fd9\u662f\u53c2\u6570\u68c0\u67e5\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private ParamExamineAction paramExamineAction;

    public String getKey() {
        return PARAM_EXAMINE_RESOURCE_KEY;
    }

    public String getTitle() {
        return PARAM_EXAMINE_RESOURCE_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-param-tool";
    }

    public Double getOrder() {
        return 0.5;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return PARAM_EXAMINE_RESOURCE_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(PARAM_EXAMINE_RESOURCE_TITLE);
        tagMessages.add("\u53c2\u6570\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.paramExamineAction;
    }
}

