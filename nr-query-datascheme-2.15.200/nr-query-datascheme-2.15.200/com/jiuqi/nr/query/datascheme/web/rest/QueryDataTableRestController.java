/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.api.type.RelationType
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataTableRelDesignService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.RelationType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelDesignService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.datascheme.bean.FieldRel;
import com.jiuqi.nr.query.datascheme.bean.TableRelInfo;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataDimFieldVO;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataTableVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6570\u636e\u8868\u7ba1\u7406\u670d\u52a1"})
public class QueryDataTableRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDataTableRestController.class);
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private DataFieldDeployInfoDaoImpl deployInfoService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataTableValidator dataTableValidator;
    @Autowired
    private DataTableRelDesignService dataTableRelDesignService;

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u8868\u7684\u6570\u636e\u6e90\u7c7b\u578b")
    @GetMapping(value={"table/query/dataSourceType/{key}"})
    public String queryDataSourceType(@PathVariable String key) {
        QueryDataTableDTO queryDataTable = this.designDataSchemeService.getQueryDataTable(key);
        if (null == queryDataTable) {
            return null;
        }
        return queryDataTable.getTableType();
    }

    @ApiOperation(value="\u83b7\u53d6\u67e5\u8be2\u6570\u636e\u8868")
    @GetMapping(value={"table/query/{key}"})
    public QueryDataTableVO queryDataTable(@PathVariable String key) {
        QueryDataTableDTO queryDataTable = this.designDataSchemeService.getQueryDataTable(key);
        if (null == queryDataTable) {
            return null;
        }
        QueryDataTableVO vo = this.toVO(queryDataTable);
        List dims = this.designDataSchemeService.getDataSchemeDimension(queryDataTable.getDataSchemeKey());
        List dimDataFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(key, new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        List deployInfos = this.deployInfoService.getByDataFieldKeys((String[])dimDataFields.stream().map(Basic::getKey).toArray(String[]::new));
        List<QueryDataDimFieldVO> dimFields = this.toVO(dims, dimDataFields, deployInfos);
        vo.setDimFields(dimFields);
        if (queryDataTable.getDataTableType() == DataTableType.SUB_TABLE) {
            DesignDataTableRel tableRel = this.designDataSchemeService.getDataTableRelBySrcTable(key);
            DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableRel.getDesTableKey());
            TableRelInfo relInfo = new TableRelInfo();
            relInfo.setSrcTableKey(tableRel.getSrcTableKey());
            relInfo.setDesTableKey(tableRel.getDesTableKey());
            relInfo.setDesTableFormatTitle(dataTable.getCode());
            relInfo.setDataSchemeKey(queryDataTable.getDataSchemeKey());
            List<String> srcFieldKeys = Arrays.asList(tableRel.getSrcFieldKeys());
            List<String> desFieldKeys = Arrays.asList(tableRel.getDesFieldKeys());
            List allSrcFields = this.designDataSchemeService.getDataFieldByTable(key);
            List allDesFields = this.designDataSchemeService.getDataFieldByTable(tableRel.getDesTableKey());
            List srcDataFields = allSrcFields.stream().filter(f -> srcFieldKeys.contains(f.getKey())).collect(Collectors.toList());
            List desDataFields = allDesFields.stream().filter(f -> desFieldKeys.contains(f.getKey())).collect(Collectors.toList());
            ArrayList<FieldRel> fieldRels = new ArrayList<FieldRel>();
            for (int i = 0; i < desDataFields.size(); ++i) {
                FieldRel fieldRel = new FieldRel();
                fieldRel.setDesFieldKey(((DesignDataField)desDataFields.get(i)).getKey());
                fieldRel.setDesFieldCode(((DesignDataField)desDataFields.get(i)).getCode());
                fieldRel.setSrcFieldCode(((DesignDataField)srcDataFields.get(i)).getCode());
                fieldRels.add(fieldRel);
            }
            relInfo.setFieldRels(fieldRels);
            vo.setTableRelInfo(relInfo);
        }
        return vo;
    }

    private QueryDataTableVO toVO(QueryDataTableDTO source) {
        QueryDataTableVO target = new QueryDataTableVO();
        BeanUtils.copyProperties((Object)source, (Object)target);
        if (target.getDataGroupKey() == null) {
            target.setDataGroupKey(target.getDataSchemeKey());
        }
        return target;
    }

    private List<QueryDataDimFieldVO> toVO(List<DesignDataDimension> dims, List<DesignDataField> dimDataFields, List<? extends DataFieldDeployInfo> deployInfos) {
        ArrayList<QueryDataDimFieldVO> fields = new ArrayList<QueryDataDimFieldVO>();
        HashMap<String, String> infos = new HashMap<String, String>();
        for (DataFieldDeployInfo dataFieldDeployInfo : deployInfos) {
            infos.put(dataFieldDeployInfo.getDataFieldKey(), dataFieldDeployInfo.getFieldName());
        }
        HashMap<String, DimensionType> dimInfos = new HashMap<String, DimensionType>();
        for (DesignDataDimension dim : dims) {
            dimInfos.put(dim.getDimKey(), dim.getDimensionType());
        }
        for (DesignDataField dimDataField : dimDataFields) {
            String dimKey = StringUtils.hasLength(dimDataField.getRefDataEntityKey()) ? dimDataField.getRefDataEntityKey() : dimDataField.getCode();
            String columnName = (String)infos.get(dimDataField.getKey());
            QueryDataDimFieldVO vo = this.toVO(dimDataField, dimKey, dimInfos, columnName);
            fields.add(vo);
        }
        return fields;
    }

    private QueryDataDimFieldVO toVO(DesignDataField dimDataField, String dimKey, Map<String, DimensionType> dimInfos, String columnName) {
        QueryDataDimFieldVO vo = new QueryDataDimFieldVO();
        vo.setDimKey(dimKey);
        vo.setDimType(dimInfos.get(dimKey).getValue());
        vo.setFieldKey(dimDataField.getKey());
        vo.setFieldCode(dimDataField.getCode());
        vo.setColumnName(columnName);
        if (DimensionType.PERIOD.getValue() == vo.getDimType()) {
            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(vo.getDimKey());
            vo.setDimCode(periodEntity.getCode());
            vo.setDimTitle(periodEntity.getTitle());
        } else if ("ADJUST".equals(vo.getDimKey())) {
            vo.setDimCode(vo.getDimKey());
            vo.setDimTitle(vo.getDimKey());
        } else {
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(vo.getDimKey());
            vo.setDimCode(iEntityDefine.getCode());
            vo.setDimTitle(iEntityDefine.getTitle());
        }
        return vo;
    }

    private QueryDataTableDTO toDTO(QueryDataTableVO vo) {
        QueryDataTableDTO dto = new QueryDataTableDTO();
        BeanUtils.copyProperties((Object)vo, (Object)dto);
        if (dto.getDataSchemeKey().equals(vo.getDataGroupKey())) {
            dto.setDataGroupKey(null);
        }
        return dto;
    }

    @ApiOperation(value="\u65b0\u589e\u67e5\u8be2\u6570\u636e\u8868")
    @PostMapping(value={"table/add"})
    public String addDataTable(@RequestBody QueryDataTableVO vo) throws JQException {
        LOGGER.debug("\u65b0\u589e\u67e5\u8be2\u6570\u636e\u8868\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.getDataTableType());
        try {
            QueryDataTableDTO queryDataTable = this.toDTO(vo);
            List<QueryDataDimFieldVO> dimFields = vo.getDimFields();
            HashMap<String, String> dimColumns = new HashMap<String, String>();
            for (QueryDataDimFieldVO dimField : dimFields) {
                dimColumns.put(dimField.getDimKey(), dimField.getColumnName());
            }
            String key = this.designDataSchemeService.insertQueryDataTable(queryDataTable, dimColumns);
            this.saveTableRel(queryDataTable, key);
            return key;
        }
        catch (SchemeDataException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u4e3b\u5b50\u8868\u5173\u7cfb")
    @PostMapping(value={"tablerel/save"})
    public void addDataTableRel(@RequestBody TableRelInfo vo) throws JQException {
        LOGGER.debug("\u65b0\u589e\u4e3b\u5b50\u8868\u5173\u7cfb\uff0c\u4e3b\u8868{}\uff0c\u5b50\u8868{}\u3002", (Object)vo.getDesTableKey(), (Object)vo.getSrcTableKey());
        try {
            this.dataTableRelDesignService.deleteBySrcTable(vo.getSrcTableKey());
            this.saveTableRel(vo);
        }
        catch (SchemeDataException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u67e5\u8be2\u6570\u636e\u8868")
    @GetMapping(value={"table/delete/{key}"})
    public void deleteDataTable(@PathVariable String key) throws JQException {
        LOGGER.debug("\u5220\u9664\u6570\u636e\u8868\uff1a{}\u3002", (Object)key);
        try {
            List tableRels;
            DesignDataTable dataTable = this.designDataSchemeService.getDataTable(key);
            if (dataTable.getDataTableType() == DataTableType.DETAIL && !CollectionUtils.isEmpty(tableRels = this.dataTableRelDesignService.getByDesTable(key))) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DT_2, "\u8be5\u660e\u7ec6\u8868\u88ab\u5176\u4ed6\u5b50\u8868\u5173\u8054\uff0c\u65e0\u6cd5\u5220\u9664");
            }
            this.dataTableValidator.levelCheckTable(key);
            this.designDataSchemeService.deleteQueryDataTable(key);
            this.designDataSchemeService.deleteDataTableRelsBySrcTable(key);
        }
        catch (SchemeDataException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    private void saveTableRel(TableRelInfo vo) {
        DataTableRelDesignDTO relDto = (DataTableRelDesignDTO)this.designDataSchemeService.initDataTableRel();
        relDto.setKey(UUID.randomUUID().toString());
        relDto.setType(RelationType.MANY_TO_MANY);
        relDto.setDataSchemeKey(vo.getDataSchemeKey());
        relDto.setSrcTableKey(vo.getSrcTableKey());
        relDto.setDesTableKey(vo.getDesTableKey());
        ArrayList<String> srcFieldCodes = new ArrayList<String>();
        ArrayList<String> desFieldKeys = new ArrayList<String>();
        for (FieldRel rel : vo.getFieldRels()) {
            srcFieldCodes.add(rel.getSrcFieldCode());
            desFieldKeys.add(rel.getDesFieldKey());
        }
        List allSrcFields = this.designDataSchemeService.getDataFieldByTable(vo.getSrcTableKey());
        String[] srcFieldKeys = (String[])allSrcFields.stream().filter(f -> srcFieldCodes.contains(f.getCode())).map(Basic::getKey).toArray(String[]::new);
        String[] desFieldKeyArray = desFieldKeys.toArray(new String[0]);
        relDto.setSrcFieldKeys(srcFieldKeys);
        relDto.setDesFieldKeys(desFieldKeyArray);
        this.dataTableRelDesignService.insertDataTableRel(relDto);
    }

    private void saveTableRel(QueryDataTableDTO queryDataTable, String srcTableKey) {
        if (queryDataTable.getDataTableType() == DataTableType.SUB_TABLE) {
            DataTableRelDesignDTO relDto = (DataTableRelDesignDTO)this.designDataSchemeService.initDataTableRel();
            TableRelInfo tableRelInfo = queryDataTable.getTableRelInfo();
            relDto.setKey(UUID.randomUUID().toString());
            relDto.setType(RelationType.MANY_TO_MANY);
            relDto.setDataSchemeKey(queryDataTable.getDataSchemeKey());
            relDto.setSrcTableKey(srcTableKey);
            relDto.setDesTableKey(tableRelInfo.getDesTableKey());
            ArrayList<String> srcFieldCodes = new ArrayList<String>();
            ArrayList<String> desFieldKeys = new ArrayList<String>();
            for (FieldRel rel : tableRelInfo.getFieldRels()) {
                srcFieldCodes.add(rel.getSrcFieldCode());
                desFieldKeys.add(rel.getDesFieldKey());
            }
            List allSrcFields = this.designDataSchemeService.getDataFieldByTable(srcTableKey);
            String[] srcFieldKeys = (String[])allSrcFields.stream().filter(f -> srcFieldCodes.contains(f.getCode())).map(Basic::getKey).toArray(String[]::new);
            String[] desFieldKeyArray = desFieldKeys.toArray(new String[0]);
            relDto.setSrcFieldKeys(srcFieldKeys);
            relDto.setDesFieldKeys(desFieldKeyArray);
            this.dataTableRelDesignService.insertDataTableRel(relDto);
        }
    }
}

