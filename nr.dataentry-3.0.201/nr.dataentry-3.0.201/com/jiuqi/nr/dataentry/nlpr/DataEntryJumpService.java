/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.exception.PeriodFormatException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.framework.nros.bean.Route
 *  com.jiuqi.nvwa.framework.nros.bean.RouteParam
 *  com.jiuqi.nvwa.framework.nros.dao.IRouteDao
 *  com.jiuqi.nvwa.framework.nros.dao.IRouteParamDao
 *  com.jiuqi.nvwa.nlpr.bi.DimAttrElement
 *  com.jiuqi.nvwa.nlpr.bi.DimElement
 *  com.jiuqi.nvwa.nlpr.bi.QueryResourceDescriptor
 *  com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService
 *  com.jiuqi.nvwa.nlpr.vo.ConfigVo
 *  com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport
 */
package com.jiuqi.nr.dataentry.nlpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.nlpr.NlprDataentryParam;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.exception.PeriodFormatException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.framework.nros.bean.Route;
import com.jiuqi.nvwa.framework.nros.bean.RouteParam;
import com.jiuqi.nvwa.framework.nros.dao.IRouteDao;
import com.jiuqi.nvwa.framework.nros.dao.IRouteParamDao;
import com.jiuqi.nvwa.nlpr.bi.DimAttrElement;
import com.jiuqi.nvwa.nlpr.bi.DimElement;
import com.jiuqi.nvwa.nlpr.bi.QueryResourceDescriptor;
import com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService;
import com.jiuqi.nvwa.nlpr.vo.ConfigVo;
import com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataEntryJumpService
implements NvwaRouteJumpService {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryJumpService.class);
    public static final String APP_NAME = "dataentry";
    @Autowired
    private IRouteParamDao routeParamDao;
    @Autowired
    private IRouteDao routeDao;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private ITemplateConfigService templateConfigService;

    public boolean accept(NLPResourceSupport resourceSupport) {
        if (APP_NAME.equals(resourceSupport.getParams())) {
            return true;
        }
        if (null != resourceSupport.getParams()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                NlprDataentryParam readValue = (NlprDataentryParam)mapper.readValue(resourceSupport.getParams(), NlprDataentryParam.class);
                if (APP_NAME.equals(readValue.getApp())) {
                    return true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return false;
    }

    public ConfigVo buildConfig(NLPResourceSupport resourceSupport) {
        ConfigVo configVo = new ConfigVo();
        configVo.setFunction(APP_NAME);
        if (APP_NAME.equals(resourceSupport.getParams())) {
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
            HashMap<String, Object> dataentyMap = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String params = resourceSupport.getParams();
            NlprDataentryParam paramsReadValue = new NlprDataentryParam();
            if (null != params && !"".equals(params)) {
                try {
                    QueryResourceDescriptor resourceDescriptor;
                    List timeRestrictValues;
                    paramsReadValue = (NlprDataentryParam)mapper.readValue(params, NlprDataentryParam.class);
                    String formScheme_id = paramsReadValue.getValue();
                    String formKey = resourceSupport.getResourceId();
                    FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formScheme_id);
                    dataentyMap.put("taskId", formScheme.getTaskKey());
                    dataentyMap.put("formSchemeId", formScheme_id);
                    List<TemplateConfigImpl> miniTemplateConfig = this.templateConfigService.getMiniTemplateConfig();
                    if (miniTemplateConfig.size() > 0) {
                        dataentyMap.put("dataentryDefineCode", miniTemplateConfig.get(0).getCode());
                    }
                    if ((timeRestrictValues = (resourceDescriptor = resourceSupport.getResourceDescriptor()).getTimeRestrictValues()) != null && timeRestrictValues.size() > 0) {
                        PeriodWrapper fromPeriodWrapper;
                        PeriodType periodType = formScheme.getPeriodType();
                        int periodOffset = formScheme.getPeriodOffset();
                        Object fromPeriod = formScheme.getFromPeriod();
                        String toPeriod = formScheme.getToPeriod();
                        if (StringUtils.isEmpty((String)fromPeriod)) {
                            fromPeriodWrapper = new PeriodWrapper(1970, periodType.type(), 1);
                            fromPeriod = fromPeriodWrapper.toString();
                        }
                        if (StringUtils.isEmpty((String)toPeriod)) {
                            fromPeriodWrapper = new PeriodWrapper(9999, periodType.type(), 1);
                            toPeriod = fromPeriodWrapper.toString();
                        }
                        GregorianCalendar gregorianCalendar = new GregorianCalendar();
                        gregorianCalendar.setTime(((Calendar)timeRestrictValues.get(0)).getTime());
                        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)periodType.type(), (int)periodOffset);
                        PeriodWrapper fromPeriodWrapper2 = null;
                        PeriodWrapper toPeriodWrapper = null;
                        try {
                            fromPeriodWrapper2 = new PeriodWrapper((String)fromPeriod);
                            toPeriodWrapper = new PeriodWrapper(toPeriod);
                        }
                        catch (Exception e) {
                            throw new PeriodFormatException(new String[]{e.getMessage()});
                        }
                        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
                        GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
                        if (gregorianCalendar.compareTo(fromCalendar) >= 0 && gregorianCalendar.compareTo(toCalendar) <= 0) {
                            dataentyMap.put("period", currentPeriod.toString());
                        } else if (gregorianCalendar.after(toCalendar)) {
                            dataentyMap.put("period", toPeriodWrapper.toString());
                        } else {
                            dataentyMap.put("period", fromPeriodWrapper2.toString());
                        }
                    }
                    List dims = resourceDescriptor.getDims();
                    HashMap dimValueMap = new HashMap();
                    for (DimElement element : dims) {
                        if (element.getAttrs().size() <= 0 || ((DimAttrElement)element.getAttrs().get(0)).getValues().size() <= 0) continue;
                        dimValueMap.put(element.getName(), ((DimAttrElement)element.getAttrs().get(0)).getValues().get(0));
                    }
                    HashMap locateMap = new HashMap();
                    EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme_id);
                    locateMap.put("unitId", dimValueMap.get(dwEntity.getTableName()));
                    List entityList = this.jtableParamService.getDimEntityList(formScheme_id);
                    for (EntityViewData entity : entityList) {
                        locateMap.put(entity.getDimensionName(), dimValueMap.get(entity.getTableName()));
                    }
                    locateMap.put("formId", formKey);
                    dataentyMap.put("locate", locateMap);
                    String writeValueAsString = mapper.writeValueAsString(dataentyMap);
                    configVo.setConfig(writeValueAsString);
                    FormDefine queryForm = this.runtimeView.queryFormById(formKey);
                    configVo.setDescribe(null != queryForm ? queryForm.getFormCode() + " " + queryForm.getTitle() : "");
                    configVo.setSuccess(true);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    configVo.setSuccess(false);
                    configVo.setMsg("\u5b9a\u4f4d\u5931\u8d25");
                }
            }
        }
        return configVo;
    }
}

