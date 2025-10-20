/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.define.gather.IBizModelGather
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.BizModelManageServiceImpl;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TfvBizModelManageServiceImpl
extends BizModelManageServiceImpl {
    @Autowired
    protected IBizModelGather bizModelGather;
    @Autowired
    private BizModelService bizModelService;
    public static final BizModelDTO DEFAULT_BIZMODELDTO = new BizModelDTO();

    @Override
    public String getCategoryCode() {
        return BizModelCategoryEnum.BIZMODEL_TFV.getCode();
    }

    @Override
    public BizModelDTO getById(String id) {
        Assert.isNotEmpty((String)id);
        return this.listModel().stream().filter(item -> id.equals(item.getId())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", id)));
    }

    @Override
    public BizModelDTO getByCode(String code) {
        Assert.isNotEmpty((String)code);
        return this.listModel().stream().filter(item -> code.equals(item.getCode())).findFirst().orElseThrow(() -> new BusinessRuntimeException(String.format("\u6839\u636e\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u8bf7\u68c0\u67e5\u4e1a\u52a1\u6a21\u578b\u662f\u5426\u88ab\u505c\u7528\u6216\u5220\u9664", code)));
    }

    @Override
    public String list() {
        return null;
    }

    public List<BizModelDTO> listModel() {
        return CollectionUtils.newArrayList((Object[])new BizModelDTO[]{DEFAULT_BIZMODELDTO});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String bizModelDtoStr) {
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(String bizModelDtoStr) {
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int start(String id) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int stop(String id) {
        return 0;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public void bizmodelCacheClear() {
    }

    @Override
    public BizModelDao getBizModelDao() {
        return null;
    }

    @Override
    public IBizModelGather getBizModelGather() {
        return null;
    }

    @Override
    public BizModelColumnDefineVO getColumnDefines(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        BizModelDTO bizModelDTO = this.bizModelService.get(bizModelCode);
        BizModelColumnDefineVO modelColumnDefine = new BizModelColumnDefineVO();
        ArrayList columnDefines = new ArrayList();
        IBizComputationModel bizModel = this.bizModelGather.getComputationModelByCode(bizModelDTO.getComputationModelCode());
        columnDefines.addAll(bizModel.getFixedFields());
        modelColumnDefine.setColumnDefines(columnDefines);
        modelColumnDefine.setOptionItems(bizModel.getOptionItems());
        modelColumnDefine.setFetchSourceCode(bizModelCode);
        modelColumnDefine.setComputationModelIcon(bizModel.getIcon());
        return modelColumnDefine;
    }

    static {
        DEFAULT_BIZMODELDTO.setId(UUIDUtils.emptyHalfGUIDStr());
        DEFAULT_BIZMODELDTO.setCode("INIT_ZDYQS");
        DEFAULT_BIZMODELDTO.setName(ComputationModelEnum.TFV.getName());
        DEFAULT_BIZMODELDTO.setComputationModelCode(ComputationModelEnum.TFV.getCode());
        DEFAULT_BIZMODELDTO.setComputationModelName(ComputationModelEnum.TFV.getName());
        DEFAULT_BIZMODELDTO.setOrdinal(new BigDecimal(Integer.MAX_VALUE));
        DEFAULT_BIZMODELDTO.setStopFlag(Integer.valueOf(0));
    }
}

