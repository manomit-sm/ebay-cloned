CREATE TABLE IF NOT EXISTS public."user" (
	id uuid NOT NULL,
	created_at timestamp(6) NOT NULL,
	updated_at timestamp(6) NOT NULL,
	azure_id varchar(255) NOT NULL UNIQUE,
	first_name varchar(100),
	last_name varchar(100),
	address text,
	status varchar(20),
	photo varchar(255),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);