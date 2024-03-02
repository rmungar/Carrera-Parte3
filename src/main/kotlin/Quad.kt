class Quad(nombre:String, marca:String, modelo:String, capacidadCombustible:Float, combustibleActual:Float, kilometrosActuales:Float, cilindrada:Int): Motocicleta(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales, cilindrada) {
    val tipo:String = TIPOS_QUAD.random()


    companion object{
         val TIPOS_QUAD = listOf( "Cuadriciclos ligeros", "Cuadriciclos no ligeros","Vehículos especiales")
    }

    override fun calcularAutonomia(): Float {
        return (super.calcularAutonomia()/2).redondear(2)
    }


    override fun toString(): String {
        return "Quad(nombre=${nombre.capitalizar()}, tipo=$tipo, capacidad=${capacidadCombustible}L, combustible=${combustibleActual}L, cc=$cilindrada)"
    }
}