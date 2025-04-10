create database EcomApp;
use ecomapp;

/*Customers Table*/
create table  Customers ( customer_id int auto_increment primary key, name varchar(50) not null,email varchar(50)not null,
password varchar(20) not null);

/*Products Table*/
create table Products(product_id int auto_increment primary key not null, Product_name varchar(50) not null, price decimal(10,2),
description text, stock_quantity int not null);

/*cart table*/
create table cart(cart_id int auto_increment primary key,customer_id int not null,product_id int not null, quantity int not null, 
foreign key (customer_id) references Customers(customer_id),foreign key (product_id) references Products(product_id));

/*orders table*/
create table Orders(order_id int auto_increment primary key not null,customer_id int not null,order_date date not null,total_price decimal(10,2)not null,shipping_address varchar(255) not null, 
foreign key (customer_id) references Customers(customer_id));

/*order-items table */
create table order_items(order_item_id int auto_increment primary key, order_id int not null ,product_id int not null, quantity int not null,
foreign key (order_id) references orders(order_id),foreign key (product_id) references products(product_id));

/*Customers Table*/
insert into  Customers (name,email,password) values ('Prasanna','prasannan123@gmail.com','prasan@1235'),
('Gopal','gopal77@gmail.com','gopal@3737'),('Kannan','Kannan123@gmail.com','Kannan@1234'),('Balaji','balaji123@gmail.com','balaji@1236'),
('Lakshmi','lakshmi123@gmail.com','lakshmi@6543'),('Swetha','Swetha723@gmail.com','swetha@7654'),('Ajay','ajay673@gmail.com','ajay@0987'),
('Kanishk','kanishk643@gmail.com','kanish@1237'),('Hari','hari34@gmail.com','hari@1777'),('Pooja','pooja321@gmail.com','pooja@5437');

/*Products Table*/
insert into Products(Product_name,price,description,stock_quantity) values('Laptop',75000.00,'High performance Laptop',20),('SmartPhone',35000.00,'Latest Model SmartPhone',30),
('SmartWatch',5000.00,'Water-Resistant Smartwatch',40),('HeadPhones',2000.00,'Active-Noise-Cancellation Headphones',50),('Tablet',25000.00,'10 inch screen for better visual-experience',20),
('WireLess-Charger',8000.00,'45w fast wireless chargering',30),('Bluetooth-Speaker',5000.00,'High bass and 20w ouput Bluetooth-speaker',20),
('KeyBoards',2000.00,'Back-lighting for better visibility',70),('Mouse',15000.00,'20000dpi for fast sensing',20),('Smart-TV',85000.00,'4K with dolby atmos',50);

/*cart table*/
insert into cart(customer_id,quantity,product_id) values (1,1,2),(2,1,10),(3,2,4),(4,1,6),(5,3,7),(6,2,9),(7,1,1),(8,1,3),(9,1,5),(10,6,8);

/*orders table*/
insert into orders (customer_id,order_Date,total_price,Shipping_address) values(1,'2025-03-10',35000.00,'123 main st ,chennai'),(2,'2025-03-09',85000.00,'123 main st ,Madurai'),
(3,'2025-03-12',4000.00,'57 1st st ,Andhra pradesh'),(4,'2025-03-07',8000.00,'12 gandhi st ,Delhi'), (5,'2025-03-21',15000.00,'123 main st ,chennai'),
(6,'2025-03-11',45000.00,'123 main st ,Bangalore'),(7,'2025-03-06',75000.00,'13 main st ,Telungana'), (8,'2025-03-05',5000.00,'14 main st ,Mumbai'),
(9,'2025-03-02',25000.00,'23 main st ,Goa'),(10,'2025-03-01',12000.00,'57 boss st ,Hyderabad');

/*order-items table */
insert into order_items (order_id,Product_id,quantity) values(1,2,1),(2,10,1),(3,4,2),(4,6,1),(5,7,3),(6,9,2),(7,1,1),(8,3,1),(9,5,1),(10,8,6);




/*queries*/
desc  Customers;
select* from customers;
select* from products;
select* from cart;
select* from orders;
select* from order_items;




select * from customers inner join cart where cart.customer_id = customers.customer_id;
select * from customers left join cart on cart.customer_id = customers.customer_id;
select * from customers right join cart on cart.customer_id = customers.customer_id;

select * from customers inner join orders on orders.customer_id = customers.customer_id inner join order_items on order_items.order_id=orders.order_id
inner join products on products.product_id=order_items.product_id ;
