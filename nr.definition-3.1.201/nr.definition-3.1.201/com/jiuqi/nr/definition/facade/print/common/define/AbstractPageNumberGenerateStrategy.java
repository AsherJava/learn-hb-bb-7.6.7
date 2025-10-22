/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.define;

import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPageNumberGenerateStrategy
implements IPageNumberGenerateStrategy {
    protected int pageNumberOffset;
    protected int totalPageNumber;
    protected int templatePageOrderNumber;
    protected Map<String, Object> map = new HashMap<String, Object>();

    public AbstractPageNumberGenerateStrategy(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
        this.pageNumberOffset = 0;
        this.templatePageOrderNumber = 1;
    }

    public abstract void accumulate(int var1);

    @Override
    public int getCurrentPageNumberOffset() {
        return this.pageNumberOffset;
    }

    @Override
    public int getTotalPageNumber() {
        return this.totalPageNumber;
    }

    @Override
    public int getTemplatePageOrderNumber() {
        return this.templatePageOrderNumber;
    }

    @Override
    public String parse(ParseContext context, String pattern) {
        switch (pattern) {
            case "Counter": {
                Integer counter = (Integer)this.map.get("Counter");
                counter = null != counter ? Integer.valueOf(counter + 1) : Integer.valueOf(1);
                this.map.put("Counter", counter);
                return counter.toString();
            }
            case "ReportCounter": {
                Integer repCounter = (Integer)this.map.get(context.getFormKey());
                if (null != repCounter) {
                    return repCounter.toString();
                }
                repCounter = (Integer)this.map.get("ReportCounter");
                if (null == repCounter) {
                    repCounter = 0;
                }
                repCounter = repCounter + 1;
                this.map.put(context.getFormKey(), repCounter);
                this.map.put("ReportCounter", repCounter);
                return repCounter.toString();
            }
        }
        return "";
    }
}

