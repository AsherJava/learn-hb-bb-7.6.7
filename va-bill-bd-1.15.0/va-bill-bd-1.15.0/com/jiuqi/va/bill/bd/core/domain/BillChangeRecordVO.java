/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import java.util.Date;
import java.util.List;

public class BillChangeRecordVO {
    private String uniquecode;
    private String billcode;
    private Date changedate;
    private String verify;
    private List<BillChangeRecordDO> records;

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    public List<BillChangeRecordDO> getRecords() {
        return this.records;
    }

    public void setRecords(List<BillChangeRecordDO> records) {
        this.records = records;
    }

    public String getVerify() {
        return this.verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getUniquecode() {
        return this.uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }
}

