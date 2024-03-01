
class Camion (nombre:String, marca:String, modelo:String,capacidadCombustible: Float, combustibleActual:Float, kilometrosActuales: Float, esHibrido: Boolean, val peso:Int) :Automovil(nombre,marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales, esHibrido){

    init {
        require(peso in 1000..10000){"El peso debe estar entre 1000 y 10000 kilos"}

    }

    companion object{
        const val KM_POR_LITRO_CAMION = 6.25f
    }

    override fun calcularAutonomia(): Float {
        return if(esHibrido)
            (combustibleActual * ((KM_POR_LITRO_CAMION + AHORRO_ELECTRICO) - (peso*0.2f))).redondear(2)
        else(combustibleActual * (KM_POR_LITRO_CAMION - (peso*0.2f)))
    }

    override fun actualizaCombustible(distanciaReal: Float) {
        if (esHibrido) {
            val combustibleGastado = (distanciaReal / (KM_POR_LITRO_CAMION + AHORRO_ELECTRICO))
            combustibleActual -= combustibleGastado.redondear(2)
        }
        else {
            val combustibleGastado = distanciaReal / KM_POR_LITRO_CAMION
            combustibleActual -= combustibleGastado.redondear(2)
        }
    }

    override fun realizaDerrape(): Float {
        if (esHibrido) {
            actualizaCombustible(KM_POR_DERRAPE_ELECTRICO)
        }
        else {
            actualizaCombustible(KM_POR_DERRAPE)
        }
        return combustibleActual
    }

    override fun toString(): String {
        return "Camión(nombre=$nombre, capacidadCombustible=${capacidadCombustible}L, combustibleActual=${combustibleActual}L, esElectrico=$esHibrido, peso=${peso}Kg)"
    }
}