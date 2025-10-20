/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.chapter.vo;

import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import java.io.Serializable;

public class ChapterVO
extends ReportChapterDefine
implements Serializable {
    private boolean isLeaf = true;
    private boolean isStreamingEnabled = false;

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public boolean getIsStreamingEnabled() {
        return this.isStreamingEnabled;
    }

    public void setIsStreamingEnabled(boolean streamingEnabled) {
        this.isStreamingEnabled = streamingEnabled;
    }
}

