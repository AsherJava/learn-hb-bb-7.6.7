/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.util.CollectionUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.formtype.web.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.formtype.web.vo.FormTypeDataVO;
import com.jiuqi.nr.formtype.web.vo.FormTypeGroupVO;
import com.jiuqi.nr.formtype.web.vo.FormTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/formtype/"})
@Api(tags={"\u62a5\u8868\u7c7b\u578b\u7f16\u8f91\u670d\u52a1"})
public class FormTypeController {
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private IFormTypeGroupService iFormTypeGroupService;

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u5206\u7ec4")
    @PostMapping(value={"add/group"})
    public String addGroup(@RequestBody FormTypeGroupVO vo) throws JQException {
        FormTypeGroupDefine define = this.iFormTypeGroupService.createFormTypeGroup();
        vo.toDefine(define);
        this.iFormTypeGroupService.insertFormTypeGroup(define);
        return define.getId();
    }

    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u5206\u7ec4")
    @PostMapping(value={"update/group"})
    public void updateGroup(@RequestBody FormTypeGroupVO vo) throws JQException {
        FormTypeGroupDefine define = this.iFormTypeGroupService.createFormTypeGroup();
        vo.toDefine(define);
        this.iFormTypeGroupService.updateFormTypeGroup(define);
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u7c7b\u578b\u5206\u7ec4")
    @PostMapping(value={"delete/group/{groupKey}"})
    public void deleteGroup(@PathVariable String groupKey) throws JQException {
        this.iFormTypeGroupService.deleteFormTypeGroup(groupKey);
    }

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868\u7c7b\u578b")
    @PostMapping(value={"add/formtype"})
    public String addFormType(@RequestBody FormTypeVO vo) throws JQException {
        FormTypeDefine define = this.iFormTypeService.createFormType();
        vo.toDefine(define);
        this.iFormTypeService.insertFormType(define, vo.isCreateDefaultDatas());
        return define.getId();
    }

    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u7c7b\u578b")
    @PostMapping(value={"update/formtype"})
    public void updateFormType(@RequestBody FormTypeVO vo) throws JQException {
        FormTypeDefine define = this.iFormTypeService.createFormType();
        vo.toDefine(define);
        this.iFormTypeService.updateFormType(define);
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u7c7b\u578b")
    @PostMapping(value={"delete/formtype"})
    public void deleteFormType(@RequestBody FormTypeVO vo) throws JQException {
        FormTypeDefine define = this.iFormTypeService.createFormType();
        vo.toDefine(define);
        this.iFormTypeService.deleteFormType(define);
    }

    @ApiOperation(value="\u65b0\u589e\u62a5\u8868\u7c7b\u578b\u6570\u636e\u9879")
    @PostMapping(value={"add/data"})
    public String addFormTypeData(@RequestBody FormTypeDataVO vo) throws JQException {
        FormTypeDataDefine define = this.iFormTypeService.createFormTypeData();
        vo.toDefine(define);
        this.iFormTypeService.insertFormTypeData(define);
        return define.getId().toString();
    }

    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u7c7b\u578b\u6570\u636e\u9879")
    @PostMapping(value={"update/data"})
    public void updateFormTypeData(@RequestBody FormTypeDataVO vo) throws JQException {
        FormTypeDataDefine define = this.iFormTypeService.createFormTypeData();
        vo.toDefine(define);
        this.iFormTypeService.updateFormTypeData(define);
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u7c7b\u578b\u6570\u636e\u9879")
    @PostMapping(value={"delete/datas"})
    public void deleteFormTypeData(@RequestBody List<FormTypeDataVO> vos) throws JQException {
        if (CollectionUtils.isEmpty(vos)) {
            return;
        }
        List<FormTypeDataDefine> defines = vos.stream().map(vo -> {
            FormTypeDataDefine define = this.iFormTypeService.createFormTypeData();
            vo.toDefine(define);
            return define;
        }).collect(Collectors.toList());
        this.iFormTypeService.deleteFormTypeData(defines);
    }

    @ApiOperation(value="\u5206\u7ec4\u4ea4\u6362\u4f4d\u7f6e")
    @PostMapping(value={"exchange/group/{key}/{targetKey}"})
    public void groupExchange(@PathVariable String key, @PathVariable String targetKey) throws JQException {
        FormTypeGroupDefine group = this.iFormTypeGroupService.queryById(key);
        FormTypeGroupDefine targetGroup = this.iFormTypeGroupService.queryById(targetKey);
        String order = group.getOrder();
        group.setOrder(targetGroup.getOrder());
        targetGroup.setOrder(order);
        this.iFormTypeGroupService.updateFormTypeGroup(new FormTypeGroupDefine[]{group, targetGroup});
    }

    @ApiOperation(value="\u5206\u7ec4\u4ea4\u6362\u4f4d\u7f6e")
    @PostMapping(value={"exchange/formtype/{key}/{targetKey}"})
    public void formTypeExchange(@PathVariable String key, @PathVariable String targetKey) throws JQException {
        this.iFormTypeService.formTypeExchange(key, targetKey);
    }

    @ApiOperation(value="\u6570\u636e\u9879\u4e0a\u79fb")
    @PostMapping(value={"data/moveup/{formTypeCode}/{key}"})
    public void dataMoveUp(@PathVariable String formTypeCode, @PathVariable String key) {
        this.iFormTypeService.moveData(UUID.fromString(key), formTypeCode, true);
    }

    @ApiOperation(value="\u6570\u636e\u9879\u4e0b\u79fb")
    @PostMapping(value={"data/movedown/{formTypeCode}/{key}"})
    public void dataMoveDown(@PathVariable String formTypeCode, @PathVariable String key) {
        this.iFormTypeService.moveData(UUID.fromString(key), formTypeCode, false);
    }
}

