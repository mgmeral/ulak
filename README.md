# Teams Webhook Yönetimi (Servlet Tabanlı)

Bu uygulama, Microsoft Teams için birden fazla kanal adına webhook URL'lerini tanımlamanı, listelemeni ve güncellemeni sağlar. Java Servlet teknolojisi kullanılarak geliştirilmiştir.

## 🔧 Özellikler

* Kanal adına özel Webhook URL kaydı (POST)
* Maskelenmiş olarak listeleme (GET)
* Web üzerinden düzenleme arayüzü (HTML + JS)
* Basit hafızada tutulan `WebhookUrlCache` (ornek amaçlı)

---

## 🚀 Çalıştırmak İçin

### 1. Maven ile Derleme

```bash
mvn clean package
```

### 2. Servlet Container (Tomcat, Jetty) ile Çalıştırma

`.war` dosyasını `target/` altında bul ve Tomcat’in `webapps/` klasörünü kopyala.

---

## 🌐 Kullanım

### 🔹 Webhook Tanımlama Sayfası

```
http://localhost:8080/ulak/set-webhook.html
```

İki alan var:

* Kanal Adı
* Teams Webhook URL

### 🔹 Tanımlı Webhookları Listeleme ve Güncelleme

```
http://localhost:8080/ulak/list-webhooks.html
```

* Tabloda tüm kayıtlı webhook’lar maskelenmiş şekilde gösterilir.
* Her satırda “Düzenle” butonu bulunur.
* URL güncellenebilir, isim sabittir.

---

## 🧹 Jira Webhook Konfigürasyonu

Jira olaylarını Microsoft Teams kanalına göndermek için:

1. Jira'da **Ayarlar > Sistem > Webhooks** yoluna git.
2. **Yeni Webhook Oluştur** seçeneğine tıkla.
3. İsim ver ("Notify Teams" gibi).
4. **URL** alanına uygulamanın şu adresini gir:

   ```
   https://<senin-domainin-veya-ngrok>/jira-webhook?name=<kanalAdi>
   ```

   * `name` parametresi, UI üzerinden tanımladığın kanal adıyla eşleşmelidir.
5. Dinlemek istediğin event’leri seç ("Issue Created", "Issue Updated" vs).
6. (Opsiyonel) Belirli bir filtre eklemek istersen JQL yaz:

   ```
   issuetype = Epic AND summary ~ "Deneme"
   ```

   veya bir epice bağlı alt işleri dinlemek için:

   ```
   parent = PROJ-123
   ```

> 💡 **İpucu**: Farklı Teams kanalları için birden fazla Jira webhook'u oluşturabilir ve her birini farklı `name` değeriyle servlet'e yönlendirebilirsin.

### Lokalde ngrok ile test etmek için:

```bash
ngrok http 8080
```

Jira webhook URL’sini şu şekilde tanımla:

```
https://<ngrok-id>.ngrok-free.app/jira-webhook?name=myChannel
```

---

## 🔐 Güvenlik Notu

Bu uygulama demo amaçlıdır:

* Webhook URL’leri bellekte tutulur (restart sonrası kaybolur).
* Kimlik doğrulama veya veri kalıcılığı yoktur.
* Üretim ortamında bir veritabanı ve kullanıcı yönetimi önerilir.

---

## ✍️ Geliştiren

**[@mgmeral](https://github.com/mgmeral)**
Takım içi hızlı webhook yönetimi ve Jira–Teams entegrasyonu için demo uygulama.
