package com.Austin-W-Music.fish.database.strategies.impl;

import com.Austin-W-Music.fish.database.strategies.DatabaseTypeStrategy;
import org.jetbrains.annotations.NotNull;
import org.jooq.conf.Settings;

public class SqliteStrategy implements DatabaseTypeStrategy {

    @Override
    public Settings applySettings(@NotNull Settings settings, String tablePrefix, String dbName) {
        return settings.withRenderSchema(false);
    }

}
