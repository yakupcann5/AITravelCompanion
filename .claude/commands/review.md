# Code Review Command

Son yaptığım değişiklikleri proje standartlarına göre detaylı review et.

## Review Kriterleri

### 1. MVI Architecture Compliance ✅
- [ ] Intent/State/Effect sealed class'ları mevcut mu?
- [ ] State immutable mi? (data class, val only, copy() ile güncelleme)
- [ ] State güncellemesi sadece reduce() üzerinden mi?
- [ ] Her kullanıcı aksiyonu bir Intent'e mi map ediyor?
- [ ] Effect'ler one-shot event'ler için mi kullanılıyor?
- [ ] Screen composable sadece state + onIntent alıyor mu? (ViewModel referansı yok)

### 2. Module & Dependency Rules ✅
- [ ] Feature modülü doğru yapıda mı? (api + impl)
- [ ] core:model pure Kotlin mi? (android.* import yok)
- [ ] Feature → Feature:impl bağımlılığı yok mu?
- [ ] core → feature bağımlılığı yok mu?
- [ ] AI çağrıları :core:ai üzerinden mi?

### 3. Modern Stack Compliance ✅
- [ ] Jetpack Compose only? (XML layout yok)
- [ ] Navigation 3 kullanılmış mı? (NavDisplay, user-owned back stack)
- [ ] Ktor Client kullanılmış mı? (Retrofit değil)
- [ ] Coil 3 kullanılmış mı? (AsyncImage)
- [ ] Material 3 Expressive bileşenler kullanılmış mı?

### 4. Concurrency & State Management ✅
- [ ] Coroutines kullanılmış mı? (Callback değil)
- [ ] StateFlow kullanılmış mı? (LiveData değil)
- [ ] viewModelScope kullanılmış mı?
- [ ] CancellationException doğru handle ediliyor mu?
- [ ] Dispatcher injection yapılmış mı?

### 5. Compose & UI Quality ✅
- [ ] State hoisting uygulanmış mı?
- [ ] remember/derivedStateOf doğru kullanılmış mı?
- [ ] Modifier parametresi var mı? (Modifier = Modifier)
- [ ] Adaptive layout: WindowSizeClass kullanılmış mı?
- [ ] Accessibility (contentDescription, 48dp touch targets)
- [ ] Preview fonksiyonları var mı?

### 6. Code Quality (DRY & SOLID) ✅
- [ ] DRY prensibi uygulanmış mı?
- [ ] SOLID prensipleri uygulanmış mı?
- [ ] Immutability: Yeni obje mi oluşturuluyor? (mutation yok)
- [ ] Memory leak riski var mı?
- [ ] Fonksiyon < 50 satır mı?
- [ ] Dosya < 800 satır mı?

### 7. AI Integration ✅
- [ ] AI çağrıları abstraction layer üzerinden mi?
- [ ] On-device availability check yapılıyor mu?
- [ ] Cloud fallback mevcut mu?
- [ ] AI model indirme durumu UI'da gösteriliyor mu?
- [ ] Firebase App Check aktif mi?

### 8. Testing & Security ✅
- [ ] ViewModel unit test var mı?
- [ ] UseCase test var mı?
- [ ] Hardcoded secret var mı?
- [ ] API key local.properties'de mi?
- [ ] Error handling doğru mu?

### 9. Dokümantasyon ✅
- [ ] Class header @author tag'i var mı?
- [ ] Tarih doğru mu?
- [ ] Karmaşık fonksiyonlarda Türkçe yorum var mı?

## Review Formatı

Her kriter için:
- ✅ İyi: Ne yapılmış
- ⚠️ Uyarı: Potansiyel sorun
- ❌ Hata: Düzeltilmesi gereken

## Sonuç

Review sonunda:
1. Toplam skor ver (X/9)
2. Kritik sorunları listele
3. İyileştirme önerileri sun
4. Onay ver veya düzeltme iste

Lütfen detaylı ve yapıcı bir review yap.
