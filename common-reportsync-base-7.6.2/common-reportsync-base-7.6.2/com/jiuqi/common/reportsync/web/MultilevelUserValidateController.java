/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.reportsync.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.reportsync.api.MultilevelUserValidateClient;
import com.jiuqi.common.reportsync.dto.MultilevelCheckParam;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.R;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultilevelUserValidateController
implements MultilevelUserValidateClient {
    @Autowired
    private NvwaUserClient userClient;
    @Autowired
    private NvwaSystemUserClient systemUserClient;
    @Autowired
    private NvwaPasswordClient passwordClient;

    @Override
    public BusinessResponseEntity<String> checkPassword(MultilevelCheckParam param) {
        String userName = param.getUserName();
        String password = param.getPassword();
        String encryptType = param.getEncryptType();
        if (StringUtils.isEmpty((String)userName) || StringUtils.isEmpty((String)password)) {
            return BusinessResponseEntity.error((String)"\u7528\u6237\u540d\u6216\u5bc6\u7801\u4e3a\u7a7a");
        }
        String userId = null;
        UserDTO userDTO = this.userClient.findByUsername(userName);
        if (userDTO != null) {
            userId = userDTO.getId();
        } else {
            SystemUserDTO systemUserDTO = this.systemUserClient.findByUsername(userName);
            if (systemUserDTO == null) {
                return BusinessResponseEntity.error((String)("\u5f53\u524d\u7cfb\u7edf\u672a\u627e\u5230\u7528\u6237\u540d\u4e3a\uff1a" + userName + "\u7684\u7528\u6237"));
            }
            userId = systemUserDTO.getId();
        }
        String userPassword = null;
        userPassword = StringUtils.isEmpty((String)encryptType) ? password : MultilevelUserValidateController.getNvwaLoginEncryptProviderByEncryptType(encryptType).decrypt(password);
        Boolean pd = this.passwordClient.validate(userId, userPassword);
        String ssoToken = null;
        if (pd.booleanValue()) {
            NvwaLoginService loginService = (NvwaLoginService)SpringContextUtils.getBean(NvwaLoginService.class);
            NvwaLoginUserDTO nvwaLoginUserDTO = new NvwaLoginUserDTO();
            nvwaLoginUserDTO.setUsername(userName);
            nvwaLoginUserDTO.setTenant("__default_tenant__");
            nvwaLoginUserDTO.setCheckPwd(false);
            R r = loginService.tryLogin(nvwaLoginUserDTO, true);
            ssoToken = (String)r.get((Object)"token");
        }
        return BusinessResponseEntity.ok(ssoToken);
    }

    public static NvwaLoginEncryptProvider getNvwaLoginEncryptProviderByEncryptType(String encryptType) {
        Collection nvwaLoginEncryptProviders = SpringContextUtils.getBeans(NvwaLoginEncryptProvider.class);
        Optional<NvwaLoginEncryptProvider> encryptProvider = nvwaLoginEncryptProviders.stream().filter(nvwaLoginEncryptProvider -> nvwaLoginEncryptProvider.getType().equals(encryptType)).findAny();
        if (!encryptProvider.isPresent()) {
            throw new BusinessRuntimeException("\u6682\u672a\u652f\u6301\u52a0\u5bc6\u7b97\u6cd5\uff1a" + encryptType);
        }
        return encryptProvider.get();
    }
}

