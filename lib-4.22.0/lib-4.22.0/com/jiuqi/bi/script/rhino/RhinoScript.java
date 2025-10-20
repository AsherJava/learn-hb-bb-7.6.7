/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.mozilla.javascript.BaseFunction
 *  org.mozilla.javascript.Context
 *  org.mozilla.javascript.Function
 *  org.mozilla.javascript.FunctionObject
 *  org.mozilla.javascript.IdFunctionObject
 *  org.mozilla.javascript.IdScriptableObject
 *  org.mozilla.javascript.JavaScriptException
 *  org.mozilla.javascript.NativeJavaObject
 *  org.mozilla.javascript.RhinoException
 *  org.mozilla.javascript.Scriptable
 *  org.mozilla.javascript.ScriptableObject
 *  org.mozilla.javascript.Undefined
 *  org.mozilla.javascript.UniqueTag
 */
package com.jiuqi.bi.script.rhino;

import com.jiuqi.bi.script.CompileException;
import com.jiuqi.bi.script.EvalException;
import com.jiuqi.bi.script.IScriptObject;
import com.jiuqi.bi.script.Script;
import com.jiuqi.bi.script.ScriptManager;
import com.jiuqi.bi.script.rhino.RhinoScriptFactory;
import com.jiuqi.bi.script.rhino.RhinoScriptObject;
import com.jiuqi.bi.script.rhino.ScriptHelper;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.UniqueTag;

public class RhinoScript
implements Script {
    private Context cx = Context.enter();
    private Scriptable scope = this.cx.initStandardObjects(null, false);

    public RhinoScript() {
        this.regDefaultFunction();
        Context.exit();
    }

    @Override
    public Object evaluate(String script, String sourceName) throws CompileException, EvalException {
        this.cx = Context.enter();
        try {
            Object result = this.cx.evaluateString(this.scope, script, sourceName, 1, null);
            Object object = this.convertObject(result);
            return object;
        }
        catch (JavaScriptException e) {
            throw new EvalException(e.getMessage(), e.sourceName(), e.lineNumber(), e.columnNumber(), e);
        }
        catch (RhinoException e) {
            throw new CompileException(e.getMessage(), e.sourceName(), e.lineNumber(), e.columnNumber(), e);
        }
        finally {
            Context.exit();
        }
    }

    @Override
    public Object call(String name, Object[] args) throws NoSuchMethodException, CompileException, EvalException {
        Object[] param = args;
        if (param == null) {
            param = new Object[]{};
        }
        this.convertArgument(param);
        this.cx = Context.enter();
        try {
            Function fun = this.getFunctionFromScope(this.scope, name);
            if (fun != null) {
                Object result = fun.call(this.cx, this.scope, (Scriptable)fun, param);
                Object object = this.convertObject(result);
                return object;
            }
            try {
                throw new NoSuchMethodException("\u6ca1\u6709\u8be5\u51fd\u6570:" + name);
            }
            catch (JavaScriptException e) {
                throw new EvalException(e.getMessage(), e.sourceName(), e.lineNumber(), e.columnNumber(), e);
            }
            catch (RhinoException e) {
                throw new CompileException(e.getMessage(), e.sourceName(), e.lineNumber(), e.columnNumber(), e);
            }
        }
        finally {
            Context.exit();
        }
    }

    @Override
    public void compile(String script, String sourceName) throws CompileException {
        this.cx = Context.enter();
        try {
            this.cx.evaluateString(this.scope, script, sourceName, 1, null);
        }
        catch (RhinoException e) {
            throw new CompileException(e.getMessage(), e.sourceName(), e.lineNumber(), e.columnNumber(), e);
        }
        finally {
            Context.exit();
        }
    }

    @Override
    public Object get(String name) {
        Object o = this.getValueFromScope(this.scope, name);
        if (o instanceof UniqueTag || o instanceof Undefined) {
            return null;
        }
        return o;
    }

    @Override
    public void put(String name, Object value) {
        this.putValueToScope(this.scope, name, value);
    }

    @Override
    public Object toJava(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RhinoScriptObject) {
            return ((RhinoScriptObject)((Object)obj)).getScriptObject();
        }
        if (!(obj instanceof IdScriptableObject)) {
            return obj;
        }
        final IdScriptableObject result = (IdScriptableObject)obj;
        if (result.getClassName().equals("Date1")) {
            IdFunctionObject v = (IdFunctionObject)ScriptableObject.getProperty((Scriptable)result, (String)"getTime");
            Object m = result.execIdCall(v, Context.getCurrentContext(), this.scope, (Scriptable)result, null);
            long millis = ((Double)m).longValue();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(millis);
            return c;
        }
        return new IScriptObject(){

            @Override
            public void setProperty(String name, Object value) {
                ScriptableObject.putProperty((Scriptable)result, (String)name, (Object)value);
            }

            @Override
            public Object invoke(String name, Object[] args) {
                Object function = result.get(name, RhinoScript.this.scope);
                if (!(function instanceof IdFunctionObject)) {
                    return null;
                }
                IdFunctionObject v = (IdFunctionObject)function;
                return result.execIdCall(v, Context.getCurrentContext(), RhinoScript.this.scope, (Scriptable)result, args);
            }

            @Override
            public boolean hasProperty(String name) {
                if (result.has(name, RhinoScript.this.scope)) {
                    Object o = result.get(name, RhinoScript.this.scope);
                    return !(o instanceof BaseFunction);
                }
                return false;
            }

            @Override
            public boolean hasFunction(String name) {
                if (result.has(name, RhinoScript.this.scope)) {
                    Object o = result.get(name, RhinoScript.this.scope);
                    return o instanceof BaseFunction;
                }
                return false;
            }

            @Override
            public String[] getPropertyNames() {
                Object[] ids = result.getIds();
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < ids.length; ++i) {
                    String id = ids[i].toString();
                    Object o = result.get(id, RhinoScript.this.scope);
                    if (o instanceof BaseFunction) continue;
                    list.add(id);
                }
                String[] rv = new String[list.size()];
                for (int i = 0; i < list.size(); ++i) {
                    rv[i] = (String)list.get(i);
                }
                return rv;
            }

            @Override
            public Object getProperty(String name) {
                return result.get(name, RhinoScript.this.scope);
            }

            @Override
            public String[] getFunctionNames() {
                Object[] ids = result.getIds();
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < ids.length; ++i) {
                    String id = ids[i].toString();
                    Object o = result.get(id, RhinoScript.this.scope);
                    if (!(o instanceof BaseFunction)) continue;
                    list.add(id);
                }
                String[] rv = new String[list.size()];
                for (int i = 0; i < list.size(); ++i) {
                    rv[i] = (String)list.get(i);
                }
                return rv;
            }

            @Override
            public void delProperty(String name) {
                ScriptableObject.deleteProperty((Scriptable)result, (String)name);
            }
        };
    }

    @Override
    public Object toScript(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IScriptObject) {
            return new RhinoScriptObject((IScriptObject)obj);
        }
        if (obj instanceof IdScriptableObject) {
            return obj;
        }
        if (obj instanceof NativeJavaObject) {
            Object result = ((NativeJavaObject)obj).unwrap();
            if (result instanceof IScriptObject) {
                return new RhinoScriptObject((IScriptObject)result);
            }
            if (result instanceof Calendar) {
                Calendar c = (Calendar)result;
                return Context.getCurrentContext().newObject(this.scope, "Date", new Object[]{new Long(c.getTimeInMillis())});
            }
            if (result instanceof Date) {
                Date d = (Date)result;
                return Context.getCurrentContext().newObject(this.scope, "Date", new Object[]{new Long(d.getTime())});
            }
        }
        return obj;
    }

    private Function getFunctionFromScope(Scriptable scope, String name) {
        Object o;
        String parentName = name;
        Scriptable parent = scope;
        if (name == null) {
            return null;
        }
        while (name.indexOf(".") != -1) {
            o = parent.get(parentName = parentName.substring(0, name.indexOf(".")), scope);
            if (!(o instanceof Scriptable)) {
                return null;
            }
            parent = (Scriptable)o;
            name = name.substring(name.indexOf(".") + 1);
        }
        o = parent.get(name, scope);
        if (o instanceof Function) {
            return (Function)o;
        }
        return null;
    }

    private Object getValueFromScope(Scriptable scope, String name) {
        String parentName = name;
        Scriptable parent = scope;
        if (name == null) {
            return null;
        }
        while (name.indexOf(".") != -1) {
            Object o = parent.get(parentName = parentName.substring(0, name.indexOf(".")), scope);
            if (!(o instanceof Scriptable)) {
                return null;
            }
            parent = (Scriptable)o;
            name = name.substring(name.indexOf(".") + 1);
        }
        return parent.get(name, scope);
    }

    private void putValueToScope(Scriptable scope, String name, Object value) {
        String parentName = name;
        Scriptable parent = scope;
        if (name == null) {
            return;
        }
        while (name.indexOf(".") != -1) {
            Object o = parent.get(parentName = parentName.substring(0, name.indexOf(".")), scope);
            if (!(o instanceof Scriptable)) {
                return;
            }
            parent = (Scriptable)o;
            name = name.substring(name.indexOf(".") + 1);
        }
        if (value instanceof IScriptObject) {
            parent.put(name, scope, (Object)new RhinoScriptObject((IScriptObject)value));
        } else {
            parent.put(name, scope, value);
        }
    }

    private void convertArgument(Object[] args) {
        if (args == null) {
            return;
        }
        for (int i = 0; i < args.length; ++i) {
            if (!(args[i] instanceof Calendar)) continue;
            Calendar c = (Calendar)args[i];
            args[i] = this.cx.newObject(this.scope, "Date", new Object[]{new Long(c.getTimeInMillis())});
        }
    }

    private Object convertObject(Object result) {
        if (result instanceof ScriptableObject) {
            ScriptableObject o = (ScriptableObject)result;
            if (o.getClassName().equals("CustomScriptObject")) {
                return ((RhinoScriptObject)o).getScriptObject();
            }
            if (o.getClassName().equals("Date")) {
                BaseFunction v = (BaseFunction)ScriptableObject.getProperty((Scriptable)o, (String)"getTime");
                Object m = v.call(this.cx, this.scope, (Scriptable)o, null);
                long millis = ((Double)m).longValue();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(millis);
                return c;
            }
            return o;
        }
        if (result instanceof NativeJavaObject) {
            Object o = ((NativeJavaObject)result).unwrap();
            return o;
        }
        return result;
    }

    private void regDefaultFunction() {
        if (this.scope == null) {
            return;
        }
        Class<ScriptHelper> c = ScriptHelper.class;
        ScriptHelper u = new ScriptHelper(this.scope);
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equals("getClassName") || !Modifier.isPublic(methods[i].getModifiers())) continue;
            this.scope.put(methods[i].getName(), this.scope, (Object)new FunctionObject(methods[i].getName(), (Member)methods[i], (Scriptable)u));
        }
    }

    static {
        ScriptManager.registerScriptFactory(new RhinoScriptFactory());
    }
}

