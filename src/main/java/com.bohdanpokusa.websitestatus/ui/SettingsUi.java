package com.bohdanpokusa.websitestatus.ui;



import com.bohdanpokusa.websitestatus.core.PluginSettings;

import javax.swing.*;

import static com.intellij.openapi.components.ServiceManager.getService;

public class SettingsUi {
    private JPanel settingsPanel;
    private JTextField webSiteUrl;
    private JRadioButton runStatusServiceRadioButton;
    private JTextField updateInterval;
    private final PluginSettings pluginSettings = getService(PluginSettings.class);

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
