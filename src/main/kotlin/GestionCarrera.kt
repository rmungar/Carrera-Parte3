import kotlin.random.Random
import kotlin.system.exitProcess

class GestionCarrera {

    companion object{
        val LISTADEMARCAS_COCHES = listOf("Seat","Wolkswagen","Hyundai","Honda","Dacia","Dodge","Masserati","Ferrari","Mercedes","Citroen","Peugeot","Toyota") //Posibles marcas de coches
        val LISTADEMODELOS_COCHES = listOf("Sandero","Demon","Touran","CLA","CH-R","C4-Cactus","Portofino", "Tucson","Civic","Grecale","3008","Panda") //Posibles modelos de coches
        val LISTADEMARCAS_MOTOS = listOf("Honda","Kawasaki","Harley","Yamaha","Suzuki","Ducati","BMW") // Posibles marcas de motos
        val LISTADEMODELOS_MOTOS = listOf("X-Max","Scrambler","Nija","V-rod","F 900 GS","GSX-S1000 GT","CBR650R") //Posibles modelos de motos
        val CILINDRADAS_MOTOS = listOf(125,250,400,500,750,900,1000)

        fun obtenerMarca(tipo:TipoVehiculo):String {
            when (tipo) {
                TipoVehiculo.Automovil -> return LISTADEMARCAS_COCHES.random()
                TipoVehiculo.Motocicleta -> return LISTADEMARCAS_MOTOS.random()
                else -> return ""
            }
        }
        fun obtenerModelo(tipo: TipoVehiculo):String {
            when (tipo) {
                TipoVehiculo.Automovil -> return LISTADEMODELOS_COCHES.random()
                TipoVehiculo.Motocicleta -> return LISTADEMODELOS_MOTOS.random()
                else -> return ""
            }
        }

        fun obtenerCapacidadCombustible(tipo: TipoVehiculo):Float{
            when(tipo){
                TipoVehiculo.Automovil -> return Random.nextInt(30,60).toFloat().redondear(2)
                TipoVehiculo.Motocicleta -> return Random.nextInt(15,30).toFloat().redondear(2)
                TipoVehiculo.Camion -> return Random.nextInt(90,150).toFloat().redondear(2)
                TipoVehiculo.Quad -> return Random.nextInt(20,40).toFloat().redondear(2)
                else -> return 0f
            }
        }
        fun obtenerCombustibleActual(combustibleMax: Float):Float{
            var porcentaje = Random.nextFloat()
            while (porcentaje !in 0.2..1.0){
                porcentaje = Random.nextFloat()
            }
            return (combustibleMax * porcentaje).redondear(2)
        }

        fun obtenerPesos():Int{
            return Random.nextInt(1000,10000)
        }
    }

    fun generarParticipantes(){
        print("Ingrese el número de jugadores -> ")
        val jugadores = readln().toInt()
        require(jugadores > 1){"El número de jugadores ha de ser mayor a 1"}
        println("--------------------------------------")
        for (jugador in 1..jugadores){
            print("Ingrese el nombre del jugador $jugador -> ")
            val nombreVehiculo = readln()
            require(nombreVehiculo.isNotBlank()){"El nombre del vehículo no puede estar vacío"}
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