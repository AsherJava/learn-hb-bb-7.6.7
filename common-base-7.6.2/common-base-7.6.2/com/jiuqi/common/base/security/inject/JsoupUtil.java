/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document$OutputSettings
 *  org.jsoup.safety.Safelist
 */
package com.jiuqi.common.base.security.inject;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class JsoupUtil {
    private static final Safelist whitelist = Safelist.basicWithImages();
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    public static String clean(String content) {
        return Jsoup.clean((String)content, (String)"", (Safelist)whitelist, (Document.OutputSettings)outputSettings);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String text = "<a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss";
        System.out.println(JsoupUtil.clean(text));
    }

    static {
        whitelist.addAttributes(":all", new String[]{"style"});
    }
}

