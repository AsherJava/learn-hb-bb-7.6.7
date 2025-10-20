/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignReportTemplateDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.IPrintSchemeService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.rest.param.CreateFormSchemePM;
import com.jiuqi.nr.designer.web.service.FormSchemeService;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormSchemeController {
    private static final Logger log = LoggerFactory.getLogger(FormSchemeController.class);
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private IPrintSchemeService iPrintSchemeService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IDesignRestService restService;
    @Autowired
    private TaskDesignerService taskDesignerService;
    @Autowired
    private SaveSchemePeriodHelper saveSchemePeriodHelper;
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private FormSchemeService formSchemeService;

    @PostMapping(value={"stepSaveSehemeObj"})
    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u65b9\u6848")
    public FormSchemeObj stepSaveSchemeObj(@RequestBody FormSchemeObj formSchemeObj) throws JQException {
        String logTitle = "\u4fee\u6539\u62a5\u8868\u65b9\u6848";
        String formSchemeTitle = "\u672a\u77e5";
        try {
            formSchemeTitle = formSchemeObj.getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(formSchemeObj.getID());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            FlowsObj flowsObj = formSchemeObj.getFlowsObj();
            if (formSchemeObj.isIsNew()) {
                this.iPrintSchemeService.createDefaultPrintScheme(formSchemeObj.getTaskId(), formSchemeObj.getID());
            }
            if (formSchemeObj.isIsDeleted()) {
                this.iPrintSchemeService.deleteAllPrintSchemeByFormScheme(formSchemeObj.getID());
            }
            this.stepSaveService.stepSaveScheme(formSchemeObj);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formSchemeObj;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_038, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/init"}, method={RequestMethod.GET})
    public List<Map<String, Object>> init(String taskKey, String language) throws JQException {
        List formSchemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        if (formSchemes != null) {
            return formSchemes.stream().map(scheme -> {
                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("key", scheme.getKey().toString());
                result.put("title", scheme.getTitle());
                try {
                    result.put("sameServeCode", this.serveCodeService.isSameServeCode(scheme.getOwnerLevelAndId()));
                }
                catch (JQException e) {
                    log.error(e.getMessage(), e);
                }
                return result;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/run/init"}, method={RequestMethod.GET})
    public List<Map<String, Object>> initRun(String taskKey, String language) throws Exception {
        List formSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        if (formSchemes != null) {
            return formSchemes.stream().map(scheme -> {
                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("key", scheme.getKey().toString());
                result.put("title", scheme.getTitle());
                try {
                    result.put("sameServeCode", this.serveCodeService.isSameServeCode(scheme.getOwnerLevelAndId()));
                }
                catch (JQException e) {
                    log.error(e.getMessage(), e);
                }
                return result;
            }).collect(Collectors.toList());
        }
        return new ArrayList<Map<String, Object>>();
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/delete"}, method={RequestMethod.GET})
    public void delete(String formSchemeKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868\u65b9\u6848";
        String formSchemeTitle = "\u672a\u77e5";
        try {
            formSchemeTitle = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(formSchemeKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.iDesignFormCopyService.deleteCopyFormInfos(formSchemeKey);
            List reportTemplateByScheme = this.nrDesignTimeController.getReportTemplateByScheme(formSchemeKey);
            this.nrDesignTimeController.deleteReportTemplateByScheme(formSchemeKey);
            for (DesignReportTemplateDefine obj : reportTemplateByScheme) {
                this.nrDesignTimeController.deleteTagsByRptKey(obj.getKey());
            }
            this.nrDesignTimeController.deleteFormSchemeDefine(formSchemeKey, true);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_123);
        }
    }

    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/delete/without_data"}, method={RequestMethod.GET})
    public void deleteWithoutData(String formSchemeKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868\u65b9\u6848";
        String formSchemeTitle = "\u672a\u77e5";
        try {
            formSchemeTitle = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(formSchemeKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.nrDesignTimeController.deleteFormSchemeDefine(formSchemeKey, false);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_123);
        }
    }

    @ApiOperation(value="\u4ea4\u6362\u4f4d\u7f6e")
    @RequestMapping(value={"form/scheme/exchange"}, method={RequestMethod.GET})
    @Transactional(rollbackFor={Exception.class})
    public void exchange(String sourceKey, String targetKey) throws JQException {
        DesignFormSchemeDefine sourceFormScheme = this.nrDesignTimeController.queryFormSchemeDefine(sourceKey);
        DesignFormSchemeDefine targetFormScheme = this.nrDesignTimeController.queryFormSchemeDefine(targetKey);
        if (sourceFormScheme != null && targetFormScheme != null) {
            String order = sourceFormScheme.getOrder();
            sourceFormScheme.setOrder(targetFormScheme.getOrder());
            targetFormScheme.setOrder(order);
            sourceFormScheme.setUpdateTime(new Date());
            targetFormScheme.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormSchemeDefine(sourceFormScheme);
            this.nrDesignTimeController.updateFormSchemeDefine(targetFormScheme);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/runtime"}, method={RequestMethod.GET})
    public List<FormSchemeDefine> getRuntimeSchemes(String taskKey) throws Exception {
        List formSchemeDefineLists = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        return formSchemeDefineLists;
    }

    @ApiOperation(value="\u83b7\u53d6\u65b9\u6848\u4e3b\u4f53\u5217\u8868")
    @RequestMapping(value={"/entitys-scheme/{id}"}, method={RequestMethod.GET})
    public List<EntityTables> getSchemeEntityList(@PathVariable(value="id") String key) throws JQException {
        return this.restService.getFormSchemeEntity(key, false);
    }

    @RequestMapping(value={"/{taskKey}/add_formscheme"}, method={RequestMethod.POST})
    @ApiOperation(value="\u4efb\u52a1\u4e0b\u6dfb\u52a0\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848")
    public String addFormScheme(@PathVariable String taskKey) throws Exception {
        String logTitle = "\u65b0\u589e\u62a5\u8868\u65b9\u6848";
        String formSchemeTitle = "\u672a\u77e5";
        try {
            formSchemeTitle = this.taskDesignerService.getSchemeTitle(taskKey);
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String formSchemeKey = this.taskDesignerService.addFormSchemeInTask(taskKey, formSchemeTitle);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formSchemeKey;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_120);
        }
    }

    @RequestMapping(value={"/checkfmdmbyscheme/{formscheme}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u6821\u9a8c\u65b9\u6848\u4e0b\u662f\u5426\u53ef\u4ee5\u65b0\u5efa\u5c01\u9762\u4ee3\u7801")
    public boolean checkAddFMDMByFormScheme(@PathVariable String formscheme) throws JQException {
        boolean isAdd = true;
        if (StringUtils.isNotEmpty((String)formscheme)) {
            List forms = this.nrDesignTimeController.getAllFormDefinesInFormSchemeWithoutBinaryData(formscheme);
            for (DesignFormDefine def : forms) {
                if (FormType.FORM_TYPE_NEWFMDM.getValue() != def.getFormType().getValue()) continue;
                isAdd = false;
            }
        }
        return isAdd;
    }

    @PostMapping(value={"/formscheme/create"})
    @ApiOperation(value="\u6dfb\u52a0\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848")
    public String createFormScheme(@RequestBody CreateFormSchemePM param) throws JQException {
        String logTitle = "\u65b0\u589e\u62a5\u8868\u65b9\u6848";
        String formSchemeTitle = "\u672a\u77e5";
        try {
            formSchemeTitle = param.getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(param.getTaskId());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String formSchemeKey = this.formSchemeService.createFormScheme(param.getTaskId(), param.getOrigin(), formSchemeTitle);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return formSchemeKey;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            NrDesignLogHelper.log(logTitle, formSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_120, e.getMessage());
        }
    }
}

