USE pizzeria;



INSERT INTO roles(id,name,description) VALUES
(1,'ROLE_ADMIN','Správca systému'),
(2,'ROLE_ZAKAZNIK','Zákazník'),
(3,'ROLE_KUCHAR','Kuchár'),
(4,'ROLE_KURIER','Kuriér');



INSERT INTO users(id,email,password,full_name,phone,address,profile_image_url,enabled,created_at,updated_at) VALUES
(1,'admin@pizzeria.sk','$2a$12$F6bSH30nYHXzLXxfr//jFe0T3mSjUknyFMHeTGwktqWQeA0pj6FbC','Admin Majiteľ','0901 000 000','Hlavná 1',NULL,TRUE,NOW(),NOW()),
(2,'kuchar@pizzeria.sk','$2a$12$9zFacVKwNUa98db7uyQMx.VswliCqn8BDyJVer3HQPa/kpfWI/2Di','Šéfkuchár Ján','0902 000 000','Pekárska 2',NULL,TRUE,NOW(),NOW()),
(3,'kurier@pizzeria.sk','$2a$12$rL.I0zlNTtprRObGSbeDDeEyNRjEn8Si3Xp0hPLenh7x83Y3h3Fuq','Kuriér Peter','0903 000 000','Doručovacia 3',NULL,TRUE,NOW(),NOW()),
(4,'zakaznik@pizzeria.sk','$2a$12$mTcEzeKAbcxpgZZQr0kjnOF9I2LhOw4d5H5e1kuZSC2T8Vq3C.LLe','Zákazník Marek','0904 000 000','Bytová 4',NULL,TRUE,NOW(),NOW());



INSERT INTO user_roles VALUES
(1,1),(2,3),(3,4),(4,2);



INSERT INTO ingredients(id,name,slug,description,extra_price,active) VALUES
(1,'Syr','syr','Mozzarella',0,TRUE),
(2,'Šunka','sunka','Bravčová šunka',0,TRUE),
(3,'Paradajkový základ','paradajkovy-zaklad','Omáčka',0,TRUE),
(4,'Kukurica','kukurica','Sladká',0,TRUE),
(5,'Šampiňóny','sampinony','Čerstvé',0,TRUE),
(6,'Slanina','slanina','Údená',0,TRUE),
(7,'Cibuľa','cibula','Červená',0,TRUE),
(8,'Paprika','paprika','Čerstvá',0,TRUE),
(9,'Olivy','olivy','Čierne',0,TRUE),
(10,'Feferóny','feferony','Pálivé',0,TRUE),
(11,'Ananás','ananas','Tropický',0,TRUE),
(12,'Prosciutto','prosciutto','Talianska šunka',0,TRUE),
(13,'Gorgonzola','gorgonzola','Modrý syr',0,TRUE),
(14,'Parmezán','parmezan','Strúhaný syr',0,TRUE),
(15,'Cesnak','cesnak','Čerstvý',0,TRUE);



INSERT INTO tags(id,name,slug,description) VALUES
(1,'Pálivá','paliva','Pálivé jedlá'),
(2,'Vegetariánska','vegetarianska','Bez mäsa'),
(3,'Bezlepková','bezlepkova','Bez lepku'),
(4,'Luxusná','luxusna','Prémiová'),
(5,'Klasická','klasicka','Tradičná');



INSERT INTO pizzas(id,name,slug,description,image_url,active,created_at,updated_at) VALUES
(1,'Margarita','margarita','Klasická','/images/margherita-classica.webp',TRUE,NOW(),NOW()),
(2,'Hawai','hawai','Ananás a šunka','/images/hawaii-classic.webp',TRUE,NOW(),NOW()),
(3,'Funghi','funghi','Šampiňóny','/images/funghi.webp',TRUE,NOW(),NOW()),
(4,'Prosciutto','prosciutto','Talianska šunka','/images/Proscuitto-Pizza_SQ-1.jpg',TRUE,NOW(),NOW()),
(5,'Quattro Formaggi','quattro-formaggi','4 syry','/images/quatro.jpg',TRUE,NOW(),NOW()),
(6,'Diavola','diavola','Pálivá pizza','/images/diavola-piccante.webp',TRUE,NOW(),NOW()),
(7,'Vegetariana','vegetariana','Zeleninová','/images/Vegetariana1.jpg',TRUE,NOW(),NOW()),
(8,'Salami','salami','Salámová pizza','/images/salami.jpg',TRUE,NOW(),NOW()),
(9,'Carbonara','carbonara','Slanina a syr','/images/carbonara-pizza.webp',TRUE,NOW(),NOW()),
(10,'Cesnaková','cesnakova','Cesnaková pizza','/images/cesnakova.jpg',TRUE,NOW(),NOW());



INSERT INTO pizza_sizes(id,pizza_id,size_label,diameter_cm,price,active) VALUES
(1,1,'M',26,6.50,TRUE),(2,1,'L',32,7.90,TRUE),(3,1,'XL',45,11.50,TRUE),
(4,2,'M',26,7.00,TRUE),(5,2,'L',32,8.50,TRUE),(6,2,'XL',45,12.00,TRUE),
(7,3,'M',26,6.90,TRUE),(8,3,'L',32,8.30,TRUE),(9,3,'XL',45,11.90,TRUE),
(10,4,'M',26,7.20,TRUE),(11,4,'L',32,8.70,TRUE),(12,4,'XL',45,12.40,TRUE),
(13,5,'M',26,8.20,TRUE),(14,5,'L',32,9.90,TRUE),(15,5,'XL',45,13.50,TRUE),
(16,6,'M',26,7.90,TRUE),(17,6,'L',32,9.40,TRUE),(18,6,'XL',45,13.00,TRUE),
(19,7,'M',26,7.50,TRUE),(20,7,'L',32,8.90,TRUE),(21,7,'XL',45,12.80,TRUE),
(22,8,'M',26,7.30,TRUE),(23,8,'L',32,8.80,TRUE),(24,8,'XL',45,12.20,TRUE),
(25,9,'M',26,7.70,TRUE),(26,9,'L',32,9.10,TRUE),(27,9,'XL',45,13.10,TRUE),
(28,10,'M',26,6.80,TRUE),(29,10,'L',32,8.20,TRUE),(30,10,'XL',45,11.90,TRUE);



INSERT INTO pizza_ingredients VALUES
(1,1),(1,3),(2,1),(2,2),(2,11),(2,3),(3,1),(3,3),(3,5),
(4,1),(4,12),(4,3),(5,1),(5,13),(5,14),(6,1),(6,10),(6,9),(6,3),
(7,1),(7,4),(7,5),(7,8),(7,9),(7,3),(8,1),(8,6),(8,3),
(9,1),(9,6),(9,14),(10,1),(10,15),(10,9);



INSERT INTO pizza_tags VALUES
(1,5),(2,5),(3,5),(4,5),(8,5),(5,4),(6,1),(7,2),(9,4),(10,2);



INSERT INTO orders(id,code,customer_id,status,total_price,delivery_address,note,assigned_cook_id,assigned_courier_id,created_at,updated_at) VALUES
(1,'ORD-0001',4,'NEW',15.80,'Bytová 4',NULL,NULL,NULL,NOW(),NOW()),
(2,'ORD-0002',4,'PRIPRAVUJE_SA',12.20,'Bytová 4',NULL,2,NULL,NOW(),NOW()),
(3,'ORD-0003',4,'PRIPRAVENA',9.90,'Bytová 4',NULL,2,NULL,NOW(),NOW()),
(4,'ORD-0004',4,'DORUCUJE_SA',13.50,'Bytová 4',NULL,2,3,NOW(),NOW()),
(5,'ORD-0005',4,'DORUCENA',8.20,'Bytová 4',NULL,2,3,NOW(),NOW());



INSERT INTO order_items(order_id,pizza_size_id,quantity,unit_price,pizza_name_snapshot,size_label_snapshot,ingredients_snapshot,image_url_snapshot) VALUES
(1,1,2,6.50,'Margarita','M','syr, základ','/images/margherita-classica.webp'),
(2,7,1,6.90,'Funghi','M','syr, základ, šampiňóny','/images/funghi.webp'),
(3,14,1,9.90,'Quattro Formaggi','L','syr, gorgonzola, parmezán','/images/quatro.jpg'),
(4,17,1,9.40,'Diavola','L','syr, feferóny','/images/diavola-piccante.webp'),
(5,28,1,6.80,'Cesnaková','M','syr, cesnak','/images/cesnakova.jpg');

