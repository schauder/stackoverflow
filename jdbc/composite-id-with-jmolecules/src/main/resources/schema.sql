create table service (
    tenant_id uuid,
    id uuid,
    name varchar(255),
    version int,
    primary key (tenant_id, id)
);
