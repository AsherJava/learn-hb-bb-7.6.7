/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
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
 */
package com.jiuqi.nr.param.transfer.formtype;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
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
import com.jiuqi.nr.param.transfer.ParamTransferConfig;
import com.jiuqi.nr.param.transfer.formtype.FormTypeBusinessManager;
import com.jiuqi.nr.param.transfer.formtype.FormTypeFolderManager;
import com.jiuqi.nr.param.transfer.formtype.FormTypeModelTransfer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormTypeTransferFactory
extends TransferFactory {
    @Autowired
    private FormTypeFolderManager formTypeFolderManager;
    @Autowired
    private FormTypeBusinessManager formTypeBusinessManager;

    public String getId() {
        return "FORM_TYPE_TRANSFER_FACTORY_ID";
    }

    public String getTitle() {
        return "\u62a5\u8868\u7c7b\u578b";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportExport(String s) throws TransferException {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) throws TransferException {
        return new FormTypeModelTransfer();
    }

    public IConfigTransfer createConfigTransfer() throws TransferException {
        return null;
    }

    public IPublisher createPublisher() throws TransferException {
        return null;
    }

    public IDataTransfer createDataTransfer(String s) throws TransferException {
        return null;
    }

    public boolean supportExportData(String s) throws TransferException {
        return false;
    }

    public List<NameMapperBean> handleMapper() throws TransferException {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) throws TransferException {
        return null;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.formTypeFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.formTypeBusinessManager;
    }

    public String getIcon() throws TransferException {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String s) throws TransferException {
        return null;
    }

    public List<String> getDependenceFactoryIds() {
        return null;
    }

    public int getOrder() {
        return 0;
    }

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
        this.doLogHelper(businessNodes);
        ParamTransferConfig.buildNpContext(context.getOperator());
    }

    private void doLogHelper(List<BusinessNode> businessNodes) {
        if (businessNodes != null && !businessNodes.isEmpty()) {
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u53c2\u6570", (String)"\u8fdb\u884c\u4e86\u62a5\u8868\u7c7b\u578b\u8d44\u6e90\u7684\u5bfc\u5165");
        }
    }
}

