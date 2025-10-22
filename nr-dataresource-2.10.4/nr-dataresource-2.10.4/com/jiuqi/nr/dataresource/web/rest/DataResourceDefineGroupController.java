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
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.dataresource.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.dataresource.web.vo.DataResourceDefineGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/dataresource/"})
@Api(tags={"\u6570\u636e\u8d44\u6e90\uff1a\u5206\u7ec4API"})
public class DataResourceDefineGroupController {
    @Autowired
    private IDataResourceDefineGroupService groupService;

    @ApiOperation(value="\u5206\u7ec4/\u76ee\u5f55\u67e5\u8be2")
    @GetMapping(value={"group/get"})
    public DataResourceDefineGroupVO queryDataResource(@RequestParam String key) {
        DataResourceDefineGroup group = this.groupService.get(key);
        if (group == null) {
            return null;
        }
        return new DataResourceDefineGroupVO(group);
    }

    @ApiOperation(value="\u5206\u7ec4/\u76ee\u5f55\u6dfb\u52a0")
    @PostMapping(value={"group/add"})
    public String addDataResource(@RequestBody DataResourceDefineGroupVO resourceVO) throws JQException {
        try {
            DataResourceDefineGroup group = resourceVO.toDg(this.groupService);
            return this.groupService.insert(group);
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u5206\u7ec4/\u76ee\u5f55\u5220\u9664")
    @PostMapping(value={"group/del"})
    public void deleteDataResource(@RequestParam String key) throws JQException {
        try {
            this.groupService.delete(key);
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u5206\u7ec4/\u76ee\u5f55\u66f4\u65b0")
    @PostMapping(value={"group/update"})
    public void updateDataResource(@RequestBody DataResourceDefineGroupVO resourceVO) throws JQException {
        try {
            this.groupService.update(resourceVO.toDg(this.groupService));
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }
}

