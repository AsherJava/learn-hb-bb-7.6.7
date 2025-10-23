/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.paramio.CustomBusiness
 *  nr.single.map.configurations.bean.SingleConfigInfo
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.paramio.CustomBusiness;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.definition.DefinitionFolderManager;
import com.jiuqi.nr.param.transfer.definition.FormulaGuidParse;
import com.jiuqi.nr.param.transfer.definition.IDesignTimeCacheProxy;
import com.jiuqi.nr.param.transfer.definition.NodeUtil;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.definition.VNode;
import com.jiuqi.nr.param.transfer.definition.custom.CustomHelper;
import com.jiuqi.nr.param.transfer.definition.service.SingleMappingService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.SingleConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DefinitionBusinessManager
extends AbstractBusinessManager {
    private static final Logger logger = LoggerFactory.getLogger(DefinitionBusinessManager.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaRunTimeController;
    @Autowired
    private IPrintDesignTimeController printRunTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private DefinitionFolderManager definitionFolderManager;
    @Autowired
    private SingleMappingService singleMappingService;
    @Autowired
    private IDesignTimeCacheProxy iDesignTimeCacheProxy;
    @Autowired(required=false)
    private List<CustomBusiness> customBusiness;
    @Autowired
    private DesignFormGroupLinkDao formGroupLinkDao;

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        if (s == null) {
            return Collections.emptyList();
        }
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        if (parse.isBusiness()) {
            return Collections.emptyList();
        }
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        if (transferNodeType == null) {
            logger.info("\u76ee\u5f55guid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u83b7\u53d6\u4e0b\u7ea7\u5931\u8d25", (Object)s);
            return Collections.emptyList();
        }
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.iDesignTimeCacheProxy.getTaskGroupDefine(key);
                    if (taskGroupDefine != null) {
                        BusinessNode businessNode = NodeUtil.metaToBusinessNode(taskGroupDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupDefine.getKey()), TransferNodeType.TASK_GROUP);
                        businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                        list.add(businessNode);
                    }
                    return list;
                }
                case TASK: {
                    DesignTaskDefine taskDefine = this.iDesignTimeCacheProxy.getTaskDefine(key);
                    if (taskDefine != null) {
                        BusinessNode businessNode = NodeUtil.taskToBusinessNode(taskDefine, s, TransferGuidParse.toTransferId(TransferNodeType.TASK, taskDefine.getKey()));
                        businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                        list.add(businessNode);
                        if (null != this.customBusiness && this.customBusiness.size() != 0) {
                            for (CustomBusiness business : this.customBusiness) {
                                IMetaItem data = business.getData(taskDefine.getKey());
                                if (data == null) continue;
                                BusinessNode customBusines = CustomHelper.createBusiness((IMetaItem)taskDefine, data, TransferNodeType.CUSTOM_DATA, s);
                                list.add(customBusines);
                            }
                        }
                    }
                    return list;
                }
                case FORM_SCHEME: {
                    DesignFormSchemeDefine formScheme = this.iDesignTimeCacheProxy.getFormSchemeDefine(key);
                    if (formScheme != null) {
                        BusinessNode businessNode = NodeUtil.metaToBusinessNode(formScheme, s, TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formScheme.getKey()), TransferNodeType.FORM_SCHEME);
                        businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                        list.add(businessNode);
                    }
                    return list;
                }
                case FORM_GROUP: {
                    if (key.startsWith(TransferConsts.V_NODE_KEY)) {
                        return Collections.emptyList();
                    }
                    DesignFormGroupDefine formGroupDefine = this.iDesignTimeCacheProxy.getFormGroup(key);
                    if (formGroupDefine != null) {
                        BusinessNode businessNode = NodeUtil.metaToBusinessNode(formGroupDefine, s, TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, formGroupDefine.getKey()), TransferNodeType.FORM_GROUP);
                        businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                        list.add(businessNode);
                    }
                    ArrayList<DesignFormDefine> allFormsInGroup = this.iDesignTimeCacheProxy.getDesignFormDefineByGroupKey(key);
                    list.addAll(NodeUtil.formToBusinessNodes(allFormsInGroup, s));
                    return list;
                }
                case PARENT_GROUP: 
                case FORMULA_GROUP: {
                    return list;
                }
                case FORMULA_SCHEME: {
                    DesignFormulaSchemeDefine define = this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                    BusinessNode businessNode = NodeUtil.metaToBusinessNode(define, s, null, TransferNodeType.FORMULA_SCHEME);
                    businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                    list.add(businessNode);
                    VNode iMetaItem = new VNode(FormulaGuidParse.toVNodeFormulaId(key), FormulaGuidParse.INTER_TABLE_FORMULA_TITLE, "", null);
                    list.add(NodeUtil.metaToBusinessNode(iMetaItem, s, null, TransferNodeType.FORMULA_FORM));
                    return list;
                }
                case PRINT_SCHEME: {
                    List settings;
                    List comTemDefines;
                    List forms;
                    DesignPrintTemplateSchemeDefine define = this.iDesignTimeCacheProxy.getPrintTemplateSchemeDefine(key);
                    BusinessNode businessNode = NodeUtil.metaToBusinessNode(define, s, null, TransferNodeType.PRINT_SCHEME);
                    businessNode.setTitle(businessNode.getTitle() + "_\u57fa\u672c\u4fe1\u606f");
                    list.add(businessNode);
                    List defines = this.printRunTimeController.getAllPrintTemplateInScheme(key, false);
                    if (!CollectionUtils.isEmpty(defines) && !(forms = defines.stream().map(PrintTemplateDefine::getFormKey).collect(Collectors.toList())).isEmpty()) {
                        HashSet existFormSet = new HashSet();
                        ArrayList<DesignPrintTemplateDefine> tdList = new ArrayList<DesignPrintTemplateDefine>();
                        for (DesignPrintTemplateDefine defineItem : defines) {
                            String formKey = defineItem.getFormKey();
                            DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(formKey);
                            if (null == formDefine || existFormSet.contains(formKey)) continue;
                            defineItem.setTitle(formDefine.getTitle());
                            tdList.add(defineItem);
                            existFormSet.add(formKey);
                        }
                        list.addAll(NodeUtil.metaToBusinessNodes(tdList, s, null, TransferNodeType.PRINT_TEMPLATE));
                    }
                    if (!CollectionUtils.isEmpty(comTemDefines = this.designTimePrintController.listPrintComTemByScheme(key))) {
                        for (Object comTemDefine : comTemDefines) {
                            list.add(NodeUtil.metaToBusinessNode(s, (PrintComTemDefine)comTemDefine));
                        }
                    }
                    if (!CollectionUtils.isEmpty(settings = this.designTimePrintController.listPrintSettingDefine(key))) {
                        for (DesignPrintSettingDefine setting : settings) {
                            DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(setting.getFormKey());
                            if (null == formDefine) continue;
                            list.add(NodeUtil.metaToBusinessNode(s, (FormDefine)formDefine, (PrintSettingDefine)setting));
                        }
                    }
                    return list;
                }
                case FORMULA_FORM_GROUP: {
                    String[] args = FormulaGuidParse.parseKey(key);
                    ArrayList<DesignFormDefine> allFormsInGroup = this.iDesignTimeCacheProxy.getDesignFormDefineByGroupKey(args[1]);
                    ArrayList<DesignFormDefineImpl> copy = new ArrayList<DesignFormDefineImpl>();
                    for (DesignFormDefine designFormDefine : allFormsInGroup) {
                        String formulaId = FormulaGuidParse.toFormulaId(args[0], designFormDefine.getKey());
                        DesignFormDefineImpl define = new DesignFormDefineImpl();
                        define.setKey(formulaId);
                        define.setTitle(designFormDefine.getTitle());
                        copy.add(define);
                    }
                    list.addAll(NodeUtil.metaToBusinessNodes(copy, s, null, TransferNodeType.FORMULA_FORM));
                    return list;
                }
                case MAPPING_GROUP: {
                    List<SingleConfigInfo> mappingConfigList = this.singleMappingService.getMappingConfigInfoByFormScheme(key);
                    list.addAll(this.config2BusinessNodes(s, mappingConfigList));
                    return list;
                }
            }
            logger.info("\u76ee\u5f55guid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u83b7\u53d6\u4e0b\u7ea7\u5931\u8d25", (Object)s);
            return null;
        }
        catch (TransferException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    private BusinessNode config2BusinessNode(String s, SingleConfigInfo config) {
        if (config == null) {
            return null;
        }
        BusinessNode node = new BusinessNode();
        node.setGuid(TransferGuidParse.toBusinessId(TransferNodeType.MAPPING_DEFINE, config.getConfigKey()));
        node.setType(TransferNodeType.MAPPING_DEFINE.name());
        node.setTitle(config.getConfigName());
        node.setName(UUIDUtils.getKey());
        node.setFolderGuid(s);
        node.setTypeTitle(TransferNodeType.MAPPING_DEFINE.getTitle());
        node.setOrder("");
        return node;
    }

    private List<BusinessNode> config2BusinessNodes(String s, List<SingleConfigInfo> mappingConfigList) {
        ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
        if (!CollectionUtils.isEmpty(mappingConfigList)) {
            for (SingleConfigInfo config : mappingConfigList) {
                BusinessNode node = this.config2BusinessNode(s, config);
                if (node == null) continue;
                list.add(node);
            }
        }
        return list;
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        TransferGuid transferGuid = TransferGuidParse.parseId(s);
        if (!transferGuid.isBusiness()) {
            return null;
        }
        Object metaItem = null;
        TransferNodeType transferNodeType = transferGuid.getTransferNodeType();
        if (transferNodeType == null) {
            logger.info("\u8282\u70b9guid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u83b7\u53d6\u5931\u8d25", (Object)s);
            return null;
        }
        String key = transferGuid.getKey();
        String parent = null;
        String bindingFolderGuid = null;
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    DesignTaskGroupDefine taskGroupDefine = this.iDesignTimeCacheProxy.getTaskGroupDefine(key);
                    if (taskGroupDefine == null) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupDefine.getKey());
                    bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.TASK_GROUP, taskGroupDefine.getKey());
                    metaItem = taskGroupDefine;
                    break;
                }
                case TASK: {
                    DesignTaskDefine define = this.iDesignTimeCacheProxy.getTaskDefine(key);
                    if (define == null) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                    bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.TASK, define.getKey());
                    BusinessNode businessNode = NodeUtil.taskToBusinessNode(define, parent, bindingFolderGuid);
                    businessNode.setName(define.getTaskCode());
                    return businessNode;
                }
                case FORM_SCHEME: {
                    DesignFormSchemeDefine formScheme = this.iDesignTimeCacheProxy.getFormSchemeDefine(key);
                    if (formScheme == null) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formScheme.getKey());
                    metaItem = formScheme;
                    bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.FORM_SCHEME, formScheme.getKey());
                    break;
                }
                case FORM_GROUP: {
                    DesignFormGroupDefine formGroupDefine = this.iDesignTimeCacheProxy.getFormGroup(key);
                    if (formGroupDefine == null) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, formGroupDefine.getKey());
                    bindingFolderGuid = TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, formGroupDefine.getKey());
                    metaItem = formGroupDefine;
                    break;
                }
                case FORM: {
                    DesignFormDefine define;
                    metaItem = define = this.iDesignTimeCacheProxy.getSoftFormDefine(key);
                    if (metaItem == null) {
                        return null;
                    }
                    DesignFormGroupDefine parentGroup = this.iDesignTimeCacheProxy.getFormGroupByFormId(key);
                    if (parentGroup != null) {
                        parent = parentGroup.getKey();
                        parent = TransferGuidParse.toTransferId(TransferNodeType.FORM_GROUP, parent);
                    }
                    BusinessNode businessNode = NodeUtil.metaToBusinessNode(metaItem, parent, null, transferGuid.getTransferNodeType());
                    businessNode.setName(define.getFormCode());
                    return businessNode;
                }
                case FORMULA_SCHEME: {
                    DesignFormulaSchemeDefine formulaSchemeDefine = this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                    if (formulaSchemeDefine == null) {
                        return null;
                    }
                    metaItem = formulaSchemeDefine;
                    parent = TransferGuidParse.toTransferId(TransferNodeType.FORMULA_SCHEME, formulaSchemeDefine.getKey());
                    break;
                }
                case PRINT_SCHEME: {
                    DesignPrintTemplateSchemeDefine schemeDefine = this.iDesignTimeCacheProxy.getPrintTemplateSchemeDefine(key);
                    if (schemeDefine == null) {
                        return null;
                    }
                    metaItem = schemeDefine;
                    parent = TransferGuidParse.toTransferId(TransferNodeType.PRINT_SCHEME, schemeDefine.getKey());
                    break;
                }
                case FORMULA_FORM: {
                    String[] split = FormulaGuidParse.parseKey(key);
                    String formKey = split[1];
                    String formulaSchemeKey = split[0];
                    if (FormulaGuidParse.INTER_TABLE_FORMULA_KEY.equals(formKey)) {
                        metaItem = new VNode(key, FormulaGuidParse.INTER_TABLE_FORMULA_TITLE, "", null);
                        parent = TransferGuidParse.toTransferId(TransferNodeType.FORMULA_SCHEME, formulaSchemeKey);
                        return NodeUtil.metaToBusinessNode(metaItem, parent, null, transferGuid.getTransferNodeType());
                    }
                    DesignFormDefine designFormDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(formKey);
                    if (designFormDefine == null) {
                        return null;
                    }
                    DesignFormDefineImpl temp = new DesignFormDefineImpl();
                    temp.setTitle(designFormDefine.getTitle());
                    temp.setFormCode(designFormDefine.getFormCode());
                    temp.setOrder(designFormDefine.getOrder());
                    temp.setKey(key);
                    DesignFormGroupDefine parentGroup = this.iDesignTimeCacheProxy.getFormGroupByFormId(formKey);
                    if (parentGroup != null) {
                        parent = parentGroup.getKey();
                        String formulaId = FormulaGuidParse.toFormulaId(formulaSchemeKey, parent);
                        parent = TransferGuidParse.toTransferId(TransferNodeType.FORMULA_FORM_GROUP, formulaId);
                    }
                    BusinessNode businessNode = NodeUtil.metaToBusinessNode(temp, parent, null, transferGuid.getTransferNodeType());
                    businessNode.setName(temp.getFormCode());
                    return businessNode;
                }
                case PRINT_TEMPLATE: {
                    DesignPrintTemplateDefine define = this.iDesignTimeCacheProxy.getPrintTemplateDefine(key);
                    metaItem = define;
                    if (metaItem == null) {
                        return null;
                    }
                    String formKey = define.getFormKey();
                    DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(formKey);
                    if (formDefine != null) {
                        define.setTitle(formDefine.getTitle());
                        parent = TransferGuidParse.toTransferId(TransferNodeType.PRINT_SCHEME, define.getPrintSchemeKey());
                        return NodeUtil.metaToBusinessNode(metaItem, parent, null, transferGuid.getTransferNodeType());
                    }
                    return null;
                }
                case PRINT_COMTEM: {
                    DesignPrintComTemDefine comTemDefine = this.iDesignTimeCacheProxy.getPrintComTemDefine(key);
                    if (null == comTemDefine) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.PRINT_SCHEME, comTemDefine.getPrintSchemeKey());
                    return NodeUtil.metaToBusinessNode(parent, (PrintComTemDefine)comTemDefine);
                }
                case PRINT_SETTING: {
                    DesignPrintSettingDefine setting = this.iDesignTimeCacheProxy.getPrintSettingDefine(key);
                    if (null == setting) {
                        return null;
                    }
                    parent = TransferGuidParse.toTransferId(TransferNodeType.PRINT_SCHEME, setting.getPrintSchemeKey());
                    DesignFormDefine form = this.iDesignTimeCacheProxy.getSoftFormDefine(setting.getFormKey());
                    if (null != form) {
                        BusinessNode node = NodeUtil.metaToBusinessNode(parent, (FormDefine)form, (PrintSettingDefine)setting);
                        node.setName(form.getFormCode());
                        return node;
                    }
                    return null;
                }
                case MAPPING_DEFINE: {
                    SingleConfigInfo define = this.iDesignTimeCacheProxy.getMappingConfigInfo(key);
                    if (define == null) {
                        return null;
                    }
                    return this.config2BusinessNode(TransferGuidParse.toTransferId(TransferNodeType.MAPPING_GROUP, define.getSchemeKey()), define);
                }
                case FORMULA_FORM_GROUP: {
                    break;
                }
                case CUSTOM_DATA: {
                    String[] strings = CustomHelper.splitKey(key);
                    if (null != strings) {
                        if (strings.length != 2) break;
                        String parentDataKey = strings[0];
                        String dataKey = strings[1];
                        if (null != this.customBusiness && this.customBusiness.size() != 0) {
                            for (CustomBusiness business : this.customBusiness) {
                                if (null == business || !business.checkBusiness(dataKey, parentDataKey)) continue;
                                metaItem = business.getData(parentDataKey);
                                if (metaItem == null) {
                                    return null;
                                }
                                DesignTaskDefine parentGroup = this.iDesignTimeCacheProxy.getTaskDefine(parentDataKey);
                                if (parentGroup == null) {
                                    return null;
                                }
                                parent = parentGroup.getKey();
                                parent = TransferGuidParse.toTransferId(TransferNodeType.TASK, parent);
                                BusinessNode businessNode = CustomHelper.createBusiness((IMetaItem)parentGroup, (IMetaItem)metaItem, TransferNodeType.CUSTOM_DATA, s);
                                businessNode.setName(metaItem.getTitle());
                                return businessNode;
                            }
                        }
                    }
                    break;
                }
                default: {
                    logger.info("\u8282\u70b9guid {} \u7684\u53c2\u6570\u7c7b\u578b\u5728\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff0c\u83b7\u53d6\u5931\u8d25", (Object)s);
                }
            }
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
        if (metaItem != null) {
            return NodeUtil.metaToBusinessNode(metaItem, parent, bindingFolderGuid, transferGuid.getTransferNodeType());
        }
        return null;
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return this.definitionFolderManager.getPathFolders(s);
    }

    public void moveBusiness(BusinessNode businessNode, String s) throws TransferException {
        if (businessNode == null) {
            return;
        }
        TransferGuid transferGuid = TransferGuidParse.parseId(businessNode.getGuid());
        if (!transferGuid.isBusiness()) {
            return;
        }
        TransferNodeType transferNodeType = transferGuid.getTransferNodeType();
        String formKey = transferGuid.getKey();
        this.getMessage("\u53c2\u6570\u5bfc\u5165\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u79fb\u52a8\u76ee\u5f55", "businessNode\u89e3\u6790\u51fa\u6765\u7684formKey", formKey, "\u5165\u53c2\u7684s", s);
        if (TransferNodeType.FORM == transferNodeType) {
            TransferGuid parse = TransferGuidParse.parse(s);
            String formGroupKey = parse.getKey();
            if (this.designTimeViewController.queryFormGroup(formGroupKey) == null) {
                throw new TransferException("\u5bfc\u5165\u8d44\u6e90\u5931\u8d25\uff1a\u672a\u627e\u5230\u8868\u5355\u5206\u7ec4");
            }
            try {
                this.formGroupLinkDao.deleteLinkByForm(formKey);
                this.designTimeViewController.addFormToGroup(formKey, formGroupKey);
                logger.info("moveBusiness\u65b9\u6cd5\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\u6210\u529fformKey\u4e3a\uff1a " + formKey + " ,  formGroupKey\u4e3a\uff1a " + formGroupKey);
            }
            catch (Exception e) {
                logger.error("moveBusiness\u65b9\u6cd5\u79fb\u52a8\u62a5\u8868\u5206\u7ec4\u5931\u8d25formKey\u4e3a\uff1a " + formKey + " ,  formGroupKey\u4e3a\uff1a " + formGroupKey + e.getMessage(), e);
            }
        }
    }

    public IMetaItem getMetaItem(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    return this.iDesignTimeCacheProxy.getTaskGroupDefine(key);
                }
                case TASK: {
                    return this.iDesignTimeCacheProxy.getTaskDefine(key);
                }
                case FORM_SCHEME: {
                    return this.iDesignTimeCacheProxy.getFormSchemeDefine(key);
                }
                case FORM_GROUP: {
                    return this.iDesignTimeCacheProxy.getFormGroup(key);
                }
                case FORM: {
                    return this.iDesignTimeCacheProxy.getSoftFormDefine(key);
                }
                case PARENT_GROUP: 
                case FORMULA_GROUP: 
                case FORMULA_FORM_GROUP: 
                case MAPPING_GROUP: 
                case MAPPING_DEFINE: {
                    return new VNode(s);
                }
                case FORMULA_SCHEME: {
                    return this.iDesignTimeCacheProxy.getFormulaSchemeDefine(key);
                }
                case PRINT_SCHEME: {
                    return this.iDesignTimeCacheProxy.getPrintTemplateSchemeDefine(key);
                }
                case PRINT_TEMPLATE: {
                    return this.iDesignTimeCacheProxy.getPrintTemplateDefine(key);
                }
                case PRINT_COMTEM: {
                    return this.iDesignTimeCacheProxy.getPrintComTemDefine(key);
                }
                case PRINT_SETTING: {
                    DesignFormDefine form = this.iDesignTimeCacheProxy.getSoftFormDefine(FormulaGuidParse.parseKey(key)[1]);
                    DesignPrintSettingDefine setting = this.iDesignTimeCacheProxy.getPrintSettingDefine(key);
                    return new VNode(key, null != form ? form.getTitle() : null, null, null != setting ? setting.getUpdateTime() : new Date());
                }
                case FORMULA_FORM: {
                    String[] split = FormulaGuidParse.parseKey(key);
                    String formulaSchemeKey = split[0];
                    String formKey = split[1];
                    String title = FormulaGuidParse.INTER_TABLE_FORMULA_TITLE;
                    String order = "";
                    if (FormulaGuidParse.INTER_TABLE_FORMULA_KEY.equals(formKey)) {
                        formKey = null;
                    } else {
                        DesignFormDefine formDefine = this.iDesignTimeCacheProxy.getSoftFormDefine(formKey);
                        if (formDefine == null) {
                            return null;
                        }
                        title = formDefine.getTitle();
                        order = formDefine.getOrder();
                    }
                    List allFormulasInForm = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
                    Date max = null;
                    for (DesignFormulaDefine designFormulaDefine : allFormulasInForm) {
                        Date updateTime = designFormulaDefine.getUpdateTime();
                        if (updateTime == null) continue;
                        if (max != null) {
                            if (updateTime.compareTo(max) <= 0) continue;
                            max = updateTime;
                            continue;
                        }
                        max = updateTime;
                    }
                    return new VNode(key, title, order, max);
                }
                case CUSTOM_DATA: {
                    String[] strings = CustomHelper.splitKey(key);
                    if (null == strings || strings.length != 2) {
                        return null;
                    }
                    String parentDataKey = strings[0];
                    String dataKey = strings[1];
                    if (null != this.customBusiness && this.customBusiness.size() != 0) {
                        for (CustomBusiness business : this.customBusiness) {
                            if (null == business || !business.checkBusiness(dataKey, parentDataKey)) continue;
                            return business.getData(parentDataKey);
                        }
                    }
                    return null;
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    private void getMessage(String resourceTypeName, String keyName1, String key1, String keyName2, String key2) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u53c2\u6570\u5bfc\u5165%s\u4fe1\u606f\uff0c\u5165\u53c2%s\u662f\uff1a%s \uff0c%s\u662f\uff1a%s \uff01", resourceTypeName, keyName1, key1, keyName2, key2));
        }
    }

    private void importLinkMessage(String resourceTypeName, Collection resource) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s\u6709\uff1a", resourceTypeName) + resource);
        }
    }
}

