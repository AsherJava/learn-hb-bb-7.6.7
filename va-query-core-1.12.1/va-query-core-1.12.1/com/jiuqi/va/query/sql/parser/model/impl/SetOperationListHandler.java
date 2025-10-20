/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.statement.select.SelectBody
 *  net.sf.jsqlparser.statement.select.SetOperationList
 */
package com.jiuqi.va.query.sql.parser.model.impl;

import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.springframework.stereotype.Component;

@Component
public class SetOperationListHandler
implements IModelHandler {
    @Override
    public Model doParser(Model model, List<String> params) {
        SetOperationList setOperationList = (SetOperationList)model;
        ArrayList<SelectBody> newSetOperationList = new ArrayList<SelectBody>();
        for (SelectBody selectBody : setOperationList.getSelects()) {
            SelectBody selectBody1 = (SelectBody)DCQuerySpringContextUtils.getBean(ModelHandlerGather.class).doParser((Model)selectBody, params);
            if (selectBody1 == null) continue;
            newSetOperationList.add(selectBody1);
        }
        setOperationList.setSelects(newSetOperationList);
        return setOperationList;
    }

    @Override
    public Class<? extends Model> getClazzType() {
        return SetOperationList.class;
    }
}

