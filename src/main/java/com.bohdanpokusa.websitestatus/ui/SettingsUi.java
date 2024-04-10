package com.bohdanpokusa.websitestatus.ui;


import com.bohdanpokusa.websitestatus.core.PluginSettings;
import com.intellij.openapi.application.ApplicationManager;

import javax.swing.*;

public class SettingsUi {
    private JPanel settingsPanel;
    private JTextField webSiteUrl;
    private JRadioButton runStatusServiceRadioButton;
    private JTextField updateInterval;
    private final PluginSettings pluginSettings = ApplicationManager.getApplication().getService(PluginSettings.class);

    public SettingsUi() {
        webSiteUrl.setText(pluginSettings.webSiteUrl);
        runStatusServiceRadioButton.setSelected(pluginSettings.serviceRunning);
        updateInterval.setText(Integer.toString(pluginSettings.updateInterval));
    }

    public JTextField getUpdateInterval() {
        return updateInterval;
    }

    public JRadioButton getRunStatusServiceRadioButton() {
        return runStatusServiceRadioButton;
    }

    public JTextField getWebSiteUrl() {
        return webSiteUrl;
    }

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }

}
