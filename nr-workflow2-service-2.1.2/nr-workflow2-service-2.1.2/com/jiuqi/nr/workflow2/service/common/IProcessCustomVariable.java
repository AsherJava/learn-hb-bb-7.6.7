/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import org.json.JSONObject;

public interface IProcessCustomVariable
extends IActionArgs {
    public JSONObject getValue();

    public IProcessCustomVariable getVariable(String var1);

    public IProcessCustomVariable clone();

    public <T> T toJavaBean(JSONObject var1, Class<T> var2) throws Exception;
}

