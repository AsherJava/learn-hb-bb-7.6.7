/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.dataentry.templTransfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TemplBusinessManager
extends AbstractBusinessManager {
    @Autowired
    private ITemplateConfigService templateConfigService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplBusinessManager.class);

    private BusinessNode templInfoToBusiness(TemplateConfigImpl templateConfig) {
        BusinessNode businessNode = null;
        if (templateConfig != null) {
            businessNode = new BusinessNode();
            businessNode.setGuid("TEMPL_" + templateConfig.getCode());
            businessNode.setName(templateConfig.getCode());
            businessNode.setTitle(templateConfig.getTitle());
            businessNode.setType("TEMPL");
            businessNode.setTypeTitle("\u6570\u636e\u5f55\u5165\u6a21\u677f");
            if (templateConfig.getUpdateTime() != null) {
                businessNode.setModifyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(templateConfig.getUpdateTime()));
            } else {
                businessNode.setModifyTime("");
            }
        }
        return businessNode;
    }

    public List<BusinessNode> getBusinessNodes(String s) throws TransferException {
        try {
            List<Object> businessNodes = new ArrayList<BusinessNode>();
            List<TemplateConfigImpl> allTemplateConfig = this.templateConfigService.getAllTemplateConfig();
            for (TemplateConfigImpl templateConfig : allTemplateConfig) {
                FTemplateConfig templateConfigByCode = this.templateConfigService.getTemplateConfigById(templateConfig.getTemplateId());
                templateConfig.setTemplateConfig(templateConfigByCode.getTemplateConfig());
            }
            if (!CollectionUtils.isEmpty(allTemplateConfig)) {
                businessNodes = allTemplateConfig.stream().map(this::templInfoToBusiness).collect(Collectors.toList());
            }
            return businessNodes;
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u5f55\u5165\u6a21\u677f", e);
            throw new TransferException((Throwable)e);
        }
    }

    public BusinessNode getBusinessNode(String s) throws TransferException {
        FTemplateConfig templateConfigByCode = this.templateConfigService.getTemplateConfigByCode(s.substring(6));
        if (templateConfigByCode.getTemplateId() == null) {
            return null;
        }
        BusinessNode businessNode = new BusinessNode();
        businessNode.setGuid("TEMPL_" + templateConfigByCode.getCode());
        businessNode.setName(templateConfigByCode.getCode());
        businessNode.setTitle(templateConfigByCode.getTitle());
        businessNode.setType("TEMPL");
        businessNode.setTypeTitle("\u6570\u636e\u5f55\u5165\u6a21\u677f");
        if (templateConfigByCode.getUpdateTime() != null) {
            businessNode.setModifyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(templateConfigByCode.getUpdateTime()));
        } else {
            businessNode.setModifyTime("");
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

