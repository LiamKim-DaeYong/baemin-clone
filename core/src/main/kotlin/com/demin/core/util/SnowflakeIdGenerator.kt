package com.demin.core.util

class SnowflakeIdGenerator(
    private val workerId: Long,
    private val datacenterId: Long
) {
    private val twepoch = 1288834974657L
    private val workerIdBits = 5L
    private val datacenterIdBits = 5L
    private val maxWorkerId = -1L xor (-1L shl workerIdBits.toInt())
    private val maxDatacenterId = -1L xor (-1L shl datacenterIdBits.toInt())
    private val sequenceBits = 12L
    private val workerIdShift = sequenceBits
    private val datacenterIdShift = sequenceBits + workerIdBits
    private val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits
    private val sequenceMask = -1L xor (-1L shl sequenceBits.toInt())

    private var lastTimestamp = -1L
    private var sequence = 0L

    init {
        require(!(workerId > maxWorkerId || workerId < 0)) { "worker Id can't be greater than $maxWorkerId or less than 0" }
        require(!(datacenterId > maxDatacenterId || datacenterId < 0)) { "datacenter Id can't be greater than $maxDatacenterId or less than 0" }
    }

    @Synchronized
    fun nextId(): String {
        var timestamp = timeGen()

        if (timestamp < lastTimestamp) {
            throw RuntimeException("Clock moved backwards. Refusing to generate id for ${lastTimestamp - timestamp} milliseconds")
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) and sequenceMask
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0L
        }

        lastTimestamp = timestamp
        val nextId = (timestamp - twepoch shl timestampLeftShift.toInt()) or
                (datacenterId shl datacenterIdShift.toInt()) or
                (workerId shl workerIdShift.toInt()) or
                sequence
        return nextId.toString()
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen()
        }
        return timestamp
    }

    private fun timeGen(): Long {
        return System.currentTimeMillis()
    }
}