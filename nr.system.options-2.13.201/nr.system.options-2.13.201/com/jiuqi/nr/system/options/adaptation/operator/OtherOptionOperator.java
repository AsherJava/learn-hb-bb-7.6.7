/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.nr.system.options.adaptation.operator;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class OtherOptionOperator
extends SystemOptionOperator {
    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        ResultObject resultObject = null;
        if (!CollectionUtils.isEmpty(itemValues)) {
            for (ISystemOptionItemValue itemValue : itemValues) {
                String code;
                if (!"NET_SERVER_CODE".equals(itemValue.getKey()) || StringUtils.isEmpty(code = super.query("NET_SERVER_CODE")) || code.equals(itemValue.getValue())) continue;
                resultObject = new ResultObject(false, "\u670d\u52a1\u7801\u4e0d\u5141\u8bb8\u4fee\u6539\uff01");
            }
        }
        if (null != resultObject) {
            return resultObject;
        }
        return super.save(itemValues);
    }
}

