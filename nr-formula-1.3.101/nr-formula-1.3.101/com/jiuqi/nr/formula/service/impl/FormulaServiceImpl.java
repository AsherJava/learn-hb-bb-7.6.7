/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent
 *  com.jiuqi.nr.definition.deploy.common.ParamDeployException
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.nr.task.api.resource.state.ResourceState
 *  com.jiuqi.util.StringUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.formula.dto.FormulaAuditTypeDTO;
import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.dto.FormulaCycleDataDTO;
import com.jiuqi.nr.formula.dto.FormulaCycleTreeDTO;
import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.nr.formula.dto.FormulaIoDTO;
import com.jiuqi.nr.formula.dto.FormulaPathNode;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.dto.FormulaSearchItem;
import com.jiuqi.nr.formula.dto.FormulaSearchParam;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.exception.FormulaException;
import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.exception.NrTaskFormulaException;
import com.jiuqi.nr.formula.internal.FormulaCycleCheckService;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.formula.service.IFormulaService;
import com.jiuqi.nr.formula.service.impl.AbstractFormulaServiceImpl;
import com.jiuqi.nr.formula.support.FormulaMonitor;
import com.jiuqi.nr.formula.utils.convert.FormulaConvert;
import com.jiuqi.nr.formula.web.param.ExpressionCheckParam;
import com.jiuqi.nr.formula.web.param.FormulaExtPM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nr.task.api.resource.state.ResourceState;
import com.jiuqi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FormulaServiceImpl
extends AbstractFormulaServiceImpl
implements IFormulaService {
    private static final Logger logger = LoggerFactory.getLogger(FormulaServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IParamDeployController paramDeployController;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IFormulaSchemeService formulaSchemeService;
    @Autowired
    private IFileAreaService fileAreaService;
    @Autowired
    private FormulaCycleCheckService cycleCheckService;

    @Override
    public FormulaDTO getFormula(String key) {
        DesignFormulaDefine formula = this.formulaDesignTimeController.getFormula(key);
        return FormulaConvert.defineToDTO(formula);
    }

    @Override
    public List<FormulaDTO> listFormulaByScheme(String formulaSchemeKey) {
        List designFormulaDefines = this.formulaDesignTimeController.listFormulaByScheme(formulaSchemeKey);
        return FormulaConvert.defineToDTOList(designFormulaDefines);
    }

    @Override
    public String getFormCodeByForm(String formKey) {
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        if (form != null) {
            return form.getFormCode();
        }
        return null;
    }

    @Override
    public FormulaCheckResult formulaCheck(FormulaExtPM pm) {
        return super.formulaCheck(pm.getFormulaSchemeKey(), pm.getFormKey(), pm.isEfdcCheck(), pm.getItemList(), pm.getDeleted());
    }

    @Override
    public <T extends FormulaExtDTO> FormulaCheckResult formulaCheckByFormScheme(String formScheme, boolean efdc, List<T> list, List<String> deleted) {
        return super.formulaCheckByFormScheme(formScheme, efdc, list, deleted);
    }

    @Override
    public void expressionCheck(ExpressionCheckParam checkParam) {
        List expressions;
        ExecutorContext executorContext = super.getExecutorContext(checkParam.getFormScheme());
        Formula formula = new Formula();
        formula.setFormula(checkParam.getExpression());
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        formulas.add(formula);
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        try {
            expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.EXPRESSION, formulaMonitor);
        }
        catch (Exception e) {
            throw new FormulaException("\u8868\u8fbe\u5f0f\u6821\u9a8c\u5931\u8d25:", e);
        }
        if (CollectionUtils.isEmpty(expressions)) {
            throw new FormulaException(String.format("\u516c\u5f0f[%s]\u89e3\u6790\u5931\u8d25", checkParam.getExpression()));
        }
    }

    private String getFormSchemeKey(String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null) {
            return formulaScheme.getFormSchemeKey();
        }
        return null;
    }

    private DesignReportFmlExecEnvironment getReportFmlExecEnvironment(String formSchemeKey, List<FormulaVariDefine> formulaVariables) {
        return new DesignReportFmlExecEnvironment(this.designTimeViewController1, this.dataDefinitionDesignTimeController, formSchemeKey, formulaVariables);
    }

    @Override
    public <T extends FormulaExtDTO> List<T> generateFormulaDescription(String formulaSchemeKey, List<T> list) {
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        ArrayList<T> formulaList = new ArrayList<T>(list);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        List meannings = new ArrayList();
        try {
            executorContext.setDesignTimeData(true, this.dataDefinitionDesignTimeController);
            String formSchemeKey = this.getFormSchemeKey(formulaSchemeKey);
            if (org.springframework.util.StringUtils.hasLength(formSchemeKey)) {
                List formulaVariables = this.formulaDesignTimeController.listFormulaVariByFormScheme(formSchemeKey);
                DesignReportFmlExecEnvironment environment = this.getReportFmlExecEnvironment(formSchemeKey, formulaVariables);
                executorContext.setEnv((IFmlExecEnvironment)environment);
                meannings = DataEngineFormulaParser.getFormulaMeannings((ExecutorContext)executorContext, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.EXPRESSION, formulaMonitor);
            }
        }
        catch (ParseException e) {
            throw new FormulaResourceException(e);
        }
        if (formulaList.size() != meannings.size()) {
            throw new NrTaskFormulaException("\u516c\u5f0f\u89e3\u6790\u6709\u8bef\uff01");
        }
        for (int i = 0; i < list.size(); ++i) {
            ((FormulaExtDTO)list.get(i)).setMeanning((String)meannings.get(i));
        }
        return list;
    }

    @Override
    public FormulaSearchParam searchFormula(FormulaSearchParam param) {
        if (org.springframework.util.StringUtils.hasLength(param.getKeywords())) {
            List<FormulaDTO> formulaDTOList = this.listFormulaByScheme(param.getFormulaSchemeKey());
            HashMap<String, FormulaPathNode> formMap = new HashMap<String, FormulaPathNode>();
            HashMap<String, FormulaPathNode> formGroupMap = new HashMap<String, FormulaPathNode>();
            ArrayList<FormulaSearchItem> searchItems = new ArrayList<FormulaSearchItem>();
            param.setData(searchItems);
            List formGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(param.getFormSchemeKey());
            for (DesignFormGroupDefine formGroupDefine : formGroupDefines) {
                List formDefines = this.designTimeViewController.listFormByGroup(formGroupDefine.getKey());
                for (DesignFormDefine formDefine : formDefines) {
                    formMap.put(formDefine.getKey(), new FormulaPathNode(formDefine.getKey(), formGroupDefine.getKey(), formDefine.getTitle()));
                    formGroupMap.put(formDefine.getKey(), new FormulaPathNode(formGroupDefine.getKey(), null, formGroupDefine.getTitle()));
                }
            }
            String keywords = this.toLowerCase(param.getKeywords());
            formulaDTOList.stream().filter(f -> this.toLowerCase(f.getExpression()).contains(keywords) || this.toLowerCase(f.getCode()).contains(keywords)).forEach(f -> this.addFormulaSearchItemList((FormulaDTO)f, (Map<String, FormulaPathNode>)formMap, (Map<String, FormulaPathNode>)formGroupMap, (List<FormulaSearchItem>)searchItems));
        }
        return param;
    }

    private void addFormulaSearchItemList(FormulaDTO formulaDTO, Map<String, FormulaPathNode> formMap, Map<String, FormulaPathNode> formGroupMap, List<FormulaSearchItem> searchItems) {
        ArrayList<FormulaPathNode> pathNodes = new ArrayList<FormulaPathNode>();
        String formKey = formulaDTO.getFormKey();
        if ("BJ".equals(formKey)) {
            pathNodes.add(FormulaPathNode.createBJNode());
        } else {
            pathNodes.add(formMap.get(formKey));
            pathNodes.add(formGroupMap.get(formKey));
        }
        FormulaSearchItem searchItem = new FormulaSearchItem();
        searchItem.setCode(formulaDTO.getCode());
        searchItem.setFormKey(formKey);
        searchItem.setKey(formulaDTO.getKey());
        searchItem.setTitle(formulaDTO.getExpression());
        searchItem.setGroupPaths(pathNodes);
        searchItems.add(searchItem);
    }

    private String toLowerCase(String str) {
        return str != null ? str.toLowerCase().replace(" ", "") : "";
    }

    @Override
    public void exportFormula(FormulaIoDTO formulaIODTO) {
    }

    @Override
    public void importFormula(InputStream io, FormulaIoDTO formulaIODTO) {
    }

    @Override
    public void deleteFormula(String key) {
        this.formulaDesignTimeController.deleteFormula(new String[]{key});
        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula(new String[]{key});
    }

    @Override
    public void insertFormula(FormulaDTO formula) {
        DesignFormulaDefine formulaDefine = FormulaConvert.dtoToDefine(formula, this.formulaDesignTimeController);
        this.formulaDesignTimeController.insertFormula(new DesignFormulaDefine[]{formulaDefine});
        this.insertConditionLink(Collections.singletonList(formula));
    }

    @Override
    public void insertFormulas(List<FormulaDTO> formulas) {
        DesignFormulaDefine[] list;
        for (DesignFormulaDefine define : list = FormulaConvert.dtoToDefineList(formulas, this.formulaDesignTimeController)) {
            if (define.getOrder() == null) {
                define.setOrder(OrderGenerator.newOrder());
            }
            if (define.getOrdinal() != null) continue;
            define.setOrdinal(BigDecimal.valueOf(OrderGenerator.newOrderID()));
        }
        this.formulaDesignTimeController.insertFormula(list);
        List<FormulaDTO> conditionFormula = formulas.stream().filter(e -> !CollectionUtils.isEmpty(e.getConditions())).collect(Collectors.toList());
        this.insertConditionLink(conditionFormula);
    }

    private void insertConditionLink(List<FormulaDTO> conditionFormula) {
        ArrayList links = new ArrayList();
        conditionFormula.forEach(e -> {
            List<FormulaConditionDTO> conditions = e.getConditions();
            conditions.forEach(c -> {
                DesignFormulaConditionLink link = this.formulaDesignTimeController.initFormulaConditionLink();
                link.setFormulaKey(e.getKey());
                link.setConditionKey(c.getKey());
                link.setFormulaSchemeKey(e.getFormulaSchemeKey());
                links.add(link);
            });
        });
        this.formulaDesignTimeController.insertFormulaConditionLinks(links);
    }

    @Override
    public void updateFormula(FormulaDTO formula) {
        DesignFormulaDefine formulaDefine = FormulaConvert.dtoToDefine(formula, ResourceState.DIRTY, this.formulaDesignTimeController);
        this.formulaDesignTimeController.updateFormula(new DesignFormulaDefine[]{formulaDefine});
        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula(new String[]{formula.getKey()});
        this.insertConditionLink(Collections.singletonList(formula));
    }

    @Override
    public void updateFormulaDefine(List<DesignFormulaDefine> formulaDefines) {
        this.formulaDesignTimeController.updateFormula(formulaDefines.toArray(new DesignFormulaDefine[0]));
    }

    @Override
    public void updateFormulas(List<FormulaDTO> formulas) {
        DesignFormulaDefine[] defines = FormulaConvert.dtoToDefineList(formulas, ResourceState.DIRTY, this.formulaDesignTimeController);
        this.formulaDesignTimeController.updateFormula(defines);
        String[] formulaKeys = (String[])formulas.stream().map(FormulaDTO::getKey).toArray(String[]::new);
        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula(formulaKeys);
        this.insertConditionLink(formulas);
    }

    @Override
    public void publish(String formKey, String formulaSchemeKey) {
        if (!this.publishCheckService.canFormulaSchemeEdit(formulaSchemeKey)) {
            throw new NrTaskFormulaException("\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
        }
        FormulaSchemeDTO formulaSchemeDTO = this.formulaSchemeService.getFormulaScheme(formulaSchemeKey);
        String formSchemeKey = formulaSchemeDTO.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new NrTaskFormulaException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u672a\u53d1\u5e03");
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (formulaSchemeDefine == null) {
            throw new NrTaskFormulaException("\u5f53\u524d\u516c\u5f0f\u65b9\u6848\u672a\u53d1\u5e03");
        }
        try {
            if ("BJ".equals(formKey)) {
                this.paramDeployController.deploy(ParamResourceType.FORMULA, formulaSchemeKey, Collections.singletonList(null));
            } else {
                FormDefine formDefine = this.runTimeViewController.getForm(formKey, formSchemeKey);
                if (formDefine == null) {
                    throw new NrTaskFormulaException("\u5f53\u524d\u62a5\u8868\u672a\u53d1\u5e03");
                }
                this.paramDeployController.deploy(ParamResourceType.FORMULA, formulaSchemeKey, Collections.singletonList(formKey));
            }
        }
        catch (ParamDeployException e) {
            throw e;
        }
        catch (Exception e) {
            throw new NrTaskFormulaException(e.getMessage(), e);
        }
        this.applicationContext.publishEvent((ApplicationEvent)new DeployRefreshFormulaEvent(Collections.singletonList(formulaSchemeKey)));
    }

    @Override
    protected List<DesignFormulaDefine> queryFormulaByScheme(String formulaScheme) {
        return this.formulaDesignTimeController.listFormulaByScheme(formulaScheme);
    }

    @Override
    protected List<DesignFormulaDefine> queryFormulaBySchemeAndForm(String formulaScheme, String formKey, String unit) {
        return this.formulaDesignTimeController.listFormulaBySchemeAndForm(formulaScheme, formKey);
    }

    @Override
    public FileInfoDTO uploadFormulaExcel(MultipartFile file) {
        try {
            this.checkFile(file);
            this.createWorkBook(file.getInputStream());
            return this.fileAreaService.fileUpload(file.getOriginalFilename(), file.getInputStream(), new FileAreaDTO(true));
        }
        catch (Exception e) {
            throw new RuntimeException("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25");
        }
    }

    @Override
    public void deleteUploadFile(String fileKey) {
        this.fileAreaService.deleteFile(fileKey, new FileAreaDTO(true));
    }

    private void checkFile(MultipartFile file) {
        if (null == file) {
            throw new FormulaResourceException("\u5bfc\u5165\u516c\u5f0f\uff1a\u5bfc\u5165\u6587\u4ef6\u4e3a\u7a7a");
        }
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toUpperCase();
        if (!fileName.endsWith("XLS") && !fileName.endsWith("XLSX")) {
            throw new FormulaResourceException("\u5bfc\u5165\u516c\u5f0f\uff1a\u6587\u4ef6\u4e0d\u662fEXCEL\u683c\u5f0f[" + fileName + "]");
        }
    }

    @Override
    public String getFormulaCode(Set<String> codes, String formKey) {
        String formCode;
        if ("BJ".equals(formKey)) {
            formCode = formKey;
        } else {
            DesignFormDefine form = this.designTimeViewController.getForm(formKey);
            formCode = form.getFormCode();
        }
        String prefix = formCode;
        for (String code : codes) {
            if (!code.startsWith(prefix) || formCode.compareTo(code) >= 0) continue;
            formCode = code;
        }
        if (formCode.length() > prefix.length()) {
            String num = formCode.split(prefix)[1];
            return String.format("%s%03d", prefix, Integer.parseInt(num) + 1);
        }
        return prefix + "001";
    }

    @Override
    public <T extends FormulaExtDTO> Collection<T> conditionStyleCheck(String formScheme, List<T> list) {
        Assert.notNull((Object)formScheme, "formScheme must not be null");
        HashMap checkResult = new HashMap(list.size());
        HashMap functionTypeListMap = new HashMap();
        this.formulaTypeClassification(list, functionTypeListMap, checkResult, false);
        FormulaMonitor formulaMonitor = new FormulaMonitor(checkResult);
        this.parseFormulas(formScheme, formulaMonitor, functionTypeListMap);
        return checkResult.values();
    }

    @Override
    public List<FormulaAuditTypeDTO> listAllAuditType() {
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (CollectionUtils.isEmpty(auditTypes)) {
                return Collections.emptyList();
            }
            return auditTypes.stream().map(FormulaAuditTypeDTO::toDto).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new FormulaResourceException(e);
        }
    }

    @Override
    public List<FormulaCycleTreeDTO> checkFormulaCycle(String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        List formDefines = this.designTimeViewController.listFormByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formCodeToTitle = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getTitle));
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        this.cycleCheckService.checkCycle(formulaScheme.getFormSchemeKey(), this.cycleCheckService.getCalcFormulaByScheme(formulaScheme), formulaMonitor);
        Map<String, List<Formula>> cycles = formulaMonitor.getCycles();
        ArrayList<FormulaCycleTreeDTO> treeObjs = new ArrayList<FormulaCycleTreeDTO>();
        int index = 0;
        List formCodes = cycles.keySet().stream().sorted().collect(Collectors.toList());
        for (String order : formCodes) {
            FormulaCycleTreeDTO treeObj = new FormulaCycleTreeDTO();
            treeObj.setId(order);
            treeObj.setCode(order);
            treeObj.setIsLeaf(true);
            treeObj.setTitle("\u5faa\u73af" + ++index);
            FormulaCycleDataDTO data = new FormulaCycleDataDTO();
            data.setKey(order);
            data.setCode(order);
            data.setTitle("\u5faa\u73af" + index);
            List<Formula> formulas = this.distinctCycle(cycles.get(order));
            for (Formula formula : formulas) {
                if (StringUtils.isNotEmpty((String)formula.getReportName())) {
                    String formTitle = formCodeToTitle.get(formula.getReportName());
                    String showTitle = formTitle.concat("(").concat(formula.getReportName()).concat(")");
                    formula.setMeanning(showTitle);
                    continue;
                }
                formula.setMeanning("\u8868\u95f4\u516c\u5f0f");
            }
            data.setFormulas(formulas);
            treeObj.setData(data);
            treeObjs.add(treeObj);
        }
        return treeObjs;
    }

    public List<Formula> distinctCycle(List<Formula> formulas) {
        ArrayList<Formula> disFormulas = new ArrayList<Formula>();
        HashSet<String> codeSet = new HashSet<String>();
        for (Formula formula : formulas) {
            if (!codeSet.add(formula.getCode())) continue;
            disFormulas.add(formula);
        }
        return disFormulas;
    }

    @Override
    public void exportCycle(HttpServletResponse response, String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        List formDefines = this.designTimeViewController.listFormByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formCodeToTitle = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getTitle));
        this.configResponse(response, formulaScheme.getTitle() + "\u5faa\u73af\u516c\u5f0f.xlsx");
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        this.cycleCheckService.checkCycle(formulaScheme.getFormSchemeKey(), this.cycleCheckService.getCalcFormulaByScheme(formulaScheme), formulaMonitor);
        Map<String, List<Formula>> cycles = formulaMonitor.getCycles();
        XSSFWorkbook workbook = new XSSFWorkbook();
        int index = 0;
        List formCodes = cycles.keySet().stream().sorted().collect(Collectors.toList());
        for (String order : formCodes) {
            XSSFSheet sheet = workbook.createSheet("\u5faa\u73af" + ++index);
            sheet.setColumnWidth(0, 5120);
            sheet.setColumnWidth(1, 5120);
            sheet.setColumnWidth(2, 25600);
            XSSFRow headerRow = sheet.createRow(0);
            String[] header = new String[]{"\u62a5\u8868", "\u516c\u5f0f\u7f16\u53f7", "\u516c\u5f0f\u5185\u5bb9"};
            for (int i = 0; i < 3; ++i) {
                XSSFCell cell = headerRow.createCell(i, CellType.STRING);
                cell.setCellValue(header[i]);
            }
            List formulas = cycles.get(order).stream().filter(new HashSet()::add).collect(Collectors.toList());
            int rowIndex = 1;
            for (Formula formula : formulas) {
                XSSFRow dataRow = sheet.createRow(rowIndex);
                ++rowIndex;
                XSSFCell reportCell = dataRow.createCell(0, CellType.STRING);
                XSSFCell codeCell = dataRow.createCell(1, CellType.STRING);
                XSSFCell contentCell = dataRow.createCell(2, CellType.STRING);
                if (org.springframework.util.StringUtils.hasText(formula.getReportName())) {
                    String formTitle = formCodeToTitle.get(formula.getReportName());
                    String reportName = formTitle.concat("(").concat(formula.getReportName()).concat(")");
                    reportCell.setCellValue(reportName);
                } else {
                    reportCell.setCellValue("\u8868\u95f4\u516c\u5f0f");
                }
                codeCell.setCellValue(formula.getCode());
                contentCell.setCellValue(formula.getFormula());
            }
        }
        try (ServletOutputStream outputStream = response.getOutputStream();){
            workbook.write((OutputStream)outputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportErrorExcel(ImportResult result, HttpServletResponse res) throws IOException {
        List<String> repeatValue = result.getRepeatCode();
        String fileName = "\u7f16\u53f7\u91cd\u590d";
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        ServletOutputStream out = res.getOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(60);
        workbook.setSheetName(0, "\u91cd\u590d\u7f16\u53f7\u4fe1\u606f");
        for (int c = 0; c < repeatValue.size(); ++c) {
            String repeatCodeInfo = repeatValue.get(c);
            String[] CodeInfo = repeatCodeInfo.split(":");
            String sheetName = CodeInfo[2].split(";")[0];
            String codePosition = CodeInfo[4];
            XSSFRow row = sheet.createRow((short)c);
            XSSFCell xSSFCell = row.createCell(0);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            XSSFHyperlink hyperlink = (XSSFHyperlink)createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("'" + sheetName + "'!" + codePosition);
            xSSFCell.setHyperlink(hyperlink);
            xSSFCell.setCellValue(repeatCodeInfo);
            XSSFCellStyle linkStyle = workbook.createCellStyle();
            XSSFFont cellFont = workbook.createFont();
            cellFont.setUnderline((byte)1);
            cellFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
            linkStyle.setFont(cellFont);
            xSSFCell.setCellStyle(linkStyle);
        }
        String[] summary = null;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(result.getFormulaSchemeKey());
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        summary = !isEfdc ? new String[]{"\u7f16\u53f7", "\u8868\u8fbe\u5f0f", "\u8bf4\u660e", "\u7c7b\u578b", "\u5ba1\u6838\u7c7b\u578b"} : new String[]{"\u7f16\u53f7", "\u8868\u8fbe\u5f0f", "\u8bf4\u660e"};
        try {
            int sheetCount = 1;
            Map<String, List<FormulaExtDTO>> formulasMap = result.getFormulas();
            for (Map.Entry entry : formulasMap.entrySet()) {
                String formulasMapKey = (String)entry.getKey();
                List formulasMapValue = (List)entry.getValue();
                if (formulasMapKey == "repeatCode") continue;
                this.exportExcel(workbook, sheetCount, formulasMapKey, summary, formulasMapValue, (OutputStream)out);
                ++sheetCount;
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void exportExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<FormulaExtDTO> result, OutputStream out) throws Exception {
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        sheet.setDefaultColumnWidth(15);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(true);
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            XSSFCell cell = row.createCell((short)i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        List queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
        if (result != null) {
            for (int r = 0; r < result.size(); ++r) {
                row = sheet.createRow(r + 1);
                FormulaExtDTO formula = result.get(r);
                block9: for (int i = 0; i <= 4; ++i) {
                    XSSFCell cell = row.createCell(i);
                    switch (i) {
                        case 0: {
                            cell.setCellValue(formula.getCode());
                            continue block9;
                        }
                        case 1: {
                            cell.setCellValue(formula.getFormula());
                            continue block9;
                        }
                        case 2: {
                            cell.setCellValue(formula.getDescription());
                            continue block9;
                        }
                        case 3: {
                            String cellValue = "";
                            String typeCal = "\u8fd0\u7b97\u516c\u5f0f";
                            String typeCheck = "\u5ba1\u6838\u516c\u5f0f";
                            boolean useCalculate = formula.isUseCalculate();
                            boolean useCheck = formula.isUseCheck();
                            if (useCalculate && !useCheck) {
                                cellValue = typeCal;
                            }
                            if (useCalculate && useCheck) {
                                cellValue = typeCal + ";" + typeCheck;
                            }
                            if (!useCalculate && !useCheck) {
                                cellValue = "";
                            }
                            if (!useCalculate && useCheck) {
                                cellValue = typeCheck;
                            }
                            cell.setCellValue(cellValue);
                            continue block9;
                        }
                        case 4: {
                            String cellVal = "";
                            Integer checktype = formula.getChecktype();
                            if (checktype != null) {
                                for (AuditType auditType : queryAllAuditType) {
                                    if (!auditType.getCode().toString().equals(checktype.toString())) continue;
                                    cellVal = auditType.getTitle();
                                }
                            }
                            cell.setCellValue(cellVal);
                        }
                    }
                }
            }
        }
    }
}

