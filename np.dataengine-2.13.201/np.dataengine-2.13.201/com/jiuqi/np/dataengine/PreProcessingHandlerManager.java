/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine;

import com.jiuqi.np.dataengine.IPreProcessingHandler;
import java.util.HashMap;
import java.util.Map;

public class PreProcessingHandlerManager {
    private static final PreProcessingHandlerManager instance = new PreProcessingHandlerManager();
    private Map<String, IPreProcessingHandler> handlerMap = new HashMap<String, IPreProcessingHandler>();

    private PreProcessingHandlerManager() {
    }

    public static final PreProcessingHandlerManager getInstance() {
        return instance;
    }

    public void regHandler(IPreProcessingHandler handler) {
        if (handler == null || handler.funcName() == null) {
            throw new NullPointerException();
        }
        this.handlerMap.put(handler.funcName().toUpperCase(), handler);
    }

    public IPreProcessingHandler findHandlerByName(String funcName) {
        if (funcName == null) {
            throw new NullPointerException();
        }
        IPreProcessingHandler handler = this.handlerMap.get(funcName.toUpperCase());
        return handler;
    }
}

