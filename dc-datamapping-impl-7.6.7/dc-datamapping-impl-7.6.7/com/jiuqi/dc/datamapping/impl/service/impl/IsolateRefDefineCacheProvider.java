/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.IsolateFieldMappingDao;
import com.jiuqi.dc.datamapping.impl.gather.IFieldMappingBizDataCacheGather;
import com.jiuqi.dc.datamapping.impl.service.FieldMappingBizDataCacheProvider;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class IsolateRefDefineCacheProvider {
    public static final String CACHE_KEY = "IsolateRefDefineCache";
    @Autowired
    private IFieldMappingBizDataCacheGather gather;
    @Autowired
    private DataSchemeCacheProvider dataSchemeCacheProvider;
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private IsolateFieldMappingDao isolateFieldMappingDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, DataRefMappingCacheDTO> getCache(IsolationParamContext isolateDimension, String dimCode) {
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + dimCode);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            return new HashMap<String, DataRefMappingCacheDTO>();
        }
        String isolationStrategy = (String)valueWrapper.get();
        FieldMappingBizDataCacheProvider serviceByCode = this.gather.getServiceByCode(isolationStrategy);
        return serviceByCode.getCache(isolateDimension, dimCode);
    }

    public List<DataRefDTO> getBaseMappingCache(IsolationParamContext isolateDimension, String dimCode) {
        Cache.ValueWrapper valueWrapper = this.cacheManger.getCache(CACHE_KEY).get(isolateDimension.getSchemeCode() + "|" + dimCode);
        if (!ObjectUtils.isEmpty(valueWrapper)) {
            String isolationStrategy = valueWrapper.get().toString();
            FieldMappingBizDataCacheProvider serviceByCode = this.gather.getServiceByCode(isolationStrategy);
            return serviceByCode.getBaseMappingCache(isolateDimension, dimCode);
        }
        return new ArrayList<DataRefDTO>();
    }

    public void syncCache(String dataSchemeCode, String tableName, String isolationStrategy) {
        this.cacheManger.getCache(CACHE_KEY).evict(dataSchemeCode + "|" + tableName);
        if (StringUtils.isEmpty((String)isolationStrategy)) {
            this.cacheManger.getCache(CACHE_KEY).put(dataSchemeCode + "|" + tableName, (Object)IsolationStrategy.SHARE.getCode());
        } else {
            this.cacheManger.getCache(CACHE_KEY).put(dataSchemeCode + "|" + tableName, (Object)isolationStrategy);
        }
        FieldMappingBizDataCacheProvider serviceByCode = this.gather.getServiceByCode(this.cacheManger.getCache(CACHE_KEY).get(dataSchemeCode + "|" + tableName).get().toString());
        serviceByCode.loadCache(dataSchemeCode, tableName);
    }

    public void mappingSaveSyncCache(String dataSchemeCode, String tableName, List<DataRefDTO> dataRefs) {
        FieldMappingBizDataCacheProvider serviceByCode = this.gather.getServiceByCode(this.cacheManger.getCache(CACHE_KEY).get(dataSchemeCode + "|" + tableName).get().toString());
        serviceByCode.loadCache(dataSchemeCode, tableName);
    }

    public void noClearLoadCache(String dataSchemeCode) {
        List schemelist = new ArrayList<CacheEntity>();
        if (!StringUtils.isEmpty((String)dataSchemeCode)) {
            schemelist.add(this.dataSchemeCacheProvider.get(dataSchemeCode));
        } else {
            schemelist = this.dataSchemeCacheProvider.list();
        }
        List dimensions = ((DimensionService)ApplicationContextRegister.getBean(DimensionService.class)).loadAllDimensions();
        Map<String, DimensionVO> dimensionMap = dimensions.stream().collect(Collectors.toMap(DimensionVO::getCode, dimension -> dimension, (existing, replacement) -> existing));
        for (DataSchemeDTO scheme : schemelist) {
            try {
                DimMappingVO cfitemMapping;
                DataMappingVO dataMapping = scheme.getDataMapping();
                OrgMappingVO orgMapping = dataMapping.getOrgMapping();
                String orgIsolationStrategy = IsolationStrategy.SHARE.getCode();
                for (Columns columns : orgMapping.getBaseMapping()) {
                    if (!"ASSISTCODE".equals(columns.getFieldName())) continue;
                    orgIsolationStrategy = IsolationStrategy.ORG_ASSISTCODE.getCode();
                }
                FieldMappingBizDataCacheProvider serviceByCode = this.gather.getServiceByCode(orgIsolationStrategy);
                this.cacheManger.getCache(CACHE_KEY).put(scheme.getCode() + "|" + orgMapping.getCode(), (Object)orgIsolationStrategy);
                serviceByCode.loadCache(scheme.getCode(), orgMapping.getCode());
                DimMappingVO subjectMapping = dataMapping.getSubjectMapping();
                if (!ObjectUtils.isEmpty(subjectMapping)) {
                    serviceByCode = this.gather.getServiceByCode(subjectMapping.getIsolationStrategy());
                    this.cacheManger.getCache(CACHE_KEY).put(scheme.getCode() + "|" + subjectMapping.getCode(), (Object)subjectMapping.getIsolationStrategy());
                    serviceByCode.loadCache(scheme.getCode(), subjectMapping.getCode());
                }
                if (!ObjectUtils.isEmpty(cfitemMapping = dataMapping.getCfitemMapping())) {
                    serviceByCode = this.gather.getServiceByCode(cfitemMapping.getIsolationStrategy());
                    this.cacheManger.getCache(CACHE_KEY).put(scheme.getCode() + "|" + cfitemMapping.getCode(), (Object)cfitemMapping.getIsolationStrategy());
                    serviceByCode.loadCache(scheme.getCode(), cfitemMapping.getCode());
                }
                List assistMappings = dataMapping.getAssistMapping();
                for (AssistMappingVO assistMapping : assistMappings) {
                    if (StringUtils.isEmpty((String)assistMapping.getAdvancedSql()) || "CUSTOMFIELD".equals(assistMapping.getOdsFieldName())) continue;
                    if (StringUtils.isEmpty((String)assistMapping.getIsolationStrategy())) {
                        assistMapping.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
                    }
                    serviceByCode = this.gather.getServiceByCode(assistMapping.getIsolationStrategy());
                    this.cacheManger.getCache(CACHE_KEY).put(scheme.getCode() + "|" + assistMapping.getCode(), (Object)assistMapping.getIsolationStrategy());
                    serviceByCode.loadCache(scheme.getCode(), assistMapping.getCode());
                }
                List advancedMappings = dataMapping.getAdvancedMapping();
                block5: for (AdvancedMappingVO advancedMapping : advancedMappings) {
                    DimensionVO dimensionVO = dimensionMap.get(advancedMapping.getCode());
                    if (!"dims".equals(dimensionVO.getDimensionType())) continue;
                    BizMappingVO bizMapping = advancedMapping.getBizMapping();
                    for (String value : bizMapping.values()) {
                        if (StringUtils.isEmpty((String)value)) continue;
                        if (StringUtils.isEmpty((String)advancedMapping.getIsolationStrategy())) {
                            advancedMapping.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
                        }
                        serviceByCode = this.gather.getServiceByCode(advancedMapping.getIsolationStrategy());
                        this.cacheManger.getCache(CACHE_KEY).put(scheme.getCode() + "|" + advancedMapping.getCode(), (Object)advancedMapping.getIsolationStrategy());
                        serviceByCode.loadCache(scheme.getCode(), advancedMapping.getCode());
                        continue block5;
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u6570\u636e\u6620\u5c04\u65b9\u6848" + scheme.getCode() + "\u521d\u59cb\u5316\u6620\u5c04\u7f13\u5b58\u62a5\u9519", (Object)e.getMessage());
            }
        }
        this.logger.info("\u9694\u79bb\u6620\u5c04\u5b9a\u4e49\u7f13\u5b58\u63d0\u4f9b\u5668\u7f13\u5b58\u6210\u529f");
    }

    public void loadCache(String dataSchemeCode) {
        this.cacheManger.getCache(CACHE_KEY).clear();
        this.noClearLoadCache(dataSchemeCode);
    }

    public Set<String> getUnitCodeRange(IsolationParamContext isolateDimension, String dimCode, String isolationStrategy) {
        FieldMappingBizDataCacheProvider service = this.gather.getServiceByCode(isolationStrategy);
        Assert.isNotNull((Object)service, (String)String.format("\u6682\u4e0d\u652f\u6301\u7684\u9694\u79bb\u7b56\u7565\uff1a%1$s", isolationStrategy), (Object[])new Object[0]);
        List<DataRefDTO> refOrgList = IsolationStrategy.BOOKCODE.getCode().equals(isolationStrategy) ? this.isolateFieldMappingDao.loadIsolateAllMapping(IsolationStrategy.BOOKCODE, isolateDimension.getSchemeCode(), "MD_ORG") : this.getBaseMappingCache(isolateDimension, "MD_ORG");
        if (CollectionUtils.isEmpty(refOrgList)) {
            return CollectionUtils.newHashSet();
        }
        return service.getUnitCodeRange(isolateDimension, dimCode, refOrgList);
    }
}

