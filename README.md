# AplicacionHistorialMedico

## Link Github: https://github.com/aalbertovalera48/AplicacionHistorialMedico.git
### Hecho por: Alberto Valera 
### Usuario y Contraseña para iniciar sesión:
**Usuario:** alberto@gmail.com

**Contraseña:** 123456

## Descripción
AplicacionHistorialMedico es una aplicación móvil para gestionar el historial médico de los usuarios. La aplicación permite a los usuarios iniciar sesión, agregar, ver y almacenar datos médicos de manera segura utilizando Firebase.

## Estructura del Proyecto

### Clases

#### `LoginActivity`
- **Descripción**: Actividad principal para el inicio de sesión de los usuarios.
- **Funciones**:
  - `onCreate(Bundle?)`: Inicializa la actividad y configura los elementos de la interfaz de usuario.
  - `signInWithEmailAndPassword(String, String)`: Autentica al usuario con Firebase Authentication.

#### `MainActivity`
- **Descripción**: Actividad principal que muestra el formulario para agregar datos médicos y la lista de datos médicos almacenados.
- **Funciones**:
  - `onCreate(Bundle?)`: Inicializa la actividad y configura los elementos de la interfaz de usuario.
  - `saveMedicalData(String, String)`: Guarda los datos médicos en Firebase Database.
  - `loadMedicalData()`: Carga los datos médicos desde Firebase Database.

#### `MedicalDataListActivity`
- **Descripción**: Actividad que muestra una lista de todos los datos médicos almacenados.
- **Funciones**:
  - `onCreate(Bundle?)`: Inicializa la actividad y configura los elementos de la interfaz de usuario.
  - `loadMedicalData()`: Carga los datos médicos desde Firebase Database.

#### `AddMedicalDataActivity`
- **Descripción**: Actividad que permite al usuario agregar nuevos datos médicos.
- **Funciones**:
  - `onCreate(Bundle?)`: Inicializa la actividad y configura los elementos de la interfaz de usuario.
  - `saveMedicalData(String, String)`: Guarda los datos médicos en Firebase Database.

#### `MedicalDataFormActivity`
- **Descripción**: Actividad que muestra un formulario para agregar o editar datos médicos.
- **Funciones**:
  - `onCreate(Bundle?)`: Inicializa la actividad y configura los elementos de la interfaz de usuario.

### Archivos XML

#### `AndroidManifest.xml`
- **Descripción**: Archivo de manifiesto que declara las actividades y permisos necesarios para la aplicación.
- **Elementos Clave**:
  - Declaración de actividades (`LoginActivity`, `MainActivity`, `MedicalDataListActivity`, `AddMedicalDataActivity`, `MedicalDataFormActivity`).
  - Permiso para acceder a Internet.

#### `activity_login.xml`
- **Descripción**: Layout para la actividad de inicio de sesión.
- **Elementos Clave**:
  - `EditText` para el correo electrónico y la contraseña.
  - `Button` para iniciar sesión.

#### `activity_main.xml`
- **Descripción**: Layout para la actividad principal.
- **Elementos Clave**:
  - `LinearLayout` para el formulario de datos médicos.
  - `RecyclerView` para la lista de datos médicos.

#### `themes.xml`
- **Descripción**: Archivo de temas que define el tema de la aplicación.
- **Elementos Clave**:
  - `Theme.AplicacionHistorialMedico`: Tema principal de la aplicación basado en `Theme.AppCompat.Light.NoActionBar`.

## Conclusiones
La aplicación AplicacionHistorialMedico proporciona una solución completa para la gestión de historiales médicos, utilizando Firebase para la autenticación y el almacenamiento de datos. La estructura del proyecto está bien organizada, con actividades separadas para diferentes funcionalidades y un uso adecuado de layouts XML para la interfaz de usuario.
