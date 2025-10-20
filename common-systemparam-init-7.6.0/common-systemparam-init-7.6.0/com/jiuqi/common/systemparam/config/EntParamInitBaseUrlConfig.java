/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.systemparam.config;

import com.jiuqi.common.base.util.StringUtils;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntParamInitBaseUrlConfig {
    @Value(value="${ent.paramInit.basePath:}")
    private String paramInitBasePath;

    public String getParamInitBasePath() {
        if (StringUtils.isEmpty((String)this.paramInitBasePath)) {
            return this.paramInitBasePath;
        }
        if (!this.paramInitBasePath.endsWith(File.separator)) {
            this.paramInitBasePath = this.paramInitBasePath + File.separator;
        }
        return this.paramInitBasePath;
    }

    public void setParamInitBasePath(String paramInitBasePath) {
        this.paramInitBasePath = paramInitBasePath;
    }
}

