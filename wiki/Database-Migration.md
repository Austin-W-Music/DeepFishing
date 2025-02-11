> 📖 Permission: `df.admin.debug.database`
# Database Migration
DF uses flyway to automatically migrate the database. Normally you shouldn't need to manually migrate the database.
In case things breaks you can use some commands to try and fix the issues.

> 📖 Permission: `df.admin.debug.database.flyway`
## /df admin database drop-flyway

Drops the flyway schema history table.
## /df admin database repair-flyway
Runs the flyway repair command.
## /df admin database clean-flyway
Runs the flyway clean command.
## /df admin database migrate-to-latest
> 📖 Permission: `df.admin.debug.database.migrate`
> 
Attempts to migrate the database to the latest version.


## Migrating from V2
Running the `/df admin migrate` will attempt to migrate from database version 2 to the latest version.
