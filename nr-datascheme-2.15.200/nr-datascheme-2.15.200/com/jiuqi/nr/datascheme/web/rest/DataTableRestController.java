/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataTableValidator
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataTableValidator;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;
import com.jiuqi.nr.datascheme.web.facade.DataTableVO;
import com.jiuqi.nr.datascheme.web.param.FindGroupPathPM;
import com.jiuqi.nr.datascheme.web.param.MoveTablePM;
import com.jiuqi.nr.datascheme.web.param.TableFieldDimensionPM;
import com.jiuqi.nr.datascheme.web.param.TableNameCheckPM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u6570\u636e\u8868\u670d\u52a1"})
public class DataTableRestController {
    private final Logger logger = LoggerFactory.getLogger(DataTableRestController.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataTableValidator dataTableValidator;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u6839\u636e\u4e3b\u952e\u67e5\u8be2\u6570\u636e\u8868\u4fe1\u606f")
    @GetMapping(value={"table/query/{key}"})
    public DataTableVO queryDataTable(@PathVariable String key) {
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(key);
        if (dataTable == null) {
            return null;
        }
        return EntityUtil.tableEntity2VO(dataTable);
    }

    @ApiOperation(value="\u65b0\u589e\u6570\u636e\u8868")
    @PostMapping(value={"table/add"})
    public String addDataTable(@RequestBody DataTableVO vo) throws JQException {
        this.logger.debug("\u65b0\u589e\u6570\u636e\u8868\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.getDataTableType());
        DesignDataTable dataTable = EntityUtil.tableVO2Entity(this.designDataSchemeService, vo, null);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            return this.designDataSchemeService.insertDataTable(dataTable);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u8868")
    @PostMapping(value={"table/update"})
    public void updateDataTable(@RequestBody DataTableVO vo) throws JQException {
        this.logger.debug("\u66f4\u65b0\u6570\u636e\u8868\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.getDataTableType());
        DesignDataTable old = this.designDataSchemeService.getDataTable(vo.getKey());
        DesignDataTable dataTable = EntityUtil.tableVO2Entity(this.designDataSchemeService, vo, old);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            this.dataTableValidator.levelCheckTable(dataTable);
            this.designDataSchemeService.updateDataTable(dataTable);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u4e3b\u952e\u5220\u9664\u6570\u636e\u8868")
    @GetMapping(value={"table/delete/{key}"})
    public void deleteDataTable(@PathVariable String key) throws JQException {
        this.logger.debug("\u5220\u9664\u6570\u636e\u8868\uff1a{}\u3002", (Object)key);
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(key);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            this.dataTableValidator.levelCheckTable(key);
            this.designDataSchemeService.deleteDataTable(key);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6709\u6570\u636e\u660e\u7ec6\u8868\u6dfb\u52a0\u8868\u5185\u7ef4\u5ea6")
    @PostMapping(value={"table/addTableFieldDimension"})
    public void addTableFieldDimension(@RequestBody List<TableFieldDimensionPM> fields) throws JQException {
        if (!CollectionUtils.isEmpty(fields)) {
            String tableKey = fields.get(0).getTableKey();
            DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
            if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
                throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
            }
            Map<String, String> stringMap = fields.stream().collect(Collectors.toMap(TableFieldDimensionPM::getFieldCode, TableFieldDimensionPM::getDefaultValue));
            List dataFields = this.designDataSchemeService.getDataFields(fields.stream().map(TableFieldDimensionPM::getFieldKey).collect(Collectors.toList()));
            try {
                this.designDataSchemeService.addTableDimToTable(tableKey, new HashSet(dataFields), stringMap);
            }
            catch (SchemeDataException e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ADD_DIMENSION, e.getMessage());
            }
            catch (SQLIntegrityConstraintViolationException e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ADD_DIMENSION1);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ADD_DIMENSION, (Throwable)e);
            }
        }
    }

    @PostMapping(value={"table/batch/move"})
    @Transactional(rollbackFor={Exception.class})
    public void moveTable(@RequestBody @Validated MoveTablePM pm) throws JQException {
        List dataTables = this.designDataSchemeService.getDataTables(pm.getTableKeys());
        Set schemes = dataTables.stream().map(DataTable::getDataSchemeKey).collect(Collectors.toSet());
        for (String scheme : schemes) {
            if (this.dataSchemeAuthService.canWriteScheme(scheme)) continue;
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        dataTables.forEach(x -> {
            x.setDataGroupKey(pm.getGroupKey());
            x.setUpdateTime(Instant.now());
        });
        this.designDataSchemeService.updateDataTables(dataTables);
    }

    @PostMapping(value={"table/group-path/get"})
    public Set<String> getGroupPath(@RequestBody FindGroupPathPM pm) {
        String dataSchemeKey = pm.getDataSchemeKey();
        Set<String> parentKeys = pm.getParentKeys();
        if (CollectionUtils.isEmpty(parentKeys)) {
            return Collections.emptySet();
        }
        HashSet<String> res = new HashSet<String>();
        res.add(dataSchemeKey);
        HashMap<String, String> stringMap = new HashMap<String, String>();
        for (DesignDataGroup designDataGroup : this.designDataSchemeService.getAllDataGroup(dataSchemeKey)) {
            stringMap.put(designDataGroup.getKey(), designDataGroup.getParentKey());
        }
        Iterator<Object> iterator = parentKeys.iterator();
        while (iterator.hasNext()) {
            String key;
            String s = key = (String)iterator.next();
            while (null != s) {
                res.add(s);
                s = (String)stringMap.get(s);
            }
        }
        return res;
    }

    @PostMapping(value={"table-name/check"})
    public Map<String, String> tableNameCheck(@RequestBody TableNameCheckPM pm) {
        HashMap<String, String> tableMap = new HashMap<String, String>();
        HashMap<String, String> res = new HashMap<String, String>();
        List dataTables = pm.getGroupKey() == null ? this.designDataSchemeService.getDataTableByScheme(pm.getDataSchemeKey()) : this.designDataSchemeService.getDataTableByGroup(pm.getGroupKey());
        List<BaseDataVO> tables = pm.getTables();
        this.checkByVo(tables, tableMap, res);
        this.checkByDo(dataTables, tableMap, res);
        return res;
    }

    private void checkByDo(List<DesignDataTable> dataTables, Map<String, String> tableMap, Map<String, String> res) {
        if (null != dataTables) {
            dataTables.forEach(x -> {
                if (tableMap.containsKey(x.getTitle())) {
                    res.put((String)tableMap.get(x.getTitle()), x.getTitle());
                } else {
                    tableMap.put(x.getTitle(), x.getKey());
                }
                res.remove(x.getKey());
            });
        }
    }

    private void checkByVo(List<BaseDataVO> tables, Map<String, String> tableMap, Map<String, String> res) {
        if (null != tables) {
            tables.forEach(x -> {
                if (tableMap.containsKey(x.getTitle())) {
                    res.put(x.getKey(), x.getTitle());
                    res.put((String)tableMap.get(x.getTitle()), x.getTitle());
                } else {
                    tableMap.put(x.getTitle(), x.getKey());
                }
            });
        }
    }
}

