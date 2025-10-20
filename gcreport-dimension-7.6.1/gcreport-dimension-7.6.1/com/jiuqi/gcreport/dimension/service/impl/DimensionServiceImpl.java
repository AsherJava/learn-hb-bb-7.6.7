/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.dimension.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.dimension.internal.cache.DimensionCacheService;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.internal.enums.EffectTablePublishStateEnum;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimSelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimTableRelVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionServiceImpl
implements DimensionService {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DimensionCacheService dimensionCacheService;

    @Override
    public List<DimensionVO> loadAllDimensions() {
        Map<String, DimensionVO> dimensionVOMap = this.dimensionCacheService.getDim2TablesCache();
        return new ArrayList<DimensionVO>(dimensionVOMap.values());
    }

    @Override
    public DimensionVO getDimensionByCode(String code) {
        return this.dimensionCacheService.getDimensionByCode(code);
    }

    @Override
    public List<DimensionVO> findDimFieldsVOByTableName(String tableName) {
        List<DimensionVO> allDimFieldsVOByTableName = this.findAllDimFieldsVOByTableName(tableName);
        return allDimFieldsVOByTableName.stream().filter(dimension -> new Integer(1).equals(dimension.getGroupDimFlag())).collect(Collectors.toList());
    }

    @Override
    public List<DimensionVO> findAllDimFieldsVOByTableName(String tableName) {
        List<DimensionEO> dimensions = this.findAllDimFieldsByTableName(tableName);
        return dimensions.stream().map(dimension -> {
            DimensionVO dimensionVO = new DimensionVO((DimensionEO)dimension);
            dimensionVO.setDictTableName(this.getTableName(dimension.getReferTable()));
            return dimensionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DimensionEO> findDimFieldsByTableName(String tableName) {
        List<DimensionEO> dimFieldsByTableName = this.findAllDimFieldsByTableName(tableName);
        return dimFieldsByTableName.stream().filter(dimension -> new Integer(1).equals(dimension.getGroupDimFlag())).collect(Collectors.toList());
    }

    @Override
    public List<DimensionEO> findAllDimFieldsByTableName(String tableName) {
        if (StringUtils.isEmpty((String)tableName)) {
            return Collections.emptyList();
        }
        tableName = tableName.toUpperCase();
        Map<String, List<DimensionEO>> table2DimCache = this.dimensionCacheService.getTable2DimCache();
        List<DimensionEO> dimensionEOS = table2DimCache.get(tableName);
        if (CollectionUtils.isEmpty(dimensionEOS)) {
            String sourceTable;
            EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
            BaseEntity entity = entityTableCollector.getEntityByName(tableName);
            if (entity == null) {
                return Collections.emptyList();
            }
            DBTable dbTableAnnotation = entityTableCollector.getDbTableByType(entity.getClass());
            if (dbTableAnnotation != null && !StringUtils.isEmpty((String)(sourceTable = dbTableAnnotation.sourceTable()))) {
                dimensionEOS = table2DimCache.get(sourceTable.toUpperCase());
            }
        }
        if (CollectionUtils.isEmpty(dimensionEOS)) {
            return Collections.emptyList();
        }
        return dimensionEOS.stream().peek(dimItem -> dimItem.setTitle(this.getLocalMessage(dimItem.getId(), dimItem.getTitle()))).collect(Collectors.toList());
    }

    @Override
    public List<DimSelectOptionVO> findManagementDimensionVO() {
        List<DimensionEO> dimensions = this.findAllDimFieldsByTableName("GC_OFFSETVCHRITEM");
        return dimensions.stream().map(dim -> {
            DimSelectOptionVO selectOptionVO = new DimSelectOptionVO();
            selectOptionVO.setLabel("[" + dim.getCode() + "]" + dim.getTitle());
            selectOptionVO.setValue(dim.getId());
            return selectOptionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Set<String> listTableNamesByDimCode(String code) {
        Map<String, DimensionVO> dim2TablesCache = this.dimensionCacheService.getDim2TablesCache();
        DimensionVO dimensionVO = dim2TablesCache.get(code);
        if (Objects.isNull(dimensionVO)) {
            return new HashSet<String>();
        }
        List<DimTableRelVO> dimTableRelVOS = dimensionVO.getDimTableRelVOS();
        return dimTableRelVOS.stream().filter(dimTableRelEO -> EffectTablePublishStateEnum.SUCCESS.getCode().equals(dimTableRelEO.getState()) || EffectTablePublishStateEnum.UPDATE_FAILURE.getCode().equals(dimTableRelEO.getState())).map(DimTableRelVO::getEffectTableName).collect(Collectors.toSet());
    }

    private String getLocalMessage(String key, String defaultTitle) {
        GcI18nHelper i18Provider = (GcI18nHelper)SpringBeanUtils.getBean(GcI18nHelper.class);
        String localTitle = i18Provider.getMessage(key);
        return StringUtils.isEmpty((String)localTitle) ? defaultTitle : localTitle;
    }

    private String getTableName(String tableId) {
        TableModelDefine table;
        try {
            table = this.dataModelService.getTableModelDefineById(tableId);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6807\u8bc6\u4e3a\u201c" + tableId + "\u201d\u7684\u8868\u5b9a\u4e49\u5931\u8d25\u3002");
        }
        if (table == null) {
            return "";
        }
        return table.getName();
    }
}

