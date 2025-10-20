/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.IDesignerEntityUpgrader;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.facade.AnalysisSchemeParamObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5d4c\u5165\u5f0f\u5206\u6790"})
public class AnalysisFormController {
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private DesignTimeViewController viewController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IFormulaDesignTimeController formulaController;
    @Autowired
    private IDesignerEntityUpgrader iDesignerEntityUpgrader;

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u662f\u5426\u542f\u7528\u5206\u6790\u529f\u80fd")
    @RequestMapping(value={"enable/analysis/{schemeKey}"}, method={RequestMethod.GET})
    public boolean enableAnalysisScheme(@PathVariable String schemeKey) throws JQException {
        return this.nrDesignTimeController.enableAnalysisScheme(schemeKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u65b9\u6848\u7ea7\u5206\u6790\u53c2\u6570")
    @RequestMapping(value={"query/analysis/{schemeKey}"}, method={RequestMethod.GET})
    public AnalysisSchemeParamObj queryAnaSchemeParams(@PathVariable String schemeKey) throws JQException {
        DesignAnalysisSchemeParamDefine define = this.nrDesignTimeController.queryAnalysisSchemeParamDefine(schemeKey);
        if (null == define) {
            return null;
        }
        return AnalysisSchemeParamObj.toObj(define);
    }

    @ApiOperation(value="\u4fdd\u5b58\u65b9\u6848\u7ea7\u53c2\u6570")
    @RequestMapping(value={"save/analysis/{schemeKey}"}, method={RequestMethod.POST})
    public void saveAnaSchemeParams(@PathVariable String schemeKey, @RequestBody String params) throws JQException {
        try {
            DesignAnalysisSchemeParamDefine define = AnalysisSchemeParamObj.toDefine(params);
            this.nrDesignTimeController.updataAnalysisSchemeParamDefine(schemeKey, define);
            DesignFormulaSchemeDefine formulaScheme = this.getFetchFormulaScheme(schemeKey);
            if (formulaScheme == null) {
                formulaScheme = this.formulaController.createFormulaSchemeDefine();
                formulaScheme.setTitle("\u53d6\u6570\u516c\u5f0f\u65b9\u6848");
                formulaScheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
                formulaScheme.setFormSchemeKey(schemeKey);
                formulaScheme.setUpdateTime(new Date());
                formulaScheme.setOrder(OrderGenerator.newOrder());
                formulaScheme.setDefault(true);
                this.formulaController.insertFormulaSchemeDefine(formulaScheme);
            }
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_168, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5220\u9664\u65b9\u6848\u7ea7\u53c2\u6570")
    @RequestMapping(value={"delete/analysis/{schemeKey}"}, method={RequestMethod.GET})
    public void deleteAnaSchemeParams(@PathVariable String schemeKey) throws JQException {
        this.nrDesignTimeController.deleteAnalysisSchemeParamDefine(schemeKey);
        List allForms = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(schemeKey);
        for (DesignFormDefine designFormDefine : allForms) {
            if (!designFormDefine.isAnalysisForm()) continue;
            designFormDefine.setAnalysisForm(false);
            this.nrDesignTimeController.updateFormDefine(designFormDefine);
            this.nrDesignTimeController.deleteAnalysisFormParamDefine(designFormDefine.getKey());
        }
        DesignFormulaSchemeDefine formulaScheme = this.getFetchFormulaScheme(schemeKey);
        if (formulaScheme != null) {
            this.nrDesignTimeController.deleteFormulaSchemeDefine(formulaScheme.getKey());
        }
    }

    private DesignFormulaSchemeDefine getFetchFormulaScheme(String schemeKey) {
        List formulaSchemes = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(schemeKey);
        List fetchSchemes = formulaSchemes.stream().filter(item -> item.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM).collect(Collectors.toList());
        DesignFormulaSchemeDefine formulaScheme = fetchSchemes.size() > 0 ? (DesignFormulaSchemeDefine)fetchSchemes.get(0) : null;
        return formulaScheme;
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @RequestMapping(value={"get/runtime/tasks"}, method={RequestMethod.GET})
    public List<CommonVO> getAllTasks(TaskType type) {
        List allTasks = null == type ? this.viewController.getAllTaskDefines() : this.viewController.getAllTaskDefinesByType(type);
        ArrayList<CommonVO> results = new ArrayList<CommonVO>();
        if (null != allTasks && !allTasks.isEmpty()) {
            for (TaskDefine define : allTasks) {
                CommonVO vo = new CommonVO();
                vo.setKey(define.getKey());
                vo.setTitle(define.getTitle());
                vo.setCode(define.getTaskCode());
                results.add(vo);
            }
        }
        return results;
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"get/runtime/formschemes"}, method={RequestMethod.GET})
    public List<CommonVO> getAllFormschemes(String taskId) throws JQException {
        ArrayList<CommonVO> results = new ArrayList<CommonVO>();
        if (StringUtils.isEmpty((CharSequence)taskId)) {
            return results;
        }
        List formSchemes = null;
        try {
            formSchemes = this.viewController.queryFormSchemeByTask(taskId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_147, (Throwable)e);
        }
        if (null != formSchemes && !formSchemes.isEmpty()) {
            for (FormSchemeDefine define : formSchemes) {
                CommonVO vo = new CommonVO();
                vo.setKey(define.getKey());
                vo.setTitle(define.getTitle());
                vo.setCode(define.getFormSchemeCode());
                results.add(vo);
            }
        }
        return results;
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1/\u62a5\u8868\u65b9\u6848\u7684\u4e3b\u4f53")
    @RequestMapping(value={"get/formscheme/entities"}, method={RequestMethod.GET})
    public List<DimensionInfo> getSrcDims(String taskId, String formSchemeId) throws JQException {
        throw new NotSupportedException();
    }

    @ApiOperation(value="\u83b7\u53d6\u4e0d\u5b9a\u671f\u65f6\u671f\u503c")
    @RequestMapping(value={"get/custom/period"}, method={RequestMethod.GET})
    public List<Map<String, String>> getCustomPeriods(String entityId) {
        throw new NotSupportedException();
    }

    public class CommonVO {
        private String key;
        private String title;
        private String code;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}

