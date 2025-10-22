/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NrUtils {
    private static final Logger logger = LoggerFactory.getLogger(NrUtils.class);
    private static final Pattern ZB_PATTERN = Pattern.compile("(.+)\\[(.+)\\]");

    public static Set<String> convert2SimpleTableDefineCodes(List<String> filterFormIdList) {
        Set<TableModelDefine> tableDefineSet = NrUtils.convert2SimpleTableDefines(filterFormIdList);
        if (CollectionUtils.isEmpty(tableDefineSet)) {
            return Collections.EMPTY_SET;
        }
        return tableDefineSet.stream().map(TableModelDefine::getName).collect(Collectors.toSet());
    }

    private static Set<TableModelDefine> convert2SimpleTableDefines(List<String> filterFormIdList) {
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TreeSet<TableModelDefine> tableDefineSet = new TreeSet<TableModelDefine>((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        filterFormIdList.stream().filter(Objects::nonNull).forEach(filterFormId -> {
            List dataRegionDefines = runtimeViewController.getAllRegionsInForm(filterFormId);
            if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
                return;
            }
            dataRegionDefines.stream().filter(Objects::nonNull).filter(dataRegionDefine -> dataRegionDefine != null && dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).forEach(dataRegionDefine -> {
                List dataFieldDeployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(runtimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()).toArray(new String[0]));
                if (CollectionUtils.isEmpty((Collection)dataFieldDeployInfos)) {
                    return;
                }
                List tableNames = dataFieldDeployInfos.stream().map(DataFieldDeployInfo::getTableName).distinct().collect(Collectors.toList());
                for (String tableName : tableNames) {
                    TableModelDefine modelDefine = dataModelService.getTableModelDefineByName(tableName);
                    if (modelDefine == null) {
                        logger.error(String.format("\u6839\u636e\u5b58\u50a8\u8868Code\u3010%1s\u3011\u4e2d\u672a\u67e5\u8be2\u5230\u5bf9\u5e94\u5b58\u50a8\u8868", tableName));
                        continue;
                    }
                    tableDefineSet.add(modelDefine);
                }
            });
        });
        if (CollectionUtils.isEmpty(tableDefineSet)) {
            return null;
        }
        return tableDefineSet;
    }

    public static ArrayKey parseZbCode(String tableZbCode) {
        Matcher matcher = ZB_PATTERN.matcher(tableZbCode);
        String zbCode = "";
        String tableName = "";
        if (matcher.find()) {
            tableName = matcher.group(1).trim();
            zbCode = matcher.group(2).trim();
            return new ArrayKey(new Object[]{tableName, zbCode});
        }
        return null;
    }
}

