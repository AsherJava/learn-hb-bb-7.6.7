/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JQException
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bpm.instance.web;

import com.jiuqi.bi.util.JQException;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.instance.bean.CorporateParam;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value={"/api/workflow/instacne/extend"})
@RestController
public class WorkflowInstanceManageWebExtend {
    @Autowired
    private WorkflowInstanceService workflowInstanceService;

    @NRContextBuild
    @RequestMapping(value={"/queryCorporateValueLists"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u7684\u503c")
    @ResponseBody
    public List<CorporateData> queryCorporateValueLists(@RequestBody CorporateParam corporateParam) throws JQException {
        try {
            return this.workflowInstanceService.queryCorporateList(corporateParam.getTaskid());
        }
        catch (Exception e) {
            throw new JQException("\u83b7\u53d6\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u7684\u503c\u5931\u8d25");
        }
    }
}

