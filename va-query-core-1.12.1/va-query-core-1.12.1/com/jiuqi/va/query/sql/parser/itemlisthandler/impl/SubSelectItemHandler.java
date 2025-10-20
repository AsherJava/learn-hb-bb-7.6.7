/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.SelectBody
 *  net.sf.jsqlparser.statement.select.SubSelect
 */
package com.jiuqi.va.query.sql.parser.itemlisthandler.impl;

import com.jiuqi.va.query.sql.parser.itemlisthandler.IItemListHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubSelectItemHandler
implements IItemListHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends ItemsList> getClazzType() {
        return SubSelect.class;
    }

    @Override
    public ItemsList doParser(ItemsList itemsList, List<String> params) {
        SubSelect subSelect = (SubSelect)itemsList;
        SelectBody selectBody = subSelect.getSelectBody();
        PlainSelect plainSelect = (PlainSelect)selectBody;
        Model subModel = this.modelHandlerGather.doParser((Model)plainSelect, params);
        subSelect.setSelectBody((SelectBody)subModel);
        return subSelect;
    }
}

