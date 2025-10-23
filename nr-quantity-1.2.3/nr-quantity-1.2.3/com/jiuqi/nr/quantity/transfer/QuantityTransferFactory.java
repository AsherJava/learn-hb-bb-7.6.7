/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 */
package com.jiuqi.nr.quantity.transfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.nr.quantity.transfer.QuantityBusinessManager;
import com.jiuqi.nr.quantity.transfer.QuantityFolderManager;
import com.jiuqi.nr.quantity.transfer.QuantityModelTransfer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityTransferFactory
extends TransferFactory {
    @Autowired
    private QuantityModelTransfer quantityModelTransfer;
    @Autowired
    private QuantityFolderManager quantityFolderManager;
    @Autowired
    private QuantityBusinessManager quantityBusinessManager;

    public String getId() {
        return "quantityTransfer_factoryId";
    }

    public String getTitle() {
        return "\u91cf\u7eb2";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportExport(String s) throws TransferException {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) throws TransferException {
        return this.quantityModelTransfer;
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
        return this.quantityFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.quantityBusinessManager;
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
}

