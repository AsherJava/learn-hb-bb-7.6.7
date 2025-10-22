/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.TemplateItem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class SimpleTemplateImpl
implements TemplateItem {
    private static final Logger logger = LoggerFactory.getLogger(SimpleTemplateImpl.class);

    @Override
    public String getCode() {
        return "simpleTemplate";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getContent() {
        InputStream input = null;
        String content = "";
        try {
            ClassPathResource templateResource = new ClassPathResource("template/template_simple.json");
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

    @Override
    public String getDesc() {
        return "\u7b80\u5355\u5f55\u5165\u6a21\u677f";
    }

    @Override
    public String getTitle() {
        return "\u7b80\u5355\u5f55\u5165\u6a21\u677f";
    }
}

