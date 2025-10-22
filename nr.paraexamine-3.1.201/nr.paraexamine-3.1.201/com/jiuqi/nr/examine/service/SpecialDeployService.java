/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nr.file.FileService
 */
package com.jiuqi.nr.examine.service;

import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.file.FileService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SpecialDeployService {
    @Autowired
    List<NODDLDeployExecutor> noddlDeployExecutors;
    @Autowired
    FileService fileService;

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> ddlSqls = new ArrayList<String>();
        Collections.sort(this.noddlDeployExecutors, Comparator.comparingDouble(NODDLDeployExecutor::getOrder));
        for (NODDLDeployExecutor executor : this.noddlDeployExecutors) {
            List strings = executor.preDeploy(taskKey);
            if (CollectionUtils.isEmpty(strings)) continue;
            ddlSqls.addAll(strings);
        }
        return ddlSqls;
    }

    public void doDeploy(String taskKey) {
        this.noddlDeployExecutors.sort(Comparator.comparingDouble(NODDLDeployExecutor::getOrder));
        for (NODDLDeployExecutor executor : this.noddlDeployExecutors) {
            executor.doDeploy(taskKey);
        }
    }
}

