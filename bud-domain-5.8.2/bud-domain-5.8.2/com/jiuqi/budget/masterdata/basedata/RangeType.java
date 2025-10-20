/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.CommonUtil
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public enum RangeType {
    NONE(""){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (all instanceof List) {
                return (List)all;
            }
            return new ArrayList<FBaseDataObj>(all);
        }
    }
    ,
    SELF("\u81ea\u5df1"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            return Collections.singletonList(self);
        }
    }
    ,
    ALL_CHILDREN("\u6240\u6709\u4e0b\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            Map<String, List<FBaseDataObj>> parentToChildren = 3.getParentToChildren(all);
            List<FBaseDataObj> children = parentToChildren.get(self.getCode());
            if (children == null) {
                return new ArrayList<FBaseDataObj>();
            }
            LinkedList<FBaseDataObj> linkedList = new LinkedList<FBaseDataObj>(children);
            ArrayList<FBaseDataObj> result = new ArrayList<FBaseDataObj>();
            while (!linkedList.isEmpty()) {
                FBaseDataObj orgDataObj = linkedList.pop();
                result.add(orgDataObj);
                List<FBaseDataObj> childList = parentToChildren.get(orgDataObj.getCode());
                if (childList == null || childList.isEmpty()) continue;
                linkedList.addAll(0, childList);
            }
            return result;
        }
    }
    ,
    ALL_CHILDREN_AND_SELF("\u81ea\u5df1\u53ca\u6240\u6709\u4e0b\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            List<FBaseDataObj> allChildren = ALL_CHILDREN.getFilterResult(self, all);
            allChildren.add(0, self);
            return allChildren;
        }
    }
    ,
    MINE_TOP("\u6700\u9876\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            String topCode = 5.getTopCode(self.getParents());
            if (!StringUtils.hasLength(topCode)) {
                return Collections.singletonList(self);
            }
            Optional<FBaseDataObj> any = all.stream().filter(baseDataObj -> topCode.equals(baseDataObj.getCode())).findAny();
            return any.map(Collections::singletonList).orElseGet(ArrayList::new);
        }
    }
    ,
    TOP_AND_SELF("\u81ea\u5df1\u53ca\u6700\u9876\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            ArrayList<FBaseDataObj> filterResult = new ArrayList<FBaseDataObj>(MINE_TOP.getFilterResult(self, all));
            if (filterResult.isEmpty()) {
                filterResult.add(self);
            } else if (!((FBaseDataObj)filterResult.get(0)).getKey().equals(self.getKey())) {
                filterResult.add(self);
            }
            return filterResult;
        }
    }
    ,
    BROTHER("\u5144\u5f1f"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            String parentCode = self.getParent();
            return all.stream().filter(baseDataObj -> parentCode.equals(baseDataObj.getParent())).collect(Collectors.toList());
        }
    }
    ,
    BROTHER_AND_SELF("\u81ea\u5df1\u53ca\u5144\u5f1f"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            List<FBaseDataObj> brother = BROTHER.getFilterResult(self, all);
            brother.add(0, self);
            return brother;
        }
    }
    ,
    CHILDREN("\u76f4\u63a5\u4e0b\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            String parentCode = self.getCode();
            return all.stream().filter(baseDataObj -> parentCode.equals(baseDataObj.getParent())).collect(Collectors.toList());
        }
    }
    ,
    CHILDREN_AND_SELF("\u81ea\u5df1\u53ca\u76f4\u63a5\u4e0b\u7ea7"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            List<FBaseDataObj> children = CHILDREN.getFilterResult(self, all);
            children.add(0, self);
            return children;
        }
    }
    ,
    PARENT("\u76f4\u63a5\u7236\u8282\u70b9"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            String parentCode = self.getParent();
            Optional<FBaseDataObj> any = all.stream().filter(baseDataObj -> parentCode.equals(baseDataObj.getCode())).findAny();
            return any.map(Collections::singletonList).orElseGet(ArrayList::new);
        }
    }
    ,
    PARENT_AND_SELF("\u81ea\u5df1\u53ca\u76f4\u63a5\u7236\u8282\u70b9"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            ArrayList<FBaseDataObj> filterResult = new ArrayList<FBaseDataObj>(PARENT.getFilterResult(self, all));
            if (filterResult.isEmpty()) {
                filterResult.add(self);
            } else if (!((FBaseDataObj)filterResult.get(0)).getKey().equals(self.getKey())) {
                filterResult.add(self);
            }
            return filterResult;
        }
    }
    ,
    ALL_PARENT("\u6240\u6709\u7236\u8282\u70b9"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            List<String> parentCodes = 13.getParentCodes(self.getParents());
            Map<String, FBaseDataObj> collect = all.stream().filter(baseDataObj -> parentCodes.contains(baseDataObj.getCode())).collect(Collectors.toMap(FBaseDataObj::getCode, baseData -> baseData));
            ArrayList<FBaseDataObj> result = new ArrayList<FBaseDataObj>(parentCodes.size() + 1);
            for (String parentCode : parentCodes) {
                result.add(collect.get(parentCode));
            }
            return result;
        }
    }
    ,
    ALL_PARENT_AND_SELF("\u81ea\u5df1\u53ca\u6240\u6709\u7236\u8282\u70b9"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            List<FBaseDataObj> filterResult = ALL_PARENT.getFilterResult(self, all);
            filterResult.add(self);
            return filterResult;
        }
    }
    ,
    MINE_LEAF("\u81ea\u5df1\u7684\u6240\u6709\u53f6\u5b50"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            Map<String, List<FBaseDataObj>> parentToChildren = 15.getParentToChildren(all);
            List<FBaseDataObj> children = parentToChildren.get(self.getCode());
            if (children == null) {
                return new ArrayList<FBaseDataObj>();
            }
            LinkedList<FBaseDataObj> linkedList = new LinkedList<FBaseDataObj>(children);
            ArrayList<FBaseDataObj> result = new ArrayList<FBaseDataObj>();
            while (!linkedList.isEmpty()) {
                FBaseDataObj orgDataObj = linkedList.pop();
                List<FBaseDataObj> childList = parentToChildren.get(orgDataObj.getCode());
                if (childList != null && !childList.isEmpty()) {
                    linkedList.addAll(0, childList);
                    continue;
                }
                result.add(orgDataObj);
            }
            return result;
        }
    }
    ,
    MINE_NOT_LEAF("\u81ea\u5df1\u7684\u6240\u6709\u975e\u53f6\u5b50"){

        @Override
        public List<FBaseDataObj> getFilterResult(FBaseDataObj self, Collection<FBaseDataObj> all) {
            if (self == null) {
                return new ArrayList<FBaseDataObj>();
            }
            Map<String, List<FBaseDataObj>> parentToChildren = 16.getParentToChildren(all);
            List<FBaseDataObj> children = parentToChildren.get(self.getCode());
            if (children == null) {
                return new ArrayList<FBaseDataObj>();
            }
            LinkedList<FBaseDataObj> linkedList = new LinkedList<FBaseDataObj>(children);
            ArrayList<FBaseDataObj> result = new ArrayList<FBaseDataObj>();
            while (!linkedList.isEmpty()) {
                FBaseDataObj orgDataObj = linkedList.pop();
                List<FBaseDataObj> childList = parentToChildren.get(orgDataObj.getCode());
                if (childList == null || childList.isEmpty()) continue;
                linkedList.addAll(0, childList);
                result.add(orgDataObj);
            }
            return result;
        }
    };

    private final String title;
    static final char PARENTS_SEPARATOR = '/';

    private RangeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract List<FBaseDataObj> getFilterResult(FBaseDataObj var1, Collection<FBaseDataObj> var2);

    static Map<String, List<FBaseDataObj>> getParentToChildren(Collection<FBaseDataObj> all) {
        HashMap<String, List<FBaseDataObj>> parentToChildren = new HashMap<String, List<FBaseDataObj>>();
        for (FBaseDataObj baseDataObj : all) {
            String parentCode = baseDataObj.getParent();
            parentToChildren.computeIfAbsent(parentCode, key -> new ArrayList()).add(baseDataObj);
        }
        return parentToChildren;
    }

    public static String getTopCode(String parents) {
        int index = parents.indexOf(47);
        if (index == -1) {
            return null;
        }
        return parents.substring(0, index);
    }

    public static List<String> getParentCodes(String parents) {
        ArrayList strings = CommonUtil.splitStr((String)parents, (char)'/');
        if (strings.size() == 1) {
            return new ArrayList<String>();
        }
        return strings.subList(0, strings.size() - 1);
    }
}

