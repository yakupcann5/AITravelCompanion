package com.travel.`companion`.core.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.travel.`companion`.core.database.dao.CityDao
import com.travel.`companion`.core.database.dao.CityDao_Impl
import com.travel.`companion`.core.database.dao.GeneratedImageDao
import com.travel.`companion`.core.database.dao.GeneratedImageDao_Impl
import com.travel.`companion`.core.database.dao.TranslationHistoryDao
import com.travel.`companion`.core.database.dao.TranslationHistoryDao_Impl
import com.travel.`companion`.core.database.dao.TravelPlanDao
import com.travel.`companion`.core.database.dao.TravelPlanDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class TravelDatabase_Impl : TravelDatabase() {
  private val _cityDao: Lazy<CityDao> = lazy {
    CityDao_Impl(this)
  }

  private val _travelPlanDao: Lazy<TravelPlanDao> = lazy {
    TravelPlanDao_Impl(this)
  }

  private val _translationHistoryDao: Lazy<TranslationHistoryDao> = lazy {
    TranslationHistoryDao_Impl(this)
  }

  private val _generatedImageDao: Lazy<GeneratedImageDao> = lazy {
    GeneratedImageDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(4,
        "c6d49eb0444a057199098243b480edac", "b35755455a5da38711cf686947f6ae77") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `cities` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `country` TEXT NOT NULL, `description` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `tagsJson` TEXT NOT NULL, `isPopular` INTEGER NOT NULL, `searchedAt` INTEGER, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `travel_plans` (`id` TEXT NOT NULL, `cityId` TEXT NOT NULL, `cityName` TEXT NOT NULL, `budget` TEXT NOT NULL, `interests` TEXT NOT NULL, `daysJson` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `translation_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sourceText` TEXT NOT NULL, `targetText` TEXT NOT NULL, `sourceLang` TEXT NOT NULL, `targetLang` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `generated_images` (`id` TEXT NOT NULL, `prompt` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `isFavorite` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c6d49eb0444a057199098243b480edac')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `cities`")
        connection.execSQL("DROP TABLE IF EXISTS `travel_plans`")
        connection.execSQL("DROP TABLE IF EXISTS `translation_history`")
        connection.execSQL("DROP TABLE IF EXISTS `generated_images`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsCities: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCities.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("country", TableInfo.Column("country", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("latitude", TableInfo.Column("latitude", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("longitude", TableInfo.Column("longitude", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("tagsJson", TableInfo.Column("tagsJson", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("isPopular", TableInfo.Column("isPopular", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCities.put("searchedAt", TableInfo.Column("searchedAt", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCities: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCities: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCities: TableInfo = TableInfo("cities", _columnsCities, _foreignKeysCities,
            _indicesCities)
        val _existingCities: TableInfo = read(connection, "cities")
        if (!_infoCities.equals(_existingCities)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |cities(com.travel.companion.core.database.entity.CityEntity).
              | Expected:
              |""".trimMargin() + _infoCities + """
              |
              | Found:
              |""".trimMargin() + _existingCities)
        }
        val _columnsTravelPlans: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTravelPlans.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("cityId", TableInfo.Column("cityId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("cityName", TableInfo.Column("cityName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("budget", TableInfo.Column("budget", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("interests", TableInfo.Column("interests", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("daysJson", TableInfo.Column("daysJson", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTravelPlans.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTravelPlans: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTravelPlans: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTravelPlans: TableInfo = TableInfo("travel_plans", _columnsTravelPlans,
            _foreignKeysTravelPlans, _indicesTravelPlans)
        val _existingTravelPlans: TableInfo = read(connection, "travel_plans")
        if (!_infoTravelPlans.equals(_existingTravelPlans)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |travel_plans(com.travel.companion.core.database.entity.TravelPlanEntity).
              | Expected:
              |""".trimMargin() + _infoTravelPlans + """
              |
              | Found:
              |""".trimMargin() + _existingTravelPlans)
        }
        val _columnsTranslationHistory: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTranslationHistory.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslationHistory.put("sourceText", TableInfo.Column("sourceText", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslationHistory.put("targetText", TableInfo.Column("targetText", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslationHistory.put("sourceLang", TableInfo.Column("sourceLang", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslationHistory.put("targetLang", TableInfo.Column("targetLang", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTranslationHistory.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTranslationHistory: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTranslationHistory: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTranslationHistory: TableInfo = TableInfo("translation_history",
            _columnsTranslationHistory, _foreignKeysTranslationHistory, _indicesTranslationHistory)
        val _existingTranslationHistory: TableInfo = read(connection, "translation_history")
        if (!_infoTranslationHistory.equals(_existingTranslationHistory)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |translation_history(com.travel.companion.core.database.entity.TranslationHistoryEntity).
              | Expected:
              |""".trimMargin() + _infoTranslationHistory + """
              |
              | Found:
              |""".trimMargin() + _existingTranslationHistory)
        }
        val _columnsGeneratedImages: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsGeneratedImages.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGeneratedImages.put("prompt", TableInfo.Column("prompt", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGeneratedImages.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsGeneratedImages.put("isFavorite", TableInfo.Column("isFavorite", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsGeneratedImages.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysGeneratedImages: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesGeneratedImages: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoGeneratedImages: TableInfo = TableInfo("generated_images", _columnsGeneratedImages,
            _foreignKeysGeneratedImages, _indicesGeneratedImages)
        val _existingGeneratedImages: TableInfo = read(connection, "generated_images")
        if (!_infoGeneratedImages.equals(_existingGeneratedImages)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |generated_images(com.travel.companion.core.database.entity.GeneratedImageEntity).
              | Expected:
              |""".trimMargin() + _infoGeneratedImages + """
              |
              | Found:
              |""".trimMargin() + _existingGeneratedImages)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "cities", "travel_plans",
        "translation_history", "generated_images")
  }

  public override fun clearAllTables() {
    super.performClear(false, "cities", "travel_plans", "translation_history", "generated_images")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(CityDao::class, CityDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TravelPlanDao::class, TravelPlanDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TranslationHistoryDao::class,
        TranslationHistoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(GeneratedImageDao::class, GeneratedImageDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun cityDao(): CityDao = _cityDao.value

  public override fun travelPlanDao(): TravelPlanDao = _travelPlanDao.value

  public override fun translationHistoryDao(): TranslationHistoryDao = _translationHistoryDao.value

  public override fun generatedImageDao(): GeneratedImageDao = _generatedImageDao.value
}
