/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.datamodel.controller;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaDataModelPublishedController")
@RequestMapping(value={"/dataModel/binary"})
public class VaDataModelPublishedController {
    private String errorOperate = "datamodel.error.common.operate";
    private String successOperate = "datamodel.success.common.operate";
    @Autowired
    private VaDataModelPublishedService dataModelService;
    @Autowired
    private CommonDao commonDao;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        DataModelDO rs = this.dataModelService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        PageVO<DataModelDO> rs = this.dataModelService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = null;
        DataModelDTO param = new DataModelDTO();
        param.setName(dataModelDO.getName());
        DataModelDO old = this.dataModelService.get(param);
        if (old != null) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.add.existed", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = this.dataModelService.push(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object push(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = this.dataModelService.push(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/updateComplete"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object pushComplete(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = this.dataModelService.pushComplete(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/updateIncrement"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object pushIncrement(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = this.dataModelService.pushIncrement(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/updateBaseInfo"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object updateBaseInfo(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = this.dataModelService.updateBaseInfo(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        DataModelDO dataModelDO = (DataModelDO)JSONUtil.parseObject((byte[])binaryData, DataModelDO.class);
        R rs = this.dataModelService.remove(dataModelDO);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batchRemove"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object batchRemove(@RequestBody byte[] binaryData) {
        List datas = JSONUtil.parseArray((byte[])binaryData, DataModelDO.class);
        if (datas != null) {
            for (DataModelDO data : datas) {
                this.dataModelService.remove(data);
            }
        }
        R rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/group/list"})
    Object listGroup(@RequestBody byte[] binaryData) {
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        List<String> rs = this.dataModelService.listGroup(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object syncCache(@RequestBody byte[] binaryData) {
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        R rs = this.dataModelService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/checkZbRelated"})
    Object checkZbRelated(@RequestBody byte[] binaryData) {
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        String curTableName = param.getName();
        List checkColumns = param.getColumns();
        HashSet<String> checkColumnNameSet = new HashSet<String>();
        for (DataModelColumn column : checkColumns) {
            checkColumnNameSet.add(column.getColumnName());
        }
        HashMap<String, LinkedHashSet> relatedInfo = new HashMap<String, LinkedHashSet>();
        PageVO<DataModelDO> dataModels = this.dataModelService.list(new DataModelDTO());
        for (DataModelDO dataModel : dataModels.getRows()) {
            if (dataModel.getName().equalsIgnoreCase(curTableName)) continue;
            for (DataModelColumn column : dataModel.getColumns()) {
                String columnName;
                String[] mappingInfo;
                if (!StringUtils.hasText(column.getMapping()) || !column.getMapping().split("\\.")[0].equalsIgnoreCase(curTableName) || (mappingInfo = column.getMapping().split("\\.")).length < 2 || !checkColumnNameSet.contains(columnName = mappingInfo[1])) continue;
                LinkedHashSet relateTables = (LinkedHashSet)relatedInfo.get(columnName);
                relateTables = relateTables == null ? new LinkedHashSet() : relateTables;
                relateTables.add(dataModel.getName());
                relatedInfo.put(columnName, relateTables);
            }
        }
        R rs = null;
        if (!relatedInfo.isEmpty()) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
            rs.put("relatedInfo", relatedInfo);
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/checkZbExistData"})
    Object checkZbExistData(@RequestBody byte[] binaryData) {
        JTableModel jtm;
        DataModelDTO param = (DataModelDTO)JSONUtil.parseObject((byte[])binaryData, DataModelDTO.class);
        String tenantName = param.getTenantName();
        String curTableName = param.getName();
        List checkColumns = param.getColumns();
        String subTableName = curTableName + "_SUBLIST";
        boolean hasSubTable = false;
        JDialectUtil jDialect = JDialectUtil.getInstance();
        if (jDialect.hasTable(jtm = new JTableModel(tenantName, subTableName))) {
            hasSubTable = true;
        }
        ArrayList<String> existInfo = new ArrayList<String>();
        for (DataModelColumn column : checkColumns) {
            if (this.isExistData(tenantName, curTableName, column.getColumnName())) {
                existInfo.add(column.getColumnName());
                continue;
            }
            if (!hasSubTable || !this.isSubTableExistData(tenantName, subTableName, column.getColumnName())) continue;
            existInfo.add(column.getColumnName());
        }
        R rs = null;
        if (existInfo.isEmpty()) {
            rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        rs.put("existInfo", existInfo);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    private boolean isExistData(String tenantName, String tableName, String columnName) {
        String sqlTemp = "select count(0) as cnt from %s where %s is not null";
        SqlDTO sqlDTO = new SqlDTO(tenantName, sqlTemp);
        sqlDTO.setSql(String.format(sqlTemp, tableName, columnName));
        try {
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    private boolean isSubTableExistData(String tenantName, String tbName, String colName) {
        String sqlTemp = "select count(0) as cnt from %s where FIELDNAME = '%s'";
        SqlDTO sqlDTO = new SqlDTO(tenantName, sqlTemp);
        sqlDTO.setSql(String.format(sqlTemp, tbName, colName));
        try {
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }
}

