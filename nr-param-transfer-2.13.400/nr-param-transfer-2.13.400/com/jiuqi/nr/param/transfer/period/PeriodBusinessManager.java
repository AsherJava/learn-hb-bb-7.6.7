/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.service.PeriodService
 */
package com.jiuqi.nr.param.transfer.period;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.param.transfer.TransferConsts;
import com.jiuqi.nr.param.transfer.period.PeriodFolderManager;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.service.PeriodService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodBusinessManager
extends AbstractBusinessManager {
    @Autowired
    private PeriodService periodService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodBusinessManager.class);
    @Autowired
    private PeriodFolderManager periodFolderManager;

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        try {
            if ("00000000-0000-0000-0000-000000000001".equals(s)) {
                List periods = this.periodService.queryCustomPeriodList();
                ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
                for (IPeriodEntity period : periods) {
                    BusinessNode folderNode = this.buildBusinessNode(period);
                    list.add(folderNode);
                }
                return list;
            }
            if ("00000000-0000-0000-0000-000000000002".equals(s)) {
                List periodList = this.periodService.getPeriodList();
                ArrayList<BusinessNode> list = new ArrayList<BusinessNode>();
                for (IPeriodEntity iPeriodEntity : periodList) {
                    if (!PeriodUtils.isPeriod13((String)iPeriodEntity.getCode(), (PeriodType)iPeriodEntity.getPeriodType())) continue;
                    BusinessNode folderNode = this.buildBusinessNode(iPeriodEntity);
                    list.add(folderNode);
                }
                return list;
            }
            return Collections.emptyList();
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u81ea\u5b9a\u4e49\u65f6\u671f\u5931\u8d25", e);
            throw new TransferException((Throwable)e);
        }
    }

    private BusinessNode buildBusinessNode(IPeriodEntity period) {
        Date updateTime;
        BusinessNode node = new BusinessNode();
        node.setGuid(period.getKey());
        node.setName(period.getCode());
        node.setTitle(period.getTitle());
        node.setType("");
        Date createTime = period.getCreateTime();
        if (createTime != null) {
            node.setCreateTime(TransferConsts.DATE_FORMAT.get().format(createTime));
        }
        if ((updateTime = period.getUpdateTime()) != null) {
            node.setModifyTime(TransferConsts.DATE_FORMAT.get().format(updateTime));
        }
        node.setDataMode(DataMode.NONE);
        node.setTypeTitle("\u81ea\u5b9a\u4e49\u65f6\u671f");
        return node;
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        try {
            IPeriodEntity period = this.periodService.queryPeriodByKey(s);
            if (period != null) {
                return this.buildBusinessNode(period);
            }
            return null;
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u81ea\u5b9a\u4e49\u65f6\u671f\u5931\u8d25", e);
            throw new TransferException((Throwable)e);
        }
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        ArrayList<FolderNode> result = new ArrayList<FolderNode>();
        try {
            IPeriodEntity period = this.periodService.queryPeriodByKey(s);
            if (PeriodUtils.isPeriod13((String)period.getCode(), (PeriodType)period.getPeriodType())) {
                result.add(new FolderNode("00000000-0000-0000-0000-000000000002", "\u8d22\u52a1\u65f6\u671f", "", null, "2"));
            } else {
                result.add(new FolderNode("00000000-0000-0000-0000-000000000001", "\u81ea\u5b9a\u4e49\u65f6\u671f", "", null, "1"));
            }
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u65f6\u671f\u5931\u8d25", e);
            throw new TransferException((Throwable)e);
        }
        return result;
    }

    public void moveBusiness(BusinessNode businessNode, String parent) {
    }
}

