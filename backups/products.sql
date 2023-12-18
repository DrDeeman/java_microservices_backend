--
-- PostgreSQL database dump
--

-- Dumped from database version 12.16 (Ubuntu 12.16-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.16 (Ubuntu 12.16-0ubuntu0.20.04.1)

-- Started on 2023-12-05 14:07:48 +04

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
-- TOC entry 2974 (class 1262 OID 165307)
-- Name: products; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE products WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE products OWNER TO postgres;

\connect products

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
-- TOC entry 204 (class 1259 OID 165353)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(255),
    page_image character varying(255),
    price numeric(20,0),
    raiting double precision,
    year_issue timestamp(6) without time zone
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 165349)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 165351)
-- Name: products_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq1 OWNER TO postgres;

--
-- TOC entry 2975 (class 0 OID 0)
-- Dependencies: 203
-- Name: products_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq1 OWNED BY public.products.id;


--
-- TOC entry 2837 (class 2604 OID 165356)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq1'::regclass);


--
-- TOC entry 2968 (class 0 OID 165353)
-- Dependencies: 204
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (1, 'Batman #9', 'batman1.jpeg', 350, 5, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (2, 'Batman #50', 'batman2.jpg', 320, 4.9, '2021-02-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (3, 'Batman #608', 'batman3.jpg', 350, 4.8, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (4, 'Batman #402', 'batman4.jpg', 350, 5, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (5, 'Batman #422', 'batman5.jpg', 350, 5, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (6, 'Batman #15', 'batman6.jpg', 350, 2, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (7, 'Batman #703', 'batman7.jpg', 350, 5, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (8, 'Batman #800', 'batman8.jpeg', 350, 5, '2021-01-01 00:00:00');
INSERT INTO public.products (id, name, page_image, price, raiting, year_issue) VALUES (9, 'Batman #5', 'batman9.jpg', 350, 5, '2021-01-01 00:00:00');


--
-- TOC entry 2976 (class 0 OID 0)
-- Dependencies: 202
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 1, false);


--
-- TOC entry 2977 (class 0 OID 0)
-- Dependencies: 203
-- Name: products_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq1', 9, true);


--
-- TOC entry 2839 (class 2606 OID 165361)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


-- Completed on 2023-12-05 14:07:48 +04

--
-- PostgreSQL database dump complete
--

