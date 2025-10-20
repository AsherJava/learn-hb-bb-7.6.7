/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.codec.Base64
 */
package com.jiuqi.nvwa.login.encrypt;

import com.jiuqi.nvwa.login.provider.NvwaLoginEncryptProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NvwaLogin2Base64EncryptProvider
implements NvwaLoginEncryptProvider {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final Logger LOGGER = LoggerFactory.getLogger(NvwaLogin2Base64EncryptProvider.class);

    @Override
    public String getType() {
        return "Base64_2";
    }

    @Override
    public String getAlias() {
        return "2";
    }

    @Override
    public String encrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            byte[] bytes = content.getBytes(CHARSET);
            byte[] encodes = Base64.encode((byte[])Base64.encode((byte[])bytes));
            return new String(encodes, CHARSET);
        }
        catch (Exception e) {
            LOGGER.error("Base64_2\u52a0\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String content) {
        if (!StringUtils.hasLength(content)) {
            return "";
        }
        try {
            byte[] bytes = content.getBytes(CHARSET);
            byte[] encodes = Base64.decode((byte[])Base64.decode((byte[])bytes));
            return new String(encodes, CHARSET);
        }
        catch (Exception e) {
            LOGGER.error("Base64_2\u89e3\u5bc6\u62a5\u9519\uff01", e);
            throw new RuntimeException(e);
        }
    }
}

