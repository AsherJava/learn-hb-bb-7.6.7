/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.Script;
import com.jiuqi.bi.script.ScriptException;
import com.jiuqi.bi.script.ScriptFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptManager {
    private static List factoryList = new ArrayList();
    private static Map factoryMap = new HashMap();

    private ScriptManager() {
    }

    public static Script getScript(String name) throws ScriptException {
        ScriptFactory factory = (ScriptFactory)factoryMap.get(name);
        if (factory == null) {
            throw new ScriptException("\u672a\u6ce8\u518c\u540d\u4e3a" + name + "\u7684\u811a\u672c\u5f15\u64ce");
        }
        return factory.createScript();
    }

    public static void registerScriptFactory(ScriptFactory scriptFactory) {
        factoryList.add(scriptFactory);
        String[] names = scriptFactory.getNames();
        for (int i = 0; i < names.length; ++i) {
            factoryMap.put(names[i], scriptFactory);
        }
    }

    public static ScriptFactory[] getScriptFactories() {
        return factoryList.toArray(new ScriptFactory[0]);
    }

    static {
        try {
            Class.forName("com.jiuqi.bi.script.rhino.RhinoScript");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }
}

