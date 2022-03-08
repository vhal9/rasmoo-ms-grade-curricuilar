-- used in tests that use SQL
create table oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

create table oauth_client_token (
    token_id VARCHAR(256),
    token BYTEA,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256)
);

create table oauth_access_token (
    token_id VARCHAR(256),
    token BYTEA,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication BYTEA,
    refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
    token_id VARCHAR(256),
    token BYTEA,
    authentication BYTEA
);

create table oauth_code (
    code VARCHAR(256), authentication BYTEA
);

create table oauth_approvals (
    userId VARCHAR(256),
    clientId VARCHAR(256),
    scope VARCHAR(256),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
    appId VARCHAR(256) PRIMARY KEY,
    resourceIds VARCHAR(256),
    appSecret VARCHAR(256),
    scope VARCHAR(256),
    grantTypes VARCHAR(256),
    redirectUrl VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additionalInformation VARCHAR(4096),
    autoApproveScopes VARCHAR(256)
);

insert into	oauth_client_details (client_id, client_secret, scope, authorized_grant_types, resource_ids, authorities,
    access_token_validity, refresh_token_validity, web_server_redirect_uri,additional_information, autoapprove)

values ('cliente-web', '$2a$10$XFNAXsGXXyiKA7OD2jpq3ulKYOtr2VGEscFr/I7xdg1uUZkSHR4j6','cw_logado,cw_nao_logado',
        'password,client_credentials', 'gradeControll','cw_logado,cw_nao_logado', 3601, -1, '', null, 'false')
