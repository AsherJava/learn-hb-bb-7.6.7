/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.util.CommonAuthUtils
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.login.encrypt.NvwaLogin2Base64EncryptProvider
 *  com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.util.CommonAuthUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.login.encrypt.NvwaLogin2Base64EncryptProvider;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportDataSyncAuthUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncAuthUtils.class);

    public static boolean initNvwaFeignClientTokenEnv(ReportDataSyncServerInfoVO serverInfoVO) {
        String encryptType;
        String password;
        String username;
        String baseUrl = serverInfoVO.getUrl();
        boolean pd = CommonAuthUtils.validateUserPassword((String)baseUrl, (String)(username = serverInfoVO.getUserName()), (String)(password = serverInfoVO.getPwd()), (String)(encryptType = serverInfoVO.getEncryptType()));
        if (!pd) {
            throw new BusinessRuntimeException("\u7528\u6237[" + username + "]\u83b7\u53d6\u670d\u52a1[" + baseUrl + "]\u4ee4\u724c\u5931\u8d25\uff0c\u53ef\u80fd\u662f\u7f51\u7edc\u4e0d\u901a\u6216\u76ee\u6807\u670d\u52a1\u5668\u8d26\u53f7\u5bc6\u7801\u4e0d\u6b63\u786e");
        }
        return true;
    }

    public static User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((String)userName)) {
            return null;
        }
        NvwaUserClient userService = (NvwaUserClient)SpringContextUtils.getBean(NvwaUserClient.class);
        NvwaSystemUserClient systemUserService = (NvwaSystemUserClient)SpringContextUtils.getBean(NvwaSystemUserClient.class);
        UserDTO username = userService.findByUsername(userName);
        if (null != username) {
            return username;
        }
        SystemUserDTO systemUserDTO = systemUserService.findByUsername(userName);
        if (null != systemUserDTO) {
            return systemUserDTO;
        }
        return null;
    }

    public static NvwaLoginEncryptProvider getNvwaLoginEncryptProvider() {
        NvwaLoginEncryptProvider nvwaLoginEncryptProvider = (NvwaLoginEncryptProvider)SpringContextUtils.getBean(NvwaLogin2Base64EncryptProvider.class);
        return nvwaLoginEncryptProvider;
    }
}

