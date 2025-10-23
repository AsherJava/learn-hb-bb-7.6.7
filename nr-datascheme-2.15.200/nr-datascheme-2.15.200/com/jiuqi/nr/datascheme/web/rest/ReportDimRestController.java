/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.web.param.ReportDimFieldVO;
import com.jiuqi.nr.datascheme.web.param.ShowFieldPM;
import com.jiuqi.nr.datascheme.web.param.ShowFieldVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u586b\u62a5\u60c5\u666f"})
public class ReportDimRestController {
    private final IEntityMetaService entityMetaService;
    @Value(value="${jiuqi.nr.task2.enable:false}")
    private Boolean taskV2 = false;

    public ReportDimRestController(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    @ApiOperation(value="\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u5c5e\u6027")
    @PostMapping(value={"report-dim/entity-field/get"})
    public ReportDimFieldVO getEntityAttribute(@RequestBody ShowFieldPM showFieldPM) {
        ReportDimFieldVO res = new ReportDimFieldVO();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(showFieldPM.getOrgDimKey());
        if (entityModel == null) {
            return res;
        }
        List entityRefers = this.entityMetaService.getEntityRefer(showFieldPM.getOrgDimKey());
        List<String> dimKeys = showFieldPM.getDimKeys();
        if (CollectionUtils.isEmpty(entityRefers)) {
            return res;
        }
        Set include = entityRefers.stream().map(IEntityRefer::getOwnField).collect(Collectors.toSet());
        List<IEntityAttribute> showFields = entityModel.getShowFields().stream().filter(x -> include.contains(x.getCode())).collect(Collectors.toList());
        List<ShowFieldVO> convert = this.convert(showFields);
        res.setShowFields(convert);
        res.setTaskV2(this.taskV2);
        HashMap<String, String> defaultCode = new HashMap<String, String>();
        for (String dimKey : dimKeys) {
            IEntityRefer entityRefer = entityRefers.stream().filter(x -> x.getReferEntityId().equals(dimKey)).findFirst().orElse(null);
            if (!Objects.nonNull(entityRefer)) continue;
            for (ShowFieldVO vo : convert) {
                if (!vo.getCode().equals(entityRefer.getOwnField())) continue;
                defaultCode.put(dimKey, vo.getCode());
            }
        }
        res.setDefaultCode(defaultCode);
        return res;
    }

    private List<ShowFieldVO> convert(List<IEntityAttribute> showFields) {
        ArrayList<ShowFieldVO> list = new ArrayList<ShowFieldVO>(showFields.size());
        showFields.forEach(x -> list.add(ShowFieldVO.convert(x)));
        return list;
    }

    @ApiOperation(value="\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u548c\u60c5\u666f\u5173\u8054\u7684\u5c5e\u6027")
    @GetMapping(value={"report-dim/attribute/get"})
    public ShowFieldVO getDimAttribute(@RequestParam String orgDimKey, @RequestParam String dimKey) {
        List entityRefer = this.entityMetaService.getEntityRefer(orgDimKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(orgDimKey);
        if (CollectionUtils.isEmpty(entityRefer)) {
            return null;
        }
        IEntityRefer refer = entityRefer.stream().filter(x -> x.getReferEntityId().equals(dimKey)).findFirst().orElse(null);
        if (Objects.isNull(refer)) {
            return null;
        }
        ShowFieldVO vo = new ShowFieldVO();
        vo.setTitle(entityModel.getAttribute(refer.getOwnField()).getTitle());
        vo.setCode(refer.getOwnField());
        return vo;
    }
}

