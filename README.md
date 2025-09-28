# Teams Webhook Yönetimi (Servlet Tabanlı)

Bu uygulama, Microsoft Teams için birden fazla kanal adına webhook URL'lerini tanımlamanı, listelemeni ve güncellemeni sağlar. Java Servlet teknolojisi kullanılarak geliştirilmiştir.

## 🔧 Özellikler

- Kanal adına özel Webhook URL kaydı (POST)
- Maskelenmiş olarak listeleme (GET)
- Web üzerinden düzenleme arayüzü (HTML + JS)
- Basit hafızada tutulan `WebhookUrlCache` (örnek amaçlı)

---

## 🚀 Çalıştırmak İçin

### 1. Maven ile Derleme
```bash
mvn clean package
```

### 2. Servlet Container (Tomcat, Jetty) ile Çalıştırma
`.war` dosyasını `target/` altında bul ve Tomcat’in `webapps/` klasörüne kopyala.

---

## 🌐 Kullanım

### 🔹 Webhook Tanımlama Sayfası
```
http://localhost:8080/ulak/set-webhook.html
```
İki alan var:
- Kanal Adı
- Teams Webhook URL

### 🔹 Tanımlı Webhookları Listeleme ve Güncelleme
```
http://localhost:8080/ulak/list-webhooks.html
```
- Tabloda tüm kayıtlı webhook’lar maskelenmiş şekilde gösterilir.
- Her satırda “Düzenle” butonu bulunur.
- URL güncellenebilir, isim sabittir.

---

## 🔒 Güvenlik Notu

Bu uygulama demo amaçlıdır:
- Webhook URL’leri bellekte tutulur (restart sonrası kaybolur).
- Kimlik doğrulama veya veri kalıcılığı yoktur.
- Üretim ortamında bir veritabanı ve kullanıcı yönetimi önerilir.

---

## ✍️ Geliştiren

**[@mgmeral](https://github.com/mgmeral)**  
İçin: Takım içi hızlı webhook yönetimi, demo amaçlı Java EE uygulaması.
