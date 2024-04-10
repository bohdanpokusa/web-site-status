package com.bohdanpokusa.websitestatus.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.bohdanpokusa.websitestatus.core.WebSiteStatusService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingsDialogWrapper extends DialogWrapper {
    private static final SettingsUi settingsUi = new SettingsUi();

    public SettingsDialogWrapper() {
        super(true); // use current window as parent
        setTitle("Plugin Settings");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return settingsUi.getSettingsPanel();
    }

    protected void doOKAction() {
        applySettings();
        super.doOKAction();
    }

    private void applySettings() {
        WebSiteStatusService.setServiceRunning(settingsUi.getRunStatusServiceRadioButton().isSelected());
        WebSiteStatusService.setWebSiteUrl(settingsUi.getWebSiteUrl().getText());
        WebSiteStatusService.setUpdateInterval(Integer.parseInt(settingsUi.getUpdateInterval().getText()));
    }

}
