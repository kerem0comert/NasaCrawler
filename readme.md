# NasaCrawler

Bu uygulama, Appcent'in değerlendirmesi için, verilen spesifikasyon doğrultusunda geliştirilmiştir. [NASA API](https://api.nasa.gov/) fotoğrafların veri kaynağı olarak kullanıldı.

Geliştirme boyunca, Android SDK'sının önerdiği en son kütüphaneleri kullanmaya ve Material Design kurallarına riayet etmeye çalıştım. Mimari olarak MVVM kullanılmıştır. Uygulamada aşağıdaki yapılar mevcuttur:

- HTTP Client olarak **Retrofit2**
- Pagination için **Paging3** ve muhtelif data state'lerinin UI'da yönetimi için bu kütüphanede bulunan **LoadStateAdapter**
- Asenkron fotoğraf indirimi ve medya yönetimi için **Glide**
- Dependency injection için **Hilt**
- Uygulama içi veri muhafazası için **LiveData**
- Dökümantasyon için **Dokka**
- Fragmentlar arası navigasyon için **Navigation Component**

## Notlar

- Dökümantasyona kök dizininde bulunan "dokka" dosyası aracılığı ile ulaşabilirsiniz.
- Spesifikasyonda fotoğraf detaylarının bir pop-up'da açılması istenmesine rağmen, ben yeni bir Fragment'ta göstererek, Navigation Component yapısını da uygulamaya entegre etmek istedim.
- CollectionView, Native Android Geliştirme'nin bir component'i olmadığı için, yerine muadili olan RecyclerView kullanılmıştır.
- API Key bu demo için **gradle.properties**'de mevcut olmasına rağmen, reel bir senaryoda key'in bulunduğu bu dosyanın **.gitignore**'a eklenmesi daha sağlıklı olacaktır.
