/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.Result
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.nr.data.common.service.Result;
import java.util.ArrayList;
import java.util.List;

public class FormDataResult
implements Result {
    private String message;
    private List<String> failUnits;

    public FormDataResult(String message, List<String> failUnits) {
        this.message = message;
        this.failUnits = failUnits;
    }

    public String getMessage() {
        if (this.message == null) {
            this.message = "";
        }
        return this.message;
    }

    public List<String> getFailUnits() {
        if (this.failUnits == null) {
            this.failUnits = new ArrayList<String>();
        }
        return this.failUnits;
    }
}

