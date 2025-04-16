package activitycreator.com

// Импорти, необходими за работа с изгледи, списъци и форматиране на дата
import android.content.Intent // Импортиране на клас за създаване на намерения (Intent) за стартиране на нови дейности или действия в приложението
import android.net.Uri // Импортиране на клас за работа с URI адреси (Uri) за създаване на линкове и извикване на действия с тях
import android.view.LayoutInflater // Импортиране на клас за създаване на изгледи от XML файлове и за тяхното настаняване в изгледа
import android.view.View // Импортиране на клас за представяне на изгледите на елементите в списък (в случая, всеки елемент в RecyclerView)
import android.view.ViewGroup // Импортиране на клас за управление на изгледи в групи, като RecyclerView, който съдържа множество елементи
import android.widget.TextView // Импортиране на клас за използване на текстови полета (TextView), които ще показват информация като заглавие, дата, местоположение и др.
import androidx.recyclerview.widget.RecyclerView // Импортиране на клас за работа с RecyclerView, който е необходим за създаване на списъци от данни в приложението
import java.text.SimpleDateFormat // Импортиране на клас за форматиране на дати в различни формати
import java.util.* // Импортиране на клас за работа с дати и време в приложението

// Адаптер за RecyclerView, който показва списък от събития
class EventAdapter(private var events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // Актуализиране на списъка със събития и презареждане на изгледа
    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

    // Вътрешен клас, който държи изгледите за всяко събитие (ViewHolder)
    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.eventTitle)       // Текстово поле за заглавие
        val dateText: TextView = view.findViewById(R.id.eventDate)         // Текстово поле за дата
        val locationText: TextView = view.findViewById(R.id.eventLocation) // Текстово поле за местоположение
    }

    // Създаване на нов изглед за елемент от списъка (вика се автоматично)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false) // Създава изглед от XML файла
        return EventViewHolder(view)
    }

    // Свързва данните със съответния изглед (вика се за всеки елемент)
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleText.text = event.title

        // Форматираме датата на български
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("bg"))
        holder.dateText.text = formatter.format(event.date)

        // Ако има въведено местоположение, го показваме и го правим кликваемо
        if (!event.location.isNullOrBlank()) {
            holder.locationText.text = event.location
            holder.locationText.visibility = View.VISIBLE

            // При клик на местоположението отваряме Google Maps с него
            holder.locationText.setOnClickListener {
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(event.location)}")
                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage("com.google.android.apps.maps") // Явно указваме Google Maps
                }

                // Проверяваме дали има инсталирано приложение за работа с този intent
                if (intent.resolveActivity(holder.itemView.context.packageManager) != null) {
                    holder.itemView.context.startActivity(intent)
                }
            }
        } else {
            // Ако няма местоположение, скриваме TextView
            holder.locationText.visibility = View.GONE
        }
    }

    // Връща броя на събитията в списъка
    override fun getItemCount(): Int = events.size
}
