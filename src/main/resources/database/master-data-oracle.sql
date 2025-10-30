
-- 1. YAKINLIK KODLARI (MERNİS Standartları)
-- =========================================================================
INSERT INTO yakinlik_kodu (kod, adi, aciklama, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT '01', 'Kendisi', 'Kendisi', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '01')
UNION ALL SELECT '02', 'Eşi', 'Eşi', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '02')
UNION ALL SELECT '03', 'Annesi', 'Annesi', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '03')
UNION ALL SELECT '04', 'Babası', 'Babası', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '04')
UNION ALL SELECT '05', 'Oğlu', 'Oğlu', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '05')
UNION ALL SELECT '06', 'Kızı', 'Kızı', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '06')
UNION ALL SELECT '07', 'Kardeşi', 'Kardeşi', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '07')
UNION ALL SELECT '08', 'Büyükanne', 'Büyükanne', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '08')
UNION ALL SELECT '09', 'Büyükbaba', 'Büyükbaba', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '09')
UNION ALL SELECT '10', 'Torun', 'Torun', 10, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '10')
UNION ALL SELECT '11', 'Gelin', 'Gelin', 11, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '11')
UNION ALL SELECT '12', 'Damat', 'Damat', 12, 1, SYSDATE, 'SYSTEM' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM yakinlik_kodu WHERE kod = '12');

-- 2. MESLEK TANIMLARI
-- =========================================================================
INSERT INTO meslek (kod, adi, aktif, olusturma_tarihi, olusturan)
SELECT 'M001', 'İşsiz', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M001')
UNION ALL SELECT 'M002', 'İşçi', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M002')
UNION ALL SELECT 'M003', 'Memur', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M003')
UNION ALL SELECT 'M004', 'Öğretmen', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M004')
UNION ALL SELECT 'M005', 'Doktor', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M005')
UNION ALL SELECT 'M006', 'Hemşire', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M006')
UNION ALL SELECT 'M007', 'Mühendis', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M007')
UNION ALL SELECT 'M008', 'Serbest Meslek', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M008')
UNION ALL SELECT 'M009', 'Esnaf', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M009')
UNION ALL SELECT 'M010', 'Emekli', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M010')
UNION ALL SELECT 'M011', 'Ev Hanımı', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M011')
UNION ALL SELECT 'M012', 'Öğrenci', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M012')
UNION ALL SELECT 'M013', 'Çiftçi', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M013')
UNION ALL SELECT 'M014', 'Şoför', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M014')
UNION ALL SELECT 'M015', 'Güvenlik Görevlisi', 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM meslek WHERE kod = 'M015');

-- 3. ÖZEL STATÜ TANIMLARI
-- =========================================================================
INSERT INTO ozel_statu (kod, adi, aciklama, oncelik_puani, aktif, olusturma_tarihi, olusturan)
SELECT 'OS001', 'Şehit Yakını', 'Şehit ailesi', 100, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS001')
UNION ALL SELECT 'OS002', 'Gazi Yakını', 'Gazi ailesi', 90, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS002')
UNION ALL SELECT 'OS003', 'Yetim', 'Anne veya baba kaybı', 80, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS003')
UNION ALL SELECT 'OS004', 'Öksüz', 'Anne ve baba kaybı', 85, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS004')
UNION ALL SELECT 'OS005', 'Dul', 'Eş kaybı', 70, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS005')
UNION ALL SELECT 'OS006', 'Koruma Altında', 'ASPB koruma altında', 60, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS006')
UNION ALL SELECT 'OS007', 'Mülteci', 'Geçici koruma statüsü', 50, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM ozel_statu WHERE kod = 'OS007');

-- 4. ENGELLİ TİPİ TANIMLARI (Ana Tipler + Alt Tipler)
-- =========================================================================
-- Ana Tipler
INSERT INTO engelli_tipi (kod, adi, ust_tip_id, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'ET001', 'Bedensel Engelli', NULL, 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET001')
UNION ALL SELECT 'ET002', 'Görme Engelli', NULL, 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET002')
UNION ALL SELECT 'ET003', 'İşitme Engelli', NULL, 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET003')
UNION ALL SELECT 'ET004', 'Zihinsel Engelli', NULL, 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET004')
UNION ALL SELECT 'ET005', 'Ruhsal/Psikolojik Engelli', NULL, 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET005')
UNION ALL SELECT 'ET006', 'Süreğen Hastalık', NULL, 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET006');

-- Alt Tipler
INSERT INTO engelli_tipi (kod, adi, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'ET001-01', 'Ortopedik Engelli', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET001-01')
UNION ALL SELECT 'ET001-02', 'Omurilik Felci', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET001-02')
UNION ALL SELECT 'ET002-01', 'Az Gören', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET002-01')
UNION ALL SELECT 'ET002-02', 'Tamamen Görme Engelli', 10, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET002-02')
UNION ALL SELECT 'ET003-01', 'Az İşiten', 11, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET003-01')
UNION ALL SELECT 'ET003-02', 'Tamamen İşitme Engelli', 12, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET003-02')
UNION ALL SELECT 'ET004-01', 'Hafif Zihinsel Engelli', 13, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET004-01')
UNION ALL SELECT 'ET004-02', 'Orta Zihinsel Engelli', 14, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET004-02')
UNION ALL SELECT 'ET004-03', 'Ağır Zihinsel Engelli', 15, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET004-03')
UNION ALL SELECT 'ET005-01', 'Otizm', 16, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET005-01')
UNION ALL SELECT 'ET005-02', 'Down Sendromu', 17, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM engelli_tipi WHERE kod = 'ET005-02');

-- Alt tiplerin üst tiplerini bağlama
UPDATE engelli_tipi e SET e.ust_tip_id = (SELECT id FROM engelli_tipi WHERE kod = 'ET001' AND ROWNUM = 1) WHERE e.kod LIKE 'ET001-%';
UPDATE engelli_tipi e SET e.ust_tip_id = (SELECT id FROM engelli_tipi WHERE kod = 'ET002' AND ROWNUM = 1) WHERE e.kod LIKE 'ET002-%';
UPDATE engelli_tipi e SET e.ust_tip_id = (SELECT id FROM engelli_tipi WHERE kod = 'ET003' AND ROWNUM = 1) WHERE e.kod LIKE 'ET003-%';
UPDATE engelli_tipi e SET e.ust_tip_id = (SELECT id FROM engelli_tipi WHERE kod = 'ET004' AND ROWNUM = 1) WHERE e.kod LIKE 'ET004-%';
UPDATE engelli_tipi e SET e.ust_tip_id = (SELECT id FROM engelli_tipi WHERE kod = 'ET005' AND ROWNUM = 1) WHERE e.kod LIKE 'ET005-%';

-- 5. HASTALIK TANIMLARI
-- =========================================================================
INSERT INTO hastalik (kod, adi, kronik, aktif, olusturma_tarihi, olusturan)
SELECT 'H001', 'Şeker Hastalığı (Diyabet)', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H001')
UNION ALL SELECT 'H002', 'Tansiyon (Hipertansiyon)', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H002')
UNION ALL SELECT 'H003', 'Kalp Hastalığı', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H003')
UNION ALL SELECT 'H004', 'Astım', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H004')
UNION ALL SELECT 'H005', 'KOAH', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H005')
UNION ALL SELECT 'H006', 'Böbrek Yetmezliği', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H006')
UNION ALL SELECT 'H007', 'Kanser', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H007')
UNION ALL SELECT 'H008', 'Epilepsi', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H008')
UNION ALL SELECT 'H009', 'Multiple Skleroz (MS)', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H009')
UNION ALL SELECT 'H010', 'Parkinson', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H010')
UNION ALL SELECT 'H011', 'Alzheimer', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H011')
UNION ALL SELECT 'H012', 'Talasemi', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H012')
UNION ALL SELECT 'H013', 'Hemofili', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H013')
UNION ALL SELECT 'H014', 'Sedef Hastalığı', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H014')
UNION ALL SELECT 'H015', 'Romatizma', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM hastalik WHERE kod = 'H015');

-- 6. GELİR TÜRÜ TANIMLARI
-- =========================================================================
INSERT INTO gelir_turu (kod, adi, aciklama, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'GT001', 'Maaş', 'Aylık maaş geliri', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT001')
UNION ALL SELECT 'GT002', 'Emekli Maaşı', 'Emeklilik maaşı', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT002')
UNION ALL SELECT 'GT003', 'Kira Geliri', 'Gayrimenkulden kira geliri', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT003')
UNION ALL SELECT 'GT004', 'Ticari Kazanç', 'Ticari faaliyetten gelir', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT004')
UNION ALL SELECT 'GT005', 'Sosyal Yardım', 'Devlet/kuruluşlardan sosyal yardım', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT005')
UNION ALL SELECT 'GT006', 'Nafaka', 'Nafaka geliri', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT006')
UNION ALL SELECT 'GT007', 'Engelli Maaşı', 'Engelliler için aylık', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT007')
UNION ALL SELECT 'GT008', 'Yaşlılık Aylığı', '65 yaş üstü aylık', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT008')
UNION ALL SELECT 'GT009', 'Diğer', 'Diğer gelir kaynakları', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM gelir_turu WHERE kod = 'GT009');

-- 7. BORÇ TÜRÜ TANIMLARI
-- =========================================================================
INSERT INTO borc_turu (kod, adi, aciklama, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'BT001', 'Elektrik', 'Elektrik faturası', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT001')
UNION ALL SELECT 'BT002', 'Su', 'Su faturası', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT002')
UNION ALL SELECT 'BT003', 'Doğalgaz', 'Doğalgaz faturası', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT003')
UNION ALL SELECT 'BT004', 'Kira', 'Aylık kira borcu', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT004')
UNION ALL SELECT 'BT005', 'Kredi Kartı', 'Kredi kartı borcu', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT005')
UNION ALL SELECT 'BT006', 'Banka Kredisi', 'Tüketici kredisi', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT006')
UNION ALL SELECT 'BT007', 'İnternet', 'İnternet faturası', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT007')
UNION ALL SELECT 'BT008', 'Telefon', 'Telefon faturası', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT008')
UNION ALL SELECT 'BT009', 'Eğitim Masrafı', 'Okul, kurs vb.', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT009')
UNION ALL SELECT 'BT010', 'Sağlık Masrafı', 'İlaç, tedavi vb.', 10, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT010')
UNION ALL SELECT 'BT011', 'Diğer', 'Diğer borç/giderler', 11, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM borc_turu WHERE kod = 'BT011');

-- 8. YARDIM DİLİMİ TANIMLARI
-- =========================================================================
INSERT INTO yardim_dilimi (kod, adi, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'D01', 'Birinci Dilim', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_dilimi WHERE kod = 'D01')
UNION ALL SELECT 'D02', 'İkinci Dilim', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_dilimi WHERE kod = 'D02')
UNION ALL SELECT 'D03', 'Üçüncü Dilim', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_dilimi WHERE kod = 'D03')
UNION ALL SELECT 'D04', 'Dördüncü Dilim', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_dilimi WHERE kod = 'D04');

-- 9. YARDIM DÖNEMİ TANIMLARI (1-12 ay)
-- =========================================================================
INSERT INTO yardim_donemi (adi, ay_sayisi, aktif, olusturma_tarihi, olusturan)
SELECT '1 Aylık', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '1 Aylık')
UNION ALL SELECT '2 Aylık', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '2 Aylık')
UNION ALL SELECT '3 Aylık', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '3 Aylık')
UNION ALL SELECT '4 Aylık', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '4 Aylık')
UNION ALL SELECT '5 Aylık', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '5 Aylık')
UNION ALL SELECT '6 Aylık', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '6 Aylık')
UNION ALL SELECT '7 Aylık', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '7 Aylık')
UNION ALL SELECT '8 Aylık', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '8 Aylık')
UNION ALL SELECT '9 Aylık', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '9 Aylık')
UNION ALL SELECT '10 Aylık', 10, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '10 Aylık')
UNION ALL SELECT '11 Aylık', 11, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '11 Aylık')
UNION ALL SELECT '12 Aylık', 12, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_donemi WHERE adi = '12 Aylık');

-- 10. YARDIM RED SEBEBİ TANIMLARI
-- =========================================================================
INSERT INTO yardim_red_sebebi (kod, adi, aciklama, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'RS001', 'Gelir Düzeyi Yüksek', 'Gelir seviyesi yardım almak için uygun değil', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS001')
UNION ALL SELECT 'RS002', 'Evrak Eksikliği', 'Gerekli belgeler eksik', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS002')
UNION ALL SELECT 'RS003', 'Gebze Dışı İkamet', 'Başvuru sahibi Gebze dışında ikamet ediyor', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS003')
UNION ALL SELECT 'RS004', 'Sahte Beyan', 'Yanlış veya eksik bilgi verme', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS004')
UNION ALL SELECT 'RS005', 'Başka Kuruluştan Alıyor', 'Başka bir yerden benzer yardım alıyor', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS005')
UNION ALL SELECT 'RS006', 'Kriterlere Uymuyor', 'Yardım kriterlere uygun değil', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS006')
UNION ALL SELECT 'RS007', 'Bütçe Yetersizliği', 'Yardım bütçesi tükendi', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS007')
UNION ALL SELECT 'RS008', 'Diğer', 'Diğer sebepler', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_red_sebebi WHERE kod = 'RS008');

-- 11. YARDIM ALT TİPİ TANIMLARI
-- =========================================================================
-- Komisyonlu Nakdi Yardımlar
INSERT INTO yardim_alt_tipi (kod, adi, komisyon_kararli, yardim_tipi, aciklama, birim, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'NAKDI-001', 'Genel Ekonomik Destek', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Genel ihtiyaçlar', 'TL', 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-001')
UNION ALL SELECT 'NAKDI-002', 'Kira Yardımı', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Kira ödemesi', 'TL', 2, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-002')
UNION ALL SELECT 'NAKDI-003', 'Eğitim Yardımı', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Eğitim masrafları', 'TL', 3, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-003')
UNION ALL SELECT 'NAKDI-004', 'Sağlık Yardımı', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Tedavi masrafları', 'TL', 4, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-004')
UNION ALL SELECT 'NAKDI-005', 'Engelli Bakım Desteği', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Engelli bakım', 'TL', 5, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-005')
UNION ALL SELECT 'NAKDI-006', 'Yaşlı Bakım Desteği', 1, 'NAKDI', 'Komisyonlu nakdi yardım - Yaşlı bakım', 'TL', 6, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'NAKDI-006');

-- Komisyonlu Ayni Yardımlar
INSERT INTO yardim_alt_tipi (kod, adi, komisyon_kararli, yardim_tipi, aciklama, birim, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'AYNI-001', 'Gıda Kolisi', 1, 'AYNI', 'Komisyonlu ayni yardım - Gıda ürünleri', 'Adet', 7, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-001')
UNION ALL SELECT 'AYNI-002', 'Kömür Yardımı', 1, 'AYNI', 'Komisyonlu ayni yardım - Kış yakıtı', 'Adet', 8, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-002')
UNION ALL SELECT 'AYNI-003', 'Beyaz Eşya', 1, 'AYNI', 'Komisyonlu ayni yardım - Buzdolabı, çamaşır makinesi', 'Adet', 9, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-003')
UNION ALL SELECT 'AYNI-004', 'Mobilya', 1, 'AYNI', 'Komisyonlu ayni yardım - Yatak, dolap, masa', 'Adet', 10, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-004')
UNION ALL SELECT 'AYNI-005', 'Kırtasiye Yardımı', 1, 'AYNI', 'Komisyonlu ayni yardım - Okul malzemeleri', 'Adet', 11, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-005')
UNION ALL SELECT 'AYNI-006', 'Giyim Yardımı', 1, 'AYNI', 'Komisyonlu ayni yardım - Giyecek', 'Adet', 12, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-006');

-- Komisyonsuz Ayni Yardımlar
INSERT INTO yardim_alt_tipi (kod, adi, komisyon_kararli, yardim_tipi, aciklama, birim, sira_no, aktif, olusturma_tarihi, olusturan)
SELECT 'AYNI-K001', 'Acil Gıda Paketi', 0, 'AYNI', 'Komisyonsuz ayni yardım - Acil durum paketi', 'Adet', 13, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-K001')
UNION ALL SELECT 'AYNI-K002', 'Hijyen Paketi', 0, 'AYNI', 'Komisyonsuz ayni yardım - Temizlik malzemeleri', 'Adet', 14, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-K002')
UNION ALL SELECT 'AYNI-K003', 'Bebek Paketi', 0, 'AYNI', 'Komisyonsuz ayni yardım - Bebek bezi, mama', 'Adet', 15, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-K003')
UNION ALL SELECT 'AYNI-K004', 'İlaç Kuponu', 0, 'AYNI', 'Komisyonsuz ayni yardım - Eczane kuponu', 'Adet', 16, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-K004')
UNION ALL SELECT 'AYNI-K005', 'Ekmek Kuponu', 0, 'AYNI', 'Komisyonsuz ayni yardım - Günlük ekmek', 'Adet', 17, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM yardim_alt_tipi WHERE kod = 'AYNI-K005');

-- 12. PERSONEL KAYITLARI
-- =========================================================================
INSERT INTO personel (tc_kimlik_no, ad, soyad, telefon, email, unvan, departman, tahkikat_yetkili, komisyon_uyesi, aktif, olusturma_tarihi, olusturan)
SELECT '11111111110', 'Ahmet', 'YILMAZ', '5551234567', 'ahmet.yilmaz@gebze.bel.tr', 'Sosyal Yardım Uzmanı', 'Sosyal Hizmetler', 1, 0, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM personel WHERE tc_kimlik_no = '11111111110')
UNION ALL SELECT '22222222220', 'Ayşe', 'KAYA', '5559876543', 'ayse.kaya@gebze.bel.tr', 'Sosyal Çalışmacı', 'Sosyal Hizmetler', 1, 0, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM personel WHERE tc_kimlik_no = '22222222220')
UNION ALL SELECT '33333333330', 'Mehmet', 'DEMİR', '5551112233', 'mehmet.demir@gebze.bel.tr', 'Müdür', 'Sosyal Hizmetler', 0, 1, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM personel WHERE tc_kimlik_no = '33333333330');

-- 13. KİŞİ KAYITLARI (Gerçekçi test verileri)
-- =========================================================================
-- GEBZE İÇİ KİŞİLER (TC son hanesi 0-5: Gebze içi simülasyon kuralı)
INSERT INTO kisi (tc_kimlik_no, ad, soyad, baba_adi, anne_adi, dogum_tarihi, dogum_yeri, cinsiyet, adres, il, ilce, telefon, gebze_ikameti, sgk_durum, ogrenim_durum, son_mernis_sorgu_tarihi, mernis_guncelleme_sayisi, olusturma_tarihi, olusturan)
SELECT '24151827460', 'Fatma', 'YILMAZ', 'Mehmet', 'Ayşe', TO_DATE('1985-03-15', 'YYYY-MM-DD'), 'İstanbul', 'K', 'Güzeller Mahallesi No:45 Gebze', 'Kocaeli', 'Gebze', '5551112233', 1, 'SSK', 'LISE', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '24151827460')
UNION ALL SELECT '35678912340', 'Ali', 'KAYA', 'Hasan', 'Zeynep', TO_DATE('1978-07-22', 'YYYY-MM-DD'), 'Ankara', 'E', 'Aydıntepe Mahallesi No:12 Gebze', 'Kocaeli', 'Gebze', '5552223344', 1, 'EMEKLI_SANDIGI', 'LISANS', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '35678912340')
UNION ALL SELECT '18956234520', 'Zeynep', 'ÇELİK', 'Ahmet', 'Hatice', TO_DATE('1990-11-08', 'YYYY-MM-DD'), 'İzmir', 'K', 'Cumhuriyet Mahallesi No:78 Gebze', 'Kocaeli', 'Gebze', '5553334455', 1, 'BAG_KUR', 'ORTAOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '18956234520')
UNION ALL SELECT '42789654310', 'Mustafa', 'ŞAHİN', 'İbrahim', 'Emine', TO_DATE('1965-05-30', 'YYYY-MM-DD'), 'Kocaeli', 'E', 'Eskihisar Mahallesi No:23 Gebze', 'Kocaeli', 'Gebze', '5554445566', 1, 'EMEKLI_SANDIGI', 'ILKOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '42789654310')
UNION ALL SELECT '56234789120', 'Emine', 'YILDIZ', 'Ömer', 'Fatma', TO_DATE('1995-09-12', 'YYYY-MM-DD'), 'Bursa', 'K', 'Kirazpınar Mahallesi No:56 Gebze', 'Kocaeli', 'Gebze', '5555556677', 1, 'YOK', 'LISE', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '56234789120')
UNION ALL SELECT '67123456780', 'Hasan', 'ÖZTÜRK', 'Ali', 'Zeynep', TO_DATE('1982-01-25', 'YYYY-MM-DD'), 'Kocaeli', 'E', 'Adnan Menderes Mahallesi Gebze', 'Kocaeli', 'Gebze', '5556667788', 1, 'SSK', 'ON_LISANS', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '67123456780')
UNION ALL SELECT '78945612300', 'Hatice', 'AYDIN', 'Mustafa', 'Ayşe', TO_DATE('1988-12-03', 'YYYY-MM-DD'), 'Sakarya', 'K', 'Karadeniz Mahallesi No:34 Gebze', 'Kocaeli', 'Gebze', '5557778899', 1, 'YESIL_KART', 'OKUR_YAZAR', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '78945612300')
UNION ALL SELECT '89456723410', 'İbrahim', 'ARSLAN', 'Mehmet', 'Emine', TO_DATE('1970-04-18', 'YYYY-MM-DD'), 'Ankara', 'E', 'Çınar Mahallesi No:89 Gebze', 'Kocaeli', 'Gebze', '5558889900', 1, 'BAG_KUR', 'ILKOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '89456723410')
UNION ALL SELECT '12345678920', 'Elif', 'DOĞAN', 'Hüseyin', 'Fatma', TO_DATE('1998-06-27', 'YYYY-MM-DD'), 'İstanbul', 'K', 'Pelitli Mahallesi No:67 Gebze', 'Kocaeli', 'Gebze', '5559990011', 1, 'YOK', 'LISANS', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '12345678920')
UNION ALL SELECT '23456789030', 'Hüseyin', 'KURT', 'Ali', 'Zeynep', TO_DATE('1975-08-14', 'YYYY-MM-DD'), 'Kocaeli', 'E', 'Hürriyet Mahallesi No:45 Gebze', 'Kocaeli', 'Gebze', '5550011122', 1, 'SSK', 'ORTAOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '23456789030');

-- GEBZE DIŞI KİŞİLER
INSERT INTO kisi (tc_kimlik_no, ad, soyad, baba_adi, anne_adi, dogum_tarihi, dogum_yeri, cinsiyet, adres, il, ilce, telefon, gebze_ikameti, sgk_durum, ogrenim_durum, son_mernis_sorgu_tarihi, mernis_guncelleme_sayisi, olusturma_tarihi, olusturan)
SELECT '98765432106', 'Emre', 'YILDIRIM', 'Mustafa', 'Ayşe', TO_DATE('1992-02-10', 'YYYY-MM-DD'), 'İstanbul', 'E', 'Kadıköy Mahallesi No:123', 'İstanbul', 'Kadıköy', '5551234321', 0, 'SSK', 'LISANS', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '98765432106')
UNION ALL SELECT '87654321097', 'Derya', 'ÖZKAN', 'Ahmet', 'Fatma', TO_DATE('1987-05-20', 'YYYY-MM-DD'), 'Ankara', 'K', 'Çankaya Mahallesi No:45', 'Ankara', 'Çankaya', '5552345432', 0, 'BAG_KUR', 'YUKSEK_LISANS', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '87654321097')
UNION ALL SELECT '76543210988', 'Burak', 'AKTAŞ', 'Hasan', 'Zeynep', TO_DATE('1980-09-08', 'YYYY-MM-DD'), 'İzmir', 'E', 'Karşıyaka Mahallesi No:67', 'İzmir', 'Karşıyaka', '5553456543', 0, 'EMEKLI_SANDIGI', 'LISE', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '76543210988')
UNION ALL SELECT '65432109879', 'Selin', 'POLAT', 'Mehmet', 'Emine', TO_DATE('1993-11-15', 'YYYY-MM-DD'), 'Bursa', 'K', 'Nilüfer Mahallesi No:89', 'Bursa', 'Nilüfer', '5554567654', 0, 'YOK', 'ORTAOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '65432109879')
UNION ALL SELECT '54321098766', 'Serkan', 'KAPLAN', 'İbrahim', 'Hatice', TO_DATE('1968-03-28', 'YYYY-MM-DD'), 'Antalya', 'E', 'Muratpaşa Mahallesi No:12', 'Antalya', 'Muratpaşa', '5555678765', 0, 'EMEKLI_SANDIGI', 'ILKOKUL', SYSDATE, 1, SYSDATE, 'SYSTEM' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM kisi WHERE tc_kimlik_no = '54321098766');

-- 14. HESAP BİLGİSİ (IBAN) KAYITLARI
-- =========================================================================
INSERT INTO hesap_bilgisi (kisi_id, banka_adi, iban, hesap_sahibi_adi, varsayilan, aktif, olusturma_tarihi, olusturan)
SELECT k.id, 'Ziraat Bankası', 'TR330006100519786457841326', 'Fatma YILMAZ', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '24151827460' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Halkbank', 'TR120001200934800019850016', 'Ali KAYA', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '35678912340' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Vakıfbank', 'TR150001500158007301234567', 'Zeynep ÇELİK', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '18956234520' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'İş Bankası', 'TR640006400000112340056789', 'Mustafa ŞAHİN', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '42789654310' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Akbank', 'TR460004600232888001298765', 'Emine YILDIZ', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '56234789120' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Garanti BBVA', 'TR370006200019200006789123', 'Hasan ÖZTÜRK', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '67123456780' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Yapı Kredi', 'TR670006700100000022334455', 'Hatice AYDIN', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '78945612300' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'QNB Finansbank', 'TR110001100000000111122233', 'İbrahim ARSLAN', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '89456723410' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'Denizbank', 'TR340001340002550099887766', 'Elif DOĞAN', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '12345678920' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id)
UNION ALL SELECT k.id, 'TEB', 'TR320003200015510000554433', 'Hüseyin KURT', 1, 1, SYSDATE, 'SYSTEM' FROM kisi k WHERE k.tc_kimlik_no = '23456789030' AND NOT EXISTS (SELECT 1 FROM hesap_bilgisi WHERE kisi_id = k.id);

-- =========================================================================
-- VERİ YÜKLEME TAMAMLANDI
-- =========================================================================

