/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.missmapping.client.dto;

import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO;
import java.io.Serializable;
import java.util.Objects;

public class MissMappingGatherDTO
implements Serializable {
    private static final long serialVersionUID = 4082929144853821328L;
    private String dataSchemeCode;
    private String unitCode;
    private MissMappingDimVO dimVO;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public MissMappingDimVO getDimVO() {
        return this.dimVO;
    }

    public void setDimVO(MissMappingDimVO dimVO) {
        this.dimVO = dimVO;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MissMappingGatherDTO that = (MissMappingGatherDTO)o;
        return Objects.equals(this.dataSchemeCode, that.dataSchemeCode) && Objects.equals(this.unitCode, that.unitCode) && Objects.equals(this.dimVO, that.dimVO);
    }

    public int hashCode() {
        return Objects.hash(this.dataSchemeCode, this.unitCode, this.dimVO);
    }
}

