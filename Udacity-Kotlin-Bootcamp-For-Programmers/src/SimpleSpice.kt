class SimpleSpice {
    var name: String = "curry"
    var spiciness: String = "mild"

    var heat: Int
        get() = when(spiciness) {
            "mild" -> 5
            else -> 0
        }
        set(value) { spiciness = when(heat) {
            5 -> "mild"
            else -> "low"
        }}
}