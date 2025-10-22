/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.datacrud.impl.check;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class DataFieldNode
extends DynamicNode {
    private int dataType;
    private final Object value;
    private final int scale;

    public DataFieldNode(Token token, int dataType, int scale, Object value) {
        super(token);
        this.dataType = dataType;
        this.value = value;
        this.scale = scale;
        if (dataType == 0) {
            this.dataType = DataType.typeOf((Object)value);
        }
    }

    public int getType(IContext context) {
        return this.dataType;
    }

    public Object evaluate(IContext context) {
        return this.value;
    }

    public void toString(StringBuilder buffer) {
        try {
            this.toFormula(null, buffer, null);
        }
        catch (InterpretException e) {
            if (this.isNull()) {
                buffer.append("null");
            }
            buffer.append(DataType.formatValue((int)this.dataType, (Object)this.value));
        }
    }

    public boolean isNull() {
        return this.value == null;
    }

    protected int initNumSacle(IContext context) {
        return this.scale;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (this.isNull()) {
            buffer.append("NULL");
        } else {
            switch (this.dataType) {
                case 1: {
                    buffer.append(DataType.formatValue((int)this.dataType, (Object)this.value, (String)"TRUE/FALSE"));
                    break;
                }
                case 2: {
                    buffer.append('\'').append(DataType.formatValue((int)2, (Object)this.value)).append('\'');
                    break;
                }
                case 6: {
                    String val = (String)this.value;
                    val = val.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'").replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n");
                    buffer.append('\"').append(val).append('\"');
                    break;
                }
                case 11: {
                    ArrayData arr = (ArrayData)this.value;
                    if (arr.isEmpty()) {
                        buffer.append("NULL");
                        break;
                    }
                    buffer.append(arr);
                    break;
                }
                default: {
                    buffer.append(DataType.formatValue((int)this.dataType, (Object)this.value));
                }
            }
        }
    }
}

