/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.util.TreeNode
 *  com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO
 */
package com.jiuqi.bde.bizmodel.impl.model.service;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.util.TreeNode;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import java.util.List;

public interface BizModelService {
    public List<? extends BizModelDTO> listByCategory(String var1);

    public List<BizModelDTO> list();

    public List<BizModelDTO> listAll();

    public BizModelDTO get(String var1);

    public CustomFetchFormVO getCustomFetchFormData();

    public List<TreeNode> queryTreeInitByFetchSourceCode(String var1);
}

