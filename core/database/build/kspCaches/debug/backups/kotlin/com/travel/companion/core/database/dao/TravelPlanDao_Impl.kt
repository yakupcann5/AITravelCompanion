package com.travel.`companion`.core.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.travel.`companion`.core.database.entity.TravelPlanEntity
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
public class TravelPlanDao_Impl(
  __db: RoomDatabase,
) : TravelPlanDao {
  private val __db: RoomDatabase

  private val __upsertAdapterOfTravelPlanEntity: EntityUpsertAdapter<TravelPlanEntity>
  init {
    this.__db = __db
    this.__upsertAdapterOfTravelPlanEntity = EntityUpsertAdapter<TravelPlanEntity>(object :
        EntityInsertAdapter<TravelPlanEntity>() {
      protected override fun createQuery(): String =
          "INSERT INTO `travel_plans` (`id`,`cityId`,`cityName`,`budget`,`interests`,`daysJson`,`createdAt`) VALUES (?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TravelPlanEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.cityId)
        statement.bindText(3, entity.cityName)
        statement.bindText(4, entity.budget)
        statement.bindText(5, entity.interests)
        statement.bindText(6, entity.daysJson)
        statement.bindLong(7, entity.createdAt)
      }
    }, object : EntityDeleteOrUpdateAdapter<TravelPlanEntity>() {
      protected override fun createQuery(): String =
          "UPDATE `travel_plans` SET `id` = ?,`cityId` = ?,`cityName` = ?,`budget` = ?,`interests` = ?,`daysJson` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TravelPlanEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.cityId)
        statement.bindText(3, entity.cityName)
        statement.bindText(4, entity.budget)
        statement.bindText(5, entity.interests)
        statement.bindText(6, entity.daysJson)
        statement.bindLong(7, entity.createdAt)
        statement.bindText(8, entity.id)
      }
    })
  }

  public override suspend fun upsertTravelPlan(plan: TravelPlanEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfTravelPlanEntity.upsert(_connection, plan)
  }

  public override fun getAllTravelPlans(): Flow<List<TravelPlanEntity>> {
    val _sql: String = "SELECT * FROM travel_plans ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("travel_plans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCityId: Int = getColumnIndexOrThrow(_stmt, "cityId")
        val _columnIndexOfCityName: Int = getColumnIndexOrThrow(_stmt, "cityName")
        val _columnIndexOfBudget: Int = getColumnIndexOrThrow(_stmt, "budget")
        val _columnIndexOfInterests: Int = getColumnIndexOrThrow(_stmt, "interests")
        val _columnIndexOfDaysJson: Int = getColumnIndexOrThrow(_stmt, "daysJson")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<TravelPlanEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TravelPlanEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpCityId: String
          _tmpCityId = _stmt.getText(_columnIndexOfCityId)
          val _tmpCityName: String
          _tmpCityName = _stmt.getText(_columnIndexOfCityName)
          val _tmpBudget: String
          _tmpBudget = _stmt.getText(_columnIndexOfBudget)
          val _tmpInterests: String
          _tmpInterests = _stmt.getText(_columnIndexOfInterests)
          val _tmpDaysJson: String
          _tmpDaysJson = _stmt.getText(_columnIndexOfDaysJson)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              TravelPlanEntity(_tmpId,_tmpCityId,_tmpCityName,_tmpBudget,_tmpInterests,_tmpDaysJson,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTravelPlan(id: String): Flow<TravelPlanEntity> {
    val _sql: String = "SELECT * FROM travel_plans WHERE id = ?"
    return createFlow(__db, false, arrayOf("travel_plans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCityId: Int = getColumnIndexOrThrow(_stmt, "cityId")
        val _columnIndexOfCityName: Int = getColumnIndexOrThrow(_stmt, "cityName")
        val _columnIndexOfBudget: Int = getColumnIndexOrThrow(_stmt, "budget")
        val _columnIndexOfInterests: Int = getColumnIndexOrThrow(_stmt, "interests")
        val _columnIndexOfDaysJson: Int = getColumnIndexOrThrow(_stmt, "daysJson")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: TravelPlanEntity
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpCityId: String
          _tmpCityId = _stmt.getText(_columnIndexOfCityId)
          val _tmpCityName: String
          _tmpCityName = _stmt.getText(_columnIndexOfCityName)
          val _tmpBudget: String
          _tmpBudget = _stmt.getText(_columnIndexOfBudget)
          val _tmpInterests: String
          _tmpInterests = _stmt.getText(_columnIndexOfInterests)
          val _tmpDaysJson: String
          _tmpDaysJson = _stmt.getText(_columnIndexOfDaysJson)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result =
              TravelPlanEntity(_tmpId,_tmpCityId,_tmpCityName,_tmpBudget,_tmpInterests,_tmpDaysJson,_tmpCreatedAt)
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type <com.travel.`companion`.core.database.entity.TravelPlanEntity>.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteTravelPlan(id: String) {
    val _sql: String = "DELETE FROM travel_plans WHERE id = ?"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
