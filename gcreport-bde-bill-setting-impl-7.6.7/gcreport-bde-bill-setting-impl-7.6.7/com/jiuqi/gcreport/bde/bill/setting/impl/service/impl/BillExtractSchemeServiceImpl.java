/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSchemeDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillDefineRefChecker;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillExtractSchemeUnifiedHandler;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFetchCondiService;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSchemeDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillExtractSchemeServiceImpl
implements BillExtractSchemeService,
IBillDefineRefChecker {
    @Autowired
    private FetchSchemeDao fetchSchemeDao;
    private final NedisCache schemeCache;
    @Autowired
    private BillFetchCondiService fetchCondiService;
    @Autowired(required=false)
    private List<IBillExtractSchemeUnifiedHandler> unifiedHandlerList;
    private static final int FETCH_SCHEME_NAME_LENGTH = 36;

    public BillExtractSchemeServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("BDE_BILL_EXTRACT_SCHEME_MANAGE");
        this.schemeCache = cacheManager.getCache("BDE_BILL_EXTRACT_SCHEME");
    }

    @Override
    public List<BillFetchSchemeDTO> listScheme(String billType) {
        return (List)this.schemeCache.get(billType, () -> this.fetchSchemeDao.listFetchSchemeByFormSchemeId(billType).stream().map(this::convert2Dto).collect(Collectors.toList()));
    }

    @Override
    public BillFetchSchemeDTO findById(String id) {
        Assert.isNotEmpty((String)id);
        return (BillFetchSchemeDTO)this.schemeCache.get(id, () -> this.convert2Dto(this.fetchSchemeDao.selectById(id)));
    }

    @Override
    public BillFetchSchemeDTO getById(String id) {
        Assert.isNotEmpty((String)id);
        BillFetchSchemeDTO fetchScheme = this.findById(id);
        if (fetchScheme == null) {
            throw new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u65b9\u6848", id));
        }
        return fetchScheme;
    }

    private BillFetchSchemeDTO convert2Dto(FetchSchemeEO fetchSchemeEO) {
        if (fetchSchemeEO == null) {
            return null;
        }
        BillFetchSchemeDTO dto = (BillFetchSchemeDTO)BeanConvertUtil.convert((Object)fetchSchemeEO, BillFetchSchemeDTO.class, (String[])new String[0]);
        dto.setBillType(fetchSchemeEO.getFormSchemeId());
        dto.setBizType(fetchSchemeEO.getBizType().getCode());
        return dto;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean save(BillFetchSchemeDTO scheme) {
        this.check(scheme);
        FetchSchemeEO fetchScheme = this.convert2EO(scheme);
        this.fetchSchemeDao.save(fetchScheme);
        this.fetchCondiService.initBillFetchCondi(fetchScheme.getId());
        this.schemeCache.evict(scheme.getBillType());
        return true;
    }

    private FetchSchemeEO convert2EO(BillFetchSchemeDTO scheme) {
        FetchSchemeEO fetchScheme = (FetchSchemeEO)BeanConvertUtil.convert((Object)scheme, FetchSchemeEO.class, (String[])new String[0]);
        fetchScheme.setFormSchemeId(scheme.getBillType());
        fetchScheme.setBizType(BizTypeEnum.getEnumByCode((String)scheme.getBizType()));
        return fetchScheme;
    }

    private void check(BillFetchSchemeDTO scheme) {
        Assert.isNotEmpty((String)scheme.getBillType());
        Assert.isNotEmpty((String)scheme.getBizType());
        if (scheme.getName().length() > 36 || scheme.getName().getBytes(StandardCharsets.UTF_8).length > 36) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u540d\u79f0\u8d85\u8fc7\u6700\u5927\u957f\u5ea6\uff0c\u8bf7\u4fee\u6539\u3002");
        }
        List fetchSchemeDatas = this.fetchSchemeDao.loadAllByBizType(scheme.getBizType());
        if (CollectionUtils.isEmpty((Collection)fetchSchemeDatas)) {
            return;
        }
        FetchSchemeEO repeatNameScheme = fetchSchemeDatas.stream().filter(item -> item.getFormSchemeId().equals(scheme.getBillType()) && item.getName().equals(scheme.getName()) && !item.getId().equals(scheme.getId())).findFirst().orElse(null);
        if (repeatNameScheme != null) {
            throw new BdeRuntimeException("\u53d6\u6570\u65b9\u6848\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d");
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean update(BillFetchSchemeDTO scheme) {
        Assert.isNotNull((Object)scheme);
        Assert.isNotEmpty((String)scheme.getId());
        this.check(scheme);
        BillFetchSchemeDTO oldFetchScheme = this.getById(scheme.getId());
        scheme.setBizType(oldFetchScheme.getBizType());
        scheme.setBillType(oldFetchScheme.getBillType());
        FetchSchemeEO fetchScheme = this.convert2EO(scheme);
        this.fetchSchemeDao.update(fetchScheme);
        this.schemeCache.evict(oldFetchScheme.getBillType());
        this.schemeCache.evict(scheme.getId());
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delete(String schemeId) {
        Assert.isNotEmpty((String)schemeId);
        BillFetchSchemeDTO schemeDto = this.getById(schemeId);
        FetchSchemeEO delSchemeCondi = new FetchSchemeEO();
        delSchemeCondi.setId(schemeId);
        this.fetchSchemeDao.delete(delSchemeCondi);
        this.fetchCondiService.deleteBillFetchCondiByFetchSchemeId(schemeId);
        this.unifiedHandlerList.forEach(handler -> handler.delete(schemeDto));
        this.unifiedHandlerList.forEach(handler -> handler.syncCache(schemeDto));
        this.schemeCache.evict(schemeDto.getBillType());
        this.schemeCache.evict(schemeId);
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean publish(String schemeId) {
        Assert.isNotEmpty((String)schemeId);
        BillFetchSchemeDTO schemeDto = this.getById(schemeId);
        this.unifiedHandlerList.forEach(handler -> handler.publish(schemeDto));
        this.unifiedHandlerList.forEach(handler -> handler.syncCache(schemeDto));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean copy(String schemeId, String newSchemeName) {
        Assert.isNotEmpty((String)newSchemeName);
        BillFetchSchemeDTO srcSchemeDTO = this.getById(schemeId);
        BillFetchSchemeDTO schemeDto = (BillFetchSchemeDTO)BeanConvertUtil.convert((Object)srcSchemeDTO, BillFetchSchemeDTO.class, (String[])new String[0]);
        schemeDto.setOrdinal(new BigDecimal(System.currentTimeMillis()));
        schemeDto.setId(UUIDUtils.newHalfGUIDStr());
        schemeDto.setName(newSchemeName);
        this.save(schemeDto);
        this.fetchCondiService.copyBillFetchCondiDTOByFetchSchemeId(schemeId, schemeDto.getId());
        this.unifiedHandlerList.forEach(handler -> handler.copy(srcSchemeDTO, schemeDto.getId()));
        this.schemeCache.evict(schemeDto.getBillType());
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean exchangeOrdinal(String srcSchemeId, String targetSchemeId) {
        Assert.isNotEmpty((String)srcSchemeId);
        Assert.isNotEmpty((String)targetSchemeId);
        Assert.isTrue((!srcSchemeId.equals(targetSchemeId) ? 1 : 0) != 0, (String)"\u4ea4\u6362\u524d\u540e\u7684\u6807\u8bc6\u4e0d\u80fd\u76f8\u540c", (Object[])new Object[0]);
        BillFetchSchemeDTO srcFetchScheme = this.getById(srcSchemeId);
        Assert.isNotNull((Object)srcFetchScheme, (String)String.format("\u6807\u8bc6\u3010%1$s\u3011\u5bf9\u5e94\u7684\u6570\u636e\u65b9\u6848\u5df2\u7ecf\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", srcSchemeId), (Object[])new Object[0]);
        BillFetchSchemeDTO targetFetchScheme = this.getById(targetSchemeId);
        Assert.isNotNull((Object)srcFetchScheme, (String)String.format("\u6807\u8bc6\u3010%1$s\u3011\u5bf9\u5e94\u7684\u6570\u636e\u65b9\u6848\u5df2\u7ecf\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", targetSchemeId), (Object[])new Object[0]);
        this.fetchSchemeDao.updateOrdinalById(srcSchemeId, targetFetchScheme.getOrdinal());
        this.fetchSchemeDao.updateOrdinalById(targetSchemeId, srcFetchScheme.getOrdinal());
        this.schemeCache.evict(srcFetchScheme.getBillType());
        this.schemeCache.evict(srcSchemeId);
        this.schemeCache.evict(targetSchemeId);
        return true;
    }

    @Override
    public boolean hasRef(String billType) {
        List<BillFetchSchemeDTO> schemeList = this.listScheme(billType);
        return !CollectionUtils.isEmpty(schemeList);
    }
}

