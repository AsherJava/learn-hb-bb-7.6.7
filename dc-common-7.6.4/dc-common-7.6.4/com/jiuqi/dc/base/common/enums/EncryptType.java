/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.common.base.util.JsonUtils;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;

public enum EncryptType {
    BASE64(data -> Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8)), encryptedData -> new String(Base64.getDecoder().decode((String)encryptedData), StandardCharsets.UTF_8));

    private final Function<String, String> encryptor;
    private final Function<String, String> decryptor;

    private EncryptType(Function<String, String> encryptor, Function<String, String> decryptor) {
        this.encryptor = encryptor;
        this.decryptor = decryptor;
    }

    public String encrypt(String data) {
        return this.encryptor.apply(data);
    }

    public String encrypt(Object data) {
        return this.encryptor.apply(JsonUtils.writeValueAsString((Object)data));
    }

    public String decrypt(String encryptedData) {
        return this.decryptor.apply(encryptedData);
    }

    public <T> T decrypt(String decryptedJson, Class<T> clazz) {
        return (T)JsonUtils.readValue((String)this.decryptor.apply(decryptedJson), clazz);
    }
}

