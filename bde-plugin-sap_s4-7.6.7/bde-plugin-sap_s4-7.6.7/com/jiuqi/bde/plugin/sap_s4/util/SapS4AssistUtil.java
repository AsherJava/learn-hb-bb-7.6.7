/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.apache.commons.io.Charsets
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.bde.plugin.sap_s4.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4DefaultAssistPojo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class SapS4AssistUtil {
    @Value(value="classpath:/template/sapS4SchemeInitDefine.json")
    private Resource schemeInitTmplateDefine;
    private String schemeInitTmplateJson;

    public List<SapS4DefaultAssistPojo> getDefaultAssistList() {
        if (this.schemeInitTmplateJson == null) {
            try (BufferedInputStream br = new BufferedInputStream(this.schemeInitTmplateDefine.getInputStream());){
                this.schemeInitTmplateJson = IOUtils.toString((InputStream)br, (Charset)Charsets.toCharset((Charset)StandardCharsets.UTF_8));
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("SAP_S4\u63d2\u4ef6\u9ed8\u8ba4\u521d\u59cb\u5316\u65b9\u6848\u52a0\u8f7d\u5931\u8d25", (Throwable)e);
            }
        }
        return (List)JsonUtils.readValue((String)this.schemeInitTmplateJson, (TypeReference)new TypeReference<List<SapS4DefaultAssistPojo>>(){});
    }
}

