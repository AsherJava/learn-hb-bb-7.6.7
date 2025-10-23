/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.ILanguagePacket;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessStatsService;

public class ProcessEngineRegisteration {
    private String name;
    private String title;
    private short order;
    private IProcessDefinitionService processDefinitionService;
    private IProcessRuntimeService processRuntimeService;
    private IProcessStatsService processStatsService;
    private ILanguagePacket languagePacket;
    private IProcessIOService processIOService;

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public short getOrder() {
        return this.order;
    }

    public IProcessDefinitionService getProcessDefinitionService() {
        return this.processDefinitionService;
    }

    public IProcessRuntimeService getProcessRuntimeService() {
        return this.processRuntimeService;
    }

    public IProcessStatsService getProcessStatsService() {
        return this.processStatsService;
    }

    public ILanguagePacket getLanguagePacket() {
        return this.languagePacket;
    }

    public IProcessIOService getProcessIOService() {
        return this.processIOService;
    }

    public ProcessEngineRegisteration(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public ProcessEngineRegisteration order(short order) {
        this.order = order;
        return this;
    }

    public ProcessEngineRegisteration processDefinitionService(IProcessDefinitionService processDefinitionService) {
        this.processDefinitionService = processDefinitionService;
        return this;
    }

    public ProcessEngineRegisteration processRuntimeService(IProcessRuntimeService processRuntimeService) {
        this.processRuntimeService = processRuntimeService;
        return this;
    }

    public ProcessEngineRegisteration processStatsService(IProcessStatsService processStatsService) {
        this.processStatsService = processStatsService;
        return this;
    }

    public ProcessEngineRegisteration languagePacket(ILanguagePacket languagePacket) {
        this.languagePacket = languagePacket;
        return this;
    }

    public ProcessEngineRegisteration processIOService(IProcessIOService processIOService) {
        this.processIOService = processIOService;
        return this;
    }
}

