/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.service.rest;

import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckConfigController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/entityCheckConfig"})
public class EntityCheckConfigService {
    private static final Logger logger = LoggerFactory.getLogger(EntityCheckConfigService.class);
    @Autowired
    private IEntityCheckConfigController entityCheckConfigController;

    @RequestMapping(value={"/getDataEntryInit"}, method={RequestMethod.GET})
    public FuncExecResult getDataEntryInit(String taskKey) throws Exception {
        return this.entityCheckConfigController.getDataEntryInit(taskKey);
    }

    @RequestMapping(value={"/getAssTasks"}, method={RequestMethod.GET})
    public List<SelectStructure> getAssTasks(String taskKey, String formSchemeKey) throws Exception {
        return this.entityCheckConfigController.getAssTasks(taskKey, formSchemeKey);
    }

    @RequestMapping(value={"/getAssFormSchemes"}, method={RequestMethod.GET})
    public List<SelectStructure> getAssFormSchemes(String taskKey, String formSchemeKey, String assTaskKey) {
        return this.entityCheckConfigController.getAssFormSchemes(taskKey, formSchemeKey, assTaskKey);
    }

    @RequestMapping(value={"/getAssociation"}, method={RequestMethod.GET})
    public Association getAssociation(String taskKey, String formSchemeKey, String assTaskKey, String assFormSchemeKey) {
        return this.entityCheckConfigController.getAssociation(taskKey, formSchemeKey, assTaskKey, assFormSchemeKey);
    }
}

