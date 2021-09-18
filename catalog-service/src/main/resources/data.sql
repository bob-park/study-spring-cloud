-- spring boot 2.5 버전 이상부터 flyway 등 db initialize tool 과 통합하기 위해, 사용하지 않는 걸 추천
-- 동작도 잘 안함
insert into catalog (product_id, product_name, stock, unit_price)
values ('CATALOG-001', 'Berlin', 100, 1500);
insert into catalog (product_id, product_name, stock, unit_price)
values ('CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog (product_id, product_name, stock, unit_price)
values ('CATALOG-003', 'Stockholm', 120, 2000);