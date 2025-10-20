/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.RSAEncryptUtils
 */
package com.jiuqi.nvwa.login.encrypt;

import com.jiuqi.np.core.utils.RSAEncryptUtils;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NvwaLoginRsaEncryptProvider
implements NvwaLoginEncryptProvider {
    public static final Logger LOGGER = LoggerFactory.getLogger(NvwaLoginRsaEncryptProvider.class);

    @Override
    public String getType() {
        return "RSA";
    }

    @Override
    public String getAlias() {
        return "3";
    }

    @Override
    public String encrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            return RSAEncryptUtils.encrypt((String)content);
        }
        catch (Exception e) {
            LOGGER.error("RSA\u52a0\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            return RSAEncryptUtils.decrypt((String)content);
        }
        catch (Exception e) {
            LOGGER.error("RSA\u89e3\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }
}

