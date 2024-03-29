@echo off
start cmd /c  Experiment_1\batch.bat -b
start cmd /c  Experiment_2\batch.bat -b
start cmd /c  Experiment_3\batch.bat -b
start cmd /c  Experiment_4\batch.bat -b
start cmd /c  Experiment_5\batch.bat -b


:top

set fin=1

powershell sleep 5
rem 実行したい処理内容
for /D %%a in (Experiment_*) do (
	echo %%a\FIN
	if not exist %%a\FIN (
		set fin=0
	)
)
echo %fin%


rem 終了判定
if %fin%==0 (
	goto top
)

rem pause
rem FINの削除
for /D %%a in (Experiment_*) do (
	del /Q %%a\FIN
)

exit(0)