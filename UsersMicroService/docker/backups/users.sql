--
-- PostgreSQL database dump
--

-- Dumped from database version 12.17 (Ubuntu 12.17-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.17 (Ubuntu 12.17-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: users; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE users WITH TEMPLATE = template0 ENCODING = 'UTF8';


ALTER DATABASE users OWNER TO postgres;

\connect users

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255),
    login character varying(255),
    name character varying(255),
    password character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq1 OWNER TO postgres;

--
-- Name: users_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq1 OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq1'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (332, 'fsdfsd@gmail.com', 'testtt', 'testtt', '$2a$10$MNnuwZDMpnPosJi1RhIeCu2Kk42ufKZHg3ff/WmEOQnhYLaX68lPK');
INSERT INTO public.users VALUES (342, 'sdfsdfsdfs@gmail.com', 'hfghfhf', 'hfghfhf', '$2a$10$OZUUW52ljEtPFzQbVutTA.ox2Nb1rhKO3tKm5ZCIIWCoSsOTn452y');
INSERT INTO public.users VALUES (343, 'sdsdfs@gmail.com', 'zxczxczxc', 'zxczxczxc', '$2a$10$ZcO49k/NRm1iGPim5AveDeGFGOQLuIwpaarbLcHZ3zXQx8Vm60Vhy');
INSERT INTO public.users VALUES (214, 'stalkerdrdeeman@gmail.com', 'demo', 'demo', '$2a$10$kl.UYFfc1xhdaY9NlBDRu.zbJZj/XjdXoUr.3pEw38f25ImE7IhRG');
INSERT INTO public.users VALUES (352, 'stalkerdrdeeman@gmail.com', 'demo2', 'demo2', '$2a$10$NcCEo8pQFcmVnTQU5h7wJ.FRE.3ueUPW9HcpYqjFoIBaboxNgYF5q');
INSERT INTO public.users VALUES (362, 'stalkerdrdeeman@gmail.com', 'demo3', 'demo3', '$2a$10$O.f9cPWO/QDK.G8yT5KdC.HwBPUZHHhIdiWn3uxH8RTlJZ3j6b5gS');
INSERT INTO public.users VALUES (363, 'stalkerdrdeeman@gmail.com', 'demo5', 'demo5', '$2a$10$Hwce72h6xJJtuZR38958He6d9PreQpsDc9WcOi2ZqtcXHCisdd8eW');
INSERT INTO public.users VALUES (372, 'dfgdfgdf@gmail.com', 'rytefgdfg', 'rytefgdfg', '$2a$10$VGQ346Rf1sf0XISViFXpw.qP2gzuuZqjVQ6dIe6dzqiWIrAPgBdIS');


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 381, true);


--
-- Name: users_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq1', 1, false);


--
-- Name: users uni_login; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uni_login UNIQUE (login);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

