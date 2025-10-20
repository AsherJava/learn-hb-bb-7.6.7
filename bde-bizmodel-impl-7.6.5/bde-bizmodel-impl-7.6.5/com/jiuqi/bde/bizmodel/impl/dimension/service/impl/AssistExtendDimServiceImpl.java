/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.dimension.service.impl;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.dao.AssistExtendDimDao;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssistExtendDimServiceImpl
implements AssistExtendDimService {
    private final NedisCache assistExtendDimCache;
    @Autowired
    private AssistExtendDimDao assistExtendDimDao;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private VaDataModelPublishedService vaDataModelPublishedService;
    private static final String ALL_ASSIST_EXTEND = "ALL_ASSIST_EXTEND";
    private static final String START_ASSIST_EXTEND = "START_ASSIST_EXTEND";

    public AssistExtendDimServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_ASSIST_EXTENT_DIM");
        this.assistExtendDimCache = cacheManager.getCache("BDE_ASSIST_EXTENT");
    }

    @Override
    public AssistExtendDimVO getAssistExtendDimByCode(String code) {
        return (AssistExtendDimVO)this.assistExtendDimCache.get(code, () -> this.assistExtendDimDao.getAssistExtendDimByCode(code));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(AssistExtendDimVO assistExtendDimVO) {
        Assert.isNotEmpty((String)assistExtendDimVO.getCode(), (String)"\u7ef4\u5ea6\u5c5e\u6027\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)assistExtendDimVO.getAssistDimCode(), (String)"\u7ef4\u5ea6\u5c5e\u6027\u5173\u8054\u7ef4\u5ea6\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)assistExtendDimVO.getRefField(), (String)"\u7ef4\u5ea6\u5c5e\u6027\u5c5e\u6027\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)assistExtendDimVO.getMatchRule(), (String)"\u7ef4\u5ea6\u5c5e\u6027\u5339\u914d\u89c4\u5219\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List dimensions = this.dimensionService.loadAllDimensions();
        if (dimensions.stream().anyMatch(item -> item.getCode().equals(assistExtendDimVO.getCode()))) {
            throw new BusinessRuntimeException("\u7ef4\u5ea6\u5c5e\u6027\u4ee3\u7801\u4e0d\u5f97\u4e0e\u7ef4\u5ea6\u4ee3\u7801\u91cd\u590d");
        }
        if (dimensions.stream().anyMatch(item -> item.getTitle().equals(assistExtendDimVO.getName()))) {
            throw new BusinessRuntimeException("\u7ef4\u5ea6\u5c5e\u6027\u540d\u79f0\u4e0d\u5f97\u4e0e\u7ef4\u5ea6\u540d\u79f0\u91cd\u590d");
        }
        List<AssistExtendDimVO> assistExtendDimList = this.getAllAssistExtendDim();
        if (assistExtendDimList.stream().anyMatch(item -> item.getCode().equals(assistExtendDimVO.getCode()))) {
            throw new BusinessRuntimeException("\u7ef4\u5ea6\u5c5e\u6027\u6807\u8bc6\u5df2\u5b58\u5728");
        }
        if (assistExtendDimList.stream().anyMatch(item -> item.getName().equals(assistExtendDimVO.getName()))) {
            throw new BusinessRuntimeException("\u7ef4\u5ea6\u5c5e\u6027\u540d\u79f0\u5df2\u5b58\u5728");
        }
        this.assistExtendDimDao.save(assistExtendDimVO);
        this.assistExtendDimCacheClear();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(AssistExtendDimVO assistExtendDimVO) {
        this.assistExtendDimDao.update(assistExtendDimVO);
        this.assistExtendDimCacheClear();
    }

    @Override
    public List<AssistExtendDimVO> getAllStartAssistExtendDim() {
        return (List)this.assistExtendDimCache.get(START_ASSIST_EXTEND, () -> this.assistExtendDimDao.getAllStartAssistExtendDim());
    }

    @Override
    public List<AssistExtendDimVO> getAllAssistExtendDim() {
        return (List)this.assistExtendDimCache.get(ALL_ASSIST_EXTEND, () -> this.assistExtendDimDao.getAllAssistExtendDim());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void stopAssistExtendDimById(String id) {
        this.assistExtendDimDao.updateAssistExtendDimStopFlag(id, StopFlagEnum.STOP);
        this.assistExtendDimCacheClear();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void startAssistExtendDimById(String id) {
        this.assistExtendDimDao.updateAssistExtendDimStopFlag(id, StopFlagEnum.START);
        this.assistExtendDimCacheClear();
    }

    @Override
    public List<DataModelColumn> getBaseDataColumns(String name) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(name);
        return this.vaDataModelPublishedService.get(dataModelDTO).getColumns().stream().filter(item -> !BdeAssistDimUtils.SYS_SPECOL.contains(item.getColumnName().toLowerCase())).collect(Collectors.toList());
    }

    private void assistExtendDimCacheClear() {
        this.assistExtendDimCache.clear();
    }
}

