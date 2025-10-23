/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.mapping2.web.dto;

import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;
import java.util.Map;

public class RegionDTO {
    public DataRegionDefine region;
    public boolean rowFloat;
    public int beginNum;
    public List<String> enums;
    public Map<String, List<IEntityRow>> extendEnums;
    public int singleOffset;
    public int offset;
    public Map<String, Integer> extendZbs;
}

