/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo
 *  com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin
 *  com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather
 */
package com.jiuqi.gc.financial.service.impl;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo;
import com.jiuqi.gc.financial.service.FinancialStatusManageService;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialStatusManageServiceImpl
implements FinancialStatusManageService {
    @Autowired
    private IFinancialStatusModuleGather pluginGather;

    @Override
    public List<FinancialStatusManageVo> listAllPlugin() {
        ArrayList<FinancialStatusManageVo> modules = new ArrayList<FinancialStatusManageVo>();
        for (IFinancialStatusModulePlugin modulePlugin : this.pluginGather.list()) {
            modules.add(new FinancialStatusManageVo(modulePlugin.getModuleCode(), modulePlugin.getModuleName(), modulePlugin.getAppName(), modulePlugin.getProdLine(), Integer.valueOf(modulePlugin.order())));
        }
        return modules.stream().sorted(Comparator.comparing(item -> {
            IFinancialStatusModulePlugin plugin = this.pluginGather.getPluginByModuleCode(item.getCode());
            return plugin.order();
        })).collect(Collectors.toList());
    }

    @Override
    public List<FinancialStatusManageConfigVo> listPluginConfig() {
        ArrayList<FinancialStatusManageConfigVo> configs = new ArrayList<FinancialStatusManageConfigVo>();
        for (IFinancialStatusModulePlugin pluginConfig : this.pluginGather.list()) {
            FinancialStatusManageConfigVo manageConfigVo = new FinancialStatusManageConfigVo();
            manageConfigVo.setCode(pluginConfig.getModuleCode());
            manageConfigVo.setIndex(Integer.valueOf(pluginConfig.order()));
            manageConfigVo.setName(pluginConfig.getModuleName());
            manageConfigVo.setFinancialStatusConfigVo(pluginConfig.getPluginDefaultConfig());
            configs.add(manageConfigVo);
        }
        return configs;
    }
}

