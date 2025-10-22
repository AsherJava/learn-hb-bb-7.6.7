/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.nr.impl.function.NrUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.nr.impl.function.NrUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

abstract class BaseUnitFunction
extends Function
implements IFunctionCache,
INrFunction,
IGcFunction {
    private static final long serialVersionUID = 1L;
    private static ThreadLocal<Map<ArrayKey, Object>> zbCode2ValueMapLocal = new ThreadLocal();
    private static Logger logger = LoggerFactory.getLogger(BaseUnitFunction.class);
    @Lazy
    @Autowired
    private transient IDataDefinitionRuntimeController runtimeController;
    @Lazy
    @Autowired
    private transient InputDataDao inputDataDao;
    @Lazy
    @Autowired
    private InputDataNameProvider inputDataNameProvider;

    public BaseUnitFunction() {
        this.parameters().add(new Parameter("zbcode", 6, "\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("offsetTerm", 6, "\u65f6\u671f\u504f\u79fb(-1N:\u4e0a\u4e00\u5e74 -1:\u4e0a\u4e00\u671f \u4e0d\u5199\u9ed8\u8ba4\u5f53\u524d\u671f)", true));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        QueryContext queryContext = (QueryContext)context;
        String unitId = this.getUnitId(queryContext);
        try {
            Object value;
            Object zbCode = parameters.get(0).evaluate((IContext)queryContext);
            if (StringUtils.isEmpty((String)unitId)) {
                return this.getDefaultVal((String)zbCode);
            }
            Map<ArrayKey, Object> zbCode2ValueMap = zbCode2ValueMapLocal.get();
            ArrayKey key = new ArrayKey(new Object[]{unitId, zbCode});
            if (null != zbCode2ValueMap && zbCode2ValueMap.containsKey(key)) {
                return zbCode2ValueMap.get(key);
            }
            if (((String)zbCode).startsWith("MD_ORG")) {
                return this.getOrgFiledValue((String)zbCode, unitId, (String)queryContext.getCurrentMasterKey().getValue("DATATIME"));
            }
            DimensionValueSet ds = this.calcDimensionValueSet(queryContext.getCurrentMasterKey(), parameters, unitId);
            AbstractData abstractData = NrTool.getZbValue((DimensionValueSet)ds, (String)((String)zbCode));
            Object object = value = null == abstractData || null == abstractData.getAsObject() ? this.getDefaultVal((String)zbCode) : abstractData.getAsObject();
            if (null != zbCode2ValueMap) {
                zbCode2ValueMap.put(key, value);
            }
            return value;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5355\u4f4d\u7684\u6307\u6807\u6570\u636e\u51fa\u9519", e);
            return 0.0;
        }
    }

    private Object getOrgFiledValue(String zbCode, String unitId, String periodStr) {
        String regex = "(.+)\\[(.+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zbCode);
        String tableName = "";
        String filed = "";
        if (matcher.find()) {
            tableName = matcher.group(1).trim();
            filed = matcher.group(2).trim();
        }
        OrgToJsonVO mdOrgVo = GcOrgMangerCenterTool.getInstance((String)tableName, (String)periodStr).getOrgByCode(unitId);
        return mdOrgVo.getFieldValue(filed);
    }

    private Object getDefaultVal(String zbCode) throws Exception {
        ArrayKey zbCodeArrayKey = NrUtils.parseZbCode((String)zbCode);
        TableDefine tableDefine = this.runtimeController.queryTableDefineByCode((String)zbCodeArrayKey.get(0));
        if (null == tableDefine) {
            return 0.0;
        }
        FieldDefine define = this.runtimeController.queryFieldByCodeInTable((String)zbCodeArrayKey.get(1), tableDefine.getKey());
        if (null == define) {
            return 0.0;
        }
        if (define.getType() == FieldType.FIELD_TYPE_FLOAT || define.getType() == FieldType.FIELD_TYPE_DECIMAL || define.getType() == FieldType.FIELD_TYPE_INTEGER) {
            return 0.0;
        }
        if (define.getType() == FieldType.FIELD_TYPE_STRING) {
            return "";
        }
        return 0.0;
    }

    abstract String getUnitId(QueryContext var1);

    private DimensionValueSet calcDimensionValueSet(DimensionValueSet olDimensionValueSet, List<IASTNode> parameters, String unitId) throws Exception {
        DimensionValueSet ds = new DimensionValueSet(olDimensionValueSet);
        ds.setValue("MD_ORG", (Object)unitId);
        String periodString = (String)ds.getValue("DATATIME");
        YearPeriodObject yp = new YearPeriodObject(null, periodString);
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)((String)ds.getValue("MD_GCORGTYPE")), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO mergeUnitOrg = orgTool.getOrgByCode(unitId);
        ds.setValue("MD_GCORGTYPE", (Object)mergeUnitOrg.getOrgTypeId());
        PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        if (parameters.size() <= 1) {
            return ds;
        }
        String offsetTerm = (String)parameters.get(1).evaluate(null);
        if (!"-1".equalsIgnoreCase(offsetTerm) && !"-1N".equalsIgnoreCase(offsetTerm)) {
            return ds;
        }
        if ("-1".equalsIgnoreCase(offsetTerm)) {
            periodWrapper.priorPeriod();
        } else {
            periodWrapper.setYear(periodWrapper.getYear() - 1);
        }
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        return ds;
    }

    public void enableCache() {
        zbCode2ValueMapLocal.set(new HashMap());
    }

    public void releaseCache() {
        zbCode2ValueMapLocal.remove();
    }

    protected DefaultTableEntity getDefaultTableEntity(QueryContext queryContext) {
        DefaultTableEntity entity = null;
        if (queryContext.getExeContext() instanceof GcReportSimpleExecutorContext) {
            GcReportSimpleExecutorContext exceutorContext = (GcReportSimpleExecutorContext)queryContext.getExeContext();
            entity = exceutorContext.getData();
            if (exceutorContext instanceof GcReportExceutorContext) {
                List inputDatas = ((GcReportExceutorContext)exceutorContext).getInputDatas();
                if (entity == null && !CollectionUtils.isEmpty((Collection)inputDatas)) {
                    Map rowValuesMap = queryContext.getRowValuesMap();
                    entity = this.getDefaultTableEntity(inputDatas, rowValuesMap);
                }
            }
        } else {
            DimensionValueSet rowKey = queryContext.getRowKey();
            String recordKey = (String)rowKey.getValue("RECORDKEY");
            ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
            try {
                String tableName = this.inputDataNameProvider.getTableNameByTaskId(env.getTaskDefine().getKey());
                List<InputDataEO> inputDataEOList = this.inputDataDao.queryByIds(Collections.singletonList(recordKey), tableName);
                if (!CollectionUtils.isEmpty(inputDataEOList)) {
                    return inputDataEOList.get(0);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return entity;
    }

    public InputDataEO getDefaultTableEntity(List<? extends AbstractFieldDynamicDeclarator> inputDatas, Map<QueryField, Object> rowValuesMap) {
        if (rowValuesMap.isEmpty()) {
            return null;
        }
        Optional queryFieldEntry = rowValuesMap.entrySet().stream().findFirst();
        if (queryFieldEntry.isPresent()) {
            String fieldCode = ((QueryField)((Map.Entry)queryFieldEntry.get()).getKey()).getFieldCode();
            if (StringUtils.isEmpty((String)fieldCode) || !"ID".equals(fieldCode)) {
                throw new BusinessRuntimeException("\u516c\u5f0fBFDW\u548cDFDW\u53d6\u6570\u65b9\u5f0f\u4e3a\u6c47\u603b\u7c7b\u578b\u65f6\u53ea\u652f\u6301\u8bbe\u7f6e\u5185\u90e8\u8868ID\u5b57\u6bb5\u3002");
            }
            Object fieldValue = ((Map.Entry)queryFieldEntry.get()).getValue();
            if (fieldValue == null) {
                return null;
            }
            for (AbstractFieldDynamicDeclarator abstractFieldDynamicDeclarator : inputDatas) {
                Object value;
                if (!(abstractFieldDynamicDeclarator instanceof InputDataEO) || !(value = abstractFieldDynamicDeclarator.getFieldValue("ID")).equals(fieldValue)) continue;
                return (InputDataEO)abstractFieldDynamicDeclarator;
            }
        }
        return null;
    }
}

