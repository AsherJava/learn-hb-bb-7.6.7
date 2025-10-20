/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.calculate.api.FunctionEditorClient
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.grid2.Grid2Data
 *  com.jiuqi.np.grid2.GridCellData
 *  com.jiuqi.np.grid2.json.Grid2DataSerialize
 *  com.jiuqi.np.grid2.json.GridCellDataSerialize
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.designer.common.NrDesingerErrorEnum
 *  com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData
 *  com.jiuqi.nr.designer.web.service.FormulaDesignerService
 *  io.swagger.annotations.ApiParam
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.calculate.formula.functionEditor.web;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.calculate.api.FunctionEditorClient;
import com.jiuqi.gcreport.calculate.formula.functionEditor.service.FunctionEditorService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.json.Grid2DataSerialize;
import com.jiuqi.np.grid2.json.GridCellDataSerialize;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import com.jiuqi.nr.designer.web.service.FormulaDesignerService;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FunctionEditorController
implements FunctionEditorClient {
    @Autowired
    private IDesignTimeViewController controller;
    @Autowired
    private IDataDefinitionDesignTimeController npController;
    @Autowired
    private IDataDefinitionRuntimeController npRuntimeController;
    @Autowired
    private FunctionEditorService functionEditorService;
    @Autowired
    private FormulaDesignerService formulaDesignerService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TreeNode>> initFunctionEntityTree() {
        List<TreeNode> tableDefineTree5 = this.functionEditorService.getTabDefineByTableKind(TableKind.TABLE_KIND_UNDEFINED);
        FunctionProvider globalProvider = FunctionProvider.GLOBAL_PROVIDER;
        ArrayList<IFunction> funList = new ArrayList<IFunction>();
        Iterator iterator = globalProvider.iterator();
        if (iterator.hasNext()) {
            IFunction next = (IFunction)iterator.next();
            funList.add(next);
        }
        return BusinessResponseEntity.ok(tableDefineTree5);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TreeNode>> initFunctionFormTree(@PathVariable(value="tableKey") String tableKey) {
        List<TreeNode> tableDefineTree = this.functionEditorService.getFieldsInTableForFunctionTree(tableKey);
        return BusinessResponseEntity.ok(tableDefineTree);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> addFunction(@RequestBody FunctionEditorVO functionEditorVO) {
        this.functionEditorService.addFunction(functionEditorVO);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getFunctions(@PathVariable(value="pageSize") Integer pageSize, @PathVariable(value="pageNum") Integer pageNum) {
        List<GcFunctionEditorEO> functions = this.functionEditorService.getFunctionsByUserId(pageSize, pageNum);
        int count = this.functionEditorService.queryAllCountByUserId();
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", count);
        retMap.put("elements", functions);
        return BusinessResponseEntity.ok(retMap);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> deleteFunction(@PathVariable(value="functionId") String functionId) {
        this.functionEditorService.deleteFunction(functionId);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getInheritFunctionsTree(Boolean notIncludeGc) {
        List<FunctionTreeVO> inheritFunctionsTree = this.functionEditorService.getInheritFunctionsTree(notIncludeGc);
        return BusinessResponseEntity.ok(inheritFunctionsTree);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getBindTask() {
        List<TaskSchemeVO> taskAndSchemeVOS = this.functionEditorService.getBondTaskAndSchemeVOS();
        return BusinessResponseEntity.ok(taskAndSchemeVOS);
    }

    public BusinessResponseEntity<Object> getBindTaskByTaskKeyList(@RequestBody String[] taskKeyList) {
        List<TaskSchemeVO> taskAndSchemeVOS = this.functionEditorService.getBondTaskAndSchemeVOSByTaskKeyList(taskKeyList);
        return BusinessResponseEntity.ok(taskAndSchemeVOS);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getTaskSchemeByScheme(String schemeId) {
        List<TaskSchemeVO> taskAndSchemeVOS = this.functionEditorService.getTaskSchemeByFormScheme(schemeId);
        return BusinessResponseEntity.ok(taskAndSchemeVOS);
    }

    public BusinessResponseEntity<Object> checkFormula(String jsonParam) {
        Map map = (Map)JsonUtils.readValue((String)jsonParam, Map.class);
        String formula = (String)map.get("formula");
        ArrayList sourceTables = (ArrayList)map.get("sourceTables");
        boolean notIncludeGc = false;
        String notIncludeGcStr = "notIncludeGc";
        if (map.containsKey(notIncludeGcStr) && ((Boolean)map.get(notIncludeGcStr)).booleanValue()) {
            notIncludeGc = true;
        }
        ReturnObject returnObject = this.functionEditorService.checkFunction(formula, sourceTables, notIncludeGc);
        return BusinessResponseEntity.ok((Object)returnObject);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> getDesignFormData(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String formKey) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormulaDesignFormData formData = this.functionEditorService.getFormData(formKey);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            String fromDataStr = objectMapper.writeValueAsString((Object)formData);
            return BusinessResponseEntity.ok((Object)fromDataStr);
        }
        catch (Exception ex) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)ex);
        }
    }

    public BusinessResponseEntity<Object> getBindTaskByDataScheme(String dataSchemeId) {
        return BusinessResponseEntity.ok(this.functionEditorService.getBondTaskAndSchemeByDataScheme(dataSchemeId));
    }
}

