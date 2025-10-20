/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IReverseModelService;
import com.jiuqi.nr.designer.util.ReverseModelUtils;
import com.jiuqi.nr.designer.web.rest.param.ReverseBatchCheckPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCheckPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateFieldPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateTablePM;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataTableVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseFormVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u53cd\u5411\u5efa\u6a21"})
public class ReverseModelController {
    private final Logger logger = LoggerFactory.getLogger(ReverseModelController.class);
    private final NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ReverseModelUtils reverseModelUtils;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IReverseModelService reverseModelService;

    public ReverseModelController(NRDesignTimeController nrDesignTimeController) {
        this.nrDesignTimeController = nrDesignTimeController;
    }

    @ApiOperation(value="\u53cd\u5411\u5efa\u6a21\u67e5\u8be2\u62a5\u8868")
    @GetMapping(value={"reverse/form/get/{language}/{formKey}"})
    public ReverseFormVO getReverseForm(@PathVariable int language, @PathVariable String formKey) throws JQException {
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
        ReverseFormVO reverseFormVO = new ReverseFormVO();
        reverseFormVO.setLanguage(language);
        try {
            this.reverseModelUtils.fillForm(reverseFormVO, formDefine);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_211, e.getMessage());
        }
        return reverseFormVO;
    }

    @ApiOperation(value="\u53cd\u5411\u5efa\u6a21 \u521b\u5efa\u6570\u636e\u8868 \u7684\u6807\u8bc6")
    @PostMapping(value={"reverse/tables/table-get"})
    public Map<String, ReverseDataTableVO> creatTablesAndCodes(@RequestBody ReverseCreateTablePM tablePM) {
        HashMap<String, ReverseDataTableVO> res = new HashMap<String, ReverseDataTableVO>();
        List dataTables = this.designDataSchemeService.getAllDataTable();
        List<String> tableCodes = tablePM.getTableCodes();
        dataTables.forEach(d -> tableCodes.add(d.getCode()));
        for (ReverseCreateTablePM.Region region : tablePM.getRegions()) {
            if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                res.put(region.getRegionKey(), this.reverseModelService.createZBTables(tablePM, region, dataTables));
                continue;
            }
            if (region.getRegionKind() != DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() && region.getRegionKind() != DataRegionKind.DATA_REGION_ROW_LIST.getValue()) continue;
            res.put(region.getRegionKey(), this.reverseModelService.createMXTables(tablePM, region, dataTables));
        }
        return res;
    }

    @ApiOperation(value="\u6279\u91cf\u83b7\u53d6\u7b26\u5408\u89c4\u5219\u7684\u6307\u6807\u6807\u8bc6")
    @PostMapping(value={"reverse/fields/field-get"})
    public Map<String, List<ReverseDataFieldVO>> createFieldsAndCodes(@RequestBody ReverseCreateFieldPM fieldsPM) throws JQException {
        try {
            return this.reverseModelService.createFieldsAndCodes(fieldsPM);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_219, e.getMessage());
        }
    }

    @ApiOperation(value="\u53cd\u5411\u5efa\u6a21\u68c0\u67e5\u63a5\u53e3")
    @PostMapping(value={"reverse/fieldCode/check"})
    public ReturnObject reverseFieldCheck(@RequestBody ReverseCheckPM checkPM) {
        int count = 0;
        if (checkPM.getFieldKind() == DataFieldKind.FIELD.getValue()) {
            List fields = this.designDataSchemeService.getDataFieldByTable(checkPM.getTableKey());
            count = (int)fields.stream().filter(field -> field.getCode().equals(checkPM.getFieldCode())).count();
        } else if (checkPM.getFieldKind() == DataFieldKind.FIELD_ZB.getValue()) {
            List allDataFieldByKind = this.designDataSchemeService.getAllDataFieldByKind(checkPM.getDataSchemeKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.BUILT_IN_FIELD});
            count = (int)allDataFieldByKind.stream().filter(field -> field.getCode().equals(checkPM.getFieldCode())).count();
        }
        if (count > 0) {
            return new ReturnObject(false, "\u6807\u8bc6\u4e0d\u80fd\u91cd\u590d");
        }
        return new ReturnObject(true, "\u6821\u9a8c\u901a\u8fc7");
    }

    @ApiOperation(value="\u6279\u91cf\u68c0\u67e5\u63a5\u53e3")
    @PostMapping(value={"reverse/fieldCode/checks"})
    public Map<String, Boolean> reverseFieldChecks(@RequestBody ReverseBatchCheckPM checkPM) {
        return this.reverseModelService.reverseFieldChecks(checkPM);
    }

    @ApiOperation(value="\u53cd\u5411\u5efa\u6a21\u4fdd\u5b58\u63a5\u53e3")
    @PostMapping(value={"reverse/form/save"})
    @Transactional(readOnly=false, rollbackFor={Exception.class})
    public void reverseFieldsSave(@RequestBody ReverseFormVO reverseFormVO) throws JQException {
        this.reverseModelService.reverseFieldsSave(reverseFormVO);
    }
}

