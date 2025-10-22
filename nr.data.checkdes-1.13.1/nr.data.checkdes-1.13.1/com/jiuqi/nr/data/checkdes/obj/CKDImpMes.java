/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.AbstractMessage
 *  com.jiuqi.nr.data.common.Message
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.common.AbstractMessage;
import com.jiuqi.nr.data.common.Message;
import java.io.Serializable;
import java.util.List;

public class CKDImpMes
extends AbstractMessage
implements Message<CKDImpMes>,
Serializable {
    private static final long serialVersionUID = -8375106629635248308L;
    private List<String> formulaSchemeKeys;
    private List<CKDImpDetails> detail;

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public List<CKDImpDetails> getDetail() {
        return this.detail;
    }

    public void setDetail(List<CKDImpDetails> detail) {
        this.detail = detail;
    }

    public CKDImpMes getMessage() {
        return null;
    }
}

