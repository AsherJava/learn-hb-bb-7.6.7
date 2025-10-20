/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.bi.quickreport.engine.script.IScriptObjFactory;
import com.jiuqi.bi.quickreport.model.QuickReport;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScriptObjManager {
    private static final ScriptObjManager instance = new ScriptObjManager();
    private Map<String, IScriptObjFactory> factories = new HashMap<String, IScriptObjFactory>();

    private ScriptObjManager() {
    }

    public static ScriptObjManager getInstance() {
        return instance;
    }

    public String[] getFactoryIds() {
        Set<String> set = this.factories.keySet();
        return set.toArray(new String[set.size()]);
    }

    public IScriptObj newScriptObj(String id, QuickReport report) throws ReportEngineException {
        IScriptObjFactory factory = this.factories.get(id);
        if (factory == null) {
            throw new ReportEngineException("\u672a\u77e5\u7684\u811a\u672c\u5bf9\u8c61ID[" + id + "]");
        }
        return factory.newScriptObj(report);
    }

    public void register(IScriptObjFactory factory) {
        if (factory == null) {
            return;
        }
        this.factories.put(factory.getId(), factory);
    }

    public void unregister(IScriptObjFactory factory) {
        if (factory == null) {
            return;
        }
        this.factories.remove(factory.getId());
    }
}

