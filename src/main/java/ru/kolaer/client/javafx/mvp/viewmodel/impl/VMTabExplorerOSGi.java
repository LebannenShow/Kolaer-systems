package ru.kolaer.client.javafx.mvp.viewmodel.impl;

import ru.kolaer.api.plugins.UniformSystemPlugin;
import ru.kolaer.client.javafx.plugins.PluginBundle;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Danilov on 15.04.2016.
 */
public class VMTabExplorerOSGi extends AbstractVMTabExplorer {

    public VMTabExplorerOSGi() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void addPlugin(PluginBundle pluginBundle) {
        this.addTabPlugin(pluginBundle.getNamePlugin(), pluginBundle.getUniformSystemPlugin());
    }

    @Override
    public void addAllPlugins(Collection<PluginBundle> plugins) {
        plugins.parallelStream().forEach(this::addPlugin);
    }

    @Override
    public void removePlugin(PluginBundle pluginBundle) {
        this.removeTabPlugin(pluginBundle.getNamePlugin());
    }

    @Override
    public void removeAll() {

    }


    @Override
    public void addTabPlugin(String tabName, UniformSystemPlugin uniformSystemPlugin) {
        
    }

    @Override
    public void removeTabPlugin(String name) {

    }
}
