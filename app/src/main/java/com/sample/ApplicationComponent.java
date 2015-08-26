package com.sample;

import com.sample.ui.ShutterStockFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    // Field injections of any dependencies of the VideoApplication
    void inject(App application);

    void inject(ShutterStockFragment fragment);
}