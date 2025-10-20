/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 */
package com.jiuqi.bde.bizmodel.impl.model.service;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelBuildContext;
import java.util.List;
import java.util.Map;

public interface BizModelManageService {
    public String getCategoryCode();

    public BizModelDTO getById(String var1);

    public BizModelDTO getByCode(String var1);

    public String list();

    public List<? extends BizModelDTO> listModel();

    public void save(String var1);

    public void update(String var1);

    public int start(String var1);

    public int stop(String var1);

    public void exchangeOrdinal(String var1, String var2);

    public CustomFetchFormVO getCustomFetchFormData();

    public String getFetchTypeByBizModelCode(String var1);

    public String getDimensionByBizModelCode(String var1);

    public List<BizModelTreeNode> getBizModelTree();

    public BizModelColumnDefineVO getColumnDefines(String var1);

    public Map<String, BizModelColumnDefineVO> getBatchColumnDefinesForExtInfo(List<BizModelDTO> var1, BizModelBuildContext var2);
}

