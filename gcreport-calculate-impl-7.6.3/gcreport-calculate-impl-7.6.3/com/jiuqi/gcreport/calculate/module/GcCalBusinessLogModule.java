/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.web.module.BusinessLogModule
 *  com.jiuqi.common.web.module.BusinessLogModuleOperate
 */
package com.jiuqi.gcreport.calculate.module;

import com.jiuqi.common.web.module.BusinessLogModule;
import com.jiuqi.common.web.module.BusinessLogModuleOperate;
import org.springframework.stereotype.Component;

@Component
public class GcCalBusinessLogModule
extends BusinessLogModule<GcCalBusinessModuleOperateEnum> {
    public GcCalBusinessLogModule() {
        super("\u5408\u5e76\u8ba1\u7b97", (Enum[])GcCalBusinessModuleOperateEnum.values());
    }

    public static enum GcCalBusinessModuleOperateEnum implements BusinessLogModuleOperate
    {
        INTO_CALC("\u7528\u6237\u8fdb\u5165\u5408\u5e76\u8ba1\u7b97\u529f\u80fd"),
        RELATION_TO_MERGE("\u7528\u6237\u6267\u884c\u5173\u8054\u8f6c\u5408\u5e76"),
        RUN_CALC("\u7528\u6237\u6267\u884c\u5f00\u59cb\u5408\u5e76\u8ba1\u7b97");

        private String moduleOperate;

        private GcCalBusinessModuleOperateEnum(String moduleOperate) {
            this.moduleOperate = moduleOperate;
        }

        public String getModuleOperate() {
            return this.moduleOperate;
        }
    }
}

