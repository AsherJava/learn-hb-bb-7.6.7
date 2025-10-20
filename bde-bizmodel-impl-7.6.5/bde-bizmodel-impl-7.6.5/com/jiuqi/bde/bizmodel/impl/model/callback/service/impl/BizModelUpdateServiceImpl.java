/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.Dimension
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.model.callback.service.impl;

import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.bizmodel.impl.model.callback.FetchSourceUpdateEO;
import com.jiuqi.bde.bizmodel.impl.model.callback.dao.BizModelUpdateDao;
import com.jiuqi.bde.bizmodel.impl.model.callback.service.BizModelUpdateService;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BizModelUpdateServiceImpl
implements BizModelUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(BizModelUpdateServiceImpl.class);
    private static final String LOG_PREFIX = "\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7-";
    @Autowired
    private BizModelUpdateDao bizModelUpdateDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void bizModelUpdate() {
        logger.info("\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7-\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7\u5f00\u59cb");
        List<FetchSourceUpdateEO> fetchSourceDataList = this.bizModelUpdateDao.loadAll();
        if (CollectionUtils.isEmpty(fetchSourceDataList)) {
            logger.info("\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7-\u4e1a\u52a1\u6a21\u578b\u9700\u8981\u5347\u7ea7\u7684\u6570\u636e\u4e3a\u7a7a\u3002");
            return;
        }
        ArrayList finBizModelList = CollectionUtils.newArrayList();
        ArrayList customBizModelList = CollectionUtils.newArrayList();
        ArrayList baseDataBizModelList = CollectionUtils.newArrayList();
        for (FetchSourceUpdateEO fetchSourceUpdateEO : fetchSourceDataList) {
            FinBizModelEO finBizModelData = this.convertFinBizModel(fetchSourceUpdateEO);
            CustomBizModelEO customBizModelData = this.convertCustomBizModel(fetchSourceUpdateEO);
            BaseDataBizModelEO baseDataBizModelData = this.convertBaseDataBizModel(fetchSourceUpdateEO);
            if (finBizModelData != null) {
                finBizModelList.add(finBizModelData);
            }
            if (customBizModelData != null) {
                customBizModelList.add(customBizModelData);
            }
            if (baseDataBizModelData == null) continue;
            baseDataBizModelList.add(baseDataBizModelData);
        }
        this.bizModelUpdateDao.insertFinBizModel(finBizModelList);
        this.bizModelUpdateDao.insertCustomBizModel(customBizModelList);
        this.bizModelUpdateDao.insertBaseDataBizModel(baseDataBizModelList);
        logger.info("\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7-\u4e1a\u52a1\u6a21\u578b\u6570\u636e\u5347\u7ea7\u7ed3\u675f");
    }

    private FinBizModelEO convertFinBizModel(FetchSourceUpdateEO fetchSourceData) {
        if (ComputationModelEnum.TFV.getCode().equals(fetchSourceData.getBizModelCode()) || ComputationModelEnum.BASEDATA.getCode().equals(fetchSourceData.getBizModelCode()) || ComputationModelEnum.CUSTOMFETCH.getCode().equals(fetchSourceData.getBizModelCode())) {
            return null;
        }
        FinBizModelEO finBizModelData = new FinBizModelEO();
        finBizModelData.setId(UUIDUtils.newUUIDStr());
        finBizModelData.setCode(fetchSourceData.getCode());
        finBizModelData.setName(fetchSourceData.getName());
        finBizModelData.setComputationModelCode(fetchSourceData.getBizModelCode());
        finBizModelData.setStopFlag(fetchSourceData.getStopFlag());
        finBizModelData.setOrdinal(fetchSourceData.getOrdinal());
        finBizModelData.setFetchTypes(fetchSourceData.getFetchTypes());
        String computationModelCode = fetchSourceData.getBizModelCode();
        if (ComputationModelEnum.BALANCE.getCode().equals(computationModelCode) || ComputationModelEnum.CFLBALANCE.getCode().equals(computationModelCode) || ComputationModelEnum.AGINGBALANCE.getCode().equals(computationModelCode)) {
            finBizModelData.setSelectAll(BooleanValEnum.NO.getCode());
        } else if (!StringUtils.isEmpty((String)fetchSourceData.getDimensions())) {
            String[] dimensionCodes;
            ArrayList dimensions = CollectionUtils.newArrayList();
            for (String code : dimensionCodes = fetchSourceData.getDimensions().split(",")) {
                Dimension dimension = new Dimension();
                dimension.setDimensionCode(code);
                dimension.setRequired(Boolean.valueOf(true));
                dimensions.add(dimension);
            }
            finBizModelData.setDimensions(JsonUtils.writeValueAsString((Object)dimensions));
            finBizModelData.setSelectAll(BooleanValEnum.NO.getCode());
        } else {
            finBizModelData.setSelectAll(BooleanValEnum.YES.getCode());
        }
        return finBizModelData;
    }

    private CustomBizModelEO convertCustomBizModel(FetchSourceUpdateEO fetchSourceData) {
        if (!ComputationModelEnum.CUSTOMFETCH.getCode().equals(fetchSourceData.getBizModelCode())) {
            return null;
        }
        CustomBizModelEO customBizModelData = new CustomBizModelEO();
        customBizModelData.setId(UUIDUtils.newUUIDStr());
        customBizModelData.setCode(fetchSourceData.getCode());
        customBizModelData.setName(fetchSourceData.getName());
        customBizModelData.setComputationModelCode(fetchSourceData.getBizModelCode());
        customBizModelData.setStopFlag(fetchSourceData.getStopFlag());
        customBizModelData.setOrdinal(fetchSourceData.getOrdinal());
        customBizModelData.setFetchTable(fetchSourceData.getBaseDataDefine());
        customBizModelData.setFetchFields(fetchSourceData.getFetchTypes());
        customBizModelData.setFixedCondition(fetchSourceData.getFixedCondition());
        customBizModelData.setCustomCondition(fetchSourceData.getCustomCondition());
        return customBizModelData;
    }

    private BaseDataBizModelEO convertBaseDataBizModel(FetchSourceUpdateEO fetchSourceData) {
        if (!ComputationModelEnum.BASEDATA.getCode().equals(fetchSourceData.getBizModelCode())) {
            return null;
        }
        BaseDataBizModelEO baseDataBizModelData = new BaseDataBizModelEO();
        baseDataBizModelData.setId(UUIDUtils.newUUIDStr());
        baseDataBizModelData.setCode(fetchSourceData.getCode());
        baseDataBizModelData.setName(fetchSourceData.getName());
        baseDataBizModelData.setComputationModelCode(fetchSourceData.getBizModelCode());
        baseDataBizModelData.setStopFlag(fetchSourceData.getStopFlag());
        baseDataBizModelData.setOrdinal(fetchSourceData.getOrdinal());
        baseDataBizModelData.setBaseDataDefine(fetchSourceData.getBaseDataDefine());
        baseDataBizModelData.setFetchFields(fetchSourceData.getFetchTypes());
        return baseDataBizModelData;
    }
}

