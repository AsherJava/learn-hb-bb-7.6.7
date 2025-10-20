/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectBody
 *  net.sf.jsqlparser.statement.select.SubSelect
 *  net.sf.jsqlparser.statement.select.WithItem
 */
package com.jiuqi.va.query.sql.parser.model.impl;

import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import org.springframework.stereotype.Component;

@Component
public class SelectHandler
implements IModelHandler {
    @Override
    public Class<? extends Model> getClazzType() {
        return Select.class;
    }

    @Override
    public Model doParser(Model srcModel, List<String> params) {
        Select select = (Select)srcModel;
        SelectBody selectBody = (SelectBody)DCQuerySpringContextUtils.getBean(ModelHandlerGather.class).doParser((Model)select.getSelectBody(), params);
        ArrayList<WithItem> withItems = new ArrayList<WithItem>();
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                SubSelect subSelect = (SubSelect)DCQuerySpringContextUtils.getBean(ModelHandlerGather.class).doParser((Model)withItem.getSubSelect(), params);
                if (subSelect == null) continue;
                withItem.setSubSelect(subSelect);
                withItems.add(withItem);
            }
            select.setWithItemsList(withItems);
            select.setSelectBody(selectBody);
        }
        return select;
    }
}

