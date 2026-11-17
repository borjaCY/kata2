# Kata 2 - Entrada, salida y carga de datos (IS2)

## Objetivo de la entrega
El objetivo de esta práctica es desarrollar una aplicación en Java capaz de leer un conjunto de datos tabulares (TSV) desde distintos orígenes mediante flujos de entrada (`InputStream`), transformar cada registro en entidades de dominio, realizar una agrupación estadística sobre los datos y escribir el resultado de forma desacoplada a través de flujos de salida (`OutputStream`).

---

## Vídeo explicativo
* **Enlace al vídeo (máx. 7 min):** [PEGAR AQUÍ EL ENLACE AL VÍDEO]

---

## Requisitos y dependencias
* **Lenguaje:** Java (JDK 21 o superior).
* **Gestor de dependencias y compilación:** Apache Maven (`pom.xml`).
* **Dependencias externas:** Ninguna (implementado íntegramente con la biblioteca estándar de Java: `java.io`, `java.net`, `java.util.stream`).

---

## Cómo compilar y ejecutar

### Desde terminal (Maven)
1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com
   cd kata2
   ```
2. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```
3. **Ejecutar la aplicación:**
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

### Desde IntelliJ IDEA
1. Abrir el proyecto seleccionando el archivo `pom.xml`.
2. Asegurarse de que la carpeta `src/resources` esté marcada como **Resources Root** (para la repetición 2) o que el fichero `employees.tsv` exista en la raíz del proyecto (para la repetición 3).
3. Ejecutar el método `main` de la clase `Main`. *(Opcionalmente, ejecutar `Main2` para descargar una copia fresca del dataset remoto).*

---

## Estructura del proyecto
* **`model.Employee`**: Entidad de dominio inmutable que representa cada fila del dataset (`name`, `gender`, `status`).
* **`model.Gender` / `model.MaritalStatus`**: Tipos enumerados que garantizan la integridad de los valores categóricos y gestionan de forma segura las celdas vacías.
* **`io.EmployeeReader`**: Interfaz que desacopla la lógica de negocio del origen concreto de los datos.
* **`io.URLEmployeeReader`**: Implementación encargada de abrir el `InputStream`, convertir los bytes en líneas de texto mediante `BufferedReader` y mapear cada fila separada por tabuladores (`\t`) a objetos `Employee`.
* **`Main` / `Main2`**: Clases principales encargadas de conectar el origen de entrada, la transformación mediante *Java Streams* (`groupingBy` y `counting`) y el flujo de salida (`OutputStream`).

---

## Dinámica de repetición y variaciones realizadas
El desarrollo se ha dividido en **tres repeticiones** organizadas en distintas ramas del repositorio para practicar el desacoplamiento entre entrada, transformación y salida:

| Rama | Repetición | Origen (`InputStream`) | Transformación | Destino (`OutputStream`) |
| :--- | :--- | :--- | :--- | :--- |
| **`develop1`** | Repetición 1 (Base) | URL remota en GitHub (`url.openStream()`) | Lectura y mapeo básico de empleados (`name`, `gender`). | Salida estándar por consola (`System.out`). |
| **`develop2`** | Repetición 2 (Variación 1) | Recurso interno (`getResource("/employees.tsv")`) | Ampliación de entidad con `MaritalStatus` y conteo agrupado. | Persistencia en fichero (`FileOutputStream` -> `resultado.txt`). |
| **`develop3`** / **`master`** | Repetición 3 (Variación 2) | Fichero local en disco (`employees.tsv`) | Filtrado de valores nulos y conteo agrupado por estado civil. | Escritura de bytes en consola mediante `OutputStream` (`System.out`). |

---

## Evidencias de verificación y pruebas
Durante el desarrollo se han verificado los siguientes escenarios:
1. **Carga desde distintos orígenes:** Comprobación de que la lectura desde URL remota, carpeta `resources` y fichero local produce exactamente los mismos 25 registros sin alterar la lógica de transformación.
2. **Manejo de datos incompletos:** Validación de celdas vacías en las columnas de género y estado civil (ej. registros de *George*, *Vin* y *Kate*) mediante conversión controlada a `null` y filtrado previo en el `Stream`, evitando excepciones `IllegalArgumentException`.
3. **Verificación de salida (consola y fichero):** Comprobación de que tanto el archivo generado en la repetición 2 (`resultado.txt`) como la salida por consola de la repetición 3 producen exactamente el mismo resultado:
   ```text
   Total empleados: 25
   Por estado civil: {Married=10, Single=8, Divorced=5}
   ```
4. **Control de errores de ruta:** Verificación del lanzamiento controlado de excepciones (`NullPointerException` / `IOException`) cuando el recurso o fichero de origen no existe.
