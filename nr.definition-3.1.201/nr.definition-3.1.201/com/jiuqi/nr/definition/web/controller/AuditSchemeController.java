/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.definition.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.web.vo.AuditSchemeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/definition/auditscheme"})
@Api(tags={"\u4e0a\u62a5\u524d\u5ba1\u6838\u4e2a\u6027\u5316\u65b9\u6848"})
public class AuditSchemeController {
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    @GetMapping(value={"/get"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e")
    public List<AuditSchemeVO> dataGet() throws JQException {
        try {
            List<AuditType> auditTypes = this.auditTypeDefineService.queryAllAuditType();
            ArrayList<AuditSchemeVO> res = new ArrayList<AuditSchemeVO>();
            res.add(new AuditSchemeVO("AUDIT_SCHEME_CONDITION", "\u9002\u7528\u6761\u4ef6"));
            for (AuditType auditType : auditTypes) {
                AuditSchemeVO auditSchemeVO = new AuditSchemeVO();
                auditSchemeVO.setCode(String.valueOf(auditType.getCode()));
                auditSchemeVO.setTitle(auditType.getTitle());
                auditSchemeVO.setValue("2");
                res.add(auditSchemeVO);
            }
            return res;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0104, e.getMessage());
        }
    }
}

