/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExplainInfoCheckResultTimeSpan
implements Serializable {
    private static final long serialVersionUID = -486162051813956276L;
    private List<ExplainInfoCheckResultItem> result = new ArrayList<ExplainInfoCheckResultItem>();
    private Date checkDate;

    public List<ExplainInfoCheckResultItem> getResult() {
        return this.result;
    }

    public void setResult(List<ExplainInfoCheckResultItem> result) {
        this.result = result;
    }

    public Date getCheckDate() {
        return this.checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}

