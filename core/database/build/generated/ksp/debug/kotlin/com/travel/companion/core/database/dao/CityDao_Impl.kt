package com.travel.`companion`.core.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.travel.`companion`.core.database.entity.CityEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
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
public class CityDao_Impl(
  __db: RoomDatabase,
) : CityDao {
  private val __db: RoomDatabase

  private val __upsertAdapterOfCityEntity: EntityUpsertAdapter<CityEntity>
  init {
    this.__db = __db
    this.__upsertAdapterOfCityEntity = EntityUpsertAdapter<CityEntity>(object :
        EntityInsertAdapter<CityEntity>() {
      protected override fun createQuery(): String =
          "INSERT INTO `cities` (`id`,`name`,`country`,`description`,`imageUrl`,`latitude`,`longitude`,`tagsJson`,`isPopular`,`searchedAt`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CityEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.country)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.imageUrl)
        statement.bindDouble(6, entity.latitude)
        statement.bindDouble(7, entity.longitude)
        statement.bindText(8, entity.tagsJson)
        val _tmp: Int = if (entity.isPopular) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        val _tmpSearchedAt: Long? = entity.searchedAt
        if (_tmpSearchedAt == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpSearchedAt)
        }
      }
    }, object : EntityDeleteOrUpdateAdapter<CityEntity>() {
      protected override fun createQuery(): String =
          "UPDATE `cities` SET `id` = ?,`name` = ?,`country` = ?,`description` = ?,`imageUrl` = ?,`latitude` = ?,`longitude` = ?,`tagsJson` = ?,`isPopular` = ?,`searchedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CityEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.country)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.imageUrl)
        statement.bindDouble(6, entity.latitude)
        statement.bindDouble(7, entity.longitude)
        statement.bindText(8, entity.tagsJson)
        val _tmp: Int = if (entity.isPopular) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        val _tmpSearchedAt: Long? = entity.searchedAt
        if (_tmpSearchedAt == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpSearchedAt)
        }
        statement.bindText(11, entity.id)
      }
    })
  }

  public override suspend fun upsertCity(city: CityEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __upsertAdapterOfCityEntity.upsert(_connection, city)
  }

  public override suspend fun upsertCities(cities: List<CityEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __upsertAdapterOfCityEntity.upsert(_connection, cities)
  }

  public override fun getPopularCities(): Flow<List<CityEntity>> {
    val _sql: String = "SELECT * FROM cities WHERE isPopular = 1"
    return createFlow(__db, false, arrayOf("cities")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCountry: Int = getColumnIndexOrThrow(_stmt, "country")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTagsJson: Int = getColumnIndexOrThrow(_stmt, "tagsJson")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfSearchedAt: Int = getColumnIndexOrThrow(_stmt, "searchedAt")
        val _result: MutableList<CityEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CityEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCountry: String
          _tmpCountry = _stmt.getText(_columnIndexOfCountry)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTagsJson: String
          _tmpTagsJson = _stmt.getText(_columnIndexOfTagsJson)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpSearchedAt: Long?
          if (_stmt.isNull(_columnIndexOfSearchedAt)) {
            _tmpSearchedAt = null
          } else {
            _tmpSearchedAt = _stmt.getLong(_columnIndexOfSearchedAt)
          }
          _item =
              CityEntity(_tmpId,_tmpName,_tmpCountry,_tmpDescription,_tmpImageUrl,_tmpLatitude,_tmpLongitude,_tmpTagsJson,_tmpIsPopular,_tmpSearchedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getRecentSearches(): Flow<List<CityEntity>> {
    val _sql: String =
        "SELECT * FROM cities WHERE searchedAt IS NOT NULL ORDER BY searchedAt DESC LIMIT 10"
    return createFlow(__db, false, arrayOf("cities")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCountry: Int = getColumnIndexOrThrow(_stmt, "country")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTagsJson: Int = getColumnIndexOrThrow(_stmt, "tagsJson")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfSearchedAt: Int = getColumnIndexOrThrow(_stmt, "searchedAt")
        val _result: MutableList<CityEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CityEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCountry: String
          _tmpCountry = _stmt.getText(_columnIndexOfCountry)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTagsJson: String
          _tmpTagsJson = _stmt.getText(_columnIndexOfTagsJson)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpSearchedAt: Long?
          if (_stmt.isNull(_columnIndexOfSearchedAt)) {
            _tmpSearchedAt = null
          } else {
            _tmpSearchedAt = _stmt.getLong(_columnIndexOfSearchedAt)
          }
          _item =
              CityEntity(_tmpId,_tmpName,_tmpCountry,_tmpDescription,_tmpImageUrl,_tmpLatitude,_tmpLongitude,_tmpTagsJson,_tmpIsPopular,_tmpSearchedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCity(id: String): Flow<CityEntity?> {
    val _sql: String = "SELECT * FROM cities WHERE id = ?"
    return createFlow(__db, false, arrayOf("cities")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCountry: Int = getColumnIndexOrThrow(_stmt, "country")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTagsJson: Int = getColumnIndexOrThrow(_stmt, "tagsJson")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfSearchedAt: Int = getColumnIndexOrThrow(_stmt, "searchedAt")
        val _result: CityEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCountry: String
          _tmpCountry = _stmt.getText(_columnIndexOfCountry)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTagsJson: String
          _tmpTagsJson = _stmt.getText(_columnIndexOfTagsJson)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpSearchedAt: Long?
          if (_stmt.isNull(_columnIndexOfSearchedAt)) {
            _tmpSearchedAt = null
          } else {
            _tmpSearchedAt = _stmt.getLong(_columnIndexOfSearchedAt)
          }
          _result =
              CityEntity(_tmpId,_tmpName,_tmpCountry,_tmpDescription,_tmpImageUrl,_tmpLatitude,_tmpLongitude,_tmpTagsJson,_tmpIsPopular,_tmpSearchedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchCities(query: String): Flow<List<CityEntity>> {
    val _sql: String =
        "SELECT * FROM cities WHERE name LIKE '%' || ? || '%' OR country LIKE '%' || ? || '%' COLLATE NOCASE"
    return createFlow(__db, false, arrayOf("cities")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCountry: Int = getColumnIndexOrThrow(_stmt, "country")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTagsJson: Int = getColumnIndexOrThrow(_stmt, "tagsJson")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfSearchedAt: Int = getColumnIndexOrThrow(_stmt, "searchedAt")
        val _result: MutableList<CityEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CityEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCountry: String
          _tmpCountry = _stmt.getText(_columnIndexOfCountry)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTagsJson: String
          _tmpTagsJson = _stmt.getText(_columnIndexOfTagsJson)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpSearchedAt: Long?
          if (_stmt.isNull(_columnIndexOfSearchedAt)) {
            _tmpSearchedAt = null
          } else {
            _tmpSearchedAt = _stmt.getLong(_columnIndexOfSearchedAt)
          }
          _item =
              CityEntity(_tmpId,_tmpName,_tmpCountry,_tmpDescription,_tmpImageUrl,_tmpLatitude,_tmpLongitude,_tmpTagsJson,_tmpIsPopular,_tmpSearchedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCityByName(name: String): CityEntity? {
    val _sql: String = "SELECT * FROM cities WHERE LOWER(name) = LOWER(?) LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCountry: Int = getColumnIndexOrThrow(_stmt, "country")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTagsJson: Int = getColumnIndexOrThrow(_stmt, "tagsJson")
        val _columnIndexOfIsPopular: Int = getColumnIndexOrThrow(_stmt, "isPopular")
        val _columnIndexOfSearchedAt: Int = getColumnIndexOrThrow(_stmt, "searchedAt")
        val _result: CityEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCountry: String
          _tmpCountry = _stmt.getText(_columnIndexOfCountry)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTagsJson: String
          _tmpTagsJson = _stmt.getText(_columnIndexOfTagsJson)
          val _tmpIsPopular: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPopular).toInt()
          _tmpIsPopular = _tmp != 0
          val _tmpSearchedAt: Long?
          if (_stmt.isNull(_columnIndexOfSearchedAt)) {
            _tmpSearchedAt = null
          } else {
            _tmpSearchedAt = _stmt.getLong(_columnIndexOfSearchedAt)
          }
          _result =
              CityEntity(_tmpId,_tmpName,_tmpCountry,_tmpDescription,_tmpImageUrl,_tmpLatitude,_tmpLongitude,_tmpTagsJson,_tmpIsPopular,_tmpSearchedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPopularCityCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM cities WHERE isPopular = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
