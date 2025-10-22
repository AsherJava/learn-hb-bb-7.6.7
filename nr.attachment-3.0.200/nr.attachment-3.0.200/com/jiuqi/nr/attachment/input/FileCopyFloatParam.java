/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileCopyBaseParam;
import com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo;
import java.util.List;

public class FileCopyFloatParam
extends FileCopyBaseParam {
    private List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos;

    public FileCopyFloatParam() {
    }

    public FileCopyFloatParam(List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos) {
        this.floatFieldAndGroupInfos = floatFieldAndGroupInfos;
    }

    public FileCopyFloatParam(String fromTaskKey, List<String> oldFileGroupKeys, List<String> oldPicGroupKeys, String toTaskKey, String toFormSchemeKey, List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos) {
        super(fromTaskKey, oldFileGroupKeys, oldPicGroupKeys, toTaskKey, toFormSchemeKey);
        this.floatFieldAndGroupInfos = floatFieldAndGroupInfos;
    }

    public List<FloatFieldAndGroupInfo> getFloatFieldAndGroupInfos() {
        return this.floatFieldAndGroupInfos;
    }

    public void setFloatFieldAndGroupInfos(List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos) {
        this.floatFieldAndGroupInfos = floatFieldAndGroupInfos;
    }
}

