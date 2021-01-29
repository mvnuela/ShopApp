use shop;
delimiter $$
drop function if exists  addInvoice $$
/*
create function addInvoice(cl int) returns int
begin
    declare date DATETIME;
    declare result int;
    SET date = now();
    insert into Invoices (dateIssued,client)
    values (date,cl);
    select id from Invoices where client=cl and dateIssued=date limit 1 into result;
    return result;
*/
create function addInvoice(cl int) returns INT deterministic
begin
    declare date DATETIME;
    declare result int;
    SET @date=now();
    insert into invoices (dateIssued,client)
    VALUES (@date,cl);
    SET @result=(select id from invoices where client=cl and dateIssued=@date);
    RETURN @result;
end $$
delimiter ;

delimiter $$
drop procedure if exists addLine $$
create procedure addLine(in inv int,in prod int,in units int)
begin
    START TRANSACTION;
    INSERT INTO InvoiceLine (units,product,invoice)
    values (units,prod,inv); /* cenę za wszystkie sztuki wstawiamy tutaj czy robimy triggera?*/
    UPDATE Offer
    SET unitsInStock=unitsInStock-units
    WHERE product=prod;
    COMMIT;
end $$
delimiter ;