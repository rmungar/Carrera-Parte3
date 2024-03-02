import kotlin.random.Random

open class GestionCarrera {

    companion object{
        private val LISTADEMARCAS_COCHES = listOf("Seat","Wolkswagen","Hyundai","Honda","Dacia","Dodge","Masserati","Ferrari","Mercedes","Citroen","Peugeot","Toyota") //Posibles marcas de coches
        private val LISTADEMODELOS_COCHES = listOf("Sandero","Demon","Touran","CLA","CH-R","C4-Cactus","Portofino", "Tucson","Civic","Grecale","3008","Panda") //Posibles modelos de coches
        private val LISTADEMARCAS_MOTOS = listOf("Honda","Kawasaki","Harley","Yamaha","Suzuki","Ducati","BMW") // Posibles marcas de motos
        private val LISTADEMODELOS_MOTOS = listOf("X-Max","Scrambler","Nija","V-rod","F 900 GS","GSX-S1000 GT","CBR650R") //Posibles modelos de motos
        private val CILINDRADAS_MOTOS = listOf(125,250,400,500,750,900,1000) //Posibles cilindradas de motos
        private val NOMBRES_OCUPADOS = mutableListOf<String>() //Lista que almacenará los nombres ya ingresados por el usuario

        fun obtenerMarca(tipo:TipoVehiculo):String {
            return when (tipo) {
                TipoVehiculo.Automovil -> LISTADEMARCAS_COCHES.random()
                TipoVehiculo.Motocicleta -> LISTADEMARCAS_MOTOS.random()
                else -> ""
            }
        }
        fun obtenerModelo(tipo: TipoVehiculo):String {
            return when (tipo) {
                TipoVehiculo.Automovil -> LISTADEMODELOS_COCHES.random()
                TipoVehiculo.Motocicleta -> LISTADEMODELOS_MOTOS.random()
                else -> ""
            }
        }

        fun obtenerCapacidadCombustible(tipo: TipoVehiculo):Float{
            return when(tipo){
                TipoVehiculo.Automovil -> Random.nextInt(30,60).toFloat().redondear(2)
                TipoVehiculo.Motocicleta -> Random.nextInt(15,30).toFloat().redondear(2)
                TipoVehiculo.Camion -> Random.nextInt(90,150).toFloat().redondear(2)
                TipoVehiculo.Quad -> Random.nextInt(20,40).toFloat().redondear(2)
            }
        }
        fun obtenerCombustibleActual(combustibleMax: Float):Float{
            var porcentaje = Random.nextFloat() // Porcentaje sobre la capacidad máxima con la que van a inicar los vehiculos
            while (porcentaje !in 0.2..1.0){
                porcentaje = Random.nextFloat()
            }
            return (combustibleMax * porcentaje).redondear(2)
        }

        fun obtenerPesos():Int{
            return Random.nextInt(1000,10000) //Peso aleatorio de los camiones
        }

        fun comprobarNombre(nombreIntroducido: String):String{
            var nombreCorregido: String// Cadena que se va a modificar y retornar en caso de que la cadena ingresada por el usuario no se correcta
            if (nombreIntroducido.isBlank() || nombreIntroducido.isEmpty() || NOMBRES_OCUPADOS.contains(nombreIntroducido)){
                println("Vuelva a ingresar un nombre para el vehículo: ")
                nombreCorregido = readln().lowercase()
                while (nombreCorregido.isBlank() || nombreCorregido.isEmpty() || NOMBRES_OCUPADOS.contains(nombreCorregido)){
                    print("Vuelva a ingresar un nombre para el vehículo: ")
                    nombreCorregido = readln().lowercase()
                }
                NOMBRES_OCUPADOS.add(nombreCorregido)
                return nombreCorregido
            }
            NOMBRES_OCUPADOS.add(nombreIntroducido)
            return nombreIntroducido
        }

        fun comprobarJugadores():Int{
            print("Ingrese el número de jugadores -> ")
            try {
                var jugadores = readln().toInt() //Entero que representa la cantidad de instancias a generar
                while (jugadores !in 1..15) {
                    print("Vuelva a ingresar el número de jugadores -> ")
                    jugadores = readln().toInt()
                }
                return jugadores
            }
            catch (e:NumberFormatException){
                println("Ese valor no está dentro del rango")
            }
            return 0
        }
        fun mostrarEntreRondas(contador:Int, carrera: Carrera){
            val participantes = carrera.participantes
            var posicion = 1
            var tipo:TipoVehiculo
            println("*** CLASIFICACIÓN PARCIAL (ronda $contador) ***")
            for (vehiculo in participantes.sortedByDescending{ it.kilometrosActuales }){
                when (vehiculo){
                    is Automovil -> {
                        if (vehiculo is Camion){
                            tipo = TipoVehiculo.Camion
                            println("$posicion ${vehiculo.obtenerInformacion(tipo)}")
                        }
                        else    {
                            tipo = TipoVehiculo.Automovil
                            println("$posicion ${vehiculo.obtenerInformacion(tipo)}")
                        }
                    }
                    is Motocicleta -> {
                        if (vehiculo is Quad){
                            tipo = TipoVehiculo.Quad
                            println("$posicion ${vehiculo.obtenerInformacion(tipo)}")
                        }
                        else{
                            tipo = TipoVehiculo.Motocicleta
                            println("$posicion ${vehiculo.obtenerInformacion(tipo)}")
                        }
                    }
                }
                posicion++
            }
            println()
        }
    }

    fun generarParticipantes():List<Vehiculo>{
        val participantes = mutableListOf<Vehiculo>()
        val jugadores = comprobarJugadores() //Entero que representa la cantidad de instancias a generar
        println("--------------------------------------")
        for (jugador in 1..jugadores){
            print("Ingrese el nombre del jugador $jugador -> ")
            var nombreVehiculo = readln().lowercase().trim() // Nombre del vehículo del usuario
            nombreVehiculo = comprobarNombre(nombreVehiculo)
            val tipoDeVehiculo = TipoVehiculo.entries.random() //Genera un tipo de vehículo aleatorio
            val vehiculoGenerado = generarVehiculo(tipoDeVehiculo,  nombreVehiculo) //El vehículo ya generado, con el nombre que ingresó el usuario
            mostrarVehiculo(vehiculoGenerado)
            participantes.add(vehiculoGenerado)
        }
        return participantes.toList()
    }

    private fun generarVehiculo(tipoDeVehiculo: TipoVehiculo, nombreVehiculo:String):Vehiculo{

        val marca = obtenerMarca(tipoDeVehiculo) // La marca del vehículo
        val modelo = obtenerModelo(tipoDeVehiculo) // El modelo del vehículo
        val combustibleMax = obtenerCapacidadCombustible(tipoDeVehiculo) // Capacidad de combustile del vehículo
        val combustibleActual = obtenerCombustibleActual(combustibleMax) // Combustible con el que el vehículo va a iniciar la carrera

        when (tipoDeVehiculo){
            TipoVehiculo.Automovil -> {
                val hibrido = Random.nextBoolean() // Valor aleatorio (true / false) para la propiedad esHibrido
                val automovil = Automovil(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, hibrido)
                return automovil
            }
            TipoVehiculo.Motocicleta -> {
                val cilindradas = CILINDRADAS_MOTOS.random() // Entero aleatorio procedente de la lista para el valor de la propiedad cilindradas
                val motocicleta = Motocicleta(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, cilindradas)
                return motocicleta
            }
            TipoVehiculo.Camion -> {
                val hibrido = Random.nextBoolean() // Valor aleatorio (true / false) para la propiedad esHibrido
                val peso = obtenerPesos() // Entero aleatorio (entre 1.000 y 10.000) para el valor de la propiedad peso
                val camion = Camion(nombreVehiculo,marca, modelo, combustibleMax, combustibleActual, 0.0F, hibrido, peso)
                return camion
            }
            TipoVehiculo.Quad -> {
                val cilindradas = CILINDRADAS_MOTOS.random() // Entero aleatorio procedente de la lista para el valor de la propiedad cilindradas
                val quad = Quad(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, cilindradas)
                return quad
            }
        }
    }

    private fun mostrarVehiculo(vehiculoGenerado:Vehiculo){
        println("Te ha tocado un ${vehiculoGenerado}")
    }


}