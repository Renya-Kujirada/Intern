@echo off

rem 開始Experimentファイルナンバー
set startNember=10

rem 実行ファイル数
set endIteration=11

rem インクリメント数
set incrementnum=1

for /L %%i in (%startNember%, %incrementnum%, %endIteration%) do (
	echo expSuite_%%i
	cd expSuite_%%i
	start /wait execute_all_batch.bat
	echo %%i times finished
	cd ..
)

