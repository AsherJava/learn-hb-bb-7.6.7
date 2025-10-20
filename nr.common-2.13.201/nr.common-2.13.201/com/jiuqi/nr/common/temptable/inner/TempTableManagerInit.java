/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class TempTableManagerInit {
    @Autowired(required=false)
    private List<BaseTempTableDefine> tempTableDefines;

    public void init() {
        if (this.tempTableDefines != null) {
            for (BaseTempTableDefine tempTableDefine : this.tempTableDefines) {
                String type = tempTableDefine.getType();
                if (type == null) {
                    throw new IllegalArgumentException("\u4e34\u65f6\u8868\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a," + (Object)((Object)tempTableDefine));
                }
                if (type.matches("^[a-zA-Z][a-zA-Z0-9_]{0,12}$")) continue;
                throw new IllegalArgumentException("\u4e34\u65f6\u8868Type\u4e0d\u7b26\u5408\u89c4\u8303," + (Object)((Object)tempTableDefine) + ",Type \u5fc5\u987b\u5b57\u6bcd\u5f00\u5934\uff0c\u6570\u5b57\u3001\u5b57\u6bcd\u548c\u4e0b\u5212\u7ebf\u7ec4\u6210\u76841-10\u4f4d\u7684\u5b57\u7b26\u4e32");
            }
        }
    }
}

