/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.GroupKeyInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.GroupKeyInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.internal.service3.FormDefineImportServiceImpl;
import nr.single.para.parain.service.IFormGroupDefineImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FormGroupDefineImportServiceImpl
implements IFormGroupDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(FormDefineImportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;

    @Override
    public String importFormGroupDefines(TaskImportContext importContext, String schemeKey) throws Exception {
        DesignFormGroupDefine group;
        String formCode;
        DesignFormGroupDefine fmdmGroup;
        List<Object> groupRepList;
        List groupMap = importContext.getParaInfo().getReportGroupList();
        List<RepInfo> sortRepList = importContext.getSortRepList();
        Map<String, List<DesignFormGroupDefine>> groupFormMap = importContext.getGroupFormMap();
        List<DesignFormGroupDefine> groupFormList = importContext.getGroupFormList();
        importContext.getEntityFieldMaps().clear();
        importContext.getEntityTableMaps().clear();
        importContext.getFormCache().clear();
        sortRepList.clear();
        groupFormMap.clear();
        HashMap<String, DesignFormGroupDefine> groupDic = new HashMap<String, DesignFormGroupDefine>();
        HashMap groupRepDic = new HashMap();
        HashMap<String, Object> HasUpdateReps = new HashMap<String, Object>();
        HashMap<String, RepInfo> repDic = new HashMap<String, RepInfo>();
        List repList = importContext.getParaInfo().getRepInfos();
        for (RepInfo info : repList) {
            repDic.put(info.getCode().toUpperCase(), info);
        }
        for (GroupKeyInfo entry : groupMap) {
            String repFlag = entry.getCode();
            if (!repDic.containsKey(repFlag.toUpperCase())) continue;
            Object rep = (RepInfo)repDic.get(repFlag.toUpperCase());
            String GroupName = entry.getValue();
            groupRepList = null;
            if (groupRepDic.containsKey(GroupName)) {
                groupRepList = (List)groupRepDic.get(GroupName);
            } else {
                groupRepList = new ArrayList();
                groupRepDic.put(GroupName, groupRepList);
            }
            groupRepList.add(rep);
        }
        this.DeleteDefaultFormGroup(schemeKey);
        HashMap<String, DesignFormGroupDefine> importFormGroups = new HashMap<String, DesignFormGroupDefine>();
        List netFormGroups = this.viewController.queryAllGroupsByFormScheme(schemeKey);
        if (groupMap.size() > 0) {
            fmdmGroup = null;
            if (repDic.containsKey("FMDM")) {
                fmdmGroup = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), "\u5c01\u9762\u4ee3\u7801", true, groupDic, groupFormList);
                this.addFormToGroup("FMDM", fmdmGroup, groupFormMap);
                sortRepList.add((RepInfo)repDic.get("FMDM"));
                HasUpdateReps.put("FMDM", repDic.get("FMDM"));
                if (fmdmGroup != null) {
                    importFormGroups.put(fmdmGroup.getKey(), fmdmGroup);
                }
            }
            for (Object groupName : importContext.getParaInfo().getReportGroupNameList()) {
                if (!groupRepDic.containsKey(groupName)) continue;
                groupRepList = (List)groupRepDic.get(groupName);
                if (groupRepList.size() > 0) {
                    DesignFormGroupDefine formGroup = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), (String)groupName, true, groupDic, groupFormList);
                    if (formGroup == null) continue;
                    importFormGroups.put(formGroup.getKey(), formGroup);
                    continue;
                }
                log.info("\u65e0\u6cd5\u5bfc\u5165\u62a5\u8868\u5206\u7ec4\uff0c\u56e0\u5206\u7ec4\u5185\u65e0\u62a5\u8868:{}", groupName);
            }
        } else {
            fmdmGroup = null;
            if (repDic.containsKey("FMDM") && (fmdmGroup = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), "\u5c01\u9762\u4ee3\u7801", false, groupDic, groupFormList)) != null) {
                importFormGroups.put(fmdmGroup.getKey(), fmdmGroup);
            }
            DesignFormGroupDefine group2 = null;
            if (repList.size() > 1 && (group2 = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), importContext.getParaInfo().getTaskName(), false, groupDic, groupFormList)) != null) {
                importFormGroups.put(group2.getKey(), group2);
            }
            for (RepInfo rep : importContext.getParaInfo().getRepInfos()) {
                String formCode2 = rep.getCode().toUpperCase();
                if ("FMDM".equalsIgnoreCase(formCode2)) {
                    this.addFormToGroup("FMDM", fmdmGroup, groupFormMap);
                } else {
                    this.addFormToGroup(formCode2, group2, groupFormMap);
                }
                sortRepList.add(rep);
                HasUpdateReps.put(formCode2, rep);
            }
        }
        for (GroupKeyInfo entry : groupMap) {
            formCode = entry.getCode().toUpperCase();
            if (!repDic.containsKey(formCode)) continue;
            group = null;
            if (groupDic.containsKey(entry.getValue())) {
                group = (DesignFormGroupDefine)groupDic.get(entry.getValue());
            } else {
                group = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), entry.getValue(), true, groupDic, groupFormList);
                if (group != null) {
                    importFormGroups.put(group.getKey(), group);
                }
            }
            this.addFormToGroup(formCode, group, groupFormMap);
            RepInfo rep = (RepInfo)repDic.get(formCode);
            if (HasUpdateReps.containsKey(formCode)) continue;
            sortRepList.add((RepInfo)repDic.get(formCode));
            HasUpdateReps.put(formCode, rep);
        }
        for (Object rep : importContext.getParaInfo().getRepInfos()) {
            formCode = rep.getCode().toUpperCase();
            if (HasUpdateReps.containsKey(formCode)) continue;
            sortRepList.add((RepInfo)rep);
            group = this.getGroupByName(importContext, schemeKey, importContext.getParaInfo(), "\u5176\u4ed6", true, groupDic, groupFormList);
            group.setCondition("1<>1");
            HasUpdateReps.put(formCode, rep);
            this.addFormToGroup(formCode, group, groupFormMap);
            if (group == null) continue;
            importFormGroups.put(group.getKey(), group);
        }
        if (importContext.getImportOption().isOverWriteAll() && netFormGroups != null && !netFormGroups.isEmpty()) {
            for (DesignFormGroupDefine oldGroup : netFormGroups) {
                if (importFormGroups.containsKey(oldGroup.getKey())) continue;
                oldGroup.setOrder(OrderGenerator.newOrder());
                this.viewController.updateFormGroup(oldGroup);
            }
        }
        return null;
    }

    @Override
    public void updateFormGroup(List<DesignFormGroupDefine> groups, boolean isFMDM, String formKey) {
        if (null != groups) {
            ArrayList<DesignFormGroupDefine> existGroups = new ArrayList<DesignFormGroupDefine>();
            HashMap<String, DesignFormGroupDefine> curFormGroups = new HashMap<String, DesignFormGroupDefine>();
            for (DesignFormGroupDefine group : groups) {
                curFormGroups.put(group.getKey(), group);
            }
            List formIngroups = this.viewController.getFormGroupsByFormId(formKey);
            for (DesignFormGroupDefine agroup : formIngroups) {
                if (curFormGroups.containsKey(agroup.getKey())) {
                    existGroups.add(agroup);
                    continue;
                }
                if (isFMDM) continue;
                this.viewController.removeFormFromGroup(formKey, agroup.getKey());
            }
            if (existGroups.size() != groups.size()) {
                for (DesignFormGroupDefine group : groups) {
                    if (existGroups.contains(group)) continue;
                    this.viewController.addFormToGroup(formKey, group.getKey());
                }
            }
        }
    }

    @Override
    public void importDataGroups(TaskImportContext importContext) throws Exception {
        DesignTaskDefine task = importContext.getTaskDefine();
        DesignDataScheme dataScheme = this.dataSchemeService.getDataScheme(task.getDataScheme());
        DesignDataGroup dataGroup = this.createNewDataGroup(null, task.getKey(), task.getTitle(), task.getTaskCode(), importContext.getTaskDefine().getTitle() + "\u9ed8\u8ba4\u6307\u6807\u5206\u7ec4", OrderGenerator.newOrder(), dataScheme.getKey());
        importContext.setTaskFieldGroupKey(dataGroup.getKey());
        DesignDataGroup dataGroup2 = dataGroup;
        importContext.getSchemeInfoCache().getFormGroupMapDataGroups().clear();
        List<DesignFormGroupDefine> groupFormList = importContext.getGroupFormList();
        for (DesignFormGroupDefine group : groupFormList) {
            DesignDataGroup dataGroup3 = this.createNewDataGroup(dataGroup2.getKey(), group.getKey(), group.getTitle(), group.getCode(), group.getTitle() + "", OrderGenerator.newOrder(), dataScheme.getKey());
            if (dataGroup3 == null) continue;
            importContext.getSchemeInfoCache().getFormGroupMapDataGroups().put(group.getKey(), dataGroup3.getKey());
        }
    }

    @Override
    public DesignDataGroup createNewDataGroup(String parentKey, String key, String title, String code, String descript, String order, String dataSchemeKey) throws Exception {
        List dataGroups;
        HashMap<String, DesignDataGroup> dataGroupMap = new HashMap<String, DesignDataGroup>();
        if (StringUtils.isNotEmpty((String)parentKey)) {
            dataGroups = this.dataSchemeService.getDataGroupByParent(parentKey);
            for (DesignDataGroup group : dataGroups) {
                dataGroupMap.put(group.getTitle(), group);
            }
        } else {
            dataGroups = this.dataSchemeService.getDataGroupByScheme(dataSchemeKey);
            for (DesignDataGroup group : dataGroups) {
                if (!StringUtils.isEmpty((String)group.getParentKey())) continue;
                dataGroupMap.put(group.getTitle(), group);
            }
        }
        if (StringUtils.isNotEmpty((String)title) && title.length() > 40) {
            title = title.substring(0, 40);
        }
        DesignDataGroup group = null;
        group = dataGroupMap.containsKey(title) ? (DesignDataGroup)dataGroupMap.get(title) : this.dataSchemeService.getDataGroup(key);
        if (null == group) {
            group = this.dataSchemeService.initDataGroup();
            group.setKey(key);
            if (StringUtils.isNotEmpty((String)code) && code.length() > 20) {
                code = code.substring(0, 20);
            }
            if (StringUtils.isNotEmpty((String)descript) && descript.length() > 40) {
                descript = descript.substring(0, 40);
            }
            group.setCode(code);
            group.setTitle(title);
            group.setOrder(order);
            group.setDesc(descript);
            group.setParentKey(parentKey);
            group.setDataSchemeKey(dataSchemeKey);
            this.dataSchemeService.insertDataGroup(group);
        } else {
            group.setTitle(title);
            group.setUpdateTime(Instant.now());
            this.dataSchemeService.updateDataGroup(group);
        }
        return group;
    }

    @Override
    public void deleteOtherFormgGroups(TaskImportContext importContext, String formSchemeKey) throws Exception {
        if (!importContext.getImportOption().isOverWriteAll()) {
            return;
        }
        List<DesignFormGroupDefine> groupFormList = importContext.getGroupFormList();
        if (groupFormList == null || groupFormList.isEmpty()) {
            return;
        }
        HashMap<String, DesignFormGroupDefine> importFormGroups = new HashMap<String, DesignFormGroupDefine>();
        for (DesignFormGroupDefine importGroup : groupFormList) {
            importFormGroups.put(importGroup.getKey(), importGroup);
        }
        List netFormGroups = this.viewController.queryAllGroupsByFormScheme(formSchemeKey);
        for (DesignFormGroupDefine oldGroup : netFormGroups) {
            List forms = this.viewController.queryAllSoftFormDefinesInGroup(oldGroup.getKey());
            if (forms != null && !forms.isEmpty()) continue;
            importContext.info(log, "\u5220\u9664\u8868\u5355\u5206\u7ec4:" + oldGroup.getTitle() + ",");
            this.viewController.deleteFormGroup(oldGroup.getKey());
        }
    }

    private void addFormToGroup(String formCode, DesignFormGroupDefine group, Map<String, List<DesignFormGroupDefine>> groupFormMap) {
        if (groupFormMap.containsKey(formCode)) {
            List<DesignFormGroupDefine> formGroupList = groupFormMap.get(formCode);
            formGroupList.add(group);
        } else {
            ArrayList<DesignFormGroupDefine> formGroupList = new ArrayList<DesignFormGroupDefine>();
            formGroupList.add(group);
            groupFormMap.put(formCode, formGroupList);
        }
    }

    private DesignFormGroupDefine getGroupByName(TaskImportContext importContext, String schemeKey, ParaInfo paraInfo, String groupName, boolean isNewOrder, Map<String, DesignFormGroupDefine> groupDic, List<DesignFormGroupDefine> groupFormList) {
        DesignFormGroupDefine group = null;
        List groups = this.viewController.queryFormGroupByTitleInFormScheme(schemeKey, groupName);
        if (null == groups || groups.size() == 0) {
            group = this.createNewFormGroup(importContext, schemeKey, groupName, "", OrderGenerator.newOrder(), OrderGenerator.newOrder());
        } else {
            group = (DesignFormGroupDefine)groups.get(0);
            if (isNewOrder) {
                group.setOrder(OrderGenerator.newOrder());
                this.viewController.updateFormGroup(group);
            }
        }
        groupDic.put(group.getTitle(), group);
        groupFormList.add(group);
        return group;
    }

    private void DeleteDefaultFormGroup(String schemeKey) throws Exception {
        List groups = this.viewController.queryAllGroupsByFormScheme(schemeKey);
        if (null != groups && groups.size() == 1) {
            DesignFormGroupDefine group = (DesignFormGroupDefine)groups.get(0);
            boolean groupNeedDelete = "\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4".equalsIgnoreCase(group.getTitle());
            List forms = this.viewController.queryAllSoftFormDefinesInGroup(group.getKey());
            boolean formNeedDelete = forms.size() <= 1;
            DesignFormDefine form = null;
            if (1 == forms.size()) {
                form = (DesignFormDefine)forms.get(0);
                List links = this.viewController.getAllLinksInForm(form.getKey());
                boolean bl = formNeedDelete = links.size() == 0 && "\u5de5\u4f5c\u88681".equalsIgnoreCase(form.getTitle());
            }
            if (groupNeedDelete && formNeedDelete) {
                if (null != form) {
                    this.viewController.deleteFormDefine(form.getKey());
                }
                this.viewController.deleteFormGroup(group.getKey());
            }
        }
    }

    private DesignFormGroupDefine createNewFormGroup(TaskImportContext importContext, String schemeKey, String title, String condition, String code, String order) {
        DesignFormGroupDefine group = this.viewController.createFormGroup();
        group.setTitle(title);
        group.setFormSchemeKey(schemeKey);
        group.setCode(code);
        group.setOrder(order);
        group.setCondition(condition);
        group.setOwnerLevelAndId(importContext.getCurServerCode());
        this.viewController.insertFormGroup(group);
        return group;
    }
}

