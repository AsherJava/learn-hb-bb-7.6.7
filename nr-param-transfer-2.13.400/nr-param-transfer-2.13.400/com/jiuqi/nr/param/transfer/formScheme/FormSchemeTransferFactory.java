/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferEffectScope
 *  com.jiuqi.bi.transfer.engine.TransferFunctionFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  org.jdom2.Element
 */
package com.jiuqi.nr.param.transfer.formScheme;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferEffectScope;
import com.jiuqi.bi.transfer.engine.TransferFunctionFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.param.transfer.ParamTransferConfig;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.definition.DefinitionTransferFactory;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.formScheme.FormSchemeBusinessManager;
import com.jiuqi.nr.param.transfer.formScheme.FormSchemeFolderManager;
import com.jiuqi.nr.param.transfer.formScheme.FormSchemeModelTransfer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormSchemeTransferFactory
extends TransferFunctionFactory {
    @Autowired
    private FormSchemeModelTransfer formSchemeModelTransfer;
    @Autowired
    private FormSchemeFolderManager formSchemeFolderManager;
    @Autowired
    private FormSchemeBusinessManager formSchemeBusinessManager;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DefinitionTransferFactory definitionTransferFactory;
    public static final String ALL_MODE_SCOPE = "FORM_SCHEME_ALL_MODEL";
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeTransferFactory.class);

    public String getId() {
        return "ALL_MODE_FORM_SCHEME_FACTORY_ID";
    }

    public String getTitle() {
        return "\u4efb\u52a1";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportExport(String s) {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) {
        return this.formSchemeModelTransfer;
    }

    public IConfigTransfer createConfigTransfer() {
        return null;
    }

    public IPublisher createPublisher() {
        return null;
    }

    public IDataTransfer createDataTransfer(String s) {
        return null;
    }

    public boolean supportExportData(String s) {
        return false;
    }

    public List<NameMapperBean> handleMapper() {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) {
        return null;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.formSchemeFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.formSchemeBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        String key = parse.getKey();
        DesignTaskGroupDefine metaItem = null;
        try {
            switch (transferNodeType) {
                case TASK_GROUP: {
                    metaItem = this.designTimeViewController.queryTaskGroupDefine(key);
                    break;
                }
                case TASK: {
                    metaItem = this.designTimeViewController.queryTaskDefine(key);
                    break;
                }
                case FORM_SCHEME: {
                    return "";
                }
                case CUSTOM_DATA: {
                    return this.definitionTransferFactory.getModifiedTime(s);
                }
                default: {
                    logger.info("\u4efb\u52a1\u5168\u91cf\u5bfc\u5165\u65f6\uff0cgetModifiedTime\u65b9\u6cd5\u6bd4\u8f83\u5f53\u524d\u8d44\u6e90\u5728\u670d\u52a1\u4e0a\u7684updateTime\uff0c\u8d44\u6e90\u7c7b\u578b\u4e0d\u5728\u5339\u914d\u8303\u56f4\u5185\uff0c\u5c06\u4f1a\u8fd4\u56de\u7a7a\u8d44\u6e90");
                    return null;
                }
            }
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
        if (metaItem == null) {
            return null;
        }
        Date updateTime = metaItem.getUpdateTime();
        return updateTime == null ? null : TransferConsts.DATE_FORMAT_1.get().format(updateTime);
    }

    public String getIcon() {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parse(s);
        if (parse == null) {
            return Collections.emptyList();
        }
        ArrayList<ResItem> list = new ArrayList<ResItem>();
        TransferNodeType transferNodeType = parse.getTransferNodeType();
        String key = parse.getKey();
        if (transferNodeType == TransferNodeType.TASK) {
            this.definitionTransferFactory.getRelatedBusinessOfTheTask(list, key);
        }
        if (transferNodeType == TransferNodeType.FORM_SCHEME) {
            this.definitionTransferFactory.getRelatedBusinessOfTheFormScheme(list, key);
            DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(key);
            this.definitionTransferFactory.getRelatedBusinessOfTheTask(list, designFormSchemeDefine.getTaskKey());
        }
        return list;
    }

    public int getOrder() {
        return -130;
    }

    public void sortImportBusinesses(List<BusinessNode> importBusinessNodes) {
        this.definitionTransferFactory.sortImportBusinesses(importBusinessNodes);
    }

    public String getFunctionCategroyId() {
        return "ALL_MODE_FORM_SCHEME_FUNCTION_CATEGORY_ID";
    }

    public List<String> getFunctionContainedFactoryIdList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DEFINITION_TRANSFER_FACTORY_ID");
        return list;
    }

    public List<String> getDependenceFactoryIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DATASCHEME_TRANSFER_FACTORY_ID");
        return list;
    }

    public void toDocumentExtra(Element businessElement, BusinessNode businessNode) {
        this.definitionTransferFactory.toDocumentExtra(businessElement, businessNode);
    }

    public void loadBusinessExtra(Element element, BusinessNode businessNode) {
        this.definitionTransferFactory.loadBusinessExtra(element, businessNode);
    }

    public BusinessNode createBusinessNode() {
        return this.definitionTransferFactory.createBusinessNode();
    }

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
        this.doLogHelper(businessNodes);
        ParamTransferConfig.buildNpContext(context.getOperator());
    }

    private void doLogHelper(List<BusinessNode> businessNodes) {
        if (businessNodes != null && !businessNodes.isEmpty()) {
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u4efb\u52a1\u5168\u91cf\u5bfc\u5165", (String)"\u4efb\u52a1\u5168\u91cf\u6267\u884c\u4efb\u52a1\u53c2\u6570\u5bfc\u5165");
        }
    }

    public TransferEffectScope getEffectScope() {
        return new TransferEffectScope(ALL_MODE_SCOPE, "\u4efb\u52a1\u5168\u91cf");
    }
}

