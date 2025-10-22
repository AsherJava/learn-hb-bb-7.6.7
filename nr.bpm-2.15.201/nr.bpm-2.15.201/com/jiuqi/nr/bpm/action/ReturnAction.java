/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.upload.UploadState;
import org.springframework.stereotype.Component;

@Component(value="act_return")
public class ReturnAction
implements ActionBase {
    @Override
    public String getActionTitle() {
        return "\u9000\u5ba1";
    }

    @Override
    public String getActionCode() {
        return "cus_return";
    }

    @Override
    public String getActionID() {
        return "5f9ebbeb-0a37-4a6d-84da-actreturn003";
    }

    @Override
    public String getActionIcon() {
        return "#icon-_GJHtuishen";
    }

    @Override
    public String getStateCode() {
        return UploadState.RETURNED.toString();
    }

    @Override
    public String getStateName() {
        return "\u5df2\u9000\u5ba1";
    }
}

