CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists Client cascade;
create table Client(
	id uuid primary key default uuid_generate_v4(),
	name text not null,
	username text not null,
	password text not null,
	credit_card int not null,
	public_key text not null,
	current_total_spent_euro INTEGER not null default 0,
	current_total_spent_cent INTEGER not null default 0,
	current_accumulated_euro INTEGER not null default 0,
	current_accumulated_cent INTEGER not null default 0
);

drop table if exists Voucher cascade;
create table Voucher(
	id uuid primary key default uuid_generate_v4(),
	user uuid not null REFERENCES Client(id),
	was_used BOOLEAN not null DEFAULT FALSE
);

drop table if exists Purchase cascade;
create table Purchase(
	id uuid primary key default uuid_generate_v4(),
	user uuid not null REFERENCES Client(id),
	voucher uuid REFERENCES Voucher(id) DEFAULT NULL,
	should_discount BOOLEAN not null DEFAULT false
);

drop table if exists Product cascade;
create table Product(
	id uuid primary key default uuid_generate_v4(),
	price_euro INTEGER not null,
	price_cent INTEGER not null,
	name text not null,
	image_url text DEFAULT NULL,
	purchase uuid REFERENCES Purchase(id) DEFAULT null
);
