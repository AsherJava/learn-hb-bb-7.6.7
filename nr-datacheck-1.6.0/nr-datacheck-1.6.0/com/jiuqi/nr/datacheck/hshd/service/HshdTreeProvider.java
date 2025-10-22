/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.lwtree.para.ITreeParamsInitializer
 *  com.jiuqi.nr.lwtree.provider.impl.LightTreeProvider
 *  com.jiuqi.nr.lwtree.provider.impl.LightTreeSearchProvider
 *  com.jiuqi.nr.lwtree.query.IEntityRowQueryer
 *  com.jiuqi.nr.lwtree.query.IEntityRowQueryerImpl
 *  com.jiuqi.nr.lwtree.request.LightTreeLoadParam
 *  com.jiuqi.nr.lwtree.request.SearchParam
 *  com.jiuqi.nr.lwtree.response.INodeInfos
 *  com.jiuqi.nr.lwtree.response.LightNodeData
 */
package com.jiuqi.nr.datacheck.hshd.service;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.lwtree.provider.impl.LightTreeProvider;
import com.jiuqi.nr.lwtree.provider.impl.LightTreeSearchProvider;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryerImpl;
import com.jiuqi.nr.lwtree.request.LightTreeLoadParam;
import com.jiuqi.nr.lwtree.request.SearchParam;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HshdTreeProvider
extends LightTreeProvider {
    private final IEntityTable entityTable;

    public HshdTreeProvider(LightTreeLoadParam ltParam, IEntityTable entityTable) {
        super(ltParam);
        this.queryer = new HshdTreeQueryer(this.loadInfo);
        this.entityTable = entityTable;
    }

    public INodeInfos<LightNodeData> searchNode() {
        return new HshdLightTreeSearchProvider(this.queryer, this.ltParam.getSearchParam()).searchNode();
    }

    private class HshdTreeQueryer
    extends IEntityRowQueryerImpl {
        public HshdTreeQueryer(ITreeParamsInitializer loadInfo) {
            super(loadInfo);
        }

        public IEntityTable getIEntityTable() {
            return HshdTreeProvider.this.entityTable;
        }
    }

    private class HshdLightTreeSearchProvider
    extends LightTreeSearchProvider {
        public HshdLightTreeSearchProvider(IEntityRowQueryer queryer, SearchParam searchParam) {
            super(queryer, searchParam);
        }

        protected List<IEntityRow> searchNodes(String searchText) {
            ArrayList<IEntityRow> rows = new ArrayList<IEntityRow>(0);
            String[] searchTxtArr = searchText.split("\\|");
            List rootRows = HshdTreeProvider.this.queryer.getRootRows();
            LinkedList queue = new LinkedList(rootRows);
            while (!queue.isEmpty()) {
                List childRows;
                IEntityRow row = (IEntityRow)queue.poll();
                String title = row.getTitle();
                String code = row.getCode();
                for (String sTxt : searchTxtArr) {
                    if ((title == null || !title.contains(sTxt)) && (code == null || !code.contains(sTxt))) continue;
                    rows.add(row);
                    break;
                }
                if ((childRows = HshdTreeProvider.this.queryer.getChildRows(row.getEntityKeyData())) == null || childRows.isEmpty()) continue;
                queue.addAll(childRows);
            }
            return rows;
        }
    }
}

