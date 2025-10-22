/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.unit.uselector.web;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.unit.uselector.web.response.FiledInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/unit-selector/formula-editor"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u516c\u5f0f\u7f16\u8f91\u5668\u7ec4\u4ef6API"})
public class IFormulaEditorController {
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IDataDefinitionDesignTimeController tbDesCtl;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    public IRunTimeViewController rtViewService;
    private static final String KEY_CURRENT_TASK = "currentTask";
    private static final String KEY_CURRENT_PERIOD = "currentPeriod";
    private static final String KEY_CURRENT_FORM_SCHEME = "currentFormScheme";
    private static final String KEY = "key";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String TASK = "task";
    private static final String TYPE_TASK_LINK = "task-link";

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u516c\u5f0f\u7f16\u8f91\u5668-\u83b7\u53d6\u5b9e\u4f53\u5c5e\u6027")
    @PostMapping(value={"/inquery-entity-attrs"})
    public List<FiledInfo> inqueryEntityAttrs(@Valid @RequestBody UnitTreeContextData context) {
        ArrayList<FiledInfo> attrs = new ArrayList<FiledInfo>();
        try {
            IUnitTreeContext treeContext = this.contextBuilder.createTreeContext(context);
            FormSchemeDefine formScheme = treeContext.getFormScheme();
            IEntityDefine entityDefine = treeContext.getEntityDefine();
            if (formScheme != null && entityDefine != null) {
                List fmdmShowAttribute = this.contextWrapper.getFMDMShowAttribute(formScheme, entityDefine, treeContext.getEntityQueryPloy());
                for (IFMDMAttribute attribute : fmdmShowAttribute) {
                    FiledInfo attr = new FiledInfo();
                    attr.setKey(attribute.getID());
                    attr.setCode(attribute.getCode());
                    attr.setTitle(attribute.getTitle());
                    attrs.add(attr);
                }
            } else if (entityDefine != null) {
                String entityId = entityDefine.getId();
                IEntityModel entityModel = this.metaService.getEntityModel(entityId);
                List showFields = entityModel.getShowFields();
                for (IEntityAttribute fd : showFields) {
                    FiledInfo attr = new FiledInfo();
                    attr.setKey(fd.getID());
                    attr.setCode(fd.getCode());
                    attr.setTitle(fd.getTitle());
                    attrs.add(attr);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return attrs;
    }

    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u516c\u5f0f-\u8868\u8fbe\u5f0f-\u6b63\u786e\u6027\u6821\u9a8c")
    @PostMapping(value={"/check-formula-expression"})
    public String checkFormulaExpression(@RequestBody Map<String, String> formulaInfo) {
        String msg;
        block5: {
            msg = "";
            boolean cond = true;
            String formula = formulaInfo.get("expression");
            String entityID = formulaInfo.get("entityViewKey");
            IEntityDefine entityDefine = this.metaService.queryEntity(entityID);
            if (entityDefine != null) {
                try {
                    String tableCode = entityDefine.getCode();
                    ExecutorContext context = new ExecutorContext(this.tbRtCtl);
                    context.setDesignTimeData(true, this.tbDesCtl);
                    ReportFormulaParser parser = context.getCache().getFormulaParser(context);
                    QueryContext qContext = new QueryContext(context, null);
                    qContext.setDefaultGroupName(tableCode);
                    if (cond) {
                        parser.parseCond(formula, (IContext)qContext);
                        break block5;
                    }
                    parser.parseEval(formula, (IContext)qContext);
                }
                catch (Exception e) {
                    msg = "\u65e0\u6cd5\u89e3\u6790\u7684\u516c\u5f0f\uff1a\u3010" + e.getMessage() + "\u3011";
                }
            } else {
                msg = "\u65e0\u6cd5\u6839\u636e\u5b9e\u4f53ID[" + entityID + "]\u83b7\u53d6\u5b9e\u4f53\u5b9a\u4e49\uff01";
            }
        }
        return msg;
    }

    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u516c\u5f0f\u7f16\u8f91\u5668-\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u5217\u8868")
    @PostMapping(value={"/formula-editor-inquiry-task-list"})
    public List<Map<String, String>> formulaEditorInquiryTaskList(@RequestBody Map<String, String> reqParam) {
        ArrayList<Map<String, String>> resp = new ArrayList<Map<String, String>>();
        FormSchemeDefine currFormScheme = this.getCurrentFormScheme(reqParam);
        if (currFormScheme != null) {
            TaskDefine currTaskDefine = this.rtViewService.queryTaskDefine(currFormScheme.getTaskKey());
            resp.add(this.createTaskRecord(currTaskDefine));
            List taskLinkDefines = this.rtViewService.queryLinksByCurrentFormScheme(currFormScheme.getKey());
            taskLinkDefines.sort((o1, o2) -> {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                if (o1.getOrder() == null) {
                    return -1;
                }
                if (o2.getOrder() == null) {
                    return 1;
                }
                return o1.getOrder().compareTo(o2.getOrder());
            });
            for (TaskLinkDefine taskLink : taskLinkDefines) {
                Map<String, String> taskLinkRecord = this.createTaskLinkRecord(taskLink);
                if (taskLinkRecord == null) continue;
                resp.add(taskLinkRecord);
            }
        }
        return resp;
    }

    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668-\u516c\u5f0f\u7f16\u8f91\u5668-\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848")
    @PostMapping(value={"/formula-editor-inquiry-form-scheme-list"})
    public List<Map<String, String>> formulaEditorInquiryFormSchemeList(@RequestBody Map<String, String> taskInfo) {
        ArrayList<Map<String, String>> resp = new ArrayList<Map<String, String>>();
        String task_type = taskInfo.get(TYPE);
        if (TASK.equals(task_type)) {
            String taskKey = taskInfo.get(KEY);
            try {
                List formSchemeDefines = this.rtViewService.queryFormSchemeByTask(taskKey);
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    resp.add(this.createFormSchemeRecord(formSchemeDefine));
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (TYPE_TASK_LINK.equals(task_type)) {
            String formSchemeKey = taskInfo.get(KEY);
            FormSchemeDefine formSchemeDefine = this.rtViewService.getFormScheme(formSchemeKey);
            resp.add(this.createFormSchemeRecord(formSchemeDefine));
        }
        return resp;
    }

    private Map<String, String> createTaskRecord(TaskDefine currTaskDefine) {
        HashMap<String, String> record = new HashMap<String, String>();
        record.put(KEY, currTaskDefine.getKey());
        record.put(TITLE, currTaskDefine.getTitle());
        record.put(TYPE, TASK);
        return record;
    }

    private Map<String, String> createTaskLinkRecord(TaskLinkDefine taskLinkDefine) {
        FormSchemeDefine relateFormScheme = this.rtViewService.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
        if (relateFormScheme != null) {
            TaskDefine relateTaskDefine = this.rtViewService.queryTaskDefine(relateFormScheme.getTaskKey());
            HashMap<String, String> record = new HashMap<String, String>();
            record.put(KEY, relateFormScheme.getKey());
            record.put(TITLE, relateTaskDefine.getTitle() + "@" + taskLinkDefine.getLinkAlias());
            record.put(TYPE, TYPE_TASK_LINK);
            return record;
        }
        return null;
    }

    private Map<String, String> createFormSchemeRecord(FormSchemeDefine formSchemeDefine) {
        HashMap<String, String> record = new HashMap<String, String>();
        record.put(KEY, formSchemeDefine.getKey());
        record.put(TITLE, formSchemeDefine.getTitle());
        record.put(TYPE, TYPE_TASK_LINK);
        return record;
    }

    private FormSchemeDefine getCurrentFormScheme(Map<String, String> reqParam) {
        String taskKey = reqParam.get(KEY_CURRENT_TASK);
        String period = reqParam.get(KEY_CURRENT_PERIOD);
        String formSchemeKey = reqParam.get(KEY_CURRENT_FORM_SCHEME);
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            return this.rtViewService.getFormScheme(formSchemeKey);
        }
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)period)) {
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.rtViewService.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
                if (schemePeriodLinkDefine != null) {
                    return this.rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }
}

