# Tienda Online API

Este README sera una pequeña descripción de lo que se hizo, lo que quedo por
hacer y de los problemas que aparecieron durante el desarrollo (bugs, problemas
con el build system, etc)

## Objetivos 

### Paso 1: Crear proyecto
- [x] Crear proyecto Spring Boot con dependencias:
  - [x] Spring Web (`spring-boot-starter-webmvc`)
  - [x] Spring Data JPA (`spring-boot-starter-data-jpa`)
  - [x] PostgreSQL Driver (`postgresql`)

### Paso 2: Configurar conexión
- [x] Archivo `application.yml` con variables de entorno
- [x] Variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` desde `.env`
- [x] `ddl-auto: update` (actualización automática de tablas)
- [x] `show-sql: true` (mostrar consultas SQL)
- [x] Archivo `.env` con configuración de base de datos

### Paso 3: Crear package `model`
- [x] Entidad **Cliente** (`id`, `nombre`, `apellido`, `correo`, `telefono`, `direccion`)
- [x] Entidad **Producto** (`id`, `nombre`, `descripcion`, `precio`, `stock`, `categoria`)

### Paso 4: Crear package `repository`
- [x] **ClienteRepository** — `save`, `findAll`, `findById`
- [x] **ProductoRepository** — `save`, `findAll`, `findById`, `deleteById`

### Paso 5: Crear package `dto`
- [x] **ClienteRequestDTO** — `nombre`, `apellido`, `correo`, `telefono`, `direccion`
- [x] **ClienteResponseDTO** — `id`, `nombre`, `correo`
- [x] **ProductoRequestDTO** — `nombre`, `descripcion`, `precio`, `stock`, `categoria`
- [x] **ProductoResponseDTO** — `id`, `nombre`, `stock`

### Paso 6: Crear package `mapper`
- [x] **ClienteMapper** — `toEntity` (RequestDTO → Entidad), `toResponse` (Entidad → ResponseDTO)
- [x] **ProductoMapper** — `toEntity` (RequestDTO → Entidad), `toResponse` (Entidad → ResponseDTO)

### Paso 7: Crear package `service`
- [x] **ClienteService** — `crear`, `listar`, `buscarPorId`
- [x] **ProductoService** — `crear`, `listar`, `buscarPorId`, `eliminar`

### Paso 8: Crear package `controller`
- [x] **ClienteController**
  - [x] `POST /clientes`
  - [x] `GET /clientes`
  - [x] `GET /clientes/{id}`
- [x] **ProductoController**
  - [x] `POST /productos`
  - [x] `GET /productos`
  - [x] `GET /productos/{id}`
  - [x] `DELETE /productos/{id}`

### Pruebas
- [x] Script `test_api.sh` (pruebas con `curl`)
- [x] Script `importar_csv.py` (importar datos desde CSV)
- [x] Archivos CSV con datos de ejemplo (`clientes.csv`, `productos.csv`)

## Problemas

1. [x] No existe un sistema el cual permita evitar elementos duplicados cuando
   se ingresan en la API (productos y clientes)

   Solucion: Implementar una guardia segura en los servicios de clientes y
   productos que revise en la base de datos si ya existe información sensible
   repetida en el registro y asi no tener duplicados.

2. [ ] Solo como ejercicio, decidi crear un endpoint en la API para poder
   reiniciar la base de datos, a partir de Spring Boot ejecutando una query la
   cual trunque las tablas correspondientes de las entidades definidas en la
   aplicación

