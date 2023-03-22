package ir.thealif.tennis.models

import java.util.*

data class PlayerModel(val name: String) {
    var uuid: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {
        if (other !is PlayerModel)
            return false

        return this.uuid == other.uuid
    }

    override fun hashCode(): Int {
        return name.hashCode() * uuid.hashCode()
    }
}
