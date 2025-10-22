/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;

public class CheckExeParam {
    private IProviderStore providerStore;
    private ICheckOptionProvider optionProvider;
    private ICheckMonitor monitor;
    private ActionEnum action;
    private String executeId;

    public CheckExeParam(IProviderStore providerStore, ICheckOptionProvider optionProvider, ICheckMonitor monitor, ActionEnum action, String executeId) {
        this.providerStore = providerStore;
        this.optionProvider = optionProvider;
        this.monitor = monitor;
        this.action = action;
        this.executeId = executeId;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public ICheckOptionProvider getOptionProvider() {
        return this.optionProvider;
    }

    public void setOptionProvider(ICheckOptionProvider optionProvider) {
        this.optionProvider = optionProvider;
    }

    public ICheckMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(ICheckMonitor monitor) {
        this.monitor = monitor;
    }

    public ActionEnum getAction() {
        return this.action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public String getExecuteId() {
        return this.executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }
}

