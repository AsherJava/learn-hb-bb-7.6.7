/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.nr.entity.adapter.provider;

import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.util.List;
import java.util.Map;

public interface IDefineQueryProvider {
    public List<IEntityDefine> query(DefineDTO var1);

    public IEntityDefine get(String var1);

    public DataModelDO getModel(String var1);

    public TableModelDefine getNvwaModel(String var1);

    public Map<String, IEntityAttribute> getAttributes(String var1);
}

