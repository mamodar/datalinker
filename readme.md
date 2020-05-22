# MaMoDaR - Management Molekularer Daten im Research Data Life Cycle

For more information check: https://www.rki.de/DE/Content/Institut/OrgEinheiten/MF/MF4/MaMoDaR.html

## How to build
Here we describe how to build and run this project on Microsoft Windows using Visual Studio Code. Similiar requirements have to be meet to build this project in Linux or when using any other IDE.  

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
