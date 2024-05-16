package com.bohdanpokusa.websitestatus.ui;

import com.bohdanpokusa.websitestatus.core.WebSiteStatusService;
import com.intellij.openapi.ui.DialogWrapper;
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
        WebSiteStatusService.setWebSiteUrl(settingsUi.getWebSiteUrl().getText());
        WebSiteStatusService.setUpdateInterval(Integer.parseInt(settingsUi.getUpdateInterval().getText()));
        WebSiteStatusService.setUpdateOnFocus(settingsUi.getCheckOnFocusChangeRadioButton().isSelected());
        WebSiteStatusService.setServiceRunning(settingsUi.getRunStatusServiceRadioButton().isSelected());
    }

}
