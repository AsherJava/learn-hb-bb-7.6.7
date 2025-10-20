/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.parse.PreProcessingFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataSetStrategy
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn
 *  com.jiuqi.nr.jtable.filter.RegionTabFilter
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.params.input.RegionFilterInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DataFormaterCache
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParamBuilder;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.parse.PreProcessingFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionQueryTabeStrategy;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Deprecated
@Component
public class SumHbFunction
extends PreProcessingFunction
implements INrFunction {
    private static final long serialVersionUID = 899333003095435298L;
    private final Logger logger = LoggerFactory.getLogger(SumHbFunction.class);
    public static final String FUNCTION_NAME = "SUMHB";

    public SumHbFunction() {
        this.parameters().add(new Parameter("value", 6, "\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("filter", 6, "\u6761\u4ef6"));
        this.parameters().add(new Parameter("tableName", 6, "\u62a5\u8868"));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u516c\u5f0f\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528SUMHBZB";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    private SumhbParam getSumhbParam(QueryContext queryContext, List<IASTNode> parameters) {
        SumhbParamBuilder sumhbParamBuilder;
        FormSchemeDefine formSchemeDefine;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            formSchemeDefine = env.getFormSchemeDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamsparseformdefine"));
        }
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String inputTableName = inputDataNameProvider.getTableNameByTaskId(formSchemeDefine.getTaskKey());
        try {
            sumhbParamBuilder = new SumhbParamBuilder(queryContext, FUNCTION_NAME, inputTableName);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return sumhbParamBuilder.build(parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        FormSchemeDefine formSchemeDefine;
        QueryContext queryContext = (QueryContext)context;
        SumhbParam sumhbParam = this.getSumhbParam((QueryContext)context, parameters);
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            formSchemeDefine = env.getFormSchemeDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamsparseformdefine"));
        }
        DimensionValueSet ds = queryContext.getCurrentMasterKey();
        String orgId = (String)ds.getValue("MD_ORG");
        String currencyId = (String)ds.getValue("MD_CURRENCY");
        if (queryContext.getCache().containsKey(SumHbService.getCacheKey(orgId, currencyId, sumhbParam))) {
            return queryContext.getCache().get(SumHbService.getCacheKey(orgId, currencyId, sumhbParam));
        }
        try {
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
            RunTimeAuthViewController runTimeViewController = (RunTimeAuthViewController)SpringContextUtils.getBean(RunTimeAuthViewController.class);
            IJtableParamService entityControlService = (IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class);
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formSchemeDefine.getKey());
            queryEnvironment.setRegionKey(sumhbParam.getReginId());
            IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.setFilterDataByAuthority(false);
            dataQuery.setDefaultGroupName(sumhbParam.getFormCode());
            dataQuery.setMasterKeys(new DimensionValueSet(queryContext.getCurrentMasterKey()));
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(formSchemeDefine.getTaskKey());
            jtableContext.setFormSchemeKey(formSchemeDefine.getKey());
            IRunTimeViewController paramsService = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            FormDefine formDefine = paramsService.queryFormByCodeInScheme(formSchemeDefine.getKey(), sumhbParam.getFormCode());
            jtableContext.setFormKey(formDefine.getKey());
            jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)queryContext.getCurrentMasterKey()));
            FloatRegionRelationEvn regionRelationEvn = new FloatRegionRelationEvn(entityControlService.getRegion(sumhbParam.getReginId()), jtableContext);
            if (StringUtils.hasText(sumhbParam.getFilter())) {
                regionRelationEvn.getRegionData().setFilterCondition(sumhbParam.getFilter().concat(" and (").concat(regionRelationEvn.getRegionData().getFilterCondition() + " )"));
            }
            RegionQueryInfo regionQueryInfo = this.initRegionQueryInfo(entityControlService, jtableContext, queryContext.getCurrentMasterKey(), regionRelationEvn);
            FloatRegionQueryTabeStrategy queryTabeStrategy = new FloatRegionQueryTabeStrategy(dataQuery, (AbstractRegionRelationEvn)regionRelationEvn, regionQueryInfo);
            DataFormaterCache dataFormater = new DataFormaterCache(jtableContext);
            FloatRegionDataSetStrategy dataSetStrategy = new FloatRegionDataSetStrategy((AbstractRegionRelationEvn)regionRelationEvn, (AbstractRegionQueryTableStrategy)queryTabeStrategy, dataFormater, regionQueryInfo);
            RegionDataSet regionDataSet = dataSetStrategy.getRegionDataSet();
            List list = (List)regionDataSet.getCells().get(sumhbParam.getReginId());
            if (sumhbParam.getFieldDefine() == null || ObjectUtils.isEmpty(sumhbParam.getFieldDefine().getID())) {
                return 0;
            }
            IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
            InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
            String inputTableId = inputDataNameProvider.getDataTableKeyByTaskId(formSchemeDefine.getTaskKey());
            String zbId = runtimeDataSchemeService.getDataFieldByTableKeyAndCode(inputTableId, sumhbParam.getFieldDefine().getCode()).getKey();
            List linksInFormByField = runTimeViewController.getLinksInRegionByField(sumhbParam.getReginId(), zbId);
            if (linksInFormByField == null || linksInFormByField.isEmpty()) {
                return 0;
            }
            double sumValue = 0.0;
            int matchedFieldIndex = list.indexOf(((DataLinkDefine)linksInFormByField.get(0)).getKey());
            if (matchedFieldIndex > 0) {
                List data = regionDataSet.getData();
                for (List rowData : data) {
                    Object dataObj = rowData.get(matchedFieldIndex);
                    String valueStr = String.valueOf(dataObj);
                    valueStr = ObjectUtils.isEmpty(valueStr) ? "0" : valueStr;
                    double value = 0.0;
                    try {
                        value = Double.parseDouble(valueStr);
                    }
                    catch (NumberFormatException e) {
                        this.logger.error("SUMHB\u6570\u503c\u7c7b\u578b\u8f6c\u6362\u5f02\u5e38\uff0c value\uff1a" + valueStr, e);
                    }
                    sumValue += value;
                }
            }
            return NumberUtils.round((double)sumValue, (int)sumhbParam.getFieldDefine().getDecimal());
        }
        catch (Exception e) {
            this.logger.error("SUMHB\u8fd0\u7b97\u62a5\u9519", e);
            return 0;
        }
    }

    private RegionQueryInfo initRegionQueryInfo(IJtableParamService entityControlService, JtableContext jtableContext, DimensionValueSet dimensionValueSet, FloatRegionRelationEvn regionRelationEvn) {
        List regionTabs;
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(jtableContext);
        RegionData region = regionRelationEvn.getRegionData();
        regionQueryInfo.setRegionKey(region.getKey());
        if (region != null && (regionQueryInfo.getFilterInfo() == null || StringUtils.isEmpty(regionQueryInfo.getFilterInfo().getFilterFormula())) && null != (regionTabs = entityControlService.getRegionTabs(region.getKey()))) {
            RegionTabFilter regionTabSettingFilter = new RegionTabFilter(jtableContext, dimensionValueSet);
            for (RegionTab regionTab : regionTabs) {
                if (!regionTabSettingFilter.accept(regionTab)) continue;
                RegionFilterInfo regionFilterInfo = regionQueryInfo.getFilterInfo() == null ? new RegionFilterInfo() : regionQueryInfo.getFilterInfo();
                regionQueryInfo.setFilterInfo(regionFilterInfo);
                regionFilterInfo.setFilterFormula(Arrays.asList(regionTab.getFilter()));
                regionFilterInfo.setCellQuerys(region.getCells());
                break;
            }
        }
        return regionQueryInfo;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }
}

