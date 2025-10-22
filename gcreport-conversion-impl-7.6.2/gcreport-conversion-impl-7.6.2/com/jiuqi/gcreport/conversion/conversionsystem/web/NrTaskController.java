/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.conversion.conversionsystem.api.NrTaskClient
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO
 *  com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.conversion.conversionsystem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.conversionsystem.api.NrTaskClient;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class NrTaskController
implements NrTaskClient {
    @Autowired
    private ConversionSystemService service;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskCommonVO>> getTasksInfo() {
        return BusinessResponseEntity.ok(this.service.getTaskList());
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskCommonVO>> getSchemesInfo(@PathVariable(value="taskid") String taskid) {
        return BusinessResponseEntity.ok(this.service.getSchemeList(taskid));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskFormInfoVO>> queryAllFormDefinesInFormScheme(@PathVariable(value="schemeId") String schemeID) throws Exception {
        return BusinessResponseEntity.ok(this.service.getFormList(schemeID));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<FormulaSchemeObject>> getFormulaSchemes(@PathVariable(value="schemeId") String schemeID) {
        return BusinessResponseEntity.ok(this.service.getFormulaSchemeByFromScheme(schemeID));
    }
}

