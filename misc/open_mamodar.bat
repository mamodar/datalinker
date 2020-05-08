@echo off
SET PARAMETER=%1
echo %PARAMETER:~0,2%
IF "%PARAMETER:~0,2%"=="S:" (
echo S:
  SET @URL=http://localhost:4200^^^\projects?path=%PARAMETER%^^^&location=SAN
  ) ELSE (
  IF "%PARAMETER:~0,2%"=="P:" (
  echo P:
    SET @URL=http://localhost:4200^^^\projects?path=%PARAMETER%^^^&location=SAN
    ) ELSE (
    echo else
    SET @URL=http://localhost:4200^^^\projects?path=%PARAMETER%^^^&location=LOCAL
    )
ECHO %@URL%
start "" %@URL%
pause
