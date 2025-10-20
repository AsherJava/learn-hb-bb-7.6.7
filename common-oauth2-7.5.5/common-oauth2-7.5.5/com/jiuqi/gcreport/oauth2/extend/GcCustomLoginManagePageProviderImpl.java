/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.nvwa.framework.nros.extend.login.impl.NvwaLoginManagePageProviderImpl
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.gcreport.oauth2.extend;

import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.nvwa.framework.nros.extend.login.impl.NvwaLoginManagePageProviderImpl;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component(value="GcCustomLoginManagePageProviderImpl")
@Order(value=-2147483298)
public class GcCustomLoginManagePageProviderImpl
extends NvwaLoginManagePageProviderImpl {
    private static final String ALLOW_ALL_WILDCARD = "*";
    @Lazy
    @Autowired
    private NvwaSystemUserClient systemUserClient;
    @Lazy
    @Autowired(required=false)
    private List<NvwaLoginManagePageProviderImpl> nvwaLoginManagePageProviders;
    private Set<String> loginManageUsers = new CopyOnWriteArraySet<String>();
    private Boolean allowAll = false;

    @Autowired
    public GcCustomLoginManagePageProviderImpl(@Value(value="${oauth2.loginManageUsers:}") String manageUsers) {
        String[] split;
        if (StringUtils.hasLength(manageUsers) && (split = manageUsers.split(",")).length > 0) {
            if (ALLOW_ALL_WILDCARD.equals(split[0])) {
                this.allowAll = true;
            } else {
                Collections.addAll(this.loginManageUsers, split);
            }
        }
    }

    public R loginBefore(NvwaLoginUserDTO userDTO) {
        SystemUserDTO npUser;
        String username;
        Object extInfo;
        if (!CollectionUtils.isEmpty(this.nvwaLoginManagePageProviders)) {
            return R.ok();
        }
        if (userDTO.isCheckPwd() && null != (extInfo = userDTO.getExtInfo("loginManage")) && "true".equals(extInfo.toString()) && StringUtils.hasLength(username = userDTO.getUsername()) && !this.allowAll.booleanValue() && !this.loginManageUsers.contains(username) && null == (npUser = this.systemUserClient.findByUsername(username))) {
            R r = R.ok();
            r.put("code", (Object)450);
            r.put("msg", (Object)"\u5f53\u524d\u9875\u9762\u53ea\u5141\u8bb8\u7cfb\u7edf\u7ba1\u7406\u5458\u767b\u5f55\uff01");
            return r;
        }
        return R.ok();
    }
}

