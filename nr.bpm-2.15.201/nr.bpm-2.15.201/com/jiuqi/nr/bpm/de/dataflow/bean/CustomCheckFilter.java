/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import java.util.ArrayList;
import java.util.List;

public class CustomCheckFilter {
    private List<Integer> ignorError = new ArrayList<Integer>();
    private List<Integer> needCommentError = new ArrayList<Integer>();

    public List<Integer> getIgnorError() {
        return this.ignorError;
    }

    public void setIgnorError(List<Integer> ignorError) {
        this.ignorError = ignorError;
    }

    public List<Integer> getNeedCommentError() {
        return this.needCommentError;
    }

    public void setNeedCommentError(List<Integer> needCommentError) {
        this.needCommentError = needCommentError;
    }
}

