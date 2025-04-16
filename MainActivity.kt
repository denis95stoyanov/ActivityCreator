package activitycreator.com

// Импортиране на необходимите класове за работа с Android UI компоненти и управление на ресурси
import android.os.Bundle // Клас за работа с данни, свързани със структурата на дата и време
import android.widget.Button // Клас за работа с бутоните в интерфейса на приложението
import android.widget.CalendarView // Клас за календарния изглед
import androidx.appcompat.app.AppCompatActivity // Основен клас за всяка активност в Android приложенията
// Класове за работа с RecyclerView, които се използват за показване на списъци от елементи
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.* // Клас за работа с дата и време в Java
import android.content.res.Configuration // Клас за работа с конфигурациите на ресурсите (например локализация, езици и други настройки на устройството)

// Основната активност на приложението, която наследява AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Деклариране на променливи за компонентите от потребителския интерфейс
    private lateinit var calendarView: CalendarView // Календар за избор на дата
    private lateinit var btnAddEvent: Button // Бутон за добавяне на ново събитие
    private lateinit var recyclerView: RecyclerView // Списък, в който ще се показват добавените събития

    // Променлива за запазване на избраната дата
    private var selectedDate: Date = Date()  // Датата, избрана от календара

    // Лист с всички събития, които ще се показват в RecyclerView
    private val events = mutableListOf<Event>()

    // Адаптер за RecyclerView, който ще управлява събитията
    private lateinit var adapter: EventAdapter

    // Метод, който се извиква при създаване на активността
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройваме локализацията на български език
        val locale = Locale("bg")  // Задаваме локализация на български
        Locale.setDefault(locale)  // Настройваме езика на устройството
        val config = resources.configuration  // Получаваме конфигурацията на ресурса
        config.setLocale(locale)  // Променяме локализацията
        createConfigurationContext(config)  // Прилагаме новата конфигурация

        // Зареждаме XML layout за активността
        setContentView(R.layout.activity_main)

        // Инициализиране на компонентите от интерфейса
        calendarView = findViewById(R.id.calendarView)  // Инициализиране на CalendarView
        btnAddEvent = findViewById(R.id.btnAddEvent)  // Инициализиране на бутон за добавяне на събитие
        recyclerView = findViewById(R.id.recyclerView)  // Инициализиране на RecyclerView

        // Инициализиране на избраната дата (първоначално е текущата дата)
        selectedDate = Date(calendarView.date)  // Избиране на текущата дата в календара

        // Настройване на RecyclerView за показване на събития
        adapter = EventAdapter(events)  // Създаване на адаптер с празен списък от събития
        recyclerView.layoutManager = LinearLayoutManager(this)  // Използваме LinearLayoutManager за подреждане
        recyclerView.adapter = adapter  // Задаваме адаптера на RecyclerView

        // Слушател за промяна на дата в календара
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()  // Създаваме нов календар
            calendar.set(year, month, dayOfMonth)  // Задаваме избраната дата
            selectedDate = calendar.time  // Обновяваме избраната дата
        }

        // При натискане на бутона за добавяне на събитие
        btnAddEvent.setOnClickListener {
            // Използваме избраната дата от календара и я предаваме на AddEventDialog
            AddEventDialog(this, selectedDate) { newEvent ->
                events.add(newEvent)  // Добавяме новото събитие в списъка
                events.sortBy { it.date }  // Сортираме събитията по дата
                adapter.updateEvents(events)  // Обновяваме RecyclerView с новите събития
            }.show()  // Показваме диалога за добавяне на събитие
        }
    }
}
