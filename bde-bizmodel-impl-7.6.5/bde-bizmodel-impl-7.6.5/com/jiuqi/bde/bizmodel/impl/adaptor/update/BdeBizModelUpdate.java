/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.executor.IBizModelUpdate
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.BaseDataBizModelManageServiceImpl;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.CustomBizModelManageServiceImpl;
import com.jiuqi.bde.bizmodel.impl.model.service.impl.FinBizModelManageServiceImpl;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.mappingscheme.impl.service.executor.IBizModelUpdate;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BdeBizModelUpdate
implements IBizModelUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BdeBizModelUpdate.class);
    @Autowired
    private FinBizModelManageServiceImpl finBizModelManageService;
    @Autowired
    private CustomBizModelManageServiceImpl customBizModelManageService;
    @Autowired
    private BaseDataBizModelManageServiceImpl baseDataBizModelManageService;
    @Autowired
    private BizModelService bizModelService;

    public void bizModelUpdate(String dataSourceCode) {
        try {
            this.doUpdate(dataSourceCode);
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", (Object)e.getMessage(), (Object)e);
        }
    }

    public void doUpdate(String dataSourceCode) {
        boolean tableExists = TableParseUtils.tableExist((String)dataSourceCode, (List)CollectionUtils.newArrayList((Object[])new String[]{"BDE_BIZMODEL_BASEDATA", "BDE_BIZMODEL_CUSTOM", "BDE_BIZMODEL_FINDATA"}));
        if (!tableExists) {
            logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u5e95\u8868\u6821\u9a8c\u5931\u8d25\uff0c\u8df3\u8fc7\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        ((BdeBizModelUpdate)ApplicationContextRegister.getBean(BdeBizModelUpdate.class)).doFinModelUpdate(dataSourceCode);
        ((BdeBizModelUpdate)ApplicationContextRegister.getBean(BdeBizModelUpdate.class)).doCustomModelUpdate(dataSourceCode);
        ((BdeBizModelUpdate)ApplicationContextRegister.getBean(BdeBizModelUpdate.class)).doBaseDataModelUpdate(dataSourceCode);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void doFinModelUpdate(String dataSourceCode) {
        String sql;
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        List finBizModelDTOS = bizJdbcTemplate.query(sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.FETCHTYPES,DIM.DIMENSIONS,DIM.SELECTALL  FROM BDE_BIZMODEL_FINDATA DIM  ORDER BY ORDINAL", (rs, row) -> {
            FinBizModelEO finBizModelDTO = new FinBizModelEO();
            finBizModelDTO.setId(rs.getString(1));
            finBizModelDTO.setCode(rs.getString(2));
            finBizModelDTO.setName(rs.getString(3));
            finBizModelDTO.setComputationModelCode(rs.getString(4));
            finBizModelDTO.setStopFlag(rs.getInt(5));
            finBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            finBizModelDTO.setFetchTypes(rs.getString(7));
            finBizModelDTO.setDimensions(rs.getString(8));
            finBizModelDTO.setSelectAll(rs.getInt(9));
            return finBizModelDTO;
        });
        if (CollectionUtils.isEmpty((Collection)finBizModelDTOS)) {
            logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u8d22\u52a1\u6a21\u578b\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u8df3\u8fc7\u8d22\u52a1\u53d6\u6570\u6a21\u578b\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        List<BizModelDTO> existBizModelList = this.bizModelService.list();
        HashMap existsBizModelMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getCode, item -> item));
        HashMap existsBizModelNameMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getName, item -> item));
        for (FinBizModelEO bizModel : finBizModelDTOS) {
            if (existsBizModelMap.containsKey(bizModel.getCode())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u8d22\u52a1\u6a21\u578b{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)((BizModelDTO)existsBizModelMap.get(bizModel.getCode())).getName());
                continue;
            }
            if (existsBizModelNameMap.containsKey(bizModel.getName())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u8d22\u52a1\u6a21\u578b{}{}\u540d\u79f0\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)bizModel.getName());
                continue;
            }
            try {
                this.finBizModelManageService.save(JsonUtils.writeValueAsString((Object)this.finBizModelManageService.convertFinBizModelDTO(bizModel)));
            }
            catch (Exception e) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u8d22\u52a1\u6a21\u578b{}{}\u4fdd\u5b58\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", bizModel.getCode(), JsonUtils.writeValueAsString((Object)bizModel), e);
            }
        }
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void doCustomModelUpdate(String dataSourceCode) {
        String sql;
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        List customBizModelDTOS = bizJdbcTemplate.query(sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.FETCHTABLE,DIM.FETCHFIELDS,DIM.FIXEDCONDITION,DIM.CUSTOMCONDITION,DIM.APPLYSCOPE  FROM BDE_BIZMODEL_CUSTOM DIM  ORDER BY ORDINAL", (rs, row) -> {
            CustomBizModelEO customBizModelDTO = new CustomBizModelEO();
            customBizModelDTO.setId(rs.getString(1));
            customBizModelDTO.setCode(rs.getString(2));
            customBizModelDTO.setName(rs.getString(3));
            customBizModelDTO.setComputationModelCode(rs.getString(4));
            customBizModelDTO.setStopFlag(rs.getInt(5));
            customBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            customBizModelDTO.setFetchTable(rs.getString(7));
            customBizModelDTO.setFetchFields(rs.getString(8));
            customBizModelDTO.setFixedCondition(rs.getString(9));
            customBizModelDTO.setCustomCondition(rs.getString(10));
            customBizModelDTO.setApplyScope(rs.getString(11));
            return customBizModelDTO;
        });
        if (CollectionUtils.isEmpty((Collection)customBizModelDTOS)) {
            logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u8df3\u8fc7\u8d22\u52a1\u53d6\u6570\u6a21\u578b\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        List<BizModelDTO> existBizModelList = this.bizModelService.list();
        HashMap existsBizModelMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getCode, item -> item));
        HashMap existsBizModelNameMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getName, item -> item));
        for (CustomBizModelEO bizModel : customBizModelDTOS) {
            if (existsBizModelMap.containsKey(bizModel.getCode())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)((BizModelDTO)existsBizModelMap.get(bizModel.getCode())).getName());
                continue;
            }
            if (existsBizModelNameMap.containsKey(bizModel.getName())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b{}{}\u540d\u79f0\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)bizModel.getName());
                continue;
            }
            try {
                this.customBizModelManageService.save(JsonUtils.writeValueAsString((Object)this.customBizModelManageService.convertCustomBizModelDTO(bizModel)));
            }
            catch (Exception e) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b{}{}\u4fdd\u5b58\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", bizModel.getCode(), JsonUtils.writeValueAsString((Object)bizModel), e);
            }
        }
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void doBaseDataModelUpdate(String dataSourceCode) {
        String sql;
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        List baseDataBizModelDTOS = bizJdbcTemplate.query(sql = "  SELECT DIM.ID,DIM.CODE,DIM.NAME,DIM.COMPUTATIONMODELCODE,DIM.STOPFLAG,DIM.ORDINAL,DIM.BASEDATADEFINE,DIM.FETCHFIELDS  FROM BDE_BIZMODEL_BASEDATA DIM  ORDER BY ORDINAL", (rs, row) -> {
            BaseDataBizModelEO baseDataBizModelDTO = new BaseDataBizModelEO();
            baseDataBizModelDTO.setId(rs.getString(1));
            baseDataBizModelDTO.setCode(rs.getString(2));
            baseDataBizModelDTO.setName(rs.getString(3));
            baseDataBizModelDTO.setComputationModelCode(rs.getString(4));
            baseDataBizModelDTO.setStopFlag(rs.getInt(5));
            baseDataBizModelDTO.setOrdinal(rs.getBigDecimal(6));
            baseDataBizModelDTO.setBaseDataDefine(rs.getString(7));
            baseDataBizModelDTO.setFetchFields(rs.getString(8));
            return baseDataBizModelDTO;
        });
        if (CollectionUtils.isEmpty((Collection)baseDataBizModelDTOS)) {
            logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u8df3\u8fc7\u8d22\u52a1\u53d6\u6570\u6a21\u578b\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        List<BizModelDTO> existBizModelList = this.bizModelService.list();
        HashMap existsBizModelMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getCode, item -> item));
        HashMap existsBizModelNameMap = CollectionUtils.isEmpty(existBizModelList) ? CollectionUtils.newHashMap() : existBizModelList.stream().collect(Collectors.toMap(BizModelDTO::getName, item -> item));
        for (BaseDataBizModelEO bizModel : baseDataBizModelDTOS) {
            if (existsBizModelMap.containsKey(bizModel.getCode())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u57fa\u7840\u6570\u636e\u6a21\u578b{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)((BizModelDTO)existsBizModelMap.get(bizModel.getCode())).getName());
                continue;
            }
            if (existsBizModelNameMap.containsKey(bizModel.getName())) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u57fa\u7840\u6570\u636e\u6a21\u578b{}{}\u540d\u79f0\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)bizModel.getCode(), (Object)bizModel.getName());
                continue;
            }
            try {
                this.baseDataBizModelManageService.save(JsonUtils.writeValueAsString((Object)this.baseDataBizModelManageService.convertBaseDataBizModelDTO(bizModel)));
            }
            catch (Exception e) {
                logger.info("\u4e1a\u52a1\u6a21\u578b\u5347\u7ea7-\u57fa\u7840\u6570\u636e\u6a21\u578b{}{}\u4fdd\u5b58\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", bizModel.getCode(), ((BizModelDTO)existsBizModelMap.get(bizModel.getCode())).getName(), e);
            }
        }
    }
}

