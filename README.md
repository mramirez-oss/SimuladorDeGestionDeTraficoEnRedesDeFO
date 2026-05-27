#Simulador de Gestion de Trafico en Redes de FO

##De que trata?
Es un sistema que simula el funcionamiento de una red de FO(GPON).
Siendo un personal de una empresa de Internet, teniendo una central (OLT) que distribuye internet a muchos clientes.
El desafio principal y central es la distribucion de la banda ancha respetando ciertas cuestiones de manera inteligente, ya sea el contrato por los mb adquiridos o el tipo de cliente.

Este programa nos da una idea minima de como resolver esas situaciones teniendo la posibilidad de agregar y eliminar ONT's o clientes definiendo en el caso de agregarlos todas las caracteristicas necesarias para la distribucion correcta de la banda


#Que puede hacer el programa?
-Agregar y eliminar clientes: Podes agregar nuevas ONT's con su informacion: ID, nombre, distancia, mb/sla garantizado, cant de usuarios/ip's fijas, etc.
-Diferenciar a los clientes por tipos: Ya sea Empresarial o Residencial, los clientes empresariales tienen prioridad en la distribucion de la banda ancha sobrante.
-El programa distribuye la banda automaticamente dependiendo del tipo del cliente y de la cantidad de clientes existentes
-Manejo de archivos: El programa puede guardar y cargar las configuraciones de OLT's con sus muchas ONT's asi permitiendo el manejo exclusivo de cada OLT sin perder informacion o cruzando olt's
-Calculos tecnicos: El programa calcula atenuacion de señal debilitandola con la distancia. Esto es algo simbolico ya que en realidad se deberia de tener un control de la atenuacion por cada cliente o zona.
-Visualizacion en tiempo real de la red: Hay una ventana desplegable que nos muestra una simulacion en tiempo real de la central.


#Organizacion del codigo
El proyecto se divide en paquetes que manejan distintas partes del mismo 
Paquete 1: Modelo: Es el paquete en el que se encuentran las clases que manejan todo el proyecto de red en si
  -DispositivoRed (Superclase padre de OLT y ONT) Abstracta
  -OLT            (central)
  -ONT            (cliente)Abstracta
  -ONTResidencial (cliente casa)
  -ONTEmpresarial (cliente empresa)

Paquete 2: Persistencia: Es el paquete que se encarga del manejo de archivos ya sea cargar o guardar datos en este caso se guardan OLT
  -GestorPersistencia (guardar y cargar olt)


Paquete 3: GUI: El paquete que se encarga de la visualizacion del programa para el usuario.
  -VentanaInicialOLT (primera ventana para crear o cargar OLT)
  -VentanaPrincipal  (donde se agregan clientes a la olt seleccionada en la ventana inicial)
  -PanelRed          (el dibujo o representacion de la red)
  -DialogRed         (Ventana en donde se ve la red)


Paquete 4: main: El paquete para iniciar el programa
  -SimuladorMain     (Para iniciar el programa)


#Utilizacion del Programa
 ##Primera Pantalla: Configurar la OLT
Cuando inicias el programa, lo primero que ves es una ventana donde configurás la central (OLT):

- ID: Un nombre para identificarla (ej: OLT-01)
- Nombre: Descripción (ej: Central Principal)
- Capacidad bajada: Cuánto Mbps total tienes (ej: 2500)
- Capacidad subida: Ancho de banda para subir datos (ej: 1250)

Tenés dos opciones:
- Crear nueva OLT: Si estás empezando de cero
- Cargar OLT existente: Si ya habías guardado una configuración anterior

##

Una vez dentro, ves un formulario donde podés:

## Agregar un cliente

1. Completas los campos:
   - **ID:** Nombre único del cliente (ej: ONT-001)
   - **Nombre:** Descripción del cliente (ej: Casa García)
   - **Distancia:** Cuántos km de fibra hay hasta ese cliente (ej: 3.2)
   - **Perfil:** ¿Es una casa (Residencial) o negocio (Empresarial)?
   - **Parámetro extra:** Depende del perfil
     - Si es Residencial: MB contrado, cant de usuarios y si tiene tv (ej: 50.0, 5, true)
     - Si es Empresarial: cuántos Mbps mínimos le garantizás e ip's fijas (ej: 500 y 10)

2. Haces clic en "Agregar"

El programa verifica que todo esté bien (que no sea muy lejos, que no tengas ya ese ID, que tengas capacidad). Si hay problema, te lo dice. Si está todo bien, la agrega automáticamente.

## Eliminar un cliente

Haces clic en "Eliminar ONT", escribís el ID del cliente, y listo.

## Ver la red visualmente

Haces clic en "Ver Red" y se abre una ventana donde ves:
- Un círculo morado en el centro (tu central OLT)
- Los clientes alrededor distribuidos en círculo
- Líneas que conectan la central con cada cliente (esas son las fibras ópticas)
- Colores: verde para clientes residenciales, azul para empresariales
- Barras que muestran cuánto ancho de banda está usando cada uno
- Números que indican la distancia en km

  #### Guardar tu configuración

Cuando terminás de agregar clientes, haces clic en "Guardar" para que no se pierda nada. Se guarda en un archivo interno llamado `red_gpon.dat`.

## El corazón del programa: Distribución de ancho de banda

Lo más importante de este sistema es cómo reparte el ancho de banda.

Imaginate que tienes 2500 Mbps para repartir entre 3 clientes:
- Cliente A (Residencial, MB minimo 50)
- Cliente B (Empresarial, con SLA de 500 Mbps)
- Cliente C (Residencial, MB minimo 100)

El programa hace esto automáticamente:

1. **Primero, garantiza lo mínimo:** Cada cliente residencial necesita al menos 50 Mbps, cada empresarial lo que dice su contrato. En este caso:
   - Cliente B: 500 Mbps (empresa tiene prioridad)
   - Cliente A: 50 Mbps (casa)
   - Cliente C: 100 Mbps (casa)
   - **Total garantizado:** 650 Mbps

2. **Después, reparte lo que sobra:** Quedan 1850 Mbps. Como los empresariales tienen mayor prioridad, ellos reciben más del excedente. Así quedan con bastante más ancho.

3. **El resultado:** Cada cliente tiene su banda mínima garantizada, pero si hay capacidad disponible, usa más.

