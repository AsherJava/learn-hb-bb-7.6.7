/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.dto;

import com.jiuqi.gcreport.rate.impl.dto.ConvertParam;
import com.jiuqi.gcreport.rate.impl.dto.RateBatchParam;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateBatchData
extends RateBatchParam
implements Serializable {
    private static final long serialVersionUID = -3937325251094067982L;
    private Map<String, List<ConvertParam>> params;

    public RateBatchData() {
    }

    public RateBatchData(String currCode, String repCurrCode, String acctYear, String acctPeriod) {
        this.setSourceCurrencyCode(currCode);
        this.setTargetCurrencyCode(repCurrCode);
        this.setAcctYear(acctYear);
        this.setAcctPeriod(acctPeriod);
    }

    public Map<String, List<ConvertParam>> getParams() {
        if (this.params == null) {
            this.params = new HashMap<String, List<ConvertParam>>();
        }
        return this.params;
    }

    public void setParams(Map<String, List<ConvertParam>> params) {
        this.params = params;
    }

    public void addParam(String subjectCode, List<ConvertParam> param) {
        if (param != null) {
            this.getParams().put(subjectCode, param);
        }
    }
}

