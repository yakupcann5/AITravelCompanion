package com.travel.`companion`.core.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.travel.`companion`.core.database.entity.TranslationHistoryEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class TranslationHistoryDao_Impl(
  __db: RoomDatabase,
) : TranslationHistoryDao {
  private val __db: RoomDatabase

  private val __upsertAdapterOfTranslationHistoryEntity:
      EntityUpsertAdapter<TranslationHistoryEntity>
  init {
    this.__db = __db
    this.__upsertAdapterOfTranslationHistoryEntity =
        EntityUpsertAdapter<TranslationHistoryEntity>(object :
        EntityInsertAdapter<TranslationHistoryEntity>() {
      protected override fun createQuery(): String =
          "INSERT INTO `translation_history` (`id`,`sourceText`,`targetText`,`sourceLang`,`targetLang`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TranslationHistoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.sourceText)
        statement.bindText(3, entity.targetText)
        statement.bindText(4, entity.sourceLang)
        statement.bindText(5, entity.targetLang)
        statement.bindLong(6, entity.timestamp)
      }
    }, object : EntityDeleteOrUpdateAdapter<TranslationHistoryEntity>() {
      protected override fun createQuery(): String =
          "UPDATE `translation_history` SET `id` = ?,`sourceText` = ?,`targetText` = ?,`sourceLang` = ?,`targetLang` = ?,`timestamp` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TranslationHistoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.sourceText)
        statement.bindText(3, entity.targetText)
        statement.bindText(4, entity.sourceLang)
        statement.bindText(5, entity.targetLang)
        statement.bindLong(6, entity.timestamp)
        statement.bindLong(7, entity.id)
      }
    })
  }

  public override suspend fun insertTranslation(entity: TranslationHistoryEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfTranslationHistoryEntity.upsert(_connection, entity)
  }

  public override fun getRecentTranslations(): Flow<List<TranslationHistoryEntity>> {
    val _sql: String = "SELECT * FROM translation_history ORDER BY timestamp DESC LIMIT 20"
    return createFlow(__db, false, arrayOf("translation_history")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSourceText: Int = getColumnIndexOrThrow(_stmt, "sourceText")
        val _columnIndexOfTargetText: Int = getColumnIndexOrThrow(_stmt, "targetText")
        val _columnIndexOfSourceLang: Int = getColumnIndexOrThrow(_stmt, "sourceLang")
        val _columnIndexOfTargetLang: Int = getColumnIndexOrThrow(_stmt, "targetLang")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<TranslationHistoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TranslationHistoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSourceText: String
          _tmpSourceText = _stmt.getText(_columnIndexOfSourceText)
          val _tmpTargetText: String
          _tmpTargetText = _stmt.getText(_columnIndexOfTargetText)
          val _tmpSourceLang: String
          _tmpSourceLang = _stmt.getText(_columnIndexOfSourceLang)
          val _tmpTargetLang: String
          _tmpTargetLang = _stmt.getText(_columnIndexOfTargetLang)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              TranslationHistoryEntity(_tmpId,_tmpSourceText,_tmpTargetText,_tmpSourceLang,_tmpTargetLang,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearHistory() {
    val _sql: String = "DELETE FROM translation_history"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
