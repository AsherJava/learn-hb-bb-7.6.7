/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.inputdata.formsetting.service.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.DTO.ManagementDim;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO;
import com.jiuqi.gcreport.inputdata.formsetting.dao.OffsetDimSettingDao;
import com.jiuqi.gcreport.inputdata.formsetting.entity.OffsetDimSettingEO;
import com.jiuqi.gcreport.inputdata.formsetting.service.OffsetDimSettingService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OffsetDimSettingServiceImpl
implements OffsetDimSettingService {
    private OffsetDimSettingDao offsetDimSettingDao;
    private DimensionService dimensionService;
    private String[] mustRequiredOffsetDims = new String[]{"OPPUNITID", "SUBJECTOBJ", "AMT"};

    public OffsetDimSettingServiceImpl(OffsetDimSettingDao offsetDimSettingDao, DimensionService dimensionService) {
        this.offsetDimSettingDao = offsetDimSettingDao;
        this.dimensionService = dimensionService;
    }

    @Override
    public OffsetDimSettingVO getOffsetDimSettingByFormId(String formId) {
        OffsetDimSettingEO offsetDimSetting = this.offsetDimSettingDao.getOffsetDimSettingByFormId(formId);
        Set managementDims = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        HashSet allOffsetDims = new HashSet(managementDims);
        OffsetDimSettingVO offsetDimSettingVO = new OffsetDimSettingVO();
        if (offsetDimSetting != null) {
            allOffsetDims.addAll(Arrays.asList(offsetDimSetting.getOffsetDims().split(",")));
            offsetDimSetting.setOffsetDims(String.join((CharSequence)",", allOffsetDims));
            BeanUtils.copyProperties((Object)offsetDimSetting, offsetDimSettingVO);
            return offsetDimSettingVO;
        }
        allOffsetDims.addAll(Arrays.asList(this.mustRequiredOffsetDims));
        offsetDimSettingVO.setOffsetDims(String.join((CharSequence)",", this.getAllOffsetDims("")));
        return offsetDimSettingVO;
    }

    @Override
    public Set<String> getDimsByFormId(String formId, String reportSystem) {
        OffsetDimSettingEO offsetDimSetting = this.offsetDimSettingDao.getOffsetDimSettingByFormId(formId);
        return this.getAllOffsetDims(reportSystem, offsetDimSetting == null ? "" : offsetDimSetting.getOffsetDims());
    }

    private Set<String> getAllOffsetDims(String reportSystem, String dbOffsetDims) {
        Set managementDims = ((ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class)).getManagementDimsBySystemId(reportSystem).stream().map(ManagementDim::getCode).collect(Collectors.toSet());
        HashSet<String> allOffsetDims = new HashSet<String>();
        if (!StringUtils.isEmpty(dbOffsetDims)) {
            allOffsetDims.addAll(Arrays.asList(dbOffsetDims.split(",")));
        }
        allOffsetDims.addAll(Arrays.asList(this.mustRequiredOffsetDims));
        allOffsetDims.addAll(managementDims);
        return allOffsetDims;
    }

    private Set<String> getAllOffsetDims(String dbOffsetDims) {
        Set managementDims = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        HashSet<String> allOffsetDims = new HashSet<String>();
        if (!StringUtils.isEmpty(dbOffsetDims)) {
            allOffsetDims.addAll(Arrays.asList(dbOffsetDims.split(",")));
        }
        allOffsetDims.addAll(Arrays.asList(this.mustRequiredOffsetDims));
        allOffsetDims.addAll(managementDims);
        return allOffsetDims;
    }

    @Override
    public OffsetDimSettingVO save(OffsetDimSettingVO offsetDimSetting) {
        String logContent = String.format("\u4fee\u6539\u62b5\u9500\u7ef4\u5ea6-\u6307\u6807\u6807\u8bc6%1$s", offsetDimSetting.getOffsetDims());
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u62b5\u9500\u8868\u8bbe\u7f6e", (String)logContent, (String)logContent);
        OffsetDimSettingEO offsetDimSettingEntity = new OffsetDimSettingEO();
        BeanUtils.copyProperties(offsetDimSetting, (Object)offsetDimSettingEntity);
        offsetDimSettingEntity.setOperateTime(new Date());
        offsetDimSettingEntity.setDimOperator(NpContextHolder.getContext().getUserName());
        if (StringUtils.isEmpty(offsetDimSettingEntity.getId())) {
            this.offsetDimSettingDao.save(offsetDimSettingEntity);
            offsetDimSetting.setId(offsetDimSettingEntity.getId());
        } else {
            this.offsetDimSettingDao.update((BaseEntity)offsetDimSettingEntity);
        }
        return offsetDimSetting;
    }
}

