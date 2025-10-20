/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.ParamMessageParser
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.build.hyperlink;

import com.jiuqi.bi.parameter.ParamMessageParser;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public final class HyperlinkContext
implements IHyperlinkContext {
    private JSONObject target;
    private List<String> measures;
    private Map<String, Object> restrictions;

    public HyperlinkContext(JSONObject target) {
        this.target = target;
        this.measures = new ArrayList<String>();
        this.restrictions = new HashMap<String, Object>();
    }

    @Override
    public JSONObject getTarget() {
        return this.target;
    }

    @Override
    public List<String> getMeasures() {
        return this.measures;
    }

    @Override
    public Map<String, Object> getRestrictions() {
        return this.restrictions;
    }

    @Override
    public String toMessage() {
        ParamMessageParser msg = new ParamMessageParser();
        for (Map.Entry<String, Object> e : this.restrictions.entrySet()) {
            msg.addParam(e.getKey(), e.getValue());
        }
        if (!this.measures.isEmpty()) {
            msg.addParam("measureCode", this.measures);
        }
        return msg.getParamStr();
    }
}

