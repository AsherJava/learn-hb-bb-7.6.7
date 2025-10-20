/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.mozilla.javascript.Callable
 *  org.mozilla.javascript.Context
 *  org.mozilla.javascript.Scriptable
 *  org.mozilla.javascript.ScriptableObject
 */
package com.jiuqi.bi.script.rhino;

import com.jiuqi.bi.script.IScriptObject;
import com.jiuqi.bi.script.rhino.ScriptHelper;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoScriptObject
extends ScriptableObject {
    private static final long serialVersionUID = 5417776227583521823L;
    public static final String CLASSNAME = "CustomScriptObject";
    private IScriptObject sobj;

    public RhinoScriptObject(IScriptObject sobj) {
        this.sobj = sobj;
    }

    public String getClassName() {
        return CLASSNAME;
    }

    public IScriptObject getScriptObject() {
        return this.sobj;
    }

    public Object[] getAllIds() {
        return this.getIds();
    }

    public Object[] getIds() {
        String[] properties = this.sobj.getPropertyNames();
        String[] functions = this.sobj.getFunctionNames();
        Object[] ids = new String[properties.length + functions.length];
        System.arraycopy(properties, 0, ids, 0, properties.length);
        System.arraycopy(functions, 0, ids, properties.length, functions.length);
        return ids;
    }

    public Object get(String name, Scriptable start) {
        if (this.sobj.hasFunction(name)) {
            return new ScriptFunction(name, this.sobj);
        }
        return this.sobj.getProperty(name);
    }

    public Object get(int index, Scriptable start) {
        String[] properties = this.sobj.getPropertyNames();
        if (properties.length > index) {
            return this.sobj.getProperty(this.sobj.getPropertyNames()[index]);
        }
        return null;
    }

    public boolean has(String name, Scriptable start) {
        return this.sobj.hasProperty(name);
    }

    public boolean has(int index, Scriptable start) {
        String[] properties = this.sobj.getPropertyNames();
        if (properties.length > index) {
            return this.sobj.hasProperty(this.sobj.getPropertyNames()[index]);
        }
        return false;
    }

    public void put(String name, Scriptable start, Object value) {
        this.sobj.setProperty(name, value);
    }

    public void delete(String name) {
        this.sobj.delProperty(name);
    }

    public void delete(int index) {
        String[] properties = this.sobj.getPropertyNames();
        if (properties.length > index) {
            this.sobj.delProperty(this.sobj.getPropertyNames()[index]);
        }
    }

    private class ScriptFunction
    implements Callable {
        private String functionName;
        private IScriptObject scriptObject;

        public ScriptFunction(String functionName, IScriptObject scriptObject) {
            this.functionName = functionName;
            this.scriptObject = scriptObject;
        }

        public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
            if (args == null) {
                return this.scriptObject.invoke(this.functionName, new Object[0]);
            }
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < args.length; ++i) {
                newArgs[i] = ScriptHelper._toJava(args[i], scope);
            }
            return this.scriptObject.invoke(this.functionName, newArgs);
        }
    }
}

