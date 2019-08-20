
@echo off
@pushd %~dp0

set JAVA_PATH="C:\Program Files\Java\jdk1.8.0_162"

powershell.exe Start-Process -FilePath '"%JAVA_PATH%\bin\javaw.exe"' -Argument '-jar FXGetMAC.jar' -verb RunAs

::unmount
@popd