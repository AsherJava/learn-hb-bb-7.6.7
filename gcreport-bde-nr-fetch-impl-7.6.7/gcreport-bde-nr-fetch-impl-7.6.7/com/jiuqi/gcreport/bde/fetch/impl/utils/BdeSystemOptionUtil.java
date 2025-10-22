/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.bde.fetch.impl.utils;

import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BdeSystemOptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(BdeSystemOptionUtil.class);
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    public int getSystemOptionAndDefaultValue(String optionName, int defaultValue) {
        if (this.nvwaSystemOptionService == null) {
            return defaultValue;
        }
        String config = null;
        try {
            config = this.nvwaSystemOptionService.findValueById(optionName);
        }
        catch (NullPointerException e) {
            return defaultValue;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6BDE\u7cfb\u7edf\u9009\u9879\u5931\u8d25\u8fd4\u56de\u9ed8\u8ba4\u503c\uff0c\u5f02\u5e38\u4fe1\u606f:" + e.getMessage(), e);
            return defaultValue;
        }
        if (!StringUtils.hasLength(config)) {
            return defaultValue;
        }
        try {
            return Convert.toInt((String)config);
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean enableAutoFetch() {
        try {
            return "1".equals(this.nvwaSystemOptionService.findValueById("FINANCIAL_AUTO_FETCH"));
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6BDE\u7cfb\u7edf\u9009\u9879\u5931\u8d25\u8fd4\u56de\u9ed8\u8ba4\u503c\uff0c\u5f02\u5e38\u4fe1\u606f:" + e.getMessage(), e);
            return false;
        }
    }
}

