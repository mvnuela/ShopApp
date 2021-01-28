use shop;
delimiter $$
drop procedure if exists addProduct $$
create procedure addProduct(in c varchar(30), in b varchar(30), in cLn varchar(30), in cLv int, in t varchar(20), in n varchar(100))
begin
declare ci, bi, cLi, ti int;
	if c not in (select name from Colors) then
        insert into colors (name) values (c);
    end if;
    if b not in (select name from Brands) then
        insert into brands (name) values (b);
    end if;
    if not exists (select * from CoverageLevels where name = cLn and numericValue = cLv) then
        insert into coveragelevels (numericValue, name) values (cLv, cLn);
    end if;
    if t not in (select name from types) then
        insert into types (name) values (t);
    end if;
    select id from types where name = t into ti;
    select id from coveragelevels where name = cLn and numericValue = cLv into cLi;
    select id from brands where name = b into bi;
    select id from colors where name = c into ci;
    if exists (
        select * from products
        where color = ci
        and coverageLevel = cLi
        and brand = bi
        and type = ti
    ) then
    signal sqlstate '45000' set message_text="This product already exists";
    else
	    insert into Products (color,brand,coverageLevel,type)
        values(ci, bi, cLi, ti);
	end if;
end; $$
delimiter ;

delimiter $$
drop procedure if exists addClient $$
create procedure addClient(in n varchar(50), in s varchar(50), in a varchar(100))
begin
 if exists (select name, surname, address from Clients where name=n and surname=s and address=a) then
	 signal sqlstate '45000' set message_text="Client already exists";
 else
	insert into Clients(name,surname,address)
	values (n,s,a);
 end if;
end; $$
delimiter ;

-- saving for later since it's already here, definitely will get handy
/*      select * from Products
        inner join brands b2 on products.brand = b2.id
        inner join colors c2 on products.color = c2.id
        inner join coverageLevels c3 on products.coverageLevel = c3.id
        inner join types t2 on products.type = t2.id
        where
            b2.name = b
        and c2.name = c
        and c3.name = cLn
        and c3.numericValue = cLv
        and t2.name = t
 */