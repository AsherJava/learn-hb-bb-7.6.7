/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 */
package com.jiuqi.nr.workflow2.todo.register;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoClearExtendRegister
implements IDataSchemeDataClearExtendService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TodoManipulationService todoManipulationService;

    public void doClear(DataClearParamObj clearParam) {
        List taskKeyList = clearParam.getTaskKey();
        for (String taskKey : taskKeyList) {
            List formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(taskKey);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                this.todoManipulationService.deleteTodoMessageByFormSchemeKey(formSchemeDefine.getKey());
            }
        }
    }
}

