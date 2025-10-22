/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.authorize.service.LicenceService
 */
package com.jiuqi.nr.definition.config;

import com.jiuqi.nr.authorize.service.LicenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ParamMaxNumberConfig {
    private static final Logger log = LoggerFactory.getLogger(ParamMaxNumberConfig.class);
    @Autowired
    private LicenceService licenceService;

    public int getTaskMaxNumber() {
        try {
            String taskNum = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.maxTaskNum");
            if (StringUtils.hasText(taskNum)) {
                return Integer.parseInt(taskNum);
            }
            return 0;
        }
        catch (Exception e) {
            log.error("\u4efb\u52a1\u6700\u5927\u6570\u91cf\u6388\u6743\u68c0\u67e5\u5931\u8d25\uff01\u9ed8\u8ba4\u5141\u8bb8\u521b\u5efa\u4efb\u52a1");
            return 0;
        }
    }

    public int getFormMaxNumber() {
        try {
            String formNum = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.maxFormNum");
            if (StringUtils.hasText(formNum)) {
                return Integer.parseInt(formNum);
            }
            return 0;
        }
        catch (Exception e) {
            log.error("\u62a5\u8868\u6700\u5927\u6570\u91cf\u6388\u6743\u68c0\u67e5\u5931\u8d25\uff01\u9ed8\u8ba4\u5141\u8bb8\u521b\u5efa\u62a5\u8868");
            return 0;
        }
    }
}

