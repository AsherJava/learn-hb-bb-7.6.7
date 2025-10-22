/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;

public class MistoreWorkFailInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    Map<String, MistoreWorkUnitInfo> unitInfos;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, MistoreWorkUnitInfo> getUnitInfos() {
        if (this.unitInfos == null) {
            this.unitInfos = new HashMap<String, MistoreWorkUnitInfo>();
        }
        return this.unitInfos;
    }

    public void setUnitInfos(Map<String, MistoreWorkUnitInfo> unitInfos) {
        this.unitInfos = unitInfos;
    }
}

