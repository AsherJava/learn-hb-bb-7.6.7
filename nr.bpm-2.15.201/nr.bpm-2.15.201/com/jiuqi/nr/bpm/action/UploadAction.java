/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.upload.UploadState;
import org.springframework.stereotype.Component;

@Component(value="act_upload")
public class UploadAction
implements ActionBase {
    @Override
    public String getActionTitle() {
        return "\u4e0a\u62a5";
    }

    @Override
    public String getActionCode() {
        return "cus_upload";
    }

    @Override
    public String getActionID() {
        return "5f9ebbeb-0a37-4a6d-84da-textaction00";
    }

    @Override
    public String getActionIcon() {
        return "#icon-_GJHshangbao";
    }

    @Override
    public String getStateCode() {
        return UploadState.UPLOADED.toString();
    }

    @Override
    public String getStateName() {
        return "\u5df2\u4e0a\u62a5";
    }
}

