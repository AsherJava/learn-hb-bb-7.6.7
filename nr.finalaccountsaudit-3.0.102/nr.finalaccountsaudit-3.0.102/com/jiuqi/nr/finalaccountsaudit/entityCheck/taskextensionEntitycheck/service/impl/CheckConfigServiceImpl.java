/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.service.impl;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigItemStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigRequsetParam;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.MatchTypeStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.service.ICheckConfigService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckConfigServiceImpl
implements ICheckConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckConfigServiceImpl.class);
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    TaskExtConfigController taskExtConfigController;

    @Override
    public List<SelectStructure> getTasks() {
        ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
        List taskDataList = this.dataEntryParamService.getRuntimeTaskList();
        if (taskDataList != null) {
            for (TaskData taskData : taskDataList) {
                SelectStructure selectStructure = new SelectStructure();
                selectStructure.setTitle(taskData.getTitle());
                selectStructure.setKey(taskData.getKey());
                list.add(selectStructure);
            }
        }
        return list;
    }

    @Override
    public List<SelectStructure> removeSavedTasks(ConfigRequsetParam param) {
        Set<String> formSchemeKeySet = this.querySavedFormScemeKeyList(param.getTaskKey(), param.getFormSchemeKey());
        try {
            ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
            List taskDefines = this.runTimeViewController.getAllTaskDefines();
            block2: for (TaskDefine taskDefine : taskDefines) {
                List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    if (formSchemeKeySet.contains(formSchemeDefine.getKey())) continue;
                    SelectStructure selectStructure = new SelectStructure();
                    selectStructure.setTitle(taskDefine.getTitle());
                    selectStructure.setKey(taskDefine.getKey());
                    list.add(selectStructure);
                    continue block2;
                }
            }
            return list;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Set<String> querySavedFormScemeKeyList(String taskKey, String formSchemeKey) {
        HashSet<String> formSchemeKeySet = new HashSet<String>();
        ExtensionBasicModel basicModel = this.taskExtConfigController.getTaskExtConfigDefineBySchemakey(taskKey, formSchemeKey, "taskextension-entitycheck");
        EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicModel.getExtInfoModel();
        if (entityCheckConfigData.getEntityCheckEnable()) {
            for (ConfigInfo configInfo : entityCheckConfigData.getConfigInfos()) {
                formSchemeKeySet.add(configInfo.getAssFormSchemeKey());
            }
        }
        return formSchemeKeySet;
    }

    @Override
    public List<SelectStructure> removeSavedFormSchemesByTask(ConfigRequsetParam param) {
        Set<String> formSchemeKeySet = this.querySavedFormScemeKeyList(param.getTaskKey(), param.getFormSchemeKey());
        try {
            ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(param.getAssTaskKey());
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (formSchemeKeySet.contains(formSchemeDefine.getKey())) continue;
                SelectStructure selectStructure = new SelectStructure();
                selectStructure.setTitle(formSchemeDefine.getTitle());
                selectStructure.setKey(formSchemeDefine.getKey());
                list.add(selectStructure);
            }
            return list;
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<SelectStructure> getFormSchemes(ConfigRequsetParam param) {
        ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
        try {
            List formSchemeDataList = this.dataEntryParamService.runtimeFormSchemeList(param.getTaskKey());
            for (FormSchemeData formSchemeData : formSchemeDataList) {
                SelectStructure selectStructure = new SelectStructure();
                selectStructure.setTitle(formSchemeData.getTitle());
                selectStructure.setKey(formSchemeData.getKey());
                list.add(selectStructure);
            }
        }
        catch (Exception e) {
            return null;
        }
        return list;
    }

    @Override
    public int getPeriodType(ConfigRequsetParam param) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        return formSchemeDefine == null ? 0 : formSchemeDefine.getPeriodType().type();
    }

    @Override
    public List<MatchTypeStructure> getMatching() {
        ArrayList<MatchTypeStructure> list = new ArrayList<MatchTypeStructure>();
        list.add(new MatchTypeStructure(TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY.getValue(), "\u6309\u4e3b\u4f53\u4e3b\u952e\u5339\u914d"));
        list.add(new MatchTypeStructure(TaskLinkMatchingType.MATCHING_TYPE_CODE.getValue(), "\u6309\u4e3b\u4f53\u4ee3\u7801\u5339\u914d"));
        list.add(new MatchTypeStructure(TaskLinkMatchingType.MATCHING_TYPE_TITLE.getValue(), "\u6309\u4e3b\u4f53\u540d\u79f0\u5339\u914d"));
        list.add(new MatchTypeStructure(TaskLinkMatchingType.FORM_TYPE_EXPRESSION.getValue(), "\u6309\u8868\u8fbe\u5f0f\u5339\u914d"));
        return list;
    }

    @Override
    public List<ConfigItemStructure> getConfigItemsByType(ConfigRequsetParam param) {
        String formSchemeKey = param.getFormSchemeKey();
        ArrayList<ConfigItemStructure> configItems = new ArrayList<ConfigItemStructure>();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormType formType = formDefine.getFormType();
                if (!formType.equals((Object)FormType.FORM_TYPE_NEWFMDM) && !formType.equals((Object)FormType.FORM_TYPE_FMDM)) continue;
                for (DataLinkDefine dl : this.runTimeViewController.getAllLinksInForm(formDefine.getKey())) {
                    if (dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_INFO) || dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)) {
                        DataField field = this.runtimeDataSchemeService.getDataField(dl.getLinkExpression());
                        if (field == null) continue;
                        configItems.add(new ConfigItemStructure(field));
                        continue;
                    }
                    if (!dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)) continue;
                    FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                    fmdmAttributeDTO.setEntityId(formSchemeDefine.getDw());
                    fmdmAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                    fmdmAttributeDTO.setAttributeCode(dl.getLinkExpression());
                    fmdmAttributeDTO.setZBKey(dl.getLinkExpression());
                    IFMDMAttribute fmAttribute = this.fmdmAttributeService.queryByZbKey(fmdmAttributeDTO);
                    if (fmAttribute == null) continue;
                    configItems.add(new ConfigItemStructure(fmAttribute));
                }
            }
        }
        return configItems;
    }

    @Override
    public List<EnumStructure> getBBLXEnumData(ConfigRequsetParam param) {
        List<EnumStructure> enums = new ArrayList<EnumStructure>();
        String formSchemeKey = param.getFormSchemeKey();
        String bblxCode = param.getBblxCode();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (formSchemeDefine != null && formDefines != null) {
            for (FormDefine formDefine : formDefines) {
                FormType formType = formDefine.getFormType();
                if (!formType.equals((Object)FormType.FORM_TYPE_NEWFMDM) && !formType.equals((Object)FormType.FORM_TYPE_FMDM)) continue;
                for (DataLinkDefine dl : this.runTimeViewController.getAllLinksInForm(formDefine.getKey())) {
                    if (dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_INFO) || dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)) {
                        DataField field = this.runtimeDataSchemeService.getDataField(dl.getLinkExpression());
                        if (!field.getCode().equals(bblxCode) || field.getRefDataEntityKey() == null) continue;
                        enums = this.getEnumData(field.getRefDataEntityKey(), formSchemeKey);
                        continue;
                    }
                    if (!dl.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)) continue;
                    FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                    fmdmAttributeDTO.setEntityId(formSchemeDefine.getDw());
                    fmdmAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                    fmdmAttributeDTO.setAttributeCode(dl.getLinkExpression());
                    fmdmAttributeDTO.setZBKey(dl.getLinkExpression());
                    IFMDMAttribute fmAttribute = this.fmdmAttributeService.queryByZbKey(fmdmAttributeDTO);
                    if (fmAttribute == null || !fmAttribute.getCode().equals(bblxCode) || fmAttribute.getReferEntityId() == null) continue;
                    enums = this.getEnumData(fmAttribute.getReferEntityId(), formSchemeKey);
                }
            }
        }
        return enums;
    }

    private List<EnumStructure> getEnumData(String entityId, String formSchemeKey) {
        ArrayList<EnumStructure> enumStructures = new ArrayList<EnumStructure>();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityDefine.getId());
        try {
            IEntityTable table = this.entityQueryHelper.buildEntityTable(entityViewDefine, null, formSchemeKey, false);
            List rows = table.getAllRows();
            for (IEntityRow row : rows) {
                EnumStructure enumStructure = new EnumStructure();
                enumStructure.setKey(row.getEntityKeyData());
                enumStructure.setValue(row.getTitle());
                enumStructures.add(enumStructure);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        enumStructures.sort(new Comparator<EnumStructure>(){

            @Override
            public int compare(EnumStructure o1, EnumStructure o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return enumStructures;
    }

    @Override
    public ExtensionBasicModel<?> queryConfigData(ConfigRequsetParam param) {
        ExtensionBasicModel basicModel = this.taskExtConfigController.getTaskExtConfigDefineBySchemakey(param.getTaskKey(), param.getFormSchemeKey(), "taskextension-entitycheck");
        EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicModel.getExtInfoModel();
        if (entityCheckConfigData.getEntityCheckEnable()) {
            for (ConfigInfo configInfo : entityCheckConfigData.getConfigInfos()) {
                FormSchemeDefine formSchemeDefine;
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(configInfo.getAssTaskKey());
                if (taskDefine != null) {
                    configInfo.setAssTaskTitle(taskDefine.getTitle());
                }
                if ((formSchemeDefine = this.runTimeViewController.getFormScheme(configInfo.getAssFormSchemeKey())) == null) continue;
                configInfo.setAssFormSchemeTitle(formSchemeDefine.getTitle());
            }
        }
        return basicModel;
    }
}

