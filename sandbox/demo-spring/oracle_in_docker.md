# How to run Oracle Database in Docker container

Helpful links:

- [Oracle Container Registry](https://container-registry.oracle.com),
- [How to run Oracle Database in a Docker Container using Docker Compose](https://collabnix.com/how-to-run-oracle-database-in-a-docker-container-using-docker-compose/),
- [docker pull container-registry.oracle.com - access denied (or) Unauthorized](https://www.middlewareinventory.com/blog/docker-pull-container-registry-oracle-com-access-denied-or-unauthorized/),
- [How to Install Oracle Database 21c on Docker](https://docs.oracle.com/en/database/oracle/oracle-database/21/deeck/oracle-database-enterprise-edition-installation-guide-docker-containers-oracle-linux.pdf) :file_folder:,
- [Oracle by Example brandingAccess the Database Home Page in EM Database Express](https://docs.oracle.com/en/database/oracle/oracle-database/tutorial-access-em/index.html?opt-release-19c?learningpath=true&appuser=nobody&appsession=365245126555&contentid=26468&activityname=Access%20the%20Database%20Homepage%20in%20EM%20Database%20Express&eventid=6362),
- [Use VSCode with Oracle Database](https://youtu.be/u4hCAMzOTH4),
- [How to Resolve ORA-65096: invalid common user or role name](https://logic.edchen.org/how-to-resolve-ora-65096-invalid-common-user-or-role-name/),
- [Create JDBC Data Source or JDBC URL database connection to an Oracle Pluggable Database](https://technology.amis.nl/database/create-jdbc-data-source-or-jdbc-url-database-connection-to-an-oracle-pluggable-database/).

Used commands:

To download image from Oracle Container Registry you need to login first:

```cmd
docker login container-registry.oracle.com
```

Then you can pull image:

```cmd
docker pull container-registry.oracle.com/database/express:latest
docker pull container-registry.oracle.com/database/enterprise:latest
```

Finally you can run container:

```cmd
docker run -d --name oracle-db -p 1521:1521 container-registry.oracle.com/database/enterprise:latest
```

Or use docker-compose.yml file:

```yml
version: '3.1'
services:
  oracle-db:
    image: container-registry.oracle.com/database/enterprise:latest
    environment:
      - ORACLE_SID=ORCLCDB
      - ORACLE_PDB=ORCLPDB1
      - ORACLE_PWD=Oracle_123
    ports:
      - 1521:1521
    volumes:
      - oracle-data:/opt/oracle/oradata
      - oracle-backup:/opt/oracle/backup
    healthcheck:
      test: ["CMD", "sqlplus", "-L", "sys/Oracle_123@//localhost:1521/ORCLCDB as sysdba", "@healthcheck.sql"]
      interval: 30s
      timeout: 10s
      retries: 5

volumes:
  oracle-data:
  oracle-backup:
```

Connecting to the Oracle database from the container terminal:

```cmd
sqlplus / as sysdba
```

To create new user in pluggable database.

First connect to the database:

```cmd
sqlplus sys/Oracle_123@//localhost:1521/ORCLPDB1 as sysdba
```

Then change container to pluggable database:

```sql
ALTER SESSION SET CONTAINER=ORCLPDB1;
```

Then create new user in pluggable database and grant privileges:

```sql
CREATE USER app_user IDENTIFIED BY password;
GRANT CONNECT, RESOURCE, DBA TO app_user; -- this is not recommended in production!
```

Other tips & tricks:

If your Docker Desktop uses to much memory under Windws 10 with WSL2, do the following:

Try these steps:

1. In C:\Users\<yourusername>, create `.wslconfig` file (skip if already there). 
2. Edit the file and add the following lines:

```ini
[wsl2]
memory=1GB # Limits VM memory in WSL 2. If you want less than 1GB, use something like 500MB, not 0.5GB
processors=2 # Makes the WSL 2 VM use two virtual processors
```

Check if the file is in UTF-8. Some guys had to change the end-of-line type to LF also (Mine was fine with CR-LF).


3. Quit Docker and in PowerShell (admin), run:

```cmd
wsl --shutdown
Restart-Service LxssManager
```

4. Run things again. Inside wsl, run `free -mh` and check total value, it should be 1GB as configured above
