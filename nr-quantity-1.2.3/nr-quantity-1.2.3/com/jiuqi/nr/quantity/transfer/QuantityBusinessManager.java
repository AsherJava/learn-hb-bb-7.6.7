/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.quantity.transfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.service.IQuantityService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class QuantityBusinessManager
extends AbstractBusinessManager {
    private static final Logger logger = LoggerFactory.getLogger(QuantityBusinessManager.class);
    @Autowired
    private IQuantityService quantityService;

    private BusinessNode quantityInfoToBusiness(QuantityInfo quantityInfo) {
        BusinessNode businessNode = null;
        if (quantityInfo != null) {
            businessNode = new BusinessNode();
            businessNode.setGuid("QI_" + quantityInfo.getId());
            businessNode.setName(quantityInfo.getName());
            businessNode.setTitle(quantityInfo.getTitle());
            businessNode.setType("QUANTITY");
            businessNode.setTypeTitle("\u91cf\u7eb2");
            businessNode.setModifyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(quantityInfo.getModifyTime())));
        }
        return businessNode;
    }

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        List<Object> businessNodes = new ArrayList<BusinessNode>();
        try {
            List<QuantityInfo> quantityInfos;
            if (!(StringUtils.hasLength(s) && s.startsWith("QI") || CollectionUtils.isEmpty(quantityInfos = this.quantityService.getAllQuantityInfo()))) {
                businessNodes = quantityInfos.stream().map(this::quantityInfoToBusiness).collect(Collectors.toList());
            }
        }
        catch (JQException e) {
            logger.error("\u91cf\u7eb2\u83b7\u53d6\u5931\u8d25\uff01", e);
        }
        return businessNodes;
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        BusinessNode businessNode = null;
        if (StringUtils.hasLength(s) && s.startsWith("QI")) {
            try {
                QuantityInfo quantityInfo = this.quantityService.getQuantityInfoById(s.substring(3));
                businessNode = this.quantityInfoToBusiness(quantityInfo);
            }
            catch (JQException e) {
                logger.error("\u91cf\u7eb2\u83b7\u53d6\u5931\u8d25\uff01", e);
            }
        }
        return businessNode;
    }

    public BusinessNode getBusinessByNameAndType(String s, String s1) throws TransferException {
        return null;
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return new ArrayList<FolderNode>();
    }

    public void moveBusiness(BusinessNode businessNode, String s) throws TransferException {
    }
}

