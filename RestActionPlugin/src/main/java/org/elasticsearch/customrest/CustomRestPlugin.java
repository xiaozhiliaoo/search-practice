package org.elasticsearch.customrest;

import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;


public class CustomRestPlugin extends Plugin implements ActionPlugin {

    @Override
    public Collection<String> getRestHeaders() {
        return Collections.singletonList("CustomRestActionPlugin");
    }

}
