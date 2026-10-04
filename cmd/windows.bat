@echo off
setlocal
pushd "%~dp0.."

mvn clean compile exec:java
set "RESULT=%ERRORLEVEL%"

popd
exit /b %RESULT%
