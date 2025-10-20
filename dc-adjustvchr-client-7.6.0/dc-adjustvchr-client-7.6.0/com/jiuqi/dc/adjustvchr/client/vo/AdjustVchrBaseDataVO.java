/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.dc.adjustvchr.client.vo;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.LinkedHashMap;

public class AdjustVchrBaseDataVO
extends LinkedHashMap<String, String> {
    private static final long serialVersionUID = -7270388979546505795L;

    public AdjustVchrBaseDataVO() {
    }

    public AdjustVchrBaseDataVO(BaseDataDO basedata) {
        this.setCode(basedata.getCode());
        this.setName(basedata.getName());
    }

    public String getCode() {
        return (String)this.get("code");
    }

    public void setCode(String code) {
        this.put("code", code);
    }

    public String getName() {
        return (String)this.get("name");
    }

    public void setName(String name) {
        this.put("name", name);
    }
}

