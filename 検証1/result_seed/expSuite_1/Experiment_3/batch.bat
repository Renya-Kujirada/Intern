@echo off

rem 開始ファイルナンバー
set startNember=40

rem 試行回数-1（実行ファイル数）
set endIteration=59

rem インクリメント数
set incrementnum=1

pushd %0\..
cls

for /L %%i in (%startNember%, %incrementnum%, %endIteration%) do (
	java -jar main.jar %%i
	echo %%i times finished
)


rem change file name
set mm=%date:~5,2%
set dd=%date:~8,2%

set time2=%time: =0%
 
set hh=%time2:~0,2%
set mn=%time2:~3,2%
set ss=%time2:~6,2%

set changeName=%mm%%dd%-%hh%%mn%-%ss%

rename data\result\evaluationOfAllBenchmark.csv evaluationOfAllBenchmark_%changeName%.csv

type nul > .\FIN