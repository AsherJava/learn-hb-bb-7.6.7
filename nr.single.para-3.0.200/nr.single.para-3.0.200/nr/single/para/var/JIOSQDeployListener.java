/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 */
package nr.single.para.var;

import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import java.util.Set;
import nr.single.para.var.JIOSQCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JIOSQDeployListener
implements ApplicationListener<DeployFinishedEvent> {
    @Autowired
    private JIOSQCache jioSQCache;

    @Override
    public void onApplicationEvent(DeployFinishedEvent event) {
        Set formSchemeKeys = event.getDeployParams().getFormScheme().getRunTimeKeys();
        formSchemeKeys.forEach(f -> this.jioSQCache.evict((String)f));
    }
}

