/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 */
package com.jiuqi.dc.integration.execute.client.dto;

import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import java.io.Serializable;

public class RefChangeDTO
implements Serializable {
    private static final long serialVersionUID = -1702971019027879252L;
    private String dataSchemeCode;
    private String tableName;
    private DataRefDTO refData;
    private DataRefDTO oldRefData;
    private String unitCode;
    private VchrMasterDim dim;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataRefDTO getRefData() {
        return this.refData;
    }

    public void setRefData(DataRefDTO refData) {
        this.refData = refData;
    }

    public DataRefDTO getOldRefData() {
        return this.oldRefData;
    }

    public void setOldRefData(DataRefDTO oldRefData) {
        this.oldRefData = oldRefData;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public VchrMasterDim getDim() {
        return this.dim;
    }

    public void setDim(VchrMasterDim dim) {
        this.dim = dim;
    }
}

