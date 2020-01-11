@echo off
start cmd /c  Experiment_1\batch.bat -b
start cmd /c  Experiment_2\batch.bat -b
start cmd /c  Experiment_3\batch.bat -b
start cmd /c  Experiment_4\batch.bat -b
start cmd /c  Experiment_5\batch.bat -b


:top

set fin=1

powershell sleep 60
rem Às‚µ‚½‚¢ˆ—“à—e
for /D %%a in (Experiment_*) do (
	echo %%a\FIN
	if not exist %%a\FIN (
		set fin=0
	)
)
echo %fin%


rem I—¹”»’è
if %fin%==0 (
	goto top
)

rem pause
rem FIN‚Ìíœ
for /D %%a in (Experiment_*) do (
	del /Q %%a\FIN
)

exit(0)