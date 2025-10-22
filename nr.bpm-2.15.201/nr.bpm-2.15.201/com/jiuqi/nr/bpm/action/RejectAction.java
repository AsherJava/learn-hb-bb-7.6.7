/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.upload.UploadState;
import org.springframework.stereotype.Component;

@Component(value="act_reject")
public class RejectAction
implements ActionBase {
    @Override
    public String getActionTitle() {
        return "\u9000\u56de";
    }

    @Override
    public String getActionCode() {
        return "cus_reject";
    }

    @Override
    public String getActionID() {
        return "5f9ebbeb-0a37-4a6d-84da-textaction02";
    }

    @Override
    public String getActionIcon() {
        return "#icon-_GJZtuihui";
    }

    @Override
    public String getStateCode() {
        return UploadState.REJECTED.toString();
    }

    @Override
    public String getStateName() {
        return "\u5df2\u9000\u56de";
    }
}

