/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather
 *  com.jiuqi.bde.bizmodel.client.util.TreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather;
import com.jiuqi.bde.bizmodel.client.util.TreeNode;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.model.gather.IBizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelManageService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BizModelServiceImpl
implements BizModelService {
    @Autowired
    @Lazy
    private IBizModelServiceGather bizModelServiceGather;
    @Autowired
    private AssistExtendDimService assistExtendDimService;
    @Autowired
    private IBizModelCategoryGather categoryGather;

    @Override
    public List<? extends BizModelDTO> listByCategory(String category) {
        return this.bizModelServiceGather.getByCode(category).listModel();
    }

    @Override
    public List<BizModelDTO> list() {
        List categoryList = this.categoryGather.list();
        ArrayList bizModelList = CollectionUtils.newArrayList();
        categoryList.forEach(category -> bizModelList.addAll(this.bizModelServiceGather.getByCode(category.getCode()).listModel()));
        List<BizModelDTO> list = bizModelList.stream().filter(item -> StopFlagEnum.START.getCode().equals(item.getStopFlag())).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<BizModelDTO> listAll() {
        ArrayList bizModelList = CollectionUtils.newArrayList();
        for (BizModelManageService manageService : this.bizModelServiceGather.list()) {
            bizModelList.addAll(manageService.listModel());
        }
        return bizModelList;
    }

    @Override
    public BizModelDTO get(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        return this.list().stream().filter(item -> bizModelCode.equals(item.getCode())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", bizModelCode)));
    }

    @Override
    public CustomFetchFormVO getCustomFetchFormData() {
        return new CustomFetchFormVO(Arrays.asList(AggregateFuncEnum.values()), Arrays.asList(MatchingRuleEnum.values()));
    }

    @Override
    public List<TreeNode> queryTreeInitByFetchSourceCode(String bizModelCode) {
        FinBizModelDTO finBizModel = (FinBizModelDTO)this.get(bizModelCode);
        ArrayList<TreeNode> tree = new ArrayList<TreeNode>();
        TreeNode root = new TreeNode(finBizModel.getName());
        tree.add(root);
        ArrayList<TreeNode> children = new ArrayList<TreeNode>();
        children.add(new TreeNode("\u79d1\u76ee", "SUBJECTCODE"));
        children.add(new TreeNode("\u5e01\u79cd", "CURRENCYCODE"));
        List fetchTypes = finBizModel.getFetchTypes();
        if (!ComputationModelEnum.XJLLBALANCE.getCode().equals(finBizModel.getComputationModelCode())) {
            fetchTypes = fetchTypes.stream().filter(type -> !type.equals("BQNUM") && !type.equals("LJNUM") && !type.equals("WBQNUM") && !type.equals("WLJNUM")).collect(Collectors.toList());
            children.add(new TreeNode("\u79d1\u76ee\u65b9\u5411", "ORIENT"));
        } else {
            children.add(new TreeNode("\u73b0\u91d1\u6d41\u91cf\u9879\u76ee", "CFITEMCODE"));
        }
        for (String fetchType : fetchTypes) {
            children.add(new TreeNode(FetchTypeEnum.getEnumByCode((String)fetchType).getName(), FetchTypeEnum.getEnumByCode((String)fetchType).getCode()));
        }
        Map<String, String> dimensionMap = Optional.ofNullable(finBizModel.getDimensionMap()).orElseGet(HashMap::new);
        Set assistExtendDimSet = this.assistExtendDimService.getAllStartAssistExtendDim().stream().map(AssistExtendDimVO::getCode).collect(Collectors.toSet());
        if (!ComputationModelEnum.BALANCE.getCode().equals(finBizModel.getComputationModelCode())) {
            if (!finBizModel.getComputationModelCode().equals(ComputationModelEnum.ASSBALANCE.getCode())) {
                dimensionMap = dimensionMap.entrySet().stream().filter(entry -> !assistExtendDimSet.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            for (Map.Entry entry2 : dimensionMap.entrySet()) {
                children.add(new TreeNode((String)entry2.getValue(), (String)entry2.getKey()));
            }
        }
        for (TreeNode treeNode : children) {
            root.addChild(treeNode);
        }
        return tree;
    }
}

