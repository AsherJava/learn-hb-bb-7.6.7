/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.penetratebill.client.common.CustomParam
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateContext
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateParam
 *  com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO
 *  com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO
 */
package com.jiuqi.dc.penetratebill.impl.service;

import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import java.util.List;

public interface PenetrateBillService {
    public List<BaseDataShowVO> listScopeType();

    public List<BaseDataShowVO> listHandler();

    public List<BaseDataShowVO> listOpenWay();

    public List<CustomParam> listParam(String var1);

    public List<TreeDTO> tree(PenetrateSchemeDTO var1);

    public List<PenetrateSchemeDTO> listAll();

    public PenetrateSchemeDTO getById(String var1);

    public Boolean modify(PenetrateSchemeDTO var1);

    public Boolean create(PenetrateSchemeDTO var1);

    public Boolean delete(PenetrateSchemeDTO var1);

    public MatchResultDTO match(PenetrateContext var1);

    public PenetrateParam penetrate(PenetrateContext var1);
}

