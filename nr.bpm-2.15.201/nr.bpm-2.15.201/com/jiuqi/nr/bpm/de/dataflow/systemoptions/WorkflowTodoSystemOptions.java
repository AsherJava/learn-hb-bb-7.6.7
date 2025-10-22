/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 */
package com.jiuqi.nr.bpm.de.dataflow.systemoptions;

import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowTodoSystemOptions
implements ISystemOptionDeclare {
    private static final String TODONAME = "\u5f85\u529e";
    public static final String TODOID = "nr-flow-todo-id";
    public static final String PROCESS_UPLOAD_CAN_SEND_MSG = "PROCESS_UPLOAD_CAN_SEND_MSG";
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return TODOID;
    }

    public String getTitle() {
        return TODONAME;
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)new AbstractCheckBoxOption(){

            public String getId() {
                return WorkflowTodoSystemOptions.PROCESS_UPLOAD_CAN_SEND_MSG;
            }

            public String getTitle() {
                return " \u542f\u7528\u5f85\u529e\u4e8b\u9879";
            }

            public String getDefaultValue() {
                return "[\"0\",\"1\"]";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> optionItems = new ArrayList<ISystemOptionalValue>();
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6d41\u7a0b\u5f85\u529e";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7533\u8bf7\u9000\u56de";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                return optionItems;
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public int getOrdinal() {
        return 11;
    }
}

