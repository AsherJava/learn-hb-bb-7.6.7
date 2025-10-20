/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.ent.bamboocloud.bim.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.GeneralDTO;
import com.jiuqi.ent.bamboocloud.bim.util.BimUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BimHelper {
    private static final Logger logger = LoggerFactory.getLogger(BimHelper.class);
    @Autowired
    private BimProperties properties;

    private Map<String, Object> getPlaintextAndVerifySignature(HttpServletRequest request, String key) {
        String plainText = this.properties.isTransEncode() ? BimUtils.transDecode(BimUtils.getRequestBody(request), key, this.properties.getTransAlgorithm()) : BimUtils.getRequestBody(request);
        Assert.hasText(plainText, "\u83b7\u53d6\u5230\u7684\u660e\u6587\u4e3a\u7a7a\u3002");
        Map param = null;
        try {
            param = (Map)JsonUtils.readValue((String)plainText, (TypeReference)new TypeReference<Map<String, Object>>(){});
        }
        catch (Exception e) {
            logger.error("\u660e\u6587\u8f6cmap\u5931\u8d25\u3002plainText=" + plainText, e);
            throw new RuntimeException("\u660e\u6587\u8f6cmap\u5931\u8d25\u3002\u9519\u8bef\u4fe1\u606f:" + e.getMessage());
        }
        if (this.properties.isTransEncode() && !BimUtils.verifySignature(param, this.properties.getVerifyAlgorithm())) {
            logger.error("BIM\u6570\u636e\u540c\u6b65\u6821\u9a8c\u7b7e\u540d\u5931\u8d25\uff0cplainText=[{}]", (Object)plainText);
            throw new RuntimeException("BIM\u6570\u636e\u540c\u6b65\u6821\u9a8c\u7b7e\u540d\u5931\u8d25\uff0cplainText=[" + plainText + "]");
        }
        return param;
    }

    public Map<String, Object> validate(HttpServletRequest request) {
        Map<String, Object> param = this.getPlaintextAndVerifySignature(request, this.properties.getKey());
        Assert.hasText(this.properties.getBimRemoteUser(), "\u4e1a\u52a1\u7cfb\u7edf\u672a\u914d\u7f6ebimRemoteUser");
        Assert.hasText(this.properties.getBimRemotePwd(), "\u4e1a\u52a1\u7cfb\u7edf\u672a\u914d\u7f6ebimRemotePwd");
        Assert.isTrue(StringUtils.equals((CharSequence)this.properties.getBimRemoteUser(), (CharSequence)((String)param.get("bimRemoteUser"))), "bimRemoteUser\u6821\u9a8c\u4e0d\u901a\u8fc7");
        Assert.isTrue(StringUtils.equals((CharSequence)this.properties.getBimRemotePwd(), (CharSequence)((String)param.get("bimRemotePwd"))), "bimRemotePwd\u6821\u9a8c\u4e0d\u901a\u8fc7");
        return param;
    }

    public String encrypt(String plaintext) {
        if (!this.properties.isTransEncode()) {
            return plaintext;
        }
        if (this.properties.isTransEncode() && StringUtils.isNotBlank((CharSequence)this.properties.getKey())) {
            return BimUtils.transEncode(plaintext, this.properties.getKey(), this.properties.getTransAlgorithm());
        }
        logger.error("\u7cfb\u7edf\u5f53\u524d\u672a\u8bbe\u7f6e\u7af9\u4e91\u540c\u6b65key\uff0c\u65e0\u6cd5\u8fdb\u884c\u6570\u636e\u540c\u6b65!");
        return null;
    }

    public String errorJson(String bimRequestId, Exception e) {
        GeneralDTO response = new GeneralDTO(bimRequestId, "500", e.getMessage());
        return JsonUtils.writeValueAsString((Object)response);
    }

    public String getBimRequestId(Map<String, Object> params) {
        return (String)params.get("bimRequestId");
    }

    public void setSyncProperties(BimProperties properties) {
        this.properties = properties;
    }
}

