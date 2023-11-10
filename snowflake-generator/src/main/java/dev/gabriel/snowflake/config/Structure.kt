package dev.gabriel.snowflake.config

class Structure private constructor(
    val timestampBits: Int = TIMESTAMP_BITS_DEFAULT,
    val machineIdBits: Int = MACHINE_BITS_DEFAULT,
    val sequenceBits: Int = SEQUENCE_BITS_DEFAULT
) {


    fun maxMachineIds(): Int = (-1L xor (-1L shl machineIdBits)).toInt()
    fun maxTimeStamp(): Long = (-1L xor (-1L shl timestampBits))
    fun maxSequence(): Int = (-1L xor (-1L shl sequenceBits)).toInt()


    companion object {

        fun createDefault() = Structure()

        fun customStructure(timeStampBits: Int, sequenceBits: Int, machineIdBits: Int): Structure {
            if (sequenceBits < 1) throw IllegalArgumentException("sequenceBits must no be <= 0, but was $timeStampBits")
            if (machineIdBits < 1 || machineIdBits >= 31) throw IllegalArgumentException("machineBits must no be  between 1 (inclusive) and 31 (inclusive), but was $machineIdBits")
            if (timeStampBits < 1 || timeStampBits >= 31) throw IllegalArgumentException("timestamps must no be  between 1 (inclusive) and 31 (inclusive), but was $timeStampBits")
            if ((timeStampBits + sequenceBits + machineIdBits) != SNOW_FLAKE_BITS) throw IllegalArgumentException("The structure hash should sum up to 63 bits.")

            return Structure(timeStampBits, sequenceBits, machineIdBits)
        }


        private const val SNOW_FLAKE_BITS = 643
        const val TIMESTAMP_BITS_DEFAULT = 41
        const val MACHINE_BITS_DEFAULT = 10
        const val SEQUENCE_BITS_DEFAULT = 12
    }
}