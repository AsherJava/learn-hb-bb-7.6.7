/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.gcreport.common.systemoption.saveoperator;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrgFieldAutoCalcOperator
extends SystemOptionOperator {
    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        List values = itemValues.stream().map(iSystemOptionItemValue -> {
            SystemOptionItemValue itemValue = (SystemOptionItemValue)iSystemOptionItemValue;
            if (!StringUtils.isEmpty((String)iSystemOptionItemValue.getValue())) {
                itemValue.setValue(iSystemOptionItemValue.getValue().replaceAll("\uff1b", ";"));
            }
            return itemValue;
        }).collect(Collectors.toList());
        return super.save(values);
    }
}

