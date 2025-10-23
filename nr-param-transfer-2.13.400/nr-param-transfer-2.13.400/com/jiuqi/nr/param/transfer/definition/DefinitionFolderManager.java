/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.paramio.CustomBusiness
 *  nr.single.map.configurations.bean.SingleConfigInfo
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.paramio.CustomBusiness;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.IDesignTimeCacheProxy;
import com.jiuqi.nr.param.transfer.definition.NodeUtil;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.custom.CustomHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DefinitionFolderManager
extends AbstractFolderManager {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaRunTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimeCacheProxy iDesignTimeCacheProxy;
    @Autowired(required=false)
    private List<CustomBusiness> customBusiness;

    public List<FolderNode> getFolderNodes(String s) throws TransferException {
        try {
            if (s == null) {
                List childTaskGroups = this.designTimeViewController.getChildTaskGroups(null, false);
                List<FolderNode> list = NodeUtil.metaToFolderNodes(childTaskGroups, null, TransferNodeType.TASK_GROUP);
                List rootTasks = this.designTimeViewController.getAllTasksInGroup(null, false);
                list.addAll(NodeUtil.taskToFolderNodes(rootTasks, null));
                return list;
            }
            TransferGuid parse = TransferGuidParse.parseId(s);
            String key = parse.getKey();
            if (parse.isBusiness()) {
                return Collections.emptyList();
            }
            TransferNodeType transferNodeType = parse.getTransferNodeType();
            switch (transferNodeType) {
                case TASK_GROUP: {
                    List childTaskGroups = this.designTimeViewController.getChildTaskGroups(key, false);
                    List<FolderNode> list = NodeUtil.metaToFolderNodes(childTaskGroups, s, TransferNodeType.TASK_GROUP);
                    List rootTasks = this.designTimeViewController.getAllTasksInGroup(key, false);
                    list.addAll(NodeUtil.taskToFolderNodes(rootTasks, s));
                    return list;
                }
                case TASK: {
                    List formSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(key);
                    return NodeUtil.metaToFolderNodes(formSchemeDefines, s, TransferNodeType.FORM_SCHEME);
                }
                case FORM_SCHEME: {
                    ArrayList<FolderNode> list = new ArrayList<FolderNode>();
                    list.add(this.buildFormGroup(key));
                    list.add(this.buildFormulaGroup(key));
                    list.add(this.buildPrintGroup(key));
                    list.add(this.buildMappingGroup(key));
                    return list;
                }
                case FORM_GROUP: {
                    if (key.startsWith(TransferConsts.V_NODE_KEY)) {
                        String formSchemeKey = key.substring(TransferConsts.V_NODE_KEY.length());
                        List childFormGroups = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
                        return NodeUtil.metaToFolderNodes(childFormGroups, s, TransferNodeType.FORM_GROUP);
                    }
                    return new ArrayList<FolderNode>();
                }
                case PRINT_SCHEME: 
                case MAPPING_GROUP: 
                case FORMULA_FORM_GROUP: {
                    return Collections.emptyList();
                }
                case PARENT_GROUP: {
                    List tempSchemes = this.printDesignTimeController.getAllPrintSchemeByFormScheme(key, false);
                    return NodeUtil.metaToFolderNodes(tempSchemes, s, TransferNodeType.PRINT_SCHEME);
                }
                case FORMULA_GROUP: {
                    List defines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(key);
                    return NodeUtil.metaToFolderNodes(defines, s, TransferNodeType.FORMULA_SCHEME);
                }
                case FORMULA_SCHEME: {
                    DesignFormulaSchemeDefine define = this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                    if (define == null) {
                        return Collections.emptyList();
                    }
                    String formSchemeKey = define.getFormSchemeKey();
                    List childFormGroups = this.designTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
                    for (DesignFormGroupDefine childFormGroup : childFormGroups) {
                        String formulaId = FormulaGuidParse.toFormulaId(define.getKey(), childFormGroup.getKey());
                        childFormGroup.setKey(formulaId);
                    }
                    return NodeUtil.metaToFolderNodes(childFormGroups, s, TransferNodeType.FORMULA_FORM_GROUP);
                }
            }
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    private FolderNode buildFormGroup(String formSchemeKey) {
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, TransferConsts.V_NODE_KEY + formSchemeKey));
        folderNode.setName("\u8868\u5355\u5206\u7ec4");
        folderNode.setTitle("\u8868\u5355\u5206\u7ec4");
        folderNode.setType("\u8868\u5355\u5206\u7ec4");
        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formSchemeKey));
        return folderNode;
    }

    private FolderNode buildMappingGroup(String formSchemeKey) {
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid(TransferGuidParse.toTransferId(TransferNodeType.MAPPING_GROUP, formSchemeKey));
        folderNode.setName("\u6620\u5c04\u65b9\u6848\u5206\u7ec4");
        folderNode.setTitle("\u6620\u5c04\u65b9\u6848\u5206\u7ec4");
        folderNode.setType("\u6620\u5c04\u65b9\u6848\u5206\u7ec4");
        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formSchemeKey));
        return folderNode;
    }

    private FolderNode buildFormulaGroup(String formSchemeKey) {
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid(TransferGuidParse.toTransferId(TransferNodeType.FORMULA_GROUP, formSchemeKey));
        folderNode.setName("\u516c\u5f0f\u65b9\u6848\u5206\u7ec4");
        folderNode.setTitle("\u516c\u5f0f\u65b9\u6848\u5206\u7ec4");
        folderNode.setType("\u516c\u5f0f\u65b9\u6848\u5206\u7ec4");
        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formSchemeKey));
        return folderNode;
    }

    private FolderNode buildPrintGroup(String formSchemeKey) {
        FolderNode folderNode = new FolderNode();
        folderNode.setGuid(TransferGuidParse.toTransferId(TransferNodeType.PARENT_GROUP, formSchemeKey));
        folderNode.setName("\u6253\u5370\u65b9\u6848\u5206\u7ec4");
        folderNode.setTitle("\u6253\u5370\u65b9\u6848\u5206\u7ec4");
        folderNode.setType("\u6253\u5370\u65b9\u6848\u5206\u7ec4");
        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formSchemeKey));
        return folderNode;
    }

    public FolderNode getFolderNode(String s) throws TransferException {
        if (!StringUtils.hasLength(s)) {
            return null;
        }
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        if (parse.isBusiness()) {
            return null;
        }
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.iDesignTimeCacheProxy.getTaskGroupDefine(key);
                    if (taskGroupDefine == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(taskGroupDefine, TransferNodeType.TASK_GROUP);
                    String parentKey = taskGroupDefine.getParentKey();
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, parentKey));
                    return folderNode;
                }
                case TASK: {
                    DesignTaskDefine taskDefine = this.iDesignTimeCacheProxy.getTaskDefine(key);
                    if (taskDefine == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.taskToFolderNode((TaskDefine)taskDefine);
                    DesignTaskGroupLink taskGroupLink = this.iDesignTimeCacheProxy.getTaskGroupLink(key);
                    if (taskGroupLink != null) {
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupLink.getGroupKey()));
                    }
                    return folderNode;
                }
                case FORM_SCHEME: {
                    DesignFormSchemeDefine formScheme = this.iDesignTimeCacheProxy.getFormSchemeDefine(key);
                    if (formScheme == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(formScheme, TransferNodeType.FORM_SCHEME);
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK, formScheme.getTaskKey()));
                    return folderNode;
                }
                case FORM_GROUP: {
                    if (key.startsWith(TransferConsts.V_NODE_KEY)) {
                        String formSchemeKey = key.substring(TransferConsts.V_NODE_KEY.length());
                        return this.buildFormGroup(formSchemeKey);
                    }
                    DesignFormGroupDefine formGroupDefine = this.iDesignTimeCacheProxy.getFormGroup(key);
                    if (formGroupDefine == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(formGroupDefine, TransferNodeType.FORM_GROUP);
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, TransferConsts.V_NODE_KEY + formGroupDefine.getFormSchemeKey()));
                    return folderNode;
                }
                case FORMULA_GROUP: {
                    return this.buildFormulaGroup(key);
                }
                case PARENT_GROUP: {
                    return this.buildPrintGroup(key);
                }
                case MAPPING_GROUP: {
                    return this.buildMappingGroup(key);
                }
                case FORMULA_SCHEME: {
                    DesignFormulaSchemeDefine define = this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                    if (define == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(define, TransferNodeType.FORMULA_SCHEME);
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORMULA_GROUP, define.getFormSchemeKey()));
                    return folderNode;
                }
                case PRINT_SCHEME: {
                    DesignPrintTemplateSchemeDefine define = this.iDesignTimeCacheProxy.getPrintTemplateSchemeDefine(key);
                    if (define == null) {
                        return null;
                    }
                    FolderNode folderNode = NodeUtil.metaToFolderNode(define, TransferNodeType.PRINT_SCHEME);
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.PARENT_GROUP, define.getFormSchemeKey()));
                    return folderNode;
                }
                case FORMULA_FORM_GROUP: {
                    String[] args = FormulaGuidParse.parseKey(key);
                    DesignFormGroupDefine define = this.iDesignTimeCacheProxy.getFormGroup(args[1]);
                    if (define == null) {
                        return null;
                    }
                    DesignFormDefineImpl temp = new DesignFormDefineImpl();
                    temp.setTitle(define.getTitle());
                    temp.setKey(key);
                    temp.setOrder(define.getOrder());
                    FolderNode folderNode = NodeUtil.metaToFolderNode(temp, TransferNodeType.FORMULA_FORM_GROUP);
                    folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORMULA_SCHEME, args[0]));
                    return folderNode;
                }
            }
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25" + (Object)((Object)transferNodeType));
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<FolderNode> getPathFolders(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        ArrayList<FolderNode> list = new ArrayList<FolderNode>();
        try {
            block22: while (StringUtils.hasLength(key)) {
                FolderNode folderNode;
                switch (transferNodeType) {
                    case TASK_GROUP: {
                        DesignTaskGroupDefine taskGroupDefine = this.iDesignTimeCacheProxy.getTaskGroupDefine(key);
                        if (taskGroupDefine != null) {
                            folderNode = NodeUtil.metaToFolderNode(taskGroupDefine, TransferNodeType.TASK_GROUP);
                            key = taskGroupDefine.getParentKey();
                            if (!StringUtils.hasLength(key)) break;
                            folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, key));
                            break;
                        }
                        folderNode = null;
                        key = null;
                        break;
                    }
                    case TASK: {
                        DesignTaskDefine taskDefine = this.iDesignTimeCacheProxy.getTaskDefine(key);
                        folderNode = NodeUtil.taskToFolderNode((TaskDefine)taskDefine);
                        DesignTaskGroupLink linkItem = this.iDesignTimeCacheProxy.getTaskGroupLink(key);
                        if (linkItem == null) {
                            key = null;
                            break;
                        }
                        key = linkItem.getGroupKey();
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, key));
                        transferNodeType = TransferNodeType.TASK_GROUP;
                        break;
                    }
                    case FORM_SCHEME: {
                        DesignFormSchemeDefine formScheme = this.iDesignTimeCacheProxy.getFormSchemeDefine(key);
                        folderNode = NodeUtil.metaToFolderNode(formScheme, TransferNodeType.FORM_SCHEME);
                        key = formScheme.getTaskKey();
                        transferNodeType = TransferNodeType.TASK;
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK, key));
                        break;
                    }
                    case FORM_GROUP: {
                        if (key.startsWith(TransferConsts.V_NODE_KEY)) {
                            String formSchemeKey;
                            key = formSchemeKey = key.substring(TransferConsts.V_NODE_KEY.length());
                            folderNode = this.buildFormGroup(formSchemeKey);
                            transferNodeType = TransferNodeType.FORM_SCHEME;
                            break;
                        }
                        DesignFormGroupDefine formGroupDefine = this.iDesignTimeCacheProxy.getFormGroup(key);
                        folderNode = NodeUtil.metaToFolderNode(formGroupDefine, TransferNodeType.FORM_GROUP);
                        String formSchemeKey = formGroupDefine.getFormSchemeKey();
                        key = TransferConsts.V_NODE_KEY + formSchemeKey;
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, key));
                        break;
                    }
                    case FORM: {
                        DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(key);
                        DesignFormGroupDefine parentGroup = this.iDesignTimeCacheProxy.getFormGroupByFormId(key);
                        if (parentGroup == null) {
                            transferNodeType = TransferNodeType.FORM_SCHEME;
                            key = formDefine.getFormScheme();
                            continue block22;
                        }
                        key = parentGroup.getKey();
                        transferNodeType = TransferNodeType.FORM_GROUP;
                        continue block22;
                    }
                    case FORMULA_GROUP: {
                        transferNodeType = TransferNodeType.FORM_SCHEME;
                        folderNode = this.buildFormulaGroup(key);
                        break;
                    }
                    case PARENT_GROUP: {
                        transferNodeType = TransferNodeType.FORM_SCHEME;
                        folderNode = this.buildPrintGroup(key);
                        break;
                    }
                    case MAPPING_GROUP: {
                        transferNodeType = TransferNodeType.FORM_SCHEME;
                        folderNode = this.buildMappingGroup(key);
                        break;
                    }
                    case PRINT_SCHEME: {
                        DesignPrintTemplateSchemeDefine printDefine = this.iDesignTimeCacheProxy.getPrintTemplateSchemeDefine(key);
                        folderNode = NodeUtil.metaToFolderNode(printDefine, TransferNodeType.PRINT_SCHEME);
                        key = printDefine.getFormSchemeKey();
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.PARENT_GROUP, key));
                        transferNodeType = TransferNodeType.PARENT_GROUP;
                        break;
                    }
                    case FORMULA_SCHEME: {
                        DesignFormulaSchemeDefine formulaSchemeDefine = this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                        folderNode = NodeUtil.metaToFolderNode(formulaSchemeDefine, TransferNodeType.FORMULA_SCHEME);
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORMULA_GROUP, formulaSchemeDefine.getFormSchemeKey()));
                        key = formulaSchemeDefine.getFormSchemeKey();
                        transferNodeType = TransferNodeType.FORMULA_GROUP;
                        break;
                    }
                    case FORMULA_FORM_GROUP: {
                        String[] args = FormulaGuidParse.parseKey(key);
                        String formulaSchemeKey = args[0];
                        DesignFormGroupDefine formGroupDefine = this.iDesignTimeCacheProxy.getFormGroup(args[1]);
                        DesignFormGroupDefineImpl temp = new DesignFormGroupDefineImpl();
                        temp.setCode(formGroupDefine.getCode());
                        temp.setTitle(formGroupDefine.getTitle());
                        temp.setOrder(formGroupDefine.getOrder());
                        temp.setKey(key);
                        folderNode = NodeUtil.metaToFolderNode(temp, TransferNodeType.FORMULA_FORM_GROUP);
                        transferNodeType = TransferNodeType.FORMULA_SCHEME;
                        folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.FORMULA_SCHEME, formulaSchemeKey));
                        key = formulaSchemeKey;
                        break;
                    }
                    case FORMULA_FORM: {
                        String[] args = FormulaGuidParse.parseKey(key);
                        String formulaSchemeKey = args[0];
                        String formKey = args[1];
                        if (FormulaGuidParse.INTER_TABLE_FORMULA_KEY.equals(formKey)) {
                            transferNodeType = TransferNodeType.FORMULA_SCHEME;
                            key = formulaSchemeKey;
                            continue block22;
                        }
                        DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(formKey);
                        DesignFormDefineImpl temp = new DesignFormDefineImpl();
                        temp.setTitle(formDefine.getTitle());
                        temp.setFormCode(formDefine.getFormCode());
                        temp.setKey(key);
                        temp.setOrder(formDefine.getOrder());
                        DesignFormGroupDefine parentGroup = this.iDesignTimeCacheProxy.getFormGroupByFormId(formKey);
                        if (parentGroup == null) {
                            transferNodeType = TransferNodeType.FORMULA_SCHEME;
                            key = formulaSchemeKey;
                            continue block22;
                        }
                        key = parentGroup.getKey();
                        key = FormulaGuidParse.toFormulaId(formulaSchemeKey, key);
                        transferNodeType = TransferNodeType.FORMULA_FORM_GROUP;
                        continue block22;
                    }
                    case PRINT_TEMPLATE: {
                        DesignPrintTemplateDefine define = this.iDesignTimeCacheProxy.getPrintTemplateDefine(key);
                        transferNodeType = TransferNodeType.PRINT_SCHEME;
                        key = define.getPrintSchemeKey();
                        continue block22;
                    }
                    case PRINT_COMTEM: {
                        DesignPrintTemplateDefine define = this.iDesignTimeCacheProxy.getPrintComTemDefine(key);
                        transferNodeType = TransferNodeType.PRINT_SCHEME;
                        key = define.getPrintSchemeKey();
                        continue block22;
                    }
                    case PRINT_SETTING: {
                        key = FormulaGuidParse.parseKey(key)[0];
                        transferNodeType = TransferNodeType.PRINT_SCHEME;
                        continue block22;
                    }
                    case MAPPING_DEFINE: {
                        SingleConfigInfo mappingConfigInfo = this.iDesignTimeCacheProxy.getMappingConfigInfo(key);
                        transferNodeType = TransferNodeType.MAPPING_GROUP;
                        key = mappingConfigInfo.getSchemeKey();
                        continue block22;
                    }
                    case CUSTOM_DATA: {
                        String[] strings = CustomHelper.splitKey(key);
                        if (null == strings || strings.length != 2) {
                            key = null;
                            continue block22;
                        }
                        String parentDataKey = strings[0];
                        String dataKey = strings[1];
                        if (null == this.customBusiness || this.customBusiness.size() == 0) continue block22;
                        IMetaItem data = null;
                        for (CustomBusiness business : this.customBusiness) {
                            if (null == business || !business.checkBusiness(dataKey, parentDataKey)) continue;
                            data = business.getData(parentDataKey);
                            folderNode = NodeUtil.metaToFolderNode(data, TransferNodeType.CUSTOM_DATA);
                            folderNode.setParentGuid(TransferGuidParse.toTransferId(TransferNodeType.TASK, key));
                            folderNode.setName(data.getTitle());
                            break;
                        }
                        if (null != data) {
                            key = parentDataKey;
                            transferNodeType = TransferNodeType.TASK;
                            continue block22;
                        }
                        key = null;
                        continue block22;
                    }
                    default: {
                        throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
                    }
                }
                if (folderNode == null) continue;
                list.add(folderNode);
            }
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
        Collections.reverse(list);
        return list;
    }

    public String addFolder(FolderNode folderNode) {
        return null;
    }

    public void modifyFolder(FolderNode folderNode) {
    }

    public FolderNode getFolderByTitle(String s, String s1, String s2) {
        return null;
    }
}

