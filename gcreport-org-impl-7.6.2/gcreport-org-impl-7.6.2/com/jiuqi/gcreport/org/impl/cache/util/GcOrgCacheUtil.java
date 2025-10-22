/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.auth.entity.EntityOperation
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 */
package com.jiuqi.gcreport.org.impl.cache.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import com.jiuqi.np.dataengine.auth.entity.EntityOperation;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GcOrgCacheUtil {
    public static List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> list) {
        ArrayList tree = CollectionUtils.newArrayList();
        Map datas = list.stream().map(v -> {
            GcOrgCacheVO vo = ((GcOrgCacheInnerVO)((Object)v)).assignTo(new GcOrgCacheInnerVO());
            ((GcOrgCacheInnerVO)vo).setLeaf(true);
            return vo;
        }).collect(Collectors.toMap(GcOrgCacheVO::getId, Function.identity(), (o1, o2) -> o1));
        list.stream().forEach(org -> {
            GcOrgCacheVO pobj = (GcOrgCacheVO)datas.get(org.getParentId());
            if (pobj != null) {
                ((GcOrgCacheInnerVO)pobj).setLeaf(false);
                ((GcOrgCacheInnerVO)pobj).addChildren((GcOrgCacheVO)datas.get(org.getId()));
            } else {
                ((GcOrgCacheInnerVO)((Object)org)).setLeaf(false);
                tree.add(datas.get(org.getId()));
            }
        });
        return tree;
    }

    public static EntityOperation replaceAuthz(GcAuthorityType type) {
        switch (type) {
            case ACCESS: {
                return EntityOperation.READ;
            }
            case APPROVAL: {
                return EntityOperation.AUDIT_FORM_DATA;
            }
            case MANAGE: {
                return EntityOperation.EDIT;
            }
            case REPORT: {
                return EntityOperation.UPLOAD_FORM_DATA;
            }
            case SUBMIT: {
                return EntityOperation.SUBMIT_FORM_DATA;
            }
            case WRITE: {
                return EntityOperation.EDIT_FORM_DATA;
            }
        }
        return EntityOperation.READ;
    }

    public static OrgDataOption.AuthType replaceVAuthz(GcAuthorityType type) {
        switch (type) {
            case NONE: {
                return OrgDataOption.AuthType.NONE;
            }
            case ACCESS: {
                return OrgDataOption.AuthType.ACCESS;
            }
            case APPROVAL: {
                return OrgDataOption.AuthType.APPROVAL;
            }
            case MANAGE: {
                return OrgDataOption.AuthType.MANAGE;
            }
            case REPORT: {
                return OrgDataOption.AuthType.REPORT;
            }
            case SUBMIT: {
                return OrgDataOption.AuthType.SUBMIT;
            }
            case WRITE: {
                return OrgDataOption.AuthType.WRITE;
            }
        }
        return OrgDataOption.AuthType.ACCESS;
    }
}

