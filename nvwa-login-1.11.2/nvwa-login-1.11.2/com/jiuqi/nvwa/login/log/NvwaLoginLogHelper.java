/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperResult
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 */
package com.jiuqi.nvwa.login.log;

import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperResult;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.shiro.MyNvwaExtendProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NvwaLoginLogHelper {
    @Autowired
    private NvwaSystemUserClient systemUserService;
    @Autowired
    private NvwaUserClient userService;
    @Autowired
    private MyNvwaExtendProvider myNvwaExtendProvider;
    private static final String moudle = "\u767b\u5f55\u6a21\u5757";

    public void forceLogout(NvwaContext context) {
        LogHelper.log((int)2, (String)moudle, (String)"\u9000\u51fa\u6210\u529f", (String)"\u5f3a\u5236\u9000\u51fa\u6210\u529f", (ContextUser)context.getConetxtUser(), (ContextOrganization)context.getContextOrg(), (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public void logout() {
        LogHelper.info((String)moudle, (String)"\u9000\u51fa\u6210\u529f", (String)"\u9000\u51fa\u6210\u529f", (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public void successLog(NvwaContext nvwaUser, String message) {
        LogHelper.log((int)2, (String)moudle, (String)"\u767b\u5f55\u6210\u529f", (String)message, (ContextUser)nvwaUser.getConetxtUser(), (ContextOrganization)nvwaUser.getContextOrg(), (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public void failLog(NvwaLoginUserDTO userDTO, User npUser, String message) {
        NvwaContextUser user = new NvwaContextUser();
        NvwaContextOrg org = new NvwaContextOrg();
        String userName = userDTO.getUsername();
        if (!StringUtils.hasLength(userName)) {
            user.setName("\u533f\u540d\u7528\u6237");
            LogHelper.log((int)3, (String)moudle, (String)"\u767b\u5f55\u5931\u8d25", (String)message, (ContextUser)user, (ContextOrganization)org, (OperResult)OperResult.FAIL, (OperLevel)OperLevel.USER_OPER);
            return;
        }
        if (null == npUser) {
            npUser = this.getUser(userName);
        }
        if (null != npUser) {
            user.setId(npUser.getId());
            user.setName(npUser.getName());
            user.setType(npUser.getUserType());
            user.setOrgCode(npUser.getOrgCode());
            String orgCode = npUser.getOrgCode();
            org.setCode(orgCode);
            if (StringUtils.hasLength(orgCode)) {
                org.setName(this.myNvwaExtendProvider.getOrgTitle(orgCode));
            }
        } else {
            user.setName(userName);
        }
        LogHelper.log((int)3, (String)moudle, (String)"\u767b\u5f55\u5931\u8d25", (String)message, (ContextUser)user, (ContextOrganization)org, (OperResult)OperResult.FAIL, (OperLevel)OperLevel.USER_OPER);
    }

    private User getUser(String username) {
        SystemUserDTO npUser = this.systemUserService.findByUsername(username);
        if (npUser == null) {
            npUser = this.userService.findByUsername(username);
        }
        return npUser;
    }
}

