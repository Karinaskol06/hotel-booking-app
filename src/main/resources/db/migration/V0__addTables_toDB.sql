-- ============================
-- CREATE TABLES
-- ============================

create table apartment_classes
(
    id                bigint not null auto_increment,
    apartment_class   varchar(70),
    class_description varchar(200),
    facilities        varchar(255),
    link_img          varchar(255),
    primary key (id)
) engine = InnoDB;

create table apartments
(
    id                 bigint    not null auto_increment,
    apartment_number   integer   not null,
    area               float(53) not null,
    capacity           integer   not null,
    link_img           varchar(255),
    name               varchar(255),
    num_of_rooms       integer   not null,
    price_per_night    float(53) not null,
    apartment_class_id bigint,
    primary key (id)
) engine = InnoDB;

create table bookings
(
    id           bigint not null auto_increment,
    check_in     varchar(255),
    check_out    varchar(255),
    date_created datetime(6),
    status       enum ('CANCELLED','CONFIRMED','NOT_PROCESSED'),
    client_id    bigint,
    invoice_id   bigint,
    payment_id   bigint,
    primary key (id)
) engine = InnoDB;

create table apartment_has_booking
(
    id           bigint  not null auto_increment,
    apartment_id bigint,
    booking_id   bigint,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    primary key (id)
) engine = InnoDB;

create table users
(
    id       bigint not null auto_increment,
    username varchar(50),
    password varchar(255),
    primary key (id)
) engine = InnoDB;

create table clients
(
    id         bigint      not null, -- 1:1 with users.id
    email      varchar(255),
    first_name varchar(60) not null,
    last_name  varchar(60) not null,
    phone      varchar(255),
    primary key (id)
) engine = InnoDB;

create table invoices
(
    id           bigint not null auto_increment,
    date         datetime(6),
    total_amount decimal(38, 2),
    primary key (id)
) engine = InnoDB;

create table payments
(
    id             bigint not null auto_increment,
    payment_method varchar(255),
    invoice_id     bigint,
    primary key (id)
) engine = InnoDB;

create table images
(
    id              bigint not null auto_increment,
    url_img         varchar(255),
    apartmentimg_id bigint,
    primary key (id)
) engine = InnoDB;

create table roles
(
    id        bigint not null auto_increment,
    role_name varchar(255),
    primary key (id)
) engine = InnoDB;

create table users_roles_set
(
    users_set_id bigint not null,
    roles_set_id bigint not null,
    primary key (users_set_id, roles_set_id)
) engine = InnoDB;

-- ============================
-- ADD INDEXES (safe for FK)
-- ============================

create index idx_bookings_invoice_id on bookings (invoice_id);
create index idx_payments_invoice_id on payments (invoice_id);
create index idx_apartment_has_booking_apartment on apartment_has_booking (apartment_id);
create index idx_apartment_has_booking_booking on apartment_has_booking (booking_id);
create index idx_apartments_class on apartments (apartment_class_id);
create index idx_bookings_client_id on bookings (client_id);
create index idx_bookings_payment_id on bookings (payment_id);
create index idx_images_apartment on images (apartmentimg_id);
create index idx_users_roles_user on users_roles_set (users_set_id);
create index idx_users_roles_role on users_roles_set (roles_set_id);

-- ============================
-- ADD CONSTRAINTS
-- ============================

alter table apartments
    add constraint fk_apartments_class foreign key (apartment_class_id) references apartment_classes (id);

alter table apartment_has_booking
    add constraint fk_booking_apartment foreign key (apartment_id) references apartments (id),
    add constraint fk_booking_booking foreign key (booking_id) references bookings (id);

alter table bookings
    add constraint fk_booking_client foreign key (client_id) references clients (id),
    add constraint fk_booking_invoice foreign key (invoice_id) references invoices (id),
    add constraint fk_booking_payment foreign key (payment_id) references payments (id),
    add constraint uq_booking_invoice unique (invoice_id);

alter table clients
    add constraint fk_client_user foreign key (id) references users (id);

alter table images
    add constraint fk_images_apartment foreign key (apartmentimg_id) references apartments (id);

alter table payments
    add constraint fk_payment_invoice foreign key (invoice_id) references invoices (id),
    add constraint uq_payment_invoice unique (invoice_id);

alter table users_roles_set
    add constraint fk_user_role_user foreign key (users_set_id) references users (id),
    add constraint fk_user_role_role foreign key (roles_set_id) references roles (id);
