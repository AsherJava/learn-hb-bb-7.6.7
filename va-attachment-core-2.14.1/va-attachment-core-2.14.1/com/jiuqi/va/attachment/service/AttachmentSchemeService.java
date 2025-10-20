/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentConfigItemDO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.AttachmentConfigItemDO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;

public interface AttachmentSchemeService {
    public AttachmentSchemeDO get(AttachmentSchemeDO var1);

    public PageVO<TreeVO<AttachmentConfigItemDO>> tree(AttachmentSchemeDO var1);

    public R add(AttachmentSchemeDO var1);

    public R checkScheme(AttachmentSchemeDO var1);

    public R update(AttachmentSchemeDO var1);

    public R delete(AttachmentSchemeDO var1);

    public R connect(AttachmentSchemeDO var1);
}

