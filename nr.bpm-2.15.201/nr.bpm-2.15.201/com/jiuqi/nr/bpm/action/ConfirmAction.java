/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.upload.UploadState;
import org.springframework.stereotype.Component;

@Component(value="act_confirm")
public class ConfirmAction
implements ActionBase {
    @Override
    public String getActionTitle() {
        return "\u786e\u8ba4";
    }

    @Override
    public String getActionCode() {
        return "cus_confirm";
    }

    @Override
    public String getActionID() {
        return "5f9ebbeb-0a37-4a6d-84da-actconfirm03";
    }

    @Override
    public String getActionIcon() {
        return "#icon-_GJHqueren";
    }

    @Override
    public String getStateCode() {
        return UploadState.CONFIRMED.toString();
    }

    @Override
    public String getStateName() {
        return "\u5df2\u786e\u8ba4";
    }
}

