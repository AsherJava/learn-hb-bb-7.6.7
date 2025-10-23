/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.conditionalstyle.web.vo.RegionVO
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.conditionalstyle.web.vo.RegionVO;
import com.jiuqi.nr.task.form.controller.vo.ConditionalStylePosVO;
import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import com.jiuqi.nr.task.form.service.IConditionStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/conditional-style/"})
@Api(tags={"\u6761\u4ef6\u6837\u5f0f"})
public class ConditionalStyleController {
    @Autowired
    private IConditionStyleService conditionStyleService;

    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u4e0b\u6240\u6709\u6761\u4ef6\u6837\u5f0f")
    @GetMapping(value={"getByForm/{formKey}"})
    public List<ConditionStyleDTO> getByForm(@PathVariable String formKey) {
        List<ConditionStyleDTO> conditionStyles = this.conditionStyleService.getByForm(formKey);
        return conditionStyles;
    }

    @ApiOperation(value="\u83b7\u53d6\u9009\u4e2d\u533a\u57df\u4e0b\u7684\u6761\u4ef6\u6837\u5f0f")
    @PostMapping(value={"getByRegion"})
    public List<ConditionStyleDTO> getByRegion(@RequestBody RegionVO regionVO) {
        List<ConditionStyleDTO> conditionStyles = this.conditionStyleService.getByRegion(regionVO.getFormKey(), regionVO.getStart(), regionVO.getEnd());
        return conditionStyles;
    }

    @ApiOperation(value="\u83b7\u53d6\u67d0\u4e2a\u5355\u5143\u683c\u7684\u6761\u4ef6\u6837\u5f0f")
    @PostMapping(value={"getByPos"})
    public List<ConditionStyleDTO> getByPos(@RequestBody ConditionalStylePosVO posVO) {
        List<ConditionStyleDTO> conditionStyles = this.conditionStyleService.getByPos(posVO.getFormKey(), posVO.getPosX(), posVO.getPoxY());
        return conditionStyles;
    }

    @ApiOperation(value="\u6821\u9a8c\u9009\u4e2d\u533a\u57df\u4e2d\u7684\u6761\u4ef6\u6837\u5f0f\u662f\u5426\u51b2\u7a81")
    @PostMapping(value={"check-difference"})
    public boolean checkDifference(@RequestBody RegionVO param) {
        return this.conditionStyleService.checkDifferent(param.getFormKey(), param.getStart(), param.getEnd());
    }
}

