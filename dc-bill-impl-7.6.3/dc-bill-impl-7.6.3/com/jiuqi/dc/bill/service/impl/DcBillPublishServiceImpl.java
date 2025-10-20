/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.dto.DimTableRelDTO
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.impl.AbstractPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.datamodel.domain.DataModelMaintainDO
 *  com.jiuqi.va.datamodel.service.VaDataModelMaintainService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.dc.bill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.impl.AbstractPublishServiceImpl;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.datamodel.domain.DataModelMaintainDO;
import com.jiuqi.va.datamodel.service.VaDataModelMaintainService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class DcBillPublishServiceImpl
extends AbstractPublishServiceImpl {
    @Autowired
    private VaDataModelMaintainService vaDataModelMaintainService;

    public void publish(TableModelDefine tableModelDefine, DimensionDTO dimensionDTO) {
        if (null == tableModelDefine || null == dimensionDTO) {
            return;
        }
        DataModelMaintainDO dataModelMaintainDO = this.getDataModelMaintainDO(tableModelDefine.getName());
        if (null == dataModelMaintainDO) {
            LogHelper.error((String)"\u7ef4\u5ea6\u7ba1\u7406", (String)"\u5355\u636e\u8868\u53d1\u5e03", (String)("\u5355\u636e\u5b9a\u4e49\u4e3a\u7a7a" + tableModelDefine.getTitle()));
            throw new BusinessRuntimeException("\u7ef4\u5ea6\u53d1\u5e03\u5931\u8d25\uff0c\u8868\u201c" + tableModelDefine.getTitle() + "\u201d\u53d1\u5e03\u5931\u8d25\u3002\u5355\u636e\u5b9a\u4e49\u4e3a\u7a7a");
        }
        Optional<DataModelColumn> optionalDataModelColumn = dataModelMaintainDO.getColumns().stream().filter(dataModelColumn -> dimensionDTO.getCode().equalsIgnoreCase(dataModelColumn.getColumnName())).findFirst();
        if (optionalDataModelColumn.isPresent()) {
            DataModelColumn dataModelColumn2 = optionalDataModelColumn.get();
            dataModelMaintainDO.getColumns().remove(dataModelColumn2);
        }
        dataModelMaintainDO.getColumns().add(this.converDataModelColumn(dimensionDTO));
        dataModelMaintainDO.getColumns().add(this.converDataModelNameColumn(dimensionDTO));
        Map<String, DataModelColumn> modelColumnMap = dataModelMaintainDO.getColumns().stream().collect(Collectors.toMap(DataModelColumn::getColumnName, e -> e, (k1, k2) -> k1));
        dataModelMaintainDO.setColumns(new ArrayList<DataModelColumn>(modelColumnMap.values()));
        this.deployBillTableDefine(dataModelMaintainDO);
    }

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        DimensionPublishInfoVO dimensionPublishInfoVO = new DimensionPublishInfoVO();
        dimensionPublishInfoVO.setScopeTitle(dimTableRelDTO.getEffectScopeTitle());
        dimensionPublishInfoVO.setTableTitle(dimTableRelDTO.getEffectTableTitle());
        dimensionPublishInfoVO.setScope(dimTableRelDTO.getEffectScope());
        dimensionPublishInfoVO.setTableName(dimTableRelDTO.getEffectTableName());
        dimensionPublishInfoVO.setSysCode(dimTableRelDTO.getSysCode());
        dimensionPublishInfoVO.setSystitle(dimTableRelDTO.getSysTitle());
        dimensionPublishInfoVO.setSuccess(Boolean.valueOf(true));
        DataModelMaintainDO dataModelMaintainDO = this.getDataModelMaintainDO(dimTableRelDTO.getEffectTableName());
        if (Objects.isNull(dataModelMaintainDO)) {
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
            return dimensionPublishInfoVO;
        }
        Optional<DataModelColumn> optionalDataModelColumn = dataModelMaintainDO.getColumns().stream().filter(dataModelColumn -> dimensionDTO.getCode().equalsIgnoreCase(dataModelColumn.getColumnName())).findFirst();
        Optional<DataModelColumn> optionalDataModelColumnName = dataModelMaintainDO.getColumns().stream().filter(dataModelColumn -> (dimensionDTO.getCode() + "_NAME").equalsIgnoreCase(dataModelColumn.getColumnName())).findFirst();
        if (!optionalDataModelColumn.isPresent()) {
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
            return dimensionPublishInfoVO;
        }
        if (!optionalDataModelColumnName.isPresent()) {
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
            dimensionPublishInfoVO.setErrorInfo("\u3010\u4e00\u672c\u8d26\u3011 : " + dimTableRelDTO.getEffectTableName() + "]" + dimTableRelDTO.getEffectTableTitle() + "\u4e2d\u7f3a\u5c11\u5b57\u6bb5[" + dimensionDTO.getCode() + "_NAME|" + dimensionDTO.getCode() + "\u540d\u79f0]");
            return dimensionPublishInfoVO;
        }
        dimensionPublishInfoVO.setSuccess(Boolean.valueOf(this.checkTable(dimTableRelDTO.getEffectTableName(), dimensionDTO.getCode())));
        return dimensionPublishInfoVO;
    }

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: publishCheckUnPublished");
    }

    private DataModelMaintainDO getDataModelMaintainDO(String tableName) {
        DataModelDTO param = new DataModelDTO();
        param.setName(tableName);
        param.setTenantName(ShiroUtil.getTenantName());
        DataModelMaintainDO dataModelMaintainDO = this.vaDataModelMaintainService.get(param);
        return dataModelMaintainDO;
    }

    private DataModelColumn converDataModelColumn(DimensionDTO dimensionDTO) {
        DataModelColumn dataModelColumn = new DataModelColumn();
        dataModelColumn.setColumnName(dimensionDTO.getCode());
        dataModelColumn.setColumnTitle(dimensionDTO.getTitle());
        dataModelColumn.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelColumn.setColumnType(DataModelType.ColumnType.NVARCHAR);
        dataModelColumn.setLengths(new Integer[]{dimensionDTO.getFieldSize()});
        if (StringUtils.isEmpty((String)dimensionDTO.getReferField())) {
            return dataModelColumn;
        }
        dataModelColumn.setMapping(this.getMapping(dimensionDTO.getReferField()));
        dataModelColumn.setMappingType(Integer.valueOf(1));
        return dataModelColumn;
    }

    private DataModelColumn converDataModelNameColumn(DimensionDTO dimensionDTO) {
        DataModelColumn dataModelColumn = new DataModelColumn();
        dataModelColumn.setColumnName(dimensionDTO.getCode() + "_NAME");
        dataModelColumn.setColumnTitle(dimensionDTO.getTitle() + "\u540d\u79f0");
        dataModelColumn.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelColumn.setColumnType(DataModelType.ColumnType.NVARCHAR);
        dataModelColumn.setLengths(new Integer[]{100});
        if (StringUtils.isEmpty((String)dimensionDTO.getReferField())) {
            return dataModelColumn;
        }
        dataModelColumn.setMapping(this.getMapping(dimensionDTO.getReferField()));
        dataModelColumn.setMappingType(Integer.valueOf(1));
        return dataModelColumn;
    }

    private String getMapping(String referField) {
        return referField == null ? "" : referField + ".OBJECTCODE";
    }

    private void deployBillTableDefine(DataModelMaintainDO dataModelMaintainDO) {
        dataModelMaintainDO.setDefinedata(JSONUtil.toJSONString((Object)dataModelMaintainDO));
        this.vaDataModelMaintainService.modify(dataModelMaintainDO);
        DataModelDTO param = new DataModelDTO();
        param.setName(dataModelMaintainDO.getName());
        param.setTenantName(ShiroUtil.getTenantName());
        param.setExtInfo(new HashMap());
        this.vaDataModelMaintainService.merge(param);
    }

    public TableModelDefine checkDesignAndRunTimeDiff(String tableName, String fieldCode) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: checkDesignAndRunTimeDiff");
    }

    public boolean existTable(String tableName) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: existTable");
    }

    public boolean checkTable(String tableName, String fieldCode) {
        throw new BusinessRuntimeException("\u65b9\u6cd5\u88ab\u7981\u7528: checkTable");
    }
}

