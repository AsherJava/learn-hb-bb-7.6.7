/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationCategory
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo
 *  com.jiuqi.nvwa.framework.automation.api.IAutomationCategory
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 */
package com.jiuqi.nr.etl.automation;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationCategory;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo;
import com.jiuqi.nvwa.framework.automation.api.IAutomationCategory;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationCategory(name="nr-datatable", title="\u6570\u636e\u8868\u81ea\u52a8\u5316\u5bf9\u8c61", path="/\u6570\u636e\u65b9\u6848")
public class DataTableAutomationCategory
implements IAutomationCategory {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private static final AutomationInstance INSTANCE = new AutomationInstance();

    private static AutomationFolder dataSchemeAutomationFolder(DataScheme dataScheme) {
        AutomationFolder automationFolder = new AutomationFolder();
        automationFolder.setGuid(dataScheme.getKey());
        automationFolder.setTitle(dataScheme.getTitle());
        automationFolder.setPid("00000000-0000-0000-0000-000000000000");
        return automationFolder;
    }

    private static AutomationFolder dataGroupAutomationFolder(DataGroup dataGroup, String pid) {
        AutomationFolder automationFolder = new AutomationFolder();
        automationFolder.setGuid(dataGroup.getKey());
        automationFolder.setTitle(dataGroup.getTitle());
        automationFolder.setPid(pid);
        return automationFolder;
    }

    private AutomationInstance dataTableAutomationInfo(DataTable dataTable, String pid) {
        AutomationInstance automationInstance = new AutomationInstance();
        automationInstance.setGuid(dataTable.getKey());
        automationInstance.setTitle(dataTable.getCode() + "|" + dataTable.getTitle());
        automationInstance.setType("nr-datatable-type");
        automationInstance.setName(dataTable.getCode());
        automationInstance.setPid(pid);
        return automationInstance;
    }

    public List<AutomationInstance> getChildInstances(String pid) throws AutomationException {
        if (StringUtils.isEmpty((String)pid)) {
            return null;
        }
        if (this.dataSchemeService.getDataTableByGroup(pid).size() > 0) {
            return this.dataSchemeService.getDataTableByGroup(pid).stream().map(item -> this.dataTableAutomationInfo((DataTable)item, pid)).collect(Collectors.toList());
        }
        if (this.dataSchemeService.getDataTableByScheme(pid).size() > 0) {
            return this.dataSchemeService.getDataTableByScheme(pid).stream().map(item -> this.dataTableAutomationInfo((DataTable)item, pid)).collect(Collectors.toList());
        }
        return null;
    }

    public List<AutomationFolder> getChildFolders(String pid) throws AutomationException {
        if (StringUtils.isEmpty((String)pid)) {
            return this.dataSchemeService.getAllDataScheme().stream().map(DataTableAutomationCategory::dataSchemeAutomationFolder).collect(Collectors.toList());
        }
        if (this.dataSchemeService.getDataGroupByScheme(pid).size() > 0) {
            return this.dataSchemeService.getDataGroupByScheme(pid).stream().map(item -> DataTableAutomationCategory.dataGroupAutomationFolder(item, pid)).collect(Collectors.toList());
        }
        if (this.dataSchemeService.getDataGroupByParent(pid).size() > 0) {
            return this.dataSchemeService.getDataGroupByParent(pid).stream().map(item -> DataTableAutomationCategory.dataGroupAutomationFolder(item, pid)).collect(Collectors.toList());
        }
        return null;
    }

    public AutomationInstance getInstance(String guid) throws AutomationException {
        return this.dataTableAutomationInfo(this.dataSchemeService.getDataTable(guid), this.dataSchemeService.getDataTable(guid).getDataGroupKey());
    }

    public AutomationFolder getFolder(String guid) throws AutomationException {
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(guid);
        if (dataScheme != null) {
            return DataTableAutomationCategory.dataSchemeAutomationFolder(dataScheme);
        }
        DataGroup dataGroup = this.dataSchemeService.getDataGroup(guid);
        if (dataGroup != null) {
            String parentKey = dataGroup.getParentKey();
            if (parentKey != null) {
                return DataTableAutomationCategory.dataGroupAutomationFolder(dataGroup, parentKey);
            }
            return DataTableAutomationCategory.dataGroupAutomationFolder(dataGroup, dataGroup.getDataSchemeKey());
        }
        return null;
    }

    public List<AutomationInstance> search(String keyword) throws AutomationException {
        List allDataSchemeList = this.dataSchemeService.getAllDataScheme();
        ArrayList allDataTableList = new ArrayList();
        ArrayList<AutomationInstance> dataTableResultList = new ArrayList<AutomationInstance>();
        for (DataScheme dataScheme : allDataSchemeList) {
            List dataTableList = this.dataSchemeService.getAllDataTable(dataScheme.getKey());
            allDataTableList.addAll(dataTableList);
        }
        for (DataTable datatable : allDataTableList) {
            if (!datatable.getCode().contains(keyword) && !datatable.getTitle().contains(keyword)) continue;
            dataTableResultList.add(this.dataTableAutomationInfo(datatable, datatable.getDataGroupKey()));
        }
        return dataTableResultList;
    }

    public List<String> getInstanceGuidPathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        DataTable dataTable = this.dataSchemeService.getDataTable(guid);
        pathList.add(guid);
        String dataGroupKey = dataTable.getDataGroupKey();
        if (dataGroupKey != null) {
            DataGroup dataGroup = this.dataSchemeService.getDataGroup(dataGroupKey);
            pathList.add(dataGroup.getKey());
            while (dataGroup.getParentKey() != null) {
                dataGroup = this.dataSchemeService.getDataGroup(dataGroup.getParentKey());
                pathList.add(dataGroup.getKey());
            }
        }
        pathList.add(dataTable.getDataSchemeKey());
        pathList.add("00000000-0000-0000-0000-000000000000");
        Collections.reverse(pathList);
        return pathList;
    }

    public List<String> getInstancePathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        DataTable dataTable = this.dataSchemeService.getDataTable(guid);
        pathList.add(dataTable.getCode() + "|" + dataTable.getTitle());
        String dataGroupKey = dataTable.getDataGroupKey();
        if (dataGroupKey != null) {
            DataGroup dataGroup = this.dataSchemeService.getDataGroup(dataTable.getDataGroupKey());
            pathList.add(dataGroup.getTitle());
            while (dataGroup.getParentKey() != null) {
                dataGroup = this.dataSchemeService.getDataGroup(dataGroup.getParentKey());
                pathList.add(dataGroup.getTitle());
            }
        }
        pathList.add(this.dataSchemeService.getDataScheme(dataTable.getDataSchemeKey()).getTitle());
        Collections.reverse(pathList);
        return pathList;
    }

    public AutomationInstanceAppInfo getInstanceAppInfo(String guid) throws AutomationException {
        return null;
    }

    public AutomationTransferResourceInfo getInstanceTransferResourceInfo(String guid) throws AutomationException {
        return null;
    }

    static {
        INSTANCE.setGuid("nr-datatable");
        INSTANCE.setName("nr-datatable");
        INSTANCE.setTitle("\u6570\u636e\u8868\u81ea\u52a8\u5316\u5bf9\u8c61");
        INSTANCE.setType("nr-datatable-type");
        INSTANCE.setPid(null);
    }
}

