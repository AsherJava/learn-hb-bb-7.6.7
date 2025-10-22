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
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check2.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.system.check2.common.SystemCheck2ErrorEnum;
import com.jiuqi.nr.system.check2.service.SystemCheckExecutor;
import com.jiuqi.nr.system.check2.vo.ExecutorResult;
import com.jiuqi.nr.system.check2.vo.ResourceGroupVO;
import com.jiuqi.nr.system.check2.vo.ResourceVO;
import com.jiuqi.nr.system.check2.vo.SearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system_check2"})
@Api(tags={"\u53c2\u6570\u68c0\u67e5"})
public class SystemCheck2Controller {
    private static final Logger logger = LoggerFactory.getLogger(SystemCheck2Controller.class);
    @Autowired
    private SystemCheckExecutor systemCheckExecutor;

    @ApiOperation(value="\u83b7\u53d6\u8d44\u6e90\u5206\u7ec4")
    @GetMapping(value={"/get_resource_group"})
    public List<ResourceGroupVO> getAllResourceGroupVO() throws JQException {
        try {
            List<ResourceGroupVO> result = this.systemCheckExecutor.getResourcesGroup();
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SystemCheck2ErrorEnum.SYSTEM_CHECK2_001, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8d44\u6e90")
    @GetMapping(value={"/get_resource"})
    public List<ResourceVO> getAllResourceVO() throws JQException {
        try {
            List<ResourceVO> result = this.systemCheckExecutor.getResourceVOS();
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SystemCheck2ErrorEnum.SYSTEM_CHECK2_001, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6267\u884c\u6309\u94ae\u64cd\u4f5c")
    @GetMapping(value={"/executor_resource/{userId}/{resourceKey}"})
    public ExecutorResult doExecutor(@PathVariable String userId, @PathVariable String resourceKey) throws JQException {
        try {
            ExecutorResult results = this.systemCheckExecutor.doExecutor(userId, resourceKey);
            return results;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SystemCheck2ErrorEnum.SYSTEM_CHECK2_002, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6267\u884c\u6309\u94ae\u64cd\u4f5c")
    @PostMapping(value={"/search"})
    public List<ResourceVO> doSearch(@RequestBody SearchVO searchVO) throws JQException {
        try {
            return this.systemCheckExecutor.doSearch(searchVO.getGroupKey(), searchVO.getKeyword());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SystemCheck2ErrorEnum.SYSTEM_CHECK2_003, (Throwable)e);
        }
    }
}

