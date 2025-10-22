/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.function.func.ExistChinese
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidator;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CKDValResult;
import com.jiuqi.nr.data.logic.internal.obj.CKDCheckSetting;
import com.jiuqi.nr.data.logic.internal.service.impl.DefCKDValidator;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.function.func.ExistChinese;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
@Deprecated
public class DefCKDValidatorProvider
implements ICheckDesValidatorProvider {
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    private static final String NONNULL_VAL_CODE = "NR-1";
    private static final String LENGTH_VAL_CODE = "NR-2";
    private static final String CHN_VAL_CODE = "NR-3";

    @Override
    public List<ICheckDesValidator> getValidators(CheckDesContext context) {
        return Collections.emptyList();
    }

    private ICheckDesValidator getNonNullValidator(CKDCheckSetting ckdCheckSetting) {
        return new DefCKDValidator(ckdCheckSetting){

            @Override
            public CKDValResult validate(CheckDesObj checkDesObj) {
                boolean pass = true;
                String msg = null;
                if (DefCKDValidatorProvider.ckdIsEmpty(checkDesObj)) {
                    pass = false;
                    msg = "\u4e0d\u5141\u8bb8\u4e3a\u7a7a";
                }
                CKDValResult ckdValResult = new CKDValResult();
                ckdValResult.setPass(pass);
                ckdValResult.setMessage(msg);
                return ckdValResult;
            }

            @Override
            public String getUniqueCode() {
                return DefCKDValidatorProvider.NONNULL_VAL_CODE;
            }
        };
    }

    private static boolean ckdIsEmpty(CheckDesObj checkDesObj) {
        return checkDesObj == null || checkDesObj.getCheckDescription() == null || StringUtils.isEmpty((String)checkDesObj.getCheckDescription().getDescription());
    }

    private ICheckDesValidator getLengthValidator(CKDCheckSetting ckdCheckSetting) {
        return new DefCKDValidator(ckdCheckSetting){

            @Override
            public CKDValResult validate(CheckDesObj checkDesObj) {
                boolean pass;
                String msg = null;
                int minNum = this.ckdCheckSetting.getMinNum();
                int maxNum = this.ckdCheckSetting.getMaxNum();
                if (DefCKDValidatorProvider.ckdIsEmpty(checkDesObj) && minNum > 0) {
                    pass = false;
                    msg = "\u4e0d\u5141\u8bb8\u5c11\u4e8e" + minNum + "\u4e2a\u5b57";
                } else {
                    String description = checkDesObj.getCheckDescription().getDescription();
                    int length = description.length();
                    if (length < minNum) {
                        pass = false;
                        msg = "\u4e0d\u5141\u8bb8\u5c11\u4e8e" + minNum + "\u4e2a\u5b57";
                    } else if (length > maxNum) {
                        pass = false;
                        msg = "\u4e0d\u5141\u8bb8\u591a\u4e8e" + maxNum + "\u4e2a\u5b57";
                    } else {
                        pass = true;
                    }
                }
                CKDValResult ckdValResult = new CKDValResult();
                ckdValResult.setPass(pass);
                ckdValResult.setMessage(msg);
                return ckdValResult;
            }

            @Override
            public String getUniqueCode() {
                return DefCKDValidatorProvider.LENGTH_VAL_CODE;
            }
        };
    }

    private ICheckDesValidator getCHNValidator(CKDCheckSetting ckdCheckSetting) {
        return new DefCKDValidator(ckdCheckSetting){

            @Override
            public CKDValResult validate(CheckDesObj checkDesObj) {
                boolean pass = true;
                String msg = null;
                if (this.ckdCheckSetting.isExistChinese()) {
                    if (DefCKDValidatorProvider.ckdIsEmpty(checkDesObj)) {
                        pass = false;
                        msg = "\u5fc5\u987b\u5305\u542b\u6c49\u5b57";
                    } else {
                        String description = checkDesObj.getCheckDescription().getDescription();
                        if (!ExistChinese.existChinese((String)description)) {
                            pass = false;
                            msg = "\u5fc5\u987b\u5305\u542b\u6c49\u5b57";
                        }
                    }
                }
                CKDValResult ckdValResult = new CKDValResult();
                ckdValResult.setPass(pass);
                ckdValResult.setMessage(msg);
                return ckdValResult;
            }

            @Override
            public String getUniqueCode() {
                return DefCKDValidatorProvider.CHN_VAL_CODE;
            }
        };
    }
}

