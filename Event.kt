package activitycreator.com

import java.util.Date  // Импорт на класа Date, който използваме за представяне на дата и час

data class Event(  // Данни за събитие – използваме data class за автоматично генериране на полезни методи (като toString(), equals() и copy())
    val title: String,  // Заглавие на събитието
    val date: Date,  // Дата, на която се провежда събитието
    val location: String? = null // Местоположение на събитието (по избор, може да е null)
)
