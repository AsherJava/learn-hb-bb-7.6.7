/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileCopyBaseParam;
import com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo;
import java.util.List;

public class FileCopyFixedParam
extends FileCopyBaseParam {
    private List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos;

    public FileCopyFixedParam() {
    }

    public FileCopyFixedParam(List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos) {
        this.fixedFieldAndGroupInfos = fixedFieldAndGroupInfos;
    }

    public FileCopyFixedParam(String fromTaskKey, List<String> oldFileGroupKeys, List<String> oldPicGroupKeys, String toTaskKey, String toFormSchemeKey, List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos) {
        super(fromTaskKey, oldFileGroupKeys, oldPicGroupKeys, toTaskKey, toFormSchemeKey);
        this.fixedFieldAndGroupInfos = fixedFieldAndGroupInfos;
    }

    public List<FixedFieldAndGroupInfo> getFixedFieldAndGroupInfos() {
        return this.fixedFieldAndGroupInfos;
    }

    public void setFixedFieldAndGroupInfos(List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos) {
        this.fixedFieldAndGroupInfos = fixedFieldAndGroupInfos;
    }
}

