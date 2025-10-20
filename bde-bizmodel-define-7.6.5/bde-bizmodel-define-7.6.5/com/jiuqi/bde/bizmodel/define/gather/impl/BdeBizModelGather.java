/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.exception.BusinessException
 */
package com.jiuqi.bde.bizmodel.define.gather.impl;

import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;

@DependsOn(value={"i18nHelperSupport"})
public class BdeBizModelGather
implements IBizModelGather {
    private List<IBizDataModel> registeredDataModelList;
    private List<IBizComputationModel> registeredComputationModelList;
    protected List<IBizDataModel> bizDataModelList = new ArrayList<IBizDataModel>();
    protected Map<String, IBizDataModel> bizDataModelMap = new ConcurrentHashMap<String, IBizDataModel>(16);
    protected List<IBizComputationModel> computationModelList = new ArrayList<IBizComputationModel>();
    protected Map<String, IBizComputationModel> computationModelMap = new ConcurrentHashMap<String, IBizComputationModel>(16);
    private static final Logger logger = LoggerFactory.getLogger(BdeBizModelGather.class);

    public BdeBizModelGather(@Autowired(required=false) List<IBizDataModel> registeredDataModelList, @Autowired(required=false) List<IBizComputationModel> registeredComputationModelList) {
        this.registeredDataModelList = registeredDataModelList;
        this.registeredComputationModelList = registeredComputationModelList;
        this.init();
    }

    @Override
    public List<IBizDataModel> listBizDataModel() {
        return Collections.unmodifiableList(this.getBizDataModelList());
    }

    @Override
    public List<IBizDataModel> listBizDataModelByCategory(String category) {
        Assert.isNotEmpty((String)category);
        return this.getBizDataModelList().stream().filter(item -> category.equals(item.getCategory())).collect(Collectors.toList());
    }

    @Override
    public IBizDataModel getBizDataModel(String bizDataModelCode) {
        IBizDataModel bizDataModel = this.getBizDataModelMap().get(bizDataModelCode);
        if (bizDataModel != null) {
            return bizDataModel;
        }
        throw new BusinessException(String.format("\u6839\u636e\u6570\u636e\u6a21\u578b\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u6a21\u578b\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u6570\u636e\u6a21\u578b\u8303\u56f4", bizDataModelCode));
    }

    @Override
    public IBizDataModel getBizDataModelByComputationModel(String computationModelCode) {
        return this.getBizDataModel(this.getComputationModelByCode(computationModelCode).getBizDataCode());
    }

    @Override
    public IBizComputationModel getComputationModelByCode(String computationModelCode) {
        Assert.isNotEmpty((String)computationModelCode, (String)"\u8ba1\u7b97\u6a21\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        IBizComputationModel computationModel = this.getComputationModelMap().get(computationModelCode);
        if (computationModel == null) {
            throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u4ee3\u7801\u4e3a\u3010%1$s\u3011\u7684\u8ba1\u7b97\u6a21\u578b\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e", computationModelCode));
        }
        return computationModel;
    }

    @Override
    public IBizComputationModel findComputationModelByCode(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode, (String)"\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        return this.getComputationModelMap().get(bizModelCode);
    }

    @Override
    public List<IBizComputationModel> listComputationModel() {
        return Collections.unmodifiableList(this.getComputationModelList());
    }

    @Override
    public List<IBizComputationModel> listComputationModelByCategory(String category) {
        Assert.isNotEmpty((String)category);
        List<IBizDataModel> bizDataModelList = this.listBizDataModelByCategory(category);
        ArrayList result = CollectionUtils.newArrayList();
        for (IBizDataModel bizDataModel : bizDataModelList) {
            result.addAll(this.listComputationModelByDataModel(bizDataModel.getCode()));
        }
        return result;
    }

    @Override
    public List<IBizComputationModel> listComputationModelByDataModel(String dataModel) {
        Assert.isNotEmpty((String)dataModel);
        return this.getComputationModelList().stream().filter(item -> dataModel.equals(item.getBizDataCode())).collect(Collectors.toList());
    }

    @Override
    public void reload() {
        this.bizDataModelList.clear();
        this.bizDataModelMap.clear();
        this.computationModelList.clear();
        this.computationModelMap.clear();
        if (CollectionUtils.isEmpty(this.registeredDataModelList)) {
            this.registeredDataModelList = new ArrayList<IBizDataModel>();
        }
        if (CollectionUtils.isEmpty(this.registeredComputationModelList)) {
            this.registeredComputationModelList = new ArrayList<IBizComputationModel>();
        }
        this.registeredDataModelList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                logger.warn("\u6570\u636e\u6a21\u578b\u52a0\u8f7d\u63d2\u4ef6{}\uff0c\u6570\u636e\u6a21\u578b\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.bizDataModelList.add((IBizDataModel)item);
            this.bizDataModelMap.putIfAbsent(item.getCode(), (IBizDataModel)item);
        });
        this.bizDataModelList.sort((p1, p2) -> p1.getOrder() - p2.getOrder());
        this.registeredComputationModelList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                logger.warn("\u4e1a\u52a1\u6a21\u578b\u5b9a\u4e49{}\uff0c\u4e1a\u52a1\u6a21\u578b\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (this.bizDataModelMap.get(item.getBizDataCode()) == null) {
                logger.warn("\u4e1a\u52a1\u6a21\u578b\u5b9a\u4e49{}\uff0c\u6570\u636e\u6a21\u578b\u4ee3\u7801\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.computationModelMap.containsKey(item.getCode())) {
                this.computationModelMap.put(item.getCode(), (IBizComputationModel)item);
            }
            this.computationModelList.add((IBizComputationModel)item);
        });
        this.computationModelList.sort(Comparator.comparingInt(IBizComputationModel::getOrder));
        this.computationModelList.sort((p1, p2) -> p1.getOrder() - p2.getOrder());
    }

    public List<IBizDataModel> getBizDataModelList() {
        return this.bizDataModelList;
    }

    public Map<String, IBizDataModel> getBizDataModelMap() {
        return this.bizDataModelMap;
    }

    public List<IBizComputationModel> getComputationModelList() {
        return this.computationModelList;
    }

    public Map<String, IBizComputationModel> getComputationModelMap() {
        return this.computationModelMap;
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u6a21\u578b\u52a0\u8f7d\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }
}

