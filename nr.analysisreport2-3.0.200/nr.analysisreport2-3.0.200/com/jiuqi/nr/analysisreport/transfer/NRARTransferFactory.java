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
package com.jiuqi.nr.analysisreport.transfer;

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
import com.jiuqi.nr.analysisreport.transfer.NRARBusinessManager;
import com.jiuqi.nr.analysisreport.transfer.NRARFolderManager;
import com.jiuqi.nr.analysisreport.transfer.NRARModelTransfer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NRARTransferFactory
extends TransferFactory {
    @Autowired
    private NRARFolderManager nrarFolderManager;
    @Autowired
    private NRARBusinessManager nrarBusinessManager;
    @Autowired
    private NRARModelTransfer nrarModelTransfer;

    public String getId() {
        return "nr_analysisreport";
    }

    public String getTitle() {
        return "\u5206\u6790\u62a5\u544a";
    }

    public String getModuleId() {
        return "nr_analysisreport_module";
    }

    public boolean supportExport(String guid) throws TransferException {
        return true;
    }

    public IModelTransfer createModelTransfer(String type) throws TransferException {
        return this.nrarModelTransfer;
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

    public boolean supportExportData(String guid) throws TransferException {
        return false;
    }

    public List<NameMapperBean> handleMapper() throws TransferException {
        return Collections.emptyList();
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> list) throws TransferException {
        return Collections.emptyList();
    }

    public IMetaFinder createMetaFinder(String mapperType) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.nrarFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.nrarBusinessManager;
    }

    public String getIcon() throws TransferException {
        return null;
    }

    public List<ResItem> getRelatedBusiness(String guid) throws TransferException {
        ArrayList<ResItem> items = new ArrayList<ResItem>();
        return items;
    }

    public List<String> getDependenceFactoryIds() {
        return Collections.emptyList();
    }

    public int getOrder() {
        return -130;
    }
}

