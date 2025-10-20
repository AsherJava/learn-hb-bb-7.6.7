/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.statement.select.SelectBody
 *  net.sf.jsqlparser.statement.select.SubSelect
 */
package com.jiuqi.va.query.sql.parser.model.impl;

import com.jiuqi.va.query.sql.parser.fromitemhandler.IFromItemHandler;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubSelectHandler
implements IFromItemHandler,
IModelHandler {
    @Autowired
    private ModelHandlerGather modelHandlerGather;

    @Override
    public Class<? extends Model> getClazzType() {
        return SubSelect.class;
    }

    @Override
    public Model doParser(Model srcModel, List<String> params) {
        SubSelect subSelect = (SubSelect)srcModel;
        SelectBody selectBody = subSelect.getSelectBody();
        Model subModel = this.modelHandlerGather.doParser((Model)selectBody, params);
        subSelect.setSelectBody((SelectBody)subModel);
        return subSelect;
    }
}

