/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.va.domain.common.R
 *  org.apache.commons.lang3.ObjectUtils
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2UserService;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyThreadLocal;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.domain.common.R;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOAuth2UserServiceImpl
implements GcOAuth2UserService {
    @Autowired
    private NvwaLoginService loginService;

    @Override
    public R simulationLogin(String username, String state) {
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        userDTO.setUsername(username);
        userDTO.setTenant("__default_tenant__");
        userDTO.setCheckPwd(false);
        if (this.doesStateIsUiCode() && ObjectUtils.isNotEmpty((Object)state)) {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("UI_SCHEME_ID", state);
            userDTO.setExtInfo(extInfo);
        }
        return this.loginService.tryLogin(userDTO, true);
    }

    private boolean doesStateIsUiCode() {
        NvwaCertify nc = NvwaCertifyThreadLocal.get();
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
        return ext.getStateIsUiCode() != null ? ext.getStateIsUiCode() : false;
    }
}

