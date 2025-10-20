/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class DataRegionHelper {
    private static IDesignTimeViewController designTimeViewController;
    private static IRunTimeViewController runTimeViewController;
    private static IDesignDataSchemeService designDataSchemeService;
    private static IRuntimeDataSchemeService runtimeDataSchemeService;

    @Autowired
    public void setDesignTimeViewController(IDesignTimeViewController designTimeViewController) {
        DataRegionHelper.designTimeViewController = designTimeViewController;
    }

    @Autowired
    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        DataRegionHelper.runTimeViewController = runTimeViewController;
    }

    @Autowired
    public void setDesignDataSchemeService(IDesignDataSchemeService designDataSchemeService) {
        DataRegionHelper.designDataSchemeService = designDataSchemeService;
    }

    @Autowired
    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        DataRegionHelper.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public static boolean allowRepeatCode(String dataRegionKey) {
        DataRegionDefine region = runTimeViewController.queryDataRegionDefine(dataRegionKey);
        return DataRegionHelper.allowRepeatCode(region);
    }

    public static boolean desAllowRepeatCode(String dataRegionKey) {
        DesignDataRegionDefine region = designTimeViewController.queryDataRegionDefine(dataRegionKey);
        return DataRegionHelper.allowRepeatCode(region);
    }

    public static boolean allowRepeatCode(DataRegionDefine region) {
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return false;
        }
        List<DataLinkDefine> links = runTimeViewController.getAllLinksInRegion(region.getKey());
        if (null == links || links.isEmpty()) {
            return false;
        }
        DataTable dataTable = null;
        for (DataLinkDefine link : links) {
            DataField dataField;
            String dataFieldKey = link.getLinkExpression();
            if (!StringUtils.hasLength((String)dataFieldKey) || !DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)link.getType()) && !DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType()) || null == (dataField = runtimeDataSchemeService.getDataField(dataFieldKey))) continue;
            dataTable = runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        return null != dataTable && dataTable.isRepeatCode();
    }

    public static boolean allowRepeatCode(DesignDataRegionDefine region) {
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return false;
        }
        List<DesignDataLinkDefine> links = designTimeViewController.getAllLinksInRegion(region.getKey());
        if (null == links || links.isEmpty()) {
            return false;
        }
        DesignDataTable dataTable = null;
        for (DesignDataLinkDefine link : links) {
            DesignDataField dataField;
            String dataFieldKey = link.getLinkExpression();
            if (!StringUtils.hasLength((String)dataFieldKey) || link.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || null == (dataField = designDataSchemeService.getDataField(dataFieldKey))) continue;
            dataTable = designDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        return null != dataTable && dataTable.isRepeatCode();
    }

    public static String getBizKeyFields(DataRegionDefine region) {
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return "";
        }
        List<DataLinkDefine> links = runTimeViewController.getAllLinksInRegion(region.getKey());
        if (null == links || links.isEmpty()) {
            return "";
        }
        DataTable dataTable = null;
        for (DataLinkDefine link : links) {
            DataField dataField;
            String dataFieldKey = link.getLinkExpression();
            if (!StringUtils.hasLength((String)dataFieldKey) || link.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || null == (dataField = runtimeDataSchemeService.getDataField(dataFieldKey))) continue;
            dataTable = runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        if (null == dataTable) {
            return "";
        }
        List bizFields = designDataSchemeService.getDataFields(Arrays.asList(dataTable.getBizKeys())).stream().filter(f -> DataFieldKind.PUBLIC_FIELD_DIM != f.getDataFieldKind() && DataFieldKind.BUILT_IN_FIELD != f.getDataFieldKind()).collect(Collectors.toList());
        bizFields.sort(null);
        List bizKeys = bizFields.stream().map(Basic::getKey).collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(bizKeys, (String)";");
    }

    public static String getBizKeyFields(DesignDataRegionDefine region) {
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
            return "";
        }
        List<DesignDataLinkDefine> links = designTimeViewController.getAllLinksInRegion(region.getKey());
        if (null == links || links.isEmpty()) {
            return "";
        }
        DesignDataTable dataTable = null;
        for (DesignDataLinkDefine link : links) {
            DesignDataField dataField;
            String dataFieldKey = link.getLinkExpression();
            if (!StringUtils.hasLength((String)dataFieldKey) || !DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)link.getType()) && !DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)link.getType()) || null == (dataField = designDataSchemeService.getDataField(dataFieldKey))) continue;
            dataTable = designDataSchemeService.getDataTable(dataField.getDataTableKey());
            break;
        }
        if (null == dataTable) {
            return "";
        }
        List bizFields = designDataSchemeService.getDataFields(Arrays.asList(dataTable.getBizKeys())).stream().filter(f -> DataFieldKind.PUBLIC_FIELD_DIM != f.getDataFieldKind() && DataFieldKind.BUILT_IN_FIELD != f.getDataFieldKind()).collect(Collectors.toList());
        bizFields.sort(null);
        List bizKeys = bizFields.stream().map(Basic::getKey).collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(bizKeys, (String)";");
    }
}

