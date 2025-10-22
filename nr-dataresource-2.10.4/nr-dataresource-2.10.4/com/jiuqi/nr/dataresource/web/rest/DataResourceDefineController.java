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
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.web.vo.DataResourceDefineVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/dataresource/"})
@Api(tags={"\u6570\u636e\u8d44\u6e90\uff1a\u8d44\u6e90\u6811API"})
public class DataResourceDefineController {
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private DataResourceAuthorityService auth;

    @ApiOperation(value="\u8d44\u6e90\u6811\u67e5\u8be2")
    @GetMapping(value={"define/get"})
    public DataResourceDefineVO queryDataResource(@RequestParam String key) {
        DataResourceDefine dataResourceDefine = this.defineService.get(key);
        if (dataResourceDefine == null) {
            return null;
        }
        DataResourceDefineVO vo = new DataResourceDefineVO(dataResourceDefine);
        vo.setCanWrite(this.auth.canWrite(dataResourceDefine.getKey(), NodeType.TREE.getValue()));
        return vo;
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u67e5\u8be2")
    @GetMapping(value={"define/group"})
    public List<DataResourceDefineVO> queryDataResourceByGroup(@RequestParam String key) {
        ArrayList<DataResourceDefineVO> defines = new ArrayList<DataResourceDefineVO>();
        List<DataResourceDefine> byGroupKey = this.defineService.getByGroupKey(key);
        for (DataResourceDefine dataResourceDefine : byGroupKey) {
            DataResourceDefineVO vo = new DataResourceDefineVO(dataResourceDefine);
            vo.setCanWrite(this.auth.canWrite(dataResourceDefine.getKey(), NodeType.TREE.getValue()));
            defines.add(vo);
        }
        return defines;
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u6dfb\u52a0")
    @PostMapping(value={"define/add"})
    public String addDataResource(@RequestBody DataResourceDefineVO resourceVO) throws JQException {
        try {
            return this.defineService.insert(resourceVO.toDd(this.defineService));
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u5220\u9664")
    @PostMapping(value={"define/del"})
    public void deleteDataResource(@RequestParam String key) throws JQException {
        try {
            this.defineService.delete(key);
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u66f4\u65b0")
    @PostMapping(value={"define/update"})
    public void updateDataResource(@RequestBody DataResourceDefineVO resourceVO) throws JQException {
        try {
            this.defineService.update(resourceVO.toDd(this.defineService));
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u590d\u5236")
    @PostMapping(value={"define/copy"})
    public String copyDataResource(@RequestBody DataResourceDefineVO resourceVO) throws JQException {
        try {
            return this.defineService.copy(resourceVO.toDd(this.defineService), resourceVO.getKey());
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u6743\u9650")
    @GetMapping(value={"define/auth"})
    public boolean getAuth(String key) {
        return this.auth.canWrite(key, NodeType.TREE.getValue());
    }
}

