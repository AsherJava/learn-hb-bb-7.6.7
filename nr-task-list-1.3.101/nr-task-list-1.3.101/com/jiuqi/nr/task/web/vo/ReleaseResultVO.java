/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.ReleaseErrorVO;
import java.util.ArrayList;
import java.util.List;

public class ReleaseResultVO {
    private final boolean success;
    private final List<ReleaseErrorVO> errors;

    public ReleaseResultVO() {
        this.success = true;
        this.errors = new ArrayList<ReleaseErrorVO>();
    }

    public ReleaseResultVO(String type, String desc) {
        this.success = false;
        this.errors = new ArrayList<ReleaseErrorVO>();
        this.errors.add(new ReleaseErrorVO(type, desc));
    }

    public ReleaseResultVO(List<ReleaseErrorVO> errors) {
        this.success = false;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public List<ReleaseErrorVO> getErrors() {
        return this.errors;
    }
}

