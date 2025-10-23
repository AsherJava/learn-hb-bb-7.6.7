/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  nr.single.map.configurations.bean.SingleConfigInfo
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.datascheme.api.core.DataWrapper;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.param.transfer.cache.TransferCacheService;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.service.SingleMappingService;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class IDesignTimeCacheProxy {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaRunTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private SingleMappingService singleMappingService;
    @Autowired
    private TransferCacheService transferCacheService;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    private final String CACHE_NAME = "TASK_CACHE_NAME";

    public DesignTaskGroupDefine getTaskGroupDefine(String taskGroupKey) {
        DesignTaskGroupDefine taskGroupDefine;
        DataWrapper<DesignTaskGroupDefine> designTaskGroupDefineDataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", taskGroupKey, DesignTaskGroupDefine.class);
        if (!designTaskGroupDefineDataWrapper.isPresent()) {
            taskGroupDefine = this.designTimeViewController.queryTaskGroupDefine(taskGroupKey);
            this.transferCacheService.put("TASK_CACHE_NAME", taskGroupKey, taskGroupDefine);
        } else {
            taskGroupDefine = (DesignTaskGroupDefine)designTaskGroupDefineDataWrapper.get();
        }
        return taskGroupDefine;
    }

    public DesignTaskDefine getTaskDefine(String taskKey) {
        DesignTaskDefine taskDefine;
        DataWrapper<DesignTaskDefine> taskDefineDataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", taskKey, DesignTaskDefine.class);
        if (!taskDefineDataWrapper.isPresent()) {
            taskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
            this.transferCacheService.put("TASK_CACHE_NAME", taskKey, taskDefine);
        } else {
            taskDefine = (DesignTaskDefine)taskDefineDataWrapper.get();
        }
        return taskDefine;
    }

    public DesignTaskGroupLink getTaskGroupLink(String taskKey) {
        Object linkItem;
        DataWrapper<DesignTaskGroupLink> designTaskGroupLinkDataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", taskKey + ":task_group", DesignTaskGroupLink.class);
        if (!designTaskGroupLinkDataWrapper.isPresent()) {
            List link = this.designTimeViewController.getGroupLinkByTaskKey(taskKey);
            linkItem = !CollectionUtils.isEmpty(link) ? (DesignTaskGroupLink)link.get(0) : null;
            this.transferCacheService.put("TASK_CACHE_NAME", taskKey + ":task_group", linkItem);
        } else {
            linkItem = (DesignTaskGroupLink)designTaskGroupLinkDataWrapper.get();
        }
        return linkItem;
    }

    public DesignFormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        DesignFormSchemeDefine formScheme;
        DataWrapper<DesignFormSchemeDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formSchemeKey, DesignFormSchemeDefine.class);
        if (!dataWrapper.isPresent()) {
            formScheme = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
            this.transferCacheService.put("TASK_CACHE_NAME", formSchemeKey, formScheme);
        } else {
            formScheme = (DesignFormSchemeDefine)dataWrapper.get();
        }
        return formScheme;
    }

    public DesignFormGroupDefine getFormGroup(String formGroupKey) {
        DesignFormGroupDefine formGroupDefine;
        DataWrapper<DesignFormGroupDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formGroupKey, DesignFormGroupDefine.class);
        if (!dataWrapper.isPresent()) {
            formGroupDefine = this.designTimeViewController.queryFormGroup(formGroupKey);
            this.transferCacheService.put("TASK_CACHE_NAME", formGroupKey, formGroupDefine);
        } else {
            formGroupDefine = (DesignFormGroupDefine)dataWrapper.get();
        }
        return formGroupDefine;
    }

    public DesignFormDefine getSoftFormDefine(String formKey) {
        DesignFormDefine formDefine;
        DataWrapper<DesignFormDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formKey, DesignFormDefine.class);
        if (!dataWrapper.isPresent()) {
            formDefine = this.designTimeViewController.querySoftFormDefine(formKey);
            this.transferCacheService.put("TASK_CACHE_NAME", formKey, formDefine);
        } else {
            formDefine = (DesignFormDefine)dataWrapper.get();
        }
        return formDefine;
    }

    public DesignFormGroupDefine getFormGroupByFormId(String formKey) {
        Object parentGroup;
        DataWrapper<DesignFormGroupDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formKey + ":form_group", DesignFormGroupDefine.class);
        if (!dataWrapper.isPresent()) {
            List groups = this.designTimeViewController.getFormGroupsByFormId(formKey);
            parentGroup = !CollectionUtils.isEmpty(groups) ? (DesignFormGroupDefine)groups.get(0) : null;
            this.transferCacheService.put("TASK_CACHE_NAME", formKey + ":form_group", parentGroup);
        } else {
            parentGroup = (DesignFormGroupDefine)dataWrapper.get();
        }
        return parentGroup;
    }

    public DesignPrintTemplateSchemeDefine getPrintTemplateSchemeDefine(String printSchemeKey) throws TransferException {
        DataWrapper<DesignPrintTemplateSchemeDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", printSchemeKey, DesignPrintTemplateSchemeDefine.class);
        if (!dataWrapper.isPresent()) {
            try {
                DesignPrintTemplateSchemeDefine printDefine = this.printDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeKey, false);
                this.transferCacheService.put("TASK_CACHE_NAME", printSchemeKey, printDefine);
                return printDefine;
            }
            catch (Exception e) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
            }
        }
        return (DesignPrintTemplateSchemeDefine)dataWrapper.get();
    }

    public DesignFormulaSchemeDefine getFormulaSchemeDefine(String formulaSchemeKey) {
        DataWrapper<DesignFormulaSchemeDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formulaSchemeKey, DesignFormulaSchemeDefine.class);
        if (!dataWrapper.isPresent()) {
            DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
            this.transferCacheService.put("TASK_CACHE_NAME", formulaSchemeKey, formulaSchemeDefine);
            return formulaSchemeDefine;
        }
        return (DesignFormulaSchemeDefine)dataWrapper.get();
    }

    public ArrayList<DesignFormulaSchemeDefine> getFormulaSchemeDefineBySchemeKey(String formSchemeKey) {
        DataWrapper<ArrayList> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formSchemeKey + ":formulas", ArrayList.class);
        if (!dataWrapper.isPresent()) {
            List formulaSchemes = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
            ArrayList<DesignFormulaSchemeDefine> allFormulas = new ArrayList<DesignFormulaSchemeDefine>();
            for (DesignFormulaSchemeDefine formulaScheme : formulaSchemes) {
                allFormulas.add(formulaScheme);
            }
            this.transferCacheService.put("TASK_CACHE_NAME", formSchemeKey + ":formulas", allFormulas);
            for (DesignFormulaSchemeDefine define : allFormulas) {
                this.transferCacheService.put("TASK_CACHE_NAME", define.getKey(), define);
            }
            return allFormulas;
        }
        return (ArrayList)dataWrapper.get();
    }

    public DesignPrintTemplateDefine getPrintTemplateDefine(String printTemplateKey) throws TransferException {
        DataWrapper<DesignPrintTemplateDefine> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", printTemplateKey, DesignPrintTemplateDefine.class);
        if (!dataWrapper.isPresent()) {
            try {
                DesignPrintTemplateDefine define = this.printDesignTimeController.queryPrintTemplateDefine(printTemplateKey, false);
                this.transferCacheService.put("TASK_CACHE_NAME", printTemplateKey, define);
                return define;
            }
            catch (Exception e) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
            }
        }
        return (DesignPrintTemplateDefine)dataWrapper.get();
    }

    public DesignPrintComTemDefine getPrintComTemDefine(String key) throws TransferException {
        String cacheKey = "print_com_tem_" + key;
        DataWrapper<DesignPrintComTemDefine> wrapper = this.transferCacheService.get("TASK_CACHE_NAME", cacheKey, DesignPrintComTemDefine.class);
        if (!wrapper.isPresent()) {
            DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(key);
            this.transferCacheService.put("TASK_CACHE_NAME", cacheKey, define);
            return define;
        }
        return (DesignPrintComTemDefine)wrapper.get();
    }

    public DesignPrintSettingDefine getPrintSettingDefine(String key) throws TransferException {
        DataWrapper<DesignPrintSettingDefine> wrapper = this.transferCacheService.get("TASK_CACHE_NAME", key, DesignPrintSettingDefine.class);
        if (!wrapper.isPresent()) {
            String[] split = FormulaGuidParse.parseKey(key);
            DesignPrintSettingDefine define = this.designTimePrintController.getPrintSettingDefine(split[0], split[1]);
            this.transferCacheService.put("TASK_CACHE_NAME", key, define);
            return define;
        }
        return (DesignPrintSettingDefine)wrapper.get();
    }

    public SingleConfigInfo getMappingConfigInfo(String key) {
        DataWrapper<SingleConfigInfo> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", key, SingleConfigInfo.class);
        if (!dataWrapper.isPresent()) {
            SingleConfigInfo mappingConfigInfo = this.singleMappingService.getMappingConfigInfo(key);
            this.transferCacheService.put("TASK_CACHE_NAME", key, mappingConfigInfo);
            return mappingConfigInfo;
        }
        return (SingleConfigInfo)dataWrapper.get();
    }

    public ArrayList<DesignFormDefine> getDesignFormDefineByGroupKey(String groupKey) {
        DataWrapper<ArrayList> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", groupKey + ":forms", ArrayList.class);
        if (!dataWrapper.isPresent()) {
            List designFormDefines = this.designTimeViewController.queryAllSoftFormDefinesInGroup(groupKey);
            ArrayList<DesignFormDefine> allFormsInGroup = new ArrayList<DesignFormDefine>(designFormDefines);
            this.transferCacheService.put("TASK_CACHE_NAME", groupKey + ":forms", allFormsInGroup);
            for (DesignFormDefine designFormDefine : designFormDefines) {
                this.transferCacheService.put("TASK_CACHE_NAME", designFormDefine.getKey(), designFormDefine);
            }
            return allFormsInGroup;
        }
        return (ArrayList)dataWrapper.get();
    }

    public ArrayList<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) throws TransferException {
        DataWrapper<ArrayList> dataWrapper = this.transferCacheService.get("TASK_CACHE_NAME", formSchemeKey + ":printSchemes", ArrayList.class);
        if (!dataWrapper.isPresent()) {
            try {
                List printSchemes = this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey, false);
                ArrayList<DesignPrintTemplateSchemeDefine> allPrint = new ArrayList<DesignPrintTemplateSchemeDefine>(printSchemes);
                this.transferCacheService.put("TASK_CACHE_NAME", formSchemeKey + ":printSchemes", allPrint);
                for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : allPrint) {
                    this.transferCacheService.put("TASK_CACHE_NAME", printTemplateSchemeDefine.getKey(), printTemplateSchemeDefine);
                }
                return allPrint;
            }
            catch (Exception e) {
                throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
            }
        }
        return (ArrayList)dataWrapper.get();
    }
}

