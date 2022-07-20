CREATE SEQUENCE IF NOT EXISTS public.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.users_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.endpoint_histories_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.endpoint_histories_id_seq
    OWNER TO postgres;

CREATE SEQUENCE IF NOT EXISTS public.token_blacklist_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.token_blacklist_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying(50) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.endpoint_histories
(
    id bigint NOT NULL DEFAULT nextval('endpoint_histories_id_seq'::regclass),
    created_at timestamp without time zone,
    endpoint_type character varying(10) COLLATE pg_catalog."default",
    endpoint_url character varying(100) COLLATE pg_catalog."default",
    json_response character varying(4000) COLLATE pg_catalog."default",
    CONSTRAINT endpoint_histories_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.endpoint_histories
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.token_blacklist
(
    id bigint NOT NULL DEFAULT nextval('token_blacklist_id_seq'::regclass),
    created_at timestamp without time zone NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT token_blacklist_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token_blacklist
    OWNER to postgres;


INSERT INTO public.users(username, password)
VALUES ('Admin', '$2a$10$KOyJ8ZJTpvJQfM5Gz6oFxe2apUU3R544GzPDoSWNXJDWBSeNkRctO');