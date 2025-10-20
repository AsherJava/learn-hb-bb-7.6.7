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
package com.jiuqi.va.datamodel.controller.abandoned;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="deprecatedDataModelPublishedController")
@Deprecated
@ConditionalOnProperty(name={"va.datamodel.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/dataModel"})
public class VaDataModelPublishedController {
    private String errorOperate = "datamodel.error.common.operate";
    private String successOperate = "datamodel.success.common.operate";
    @Autowired
    private VaDataModelPublishedService dataModelService;
    @Autowired
    private CommonDao commonDao;

    @PostMapping(value={"/get"})
    Object get(@RequestBody DataModelDTO param) {
        return MonoVO.just((Object)this.dataModelService.get(param));
    }

    @PostMapping(value={"/define/format"})
    Object define(@RequestBody DataModelDTO param) {
        DataModelDO data = this.dataModelService.get(param);
        if (data != null) {
            return MonoVO.just((Object)JSONUtil.toJSONString((Object)data, (boolean)true));
        }
        return MonoVO.just((Object)"{}");
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody DataModelDTO param) {
        return MonoVO.just(this.dataModelService.list(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object add(@RequestBody DataModelDO dataModelDO) {
        DataModelDTO param = new DataModelDTO();
        param.setName(dataModelDO.getName());
        DataModelDO old = this.dataModelService.get(param);
        if (old != null) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.add.existed", new Object[0])));
        }
        return MonoVO.just((Object)this.dataModelService.push(dataModelDO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object push(@RequestBody DataModelDO dataModelDO) {
        return MonoVO.just((Object)this.dataModelService.push(dataModelDO));
    }

    @PostMapping(value={"/updateComplete"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object pushComplete(@RequestBody DataModelDO dataModelDO) {
        return MonoVO.just((Object)this.dataModelService.pushComplete(dataModelDO));
    }

    @PostMapping(value={"/updateIncrement"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object pushIncrement(@RequestBody DataModelDO dataModelDO) {
        return MonoVO.just((Object)this.dataModelService.pushIncrement(dataModelDO));
    }

    @PostMapping(value={"/updateBaseInfo"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object updateBaseInfo(@RequestBody DataModelDO dataModelDO) {
        return MonoVO.just((Object)this.dataModelService.updateBaseInfo(dataModelDO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object remove(@RequestBody DataModelDO dataModelDO) {
        return MonoVO.just((Object)this.dataModelService.remove(dataModelDO));
    }

    @PostMapping(value={"/batchRemove"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object batchRemove(@RequestBody List<DataModelDO> datas) {
        if (datas != null) {
            for (DataModelDO data : datas) {
                this.dataModelService.remove(data);
            }
        }
        return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
    }

    @PostMapping(value={"/group/list"})
    Object listGroup(@RequestBody DataModelDTO param) {
        return MonoVO.just(this.dataModelService.listGroup(param));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object sync(@RequestBody DataModelDTO param) {
        return this.dataModelService.syncCache(param);
    }

    @PostMapping(value={"/checkZbRelated"})
    Object checkZbRelated(@RequestBody DataModelDTO param) {
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
        if (!relatedInfo.isEmpty()) {
            R r = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
            r.put("relatedInfo", relatedInfo);
            return MonoVO.just((Object)r);
        }
        return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
    }

    @PostMapping(value={"/checkZbExistData"})
    Object checkZbExistData(@RequestBody DataModelDTO param) {
        JTableModel jtm;
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
        if (existInfo.isEmpty()) {
            return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        }
        R res = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        res.put("existInfo", existInfo);
        return MonoVO.just((Object)res);
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

