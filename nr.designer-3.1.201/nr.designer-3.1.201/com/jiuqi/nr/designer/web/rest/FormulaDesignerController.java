/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.editor.EditorParam
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.editor.EditorParam;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.facade.FunctionObj;
import com.jiuqi.nr.designer.web.facade.ParameterObj;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormTree;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import com.jiuqi.nr.designer.web.service.FormulaDesignerService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u516c\u5f0f\u7f16\u8f91\u5668"})
@RequestMapping(value={"api/v1/designer/formula_designer/"})
public class FormulaDesignerController {
    private static final Logger log = LoggerFactory.getLogger(FormulaDesignerController.class);
    @Autowired
    private FormulaDesignerService formulaDesignerService;
    @Autowired
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController2;

    @RequestMapping(value={"form_tree"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u9875\u7b7e")
    public FormTree getGroupAndFroms(@ApiParam(value="\u62a5\u8868\u65b9\u6848Key", required=true) String schemeKey) throws JQException {
        return this.formulaDesignerService.getGroupAndFroms(schemeKey);
    }

    @RequestMapping(value={"runTime_form_tree"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u62a5\u8868\u9875\u7b7e")
    public FormTree getGroupAndFromsByRunTime(@ApiParam(value="\u62a5\u8868\u65b9\u6848Key", required=true) String schemeKey) throws JQException {
        try {
            return this.formulaDesignerService.getGroupAndFromsByRunTime(schemeKey);
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"functions/{style}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u51fd\u6570\u5217\u8868 ")
    public List<FunctionObj> getFunctions(@ApiParam(value="\u516c\u5f0f\u8bed\u6cd5\uff1a1-\u62a5\u8868\u516c\u5f0f\uff0c2-excel\u516c\u5f0f", required=true) @PathVariable int style) throws JQException {
        boolean isExcelStyle = FormulaSyntaxStyle.forValue((int)style) == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL;
        ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController2);
        DefinitionsCache cache = null;
        ArrayList<FunctionObj> functionObjs = new ArrayList<FunctionObj>();
        try {
            cache = new DefinitionsCache(executorContext);
            ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
            Set functions = formulaParser.allFunctions();
            for (IFunction iFunction : functions) {
                if (isExcelStyle && !iFunction.support(Language.EXCEL)) continue;
                try {
                    FunctionObj functionObj = new FunctionObj(iFunction.name().toUpperCase(), iFunction.title(), iFunction.category(), iFunction.toDescription(), iFunction.parameters().size());
                    functionObj.setIsInfiniteParameter(iFunction.isInfiniteParameter());
                    functionObj.setInfiniteParameterCount(1);
                    functionObj.setResultType(iFunction.getResultType(null, null));
                    ArrayList<ParameterObj> parameterObjs = new ArrayList<ParameterObj>();
                    for (IParameter parameter : iFunction.parameters()) {
                        ParameterObj parameterObj = new ParameterObj(parameter.name(), parameter.title(), parameter.dataType(), parameter.isOmitable());
                        parameterObjs.add(parameterObj);
                    }
                    functionObj.setParameterList(parameterObjs);
                    functionObj.setParameterCount(parameterObjs.size());
                    functionObjs.add(functionObj);
                }
                catch (Exception e) {}
            }
        }
        catch (SyntaxException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)e);
        }
        return functionObjs;
    }

    @RequestMapping(value={"design_griddata/{formKey}/{viewType}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8bbe\u8ba1\u671f\u8868\u683c\u4fe1\u606f ")
    public String getDesignFormDataA(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String formKey, @ApiParam(value="\u5c55\u793a\u89c6\u56fe\uff1a1-\u6307\u6807\u4ee3\u7801\uff0c2-\u5355\u5143\u683c\u7f16\u53f7", required=true) @PathVariable int viewType) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormData(formKey, viewType, "null");
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    @RequestMapping(value={"design_griddata/{formKey}/{viewType}/{regionKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8bbe\u8ba1\u671f\u8868\u683c\u4fe1\u606f ")
    public String getDesignFormDataB(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String formKey, @ApiParam(value="\u5c55\u793a\u89c6\u56fe\uff1a1-\u6307\u6807\u4ee3\u7801\uff0c2-\u5355\u5143\u683c\u7f16\u53f7", required=true) @PathVariable int viewType, @PathVariable String regionKey) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormData(formKey, viewType, regionKey);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    @RequestMapping(value={"design_griddata/run_time/{formKey}/{viewType}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u671f\u8868\u683c\u4fe1\u606f ")
    public String getDesignFormDataByRunTime(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String formKey, @ApiParam(value="\u5c55\u793a\u89c6\u56fe\uff1a1-\u6307\u6807\u4ee3\u7801\uff0c2-\u5355\u5143\u683c\u7f16\u53f7", required=true) @PathVariable int viewType) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormDataByRunTime(formKey, viewType, "null");
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    @RequestMapping(value={"design_griddata/run_time/{formKey}/{viewType}/{regionKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u671f\u8868\u683c\u4fe1\u606f ")
    public String getDesignFormDataByRunTime(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String formKey, @ApiParam(value="\u5c55\u793a\u89c6\u56fe\uff1a1-\u6307\u6807\u4ee3\u7801\uff0c2-\u5355\u5143\u683c\u7f16\u53f7", required=true) @PathVariable int viewType, @PathVariable String regionKey) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormDataByRunTime(formKey, viewType, regionKey);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    @RequestMapping(value={"design_griddata/designer"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u8bbe\u8ba1\u671f\u8868\u683c\u4fe1\u606f ")
    public String getDesignFormDataC(@RequestBody EditorParam editorParam) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormData(editorParam);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    @RequestMapping(value={"design_griddata/run_time"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u671f\u8868\u683c\u4fe1\u606f ")
    @RequiresPermissions(value={"nr:task_formula_editor:manage"})
    public String getDesignFormDataByRunTimeC(@RequestBody EditorParam editorParam) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.formulaDesignerService.getFormDataByRunTime(editorParam);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }
}

