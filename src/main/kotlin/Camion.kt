import kotlin.random.Random

class Camion (nombre:String, marca:String, modelo:String,capacidadCombustible: Float, combustibleActual:Float, kilometrosActuales: Float, esHibrido: Boolean, val peso:Int) :Automovil(nombre,marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales, esHibrido){


    companion object{
        const val KM_POR_LITRO_CAMION = 6.25f
    }

    override fun calcularAutonomia(): Float {
        return if(esHibrido)
            (combustibleActual * ((KM_POR_LITRO_CAMION + AHORRO_ELECTRICO) - ((peso*0.2f)/1000))).redondear(2)
        else(combustibleActual * (KM_POR_LITRO_CAMION - ((peso*0.2f)/2))).redondear(2)
    }

    override fun actualizaCombustible(distanciaReal: Float) {
        if (esHibrido) {
            val combustibleGastado = distanciaReal / (KM_POR_LITRO_CAMION + AHORRO_ELECTRICO)
            combustibleActual -= combustibleGastado.redondear(2)
        }
        else {
            val combustibleGastado = distanciaReal / KM_POR_LITRO_CAMION
            combustibleActual -= combustibleGastado.redondear(2)
        }
    }

    override fun realizaDerrape(): Pair<Float,Int>{
        val retrocesoKm = Random.nextInt(10,50) // Valor que indica lo que retrocede por realizar una filigrana
        if (esHibrido) {
            actualizaCombustible(KM_POR_DERRAPE_ELECTRICO)
            kilometrosActuales -= retrocesoKm
        }
        else {
            actualizaCombustible(KM_POR_DERRAPE)
            kilometrosActuales -= retrocesoKm
        }
        return Pair(combustibleActual, retrocesoKm)
    }

    override fun toString(): String {
        return "Camión(nombre=${nombre.capitalizar()}, capacidadCombustible=${capacidadCombustible}L, combustibleActual=${combustibleActual}L, esElectrico=$esHibrido, peso=${peso}Kg)"
    }
}