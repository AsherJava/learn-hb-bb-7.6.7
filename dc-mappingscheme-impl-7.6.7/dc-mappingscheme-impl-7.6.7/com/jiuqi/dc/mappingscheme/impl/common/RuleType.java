/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.RuleTypeClass
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.mappingscheme.impl.common;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.RuleTypeClass;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Optional;

public enum RuleType implements IRuleType
{
    ALL("ALL", "\u5f3a\u6620\u5c04\u7ea6\u675f"),
    PART("PART", "\u6620\u5c04+\u6e90\u503c\u517c\u5bb9"),
    NONE("NONE", "\u53d6\u6e90\u503c"),
    ID_TO_CODE("ID_TO_CODE", "\u6e90CODE\uff08\u6839\u636e\u6e90ID\u8f6c\u6362\uff09"),
    ID_TO_DS_CODE("ID_TO_DS_CODE", "\u6620\u5c04\u65b9\u6848\u7f16\u53f7+\u6e90CODE\uff08\u6839\u636e\u6e90ID\u8f6c\u6362\uff09"),
    CODE_TO_DS_CODE("CODE_TO_DS_CODE", "\u6620\u5c04\u65b9\u6848\u7f16\u53f7+\u6e90CODE\uff08\u6839\u636e\u6e90CODE\u8f6c\u6362\uff09"),
    ITEM_BY_ITEM("ITEM_BY_ITEM", "\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM),
    CONST_TRANSFER("CONST_TRANSFER", "\u5e38\u91cf\u8f6c\u6362"),
    ISOLATION_BY_UNIT("ISOLATION_BY_UNIT", "\u6309\u7ec4\u7ec7\u673a\u6784\u9694\u79bb"),
    ISOLATION_BY_UNIT_ITEM_BY_ITEM("ISOLATION_BY_UNIT_ITEM_BY_ITEM", "\u6309\u7ec4\u7ec7\u673a\u6784\u9694\u79bb\u4e0b\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("UNITCODE", "\u7ec4\u7ec7\u673a\u6784")});
        }
    }
    ,
    SHARE_TO_SUB_BY_UNIT("SHARE_TO_SUB_BY_UNIT", "\u6309\u7ec4\u7ec7\u673a\u6784\u5411\u4e0b\u7ea7\u5171\u4eab"),
    SHARE_TO_SUB_BY_BOOKUNIT("SHARE_TO_SUB_BY_BOOKUNIT", "\u6309\u8d26\u7c3f+\u7ec4\u7ec7\u673a\u6784\u5411\u4e0b\u7ea7\u5171\u4eab"),
    ISOLATION_BY_BOOKUNIT_ITEM_BY_ITEM("ISOLATION_BY_BOOKUNIT_ITEM_BY_ITEM", "\u6309\u8d26\u7c3f+\u7ec4\u7ec7\u673a\u6784\u9694\u79bb\u4e0b\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("BOOKCODE", "\u8d26\u7c3f"), new SelectOptionVO("UNITCODE", "\u7ec4\u7ec7\u673a\u6784")});
        }
    }
    ,
    ISOLATION_BY_BOOK("ISOLATION_BY_BOOK", "\u6309\u8d26\u5957\u9694\u79bb"),
    ISOLATION_BY_BOOK_ITEM_BY_ITEM("ISOLATION_BY_BOOK_ITEM_BY_ITEM", "\u6309\u8d26\u5957\u9694\u79bb\u4e0b\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("BOOKCODE", "\u8d26\u7c3f")});
        }
    }
    ,
    ISOLATION_BY_YEAR_ITEM_BY_ITEM("ISOLATION_BY_YEAR_ITEM_BY_ITEM", "\u6309\u5e74\u5ea6\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("YEAR", "\u4f1a\u8ba1\u5e74\u5ea6")});
        }
    }
    ,
    ISOLATION_BY_YEARUNIT_ITEM_BY_ITEM("ISOLATION_BY_YEARUNIT_ITEM_BY_ITEM", "\u6309\u5e74\u5ea6+\u7ec4\u7ec7\u673a\u6784\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("YEAR", "\u4f1a\u8ba1\u5e74\u5ea6"), new SelectOptionVO("UNITCODE", "\u7ec4\u7ec7\u673a\u6784")});
        }
    }
    ,
    SUBJECT_ITEM_BY_ITEM_ASSIST("SUBJECT_ITEM_BY_ITEM_ASSIST", "\u79d1\u76ee\u9010\u6761\u6620\u5c04\u8f85\u52a9\u9879", RuleTypeClass.ITEM_BY_ITEM),
    SHARE_ISOLATION_BY_UNIT("SHARE_ISOLATION_BY_UNIT", "\u6309\u7ec4\u7ec7\u673a\u6784\u5171\u4eab+\u9694\u79bb"),
    SHARE_ISOLATION_BY_UNIT_ITEM_BY_ITEM("SHARE_ISOLATION_BY_UNIT_ITEM_BY_ITEM", "\u6309\u7ec4\u7ec7\u673a\u6784\u5171\u4eab+\u9694\u79bb\u4e0b\u9010\u6761\u6620\u5c04", RuleTypeClass.ITEM_BY_ITEM){

        @Override
        public List<SelectOptionVO> isolationDim() {
            return CollectionUtils.newArrayList((Object[])new SelectOptionVO[]{new SelectOptionVO("UNITCODE", "\u7ec4\u7ec7\u673a\u6784")});
        }
    }
    ,
    CUSTOM_BY_CODE("CUSTOM_BY_CODE", "\u6309\u6e90\u503c\u81ea\u5b9a\u4e49\u6620\u5c04", RuleTypeClass.CUSTOM),
    CUSTOM_BY_ID_TO_CODE("CUSTOM_BY_ID_TO_CODE", "\u6309\u6e90CODE\u81ea\u5b9a\u4e49\u6620\u5c04\uff08\u6839\u636e\u6e90ID\u8f6c\u6362\uff09", RuleTypeClass.CUSTOM);

    private String code;
    private String name;
    private RuleTypeClass ruleTypeClass;

    private RuleType(String code, String name) {
        this.code = code;
        this.name = name;
        this.ruleTypeClass = RuleTypeClass.NON_ITEM_BY_ITEM;
    }

    private RuleType(String code, String name, RuleTypeClass ruleTypeClass) {
        this.code = code;
        this.name = name;
        this.ruleTypeClass = ruleTypeClass;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public RuleTypeClass getRuleTypeClass() {
        return this.ruleTypeClass;
    }

    @Override
    public Boolean getItem2Item() {
        return RuleTypeClass.NON_ITEM_BY_ITEM != this.getRuleTypeClass();
    }

    public static Boolean isItemByItem(String code) {
        return Optional.ofNullable(((IRuleTypeGather)ApplicationContextRegister.getBean(IRuleTypeGather.class)).getRuleTypeByCode(code)).map(IRuleType::getItem2Item).orElse(Boolean.FALSE);
    }
}

