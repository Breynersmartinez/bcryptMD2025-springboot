# bcrypt2025-springboot
Authentication system with Spring Boot, MySQL, and bcrypt encryption.

###  **"Evaluación del rendimiento del algoritmo bcrypt en la derivación de contraseñas de usuario utilizando una base de datos MySQL durante el proceso de login para descubrir su comportamiento al aplicar distintos parámetros 'salt'."**



- **Objetivo**: Evaluar cómo el algoritmo bcrypt se comporta (en seguridad y rendimiento) cuando se cambian los valores del parámetro `salt`, usando una base de datos MySQL en los procesos de registro y login.
  
- **Tecnologías base**:
  - Lenguaje de programación: **Java**
  - Motor de base de datos: **MySQL**
  - Evaluación de rendimiento en CPU y RAM
  - Entorno Linux (máquina virtual)
  
- **Metodología**:
  - Crear un entorno de pruebas (máquina virtual Linux).
  - Ejecutar scripts para registrar y loguear usuarios.
  - Medir tiempos de procesamiento, uso de CPU y RAM.
  - Analizar diferentes configuraciones de `salt`.
  - Recoger datos, analizarlos estadísticamente y presentar recomendaciones.

