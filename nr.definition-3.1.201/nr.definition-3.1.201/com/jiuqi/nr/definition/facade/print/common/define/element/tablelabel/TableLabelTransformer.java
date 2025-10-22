/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IDrawElement
 *  com.jiuqi.xg.process.IDrawElementContainer
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IPaginateContext
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.impl.AbstractTransformer
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelTemplateObject;
import com.jiuqi.xg.process.IDrawElement;
import com.jiuqi.xg.process.IDrawElementContainer;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IPaginateContext;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.impl.AbstractTransformer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableLabelTransformer
extends AbstractTransformer {
    private static final Logger logger = LoggerFactory.getLogger(TableLabelTransformer.class);
    private static int count = 0;

    public void prepare(ITemplateElement<?> element, IPaginateInteractor interactor, IPaginateContext context) {
        super.prepare(element, interactor, context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<IDrawElement> transform(IPaginateContext context, IProcessMonitor monitor) {
        try {
            monitor.enter(((Object)((Object)this)).getClass(), ((Object)((Object)this)).getClass().getSimpleName(), "transform");
            monitor.beginTask("\u6267\u884c\u8868\u683c\u6807\u7b7e\u6a21\u677f\u5bf9\u8c61\u7684\u8f6c\u5316\u5904\u7406\u8fc7\u7a0b", 100.0);
            ArrayList<IDrawElement> results = new ArrayList<IDrawElement>();
            IDrawElementContainer[] drawContainers = this.paginater.getDrawContainers();
            for (int index = 0; index < drawContainers.length; ++index) {
                if (!this.interactor.isWithinDrawScope((ITemplateObject)this.element, this.paginater.getAccumulatePageCount() + index)) continue;
                TableLabelDrawObject tableLabel = (TableLabelDrawObject)context.createDrawObject(this.element.getKind());
                this.initialize((ITemplateObject)this.element, (IDrawObject)tableLabel);
                drawContainers[index].add((IDrawElement)tableLabel);
                results.add(tableLabel);
                if (this.interactor.adjustment((ITemplateObject)this.element, (IDrawObject)tableLabel, this.paginater.getAccumulatePageCount() + index)) continue;
                drawContainers[index].remove((IDrawElement)tableLabel);
                results.remove(tableLabel);
            }
            ArrayList<IDrawElement> arrayList = results;
            return arrayList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            monitor.finishTask();
            monitor.exit(((Object)((Object)this)).getClass(), ((Object)((Object)this)).getClass().getSimpleName(), "transform");
        }
        return null;
    }

    protected void initialize(ITemplateObject source, IDrawObject target) {
        super.initialize(source, target);
        TableLabelTemplateObject tlTempObj = (TableLabelTemplateObject)source;
        TableLabelDrawObject tlDrawObj = (TableLabelDrawObject)target;
        tlDrawObj.setID(tlTempObj.getID() + "_" + count++);
        GridData tempGrid = tlTempObj.getGridData();
        if (tempGrid == null) {
            tempGrid = new GridData();
            tempGrid.setRowCount(5);
            tempGrid.setColCount(3);
            tempGrid.setColWidths(0, 100);
            tempGrid.setColWidths(1, 100);
        }
        tlTempObj.setGridData((GridData)tempGrid.clone());
        tlDrawObj.setGridData((GridData)tempGrid.clone());
    }
}

