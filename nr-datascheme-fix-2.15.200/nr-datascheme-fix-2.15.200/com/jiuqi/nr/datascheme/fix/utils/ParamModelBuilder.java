/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 */
package com.jiuqi.nr.datascheme.fix.utils;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamModelBuilder {
    @Autowired
    public Tools tools;
    @Autowired
    private DeployFixUtils fixUtils;

    public DeployFixDataTable initFixDataTable(String dataTableKey) {
        DeployFixDataTable fixDataTable = new DeployFixDataTable(dataTableKey);
        return fixDataTable;
    }

    public DeployFixTableModel initFixTableModel(String tableModelKey) {
        DeployFixTableModel fixTableModel = new DeployFixTableModel(tableModelKey);
        return fixTableModel;
    }

    public List<DeployFixTableModel> initFixTableModels(List<String> tableModelKeys) {
        ArrayList<DeployFixTableModel> fixTableModels = new ArrayList<DeployFixTableModel>();
        for (String tableModelKey : tableModelKeys) {
            DeployFixTableModel fixTableModel = new DeployFixTableModel(tableModelKey);
            fixTableModels.add(fixTableModel);
        }
        return fixTableModels;
    }

    public DeployFixDataTable insertDataTable(DeployFixDataTable fixDataTable) {
        fixDataTable.setDesDataTable(this.tools.getDesDataTableByTableKey(fixDataTable.getDataTableKey()));
        fixDataTable.setRtdataTable((DataTable)this.tools.getDataTableByTableKey(fixDataTable.getDataTableKey()));
        return fixDataTable;
    }

    public DeployFixTableModel insertTableModel(DeployFixTableModel fixTableModel) {
        fixTableModel.setDesTableModelDefine(this.tools.getDesTableModelByTableModelKey(fixTableModel.getTableModelKey()));
        fixTableModel.setTableModelDefine(this.tools.getTableModelByTableModelKey(fixTableModel.getTableModelKey()));
        fixTableModel.setLogicTable(this.fixUtils.getLogicTable(fixTableModel.getTableModelKey()));
        return fixTableModel;
    }

    public DeployFixDataTable insertDataFields(DeployFixDataTable fixDataTable) {
        fixDataTable.setDesDataFields(this.tools.getDesDataFieldByTableKey(fixDataTable.getDataTableKey()));
        fixDataTable.setDataFields(this.tools.getDataFieldByTableKey(fixDataTable.getDataTableKey()));
        List<DataFieldDeployInfoDO> infos = this.tools.getDeployInfoByDataTableKey(fixDataTable.getDataTableKey());
        fixDataTable.setDeployInfos(this.filterInfos(infos));
        return fixDataTable;
    }

    public List<DataFieldDeployInfoDO> filterInfos(List<DataFieldDeployInfoDO> infos) {
        return infos.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DataFieldDeployInfoDO>(Comparator.comparing(u -> u.getDataFieldKey()))), ArrayList::new));
    }

    public DeployFixTableModel insertColumnModels(DeployFixTableModel fixTableModel) {
        fixTableModel.setDesColumnModels(this.tools.getDesColumnModelByTableModelKey(fixTableModel.getTableModelKey()));
        fixTableModel.setColumnModels(this.tools.getColumnModelByTableModelKey(fixTableModel.getTableModelKey()));
        fixTableModel.setLogicFields(this.fixUtils.getLogicField(fixTableModel.getTableModelKey(), null));
        return fixTableModel;
    }

    public DeployFixDataTable getTableModelKey(DeployFixDataTable fixDataTable) {
        List<DataFieldDeployInfoDO> deployInfos = this.tools.getDeployInfoByDataTableKey(fixDataTable.getDataTableKey());
        ArrayList<String> tableModelKeys = new ArrayList<String>();
        for (DataFieldDeployInfoDO deployInfo : deployInfos) {
            String tableModelKey = deployInfo.getTableModelKey();
            tableModelKeys.add(tableModelKey);
        }
        List<String> tableModelKey = tableModelKeys.stream().distinct().collect(Collectors.toList());
        fixDataTable.setTableModelKey(tableModelKey);
        return fixDataTable;
    }
}

