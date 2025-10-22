/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.entity;

import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_I18N_DES")
@DBAnno.DBLink(linkWith=DesignDataFieldDO.class, linkField="key", field="key")
public class DesignDataSchemeI18nDO
extends DataSchemeI18nDO {
    private static final long serialVersionUID = -4449772430067094050L;
}

