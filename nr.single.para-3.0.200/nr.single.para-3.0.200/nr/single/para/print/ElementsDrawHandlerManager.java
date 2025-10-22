/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.print;

import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import nr.single.para.print.ElementsDrawHandler;
import nr.single.para.print.IElementsDrawHandler;

public class ElementsDrawHandlerManager {
    public static final String DEF_HANDLER_ID = "_defaultHandler";
    private static Map<String, IElementsDrawHandler> handlers = new HashMap<String, IElementsDrawHandler>();

    public static IElementsDrawHandler getHandler(String handlerId) {
        if (StringUtils.isEmpty((String)handlerId)) {
            return null;
        }
        if (handlers.containsKey(handlerId)) {
            return handlers.get(handlerId);
        }
        return ElementsDrawHandlerManager.getHandler();
    }

    public static IElementsDrawHandler getHandler() {
        return handlers.get(DEF_HANDLER_ID);
    }

    public static void removeHandler(String handlerId) {
        handlers.remove(handlerId);
    }

    public static void registerHandler(IElementsDrawHandler handler) {
        handlers.put(handler.getId(), handler);
    }

    static {
        handlers.put(DEF_HANDLER_ID, new ElementsDrawHandler());
    }
}

