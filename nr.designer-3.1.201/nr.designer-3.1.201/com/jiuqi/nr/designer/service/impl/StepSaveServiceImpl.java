/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.service.DesignTaskDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.service.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.designer.common.EntityChangeInfo;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.SaveEntityLinkageHelper;
import com.jiuqi.nr.designer.helper.SaveFormGroupHelper;
import com.jiuqi.nr.designer.helper.SaveFormHelper;
import com.jiuqi.nr.designer.helper.SaveFormulaSchemeHelper;
import com.jiuqi.nr.designer.helper.SaveFormulasHelper;
import com.jiuqi.nr.designer.helper.SaveSehemeHelper;
import com.jiuqi.nr.designer.helper.SaveTaskLinkHelper;
import com.jiuqi.nr.designer.helper.SaveTaskObjHelper;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.vo.LanguageTypeObject;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FormData;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.designer.web.treebean.TaskObject;
import com.jiuqi.nr.designer.web.treebean.TaskOrderObject;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StepSaveServiceImpl
implements StepSaveService {
    @Autowired
    private SaveTaskObjHelper saveTaskHelper;
    @Autowired
    private SaveSehemeHelper saveSehemeHelper;
    @Autowired
    private SaveFormHelper saveFormHelper;
    @Autowired
    private SaveFormGroupHelper saveFormGroupHelper;
    @Autowired
    private SaveFormulaSchemeHelper saveFormulaSchemeHelper;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private SaveTaskLinkHelper saveTaskLinkHelper;
    @Autowired
    private SaveFormulasHelper saveFormulasHelper;
    @Autowired
    private SaveEntityLinkageHelper saveEntityLinkageHelper;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private LanguageTypeService languageService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    Logger log = LogFactory.getLogger(StepSaveServiceImpl.class);
    @Autowired
    private DesignTaskGroupDefineService designTaskGroupDefineService;
    @Autowired
    private DesignTaskDefineService designTaskDefineService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public TaskObject saveTask(TaskObject taskObject) throws Exception {
        TaskDefine taskDefine = this.saveTaskHelper.saveTaskObject(taskObject);
        this.saveTaskHelper.saveEntityLinkageObject(taskObject);
        return taskObject;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void stepSaveScheme(FormSchemeObj formSchemeObj) throws Exception {
        HashMap<String, EntityChangeInfo> entityChangeMap = new HashMap<String, EntityChangeInfo>();
        this.saveSehemeHelper.stepSaveFormScheme(formSchemeObj, entityChangeMap);
        this.saveEntityLinkageHelper.saveEntityLinkageObject(formSchemeObj.getEntityList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void stepSaveForm(FormObj formObj) throws Exception {
        this.saveFormHelper.saveFormObject(formObj);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void stepSaveFormulaScheme(FormulaSchemeObject formulaScheme) throws Exception {
        this.saveFormulaSchemeHelper.saveFormulaSchemeObject(formulaScheme);
    }

    @Override
    public void stepChangeFormOrder(String formKey, String order, String groupKey) throws Exception {
        DesignFormDefine define = this.nrDesignTimeController.queryFormById(formKey);
        define.setOrder(order);
        this.nrDesignTimeController.updateFormDefine(define);
        DesignFormGroupLink designFormGroupLink = this.nrDesignTimeController.getFormGroupLinksByFormIdAndGroupId(formKey, groupKey);
        designFormGroupLink.setFormOrder(order);
        this.nrDesignTimeController.updateDesignFormGroupLink(designFormGroupLink);
    }

    @Override
    public void saveFormGroup(FormGroupObject formGroupObject) throws Exception {
        this.saveFormGroupHelper.saveFormGroup(formGroupObject);
    }

    @Override
    public void saveChangeFormGroupOrder(String formKey, String order) throws JQException {
        DesignFormGroupDefine designFormGroupDefine = this.nrDesignTimeController.queryFormGroup(formKey);
        designFormGroupDefine.setOrder(order);
        this.nrDesignTimeController.updateFormGroup(designFormGroupDefine);
    }

    @Override
    public void delFormWhenDelFormGroup(String formKey, Boolean delLinkedParam) throws Exception {
        this.nrDesignTimeController.deleteFormDefine(formKey, delLinkedParam.booleanValue());
    }

    @Override
    public void delForm(String formKey, boolean delLinkedParam) throws Exception {
        ReturnObject returnObject = new ReturnObject();
        DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(formKey);
        List formGroupDefines = this.nrDesignTimeController.getFormGroupsByFormId(formKey);
        String formGroupKey = null;
        if (formGroupDefines != null && formGroupDefines.size() == 1) {
            formGroupKey = ((DesignFormGroupDefine)formGroupDefines.get(0)).getKey();
        }
        if (designFormDefine == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_103);
        }
        this.nrDesignTimeController.deleteFormDefine(formKey, delLinkedParam);
        this.nrDesignTimeController.removeFormFromGroup(formKey, formGroupKey);
        returnObject.setMessage("\u62a5\u8868\u5220\u9664\u6210\u529f");
        returnObject.setSuccess(true);
    }

    @Override
    public void saveTaskLinkage(TaskLinkObject[] taskLinkObj) throws JQException {
        this.saveTaskLinkHelper.saveTaskLinkObject(taskLinkObj);
    }

    @Override
    public ReturnObject saveFormula(Map<String, FormulaObj> formulas) throws Exception {
        this.saveFormulasHelper.saveFormulaObj(formulas);
        return null;
    }

    @Override
    public ArrayList<TaskLinkObject> getTaskLinkObjBySchemeKey(String schemeKey) {
        ArrayList<TaskLinkObject> taskLinkList = this.saveTaskLinkHelper.getTaskLinkObj(schemeKey);
        return taskLinkList;
    }

    @Override
    public String getFormPropWhenSave(String mess) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject(mess);
        String formDefineKey = (String)json.get("formId");
        String taskKey = (String)json.get("taskId");
        String ownGroupId = (String)json.get("ownGroupId");
        int type = (Integer)json.get("NextLanguageType");
        DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(formDefineKey);
        byte[] data = this.nrDesignTimeController.getReportDataFromForm(formDefineKey, type);
        if (data == null || data.length == 0) {
            String defaultLanguage = this.languageService.queryDefaultLanguage();
            data = this.nrDesignTimeController.getReportDataFromForm(formDefineKey, Integer.valueOf(defaultLanguage).intValue());
        }
        designFormDefine.setBinaryData(data);
        FormData formData = new FormData();
        FormObj formObject = this.initParamObjPropertyUtil.setFormObjProperty(designFormDefine, taskKey, ownGroupId, true, true);
        try {
            String s = this.systemOptionService.get("other-group", "GRID_LINE_SPACE");
            formObject.setRowSpace(Integer.parseInt(s));
        }
        catch (Exception e) {
            this.log.info("\u67e5\u8be2\u7cfb\u7edf\u9009\u9879-\u62a5\u8868\u884c\u95f4\u8ddd\u8bbe\u7f6e\u5931\u8d25\uff0c\u4f7f\u7528\u9ed8\u8ba4\u503c0");
        }
        formData.setFormObject(formObject);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)formData);
    }

    @Override
    public FormObj setFormObjProperty(String formKey, int type) throws Exception {
        FormObj formObject = new FormObj();
        byte[] data = this.nrDesignTimeController.getReportDataFromForm(formKey, type);
        if (data == null) {
            String defaultLang = this.languageService.queryDefaultLanguage();
            data = this.nrDesignTimeController.getReportDataFromForm(formKey, Integer.parseInt(defaultLang));
        }
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])data);
        formObject.setFormStyle(grid2Data);
        return formObject;
    }

    @Override
    public void editTaskOrder(TaskOrderObject taskOrderObject) throws Exception {
        if (StringUtils.isEmpty((String)taskOrderObject.getTaskGroupKey())) {
            DesignTaskDefine preTask = this.nrDesignTimeController.queryTaskDefine(taskOrderObject.getPreTaskKey());
            DesignTaskDefine sufTask = this.nrDesignTimeController.queryTaskDefine(taskOrderObject.getSufTaskKey());
            String temp = preTask.getOrder();
            preTask.setOrder(sufTask.getOrder());
            sufTask.setOrder(temp);
            this.nrDesignTimeController.updateTaskDefine(preTask);
            this.nrDesignTimeController.updateTaskDefine(sufTask);
        } else {
            List groupLinkByGroupKey = this.designTaskDefineService.getGroupLinkByGroupKey(taskOrderObject.getTaskGroupKey());
            DesignTaskGroupLink preLink = null;
            DesignTaskGroupLink sufLink = null;
            for (DesignTaskGroupLink designTaskGroupLink : groupLinkByGroupKey) {
                if (taskOrderObject.getPreTaskKey().equals(designTaskGroupLink.getTaskKey())) {
                    preLink = designTaskGroupLink;
                }
                if (!taskOrderObject.getSufTaskKey().equals(designTaskGroupLink.getTaskKey())) continue;
                sufLink = designTaskGroupLink;
            }
            if (preLink != null && sufLink != null) {
                String temp = preLink.getOrder();
                preLink.setOrder(sufLink.getOrder());
                sufLink.setOrder(temp);
                this.designTaskDefineService.updateTaskGroupLink(new DesignTaskGroupLink[]{preLink, sufLink});
            } else {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_212);
            }
        }
    }

    @Override
    public void syncGridData(String formKey, int language, List<IAction> actions) {
        List<LanguageTypeObject> queryAll = this.languageService.queryAll();
        String lge = String.valueOf(language);
        HashMap<String, String> lgeMap = new HashMap<String, String>();
        for (LanguageTypeObject languageType : queryAll) {
            if (languageType.isDefault() && !lge.equals(languageType.getType())) {
                return;
            }
            lgeMap.put(languageType.getType(), languageType.getLanguage());
        }
        if (null == actions || actions.isEmpty()) {
            return;
        }
        Map<Integer, Grid2Data> gridMap = this.getOtherGrid(formKey, language);
        if (null == gridMap || gridMap.isEmpty()) {
            return;
        }
        this.log.info("\u540c\u6b65\u8868\u6837\u64cd\u4f5c\u5f00\u59cb:");
        for (IAction iAction : actions) {
            for (Map.Entry<Integer, Grid2Data> grid : gridMap.entrySet()) {
                Grid2Data gridData = grid.getValue();
                iAction.run(gridData);
                String lgeTitle = (String)lgeMap.get(String.valueOf(grid.getKey()));
                this.nrDesignTimeController.setReportDataToFormByLanguage(formKey, Grid2Data.gridToBytes((Grid2Data)gridData), grid.getKey().intValue());
                this.log.info("FormKey: {}, Language: {}, Action: {};", new Object[]{formKey, lgeTitle, iAction});
            }
        }
        this.log.info("\u540c\u6b65\u8868\u6837\u64cd\u4f5c\u7ed3\u675f.");
    }

    private Map<Integer, Grid2Data> getOtherGrid(String formKey, int language) {
        HashMap<Integer, Grid2Data> gridMap = new HashMap<Integer, Grid2Data>();
        Map datas = this.nrDesignTimeController.getReportDataFromForms(formKey);
        if (null == datas || datas.isEmpty()) {
            return null;
        }
        for (Map.Entry l : datas.entrySet()) {
            if (language == (Integer)l.getKey()) continue;
            gridMap.put((Integer)l.getKey(), Grid2Data.bytesToGrid((byte[])((byte[])l.getValue())));
        }
        return gridMap;
    }
}

