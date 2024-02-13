package com.example.taskat.core.ui


object TaskPersons {
    private var specialistID: Int ? =  null
    private  var specialistName: String = ""
    private var distributorID: Int ? =  null
    private  var distributorName: String = ""

    fun setSpecialistID(specialistID: Int ){
        this.specialistID = specialistID
    }
    fun setSpecialistName(specialistName: String ){
        this.specialistName = specialistName
    }
    fun setDistributorID(distributorID: Int ){
        this.distributorID = distributorID
    }
    fun setDistributorName(distributorName: String ){
        this.distributorName = distributorName
    }
    fun getSpecialistID() = specialistID
    fun getSpecialistName() = specialistName

    fun getDistributorID() = distributorID
    fun getDistributorName() = distributorName
    fun clear(){
        setSpecialistID(-1)
        setDistributorID(-1)
        setSpecialistName("")
        setDistributorName("")
    }
}