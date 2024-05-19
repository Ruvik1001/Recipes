package com.tasks.domain.room

import android.content.Context
import androidx.room.Room

object DBProvider {
    private var db: DB? = null

    fun getDB(context: Context): DB {
        if (db == null) {
            synchronized(DB::class) {
                if (db == null) {
                    db = Room.databaseBuilder(
                        context.applicationContext,
                        DB::class.java,
                        "my_db"
                    ).build()
                }
            }
        }
        return db!!
    }
}
