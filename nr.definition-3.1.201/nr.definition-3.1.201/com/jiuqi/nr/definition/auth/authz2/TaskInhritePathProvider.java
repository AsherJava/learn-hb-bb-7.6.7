/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.authz2.AllFormulaSchemeResoucre;
import com.jiuqi.nr.definition.auth.authz2.FormSchemeResource;
import com.jiuqi.nr.definition.auth.authz2.FormulaSchemeResoucre;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class TaskInhritePathProvider
implements SuperInheritPathProvider {
    private static final String ALL_TASK_GROUP_KEY = "00000000-0000-0000-0000-000000000000";
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IPrintRunTimeController printController;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;

    public String getResourceCategoryId() {
        return "TaskResourceCategory-1292dcf2da1e";
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return null;
    }

    private String getResourceId(Object resource) {
        if (resource instanceof String) {
            return String.valueOf(resource);
        }
        if (resource instanceof Identifiable) {
            return ((Identifiable)resource).getId();
        }
        if (resource instanceof Resource) {
            return ((Resource)resource).getId();
        }
        throw new RuntimeException(String.format("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u51fa\u9519: %s", this.getClass().toString()));
    }

    public Resource getParent(Object resource) {
        List<Object> parent = this.getParent(null, resource);
        if (parent.isEmpty()) {
            return null;
        }
        Object ret = parent.get(0);
        if (ret instanceof FormSchemeResource) {
            FormSchemeResource formScheme = (FormSchemeResource)ret;
            List<Object> formSchemeResource = this.createResource(ResourceType.FORM_SCHEME, (IBaseMetaItem)formScheme.getFormSchemeDefine());
            return (Resource)formSchemeResource.get(0);
        }
        return (Resource)ret;
    }

    public List<Resource> getParents(Privilege privilege, Object resource) {
        List<Object> parents = this.getParent(null, resource);
        if (parents.isEmpty()) {
            return null;
        }
        ArrayList<Resource> parentsResource = new ArrayList<Resource>();
        for (Object parent : parents) {
            if (parent instanceof FormSchemeResource) {
                FormSchemeResource formScheme = (FormSchemeResource)parent;
                List<Object> formSchemeResource = this.createResource(ResourceType.FORM_SCHEME, (IBaseMetaItem)formScheme.getFormSchemeDefine());
                parentsResource.add((Resource)formSchemeResource.get(0));
                continue;
            }
            parentsResource.add((Resource)parent);
        }
        return parentsResource;
    }

    public List<Object> getParent(Privilege privilege, Object resource) {
        String resourceId = this.getResourceId(resource);
        if (resourceId == null) {
            return null;
        }
        ResourceType resourceType = ResourceType.parseFrom(resourceId);
        if (this.notValid(resourceType, privilege)) {
            return Collections.emptyList();
        }
        try {
            switch (resourceType) {
                case TASK_GROUP: {
                    TaskGroupDefine gr;
                    TaskGroupDefine groupDefine;
                    String taskGroupKey = resourceType.toObjectId(resourceId);
                    if (StringUtils.hasText(taskGroupKey) && !taskGroupKey.equals(ALL_TASK_GROUP_KEY) && StringUtils.hasText((groupDefine = this.viewController.queryTaskGroupDefine(taskGroupKey)).getParentKey()) && !StringUtils.hasText((gr = this.viewController.queryTaskGroupDefine(groupDefine.getParentKey())).getParentKey())) {
                        return this.createResource(ResourceType.TASK_GROUP, (IBaseMetaItem)gr);
                    }
                    break;
                }
                case TASK: {
                    String taskKey = resourceType.toObjectId(resourceId);
                    List<TaskGroupDefine> groups = this.viewController.getGroupByTask(taskKey);
                    if (!CollectionUtils.isEmpty(groups)) {
                        ArrayList<Object> groupItems = new ArrayList<Object>();
                        groups.forEach(group -> groupItems.add(ResourceGroupItem.createResourceGroupItem((String)ResourceType.TASK_GROUP.toResourceId(group.getKey()), (String)group.getTitle(), (boolean)true)));
                        return groupItems;
                    }
                    break;
                }
                case FORM_SCHEME: {
                    TaskDefine taskDefine;
                    FormSchemeDefine formScheme = resource instanceof FormSchemeResource ? ((FormSchemeResource)resource).getFormSchemeDefine() : this.viewController.getFormScheme(resourceType.toObjectId(resourceId));
                    if (formScheme != null && formScheme.getTaskKey() != null && (taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey())) != null) {
                        return this.createResource(ResourceType.TASK, (IBaseMetaItem)taskDefine);
                    }
                    break;
                }
                case ALL_FORM: {
                    String formSchemeKey = ResourceType.ALL_FORM.toObjectId(resourceId);
                    FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
                    if (schemeDefine != null) {
                        return Arrays.asList(new FormSchemeResource(schemeDefine));
                    }
                    break;
                }
                case ALL_FORMULA_SCHEME: {
                    FormSchemeDefine schemeDefine;
                    if (resource instanceof AllFormulaSchemeResoucre) {
                        schemeDefine = ((AllFormulaSchemeResoucre)resource).getFormScheme();
                    } else {
                        String formSchemeKey = ResourceType.ALL_FORMULA_SCHEME.toObjectId(resourceId);
                        schemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
                    }
                    if (schemeDefine != null) {
                        return Arrays.asList(new FormSchemeResource(schemeDefine));
                    }
                    break;
                }
                case ALL_PRINT_SCHEME: {
                    String formSchemeKey = ResourceType.ALL_PRINT_SCHEME.toObjectId(resourceId);
                    FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
                    if (schemeDefine != null) {
                        return Arrays.asList(new FormSchemeResource(schemeDefine));
                    }
                    break;
                }
                case FORM_GROUP: {
                    FormSchemeDefine schemeDefine;
                    FormGroupDefine formGroup = this.viewController.queryFormGroup(resourceType.toObjectId(resourceId));
                    if (formGroup != null && formGroup.getFormSchemeKey() != null && (schemeDefine = this.runTimeViewController.getFormScheme(formGroup.getFormSchemeKey())) != null) {
                        return this.createResource(ResourceType.ALL_FORM, schemeDefine.getKey());
                    }
                    break;
                }
                case FORM: {
                    FormGroupDefine formGroupDefine;
                    List<FormGroupDefine> formGroups = this.viewController.getFormGroupsByFormKey(resourceType.toObjectId(resourceId));
                    if (formGroups != null && !formGroups.isEmpty() && StringUtils.hasLength((formGroupDefine = formGroups.get(0)).getKey())) {
                        return this.createResource(ResourceType.FORM_GROUP, (IBaseMetaItem)formGroupDefine);
                    }
                    break;
                }
                case FORMULA_SCHEME: {
                    FormSchemeDefine schemeDefine;
                    if (resource instanceof FormulaSchemeResoucre) {
                        return this.createAllFormulaSchemeResource(((FormulaSchemeResoucre)resource).getFormScheme());
                    }
                    FormulaSchemeDefine formulaScheme = this.formulaController.queryFormulaSchemeDefine(resourceType.toObjectId(resourceId));
                    if (formulaScheme != null && formulaScheme.getFormSchemeKey() != null && (schemeDefine = this.runTimeViewController.getFormScheme(formulaScheme.getFormSchemeKey())) != null) {
                        return this.createResource(ResourceType.ALL_FORMULA_SCHEME, schemeDefine.getKey());
                    }
                    break;
                }
                case PRINT_SCHEME: {
                    PrintTemplateSchemeDefine printScheme = this.printController.queryPrintTemplateSchemeDefine(resourceType.toObjectId(resourceId));
                    if (printScheme != null && printScheme.getFormSchemeKey() != null) {
                        return this.createResource(ResourceType.ALL_PRINT_SCHEME, printScheme.getFormSchemeKey());
                    }
                    break;
                }
                case ALL_TASK_ORG_STRUCTURE: {
                    TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(resourceType.toObjectId(resourceId));
                    if (taskDefine != null) {
                        return this.createResource(ResourceType.TASK, (IBaseMetaItem)taskDefine);
                    }
                    break;
                }
                case TASK_ORG_STRUCTURE: {
                    TaskOrgLinkDefine taskOrgLink = this.taskOrgLinkService.getByKey(resourceType.toObjectId(resourceId));
                    TaskDefine task = this.runTimeViewController.queryTaskDefine(taskOrgLink.getTask());
                    if (taskOrgLink != null) {
                        return this.createResource(ResourceType.ALL_TASK_ORG_STRUCTURE, (IBaseMetaItem)task);
                    }
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u9519\u8bef\u3002", e);
        }
        return Collections.emptyList();
    }

    private boolean notValid(ResourceType resourceType, Privilege privilege) {
        if (privilege == null) {
            return false;
        }
        if (resourceType == ResourceType.TASK || resourceType == ResourceType.TASK_GROUP || resourceType == ResourceType.ALL_FORM) {
            return "task_privilege_data_publish".equals(privilege.getId()) || "task_privilege_read_unpublish".equals(privilege.getId());
        }
        return false;
    }

    private List<Object> createResource(ResourceType resourceType, String key) {
        switch (resourceType) {
            case ALL_PRINT_SCHEME: {
                String resourceId = ResourceType.ALL_PRINT_SCHEME.toResourceId(key);
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)"\u6240\u6709\u6253\u5370\u65b9\u6848", (boolean)true));
            }
            case ALL_FORMULA_SCHEME: {
                String resourceId = ResourceType.ALL_FORMULA_SCHEME.toResourceId(key);
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)"\u6240\u6709\u516c\u5f0f\u65b9\u6848", (boolean)true));
            }
            case ALL_FORM: {
                String resourceId = ResourceType.ALL_FORM.toResourceId(key);
                return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)"\u6240\u6709\u62a5\u8868", (boolean)true));
            }
        }
        return Collections.emptyList();
    }

    private List<Object> createAllFormulaSchemeResource(FormSchemeDefine formScheme) {
        return Arrays.asList(new AllFormulaSchemeResoucre(formScheme));
    }

    private <E extends IBaseMetaItem> List<Object> createResource(ResourceType resourceType, IBaseMetaItem item) {
        String resourceId;
        switch (resourceType) {
            case TASK_GROUP: {
                resourceId = ResourceType.TASK_GROUP.toResourceId(item.getKey());
                break;
            }
            case TASK: {
                resourceId = ResourceType.TASK.toResourceId(item.getKey());
                break;
            }
            case FORM_SCHEME: {
                resourceId = ResourceType.FORM_SCHEME.toResourceId(item.getKey());
                break;
            }
            case FORM_GROUP: {
                resourceId = ResourceType.FORM_GROUP.toResourceId(item.getKey());
                break;
            }
            case ALL_TASK_ORG_STRUCTURE: {
                resourceId = ResourceType.ALL_TASK_ORG_STRUCTURE.toResourceId(item.getKey());
                break;
            }
            default: {
                throw new UnsupportedOperationException("unrecognized task resource id: " + item.getTitle());
            }
        }
        return Arrays.asList(ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)item.getTitle(), (boolean)true));
    }

    public List<String> getChildren(Privilege privilege, String resourceId) {
        if (!StringUtils.hasText(resourceId)) {
            return Collections.emptyList();
        }
        ResourceType resourceType = ResourceType.parseFrom(resourceId);
        String resourceObjectId = resourceType.toObjectId(resourceId);
        ArrayList<String> childrenResourceIds = new ArrayList<String>();
        try {
            switch (resourceType) {
                case TASK_GROUP: {
                    childrenResourceIds = new ArrayList();
                    List<TaskGroupDefine> taskGroupDefines = this.runTimeViewController.getChildTaskGroups(resourceObjectId, false);
                    List<TaskDefine> taskDefines = this.runTimeViewController.getAllRunTimeTasksInGroup(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.TASK_GROUP, taskGroupDefines));
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.TASK, taskDefines));
                    break;
                }
                case TASK: {
                    childrenResourceIds = new ArrayList();
                    List<FormSchemeDefine> formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(resourceObjectId);
                    childrenResourceIds.add(ResourceType.ALL_TASK_ORG_STRUCTURE.toResourceId(resourceObjectId));
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.FORM_SCHEME, formSchemeDefines));
                    break;
                }
                case FORM_SCHEME: {
                    childrenResourceIds = new ArrayList();
                    childrenResourceIds.add(ResourceType.ALL_FORM.toResourceId(resourceObjectId));
                    childrenResourceIds.add(ResourceType.ALL_FORMULA_SCHEME.toResourceId(resourceObjectId));
                    childrenResourceIds.add(ResourceType.ALL_PRINT_SCHEME.toResourceId(resourceObjectId));
                    break;
                }
                case ALL_FORM: {
                    childrenResourceIds = new ArrayList();
                    List<FormGroupDefine> formGroupDefines = this.runTimeViewController.getAllFormGroupsInFormScheme(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.FORM_GROUP, formGroupDefines));
                    break;
                }
                case ALL_FORMULA_SCHEME: {
                    childrenResourceIds = new ArrayList();
                    List<FormulaSchemeDefine> formulaSchemeDefines = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.FORMULA_SCHEME, formulaSchemeDefines));
                    break;
                }
                case ALL_PRINT_SCHEME: {
                    childrenResourceIds = new ArrayList();
                    List<PrintTemplateSchemeDefine> printSchemeDefines = this.printController.getAllPrintSchemeByFormSchemeWithoutBinary(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.PRINT_SCHEME, printSchemeDefines));
                    break;
                }
                case FORM_GROUP: {
                    childrenResourceIds = new ArrayList();
                    List<FormDefine> formDefines = this.runTimeViewController.getAllFormsInGroup(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.FORM, formDefines));
                    break;
                }
                case ALL_TASK_ORG_STRUCTURE: {
                    childrenResourceIds = new ArrayList();
                    List<TaskOrgLinkDefine> taskOrgLinkList = this.taskOrgLinkService.getByTask(resourceObjectId);
                    childrenResourceIds.addAll(this.convertResourceId(ResourceType.ALL_TASK_ORG_STRUCTURE, taskOrgLinkList));
                    break;
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u4e0b\u7ea7\u8d44\u6e90\u9519\u8bef", e);
        }
        return childrenResourceIds;
    }

    private List<String> convertResourceId(ResourceType resourceType, List<? extends IBaseMetaItem> resourceDefines) {
        ArrayList<String> resourceId = new ArrayList<String>();
        if (CollectionUtils.isEmpty(resourceDefines)) {
            return resourceId;
        }
        List resourceObjectIds = resourceDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        for (String resourceObjectId : resourceObjectIds) {
            resourceId.add(resourceType.toResourceId(resourceObjectId));
        }
        return resourceId;
    }

    public Set<String> computeIfChildren(String resourceId, Set<String> resourceIds) {
        return Collections.emptySet();
    }
}

