/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IPaginateContext
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.table.ITablePaginateContentProvider
 *  com.jiuqi.xg.process.table.impl.TableTransformer
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.interactor.AnalTablePaginateContentProvider;
import com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.xg.process.IPaginateContext;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.table.ITablePaginateContentProvider;
import com.jiuqi.xg.process.table.impl.TableTransformer;

public class ReportTransformer
extends TableTransformer {
    private ReportTemplateObject reportTemplateObject;

    public void prepare(ITemplateElement<?> element, IPaginateInteractor interactor, IPaginateContext context) {
        super.prepare(element, interactor, context);
        this.reportTemplateObject = (ReportTemplateObject)element;
    }

    protected void checkPaginateContentProvider(GridData grid, IPaginateContext context) {
        boolean analReport = this.reportTemplateObject.isAnalReport();
        if (analReport) {
            super.checkPaginateContentProvider(grid, context);
            AnalTablePaginateContentProvider provider = new AnalTablePaginateContentProvider(this.reportTemplateObject, DocumentBuildUtil.getDrawObjectFactory());
            provider.initHeaderAndFooter(grid);
            this.reportTemplateObject.getPaginateConfig().setContentProvider((ITablePaginateContentProvider)provider);
        } else {
            this.reportTemplateObject.getPaginateConfig().setContentProvider(null);
            super.checkPaginateContentProvider(grid, context);
        }
    }
}

