/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.event.SyncSrcVchrTableEvent
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.mappingscheme.impl.listener;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.event.SyncSrcVchrTableEvent;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SyncSrcVchrTableEventListener
implements ApplicationListener<SyncSrcVchrTableEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final Integer FN_DEFAULT_VALUELENGTH = 60;
    @Autowired
    private IRuleTypeGather ruleTypeGather;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private BizDataRefDefineService bizDataRefDefineService;

    @Override
    public void onApplicationEvent(SyncSrcVchrTableEvent event) {
        Set dimensions = this.dimensionService.loadAllDimensions().stream().map(DimensionVO::getCode).collect(Collectors.toSet());
        DataRefDefineListDTO dto = new DataRefDefineListDTO();
        dto.setCode("BizVoucherConvert");
        List<DataMappingDefineDTO> bizList = this.bizDataRefDefineService.list(dto);
        if (CollectionUtils.isEmpty(bizList)) {
            return;
        }
        HashSet itemByItemAssistFlags = CollectionUtils.newHashSet();
        for (DataMappingDefineDTO dataMappingDefineDTO : bizList) {
            Set items = dataMappingDefineDTO.getItems().stream().filter(e -> {
                IRuleType ruleType = this.ruleTypeGather.getRuleTypeByCode(e.getRuleType());
                return dimensions.contains(e.getFieldName()) && Boolean.TRUE.equals(ruleType.getItem2Item());
            }).map(FieldMappingDefineDTO::getFieldName).collect(Collectors.toSet());
            itemByItemAssistFlags.addAll(items);
        }
        if (CollectionUtils.isEmpty((Collection)itemByItemAssistFlags)) {
            return;
        }
        itemByItemAssistFlags.forEach(assistFlag -> {
            try {
                this.syncVchrTableWithItemByItemAssistDim((String)assistFlag);
            }
            catch (Exception e) {
                this.logger.error("{}\u7ef4\u5ea6\u4e1a\u52a1\u6620\u5c04\u6620\u5c04\uff08SRC_MD_XX_ID\uff09\u540c\u6b65\u8868\u7ed3\u6784\u51fa\u9519", assistFlag, (Object)e);
            }
        });
    }

    private void syncVchrTableWithItemByItemAssistDim(String assistFlag) {
        DefaultPublishServiceImpl publishService = (DefaultPublishServiceImpl)ApplicationContextRegister.getBean(DefaultPublishServiceImpl.class);
        String fieldName = String.format("SRC_%1$s_ID", assistFlag);
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        Set tableList = this.dimensionService.listTableNamesByDimCode(assistFlag);
        List yearList = entityTableCollector.getEntitys().stream().filter(e -> "DC_VOUCHERITEMASS_".equals(e.getTableName())).findFirst().map(entity -> ((ShardingBaseEntity)entity).getShardingList()).orElse(CollectionUtils.newArrayList());
        if (CollectionUtils.isEmpty((Collection)yearList)) {
            return;
        }
        for (String year : yearList) {
            TableModelDefine tableModelDefine = publishService.checkDesignAndRunTimeDiff("DC_VOUCHERITEMASS_" + year, fieldName);
            DimensionDTO dimensionDTO = new DimensionDTO();
            dimensionDTO.setCode(fieldName);
            dimensionDTO.setTitle(fieldName);
            dimensionDTO.setFieldType(Integer.valueOf(DBColumn.DBType.NVarchar.getType()));
            dimensionDTO.setFieldSize(FN_DEFAULT_VALUELENGTH);
            dimensionDTO.setDefaultValue("'#'");
            publishService.publish(tableModelDefine, dimensionDTO);
            if (!tableList.contains("DC_CFVOUCHERITEMASS_".substring(0, "DC_CFVOUCHERITEMASS_".length() - 1))) continue;
            TableModelDefine cfTableModelDefine = publishService.checkDesignAndRunTimeDiff("DC_CFVOUCHERITEMASS_" + year, fieldName);
            publishService.publish(cfTableModelDefine, dimensionDTO);
        }
    }
}

