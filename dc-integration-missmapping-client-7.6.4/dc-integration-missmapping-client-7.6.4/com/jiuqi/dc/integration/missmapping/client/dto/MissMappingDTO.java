/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.missmapping.client.dto;

import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO;
import java.io.Serializable;

public class MissMappingDTO
implements Serializable {
    private static final long serialVersionUID = 8929827780987424167L;
    private String dataSchemeCode;
    private String dataSchemeName;
    private String unitCode;
    private String vchrNum;
    private MissMappingDimVO[] dimVOs;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeName() {
        return this.dataSchemeName;
    }

    public void setDataSchemeName(String dataSchemeName) {
        this.dataSchemeName = dataSchemeName;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public MissMappingDimVO[] getDimVOs() {
        return this.dimVOs;
    }

    public void setDimVOs(MissMappingDimVO[] dimVOs) {
        this.dimVOs = dimVOs;
    }
}

