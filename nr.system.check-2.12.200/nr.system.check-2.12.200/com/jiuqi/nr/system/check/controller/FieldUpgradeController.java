/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.system.check.exception.ExceptionWrapper;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.EntityVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TaskInfoVO;
import com.jiuqi.nr.system.check.model.response.fieldupgrade.TreeNode;
import com.jiuqi.nr.system.check.service.FieldUpgradeService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check/field-upgrade"})
@Api(tags={"\u7cfb\u7edf\u68c0\u67e5"})
public class FieldUpgradeController {
    @Autowired
    private FieldUpgradeService fieldUpgradeService;

    @GetMapping(value={"/all-task"})
    public List<TreeNode> treeNodeList() throws JQException {
        try {
            return this.fieldUpgradeService.treeNodeList();
        }
        catch (Exception e) {
            throw ExceptionWrapper.wrapper(e);
        }
    }

    @PostMapping(value={"/formscheme"})
    public List<TaskInfoVO> getFormSchemeVOList(@RequestBody List<String> keys) throws JQException {
        try {
            return this.fieldUpgradeService.getTaskInfo(keys);
        }
        catch (Exception e) {
            throw ExceptionWrapper.wrapper(e);
        }
    }

    @GetMapping(value={"/entity/{key}"})
    public List<EntityVO> getSelectedEntities(@PathVariable String key) throws JQException {
        try {
            return this.fieldUpgradeService.getSelectedEntities(key);
        }
        catch (Exception e) {
            throw ExceptionWrapper.wrapper(e);
        }
    }
}

