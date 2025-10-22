/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.upload.UploadState;
import org.springframework.stereotype.Component;

@Component(value="act_submit")
public class SubmitAction
implements ActionBase {
    @Override
    public String getActionTitle() {
        return "\u9001\u5ba1";
    }

    @Override
    public String getActionCode() {
        return "cus_submit";
    }

    @Override
    public String getActionID() {
        return "5f9ebbeb-0a37-4a6d-84da-actsubmit002";
    }

    @Override
    public String getActionIcon() {
        return "#icon-_GJHsongshen";
    }

    @Override
    public String getStateCode() {
        return UploadState.SUBMITED.toString();
    }

    @Override
    public String getStateName() {
        return "\u5df2\u9001\u5ba1";
    }
}

