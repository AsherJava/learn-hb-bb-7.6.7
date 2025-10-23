/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.ILanguagePacket;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessStatsService;
import com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessEngineRegisterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.map.HashedMap;

public class ProcessEngineFactoryImpl
implements IProcessEngineFactory {
    private final List<IProcessEngine> srotedEngines = new ArrayList<IProcessEngine>();
    private final Map<String, IProcessEngine> processEngines = new HashMap<String, IProcessEngine>();

    public ProcessEngineFactoryImpl(List<ProcessEngineRegisteration> registerations) {
        registerations.sort((r1, r2) -> r1.getOrder() - r2.getOrder());
        for (ProcessEngineRegisteration registeration : registerations) {
            this.validateRegisteration(registeration);
            if (this.processEngines.containsKey(registeration.getName())) {
                throw new ProcessEngineRegisterException(registeration.getName(), "process engine name " + registeration.getName() + " already exists.");
            }
            InternalProcessEngine processEngine = new InternalProcessEngine(registeration);
            this.srotedEngines.add(processEngine);
            this.processEngines.put(processEngine.getName(), processEngine);
        }
    }

    private void validateRegisteration(ProcessEngineRegisteration registeration) {
        if (registeration.getName() == null || registeration.getName().trim().length() == 0) {
            throw new ProcessEngineRegisterException("", "process engine name must not be empty: " + registeration.getTitle());
        }
        if (registeration.getTitle() == null || registeration.getTitle().trim().length() == 0) {
            throw new ProcessEngineRegisterException(registeration.getName(), "process engine title must not be empty: " + registeration.getName());
        }
        if (registeration.getProcessDefinitionService() == null) {
            throw new ProcessEngineRegisterException(registeration.getName(), "process definiton service must not be null: " + registeration.getName());
        }
        if (registeration.getProcessRuntimeService() == null) {
            throw new ProcessEngineRegisterException(registeration.getName(), "process runtime service must not be null: " + registeration.getName());
        }
    }

    @Override
    public IProcessEngine getProcessEngine(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        return this.processEngines.get(name);
    }

    @Override
    public List<IProcessEngine> getAllProcessEngines() {
        return Collections.unmodifiableList(this.srotedEngines);
    }

    public static void main(String[] args) {
        InternalProcessEngine p1 = new InternalProcessEngine(new ProcessEngineRegisteration("nr-default", ""));
        InternalProcessEngine p2 = new InternalProcessEngine(new ProcessEngineRegisteration("nr-custom", ""));
        InternalProcessEngine p3 = new InternalProcessEngine(new ProcessEngineRegisteration("gsi-default", ""));
        HashedMap<String, InternalProcessEngine> engines1 = new HashedMap<String, InternalProcessEngine>();
        engines1.put(p1.getName(), p1);
        engines1.put(p2.getName(), p2);
        engines1.put(p3.getName(), p3);
        ConcurrentHashMap<String, InternalProcessEngine> engines2 = new ConcurrentHashMap<String, InternalProcessEngine>();
        engines2.put(p1.getName(), p1);
        engines2.put(p2.getName(), p2);
        engines2.put(p3.getName(), p3);
        ArrayList<InternalProcessEngine> engines3 = new ArrayList<InternalProcessEngine>();
        engines3.add(p1);
        engines3.add(p2);
        engines3.add(p3);
        List<String> engineNames = Arrays.asList("nr-default", "nr-custom", "gsi-default");
        ArrayList<String> engineNameToQuery = new ArrayList<String>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100000000; ++i) {
            engineNameToQuery.add(engineNames.get(random.nextInt(2)));
        }
        long startTime = System.currentTimeMillis();
        for (String name : engineNameToQuery) {
            engines1.get(name);
        }
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("hash map: " + cost);
        startTime = System.currentTimeMillis();
        for (String name : engineNameToQuery) {
            engines2.get(name);
        }
        cost = System.currentTimeMillis() - startTime;
        System.out.println("concurrent hash map: " + cost);
        startTime = System.currentTimeMillis();
        for (String name : engineNameToQuery) {
            IProcessEngine e;
            Iterator iterator = engines3.iterator();
            while (iterator.hasNext() && !(e = (IProcessEngine)iterator.next()).getName().equals(name)) {
            }
        }
        cost = System.currentTimeMillis() - startTime;
        System.out.println("arraylist map: " + cost);
    }

    private static class InternalProcessEngine
    implements IProcessEngine {
        final String name;
        private final String title;
        private final IProcessDefinitionService processDefintionService;
        private final IProcessRuntimeService processRuntimeService;
        private final IProcessStatsService processStatService;
        private final ILanguagePacket languagePacket;
        private final IProcessIOService processIOService;

        public InternalProcessEngine(ProcessEngineRegisteration registeration) {
            this.name = registeration.getName();
            this.title = registeration.getTitle();
            this.processDefintionService = registeration.getProcessDefinitionService();
            this.processRuntimeService = registeration.getProcessRuntimeService();
            this.processStatService = registeration.getProcessStatsService();
            this.languagePacket = registeration.getLanguagePacket();
            this.processIOService = registeration.getProcessIOService();
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public IProcessDefinitionService getProcessDefinitionService() {
            return this.processDefintionService;
        }

        @Override
        public IProcessRuntimeService getProcessRuntimeService() {
            return this.processRuntimeService;
        }

        @Override
        public IProcessStatsService getProcessStatsService() {
            return this.processStatService;
        }

        @Override
        public ILanguagePacket getLanguagePacket() {
            return this.languagePacket;
        }

        @Override
        public IProcessIOService getProcessIOService() {
            return this.processIOService;
        }
    }
}

