/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResultDetail
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDaoImpl
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataTableDaoImpl
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datascheme.fix.utils;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployResultDetail;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataTableDaoImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Tools {
    @Autowired
    protected DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfo;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataTableDaoImpl dataTableDao;
    @Autowired
    private DataFieldDaoImpl dataFieldDao;
    @Autowired
    private DesignDataModelService desDataModelService;
    @Autowired
    private ColumnModelDao columnModelDao;
    @Autowired
    private DataModelDao dataModelDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TableCheckDao tableCheckDao;

    public Set<String> getAllDeployFailDsKey() {
        HashSet<String> deployFailDsKey = new HashSet<String>();
        List allStatus = this.dataSchemeDeployStatusDao.getAll();
        for (DataSchemeDeployStatusDO statusDO : allStatus) {
            if (statusDO.getDeployStatus() != DeployStatusEnum.FAIL) continue;
            deployFailDsKey.add(statusDO.getDataSchemeKey());
        }
        return deployFailDsKey;
    }

    public Set<String> getAllDeployFailDsKey(List<DeployFixParamDTO> deployFixParams) {
        HashSet<String> dsKeys = new HashSet<String>();
        for (DeployFixParamDTO fixParam : deployFixParams) {
            dsKeys.add(fixParam.getDataSchemeKey());
        }
        return dsKeys;
    }

    public List<DeployResultDetail> getFailDetailsByDataSchemeKey(String dataSchemeKey) {
        Collection deployFailDetails = null;
        DataSchemeDeployStatusDO deployStatus = this.getDeployStatus(dataSchemeKey);
        DeployResult deployResult = deployStatus.getDeployResult();
        if (deployResult != null) {
            if (!deployResult.getCheckState() && deployResult.getCheckDetials() != null) {
                deployFailDetails = deployResult.getCheckDetials();
            } else if (deployResult.getDeployState() == DeployStatusEnum.FAIL && deployResult.getDeployDetials() != null) {
                deployFailDetails = deployResult.getDeployDetials();
            }
        }
        if (deployFailDetails == null) {
            return null;
        }
        return deployFailDetails.stream().filter(detial -> !detial.isSuccess()).collect(Collectors.toList());
    }

    public DesignDataScheme getDataScheme(String dataSchemeKey) {
        return this.designDataSchemeService.getDataScheme(dataSchemeKey);
    }

    public String getDeployMessage(String dataSchemeKey) {
        DataSchemeDeployStatusDO deployStatus = this.getDeployStatus(dataSchemeKey);
        DeployResult deployResult = deployStatus.getDeployResult();
        if (deployResult != null) {
            String message = deployResult.getDeployMessage();
            return message;
        }
        return null;
    }

    public DataSchemeDeployStatusDO getDeployStatus(String dataSchemeKey) {
        return this.dataSchemeDeployStatusDao.getByDataSchemeKey(dataSchemeKey);
    }

    public List<DataFieldDeployInfoDO> getDeployInfoByDataTableKey(String dataTableKey) {
        return this.dataFieldDeployInfo.getByDataTableKey(dataTableKey);
    }

    public List<DataFieldDeployInfoDO> getDeployInfoByTableModelKey(String tableModelKey) {
        return this.dataFieldDeployInfo.getByTableModelKey(tableModelKey);
    }

    public DataTableDO getDataTableByTableKey(String dataTableKey) {
        return this.dataTableDao.get(dataTableKey);
    }

    public DesignDataTable getDesDataTableByTableKey(String dataTableKey) {
        return this.designDataSchemeService.getDataTable(dataTableKey);
    }

    public DesignDataTable getDesDataTableByTableCode(String dataTableCode) {
        return this.designDataSchemeService.getDataTableByCode(dataTableCode);
    }

    public List<DataFieldDO> getDataFieldByTableKey(String dataTableKey) {
        return this.dataFieldDao.getByTable(dataTableKey);
    }

    public List<DesignDataField> getDesDataFieldByTableKey(String dataTableKey) {
        return this.designDataSchemeService.getDataFieldByTable(dataTableKey);
    }

    public DataField getDataFieldByFieldKey(String fieldKey) {
        return this.dataFieldDao.get(fieldKey);
    }

    public TableModelDefine getTableModelByTableModelKey(String tableModelKey) {
        return this.dataModelDao.getTableModelDefineById(tableModelKey);
    }

    public List<DesignTableModelDefine> getTableModelByDataTableKey(String dataTableKey) {
        List<DataFieldDeployInfoDO> fieldInfos = this.getDeployInfoByDataTableKey(dataTableKey);
        HashSet<String> tableModelKeys = new HashSet<String>();
        ArrayList<DesignTableModelDefine> tableModels = new ArrayList<DesignTableModelDefine>();
        if (fieldInfos != null && fieldInfos.size() != 0) {
            for (DataFieldDeployInfoDO fieldInfo : fieldInfos) {
                tableModelKeys.add(fieldInfo.getTableModelKey());
            }
            for (String tableModelKey : tableModelKeys) {
                tableModels.add(this.getDesTableModelByTableModelKey(tableModelKey));
            }
        }
        return tableModels;
    }

    public Set<String> getTableModelKeyByDtKey(String dataTableKey) {
        List<DesignTableModelDefine> tableModels = this.getTableModelByDataTableKey(dataTableKey);
        return tableModels.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
    }

    public DesignTableModelDefine getDesTableModelByTableModelKey(String tableModelKey) {
        return this.desDataModelService.getTableModelDefine(tableModelKey);
    }

    public DesignTableModelDefine getDesTableModelByTableModelCode(String code) {
        return this.desDataModelService.getTableModelDefineByCode(code);
    }

    public List<DesignColumnModelDefine> getDesColumnModelByTableModelKey(String tableModelKey) {
        return this.desDataModelService.getColumnModelDefinesByTable(tableModelKey);
    }

    public List<ColumnModelDefine> getColumnModelByTableModelKey(String tableModelKey) {
        return this.columnModelDao.getColumnModelDefinesByTable(tableModelKey);
    }

    public boolean getCheckData(List<DeployFixTableModel> fixTableModels) {
        boolean checkData = false;
        for (DeployFixTableModel fixTableModel : fixTableModels) {
            if (fixTableModel.getLogicTable() == null) continue;
            try {
                checkData = this.tableCheckDao.checkTableExistData(fixTableModel.getLogicTable().getName());
            }
            catch (Exception throwables) {
                checkData = false;
            }
            if (!checkData) continue;
            break;
        }
        return checkData;
    }

    public List<DataField> getdataFieldByTableModelKey(String tableModelKey) {
        ArrayList<DataField> dataFields = new ArrayList<DataField>();
        List<DataFieldDeployInfoDO> deployInfos = this.getDeployInfoByTableModelKey(tableModelKey);
        for (DataFieldDeployInfoDO deployInfo : deployInfos) {
            DataField dataField = this.getDataFieldByFieldKey(deployInfo.getDataFieldKey());
            dataFields.add(dataField);
        }
        return dataFields;
    }

    public void updateDeployStatus(String dataSchemeKey, DeployStatusEnum state, DeployResult deployResult) {
        DataSchemeDeployStatusDO deployStatus = this.dataSchemeDeployStatusDao.getByDataSchemeKey(dataSchemeKey);
        if (null == deployStatus) {
            deployStatus = new DataSchemeDeployStatusDO();
            deployStatus.setDataSchemeKey(dataSchemeKey);
            deployStatus.setDeployStatus(state);
            deployStatus.setDeployResult(deployResult);
            deployStatus.setUpdateTime(Instant.now());
            this.dataSchemeDeployStatusDao.insert(deployStatus);
        } else {
            deployStatus.setDataSchemeKey(dataSchemeKey);
            deployStatus.setDeployStatus(state);
            deployStatus.setDeployResult(deployResult);
            deployStatus.setLastUpdateTime(deployStatus.getUpdateTime());
            deployStatus.setUpdateTime(Instant.now());
            this.dataSchemeDeployStatusDao.update(deployStatus);
        }
    }

    public Instant string2Instant(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        }
        catch (ParseException e) {
            return null;
        }
        return date.toInstant();
    }
}

