/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.common.PageSize
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.rest.vo.PrintSettingVO;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"Excel \u6253\u5370\u8bbe\u7f6e"})
public class PrintSettingController {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @ApiOperation(value="\u67e5\u8be2 Excel \u6253\u5370\u8bbe\u7f6e")
    @GetMapping(value={"excel/print/setting/query/{printSchemeKey}/{formKey}"})
    public PrintSettingVO query(@PathVariable String printSchemeKey, @PathVariable String formKey) throws JQException {
        PrintSettingVO vo = null;
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
        DesignPrintSettingDefine define = this.nrDesignTimeController.getPrintSettingDefine(printSchemeKey, formKey);
        if (null == define) {
            vo = new PrintSettingVO();
            vo.setPrintSchemeKey(printSchemeKey);
            vo.setFormKey(formKey);
        } else {
            vo = new PrintSettingVO(define);
        }
        HashMap<Integer, String> pageSizes = new HashMap<Integer, String>();
        for (PageSize pageSize : PageSize.values()) {
            pageSizes.put(pageSize.getValue(), pageSize.getTitle());
        }
        vo.setPageSizes(pageSizes);
        byte[] data = formDefine.getBinaryData();
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])data);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        objectMapper.registerModule((Module)module);
        try {
            vo.setFormStyle(objectMapper.writeValueAsString((Object)grid2Data));
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_118);
        }
        return vo;
    }

    @ApiOperation(value="\u4fdd\u5b58 Excel \u6253\u5370\u8bbe\u7f6e")
    @PostMapping(value={"excel/print/setting/save"})
    public void save(@RequestBody PrintSettingVO vo) throws JQException {
        DesignPrintSettingDefine define = this.nrDesignTimeController.getPrintSettingDefine(vo.getPrintSchemeKey(), vo.getFormKey());
        if (null == define) {
            define = this.nrDesignTimeController.createDesignPrintSettingDefine();
            vo.value2Define(define);
            this.nrDesignTimeController.insertPrintSettingDefine(define);
        } else {
            vo.value2Define(define);
            this.nrDesignTimeController.updatePrintSettingDefine(define);
        }
    }

    @ApiOperation(value="\u91cd\u7f6e Excel \u6253\u5370\u8bbe\u7f6e")
    @GetMapping(value={"excel/print/setting/reset/{printSchemeKey}/{formKey}"})
    public PrintSettingVO reset(@PathVariable String printSchemeKey, @PathVariable String formKey) throws JQException {
        this.nrDesignTimeController.deletePrintSettingDefine(printSchemeKey, formKey);
        return this.query(printSchemeKey, formKey);
    }

    @ApiOperation(value="\u5220\u9664 Excel \u6253\u5370\u8bbe\u7f6e")
    @GetMapping(value={"excel/print/setting/delete/{printSchemeKey}"})
    public void delete(@PathVariable String printSchemeKey) throws JQException {
        this.nrDesignTimeController.deletePrintSettingDefine(printSchemeKey);
    }
}

