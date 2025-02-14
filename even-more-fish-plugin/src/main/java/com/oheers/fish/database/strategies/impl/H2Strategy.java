package com.Austin-W-Music.fish.database.strategies.impl;

import com.Austin-W-Music.fish.config.MainConfig;
import com.Austin-W-Music.fish.database.strategies.DatabaseTypeStrategy;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.jetbrains.annotations.NotNull;


public class H2Strategy implements DatabaseTypeStrategy {

    @Override
    public FluentConfiguration configureFlyway(@NotNull FluentConfiguration flywayConfig) {
        return flywayConfig.schemas(MainConfig.getInstance().getDatabase());
    }
}
