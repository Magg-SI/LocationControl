package com.example.locationcontrol.data.model

sealed class Place {
    var list : MutableList<Location> = ArrayList()

    init{
        setupList()
    }

    abstract fun setupList()

    class Wiata : Place(){
        companion object{
            const val SIGN = "P"
            const val FIRST = 1
            const val LAST = 37
        }
        override fun setupList(){
            for (x in FIRST.. LAST){
                list.add(Location("$SIGN$x"))
            }
        }
    }

    class Bexy : Place(){
        companion object{
            const val SIGN = "P"
            const val FIRST = 1
            const val LAST = 30
        }
        override fun setupList(){
            for (x in FIRST.. LAST){
                list.add(Location("$SIGN$x"))
            }
        }
    }

    class W : Place(){
        companion object{
            const val SIGN = "W"
            const val FIRST = 21
            const val LAST = 45
        }
        override fun setupList(){
            for (x in FIRST.. LAST){
                list.add(Location("$SIGN$x"))
            }
        }
    }

    class D : Place(){
        companion object{
            const val SIGN = "D"
            const val FIRST = 1
            const val LAST = 31
        }
        override fun setupList(){
            for (x in FIRST.. LAST){
                list.add(Location("$SIGN$x"))
            }
        }
    }

    class S : Place(){
        companion object{
            const val SIGN = "S"
            const val FIRST = 1
            const val LAST = 37
        }
        override fun setupList(){
            for (x in FIRST.. LAST){
                list.add(Location("$SIGN$x"))
            }
        }
    }

}