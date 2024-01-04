package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Article


@Database(
    entities = [Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {


    abstract fun getArticleDao(): ArticleDAO


    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val lock = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context, ArticleDatabase::class.java, "article_db.db"
        ).build()


    }


}