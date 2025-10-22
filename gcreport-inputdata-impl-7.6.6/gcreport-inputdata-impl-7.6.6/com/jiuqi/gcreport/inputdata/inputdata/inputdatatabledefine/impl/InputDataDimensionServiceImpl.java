/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.dimension.dto.DimTableRelDTO
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService
 *  com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService;
import com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InputDataDimensionServiceImpl
implements DimensionCustomPublishService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataDimensionServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private InputDataSchemeService inputDataSchemeService;
    @Autowired
    private IDataSchemeDeployService iDataSchemeDeployService;
    @Autowired
    private DefaultPublishServiceImpl publishService;

    public List<String> getTableNames() {
        return CollectionUtils.newArrayList((Object[])new String[]{"GC_INPUTDATATEMPLATE"});
    }

    public void publish(String tableName, DimensionDTO dimensionDTO) {
        TableModelDefine tableModelDefine = this.publishService.checkDesignAndRunTimeDiff(tableName, dimensionDTO.getCode());
        if (null == tableModelDefine || !"GC_INPUTDATATEMPLATE".equalsIgnoreCase(tableModelDefine.getCode())) {
            return;
        }
        InputDataDimensionServiceImpl inputDataDimensionService = (InputDataDimensionServiceImpl)SpringContextUtils.getBean(InputDataDimensionServiceImpl.class);
        List inputDataSchemes = this.inputDataSchemeService.listInputDataScheme();
        for (InputDataSchemeVO inputDataScheme : inputDataSchemes) {
            try {
                DesignDataTable designDataTable = this.iDesignDataSchemeService.getDataTable(inputDataScheme.getTableKey());
                if (designDataTable == null) continue;
                DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(designDataTable.getDataSchemeKey());
                if (null == dataScheme) {
                    throw new RuntimeException("\u7ef4\u5ea6\u7ba1\u7406\u4e2d\u53d1\u5e03\u6570\u636e\u8868\u5f02\u5e38\uff0c\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728, dataSchemeKey\uff1a" + designDataTable.getDataSchemeKey() + "\u201d\u3002");
                }
                inputDataDimensionService.handleInputDataFieldByColumnModelDefine(inputDataScheme, designDataTable, dimensionDTO);
                if (StringUtils.isEmpty((String)designDataTable.getKey())) continue;
                try {
                    this.iDataSchemeDeployService.deployDataTable(designDataTable.getKey(), true);
                }
                catch (JQException e) {
                    String msg = String.format("\u7ef4\u5ea6\u7ba1\u7406\u4e2d\u53d1\u5e03\u6570\u636e\u8868\u5f02\u5e38\uff0c\u53d1\u5e03\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u65b9\u6848\u3010%s\u3011\uff0c\u6570\u636e\u8868\u3010%s\u3011\u3002", dataScheme.getTitle(), designDataTable.getCode());
                    LOGGER.error(msg, e);
                    throw new RuntimeException(msg + e.getMessage(), e);
                }
            }
            catch (Exception e) {
                LogHelper.error((String)"\u7ef4\u5ea6\u7ba1\u7406", (String)"\u5185\u90e8\u6a21\u677f\u8868\u53d1\u5e03\u540e", (String)e.getMessage());
                LOGGER.error("\u7ef4\u5ea6\u7ba1\u7406\u5185\u90e8\u6a21\u677f\u8868\u53d1\u5e03\u540e\uff0c\u8868\u201c" + tableModelDefine.getTitle() + "\u201d\u53d1\u5e03\u5931\u8d25\u3002" + e.getMessage(), e);
            }
        }
        this.publishService.publish(tableModelDefine, dimensionDTO);
    }

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        DimensionPublishInfoVO dimensionPublishInfoVO = this.publishService.publishCheck(dimTableRelDTO, dimensionDTO);
        List inputDataSchemes = this.inputDataSchemeService.listInputDataScheme();
        String template = "\u3010%1$s\u3011\uff1a[%2$s|%3$s]\u4e2d\u7f3a\u5c11\u5b57\u6bb5[%4$s|%5$s];";
        for (InputDataSchemeVO inputDataScheme : inputDataSchemes) {
            DesignDataTable designDataTable = this.iDesignDataSchemeService.getDataTable(inputDataScheme.getTableKey());
            if (Objects.isNull(designDataTable)) {
                LOGGER.warn("\u5185\u90e8\u8868\u4e0e\u6570\u636e\u65b9\u6848\u5173\u8054\u5173\u7cfb\u4e2d\u65e0\u5bf9\u5e94\u7684\u7269\u7406\u8868\uff0c\u6620\u5c04Id\uff1a{}\uff0c\u6570\u636e\u65b9\u6848\uff1a{}\uff0c\u7269\u7406\u8868\u6807\u8bc6\uff1a{}", inputDataScheme.getId(), inputDataScheme.getDataSchemeKey(), inputDataScheme.getTableCode());
                continue;
            }
            DesignDataField designDataField = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(designDataTable.getKey(), dimensionDTO.getCode());
            if (!Objects.isNull(designDataField)) continue;
            dimensionPublishInfoVO.setErrorInfo(String.format(template, dimensionPublishInfoVO.getSystitle(), inputDataScheme.getTableCode(), dimensionPublishInfoVO.getTableTitle(), dimensionDTO.getCode(), dimensionDTO.getTitle()));
            dimensionPublishInfoVO.setSuccess(Boolean.valueOf(false));
        }
        return dimensionPublishInfoVO;
    }

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO dimTableRelDTO, DimensionDTO dimensionDTO) {
        return this.publishService.publishCheckUnPublished(dimTableRelDTO, dimensionDTO);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void handleInputDataFieldByColumnModelDefine(InputDataSchemeVO inputDataScheme, DesignDataTable designDataTable, DimensionDTO dimensionDTO) {
        DesignDataField designDataField = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(designDataTable.getKey(), dimensionDTO.getCode());
        if (designDataField == null) {
            designDataField = this.iDesignDataSchemeService.initDataField();
            designDataField.setDataSchemeKey(inputDataScheme.getDataSchemeKey());
            designDataField.setDataTableKey(inputDataScheme.getTableKey());
            designDataField.setCode(dimensionDTO.getCode());
            designDataField.setTitle(dimensionDTO.getTitle());
            int fieldTypeNum = dimensionDTO.getFieldType();
            if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue() == fieldTypeNum) {
                fieldTypeNum = FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue();
            }
            FieldType fieldType = FieldType.forValue((int)fieldTypeNum);
            int dataType = DataTypesConvert.fieldTypeToDataType((FieldType)fieldType);
            DataFieldType dataFieldType = DataFieldType.valueOf((int)dataType);
            designDataField.setDataFieldType(dataFieldType);
            designDataField.setDataFieldKind(DataFieldKind.FIELD);
            designDataField.setPrecision(dimensionDTO.getFieldSize());
            if (FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue() == fieldTypeNum) {
                designDataField.setDecimal(dimensionDTO.getFieldDecimal());
            }
            designDataField.setNullable(dimensionDTO.getNullAble());
            if (!StringUtils.isEmpty((String)dimensionDTO.getReferField())) {
                designDataField.setRefDataEntityKey(dimensionDTO.getReferField());
            }
            designDataField.setDesc(dimensionDTO.getDescription());
            designDataField.setOrder(OrderGenerator.newOrder());
            LOGGER.info("\u7ef4\u5ea6\u7ba1\u7406\u5411\u5185\u90e8\u8868\u3010{}\u3011\u6dfb\u52a0\u65b0\u589e\u5b57\u6bb5\u3010{}\u3011", (Object)designDataTable.getCode(), (Object)designDataField.getCode());
            this.iDesignDataSchemeService.insertDataField(designDataField);
        } else {
            designDataField.setPrecision(dimensionDTO.getFieldSize());
            designDataField.setNullable(dimensionDTO.getNullAble());
            if (!StringUtils.isEmpty((String)dimensionDTO.getReferField())) {
                designDataField.setRefDataEntityKey(dimensionDTO.getReferField() + "@BASE");
            } else {
                designDataField.setRefDataEntityKey(null);
            }
            designDataField.setUpdateTime(Instant.now());
            designDataField.setDesc(dimensionDTO.getDescription());
            LOGGER.info("\u7ef4\u5ea6\u7ba1\u7406\u5411\u5185\u90e8\u8868\u3010{}\u3011\u4fee\u6539\u5b57\u6bb5\u3010{}\u3011", (Object)designDataTable.getCode(), (Object)designDataField.getCode());
            this.iDesignDataSchemeService.updateDataField(designDataField);
        }
    }
}

