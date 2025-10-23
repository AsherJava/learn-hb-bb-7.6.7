/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
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
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  org.jdom2.Element
 */
package com.jiuqi.nr.param.transfer.task;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
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
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.param.transfer.ParamTransferConfig;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.definition.DefinitionTransferFactory;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nr.param.transfer.definition.TransferNodeType;
import com.jiuqi.nr.param.transfer.task.TaskBusinessManager;
import com.jiuqi.nr.param.transfer.task.TaskFolderManager;
import com.jiuqi.nr.param.transfer.task.TaskModelTransfer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTransferFactory
extends TransferFunctionFactory {
    @Autowired
    private TaskModelTransfer taskModelTransfer;
    @Autowired
    private TaskFolderManager taskFolderManager;
    @Autowired
    private TaskBusinessManager taskBusinessManager;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DefinitionTransferFactory definitionTransferFactory;

    public String getId() {
        return "TASK_TRANSFER_FACTORY_ID";
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
        return this.taskModelTransfer;
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
        return this.taskFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.taskBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        TransferGuid parse = TransferGuidParse.parseId(s);
        String key = parse.getKey();
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(key);
        Date updateTime = new Date();
        if (null != designTaskDefine) {
            updateTime = designTaskDefine.getUpdateTime();
        }
        return TransferConsts.DATE_FORMAT_1.get().format(updateTime);
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
            try {
                List designFormSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(key);
                for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
                    this.definitionTransferFactory.getRelatedBusinessOfTheFormScheme(list, designFormSchemeDefine.getKey());
                }
            }
            catch (Exception e) {
                throw new TransferException(e.getMessage());
            }
        }
        return list;
    }

    public List<String> getDependenceFactoryIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("DATASCHEME_TRANSFER_FACTORY_ID");
        return list;
    }

    public int getOrder() {
        return -125;
    }

    public void sortImportBusinesses(List<BusinessNode> importBusinessNodes) {
        this.definitionTransferFactory.sortImportBusinesses(importBusinessNodes);
    }

    public String getFunctionCategroyId() {
        return "taskFunctionCategroy";
    }

    public List<String> getFunctionContainedFactoryIdList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("PERIOD_TRANSFER_FACTORY_ID");
        list.add("DATASCHEME_TRANSFER_FACTORY_ID");
        list.add("OrgData");
        list.add("DEFINITION_TRANSFER_FACTORY_ID");
        list.add("FORM_TYPE_TRANSFER_FACTORY_ID");
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
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u5355\u673a\u7248\u6267\u884c\u4efb\u52a1\u5bfc\u5165", (String)"\u5355\u673a\u7248\u6267\u884c\u4efb\u52a1\u53c2\u6570\u5bfc\u5165");
        }
    }
}

