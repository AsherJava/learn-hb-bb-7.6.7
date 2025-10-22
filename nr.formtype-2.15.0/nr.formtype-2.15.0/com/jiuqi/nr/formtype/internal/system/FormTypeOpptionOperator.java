/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.nr.formtype.internal.system;

import com.jiuqi.nr.formtype.internal.system.FormTypeOptionsService;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTypeOpptionOperator
extends SystemOptionOperator {
    @Autowired
    private FormTypeOptionsService formTypeOptionsService;
    private static final Logger logger = LoggerFactory.getLogger(FormTypeOpptionOperator.class);

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        for (ISystemOptionItemValue iSystemOptionItemValue : itemValues) {
            if (!"formtype_option_gzwms".equals(iSystemOptionItemValue.getKey()) || "1".equals(iSystemOptionItemValue.getValue()) || !this.formTypeOptionsService.enableNrFormTypeMgr()) continue;
            logger.error("\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\uff1a\u56fd\u8d44\u59d4\u6a21\u5f0f\u4e00\u65e6\u542f\u7528\uff0c\u7981\u6b62\u53d6\u6d88");
            throw new RuntimeException("\u56fd\u8d44\u59d4\u6a21\u5f0f\u4e00\u65e6\u542f\u7528\uff0c\u7981\u6b62\u53d6\u6d88\uff01");
        }
        return super.save(itemValues);
    }
}

