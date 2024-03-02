import kotlin.random.Random
import kotlin.system.exitProcess

class GestionCarrera {

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
                else -> 0f
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
            var nombreCorregido = "" // Cadena que se va a modificar y retornar en caso de que la cadena ingresada por el usuario no se correcta
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
    }

    fun generarParticipantes(){
        print("Ingrese el número de jugadores -> ")
        val jugadores = readln().toInt()
        require(jugadores > 1){"El número de jugadores ha de ser mayor a 1"}
        println("--------------------------------------")
        for (jugador in 1..jugadores){
            print("Ingrese el nombre del jugador $jugador -> ")
            var nombreVehiculo = readln().lowercase().trim()
            nombreVehiculo = comprobarNombre(nombreVehiculo)
            val tipoDeVehiculo = TipoVehiculo.entries.random()
            val vehiculoGenerado = generarVehiculo(tipoDeVehiculo,  nombreVehiculo) //El vehículo ya generado, con el nombre que ingresó el usuario
            mostrarVehiculo(vehiculoGenerado)

        }
    }

    fun generarVehiculo(tipoDeVehiculo: TipoVehiculo, nombreVehiculo:String):Vehiculo{

        val marca = obtenerMarca(tipoDeVehiculo)
        val modelo = obtenerModelo(tipoDeVehiculo)
        val combustibleMax = obtenerCapacidadCombustible(tipoDeVehiculo)
        val combustibleActual = obtenerCombustibleActual(combustibleMax)

        when (tipoDeVehiculo){
            TipoVehiculo.Automovil -> {
                val hibrido = Random.nextBoolean()
                val automovil = Automovil(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, hibrido)
                return automovil
            }
            TipoVehiculo.Motocicleta -> {
                val cilindradas = CILINDRADAS_MOTOS.random()
                val motocicleta = Motocicleta(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, cilindradas)
                return motocicleta
            }
            TipoVehiculo.Camion -> {
                val hibrido = Random.nextBoolean()
                val peso = obtenerPesos()
                val camion = Camion(nombreVehiculo,marca, modelo, combustibleMax, combustibleActual, 0.0F, hibrido, peso)
                return camion
            }
            TipoVehiculo.Quad -> {
                val cilindradas = CILINDRADAS_MOTOS.random()
                val quad = Quad(nombreVehiculo, marca, modelo, combustibleMax, combustibleActual, 0.0F, cilindradas)
                return quad
            }
        }
    }

    fun mostrarVehiculo(vehiculoGenerado:Vehiculo){
        println("Te ha tocado un ${vehiculoGenerado.toString()}")
    }
}