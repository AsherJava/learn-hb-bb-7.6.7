/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.feign.context.BusinessFeignHeadersContext
 *  com.jiuqi.common.feign.context.BusinessFeignHeadersContextHolder
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.common.reportsync.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.feign.context.BusinessFeignHeadersContext;
import com.jiuqi.common.feign.context.BusinessFeignHeadersContextHolder;
import com.jiuqi.common.reportsync.api.MultilevelUserValidateClient;
import com.jiuqi.common.reportsync.api.ReportSyncTokenClient;
import com.jiuqi.common.reportsync.dto.MultilevelCheckParam;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.FeignUtil;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class CommonAuthUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonAuthUtils.class);

    public static boolean validateUserPassword(String baseUrl, String username, String password, String encryptType) {
        String token = null;
        try {
            MultilevelUserValidateClient validateClient = (MultilevelUserValidateClient)FeignUtil.getDynamicClient(MultilevelUserValidateClient.class, (String)baseUrl);
            MultilevelCheckParam param = new MultilevelCheckParam();
            param.setUserName(username);
            param.setPassword(password);
            param.setEncryptType(encryptType);
            token = (String)validateClient.checkPassword(param).getData();
        }
        catch (Exception e) {
            LOGGER.error("\u591a\u7ea7\u90e8\u7f72\u6821\u9a8c\u7528\u6237\u5bc6\u7801\u5931\u8d25,\u8c03\u7528\u65e7\u7248\u65b9\u6cd5\u83b7\u53d6", e);
            CommonAuthUtils.initNvwaFeignClientTokenEnv(baseUrl, username, password, encryptType);
            return true;
        }
        if (StringUtils.isEmpty((String)token)) {
            throw new BusinessRuntimeException("\u7528\u6237[" + username + "]\u83b7\u53d6\u670d\u52a1[" + baseUrl + "]\u4ee4\u724c\u5931\u8d25\uff0c\u8be6\u60c5\uff1a\u7528\u6237\u540d\u5bc6\u7801\u4e0d\u6b63\u786e");
        }
        BusinessFeignHeadersContext feignHeadersContext = new BusinessFeignHeadersContext();
        feignHeadersContext.putHeader("Authorization", new String[]{token});
        BusinessFeignHeadersContextHolder.setFeignHeadersContext((BusinessFeignHeadersContext)feignHeadersContext);
        return true;
    }

    public static NvwaLoginUserDTO initNvwaFeignClientTokenEnv(String baseUrl, String username, String password, String encryptType) {
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        NpContext context = NpContextHolder.getContext();
        userDTO.setEncrypted(!StringUtils.isEmpty((String)encryptType));
        userDTO.setEncryptType(encryptType);
        userDTO.setCheckPwd(false);
        userDTO.setTenant(context.getTenant());
        userDTO.setPwd(password);
        userDTO.setUsername(StringUtils.isEmpty((String)encryptType) ? username : CommonAuthUtils.getNvwaLoginEncryptProviderByEncryptType(encryptType).encrypt(username));
        ReportSyncTokenClient client = (ReportSyncTokenClient)FeignUtil.getDynamicClient(ReportSyncTokenClient.class, (String)baseUrl);
        String token = null;
        String msg = null;
        String chanceCount = null;
        try {
            R responseEntity = client.nvwaLogin(new URI(baseUrl), userDTO);
            token = ConverterUtils.getAsString((Object)responseEntity.get((Object)"token"), null);
            msg = ConverterUtils.getAsString((Object)responseEntity.get((Object)"msg"), (String)"");
            chanceCount = ConverterUtils.getAsString((Object)responseEntity.get((Object)"chance-count"), null);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u7f51\u7edc\u4e0d\u901a\uff0curl\uff1a" + baseUrl);
        }
        if (ObjectUtils.isEmpty(token)) {
            if (chanceCount != null) {
                msg = msg.replace("%s", chanceCount);
            }
            throw new BusinessRuntimeException("\u7528\u6237[" + username + "]\u83b7\u53d6\u670d\u52a1[" + baseUrl + "]\u4ee4\u724c\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + msg);
        }
        BusinessFeignHeadersContext feignHeadersContext = new BusinessFeignHeadersContext();
        feignHeadersContext.putHeader("Authorization", new String[]{token});
        BusinessFeignHeadersContextHolder.setFeignHeadersContext((BusinessFeignHeadersContext)feignHeadersContext);
        return userDTO;
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

