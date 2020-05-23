@echo off
title DemoAut Project
CD /D %root%
echo %cd%
pause
call mvn clean
echo mavenClean
call mvn verify
echo mavenVerify
pause
cd test-output
echo %cd%
pause
Echo Results html file
start ExtentReport.html
pause
