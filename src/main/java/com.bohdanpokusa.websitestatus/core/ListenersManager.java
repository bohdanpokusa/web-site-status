package com.bohdanpokusa.websitestatus.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ex.EditorEventMulticasterEx;
import com.intellij.openapi.editor.ex.FocusChangeListener;
import org.jetbrains.annotations.NotNull;

public class ListenersManager {
    private static PluginSettings pluginSettings = ApplicationManager.getApplication().getService(PluginSettings.class);

    public static void addFocusChangeListeners() {
        Object multicaster = EditorFactory.getInstance().getEventMulticaster();
        if (multicaster instanceof EditorEventMulticasterEx) {
            EditorEventMulticasterEx ex = (EditorEventMulticasterEx) multicaster;
            ex.addFocusChangeListener(new FocusChangeListener() {
                @Override
                public void focusGained(@NotNull Editor editor) {
                    if (pluginSettings.updateOnFocus) WebSiteStatusService.checkStatus();
                }
            }, ApplicationManager.getApplication());
        }
    }

}
