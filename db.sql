create table MIGRATIONS
(
    ID        NUMBER(10)    not null
        constraint MIGRATIONS_ID_PK
            primary key,
    MIGRATION VARCHAR2(255) not null,
    BATCH     NUMBER(10)    not null
)
/

create trigger MIGRATIONS_ID_TRG
    before insert
    on MIGRATIONS
    for each row
begin
    if :new.ID is null then
        select migrations_id_seq.nextval into :new.ID from dual;
    end if;
end;
/

create table CANDIDATS
(
    ID_CAND              NUMBER(19)    not null
        constraint CANDIDATS_ID_CAND_PK
            primary key,
    PHOTO_CANDIDAT       VARCHAR2(255) not null,
    NOM_CANDIDAT         VARCHAR2(255) not null,
    PRENOM_CANDIDAT      VARCHAR2(255) not null,
    DATE_NAISS_CANDIDAT  VARCHAR2(255) not null,
    LIEU_NAIS_CANDIDAT   VARCHAR2(255) not null,
    NATIONALITE_CANDIDAT VARCHAR2(255) not null,
    CONTACT_CANDIDAT     VARCHAR2(255) not null,
    MAIL_CANDIDAT        VARCHAR2(255) not null
)
/

create trigger CANDIDATS_ID_CAND_TRG
    before insert
    on CANDIDATS
    for each row
begin
    if :new.ID_CAND is null then
        select candidats_id_cand_seq.nextval into :new.ID_CAND from dual;
    end if;
end;
/

create table PLANTATION_CANDIDATS
(
    ID_PLANT                NUMBER(19)    not null
        constraint PLANTATIO_CANDIDAT_ID_PLAN_PK
            primary key,
    LOCALISATION_PLANTATION VARCHAR2(255) not null,
    SUPERFICIE_PLANTATION   VARCHAR2(255) not null,
    CERTIFICAT_PROPRIETE    VARCHAR2(255) not null
)
/

create trigger PLANTATION_CANDIDATS_ID_PLANT_
    before insert
    on PLANTATION_CANDIDATS
    for each row
begin
    if :new.ID_PLANT is null then
        select plantation_candidats_id_plant_.nextval into :new.ID_PLANT from dual;
    end if;
end;
/

create table EMPLOYE_CANDIDATS
(
    ID_EMPL_CAND       NUMBER(19)             not null
        constraint EMPLOY_CANDIDAT_ID_EMP_CAN_PK
            primary key,
    NOMBRE_EMPLOYE     NUMBER(10)             not null,
    NOMBRE_FEMME       NUMBER(10)             not null,
    SALAIRE_MOYEN      NUMBER(10)             not null,
    MINEUR             NUMBER(10) default '1' not null,
    CERTIFICAT_EMPLOYE VARCHAR2(255)          not null
)
/

create trigger EMPLOYE_CANDIDATS_ID_EMPL_CAND
    before insert
    on EMPLOYE_CANDIDATS
    for each row
begin
    if :new.ID_EMPL_CAND is null then
        select employe_candidats_id_empl_cand.nextval into :new.ID_EMPL_CAND from dual;
    end if;
end;
/

create table TYPE_DIPLOMES
(
    ID_TYPE_DIPLOMES      NUMBER(19)    not null
        constraint TYP_DIPLOME_ID_TYP_DIPLOME_PK
            primary key,
    LIBELLE_TYPE_DIPLOMES VARCHAR2(255) not null
)
/

create trigger TYPE_DIPLOMES_ID_TYPE_DIPLOMES
    before insert
    on TYPE_DIPLOMES
    for each row
begin
    if :new.ID_TYPE_DIPLOMES is null then
        select type_diplomes_id_type_diplomes.nextval into :new.ID_TYPE_DIPLOMES from dual;
    end if;
end;
/

create table TYPE_PIECES
(
    ID_PIECE      NUMBER(19)    not null
        constraint TYPE_PIECES_ID_PIECE_PK
            primary key,
    LIBELLE_PIECE VARCHAR2(255) not null
)
/

create trigger TYPE_PIECES_ID_PIECE_TRG
    before insert
    on TYPE_PIECES
    for each row
begin
    if :new.ID_PIECE is null then
        select type_pieces_id_piece_seq.nextval into :new.ID_PIECE from dual;
    end if;
end;
/

create table TYPE_CULTURES
(
    ID_TYPE_CULTURES      NUMBER(19)    not null
        constraint TYP_CULTURE_ID_TYP_CULTURE_PK
            primary key,
    LIBELLE_TYPE_CULTURES VARCHAR2(255) not null
)
/

create trigger TYPE_CULTURES_ID_TYPE_CULTURES
    before insert
    on TYPE_CULTURES
    for each row
begin
    if :new.ID_TYPE_CULTURES is null then
        select type_cultures_id_type_cultures.nextval into :new.ID_TYPE_CULTURES from dual;
    end if;
end;
/

create table METHODE_CULTURES
(
    ID_METHODES_CULTURES      NUMBER(19)    not null
        constraint METH_CULTU_ID_METHO_CULTU_PK
            primary key,
    LIBELLE_METHODES_CULTURES VARCHAR2(255) not null
)
/

create trigger METHODE_CULTURES_ID_METHODES_C
    before insert
    on METHODE_CULTURES
    for each row
begin
    if :new.ID_METHODES_CULTURES is null then
        select methode_cultures_id_methodes_c.nextval into :new.ID_METHODES_CULTURES from dual;
    end if;
end;
/

create table AVOIR_DIPLOMES
(
    ID_AV_DIP NUMBER(19)    not null
        constraint AVOIR_DIPLOMES_ID_AV_DIP_PK
            primary key,
    DIPLOMES  NUMBER(10)    not null,
    ID_CAND   NUMBER(10)    not null,
    NUM_DIP   VARCHAR2(255) not null,
    IMG_DIP   VARCHAR2(255) not null
)
/

create trigger AVOIR_DIPLOMES_ID_AV_DIP_TRG
    before insert
    on AVOIR_DIPLOMES
    for each row
begin
    if :new.ID_AV_DIP is null then
        select avoir_diplomes_id_av_dip_seq.nextval into :new.ID_AV_DIP from dual;
    end if;
end;
/

create table AVOIR_PIECES
(
    ID_AV_PI NUMBER(19)    not null
        constraint AVOIR_PIECES_ID_AV_PI_PK
            primary key,
    PIECES   NUMBER(10)    not null,
    ID_CAND  NUMBER(10)    not null,
    NUM_PI   VARCHAR2(255) not null,
    IMG_PI   VARCHAR2(255) not null
)
/

create trigger AVOIR_PIECES_ID_AV_PI_TRG
    before insert
    on AVOIR_PIECES
    for each row
begin
    if :new.ID_AV_PI is null then
        select avoir_pieces_id_av_pi_seq.nextval into :new.ID_AV_PI from dual;
    end if;
end;
/

create table DOSSIER_INSCRIPTIONS
(
    DOSSIER              NUMBER(19)             not null
        constraint DOSSIE_INSCRIPTION_DOSSIE_PK
            primary key,
    IDENTIFIANT_CANDIDAT VARCHAR2(255)          not null,
    MOT_DE_PASSE         VARCHAR2(255)          not null,
    ID_PLANT             NUMBER(10)             not null,
    ID_CAND              NUMBER(10)             not null,
    ID_EMPL_CAND         NUMBER(10)             not null,
    DATE_INSCRIPTION     VARCHAR2(255)          not null,
    VALIDATION           NUMBER(10) default '1' not null
)
/

create trigger DOSSIER_INSCRIPTIONS_DOSSIER_T
    before insert
    on DOSSIER_INSCRIPTIONS
    for each row
begin
    if :new.DOSSIER is null then
        select dossier_inscriptions_dossier_s.nextval into :new.DOSSIER from dual;
    end if;
end;
/

create table AVOIR_METHODES
(
    AVOIR_METH NUMBER(19) not null
        constraint AVOIR_METHODES_AVOIR_METH_PK
            primary key,
    ID_PLANT   NUMBER(10) not null,
    ID_METHODE NUMBER(10) not null
)
/

create trigger AVOIR_METHODES_AVOIR_METH_TRG
    before insert
    on AVOIR_METHODES
    for each row
begin
    if :new.AVOIR_METH is null then
        select avoir_methodes_avoir_meth_seq.nextval into :new.AVOIR_METH from dual;
    end if;
end;
/

create table AVOIR_CULTURES
(
    ID_AV_CULT   NUMBER(19) not null
        constraint AVOIR_CULTURES_ID_AV_CULT_PK
            primary key,
    ID_PLANT     NUMBER(10) not null,
    ID_TYPE_CULT NUMBER(10) not null
)
/

create trigger AVOIR_CULTURES_ID_AV_CULT_TRG
    before insert
    on AVOIR_CULTURES
    for each row
begin
    if :new.ID_AV_CULT is null then
        select avoir_cultures_id_av_cult_seq.nextval into :new.ID_AV_CULT from dual;
    end if;
end;
/

create table ETATS
(
    ID_TABLE     NUMBER(19)    not null
        constraint ETATS_ID_TABLE_PK
            primary key,
    LIBELLE_ETAT VARCHAR2(255) not null
)
/

create trigger ETATS_ID_TABLE_TRG
    before insert
    on ETATS
    for each row
begin
    if :new.ID_TABLE is null then
        select etats_id_table_seq.nextval into :new.ID_TABLE from dual;
    end if;
end;
/

create table USERS
(
    ID_USERS     NUMBER(19)    not null
        constraint USERS_ID_USERS_PK
            primary key,
    IDENTIFIANT  VARCHAR2(255) not null,
    MOT_DE_PASSE VARCHAR2(255) not null
)
/

create trigger USERS_ID_USERS_TRG
    before insert
    on USERS
    for each row
begin
    if :new.ID_USERS is null then
        select users_id_users_seq.nextval into :new.ID_USERS from dual;
    end if;
end;
/

