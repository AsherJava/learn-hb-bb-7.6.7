/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.gather.ISingletonGather;
import com.jiuqi.nr.dataentry.util.Consts;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class DefaultTemplateConfigProvider
implements ISingletonGather<String> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultTemplateConfigProvider.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String gather() {
        InputStream input = null;
        try {
            String content;
            ClassPathResource templateResource = new ClassPathResource("template/templateConfig_default.json");
            input = templateResource.getInputStream();
            String string = content = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
            return string;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return null;
    }

    @Override
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.DEFAULTTEMPLATE;
    }
}

