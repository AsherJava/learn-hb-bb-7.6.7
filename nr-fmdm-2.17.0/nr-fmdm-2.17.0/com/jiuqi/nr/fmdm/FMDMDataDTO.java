/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.data.ObjectData
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.ObjectData;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import org.springframework.util.Assert;

public class FMDMDataDTO
extends FMDMModifyDTO {
    public void setValue(String code, Object value) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (value == null) {
            value = "";
        }
        this.putValue(code, value);
    }

    public void setValue(String code, Object value, DataLinkType linkType) {
        if (DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)linkType)) {
            this.setDataValue(code, value);
        } else if (DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)linkType)) {
            this.setEntityValue(code, value);
        } else if (DataLinkType.DATA_LINK_TYPE_INFO.equals((Object)linkType)) {
            this.setInfoValue(code, value);
        } else {
            this.setValue(code, value);
        }
    }

    public void setEntityValue(String code, Object value) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (value == null) {
            value = "";
        }
        this.putEntityValue(code, value);
    }

    public void setDataValue(String code, Object value) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (value == null) {
            value = "";
        }
        this.putDataValue(code, value);
    }

    public void setInfoValue(String code, Object value) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (value == null) {
            value = "";
        }
        this.putInfoValue(code, value);
    }

    public AbstractData getValue(String code) {
        Object value = this.getAsObject(code);
        if (value == null) {
            return new ObjectData();
        }
        return new ObjectData(value);
    }

    public Object getAsObject(String code) {
        return super.getModifyValue().get(code);
    }
}

