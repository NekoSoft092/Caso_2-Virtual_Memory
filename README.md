# Proyecto Java con Maven

## Requisitos

- Java Development Kit (JDK) 17 o superior.
- Apache Maven.

Comprueba que estén instalados con:

```bash
java -version
mvn -version
```

## Quick start

### Windows

Instala [Eclipse Temurin JDK 17](https://adoptium.net/temurin/releases/?version=17) y
[Apache Maven](https://maven.apache.org/download.cgi). Si usas
[winget](https://learn.microsoft.com/windows/package-manager/winget/), puedes
instalarlos desde PowerShell o Símbolo del sistema:

```powershell
winget install --id EclipseAdoptium.Temurin.17.JDK -e
winget install --id Apache.Maven -e
```

Cierra y vuelve a abrir la terminal, verifica la instalación y, desde la carpeta
del proyecto, ejecuta:

```powershell
java -version
mvn -version
.\cmd\windows.bat
```

### macOS

Instala [Homebrew](https://brew.sh/) si aún no lo tienes. Después, instala Java
17 y Maven:

```bash
brew install --cask temurin@17
brew install maven
```

Desde la carpeta del proyecto, verifica la instalación y ejecuta el script:

```bash
java -version
mvn -version
bash cmd/macos.sh
```

Los scripts de `cmd` ejecutan `mvn clean compile exec:java` desde la raíz del
proyecto, así que también pueden invocarse desde otra carpeta indicando su ruta.
Para compilar y ejecutar manualmente, consulta la sección siguiente.

## Compilar y ejecutar

Desde la carpeta raíz del proyecto, ejecuta:

```bash
mvn clean compile exec:java
```

Maven limpiará los archivos de compilaciones anteriores, compilará el proyecto y ejecutará la clase principal `com.tics.Main`.

También puedes ejecutar cada paso por separado:

```bash
mvn clean compile
mvn exec:java
```

Al ejecutar, el programa solicitará estos parámetros:

```text
Filas (NF) [300]:
Columnas (NC) [20]:
Tamaño del vector clave (NV) [142]:
Tamaño de página (TP, en bytes) [256]:
Número de pasadas [5]:
Nombre del archivo de salida [salida.txt]:
```

Presiona Enter para usar el valor de ejemplo que aparece entre corchetes. Los
parámetros numéricos deben ser enteros mayores que cero. Si el programa se
ejecuta sin entrada interactiva disponible, completará los parámetros restantes
con esos mismos valores de ejemplo. La traza siempre se guarda dentro de la
carpeta `output`; si se proporciona una ruta, solo se utiliza el nombre del
archivo.

El archivo de salida comienza con los campos `TP`, `NF`, `NC`, `NV`,
`numPasadas`, `NR` y `NP`. Cada referencia posterior tiene el formato
`[etiqueta],pagina,desplazamiento`. La matriz se mapea en orden por filas y el
vector se ubica inmediatamente después de ella. Por cada elemento cifrado se
registran la lectura de la matriz, la lectura de la clave y la escritura de la
matriz. En cada pasada se ejecuta primero el recorrido por filas y luego el
recorrido por columnas. Por eso `NR` es `6 * NF * NC * numPasadas`; `NP` es el
techo de `(NF * NC + NV) / TP`.