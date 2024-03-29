package org.nautilusapp.localstorage

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

class IosDatabaseDriver() : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "App1.db")
    }
}