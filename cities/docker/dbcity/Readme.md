# Docker: dbcity

## Start container
in directory docker/dbcity

```
docker compose up -d
```

## Check container
inside container db (exec)

```
psql -U city -d dbcity
\d
-- SQL queries:
select count(*) from city;
select * from city where nom_standard = 'Avignon';
\q
```