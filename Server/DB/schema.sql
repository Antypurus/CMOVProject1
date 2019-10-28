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
