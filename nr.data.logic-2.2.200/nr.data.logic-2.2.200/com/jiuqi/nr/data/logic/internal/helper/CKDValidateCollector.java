/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult;
import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidator;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CKDValResult;
import com.jiuqi.nr.data.logic.internal.obj.CKDValidInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

public class CKDValidateCollector {
    private final List<ICheckDesValidator> validators = new ArrayList<ICheckDesValidator>();
    private final ContentCheckByGroupService contentCheckService;
    private final String ruleGroupKey;

    public CKDValidateCollector(@NonNull List<ICheckDesValidatorProvider> checkDesValidatorProviders, @NonNull ContentCheckByGroupService contentCheckByGroupService, @NonNull String ruleGroupKey, @NonNull CheckDesContext context) {
        this.contentCheckService = contentCheckByGroupService;
        this.ruleGroupKey = ruleGroupKey;
        for (ICheckDesValidatorProvider validatorProvider : checkDesValidatorProviders) {
            List<ICheckDesValidator> desValidators = validatorProvider.getValidators(context);
            if (CollectionUtils.isEmpty(desValidators)) continue;
            this.validators.addAll(desValidators);
        }
    }

    public boolean validPass(CheckDesObj checkDesObj) {
        if (CKDValidateCollector.ckdIsEmpty(checkDesObj)) {
            return false;
        }
        ContentCheckResult checkResult = this.contentCheckService.check(checkDesObj.getCheckDescription().getDescription(), this.ruleGroupKey);
        if (checkResult != null && !checkResult.getStatus()) {
            return false;
        }
        for (ICheckDesValidator validator : this.validators) {
            if (validator.validate(checkDesObj).isPass()) continue;
            return false;
        }
        return true;
    }

    public CKDValidInfo getValidInfo(CheckDesObj checkDesObj) {
        CKDValidInfo ckdValidInfo = new CKDValidInfo();
        ArrayList<String> errorMsgList = new ArrayList<String>();
        if (CKDValidateCollector.ckdIsEmpty(checkDesObj)) {
            errorMsgList.add("\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            ckdValidInfo.setPass(false);
            ckdValidInfo.setErrorMsgList(errorMsgList);
            return ckdValidInfo;
        }
        boolean pass = true;
        ContentCheckResult checkResult = this.contentCheckService.check(checkDesObj.getCheckDescription().getDescription(), this.ruleGroupKey);
        if (checkResult != null && !checkResult.getStatus()) {
            pass = false;
            errorMsgList.addAll(checkResult.getMessages());
        }
        for (ICheckDesValidator validator : this.validators) {
            CKDValResult validate = validator.validate(checkDesObj);
            if (validate.isPass()) continue;
            pass = false;
            errorMsgList.add(validate.getMessage());
        }
        ckdValidInfo.setPass(pass);
        ckdValidInfo.setErrorMsgList(errorMsgList);
        return ckdValidInfo;
    }

    private static boolean ckdIsEmpty(CheckDesObj checkDesObj) {
        return checkDesObj == null || checkDesObj.getCheckDescription() == null || StringUtils.isEmpty((String)checkDesObj.getCheckDescription().getDescription());
    }
}

