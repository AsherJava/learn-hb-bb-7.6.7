/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.AES128EncryptUtil
 */
package com.jiuqi.nvwa.login.encrypt;

import com.jiuqi.np.core.utils.AES128EncryptUtil;
import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NvwaLoginAes128EncryptProvider
implements NvwaLoginEncryptProvider {
    public static final Logger LOGGER = LoggerFactory.getLogger(NvwaLoginAes128EncryptProvider.class);

    @Override
    public String getType() {
        return "AES128";
    }

    @Override
    public String getAlias() {
        return "1";
    }

    @Override
    public String encrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            return AES128EncryptUtil.encrypt((String)content);
        }
        catch (Exception e) {
            LOGGER.error("AES128\u52a0\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            return AES128EncryptUtil.decrypt((String)content);
        }
        catch (Exception e) {
            LOGGER.error("AES128\u89e3\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }
}

