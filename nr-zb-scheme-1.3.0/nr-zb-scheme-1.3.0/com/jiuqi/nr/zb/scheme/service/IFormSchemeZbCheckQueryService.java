/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckExportParam;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckQueryParam;
import java.util.List;

public interface IFormSchemeZbCheckQueryService {
    public List<ZbCheckItemDTO> queryByForm(String var1, String var2);

    public List<ZbCheckItemDTO> filterByCond(ZbCheckQueryParam var1);

    public List<ZbCheckItemDTO> search(ZbCheckQueryParam var1);

    public List<ZbCheckItemDTO> queryForExport(ZbCheckExportParam var1);
}

