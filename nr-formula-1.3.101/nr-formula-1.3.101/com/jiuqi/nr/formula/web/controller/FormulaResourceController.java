/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.formula.utils.ErrorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/resource"})
public class FormulaResourceController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @GetMapping(value={"/task/get/{formScheme}"})
    public String getTaskKey(@PathVariable String formScheme) throws JQException {
        try {
            DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formScheme);
            if (formSchemeDefine != null) {
                DesignTaskDefine task = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey());
                return task == null ? null : task.getKey();
            }
            return null;
        }
        catch (Exception e) {
            throw ErrorWrapper.wrap(e);
        }
    }
}

