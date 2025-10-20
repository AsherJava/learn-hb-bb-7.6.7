/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.ext;

import com.jiuqi.nr.common.paramcheck.bean.ErrorParam;
import com.jiuqi.nr.common.paramcheck.common.ParamType;
import com.jiuqi.nr.common.paramcheck.ext.ErrorTypeEnum;
import com.jiuqi.nr.common.paramcheck.service.ACheckService;
import com.jiuqi.nr.common.paramcheck.service.CheckParam;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
@CheckParam(name="checkBDService", title="\u679a\u4e3e\u5b57\u5178\u8fd0\u884c\u53c2\u6570\u68c0\u67e5", type=ParamType.CUSTOM_PARAM)
public class CheckBDService
extends ACheckService {
    @Override
    public boolean execute() throws Exception {
        this.clearErrorData();
        ErrorParam error = new ErrorParam(UUID.randomUUID().toString(), ErrorTypeEnum.FORM_ERROR.getError(), "\u627e\u4e0d\u5230\u7684\u62a5\u8868.", "FMXX", "FMXX_FMDM", "/component/fixFormData");
        this.addErrorData(error);
        return false;
    }

    @Override
    public boolean fix(Object object) throws Exception {
        this.clearErrorData();
        return false;
    }
}

