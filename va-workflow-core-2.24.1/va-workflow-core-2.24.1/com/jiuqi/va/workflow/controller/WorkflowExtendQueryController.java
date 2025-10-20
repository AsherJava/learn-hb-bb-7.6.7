/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.domain.WorkflowExtendQueryDTO;
import com.jiuqi.va.workflow.service.WorkflowExtendQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/extend"})
public class WorkflowExtendQueryController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowExtendQueryController.class);
    @Autowired
    private WorkflowExtendQueryService workflowExtendQueryService;

    @RequestMapping(value={"/user/list"})
    public PageVO<UserDO> queryUser(@RequestBody WorkflowExtendQueryDTO workflowExtendQueryDTO) {
        try {
            return this.workflowExtendQueryService.queryUser(workflowExtendQueryDTO);
        }
        catch (WorkflowException e) {
            logger.error(e.getMessage(), e);
            return new PageVO(null, 0, R.error((String)e.getMessage()));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PageVO(null, 0, R.error((String)"\u67e5\u8be2\u7528\u6237\u5217\u8868\u5931\u8d25"));
        }
    }

    @RequestMapping(value={"/staff/list"})
    public PageVO<BaseDataDO> query(@RequestBody WorkflowExtendQueryDTO workflowExtendQueryDTO) {
        try {
            return this.workflowExtendQueryService.queryStaff(workflowExtendQueryDTO);
        }
        catch (WorkflowException e) {
            logger.error(e.getMessage(), e);
            return new PageVO(null, 0, R.error((String)e.getMessage()));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new PageVO(null, 0, R.error((String)"\u67e5\u8be2\u804c\u5458\u5217\u8868\u5931\u8d25"));
        }
    }
}

