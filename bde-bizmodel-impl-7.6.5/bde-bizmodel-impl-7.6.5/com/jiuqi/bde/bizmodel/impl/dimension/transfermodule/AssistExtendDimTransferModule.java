/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.bde.bizmodel.impl.dimension.transfermodule;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class AssistExtendDimTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger LOG = LoggerFactory.getLogger(AssistExtendDimTransferModule.class);
    private static final String MODULE_ID_ASSIST_EXTEND_DIM = "MODULE_ID_ASSIST_EXTEND_DIM";
    private static final String MODULE_NAME_ASSIST_EXTEND_DIM = "MODULE_ASSIST_EXTEND_DIM";
    private static final String MODULE_TITLE_ASSIST_EXTEND_DIM = "\u7ef4\u5ea6\u5c5e\u6027";
    private static final String CATEGORY_NAME_ASSIST_EXTEND_DIM = "CATEGORY_NAME_ASSIST_EXTEND_DIM";
    private static final String CATEGORY_TITLE_ASSIST_EXTEND_DIM = "\u7ef4\u5ea6\u5c5e\u6027";
    @Autowired
    private AssistExtendDimService extendDimService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public String getModuleId() {
        return MODULE_ID_ASSIST_EXTEND_DIM;
    }

    public String getName() {
        return MODULE_NAME_ASSIST_EXTEND_DIM;
    }

    public String getTitle() {
        return "\u7ef4\u5ea6\u5c5e\u6027";
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>(2);
        VaParamTransferCategory vaParamTransferCategory = new VaParamTransferCategory();
        vaParamTransferCategory.setName(CATEGORY_NAME_ASSIST_EXTEND_DIM);
        vaParamTransferCategory.setTitle("\u7ef4\u5ea6\u5c5e\u6027");
        vaParamTransferCategory.setSupportExport(true);
        vaParamTransferCategory.setSupportExportData(false);
        categories.add(vaParamTransferCategory);
        return categories;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        return Collections.emptyList();
    }

    public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        return super.getFolderNode(category, nodeId);
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        if (!this.isCategoryNode(category, parent)) {
            return Collections.emptyList();
        }
        ArrayList<VaParamTransferBusinessNode> vaParamTransferBusinessNodeList = new ArrayList<VaParamTransferBusinessNode>();
        List<AssistExtendDimVO> assistExtendDimVOList = this.extendDimService.getAllAssistExtendDim();
        assistExtendDimVOList.forEach(assistExtendDimVO -> {
            VaParamTransferBusinessNode vaParamTransferBusinessNode = new VaParamTransferBusinessNode();
            vaParamTransferBusinessNode.setId(assistExtendDimVO.getCode());
            vaParamTransferBusinessNode.setTitle(assistExtendDimVO.getName());
            vaParamTransferBusinessNode.setOrder(assistExtendDimVO.getOrdinal().toPlainString());
            vaParamTransferBusinessNodeList.add(vaParamTransferBusinessNode);
        });
        return vaParamTransferBusinessNodeList;
    }

    private boolean isCategoryNode(String category, String parent) {
        return parent == null && CATEGORY_NAME_ASSIST_EXTEND_DIM.equals(category);
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (this.isCategoryNode(category, nodeId)) {
            return null;
        }
        AssistExtendDimVO assistExtendDimVO = this.extendDimService.getAssistExtendDimByCode(nodeId);
        if (assistExtendDimVO == null) {
            return null;
        }
        VaParamTransferBusinessNode vaParamTransferBusinessNode = new VaParamTransferBusinessNode();
        vaParamTransferBusinessNode.setId(nodeId);
        vaParamTransferBusinessNode.setName(assistExtendDimVO.getCode());
        vaParamTransferBusinessNode.setTitle(assistExtendDimVO.getName());
        vaParamTransferBusinessNode.setOrder(assistExtendDimVO.getOrdinal().toPlainString());
        vaParamTransferBusinessNode.setType(CATEGORY_NAME_ASSIST_EXTEND_DIM);
        vaParamTransferBusinessNode.setTypeTitle("\u7ef4\u5ea6\u5c5e\u6027");
        return vaParamTransferBusinessNode;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        AssistExtendDimVO assistExtendDimVO = this.extendDimService.getAssistExtendDimByCode(nodeId);
        if (assistExtendDimVO == null) {
            return Collections.emptyList();
        }
        String pathTitle = AssistExtendDimTransferModule.buildPathTitle("\u7ef4\u5ea6\u5c5e\u6027", "\u7ef4\u5ea6\u5c5e\u6027", assistExtendDimVO.getName());
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        node.setTitle(pathTitle);
        return Collections.singletonList(node);
    }

    private static String buildPathTitle(String ... titles) {
        return String.join((CharSequence)" / ", titles);
    }

    public String getExportModelInfo(String category, String nodeId) {
        AssistExtendDimVO assistExtendDimVO = this.extendDimService.getAssistExtendDimByCode(nodeId);
        if (assistExtendDimVO == null) {
            return null;
        }
        return JsonUtils.writeValueAsString((Object)assistExtendDimVO);
    }

    public void importModelInfo(String category, String info) {
        this.transactionTemplate.execute(status -> {
            try {
                this.doImport(info);
                return null;
            }
            catch (Exception e) {
                LOG.error("\u5bfc\u5165\u7ef4\u5ea6\u5c5e\u6027\u5931\u8d25\uff01", e);
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }

    private void doImport(String json) {
        AssistExtendDimVO assistExtendDimVO = (AssistExtendDimVO)JsonUtils.readValue((String)json, AssistExtendDimVO.class);
        if (assistExtendDimVO == null) {
            return;
        }
        AssistExtendDimVO oldAssistExtendDimVO = this.extendDimService.getAssistExtendDimByCode(assistExtendDimVO.getCode());
        if (oldAssistExtendDimVO != null) {
            assistExtendDimVO.setId(oldAssistExtendDimVO.getId());
            this.extendDimService.update(assistExtendDimVO);
            return;
        }
        this.extendDimService.save(assistExtendDimVO);
    }
}

