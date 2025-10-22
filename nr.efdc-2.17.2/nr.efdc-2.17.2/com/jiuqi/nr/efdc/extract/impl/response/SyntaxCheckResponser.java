/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

import com.jiuqi.nr.efdc.extract.impl.response.SyntaxCheckResult;
import java.util.ArrayList;
import java.util.List;

public class SyntaxCheckResponser {
    private List<SyntaxCheckResult> errResults = new ArrayList<SyntaxCheckResult>();

    public List<SyntaxCheckResult> getErrResults() {
        return this.errResults;
    }

    public void setErrResults(List<SyntaxCheckResult> errResults) {
        this.errResults = errResults;
    }

    public int getErrCount() {
        return this.errResults.size();
    }
}

