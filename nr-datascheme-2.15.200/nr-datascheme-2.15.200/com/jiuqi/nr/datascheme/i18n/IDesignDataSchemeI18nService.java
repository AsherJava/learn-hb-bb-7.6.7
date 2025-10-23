/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n;

import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

public interface IDesignDataSchemeI18nService {
    public List<DesignDataSchemeI18nDTO> getBySchemeKey(String var1);

    public List<DesignDataSchemeI18nDTO> getByTableKey(String var1, String var2);

    public void save(String var1, String var2, List<DesignDataSchemeI18nDO> var3);

    public void deleteBySchemeKey(String var1);

    public void deleteByTableKey(String var1);

    public void deleteByFieldKey(String var1);

    public void doExport(String var1, Sheet var2);

    public void doImport(String var1, Sheet var2);
}

