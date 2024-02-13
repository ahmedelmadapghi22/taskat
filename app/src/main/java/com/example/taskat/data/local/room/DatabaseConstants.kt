package com.example.taskat.data.local.room

object DatabaseConstants {
   // Task
   internal const val TASK_TABLE = "Tasks"
   internal const val TASK_ID_COLUMN = "taskID"
   internal const val TASK_NAME_COLUMN = "taskName"
   internal const val TASK_DATE_COLUMN = "taskDate"
   internal const val TASK_PRICE_COLUMN = "taskPrice"
   internal const val TASK_PRICE_SPECIALIST_COLUMN = "taskPriceSpecialist"
   internal const val TASK_CURRENCY_INCOME_COLUMN = "currencyOfDistributor"
   internal const val TASK_CURRENCY_OUTCOME_COLUMN = "currencyOfSpecialist"
   internal const val TASK_STATE_COLUMN = "taskState"
   internal const val TASK_IS_DISTRIBUTOR_ACCOUNTED_COLUMN = "isDistributorAccounted"
   internal const val TASK_IS_SPECIALIST_ACCOUNTED_COLUMN = "isSpecialistAccounted"
   internal const val TASK_FK_DELIVERER_COLUMN = "FKdeliverer"
   internal const val TASK_FK_RECIPIENT_COLUMN = "FKrecipient"
   //Deliverer
   internal const val DELIVERER_TABLE = "Deliverers"
   internal const val DELIVERER_ID_COLUMN = "delivererID"
   internal const val DELIVERER_NAME_COLUMN = "delivererName"
   internal const val DELIVERER_PHONE_COLUMN = "delivererPhone"
   //Recipient
   internal const val RECIPIENT_TABLE = "Recipient"
   internal const val RECIPIENT_ID_COLUMN = "recipientID"
   internal const val RECIPIENT_NAME_COLUMN = "recipientName"
   internal const val RECIPIENT_PHONE_COLUMN = "recipientPhone"
   internal const val RECIPIENT_NOTE_COLUMN = "recipientNote"
   internal const val RECIPIENT_EVALUATION_COLUMN = "recipientEvaluation"

   //Specialization
   internal const val SPECIALIZATION_TABLE = "Specializations"
   internal const val SPECIALIZATION_ID_COLUMN = "specializationID"
   internal const val SPECIALIZATION_NAME_COLUMN = "specializationName"
   //Specialist_Specialization
   internal const val SPECIALIST_SPECIALIZATION_TABLE = "Specialist_Specializations"
   internal const val SPECIALIST_SPECIALIZATION_FK_SPECIALIST = "specialistID"
   internal const val SPECIALIST_SPECIALIZATION_FK_SPECIALIZATION = "specializationID"

}