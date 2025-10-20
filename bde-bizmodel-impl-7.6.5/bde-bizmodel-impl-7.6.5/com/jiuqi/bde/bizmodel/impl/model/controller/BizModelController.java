/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.util.TreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO
 *  com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.ApplyScopeEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.impl.model.controller;

import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.util.TreeNode;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.gather.IBizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelBaseDataConfigService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelSettingService;
import com.jiuqi.bde.common.constant.ApplyScopeEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizModelController
implements BizModelClient {
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private IBizModelServiceGather bizModelServiceGather;
    @Autowired
    private IBizModelGather bizModelGather;
    @Autowired
    private BizModelSettingService bizModelSettingService;
    @Autowired
    private BizModelBaseDataConfigService bizModelBaseDataConfigService;

    @GetMapping(value={"/api/bde/v1/bizModel/listByCategory"})
    public BusinessResponseEntity<List<? extends BizModelDTO>> listByCategory(String category) {
        return BusinessResponseEntity.ok(this.bizModelService.listByCategory(category));
    }

    @GetMapping(value={"/api/bde/v1/bizModel/list"})
    public BusinessResponseEntity<List<BizModelDTO>> list() {
        return BusinessResponseEntity.ok(this.bizModelService.list());
    }

    public BusinessResponseEntity<List<BizModelAllPropsDTO>> listBizModelAllProps() {
        List<BizModelDTO> list = this.bizModelService.list();
        ArrayList<Object> allPropsDTOList = new ArrayList<Object>(list.size());
        for (BizModelDTO bizModelDTO : list) {
            if (ComputationModelEnum.CUSTOMFETCH.getCode().equals(bizModelDTO.getComputationModelCode())) {
                CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)bizModelDTO;
                if (ApplyScopeEnum.FLOAT.getCode().equals(customBizModelDTO.getApplyScope())) continue;
            }
            allPropsDTOList.add(BeanConvertUtil.convert((Object)bizModelDTO, BizModelAllPropsDTO.class, (String[])new String[0]));
        }
        return BusinessResponseEntity.ok(allPropsDTOList);
    }

    @GetMapping(value={"/api/bde/v1/bizModel/get/{bizModelCode"})
    public BusinessResponseEntity<BizModelDTO> get(@PathVariable(value="bizModelCode") String bizModelCode) {
        return BusinessResponseEntity.ok((Object)this.bizModelService.get(bizModelCode));
    }

    @GetMapping(value={"/api/bde/v1/fetch/getFetchSourceColumnDefines/{bizModelCode}"})
    public BusinessResponseEntity<BizModelColumnDefineVO> getColumnDefines(@PathVariable(value="bizModelCode") String bizModelCode) {
        return BusinessResponseEntity.ok((Object)this.bizModelServiceGather.getByCode(this.bizModelGather.getBizDataModelByComputationModel(((BizModelDTO)this.get(bizModelCode).getData()).getComputationModelCode()).getCategory()).getColumnDefines(bizModelCode));
    }

    public BusinessResponseEntity<Map<String, String>> getBaseDataInputConfig() {
        return BusinessResponseEntity.ok(this.bizModelBaseDataConfigService.getBaseDataInputConfig());
    }

    @GetMapping(value={"/api/bde/v1/fetch/customFetchComboBoxData"})
    public BusinessResponseEntity<CustomFetchFormVO> getCustomFetchFormData() {
        return BusinessResponseEntity.ok((Object)this.bizModelService.getCustomFetchFormData());
    }

    @GetMapping(value={"/api/bde/v1/fetch/queryTreeInit/{bizModelCode}"})
    public BusinessResponseEntity<List<TreeNode>> queryTreeInitByFetchSourceCode(@PathVariable(value="bizModelCode") String bizModelCode) {
        return BusinessResponseEntity.ok(this.bizModelService.queryTreeInitByFetchSourceCode(bizModelCode));
    }

    @PostMapping(value={"/api/bde/v1/fetch/queryExtInfo"})
    public BusinessResponseEntity<Map<String, List<ExtInfoResultVO>>> queryExtInfo(@RequestBody ExtInfoParamVO extInfoParamVO) {
        return BusinessResponseEntity.ok(this.bizModelSettingService.queryExtInfo(extInfoParamVO));
    }

    public BusinessResponseEntity<List<BizModelDTO>> listByTfv() {
        return BusinessResponseEntity.ok(this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_TFV.getCode()).listModel());
    }

    public BusinessResponseEntity<List<FinBizModelDTO>> listByFin() {
        return BusinessResponseEntity.ok(this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_FINDATA.getCode()).listModel());
    }

    public BusinessResponseEntity<List<BaseDataBizModelDTO>> listByBaseData() {
        return BusinessResponseEntity.ok(this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_BASEDATA.getCode()).listModel());
    }

    public BusinessResponseEntity<List<CustomBizModelDTO>> listByCustom() {
        return BusinessResponseEntity.ok(this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode()).listModel());
    }

    @GetMapping(value={"/api/bde/v1/fetch/listFetchSource"})
    @Deprecated
    public BusinessResponseEntity<List<BizModelDTO>> listFetchSource() {
        return BusinessResponseEntity.ok(this.bizModelService.list());
    }
}

