/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 */
package com.jiuqi.gcreport.org.impl.cache.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class GcOrgServiceBase {
    GcOrgServiceBase() {
    }

    protected GcOrgCacheVO getOrgByOrgId(List<GcOrgCacheVO> tree, String orgId) {
        return this.getOrgByParam(tree, new ArrayList<GcOrgCacheVO>(), c -> c.getId().equals(orgId));
    }

    protected List<GcOrgCacheVO> getParentOrg(List<GcOrgCacheVO> tree, String orgId) {
        ArrayList<GcOrgCacheVO> parent = new ArrayList<GcOrgCacheVO>();
        this.getOrgByParam(tree, parent, c -> c.getId().equals(orgId));
        Collections.reverse(parent);
        return parent;
    }

    protected GcOrgCacheVO getOrgByParam(List<GcOrgCacheVO> tree, List<GcOrgCacheVO> parent, Predicate<GcOrgCacheVO> predicate) {
        if (tree == null || tree.size() < 1) {
            return null;
        }
        for (GcOrgCacheVO c : tree) {
            GcOrgCacheVO data;
            if (predicate.test(c)) {
                parent.add(c);
                return c;
            }
            if (c.getChildren() == null || c.getChildren().size() <= 0 || (data = this.getOrgByParam(c.getChildren(), parent, predicate)) == null) continue;
            parent.add(c);
            return data;
        }
        return null;
    }

    protected void searchOrgByText(List<GcOrgCacheVO> tree, String searchText, String parentCode, List<GcOrgCacheVO> findOrgs) {
        if (tree == null || tree.size() < 1) {
            return;
        }
        if (StringUtils.isEmpty((String)searchText)) {
            this.treeToList(findOrgs, tree);
            return;
        }
        for (GcOrgCacheVO c : tree) {
            if ((c.getCode().toUpperCase().contains(searchText.toUpperCase()) || c.getTitle().toUpperCase().contains(searchText.toUpperCase())) && (StringUtils.isEmpty((String)parentCode) || c.getParentStr().contains(parentCode))) {
                findOrgs.add(c);
            }
            if (c.getChildren() == null || c.getChildren().size() <= 0) continue;
            this.searchOrgByText(c.getChildren(), searchText, parentCode, findOrgs);
        }
    }

    protected List<GcOrgCacheVO> formatOrgKinds(Collection<GcOrgCacheVO> list) {
        if (list == null) {
            return new ArrayList<GcOrgCacheVO>();
        }
        HashMap diffList = new HashMap();
        HashMap baseList = new HashMap();
        List parentIds = list.stream().map(org -> {
            GcOrgCacheVO vo;
            if (org.getDiffUnitId() != null && ((vo = (GcOrgCacheVO)diffList.get(org.getDiffUnitId())) == null || vo.getParentStr().contains(org.getParentStr()))) {
                diffList.put(org.getDiffUnitId(), org);
            }
            if (org.getBaseUnitId() != null && ((vo = (GcOrgCacheVO)baseList.get(org.getBaseUnitId())) == null || vo.getParentStr().contains(org.getParentStr()))) {
                baseList.put(org.getBaseUnitId(), org);
            }
            return org.getParentId();
        }).distinct().collect(Collectors.toList());
        List<GcOrgCacheVO> datas = list.stream().map(org -> {
            if (parentIds.contains(org.getId())) {
                ((GcOrgCacheInnerVO)((Object)org)).setOrgKind(GcOrgKindEnum.UNIONORG);
                ((GcOrgCacheInnerVO)((Object)org)).setLeaf(false);
                if (baseList.containsKey(org.getId())) {
                    ((GcOrgCacheInnerVO)((Object)org)).setMergeUnitId(((GcOrgCacheVO)baseList.get(org.getId())).getId());
                }
            } else {
                ((GcOrgCacheInnerVO)((Object)org)).setLeaf(true);
                ((GcOrgCacheInnerVO)((Object)org)).setOrgKind(GcOrgKindEnum.SINGLE);
                if (diffList.containsKey(org.getId())) {
                    ((GcOrgCacheInnerVO)((Object)org)).setMergeUnitId(((GcOrgCacheVO)diffList.get(org.getId())).getId());
                    ((GcOrgCacheInnerVO)((Object)org)).setOrgKind(GcOrgKindEnum.DIFFERENCE);
                }
                if (baseList.containsKey(org.getId())) {
                    ((GcOrgCacheInnerVO)((Object)org)).setMergeUnitId(((GcOrgCacheVO)baseList.get(org.getId())).getId());
                    ((GcOrgCacheInnerVO)((Object)org)).setOrgKind(GcOrgKindEnum.BASE);
                }
            }
            return org;
        }).collect(Collectors.toList());
        return datas;
    }

    protected List<Map<String, Object>> formatOrgKinds(List<Map<String, Object>> list) {
        HashMap diffList = new HashMap();
        HashMap baseList = new HashMap();
        List parentIds = list.stream().map(org -> {
            Map vo;
            String diffId = (String)org.get("DIFFUNITID");
            String baseunitId = (String)org.get("BASEUNITID");
            if (diffId != null && ((vo = (Map)diffList.get(diffId)) == null || ((String)vo.get("PARENTS")).contains((String)org.get("PARENTS")))) {
                diffList.put(diffId, org);
            }
            if (baseunitId != null && ((vo = (Map)baseList.get(baseunitId)) == null || ((String)vo.get("PARENTS")).contains((String)org.get("PARENTS")))) {
                baseList.put(baseunitId, org);
            }
            return (String)org.get("PARENTCODE");
        }).distinct().collect(Collectors.toList());
        List<Map<String, Object>> datas = list.stream().map(org -> {
            org.put("ORGKIND", GcOrgKindEnum.SINGLE);
            if (parentIds.contains(org.get("CODE"))) {
                org.put("ORGKIND", GcOrgKindEnum.UNIONORG);
                org.put("LEAF", false);
                if (baseList.containsKey(org.get("CODE"))) {
                    org.put("MERGEUNITID", ((Map)baseList.get(org.get("CODE"))).get("CODE"));
                }
            } else {
                org.put("LEAF", true);
                if (diffList.containsKey(org.get("CODE"))) {
                    org.put("MERGEUNITID", ((Map)diffList.get(org.get("CODE"))).get("CODE"));
                    org.put("ORGKIND", GcOrgKindEnum.DIFFERENCE);
                }
                if (baseList.containsKey(org.get("CODE"))) {
                    org.put("MERGEUNITID", ((Map)baseList.get(org.get("CODE"))).get("CODE"));
                    org.put("ORGKIND", GcOrgKindEnum.BASE);
                }
            }
            return org;
        }).collect(Collectors.toList());
        return datas;
    }

    protected void treeToList(List<GcOrgCacheVO> list, Collection<GcOrgCacheVO> tree) {
        tree.stream().forEach(v -> {
            list.add((GcOrgCacheVO)v);
            if (v.getChildren() != null && !v.getChildren().isEmpty()) {
                this.treeToList(list, v.getChildren());
            }
        });
    }

    protected <T> List<T> collectionToTree(Collection<T> list, Class<T> clzz) {
        if (clzz.isAssignableFrom(GcOrgCacheVO.class)) {
            return this.collectionToCacheTree(list);
        }
        return this.collectionToJsonTree(list);
    }

    protected List<GcOrgCacheVO> collectionToCacheTree(Collection<GcOrgCacheVO> list) {
        ArrayList tree = CollectionUtils.newArrayList();
        Map datas = list.stream().map(org -> {
            GcOrgCacheInnerVO vo = new GcOrgCacheInnerVO();
            ((GcOrgCacheInnerVO)((Object)org)).assignTo(vo);
            vo.setLeaf(true);
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

    protected List<OrgToJsonVO> collectionToJsonTree(Collection<OrgToJsonVO> list) {
        ArrayList tree = CollectionUtils.newArrayList();
        Map datas = list.stream().collect(Collectors.toMap(OrgToJsonVO::getId, Function.identity(), (o1, o2) -> o1));
        list.stream().forEach(org -> {
            OrgToJsonVO pobj = (OrgToJsonVO)datas.get(org.getParentid());
            if (pobj != null) {
                pobj.getChildren().add(datas.get(org.getId()));
                pobj.setLeaf(true);
            } else {
                org.setLeaf(true);
                tree.add(datas.get(org.getId()));
            }
        });
        return tree;
    }
}

