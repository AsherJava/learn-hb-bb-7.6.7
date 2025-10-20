/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.reltxquery.vo;

import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryTableDataVO;

public class RelTxCheckQueryLevel4Param {
    private RelTxCheckQueryParamVO param;
    private RelTxCheckQueryTableDataVO level3Data;

    public RelTxCheckQueryParamVO getParam() {
        return this.param;
    }

    public void setParam(RelTxCheckQueryParamVO param) {
        this.param = param;
    }

    public RelTxCheckQueryTableDataVO getLevel3Data() {
        return this.level3Data;
    }

    public void setLevel3Data(RelTxCheckQueryTableDataVO level3Data) {
        this.level3Data = level3Data;
    }
}

