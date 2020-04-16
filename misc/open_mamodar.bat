@echo on
#SET @URL=http://localhost:4200^^^\index.html?path=%1^^^&user=%USERNAME%
ECHO %@URL%
start "" %@URL%
pause
