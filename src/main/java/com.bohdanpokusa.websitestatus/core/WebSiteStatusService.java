package com.bohdanpokusa.websitestatus.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.messages.Topic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EventListener;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSiteStatusService {
    public static final Topic<WebSiteStatusService.EnvStatusListener> TOPIC = new Topic<>(WebSiteStatusService.EnvStatusListener.class, Topic.BroadcastDirection.TO_DIRECT_CHILDREN);
    private static boolean reachable = false;
    private static PluginSettings pluginSettings = ApplicationManager.getApplication().getService(PluginSettings.class);

    public static void startService() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(WebSiteStatusService::checkStatus, 0, pluginSettings.updateInterval,
                TimeUnit.SECONDS);
    }

    public static void setReachable(boolean reachable) {
        WebSiteStatusService.reachable = reachable;
        ApplicationManager.getApplication().getMessageBus().syncPublisher(TOPIC).serverStatusUpdated();
    }

    public static boolean isWebSiteReachable() {
        return reachable;
    }

    public static void checkStatus() {
        if (pluginSettings.serviceRunning) {
            try {
                boolean reachable = getStatus();
                setReachable(reachable);
            } catch (IOException e) {
                setReachable(false);
            }
        } else setReachable(false);
    }

    private static boolean getStatus() throws IOException {
        HttpURLConnection connection =
                (HttpURLConnection) new URL(Objects.requireNonNull(pluginSettings.webSiteUrl)).openConnection();
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.setRequestMethod("HEAD");
        return 200 <= connection.getResponseCode();
    }

    public static void setWebSiteUrl(String webSiteUrl) {
        pluginSettings.webSiteUrl = webSiteUrl;
        ApplicationManager.getApplication().getService(PluginSettings.class).loadState(pluginSettings);
    }

    public static void setServiceRunning(boolean serviceRunning) {
        pluginSettings.serviceRunning = serviceRunning;
        ApplicationManager.getApplication().getService(PluginSettings.class).loadState(pluginSettings);
        ApplicationManager.getApplication().getMessageBus().syncPublisher(TOPIC).serverStatusUpdated();
    }

    public static void setUpdateInterval(int updateInterval) {
        pluginSettings.updateInterval = updateInterval;
        ApplicationManager.getApplication().getService(PluginSettings.class).loadState(pluginSettings);
    }

    public interface EnvStatusListener extends EventListener {
        void serverStatusUpdated();
    }
}
