/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.bean;

import com.jiuqi.nr.common.paramcheck.bean.ErrorParam;
import java.util.ArrayList;
import java.util.List;

public class CheckResult {
    private String beanName;
    private Boolean result;
    private List<ErrorParam> error;

    public CheckResult() {
    }

    public CheckResult(String beanName, Boolean result, List<ErrorParam> error) {
        this.beanName = beanName;
        this.result = result;
        this.error = error;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Boolean getResult() {
        if (this.result == null) {
            return false;
        }
        return this.result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<ErrorParam> getError() {
        if (this.error == null) {
            this.error = new ArrayList<ErrorParam>();
        }
        return this.error;
    }

    public void setError(List<ErrorParam> error) {
        this.error = error;
    }
}

