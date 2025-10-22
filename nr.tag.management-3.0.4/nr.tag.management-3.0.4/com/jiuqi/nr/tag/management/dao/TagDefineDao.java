/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.dao;

import com.jiuqi.nr.tag.management.entity.ITagDefine;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import java.util.List;

public interface TagDefineDao {
    public List<ITagDefine> queryTagDefineRows(String var1, String var2);

    public List<ITagDefine> queryAdminTagDefineRows(String var1);

    public ITagDefine queryTagDefineRowByKey(String var1);

    public int insertTagDefineRow(ITagDefine var1);

    public int updateTagDefineFormula(String var1, String var2);

    public int[] deleteTagDefineRow(String ... var1);

    public int[] updateTagDefineRow(List<TagManagerShowData> var1);
}

