package com.travel.`companion`.core.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.travel.`companion`.core.database.entity.GeneratedImageEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class GeneratedImageDao_Impl(
  __db: RoomDatabase,
) : GeneratedImageDao {
  private val __db: RoomDatabase

  private val __upsertAdapterOfGeneratedImageEntity: EntityUpsertAdapter<GeneratedImageEntity>
  init {
    this.__db = __db
    this.__upsertAdapterOfGeneratedImageEntity = EntityUpsertAdapter<GeneratedImageEntity>(object :
        EntityInsertAdapter<GeneratedImageEntity>() {
      protected override fun createQuery(): String =
          "INSERT INTO `generated_images` (`id`,`prompt`,`imageUrl`,`isFavorite`,`createdAt`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: GeneratedImageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.prompt)
        statement.bindText(3, entity.imageUrl)
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.createdAt)
      }
    }, object : EntityDeleteOrUpdateAdapter<GeneratedImageEntity>() {
      protected override fun createQuery(): String =
          "UPDATE `generated_images` SET `id` = ?,`prompt` = ?,`imageUrl` = ?,`isFavorite` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: GeneratedImageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.prompt)
        statement.bindText(3, entity.imageUrl)
        val _tmp: Int = if (entity.isFavorite) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.createdAt)
        statement.bindText(6, entity.id)
      }
    })
  }

  public override suspend fun upsertImage(entity: GeneratedImageEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfGeneratedImageEntity.upsert(_connection, entity)
  }

  public override fun getAllImages(): Flow<List<GeneratedImageEntity>> {
    val _sql: String = "SELECT * FROM generated_images ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("generated_images")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPrompt: Int = getColumnIndexOrThrow(_stmt, "prompt")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsFavorite: Int = getColumnIndexOrThrow(_stmt, "isFavorite")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<GeneratedImageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: GeneratedImageEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpPrompt: String
          _tmpPrompt = _stmt.getText(_columnIndexOfPrompt)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpIsFavorite: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFavorite).toInt()
          _tmpIsFavorite = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = GeneratedImageEntity(_tmpId,_tmpPrompt,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteImage(id: String) {
    val _sql: String = "DELETE FROM generated_images WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
    val _sql: String = "UPDATE generated_images SET isFavorite = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isFavorite) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
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
