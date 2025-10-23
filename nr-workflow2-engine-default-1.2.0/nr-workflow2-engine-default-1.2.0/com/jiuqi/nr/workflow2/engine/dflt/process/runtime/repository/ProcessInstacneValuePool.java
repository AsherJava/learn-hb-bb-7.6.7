/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import java.util.HashMap;
import java.util.Map;

abstract class ProcessInstacneValuePool {
    static ProcessInstacneValuePool EMPTY = new EmptyValuePool();

    ProcessInstacneValuePool() {
    }

    public static ProcessInstacneValuePool createPool() {
        return new ValuePool();
    }

    abstract String putDimensionValueIfAbsent(String var1, String var2);

    abstract String putFormOrGroupIfAbsent(String var1);

    abstract String putProcessDefinitionIdIfAbsent(String var1);

    abstract String putNodeIdIfAbsent(String var1);

    abstract String putStatusIdIfAbsent(String var1);

    abstract String putUserIdIfAbsent(String var1);

    static class EmptyValuePool
    extends ProcessInstacneValuePool {
        EmptyValuePool() {
        }

        @Override
        public String putDimensionValueIfAbsent(String dimensionName, String newValue) {
            return newValue;
        }

        @Override
        public String putFormOrGroupIfAbsent(String newValue) {
            return newValue;
        }

        @Override
        public String putProcessDefinitionIdIfAbsent(String newValue) {
            return newValue;
        }

        @Override
        public String putNodeIdIfAbsent(String newValue) {
            return newValue;
        }

        @Override
        public String putStatusIdIfAbsent(String newValue) {
            return newValue;
        }

        @Override
        public String putUserIdIfAbsent(String newValue) {
            return newValue;
        }
    }

    static class ValuePool
    extends ProcessInstacneValuePool {
        Map<String, Map<String, String>> dimensionValuePools = new HashMap<String, Map<String, String>>();
        Map<String, String> formKeyOrFormGroupKeyPool = new HashMap<String, String>();
        Map<String, String> processDefinitionIdPool = new HashMap<String, String>(1);
        Map<String, String> nodeIdPool = new HashMap<String, String>(8);
        Map<String, String> statusIdPool = new HashMap<String, String>(8);
        Map<String, String> userIdPool = new HashMap<String, String>();

        @Override
        public String putDimensionValueIfAbsent(String dimensionName, String newValue) {
            String oldValue;
            Map<String, String> dimensionValuePool = this.dimensionValuePools.get(dimensionName);
            if (dimensionValuePool == null) {
                dimensionValuePool = new HashMap<String, String>();
                this.dimensionValuePools.put(dimensionName, dimensionValuePool);
            }
            return (oldValue = dimensionValuePool.putIfAbsent(newValue, newValue)) == null ? newValue : oldValue;
        }

        @Override
        public String putFormOrGroupIfAbsent(String newValue) {
            String oldValue = this.formKeyOrFormGroupKeyPool.putIfAbsent(newValue, newValue);
            return oldValue == null ? newValue : oldValue;
        }

        @Override
        public String putProcessDefinitionIdIfAbsent(String newValue) {
            String oldValue = this.processDefinitionIdPool.putIfAbsent(newValue, newValue);
            return oldValue == null ? newValue : oldValue;
        }

        @Override
        public String putNodeIdIfAbsent(String newValue) {
            String oldValue = this.nodeIdPool.putIfAbsent(newValue, newValue);
            return oldValue == null ? newValue : oldValue;
        }

        @Override
        public String putStatusIdIfAbsent(String newValue) {
            String oldValue = this.statusIdPool.putIfAbsent(newValue, newValue);
            return oldValue == null ? newValue : oldValue;
        }

        @Override
        public String putUserIdIfAbsent(String newValue) {
            String oldValue = this.userIdPool.putIfAbsent(newValue, newValue);
            return oldValue == null ? newValue : oldValue;
        }
    }
}

