package com.bohdanpokusa.websitestatus.core;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(name = "PluginSettings", storages = @Storage("statusPluginSettings.xml"))
public final class PluginSettings implements PersistentStateComponent<PluginSettings> {
    public String webSiteUrl = "https://yourwebsite.com";
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

}
