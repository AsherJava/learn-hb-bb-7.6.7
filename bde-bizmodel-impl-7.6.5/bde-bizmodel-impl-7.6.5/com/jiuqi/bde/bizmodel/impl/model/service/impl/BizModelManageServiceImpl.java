/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.BdeFunctionModuleEnum
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.np.log.LogHelper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelBuildContext;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelManageService;
import com.jiuqi.bde.common.constant.BdeFunctionModuleEnum;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public abstract class BizModelManageServiceImpl
implements BizModelManageService {
    @Override
    @Transactional(rollbackFor={Exception.class})
    public int start(String id) {
        BizModelDTO bizModelData = this.getById(id);
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u542f\u7528-%1$s", bizModelData.getName()), (String)JsonUtils.writeValueAsString((Object)bizModelData));
        String tableName = this.getTableName();
        int flag = this.getBizModelDao().updateStopFlagById(id, StopFlagEnum.START, tableName);
        this.bizmodelCacheClear();
        return flag;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int stop(String id) {
        BizModelDTO bizModelData = this.getById(id);
        LogHelper.info((String)BdeFunctionModuleEnum.BIZMODEL.getFullModuleName(), (String)String.format("\u505c\u7528-%1$s", bizModelData.getName()), (String)JsonUtils.writeValueAsString((Object)bizModelData));
        String tableName = this.getTableName();
        int flag = this.getBizModelDao().updateStopFlagById(id, StopFlagEnum.STOP, tableName);
        this.bizmodelCacheClear();
        return flag;
    }

    @Override
    public void exchangeOrdinal(String srcId, String targetId) {
        BizModelDTO srcBizModel = this.getById(srcId);
        Assert.isNotNull((Object)srcBizModel, (String)String.format("ID\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u53d6\u6570\u6765\u6e90", srcId), (Object[])new Object[0]);
        BizModelDTO targetBizModel = this.getById(targetId);
        Assert.isNotNull((Object)targetBizModel, (String)String.format("ID\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u53d6\u6570\u6765\u6e90", targetId), (Object[])new Object[0]);
        String tableName = this.getTableName();
        this.getBizModelDao().updateOrdinalById(srcId, targetBizModel.getOrdinal(), tableName);
        this.getBizModelDao().updateOrdinalById(targetId, srcBizModel.getOrdinal(), tableName);
        this.bizmodelCacheClear();
    }

    @Override
    public CustomFetchFormVO getCustomFetchFormData() {
        return new CustomFetchFormVO(Arrays.asList(AggregateFuncEnum.values()), Arrays.asList(MatchingRuleEnum.values()));
    }

    @Override
    public String getFetchTypeByBizModelCode(String bizModelCode) {
        List<SelectOptionVO> fetchTypeList = this.listFetchTypeByBizModelCode(bizModelCode);
        return JsonUtils.writeValueAsString(fetchTypeList);
    }

    @Override
    public String getDimensionByBizModelCode(String bizModelCode) {
        List<SelectOptionVO> dimensionList = this.listDimensionByBizModelCode(bizModelCode);
        return JsonUtils.writeValueAsString(dimensionList);
    }

    public List<SelectOptionVO> listFetchTypeByBizModelCode(String bizModelCode) {
        IBizComputationModel computationModel = this.getBizModelGather().getComputationModelByCode(bizModelCode);
        List<Object> fetchTypes = new ArrayList<SelectOptionVO>();
        if (null != computationModel) {
            fetchTypes = computationModel.getFetchTypes();
        }
        return fetchTypes;
    }

    public List<SelectOptionVO> listDimensionByBizModelCode(String bizModelCode) {
        List dimensions;
        IBizComputationModel computationModel = this.getBizModelGather().getComputationModelByCode(bizModelCode);
        if (null != computationModel && !CollectionUtils.isEmpty((Collection)(dimensions = computationModel.getDimensions()))) {
            return dimensions;
        }
        return Collections.emptyList();
    }

    @Override
    public final List<BizModelTreeNode> getBizModelTree() {
        ArrayList<BizModelTreeNode> tree = new ArrayList<BizModelTreeNode>();
        List bizDataModelList = this.getBizModelGather().listBizDataModelByCategory(this.getCategoryCode());
        BizModelTreeNode node = null;
        for (IBizDataModel bizDataModel : bizDataModelList) {
            node = new BizModelTreeNode(bizDataModel.getCode(), bizDataModel.getName());
            List modelDefineList = this.getBizModelGather().listComputationModelByDataModel(bizDataModel.getCode());
            for (IBizComputationModel bizComputationModel : modelDefineList) {
                node.getChildren().add(new BizModelTreeNode(bizComputationModel.getCode(), bizComputationModel.getName()));
            }
            if (CollectionUtils.isEmpty((Collection)node.getChildren())) continue;
            if (node.getChildren().size() == 1) {
                tree.add((BizModelTreeNode)node.getChildren().get(0));
                continue;
            }
            tree.add(node);
        }
        return tree;
    }

    public abstract String getTableName();

    public abstract void bizmodelCacheClear();

    public abstract BizModelDao getBizModelDao();

    public abstract IBizModelGather getBizModelGather();

    @Override
    public Map<String, BizModelColumnDefineVO> getBatchColumnDefinesForExtInfo(List<BizModelDTO> listBizModelDTO, BizModelBuildContext context) {
        HashMap<String, BizModelColumnDefineVO> ColumnDefineMap = new HashMap<String, BizModelColumnDefineVO>();
        List codeList = listBizModelDTO.stream().map(BizModelDTO::getCode).collect(Collectors.toList());
        for (String bizModelCode : codeList) {
            ColumnDefineMap.put(bizModelCode, this.getColumnDefines(bizModelCode));
        }
        return ColumnDefineMap;
    }
}

