/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MidstoreParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String midstoreSchemeId;
    private Map<String, String> excuteParams;

    public Map<String, String> getExcuteParams() {
        if (this.excuteParams == null) {
            this.excuteParams = new HashMap<String, String>();
        }
        return this.excuteParams;
    }

    public void setExcuteParams(Map<String, String> excuteParams) {
        this.excuteParams = excuteParams;
    }

    public String getMidstoreSchemeId() {
        return this.midstoreSchemeId;
    }

    public void setMidstoreSchemeId(String midstoreSchemeId) {
        this.midstoreSchemeId = midstoreSchemeId;
    }
}

