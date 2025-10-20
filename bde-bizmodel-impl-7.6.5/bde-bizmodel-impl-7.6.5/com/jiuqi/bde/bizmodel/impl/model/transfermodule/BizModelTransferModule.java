/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.transaction.support.TransactionTemplate
 */
package com.jiuqi.bde.bizmodel.impl.model.transfermodule;

import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelManageService;
import com.jiuqi.common.base.BusinessRuntimeException;
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
import org.springframework.util.Assert;

@Component
public class BizModelTransferModule
extends VaParamTransferModuleIntf {
    private static final Logger LOG = LoggerFactory.getLogger(BizModelTransferModule.class);
    private static final String MODULE_ID_BIZ_MODEL = "MODULE_ID_BIZ_MODEL";
    private static final String MODULE_NAME_BIZ_MODEL = "MODULE_BIZ_MODEL";
    private static final String MODULE_TITLE_BIZ_MODEL = "\u4e1a\u52a1\u6a21\u578b";
    private static final String CATEGORY_NAME_BIZ_MODEL_FINDATA = "CATEGORY_NAME_BIZ_MODEL_FINDATA";
    private static final String CATEGORY_TITLE_BIZ_MODEL_FINDATA = "\u8d26\u52a1\u6a21\u578b";
    private static final String CATEGORY_NAME_BIZ_MODEL_CUSTOM = "CATEGORY_NAME_BIZ_MODEL_CUSTOM";
    private static final String CATEGORY_TITLE_BIZ_MODEL_CUSTOM = "\u81ea\u5b9a\u4e49\u53d6\u6570";
    private static final String CATEGORY_NAME_BIZ_MODEL_BASEDATA = "CATEGORY_NAME_BIZ_MODEL_BASEDATA";
    private static final String CATEGORY_TITLE_BIZ_MODEL_BASEDATA = "\u57fa\u7840\u6570\u636e";
    @Autowired
    private BizModelServiceGather bizModelServiceGather;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public String getModuleId() {
        return MODULE_ID_BIZ_MODEL;
    }

    public String getName() {
        return MODULE_NAME_BIZ_MODEL;
    }

    public String getTitle() {
        return MODULE_TITLE_BIZ_MODEL;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categories = new ArrayList<VaParamTransferCategory>(2);
        VaParamTransferCategory finData = new VaParamTransferCategory();
        finData.setName(CATEGORY_NAME_BIZ_MODEL_FINDATA);
        finData.setTitle(CATEGORY_TITLE_BIZ_MODEL_FINDATA);
        finData.setSupportExport(true);
        finData.setSupportExportData(false);
        categories.add(finData);
        VaParamTransferCategory custom = new VaParamTransferCategory();
        custom.setName(CATEGORY_NAME_BIZ_MODEL_CUSTOM);
        custom.setTitle(CATEGORY_TITLE_BIZ_MODEL_CUSTOM);
        custom.setSupportExport(true);
        custom.setSupportExportData(false);
        categories.add(custom);
        VaParamTransferCategory baseData = new VaParamTransferCategory();
        baseData.setName(CATEGORY_NAME_BIZ_MODEL_BASEDATA);
        baseData.setTitle(CATEGORY_TITLE_BIZ_MODEL_BASEDATA);
        baseData.setSupportExport(true);
        baseData.setSupportExportData(false);
        categories.add(baseData);
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
        ArrayList<VaParamTransferBusinessNode> bizNodes = new ArrayList<VaParamTransferBusinessNode>();
        BizModelNode.Type bizModelType = BizModelTransferModule.getType(category);
        String bizModelCategoryCode = bizModelType.getBizModelCategoryEnum().getCode();
        List<? extends BizModelDTO> bizModelInfos = this.bizModelServiceGather.getByCode(bizModelCategoryCode).listModel();
        bizModelInfos.forEach(bizModelInfo -> {
            VaParamTransferBusinessNode bizNode = new VaParamTransferBusinessNode();
            String nodeId = BizModelNode.id(bizModelType, bizModelInfo.getCode());
            bizNode.setId(nodeId);
            bizNode.setTitle(bizModelInfo.getName());
            bizNode.setOrder(bizModelInfo.getOrdinal().toPlainString());
            bizNodes.add(bizNode);
        });
        return bizNodes;
    }

    private boolean isCategoryNode(String category, String parent) {
        return parent == null && (CATEGORY_NAME_BIZ_MODEL_FINDATA.equals(category) || CATEGORY_NAME_BIZ_MODEL_CUSTOM.equals(category) || CATEGORY_NAME_BIZ_MODEL_BASEDATA.equals(category));
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (this.isCategoryNode(category, nodeId)) {
            return null;
        }
        String bizModelCategoryTitle = BizModelTransferModule.getBizModelCategoryTitle(category);
        BizModelCategoryEnum bizModelCategoryEnum = BizModelTransferModule.getBizModelCategoryEnum(category);
        BizModelDTO bizModelInfo = this.getBizModelInfoByNodeId(bizModelCategoryEnum, nodeId);
        if (bizModelInfo == null) {
            return null;
        }
        VaParamTransferBusinessNode bizNode = new VaParamTransferBusinessNode();
        bizNode.setId(nodeId);
        bizNode.setName(bizModelInfo.getCode());
        bizNode.setTitle(bizModelInfo.getName());
        bizNode.setOrder(bizModelInfo.getOrdinal().toPlainString());
        bizNode.setType(bizModelCategoryEnum.getCode());
        bizNode.setTypeTitle(bizModelCategoryTitle);
        return bizNode;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        String bizModelCategoryTitle = BizModelTransferModule.getBizModelCategoryTitle(category);
        BizModelDTO bizModelInfo = this.getBizModelInfoByNodeId(BizModelTransferModule.getBizModelCategoryEnum(category), nodeId);
        if (bizModelInfo == null) {
            return Collections.emptyList();
        }
        String pathTitle = BizModelTransferModule.buildPathTitle(MODULE_TITLE_BIZ_MODEL, bizModelCategoryTitle, bizModelInfo.getName());
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        node.setTitle(pathTitle);
        return Collections.singletonList(node);
    }

    private static String buildPathTitle(String ... titles) {
        return String.join((CharSequence)" / ", titles);
    }

    public String getExportModelInfo(String category, String nodeId) {
        BizModelCategoryEnum bizModelCategoryEnum = BizModelTransferModule.getBizModelCategoryEnum(category);
        BizModelDTO bizModelInfo = this.getBizModelInfoByNodeId(bizModelCategoryEnum, nodeId);
        if (bizModelInfo == null) {
            return null;
        }
        return this.toJson(bizModelCategoryEnum, bizModelInfo);
    }

    public void importModelInfo(String category, String info) {
        BizModelCategoryEnum bizModelCategoryEnum = BizModelTransferModule.getBizModelCategoryEnum(category);
        this.transactionTemplate.execute(status -> {
            try {
                this.doImport(bizModelCategoryEnum, info);
                return null;
            }
            catch (Exception e) {
                LOG.error("\u5bfc\u5165\u53d6\u6570\u8bbe\u7f6e\u5931\u8d25\uff01", e);
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }

    private static String getBizModelCategoryTitle(String category) {
        Assert.hasText(category, "category \u4e0d\u80fd\u4e3a\u7a7a");
        if (CATEGORY_NAME_BIZ_MODEL_FINDATA.equals(category)) {
            return CATEGORY_TITLE_BIZ_MODEL_FINDATA;
        }
        if (CATEGORY_NAME_BIZ_MODEL_CUSTOM.equals(category)) {
            return CATEGORY_TITLE_BIZ_MODEL_CUSTOM;
        }
        if (CATEGORY_NAME_BIZ_MODEL_BASEDATA.equals(category)) {
            return CATEGORY_TITLE_BIZ_MODEL_BASEDATA;
        }
        throw new IllegalArgumentException("category \u53c2\u6570\u9519\u8bef\uff0c\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u7c7b\u578b");
    }

    private static BizModelCategoryEnum getBizModelCategoryEnum(String category) {
        BizModelNode.Type bizModelType = BizModelTransferModule.getType(category);
        return bizModelType.getBizModelCategoryEnum();
    }

    private static BizModelNode.Type getType(String category) {
        Assert.hasText(category, "category \u4e0d\u80fd\u4e3a\u7a7a");
        if (CATEGORY_NAME_BIZ_MODEL_FINDATA.equals(category)) {
            return BizModelNode.Type.FINDATA;
        }
        if (CATEGORY_NAME_BIZ_MODEL_CUSTOM.equals(category)) {
            return BizModelNode.Type.CUSTOM;
        }
        if (CATEGORY_NAME_BIZ_MODEL_BASEDATA.equals(category)) {
            return BizModelNode.Type.BASEDATA;
        }
        throw new IllegalArgumentException("category \u53c2\u6570\u9519\u8bef\uff0c\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u7c7b\u578b");
    }

    private void doImport(BizModelCategoryEnum bizModelCategoryEnum, String bizModelInfoJson) {
        BizModelDTO bizModelDTO = this.fromJson(bizModelCategoryEnum, bizModelInfoJson);
        if (bizModelDTO == null) {
            return;
        }
        BizModelManageService bizModelManageService = this.bizModelServiceGather.getByCode(bizModelCategoryEnum.getCode());
        BizModelDTO oldBizModel = this.getBizModelInfoByBizModelCode(bizModelCategoryEnum, bizModelDTO.getCode());
        if (oldBizModel != null) {
            bizModelDTO.setId(oldBizModel.getId());
            String json = this.toJson(bizModelCategoryEnum, bizModelDTO);
            bizModelManageService.update(json);
            return;
        }
        bizModelManageService.save(bizModelInfoJson);
    }

    private boolean existedBizModel(BizModelCategoryEnum bizModelCategoryEnum, String bizModelCode) {
        return this.getBizModelInfoByBizModelCode(bizModelCategoryEnum, bizModelCode) != null;
    }

    private BizModelDTO getBizModelInfoByBizModelCode(BizModelCategoryEnum bizModelCategoryEnum, String bizModelCode) {
        try {
            return this.bizModelServiceGather.getByCode(bizModelCategoryEnum.getCode()).getByCode(bizModelCode);
        }
        catch (BusinessRuntimeException e) {
            return null;
        }
    }

    private BizModelDTO getBizModelInfoByNodeId(BizModelCategoryEnum bizModelCategoryEnum, String nodeId) {
        return this.getBizModelInfoByBizModelCode(bizModelCategoryEnum, BizModelNode.bizModelCode(nodeId));
    }

    private BizModelDTO fromJson(BizModelCategoryEnum bizModelCategoryEnum, String json) {
        switch (bizModelCategoryEnum) {
            case BIZMODEL_FINDATA: {
                return this.fromJson(FinBizModelDTO.class, json);
            }
            case BIZMODEL_CUSTOM: {
                return this.fromJson(CustomBizModelDTO.class, json);
            }
            case BIZMODEL_BASEDATA: {
                return this.fromJson(BaseDataBizModelDTO.class, json);
            }
        }
        return null;
    }

    private BizModelDTO fromJson(Class<? extends BizModelDTO> clazz, String json) {
        return (BizModelDTO)JsonUtils.readValue((String)json, clazz);
    }

    private String toJson(BizModelCategoryEnum bizModelCategoryEnum, BizModelDTO bizModelInfo) {
        switch (bizModelCategoryEnum) {
            case BIZMODEL_FINDATA: {
                return this.toJsonFindata(bizModelInfo);
            }
            case BIZMODEL_CUSTOM: {
                return this.toJsonCustom(bizModelInfo);
            }
            case BIZMODEL_BASEDATA: {
                return this.toJsonBasedata(bizModelInfo);
            }
        }
        return null;
    }

    private String toJsonFindata(BizModelDTO bizModelInfo) {
        Assert.isInstanceOf(FinBizModelDTO.class, (Object)bizModelInfo, "bizModelInfo \u7c7b\u578b\u9519\u8bef");
        FinBizModelDTO finBizModelInfo = (FinBizModelDTO)bizModelInfo;
        return JsonUtils.writeValueAsString((Object)finBizModelInfo);
    }

    private String toJsonCustom(BizModelDTO bizModelInfo) {
        Assert.isInstanceOf(CustomBizModelDTO.class, (Object)bizModelInfo, "bizModelInfo \u7c7b\u578b\u9519\u8bef");
        CustomBizModelDTO customBizModelInfo = (CustomBizModelDTO)bizModelInfo;
        return JsonUtils.writeValueAsString((Object)customBizModelInfo);
    }

    private String toJsonBasedata(BizModelDTO bizModelInfo) {
        Assert.isInstanceOf(BaseDataBizModelDTO.class, (Object)bizModelInfo, "bizModelInfo \u7c7b\u578b\u9519\u8bef");
        BaseDataBizModelDTO basedataBizModelInfo = (BaseDataBizModelDTO)bizModelInfo;
        return JsonUtils.writeValueAsString((Object)basedataBizModelInfo);
    }

    static class BizModelNode {
        static final String SPLIT_CHAR = ":";
        private String id;
        private Type type;
        private String bizModelCode;

        BizModelNode() {
        }

        private static String id(Type type, String bizModelCode) {
            return type.value() + SPLIT_CHAR + bizModelCode;
        }

        private static String bizModelCode(String id) {
            Assert.hasText(id, "id\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.isTrue(id.contains(SPLIT_CHAR), "id \u683c\u5f0f\u9519\u8bef\uff0c\u7f3a\u5c11\u5206\u9694\u7b26");
            Assert.isTrue(id.split(SPLIT_CHAR).length == 2, "id \u683c\u5f0f\u9519\u8bef");
            String bizModelCode = id.split(SPLIT_CHAR)[1];
            Assert.hasText(bizModelCode, "id \u683c\u5f0f\u9519\u8bef\uff0cbizModelId \u4e0d\u80fd\u4e3a\u7a7a");
            return bizModelCode;
        }

        static enum Type {
            FINDATA(0, BizModelCategoryEnum.BIZMODEL_FINDATA),
            CUSTOM(1, BizModelCategoryEnum.BIZMODEL_CUSTOM),
            BASEDATA(2, BizModelCategoryEnum.BIZMODEL_BASEDATA);

            final int value;
            final BizModelCategoryEnum bizModelCategoryEnum;

            private Type(int value, BizModelCategoryEnum bizModelCategoryEnum) {
                this.value = value;
                this.bizModelCategoryEnum = bizModelCategoryEnum;
            }

            public int value() {
                return this.value;
            }

            public BizModelCategoryEnum getBizModelCategoryEnum() {
                return this.bizModelCategoryEnum;
            }
        }
    }
}

