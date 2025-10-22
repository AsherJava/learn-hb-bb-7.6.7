/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.formtype.common.EntityUnitNatureGetter
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BGHZCheck
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 8984390485171152251L;
    private static final Logger logger = LogFactory.getLogger(BGHZCheck.class);
    private static final String TABLE_CODE = "Z14";
    private static final String FIELD_CODE = "QYDM";
    private static final String PREFIX = "BGHZ";

    public String name() {
        return "BGHZCheck";
    }

    public String title() {
        return "\u5355\u6237\u4f01\u4e1a\u7684\u65b0\u62a5\u56e0\u7d20\u662f\u5426\u4e3a\u201c\u5212\u8f6c\u201d\u6216\u201c\u5e76\u8d2d\u201d";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u5355\u6237\u4f01\u4e1a\u7684\u65b0\u62a5\u56e0\u7d20\u662f\u201c\u5212\u8f6c\u201d\u6216\u201c\u5e76\u8d2d\u201d");
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5176\u4ed6\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        if (iContext instanceof QueryContext) {
            Object varValue;
            QueryContext qContext = (QueryContext)iContext;
            com.jiuqi.np.dataengine.executors.ExecutorContext exeContext = qContext.getExeContext();
            FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(exeContext);
            String dw = formSchemeDefine.getDw();
            String dateTime = formSchemeDefine.getDateTime();
            IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
            DimensionValueSet currentMasterKey = qContext.getCurrentMasterKey();
            IEntityQuery iEntityQuery = entityDataService.newEntityQuery();
            DimensionValueSet masterKeys = new DimensionValueSet();
            String periodCode = currentMasterKey.getValue("DATATIME").toString();
            if (!StringUtils.isEmpty((String)periodCode)) {
                masterKeys.setValue("DATATIME", (Object)periodCode);
            }
            iEntityQuery.setMasterKeys(masterKeys);
            iEntityQuery.setEntityView(entityViewRunTimeController.buildEntityView(dw));
            IDataDefinitionRuntimeController runtimeController = exeContext.getRuntimeController();
            ExecutorContext context = new ExecutorContext(runtimeController);
            context.setPeriodView(dateTime);
            IEntityTable iEntityTable = null;
            try {
                iEntityTable = iEntityQuery.executeReader((IContext)context);
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
            String orgCode = currentMasterKey.getValue(1).toString();
            assert (iEntityTable != null);
            IEntityRow entityRow = iEntityTable.findByCode(orgCode);
            String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
            List<String> strings1 = Arrays.asList(parentsEntityKeyDataPath);
            HashSet<String> strings2 = new HashSet<String>(strings1);
            Map entityKeys = iEntityTable.findByEntityKeys(strings2);
            IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
            String topEntityKey = null;
            for (int i = parentsEntityKeyDataPath.length - 1; i >= 0; --i) {
                String entityKey = parentsEntityKeyDataPath[i];
                IEntityRow ientityRow = (IEntityRow)entityKeys.get(entityKey);
                EntityUnitNatureGetter entityFormTypeGetter = formTypeApplyService.getEntityFormTypeGetter(dw);
                UnitNature unitNature = entityFormTypeGetter.getUnitNature(ientityRow);
                if (unitNature != UnitNature.JTHZB) {
                    if (i != parentsEntityKeyDataPath.length - 1) break;
                    return true;
                }
                topEntityKey = entityKey;
            }
            Set<String> data = (varValue = qContext.getCache().get(PREFIX + topEntityKey)) != null ? (Set<String>)varValue : this.getData(qContext, exeContext, formSchemeDefine, currentMasterKey, topEntityKey);
            String currentData = this.getCurrentData(exeContext, dw, currentMasterKey.getValue(0).toString(), orgCode);
            if (StringUtils.isNotEmpty((String)currentData)) {
                return data.contains(currentData);
            }
        }
        return false;
    }

    private FormSchemeDefine getFormSchemeDefine(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) {
        IFmlExecEnvironment env = executorContext.getEnv();
        ReportFmlExecEnvironment reportEnv = null;
        if (env instanceof ReportFmlExecEnvironment) {
            reportEnv = (ReportFmlExecEnvironment)env;
        }
        FormSchemeDefine formSchemeDefine = null;
        if (reportEnv != null) {
            try {
                formSchemeDefine = reportEnv.getFormSchemeDefine();
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return formSchemeDefine;
    }

    private String getCurrentData(com.jiuqi.np.dataengine.executors.ExecutorContext exeContext, String dw, String period, String orgCode) {
        IDimensionProvider dimensionProvider = (IDimensionProvider)BeanUtil.getBean(IDimensionProvider.class);
        DimensionTable dimensionTable = dimensionProvider.getDimensionTableByEntityId(exeContext, dw, new PeriodWrapper(period), (Object)orgCode);
        DimensionRow rowByCode = dimensionTable.findRowByCode(orgCode);
        Object qydm = rowByCode.getValue(FIELD_CODE);
        if (qydm != null) {
            return qydm.toString();
        }
        return null;
    }

    private Set<String> getData(QueryContext qContext, com.jiuqi.np.dataengine.executors.ExecutorContext exeContext, FormSchemeDefine formSchemeDefine, DimensionValueSet currentMasterKey, String topEntityKey) {
        HashSet<String> result = new HashSet<String>();
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)exeContext.getEnv();
        IRunTimeViewController controller = env.getController();
        IReadonlyTable iReadonlyTable = null;
        try {
            FormDefine formDefine = controller.queryFormByCodeInScheme(formSchemeDefine.getKey(), TABLE_CODE);
            List allRegionsInForm = controller.getAllRegionsInForm(formDefine.getKey());
            Optional<DataRegionDefine> first = allRegionsInForm.stream().filter(a -> a.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE).findFirst();
            if (first.isPresent()) {
                DataRegionDefine dataRegionDefine = first.get();
                List allLinksInRegion = controller.getAllLinksInRegion(dataRegionDefine.getKey());
                for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
                    FieldDefine fieldDefine;
                    if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldDefine = controller.queryFieldDefine(dataLinkDefine.getLinkExpression())) == null || !fieldDefine.getCode().equals(FIELD_CODE)) continue;
                    IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                    IDataQuery iDataQuery = accessProvider.newDataQuery();
                    DimensionValueSet dimensionValueSet1 = new DimensionValueSet(currentMasterKey);
                    dimensionValueSet1.setValue(exeContext.getUnitDimension(), (Object)topEntityKey);
                    iDataQuery.setMasterKeys(dimensionValueSet1);
                    iDataQuery.addColumn(fieldDefine);
                    iReadonlyTable = iDataQuery.executeReader(exeContext);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (iReadonlyTable != null) {
            for (int i = 0; i < iReadonlyTable.getCount(); ++i) {
                IDataRow item = iReadonlyTable.getItem(i);
                String code = item.getAsString(0);
                result.add(code);
            }
            qContext.getCache().put(PREFIX + topEntityKey, result);
        }
        return result;
    }
}

