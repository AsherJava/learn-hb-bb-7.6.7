/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.bean.DataentryRefreshParam;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.dataentry.paramInfo.DataEntryRefreshParams;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/dataentry/compatibility-layer"})
public class ComLayerController {
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired(required=false)
    private List<IRegisterDataentryRefreshParams> registerDataentryRefreshParams;

    @GetMapping(value={"/system-options"})
    public Map<String, Object> getSystemOptions(String taskKey) {
        return this.funcExecuteService.getSysParam(taskKey);
    }

    @PostMapping(value={"/extension-params"})
    public List<DataEntryRefreshParams> extensionParams(@RequestBody DataentryRefreshParam dataentryRefreshParam) {
        ArrayList<DataEntryRefreshParams> dataentryRefreshParams;
        block6: {
            ArrayList<IRegisterDataentryRefreshParams> formsRegisters;
            ArrayList<IRegisterDataentryRefreshParams> unitRegisters;
            block7: {
                dataentryRefreshParams = new ArrayList<DataEntryRefreshParams>();
                unitRegisters = new ArrayList<IRegisterDataentryRefreshParams>();
                formsRegisters = new ArrayList<IRegisterDataentryRefreshParams>();
                if (this.registerDataentryRefreshParams == null || this.registerDataentryRefreshParams.size() <= 0) break block6;
                if (!"all".equals(dataentryRefreshParam.getRefreshType())) break block7;
                for (IRegisterDataentryRefreshParams registerDataentryRefreshParam : this.registerDataentryRefreshParams) {
                    dataentryRefreshParams.add(new DataEntryRefreshParams(registerDataentryRefreshParam.getPramaKey(dataentryRefreshParam.getContext()), dataentryRefreshParam.getContext()));
                }
                break block6;
            }
            for (IRegisterDataentryRefreshParams registerDataentryRefreshParam : this.registerDataentryRefreshParams) {
                if (registerDataentryRefreshParam.getRefreshType() == IRegisterDataentryRefreshParams.RefreshType.UNIT_REFRESH) {
                    unitRegisters.add(registerDataentryRefreshParam);
                    continue;
                }
                if (registerDataentryRefreshParam.getRefreshType() != IRegisterDataentryRefreshParams.RefreshType.FORM_REFRESH) continue;
                formsRegisters.add(registerDataentryRefreshParam);
            }
            if ("unit".equals(dataentryRefreshParam.getRefreshType())) {
                for (IRegisterDataentryRefreshParams registerDataentryRefreshParam : unitRegisters) {
                    dataentryRefreshParams.add(new DataEntryRefreshParams(registerDataentryRefreshParam.getPramaKey(dataentryRefreshParam.getContext()), dataentryRefreshParam.getContext()));
                }
            }
            if (!"form".equals(dataentryRefreshParam.getRefreshType())) break block6;
            for (IRegisterDataentryRefreshParams registerDataentryRefreshParam : formsRegisters) {
                dataentryRefreshParams.add(new DataEntryRefreshParams(registerDataentryRefreshParam.getPramaKey(dataentryRefreshParam.getContext()), dataentryRefreshParam.getContext()));
            }
        }
        return dataentryRefreshParams;
    }

    @GetMapping(value={"/actions"})
    public Map<String, ActionItem> getActionItems() {
        IPlugInService plugInService = (IPlugInService)BeanUtil.getBean(IPlugInService.class);
        Tree<ActionItem> tree = plugInService.getActions();
        List list = tree.getChildren();
        HashMap<String, ActionItem> actionItems = new HashMap<String, ActionItem>();
        for (Tree item : list) {
            for (Tree children : item.getChildren()) {
                actionItems.put(((ActionItem)children.getData()).getCode(), (ActionItem)children.getData());
                if (!children.hasChildren()) continue;
                for (Tree children2 : children.getChildren()) {
                    actionItems.put(((ActionItem)children2.getData()).getCode(), (ActionItem)children2.getData());
                }
            }
        }
        return actionItems;
    }
}

