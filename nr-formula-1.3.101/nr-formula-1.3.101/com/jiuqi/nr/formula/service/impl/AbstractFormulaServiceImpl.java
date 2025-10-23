/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.util.DefinitionOptionUtils
 *  com.jiuqi.nr.definition.util.ParsedExpressionFilter
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nr.task.api.common.PageUtils
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.util.DefinitionOptionUtils;
import com.jiuqi.nr.definition.util.ParsedExpressionFilter;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formula.common.FormulaFunctionType;
import com.jiuqi.nr.formula.common.OrderUtils;
import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.dto.ImportResult;
import com.jiuqi.nr.formula.dto.StatusDTO;
import com.jiuqi.nr.formula.exception.FormulaException;
import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.exception.NrTaskFormulaException;
import com.jiuqi.nr.formula.service.IFormulaDataService;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.formula.service.IPublishCheckService;
import com.jiuqi.nr.formula.support.FormulaMonitor;
import com.jiuqi.nr.formula.utils.convert.FormulaConvert;
import com.jiuqi.nr.formula.utils.excel.FormulaExcelFactory;
import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.entity.BetweenFormulaExcelEntity;
import com.jiuqi.nr.formula.utils.excel.entity.EFDCFormulaExcelEntity;
import com.jiuqi.nr.formula.utils.excel.entity.FormulaExcelEntity;
import com.jiuqi.nr.formula.utils.excel.writer.ExcelWriter;
import com.jiuqi.nr.formula.utils.excel.writer.ExcelWriterBuilder;
import com.jiuqi.nr.formula.utils.excel.writer.WriteSheet;
import com.jiuqi.nr.formula.web.param.FormulaDataExportPM;
import com.jiuqi.nr.formula.web.param.FormulaListPM;
import com.jiuqi.nr.formula.web.param.FormulaMovePM;
import com.jiuqi.nr.formula.web.param.FormulaQueryPM;
import com.jiuqi.nr.formula.web.param.FormulaSavePM;
import com.jiuqi.nr.formula.web.param.ImportPM;
import com.jiuqi.nr.formula.web.vo.FormulaCheckResult;
import com.jiuqi.nr.formula.web.vo.FormulaDataVO;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.common.PageUtils;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractFormulaServiceImpl
implements IFormulaDataService {
    protected static final String XLS = "XLS";
    protected static final String XLSX = "XLSX";
    protected static final String REPEATCODEINFO = "\u91cd\u590d\u7f16\u53f7\u4fe1\u606f";
    protected static final String NUMBER = "\u7f16\u53f7";
    protected static final String EXPRESSION = "\u8868\u8fbe\u5f0f";
    protected static final String DESCRIPTION = "\u8bf4\u660e";
    protected static final String TYPE = "\u7c7b\u578b";
    protected static final String AUDITTYPE = "\u5ba1\u6838\u7c7b\u578b";
    protected static final String[] HEADERS = new String[]{"\u7f16\u53f7", "\u8868\u8fbe\u5f0f", "\u8bf4\u660e", "\u7c7b\u578b", "\u5ba1\u6838\u7c7b\u578b"};
    protected static final String BJFORMULA = "\u8868\u95f4\u516c\u5f0f";
    protected static final String QUOTETYPE = "\u5f15\u7528\u7c7b\u578b";
    protected static final String BALANCEZBEXP = "\u8c03\u6574\u6307\u6807";
    protected static final String OWNERLEVELANDID = "ownerLevelAndIds";
    protected static final String SERVERCODE = "SERVERCODE";
    @Autowired
    protected IDesignTimeViewController designTimeViewController;
    @Autowired
    protected IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    protected IPublishCheckService publishCheckService;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    protected IDesignDataSchemeService designDataSchemeService;
    @Autowired
    protected com.jiuqi.nr.definition.controller.IDesignTimeViewController designTimeViewController1;
    @Autowired
    protected AuditTypeDefineService auditTypeDefineService;
    @Autowired
    protected IFormulaSchemeService formulaSchemeService;
    @Autowired
    protected IFileAreaService fileAreaService;
    @Autowired
    protected ServeCodeService serveCodeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ParsedExpressionFilter parsedExpressionFilter;
    @Autowired
    private DefinitionOptionUtils definitionOptionUtils;
    protected static final String REGEX = "^[a-zA-Z][\\w-]{0,25}$";
    protected static final String ERRMSG = "\u516c\u5f0f\u7f16\u53f7\u4e0d\u7b26\u5408\u5b57\u6bcd\u5f00\u5934,\u5b57\u6bcd\u52a0\u6570\u5b57\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u9650\u523625\u8981\u6c42!";

    protected List<FormulaDTO> filterFormulaData(List<FormulaDTO> formulaDTOS, FormulaListPM param) {
        return formulaDTOS.stream().filter(f -> CollectionUtils.isEmpty(param.getConditionCode()) || f.getConditions().stream().anyMatch(c -> param.getConditionCode().contains(c.getCode()))).filter(f -> CollectionUtils.isEmpty(param.getCheckType()) || param.getCheckType().contains(f.getCheckType())).filter(f -> CollectionUtils.isEmpty(param.getFormulaType()) || f.getUseCalculate() && param.getFormulaType().contains(1) || f.getUseCheck() && param.getFormulaType().contains(2) || f.getUseBalance() && param.getFormulaType().contains(4)).collect(Collectors.toList());
    }

    @Override
    public FormulaDataVO searchFormulaData(FormulaListPM param) {
        String formCode;
        FormulaDataVO res = new FormulaDataVO();
        List<FormulaDTO> formulaDTOS = this.listFormulaByForm(param);
        List<String> codeList = formulaDTOS.stream().map(FormulaDTO::getCode).collect(Collectors.toList());
        String formKey = param.getFormKey();
        if ("BJ".equals(formKey)) {
            formCode = formKey;
        } else {
            DesignFormDefine form = this.designTimeViewController.getForm(formKey);
            if (form == null) {
                return res;
            }
            formCode = form.getFormCode();
        }
        Optional<String> max = formulaDTOS.stream().map(FormulaDTO::getCode).filter(code -> code.contains(formCode)).max(String.CASE_INSENSITIVE_ORDER);
        res.setMaxCode(max.orElse(null));
        res.setFormulaCode(codeList);
        List<FormulaDTO> list = this.filterFormulaData(formulaDTOS, param);
        if (StringUtils.hasText(param.getLocationCode()) && param.getPageSize() != null) {
            for (int i = 0; i < list.size(); ++i) {
                if (!param.getLocationCode().equals(list.get(i).getCode())) continue;
                int pageNo = i / param.getPageSize() + 1;
                param.setPageNo(pageNo);
                res.setNewPage(pageNo);
                break;
            }
        }
        if (param.getPageNo() != null && param.getPageSize() != null) {
            List paginate = PageUtils.paginate(list, (int)param.getPageNo(), (int)param.getPageSize());
            if (!CollectionUtils.isEmpty(paginate)) {
                FormulaDTO first = (FormulaDTO)paginate.get(0);
                FormulaDTO last = (FormulaDTO)paginate.get(paginate.size() - 1);
                int firstIdx = formulaDTOS.indexOf(first);
                int lastIdx = formulaDTOS.indexOf(last);
                if (firstIdx != 0) {
                    res.setLastFormula(formulaDTOS.get(firstIdx - 1));
                }
                if (lastIdx != formulaDTOS.size() - 1) {
                    res.setNextFormula(formulaDTOS.get(lastIdx + 1));
                }
            }
            res.setRows(paginate);
        } else {
            if (!CollectionUtils.isEmpty(list)) {
                FormulaDTO first = list.get(0);
                FormulaDTO last = list.get(list.size() - 1);
                int firstIdx = formulaDTOS.indexOf(first);
                int lastIdx = formulaDTOS.indexOf(last);
                if (firstIdx != 0) {
                    res.setLastFormula(formulaDTOS.get(firstIdx - 1));
                }
                if (lastIdx != formulaDTOS.size() - 1) {
                    res.setNextFormula(formulaDTOS.get(lastIdx + 1));
                }
            }
            res.setRows(list);
        }
        res.setTotal(list.size());
        return res;
    }

    @Override
    public FormulaDataVO queryFormulaData(FormulaQueryPM param) {
        Assert.notNull((Object)param.getTailKey(), "\u672b\u5c3e\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        FormulaDataVO res = new FormulaDataVO();
        List<FormulaDTO> formulaDTOS = this.listFormulaByForm(param);
        List<FormulaDTO> list = this.filterFormulaData(formulaDTOS, param);
        Optional<FormulaDTO> find = list.stream().filter(e -> e.getKey().equals(param.getTailKey())).findAny();
        if (find.isPresent()) {
            List<FormulaDTO> formulas;
            FormulaDTO last;
            int lastIdx;
            int idx = list.indexOf(find.get());
            int endIdx = list.size();
            if (param.getQueryCount() != null && param.getQueryCount() + idx <= endIdx) {
                endIdx = param.getQueryCount() + idx;
            }
            if ((lastIdx = formulaDTOS.indexOf(last = (formulas = list.subList(idx, endIdx)).get(formulas.size() - 1))) != formulaDTOS.size() - 1) {
                res.setNextFormula(formulaDTOS.get(lastIdx + 1));
            }
            res.setRows(formulas);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void moveFormulaData(FormulaMovePM param) {
        String refKey;
        if (!CollectionUtils.isEmpty(param.getItemList())) {
            this.saveFormulaData(param);
        }
        String formKey = "BJ".equals(param.getFormKey()) ? null : param.getFormKey();
        List<DesignFormulaDefine> formulaDefines = this.queryFormulaBySchemeAndForm(param.getFormulaSchemeKey(), formKey, param.getUnit());
        List<String> keys = param.getKeys();
        List<DesignFormulaDefine> filter = formulaDefines.stream().filter(e -> keys.contains(e.getKey())).sorted(Comparator.comparing(FormulaDefine::getOrdinal)).collect(Collectors.toList());
        if (filter.isEmpty()) {
            return;
        }
        String string = refKey = param.getMoveWay() == 0 ? (String)Optional.ofNullable(param.getLast()).map(FormulaDTO::getKey).orElse(null) : (String)Optional.ofNullable(param.getNext()).map(FormulaDTO::getKey).orElse(null);
        if (refKey == null) {
            return;
        }
        Optional<DesignFormulaDefine> refOpt = formulaDefines.stream().filter(e -> e.getKey().equals(refKey)).findFirst();
        if (!refOpt.isPresent()) {
            return;
        }
        DesignFormulaDefine refFormula = refOpt.get();
        BigDecimal refOrdinal = refFormula.getOrdinal();
        List originalOrdinals = filter.stream().map(FormulaDefine::getOrdinal).collect(Collectors.toList());
        if (param.getMoveWay() == 0) {
            refFormula.setOrdinal((BigDecimal)originalOrdinals.get(filter.size() - 1));
            for (int i = 0; i < filter.size(); ++i) {
                ((DesignFormulaDefine)filter.get(i)).setOrdinal(i == 0 ? refOrdinal : (BigDecimal)originalOrdinals.get(i - 1));
            }
        } else {
            refFormula.setOrdinal((BigDecimal)originalOrdinals.get(0));
            for (int i = filter.size() - 1; i >= 0; --i) {
                ((DesignFormulaDefine)filter.get(i)).setOrdinal(i == filter.size() - 1 ? refOrdinal : (BigDecimal)originalOrdinals.get(i + 1));
            }
        }
        filter.add(refFormula);
        this.updateFormulaDefine(filter);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveFormulaData(FormulaSavePM pm) {
        String formulaScheme = pm.getFormulaSchemeKey();
        List<FormulaDTO> formulas = pm.getItemList();
        try {
            if (!this.publishCheckService.canFormulaSchemeEdit(formulaScheme)) {
                throw new NrTaskFormulaException("\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
            }
            Map<Constants.DataStatus, List<FormulaDTO>> map = formulas.stream().peek(e -> {
                if ("BJ".equals(e.getFormKey())) {
                    e.setFormKey(null);
                }
            }).collect(Collectors.groupingBy(StatusDTO::getStatus));
            List<FormulaDTO> dirtyList = map.get(Constants.DataStatus.MODIFY);
            List<FormulaDTO> newList = map.get(Constants.DataStatus.NEW);
            List<FormulaDTO> delList = map.get(Constants.DataStatus.DELETE);
            try {
                this.saveCheck(dirtyList, delList);
                this.saveCheck(newList, delList);
            }
            catch (Exception e2) {
                throw new FormulaException(e2.getMessage());
            }
            if (!CollectionUtils.isEmpty(delList)) {
                this.deleteFormulas(delList.stream().map(FormulaDTO::getKey).collect(Collectors.toList()));
            }
            if (!CollectionUtils.isEmpty(dirtyList)) {
                this.updateFormulas(dirtyList);
            }
            if (!CollectionUtils.isEmpty(newList)) {
                this.insertFormulas(newList);
            }
        }
        catch (NrDefinitionRuntimeException e3) {
            throw new NrTaskFormulaException(e3.getCause().getMessage());
        }
    }

    @Override
    public abstract void updateFormulas(List<FormulaDTO> var1);

    @Override
    public abstract void insertFormulas(List<FormulaDTO> var1);

    public abstract void updateFormulaDefine(List<DesignFormulaDefine> var1);

    @Override
    public void deleteFormulas(List<String> key) {
        this.formulaDesignTimeController.deleteFormula(key.toArray(new String[0]));
        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula(key.toArray(new String[0]));
    }

    public FormulaCheckResult formulaCheck(String formulaSchemeKey, String formKey, boolean efdc, List<FormulaExtDTO> modifyItems, List<String> deletedKeys) {
        Assert.notNull((Object)formulaSchemeKey, "formulaSchemeKey must not be null");
        if (StringUtils.hasText(formKey)) {
            DesignFormDefine form = this.designTimeViewController.getForm(formKey);
            Set formulaIds = modifyItems.stream().map(Formula::getId).collect(Collectors.toSet());
            List formulaDefines = this.formulaDesignTimeController.listFormulaBySchemeAndForm(formulaSchemeKey, formKey);
            for (DesignFormulaDefine formulaDefine : formulaDefines) {
                if (formulaIds.contains(formulaDefine.getKey())) continue;
                FormulaExtDTO formulaDto = new FormulaExtDTO();
                formulaDto.setId(formulaDefine.getKey());
                formulaDto.setCode(formulaDefine.getCode());
                formulaDto.setFormula(formulaDefine.getExpression());
                formulaDto.setReportName(form.getFormCode());
                formulaDto.setFormKey(formKey);
                formulaDto.setFormulaSchemeKey(formulaSchemeKey);
                formulaDto.setUseCheck(formulaDefine.getUseCheck());
                formulaDto.setChecktype(formulaDefine.getCheckType());
                formulaDto.setUseBalance(formulaDefine.getUseBalance());
                formulaDto.setUseCalculate(formulaDefine.getUseCalculate());
                modifyItems.add(formulaDto);
            }
        }
        return this.formulaCheckByFormScheme(this.getFormSchemeKey(formulaSchemeKey), efdc, modifyItems, deletedKeys);
    }

    public <T extends FormulaExtDTO> FormulaCheckResult formulaCheckByFormScheme(String formScheme, boolean efdc, List<T> list, List<String> deleted) {
        Assert.notNull((Object)formScheme, "formScheme must not be null");
        FormulaCheckResult result = new FormulaCheckResult();
        HashMap checkResult = new HashMap(list.size());
        HashMap<FormulaFunctionType, List<T>> functionTypeListMap = new HashMap<FormulaFunctionType, List<T>>();
        this.formulaTypeClassification(list, functionTypeListMap, checkResult, efdc);
        this.formulaCodeCheck(list, checkResult, deleted);
        FormulaMonitor formulaMonitor = new FormulaMonitor(checkResult);
        if (!efdc) {
            this.parseFormulas(formScheme, formulaMonitor, functionTypeListMap);
            this.parseBalanceFormula(formScheme, formulaMonitor, functionTypeListMap, checkResult);
        }
        result.setTotal(list.size());
        result.setError(checkResult.values().stream().collect(Collectors.toMap(Formula::getId, FormulaExtDTO::getErrorMsg)));
        return result;
    }

    protected <T extends FormulaExtDTO> void formulaTypeClassification(List<T> list, Map<FormulaFunctionType, List<T>> functionTypeListMap, Map<String, T> checkResult, boolean efdc) {
        functionTypeListMap.put(FormulaFunctionType.CALCULATE, new ArrayList());
        functionTypeListMap.put(FormulaFunctionType.BALANCE, new ArrayList());
        functionTypeListMap.put(FormulaFunctionType.CHECK, new ArrayList());
        for (FormulaExtDTO t : list) {
            boolean hasContent = StringUtils.hasText(t.getFormula());
            if (!hasContent) {
                AbstractFormulaServiceImpl.addCheckError(checkResult, t, "\u516c\u5f0f\u8868\u8fbe\u5f0f\u4e3a\u7a7a! ");
            }
            if (t.isUseCalculate()) {
                functionTypeListMap.get((Object)FormulaFunctionType.CALCULATE).add(t);
                continue;
            }
            if (t.isUseCheck()) {
                functionTypeListMap.get((Object)FormulaFunctionType.CHECK).add(t);
                continue;
            }
            if (t.isUseBalance()) {
                functionTypeListMap.get((Object)FormulaFunctionType.BALANCE).add(t);
                continue;
            }
            if (efdc || !hasContent || t.getFormula().startsWith("//")) continue;
            AbstractFormulaServiceImpl.addCheckError(checkResult, t, "\u516c\u5f0f\u7c7b\u578b\u4e3a\u7a7a! ");
        }
    }

    protected <T extends FormulaExtDTO> void formulaCodeCheck(List<T> list, Map<String, T> checkResult, List<String> deleted) {
        Map<String, List<FormulaExtDTO>> codeMap = list.stream().filter(e -> StringUtils.hasText(e.getCode())).collect(Collectors.groupingBy(FormulaExtDTO::getCode));
        for (FormulaExtDTO item : list) {
            if (!StringUtils.hasText(item.getCode())) {
                AbstractFormulaServiceImpl.addCheckError(checkResult, item, "\u516c\u5f0f\u7f16\u53f7\u4e3a\u7a7a! ");
                continue;
            }
            if (this.formulaCodeCheck(item.getCode(), item.getId(), item.getFormulaSchemeKey(), deleted)) {
                AbstractFormulaServiceImpl.addCheckError(checkResult, item, "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d! ");
            } else {
                List<FormulaExtDTO> ts = codeMap.get(item.getCode());
                if (ts != null && ts.size() > 1) {
                    AbstractFormulaServiceImpl.addCheckError(checkResult, item, "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d! ");
                }
            }
            this.formulaCodeRegCheck(checkResult, item);
        }
    }

    private void saveCheck(List<FormulaDTO> formulas, List<FormulaDTO> deleteList) {
        if (CollectionUtils.isEmpty(formulas)) {
            return;
        }
        Map<String, List<FormulaDTO>> codeMap = formulas.stream().filter(e -> StringUtils.hasText(e.getCode())).collect(Collectors.groupingBy(FormulaDTO::getCode));
        Set<Object> deleteSet = new HashSet();
        if (!CollectionUtils.isEmpty(deleteList)) {
            deleteSet = deleteList.stream().map(FormulaDTO::getKey).collect(Collectors.toSet());
        }
        for (FormulaDTO formula : formulas) {
            if (!StringUtils.hasText(formula.getCode())) {
                throw new FormulaException("\u516c\u5f0f\u7f16\u53f7\u4e3a\u7a7a! ");
            }
            ArrayList<String> deletedKeys = new ArrayList();
            if (!CollectionUtils.isEmpty(deleteList)) {
                deletedKeys = deleteList.stream().map(FormulaDTO::getKey).collect(Collectors.toList());
            }
            if (this.formulaCodeCheck(formula.getCode(), formula.getKey(), formula.getFormulaSchemeKey(), deletedKeys) && !deleteSet.contains(formula.getKey())) {
                throw new FormulaException("\u516c\u5f0f\u7f16\u53f7\u91cd\u590d! ");
            }
            List<FormulaDTO> ts = codeMap.get(formula.getCode());
            if (ts != null && ts.size() > 1) {
                throw new FormulaException("\u516c\u5f0f\u7f16\u53f7\u91cd\u590d! ");
            }
            try {
                this.codeCheck(formula.getCode(), formula.getUnit());
            }
            catch (FormulaException e2) {
                throw new FormulaException(e2.getMessage());
            }
        }
    }

    protected <T extends FormulaExtDTO> void formulaCodeRegCheck(Map<String, T> checkResult, T item) {
        try {
            this.codeCheck(item.getCode(), item.getUnit());
        }
        catch (FormulaException e) {
            AbstractFormulaServiceImpl.addCheckError(checkResult, item, e.getMessage());
        }
    }

    protected void codeCheck(String code, String unit) {
        if (!Pattern.matches(REGEX, code)) {
            throw new FormulaException(ERRMSG);
        }
    }

    protected boolean formulaCodeCheck(String code, String id, String formulaScheme, List<String> deleted) {
        Assert.notNull((Object)code, "\u516c\u5f0f\u6821\u9a8c, \u516c\u5f0f\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a!");
        Assert.notNull((Object)formulaScheme, "\u516c\u5f0f\u6821\u9a8c, formulaSchemeKey\u4e0d\u80fd\u4e3a\u7a7a!");
        DesignFormulaDefine queryItem = this.formulaDesignTimeController.getFormulaByCodeAndScheme(code, formulaScheme);
        return queryItem != null && !queryItem.getKey().equals(id) && !deleted.contains(id);
    }

    protected <T extends FormulaExtDTO> void parseFormulas(String formSchemeKey, FormulaMonitor<T> formulaMonitor, Map<FormulaFunctionType, List<T>> functionTypeListMap) {
        ExecutorContext executorContext = this.getExecutorContext(formSchemeKey);
        try {
            List calcList;
            List checkList;
            List<T> checkExpression = functionTypeListMap.get((Object)FormulaFunctionType.CHECK);
            List<T> calcExpression = functionTypeListMap.get((Object)FormulaFunctionType.CALCULATE);
            if (!CollectionUtils.isEmpty(checkExpression) && !CollectionUtils.isEmpty(checkList = checkExpression.stream().filter(e -> StringUtils.hasText(e.getFormula())).collect(Collectors.toList()))) {
                DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, new ArrayList(checkList), (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            }
            if (!CollectionUtils.isEmpty(calcExpression) && !CollectionUtils.isEmpty(calcList = calcExpression.stream().filter(e -> StringUtils.hasText(e.getFormula())).collect(Collectors.toList()))) {
                List iParsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, new ArrayList(calcList), (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE, formulaMonitor);
                if (!this.definitionOptionUtils.isSpecifyDimensionAssignment()) {
                    DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
                    DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formScheme.getTaskKey());
                    List reportDimension = this.designDataSchemeService.getReportDimension(taskDefine.getDataScheme());
                    List dwDimension = this.designDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.UNIT);
                    List periodDimension = this.designDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.PERIOD);
                    reportDimension.addAll(dwDimension);
                    reportDimension.addAll(periodDimension);
                    Set dimensionNames = reportDimension.stream().map(d -> this.entityMetaService.getDimensionName(d.getDimKey())).collect(Collectors.toSet());
                    this.parsedExpressionFilter.removeCrossDimensionFML(dimensionNames, iParsedExpressions, formulaMonitor);
                }
            }
        }
        catch (ParseException e2) {
            throw new NrTaskFormulaException(e2);
        }
    }

    protected <T extends FormulaExtDTO> void parseBalanceFormula(String formSchemeKey, FormulaMonitor<T> formulaMonitor, Map<FormulaFunctionType, List<T>> functionTypeListMap, Map<String, T> checkResult) {
        ExecutorContext executorContext = this.getExecutorContext(formSchemeKey);
        List<T> ts = functionTypeListMap.get((Object)FormulaFunctionType.BALANCE);
        try {
            for (FormulaExtDTO t : ts) {
                if (StringUtils.hasLength(t.getBalanceZBExp())) {
                    String dataFieldKey;
                    DesignDataField dataField;
                    List parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, Collections.singletonList(t), (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.BALANCE, formulaMonitor);
                    if (parsedExpressions.size() != 1 || !Objects.nonNull(dataField = this.designDataSchemeService.getDataField(dataFieldKey = ((IParsedExpression)parsedExpressions.get(0)).getBalanceField().getUID())) || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL || dataField.getDataFieldType() == DataFieldType.INTEGER) continue;
                    Map<String, T> checkResult1 = formulaMonitor.getCheckResult();
                    FormulaExtDTO t1 = (FormulaExtDTO)checkResult1.get(t.getCode());
                    if (Objects.nonNull(t1)) {
                        t1.setErrorMsg("\u8c03\u6574\u6307\u6807\u5fc5\u987b\u4e3a\u6570\u503c\u578b\uff01");
                        continue;
                    }
                    FormulaExtDTO clone = (FormulaExtDTO)t.clone();
                    clone.setUseBalance(true);
                    clone.setErrorMsg("\u8c03\u6574\u6307\u6807\u5fc5\u987b\u4e3a\u6570\u503c\u578b\uff01");
                    checkResult1.put(t.getCode(), clone);
                    continue;
                }
                FormulaExtDTO clone = (FormulaExtDTO)t.clone();
                clone.setErrorMsg("\u516c\u5f0f\u4e3a\u5e73\u8861\u516c\u5f0f\u65f6\uff0c\u8c03\u6574\u6307\u6807\u4e0d\u80fd\u4e3a\u7a7a\uff01");
                checkResult.put(t.getCode(), clone);
            }
        }
        catch (ParseException | CloneNotSupportedException e) {
            throw new FormulaResourceException(e);
        }
    }

    protected static <T extends FormulaExtDTO> void addCheckError(Map<String, T> checkResult, T item, String message) {
        FormulaExtDTO t1 = (FormulaExtDTO)checkResult.get(item.getId());
        if (t1 != null) {
            t1.setErrorMsg(message);
        } else {
            item.setErrorMsg(message);
            checkResult.put(item.getId(), item);
        }
    }

    protected ExecutorContext getExecutorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        DataEngineConsts.FormulaShowType showType = this.getFormulaShowType(formSchemeKey);
        context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
        try {
            context.setDesignTimeData(true, this.dataDefinitionDesignTimeController);
        }
        catch (ParseException e) {
            throw new FormulaResourceException(e);
        }
        List formulaVariables = this.formulaDesignTimeController.listFormulaVariByFormScheme(formSchemeKey);
        DesignReportFmlExecEnvironment environment = this.getReportFmlExecEnvironment(formSchemeKey, formulaVariables);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowType(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    private DesignReportFmlExecEnvironment getReportFmlExecEnvironment(String formSchemeKey, List<FormulaVariDefine> formulaVariables) {
        return new DesignReportFmlExecEnvironment(this.designTimeViewController1, this.dataDefinitionDesignTimeController, formSchemeKey, formulaVariables);
    }

    private String getFormSchemeKey(String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        if (formulaScheme != null) {
            return formulaScheme.getFormSchemeKey();
        }
        return null;
    }

    @Override
    public void exportFormulaInForm(HttpServletResponse response, FormulaDataExportPM exportPM) {
        String formKey = exportPM.getFormKey();
        String formulaSchemeKey = exportPM.getFormulaSchemeKey();
        if (StringUtils.hasLength(formKey)) {
            ExcelWriterBuilder writerBuilder;
            String formTitle = this.getFormTitle(formKey);
            this.configResponse(response, formTitle + "_\u516c\u5f0f.xlsx");
            DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
            List<FormulaDTO> formulaDTOS = this.listFormulaBySchemeAndForm(exportPM);
            ArrayList<ExcelEntity> data = new ArrayList<ExcelEntity>(formulaDTOS.size());
            try {
                writerBuilder = FormulaExcelFactory.write(response.getOutputStream());
            }
            catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            boolean existBalanceZb = formulaDTOS.stream().anyMatch(e -> StringUtils.hasText(e.getBalanceZBExp()));
            if (formulaScheme.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)) {
                this.convertToExcelEntity(formulaDTOS, data, EFDCFormulaExcelEntity.class);
                writerBuilder.headClass(EFDCFormulaExcelEntity.class);
            } else if ("BJ".equals(formKey)) {
                this.convertToExcelEntity(formulaDTOS, data, BetweenFormulaExcelEntity.class);
                writerBuilder.headClass(BetweenFormulaExcelEntity.class);
            } else {
                this.convertToExcelEntity(formulaDTOS, data, FormulaExcelEntity.class);
                writerBuilder.headClass(FormulaExcelEntity.class);
            }
            WriteSheet sheet = FormulaExcelFactory.writerSheet(formTitle).build();
            try (ExcelWriter writer = writerBuilder.build();){
                writer.write(data, sheet, (row, col) -> {
                    if (col == 5) {
                        return existBalanceZb;
                    }
                    return true;
                });
            }
            catch (IOException | IllegalAccessException e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    @Override
    public void exportFormulaInFormulaScheme(HttpServletResponse response, FormulaDataExportPM exportPM) {
        String formulaSchemeKey = exportPM.getFormulaSchemeKey();
        FormulaSchemeDTO formulaScheme = this.formulaSchemeService.getFormulaScheme(formulaSchemeKey);
        String formSchemeKey = formulaScheme.getFormSchemeKey();
        List<FormulaDTO> dtoList = this.listFormulaByScheme(formulaSchemeKey);
        Map map = dtoList.stream().collect(Collectors.groupingBy(FormulaDTO::getFormKey, HashMap::new, Collectors.mapping(x -> x, Collectors.toList())));
        List groupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        this.configResponse(response, formulaScheme.getTitle() + "_\u5168\u90e8\u516c\u5f0f.xlsx");
        int index = 0;
        try (ExcelWriter excelWriter = FormulaExcelFactory.write(response.getOutputStream()).build();){
            WriteSheet sheet;
            ArrayList<ExcelEntity> datas;
            List bjFormulas = (List)map.get("BJ");
            if (!CollectionUtils.isEmpty(bjFormulas)) {
                boolean existBalanceZbInBj = bjFormulas.stream().anyMatch(e -> StringUtils.hasText(e.getBalanceZBExp()));
                datas = new ArrayList<ExcelEntity>(bjFormulas.size());
                this.convertToExcelEntity(bjFormulas, datas, BetweenFormulaExcelEntity.class);
                sheet = FormulaExcelFactory.writerSheet(BetweenFormulaExcelEntity.class, index++, BJFORMULA).build();
                excelWriter.write(datas, sheet, (r, c) -> {
                    if (c == 5) {
                        return existBalanceZbInBj;
                    }
                    return true;
                });
            }
            for (DesignFormGroupDefine group : groupDefines) {
                List formDefines = this.designTimeViewController.listFormByGroup(group.getKey());
                Map<String, String> formTitleMap = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, this::getFormTitle));
                for (DesignFormDefine formDefine : formDefines) {
                    String formKey = formDefine.getKey();
                    List list = (List)map.get(formKey);
                    if (CollectionUtils.isEmpty(list)) continue;
                    boolean existBalanceZb = list.stream().anyMatch(e -> StringUtils.hasText(e.getBalanceZBExp()));
                    datas = new ArrayList(list.size());
                    if (formulaScheme.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)) {
                        this.convertToExcelEntity(list, datas, EFDCFormulaExcelEntity.class);
                        sheet = FormulaExcelFactory.writerSheet(EFDCFormulaExcelEntity.class, index++, formTitleMap.getOrDefault(formKey, BJFORMULA)).build();
                    } else if ("BJ".equals(formKey)) {
                        this.convertToExcelEntity(list, datas, BetweenFormulaExcelEntity.class);
                        sheet = FormulaExcelFactory.writerSheet(BetweenFormulaExcelEntity.class, index++, formTitleMap.getOrDefault(formKey, BJFORMULA)).build();
                    } else {
                        this.convertToExcelEntity(list, datas, FormulaExcelEntity.class);
                        sheet = FormulaExcelFactory.writerSheet(FormulaExcelEntity.class, index++, formTitleMap.getOrDefault(formKey, BJFORMULA)).build();
                    }
                    excelWriter.write(datas, sheet, (r, c) -> {
                        if (c == 5) {
                            return existBalanceZb;
                        }
                        return true;
                    });
                }
            }
        }
        catch (IOException | IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ImportResult importFormulaExcel(ImportPM importPM) {
        ImportResult result = new ImportResult();
        String formulaScheme = importPM.getFormulaSchemeKey();
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(formulaScheme);
        if (null == formulaSchemeDefine) {
            throw new FormulaResourceException("\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        List formDefines = this.designTimeViewController.listFormByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        List<String> formId = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        Workbook workbook = this.getWorkBook(importPM.getFileKey());
        ArrayList<String> repeatCheck = new ArrayList<String>();
        Map<String, List<FormulaExtDTO>> parserFormulas = this.paraExcel(workbook, formId, formulaScheme, importPM.getCover(), importPM.getUnit(), repeatCheck);
        if (!CollectionUtils.isEmpty(repeatCheck)) {
            result.setRepeatCode(repeatCheck);
            result.setFormulas(parserFormulas);
            return result;
        }
        ArrayList<Integer> levelCheck = new ArrayList<Integer>();
        if (importPM.getCover().booleanValue()) {
            this.allImportFormula(formulaScheme, parserFormulas, importPM.getUnit(), levelCheck);
        } else {
            this.addImportFormula(formId, formulaScheme, parserFormulas, levelCheck);
        }
        result.setRepeatCode(repeatCheck);
        result.setFormulas(parserFormulas);
        result.setLevelCheck(levelCheck);
        return result;
    }

    private Workbook getWorkBook(String fileKey) {
        Workbook workbook;
        byte[] file = this.fileAreaService.download(fileKey, new FileAreaDTO(true));
        try (ByteArrayInputStream fileStream = new ByteArrayInputStream(file);){
            workbook = this.createWorkBook(fileStream);
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u5de5\u4f5c\u8868\u5931\u8d25:" + e.getMessage());
        }
        return workbook;
    }

    protected Workbook createWorkBook(InputStream ins) {
        Workbook workbook = null;
        try {
            ZipSecureFile.setMinInflateRatio(0.002);
            workbook = WorkbookFactory.create(ins);
        }
        catch (IOException e) {
            throw new RuntimeException("Excel\u89e3\u6790\u5931\u8d25", e);
        }
        return workbook;
    }

    private Map<String, List<FormulaExtDTO>> paraExcel(Workbook workBook, List<String> formId, String formulaScheme, boolean cover, String unit, List<String> errorMessage) {
        HashMap<String, List<FormulaExtDTO>> parserFormulas = new HashMap<String, List<FormulaExtDTO>>();
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new FormulaResourceException("\u5bfc\u5165\u516c\u5f0f\uff1a\u516c\u5f0f\u65b9\u6848\u672a\u627e\u5230");
        }
        int sheetCount = workBook.getNumberOfSheets();
        List<FormulaDTO> formulas = this.listFormulaByScheme(formulaScheme);
        HashMap<String, String> formulaCodeToKey = new HashMap<String, String>();
        HashMap<String, List<String>> formToFormulas = new HashMap<String, List<String>>();
        HashSet<String> formulaCodeSet = new HashSet<String>();
        if (cover) {
            HashSet<String> excelFormCodeSet = new HashSet<String>();
            for (int i = 0; i < sheetCount; ++i) {
                excelFormCodeSet.add(workBook.getSheetAt(i).getSheetName().split(" ")[0]);
            }
            this.getAllFormulaCodeSet(formulas, excelFormCodeSet, formulaCodeSet, formulaCodeToKey);
        } else {
            this.getFormFormulasMap(formToFormulas, formulas, formulaCodeToKey);
        }
        Map<Object, Object> formCodeToDefine = new HashMap();
        if (formId != null) {
            formCodeToDefine = formId.stream().map(formKey -> this.designTimeViewController.getForm(formKey)).filter(Objects::nonNull).collect(Collectors.toMap(FormDefine::getFormCode, form -> form, (oldValue, newValue) -> newValue));
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        Map<String, Integer> auditTypeMap = this.getAuditTypeMap();
        HashSet<String> excelFormulaCodeSet = new HashSet<String>();
        for (int s = 0; s < sheetCount; ++s) {
            String sheetName;
            Sheet sheet = workBook.getSheetAt(s);
            if (sheet == null || (sheetName = sheet.getSheetName()).equals(REPEATCODEINFO)) continue;
            Map<Integer, String> headerMap = this.buildHeader(sheet);
            String formCode = sheetName.split(" ")[0];
            DesignFormDefine form2 = (DesignFormDefine)formCodeToDefine.get(formCode);
            int rowCount = sheet.getLastRowNum();
            ArrayList<FormulaExtDTO> formulaList = new ArrayList<FormulaExtDTO>();
            for (int r = 1; r <= rowCount; ++r) {
                boolean hasExpression = true;
                Row row = sheet.getRow(r);
                int colCount = row.getLastCellNum();
                FormulaExtDTO formula = new FormulaExtDTO();
                block20: for (int c = 0; c < colCount && hasExpression; ++c) {
                    String cellValue = headerMap.get(c);
                    String cValue = this.getCellValue(row.getCell(c));
                    switch (cellValue) {
                        case "\u7f16\u53f7": {
                            String formulaCode = this.buildCode(cValue, form2, r, unit);
                            formula.setCode(formulaCode);
                            continue block20;
                        }
                        case "\u8868\u8fbe\u5f0f": {
                            hasExpression = StringUtils.hasLength(cValue);
                            formula.setFormula(cValue);
                            continue block20;
                        }
                        case "\u8bf4\u660e": {
                            if (cValue.length() > 1000) {
                                errorMessage.add("excel\u516c\u5f0f\u8bf4\u660e\u957f\u5ea6\u8d85\u957f:sheet\u540d\u79f0:" + sheetName + ";\u957f\u5ea6\u9650\u5236\u4e3a:1000;\u8d85\u957f\u4f4d\u7f6e:C" + (r + 1));
                            }
                            formula.setDescription(cValue);
                            continue block20;
                        }
                        case "\u7c7b\u578b": {
                            if (isEfdc) continue block20;
                            if (this.validateCheckType(cValue)) {
                                if (cValue.contains("\u8fd0\u7b97\u516c\u5f0f") || cValue.contains("\u8fd0\u7b97")) {
                                    formula.setUseCalculate(true);
                                }
                                if (cValue.contains("\u5ba1\u6838\u516c\u5f0f") || cValue.contains("\u5ba1\u6838")) {
                                    formula.setUseCheck(true);
                                }
                                if (!cValue.contains("\u5e73\u8861\u516c\u5f0f") && !cValue.contains("\u5e73\u8861")) continue block20;
                                formula.setUseBalance(true);
                                continue block20;
                            }
                            errorMessage.add("excel\u516c\u5f0f\u7c7b\u578b\u9519\u8bef:sheet\u540d\u79f0:" + sheetName + ";\u81ea\u5b9a\u4e49\u516c\u5f0f\u4ec5\u652f\u6301\u5ba1\u6838\u7c7b\u578b;\u9519\u8bef\u4f4d\u7f6e:D" + (r + 1));
                            continue block20;
                        }
                        case "\u5ba1\u6838\u7c7b\u578b": {
                            if (isEfdc || !formula.isUseCheck()) continue block20;
                            Integer checkType = auditTypeMap.get(cValue);
                            formula.setChecktype(checkType);
                            continue block20;
                        }
                        case "\u8c03\u6574\u6307\u6807": {
                            if (StringUtils.hasText(cValue)) {
                                formula.setBalanceZBExp(cValue);
                                continue block20;
                            }
                            formula.setBalanceZBExp("");
                            continue block20;
                        }
                    }
                }
                if (!hasExpression) continue;
                formula.setOrder(OrderGenerator.newOrder());
                formula.setFormulaSchemeKey(formulaScheme);
                formula.setFormKey(sheetName.equals(BJFORMULA) ? null : form2.getKey());
                formula.setReportName(form2 == null ? "" : form2.getFormCode());
                formula.setId(formulaCodeToKey.get(formula.getCode()) == null ? UUIDUtils.getKey() : (String)formulaCodeToKey.get(formula.getCode()));
                formula.setUnit(unit);
                formulaList.add(formula);
                this.checkFormulaCode(formula, excelFormulaCodeSet, formulaCodeSet, errorMessage, cover, r, sheetName, formToFormulas);
            }
            parserFormulas.put(sheetName, formulaList);
        }
        return parserFormulas;
    }

    private Map<String, Integer> getAuditTypeMap() {
        HashMap<String, Integer> auditTypeMap = new HashMap<String, Integer>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            for (int i = 0; i < auditTypes.size(); ++i) {
                auditTypeMap.put(((AuditType)auditTypes.get(i)).getTitle(), ((AuditType)auditTypes.get(i)).getCode());
            }
        }
        catch (Exception e) {
            throw new FormulaResourceException("\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b\u5f02\u5e38!");
        }
        return auditTypeMap;
    }

    private Map<Integer, String> buildHeader(Sheet sheet) {
        HashMap<Integer, String> headerMap = new HashMap<Integer, String>();
        Row firstRow = sheet.getRow(0);
        int lastCellNum = firstRow.getLastCellNum();
        List<String> headers = Arrays.asList(HEADERS);
        for (int i = 0; i < lastCellNum; ++i) {
            String cellValue = this.getCellValue(firstRow.getCell(i));
            if (!StringUtils.hasText(cellValue)) continue;
            if (headers.contains(cellValue)) {
                String header = (String)headerMap.get(i);
                if (header == null) {
                    headerMap.put(i, cellValue);
                    continue;
                }
                throw new FormulaResourceException("\u5bfc\u5165Excel\u4e2d\u7684Sheet\u5b58\u5728\u5217\u8868\u540d\u201c" + cellValue + "\u201d\u91cd\u590d");
            }
            if (!BALANCEZBEXP.equals(cellValue)) continue;
            headerMap.put(i, cellValue);
        }
        Collection existHeader = headerMap.values();
        for (String header : headers) {
            if (existHeader.contains(header)) continue;
            throw new FormulaResourceException("\u5bfc\u5165Excel\u7684Sheet\u4e2d\u7f3a\u5c11\u201c" + header + "\u201d\u5c5e\u6027");
        }
        return headerMap;
    }

    protected String paraFormulaCode(DesignFormDefine form, int row) {
        StringBuilder formulaNum = new StringBuilder();
        for (int i = String.valueOf(row).length(); i < 4; ++i) {
            formulaNum.append("0");
        }
        String cValue = form == null ? "BJ" + formulaNum + row : form.getFormCode() + formulaNum + row;
        return cValue;
    }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        cellValue = cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : (cell.getCellType() == CellType.STRING ? String.valueOf(cell.getStringCellValue()) : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()) : (cell.getCellType() == CellType.FORMULA ? String.valueOf(cell.getCellFormula()) : (cell.getCellType() == CellType.BLANK ? "" : (cell.getCellType() == CellType.ERROR ? "\u975e\u6cd5\u5b57\u7b26" : "\u672a\u77e5\u7c7b\u578b")))));
        return cellValue;
    }

    private void allImportFormula(String formulaScheme, Map<String, List<FormulaExtDTO>> formulasSheetMap, String unit, List<Integer> levelCheck) {
        int count = 0;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new FormulaResourceException("\u5bfc\u5165\u516c\u5f0f\uff1a\u516c\u5f0f\u65b9\u6848\u672a\u627e\u5230");
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        ArrayList<FormulaDTO> formulaList = new ArrayList<FormulaDTO>();
        ArrayList deleteFormulas = new ArrayList();
        HashMap<String, String> formCodeMap = new HashMap<String, String>();
        List formDefineList = this.designTimeViewController.listFormByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        for (Object designFormDefine : formDefineList) {
            formCodeMap.put(designFormDefine.getFormCode(), designFormDefine.getKey());
        }
        if (!isEfdc) {
            formCodeMap.put(BJFORMULA, null);
        }
        boolean isNeedDelete = false;
        for (Map.Entry entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = (String)entry.getKey();
            List formulasMapValue = (List)entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode") || !formCodeMap.containsKey(sheetCode)) continue;
            List<DesignFormulaDefine> allSoftFormulasInForm = this.queryFormulaBySchemeAndForm(formulaScheme, (String)formCodeMap.get(sheetCode), unit);
            List canDeleteFormulas = allSoftFormulasInForm.stream().filter(item -> this.SameServeCode(item.getOwnerLevelAndId())).collect(Collectors.toList());
            count += allSoftFormulasInForm.size() - canDeleteFormulas.size();
            deleteFormulas.addAll(canDeleteFormulas);
            for (FormulaExtDTO formula : formulasMapValue) {
                String formulaCode = formula.getCode();
                List needUpdateFromulas = allSoftFormulasInForm.stream().filter(item -> item.getCode().equals(formulaCode)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(needUpdateFromulas) && !this.SameServeCode(((DesignFormulaDefine)needUpdateFromulas.get(0)).getOwnerLevelAndId())) continue;
                FormulaDTO formulaDTO = this.objectToFormulaObj(formula, null, false);
                formulaList.add(formulaDTO);
            }
            isNeedDelete = true;
        }
        if (!isNeedDelete) {
            throw new FormulaResourceException("\u6ca1\u6709\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u4e2d\u627e\u5230Sheet\u5bf9\u5e94\u7684\u8868\u5355");
        }
        if (!CollectionUtils.isEmpty(deleteFormulas)) {
            this.deleteFormulas(deleteFormulas.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        }
        if (count > 0) {
            int allCount = formulaList.size();
            levelCheck.add(count);
            levelCheck.add(allCount);
        }
        this.insertFormulas(formulaList);
    }

    private FormulaDTO objectToFormulaObj(FormulaExtDTO newFormula, DesignFormulaDefine oldFormula, boolean isDirty) {
        String formulaId;
        FormulaDTO formulaDTO = new FormulaDTO();
        formulaDTO.setCode(newFormula.getCode());
        if (isDirty && oldFormula != null) {
            formulaId = oldFormula.getKey();
            formulaDTO.setOrder(oldFormula.getOrder());
            formulaDTO.setOrdinal(oldFormula.getOrdinal());
        } else {
            formulaId = newFormula.getId();
            formulaDTO.setOrder(newFormula.getOrder());
            if (StringUtils.hasText(newFormula.getOrder())) {
                formulaDTO.setOrdinal(BigDecimal.valueOf(OrderUtils.stringToOrder(newFormula.getOrder())));
            }
        }
        formulaDTO.setKey(formulaId);
        formulaDTO.setExpression(newFormula.getFormula());
        int checkType = newFormula.getChecktype() == null ? 0 : newFormula.getChecktype();
        formulaDTO.setCheckType(checkType);
        formulaDTO.setDescription(newFormula.getDescription());
        formulaDTO.setUseCalculate(newFormula.isUseCalculate());
        formulaDTO.setUseCheck(newFormula.isUseCheck());
        formulaDTO.setUseBalance(newFormula.isUseBalance());
        formulaDTO.setBalanceZBExp(newFormula.getBalanceZBExp());
        if (StringUtils.hasText(newFormula.getFormulaSchemeKey())) {
            formulaDTO.setFormulaSchemeKey(newFormula.getFormulaSchemeKey());
        }
        formulaDTO.setFormKey(newFormula.getFormKey() == null ? null : newFormula.getFormKey());
        formulaDTO.setUnit(newFormula.getUnit());
        return formulaDTO;
    }

    private void addImportFormula(List<String> formIds, String formulaScheme, Map<String, List<FormulaExtDTO>> formulasSheetMap, List<Integer> levelCheck) {
        int count = 0;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaDesignTimeController.getFormulaScheme(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new FormulaResourceException("\u5bfc\u5165\u516c\u5f0f\uff1a\u516c\u5f0f\u65b9\u6848\u672a\u627e\u5230");
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        List<DesignFormulaDefine> formulas = this.queryFormulaByScheme(formulaScheme);
        Map<Object, Object> formulaCodeMap = new HashMap();
        if (!CollectionUtils.isEmpty(formulas)) {
            formulaCodeMap = formulas.stream().collect(Collectors.groupingBy(FormulaDefine::getCode));
        }
        ArrayList<FormulaDTO> newFormulaList = new ArrayList<FormulaDTO>();
        ArrayList<FormulaDTO> updateFormulaList = new ArrayList<FormulaDTO>();
        HashSet<String> formCodeSet = new HashSet<String>();
        for (String string : formIds) {
            DesignFormDefine formDefine = this.designTimeViewController.getForm(string);
            formCodeSet.add(formDefine.getFormCode());
        }
        if (!isEfdc) {
            formCodeSet.add(BJFORMULA);
        }
        for (Map.Entry entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = (String)entry.getKey();
            List formulasMapValue = (List)entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode")) continue;
            formCodeSet.add(sheetCode);
            for (FormulaExtDTO formula : formulasMapValue) {
                FormulaDTO formulaDTO;
                String formulaCode = formula.getCode();
                List existFormula = (List)formulaCodeMap.get(formulaCode);
                if (existFormula != null && existFormula.size() == 1) {
                    if (!this.SameServeCode(((DesignFormulaDefine)existFormula.get(0)).getOwnerLevelAndId())) {
                        ++count;
                        continue;
                    }
                    formulaDTO = this.objectToFormulaObj(formula, (DesignFormulaDefine)existFormula.get(0), true);
                    updateFormulaList.add(formulaDTO);
                    continue;
                }
                if (CollectionUtils.isEmpty(existFormula)) {
                    formulaDTO = this.objectToFormulaObj(formula, null, false);
                    newFormulaList.add(formulaDTO);
                    continue;
                }
                throw new FormulaResourceException("\u5bfc\u5165\u7684excel\u516c\u5f0f\u7f16\u53f7\u91cd\u590d");
            }
        }
        if (count > 0) {
            int allCount = newFormulaList.size() + updateFormulaList.size();
            levelCheck.add(count);
            levelCheck.add(allCount);
        }
        if (!CollectionUtils.isEmpty(newFormulaList)) {
            this.insertFormulas(newFormulaList);
        }
        if (!CollectionUtils.isEmpty(updateFormulaList)) {
            this.updateFormulas(updateFormulaList);
        }
    }

    private boolean SameServeCode(String ownerLevelAndId) {
        try {
            return this.serveCodeService.isSameServeCode(ownerLevelAndId);
        }
        catch (JQException e) {
            return false;
        }
    }

    private void getFormFormulasMap(Map<String, List<String>> formFormulasMap, List<FormulaDTO> formulas, Map<String, String> formulaCodeKeyMap) {
        for (FormulaDTO formula : formulas) {
            formulaCodeKeyMap.put(formula.getCode(), formula.getKey());
            List<String> formulaCodeList = formFormulasMap.get(formula.getFormKey());
            if (null == formulaCodeList) {
                formulaCodeList = new ArrayList<String>();
                formFormulasMap.put(formula.getFormKey(), formulaCodeList);
            }
            formulaCodeList.add(formula.getCode());
        }
    }

    private String getFormTitle(String formKey) {
        if ("BJ".equals(formKey)) {
            return BJFORMULA;
        }
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
        return formDefine.getFormCode() + " " + formDefine.getTitle();
    }

    private String getFormTitle(DesignFormDefine formDefine) {
        if (formDefine == null) {
            return "sheet";
        }
        return formDefine.getFormCode() + " " + formDefine.getTitle();
    }

    protected void configResponse(HttpServletResponse response, String title) {
        response.setContentType("Application/vnd.ms-excel;charset=UTF-8");
        try {
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(title, "utf-8"));
        }
        catch (UnsupportedEncodingException e) {
            response.setHeader("Content-Disposition", "attachment;filename=default.xlsx");
        }
    }

    private void checkFormulaCode(FormulaExtDTO excelFormula, Set<String> excelFormulaCodeSet, Set<String> formulaCodeSet, List<String> formulaRepeatCode, boolean isFullImport, int r, String sheetName, Map<String, List<String>> form_formulasMap) {
        if (isFullImport) {
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (formulaCodeSet.contains(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5df2\u6709\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        } else {
            Boolean bjFlag = sheetName.equals(BJFORMULA);
            String formKey = excelFormula.getFormKey();
            formulaCodeSet.clear();
            for (Map.Entry<String, List<String>> formulasFormKey : form_formulasMap.entrySet()) {
                List<String> formulaCodes;
                String formKeyByFormula = formulasFormKey.getKey();
                if (bjFlag.booleanValue() && "BJ".equals(formKeyByFormula) || formKeyByFormula != null && formKeyByFormula.equals(formKey) || (formulaCodes = formulasFormKey.getValue()) == null) continue;
                formulaCodeSet.addAll(formulaCodes);
            }
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (!formulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        }
    }

    private void convertToExcelEntity(List<FormulaDTO> formulaDTOS, List<ExcelEntity> datas, Class zlass) {
        boolean isEfdc = false;
        for (FormulaDTO formulaDTO : formulaDTOS) {
            ExcelEntity excelEntity;
            if (zlass.equals(FormulaExcelEntity.class)) {
                excelEntity = new FormulaExcelEntity();
            } else if (zlass.equals(EFDCFormulaExcelEntity.class)) {
                excelEntity = new EFDCFormulaExcelEntity();
                isEfdc = true;
            } else if (zlass.equals(BetweenFormulaExcelEntity.class)) {
                excelEntity = new BetweenFormulaExcelEntity();
            } else {
                throw new FormulaResourceException();
            }
            excelEntity.setNo(formulaDTO.getCode());
            excelEntity.setExpression(formulaDTO.getExpression());
            excelEntity.setDescription(formulaDTO.getDescription());
            if (!isEfdc) {
                String typeCal = "\u8fd0\u7b97\u516c\u5f0f";
                String typeCheck = "\u5ba1\u6838\u516c\u5f0f";
                String typeBalance = "\u5e73\u8861\u516c\u5f0f";
                boolean calculate = formulaDTO.getUseCalculate();
                boolean check = formulaDTO.getUseCheck();
                boolean balance = formulaDTO.getUseBalance();
                if (calculate && !check && !balance) {
                    excelEntity.setType(typeCal);
                } else if (!calculate && !check && balance) {
                    excelEntity.setType(typeBalance);
                } else if (!calculate && check && !balance) {
                    excelEntity.setType(typeCheck);
                } else if (calculate && !check && balance) {
                    excelEntity.setType(typeCal + ";" + typeBalance);
                } else if (!calculate && check && balance) {
                    excelEntity.setType(typeCheck + ";" + typeBalance);
                } else if (calculate && check && !balance) {
                    excelEntity.setType(typeCal + ";" + typeCheck);
                } else if (calculate && check && balance) {
                    excelEntity.setType(typeCal + ";" + typeCheck + ";" + typeBalance);
                } else if (!(calculate || check || balance)) {
                    excelEntity.setType("");
                }
                int checkType = formulaDTO.getCheckType();
                if (checkType != 0) {
                    List queryAllAuditType = null;
                    try {
                        queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
                        for (AuditType auditType : queryAllAuditType) {
                            if (checkType != auditType.getCode()) continue;
                            excelEntity.setAuditType(auditType.getTitle());
                        }
                    }
                    catch (Exception e) {
                        excelEntity.setAuditType("");
                    }
                }
                excelEntity.setAdjustIndicator(formulaDTO.getBalanceZBExp() != null ? formulaDTO.getBalanceZBExp() : "");
            }
            datas.add(excelEntity);
        }
    }

    private void getAllFormulaCodeSet(List<FormulaDTO> formulas, Set<String> excelFormCodeSet, Set<String> formulaCodeSet, Map<String, String> formulaCodeKeyMap) {
        if (CollectionUtils.isEmpty(formulas)) {
            return;
        }
        HashMap<String, Boolean> importFormMap = new HashMap<String, Boolean>();
        boolean hasBJ = excelFormCodeSet.contains(BJFORMULA);
        for (int i = 0; i < formulas.size(); ++i) {
            FormulaDTO formula = formulas.get(i);
            formulaCodeKeyMap.put(formula.getCode(), formula.getKey());
            String formKey = formula.getFormKey();
            String formulaCode = formula.getCode();
            if (StringUtils.isEmpty(formKey)) {
                if (hasBJ) continue;
                formulaCodeSet.add(formulaCode);
                continue;
            }
            Boolean isImportForm = (Boolean)importFormMap.get(formKey);
            if (isImportForm == null) {
                DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
                if (formDefine == null) {
                    isImportForm = true;
                    importFormMap.put(formKey, isImportForm);
                } else {
                    isImportForm = excelFormCodeSet.contains(formDefine.getFormCode());
                    importFormMap.put(formKey, isImportForm);
                }
            }
            if (isImportForm.booleanValue()) continue;
            formulaCodeSet.add(formulaCode);
        }
    }

    protected List<FormulaDTO> convertDto(List<DesignFormulaDefine> designFormulaDefines) {
        List<FormulaDTO> formulaDTOS = FormulaConvert.defineToDTOList(designFormulaDefines);
        formulaDTOS.forEach(x -> {
            if (Objects.isNull(x.getFormKey())) {
                x.setFormKey("BJ");
            }
        });
        return formulaDTOS;
    }

    public List<FormulaDTO> listFormulaByForm(FormulaListPM param) {
        String formulaSchemeKey = param.getFormulaSchemeKey();
        String formKey = param.getFormKey();
        if ("BJ".equals(formKey)) {
            formKey = null;
        }
        List conditions = this.formulaDesignTimeController.listFormulaConditionByScheme(formulaSchemeKey);
        Map<String, DesignFormulaCondition> conditionMap = conditions.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, f -> f));
        List conditionLinks = this.formulaDesignTimeController.listFormulaConditionLinkByScheme(formulaSchemeKey);
        Map map = conditionLinks.stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey, Collectors.mapping(FormulaConditionLink::getConditionKey, Collectors.toList())));
        List<DesignFormulaDefine> defines = this.queryFormulaBySchemeAndForm(formulaSchemeKey, formKey, null);
        List<FormulaDTO> formulaDTOS = FormulaConvert.defineToDTOList(defines);
        for (FormulaDTO formulaDTO : formulaDTOS) {
            List list = map.get(formulaDTO.getKey());
            if (CollectionUtils.isEmpty(list)) continue;
            for (String key : list) {
                if (!conditionMap.containsKey(key)) continue;
                formulaDTO.getConditions().add(FormulaConvert.convertCondition(conditionMap.get(key)));
            }
        }
        return formulaDTOS;
    }

    protected List<FormulaDTO> listFormulaBySchemeAndForm(FormulaDataExportPM exportPM) {
        FormulaListPM searchParam = new FormulaListPM();
        searchParam.setFormKey(exportPM.getFormKey());
        searchParam.setFormulaSchemeKey(exportPM.getFormulaSchemeKey());
        searchParam.setUnit(exportPM.getUnit());
        FormulaDataVO searchVO = this.searchFormulaData(searchParam);
        return searchVO.getRows();
    }

    protected String buildCode(String value, DesignFormDefine form, int row, String unit) {
        return StringUtils.hasLength(value) ? value : this.paraFormulaCode(form, row);
    }

    protected boolean validateCheckType(String type) {
        return true;
    }

    protected abstract List<FormulaDTO> listFormulaByScheme(String var1);

    protected abstract List<DesignFormulaDefine> queryFormulaByScheme(String var1);

    protected abstract List<DesignFormulaDefine> queryFormulaBySchemeAndForm(String var1, String var2, String var3);
}

