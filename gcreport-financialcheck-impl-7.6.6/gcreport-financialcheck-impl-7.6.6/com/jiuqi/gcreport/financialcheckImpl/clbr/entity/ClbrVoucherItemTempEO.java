/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO
 */
package com.jiuqi.gcreport.financialcheckImpl.clbr.entity;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO;
import org.springframework.beans.BeanUtils;

@DBTable(name="GC_CLBR_VOUCHERITEM_TEMP", title="\u51ed\u8bc1\u5206\u5f55\u534f\u540c\u4e34\u65f6\u8868", dataSource="jiuqi.gcreport.mdd.datasource")
public class ClbrVoucherItemTempEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CLBR_VOUCHERITEM_TEMP";
    @DBColumn(nameInDB="BATCHID", title="\u6279\u6b21\u53f7", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String batchId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36)
    private String oppUnitId;
    @DBColumn(nameInDB="GCNUMBER", title="\u534f\u540c\u7801", dbType=DBColumn.DBType.NVarchar, length=100)
    private String gcNumber;
    @DBColumn(nameInDB="BILLCODE", title="\u5355\u636e\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=50)
    private String billCode;
    @DBColumn(nameInDB="AMT", title="\u534f\u540c\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double amt;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public static ClbrVoucherItemTempEO convertVO2EO(ClbrVoucherItemVO item) {
        ClbrVoucherItemTempEO itemTemp = new ClbrVoucherItemTempEO();
        itemTemp.setId(UUIDUtils.newUUIDStr());
        BeanUtils.copyProperties(item, (Object)itemTemp);
        return itemTemp;
    }

    public String toString() {
        return "ClbrVoucherItemTempEO{batchId='" + this.batchId + '\'' + ", oppUnitId='" + this.oppUnitId + '\'' + ", gcNumber='" + this.gcNumber + '\'' + ", billCode='" + this.billCode + '\'' + ", amt=" + this.amt + '}';
    }
}

