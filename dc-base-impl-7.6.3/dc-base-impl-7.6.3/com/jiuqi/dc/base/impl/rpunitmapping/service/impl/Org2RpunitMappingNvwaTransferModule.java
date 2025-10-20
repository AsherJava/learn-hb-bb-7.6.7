/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO
 *  com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 */
package com.jiuqi.dc.base.impl.rpunitmapping.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.base.impl.acctperiod.service.impl.AcctPeriodServiceImpl;
import com.jiuqi.dc.base.impl.onlinePeriod.service.impl.OnlinePeriodDefineServiceImpl;
import com.jiuqi.dc.base.impl.rpunitmapping.dao.Org2RpunitMappingDao;
import com.jiuqi.dc.base.impl.rpunitmapping.entity.Org2RpunitMappingEntity;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Org2RpunitMappingNvwaTransferModule
implements DcTransferModule {
    private static final String ORG_TO_RPUNIT = "Org2RpunitMapping";
    private static final String ORG_TO_RPUNIT_TITLE = "\u4e00\u672c\u8d26\u5355\u4f4d\u4e0e\u62a5\u8868\u5355\u4f4d\u6620\u5c04";
    private static final String ONLINE_PERIOD = "OnlinePeriod";
    @Autowired
    private AcctPeriodServiceImpl acctPeriodService;
    @Autowired
    private Org2RpunitMappingService org2RpunitMappingService;
    @Autowired
    private Org2RpunitMappingDao org2RpunitMappingDao;
    @Autowired
    private OnlinePeriodDefineServiceImpl onlinePeriodDefineService;

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(ORG_TO_RPUNIT);
        category.setTitle(ORG_TO_RPUNIT_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        ArrayList<VaParamTransferBusinessNode> businessNodes = new ArrayList<VaParamTransferBusinessNode>();
        List<Integer> years = this.acctPeriodService.listYear();
        years.forEach(item -> {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(item.toString());
            node.setName(item.toString());
            node.setTitle(item.toString());
            node.setType(ORG_TO_RPUNIT);
            node.setTypeTitle(ORG_TO_RPUNIT_TITLE);
            businessNodes.add(node);
        });
        return businessNodes;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        Integer minPeriodYear = this.onlinePeriodDefineService.getMinPeriodYear();
        int currentYear = Integer.parseInt(nodeId);
        if (minPeriodYear == null || currentYear < minPeriodYear) {
            return null;
        }
        Org2RpunitMappingQueryVO queryVO = new Org2RpunitMappingQueryVO();
        queryVO.setAcctYear(Integer.valueOf(currentYear));
        Org2RpunitMappingReturnVO org2RpunitMappings = this.org2RpunitMappingService.query(queryVO);
        if (Objects.isNull(org2RpunitMappings)) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        node.setId(nodeId);
        node.setName(nodeId);
        node.setTitle(nodeId);
        node.setType(ORG_TO_RPUNIT);
        node.setTypeTitle(ORG_TO_RPUNIT_TITLE);
        return node;
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        Org2RpunitMappingQueryVO queryVO = new Org2RpunitMappingQueryVO();
        queryVO.setAcctYear(Integer.valueOf(Integer.parseInt(nodeId)));
        Org2RpunitMappingReturnVO org2RpunitMappings = this.org2RpunitMappingService.query(queryVO);
        return JsonUtils.writeValueAsString((Object)org2RpunitMappings);
    }

    public void importModelInfo(String category, String info) {
        if (!StringUtils.hasText(info)) {
            return;
        }
        Org2RpunitMappingReturnVO result = (Org2RpunitMappingReturnVO)JsonUtils.readValue((String)info, Org2RpunitMappingReturnVO.class);
        Assert.isNotNull((Object)result);
        List dataList = result.getDataList();
        dataList.forEach(item -> {
            Org2RpunitMappingEntity entity = new Org2RpunitMappingEntity();
            BeanUtils.copyProperties(item, (Object)entity);
            List<Org2RpunitMappingEntity> oldEntity = this.org2RpunitMappingDao.select(entity);
            if (oldEntity.isEmpty()) {
                this.org2RpunitMappingDao.insert(entity);
                return;
            }
            this.org2RpunitMappingDao.updateByPrimaryKey(entity);
        });
    }

    public List<String> getDependenceFactoryIds(String category) {
        ArrayList<String> factoryIds = new ArrayList<String>();
        factoryIds.add(ONLINE_PERIOD);
        return factoryIds;
    }
}

