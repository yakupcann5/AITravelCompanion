# Check Rules Command

Lütfen proje kurallarını kontrol et ve eksik varsa uyar:

## 1. MVI Pattern Kuralları
- [ ] Her ekran için Intent/State/Effect sealed class'ları var mı?
- [ ] State immutable mi? (data class, val only)
- [ ] State güncellemesi sadece reduce() ile mi?
- [ ] Screen composable: state + onIntent parametreleri (ViewModel referansı yok)
- [ ] Effect'ler LaunchedEffect içinde collect ediliyor mu?

## 2. Modül Kuralları
- [ ] Feature modülü api + impl yapısında mı?
- [ ] core:model'de android.* import yok mu?
- [ ] Feature → Feature:impl bağımlılığı yok mu?
- [ ] AI çağrıları :core:ai abstraction üzerinden mi?

## 3. Teknoloji Stack Kuralları
- [ ] Compose only? (XML layout yok)
- [ ] Navigation 3? (NavHost/NavController yok)
- [ ] Ktor Client? (Retrofit yok)
- [ ] Coil 3? (Glide yok)
- [ ] Material 3 Expressive?

## 4. Kod Kalite Kuralları
- [ ] Immutability: mutation yok, yeni obje oluşturuluyor
- [ ] Coroutines + StateFlow kullanılıyor (Callback/LiveData değil)
- [ ] Dispatcher injection yapılmış
- [ ] Nested if/for yok (max 4 seviye)
- [ ] Fonksiyon < 50 satır, dosya < 800 satır
- [ ] Deprecated API yok

## 5. Adaptive Layout Kuralları
- [ ] WindowSizeClass kullanılıyor mu?
- [ ] Compact: BottomNav + single pane
- [ ] Expanded: NavigationRail + ListDetailPaneScaffold

## 6. Build & Dokümantasyon
- [ ] `./gradlew assembleDebug` başarılı mı?
- [ ] @author tag'i var mı?
- [ ] Tarih doğru mu?
- [ ] Karmaşık fonksiyonlarda Türkçe yorum var mı?

## 7. Security
- [ ] Hardcoded secret yok mu?
- [ ] API key local.properties'de mi?
- [ ] Firebase App Check aktif mi?

## 8. Test
- [ ] ViewModel unit test var mı?
- [ ] Intent → State dönüşüm testleri var mı?
- [ ] Coverage %70+ mı?

Lütfen yukarıdaki konuları tek tek kontrol et ve eksik varsa uyar.
