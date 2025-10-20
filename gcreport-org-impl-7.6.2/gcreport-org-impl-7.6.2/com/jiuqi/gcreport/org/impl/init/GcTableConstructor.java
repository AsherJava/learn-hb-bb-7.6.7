/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.org.impl.init;

import com.jiuqi.common.base.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class GcTableConstructor {
    private static Logger logger = LoggerFactory.getLogger(GcTableConstructor.class);
    @Value(value="classpath:/config/vaorg/temp/GC_ORG_FIELD.templet.json")
    private Resource tempResource;
    private String templet;

    public String getTempFields() {
        if (StringUtils.isNull((String)this.templet)) {
            try (InputStream is = this.tempResource.getInputStream();){
                this.templet = IOUtils.toString((InputStream)is, (String)"UTF-8");
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return this.templet;
    }
}

