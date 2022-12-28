# shopping

## Install database:

1. Install postgres version 11, which is the version we currently use in production:

```bash
#!bash
$ brew update
$ brew install postgresql@11
$ brew services start postgres
```

2. Create the 'postgres' superuser:

```bash
#!bash
$ createuser -s postgres
```

3. Create database & user

```bash
#!bash
$ psql -U postgres
postgres=# create database shopping;
postgres=# create user myusername with encrypted password 'mypassword';
postgres=# grant all privileges on database shopping to myusername;
```

## Useful Commands

```bash
# start service (on SpringBoot)
$ ./gradlew bootRun -Pdev

# unit test
$ ./gradlew clean test
```
