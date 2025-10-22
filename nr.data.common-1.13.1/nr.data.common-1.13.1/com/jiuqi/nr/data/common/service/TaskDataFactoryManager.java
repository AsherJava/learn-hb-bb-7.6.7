/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.nr.data.common.service.ImportResultDisplayCollector;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDataFactoryManager {
    @Autowired(required=false)
    private List<TaskDataFactory> factorys;
    @Autowired(required=false)
    private List<ImportResultDisplayCollector> resultDisplayCollectors;
    public static final String OVERVIEW_FACTORY_CODE = "OVERVIEW";

    public TaskDataFactory getFactory(String code) {
        for (TaskDataFactory factory : this.factorys) {
            if (!factory.getCode().equals(code)) continue;
            return factory;
        }
        return null;
    }

    public List<TaskDataFactory> getFactory(List<String> codes) {
        ArrayList<TaskDataFactory> result = new ArrayList<TaskDataFactory>();
        for (TaskDataFactory factory : this.factorys) {
            if (!codes.contains(factory.getCode())) continue;
            result.add(factory);
        }
        return result;
    }

    public List<TaskDataFactory> getAllFactory() {
        return this.factorys;
    }

    public void resetFactory(TaskDataFactory factory) {
        for (int i = 0; i < this.factorys.size(); ++i) {
            if (!factory.getCode().equals(this.factorys.get(i).getCode())) continue;
            this.factorys.set(i, factory);
            return;
        }
    }

    public List<ImportResultDisplayCollector> getResultDisplayCollectors(List<String> codes) {
        ArrayList<ImportResultDisplayCollector> result = new ArrayList<ImportResultDisplayCollector>();
        for (ImportResultDisplayCollector resultDisplayCollector : this.resultDisplayCollectors) {
            if (!codes.contains(resultDisplayCollector.getCode())) continue;
            result.add(resultDisplayCollector);
        }
        return result;
    }
}

