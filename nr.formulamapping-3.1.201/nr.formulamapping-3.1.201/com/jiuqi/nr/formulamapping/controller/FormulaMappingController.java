/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaMappingsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formulamapping.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingsObj;
import com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaMappingsObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.formulamapping.common.MappingMode;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.FormulaMappingSchemeService;
import com.jiuqi.nr.formulamapping.service.IFormulaMappingService;
import com.jiuqi.nr.formulamapping.service.IQueryFormulaService;
import com.jiuqi.nr.formulamapping.service.InitTreeSevice;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/formula/mapping/"})
public class FormulaMappingController {
    @Autowired
    private InitTreeSevice initTreeSevice;
    @Autowired
    private IFormulaMappingService formulaMappingService;
    @Autowired
    private FormulaMappingSchemeService formulaMappingSchemeService;
    @Autowired
    private IQueryFormulaService iQueryFormulaService;

    @ApiOperation(value="\u521d\u59cb\u5316\u6811")
    @GetMapping(value={"init_tree/formula_mapping/{formulaSchemeKey}/{nodeKey}"})
    public List<TreeObj> initFormTree(@PathVariable(value="formulaSchemeKey") String formulaSchemeKey, @PathVariable(value="nodeKey") String nodeKey) throws JQException {
        return this.initTreeSevice.initFormTree(formulaSchemeKey, nodeKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"query/formulas"})
    List<QueryFormulaObj> queryFormulas(@RequestBody QueryFormulaMappingsObj queryFormulaMappingsObj) throws JQException {
        String formKey = queryFormulaMappingsObj.getFormKey();
        String groupKey = queryFormulaMappingsObj.getFormGroupKey();
        String formulaSchemeKey = queryFormulaMappingsObj.getMappingSchemekey();
        List<QueryFormulaObj> queryFormulaObjs = this.formulaMappingService.queryFormulas(formulaSchemeKey, groupKey, formKey);
        ArrayList<QueryFormulaObj> result = new ArrayList<QueryFormulaObj>();
        if (queryFormulaMappingsObj.getCheckType() != -1) {
            for (QueryFormulaObj queryFormulaObj : queryFormulaObjs) {
                if (queryFormulaObj.getCheckType() != queryFormulaMappingsObj.getCheckType()) continue;
                result.add(queryFormulaObj);
            }
        } else {
            result.addAll(queryFormulaObjs);
        }
        return result;
    }

    @ApiOperation(value="\u516c\u5f0f\u65b9\u6848/\u62a5\u8868\u5206\u7ec4/\u62a5\u8868\u5206\u9875\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"query/formula_mapping_by_page"})
    public FormulaMappingsObj queryFormulaMappings(@RequestBody QueryFormulaMappingsObj queryFormulaMappingsObj) throws Exception {
        FormulaMappingsObj formulaMappingsObj = new FormulaMappingsObj();
        List<FormulaMappingObj> formulaMappingObjs = this.formulaMappingService.queryByCondition(queryFormulaMappingsObj.getMappingSchemekey(), queryFormulaMappingsObj.getFormGroupKey(), queryFormulaMappingsObj.getFormKey(), queryFormulaMappingsObj.getKeyword(), queryFormulaMappingsObj.getMappingType());
        Long total = Integer.toUnsignedLong(formulaMappingObjs.size());
        int endRow = queryFormulaMappingsObj.getEndRow();
        int n = endRow = total.intValue() > endRow ? endRow : total.intValue();
        if (endRow != 0 && queryFormulaMappingsObj.getStarRow() - 1 == endRow) {
            queryFormulaMappingsObj.setStartPage(queryFormulaMappingsObj.getStartPage() - 1);
        }
        ArrayList<FormulaMappingObj> queryFormulaMappings = new ArrayList<FormulaMappingObj>();
        for (int i = 0; i < formulaMappingObjs.size(); ++i) {
            if (i < queryFormulaMappingsObj.getStarRow() - 1 || i >= endRow) continue;
            queryFormulaMappings.add(formulaMappingObjs.get(i));
        }
        formulaMappingsObj.setList(queryFormulaMappings);
        formulaMappingsObj.setTotal(total);
        formulaMappingsObj.setPage(queryFormulaMappingsObj.getStartPage());
        return formulaMappingsObj;
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"insert/formula_mapping"})
    public void addFormulaMappings(@RequestParam String schemeKey, @RequestBody FormulaMappingObj[] formulaMappings) throws JQException {
        this.formulaMappingService.addFormulaMappings(schemeKey, formulaMappings);
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"delete/formula_mapping"})
    public void deleteFormulaMappings(@RequestBody MappingParamsObj mappingParamsObj) throws JQException {
        if (mappingParamsObj.getFormulaMappings() == null) {
            mappingParamsObj.setFormulaMappings(new ArrayList());
        }
        this.formulaMappingService.deleteMappings(mappingParamsObj.getSchemeKey(), mappingParamsObj.getFormGroupKey(), mappingParamsObj.getFormKey(), mappingParamsObj.getFormulaMappings().toArray(new FormulaMappingObj[0]));
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"update/formula_mapping"})
    public void updateFormulaMapping(@RequestParam String schemeKey, @RequestBody FormulaMappingObj formulaMapping) throws JQException {
        formulaMapping.setMode(MappingMode.MANUAL.getValue());
        this.formulaMappingService.updateFormulaMapping(schemeKey, formulaMapping);
    }

    @ApiOperation(value="\u6279\u91cf\u4fee\u6539\u516c\u5f0f\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"clear/formula_mappings"})
    public void clearFormulaMappings(@RequestBody MappingParamsObj mappingParamsObj) throws JQException {
        this.formulaMappingService.deleteSourceMappings(mappingParamsObj.getSchemeKey(), mappingParamsObj.getFormGroupKey(), mappingParamsObj.getFormKey(), mappingParamsObj.getFormulaMappings().toArray(new FormulaMappingObj[0]));
    }

    @ApiOperation(value="\u81ea\u52a8\u516c\u5f0f\u6620\u5c04")
    @PostMapping(value={"auto/formula_mapping"})
    public void doMapping(@RequestBody MappingParamsObj mappingParamsObj) throws JQException {
        this.formulaMappingService.doMapping(mappingParamsObj);
    }

    @ApiOperation(value="\u5bfc\u5165\u516c\u5f0f\u6620\u5c04")
    @PostMapping(value={"import/formula_mapping"})
    public void importFormulaMapping(@RequestParam(value="file") MultipartFile file, @RequestParam String schemeKey) throws JQException, IOException {
        FormulaMappingSchemeDefine formulaMappingSchemeObj = this.formulaMappingSchemeService.queryFormulaMappingSchemeObjsByKey(schemeKey);
        if (formulaMappingSchemeObj != null) {
            try (InputStream is = file.getInputStream();){
                this.formulaMappingService.doImport(schemeKey, formulaMappingSchemeObj.getTargetFSKey(), formulaMappingSchemeObj.getSourceFSKey(), is);
            }
        } else {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_000);
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u516c\u5f0f\u6620\u5c04")
    @PostMapping(value={"export/formula_mapping"})
    public void exportFormulaMapping(@RequestBody ExportParamsObj exportParamsObj, HttpServletResponse res) throws JQException, IOException {
        String fileName = "\u6620\u5c04\u516c\u5f0f";
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        Workbook doExport = this.formulaMappingService.doExport(exportParamsObj);
        try (ServletOutputStream out = res.getOutputStream();){
            doExport.write((OutputStream)out);
            out.flush();
        }
    }

    @ApiOperation(value="\u516c\u5f0f\u5206\u7ea7\u67e5\u8be2")
    @GetMapping(value={"query/formulas/{formulaSchemeKey}/{formKey}"})
    public List<QueryFormulaObj> queryFormulas2(@PathVariable String formulaSchemeKey, @PathVariable String formKey) throws JQException {
        return this.iQueryFormulaService.queryFormulas(formulaSchemeKey, formKey);
    }

    @ApiOperation(value="\u516c\u5f0f\u5206\u7ea7\u67e5\u8be2")
    @PostMapping(value={"query/children/formulas/{formulaSchemeKey}"})
    public List<QueryFormulaObj> queryChildrenFormulas(@PathVariable String formulaSchemeKey, @RequestBody QueryFormulaObj formulaObj) throws JQException {
        return this.iQueryFormulaService.queryChildrenFormulas(formulaSchemeKey, formulaObj);
    }
}

