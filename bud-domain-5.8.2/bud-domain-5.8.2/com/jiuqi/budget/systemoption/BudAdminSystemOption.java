/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 */
package com.jiuqi.budget.systemoption;

import com.jiuqi.budget.autoconfigure.BudProductNameComponent;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="budAdminSystemOption")
public class BudAdminSystemOption
implements ISystemOptionDeclare {
    public static final String ID = "budAdminSystemOption";
    public static final String OPT_ITEM_BUD_ADMIN_USERS = "budAdminUsers";
    public static final String BUD_BATCH_REL_REJECT_ADMIN_USERS = "budBatchRelRejectAdminUsers";
    @Autowired
    private ISystemOptionOperator systemOptionOperator;
    @Autowired
    private BudProductNameComponent productNameComponent;

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u7cfb\u7edf\u7ba1\u7406\u5458";
    }

    public String getNameSpace() {
        return this.productNameComponent.getProductName();
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> list = new ArrayList<ISystemOptionItem>();
        list.add(new ISystemOptionItem(){

            public String getId() {
                return BudAdminSystemOption.OPT_ITEM_BUD_ADMIN_USERS;
            }

            public String getTitle() {
                return "\u7cfb\u7edf\u7ba1\u7406\u5458";
            }

            public String getDefaultValue() {
                return "SYSTEM";
            }

            public String getDescribe() {
                return "\u7528\u4e8e\u914d\u7f6e\u5f53\u51fa\u73b0\u5f02\u5e38\u60c5\u51b5\u7684\u65f6\u5019\u901a\u77e5\u7684\u7ba1\u7406\u5458\u8d26\u53f7, \u7528\u4e8e\u7cfb\u7edf\u51fa\u73b0\u5f02\u5e38\uff08\u5982\u5f02\u6b65\u4efb\u52a1\u3001\u63a5\u53e3\u96c6\u6210\u3001\u7cfb\u7edf\u76d1\u63a7\u7b49\uff09\u65f6\u5019\u7684\u7ed9\u4e0e\u8fd0\u7ef4\u7ba1\u7406\u5458\u6d88\u606f\u63d0\u793a\uff0c\u8d70\u5973\u5a32\u6d88\u606f\u7ba1\u7406\u901a\u9053\u53ca\u914d\u7f6e\uff08\u901a\u77e5\u65b9\u5f0f\uff09";
            }

            public String getPlaceholder() {
                return "\u8bf7\u8f93\u5165\u7ba1\u7406\u5458\u7528\u6237\u540d\uff0c\u591a\u4e2a\u7528\u6237\u540d\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00";
            }
        });
        list.add(new ISystemOptionItem(){

            public String getId() {
                return BudAdminSystemOption.BUD_BATCH_REL_REJECT_ADMIN_USERS;
            }

            public String getTitle() {
                return "\u7ea7\u8054\u9a73\u56de\u7ba1\u7406\u5458";
            }

            public String getDefaultValue() {
                return "SYSTEM";
            }

            public String getDescribe() {
                return "\u53ea\u6709\u914d\u7f6e\u89d2\u8272\uff0c\u5e76\u4e14\u4e3a\u89d2\u8272\u4e0b\u7684\u7528\u6237\u65f6\uff0c\u4efb\u52a1\u76d1\u63a7\u4e2d\u201c\u6279\u91cf\u7ea7\u8054\u9a73\u56de\u201d\u6309\u94ae\u624d\u53ef\u7528";
            }

            public String getPlaceholder() {
                return "\u8bf7\u8f93\u5165\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00";
            }
        });
        list.add(new ISystemOptionItem(){

            public String getId() {
                return "budBatchResetAdminUsers";
            }

            public String getTitle() {
                return "\u6d41\u7a0b\u91cd\u7f6e\u7ba1\u7406\u5458";
            }

            public String getDefaultValue() {
                return "SYSTEM";
            }

            public String getDescribe() {
                return "\u53ea\u6709\u914d\u7f6e\u89d2\u8272\uff0c\u5e76\u4e14\u4e3a\u89d2\u8272\u4e0b\u7684\u7528\u6237\u65f6\uff0c\u4efb\u52a1\u76d1\u63a7\u4e2d\u201c\u6279\u91cf\u91cd\u7f6e\u201d\u6309\u94ae\u624d\u53ef\u7528";
            }

            public String getPlaceholder() {
                return "\u8bf7\u8f93\u5165\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00";
            }
        });
        list.add(new ISystemOptionItem(){

            public String getId() {
                return "budBatchLockAdminUsers";
            }

            public String getTitle() {
                return "\u6279\u91cf\u9501\u5b9a\u7ba1\u7406\u5458";
            }

            public String getDefaultValue() {
                return "SYSTEM";
            }

            public String getDescribe() {
                return "\u4ec5\u652f\u6301\u914d\u7f6e\u89d2\u8272\uff0c\u914d\u7f6e\u540e\u5f53\u524d\u89d2\u8272\u4e0b\u7684\u7528\u6237\u53ef\u4ee5\u65e0\u89c6\u73b0\u6709\u8868\u7684\u9501\u5b9a\u72b6\u6001\uff0c\u53ef\u76f4\u63a5\u8fdb\u884c\u5f3a\u5236\u89e3\u9501\u64cd\u4f5c";
            }

            public String getPlaceholder() {
                return "\u8bf7\u8f93\u5165\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u7528\u82f1\u6587\u9017\u53f7\u9694\u5f00";
            }
        });
        return list;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

