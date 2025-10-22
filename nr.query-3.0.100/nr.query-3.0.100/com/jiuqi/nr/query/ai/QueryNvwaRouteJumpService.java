/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.JsonObject
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.framework.nros.bean.RouteParam
 *  com.jiuqi.nvwa.framework.nros.dao.IRouteDao
 *  com.jiuqi.nvwa.framework.nros.dao.IRouteParamDao
 *  com.jiuqi.nvwa.nlpr.bi.DimAttrElement
 *  com.jiuqi.nvwa.nlpr.bi.DimElement
 *  com.jiuqi.nvwa.nlpr.bi.KpiElement
 *  com.jiuqi.nvwa.nlpr.bi.QueryResourceDescriptor
 *  com.jiuqi.nvwa.nlpr.bi.QueryResourceDescriptor$Range
 *  com.jiuqi.nvwa.nlpr.bi.Scheme
 *  com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService
 *  com.jiuqi.nvwa.nlpr.vo.ConfigVo
 *  com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.query.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.query.ai.QueryFieldInfo;
import com.jiuqi.nr.query.ai.QueryNvwaFieldParam;
import com.jiuqi.nr.query.ai.ReportQueryNvwaParam;
import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.framework.nros.bean.RouteParam;
import com.jiuqi.nvwa.framework.nros.dao.IRouteDao;
import com.jiuqi.nvwa.framework.nros.dao.IRouteParamDao;
import com.jiuqi.nvwa.nlpr.bi.DimAttrElement;
import com.jiuqi.nvwa.nlpr.bi.DimElement;
import com.jiuqi.nvwa.nlpr.bi.KpiElement;
import com.jiuqi.nvwa.nlpr.bi.QueryResourceDescriptor;
import com.jiuqi.nvwa.nlpr.bi.Scheme;
import com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService;
import com.jiuqi.nvwa.nlpr.vo.ConfigVo;
import com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryNvwaRouteJumpService
implements NvwaRouteJumpService {
    private static final Logger log = LoggerFactory.getLogger(QueryNvwaRouteJumpService.class);
    public static final String APPNAME = "dataPenetration";
    public static final String CONST_TASKID = "taskId";
    public static final String CONST_SCHEMEID = "schemeId";
    public static final String CONST_FORMSCHEMEID = "formSchemeId";
    public static final String CONST_ORGCODES = "orgCodes";
    public static final String CONST_FORMKEY = "formKey";
    public static final String CONST_DIMENSION = "dimension";
    public static final String CONST_ZBLIST = "zbList";
    public static final String CONST_TARGETTYPE = "targetType";
    public static final String CONST_PERIODTYPE = "periodType";
    public static final String CONST_NEWMODEL = "newModel";
    @Autowired
    private IRouteParamDao routeParamDao;
    @Autowired
    private IRouteDao routeDao;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private RuntimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public boolean accept(NLPResourceSupport resourceSupport) {
        return "queryResourceCategory_Id".equals(resourceSupport.getResourceId());
    }

    public ConfigVo buildConfig(NLPResourceSupport resourceSupport) {
        ConfigVo configVo = new ConfigVo();
        configVo.setFunction(APPNAME);
        if (APPNAME.equals(resourceSupport.getParams())) {
            Route route = this.routeDao.select(resourceSupport.getResourceId());
            RouteParam routeParam = this.routeParamDao.select(resourceSupport.getResourceId());
            if (null != routeParam && null != routeParam.getConfigJson() && !"".equals(routeParam.getConfigJson())) {
                configVo.setConfig(routeParam.getConfigJson());
            }
            if (null != route) {
                configVo.setDescribe(route.getTitle());
            }
            configVo.setSuccess(true);
        } else {
            HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
            ReportQueryNvwaParam paramsReadValue = new ReportQueryNvwaParam();
            try {
                String formSchemeId = null;
                String taskId = null;
                QueryResourceDescriptor resourceDescriptor = resourceSupport.getResourceDescriptor();
                String extendInfo = resourceDescriptor.getExtend();
                if (!StringUtil.isNullOrEmpty((String)extendInfo)) {
                    JsonObject jsonObject = new JsonObject();
                    JsonObject extend = jsonObject.getAsJsonObject(extendInfo);
                    int flag = 0;
                    if (extend.has(CONST_TASKID)) {
                        taskId = extend.get(CONST_TASKID).getAsString();
                        ++flag;
                    }
                    if (extend.has(CONST_SCHEMEID)) {
                        formSchemeId = extend.get(CONST_SCHEMEID).getAsString();
                        ++flag;
                    }
                }
                HashMap<String, Object> dimensions = new HashMap<String, Object>();
                FormSchemeDefine formScheme = null;
                if (!StringUtil.isNullOrEmpty(formSchemeId)) {
                    formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
                }
                if (formScheme != null && StringUtil.isNullOrEmpty(taskId)) {
                    taskId = formScheme.getTaskKey();
                }
                String periodScop = resourceDescriptor.getGranularity();
                List<PeriodType> periodType = this.parsePeriodTypes(periodScop, formScheme);
                List<String> period = this.getAllPeriods(resourceDescriptor, formScheme, periodScop);
                dimensions.put("DATATIME", org.apache.commons.lang3.StringUtils.join((Object[])period.toArray(), (String)";"));
                List dims = resourceDescriptor.getDims();
                for (DimElement dim : dims) {
                    if (dim.getAttrs().size() <= 0 || ((DimAttrElement)dim.getAttrs().get(0)).getValues().size() <= 0) continue;
                    Object value = "";
                    for (String val : ((DimAttrElement)dim.getAttrs().get(0)).getValues()) {
                        if (value == "") {
                            value = val;
                            continue;
                        }
                        value = (String)value + ";" + val;
                    }
                    if (dim.isUnit()) {
                        dimensions.put("default_dw_id", value);
                        continue;
                    }
                    dimensions.put(dim.getName(), value);
                }
                ArrayList<QueryNvwaFieldParam> fields = new ArrayList<QueryNvwaFieldParam>();
                List schems = resourceDescriptor.getSchemes();
                for (Scheme s : schems) {
                    String tableName = s.getTableName();
                    List elements = s.getKpis();
                    for (KpiElement e : elements) {
                        QueryNvwaFieldParam field = new QueryNvwaFieldParam();
                        field.tableName = tableName;
                        field.fieldCode = e.getRawCode();
                        field.isVisible = true;
                        field.fieldTitle = "";
                        fields.add(field);
                    }
                }
                queryParamMap.put(CONST_TASKID, taskId);
                queryParamMap.put(CONST_FORMSCHEMEID, formSchemeId);
                queryParamMap.put(CONST_DIMENSION, dimensions);
                queryParamMap.put(CONST_FORMKEY, "");
                queryParamMap.put(CONST_ZBLIST, fields);
                queryParamMap.put(CONST_TARGETTYPE, "AI");
                queryParamMap.put(CONST_PERIODTYPE, periodType.toString());
                ObjectMapper mapper = new ObjectMapper();
                String writeValueAsString = mapper.writeValueAsString(queryParamMap);
                configVo.setConfig(writeValueAsString);
                configVo.setDescribe("");
                configVo.setSuccess(true);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"AI\u6307\u6807\u67e5\u8be2\u9519\u8bef\uff0c\u751f\u6210config\u4fe1\u606f\u9519\u8bef", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage()));
                configVo.setSuccess(false);
                configVo.setMsg("\u5b9a\u4f4d\u5931\u8d25");
            }
        }
        return configVo;
    }

    private QueryFieldInfo getField(KpiElement element) {
        if (element.isVisible()) {
            QueryFieldInfo fieldInfo = null;
            try {
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(element.getName());
                if (field != null) {
                    fieldInfo = new QueryFieldInfo();
                    fieldInfo.code = field.getCode();
                    fieldInfo.regionKey = "";
                    fieldInfo.linkKey = "";
                    fieldInfo.fieldTitle = field.getTitle();
                    fieldInfo.fieldCode = field.getKey();
                }
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"AI\u6307\u6807\u67e5\u8be2\u9519\u8bef\uff0c\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u9519\u8bef", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            }
            return fieldInfo;
        }
        return null;
    }

    private List<String> getAllPeriods(QueryResourceDescriptor resourceDescriptor, FormSchemeDefine formScheme, String periodScop) {
        List timeRange;
        List<String> rangePeriods;
        List<PeriodType> types;
        ArrayList<String> periods = new ArrayList<String>();
        List timeRestricts = resourceDescriptor.getTimeRestrictValues();
        List<String> timePeriods = this.timeRestrictToPeriods(timeRestricts, types = this.parsePeriodTypes(periodScop, formScheme), formScheme);
        if (timePeriods.size() > 0) {
            periods.addAll(timePeriods);
        }
        if ((rangePeriods = this.timeRangeToPeriods(timeRange = resourceDescriptor.getTimeRanges(), types, formScheme)).size() > 0) {
            periods.addAll(rangePeriods);
        }
        return periods;
    }

    private List<PeriodType> parsePeriodTypes(String scop, FormSchemeDefine formScheme) {
        PeriodType periodType;
        ArrayList<PeriodType> types = new ArrayList<PeriodType>();
        if (formScheme != null && (periodType = formScheme.getPeriodType()) != null) {
            types.add(periodType);
            return types;
        }
        String lowLevelPeriod = "DAY";
        switch (scop) {
            case "\u5e74": {
                types.add(PeriodType.YEAR);
                types.add(PeriodType.MONTH);
                types.add(PeriodType.DAY);
                types.add(PeriodType.HALFYEAR);
                types.add(PeriodType.WEEK);
                break;
            }
            case "\u6708\u4efd": {
                types.add(PeriodType.MONTH);
                types.add(PeriodType.DAY);
                types.add(PeriodType.WEEK);
                break;
            }
            case "\u65e5": {
                types.add(PeriodType.DAY);
                break;
            }
            case "\u534a\u5e74": {
                types.add(PeriodType.HALFYEAR);
                types.add(PeriodType.MONTH);
                types.add(PeriodType.DAY);
                types.add(PeriodType.WEEK);
                break;
            }
            case "\u5b63\u5ea6": {
                types.add(PeriodType.MONTH);
                types.add(PeriodType.WEEK);
                types.add(PeriodType.DAY);
                break;
            }
            case "\u5468": {
                types.add(PeriodType.DAY);
                types.add(PeriodType.WEEK);
                break;
            }
            default: {
                types.add(PeriodType.DAY);
            }
        }
        return types;
    }

    private List<String> timeRangeToPeriods(List<QueryResourceDescriptor.Range> timeRange, List<PeriodType> periodType, FormSchemeDefine formScheme) {
        ArrayList<String> periods = new ArrayList<String>();
        if (timeRange != null && timeRange.size() > 0) {
            int periodOffset = 0;
            for (QueryResourceDescriptor.Range rg : timeRange) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                for (PeriodType pt : periodType) {
                    PeriodWrapper endPeriod;
                    PeriodWrapper startPeriod = PeriodUtil.currentPeriod((GregorianCalendar)((GregorianCalendar)rg.start), (int)pt.type(), (int)periodOffset);
                    ArrayList periodsTemp = PeriodUtil.getPeiodStrList((PeriodWrapper)startPeriod, (PeriodWrapper)(endPeriod = PeriodUtil.currentPeriod((GregorianCalendar)((GregorianCalendar)rg.end), (int)pt.type(), (int)periodOffset)));
                    if (periodsTemp.size() <= 0) continue;
                    periods.addAll(periodsTemp);
                }
            }
        }
        return periods;
    }

    private List<String> timeRestrictToPeriods(List<Calendar> timeRestrictValues, List<PeriodType> periodType, FormSchemeDefine formScheme) {
        ArrayList<String> periods = new ArrayList<String>();
        if (timeRestrictValues != null && timeRestrictValues.size() > 0) {
            int periodOffset = 0;
            for (Calendar time : timeRestrictValues) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(time.getTime());
                for (PeriodType pt : periodType) {
                    Map<String, GregorianCalendar> range = this.getPeriodRange(formScheme, pt);
                    PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)pt.type(), (int)periodOffset);
                    if (currentPeriod == null || !this.checkPeriodInRange(range.get("from"), range.get("end"), gregorianCalendar)) continue;
                    periods.add(currentPeriod.toString());
                }
            }
        }
        return periods;
    }

    private Map<String, GregorianCalendar> getPeriodRange(FormSchemeDefine formScheme, PeriodType periodType) {
        PeriodWrapper fromPeriodWrapper;
        String fromPeriod = null;
        String toPeriod = null;
        int periodOffset = 0;
        if (formScheme != null) {
            periodType = formScheme.getPeriodType();
            periodOffset = formScheme.getPeriodOffset();
            fromPeriod = formScheme.getFromPeriod();
            toPeriod = formScheme.getToPeriod();
        }
        if (StringUtils.isEmpty(fromPeriod)) {
            fromPeriodWrapper = new PeriodWrapper(1970, periodType.type(), 1);
            fromPeriod = fromPeriodWrapper.toString();
        }
        if (StringUtils.isEmpty(toPeriod)) {
            fromPeriodWrapper = new PeriodWrapper(9999, periodType.type(), 1);
            toPeriod = fromPeriodWrapper.toString();
        }
        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar(fromPeriod);
        GregorianCalendar toCalendar = PeriodUtil.period2Calendar(toPeriod);
        HashMap<String, GregorianCalendar> range = new HashMap<String, GregorianCalendar>();
        range.put("from", fromCalendar);
        range.put("end", toCalendar);
        return range;
    }

    private List<String> calcPeriod(List<Calendar> timeRestrictValues, FormSchemeDefine formScheme, PeriodType periodType) {
        ArrayList<String> periods = new ArrayList<String>();
        if (timeRestrictValues != null && timeRestrictValues.size() > 0) {
            int periodOffset = 0;
            for (Calendar time : timeRestrictValues) {
                int year = time.get(1);
                int month = time.get(2);
                int day = time.get(5);
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(timeRestrictValues.get(0).getTime());
                PeriodWrapper currentPeriod2 = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)periodType.type(), (int)periodOffset);
                if (currentPeriod2 == null) continue;
                periods.add(currentPeriod2.toString());
            }
            String fromPeriod = null;
            String toPeriod = null;
            if (formScheme != null) {
                periodType = formScheme.getPeriodType();
                periodOffset = formScheme.getPeriodOffset();
                fromPeriod = formScheme.getFromPeriod();
                toPeriod = formScheme.getToPeriod();
            }
            if (StringUtils.isEmpty(fromPeriod)) {
                PeriodWrapper fromPeriodWrapper = new PeriodWrapper(1970, periodType.type(), 1);
                fromPeriod = fromPeriodWrapper.toString();
            }
            if (StringUtils.isEmpty(toPeriod)) {
                PeriodWrapper fromPeriodWrapper = new PeriodWrapper(9999, periodType.type(), 1);
                toPeriod = fromPeriodWrapper.toString();
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(timeRestrictValues.get(0).getTime());
            PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)periodType.type(), (int)periodOffset);
            PeriodWrapper fromPeriodWrapper = null;
            PeriodWrapper toPeriodWrapper = null;
            try {
                fromPeriodWrapper = new PeriodWrapper(fromPeriod);
                toPeriodWrapper = new PeriodWrapper(toPeriod);
            }
            catch (Exception currentPeriod2) {
                // empty catch block
            }
            GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
            GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
            if (PeriodType.DAY == periodType) {
                // empty if block
            }
        }
        return null;
    }

    private boolean checkPeriodInRange(GregorianCalendar fromCalendar, GregorianCalendar toCalendar, GregorianCalendar currentCalendar) {
        return currentCalendar.compareTo(fromCalendar) >= 0 && currentCalendar.compareTo(toCalendar) <= 0;
    }
}

