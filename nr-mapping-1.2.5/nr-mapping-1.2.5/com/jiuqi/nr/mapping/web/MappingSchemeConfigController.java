/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.mapping.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.mapping.bean.MappingConfig;
import com.jiuqi.nr.mapping.service.MappingSchemeConfigService;
import com.jiuqi.nr.mapping.web.vo.EntityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/mapping/config"})
@Api(tags={"\u6620\u5c04\u65b9\u6848\u914d\u7f6e"})
public class MappingSchemeConfigController {
    @Autowired
    private MappingSchemeConfigService mappingSchemeConfigService;

    @GetMapping(value={"/query-config/{mappingSchemeId}"})
    @ApiOperation(value="\u83b7\u53d6\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public MappingConfig queryConfig(@PathVariable String mappingSchemeId) throws JQException {
        return this.mappingSchemeConfigService.query(mappingSchemeId);
    }

    @PostMapping(value={"/update-config/{mappingSchemeId}"})
    @ApiOperation(value="\u66f4\u65b0\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public void updateConfig(@PathVariable String mappingSchemeId, @Valid @RequestBody MappingConfig mappingConfig) throws JQException {
        this.mappingSchemeConfigService.update(mappingSchemeId, mappingConfig);
    }

    @GetMapping(value={"/query-attribute/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u7ef4\u5ea6\u7684\u5c5e\u6027")
    public List<EntityVO> queryEntityAttribute(@PathVariable String formSchemekey) throws Exception {
        return this.mappingSchemeConfigService.queryEntityAttribute(formSchemekey);
    }

    @GetMapping(value={"/query-formula-scheme/{reportKey}"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u65b9\u6848")
    public List<FormulaSchemeDefine> querySchemeByReport(@PathVariable(value="reportKey") String reportKey) {
        return this.mappingSchemeConfigService.getFormulaSchemesByReport(reportKey);
    }

    @GetMapping(value={"/query-entityId/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u7ef4\u5ea6\u5b9e\u4f53id")
    public String queryEntityIdByFormSchemeKey(@PathVariable String formSchemekey) throws Exception {
        return this.mappingSchemeConfigService.queryEntityIdByFormSchemeKey(formSchemekey);
    }
}

