package com.example.vocabulary

import android.content.Context
import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "word")
data class Word(
    val text: String,
    val mean: String,
    val type: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
):Parcelable

@Dao
interface WordDao {
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>
    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")
    fun getLatestWord() : Word
    @Insert
    fun insert(word: Word)
    @Delete
    fun delete(word: Word)
    @Update
    fun update(word: Word)
}

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}