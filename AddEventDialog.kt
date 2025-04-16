package activitycreator.com

import android.app.AlertDialog // Импортиране на клас за създаване на диалогови прозорци (AlertDialog)
import android.content.Context // Импортиране на Context, за да можем да използваме контекста на приложението за създаване на изгледи и диалози
import android.widget.EditText // Импортиране на клас за използване на текстови полета в приложението (EditText)
import android.widget.LinearLayout // Импортиране на клас за използване на вертикални и хоризонтални подредби на елементи в изгледа (LinearLayout)
import java.util.* // Импортиране на клас за работа с дати (Date), за да създадем и манипулираме с дати в приложението

class AddEventDialog(
    private val context: Context, // Контекстът на приложението, необходим за създаване на диалогови прозорци и достъп до ресурси
    private val selectedDate: Date,  // Получаваме избраната дата от календара
    private val onEventAdded: (Event) -> Unit  // Callback функция, която ще се извика при добавяне на събитие
) {

    // Метод за показване на диалоговия прозорец
    fun show() {
        // Създаваме текстово поле за въвеждане на заглавие на събитието
        val inputTitle = EditText(context).apply {
            hint = "Име на събитието"
        }

        // Създаваме второ текстово поле за въвеждане на местоположение (не е задължително)
        val inputLocation = EditText(context).apply {
            hint = "Местоположение (по избор)"
        }

        // Създаваме вертикално подреден лейаут и добавяме полетата към него
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL  // Подреждаме елементите вертикално
            setPadding(50, 40, 50, 10)  // Задаваме вътрешен отстъп
            addView(inputTitle)        // Добавяме полето за заглавие
            addView(inputLocation)     // Добавяме полето за местоположение
        }

        // Създаваме и показваме AlertDialog, който съдържа нашия custom layout
        AlertDialog.Builder(context)
            .setTitle("Ново събитие")  // Заглавие на прозореца
            .setView(layout)           // Задаваме изгледа, който съдържа двете текстови полета
            .setPositiveButton("Добави") { _, _ ->  // При натискане на бутона "Добави"
                val title = inputTitle.text.toString() // Вземаме текста от полето за името на събитието и го преобразуваме в низ (String)
                val location = inputLocation.text.toString() // Вземаме текста от полето за местоположението и го преобразуваме в низ (String)

                // Проверяваме дали е въведено заглавие
                if (title.isNotBlank()) {
                    // Създаваме обект Event и подаваме избраната дата и местоположение (ако има)
                    val event = Event(title, selectedDate, if (location.isBlank()) null else location)

                    // Извикваме callback функцията и предаваме събитието
                    onEventAdded(event)
                }
            }
            .setNegativeButton("Отказ", null)  // При натискане на "Отказ" не правим нищо
            .show()  // Показваме диалоговия прозорец
    }
}
