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
package com.jiuqi.nr.system.check2.noDDLDeploy;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.noDDLDeploy.NODDLDeployAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class NODDLDeployResource
implements ICheckResource {
    public static final String NO_DDL_DEPLOY_KEY = "resource-no-ddl_deploy";
    public static final String NO_DDL_DEPLOY_TITLE = "\u65e0DDL\u6743\u9650\u53d1\u5e03";
    public static final String NO_DDL_DEPLOY_MESSAGE = "\u8fd9\u662f\u65e0DDL\u6743\u9650\u53d1\u5e03\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private NODDLDeployAction nODDLDeployAction;

    public String getKey() {
        return NO_DDL_DEPLOY_KEY;
    }

    public String getTitle() {
        return NO_DDL_DEPLOY_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-param-tool";
    }

    public Double getOrder() {
        return 5.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return NO_DDL_DEPLOY_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(NO_DDL_DEPLOY_TITLE);
        tagMessages.add("\u53c2\u6570\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.nODDLDeployAction;
    }
}

