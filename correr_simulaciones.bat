@echo off
> resultados.csv echo archivo;marcos;politica;resultado
for %%f in (output\ref_*.txt) do (
  for %%m in (4 8 16) do (
    for %%p in (FIFO FIFOModified LRU) do (
      echo Corriendo %%~nf  marcos=%%m  %%p
      for /f "delims=" %%r in ('java -jar simulador.jar %%f %%m %%p ^| findstr Total') do (
        >> resultados.csv echo %%~nf;%%m;%%p;%%r
      )
    )
  )
)
echo Listo
