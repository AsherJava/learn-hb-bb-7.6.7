/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IDrawObjectFactory
 *  com.jiuqi.xg.process.table.ITablePaginateAssistant
 *  com.jiuqi.xg.process.table.impl.BasicTablePaginateContentProvider
 *  com.jiuqi.xg.process.table.obj.TableDrawObject
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor;

import com.jiuqi.grid.GridData;
import com.jiuqi.xg.process.IDrawObjectFactory;
import com.jiuqi.xg.process.table.ITablePaginateAssistant;
import com.jiuqi.xg.process.table.impl.BasicTablePaginateContentProvider;
import com.jiuqi.xg.process.table.obj.TableDrawObject;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;

public class AnalTablePaginateContentProvider
extends BasicTablePaginateContentProvider {
    public AnalTablePaginateContentProvider(TableTemplateObject tmplTable, IDrawObjectFactory drawObjFactory) {
        super(tmplTable, drawObjFactory);
    }

    public TableDrawObject getContent(ITablePaginateAssistant assist) {
        TableDrawObject content = super.getContent(assist);
        if (assist.getPaginateDirection() == 256 && (assist.getCurrentTableIndex() & 1) == 1) {
            return null;
        }
        return content;
    }

    public void initHeaderAndFooter(GridData grid) {
        super.initHeaderAndFooter(grid);
    }
}

