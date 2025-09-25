package com.calyrsoft.ucbp1.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
///import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
@Database(entities = [DollarEntity::class], version = 2, exportSchema = true)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dollarDao(): IDollarDao

    companion object {
        @Volatile private var Instance: AppRoomDatabase? = null

        private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                // OJO: usa exactamente los nombres de columnas/tabla de tu Entity
                // Si en tu Entity usaste @ColumnInfo(name="dollar_official") etc., usa esos nombres aquí.
                db.execSQL("ALTER TABLE dollars ADD COLUMN usdt TEXT")
                db.execSQL("ALTER TABLE dollars ADD COLUMN usdc TEXT")
                db.execSQL("ALTER TABLE dollars ADD COLUMN updatedAt TEXT")

            }
        }

        fun getDatabase(context: Context): AppRoomDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppRoomDatabase::class.java, "dollar_db")
                    .addMigrations(MIGRATION_1_2) // <- aplica migración
                    .build()
                    .also { Instance = it }
            }
    }
}
