/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil
 */
package com.jiuqi.nr.data.checkdes.api;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.checkdes.api.ICKDImpValidator;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.internal.helper.Helper;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="com.jiuqi.nr.data.checkdes.api.DefCKDValidator")
public class DefCKDValidator
implements ICKDImpValidator {
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private Helper helper;

    @Override
    public CKDTransObj validate(CKDTransObj ckdTransObj, CKDImpMes ckdImpMes) {
        if (ckdTransObj != null) {
            int min = this.systemOptionUtil.getCheckCharNum();
            int max = this.systemOptionUtil.getCheckCharMaxNum();
            String checkDescription = ckdTransObj.getDescription();
            if (StringUtils.isEmpty((String)checkDescription)) {
                ImpFailedInfo failedInfo = this.helper.getImpFailedInfo(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u4e3a\u7a7a", ImpFailType.CHECK_FAIL);
                ckdImpMes.getFailedInfos().add(failedInfo);
            } else if (checkDescription.length() < min) {
                ImpFailedInfo failedInfo = this.helper.getImpFailedInfo(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u5b57\u7b26\u957f\u5ea6\u5c0f\u4e8e\u6700\u5c0f\u503c" + min, ImpFailType.CHECK_FAIL);
                ckdImpMes.getFailedInfos().add(failedInfo);
            } else if (checkDescription.length() > max) {
                ImpFailedInfo failedInfo = this.helper.getImpFailedInfo(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u957f\u5ea6\u5927\u4e8e\u6700\u5927\u503c" + max, ImpFailType.CHECK_FAIL);
                ckdImpMes.getFailedInfos().add(failedInfo);
            } else {
                return ckdTransObj;
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "com.jiuqi.nr.data.checkdes.api.DefCKDValidator";
    }
}

