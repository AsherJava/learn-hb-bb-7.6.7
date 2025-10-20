/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest.external;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DesignTimeViewParaController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @GetMapping(value={"/api/getAllDesignTasks"})
    public List<DesignTaskDefine> getAllTaskDefines() {
        return this.nrDesignTimeController.getAllTaskDefines();
    }

    @GetMapping(value={"/api/formSchemesByTask/{taskId}"})
    public List<DesignFormSchemeDefine> getFormSchemesByTask(@PathVariable(value="taskId") String taskId) throws JQException {
        return this.nrDesignTimeController.queryFormSchemeByTask(taskId);
    }

    @GetMapping(value={"/api/formGroupsByScheme/{schemeId}"})
    public List<DesignFormGroupDefine> getFormGroup(@PathVariable(value="schemeId") String schemeId) throws JQException {
        return this.nrDesignTimeController.queryRootGroupsByFormScheme(schemeId);
    }

    @GetMapping(value={"/api/formsByGroup/{groupId}"})
    public List<DesignFormDefine> getFormsByGroup(@PathVariable(value="groupId") String groupId) throws JQException {
        return this.nrDesignTimeController.getAllFormsInGroupWithoutBinaryData(groupId);
    }
}

