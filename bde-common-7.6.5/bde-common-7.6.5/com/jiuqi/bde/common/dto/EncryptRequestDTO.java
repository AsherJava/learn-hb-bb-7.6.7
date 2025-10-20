/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bi.util.Base64;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncryptRequestDTO<T> {
    private String encryptType;
    private String params;

    public EncryptRequestDTO() {
    }

    public EncryptRequestDTO(T t) {
        Assert.isNotNull(t);
        this.params = this.encode(t instanceof String ? (String)t : JsonUtils.writeValueAsString(t), StandardCharsets.UTF_8);
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public T parseParam(Class<T> clazz) {
        Assert.isNotEmpty((String)this.params);
        if (clazz == String.class) {
            return (T)this.decode(this.params, StandardCharsets.UTF_8);
        }
        return (T)JsonUtils.readValue((String)this.decode(this.params, StandardCharsets.UTF_8), clazz);
    }

    public String toString() {
        return "EncryptRequestDTO [encryptType=" + this.encryptType + ", params=" + this.params + "]";
    }

    public String encode(String str, Charset charset) {
        return Base64.byteArrayToBase64((byte[])str.getBytes(charset));
    }

    public String decode(String str, Charset charset) {
        byte[] result = "".getBytes();
        if (str != null && str.length() > 0) {
            result = Base64.base64ToByteArray((String)str);
        }
        return new String(result, charset);
    }
}

