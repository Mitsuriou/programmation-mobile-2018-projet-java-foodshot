--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5 (Debian 10.5-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: aime; Type: TABLE; Schema: public; Owner: projetmobile
--

CREATE TABLE public.aime (
    id_utilisateur integer NOT NULL,
    id_publication integer NOT NULL
);


ALTER TABLE public.aime OWNER TO projetmobile;

--
-- Name: publication; Type: TABLE; Schema: public; Owner: projetmobile
--

CREATE TABLE public.publication (
    id_publication integer NOT NULL,
    titre text,
    description text,
    url_image text,
    latitude double precision,
    longitude double precision,
    creation time with time zone DEFAULT now(),
    id_utilisateur integer
);


ALTER TABLE public.publication OWNER TO projetmobile;

--
-- Name: publication_id_publication_seq; Type: SEQUENCE; Schema: public; Owner: projetmobile
--

CREATE SEQUENCE public.publication_id_publication_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.publication_id_publication_seq OWNER TO projetmobile;

--
-- Name: publication_id_publication_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: projetmobile
--

ALTER SEQUENCE public.publication_id_publication_seq OWNED BY public.publication.id_publication;


--
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: projetmobile
--

CREATE TABLE public.utilisateur (
    id_utilisateur integer NOT NULL,
    nom text,
    pseudonyme text,
    url_image text,
    mdp_hash text,
    creation timestamp with time zone DEFAULT now()
);


ALTER TABLE public.utilisateur OWNER TO projetmobile;

--
-- Name: utilisateur_id_utilisateur_seq; Type: SEQUENCE; Schema: public; Owner: projetmobile
--

CREATE SEQUENCE public.utilisateur_id_utilisateur_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utilisateur_id_utilisateur_seq OWNER TO projetmobile;

--
-- Name: utilisateur_id_utilisateur_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: projetmobile
--

ALTER SEQUENCE public.utilisateur_id_utilisateur_seq OWNED BY public.utilisateur.id_utilisateur;


--
-- Name: publication id_publication; Type: DEFAULT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.publication ALTER COLUMN id_publication SET DEFAULT nextval('public.publication_id_publication_seq'::regclass);


--
-- Name: utilisateur id_utilisateur; Type: DEFAULT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id_utilisateur SET DEFAULT nextval('public.utilisateur_id_utilisateur_seq'::regclass);


--
-- Data for Name: aime; Type: TABLE DATA; Schema: public; Owner: projetmobile
--

COPY public.aime (id_utilisateur, id_publication) FROM stdin;
\.


--
-- Data for Name: publication; Type: TABLE DATA; Schema: public; Owner: projetmobile
--

COPY public.publication (id_publication, titre, description, url_image, latitude, longitude, creation, id_utilisateur) FROM stdin;
5	testTitre	testDescription	\N	0.100000000000000006	0.100000000000000006	22:01:06.402393-04	1
\.


--
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: projetmobile
--

COPY public.utilisateur (id_utilisateur, nom, pseudonyme, url_image, mdp_hash, creation) FROM stdin;
1	Dylan	Mitsuriou	\N	foodshot	\N
4	testNom	testPseudonyme	\N	testMDP	2018-10-11 22:00:40.536588-04
\.


--
-- Name: publication_id_publication_seq; Type: SEQUENCE SET; Schema: public; Owner: projetmobile
--

SELECT pg_catalog.setval('public.publication_id_publication_seq', 5, true);


--
-- Name: utilisateur_id_utilisateur_seq; Type: SEQUENCE SET; Schema: public; Owner: projetmobile
--

SELECT pg_catalog.setval('public.utilisateur_id_utilisateur_seq', 10, true);


--
-- Name: aime aime_pkey; Type: CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.aime
    ADD CONSTRAINT aime_pkey PRIMARY KEY (id_utilisateur, id_publication);


--
-- Name: publication publication_pkey; Type: CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.publication
    ADD CONSTRAINT publication_pkey PRIMARY KEY (id_publication);


--
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id_utilisateur);


--
-- Name: fki_aime_id_publication_fkey; Type: INDEX; Schema: public; Owner: projetmobile
--

CREATE INDEX fki_aime_id_publication_fkey ON public.aime USING btree (id_publication);


--
-- Name: fki_aime_id_utilisateur_fkey; Type: INDEX; Schema: public; Owner: projetmobile
--

CREATE INDEX fki_aime_id_utilisateur_fkey ON public.aime USING btree (id_utilisateur);


--
-- Name: fki_publication_id_utilisateur_fkey; Type: INDEX; Schema: public; Owner: projetmobile
--

CREATE INDEX fki_publication_id_utilisateur_fkey ON public.publication USING btree (id_utilisateur);


--
-- Name: aime aime_id_publication_fkey; Type: FK CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.aime
    ADD CONSTRAINT aime_id_publication_fkey FOREIGN KEY (id_publication) REFERENCES public.publication(id_publication) ON DELETE CASCADE;


--
-- Name: aime aime_id_utilisateur_fkey; Type: FK CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.aime
    ADD CONSTRAINT aime_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.utilisateur(id_utilisateur) ON DELETE CASCADE;


--
-- Name: publication publication_id_utilisateur_fkey; Type: FK CONSTRAINT; Schema: public; Owner: projetmobile
--

ALTER TABLE ONLY public.publication
    ADD CONSTRAINT publication_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.utilisateur(id_utilisateur) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

