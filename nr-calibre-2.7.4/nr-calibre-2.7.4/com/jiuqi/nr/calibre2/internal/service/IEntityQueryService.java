/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.calibre2.internal.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.domain.EntityQueryParam;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.calibre2.vo.SelectedEntityVO;
import java.util.List;

public interface IEntityQueryService {
    public List<SelectedEntityVO> querySelectedEntity(CalibreDataVO var1) throws JQException;

    public List<SelectedEntityVO> querySelectedEntity(EntityQueryParam var1) throws JQException;
}

