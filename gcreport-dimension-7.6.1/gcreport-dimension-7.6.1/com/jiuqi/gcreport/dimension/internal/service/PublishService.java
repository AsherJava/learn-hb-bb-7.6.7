/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.service;

import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface PublishService {
    public void publish(TableModelDefine var1, DimensionDTO var2);

    public TableModelDefine checkDesignAndRunTimeDiff(String var1, String var2);

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO var1, DimensionDTO var2);

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO var1, DimensionDTO var2);

    public boolean existTable(String var1);

    public boolean checkTable(String var1, String var2);
}

