# MaMoDaR - Management Molekularer Daten im Research Data Life Cycle

For more information check our [official website](https://www.rki.de/DE/Content/Institut/OrgEinheiten/MF/MF4/MaMoDaR.html)

## How to build
Here we describe how to build this project on *Microsoft Windows* using *Visual Studio Code*. Similar requirements have to be meet to build this project in Linux or when using any other IDE.  

### Requirements

- [Git](https://git-scm.com/download/win)
- [PostgreSQL](https://www.postgresql.org/download/windows/)
- [JDK >= 11](https://adoptopenjdk.net/)
- [Gradle](https://gradle.org/install/#manually)
- [Node.js](https://nodejs.org/en/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [A RDMO server](https://rdmorganiser.github.io/) and [token](https://rdmo.readthedocs.io/en/latest/administration/api.html?highlight=token#authentication)

### Create a PostgreSQL database

1. Start a shell (*cmd.exe*)
2. Navigate to your PostgreSQL installation path `$postgres` (i.e. `C:\[USERPATH]\bin\pgsql`)
3. Go into the `\bin` subdirectory (`cd bin`)
4. Create a data directory (`mkdir data`)
5. Start PostgreSQL `pg_ctl.exe start -D "C:\[USERPATH]\bin\pgsql\data"`
6. Create the *mamodar* database `createdb.exe mamodar`
7. (optional) Create the user *mamodaruser*
8. (optional) Use `misc/generate_database.sql` to generate all tables and views in the *mamodar* database (`psql -U postgres -d mamodar -a -f misc/generate_database.sql`)

### Set-Up

1. Start Visual Studio Code
2. Install two *Extensions* (fifth icon on the left bar):  
   2.1 *Gradle Tasks* (richardwillis.vscode-gradle)  
   2.1 *npm* (eg2.vscode-npm-script)  
3. Update `server\application.properties`   
   3.1. Fill in `spring.datasource.username` and `spring.datasource.password` using the PostgreSQL data  
   3.2. Update `rdmo.token` and `rdmo.url` to point to a previously setup RDMO server
4. In *Source Control* (third icon on the left bar)  
   4.1. Clone repository: https://github.com/cuehs/mamodar.git  
   4.2. Select a  local folder to clone code to (no server!)  
   4.3. Open folder in Visual Studio Code  
5. Go to *Terminal*, Run task: `npm: build -web`  

### Run project

1. Start database (if not started already)  
   1.1. Start a shell (*cmd.exe*)  
   1.2. Navigate to your PostgreSQL installation path (i.e. `C:\[USERPATH]\bin\pgsql`)  
   1.3. Start PostgreSQL `pg_ctl.exe start -D "C:\[USERPATH]\bin\pgsql\data"`  
2. Start backend  
   2.1. In Visual Studio Code: Got to Terminal in the Taskbar at the top  
   2.2. Run task: `gradle:server:bootRun`  
3. Start frontend  
   3.1. In Visual Studio Code: Got to Terminal in the Taskbar at the top  
   3.2. Run task: `npm: start -web`  
4. Open a browser at http://localhost:4200/  

## How to run

Here we describe how to run *MaMoDaR* on an *Ubuntu 18.04 LTS* server. 

### Prerequisites 
For the DataLinker we need an *apache server*, a *JRE*, and a *postgreSQL database*.  
To install these packages on  Debian/Ubuntu use:
`sudo apt install apache2 postgresql openjdk-11-jre`
For the required packages of RDMO see the [RDMO documentation](https://rdmo.readthedocs.io/en/latest/installation/index.html). We suggest that to create a RDMO instance first before starting the DataLinker instance.

### Configure PostgreSQL database
Connect to your server and login into postgres `sudo -u postgres psql postgres`, in postgres:  
1. create the mamodar database `CREATE DATABASE mamodar;`
2. connect to the mamodar database `\connect mamodar`
3. create a mamodar postgres user `CREATE ROLE mamodar;`
4. set a password for the mamodar user `\password mamodar`
5. create all tables, views, and functions by calling the content of the file `misc\generate_database.sql`.
6. quit postgres `\q`

### Build and start DataLinker frontend

1. in `web\src\environments\environment.prod.ts` set `appUrl: ` to the URL of your server.
2. build the frontend `npm buildprod` or `ng build --prod`
3. copy the files in `web\dist\web` to the apache web folder on your server (usually `/var/www/html/`)

### Build and start DataLinker backend
1. create a non-sudo user *datalinker* on the server `sudo adduser datalinker --home /srv/datalinker`
2. build the backend `gradle mamodar:server bootJar`
3. copy the jar from `server/build/libs` to the home directory of the *datalinker* user
4. copy `server/application.properties` to the home directory of the *datalinker* user 
5. open the copied *application.properties* in a text editor  
   5.1. set `spring.datasource.username` and `spring.datasource.password` to the mamodar user created in psql  
   5.2. set `rdmo.url` and `rdmo.token` to the URL of your RDMO instance and add the correct [token](https://rdmo.readthedocs.io/en/latest/administration/api.html)  
6. start the backend as `nohup java -jar server-0.0.3-SNAPSHOT.jar  -Dspring.config.location=/application.properties > log 2> err &`   
   6.1. make sure the user *datalinker*  is running the process  
   
## RDMO

To install and configure RDMO please refer to the [RDMO documentation](https://rdmo.readthedocs.io/en/latest/installation/index.html).

## Technical documentation

[JavaDoc](http://h2879007.stratoserver.net/doc/javadoc)   
[Compodoc](http://h2879007.stratoserver.net/doc/compdoc)
