# AI Integration Checklist Command

AI entegrasyonu ile ilgili kodları kontrol et.

## 1. Abstraction Layer Kontrolü
- [ ] AI çağrıları doğrudan ML Kit / Firebase'e mi gidiyor? (YANLIŞ)
- [ ] AI çağrıları :core:ai abstraction layer üzerinden mi? (DOĞRU)
- [ ] Interface tanımlı mı? (AiTextService, AiPlannerService, AiImageService)
- [ ] On-device ve cloud implementation'ları ayrı mı?

## 2. Gemini Nano (On-Device) Kontrolleri
- [ ] Feature availability check yapılıyor mu?
- [ ] Model indirme durumu kullanıcıya gösteriliyor mu?
- [ ] Cihaz desteklemiyorsa graceful fallback var mı?
- [ ] SDK 31+ kontrolü yapılıyor mu?

## 3. Firebase AI Logic (Cloud) Kontrolleri
- [ ] Firebase App Check aktif mi?
- [ ] Server-side prompt template kullanılıyor mu? (hassas prompt'lar için)
- [ ] Function calling response'u doğru parse ediliyor mu?
- [ ] Rate limiting / quota aşımı handle ediliyor mu?
- [ ] Remote Config ile model adı yönetiliyor mu?

## 4. Hybrid Strategy Kontrolü
- [ ] HybridAiService on-device first, cloud fallback yapıyor mu?
- [ ] Fallback sırasında kullanıcı bilgilendiriliyor mu?
- [ ] Network yokken sadece on-device çalışıyor mu?
- [ ] Her iki path de test edilmiş mi?

## 5. Image Generation Kontrolleri
- [ ] Nano Banana / Imagen prompt injection koruması var mı?
- [ ] Generated image cache'leniyor mu?
- [ ] Büyük bitmap'ler için memory management yapılıyor mu?
- [ ] Image oluşturma süresi kullanıcıya gösteriliyor mu? (progress)

## 6. Voice (Gemini Live API) Kontrolleri
- [ ] RECORD_AUDIO permission doğru isteniyor mu?
- [ ] Audio session yönetimi doğru mu?
- [ ] Permission denied durumu handle ediliyor mu?
- [ ] Konuşma kesintileri (interruption) yönetiliyor mu?

## 7. Test Kontrolleri
- [ ] FakeAiTextService, FakeAiPlannerService mevcut mu?
- [ ] On-device unavailable senaryosu test edilmiş mi?
- [ ] Cloud error senaryosu test edilmiş mi?
- [ ] Fallback geçişi test edilmiş mi?

Eksik olan maddeleri listele ve düzeltme öner.
