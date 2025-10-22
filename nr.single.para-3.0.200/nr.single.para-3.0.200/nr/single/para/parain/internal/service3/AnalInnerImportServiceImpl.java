/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.DimensionRange
 *  com.jiuqi.nr.definition.common.DimensionType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.LineType
 *  com.jiuqi.nr.definition.common.PeriodRangeType
 *  com.jiuqi.nr.definition.common.SumType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.analysis.ColAttribute
 *  com.jiuqi.nr.definition.facade.analysis.DimensionAttribute
 *  com.jiuqi.nr.definition.facade.analysis.DimensionConfig
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.facade.analysis.LineCaliber
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.ColAttributeImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.FloatListConfigImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.LineCaliberImpl
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.single.core.para.LinkTaskBean
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalCellFormulaItem
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalGetInfo
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalTableInfo
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalTableRegion
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalTableSet
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.DimensionRange;
import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.LineType;
import com.jiuqi.nr.definition.common.PeriodRangeType;
import com.jiuqi.nr.definition.common.SumType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.analysis.ColAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.ColAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.FloatListConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.LineCaliberImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalCellFormulaItem;
import com.jiuqi.nr.single.core.para.parser.anal.AnalGetInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableInfo;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableRegion;
import com.jiuqi.nr.single.core.para.parser.anal.AnalTableSet;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IAnalInnerImportService;
import nr.single.para.parain.service.IFormulaDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalInnerImportServiceImpl
implements IAnalInnerImportService {
    private static final Logger log = LoggerFactory.getLogger(AnalInnerImportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private IFormulaDesignTimeController formulaController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormulaDefineImportService formulaService;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @Override
    public void importAnalInner(TaskImportContext importAnalContext) throws Exception {
        DesignTaskDefine task = importAnalContext.getTaskDefine();
        if (task == null) {
            log.info("\u5d4c\u5165\u5206\u6790\u672a\u5bfc\u5165\u6210\u529f\uff01\u8bf7\u68c0\u67e5");
            return;
        }
        AnalParaInfo analParaInfo = importAnalContext.getParaInfo().getInnerAnalInfo();
        if (analParaInfo.getAnalTableList().size() > 0) {
            DesignAnalysisSchemeParamDefine analformParaDefine;
            try {
                ExecutorContext exeConext = this.formulaService.initContext(importAnalContext);
                importAnalContext.getVariableMap().put("InnerExeConext", exeConext);
            }
            catch (Exception ex) {
                log.error("\u4e2d\u95f4\u516c\u5f0f\u7ffb\u8bd1\u521d\u59cb\u5316\u51fa\u9519\uff1a" + ex.getMessage(), ex);
            }
            for (Object tableInfo : analParaInfo.getAnalTableList().values()) {
                DesignFormDefine formDefine = this.viewController.queryFormByCodeInFormScheme(importAnalContext.getFormSchemeKey(), tableInfo.getRepInfo().getCode());
                if (null == formDefine) continue;
                String formKey = formDefine.getKey();
                DesignAnalysisFormParamDefine analForm = this.viewController.queryAnalysisFormParamDefine(formKey);
                if (analForm == null) {
                    analForm = new AnalysisFormParamDefineImpl();
                }
                this.setAnalFormDefineAttr(importAnalContext, (AnalTableInfo)tableInfo, analForm);
                this.setAnalFormOtherAttr(importAnalContext, (AnalTableInfo)tableInfo, analForm);
                this.viewController.updataAnalysisFormParamDefine(formKey, analForm);
                log.info("\u5d4c\u5165\u5206\u6790\u5bfc\u5165\uff1a" + formDefine.getFormCode());
            }
            List formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(importAnalContext.getFormSchemeKey());
            for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
                if (!"\u5206\u6790\u53d6\u6570\u516c\u5f0f".equalsIgnoreCase(formulaScheme.getTitle())) continue;
                formulaScheme.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_PICKNUM);
                formulaScheme.setDefault(true);
                this.formulaController.updateFormulaSchemeDefine(formulaScheme);
                log.info("\u5d4c\u5165\u5206\u6790\u5bfc\u5165\uff1a\u66f4\u65b0\u53d6\u6570\u516c\u5f0f\u65b9\u6848," + formulaScheme.getTitle());
                break;
            }
            if ((analformParaDefine = this.viewController.queryAnalysisSchemeParamDefine(importAnalContext.getFormSchemeKey())) == null) {
                analformParaDefine = new AnalysisSchemeParamDefineImpl();
            }
            if (analParaInfo.getAnalGetInfo() != null) {
                AnalGetInfo getInfo = analParaInfo.getAnalGetInfo();
                analformParaDefine.setCondition(getInfo.getSourceUnitSelect());
                analformParaDefine.setFunctionName(getInfo.getMenuCaption());
                if (getInfo.getSourceTask() > 0) {
                    List list = this.viewController.queryLinksByCurrentFormScheme(importAnalContext.getFormSchemeKey());
                    String curLinkTaskKey = null;
                    String curLinkFormSchemeKey = null;
                    List taskLinkList = importAnalContext.getParaInfo().getTaskLinkList();
                    block4: for (LinkTaskBean linkTask : taskLinkList) {
                        if (!String.valueOf(getInfo.getSourceTask()).equalsIgnoreCase(linkTask.getLinkNumber())) continue;
                        for (DesignTaskLinkDefine netlink : list) {
                            if (!linkTask.getLinkNumber().equalsIgnoreCase(netlink.getLinkAlias())) continue;
                            curLinkFormSchemeKey = netlink.getRelatedFormSchemeKey();
                            curLinkTaskKey = netlink.getRelatedTaskKey();
                            break block4;
                        }
                    }
                    List<DimensionInfo> analDims = null;
                    if (StringUtils.isNotEmpty(curLinkFormSchemeKey)) {
                        DesignTaskDefine netLinkTask = this.viewController.queryTaskDefine(curLinkTaskKey);
                        DesignFormSchemeDefine netLinkformScheme = this.viewController.queryFormSchemeDefine(curLinkFormSchemeKey);
                        analDims = this.getDimensionConfigObjByAnal(importAnalContext, netLinkTask, netLinkformScheme);
                        for (DimensionInfo dim : analDims) {
                            DimensionAttribute attr;
                            if (dim.getType() == DimensionType.ENTITY_VERSION) continue;
                            if (dim.getType() == DimensionType.ENTITY) {
                                attr = dim.getConfig();
                                attr.setUnitRange(DimensionRange.CONDITION);
                                attr.setCondition(getInfo.getSourceUnitSelect());
                                continue;
                            }
                            if (dim.getType() != DimensionType.ENTITY_PERIOD) continue;
                            attr = dim.getConfig();
                            attr.setPeriodRangeType(PeriodRangeType.NONE);
                        }
                    }
                    analformParaDefine.setSrcDims(analDims);
                    analformParaDefine.setSrcTaskKey(curLinkTaskKey);
                    analformParaDefine.setSrcFormSchemeKey(curLinkFormSchemeKey);
                }
            } else {
                DesignFormSchemeDefine formScheme = this.viewController.queryFormSchemeDefine(importAnalContext.getFormSchemeKey());
                DesignTaskDefine taskDefine = this.viewController.queryTaskDefine(formScheme.getTaskKey());
                List<DimensionInfo> analDims = this.getDimensionConfigObjByAnal(importAnalContext, taskDefine, formScheme);
                for (DimensionInfo dim : analDims) {
                    DimensionAttribute attr;
                    if (dim.getType() == DimensionType.ENTITY_VERSION) continue;
                    if (dim.getType() == DimensionType.ENTITY) {
                        attr = dim.getConfig();
                        attr.setUnitRange(DimensionRange.ALL_CHILDREN);
                        continue;
                    }
                    if (dim.getType() != DimensionType.ENTITY_PERIOD) continue;
                    attr = dim.getConfig();
                    attr.setPeriodRangeType(PeriodRangeType.DEST);
                }
                analformParaDefine.setCondition(null);
                analformParaDefine.setFunctionName("\u5d4c\u5165\u5206\u6790");
                analformParaDefine.setSrcDims(analDims);
                analformParaDefine.setSrcTaskKey(taskDefine.getKey());
                analformParaDefine.setSrcFormSchemeKey(importAnalContext.getFormSchemeKey());
                analformParaDefine.setAutoAnalysis(false);
            }
            this.viewController.updataAnalysisSchemeParamDefine(importAnalContext.getFormSchemeKey(), analformParaDefine);
        }
    }

    private void setAnalFormDefineAttr(TaskImportContext importAnalContext, AnalTableInfo analTable, DesignAnalysisFormParamDefine analForm) throws JQException {
        DesignTaskDefine task = importAnalContext.getTaskDefine();
        analForm.setFunctionName(analTable.getShowCaption());
        analForm.setAutoAnalysis(analTable.isAutoExecute());
        analForm.setFunctionCondition(this.transFormula(importAnalContext, analTable.getRepInfo().getCode(), analTable.getShowCondition()));
        DimensionConfigImpl obj = new DimensionConfigImpl();
        DesignFormSchemeDefine formScheme = null;
        if (importAnalContext.getSchemeInfoCache() != null && importAnalContext.getSchemeInfoCache().getFormScheme() != null) {
            formScheme = importAnalContext.getSchemeInfoCache().getFormScheme();
        }
        List<DimensionInfo> analDims = this.getDimensionConfigObjByAnal(importAnalContext, task, formScheme);
        for (DimensionInfo dim : analDims) {
            DimensionAttribute attr;
            if (dim.getType() == DimensionType.ENTITY_VERSION) continue;
            if (dim.getType() == DimensionType.ENTITY) {
                attr = dim.getConfig();
                attr.setUnitRange(DimensionRange.ALL_CHILDREN);
                if ("Brothers".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.BROTHERS);
                    continue;
                }
                if ("Children".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.CHILDREN);
                    continue;
                }
                if ("SubTree".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.ALL_CHILDREN);
                    continue;
                }
                if ("All".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.NONE);
                    continue;
                }
                if ("Condition".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.CONDITION);
                    attr.setCondition(this.transFormula(importAnalContext, analTable.getRepInfo().getCode(), analTable.getFindCondition()));
                    continue;
                }
                if ("Selection".equalsIgnoreCase(analTable.getUnitFindMode())) {
                    attr.setUnitRange(DimensionRange.SELECTION);
                    continue;
                }
                if (!"Self".equalsIgnoreCase(analTable.getUnitFindMode())) continue;
                attr.setUnitRange(DimensionRange.ITSELF);
                continue;
            }
            if (dim.getType() != DimensionType.ENTITY_PERIOD) continue;
            attr = dim.getConfig();
            attr.setUnitRange(DimensionRange.ITSELF);
        }
        obj.setDestDims(analDims);
        obj.setSrcDims(analDims);
        analForm.setDimensionConfig((DimensionConfig)obj);
    }

    private List<DimensionInfo> getDimensionConfigObjByAnal(TaskImportContext importAnalContext, DesignTaskDefine task, DesignFormSchemeDefine formScheme) throws JQException {
        ArrayList<DimensionInfo> dims = new ArrayList<DimensionInfo>();
        PeriodType periodType = task.getPeriodType();
        String fromPeriod = task.getFromPeriod();
        String toPeriod = task.getToPeriod();
        String masterEntitiesKey = task.getMasterEntitiesKey();
        if (formScheme != null) {
            if (StringUtils.isNotEmpty((String)formScheme.getMasterEntitiesKey())) {
                masterEntitiesKey = formScheme.getMasterEntitiesKey();
            }
            if (StringUtils.isNotEmpty((String)formScheme.getFromPeriod())) {
                fromPeriod = formScheme.getFromPeriod();
            }
            if (StringUtils.isNotEmpty((String)formScheme.getToPeriod())) {
                toPeriod = formScheme.getToPeriod();
            }
            if (formScheme.getPeriodType() != null && formScheme.getPeriodType() != PeriodType.DEFAULT) {
                periodType = formScheme.getPeriodType();
            }
        }
        if (StringUtils.isEmpty((String)toPeriod)) {
            toPeriod = fromPeriod;
        }
        List dataSchemeDims = this.dataSchemeService.getDataSchemeDimension(importAnalContext.getDataSchemeKey());
        DesignDataDimension unitDim = null;
        for (DesignDataDimension aDim : dataSchemeDims) {
            if (aDim.getDimensionType() != com.jiuqi.nr.datascheme.api.type.DimensionType.UNIT) continue;
            unitDim = aDim;
        }
        if (StringUtils.isNotEmpty((String)masterEntitiesKey)) {
            String[] entityKeys;
            for (String entityId : entityKeys = this.paraImportService.getBizKeyFieldsID(masterEntitiesKey)) {
                if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(entityId)) {
                    IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(entityId);
                    DimensionInfoImpl periodDim = new DimensionInfoImpl();
                    periodDim.setKey(periodEntity.getKey());
                    periodDim.setCode(periodEntity.getCode());
                    periodDim.setName(periodEntity.getTitle());
                    periodDim.setTitle(periodEntity.getTitle());
                    periodDim.setType(DimensionType.ENTITY_PERIOD);
                    periodDim.setViewKey(entityId);
                    DimensionAttribute config = periodDim.getConfig();
                    if (periodType.type() == PeriodType.DEFAULT.type()) {
                        config.setPeriodType(PeriodType.YEAR.type());
                    } else {
                        config.setPeriodType(periodType.type());
                    }
                    config.setPeriodRange(fromPeriod + "-" + toPeriod);
                    dims.add((DimensionInfo)periodDim);
                    continue;
                }
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                if (entityDefine == null) continue;
                DimensionInfo dim = this.getDimensionInfo(entityDefine, periodType, fromPeriod, toPeriod);
                dim.setViewKey(null);
                if (unitDim != null && StringUtils.isNotEmpty((String)unitDim.getDimKey()) && unitDim.getDimKey().equalsIgnoreCase(dim.getConfig().getLinkEntityKey())) {
                    dim.setType(DimensionType.ENTITY);
                }
                dims.add(dim);
            }
        }
        return dims;
    }

    private DimensionInfo getDimensionInfo(IEntityDefine entityDefine, PeriodType periodType, String fromPeriod, String toPeriod) {
        DimensionInfoImpl dim = new DimensionInfoImpl();
        IEntityDefine t = entityDefine;
        if (t != null) {
            dim.setKey(t.getId());
            dim.setCode(t.getCode());
            dim.setName(t.getTitle());
            dim.setTitle(t.getTitle());
            dim.setType(DimensionType.ENTITY_VERSION);
            DimensionAttribute config = dim.getConfig();
            config.setLinkEntityKey(dim.getKey());
            dim.setViewKey(dim.getKey());
        }
        return dim;
    }

    private void setAnalFormOtherAttr(TaskImportContext importAnalContext, AnalTableInfo tableInfo, DesignAnalysisFormParamDefine analForm) throws JQException {
        DesignFormDefine formDefine;
        String fileFlag = importAnalContext.getDataScheme().getPrefix();
        ArrayList<ColAttribute> colAttributeList = new ArrayList<ColAttribute>();
        this.setColColAttribute(importAnalContext, tableInfo, colAttributeList);
        analForm.setColAttribute(colAttributeList);
        ArrayList<String> coditions = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)tableInfo.getFilter())) {
            coditions.add(tableInfo.getFilter());
        }
        for (AnalTableSet parent = tableInfo.getParent(); parent != null; parent = parent.getParent()) {
            if (!StringUtils.isNotEmpty((String)parent.getFilter())) continue;
            coditions.add(parent.getFilter());
        }
        String formContition = null;
        if (coditions.size() == 1) {
            formContition = (String)coditions.get(0);
        } else if (coditions.size() > 1) {
            for (int i = 0; i < coditions.size(); ++i) {
                formContition = i == 0 ? String.format("(%s)", coditions.get(i)) : String.format("%s and (%s)", formContition, coditions.get(i));
            }
        }
        analForm.setConditionFormula(this.transFormula(importAnalContext, tableInfo.getRepInfo().getCode(), formContition));
        List<LineCaliber> lineCaliberList = null;
        if (tableInfo.getColConditions().size() > 0 || tableInfo.getRowConditions().size() > 0) {
            try {
                lineCaliberList = this.setCaliberTableObject(importAnalContext, tableInfo, fileFlag);
            }
            catch (Exception ex) {
                log.info("\u53e3\u5f84\u5bfc\u5165\u51fa\u9519\uff1a" + tableInfo.getRepInfo().getCode());
                Log.error((Exception)ex);
            }
        } else {
            lineCaliberList = new ArrayList<LineCaliber>();
        }
        analForm.setLineCalibers(lineCaliberList);
        if (tableInfo.getRepInfo().isFloatTable()) {
            analForm.setFirstDimensionFloat(true);
            analForm.setShowAllChild(true);
        }
        if (null != (formDefine = this.viewController.queryFormByCodeInFormScheme(importAnalContext.getFormSchemeKey(), tableInfo.getRepInfo().getCode())) && tableInfo.getRegionInfos().size() > 0 && formDefine.getFormType() == FormType.FORM_TYPE_FLOAT) {
            this.setFloatRegionInfo(importAnalContext, tableInfo, formDefine, analForm);
        }
    }

    private void setFloatRegionInfo(TaskImportContext importAnalContext, AnalTableInfo tableInfo, DesignFormDefine formDefine, DesignAnalysisFormParamDefine analForm) {
        HashMap<String, AnalCellFormulaItem> fieldFormulaMap = new HashMap<String, AnalCellFormulaItem>();
        for (AnalCellFormulaItem formula : tableInfo.getFetchFormulas()) {
            fieldFormulaMap.put(formula.getFormula(), formula);
        }
        ArrayList<FloatListConfigImpl> floatListSettings = new ArrayList<FloatListConfigImpl>();
        analForm.setFloatListSettings(floatListSettings);
        List regionDefines = this.viewController.getAllRegionsInForm(formDefine.getKey());
        for (DesignDataRegionDefine regionDefine : regionDefines) {
            int floatingRow = 0;
            if (DataRegionKind.DATA_REGION_COLUMN_LIST == regionDefine.getRegionKind()) {
                floatingRow = regionDefine.getRegionLeft();
            } else if (DataRegionKind.DATA_REGION_ROW_LIST == regionDefine.getRegionKind()) {
                floatingRow = regionDefine.getRegionTop();
            }
            if (!tableInfo.getRegionInfos().containsKey(String.valueOf(floatingRow))) continue;
            AnalTableRegion singleRegionInfo = (AnalTableRegion)tableInfo.getRegionInfos().get(String.valueOf(floatingRow));
            FloatListConfigImpl floatObj = new FloatListConfigImpl();
            floatObj.setDataRegionKey(regionDefine.getKey());
            floatObj.setListCondition(this.transFormula(importAnalContext, tableInfo.getRepInfo().getCode(), singleRegionInfo.getListCondition()));
            floatObj.setListFilter(this.transFormula(importAnalContext, tableInfo.getRepInfo().getCode(), singleRegionInfo.getListFilter()));
            floatObj.setMaxRowCount(singleRegionInfo.getMaxRowCount());
            floatObj.setSortFields(singleRegionInfo.getSortFields());
            floatObj.setSortFlags(singleRegionInfo.getSortFlags());
            floatObj.setClassifyWidths(singleRegionInfo.getClassifyWidths());
            floatObj.setClassifySumOnly(singleRegionInfo.isClassifySumOnly());
            floatObj.setClassifyFields(singleRegionInfo.getClassifyFields());
            ArrayList<String> sumExpressions = new ArrayList<String>();
            if (singleRegionInfo.getStatFormulas() != null) {
                for (AnalCellFormulaItem formula : singleRegionInfo.getStatFormulas()) {
                    String sumExpression = this.transFormula(importAnalContext, tableInfo.getRepInfo().getCode(), formula.getOldFormula());
                    sumExpressions.add(sumExpression);
                }
            }
            floatObj.setSumExpressions(sumExpressions);
            floatListSettings.add(floatObj);
        }
    }

    private List<LineCaliber> setCaliberTableObject(TaskImportContext importAnalContext, AnalTableInfo tableInfo, String fileFlag) throws Exception {
        ArrayList<LineCaliber> lineCaliberList = new ArrayList<LineCaliber>();
        if (tableInfo.getColConditions().size() > 0) {
            this.saveAllCaliberItems(importAnalContext, tableInfo, lineCaliberList, true);
        }
        if (tableInfo.getRowConditions().size() > 0) {
            this.saveAllCaliberItems(importAnalContext, tableInfo, lineCaliberList, false);
        }
        return lineCaliberList;
    }

    private void setColColAttribute(TaskImportContext importAnalContext, AnalTableInfo tableInfo, List<ColAttribute> colAttributeList) {
        DesignFormSchemeDefine formScheme = importAnalContext.getSchemeInfoCache().getFormScheme();
        if (tableInfo.getRepInfo().isFloatTable()) {
            for (AnalCellFormulaItem colcell : tableInfo.getFetchFormulas()) {
                DesignFieldDefine fieldDefine;
                ParaInfo basePara;
                Map baseRepDic;
                String fieldExp;
                ColAttributeImpl colAttr = new ColAttributeImpl();
                colAttr.setColNum(colcell.getPosX());
                colAttr.setFiledTitle(colcell.getTitle());
                colAttr.setSumType(SumType.SUM);
                colAttr.setFieldKey(null);
                String fieldCode = fieldExp = colcell.getFormula();
                String tableCode = "";
                int id1 = fieldExp.indexOf("[");
                int id2 = fieldExp.indexOf("]");
                if (id1 > 0 || id2 > 0) {
                    tableCode = fieldExp.substring(0, id1);
                    fieldCode = fieldExp.substring(id1 + 1, id2);
                }
                if (StringUtils.isEmpty((String)tableCode)) {
                    tableCode = "FMDM";
                }
                if ((baseRepDic = (basePara = importAnalContext.getParaInfo()).getRepInfoCodeList()).containsKey(tableCode)) {
                    RepInfo baseRep = (RepInfo)baseRepDic.get(tableCode);
                    int idx = fieldCode.indexOf(",");
                    if (idx > 0) {
                        String posx = fieldCode.substring(0, idx);
                        String string = fieldCode.substring(idx + 1, fieldCode.length());
                    } else {
                        int n = baseRep.getDefs().fieldNameToIndex(fieldCode);
                    }
                }
                if ((fieldDefine = this.findFieldByCode(importAnalContext, formScheme, tableCode, fieldCode)) == null) continue;
                colAttr.setFieldKey(fieldDefine.getKey());
                colAttributeList.add((ColAttribute)colAttr);
                colcell.setNetSourceFlag(fieldDefine.getKey());
            }
        }
    }

    private void saveAllCaliberItems(TaskImportContext importAnalContext, AnalTableInfo tableInfo, List<LineCaliber> lineCaliberList, boolean isCol) throws JQException {
        if (isCol) {
            this.addCaliberItemToList(importAnalContext, tableInfo, tableInfo.getColConditions(), isCol, lineCaliberList);
        } else {
            this.addCaliberItemToList(importAnalContext, tableInfo, tableInfo.getRowConditions(), isCol, lineCaliberList);
        }
    }

    private void addCaliberItemToList(TaskImportContext importAnalContext, AnalTableInfo tableInfo, List<AnalCellFormulaItem> formulaItems, boolean isCol, List<LineCaliber> lineCaliberList) {
        ExecutorContext exeConext = null;
        if (importAnalContext.getVariableMap().containsKey("InnerExeConext")) {
            exeConext = (ExecutorContext)importAnalContext.getVariableMap().get("InnerExeConext");
        }
        for (AnalCellFormulaItem colItem : formulaItems) {
            String aItemCode1 = String.valueOf(colItem.getPosX());
            String aItemCode = "C" + "00000".substring(0, 5 - aItemCode1.length()) + aItemCode1;
            if (!isCol) {
                aItemCode1 = String.valueOf(colItem.getPosY());
                aItemCode = "R" + "00000".substring(0, 5 - aItemCode1.length()) + aItemCode1;
            }
            String formula = colItem.getFormula();
            if (exeConext != null) {
                try {
                    formula = this.transFormula(exeConext, importAnalContext.getFormSchemeKey(), tableInfo.getRepInfo().getCode(), formula);
                }
                catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
            LineCaliberImpl lineCaliberObj = new LineCaliberImpl();
            lineCaliberList.add((LineCaliber)lineCaliberObj);
            if (isCol) {
                lineCaliberObj.setType(LineType.COL);
                lineCaliberObj.setColNumber(colItem.getPosX());
            } else {
                lineCaliberObj.setType(LineType.ROW);
                lineCaliberObj.setLineNumber(colItem.getPosY());
            }
            lineCaliberObj.setCondition(formula);
        }
    }

    private String transFormula(TaskImportContext importAnalContext, String formCode, String formula) {
        ExecutorContext exeConext = null;
        if (importAnalContext.getVariableMap().containsKey("InnerExeConext")) {
            exeConext = (ExecutorContext)importAnalContext.getVariableMap().get("InnerExeConext");
        }
        String newFormula = formula;
        if (exeConext != null && StringUtils.isNotEmpty((String)formula)) {
            try {
                newFormula = this.transFormula(exeConext, importAnalContext.getFormSchemeKey(), formCode, formula);
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return newFormula;
    }

    private String transFormula(ExecutorContext context, String formSchemeKey, String formCode, String formula) throws Exception {
        String expression = formula;
        if (context == null) {
            return expression;
        }
        try {
            expression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)formula, (String)formCode, (DataEngineConsts.FormulaShowType)DataEngineConsts.FormulaShowType.JQ);
        }
        catch (Exception e) {
            log.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38\uff0c" + formCode + "," + formula, e);
        }
        return expression;
    }

    private DesignFieldDefine findFieldByCode(TaskImportContext importContext, DesignFormSchemeDefine formScheme, String tableCode, String fieldCode) {
        DesignFieldDefine fieldDefine = null;
        String curRegionKey = null;
        try {
            block16: {
                List regions;
                DesignFormDefine formDefine;
                block18: {
                    block17: {
                        DesignDataLinkDefine link;
                        formDefine = this.viewController.queryFormByCodeInFormScheme(formScheme.getKey(), tableCode);
                        regions = null;
                        if (null != formDefine) {
                            regions = this.viewController.getAllRegionsInForm(formDefine.getKey());
                        }
                        String tableKey = null;
                        if (null != importContext.getEntityRunTimeTableKey() && (StringUtils.isEmpty((String)tableCode) || "FMDM".equalsIgnoreCase(tableCode) || "CSFMDM".equalsIgnoreCase(tableCode))) {
                            tableKey = importContext.getEntityRunTimeTableKey();
                            fieldDefine = this.dataController.queryFieldDefineByCodeInTable(fieldCode, tableKey);
                            if (null != regions && regions.size() > 0) {
                                curRegionKey = ((DesignDataRegionDefine)regions.get(0)).getKey();
                            }
                        }
                        if (null != fieldDefine) break block16;
                        int idx = fieldCode.indexOf(",");
                        if (idx <= 0) break block17;
                        String posx = fieldCode.substring(0, idx);
                        String posy = fieldCode.substring(idx + 1, fieldCode.length());
                        if (null == formDefine || null == (link = this.viewController.queryDataLinkDefineByColRow(formDefine.getKey(), Integer.parseInt(posy), Integer.parseInt(posx)))) break block16;
                        fieldDefine = this.dataController.queryFieldDefine(link.getLinkExpression());
                        curRegionKey = link.getRegionKey();
                        break block16;
                    }
                    DesignTableDefine tableDefine = this.dataController.queryTableDefinesByCode(formScheme.getFilePrefix() + '_' + tableCode);
                    if (null == tableDefine) break block18;
                    fieldDefine = this.dataController.queryFieldDefineByCodeInTable(fieldCode, tableDefine.getKey());
                    if (null == regions || regions.size() <= 0) break block16;
                    curRegionKey = ((DesignDataRegionDefine)regions.get(0)).getKey();
                    break block16;
                }
                if (null != formDefine) {
                    HashMap<String, String> TableMap = new HashMap<String, String>();
                    boolean hasFind = false;
                    for (DataRegionDefine region : regions) {
                        List linkfields = this.viewController.getAllFieldsByLinksInRegion(region.getKey());
                        for (FieldDefine field : linkfields) {
                            if (null != field && null != field.getOwnerTableKey() && !TableMap.containsKey(field.getOwnerTableKey())) {
                                fieldDefine = this.dataController.queryFieldDefineByCodeInTable(fieldCode, field.getOwnerTableKey());
                                boolean bl = hasFind = null != fieldDefine;
                            }
                            if (hasFind) {
                                curRegionKey = region.getKey();
                                break;
                            }
                            if (field == null) continue;
                            TableMap.put(field.getOwnerTableKey(), "");
                        }
                        if (!hasFind) continue;
                        break;
                    }
                }
            }
            if (null != fieldDefine) {
                boolean isErrRef = false;
                if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                    IEntityModel entityMode = this.entityMetaService.getEntityModel(fieldDefine.getEntityKey());
                    IEntityAttribute ref = entityMode.getBizKeyField();
                    boolean bl = isErrRef = ref == null;
                    while (null != ref && !isErrRef && StringUtils.isNotEmpty((String)ref.getReferTableID())) {
                        IEntityModel entityMode2 = this.entityMetaService.getEntityModel(ref.getReferTableID());
                        IEntityAttribute field = entityMode2.getBizKeyField();
                        if (StringUtils.isNotEmpty((String)field.getReferTableID())) {
                            IEntityModel parentEntityMode = this.entityMetaService.getEntityModel(field.getReferTableID());
                            ref = parentEntityMode.getBizKeyField();
                        } else {
                            ref = null;
                        }
                        isErrRef = ref == null;
                    }
                    if (isErrRef) {
                        fieldDefine = null;
                    }
                }
            }
        }
        catch (Exception ex) {
            Log.error((Exception)ex);
        }
        return fieldDefine;
    }
}

