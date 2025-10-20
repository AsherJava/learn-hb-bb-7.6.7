/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script.rhino;

import com.jiuqi.bi.script.Script;
import com.jiuqi.bi.script.ScriptFactory;
import com.jiuqi.bi.script.rhino.RhinoScript;

public class RhinoScriptFactory
implements ScriptFactory {
    private static final String factoryName = "Rhino";
    private static final String version = "1.6 release 7 2007 08 19";
    private static final String[] rhinoNames = new String[]{"javascript", "js", "rhino", "JavaScript", "ECMAScript", "ecmascript"};

    @Override
    public Script createScript() {
        return new RhinoScript();
    }

    @Override
    public String[] getNames() {
        return rhinoNames;
    }

    @Override
    public String getFactoryName() {
        return factoryName;
    }

    @Override
    public String getVersion() {
        return version;
    }
}

