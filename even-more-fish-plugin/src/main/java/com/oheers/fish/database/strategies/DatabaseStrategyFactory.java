package com.Austin-W-Music.fish.database.strategies;

import com.Austin-W-Music.fish.database.connection.ConnectionFactory;
import com.Austin-W-Music.fish.database.connection.H2ConnectionFactory;
import com.Austin-W-Music.fish.database.connection.SqliteConnectionFactory;
import com.Austin-W-Music.fish.database.strategies.impl.H2Strategy;
import com.Austin-W-Music.fish.database.strategies.impl.MySqlStrategy;
import com.Austin-W-Music.fish.database.strategies.impl.SqliteStrategy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DatabaseStrategyFactory {
    @Contract("null -> new")
    public static @NotNull DatabaseTypeStrategy getStrategy(ConnectionFactory connectionFactory) {
        if (connectionFactory instanceof SqliteConnectionFactory) {
            return new SqliteStrategy();
        }
        if (connectionFactory instanceof H2ConnectionFactory) {
            return new H2Strategy();
        }
        return new MySqlStrategy();
    }
}
