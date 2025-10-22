/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.nr.impl.template.impl;

import com.jiuqi.gcreport.nr.impl.template.AbstractGcTemplateItem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class TabTopTemplateImpl
extends AbstractGcTemplateItem {
    private static final Logger logger = LoggerFactory.getLogger(TabTopTemplateImpl.class);
    private static final String TEMPLATE_CODE = "tabTopTemplate";

    public String getCode() {
        return TEMPLATE_CODE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getContent() {
        InputStream input = null;
        String content = "";
        try {
            ClassPathResource templateResource = new ClassPathResource("template/template_tab_top.json");
            input = templateResource.getInputStream();
            content = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
            JSONObject contentJson = new JSONObject(content);
            content = contentJson.toString();
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
        return content;
    }

    public String getDesc() {
        return "\u9875\u7b7e\u5728\u4e0a\u6a21\u677f";
    }

    public String getTitle() {
        return "\u9875\u7b7e\u5728\u4e0a\u6a21\u677f";
    }
}

