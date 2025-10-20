/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 */
package com.jiuqi.dc.integration.missmapping.client.vo;

import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import java.io.Serializable;
import java.util.Objects;

public class MissMappingDimVO
extends BaseDataShowVO
implements Serializable {
    private String dimValue;

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MissMappingDimVO dimVO = (MissMappingDimVO)o;
        return Objects.equals(this.dimValue, dimVO.dimValue);
    }

    public int hashCode() {
        return Objects.hash(this.dimValue);
    }
}

