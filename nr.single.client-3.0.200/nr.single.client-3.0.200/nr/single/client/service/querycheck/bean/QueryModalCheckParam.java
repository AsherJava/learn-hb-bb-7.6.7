/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.service.querycheck.bean;

import java.util.HashMap;
import java.util.Map;

public class QueryModalCheckParam {
    Map<String, String> param;

    public Map<String, String> getParam() {
        if (this.param == null) {
            this.param = new HashMap<String, String>();
        }
        return this.param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }
}

