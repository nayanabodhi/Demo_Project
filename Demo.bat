@echo off
title DemoAut Project
cd \
echo cd
d:
echo cd
pause
cd D:\BDD_Projects\cucumber-java-skeleton-5.6.0\DemoautProject\
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
