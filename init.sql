CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL,
    username character varying(50) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.endpoint_histories
(
    id bigint NOT NULL,
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
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT token_blacklist_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token_blacklist
    OWNER to postgres;

INSERT INTO public.users(
    id, username, password)
VALUES (1, 'Admin', '$2a$10$KOyJ8ZJTpvJQfM5Gz6oFxe2apUU3R544GzPDoSWNXJDWBSeNkRctO');