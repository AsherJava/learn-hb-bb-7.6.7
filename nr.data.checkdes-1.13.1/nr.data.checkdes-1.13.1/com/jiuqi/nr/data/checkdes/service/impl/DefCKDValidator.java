/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.service.ICKDImpValidator;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="defCKDValidator")
public class DefCKDValidator
implements ICKDImpValidator {
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private CommonUtil commonUtil;

    @Override
    public CKDTransObj validate(CKDTransObj ckdTransObj, List<CKDImpDetails> ckdImpDetails) {
        if (ckdTransObj != null) {
            int min = this.systemOptionUtil.getCheckCharNum();
            int max = this.systemOptionUtil.getCheckCharMaxNum();
            String checkDescription = ckdTransObj.getDescription();
            if (StringUtils.isEmpty((String)checkDescription)) {
                CKDImpDetails detail = this.commonUtil.getImpDetail(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u4e3a\u7a7a");
                ckdImpDetails.add(detail);
            } else if (checkDescription.length() < min) {
                CKDImpDetails detail = this.commonUtil.getImpDetail(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u5b57\u7b26\u957f\u5ea6\u5c0f\u4e8e\u6700\u5c0f\u503c" + min);
                ckdImpDetails.add(detail);
            } else if (checkDescription.length() > max) {
                CKDImpDetails detail = this.commonUtil.getImpDetail(ckdTransObj, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u957f\u5ea6\u5927\u4e8e\u6700\u5927\u503c" + max);
                ckdImpDetails.add(detail);
            } else {
                return ckdTransObj;
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "defCKDValidator";
    }
}

