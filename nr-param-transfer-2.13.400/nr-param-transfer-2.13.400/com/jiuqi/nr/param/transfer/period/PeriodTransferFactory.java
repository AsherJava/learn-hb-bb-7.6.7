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
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.service.PeriodService
 */
package com.jiuqi.nr.param.transfer.period;

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
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.period.PeriodBusinessManager;
import com.jiuqi.nr.param.transfer.period.PeriodFolderManager;
import com.jiuqi.nr.param.transfer.period.PeriodModelTransfer;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.service.PeriodService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodTransferFactory
extends TransferFactory {
    @Autowired
    private PeriodModelTransfer periodModelTransfer;
    @Autowired
    private PeriodFolderManager periodFolderManager;
    @Autowired
    private PeriodBusinessManager periodBusinessManager;
    @Autowired
    private PeriodService periodService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodTransferFactory.class);

    public String getId() {
        return "PERIOD_TRANSFER_FACTORY_ID";
    }

    public String getTitle() {
        return "\u65f6\u671f";
    }

    public String getModuleId() {
        return "com.jiuqi.nr";
    }

    public boolean supportExport(String s) {
        return true;
    }

    public IModelTransfer createModelTransfer(String s) {
        return this.periodModelTransfer;
    }

    public IMetaFinder createMetaFinder(String s) {
        return null;
    }

    public AbstractFolderManager getFolderManager() {
        return this.periodFolderManager;
    }

    public AbstractBusinessManager getBusinessManager() {
        return this.periodBusinessManager;
    }

    public String getModifiedTime(String s) throws TransferException {
        IPeriodEntity period;
        try {
            period = this.periodService.queryPeriodByKey(s);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TransferException("\u672a\u627e\u5230\u65f6\u671f\u8d44\u6e90", (Throwable)e);
        }
        if (period == null) {
            throw new TransferException("\u672a\u627e\u5230\u65f6\u671f\u8d44\u6e90");
        }
        Date updateTime = period.getUpdateTime();
        return updateTime == null ? null : TransferConsts.DATE_FORMAT_1.get().format(updateTime);
    }

    public int getOrder() {
        return -127;
    }

    public List<ResItem> getRelatedBusiness(String s) {
        return null;
    }

    public String getIcon() {
        return null;
    }

    public IConfigTransfer createConfigTransfer() {
        return null;
    }

    public List<String> getDependenceFactoryIds() {
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

    public void beforeImport(ITransferContext context, List<BusinessNode> businessNodes) throws TransferException {
        this.doLogHelper(businessNodes);
        ParamTransferConfig.buildNpContext(context.getOperator());
    }

    private void doLogHelper(List<BusinessNode> businessNodes) {
        if (businessNodes != null && !businessNodes.isEmpty()) {
            LogHelper.info((String)"\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u65f6\u671f\u53c2\u6570", (String)"\u8fdb\u884c\u4e86\u65f6\u671f\u8d44\u6e90\u7684\u5bfc\u5165");
        }
    }
}

