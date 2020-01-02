package com.smarttoolfactory.tutorial4_2kotlin_mockk.model_car

class Car {


    fun drive(direction: Direction): Outcome {

        return when (direction) {
            Direction.NORTH -> Outcome.OK
            Direction.EAST -> Outcome.RECORDED
            Direction.SOUTH -> Outcome.RECORDED
            Direction.WEST -> Outcome.RECORDED
        }
    }

}


enum class Direction(azimuth: Int) {

    NORTH(360),
    EAST(90),
    SOUTH(180),
    WEST(270)

}

enum class Outcome {

    OK,
    RECORDED
}