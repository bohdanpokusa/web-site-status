package com.bohdanpokusa.websitestatus;

import com.bohdanpokusa.websitestatus.core.ListenersManager;
import com.bohdanpokusa.websitestatus.core.PluginSettings;
import com.bohdanpokusa.websitestatus.core.WebSiteStatusService;
import com.bohdanpokusa.websitestatus.ui.SettingsDialogWrapper;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseEvent;


public class WebSiteStatusFactory implements StatusBarWidgetFactory {
    private static final String ID = "WebSiteStatus";
    private static StatusBar statusBar;

    public WebSiteStatusFactory() {
        ApplicationManager.getApplication().getMessageBus().connect().subscribe(WebSiteStatusService.TOPIC,
                () -> updatePlugin());
        WebSiteStatusService.startService();
        ListenersManager.addFocusChangeListeners();
    }

    public static void updatePlugin() {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            statusBar = WindowManager.getInstance().getStatusBar(project);
            if (statusBar != null) {
                statusBar.updateWidget(ID);
            }
        }
    }

    @Override
    public @NotNull @NonNls String getId() {
        return ID;
    }

    @Override
    public @NotNull String getDisplayName() {
        return "Web Site Plugin";
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new EnvInformerWidget();
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    private static final class EnvInformerWidget implements StatusBarWidget, StatusBarWidget.IconPresentation {
        @Override
        public @NotNull String ID() {
            return ID;
        }

        @Override
        public @NotNull WidgetPresentation getPresentation() {
            return this;
        }

        @Override
        public @NotNull String getTooltipText() {
            if (ApplicationManager.getApplication().getService(PluginSettings.class).serviceRunning) {
                return WebSiteStatusService.isWebSiteReachable() ?
                        "Web site up" :
                        "Web site down";
            } else return "Update service off";
        }

        @Override
        public @NotNull Consumer<MouseEvent> getClickConsumer() {
            return __ -> {
                new SettingsDialogWrapper().showAndGet();
            };
        }

        @Override
        public @NotNull Icon getIcon() {
            if (ApplicationManager.getApplication().getService(PluginSettings.class).serviceRunning) {
                return WebSiteStatusService.isWebSiteReachable() ? AllIcons.Debugger.ThreadStates.Idle : AllIcons.General.Error;
            } else {
                return AllIcons.Actions.Cancel;
            }
        }
    }

}
