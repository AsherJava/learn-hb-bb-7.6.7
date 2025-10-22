/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.lwtree.response.INodeInfos
 *  com.jiuqi.nr.lwtree.response.LightNodeData
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 */
package com.jiuqi.nr.datacheck.hshd.service;

import com.jiuqi.nr.datacheck.hshd.vo.AssTaskVO;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckPM;
import com.jiuqi.nr.datacheck.hshd.vo.HshdCheckResult;
import com.jiuqi.nr.datacheck.hshd.vo.HshdTreePM;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import java.util.Map;

public interface IHshdService {
    public String getItemDescribe(String var1, MultcheckItem var2);

    public String getRunItemDescribe(String var1, MultcheckItem var2);

    public CheckItemResult runCheck(CheckItemParam var1);

    public IEntityTable queryEntityTable(MultcheckScheme var1, String var2);

    @Deprecated
    public IReturnObject<INodeInfos<LightNodeData>> queryRoots(HshdTreePM var1);

    @Deprecated
    public IReturnObject<INodeInfos<LightNodeData>> queryChildren(HshdTreePM var1);

    @Deprecated
    public IReturnObject<INodeInfos<LightNodeData>> searchNodes(HshdTreePM var1);

    @Deprecated
    public IReturnObject<INodeInfos<LightNodeData>> locateNode(HshdTreePM var1);

    public HshdCheckResult entityCheckUp(HshdCheckPM var1);

    public MultCheckItemDTO getDefaultCheckItem(String var1);

    public Map<String, AssTaskVO> getAssTaskConfig(String var1);
}

