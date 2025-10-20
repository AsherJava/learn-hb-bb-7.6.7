/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.consts.CommonConst$ProductIdentificationEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum
 *  com.jiuqi.gcreport.dimension.dto.DimTableRelDTO
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather
 *  com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO
 *  com.jiuqi.gcreport.dimension.vo.EffectTableVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.base.impl.assistdim.service.impl;

import com.jiuqi.common.base.consts.CommonConst;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService;
import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DcDimensionCustomPublishServiceImpl
implements DimensionCustomPublishService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DefaultPublishServiceImpl publishService;
    @Autowired(required=false)
    private List<DimensionEffectScopeGather> scopeGathers;
    @Autowired
    private DcDimensionCustomPublishServiceImpl dcDimensionCustomPublishService;
    @Value(value="${spring.datasource.dbType:'oracle'}")
    private String dbType;

    public List<String> getTableNames() {
        ArrayList<String> tableNameList = new ArrayList<String>();
        if (Objects.isNull(this.scopeGathers) || this.scopeGathers.isEmpty()) {
            return tableNameList;
        }
        for (DimensionEffectScopeGather scopeGather : this.scopeGathers) {
            if (!CommonConst.ProductIdentificationEnum.DATACENTER.getCode().equals(scopeGather.getSysCode())) continue;
            tableNameList.addAll(scopeGather.getEffectTableVO().stream().map(EffectTableVO::getTableName).collect(Collectors.toList()));
        }
        return tableNameList;
    }

    public void publish(String tableName, DimensionDTO dimensionDTO) {
        BaseEntity entity = this.getBaseEntity(tableName);
        if (entity == null) {
            return;
        }
        if (dimensionDTO.getFieldType().intValue() == FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue()) {
            dimensionDTO.setDefaultValue("'#'");
        }
        if (dimensionDTO.getFieldType().intValue() == FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue()) {
            dimensionDTO.setDefaultValue("0.00");
        }
        if (dimensionDTO.getFieldType().intValue() == FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue()) {
            dimensionDTO.setDefaultValue("1970-01-01");
        }
        if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
            List shardingList = ((ShardingBaseEntity)entity).getShardingList();
            if (!CollectionUtils.isEmpty((Collection)shardingList)) {
                for (String sharding : shardingList) {
                    String shardingTableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + sharding).toUpperCase();
                    this.dcDimensionCustomPublishService.tablePublishDim(dimensionDTO, shardingTableName);
                }
            }
            return;
        }
        TableModelDefine tableModelDefine = this.publishService.checkDesignAndRunTimeDiff(tableName, dimensionDTO.getCode());
        this.publishService.publish(tableModelDefine, dimensionDTO);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void tablePublishDim(DimensionDTO dimensionDTO, String shardingTableName) {
        TableModelDefine tableModelDefine = this.publishService.checkDesignAndRunTimeDiff(shardingTableName, dimensionDTO.getCode());
        this.publishService.publish(tableModelDefine, dimensionDTO);
        if (dimensionDTO.getConvertByOpposite() == 1 && shardingTableName.startsWith("DC_VOUCHERITEMASS_")) {
            DimensionDTO srcDimensionDTO = new DimensionDTO();
            BeanUtils.copyProperties(dimensionDTO, srcDimensionDTO);
            srcDimensionDTO.setCode("SRC" + dimensionDTO.getCode());
            srcDimensionDTO.setReferField(null);
            srcDimensionDTO.setReferTable(null);
            TableModelDefine srcTtableModelDefine = this.publishService.checkDesignAndRunTimeDiff(shardingTableName, srcDimensionDTO.getCode());
            this.publishService.publish(srcTtableModelDefine, srcDimensionDTO);
        }
    }

    @Nullable
    private BaseEntity getBaseEntity(String tableName) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List baseEntityList = entityTableCollector.getEntitys().stream().filter(e -> {
            if (Objects.equals(e.getTableName(), tableName)) {
                return true;
            }
            if (ShardingBaseEntity.class.isAssignableFrom(e.getClass())) {
                String prefix = ((ShardingBaseEntity)e).getTableNamePrefix();
                return Objects.equals(prefix, tableName) || Objects.equals(prefix, tableName + "_");
            }
            return false;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(baseEntityList)) {
            this.logger.error("\u901a\u8fc7\u8868\u540d\u672a\u627e\u5230\u76f8\u5e94\u7684\u5b9e\u4f53\u5b9a\u4e49\uff0c\u8868\u540d:" + tableName);
            return null;
        }
        if (CollectionUtils.isEmpty(baseEntityList) || baseEntityList.size() > 1) {
            this.logger.error("\u901a\u8fc7\u8868\u540d\u627e\u5230\u591a\u4e2a\u7684\u5b9e\u4f53\u5b9a\u4e49\uff0c\u8bf7\u786e\u8ba4\u540e\u518d\u8bd5\uff0c\u8868\u540d:" + tableName);
            return null;
        }
        BaseEntity entity = (BaseEntity)baseEntityList.get(0);
        DBTable dbTable = entityTableCollector.getDbTableByType(entity.getClass());
        if (!Objects.equals(TemporaryTableTypeEnum.PHYSICAL, dbTable.tempTableType()) && "MYSQL".equalsIgnoreCase(this.dbType)) {
            return null;
        }
        return entity;
    }

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        String tableName = dimTableRelDTO.getEffectTableName();
        BaseEntity entity = this.getBaseEntity(tableName);
        if (Objects.nonNull(entity) && ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
            List shardingList = ((ShardingBaseEntity)entity).getShardingList();
            if (!CollectionUtils.isEmpty((Collection)shardingList)) {
                for (int i = 0; i < shardingList.size(); ++i) {
                    String sharding = (String)shardingList.get(i);
                    String shardingTableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + sharding).toUpperCase();
                    dimTableRelDTO.setEffectTableName(shardingTableName);
                    DimensionPublishInfoVO dimensionPublishInfoVO = this.publishService.publishCheck(dimTableRelDTO, dimensionDTO);
                    if (dimensionPublishInfoVO.getSuccess().booleanValue() && i != shardingList.size() - 1) continue;
                    dimensionPublishInfoVO.setTableName(tableName);
                    return dimensionPublishInfoVO;
                }
            }
            DimensionPublishInfoVO dimensionPublishInfoVO = new DimensionPublishInfoVO();
            dimensionPublishInfoVO.setSysCode(CommonConst.ProductIdentificationEnum.DATACENTER.getCode());
            dimensionPublishInfoVO.setSystitle(CommonConst.ProductIdentificationEnum.DATACENTER.getTitle());
            dimensionPublishInfoVO.setScope(dimTableRelDTO.getEffectScope());
            dimensionPublishInfoVO.setScopeTitle(dimTableRelDTO.getEffectScopeTitle());
            dimensionPublishInfoVO.setTableName(tableName);
            dimensionPublishInfoVO.setTableTitle(dimTableRelDTO.getEffectTableTitle());
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
            return dimensionPublishInfoVO;
        }
        DimensionPublishInfoVO dimensionPublishInfoVO = this.publishService.publishCheck(dimTableRelDTO, dimensionDTO);
        if (Objects.isNull(entity)) {
            dimensionPublishInfoVO.setSuccess(Boolean.TRUE);
        }
        return dimensionPublishInfoVO;
    }

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        String tableName = dimTableRelDTO.getEffectTableName();
        BaseEntity entity = this.getBaseEntity(tableName);
        if (Objects.nonNull(entity) && ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
            List shardingList = ((ShardingBaseEntity)entity).getShardingList();
            if (!CollectionUtils.isEmpty((Collection)shardingList)) {
                for (int i = 0; i < shardingList.size(); ++i) {
                    String sharding = (String)shardingList.get(i);
                    String shardingTableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + sharding).toUpperCase();
                    dimTableRelDTO.setEffectTableName(shardingTableName);
                    DimensionPublishInfoVO dimensionPublishInfoVO = this.publishService.publishCheckUnPublished(dimTableRelDTO, dimensionDTO);
                    if (dimensionPublishInfoVO.getSuccess().booleanValue() && i != shardingList.size() - 1) continue;
                    dimensionPublishInfoVO.setTableName(tableName);
                    return dimensionPublishInfoVO;
                }
            }
            DimensionPublishInfoVO dimensionPublishInfoVO = new DimensionPublishInfoVO();
            dimensionPublishInfoVO.setSysCode(CommonConst.ProductIdentificationEnum.DATACENTER.getCode());
            dimensionPublishInfoVO.setSystitle(CommonConst.ProductIdentificationEnum.DATACENTER.getTitle());
            dimensionPublishInfoVO.setScope(dimTableRelDTO.getEffectScope());
            dimensionPublishInfoVO.setScopeTitle(dimTableRelDTO.getEffectScopeTitle());
            dimensionPublishInfoVO.setTableName(tableName);
            dimensionPublishInfoVO.setTableTitle(dimTableRelDTO.getEffectTableTitle());
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
            return dimensionPublishInfoVO;
        }
        DimensionPublishInfoVO dimensionPublishInfoVO = this.publishService.publishCheckUnPublished(dimTableRelDTO, dimensionDTO);
        if (Objects.isNull(entity)) {
            dimensionPublishInfoVO.setSuccess(Boolean.TRUE);
        }
        return dimensionPublishInfoVO;
    }
}

