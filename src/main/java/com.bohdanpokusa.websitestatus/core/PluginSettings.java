package com.bohdanpokusa.websitestatus.core;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(name = "PluginSettings", storages = @Storage("statusPluginSettings.xml"))
public final class PluginSettings implements PersistentStateComponent<PluginSettings> {
    public String webSiteUrl = "https://google.com";
    public int updateInterval = 30;
    public boolean serviceRunning = false;

    @Override
    @Nullable
    public PluginSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

/*    public static PluginSettings getInstance() {
        return ServiceManager.getService(PluginSettings.class);
    }*/

}
