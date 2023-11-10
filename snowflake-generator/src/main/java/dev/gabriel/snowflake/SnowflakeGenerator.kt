package dev.gabriel.snowflake

import dev.gabriel.snowflake.config.Structure
import java.util.Date

/**
* This class is responsible for generating unique identifiers based on the Snowflake algorithm.
* The algorithm generates 64-bit identifiers composed of a timestamp, a machine (application) identifier, and a sequence.
*
* @property lastTimeStamp The timestamp of the last ID generation.
* @property sequence The sequence of the generated ID.
*/
object SnowflakeGenerator {

    private var lastTimeStamp = -1L
    private var sequence = -1L

    /**
     * Generates a new unique ID based on the provided parameters.
     *
     * @param applicationId The identifier of the application (machine) generating the ID.
     * @param timestamp The timestamp to be used in ID generation. If not provided, the default is the current timestamp.
     * @param structure The configuration structure of the Snowflake algorithm. Default is the standard structure.
     * @return The generated ID.
     * @throws IllegalArgumentException if the applicationId is out of the allowed range.
     */

    @Synchronized
    fun nextId(applicationId: Int, timestamp: Long = Date().time, structure: Structure = Structure.createDefault()): Long {
        if (applicationId < 0 || applicationId >= structure.maxMachineIds()) throw IllegalArgumentException("applicationId must be between 0 (inclusive) and ${structure.maxMachineIds()} (exclusive), but was ($applicationId)")
        sequence++

        var actualTimeStamp = timestamp

        if (actualTimeStamp <= lastTimeStamp) {
            if (sequence > structure.maxSequence()) {
                do {
                    actualTimeStamp = Date().time
                } while (actualTimeStamp <= lastTimeStamp)
            }
        } else {
            sequence = 0L
        }
        lastTimeStamp = actualTimeStamp

        return (timestamp shl (10 + 12)) + (applicationId shl 12) + sequence
    }

    /**
     * Extracts the components of a Snowflake ID, i.e., the timestamp, machine identifier, and sequence.
     *
     * @param id The ID to be broken down.
     * @param structure The configuration structure of the Snowflake algorithm. Default is the standard structure.
     * @return A Triple object containing the timestamp, machine identifier, and sequence.
     */
    fun extractData(id: Long, structure: Structure = Structure.createDefault()): Triple<Long, Int, Int> {
        return Triple(extractTimeStamp(id, structure), extractMachineId(id, structure), extractSequence(id, structure))
    }



    /**
     * Extracts the machine identifier from a Snowflake ID.
     *
     * @param id The ID to be broken down.
     * @param structure The configuration structure of the Snowflake algorithm. Default is the standard structure.
     * @return The machine identifier (application) contained in the ID.
     */
    fun extractMachineId(id: Long, structure: Structure = Structure.createDefault()): Int {
        val generatorIdMask = ((1L shl structure.machineIdBits) - 1) shl structure.sequenceBits
        return ((id and generatorIdMask) ushr structure.sequenceBits).toInt()
    }

    /**
     * Extracts the timestamp from a Snowflake ID.
     *
     * @param id The ID to be broken down.
     * @param structure The configuration structure of the Snowflake algorithm. Default is the standard structure.
     * @return The timestamp contained in the ID.
     */
    fun extractTimeStamp(id: Long, structure: Structure = Structure.createDefault()): Long {
        val timestampMask = (1L shl structure.timestampBits) - 1
        return (id ushr (structure.machineIdBits + structure.sequenceBits)) and timestampMask
    }

    /**
     * Extracts the sequence from a Snowflake ID.
     *
     * @param id The ID to be broken down.
     * @param structure The configuration structure of the Snowflake algorithm. Default is the standard structure.
     * @return The sequence contained in the ID.
     */
    fun extractSequence(id: Long, structure: Structure = Structure.createDefault()): Int {
        val sequenceMask = (1L shl structure.sequenceBits) - 1
        return (id and sequenceMask).toInt()
    }
}
