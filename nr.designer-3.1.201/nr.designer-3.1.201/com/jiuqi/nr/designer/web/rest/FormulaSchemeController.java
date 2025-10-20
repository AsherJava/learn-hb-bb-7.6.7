/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.MouldDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService
 *  com.jiuqi.nr.definition.internal.service.EFDCPeriodSettingService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl;
import com.jiuqi.nr.definition.internal.impl.MouldDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.EFDCPeriodSettingService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IBussinessModelService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.IFormFormulaService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.facade.AdjustPeriodObj;
import com.jiuqi.nr.designer.web.facade.AdjustPeriodRow;
import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import com.jiuqi.nr.designer.web.facade.FormulaDataVO;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.facade.FormulaSchemeObj;
import com.jiuqi.nr.designer.web.rest.param.FormulaSearchPM;
import com.jiuqi.nr.designer.web.rest.vo.FlowCalcScheme;
import com.jiuqi.nr.designer.web.rest.vo.FormTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.FormulaTreeVO;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormulaSchemeController {
    private static final Logger log = LoggerFactory.getLogger(FormulaSchemeController.class);
    private static final String FORMULA_BIAOJIAN = "number_formulas";
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDesignRestService restService;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IFormFormulaService formulaService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private IBussinessModelService bussinessModelService;
    @Autowired
    private TaskDesignerService taskDesignerService;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private DesignFormulaSchemeDefineService formulaSchemeService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    public IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    public IAdjustPeriodService iAdjustPeriodService;
    @Autowired
    public EFDCPeriodSettingService efdcPeriodSettingService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;

    @PostMapping(value={"stepSaveFormulaScheme"})
    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f\u65b9\u6848")
    public void stepSaveFormulascheme(@RequestBody FormulaSchemeObject formulaSchemeObject) throws JQException {
        try {
            this.stepSaveService.stepSaveFormulaScheme(formulaSchemeObject);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_155, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5f53\u524d\u516c\u5f0f\u65b9\u6848\u662f\u5426\u5df2\u53d1\u5e03")
    @RequestMapping(value={"check_exist_formulaScheme"}, method={RequestMethod.GET})
    public boolean checkExistByRuntime(String formulaSchemeKey) {
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (formulaSchemeDefine != null) {
            switch (formulaSchemeDefine.getFormulaSchemeType()) {
                case FORMULA_SCHEME_TYPE_FINANCIAL: {
                    return true;
                }
            }
        }
        return false;
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/query"}, method={RequestMethod.GET})
    public List<FormulaSchemeObj> query(String formSchemeKey, int formulaSchemeType, String language) {
        List formulaSchemes = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (formulaSchemes != null) {
            return formulaSchemes.stream().filter(scheme -> scheme.getFormulaSchemeType().getValue() == formulaSchemeType).map(FormulaSchemeObj::toFormulaSchemeObj).collect(Collectors.toList());
        }
        return null;
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/add"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public String add(@RequestBody FormulaSchemeObj obj) throws JQException {
        String logTitle = "\u65b0\u589e\u516c\u5f0f\u65b9\u6848";
        String formulaSchemeTitle = "\u672a\u77e5";
        try {
            List formulas;
            formulaSchemeTitle = obj.getTitle();
            DesignFormulaSchemeDefineImpl impl = FormulaSchemeObj.toDesignFormulaSchemeDefine(obj);
            String key = impl.getKey();
            String formulaSchemeKey = UUIDUtils.getKey();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(impl.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            impl.setKey(formulaSchemeKey);
            impl.setOrder(OrderGenerator.newOrder());
            this.nrDesignTimeController.checkFormulaTitle((DesignFormulaSchemeDefine)impl);
            this.nrDesignTimeController.insertFormulaSchemeDefine((DesignFormulaSchemeDefine)impl);
            if (key != null && (formulas = this.nrDesignTimeController.getAllFormulasInScheme(key)) != null && !formulas.isEmpty()) {
                for (DesignFormulaDefine formula : formulas) {
                    formula.setFormulaSchemeKey(formulaSchemeKey);
                    formula.setKey(UUIDUtils.getKey());
                }
                this.nrDesignTimeController.insertFormulaDefines(formulas.toArray(new DesignFormulaDefine[0]));
            }
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formulaSchemeKey;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_039);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848--\u6839\u636e\u7528\u6237\u8bbf\u95ee\u6743\u9650\u8fc7\u6ee4")
    @RequestMapping(value={"formula/scheme/queryByAuth"}, method={RequestMethod.GET})
    public List<FormulaSchemeObj> queryByAuth(String formSchemeKey, int formulaSchemeType, String language) {
        List formulaSchemes = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (formulaSchemes != null) {
            return formulaSchemes.stream().filter(scheme -> scheme.getFormulaSchemeType().getValue() == formulaSchemeType && this.definitionAuthority.canReadFormulaScheme(scheme.getKey())).map(FormulaSchemeObj::toFormulaSchemeObj).collect(Collectors.toList());
        }
        return null;
    }

    @ApiOperation(value="\u516c\u5f0f\u7ba1\u7406\u4e2d--\u65b0\u589e\u516c\u5f0f\u65b9\u6848\uff0c\u5f53\u524d\u6dfb\u52a0\u7528\u6237\u6709\u6743\u9650\u8bbf\u95ee")
    @RequestMapping(value={"formula/scheme/formulaManageAdd"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public String formulaManageAdd(@RequestBody FormulaSchemeObj obj) throws JQException {
        String logTitle = "\u516c\u5f0f\u7ba1\u7406--\u65b0\u589e\u516c\u5f0f\u65b9\u6848";
        String formulaSchemeTitle = "\u672a\u77e5";
        try {
            formulaSchemeTitle = obj.getTitle();
            DesignFormulaSchemeDefineImpl impl = FormulaSchemeObj.toDesignFormulaSchemeDefine(obj);
            impl.setShow(true);
            String formulaSchemeKey = UUIDUtils.getKey();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(impl.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            impl.setKey(formulaSchemeKey);
            impl.setOrder(OrderGenerator.newOrder());
            this.nrDesignTimeController.checkFormulaTitle((DesignFormulaSchemeDefine)impl);
            String result = this.nrDesignTimeController.insertFormulaSchemeDefine((DesignFormulaSchemeDefine)impl);
            if (StringUtils.isNotEmpty((String)result)) {
                String identityId = NpContextHolder.getContext().getIdentityId();
                if (identityId != null) {
                    this.definitionAuthority.grantAllPrivilegesToFormulaScheme(formulaSchemeKey);
                } else {
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_007);
                }
            }
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formulaSchemeKey;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_039);
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/update"}, method={RequestMethod.POST})
    public void update(@RequestBody FormulaSchemeObj obj) throws JQException {
        String logTitle = "\u4fee\u6539\u516c\u5f0f\u65b9\u6848";
        String formulaSchemeTitle = "\u672a\u77e5";
        try {
            formulaSchemeTitle = obj.getTitle();
            DesignFormulaSchemeDefineImpl impl = FormulaSchemeObj.toDesignFormulaSchemeDefine(obj);
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(impl.getFormSchemeKey());
            if (!taskCanEdit) {
                return;
            }
            impl.setUpdateTime(new Date());
            if (!formulaSchemeTitle.equals(this.formulaSchemeService.queryFormulaSchemeDefine(impl.getKey()).getTitle())) {
                this.nrDesignTimeController.checkFormulaTitle((DesignFormulaSchemeDefine)impl);
            }
            this.nrDesignTimeController.updateFormulaSchemeDefine((DesignFormulaSchemeDefine)impl);
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_041);
        }
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/delete"}, method={RequestMethod.GET})
    @Transactional(rollbackFor={Exception.class})
    public void delete(String formulaSchemeKey) throws JQException {
        String logTitle = "\u5220\u9664\u516c\u5f0f\u65b9\u6848";
        String formulaSchemeTitle = "\u672a\u77e5";
        try {
            formulaSchemeTitle = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.formulaSchemeCanEdit(formulaSchemeKey);
            if (!taskCanEdit) {
                return;
            }
            this.nrDesignTimeController.deleteFormulaDefinesByScheme(formulaSchemeKey);
            this.nrDesignTimeController.deleteFormulaSchemeDefine(formulaSchemeKey);
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formulaSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_040);
        }
    }

    @ApiOperation(value="\u901a\u8fc7\u516c\u5f0f\u65b9\u6848key\u67e5\u8be2\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/queryByKey"}, method={RequestMethod.GET})
    public FormulaSchemeObj queryByKey(String formulaSchemeKey, String language) {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (formulaSchemeDefine != null) {
            return FormulaSchemeObj.toFormulaSchemeObj(formulaSchemeDefine);
        }
        return null;
    }

    @ApiOperation(value="\u8bbe\u7f6e\u9ed8\u8ba4\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/setdefault"}, method={RequestMethod.GET})
    @Transactional(rollbackFor={Exception.class})
    public void setDefault(String formSchemeKey, String formulaSchemeKey) throws JQException {
        DesignFormulaSchemeDefine defaultScheme = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        List formulaSchemes = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (formulaSchemes != null && !formulaSchemes.isEmpty()) {
            for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
                if (!formulaScheme.getFormulaSchemeType().equals((Object)defaultScheme.getFormulaSchemeType()) || !formulaScheme.isDefault()) continue;
                formulaScheme.setDefault(false);
                this.nrDesignTimeController.updateFormulaSchemeDefine(formulaScheme);
            }
        }
        if (defaultScheme != null) {
            defaultScheme.setDefault(true);
            defaultScheme.setShow(true);
            this.nrDesignTimeController.updateFormulaSchemeDefine(defaultScheme);
        }
    }

    @ApiOperation(value="\u4ea4\u6362\u4f4d\u7f6e")
    @RequestMapping(value={"formula/scheme/exchange"}, method={RequestMethod.GET})
    public void exchange(String sourceKey, String targetKey) {
        this.nrDesignTimeController.exchangeFormulaSchemeOrder(sourceKey, targetKey);
    }

    @ApiOperation(value="\u8d22\u52a1\u516c\u5f0f\u5bfc\u51fa--\u67e5\u8be2\u62a5\u8868\u6811")
    @RequestMapping(value={"/exportEfdc-formTree/{schemeId}"}, method={RequestMethod.GET})
    public List<ITree<FormTreeNode>> getFormTree(@PathVariable(value="schemeId") String schemeId) {
        return this.restService.getFormTree(schemeId);
    }

    @ApiOperation(value="\u5bfc\u51fa\u5e26\u8868\u6837\u8d22\u52a1\u516c\u5f0f")
    @RequestMapping(value={"/export_efdc_excel"}, method={RequestMethod.POST})
    public void exportEfdcWithFormStyle(@RequestBody String financeFormulaParam, HttpServletResponse res) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(financeFormulaParam);
        String formKeys = jsonNode.get("formKeys").toString();
        String efdcScheme = jsonNode.get("efdcScheme").toString().replace("\"", "");
        List jsonFormKeys = JacksonUtils.toList((String)formKeys, String.class);
        this.formulaService.exportEfdcWithStyle(jsonFormKeys, efdcScheme, res);
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u516c\u5f0f\u65b9\u6848\u7684\u62a5\u8868\u516c\u5f0f")
    @PostMapping(value={"/getFormulaData"})
    public List<FormulaObj> getFormulaData(@RequestBody FormulaSearchPM searchPM) throws JQException {
        try {
            NpContext context = NpContextHolder.getContext();
            context.getDefaultExtension().put("language", (Serializable)((Object)searchPM.getLanguage()));
            return this.bussinessModelService.getFormulaData(searchPM.getFormulascheme(), searchPM.getFormKey());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u516c\u5f0f\u65b9\u6848\u7684\u62a5\u8868\u516c\u5f0f")
    @PostMapping(value={"/searchFormulaData"})
    public FormulaDataVO searchFormulaData(@RequestBody FormulaSearchPM searchPM) throws JQException {
        try {
            NpContext context = NpContextHolder.getContext();
            context.getDefaultExtension().put("language", (Serializable)((Object)searchPM.getLanguage()));
            return this.bussinessModelService.getFormulaData(searchPM);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f")
    @RequestMapping(value={"/save_formulas"}, method={RequestMethod.POST})
    public void saveFormula(@RequestBody FormulaObj[] formulas) throws JQException, ParseException {
        this.taskDesignerService.saveFormulas(formulas);
    }

    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f")
    @RequestMapping(value={"/save_formulas_rsa"}, method={RequestMethod.POST})
    public void saveFormulaRsa(@RequestBody @SFDecrypt List<FormulaObj> formulas) throws JQException, ParseException {
        if (null != formulas && formulas.size() != 0) {
            FormulaObj[] formulaDatas = formulas.toArray(new FormulaObj[0]);
            this.taskDesignerService.saveFormulas(formulaDatas);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u516c\u5f0f\u65b9\u6848\u7684\u62a5\u8868\u516c\u5f0f(\u65b0)")
    @RequestMapping(value={"/getFormulaDataNew"}, method={RequestMethod.POST})
    public List<FormulaObj> getFormulaDataNew(@RequestBody String formulaSchemeJson) throws JQException {
        ObjectMapper mapper = new ObjectMapper();
        String formulascheme = "";
        String formKey = "";
        try {
            JsonNode tableidNode = mapper.readTree(formulaSchemeJson);
            formulascheme = tableidNode.get("formulascheme").textValue();
            formKey = tableidNode.get("formKey").textValue();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000);
        }
        return this.bussinessModelService.getFormulaDataNew(formulascheme, formKey);
    }

    @ApiOperation(value="\u516c\u5f0f\u6811\u8282\u70b9\u5207\u6362")
    @RequestMapping(value={"formula_tree_node_select_new"}, method={RequestMethod.POST})
    public List<FormulaTreeVO> formulaTreeNodeSelect(@RequestBody FormulaObj formula) throws JQException {
        NpContext context = NpContextHolder.getContext();
        context.getDefaultExtension().put("language", (Serializable)((Object)formula.getLanguage()));
        if (StringUtils.isEmpty((String)formula.getFormKey())) {
            return Collections.emptyList();
        }
        List allFormulasInForm = formula.getFormKey().equals(FORMULA_BIAOJIAN) ? this.formulaDesignTimeController.getAllFormulasInForm(formula.getSchemeKey(), null) : this.formulaDesignTimeController.getAllFormulasInForm(formula.getSchemeKey(), formula.getFormKey());
        List formulaConditionLinks = this.formulaDesignTimeController.listConditionLinkByScheme(formula.getSchemeKey());
        List keys = formulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).distinct().collect(Collectors.toList());
        Map<String, DesignFormulaCondition> conditionMap = this.formulaDesignTimeController.listFormulaConditionByKey(keys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        Map<String, List<DesignFormulaConditionLink>> formulaMap = formulaConditionLinks.stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey));
        if (allFormulasInForm.isEmpty()) {
            return Collections.emptyList();
        }
        return allFormulasInForm.stream().map(define -> {
            FormulaTreeVO treeVO = this.changeFormulaSchemeKey(formula.getSchemeKey(), (DesignFormulaDefine)define);
            treeVO.setFormulaConditions(this.addFormulaConditionLink(formulaMap, conditionMap, (DesignFormulaDefine)define));
            return treeVO;
        }).collect(Collectors.toList());
    }

    private List<FormulaConditionLinkObj> addFormulaConditionLink(Map<String, List<DesignFormulaConditionLink>> formulaMap, Map<String, DesignFormulaCondition> conditionMap, DesignFormulaDefine define) {
        List<DesignFormulaConditionLink> links = formulaMap.get(define.getKey());
        if (links == null) {
            return null;
        }
        ArrayList<FormulaConditionLinkObj> linkObjs = new ArrayList<FormulaConditionLinkObj>();
        for (DesignFormulaConditionLink link : links) {
            DesignFormulaCondition condition = conditionMap.get(link.getConditionKey());
            if (condition == null) continue;
            FormulaConditionLinkObj linkObj = new FormulaConditionLinkObj();
            linkObj.setFormulaKey(define.getKey());
            linkObj.setSchemeKey(define.getFormulaSchemeKey());
            linkObj.setKey(condition.getKey());
            linkObj.setCode(condition.getCode());
            linkObj.setTitle(condition.getTitle());
            linkObjs.add(linkObj);
        }
        return linkObjs;
    }

    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u6570\u636eGrid\u8868\u683c\u5bf9\u8c61\uff08\u4e34\u65f6\uff09")
    @RequestMapping(value={"formula/grid/{schemeKey}/{formKey}"}, method={RequestMethod.GET})
    public String getFormulaGrid(@PathVariable String schemeKey, @PathVariable String formKey) throws JQException {
        List<FormulaObj> formulaDatas = this.bussinessModelService.getFormulaDataNew(schemeKey, formKey);
        Grid2Data grid2Data = new Grid2Data();
        GridCellData gridCellData = null;
        if (null != formulaDatas && !formulaDatas.isEmpty()) {
            grid2Data.setColumnCount(3);
            grid2Data.setRowCount(formulaDatas.size() + 1);
            grid2Data.setColumnWidth(0, 36);
            grid2Data.setColumnWidth(1, 220);
            grid2Data.setColumnAutoWidth(1, true);
            grid2Data.setColumnWidth(2, 720);
            grid2Data.setColumnAutoWidth(2, true);
            gridCellData = grid2Data.getGridCellData(0, 0);
            gridCellData.setShowText("\u5e8f\u53f7");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            gridCellData = grid2Data.getGridCellData(1, 0);
            gridCellData.setShowText("\u7f16\u53f7");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            gridCellData = grid2Data.getGridCellData(2, 0);
            gridCellData.setShowText("\u8868\u8fbe\u5f0f");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            for (int i = 0; i < formulaDatas.size(); ++i) {
                gridCellData = grid2Data.getGridCellData(0, i + 1);
                gridCellData.setEditable(false);
                gridCellData.setBackGroundColor(0xF7F7F7);
                gridCellData = grid2Data.getGridCellData(1, i + 1);
                gridCellData.setShowText(formulaDatas.get(i).getCode());
                gridCellData.setEditable(false);
                gridCellData = grid2Data.getGridCellData(2, i + 1);
                gridCellData.setEditable(false);
                gridCellData.setShowText(formulaDatas.get(i).getExpression());
            }
        } else {
            int defaultRowCount = 6;
            grid2Data.setColumnCount(3);
            grid2Data.setRowCount(defaultRowCount);
            grid2Data.setColumnWidth(0, 36);
            grid2Data.setColumnWidth(1, 220);
            grid2Data.setColumnAutoWidth(1, true);
            grid2Data.setColumnWidth(2, 720);
            grid2Data.setColumnAutoWidth(2, true);
            gridCellData = grid2Data.getGridCellData(0, 0);
            gridCellData.setShowText("\u5e8f\u53f7");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            gridCellData = grid2Data.getGridCellData(1, 0);
            gridCellData.setShowText("\u7f16\u53f7");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            gridCellData = grid2Data.getGridCellData(2, 0);
            gridCellData.setShowText("\u8868\u8fbe\u5f0f");
            gridCellData.setEditable(false);
            gridCellData.setBackGroundColor(0xF7F7F7);
            for (int i = 0; i < defaultRowCount - 1; ++i) {
                gridCellData = grid2Data.getGridCellData(0, i + 1);
                gridCellData.setEditable(false);
                gridCellData.setBackGroundColor(0xF7F7F7);
                grid2Data.getGridCellData(1, i + 1).setEditable(false);
                grid2Data.getGridCellData(2, i + 1).setEditable(false);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        objectMapper.registerModule((Module)module);
        try {
            return objectMapper.writeValueAsString((Object)grid2Data);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    private FormulaTreeVO changeFormulaSchemeKey(String formulaSchemeKey, DesignFormulaDefine designFormulaDefine) {
        designFormulaDefine.setFormulaSchemeKey(formulaSchemeKey);
        FormulaTreeVO formulaTreeVO = new FormulaTreeVO();
        formulaTreeVO.setFormulaSchemeKey(designFormulaDefine.getFormulaSchemeKey());
        formulaTreeVO.setFormKey(designFormulaDefine.getFormKey());
        formulaTreeVO.setCode(designFormulaDefine.getCode());
        formulaTreeVO.setExpression(designFormulaDefine.getExpression());
        formulaTreeVO.setDataItems(designFormulaDefine.getDataItems());
        formulaTreeVO.setDescription(designFormulaDefine.getDescription());
        formulaTreeVO.setAutoExecute(designFormulaDefine.getIsAutoExecute());
        formulaTreeVO.setUseCalculate(designFormulaDefine.getUseCalculate());
        formulaTreeVO.setUseCheck(designFormulaDefine.getUseCheck());
        formulaTreeVO.setUseBalance(designFormulaDefine.getUseBalance());
        formulaTreeVO.setCheckType(designFormulaDefine.getCheckType());
        formulaTreeVO.setKey(designFormulaDefine.getKey());
        formulaTreeVO.setTitle(designFormulaDefine.getTitle());
        formulaTreeVO.setOrder(designFormulaDefine.getOrder());
        formulaTreeVO.setVersion(designFormulaDefine.getVersion());
        formulaTreeVO.setOwnerLevelAndId(designFormulaDefine.getOwnerLevelAndId());
        formulaTreeVO.setUpdateTime(designFormulaDefine.getUpdateTime());
        formulaTreeVO.setLargeExpression(designFormulaDefine.getExpression());
        formulaTreeVO.setBalanceZBExp(designFormulaDefine.getBalanceZBExp());
        formulaTreeVO.setPrivate(designFormulaDefine.getIsPrivate());
        formulaTreeVO.setUnit(designFormulaDefine.getUnit());
        return formulaTreeVO;
    }

    @ApiOperation(value="\u67e5\u8be2\u8d26\u671f\u8bbe\u7f6e")
    @GetMapping(value={"accountperiod/{formulaSchemeKey}"})
    public AdjustPeriodObj getAccountPeriodSetting(@PathVariable(value="formulaSchemeKey") String formulaSchemeKey) throws Exception {
        EFDCPeriodSettingDefineImpl efdcPeriodSettingDefine;
        DesignTaskDefine taskDefine;
        DesignFormSchemeDefine formScheme;
        DesignFormulaSchemeDefine formulaSchemeDefine;
        AdjustPeriodObj adjustPeriodObj = new AdjustPeriodObj();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey) && null != (formulaSchemeDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey)) && FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL.equals((Object)formulaSchemeDefine.getFormulaSchemeType()) && null != (formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formulaSchemeDefine.getFormSchemeKey())) && null != (taskDefine = this.nrDesignTimeController.queryTaskDefine(formScheme.getTaskKey())) && null != (efdcPeriodSettingDefine = this.efdcPeriodSettingService.queryByFormulaSchemeKey(formulaSchemeKey))) {
            List data;
            MouldDataDefineImpl mouldDataDefine;
            List adjustPeriods = this.iAdjustPeriodService.queryAdjustPeriods(taskDefine.getDataScheme());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
            if (null != adjustPeriods && adjustPeriods.size() != 0) {
                List<AdjustPeriodRow> collect = adjustPeriods.stream().map(t -> {
                    AdjustPeriodRow row = new AdjustPeriodRow(t.getDataSchemeKey(), t.getPeriod(), t.getCode(), t.getTitle());
                    row.setPeriodTitle(periodProvider.getPeriodTitle(row.getPeriod()));
                    row.setnReCode(row.getPeriod().concat(",").concat(row.getCode()));
                    if (StringUtils.isNotEmpty((String)row.getPeriodTitle())) {
                        row.setPeriodTitle(row.getPeriodTitle().concat(",").concat(row.getTitle()));
                    }
                    return row;
                }).collect(Collectors.toList());
                adjustPeriodObj.setAdjustPeriods(collect);
            } else {
                adjustPeriodObj.setAdjustPeriods(new ArrayList<AdjustPeriodRow>());
            }
            if (null != efdcPeriodSettingDefine && null != (mouldDataDefine = efdcPeriodSettingDefine.getMouldDataDefine()) && null != (data = mouldDataDefine.getData()) && data.size() != 0) {
                for (MouldDefineImpl datum : data) {
                    if (!StringUtils.isNotEmpty((String)datum.getCode()) && !StringUtils.isNotEmpty((String)datum.getTitle())) continue;
                    adjustPeriodObj.setAdjustPeriod(false);
                    break;
                }
            }
            adjustPeriodObj.setData(efdcPeriodSettingDefine);
        }
        return adjustPeriodObj;
    }

    @ApiOperation(value="\u67e5\u8be2\u65b9\u6848\u4e0b\u5168\u90e8\u66ff\u6362\u540e\u7684\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formulaByTask"}, method={RequestMethod.POST})
    public FlowCalcScheme queryFormulaByTask(@RequestBody FlowCalcScheme flowCalcScheme) throws JQException {
        List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(flowCalcScheme.getFormScheme());
        ArrayList<DesignFormulaSchemeDefine> initList = new ArrayList<DesignFormulaSchemeDefine>();
        for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            if (!this.definitionAuthority.canReadFormulaScheme(formulaSchemeDefine.getKey()) || formulaSchemeDefine.getFormulaSchemeType() != FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) continue;
            initList.add(formulaSchemeDefine);
        }
        flowCalcScheme.setList(this.toList(initList));
        return flowCalcScheme;
    }

    private List<FormulaSchemeObj> toList(List<DesignFormulaSchemeDefine> initList) {
        ArrayList<FormulaSchemeObj> list = new ArrayList<FormulaSchemeObj>();
        for (DesignFormulaSchemeDefine designFormulaSchemeDefine : initList) {
            FormulaSchemeObj formulaSchemeObj = new FormulaSchemeObj();
            formulaSchemeObj.setKey(designFormulaSchemeDefine.getKey());
            formulaSchemeObj.setTitle(designFormulaSchemeDefine.getTitle());
            list.add(formulaSchemeObj);
        }
        return list;
    }
}

