/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.nlpr.extend.NvwaNlprResourceCategory
 *  com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService
 *  com.jiuqi.nvwa.nlpr.resource.DimensionValue
 *  com.jiuqi.nvwa.nlpr.resource.Resource
 *  com.jiuqi.nvwa.nlpr.resource.ResourceItem
 *  com.jiuqi.nvwa.nlpr.util.NvwaRouteJumpFactory
 *  com.jiuqi.nvwa.nlpr.vo.ConfigVo
 *  com.jiuqi.nvwa.nlpr.vo.LocationVo
 *  com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport
 */
package com.jiuqi.nr.dataentry.nlpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.nlpr.NlprDataentryParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.nlpr.extend.NvwaNlprResourceCategory;
import com.jiuqi.nvwa.nlpr.extend.NvwaRouteJumpService;
import com.jiuqi.nvwa.nlpr.resource.DimensionValue;
import com.jiuqi.nvwa.nlpr.resource.Resource;
import com.jiuqi.nvwa.nlpr.resource.ResourceItem;
import com.jiuqi.nvwa.nlpr.util.NvwaRouteJumpFactory;
import com.jiuqi.nvwa.nlpr.vo.ConfigVo;
import com.jiuqi.nvwa.nlpr.vo.LocationVo;
import com.jiuqi.nvwa.nlpr.vo.NLPResourceSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportNvwaNlprResourceCategory
implements NvwaNlprResourceCategory {
    private static final Logger logger = LoggerFactory.getLogger(ReportNvwaNlprResourceCategory.class);
    public static final String TASK_TYPE = "task_type";
    public static final String FORMSCHEME_TYPE = "formScheme_type";
    public static final String FORMGROUP_TYPE = "formGroup_type";
    public static final String FORM_TYPE = "form_type";
    public static final String TASK = "NR_TASK";
    public static final String FORM_SCHEME = "NR_FORMSCHEME";
    @Autowired
    private NvwaRouteJumpFactory nvwaRouteJumpFactory;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;

    public String getId() {
        return "reportResourceCategory_Id";
    }

    public String getCode() {
        return "reportResourceCategory_Code";
    }

    public String getTitel() {
        return "\u4e45\u671f\u62a5\u8868";
    }

    public List<Resource> getChildrens(String resourceId, String params) {
        ArrayList<Resource> resources;
        block17: {
            resources = new ArrayList<Resource>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                NlprDataentryParam paramsReadValue = new NlprDataentryParam();
                if (null != params && !"".equals(params)) {
                    try {
                        paramsReadValue = (NlprDataentryParam)mapper.readValue(params, NlprDataentryParam.class);
                    }
                    catch (IOException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
                if (StringUtils.isEmpty((String)resourceId) || resourceId.equals(this.getId())) {
                    List allTaskDefines = this.runtimeView.getAllReportTaskDefines();
                    for (TaskDefine taskData : allTaskDefines) {
                        NlprDataentryParam nlprDataentryParam = new NlprDataentryParam();
                        ResourceItem item = new ResourceItem();
                        item.setCode(taskData.getTaskCode());
                        item.setId(taskData.getKey());
                        item.setResource(false);
                        item.setTitle(taskData.getTitle());
                        nlprDataentryParam.setType(TASK_TYPE);
                        nlprDataentryParam.setApp("dataentry");
                        item.setParams(mapper.writeValueAsString((Object)nlprDataentryParam));
                        resources.add((Resource)item);
                    }
                    break block17;
                }
                if (TASK_TYPE.equals(paramsReadValue.getType())) {
                    try {
                        List queryFormSchemeByTask = this.runtimeView.queryFormSchemeByTask(resourceId);
                        for (FormSchemeDefine formSchemeData : queryFormSchemeByTask) {
                            NlprDataentryParam nlprDataentryParam = new NlprDataentryParam();
                            ResourceItem item = new ResourceItem();
                            item.setCode(formSchemeData.getFormSchemeCode());
                            item.setId(formSchemeData.getKey());
                            item.setResource(false);
                            nlprDataentryParam.setType(FORMSCHEME_TYPE);
                            nlprDataentryParam.setApp("dataentry");
                            item.setParams(mapper.writeValueAsString((Object)nlprDataentryParam));
                            item.setTitle(formSchemeData.getTitle());
                            resources.add((Resource)item);
                        }
                        break block17;
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        break block17;
                    }
                }
                if (FORMSCHEME_TYPE.equals(paramsReadValue.getType())) {
                    List allFormGroupsInFormScheme = this.runtimeView.getAllFormGroupsInFormScheme(resourceId);
                    for (FormGroupDefine formGroupDefine : allFormGroupsInFormScheme) {
                        NlprDataentryParam nlprDataentryParam = new NlprDataentryParam();
                        ResourceItem item = new ResourceItem();
                        item.setCode(formGroupDefine.getCode());
                        item.setId(formGroupDefine.getKey());
                        item.setResource(false);
                        nlprDataentryParam.setType(FORMGROUP_TYPE);
                        nlprDataentryParam.setApp("dataentry");
                        nlprDataentryParam.setValue(resourceId);
                        item.setParams(mapper.writeValueAsString((Object)nlprDataentryParam));
                        item.setTitle(formGroupDefine.getTitle());
                        resources.add((Resource)item);
                    }
                    break block17;
                }
                if (!FORMGROUP_TYPE.equals(paramsReadValue.getType())) break block17;
                try {
                    String formScheme_key = paramsReadValue.getValue();
                    FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formScheme_key);
                    TaskDefine queryTaskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
                    ArrayList<String> dimensions = new ArrayList<String>();
                    HashMap<String, DimensionValue> dimensionValues = new HashMap<String, DimensionValue>();
                    dimensions.add(TASK);
                    dimensions.add(FORM_SCHEME);
                    DimensionValue taskD = new DimensionValue();
                    taskD.setValue(formScheme.getTaskKey());
                    taskD.setTitle(queryTaskDefine.getTitle());
                    DimensionValue formSchemeD = new DimensionValue();
                    formSchemeD.setTitle(formScheme.getTitle());
                    formSchemeD.setValue(formScheme_key);
                    dimensionValues.put(TASK, taskD);
                    dimensionValues.put(FORM_SCHEME, formSchemeD);
                    EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme_key);
                    dimensions.add(dwEntity.getTableName());
                    List entityList = this.jtableParamService.getDimEntityList(formScheme_key);
                    for (EntityViewData entityViewData : entityList) {
                        dimensions.add(entityViewData.getTableName());
                    }
                    List formDefines = this.runtimeView.getAllFormsInGroup(resourceId);
                    for (FormDefine formDefine : formDefines) {
                        NlprDataentryParam nlprDataentryParam = new NlprDataentryParam();
                        ResourceItem item = new ResourceItem();
                        item.setCode(formDefine.getFormCode());
                        item.setId(formDefine.getKey());
                        item.setResource(true);
                        item.setTitle(formDefine.getTitle());
                        nlprDataentryParam.setType(FORM_TYPE);
                        nlprDataentryParam.setApp("dataentry");
                        nlprDataentryParam.setValue(formScheme_key);
                        item.setParams(mapper.writeValueAsString((Object)nlprDataentryParam));
                        item.setRefDimensions(dimensions);
                        item.setRefDimensionValues(dimensionValues);
                        resources.add((Resource)item);
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return resources;
    }

    public LocationVo buildURL(NLPResourceSupport resourceSupport) {
        LocationVo locationVo = new LocationVo();
        NvwaRouteJumpService routeJumpService = this.nvwaRouteJumpFactory.getRouteJumpService(resourceSupport);
        ConfigVo appConfigVo = routeJumpService.buildConfig(resourceSupport);
        String frontEndUrl = resourceSupport.getfrontEndUrl();
        String url = frontEndUrl + "/#/sso?jumpType=app&name=" + appConfigVo.getFunction();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("appConfig", appConfigVo.getConfig());
            String appConfig = mapper.writeValueAsString(resultMap);
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] textByte = appConfig.getBytes("UTF-8");
            String encodedText = encoder.encodeToString(textByte);
            url = url + "&os-jump=" + encodedText;
        }
        catch (JsonProcessingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        locationVo.setUrl(url);
        locationVo.setDescribe(appConfigVo.getDescribe());
        locationVo.setSuccess(appConfigVo.isSuccess());
        locationVo.setMsg(appConfigVo.getMsg());
        return locationVo;
    }
}

