class Quad(nombre:String, marca:String, modelo:String, capacidadCombustible:Float, combustibleActual:Float, kilometrosActuales:Float, cilindrada:Int): Motocicleta(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales, cilindrada) {
    val tipo:String = TIPOS_QUAD.random()

    init {

    }
    companion object{
         val TIPOS_QUAD = listOf( "Cuadriciclos ligeros", "Cuadriciclos no ligeros","Vehículos especiales")
    }

    override fun toString(): String {
        return "Quad(nombre=$nombre, capacidad=${capacidadCombustible}L, combustible=${combustibleActual}L, cc=$cilindrada)"
    }
}