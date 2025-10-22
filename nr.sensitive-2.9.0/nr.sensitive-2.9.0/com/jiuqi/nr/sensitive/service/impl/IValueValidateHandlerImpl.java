/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.ValidateResult
 *  com.jiuqi.np.dataengine.intf.IValueValidateHandler
 */
package com.jiuqi.nr.sensitive.service.impl;

import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.service.impl.CheckSensitiveWordServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IValueValidateHandlerImpl
implements IValueValidateHandler {
    @Autowired
    private CheckSensitiveWordServiceImpl checkSensitiveWordService;

    public ValidateResult checkValue(String fieldName, int dataType, Object dataValue) {
        List<SensitiveWordDaoObject> sensitiveWordList;
        ValidateResult validateResult = new ValidateResult();
        if (dataType == 6 && dataValue != null && (sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(dataValue.toString())).size() > 0) {
            validateResult.setResultValue(false);
            validateResult.setResultMsg("\u6570\u636e\u4e2d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u4fdd\u5b58\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u540e\u91cd\u65b0\u4fdd\u5b58\uff01");
        }
        return validateResult;
    }
}

